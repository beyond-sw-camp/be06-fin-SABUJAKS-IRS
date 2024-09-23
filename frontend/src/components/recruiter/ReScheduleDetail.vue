<script setup>
import {defineProps, defineEmits, onMounted, ref, watch} from 'vue';

// props를 정의합니다.
const props = defineProps({
  title: {
    type: String,
    required: false,
  },
  reScheduleInfo: {
    type: Object,
    required: false,
  },
  interviewScheduleDetail: {
    type: Object,
    required: false,
  },
  announcementIdx: {
    type: Number,
    required: false,
  },
  announcementUuid: {
    type: String,
    required: false,
  }
});
const emit = defineEmits(['updateData']);
// 선택된 필터(seeker)
const selectedFilters = ref([]);
// 선택된 이메일(estimator)
const selectedEmails = ref([]);
// 날짜, 방식, 팀, 시간대를 저장할 ref
const interviewDate = ref('');
const interviewType = ref('');
const team = ref('');
const teamList = [
  { name: '1팀', idx: 1 },
  { name: '2팀', idx: 2 },
  { name: '3팀', idx: 3 },
  { name: '4팀', idx: 4 },
  { name: '5팀', idx: 5 }
];
const interviewers = ref([]);
const timeOptions = ['09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00'];
const startTime = ref('');
const endTime = ref('');

const participantEmail = ref('');
const selectedInterviewers = ref([]);
const showInterviewerList = ref(true);


// interviewScheduleDetail에서 seekerList와 estimatorList가 있으면 미리 필터로 설정
onMounted(() => {
  // 초기값 설정
  updateData();
});

// props.interviewScheduleDetail이 변경될 때마다 호출
watch(() => props.interviewScheduleDetail, (newVal) => {
  if (newVal) {
    updateData();
  }
});

const updateData = () => {
  if (props.interviewScheduleDetail) {
    if (props.interviewScheduleDetail.seekerList) {
      selectedFilters.value = props.interviewScheduleDetail.seekerList.map(seeker => seeker.name);
      interviewers.value = props.interviewScheduleDetail.seekerList.map(seeker => seeker.name);
      selectedInterviewers.value = props.interviewScheduleDetail.seekerList.map(seeker => seeker.name);
    }

    if (props.interviewScheduleDetail.estimatorList) {
      selectedEmails.value = props.interviewScheduleDetail.estimatorList.map(estimator => estimator.email);
    }

    interviewDate.value = props.interviewScheduleDetail.interviewDate || '';
    startTime.value = props.interviewScheduleDetail.interviewStart || '';
    endTime.value = props.interviewScheduleDetail.interviewEnd || '';
    interviewType.value = props.interviewScheduleDetail.isOnline ? '온라인' : '대면';
    team.value = props.interviewScheduleDetail.teamIdx || '';
  }
};

const selectInterviewers = () => {
  // 후보자 선택 후 캘린더를 다시 보여줌
  selectedFilters.value = [...selectedInterviewers.value]; // 선택한 면접자 필터 업데이트
};

// 선택된 필터 삭제
const removeFilter = (filter) => {
  selectedFilters.value = selectedFilters.value.filter(item => item !== filter);
  selectedInterviewers.value = selectedInterviewers.value.filter(item => item !== filter); // 체크박스 해제
};

const removeEmail = (email) => {
  selectedEmails.value = selectedEmails.value.filter(item => item !== email);
};

const addParticipantEmail = () => {
  const email = participantEmail.value.trim();
  if (email && !selectedEmails.value.includes(email)) {
    selectedEmails.value.push(email);
    participantEmail.value = '';
  }
};

