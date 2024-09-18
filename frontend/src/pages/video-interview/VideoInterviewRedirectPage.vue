<template >
    <div>
    <form @submit.prevent="handleLogin">
      <label for="email">email </label>
      <input type="text" v-model="email" placeholder="Enter email" />
      <label for="password">password </label>
      <input type="text" v-model="password" placeholder="Enter videoInterviewRoomUUID" />

      <!-- 제출 버튼 -->
      <button type="submit">Submit</button>
    </form>
    <hr>

    <hr>
    <button @click="handleLogout">로그아웃</button><br>

    <button @click="handleAuthorities">권한불러오기</button><br>
    <div v-for="(data, index) in authorities" :key="index">
    <p>{{ data.authority }}</p>
</div>
</div>
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
    const loginReq = { email: email.value, password: password.value}
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
        authorities.value = response;
    } catch (error) {
        console.error(error);
    }
}

</script>
<style scoped>
.test-1 {
    display: flex;
}
</style>