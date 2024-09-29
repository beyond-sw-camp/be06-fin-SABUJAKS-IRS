<template>
  <div class="form-container">
    <div class="form-grid">
      <div class="form-grid-2">
        <div class="form-item">
          <label for="portfolioDiv">구분 <span class="required">*</span></label>
          <select id="portfolioDiv" v-model="portfolioDiv">
            <option disabled value="">선택해주세요</option>
            <option value="이력서">이력서</option>
            <option value="포트폴리오">포트폴리오</option>
            <option value="증명서">증명서</option>
            <option value="자격증">자격증</option>
            <option value="추천서">추천서</option>
            <option value="기획서">기획서</option>
            <option value="기타">기타</option>
          </select>
        </div>

        <div class="form-item">
          <label for="portfolioType">선택 <span class="required">*</span></label>
          <select id="portfolioType" v-model="portfolioType">
            <option disabled value="">선택해주세요</option>
            <option value="url">url 입력</option>
            <option value="파일">파일 선택</option>
          </select>
        </div>
      </div>

      <div v-if="portfolioType === 'url'" class="form-item url-item">
        <label for="portfolioUrl">url 입력 <span class="required">*</span></label>
        <input type="text" id="portfolioUrl" v-model="portfolioUrl" placeholder="http://"/>
      </div>

      <div v-if="portfolioType === '파일'" class="form-item url-item">
        <label for="portfolioUrl">파일 선택 <span class="required">*</span></label>
        <input type="file" ref="fileInput" @change="handleFileChange"/>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, defineEmits, watch } from 'vue';

const portfolioDiv = ref('');
const portfolioType = ref('');
const portfolioUrl = ref(null);
const portfolioFile = ref(null);


const emit = defineEmits(['updateData']);

const handleFileChange = (event) => {
  portfolioFile.value = event.target.files[0]; // 파일 업데이트
  updateData();
};

const updateData = () => {
  let data = {
    portfolioDiv: portfolioDiv.value,
    portfolioType: portfolioType.value,
    portfolioUrl: portfolioUrl.value,
    portfolioFile: portfolioFile.value,
  };
  
  emit('updateData', data);
};

watch([portfolioDiv, portfolioType, portfolioUrl, portfolioFile], updateData);
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
  grid-gap: 15px;
}

.form-grid-2 {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-gap: 15px;
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

.small-input {
  width: 80px;
}

.url-item {
    grid-column: span 3;
}
</style>
