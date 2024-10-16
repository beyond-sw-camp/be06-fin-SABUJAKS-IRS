<template>
    <div class="form-container">
        <div class="form-grid">
            <div class="form-item">
                <label for="schoolDiv">학교구분 <span class="required">*</span></label>
                <select id="schoolDiv" v-model="schoolDiv">
                    <option disabled value="">선택해주세요</option>
                    <option value="고등학교">고등학교</option>
                    <option value="대학교(2,3년)">대학교(2,3년)</option>
                    <option value="대학교(4년)">대학교(4년)</option>
                    <option value="대학원">대학원</option>
                </select>
            </div>

            <div class="form-item">
                <div class="form-grid-2">
                    <div class="form-item">
                        <label for="schoolName">학교명 <span class="required">*</span></label>
                        <input 
                            :class="{ 'small-input': schoolDiv === '대학원' }"
                            type="text" 
                            id="schoolName" 
                            v-model="schoolName" 
                        />

                    </div>
                    <div v-if="schoolDiv === '대학원'" class="form-item">
                        <div class="degree-select-container">
                            <label for="degree">학위</label>
                            <select id="degree" v-model="degree" class="small-select">
                                <option disabled value="">선택</option>
                                <option value="석사">석사</option>
                                <option value="박사">박사</option>
                                <option value="석박사">석박사</option>
                            </select>
                        </div>
                    </div>
                </div>
            </div>
            

            <!-- 고등학교 선택 시 -->
            <div v-if="schoolDiv === '고등학교'" class="form-item">
                <label :for="qualificationExam ? 'passAt' : 'graduatedAt'">
                    {{ qualificationExam ? "합격년월" : "졸업년월" }}
                </label>
                <!-- 대입검정고시 o -->
                <input v-if="qualificationExam" type="text" v-model="passedAt" class="small-input" maxlength="7" placeholder="2016.03"
                @input="$event.target.value = $event.target.value.replace(/[^0-9.]/g, '')"/>
                <!-- 대입검정고시 x -->
                <input v-else type="text" id="graduatedAt" v-model="graduatedAt" class="small-input" maxlength="7" placeholder="2016.02"
                @input="$event.target.value = $event.target.value.replace(/[^0-9.]/g, '')"/>
            </div>

            <div v-if="schoolDiv === '고등학교' && !qualificationExam" class="form-item">
                <label for="graduationStatus">졸업상태</label>
                <select id="graduationStatus" v-model="graduationStatus">
                    <option disabled value="">선택</option>
                    <option value="졸업">졸업</option>
                    <option value="졸업예정">졸업예정</option>
                    <option value="재학중">재학중</option>
                </select>
            </div>

            <div v-if="schoolDiv === '고등학교'" class="form-item">
                <div class="form-item">
                    <div class="custom-checkbox-container" @click="toggleQualificationExam">
                        <div class="custom-checkbox" :class="{ checked: qualificationExam }"></div>
                        <label>대입검정고시</label>
                    </div>
                </div>
            </div>

            <!-- 고등학교 외 선택 시 -->
            <div v-if="schoolDiv && schoolDiv !== '고등학교'" class="form-item">
                <div class="form-grid-2">
                    <div class="form-item">
                        <label for="enteredAt">입학년월</label>
                        <input type="text" id="enteredAt" v-model="enteredAt" maxlength="7" placeholder="2023.03" class="small-input" 
                        @input="$event.target.value = $event.target.value.replace(/[^0-9.]/g, '')" />
                    </div>
                    <div class="form-item">
                        <label for="graduatedAt">졸업년월</label>
                        <input type="text" id="graduatedAt" v-model="graduatedAt" maxlength="7" placeholder="2023.03" class="small-input" 
                        @input="$event.target.value = $event.target.value.replace(/[^0-9.]/g, '')"/>
                    </div>
                </div>
            </div>


            <div v-if="schoolDiv && schoolDiv !== '고등학교'" class="form-item">
                <label for="graduationStatus">졸업 상태</label>
                <select id="graduationStatus" v-model="graduationStatus">
                    <option disabled value="">선택</option>
                    <option value="졸업">졸업</option>
                    <option value="졸업예정">졸업예정</option>
                    <option value="재학중">재학중</option>
                    <option value="중퇴">중퇴</option>
                    <option value="수료">수료</option>
                    <option value="휴학">휴학</option>
                </select>
            </div>

            <div v-if="schoolDiv && schoolDiv !== '고등학교'" class="form-item">
                <div class="form-item">
                    <div class="custom-checkbox-container" @click="toggleTransfer">
                        <div class="custom-checkbox" :class="{ checked: transfer }"></div>
                        <label>편입</label>
                    </div>
                </div>
            </div>

            <div v-if="schoolDiv && schoolDiv !== '고등학교'" class="form-item major-item">
                <label for="majorName">전공명</label>
                <input type="text" id="majorName" v-model="majorName" />
            </div>

            <div v-if="schoolDiv && schoolDiv !== '고등학교'" class="form-item">
                <div class="form-grid-2">
                    <div class="form-item">
                        <label for="grade">학점</label>
                        <input type="text" id="grade" v-model="grade" class="small-input" 
                        @input="$event.target.value = $event.target.value.replace(/[^0-9.]/g, '')"/>
                    </div>
                    <div class="form-item">
                        <label for="totalGrade">총점</label>
                        <select id="totalGrade" v-model="totalGrade" class="small-select">
                            <option disabled value="">선택</option>
                            <option value="4.5">4.5</option>
                            <option value="4.3">4.3</option>
                            <option value="4.0">4.0</option>
                            <option value="100">100</option>
                        </select>
                    </div>
                </div>
            </div>

            <!-- 다른 전공 클릭 시 -->
            <div v-if="otherMajorVisible && schoolDiv && schoolDiv !== '고등학교'" class="form-grid other-major-fields">
                <div class="form-item">
                    <label for="majorType">전공선택</label>
                    <select id="majorType" v-model="majorType">
                        <option disabled value=''>선택</option>
                        <option value="부전공">부전공</option>
                        <option value="복수전공">복수전공</option>
                        <option value="이중전공">이중전공</option>
                    </select>
                </div>

                <div class="form-item">
                    <label for="otherMajor">전공명</label>
                    <input type="text" id="otherMajor" v-model="otherMajor" />
                </div>
            </div>

            <!-- 졸업 논문/작품 클릭 시 -->
            <div v-if="graduationWorkVisible && schoolDiv && schoolDiv !== '고등학교'" class="form-item graduationWork-item">
                <label for="graduationWork">졸업 논문/작품</label>
                <textarea id="graduationWork" v-model="graduationWork" class="expandable-textarea" rows="3"></textarea>
            </div>



            <div class="form-grid other-major-fields">
                <div v-if="schoolDiv && schoolDiv !== '고등학교'" class="form-item">
                    <button class="custom-button" @click="toggleOtherMajor">
                        <span class="button-icon" :class="{ active: otherMajorVisible }"></span>
                        다른 전공
                    </button>
                </div>

                <div v-if="schoolDiv && schoolDiv !== '고등학교'" class="form-item">
                    <button class="custom-button" @click="toggleGraduationWork">
                        <span class="button-icon" :class="{ active: graduationWorkVisible }"></span>
                        졸업 논문/작품
                    </button>
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

