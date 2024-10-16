<template>
    <div class="signup-wrapper">
        <SeekerHeaderComponent></SeekerHeaderComponent>
        <div class="signup-container">
            <img class="logo" src="../../../assets/img/irs_black.png">
            <div class="set-user">
                <div class="seeker">
                    <a href="/seeker/signup">지원자 가입</a>
                </div>
                <div class="recruiter">채용담당자 가입</div>
                </div>
                <form class="signup-form" @submit.prevent="handleCompanyVerify">
                <div class="input-section">
                    <h3 class="t1">기업인증</h3>
                    <input v-model="crn" type="text" maxlength="30" placeholder="사업자등록번호">
                    <input v-model="opened_at" type="text" maxlength="30" placeholder="개업일자 ex) 20201212">
                    <input v-model="ceo_name" type="text" maxlength="30" placeholder="회사대표명">
                    <input v-model="recruiter_email" type="email" maxlength="30" placeholder="채용담당자이메일">
                    <input type="text" maxlength="30" placeholder="기업인식비밀번호" readonly value="TESTCOMPANYSECRETCODE">
                    <button type="submit" class="company-verifybtn">기업인증</button>
                </div>
            </form> 
            <form class="signup-form" @submit.prevent="handleSignup">
                <div class="input-section">
                    <h3 class="t1">채용담당자 정보</h3>
                    <input v-model="email" type="email" maxlength="30" placeholder="이메일">
                    <input v-model="password" type="password" maxlength="30" placeholder="비밀번호 (8~16자의 영문, 숫자, 특수기호)">
                    <input v-model="name" type="text" maxlength="30" placeholder="이름">
                    <input v-model="phoneNumber" type="text" maxlength="30" placeholder="휴대폰번호 ex) 01012341234">
                    <label for="file">
                        <div class="file-uploadbtn">프로필 파일 업로드</div>
                    </label>
                    <input @change="handleFileUpload" type="file" name="file" id="file">
                    <div class="file-preview" v-if="fileUrl">
                        <img :src="fileUrl" />
                    </div>
                </div>
                <div class="submit-section">
                    <p class="signup-notice">기업인증을 완료해야 가입을 할 수 있습니다.</p>
                    <p class="signup-notice">가입 후 이메일 인증까지 완료하여야 계정이 활성화됩니다.</p>
                    <button v-if="showSignupBtn" type="submit" class="signup-submitbtn">가입하기</button>
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
import { ref } from "vue";
import { useToast } from "vue-toastification";

const toast = useToast();

const authStore = UseAuthStore();

const email = ref("")
const password = ref("")
const name = ref("")
const phoneNumber = ref(null)
const file = ref(null);
const fileUrl = ref(null);
const showSignupBtn = ref(false);
const crn = ref("")
const opened_at = ref("")
const ceo_name = ref("")
const recruiter_email = ref("")

const handleFileUpload = (event) => {
    file.value = event.target.files[0];
    if (file.value) { fileUrl.value = URL.createObjectURL(file.value); }
};

const handleCompanyVerify = async () => {
    const requestBody = {
        crn: crn.value,
        opened_at: opened_at.value,
        ceo_name: ceo_name.value,
        recruiter_email: recruiter_email.value,
        company_secret_code: "TESTCOMPANYSECRETCODE"
    }
    const response = await authStore.companyVerify(requestBody)
    if(response.success){
        showSignupBtn.value = true;
        toast.success("기업 인증을 완료했습니다.")
    }else {
        toast.error("기업 인증 실패")
    }
}

const handleSignup = async () => {
    const formData = new FormData();
    const requestBody = {
        role: "ROLE_RECRUITER",
        email: email.value,
        password: password.value,
        name: name.value,
        phone_number: phoneNumber.value
    }
    formData.append('dto', new Blob([JSON.stringify(requestBody)], { type: 'application/json' }));
    if (file.value) { formData.append('file', file.value); }
    const response = await authStore.signup(formData);
    if(response.success) {
        toast.success("가입 후 이메일 인증까지 완료하여야 계정이 활성화됩니다.");
    } else {
        toast.error("회원가입에 실패했습니다.");
    }
}
</script>

<style scoped>
.signup-wrapper {
    margin: 0;
    padding: 0;
}

.t1 {
    margin: 20px 0;
    font-weight: 600;
    line-height: 26px;
    font-size: 1.1875rem;
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

.recruiter {
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

.seeker {
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

.seeker a {
    text-decoration: none;
    color: black;
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

.company-verifybtn {
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

.company-verifybtn:hover {
    opacity: 70%;
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
    font-size: 18px !important;
    line-height: 26px !important;
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