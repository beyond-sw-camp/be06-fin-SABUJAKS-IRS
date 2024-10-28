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
// import { useRouter } from 'vue-router';

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


// Axios 인터셉터 설정
axios.interceptors.response.use(
    response => {
        return response;
    },
    async error => {
        if (error.response) {
            const status = error.response.status;
            const authStore = UseAuthStore();
            switch (status) {
                case 401:
                    await authStore.logout();
                    console.log("세션이 유효하지 않습니다.");
                    console.log(error.response);
                    // router.push("/");
                    break;

                case 404:
                    console.error("페이지를 찾을 수 없습니다. 404 에러");
                    router.push("/error"); // 404일 때 이동할 페이지
                    break;

                case 500:
                    console.error("서버 내부 오류가 발생했습니다. 500 에러");
                    router.push("/error"); // 500일 때 이동할 페이지
                    break;

                default:
                    console.error(`Unhandled error status: ${status}`);
            }
        }
        
        return Promise.reject(error);
    }
);


// // Axios 인터셉터 설정
// axios.interceptors.response.use(
//     response => {
//         return response;
//     },
//     async error => {
//         if (error.response && error.response.status === 401) {
            
//             // const router = useRouter();
//             // const toast = useToast();
//             const authStore = UseAuthStore();
//             await authStore.logout();

//             // toast.error("세션이 만료되어 로그인 페이지로 이동합니다.");
            
//             console.log("세션이 유효하지 않습니다.");
//             console.log(error.response);

//             // router.push("/");
            
//             return Promise.resolve();
//         }        
//         return Promise.reject(error);
//     }
// );

