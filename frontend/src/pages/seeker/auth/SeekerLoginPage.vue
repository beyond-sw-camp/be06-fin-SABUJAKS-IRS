<template>
<div class="login-wrapper">
    <SeekerHeaderComponent></SeekerHeaderComponent>
    <div class="login-container">
        <div class="login-section">
            <img class="logo" src="../../../assets/img/irs_black.png">
            <form class="login-form" @submit.prevent="handleLogin">
                <section class="input-section">
                    <label class="label-id">아이디</label>
                    <input v-model="email" type="text" placeholder="아이디" size="16" maxlength="30" title="아이디 입력" required="">
                    <label class="label-pw">비밀번호</label>
                    <input v-model="password" type="password" placeholder="비밀번호" size="16" title="비밀번호 입력" required="">
                </section>
                <button type="submit" class="login-submitbtn">로그인</button>
                <a class="redirect-signup">회원가입</a>
            </form>
            <hr>
            <section class="social-section">
                <ul>
                    <li><a class="social-naver" @click="handleSocialLogin" href="http://localhost:8080/oauth2/authorization/kakao">네이버 로그인</a></li>
                    <li><a class="social-kakao" @click="handleSocialLogin" href="http://localhost:8080/oauth2/authorization/kakao">카카오 로그인</a></li>
                    <li><a class="social-google" @click="handleSocialLogin" href="http://localhost:8080/oauth2/authorization/kakao" >구글 로그인</a></li>
                </ul>
            </section>
            <p class="login-copyright">ⓒ SABUJAKS LLC. All Rights Reserved.</p>
        </div>
    </div>
</div>
</template>

<script setup>
import SeekerHeaderComponent from "@/components/seeker/SeekerHeaderComponent.vue";
import { ref } from "vue"
import { UseAuthStore } from "@/stores/UseAuthStore"
import { useToast } from "vue-toastification";
import { useRouter } from "vue-router"
const authStore = UseAuthStore();
const toast = useToast();
const router = useRouter();

const email = ref("")
const password = ref("")

const handleLogin = async() => {
    const requestBody = {
        email: email.value,
        password: password.value
    }
    const response = await authStore.login(requestBody)
    console.log(authStore.isLoggedIn)
    if(response && authStore.isLoggedIn){
        router.push("/")
        toast.success("로그인에 성공했습니다.")
    } else {
        toast.error("로그인에 실패했습니다. id/pw를 확인해주세요")
    }
}

const handleSocialLogin = async() => {
    authStore.isLoggedIn = true;
}
</script>

<style scoped>
.login-wrapper {
    margin: 0;
    padding: 0;
}

.login-container{
    margin: 100px auto;
    padding: 0;
    background-color: #ff0000;
    width: auto;
    height: fit-content;
    max-width: 1000px;
    margin: 100px auto;
    background-color: #fff;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    justify-content: center;
    display: flex;
    align-items: center;
 
}

.login-section {
    display: flex;
    flex-direction: column;
    width: 1030px;
    margin: 0 0;
    padding: 0 120px;
    background: #fff;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    justify-content: center;
    align-items: center;
}

.logo {
    justify-content: center;
    align-items: center;
    color: white;
    font-size: 24px;
    font-weight: bold;
    width: 150px;
}

.login-form{
    border: 0 none;
    margin: 0 auto;
    width: 100%;
    display: flex;
    flex-direction: column;
    min-height: 270px
}

.login-form input::-webkit-input-placeholder {
    font-weight: 400;
    color: #9e9e9e
}


.input-section {
    position: relative;
    text-align: left
}

.input-section .label-id {
    cursor: pointer;
    vertical-align: middle;
    width: 48px;
    height: 48px;
    position: absolute;
    text-indent: -9999px;
    overflow: hidden;
    background: url(../../../assets/svg/system_my.svg) no-repeat 16px 12px;
}

.input-section .label-pw {
    cursor: pointer;
    vertical-align: middle;
    width: 48px;
    height: 48px;
    position: absolute;
    text-indent: -9999px;
    overflow: hidden;
    background: url(../../../assets/svg/system_lock_closed.svg) no-repeat 16px 12px
}

.input-section input {
    -webkit-appearance: none;
    width: 100%;
    color: #333;
    box-sizing: border-box;
    display: flex;
    height: 48px;
    padding: 0px 16px 0px 48px;
    align-items: center;
    border-radius: 8px;
    border: 1px solid #d2d2d2;
    background: #fff;
    font-size: 14px;
    letter-spacing: 0px;
    font-weight: 400;
    line-height: 22px;
    margin-bottom: 12px;;
    outline: none;
}

.login-submitbtn {
    margin-bottom: 12px;
    border: 0 none;
    padding: 15px;
    overflow: visible;
    cursor: pointer;
    width: auto;
    height: auto;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 8px;
    background: #212b36;
    color: #fff;
    text-align: center;
    font-size: 16px;
    letter-spacing: 0px;
    font-weight: 500;
    line-height: 22px;
    box-sizing: border-box;
}

.redirect-signup {
    border: 0 none;
    padding: 15px;
    overflow: visible;
    cursor: pointer;
    width: auto;
    height: auto;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 8px;
    background: #212b36;
    color: #fff;
    text-align: center;
    font-size: 16px;
    letter-spacing: 0px;
    font-weight: 500;
    line-height: 22px;
    box-sizing: border-box;
}

.social-section {
    display: table;
    width: auto;
    margin: 0 auto 0;
    padding-top: 20px;
}

.social-section ul {
    display: block;
    list-style-type: disc;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
    padding-inline-start: 40px;
    unicode-bidi: isolate;
    padding: 0;
    margin: 0;
}

.social-section li {
    float: left;
    list-style: none;
    padding: 0;
    margin: 0;
    display: list-item;
    text-align: -webkit-match-parent;
    unicode-bidi: isolate;
}

.social-section li:nth-child(n+2) {
    margin-left: 20px;
}

.social-naver {
    display: block;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background: no-repeat center;
    background-size: 100%;
    font-size: 0px;
    letter-spacing: 0px;
    text-indent: -9999px;
    background-image: url(http://localhost:3000/img/sns_naver_large.41b7343a.svg);
    background-repeat: no-repeat;
    color: #6a6a6a;
    text-decoration: none;
    cursor: pointer;
}

.social-kakao {
    display: block;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background: no-repeat center;
    background-size: 100%;
    font-size: 0px;
    letter-spacing: 0px;
    text-indent: -9999px;
    background-image: url(http://localhost:3000/img/sns_kakao_large.40083307.svg);
    background-repeat: no-repeat;
    color: #6a6a6a;
    text-decoration: none;
    cursor: pointer;
}

.social-google {
    display: block;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background: no-repeat center;
    background-size: 100%;
    font-size: 0px;
    letter-spacing: 0px;
    text-indent: -9999px;
    background-image: url(http://localhost:3000/img/sns_google_large.72a153e9.svg);
    background-repeat: no-repeat;
    color: #6a6a6a;
    text-decoration: none;
    cursor: pointer;
}

a:hover, button:hover {
    opacity: 70%;
}

.login-copyright {
    font-size: 13px;
    letter-spacing: 0px;
    line-height: 24px;
    font-weight: 400;
    color: #9e9e9e
}
</style>