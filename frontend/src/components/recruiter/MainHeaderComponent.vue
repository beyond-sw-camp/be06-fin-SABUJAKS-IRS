<script setup>
import {ref, onMounted} from "vue";
import Cookies from "js-cookie";
import jwtDecode from "jwt-decode";
import {useRouter} from 'vue-router';

// 상태 및 라우터 설정
const isLoggedIn = ref(false);
const userName = ref("");
const router = useRouter();

// 로그인 상태 확인 함수
const checkLoginStatus = () => {
  const token = Cookies.get('ATOKEN'); // 쿠키에서 토큰 가져오기
  if (token) {
    try {
      const decodedToken = jwtDecode(token); // JWT 토큰 디코딩
      userName.value = decodedToken.name; // 토큰에서 이름 추출하여 설정
      isLoggedIn.value = true; // 로그인 상태 설정
    } catch (error) {
      console.error("토큰 디코딩 실패:", error);
    }
  }
};

// 로그아웃 함수
const logout = () => {
  Cookies.remove('ATOKEN'); // 쿠키에서 토큰 삭제
  isLoggedIn.value = false;
  userName.value = "";
  router.push('/login'); // 로그아웃 후 로그인 페이지로 이동
};

// 컴포넌트 마운트 시 로그인 상태 확인
onMounted(() => {
  checkLoginStatus();
});
</script>

<template>
  <header class="header">
    <div class="logo">
      <img src="@/assets/img/irs_white.png" style="width: 150px;">
    </div>
    <div class="user-info">
      <!-- 로그인된 상태라면 사용자 이름 표시 -->
      <div class="user-name" v-if="isLoggedIn">{{ userName }}</div>
      <!-- 로그인이 된 상태에서만 로그아웃 버튼 표시 -->
      <button class="logout-button" @click="logout" v-if="isLoggedIn">Logout</button>
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
  font-size: 24px;
  font-weight: bold;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-name {
  color: white;
  margin-right: 20px;
  font-size: 18px;
}

.logout-button {
  background-color: #ffffff;
  color: #212b36;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 16px;
}
</style>
