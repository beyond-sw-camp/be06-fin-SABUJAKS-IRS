<template>
    <div class="signup-wrapper">
        <SeekerHeaderComponent></SeekerHeaderComponent>
        <div class="signup-container">
            <img class="logo" src="../../../assets/img/irs_black.png">
            <div class="set-user">
                <div class="seeker"> 지원자 가입</div>
                <div class="recruiter"> 
                    <a href="/recruiter/signup">채용담당자 가입</a></div>
                </div>
            <form class="signup-form" @submit.prevent="handleSignup">
                <!-- <div class="social-section">
                    <h3 class="t1">소셜로 간편하게 로그인하세요</h3>
                    <div class="social-list">
                        <a class="social-kakao-logo" @click.prevent="handleSocialLogin('kakao')"></a>
                        <a class="social-naver-logo" @click.prevent="handleSocialLogin('naver')"></a>
                        <a class="social-google-logo" @click.prevent="handleSocialLogin('google')"></a>
                    </div>
                </div> -->
                <div class="input-section">
                    <h3 class="t1">지원자 정보</h3>
                    <input v-model="email" type="email" maxlength="30" placeholder="이메일">
                    <input v-model="password" type="password" maxlength="16" placeholder="비밀번호 (8~16자의 영문, 숫자, 특수기호)">
                    <input v-model="name" type="text" maxlength="16" placeholder="이름">
                    <input v-model="nickname" type="text" maxlength="16" placeholder="닉네임">
                    <div class="is-col">
                        <input v-model="birth" type="text" maxlength="16" placeholder="생년월일 ex) 20201212">
                        <div class="is-col2">
                        <input v-model="gender" type="radio" id="boy" :value="true">
                        <label for="boy">남자</label>
                        <input v-model="gender" type="radio" id="girl" :value="false">
                        <label for="girl">여자</label>
                        </div>
                    </div>
                    <input v-model="phoneNumber" type="text" maxlength="16" placeholder="휴대폰번호 ex) 01012341234">
                    <div class="is-col" style="gap: 10px;">
                        <input v-model="address" type="text" placeholder="주소" @click="openAddressSearch">
                        <input v-model="addressDetail" type="text" placeholder="상세 주소">
                    </div>
                    <label for="file">
                        <div class="file-uploadbtn">프로필 파일 업로드</div>
                    </label>
                    <input @change="handleFileUpload" type="file" name="file" id="file">
                    <div class="file-preview" v-if="fileUrl">
                        <img :src="fileUrl" />
                    </div>
                </div>
                <div class="submit-section">
                    <p class="signup-notice">가입 후 이메일 인증까지 완료하여야 계정이 활성화됩니다.</p>
                    <button type="submit" class="signup-submitbtn">가입하기</button>
                </div>
            </form> 
        </div>
        <SeekerFooterComponent></SeekerFooterComponent>
    </div>
</template>

<script setup>
import SeekerHeaderComponent from "@/components/seeker/SeekerHeaderComponent.vue";
import SeekerFooterComponent from "@/components/seeker/SeekerFooterComponent.vue"
import { UseAuthStore } from "@/stores/UseAuthStore"
import { ref, onMounted } from "vue";
import { useRouter } from "vue-router";
import { useToast } from "vue-toastification";

const toast = useToast();
const router = useRouter();

const authStore = UseAuthStore();

const email = ref("")
const password = ref("")
const name = ref("")
const nickname = ref("")
const gender = ref(null)
const birth = ref("")
const phoneNumber = ref(null)
const address = ref("")
const addressDetail = ref("")
const file = ref(null);
const fileUrl = ref(null);

onMounted(() => {
  const script = document.createElement('script');
  script.src = 'https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
  document.head.appendChild(script);
});

const openAddressSearch = () => {
  // eslint-disable-next-line no-undef
  new daum.Postcode({
    oncomplete: function (data) {
      address.value = data.address;
    }
  }).open();
};

const handleFileUpload = (event) => {
    file.value = event.target.files[0];
    if (file.value) { fileUrl.value = URL.createObjectURL(file.value); }
};

const handleSignup = async () => {
    const formData = new FormData();
    const requestBody = {
        role: "ROLE_SEEKER",
        email: email.value,
        password: password.value,
        name: name.value,
        nickname: nickname.value,
        gender: gender.value,
        birth: birth.value,
        socialType: "normal",
        phone_number: phoneNumber.value,
        address: address.value + ',' + addressDetail.value
    }
    formData.append('dto', new Blob([JSON.stringify(requestBody)], { type: 'application/json' }));
    if (file.value) { formData.append('file', file.value); }
    const response = await authStore.signup(formData);
    if(response.success) {
        router.push("/seeker/login");
        toast.success("가입 후 이메일 인증까지 완료하여야 계정이 활성화됩니다.");
    } else {
        toast.error("회원가입에 실패했습니다.");
    }
}

