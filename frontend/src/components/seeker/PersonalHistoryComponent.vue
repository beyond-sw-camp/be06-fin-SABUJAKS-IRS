<template>
    <div class="form-container">
      <div class="form-grid">

        <div class="form-item">
          <label for="companyName">회사명 <span class="required">*</span></label>
          <input type="text" id="companyName" v-model="companyName" />
        </div>
        <div class="form-item">
          <label for="departmentName">부서명 <span class="required">*</span></label>
          <input type="text" id="departmentName" v-model="departmentName" />
        </div>

        <!-- 입사년월과 퇴사년월을 같은 행에 배치 -->
        <div class="form-item form-grid-2">
          <div class="form-item">
            <label for="enteredAt">입사년월 <span class="required">*</span></label>
            <input type="text" id="enteredAt" v-model="enteredAt" placeholder="2023.03" class="small-input" />
          </div>
          <div class="form-item">
            <label for="quitAt">퇴사년월 <span class="required">*</span></label>
            <input type="text" id="quitAt" v-model="quitAt" placeholder="2023.03" class="small-input" />
          </div>
        </div>

        <div class="form-item">
          <div class="custom-checkbox-container" @click="toggleEmpStatus">
            <div class="custom-checkbox" :class="{ checked: empStatus }"></div>
            <label>재직중</label>
          </div>
        </div>

        <div class="form-item">
          <label for="position">직급/직책</label>
          <select id="position" v-model="position">
            <optgroup label="직급">
                <option value="사원">사원</option>
                <option value="주임/계장">주임/계장</option>
                <option value="대리">대리</option>
                <option value="과장">과장</option>
                <option value="차장">차장</option>
                <option value="부장">부장</option>
                <option value="임원">임원</option>
                <option value="연구원">연구원</option>
                <option value="주임연구원">주임연구원</option>
                <option value="책임연구원">책임연구원</option>
                <option value="수석연구원">수석연구원</option>
                <option value="연구소장">연구소장</option>
                <option value="기사">기사</option>
                <option value="주무">주무</option>
                <option value="기원">기원</option>
                <option value="기장">기장</option>
                <option value="기감">기감</option>
                <option value="기정">기정</option>
                <option value="기성">기성</option>
            </optgroup>
            <optgroup label="직책">
                <option value="팀원">팀원</option>
                <option value="팀장">팀장</option>
                <option value="매니저">매니저</option>
                <option value="파트장">파트장</option>
                <option value="실장">실장</option>
                <option value="지점장">지점장</option>
                <option value="지사장">지사장</option>
                <option value="원장">원장</option>
                <option value="국장">국장</option>
                <option value="본부장">본부장</option>
                <option value="센터장">센터장</option>
                <option value="공장장">공장장</option>
                <option value="그룹장">그룹장</option>
                <option value="조장">조장</option>
                <option value="반장">반장</option>
                <option value="직장">직장</option>
            </optgroup>

          </select>
        </div>


        <div class="form-item">
          <label for="job">담당직무 <span class="required">*</span></label>
          <input type="text" id="job" v-model="job" />
        </div>
        <div v-if="salaryVisible" class="form-item">
          <label for="salary">연봉</label>
          <input type="text" id="salary" v-model="salary" class="small-input" />
        </div>

        
        <!-- 졸업 논문/작품 클릭 시 -->
        <div v-if="workVisible" class="form-item work-item">
            <label for="work">담당업무 <span class="required">*</span></label>
            <textarea id="work" v-model="work" class="expandable-textarea" rows="3" placeholder="담당하신 업무와 성과에 대해 간단명료하게 적어주세요."></textarea>
        </div>

        <div class="form-grid salary-fields">
            <div class="form-item">
                <button class="custom-button" @click="toggleSalary">
                    <span class="button-icon" :class="{ active: salaryVisible }"></span>
                    연봉
                </button>
            </div>

            <div class="form-item">
                <button class="custom-button" @click="toggleWork">
                    <span class="button-icon" :class="{ active: workVisible }"></span>
                    담당업무
                </button>
            </div>
        </div>
        
        
      </div>
    </div>
</template>

<script setup>
import { ref, defineEmits, watch } from 'vue';

const companyName = ref(null);
const departmentName = ref(null);
const enteredAt = ref(null);
const quitAt = ref(null);
const empStatus = ref(null);
const position = ref(null);
const job = ref(null);
const work = ref(null);
const salary = ref(null);


const emit = defineEmits(['updateData']);

const updateData = () => {
  const data = {
    companyName: companyName.value,
    departmentName: departmentName.value,
    enteredAt: enteredAt.value,
    quitAt: quitAt.value,
    empStatus: empStatus.value,
    position: position.value,
    job: job.value,
    work: work.value,
    salary: salary.value
  };
  emit('updateData', data);
};

watch(
  [companyName, departmentName, enteredAt, quitAt, empStatus, position, job, work, salary],
  updateData,
);


const salaryVisible = ref(false);
const workVisible = ref(false);

const toggleEmpStatus = () => {
    empStatus.value = !empStatus.value;
};

const toggleSalary = () => {
  salaryVisible.value = !salaryVisible.value;
};

const toggleWork = () => {
  workVisible.value = !workVisible.value;
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

/* 입사/퇴사년월을 나란히 배치 */
.form-grid-2 {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  grid-gap: 15px;
}

.small-input {
  width: 80px;
}

.custom-checkbox-container {
  display: flex;
  align-items: center;
  cursor: pointer;
  margin-top: 20px;
}

.custom-checkbox {
  width: 20px;
  height: 20px;
  margin-right: 10px;
  background: url('../../assets/img/sprite-icon.png') no-repeat 0 -187px;
  transition: background-position 0.3s;
}

.custom-checkbox.checked {
  background-position: 0 -217px;
}

.custom-checkbox-container label {
  margin: 0;
}

.salary-fields {
    grid-column: span 4;
}

/* 버튼 스타일 */
.custom-button {
    display: flex;
    align-items: center;
    background: none;
    border: 1px solid #e0e0e0;
    cursor: pointer;
    padding: 5px;
    font-size: 14px;
    color: #007bff;
}

.custom-button .button-icon {
    width: 9px;
    height: 9px;
    margin-right: 10px;
    background: url('../../assets/img/sprite-icon.png') no-repeat 0 0;
    transition: background-position 0.3s;
}

.custom-button .button-icon.active {
    background-position: 0 -19px;
    width: 11px;
    height: 11px;
}

.custom-button:focus {
    outline: none;
}

.expandable-textarea {
    display: block;
    width: 100%;
    box-sizing: border-box;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
    resize: vertical; /* 세로 방향으로만 크기 조절 가능 */
}

.work-item {
    grid-column: span 4;
}

.work-item .expandable-textarea {
    height: 40px;
}

.work-item .expandable-textarea:focus {
    height: 80px;
    border-color: #007bff;
}

</style>
