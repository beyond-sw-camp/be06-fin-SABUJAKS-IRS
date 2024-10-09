import { defineStore } from 'pinia';
import axios from 'axios';
import { backend } from '@/const';
import { useToast } from 'vue-toastification';


export const UseResumeStore = defineStore('resume', {
    state: () => ({
        file: null,
        portfolioFiles: [],
        codes: [],
        personalInfo: {},
        preferentialEmp: {},
        educations: [],
        personalHistories: [],
        internsActivities: [],
        studyingAbroads: [],
        languages: [],
        certifications: [],
        trainings: [],
        awards: [],
        customLetters: [],
        portfolios: [],

        // show* 상태 추가
        showPersonalInfo: true,
        showEducation: false,
        showPersonalHistory: false,
        showInternsActivity: false,
        showStudyingAbroad: false,
        showLanguage: false,
        showCertification: false,
        showTraining: false,
        showAward: false,
        showCustomLetter: false,
        showPortfolio: false,
        showPreferentialEmp: false,

        resumeTitle: null,
        announcementIdx: 0,

        resumeDetail: {},
        resumeIntegrated: null,
        announceResumeList: [],
        announceResumeListPage: {},
        resumeList: [],
        resumeListPage: {}
    }),
    actions: {
        updateShowEducation(value) {
            this.showEducation = value;
        },
        updateShowPersonalHistory(value) {
            this.showPersonalHistory = value;
        },
        updateShowInternsActivity(value) {
            this.showInternsActivity = value;
        },
        updateShowStudyingAbroad(value) {
            this.showStudyingAbroad = value;
        },
        updateShowLanguage(value) {
            this.showLanguage = value;
        },
        updateShowCertification(value) {
            this.showCertification = value;
        },
        updateShowTraining(value) {
            this.showTraining = value;
        },
        updateShowAward(value) {
            this.showAward = value;
        },
        updateShowCustomLetter(value) {
            this.showCustomLetter = value;
        },
        updateShowPortfolio(value) {
            this.showPortfolio = value;
        },
        updateShowPreferentialEmp(value) {
            this.showPreferentialEmp = value;
        },
        updatePersonalInfo(data) { // 인적사항
            this.personalInfo = data;
        },
        updatePreferentialEmp(data) { // 취업우대·병역
            this.preferentialEmp = data;
        },
        // 공통 메서드
        addItem(type, data) {
            this[type].push(data);
        },
        updateItem(type, index, data) {
            if (index < this[type].length) {
                this[type][index] = data;
            } else {
                this[type].push(data);
            }
        },
        removeItem(type, index) {
            if (index >= 0 && index < this[type].length) {
                this[type].splice(index, 1);
            }
        },
        updatePortfolioFile(index, file) {
            this.portfolioFiles[index] = file;
        },
        removePortfolioFile(index) {
            if (index >= 0 && index < this.portfolioFiles.length) {
                this.portfolioFiles.splice(index, 1);
            }
        },
        updateFile(file) {
            this.file = file;
        },
        async createResume(router) {
            const toast = useToast();
            try {
                const formData = new FormData();
                // 학력
                if (this.educations.length > 0 && !this.codes.includes("resume_001") && this.showEducation) {
                    this.codes.push("resume_001");
                }
                if (!this.showEducation) {
                    const index = this.codes.indexOf("resume_001");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 경력
                if (this.personalHistories.length > 0 && !this.codes.includes("resume_002") && this.showPersonalHistory) {
                    this.codes.push("resume_002");
                }
                if (!this.showPersonalHistory) {
                    const index = this.codes.indexOf("resume_002");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 인턴 대외활동
                if (this.internsActivities.length > 0 && !this.codes.includes("resume_003") && this.showInternsActivity) {
                    this.codes.push("resume_003");
                }
                if (!this.showInternsActivity) {
                    const index = this.codes.indexOf("resume_003");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 교육이수
                if (this.trainings.length > 0 && !this.codes.includes("resume_004") && this.showTraining) {
                    this.codes.push("resume_004");
                }
                if (!this.showTraining) {
                    const index = this.codes.indexOf("resume_004");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 자격증
                if (this.certifications.length > 0 && !this.codes.includes("resume_005") && this.showCertification) {
                    this.codes.push("resume_005");
                }
                if (!this.showCertification) {
                    const index = this.codes.indexOf("resume_005");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 수상
                if (this.awards.length > 0 && !this.codes.includes("resume_006") && this.showAward) {
                    this.codes.push("resume_006");
                }
                if (!this.showAward) {
                    const index = this.codes.indexOf("resume_006");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 해외경험
                if (this.studyingAbroads.length > 0 && !this.codes.includes("resume_007") && this.showStudyingAbroad) {
                    this.codes.push("resume_007");
                }
                if (!this.showStudyingAbroad) {
                    const index = this.codes.indexOf("resume_007");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 어학
                if (this.languages.length > 0 && !this.codes.includes("resume_008") && this.showLanguage) {
                    this.codes.push("resume_008");
                }
                if (!this.showLanguage) {
                    const index = this.codes.indexOf("resume_008");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 포트폴리오
                if (this.portfolios.length > 0 && !this.codes.includes("resume_009") && this.showPortfolio) {
                    this.codes.push("resume_009");
                }
                if (!this.showPortfolio) {
                    const index = this.codes.indexOf("resume_009");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 취업우대 병역
                if (Object.keys(this.preferentialEmp).length > 0 && !this.codes.includes("resume_010") && this.showPreferentialEmp) {
                    this.codes.push("resume_010");
                }
                if (!this.showPreferentialEmp) {
                    const index = this.codes.indexOf("resume_010");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 자기소개서
                if (this.customLetters.length > 0 && !this.codes.includes("resume_011") && this.showCustomLetter) {
                    this.codes.push("resume_011");
                }
                if (!this.showCustomLetter) {
                    const index = this.codes.indexOf("resume_011");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }

                // ResumeCreateReq 객체를 생성
                const resumeCreateReq = {
                    // 필수
                    personalInfo: this.personalInfo,
                    // 선택
                    codes: this.codes,
                    preferentialEmp: this.preferentialEmp,
                    educations: this.educations,
                    personalHistories: this.personalHistories,
                    internsActivities: this.internsActivities,
                    studyingAbroads: this.studyingAbroads,
                    languages: this.languages,
                    certifications: this.certifications,
                    trainings: this.trainings,
                    awards: this.awards,
                    customLetters: this.customLetters,
                    portfolios: this.portfolios
                };

                // console.log(resumeCreateReq);

                const jsonBlob = new Blob([JSON.stringify(resumeCreateReq)], { type: 'application/json' });
                formData.append('dto', jsonBlob);

                // 증명사진 (필수)
                if (this.file) {
                    formData.append('file', this.file);
                }

                if (this.codes.includes("resume_009")) {
                    Array.from(this.portfolioFiles).forEach((file) => {
                        formData.append("portfolioFiles", file);
                    });
                }

                const response = await axios.post(`${backend}/resume/create`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        withCredentials: true
                    },
                });
                // 지원서 등록 성공
                // console.log('서버 응답:', response.data);
                toast.success(response.data.message);
                router.push('/seeker/mypage/integration-resume');
            } catch (error) {
                toast.error(error.response.data.message);
                if (error.response.data.code === 2003) {
                    router.push('/seeker/mypage/integration-resume');
                }
            }
        },
        async submitResume(router, announcementIdx, resumeTitle) {
            const toast = useToast();
            try {
                const formData = new FormData();
                // 학력
                if (this.educations.length > 0 && !this.codes.includes("resume_001") && this.showEducation) {
                    this.codes.push("resume_001");
                }
                if (!this.showEducation) {
                    const index = this.codes.indexOf("resume_001");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 경력
                if (this.personalHistories.length > 0 && !this.codes.includes("resume_002") && this.showPersonalHistory) {
                    this.codes.push("resume_002");
                }
                if (!this.showPersonalHistory) {
                    const index = this.codes.indexOf("resume_002");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 인턴 대외활동
                if (this.internsActivities.length > 0 && !this.codes.includes("resume_003") && this.showInternsActivity) {
                    this.codes.push("resume_003");
                }
                if (!this.showInternsActivity) {
                    const index = this.codes.indexOf("resume_003");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 교육이수
                if (this.trainings.length > 0 && !this.codes.includes("resume_004") && this.showTraining) {
                    this.codes.push("resume_004");
                }
                if (!this.showTraining) {
                    const index = this.codes.indexOf("resume_004");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 자격증
                if (this.certifications.length > 0 && !this.codes.includes("resume_005") && this.showCertification) {
                    this.codes.push("resume_005");
                }
                if (!this.showCertification) {
                    const index = this.codes.indexOf("resume_005");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 수상
                if (this.awards.length > 0 && !this.codes.includes("resume_006") && this.showAward) {
                    this.codes.push("resume_006");
                }
                if (!this.showAward) {
                    const index = this.codes.indexOf("resume_006");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 해외경험
                if (this.studyingAbroads.length > 0 && !this.codes.includes("resume_007") && this.showStudyingAbroad) {
                    this.codes.push("resume_007");
                }
                if (!this.showStudyingAbroad) {
                    const index = this.codes.indexOf("resume_007");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 어학
                if (this.languages.length > 0 && !this.codes.includes("resume_008") && this.showLanguage) {
                    this.codes.push("resume_008");
                }
                if (!this.showLanguage) {
                    const index = this.codes.indexOf("resume_008");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 포트폴리오
                if (this.portfolios.length > 0 && !this.codes.includes("resume_009") && this.showPortfolio) {
                    this.codes.push("resume_009");
                }
                if (!this.showPortfolio) {
                    const index = this.codes.indexOf("resume_009");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 취업우대 병역
                if (Object.keys(this.preferentialEmp).length > 0 && !this.codes.includes("resume_010") && this.showPreferentialEmp) {
                    this.codes.push("resume_010");
                }
                if (!this.showPreferentialEmp) {
                    const index = this.codes.indexOf("resume_010");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }
                // 자기소개서
                if (this.customLetters.length > 0 && !this.codes.includes("resume_011") && this.showCustomLetter) {
                    this.codes.push("resume_011");
                }
                if (!this.showCustomLetter) {
                    const index = this.codes.indexOf("resume_011");
                    if (index > -1) {
                        this.codes.splice(index, 1);
                    }
                }

                this.announcementIdx = announcementIdx;
                this.resumeTitle = resumeTitle;

                // resumeSubmitReq 객체를 생성
                const resumeSubmitReq = {
                    // 필수
                    announcementIdx: this.announcementIdx,
                    resumeTitle: this.resumeTitle,
                    personalInfo: this.personalInfo,
                    // 선택
                    codes: this.codes,
                    preferentialEmp: this.preferentialEmp,
                    educations: this.educations,
                    personalHistories: this.personalHistories,
                    internsActivities: this.internsActivities,
                    studyingAbroads: this.studyingAbroads,
                    languages: this.languages,
                    certifications: this.certifications,
                    trainings: this.trainings,
                    awards: this.awards,
                    customLetters: this.customLetters,
                    portfolios: this.portfolios
                };
                // console.log(resumeSubmitReq);

                const jsonBlob = new Blob([JSON.stringify(resumeSubmitReq)], { type: 'application/json' });
                formData.append('dto', jsonBlob);

                // 증명사진 (필수)
                if (this.file) {
                    formData.append('file', this.file);
                }

                if (this.codes.includes("resume_009")) {
                    Array.from(this.portfolioFiles).forEach((file) => {
                        formData.append("portfolioFiles", file);
                    });
                }

                const response = await axios.post(`${backend}/resume/submit`, formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                        withCredentials: true
                    },
                });
                // 지원서 등록 성공
                // console.log('서버 응답:', response.data);
                const toast = useToast();
                toast.success(response.data.message);
                if (response.data.code === 2000) {
                    router.push(`/seeker/resume/detail/${response.data.result.resumeIdx}`);
                }
            } catch (error) {
                toast.error(error.response.data.message);
                if (error.response.data.code === 2007) {
                    router.push('/seeker/mypage/annouce-resume');
                }
            }
        },
        async readSubmitInfo(announcementIdx) {
            const toast = useToast();
            try {
                const response = await axios.get(`${backend}/resume/read/submit-info?announcementIdx=${announcementIdx}`, {
                    headers: { 'Content-Type': 'application/json', },
                    withCredentials: true
                });
                return response.data;
            } catch (error) {
                toast.error(error.response.data.message);
            }
        },
        async readIntegrated(router) {
            const toast = useToast();
            try {
                const response = await axios.get(`${backend}/resume/read/integrated`, {
                    headers: { 'Content-Type': 'application/json', },
                    withCredentials: true
                });
                this.resumeIntegrated = response.data.result;
            } catch (error) {
                // console.log(error.response.data);
                toast.error(error.response.data.message);
                if (error.response.data.code === 2102) {
                    router.push('/seeker/resume/create');
                }
            }
        },
        async read(resumeIdx) {
            const toast = useToast();
            try {
                const response = await axios.get(`${backend}/resume/read?resumeIdx=${resumeIdx}`, {
                    headers: { 'Content-Type': 'application/json', },
                    withCredentials: true
                });
                this.resumeDetail = response.data.result;

            } catch (error) {
                toast.error(error.response.data.message);
            }
        },
        async readAll(router, page, size) {
            const toast = useToast();
            try {
                const response = await axios.get(`${backend}/resume/read-all?page=${page}&size=${size}`, {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                });
                this.announceResumeList = response.data.result.content;
                this.announceResumeListPage = response.data.result;
                // console.log(this.announceResumeListPage);
            } catch (error) {
                // console.log(error.response.data);
                toast.error('공고별 지원서 조회에 실패하였습니다.');
                router.push('/seeker/mypage');
            }
        },
        async updateDocPassed(router, docPassedValue) {
            const toast = useToast();
            try {
                const response = await axios.post(`${backend}/total-process/create`, {
                    isPass: docPassedValue,
                    announcementIdx: this.resumeDetail.announcementIdx,
                    seekerIdx: this.resumeDetail.seekerIdx,
                    interviewNum: 0
                },
                    {
                        headers: { 'Content-Type': 'application/json' },
                        withCredentials: true
                    }
                );
                // 합격/불합격 처리 성공
                if(response.data.success) {
                    toast.success('서류 합격/불합격 처리에 성공하였습니다.');
                    router.push(`/recruiter/resume/list/${this.resumeDetail.announcementIdx}`);
                }
            } catch (error) {
                toast.error(error.response.data.message);
            }
        },
        async readAllRecruiter(router, announcementIdx, page, size) {
            const toast = useToast();
            try {
                const response = await axios.get(`${backend}/resume/recruiter/read-all?announcementIdx=${announcementIdx}&page=${page}&size=${size}`, {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                });
                this.resumeList = response.data.result.content;
                this.resumeListPage = response.data.result;
                console.log(this.resumeListPage);

            } catch (error) {
                toast.error(error.response.data.message);
                router.push('/recruiter/resume');
            }
        },

        async sendResult(resumeList) {
            const toast = useToast();
            try {
                const response = await axios.post(`${backend}/resume/recruiter/send-result`,
                    resumeList,
                    {
                        headers: { 'Content-Type': 'application/json' },
                        withCredentials: true
                    });

                console.log("resume result send email response : ", response);

                return response.data.result;

            } catch (error) {
                toast.error(error.response.data.message);
            }
        }
    },
});
