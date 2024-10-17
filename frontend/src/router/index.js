import AnnounceMainPage from '@/pages/recruiter/announce/AnnounceMainPage.vue';
import AnnounceDetailRcrPage from '@/pages/recruiter/announce/AnnounceDetailPage.vue';
import RecruiterLoginPage from '@/pages/recruiter/auth/RecruiterLoginPage.vue';
import RecruiterSignupPage from '@/pages/recruiter/auth/RecruiterSignupPage.vue';
import RecruiterResumeDetailPage from '@/pages/recruiter/resume/ResumeDetailPage.vue';
import ResumeListPage from '@/pages/recruiter/resume/ResumeListPage.vue';
import ResumeMainPage from '@/pages/recruiter/resume/ResumeMainPage.vue';
import AnnounceDetailPage from '@/pages/seeker/announce/AnnounceDetailPage.vue';
import AnnounceReadAllPage from '@/pages/seeker/announce/AnnounceReadAllPage.vue';
import SeekerLoginPage from '@/pages/seeker/auth/SeekerLoginPage.vue';
import MypageAnnouceResumePage from '@/pages/seeker/mypage/MypageAnnouceResumePage.vue';
import MypageIntegrationeResumePage from '@/pages/seeker/mypage/MypageIntegrationeResumePage.vue';
import MypageMainPage from '@/pages/seeker/mypage/MypageMainPage.vue';
import MypageNotificationPage from '@/pages/seeker/mypage/MypageNotificationPage.vue';
import MypageSchedulePage from '@/pages/seeker/mypage/MypageSchedulePage.vue';
import ResumeCreatePage from '@/pages/seeker/resume/ResumeCreatePage.vue';
import ResumeSubmitPage from '@/pages/seeker/resume/ResumeSubmitPage.vue'
import SeekerResumeDetailPage from '@/pages/seeker/resume/ResumeDetailPage.vue'
import VideoInterviewMainPage from '@/pages/video-interview/VideoInterviewMainPage.vue';
import VideoInterviewRoomPage from '@/pages/video-interview/VideoInterviewRoomPage.vue';
import { createRouter, createWebHistory } from 'vue-router';
import SeekerSignupPage from '@/pages/seeker/auth/SeekerSignupPage.vue';
import AnnounceRegisterStep2Page from '@/pages/recruiter/announce/AnnounceRegisterStep2Page.vue';
import AnnounceRegisterStep1Page from "@/pages/recruiter/announce/AnnounceRegisterStep1Page.vue";
import InterviewScheduleMainNewPage from "@/pages/recruiter/interview-schedule/InterviewScheduleMainNewPage.vue";
import InterviewScheduleMainExpPage from "@/pages/recruiter/interview-schedule/InterviewScheduleMainExpPage.vue";
import InterviewScheduleMain from '@/components/recruiter/InterviewScheduleMain.vue';
import ReScheduleMainPage from "@/pages/recruiter/interview-schedule/ReScheduleMainPage.vue";
import { UseAuthStore } from "@/stores/UseAuthStore";
import CompanyInfoPage from "@/pages/recruiter/company/CompanyInfoPage.vue";
import InterviewEvaluateResultPage from '@/pages/recruiter/interview-evaluate/InterviewEvaluateResultPage.vue';
import InterveiwEvaluateResultDetailPage from '@/pages/recruiter/interview-evaluate/InterveiwEvaluateResultDetailPage.vue';
import InterviewScheduleDetail from "@/pages/recruiter/interview-schedule/InterviewScheduleDetail.vue";
import InterviewScheduleListPage from "@/pages/recruiter/interview-schedule/InterviewScheduleListPage.vue";
import InterviewEvaluateFormCreatePage from '@/pages/recruiter/interview-evaluate/InterviewEvaluateFormCreatePage.vue';
import ReScheduleListPage from "@/pages/recruiter/interview-schedule/ReScheduleListPage.vue";
import ReScheduleDetailPage from "@/pages/recruiter/interview-schedule/ReScheduleDetailPage.vue";
import TotalProcessMainPage from "@/pages/recruiter/total-process/TotalProcessMainPage.vue";
import TotalProcessListPage from "@/pages/recruiter/total-process/TotalProcessListPage.vue";
import { useToast } from "vue-toastification";

const requireRecruiterLogin = async (to, from, next) => {
    const authStore = UseAuthStore();
    const toast = useToast();

    try {
        await authStore.getUserInfo();
        const storedUserInfo = JSON.parse(sessionStorage.getItem('auth'));
        if (storedUserInfo && storedUserInfo.userInfo.role === "ROLE_RECRUITER") {
            return next();
        } else {
            toast.error("세션이 만료되어 로그인 페이지로 이동합니다.");
            return next("/recruiter/login");
        }
    } catch (error) {
        console.error("Authentication check failed:", error);
        toast.error("세션이 만료되어 로그인 페이지로 이동합니다.");
        next("/recruiter/login");
    }
}

