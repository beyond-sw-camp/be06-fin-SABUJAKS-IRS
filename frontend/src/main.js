import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './App.css';
import { createPinia } from 'pinia'
import piniaPersistedState from 'pinia-plugin-persistedstate'
import Toast from "vue-toastification";
// Import the CSS or use your own!
import "vue-toastification/dist/index.css";
import axios from 'axios'
import { UseAuthStore } from '@/stores/UseAuthStore';
// import { useToast } from "vue-toastification";

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
            console.log("hhh");
            // const toast = useToast();
            const authStore = UseAuthStore();

            await authStore.logout();

            // toast.error("세션이 만료되어 로그인 페이지로 이동합니다.");
            
            console.log("세션이 유효하지 않습니다!!!!!!!!!!!!!!!sfjkwpeifjslkvnlxkmvowejropjefkjsdflkmdlkz.,dmvlskfjpoqejrfojdklsnvlscxmkdjfpowejfoad;kfmlsvnx,c.alsldkfpowejfwsvn");
            
            return Promise.resolve();
        }        
        return Promise.reject(error);
    }
);

// app.config.globalProperties.$axios = axios; // Axios 인스턴스를 전역 속성으로 등록
