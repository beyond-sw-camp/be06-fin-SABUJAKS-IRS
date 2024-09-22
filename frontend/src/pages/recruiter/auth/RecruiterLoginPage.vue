<template>
  <body class="app-sign-in-register">
  <section class="app-sign-in-register__branding">
    <div class="app-sign-in-register__branding__starburst"></div>
    <img
        src="../../../assets/img/irs_white.png"
        alt="logo"
        style="width: 400px; height: auto"
    />
  </section>
  <main id="main-panel" class="app-sign-in-register__content">
    <div class="app-sign-in-register__content-inner">
      <h1>Welcome to IRS</h1>
      <form method="post" name="login" action="" @submit.prevent="handleLogin">
        <div>
          <label class="app-sign-in-register__form-label" for="j_username">사용자 이름</label>
          <input
              autocorrect="off"
              autocomplete="off"
              name="j_username"
              id="j_username"
              type="text"
              autofocus="autofocus"
              class="jenkins-input"
              autocapitalize="off"
              v-model="username"
          />
        </div>
        <div>
          <label class="app-sign-in-register__form-label" for="j_password">비밀번호</label>
          <input
              name="j_password"
              id="j_password"
              type="password"
              class="jenkins-input"
              v-model="password"
          />
        </div>
        <div class="jenkins-checkbox">
          <input type="checkbox" id="remember_me" name="remember_me" />
        </div>
        <input name="from" type="hidden" value="/" />
        <button type="submit" name="Submit" class="login-btn jenkins-button">로그인</button>
      </form>
      <div class="footer"></div>
    </div>
  </main>
  </body>
</template>

<script setup>
import { ref } from 'vue'
import { UseUserStore } from '@/stores/UseUserStore'
import router from "@/router";

const username = ref('')
const password = ref('')

const userStore = UseUserStore();

const handleLogin = async () => {
  try {
    const user = {
      email: username.value,
      password: password.value
    }

    const result = await userStore.login(user);
    if (result){
      await router.push('/recruiter/announce');  // 로그인 성공 시 recruiter/announce로 라우팅
      router.go(0);
    } else {
      alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
    }
  } catch (error) {
    alert('로그인에 실패했습니다. 아이디와 비밀번호를 확인해주세요.');
    console.error('로그인 오류:', error)
  }
}
</script>

<style>
</style>
