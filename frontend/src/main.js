import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './App.css';
import { createPinia } from 'pinia'
import piniaPersistedState from 'pinia-plugin-persistedstate'
import { Toast, useToast } from "vue-toastification";
// Import the CSS or use your own!
import "vue-toastification/dist/index.css";
import axios from 'axios'
import { UseAuthStore } from '@/stores/UseAuthStore'; // UseAuthStore 임포트 추가

const app = createApp(App)
const pinia = createPinia();

pinia.use(piniaPersistedState);
app.use(router)
app.use(Toast,
    {
        timeout: 2000,
        enter: "fade-enter-active",
        leave: "Vue-Toastification__bounce-leave-active",
        move: "fade-move",
        position: "bottom-right"
    })
app.use(pinia);

app.mount('#app')

// const toast = app.config.globalProperties.$toast; // Toast 인스턴스를 변수로 가져오기

// Axios 인터셉터 설정
axios.interceptors.response.use(
    response => {
        return response;
    },
    async error => {
        if (error.response && error.response.status === 401) {
            // 서버에서 '세션이 만료되었습니다.'라는 특정 메시지가 오면 로그아웃 처리

            const authStore = UseAuthStore(); // UseAuthStore 인스턴스 생성
            await authStore.logout(); // 로그아웃 함수 호출

            const toast = useToast();

            toast.error("세션이 유효하지 않습니다. 로그인을 다시 해 주세요.");
            router.push('/'); // 메인 페이지로 리다이렉트

            // 401 오류에 대해 Promise.reject가 아닌 빈 값을 반환하여 catch로 넘어가지 않게 처리
            return;

        }
    }
);

app.config.globalProperties.$axios = axios; // Axios 인스턴스를 전역 속성으로 등록