const submitForm = () => {
  if (confirm("면접 일정을 확정하시겠습니까?")) {
    // 선택된 seeker들의 idx 값만 저장
    // 선택된 seeker의 idx 값을 가져오기
    const selectedSpanValues = selectedFilters.value.map(filter => {
      const seeker = props.interviewScheduleDetail.seekerList.find(s => s.name === filter);
      return seeker ? seeker.idx : null; // idx가 있으면 반환, 없으면 null
    }).filter(idx => idx !== null);

    const participantEmails = selectedEmails.value; // 참가자 이메일
    const selectedDate = interviewDate.value;
    const selectedStartTime = startTime.value;
    const selectedEndTime = endTime.value;
    const selectedType = interviewType.value;
    const selectedTeamIdx = team.value;

    // 데이터 객체 생성
    const interviewData = {
      seekerList: selectedSpanValues,
      estimatorList: participantEmails,
      isOnline: selectedType === '온라인',
      interviewDate: selectedDate,
      interviewStart: selectedStartTime,
      interviewEnd: selectedEndTime,
      careerBase: "경력",
      teamIdx: selectedTeamIdx,
      announcementIdx: props.announcementIdx
    };

    // 갱신해야할 데이터 객체
    const updateInterviewData = {
      interviewScheduleIdx: props.interviewScheduleDetail.idx,
      seekerList: selectedSpanValues
    }

    const pageUpdateData = {
      announcementIdx: props.announcementIdx,
      announcementUuid: props.announcementUuid
    }

    // emit으로 데이터 객체를 넘겨줍니다.
    emit('updateData', interviewData, updateInterviewData, pageUpdateData);
  }
};


</script>

<template>
  <div id="content">
    <h1>{{ props.title }}</h1>
    <table class="review-table">
      <tbody>
      <tr>
        <th>면접 정보</th>
        <th>상세 내용</th>
      </tr>
      <tr>
        <td>기존면접일자</td>
        <td>{{ props.reScheduleInfo.interviewScheduleRes ? props.reScheduleInfo.interviewScheduleRes.interviewDate : '정보 없음' }}</td>
      </tr>
      <tr>
        <td>기존면접시간</td>
        <td>{{ props.reScheduleInfo.interviewScheduleRes ? `${props.reScheduleInfo.interviewScheduleRes.interviewStart} ~ ${props.reScheduleInfo.interviewScheduleRes.interviewEnd}` : '정보 없음' }}</td>
      </tr>
      <tr>
        <td>요청면접일자</td>
        <td>{{ `${props.reScheduleInfo.interviewStart} ~ ${props.reScheduleInfo.interviewEnd}` }}</td>
      </tr>
      <tr>
        <td>요청 면접자</td>
        <td>{{ props.reScheduleInfo.seekerInfoGetRes ? props.reScheduleInfo.seekerInfoGetRes.name : '정보 없음' }}</td>
      </tr>
      </tbody>
    </table>

    <h2>일정 조율</h2>
    <div id="myModal" class="modal">
      <div class="modal-content" id="draggableModal">
        <div class="col-12 row mt-50 modal-section">
          <div class="modal-left col-6 margin-auto">
            <div class="form-group col-12 row">
              <div class="col-11">
                <label for="applicant">후보자 <span class="required">*</span></label>
                <input type="text" id="applicant" placeholder="후보자를 추가해주세요." disabled>
              </div>
              <div class="col-1 add-button-section">
                <button class="add-button" @click="addEmail">+</button>
              </div>
            </div>
            <div id="selected-filters-list">
              <!-- 미리 생성된 seekerList 데이터를 기반으로 span 태그 생성 -->
              <span v-for="filter in selectedFilters" :key="filter" @click="removeFilter(filter)">
                {{ filter }} ✕
              </span>
            </div>
            <div class="form-group col-12 row">
              <div class="col-11">
                <label for="participants">면접 참가자</label>
                <input type="text" id="participants" placeholder="이메일을 입력해 주세요." v-model="participantEmail">
              </div>
              <div class="col-1 add-button-section">
                <button class="add-email" @click="addParticipantEmail">+</button>
              </div>
            </div>
            <div id="selected-emails-list">
              <span v-for="email in selectedEmails" :key="email" @click="removeEmail(email)">
                  {{ email }} ✕
              </span>
            </div>
            <div class="form-group">
              <div class="col-12">
                <div class="form-group col-12 row">
                  <div class="form-group col-5">
                    <label for="interview-date" class="subtitle">날짜 <span class="required">*</span></label>
                    <input type="date" id="interview-date" v-model="interviewDate">
                  </div>
                  <div class="form-group col-5 ml-auto mb-0">
                    <label for="interview-type" class="subtitle">방식 <span class="required">*</span></label>
                    <div class="row">
                      <label class="checkbox-label">
                        <input type="checkbox" value="대면" :checked="interviewType === '대면'"
                               @change="handleCheckboxChange('대면')"> 대면
                      </label>

                      <label class="checkbox-label ml-auto">
                        <input type="checkbox" value="온라인" :checked="interviewType === '온라인'"
                               @change="handleCheckboxChange('온라인')"> 온라인
                      </label>
                    </div>
                  </div>

                </div>
                <div class="form-group col-12">
                  <div class="form-group">
                    <label for="end-time" class="subtitle">팀 <span class="required">*</span></label>
                    <select class="time-select interview-calender" v-model="team">
                      <option value="">팀을 선택하세요</option>
                      <option v-for="selectTeam in teamList" :key="selectTeam.idx" :value="selectTeam.idx">
                        {{ selectTeam.name }}
                      </option>
                    </select>
                  </div>
                </div>
                <div class="col-12 row">
                  <div class="form-group col-5">
                    <label for="start-time" class="subtitle">시작시간 <span class="required">*</span></label>
                    <select class="time-select interview-calender" v-model="startTime">
                      <option value="">시간을 선택하세요</option>
                      <option v-for="time in timeOptions" :key="time" :value="time">{{ time }}</option>
                    </select>
                  </div>
                  <div class="form-group col-5 ml-auto">
                    <label for="end-time" class="subtitle">종료시간 <span class="required">*</span></label>
                    <select class="time-select interview-calender" v-model="endTime">
                      <option value="">시간을 선택하세요</option>
                      <option v-for="time in timeOptions" :key="time" :value="time">{{ time }}</option>
                    </select>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-12">
              <button class="submit-button" @click="submitForm">등록</button>
            </div>
          </div>
          <div class="modal-right col-5 margin-auto">
            <!-- 후보자 목록 -->
            <div v-if="showInterviewerList" id="interviewer">
              <div id="interviewers-list">
                <h3>면접자 목록</h3>
                <form id="nameForm">
                  <label v-for="name in interviewers" :key="name">
                    <input type="checkbox" :value="name" v-model="selectedInterviewers"> {{ name }}
                  </label><br>
                </form>
              </div>
              <div class="add-button-section">
                <button class="submit-button" @click="selectInterviewers">선택</button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>



