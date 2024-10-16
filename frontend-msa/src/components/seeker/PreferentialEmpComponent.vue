<template>
  <div class="form-container">
    <div class="form-grid">
      <!-- 상태 옵션 -->
      <div
        v-for="option in statusOptions"
        :key="option"
        class="form-item"
        :class="{ selected: selectedOptions.includes(option) }"
        @click="toggleOption(option)"
      >
        <!-- 체크박스 대신 스프라이트 이미지 사용 -->
        <div class="custom-checkbox" :class="{ checked: selectedOptions.includes(option) }"></div>
        <label>{{ option }}</label>
      </div>
    </div>

    <!-- 장애 관련 셀렉트 박스 렌더링 -->
    <div v-if="selectedOptions.includes('장애')" class="form-item">
      <div class="disability-container">
        <div class="disability-status">
          <label for="disability">장애</label>
          <select id="disability" v-model="disabilityDegree">
            <option disabled value="">선택해주세요</option>
            <option value="중증">중증</option>
            <option value="경증">경증</option>
            <option value="1급">1급</option>
            <option value="2급">2급</option>
            <option value="3급">3급</option>
            <option value="4급">4급</option>
            <option value="5급">5급</option>
            <option value="6급">6급</option>
          </select>
        </div>
      </div>
    </div>

    <!-- 병역 상태 관련 필드 렌더링 -->
    <div v-if="selectedOptions.includes('병역')" class="form-item">
      <div class="military-container">
        <div class="military-class">
          <label for="militaryClass">병역</label>
          <select id="militaryClass" v-model="militaryClass" @change="handleMilitaryClassChange">
            <option disabled value="">선택해주세요</option>
            <option value="군필">군필</option>
            <option value="미필">미필</option>
            <option value="면제">면제</option>
            <option value="해당없음">해당없음</option>
          </select>
        </div>
      </div>
      <!-- 군필 선택 시 추가 입력 필드 표시 -->
      <div v-if="militaryClass === '군필'" class="additional-fields">
        <div class="additional-field">
          <label for="militaryStart">입대일</label>
          <input id="militaryStart" v-model="militaryStart" maxlength="7" type="text" placeholder="2019.10"
          @input="$event.target.value = $event.target.value.replace(/[^0-9.]/g, '')" />
        </div>

        <div class="additional-field">
          <label for="militaryEnd">제대일</label>
          <input id="militaryEnd" v-model="militaryEnd" maxlength="7" type="text" placeholder="2021.10"
          @input="$event.target.value = $event.target.value.replace(/[^0-9.]/g, '')" />
        </div>

        <div class="additional-field">
          <label for="militaryType">군별</label>
          <select id="militaryType" v-model="militaryType">
            <option disabled value="">선택해주세요</option>
            <option value="육군">육군</option>
            <option value="해군">해군</option>
            <option value="공군">공군</option>
            <option value="해병">해병</option>
            <option value="전경">전경</option>
            <option value="의경">의경</option>
            <option value="공익">공익</option>
            <option value="기타">기타</option>
          </select>
        </div>

        <div class="additional-field">
          <label for="militaryRank">제대 계급</label>
          <select id="militaryRank" v-model="militaryRank">
            <option disabled value="">선택해주세요</option>
            <option value="이병">이병</option>
            <option value="일병">일병</option>
            <option value="상병">상병</option>
            <option value="병장">병장</option>
            <option value="하사">하사</option>
            <option value="중사">중사</option>
            <option value="상사">상사</option>
            <option value="원사">원사</option>
            <option value="준위">준위</option>
            <option value="소위">소위</option>
            <option value="중의">중의</option>
            <option value="대위">대위</option>
            <option value="소령">소령</option>
            <option value="중령">중령</option>
            <option value="대령">대령</option>
            <option value="준장">준장</option>
            <option value="소장">소장</option>
            <option value="중장">중장</option>
            <option value="대장">대장</option>
            <option value="기타">기타</option>
          </select>
        </div>
      </div>
    </div>
    
  </div>
</template>

<script setup>
import { ref, defineEmits, watch, defineProps } from 'vue';

const props = defineProps({
    data: {
        type: Object
    }
});

const preferentialEmp = props.data || {
    veterans: false,
    protection: false,
    subsidy: false,
    disability: false,
    military: false,
    disabilityDegree: '',
    militaryClass: '',
    militaryStart: null,
    militaryEnd: null,
    militaryRank: '',
    militaryType: ''
};

const selectedOptions = ref([]);

