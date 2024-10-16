<template>
    <div class="login-wrapper">
        <SeekerHeaderComponent></SeekerHeaderComponent>
        <div class="login-container">
            <div class="login-section">
                <img class="logo" src="../../../assets/img/irs_black.png">
                <form class="login-form" @submit.prevent="handleLogin">
                    <section class="input-section">
                        <label class="label-id">아이디</label>
                        <input v-model="email" type="email" placeholder="아이디" size="16" maxlength="30" title="아이디 입력" required="">
                        <label class="label-pw">비밀번호</label>
                        <input v-model="password" type="password" placeholder="비밀번호" size="16" title="비밀번호 입력" required="">
                    </section>
                    <button type="submit" class="login-submitbtn">로그인</button>
                    <a href="/seeker/signup" class="redirect-signup">회원가입</a>
                </form>
                <section class="social-section">
                    <a class="social-button" @click.prevent="handleSocialLogin('kakao')">
                        <span class="social-kakao-logo"></span>
                        <span class="social-label">카카오 로그인</span>
                    </a>
                    <!-- <a class="social-button" @click.prevent="handleSocialLogin('naver')">
                        <span class="social-naver-logo"></span>
                        <span class="social-label">네이버 로그인</span>
                    </a> -->
                    <a class="social-button" @click.prevent="handleSocialLogin('google')">
                        <span class="social-google-logo"></span>
                        <span class="social-label">구글 로그인</span>
                    </a>
                </section>
            </div>
        </div>
        <SeekerFooterComponent></SeekerFooterComponent>
    </div>
</template>
    
<script setup>
import SeekerHeaderComponent from "@/components/seeker/SeekerHeaderComponent.vue";
import SeekerFooterComponent from "@/components/seeker/SeekerFooterComponent.vue"
import { ref } from "vue"
import { UseAuthStore } from "@/stores/UseAuthStore"
import { useToast } from "vue-toastification";
import { useRouter } from "vue-router"

const toast = useToast();
const router = useRouter();

const authStore = UseAuthStore();

const email = ref("")
const password = ref("")

const handleLogin = async() => {
    const requestBody = {
        email: email.value,
        password: password.value
    }
    const response = await authStore.login(requestBody)
    if(response && authStore.isLoggedIn){
        router.push("/")
        toast.success("로그인에 성공했습니다.")
    } else {
        toast.error("로그인에 실패했습니다. id/pw를 확인해주세요")
    }
}

const handleSocialLogin = async (provider) => {
    try {
        window.location.href = `/api/oauth2/authorization/${provider}`;
        const response = await authStore.getUserInfo();
        if (response.success) {
            authStore.isLoggedIn = true;
            router.push("/");
            toast.success("로그인에 성공했습니다.")
        } else {
            toast.error("로그인에 실패했습니다.");
        }
    } catch (error) {
        toast.error("소셜 로그인 처리 중 문제가 발생했습니다.");
    }
};
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
    justify-content: center;
    display: flex;
    align-items: center;
 
}

.login-section {
    display: flex;
    flex-direction: column;
    width: 1000px;
    margin: 0 0;
    padding: 60px;
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
    margin: 0;
    padding: 0;
    width: 100%;
    display: flex;
    flex-direction: column;
    min-height: auto
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
    text-decoration: none;
}

.social-section {
    margin-top: 12px;
    display: flex;
    justify-content: space-between;
    flex-direction: row;
    width: 100%;
}

.social-section a:nth-child(n+2) {
    margin-left: 20px;
}

.social-button{
    flex: 1;
    border: 0 none;
    padding: 15px;
    display: flex;
    justify-content: center;
    align-items: center;
    border-radius: 8px;
    background-color: #212b36; ;
    cursor: pointer;
    width: auto;
    height: auto;
    align-items: center;
    column-gap: 5px;
    color: white;
    font-size: 16px;
    letter-spacing: 0px;
    font-weight: 500;
    line-height: 22px;
    text-decoration: none;
}

.social-naver-logo {
    display: block;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background: no-repeat center;
    background-size: 100%;
    font-size: 0px;
    letter-spacing: 0px;
    text-indent: -9999px;
    background-image: url(../../../assets/svg/sns_naver_large.svg);
    background-repeat: no-repeat;
    color: #6a6a6a;
    text-decoration: none;
    cursor: pointer;
}

.social-kakao-logo {
    display: block;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background: no-repeat center;
    background-size: 100%;
    font-size: 0px;
    letter-spacing: 0px;
    text-indent: -9999px;
    background-image: url(../../../assets/svg/sns_kakao_large.svg);
    background-repeat: no-repeat;
    color: #6a6a6a;
    text-decoration: none;
    cursor: pointer;
}

.social-google-logo {
    display: block;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background: no-repeat center;
    background-size: 100%;
    font-size: 0px;
    letter-spacing: 0px;
    text-indent: -9999px;
    background-image: url(../../../assets/svg/sns_google_large.svg);
    background-repeat: no-repeat;
    color: #6a6a6a;
    text-decoration: none;
    cursor: pointer;
}

.social-label {
    text-decoration: none;
    cursor: pointer;
}

a:hover, button:hover {
    opacity: 70%;
}

</style>