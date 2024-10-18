<template>
  <header class="header-cp">
    <div v-if="authStore.isLoggedIn && authStore.userInfo.role == 'ROLE_SEEKER'" class="header-container">
      <a href="/">
        <img class="header-logo" src="../../assets/img/irs_white.png">
      </a>
      <div class="header-right">
        <a href="#" @click="goAndReload('/seeker/mypage')">마이페이지</a>
        <!-- <a href="#">알림</a> -->
        <a>{{ displayName }}</a>
        <a @click="handleLogout" class="logout-btn">로그아웃</a>
      </div>
    </div>
    <div v-if="authStore.isLoggedIn && authStore.userInfo.role == 'ROLE_RECRUITER'" class="header-container">
      <a href="/">
        <img class="header-logo" src="../../assets/img/irs_white.png">
      </a>
      <div class="header-right">
        <a href="/recruiter/announce">채용 관리</a>
        <a>{{ displayName }}</a>
        <a @click="handleLogout" class="logout-btn">로그아웃</a>
      </div>
    </div>
    <div v-if="authStore.isLoggedIn && authStore.userInfo.role == 'ROLE_ESTIMATOR'" class="header-container">
      <a href="/">
        <img class="header-logo" src="../../assets/img/irs_white.png">
      </a>
      <div class="header-right">
        <span class="estimator-name">면접관-{{ authStore.userInfo.name }}</span>
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
import { useRouter } from 'vue-router';
const router = useRouter();
const toast = useToast();

const authStore = UseAuthStore();

const getUserInfoResult = ref({})
const displayName = ref('')

onMounted(async () => {
  if (authStore.isLoggedIn == true) {
    await handleGetUserInfo();
    console.log(authStore.userInfo.name);
  }
})

const goAndReload = (path) => {
  router.replace(path).then(() => {
    window.location.reload();
  });
};

const handleGetUserInfo = async () => {
  if (authStore.isLoggedIn && authStore.userInfo.email != null) {
    // const response = await authStore.getUserInfo();
    
    // if (response) {
      console.log("리스폰스 있을 때");
      getUserInfoResult.value = authStore.userInfo
      if (authStore.userInfo.name == null) {
        displayName.value = authStore.userInfo.nickName
      } else {
        displayName.value = authStore.userInfo.name
      }
    }
    else {
      // 어스스토어의 유저인포에서 로그아웃을 하기 때문에 여기로 온다.
      // toast.error(`세션이 만료되었습니다. 다시 로그인 해 주세요.`);
      console.log("리스폰스 없을 때");

      // router.push("/");
    }

  // } else if (authStore.userInfo && authStore.isLoggedIn) {
  //   getUserInfoResult.value = authStore.userInfo
  //   console.log("엘즈이프");
  // }

}

const handleLogout = async () => {
  const response = await authStore.logout();
  if (response.success) {
    authStore.isLoggedIn = false;
    getUserInfoResult.value = null
    toast.success(`로그아웃 했습니다.`)
    await router.push("/");
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

.estimator-name {
  color: #fff;
}
</style>