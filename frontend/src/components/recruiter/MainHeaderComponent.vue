<script setup>
import {ref, onMounted} from "vue";
import {useRouter} from 'vue-router';
import {UseAuthStore} from "@/stores/UseAuthStore";
import { useToast } from "vue-toastification";

// 상태 및 라우터 설정
const isLoggedIn = ref(false);
const userName = ref("");
const router = useRouter();
const authStore = UseAuthStore()
const toast = useToast();

// 로그인 상태 확인 함수
const checkLoginStatus = async() => {
    if(!authStore.isLoggedIn || !authStore.userInfo.role == "ROLE_RECURITER"){
      toast.error("로그인하지 않은 사용자는 접근할 수 없습니다.")
      router.push('/recruiter/login')
    }
    // authStore.getUserInfo();
    if(authStore.userInfo != null) {
      userName.value = authStore.userInfo.name; // name 값 셋팅
    }
};


// 로그아웃 함수
const logout = () => {
  sessionStorage.clear();
  authStore.logout();
  isLoggedIn.value = false;
  router.push('/recruiter/login'); // 로그아웃 후 로그인 페이지로 이동
};

// 컴포넌트 마운트 시 로그인 상태 확인
onMounted(async() => {
  await checkLoginStatus();
});
</script>

<template>
  <header class="header">
    <div class="logo pl-25">
      <a href="/recruiter/announce">
        <img src="@/assets/img/irs_white.png">
      </a>
    </div>
    <div v-if="authStore.isLoggedIn" class="user-info">
      <div class="user-name">
        <a href="/">
          메인페이지
        </a>
      </div>
      <!-- 로그인된 상태라면 사용자 이름 표시 -->
      <div class="user-name">
        <a>
          {{ userName }}
        </a>
      </div>
      <!-- 로그인이 된 상태에서만 로그아웃 버튼 표시 -->
      <div class="user-name">
        <a class="logout-button" @click="logout">로그아웃</a>
      </div>
    </div>
  </header>
</template>


<style scoped>
/* 스타일 부분은 그대로 유지 */
header {
  background-color: #f8f9fa;
  color: #333;
  padding: 20px;
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100px;
  width: 100%;
  background-color: #212b36;
  padding: 0 20px;
  box-sizing: border-box;
  position: fixed;
  z-index: 2;
}

.logo {
  color: white;
  font-weight: bold;
}
.logo img {
  width: 100px;
  height: 100px;
}

.user-info {
  display: flex;
  align-items: center;
  padding-right: 50px;
}

.user-name {
  color: #f1f1f1;
  margin-right: 20px;
}

.user-name a {
  cursor: pointer;
  text-decoration: none;
  color: #e6e6e6;
  font-size: 15px;
}

.user-name a:hover {
  opacity: 70%;
}

.logout-button {
  background-color: #212b36;
  color: #f1f1f1;
  border: none;
  padding: 15px 10px;
  cursor: pointer;
  font-size: 15px;
}
</style>
