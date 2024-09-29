import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';

export const UseCompanyStore = defineStore('company', {
    state: () => ({
        // 기업 정보
        companyInfo: {
            saved: '',
            companyIdx: '',

            ceoName: '',
            crn: '',
            openedAt: '',
            industry: '',
            name: '',
            type: '',
            companyInfo: '',
            capital: '',
            totalEmp: '',
            establishDate: '',
            sales: '',
            business: '',
            homeUrl: '',
            address: ''
        },

        companyBenefits: [], // 복리후생 카테고리 및 세부 항목 리스트 (조회 시 사용)

        files: [], // 이미지 리스트

    }),
    // persist: {},
    actions: {
        // companyInfo 초기화
        resetCompanyInfo() {
            this.companyInfo = {
                saved: '',
                companyIdx: '',
                ceoName: '',
                crn: '',
                openedAt: '',
                industry: '',
                name: '',
                type: '',
                companyInfo: '',
                capital: '',
                totalEmp: '',
                establishDate: '',
                sales: '',
                business: '',
                homeUrl: '',
                address: ''
            };
        },


        // 기업 정보 조회
        async readCompany() {
            try {
                const response = await axios.get(
                    `${backend}/company/read`,
                    {
                        headers: { 'Content-Type': 'application/json' },
                        withCredentials: true
                    }
                );
                const data = response.data;
                // console.log(data);
                this.companyInfo = data.result;
                this.companyBenefits = data.result.companyBenefitsList || [];

                // 등록된 기업이 없다면
                if (data.result.saved == "N") {
                    alert("등록된 기업 정보가 없습니다. 기업정보를 등록 해 주세요.");
                    this.companyInfo = {
                        saved: data.result.saved,
                        ceoName: data.result.ceoName,
                        crn: data.result.crn,
                        openedAt: data.result.openedAt
                    }

                } else {
                    this.companyInfo = data.result;
                }


            } catch (error) {
                console.error('기업 정보를 불러오지 못했습니다.', error);
                throw new Error(error.response?.data?.message || '기업 정보 조회 실패');
            }
        },

        // 기업 정보 저장 로직
        async createCompany(combinedCategories, router) {
            try {
                const formDataCreate = new FormData();

                const CompanyCreateReq = {
                    industry: this.companyInfo.industry,
                    name: this.companyInfo.name,
                    type: this.companyInfo.type,
                    companyInfo: this.companyInfo.companyInfo,
                    capital: this.companyInfo.capital,
                    totalEmp: this.companyInfo.totalEmp,
                    establishDate: this.companyInfo.establishDate,
                    sales: this.companyInfo.sales,
                    business: this.companyInfo.business,
                    url: this.companyInfo.homeUrl,
                    address: this.companyInfo.address,
                    companyBenefitsList: combinedCategories
                };

                console.log('저장할 데이터:', CompanyCreateReq);

                const jsonBlob = new Blob([JSON.stringify(CompanyCreateReq)], { type: 'application/json' });
                formDataCreate.append('dto', jsonBlob);

                // 각 사진 저장
                if (this.files.length > 0) {
                    this.files.forEach((file) => {
                        formDataCreate.append('files', file);
                    });
                }

                // 백엔드로 POST 요청 보내기
                const response = await axios.post(`${backend}/company/create`, formDataCreate, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        withCredentials: true
                    },
                });
                console.log("응답" + response.data);

                if (response.status === 200) {
                    alert('기업 정보가 등록되었습니다.');

                    // router를 통해 특정 경로로 리다이렉트
                    router.push('/recruiter/mypage');
                }
            } catch (error) {
                console.error('기업 정보 등록 실패:', error);
                alert('기업 정보 등록에 실패하였습니다. ' + error);
            }

        }


    }
})