const alreadyLogin = async (to, from, next) => {
    const authStore = UseAuthStore();
    if (!authStore.isLoggedIn) {
        return next();
    } else {
        return next("/");
    }
}

const requireSeekerLogin = async (to, from, next) => {
    console.log("인덱스!!!");
    const authStore = UseAuthStore();
    const toast = useToast();

    // const response = await authStore.getUserInfo();
    // if(response && response.status ) {
    //     // const storedUserInfo = JSON.parse(sessionStorage.getItem('auth'));
    //     if (authStore.userInfo && authStore.userInfo.role === "ROLE_SEEKER") {
    //         return next();
    //     } else {
    //         toast.error("세션이 만료되어 로그인 페이지로 이동합니다.");
    //         return next("/seeker/login");
    //     }
    // } else {

    // }

    try {
        await authStore.getUserInfo();
        const storedUserInfo = JSON.parse(sessionStorage.getItem('auth'));
        if (storedUserInfo && storedUserInfo.userInfo.role === "ROLE_SEEKER") {
            return next();
        } else {
            console.log("****************************************************************");
            toast.error("세션이 만료되어 로그인 페이지로 이동합니다.");
            return next("/seeker/login");
        }
    } catch (error) {
        // console.error("Authentication check failed:", error);
        console.log("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        toast.error("세션이 만료되어 로그인 페이지로 이동합니다.");
        next("/seeker/login");
    }
}

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: AnnounceReadAllPage },

        { path: '/recruiter/login', component: RecruiterLoginPage, beforeEnter: alreadyLogin },
        { path: '/recruiter/signup', component: RecruiterSignupPage, beforeEnter: alreadyLogin },

        { path: '/recruiter/announce', component: AnnounceMainPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/announce/detail/:announcementIdx', component: AnnounceDetailRcrPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/announce/register-step1', component: AnnounceRegisterStep1Page, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/announce/register-step2/:announcementIdx/:title', name: 'AnnouncementCreateStep2', component: AnnounceRegisterStep2Page, beforeEnter: requireRecruiterLogin },

        { path: '/recruiter/interview-schedule', component: InterviewScheduleMain, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-schedule/reschedule', component: ReScheduleMainPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-schedule/reschedule/list', component: ReScheduleListPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-schedule/reschedule/detail/:idx', component: ReScheduleDetailPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-schedule/new', component: InterviewScheduleMainNewPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-schedule/exp', component: InterviewScheduleMainExpPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-schedule/list', component: InterviewScheduleListPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-schedule/detail/:idx', component: InterviewScheduleDetail, beforeEnter: requireRecruiterLogin},

        { path: '/recruiter/resume', component: ResumeMainPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/resume/list/:announcementIdx', component: ResumeListPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/resume/detail/:resumeIdx', component: RecruiterResumeDetailPage, beforeEnter: requireRecruiterLogin },

        { path: '/recruiter/total-process', component: TotalProcessMainPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/total-process/list/:announcementIdx', component: TotalProcessListPage, beforeEnter: requireRecruiterLogin },

        { path: '/recruiter/interview-evaluate/form', component: InterviewEvaluateFormCreatePage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-evaluate/result', component: InterviewEvaluateResultPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-evaluate/result/:announcementIdx/:interviewNum', component: InterveiwEvaluateResultDetailPage, beforeEnter: requireRecruiterLogin },

        { path: '/recruiter/mypage', component: CompanyInfoPage, beforeEnter: requireRecruiterLogin },

        { path: '/seeker/login', component: SeekerLoginPage, beforeEnter: alreadyLogin },
        { path: '/seeker/signup', component: SeekerSignupPage, beforeEnter: alreadyLogin },

        { path: '/seeker/announce', component: AnnounceReadAllPage },
        { path: '/seeker/announce/detail/:announcementIdx', component: AnnounceDetailPage },

        { path: '/seeker/mypage', component: MypageMainPage },
        { path: '/seeker/mypage/schedule', component: MypageSchedulePage, beforeEnter: requireSeekerLogin },
        { path: '/seeker/mypage/annouce-resume', component: MypageAnnouceResumePage, beforeEnter: requireSeekerLogin },
        { path: '/seeker/mypage/integration-resume', component: MypageIntegrationeResumePage, beforeEnter: requireSeekerLogin },
        { path: '/seeker/mypage/notification', component: MypageNotificationPage, beforeEnter: requireSeekerLogin },

        { path: '/seeker/resume/create', component: ResumeCreatePage, beforeEnter: requireSeekerLogin },
        { path: '/seeker/resume/submit/:announcementIdx', component: ResumeSubmitPage, beforeEnter: requireSeekerLogin },
        { path: '/seeker/resume/detail/:resumeIdx', component: SeekerResumeDetailPage, beforeEnter: requireSeekerLogin },

        { path: '/video-interview/:announcementUUID', component: VideoInterviewMainPage, },
        { path: '/video-interview/:announcementUUID/:videoInterviewUUID', component: VideoInterviewRoomPage },
    ]
})

export default router;