<template>
<div class="form-container">
    <div class="form-grid">
    <div class="form-item">
        <label for="name">이름 <span class="required">*</span></label>
        <input type="text" id="name" v-model="name" />
        <span>{{ nameError }}</span>
    </div>

    <div class="form-item">
        <label for="birth">생년월일 <span class="required">*</span></label>
        <input type="text" id="birth" v-model="birth" maxlength="10" placeholder="2000.10.04"
        @input="$event.target.value = $event.target.value.replace(/[^0-9.]/g, '')"
        />
    </div>

    <div class="form-item">
        <div class="form-grid-2">
            <div class="form-item">
                <label for="gender">성별 <span class="required">*</span></label>
                <select id="gender" v-model="gender">
                <option disabled value="">선택</option>
                <option value="남자">남자</option>
                <option value="여자">여자</option>
                </select>
            </div>

            <div class="form-item">
                <label for="email">이메일 <span class="required">*</span></label>
                <input type="email" id="email" v-model="email" readonly />
            </div>
        </div>
    </div>

    <div class="form-item">
        <label for="tel">전화번호</label>
        <input type="text" id="tel" v-model="tel" @input="$event.target.value = $event.target.value.replace(/[^0-9\-]/g, '')" />
    </div>

    <div class="form-item">
        <label for="phone">휴대폰번호 <span class="required">*</span></label>
        <input type="text" id="phone" v-model="phone" placeholder="010-1234-1234" 
        @input="$event.target.value = $event.target.value.replace(/[^0-9\-]/g, '')"
        />
    </div>

    <div class="form-item">
        <label for="address">주소 <span class="required">*</span><span style="font-size: 12px;"> (상세주소를 이어서
            작성해주세요)</span></label>
        <input
        type="text"
        id="address"
        v-model="address"
        @click="openAddressSearch"
        placeholder="주소를 입력하세요"
        />
    </div>

    <div class="form-item photo-container">
        <div v-if="!photo">
        <button @click="triggerFileInput">사진추가</button>
        </div>
        <div v-else class="photo-preview">
        <img :src="photo" alt="사진 미리보기" />
        <button class="change-photo" @click="triggerFileInput">사진 변경</button>
        </div>
        <input type="file" ref="fileInput" @change="handleFileChange" accept="image/*" style="display: none;" />
    </div>
    </div>
</div>
</template>


<script setup>
import { ref, defineEmits, onMounted, watch, defineProps } from 'vue';
import { UseResumeStore } from '@/stores/UseResumeStore';
import { UseAuthStore } from '@/stores/UseAuthStore';

const store = UseResumeStore();
const authStore = UseAuthStore();
const props = defineProps({
    data: {
        type: Object
    }
});

const personalInfo = props.data || {
  name: null,
  birth: null,
  gender: '',
  email: null,
  phone: null,
  tel: null,
  address: null
};

const name = ref(personalInfo.name);
const birth = ref(personalInfo.birth);
const gender = ref(personalInfo.gender ? personalInfo.gender: '');
const email = ref(authStore.userInfo.email);
const phone = ref(personalInfo.phone);
const tel = ref(personalInfo.tel);
const address = ref(personalInfo.address);
const photo = ref(null);

const fileInput = ref(null);
const emit = defineEmits(['updateData']);
emit('updateData', props.data);



const updateData = () => {
  const data = {
    name: name.value,
    birth: birth.value,
    gender: gender.value,
    email: email.value,
    phone: phone.value,
    tel: tel.value,
    address: address.value
  };
  emit('updateData', data);
};

watch([name, birth, gender, email, phone, tel, address], updateData, { immediate: true });

const triggerFileInput = () => {
  fileInput.value.click();
};

const handleFileChange = (event) => {
  const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = (e) => {
      photo.value = e.target.result;
      store.updateFile(file);
      updateData();
    };
    reader.readAsDataURL(file);
  }
};

const openAddressSearch = () => {
  // eslint-disable-next-line no-undef
  new daum.Postcode({
    oncomplete: function (data) {
      address.value = data.address + ' ';
      updateData();
    }
  }).open();
};

onMounted(() => {
  const script = document.createElement('script');
  script.src = 'https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js';
  document.head.appendChild(script);
});
</script>


<style scoped>
.form-container {
    padding: 15px;
    border: 1px solid #e0e0e0;
    max-width: 800px;
    background-color: #ffffff;
}

.form-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-gap: 10px;
}

.form-item {
    display: flex;
    flex-direction: column;
}

.form-item label {
    margin-bottom: 5px;
}

.form-item input,
.form-item select {
    padding: 8px;   
    border: 1px solid #ddd;
    border-radius: 4px;
}

.required {
    color: red;
}

.photo-container {
    grid-column: 4 / span 1;
    grid-row: 1 / span 2;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    border: 1px solid #ddd;
    width: 103px;
    height: 134px;
    position: relative;
}

.photo-preview {
    position: relative;
    width: 100%;
    height: 100%;
}

.photo-preview img {
    width: 100%;
    height: 100%;
    object-fit: cover;
    border-radius: 4px;
    border: 1px solid #ddd;
}

.change-photo {
    position: absolute;
    bottom: 10px;
    right: 10px;
    padding: 5px 10px;
    border: none;
    border-radius: 4px;
    background-color: rgba(255, 255, 255, 0.7); /* Transparent white background */
    cursor: pointer;
}

.form-grid-2 { 
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    grid-gap: 15px;
}
</style>
