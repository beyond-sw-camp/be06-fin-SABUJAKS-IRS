import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './App.css';
import { createPinia } from 'pinia'
import piniaPersistedState from 'pinia-plugin-persistedstate'
import Toast from "vue-toastification";
// Import the CSS or use your own!
import "vue-toastification/dist/index.css";
// import axios from 'axios'
// import { UseAuthStore } from '@/stores/UseAuthStore'; // UseAuthStore 임포트 추가

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

// Axios 인터셉터 설정
// axios.interceptors.response.use(
//     response => {
//       return response;
//     },
//     error => {
//       if (error.response && error.response.status === 401) {
//         // 서버에서 '세션이 만료되었습니다.'라는 특정 메시지가 오면 로그아웃 처리
//         if (error.response.data.message === '세션이 만료되었습니다.') {
//           const authStore = UseAuthStore(); // UseAuthStore 인스턴스 생성
//           authStore.logout(); // 로그아웃 함수 호출
  
//           app.config.globalProperties.$toast.error("세션이 유효하지 않습니다. 로그인을 다시 해 주세요.");
//           router.push('/'); // 메인 페이지로 리다이렉트
//         }
//       }
//       return Promise.reject(error);
//     }
//   );

// app.config.globalProperties.$axios = axios; // Axios 인스턴스를 전역 속성으로 등록

app.mount('#app')
