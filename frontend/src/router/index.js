
import AnnounceDetailPage from '@/pages/seeker/announce/AnnounceDetailPage.vue';
import AnnounceMainPage from '@/pages/recruiter/announce/AnnounceMainPage.vue';
import RecruiterLoginPage from '@/pages/recruiter/auth/RecruiterLoginPage.vue';
import InterviewScheduleMainPage from '@/pages/recruiter/interview-schedule/InterviewScheduleMainPage.vue';
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

const router = createRouter({
    history: createWebHistory(),
    routes : [
        { path: '/', component: AnnounceReadAllPage},
        { path: '/recruiter/login', component: RecruiterLoginPage},
        { path: '/recruiter/signup', component: RecruiterSignupPage},
        { path: '/recruiter/announce', component: AnnounceMainPage },
        { path: '/recruiter/video-interview', component: VideoInterviewMainPage },
        { path: '/recruiter/video-interview/participant', component: VideoInterviewParticipantPage },
        { path: '/recruiter/video-interview/estimator', component: VideoInterviewEstimatorPage },
        { path: '/recruiter/interview-schedule', component: InterviewScheduleMainPage },
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

export default router;
