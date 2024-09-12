
import AnnounceDetailPage from '@/pages/seeker/announce/AnnounceDetailPage.vue';
import AnnounceMainPage from '@/pages/recruiter/announce/AnnounceMainPage.vue';
import RecruiterLoginPage from '@/pages/recruiter/auth/RecruiterLoginPage.vue';
import ResumeMainPage from '@/pages/recruiter/resume/ResumeMainPage.vue';
import VideoInterviewEstimatorPage from '@/pages/recruiter/video-interview/VideoInterviewEstimatorPage.vue';
import VideoInterviewMainPage from '@/pages/recruiter/video-interview/VideoInterviewMainPage.vue';
import VideoInterviewParticipantPage from '@/pages/recruiter/video-interview/VideoInterviewParticipantPage.vue';
import { createRouter, createWebHistory } from 'vue-router';
import ResumeListPage from '@/pages/recruiter/resume/ResumeListPage.vue';
import ResumeDetailPage from '@/pages/recruiter/resume/ResumeDetailPage.vue';
import RecruiterSignupPage from '@/pages/recruiter/auth/RecruiterSignupPage.vue';
import AnnounceReadAllPage from '@/pages/seeker/announce/AnnounceReadAllPage.vue';
import SeekerLoginPage from '@/pages/seeker/auth/SeekerLoginPage.vue';
import MypageMainPage from '@/pages/seeker/mypage/MypageMainPage.vue';
import MypageAnnouceResumePage from '@/pages/seeker/mypage/MypageAnnouceResumePage.vue';
import MypageSchedulePage from '@/pages/seeker/mypage/MypageSchedulePage.vue';
import MypageIntegrationeResumePage from '@/pages/seeker/mypage/MypageIntegrationeResumePage.vue';
import MypageNotificationPage from '@/pages/seeker/mypage/MypageNotificationPage.vue';
import SeekerSignupPage from '@/pages/seeker/auth/SeekerSignupPage.vue';
import AnnounceRegisterStep2Page from '@/pages/recruiter/announce/AnnounceRegisterStep2Page.vue';
import AnnounceRegisterStep1Page from "@/pages/recruiter/announce/AnnounceRegisterStep1Page.vue";
import InterviewScheduleMainNew from "@/pages/recruiter/interview-schedule/InterviewScheduleMainNew.vue";
import InterviewScheduleMainExp from "@/pages/recruiter/interview-schedule/InterviewScheduleMainExp.vue";
import VideoInterViewTestPage from '@/pages/recruiter/video-interview/VideoInterViewTest1Page.vue';
import VideoInterViewRoomCreatePage from '@/pages/recruiter/video-interview/VideoInterViewTest2Page.vue';


const router = createRouter({
    history: createWebHistory(),
    routes : [
        { path: '/', component: AnnounceReadAllPage},
        { path: '/recruiter/login', component: RecruiterLoginPage},
        { path: '/recruiter/signup', component: RecruiterSignupPage},
        { path: '/recruiter/announce', component: AnnounceMainPage },
        { path: '/recruiter/announce/register-step2', component: AnnounceRegisterStep2Page},
        { path: '/recruiter/announce/register-step1', component: AnnounceRegisterStep1Page},
        { path: '/recruiter/video-interview', component: VideoInterviewMainPage },
        { path: '/recruiter/video-interview/participant', component: VideoInterviewParticipantPage },
        { path: '/recruiter/video-interview/estimator', component: VideoInterviewEstimatorPage },
        { path: '/recruiter/interview-schedule/new', component: InterviewScheduleMainNew },
        { path: '/recruiter/interview-schedule/exp', component: InterviewScheduleMainExp },
        { path: '/recruiter/video-interview/test1', component: VideoInterViewTestPage},
        { path: '/recruiter/video-interview/test2', component: VideoInterViewRoomCreatePage},
        { path: '/recruiter/resume', component: ResumeMainPage },
        { path: '/recruiter/resume/list', component: ResumeListPage },
        { path: '/recruiter/resume/detail', component: ResumeDetailPage },

        { path: '/seeker/login', component: SeekerLoginPage },
        { path: '/seeker/signup', component: SeekerSignupPage },
        { path: '/seeker/announce', component: AnnounceReadAllPage},
        { path: '/seeker/announce/detail', component: AnnounceDetailPage },
        { path: '/seeker/mypage', component: MypageMainPage },
        { path: '/seeker/mypage/schedule', component: MypageSchedulePage },
        { path: '/seeker/mypage/annouce-resume', component: MypageAnnouceResumePage },
        { path: '/seeker/mypage/integration-resume', component: MypageIntegrationeResumePage },
        { path: '/seeker/mypage/notification', component: MypageNotificationPage },
    ]
})


router.beforeEach((to, from, next) => {
    let link;

    // /recruiter/announce/register 페이지에만 CSS 추가
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
