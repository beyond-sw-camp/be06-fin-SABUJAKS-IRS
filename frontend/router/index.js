import { createRouter, createWebHistory } from 'vue-router'

// import App from '@/App.vue'
import Test from '@/components/Test.vue'

const router = createRouter({
    history: createWebHistory(),
    routes : [
        { path: '/test', component: Test },
    ]
})

export default router;
