import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';

export const UseAnnouncementStore = defineStore('announcement', {
    state: () => ({
        // 담당자 정보를 저장할 state
        managerInfo: {
            managerName: '',
            phone1: '',
            phone2: '',
            phone3: '',
            managerEmail: '',
            isManagerNamePrivate: false,
            isPhonePrivate: false,
            isEmailPrivate: false
        },

        // 복리후생 정보를 저장할 state
        companyBenefits: [], // 복리후생 카테고리 및 세부 항목 리스트

        // // 폼 데이터를 저장할 state
        // formData: {
        //     title: '',
        //     recruitmentFieldName: '',
        //     numberOfRecruit: 0,
        //     isNewbie: false,
        //     isExperienced: false,
        //     mainDuty: '',
        //     department: '',
        //     position: '',
        //     requirement: '',
        //     isOverseas: false,
        //     isReworkPossible: false,
        //     workLocationCity: '',
        //     workLocationDetail: '',
        //     salaryType: '연봉',
        //     salaryAmount: 0,
        //     workHoursPerWeek: 0,
        //     isNegotiable: false,
        //     workDays: '주 5일(월~금)',
        //     startTime: '07시',
        //     endTime: '17시',
        //     additionalBenefits: '',
        //     managerName: '',
        //     isManagerNamePrivate: false,
        //     phone1: '',
        //     phone2: '',
        //     phone3: '',
        //     isPhonePrivate: false,
        //     email: '',
        //     isEmailPrivate: false,
        //     companyIntro: '',
        //     startDate: '',
        //     endDate: '',
        //     startTimeRegi: '07시',
        //     endTimeRegi: '18시',
        //     recruitmentType: '마감일지정',
        //     interviewCount: '선택해 주세요',
        //     precautions: '',
        // },

    }),
    persist: {},
    actions: {
        // 채용담당자 정보 조회
        async fetchManagerInfo(recruiterIdx) {
            try {
                const response = await axios.get(
                    `${backend}/announce/read-recruiter-info?recruiterIdx=${recruiterIdx}`,
                    {
                        headers: { 'Content-Type': 'application/json', },
                        // withCredentials: true
                    });
                const data = response.data;
                console.log(data);

                // API 응답 데이터를 state에 저장
                this.managerInfo.managerName = data.result.managerName || '';
                this.managerInfo.phone1 = data.result.phone1 || '';
                this.managerInfo.phone2 = data.result.phone2 || '';
                this.managerInfo.phone3 = data.result.phone3 || '';
                this.managerInfo.managerEmail = data.result.managerEmail || '';
            } catch (error) {
                console.error('담당자 정보를 불러오지 못했습니다.', error);
            }
        },

        // 기업 복리후생 정보 조회
        async fetchCompanyBenefits(recruiterIdx) {
            try {
                const response = await axios.get(
                    `${backend}/announcement/read-company-info?recruiterIdx=${recruiterIdx}`,
                    {
                        headers: { 'Content-Type': 'application/json' },
                        // withCredentials: true
                    }
                );
                const data = response.data;
                console.log(data);

                // API 응답 데이터를 state에 저장
                this.companyBenefits = data.result.benefitsDataList || []; // result 자체가 리스트인데? 그래서 result를 넘김? x 
                // benefitsDataList에서 리스트 추출 = || []
                // console.log(this.companyBenefits);

            } catch (error) {
                console.error('기업 복리후생 정보를 불러오지 못했습니다.', error);
                throw new Error(error.response?.data?.message || '복리후생 정보 조회 실패');
            }
        },

        // formData를 백엔드로 전송하는 함수
        async saveFormData(formData, isImageUpload, selectedCategories) {
            try {
                // 전송할 데이터 준비
                const postData = {
                    title: formData.title, // 공고제목
                    selectForm: isImageUpload.value, // 양식선택

                    jobCategory: selectedCategories, // 직무 카테고리
                    jobTitle: formData.recruitmentFieldName, // 모집분야명
                    recruitedNum: formData.numberOfRecruit, // 모집인원
                    careerBase: formData.isNewbie.value ? "신입" : formData.isExperienced.value ? "경력" : "", // 경력
                    positionQuali: "주요 업무 : " + formData.mainDuty + " / " + 
                                    "근무 부서 : " + formData.department + " / " + 
                                    "직급 직책 : " + formData.position + " / " + 
                                    "필수/우대 조건 : " + formData.requirement, // 포지션&자격요건

                    isOverseas: this.formData.isOverseas,
                    isReworkPossible: this.formData.isReworkPossible,
                    workLocationCity: this.formData.workLocationCity,
                    workLocationDetail: this.formData.workLocationDetail,
                    salaryType: this.formData.salaryType,
                    salaryAmount: this.formData.salaryAmount,
                    workHoursPerWeek: this.formData.workHoursPerWeek,
                    isNegotiable: this.formData.isNegotiable,
                    workDays: this.formData.workDays,
                    startTime: this.formData.startTime,
                    endTime: this.formData.endTime,
                    additionalBenefits: this.formData.additionalBenefits,
                    managerName: this.formData.isManagerNamePrivate ? '비공개' : this.formData.managerName,
                    managerContact: this.formData.isPhonePrivate
                        ? '비공개'
                        : `${this.formData.phone1}-${this.formData.phone2}-${this.formData.phone3}`,
                    managerEmail: this.formData.isEmailPrivate ? '비공개' : this.formData.email,
                    companyIntro: this.formData.companyIntro,
                    startDate: this.formData.startDate,
                    endDate: this.formData.endDate,
                    startTimeRegi: this.formData.startTimeRegi,
                    endTimeRegi: this.formData.endTimeRegi,
                    recruitmentType: this.formData.recruitmentType,
                    interviewCount: this.formData.interviewCount,
                    precautions: this.formData.precautions,
                };

                // 백엔드로 POST 요청 보내기
                const response = await axios.post(`${backend}/announce/create-step1`, postData, {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                });

                if (response.status === 200) {
                    alert('데이터가 성공적으로 저장되었습니다.');
                }
            } catch (error) {
                console.error('데이터 저장 실패:', error);
                alert('데이터 저장 중 오류가 발생했습니다.');
            }
        },

    },

});