if(preferentialEmp.veterans) {
  selectedOptions.value.push('보훈대상');
}
if(preferentialEmp.protection) {
  selectedOptions.value.push('취업보호 대상');
}
if(preferentialEmp.subsidy) {
  selectedOptions.value.push('고용지원금 대상');
}
if(preferentialEmp.disability) {
  selectedOptions.value.push('장애');
}
if(preferentialEmp.military) {
  selectedOptions.value.push('병역');
}
const disabilityDegree = ref(preferentialEmp.disabilityDegree);
const militaryClass = ref(preferentialEmp.militaryClass);
const militaryStart = ref(preferentialEmp.militaryStart);
const militaryEnd = ref(preferentialEmp.militaryEnd);
const militaryRank = ref(preferentialEmp.militaryRank);
const militaryType = ref(preferentialEmp.militaryType);

const emit = defineEmits(['updateData']);
emit('updateData', props.data);


const updateData = () => {
  const data = {
    veterans: selectedOptions.value.includes('보훈대상'),
    protection: selectedOptions.value.includes('취업보호 대상'),
    subsidy: selectedOptions.value.includes('고용지원금 대상'),
    disability: selectedOptions.value.includes('장애'),
    military: selectedOptions.value.includes('병역'),
    disabilityDegree: disabilityDegree.value,
    militaryClass: militaryClass.value,
    militaryStart: militaryStart.value,
    militaryEnd: militaryEnd.value,
    militaryRank: militaryRank.value,
    militaryType: militaryType.value
  };
  emit('updateData', data);
};

watch(
  [selectedOptions, disabilityDegree, militaryClass, militaryStart, militaryEnd, militaryRank, militaryType],
  updateData,
  { immediate: true }
);

const statusOptions = ['보훈대상', '취업보호 대상', '고용지원금 대상', '장애', '병역'];

const toggleOption = (option) => {
  if (selectedOptions.value.includes(option)) {
    selectedOptions.value = selectedOptions.value.filter((opt) => opt !== option);
  } else {
    selectedOptions.value.push(option);
  }
};

const handleMilitaryClassChange = () => {
  if (militaryClass.value !== '군필') {
    militaryStart.value = '';
    militaryEnd.value = '';
    militaryRank.value = '';
    militaryType.value = '';
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

/* 그리드 레이아웃 설정 */
.form-grid {
  display: grid;
  grid-template-columns: repeat(5, 1fr); /* 5개 칸으로 구성 */
  gap: 10px;
}

/* 각 항목을 가운데 정렬 */
.form-item {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  background-color: white;
  border: 1px solid #ddd;
  border-radius: 8px;
  padding: 15px;
  height: 80px;
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: border-color 0.3s;
}

/* 선택된 항목의 스타일 */
.form-item.selected {
  border-color: #212b36; /* 선택된 상태일 때의 테두리 색상 */
}

/* 스프라이트 이미지를 체크박스 대신 사용 */
.custom-checkbox {
  width: 24px;
  height: 24px;
  background-image: url(../../assets/img/resume/check.png);

}

/* 선택된 상태의 체크박스 이미지 변경 */
.custom-checkbox.checked {
  background-image: url(../../assets/img/resume/check-mark.png);
}

/* 라벨 스타일 */
.form-item label {
  font-size: 14px;
  color: #888;
  margin-top: 10px;
}

/* 선택된 항목의 라벨 스타일 */
.form-item.selected label {
  color: #212b36; /* 선택된 상태일 때의 라벨 색상 */
}

.form-item input,
.form-item select {
  padding: 8px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

/* 장애 관련 셀렉트 박스 컨테이너 스타일 */
.disability-container {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 20px; /* 셀렉트 박스와 추가 필드 간의 간격 */
}

/* 장애 셀렉트 박스 스타일 */
.disability-status {
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 20px; /* 셀렉트 박스와 추가 필드 간의 간격 */
}

.disability-status label {
  margin-bottom: 5px; /* 라벨과 입력 필드 간의 간격 조정 */
}

/* 병역 상태 셀렉트 박스와 추가 필드 컨테이너 스타일 */
.military-container {
  display: flex;
  flex-direction: column;
}

/* 군번과 추가 필드가 옆으로 나란히 배치 */
.additional-fields {
  display: flex;
  flex-direction: row;
}

.additional-field {
  display: flex;
  flex-direction: column;
}

.additional-field label {
  margin-bottom: 5px;
}

.additional-field input,
.additional-field select {
  width: 150px;
  margin-left: 5px;
}
</style>
