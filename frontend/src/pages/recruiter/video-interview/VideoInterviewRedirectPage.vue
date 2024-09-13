<template >
    <form @submit.prevent="handleLogin">
      <label for="email">email </label>
      <input type="text" v-model="email" placeholder="Enter email" />
      <label for="password">password </label>
      <input type="text" v-model="password" placeholder="Enter videoInterviewRoomUUID" />

      <!-- 제출 버튼 -->
      <button type="submit">Submit</button>
    </form>
    <hr>
    <button @click="handleAuthorities">권한불러오기</button><br>
    <p>{{ authorities }}</p>
    <hr>
    <button @click="handleLogout">로그아웃</button><br>
</template>
<script setup>
import { ref } from 'vue';
import { UseAuthStore } from '@/stores/UseAuthStore'; 
const authStore = UseAuthStore();
const email = ref('');
const password = ref('');
const authorities = ref([]);

const handleLogin = async() => {
    try {
    const loginReq = {
        email: email.value,
        password: password.value
    }
    const response = await authStore.login(loginReq);
    console.log(response);
  } catch (error) {
    console.error(error);
  } 
}

const handleLogout = async() => {
    try {
        const response = await authStore.logout()
        console.log(response)
    } catch (error) {
        console.error(error);
    }
}
const handleAuthorities = async() => {
    try {
        const response = await authStore.getAuthorities();
        console.log(response)
        authorities.value = response.result;
    } catch (error) {
        console.error(error);
    }
}

</script>
<style scoped>
    
</style>