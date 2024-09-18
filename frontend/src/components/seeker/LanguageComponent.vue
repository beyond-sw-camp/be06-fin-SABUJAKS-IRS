<template>
    <div class="form-container">
      <div class="form-grid">
        <div class="form-grid-2">
          <div class="form-item">
            <label for="testDiv">구분 <span class="required">*</span></label>
            <select id="testDiv" v-model="testDiv">
              <option value="회화능력">회화능력</option>
              <option value="공인시험">공인시험</option>
            </select>
          </div>
  
          <div class="form-item">
            <label for="languageName">외국어명 <span class="required">*</span></label>
            <div v-if="!isDirectInput">
              <select id="languageName" v-model="languageName" @change="checkCustomLanguage">
                <option value="영어">영어</option>
                <option value="일본어">일본어</option>
                <option value="중국어">중국어</option>
                <option value="독일어">독일어</option>
                <option value="프랑스어">프랑스어</option>
                <option value="스페인어">스페인어</option>
                <option value="러시아어">러시아어</option>
                <option value="이탈리아어">이탈리아어</option>
                <option value="아랍어">아랍어</option>
                <option value="태국어">태국어</option>
                <option value="마인어">마인어</option>
                <option value="그리스어">그리스어</option>
                <option value="포르투갈어">포르투갈어</option>
                <option value="베트남어">베트남어</option>
                <option value="네덜란드어">네덜란드어</option>
                <option value="힌디어">힌디어</option>
                <option value="노르웨이어">노르웨이어</option>
                <option value="유고어">유고어</option>
                <option value="히브리어">히브리어</option>
                <option value="이란(페르시아어)">이란(페르시아어)</option>
                <option value="터키어">터키어</option>
                <option value="체코어">체코어</option>
                <option value="루마니아어">루마니아어</option>
                <option value="몽골어">몽골어</option>
                <option value="스웨덴어">스웨덴어</option>
                <option value="헝가리어">헝가리어</option>
                <option value="폴란드어">폴란드어</option>
                <option value="미얀마어">미얀마어</option>
                <option value="슬로바키아어">슬로바키아어</option>
                <option value="세르비아어">세르비아어</option>
                <option value="한국어">한국어</option>
                <option value="직접입력">직접입력</option>
              </select>
            </div>
            <div v-else>
              <input type="text" v-model="languageName" placeholder="외국어명을 입력하세요" />
            </div>
          </div>
        </div>
  
        <div v-if="testDiv !== '공인시험'" class="form-item">
          <label for="conversationLevel">회화능력 <span class="required">*</span></label>
          <select id="conversationLevel" v-model="conversationLevel">
            <option value="일상 회화 가능">일상 회화 가능</option>
            <option value="비즈니스 회화 가능">비즈니스 회화 가능</option>
            <option value="원어민 수준">원어민 수준</option>
          </select>
        </div>
  
        <!-- 공인시험 선택 시 -->
        <div v-if="testDiv === '공인시험'" class="form-item">
          <label for="officialTest">공인시험 <span class="required">*</span></label>
          <input type="text" id="officialTest" v-model="officialTest" />
        </div>
  
        <div class="form-grid-2">
          <div v-if="testDiv === '공인시험' && selectScore !== '취득'" class="form-item">
            <label for="score">급수/점수 <span class="required">*</span></label>
            <input type="text" id="score" v-model="score" class="small-input" />
          </div>
  
          <div v-if="testDiv === '공인시험'" class="form-item">
            <label for="selectScore">선택 <span class="required">*</span></label>
            <select id="selectScore" v-model="selectScore">
              <option value="점">점</option>
              <option value="급">급</option>
              <option value="취득">취득</option>
            </select>
          </div>
        </div>
  
        <div v-if="testDiv === '공인시험'" class="form-item">
          <label for="takingAt">취득년월</label>
          <input type="text" id="takingAt" v-model="takingAt" class="small-input" />
        </div>
      </div>
    </div>
  </template>
  
<script setup>
import { ref, defineEmits, watch } from 'vue';

const testDiv = ref(null);
const languageName = ref(null);
const conversationLevel = ref(null);
const officialTest = ref(null);
const score = ref(null);
const selectScore = ref(null);
const takingAt = ref(null);

const emit = defineEmits(['updateData']);

const updateData = () => {
  const data = {
    testDiv: testDiv.value,
    languageName: languageName.value,
    conversationLevel: conversationLevel.value,
    officialTest: officialTest.value,
    score: score.value,
    selectScore: selectScore.value,
    takingAt: takingAt.value
  };
  emit('updateData', data);
};

watch(
  [testDiv, languageName, conversationLevel, officialTest, score, selectScore, takingAt],
  updateData,
);
  
  const isDirectInput = ref(false);
  
  const checkCustomLanguage = () => {
    if (languageName.value === '직접입력') {
      isDirectInput.value = true;
      languageName.value = '';
    }
  };
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
  </style>
  