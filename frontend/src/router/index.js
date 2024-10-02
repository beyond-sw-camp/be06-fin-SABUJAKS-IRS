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
import RecruiterSignupPage1 from '@/pages/recruiter/auth/RecruiterSignupPage1.vue';
import SeekerResumeDetailPage from '@/pages/seeker/resume/ResumeDetailPage.vue'
import VideoInterviewMainPage from '@/pages/video-interview/VideoInterviewMainPage.vue';
import VideoInterviewRoomPage from '@/pages/video-interview/VideoInterviewRoomPage.vue';
import { createRouter, createWebHistory } from 'vue-router';
import SeekerSignupPage from '@/pages/seeker/auth/SeekerSignupPage.vue';
import AnnounceRegisterStep2Page from '@/pages/recruiter/announce/AnnounceRegisterStep2Page.vue';
import AnnounceRegisterStep1Page from "@/pages/recruiter/announce/AnnounceRegisterStep1Page.vue";
import InterviewScheduleMainNew from "@/pages/recruiter/interview-schedule/InterviewScheduleMainNew.vue";
import InterviewScheduleMainExp from "@/pages/recruiter/interview-schedule/InterviewScheduleMainExp.vue";
import InterviewScheduleMain from '@/components/recruiter/InterviewScheduleMain.vue';
import ReScheduleMainExp from "@/pages/recruiter/interview-schedule/ReScheduleMainExp.vue";
import { UseAuthStore } from "@/stores/UseAuthStore";
import CompanyInfoPage from "@/pages/recruiter/company/CompanyInfoPage.vue";
import InterviewEvaluateFormPage from '@/pages/recruiter/interview-evaluate/InterviewEvaluateFormPage.vue';
import InterviewEvaluateResultPage from '@/pages/recruiter/interview-evaluate/InterviewEvaluateResultPage.vue';
import InterveiwEvaluateResultDetailPage from '@/pages/recruiter/interview-evaluate/InterveiwEvaluateResultDetailPage.vue';
import InterviewScheduleDetail from "@/pages/recruiter/interview-schedule/InterviewScheduleDetail.vue";