const education = props.data || {
    schoolDiv: '',
    schoolName: null,
    graduatedAt: null,
    passedAt: null,
    graduationStatus: '',
    qualificationExam: false,
    enteredAt: null,
    transfer: false,
    grade: null,
    totalGrade: '',
    majorName: null,
    majorType: '',
    otherMajor: null,
    graduationWork: null,
    degree: ''
};

const schoolDiv = ref(education.schoolDiv);
const schoolName = ref(education.schoolName);
const graduatedAt = ref(education.graduatedAt);
const passedAt = ref(education.passAt);
const graduationStatus = ref(education.graduationStatus);
const qualificationExam = ref(education.qualificationExam);
const enteredAt = ref(education.enteredAt);
const transfer = ref(education.transfer);
const grade = ref(education.grade);
const totalGrade = ref(education.totalGrade);
const majorName = ref(education.majorName);
const majorType = ref(education.majorType);
const otherMajor = ref(education.otherMajor);
const graduationWork = ref(education.graduationWork);
const degree = ref(education.degree);
const otherMajorVisible = ref(education.otherMajor);
const graduationWorkVisible = ref(education.graduationWork);



const emit = defineEmits(['updateData']);
emit('updateData', props.data);


const updateData = () => {
  const data = {
    schoolDiv: schoolDiv.value,
    schoolName: schoolName.value,
    graduatedAt: graduatedAt.value,
    passedAt: passedAt.value,
    graduationStatus: graduationStatus.value,
    qualificationExam: qualificationExam.value,
    enteredAt: enteredAt.value,
    transfer: transfer.value,
    grade: grade.value,
    totalGrade: totalGrade.value,
    majorName: majorName.value,
    majorType: majorType.value,
    otherMajor: otherMajor.value,
    graduationWork: graduationWork.value,
    degree: degree.value
  };
  emit('updateData', data);
};

watch(
  [schoolDiv, schoolName, graduatedAt, passedAt, graduationStatus, qualificationExam, enteredAt, transfer, 
    grade, totalGrade, totalGrade, majorName, majorType, otherMajor, graduationWork, degree],
  updateData
);




const toggleQualificationExam = () => {
    qualificationExam.value = !qualificationExam.value;
    schoolName.value = '대입검정고시';
};

const toggleTransfer = () => {
    transfer.value = !transfer.value;
};

const toggleOtherMajor = () => {
    otherMajorVisible.value = !otherMajorVisible.value;
};

const toggleGraduationWork = () => {
    graduationWorkVisible.value = !graduationWorkVisible.value;
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
    grid-template-columns: repeat(5, 1fr);
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

.small-input,
.small-select {
    width: 80px;
}

.custom-checkbox-container {
    display: flex;
    align-items: center;
    cursor: pointer;
    margin-top: 20px;
}

.custom-checkbox {
    width: 24px;
    height: 24px;
    margin-right: 10px;
    background-image: url(../../assets/img/resume/check.png);
}

.custom-checkbox.checked {
    background-image: url(../../assets/img/resume/check-mark.png);
}

.custom-checkbox-container label {
    margin: 0;
}

.major-item {
    grid-column: span 2;
}

.other-major-fields {
    grid-column: span 5;
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

.graduationWork-item {
    grid-column: span 5;
}

.graduationWork-item .expandable-textarea {
    height: 40px;
}

.graduationWork-item .expandable-textarea:focus {
    height: 80px;
    border-color: #212b36;
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
    color: #212b36;
}

.custom-button .button-icon {
    width: 16px;
    height: 16px;
    margin-right: 10px;
    background-image: url(../../assets/img/resume/plus.png);
}

.custom-button .button-icon.active {
    background-image: url(../../assets/img/resume/x.png);
    width: 16px;
    height: 16px;
}

.custom-button:focus {
    outline: none;
}


</style>
