<template>
    <header class="header-cp">
        <div v-if="authStore.isLoggedIn && authStore.userInfo.role=='ROLE_SEEKER'" class="header-container">
            <a href="/">
                <img class="header-logo" src="../../assets/img/irs_white.png">
            </a>
            <div class="header-right">
                <a href="#">내 공고 달력</a>
                <a href="#" @click="goAndReload('/seeker/mypage')">마이페이지</a>
                <a href="#">알림</a>
                <a >{{ displayName }}</a>
                <a @click="handleLogout" class="logout-btn">로그아웃</a>
            </div>
        </div>
        <div v-if="authStore.isLoggedIn && authStore.userInfo.role=='ROLE_RECRUITER'" class="header-container">
            <a href="/">
                <img class="header-logo" src="../../assets/img/irs_white.png">
            </a>
            <div class="header-right">
                <a href="/recruiter">채용 관리</a>
                <a >{{ displayName }}</a>
                <a @click="handleLogout" class="logout-btn">로그아웃</a>
            </div>
        </div>
        <div v-if="!authStore.isLoggedIn" class="header-container">
            <a href="/">
                <img class="header-logo" src="../../assets/img/irs_white.png">
            </a>
            <div class="header-right">
                <a href="/seeker/login">지원자 로그인</a>
                <a href="/recruiter/login">채용담당자 로그인</a>
            </div>
        </div>
    </header>
</template>

<script setup>
import { UseAuthStore } from "../../stores/UseAuthStore"
import { ref, onMounted } from "vue";
import { useToast } from "vue-toastification";
import {useRouter} from 'vue-router';
const router = useRouter();

const goAndReload = (path) => {
  router.replace(path).then(() => {
    window.location.reload();
  });
};


const authStore = UseAuthStore(); 
const toast = useToast();
const getUserInfoResult = ref({})
const displayName = ref('')

onMounted( async() => { 
    if(authStore.isLoggedIn == true){
        await handleGetUserInfo(); 
    } 
})

const handleGetUserInfo = async() => {
    if(authStore.isLoggedIn && authStore.userInfo.email != null){
        const response = await authStore.getUserInfo();
        if(response.success){ 
            getUserInfoResult.value = authStore.userInfo
            if(authStore.userInfo.name == null){
                displayName.value = authStore.userInfo.nickName
            } else {
                displayName.value = authStore.userInfo.name
            }
        }
        else { 
            toast.error("회원정보를 불러오는데 실패했습니다.")
        }
    } else if (authStore.userInfo && authStore.isLoggedIn){
        getUserInfoResult.value = authStore.userInfo
    }
}

const handleLogout = async() => {
    const response = await authStore.logout();
    if(response.success){
        authStore.isLoggedIn = false;
        getUserInfoResult.value = null
        toast.success(`로그아웃 했습니다.`)
    } else {
        toast.error("로그아웃에 실패했습니다.");
    }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
}

.header-cp {
  position: fixed;
  top: 0;
  width: 100%;
  height: 100px;
  background-color: #212b36;
  border-bottom: 1px solid #ddd;
  z-index: 999;
}

.header-container {
  max-width: 1200px;
  height: 100%;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header-container h1 {
  font-size: 24px;
  color: #333;
}

.header-logo {
  color: white;
  font-size: 30px;
  font-weight: bold;
  width: 100px;
}

.header-right {
  display: flex;
}

.header-right a {
  margin-left: 20px;
  cursor: pointer;
  text-decoration: none;
  color: #e6e6e6;
  font-size: 15px;
}

.header-right a:hover {
  opacity: 70%;
}
</style>