const requireRecruiterLogin = async (to, from, next) => {
    const authStore = UseAuthStore();
    try {
        await authStore.getUserInfo();
        const storedUserInfo = JSON.parse(sessionStorage.getItem('auth'));
        if (storedUserInfo && storedUserInfo.userInfo.role === "ROLE_RECRUITER") {
            return next();
        } else {
            if (confirm("로그인이 필요합니다.")) {
                return next("/recruiter/login");
            }
        }
    } catch (error) {
        console.error("Authentication check failed:", error);
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

const router = createRouter({
    history: createWebHistory(),
    routes: [
        { path: '/', component: AnnounceReadAllPage },
        
        { path: '/recruiter/login', component: RecruiterLoginPage, beforeEnter: alreadyLogin },
        { path: '/recruiter/signup', component: RecruiterSignupPage, beforeEnter: alreadyLogin },
        { path: '/recruiter/signup1', component: RecruiterSignupPage1, beforeEnter: alreadyLogin },
        
        { path: '/recruiter/announce', component: AnnounceMainPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/announce/detail/:announcementIdx', component: AnnounceDetailRcrPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/announce/register-step1', component: AnnounceRegisterStep1Page, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/announce/register-step2/:announcementIdx/:title', name: 'AnnouncementCreateStep2', component: AnnounceRegisterStep2Page, beforeEnter: requireRecruiterLogin },
        
        { path: '/recruiter/interview-schedule', component: InterviewScheduleMain, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-schedule/reschedule', component: ReScheduleMainExp, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-schedule/new', component: InterviewScheduleMainNew, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-schedule/exp', component: InterviewScheduleMainExp, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-schedule/detail/:idx', component: InterviewScheduleDetail, beforeEnter: requireRecruiterLogin},
        
        { path: '/recruiter/resume', component: ResumeMainPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/resume/list/:announcementIdx', component: ResumeListPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/resume/detail/:resumeIdx', component: RecruiterResumeDetailPage, beforeEnter: requireRecruiterLogin },

        { path: '/recruiter/interview-evaluate/form', component: InterviewEvaluateFormPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-evaluate/result', component: InterviewEvaluateResultPage, beforeEnter: requireRecruiterLogin },
        { path: '/recruiter/interview-evaluate/result/:announcementIdx/:interviewNum', component: InterveiwEvaluateResultDetailPage, beforeEnter: requireRecruiterLogin },

        { path: '/recruiter/mypage', component: CompanyInfoPage, beforeEnter: requireRecruiterLogin },
        
        { path: '/seeker/login', component: SeekerLoginPage, beforeEnter: alreadyLogin },
        { path: '/seeker/signup', component: SeekerSignupPage, beforeEnter: alreadyLogin },
        
        { path: '/seeker/announce', component: AnnounceReadAllPage },
        { path: '/seeker/announce/detail/:announcementIdx', component: AnnounceDetailPage },
        
        { path: '/seeker/mypage', component: MypageMainPage },
        { path: '/seeker/mypage/schedule', component: MypageSchedulePage },
        { path: '/seeker/mypage/annouce-resume', component: MypageAnnouceResumePage },
        { path: '/seeker/mypage/integration-resume', component: MypageIntegrationeResumePage },
        { path: '/seeker/mypage/notification', component: MypageNotificationPage },

        { path: '/seeker/resume/create', component: ResumeCreatePage },
        { path: '/seeker/resume/submit/:announcementIdx', component: ResumeSubmitPage },
        { path: '/seeker/resume/detail/:resumeIdx', component: SeekerResumeDetailPage },

        { path: '/video-interview/:announceUUID', component: VideoInterviewMainPage, },
        { path: '/video-interview/:announceUUID/:videoInterviewUUID', component: VideoInterviewRoomPage },
    ]
})


router.beforeEach((to, from, next) => {
    let link;

    if (to.path === '/recruiter/announce/register-step1') {
        if (!document.querySelector('link[href="/css/AnnounceRegisterStep1Page.css"]')) {
            link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = '/css/AnnounceRegisterStep1Page.css'; // 절대 경로로 변경
            document.head.appendChild(link);
        }
    } else {
        // 다른 페이지로 이동하면 해당 CSS 파일 제거
        const existingLink = document.querySelector('link[href="/css/AnnounceRegisterStep1Page.css"]');
        if (existingLink) {
            document.head.removeChild(existingLink);
        }
    }

    // /recruiter/announce/register-step2 페이지에만 CSS 추가
    if (to.path === '/recruiter/announce/register-step2') {
        if (!document.querySelector('link[href="/css/AnnounceRegisterStep2Page.css"]')) {
            link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = '/css/step2.css'; // 절대 경로로 변경
            document.head.appendChild(link);
        }
    } else {
        // 다른 페이지로 이동하면 해당 CSS 파일 제거
        const existingLink = document.querySelector('link[href="/css/AnnounceRegisterStep2Page.css"]');
        if (existingLink) {
            document.head.removeChild(existingLink);
        }
    }

    // /recruiter/login 페이지에만 CSS 추가
    if (to.path === '/recruiter/login') {
        if (!document.querySelector('link[href="/css/RecruiterLoginPage.css"]')) {
            link = document.createElement('link');
            link.rel = 'stylesheet';
            link.href = '/css/RecruiterLoginPage.css'; // 절대 경로로 변경
            document.head.appendChild(link);
        }
    } else {
        // 다른 페이지로 이동하면 해당 CSS 파일 제거
        const existingLoginLink = document.querySelector('link[href="/css/RecruiterLoginPage.css"]');
        if (existingLoginLink) {
            document.head.removeChild(existingLoginLink);
        }
    }

    next();
});

export default router;