// const handleSocialLogin = async (provider) => {
//     try {
//         window.location.href = `/api/oauth2/authorization/${provider}`;
//         const response = await authStore.getUserInfo();
//         if (response.success) {
//             authStore.isLoggedIn = true;
//             router.push("/");
//             toast.success("로그인에 성공했습니다.")
//         } else {
//             toast.error("로그인에 실패했습니다.");
//         }
//     } catch (error) {
//         toast.error("소셜 로그인 처리 중 문제가 발생했습니다.");
//     }
// };
</script>

<style scoped>
.signup-wrapper {
    margin: 0;
    padding: 0;
}

.signup-container {
    width: auto;
    height: fit-content;
    max-width: 1000px;
    padding: 20px;
    margin: 100px auto;
    background-color: #fff;
    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
    justify-content: center;
    display: flex;
    flex-direction: column;
    align-items: center;
}

.t1 {
    margin: 20px 0;
    font-weight: 600;
    line-height: 26px;
    font-size: 1.1875rem;
}

.logo {
    justify-content: center;
    align-items: center;
    color: white;
    font-size: 24px;
    font-weight: bold;
    width: 150px;
}

.set-user {
    display: flex;
    flex-direction: row;
    position: relative;
    width: 700px;
    margin-bottom: 12px;
    border: 1px solid #e8e8e8;
    background-color: #fff;
    box-sizing: border-box;
    align-items: center;
    justify-content: space-between;
    height: 96px;
}

.seeker {
    align-items: center;
    justify-content: center;
    display: flex;
    flex: 1;
    width: 100%;
    height: 100%;
    background-color: #212b36;
    color: white;
    margin: 20px 0;
    font-weight: 600;
    line-height: 26px;
    font-size: 1.1875rem;
}

.recruiter {
    align-items: center;
    justify-content: center;
    display: flex;
    flex: 1;
    width: 100%;
    height: 100%;
    margin: 20px 0;
    font-weight: 600;
    line-height: 26px;
    font-size: 1.1875rem;
}

.recruiter a {
    text-decoration: none;
    color: black;
}

.social-section {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 28px 32px;
    width: 700px;
    height: 96px;
    margin-bottom: 12px;
    align-items: center;
    border: 1px solid #e8e8e8;
    background-color: #fff;
    box-sizing: border-box;
}

.social-list{
    display: flex;
    flex-direction:row;
    column-gap: 15px;
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

.signup-form {
    margin: 0;
    padding: 0;
    border: 0;
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}

.input-section {
    position: relative;
    width: 700px;
    padding: 0 32px;
    margin-bottom: 12px;
    border: 1px solid #e8e8e8;
    background-color: #fff;
    box-sizing: border-box;
    padding-bottom: 20px !important;
}

.input-section input {
    width: 100%;
    margin: 10px 0;
    height: 62px;
    padding: 0 12px;
    display: block;
    outline: 0;
    box-sizing: border-box;
    font-size: 16px;
    letter-spacing: 0px;
    color: #333;
    border: 1px solid #d2d2d2;
    background: transparent;
}

.is-col {
    position:relative;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
}

.is-col input {
    display: flex;
    flex: 1;
}

.is-col input[type="radio"]{
    width: 28px;
    height: 28px
}

.is-col2 {
    display: flex;
    flex: 1;
    flex-direction: row;
    align-items: center;
}

.input-section p {
    align-items: center;
    justify-content: center;
    display: flex;
}

.signup-submitbtn{
    background-color: #212b36;
    text-align: center;
    height: 100%;
    display: block;
    padding: 16px;
    box-sizing: border-box;
    font-size: 18px !important;
    line-height: 26px !important;
    font-weight: 700;
    color: #fff;
    width: 100%;
    border: none;
    outline: none;
}

.signup-submitbtn:hover {
    opacity: 70%;
}

.file-uploadbtn {
    background-color: #212b36;
    text-align: center;
    height: 100%;
    display: block;
    padding: 16px;
    box-sizing: border-box;
    font-size: 18px;
    line-height: 26px;
    font-weight: 700;
    color: #fff;
    width: 100%;
    border: none;
    outline: none;
}

.file-uploadbtn:hover {
    opacity: 70%;
    color: #fff;
}

#file {
  display: none;
}

.file-preview {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: fit-content;
}

.submit-section {
    position: relative;
    display: flex;
    flex-direction: column;;
    width: 700px;
    padding: 0 32px;
    margin-bottom: 12px;
    border: 1px solid #e8e8e8;
    justify-content: center;
    align-items: center;
    background-color: #fff;
    box-sizing: border-box;
    padding-bottom: 20px !important;
}
</style>