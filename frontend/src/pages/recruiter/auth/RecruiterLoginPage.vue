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
        <form method="post" name="login" action="">
          <div>
            <label class="app-sign-in-register__form-label" for="j_username"
              >사용자 이름</label
            ><input
              autocorrect="off"
              autocomplete="off"
              name="j_username"
              id="j_username"
              type="text"
              autofocus="autofocus"
              class="jenkins-input"
              autocapitalize="off"
            />
          </div>
          <div>
            <label class="app-sign-in-register__form-label" for="j_password"
              >비밀번호</label
            ><input
              name="j_password"
              id="j_password"
              type="password"
              class="jenkins-input"
            />
          </div>
          <div class="jenkins-checkbox">
            <input type="checkbox" id="remember_me" name="remember_me" />
          </div>
          <input name="from" type="hidden" value="/" />
          <button type="submit" name="Submit" class="login-btn jenkins-button">
            로그인
          </button>
        </form>
        <div class="footer"></div>
      </div>
    </main>
  </body>
</template>

<script setup>
import './RecruiterLoginPage.css'
</script>

<style>
</style>