import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import './App.css';
import { createPinia } from 'pinia'
import piniaPersistedState from 'pinia-plugin-persistedstate'

const app = createApp(App)
const pinia = createPinia();

pinia.use(piniaPersistedState);
app.use(router)
app.use(pinia);

app.mount('#app')