<style scoped>
h2 {
  margin: 0 0 20px;
}

.form-group {
  margin-bottom: 25px;
}

label {
  display: block;
  margin-bottom: 15px;
  font-size: 1.1rem;
  font-weight: 600;
}

label .subtitle {
  font-weight: 400 !important;
}

input[type="text"] {
  width: calc(100% - 40px);
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.add-button, .add-email {
  padding: 10px;
  background-color: #232b16;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.required {
  color: red;
}

.calendar {
  margin-top: 20px;
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 5px;
  margin-top: 10px;
}

.calendar-grid div {
  padding: 10px;
  text-align: center;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.submit-button {
  width: 100%;
  margin-top: 20px;
  padding: 10px;
  background-color: #232b16;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.add-button-section {
  display: flex !important;
  flex-direction: column;
  justify-content: end;
}

#interview-date, .interview-calender {
  width: 100%;
  height: 30px;
  text-align: center;
  font-size: 1rem;
}

#interviewer {
  overflow: auto;
  height: 100%;
}

#interviewers-list {
  max-height: 40%;
  overflow-y: auto;
  padding: 10px;
  border: 1px solid #ccc;
}

#selected-filters-list, #selected-emails-list {
  margin-bottom: 20px;
}

#selected-filters-list span, #selected-emails-list span {
  background-color: #e0e0e0;
  padding: 5px 10px;
  margin-right: 10px;
  margin-bottom: 10px;
  border-radius: 20px;
  display: inline-block;
  font-size: 14px;
  color: #333;
  cursor: pointer;
}


.demo-app {
  display: flex;
  min-height: 100%;
  font-family: Arial, Helvetica Neue, Helvetica, sans-serif;
  font-size: 14px;
}

.demo-app-main {
  flex-grow: 1;
}

.review-table {
  width: 100%;
  border-collapse: collapse;
}

.review-table th, .review-table td {
  border: 1px solid #ccc;
  padding: 15px;
  text-align: left;
}

.review-table th {
  background-color: #f2f2f2;
}

.review-table th:nth-child(1) {
  width: 15%;
}


</style>
