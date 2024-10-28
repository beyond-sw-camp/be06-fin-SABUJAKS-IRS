<script setup>
import {defineProps, defineEmits, onMounted, ref, watch} from 'vue';
import {UseInterviewScheduleStore} from "@/stores/UseInterviewScheduleStore";

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
  {name: '1팀', idx: 1},
  {name: '2팀', idx: 2},
  {name: '3팀', idx: 3},
  {name: '4팀', idx: 4},
  {name: '5팀', idx: 5}
];
const interviewers = ref([]);
const startTimeOptions = ref([]);
const endTimeOptions = ref([]);
const bookedTimes = ref([]);
const startTime = ref('');
const endTime = ref('');
const interviewNum = ref(0); // 인터뷰 차수

const participantEmail = ref('');
const selectedInterviewers = ref([]);
// const showInterviewerList = ref(true);

const interviewScheduleStore = UseInterviewScheduleStore();


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
      selectedEmails.value = props.interviewScheduleDetail.estimatorList.map(estimator => `${estimator.name}-${estimator.email}`);
    }

    interviewDate.value = props.interviewScheduleDetail.interviewDate || '';
    interviewNum.value = props.interviewScheduleDetail.interviewNum || '';
    interviewType.value = props.interviewScheduleDetail.isOnline ? '온라인' : '대면';
    team.value = props.interviewScheduleDetail.teamIdx || '';
  }
};

// const selectInterviewers = () => {
//   // 후보자 선택 후 캘린더를 다시 보여줌
//   selectedFilters.value = [...selectedInterviewers.value]; // 선택한 면접자 필터 업데이트
// };

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
    const selectedSpanValues = [props.reScheduleInfo.seekerInfoGetRes.idx];

    const participantEmails = selectedEmails.value; // 참가자 이메일
    const selectedDate = interviewDate.value;
    const selectedStartTime = startTime.value;
    const selectedEndTime = endTime.value;
    const selectedType = interviewType.value;
    const selectedNum = interviewNum.value;
    const selectedTeamIdx = team.value;

    // 데이터 객체 생성
    const interviewData = {
      seekerList: selectedSpanValues,
      estimatorList: participantEmails,
      isOnline: selectedType === '온라인',
      interviewDate: selectedDate,
      interviewStart: selectedStartTime,
      interviewEnd: selectedEndTime,
      interviewNum: selectedNum,
      careerBase: "경력",
      teamIdx: selectedTeamIdx,
      announcementIdx: props.announcementIdx
    };

    // 갱신해야할 데이터 객체
    const updateInterviewData = {
      interviewScheduleIdx: props.interviewScheduleDetail.idx,
      seekerList: selectedSpanValues,
      reScheduleIdx: props.reScheduleInfo.idx
    }

    const pageUpdateData = {
      announcementIdx: props.announcementIdx,
      announcementUuid: props.announcementUuid
    }

    // emit으로 데이터 객체를 넘겨줍니다.
    emit('updateData', interviewData, updateInterviewData, pageUpdateData);
  }
};


// 전체 시간대를 생성하는 함수 (30분 단위)
const generateTimeSlots = (start, end, interval) => {
  const times = [];
  let current = new Date(`1970-01-01T${start}:00`);
  const endTime = new Date(`1970-01-01T${end}:00`);

  while (current <= endTime) {
    times.push(current.toTimeString().slice(0, 5)); // HH:mm 형식으로 변환
    current.setMinutes(current.getMinutes() + interval);
  }

  return times;
};
// 예약된 시간에 따라 시작 시간 옵션 설정
const setStartTimeOptions = () => {
  if (bookedTimes.value.length === 0) {
    startTimeOptions.value = generateTimeSlots('09:00', '18:00', 30); // 전체 시간대
  } else {
    // 예약된 시간대 제외한 시작 시간 옵션 생성
    const allTimeSlots = generateTimeSlots('09:00', '18:00', 30);

    // 예약된 시간대의 시작과 끝 시간
    const bookedIntervals = bookedTimes.value.map(time => ({
      start: time.interviewStart,
      end: time.interviewEnd
    }));

    // 예약된 시간대와 겹치지 않는 시간대 필터링
    startTimeOptions.value = allTimeSlots.filter(time => {
      return !bookedIntervals.some(interval => {
        return time >= interval.start && time < interval.end; // 예약 시간대에 포함되지 않는지 확인
      });
    });
  }
};

// 시작 시간 선택 시 끝 시간 옵션 설정
watch(startTime, (newStartTime) => {
  if (newStartTime) {
    const startIndex = startTimeOptions.value.indexOf(newStartTime);
    const availableEndTimes = startTimeOptions.value.slice(startIndex + 1); // 선택된 시작 시간 이후의 옵션
    endTimeOptions.value = availableEndTimes;
  } else {
    endTimeOptions.value = []; // 시작 시간이 선택되지 않은 경우
  }
});

// 예약된 시간 정보를 가져오는 함수
watch([interviewDate, team], async ([newDate, newTeam]) => {
  if (newDate && newTeam) {
    try {
      bookedTimes.value = await interviewScheduleStore.getAvailableTime(newDate, newTeam, props.announcementIdx);
      setStartTimeOptions(); // 예약된 시간대에 따라 시작 시간 옵션 설정
    } catch (error) {
      console.error('시간 정보 가져오기 실패:', error);
    }
  }
});


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
        <td>{{
            props.reScheduleInfo.interviewScheduleRes ? props.reScheduleInfo.interviewScheduleRes.interviewDate : '정보 없음'
          }}
        </td>
      </tr>
      <tr>
        <td>기존면접시간</td>
        <td>{{
            props.reScheduleInfo.interviewScheduleRes ? `${props.reScheduleInfo.interviewScheduleRes.interviewStart} ~ ${props.reScheduleInfo.interviewScheduleRes.interviewEnd}` : '정보 없음'
          }}
        </td>
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
          <div class="modal-left col-10 margin-auto">
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
              <span @click="removeFilter(filter)">
                {{ props.reScheduleInfo.seekerInfoGetRes.name }} ✕
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
                <div class="form-group col-12 row">
                  <div class="form-group col-5">
                    <label for="end-time" class="subtitle">팀 <span class="required">*</span></label>
                    <select class="time-select interview-calender" v-model="team">
                      <option value="">팀을 선택하세요</option>
                      <option v-for="selectTeam in teamList" :key="selectTeam.idx" :value="selectTeam.idx">
                        {{ selectTeam.name }}
                      </option>
                    </select>
                  </div>
                  <div class="form-group col-5 ml-auto mb-0">
                    <label for="interview-type" class="subtitle">면접 차수<span class="required">*</span></label>
                    <div class="row">
                      <label class="checkbox-label">
                        <input type="checkbox" value="1" :checked="interviewNum === 1"
                               @change="handleInterviewNumCheckboxChange(1)"> 1차면접
                      </label>

                      <label class="checkbox-label ml-auto">
                        <input type="checkbox" value="2" :checked="interviewNum === 2"
                               @change="handleInterviewNumCheckboxChange(2)"> 2차면접
                      </label>
                    </div>
                  </div>
                </div>
                <div class="col-12 row">
                  <div class="form-group col-5">
                    <label for="start-time" class="subtitle">시작시간 <span class="required">*</span></label>
                    <select class="time-select interview-calender" v-model="startTime">
                      <option value="">시간을 선택하세요</option>
                      <option v-for="time in startTimeOptions" :key="time" :value="time">{{ time }}</option>
                    </select>
                  </div>
                  <div class="form-group col-5 ml-auto">
                    <label for="end-time" class="subtitle">종료시간 <span class="required">*</span></label>
                    <select class="time-select interview-calender" v-model="endTime">
                      <option value="">시간을 선택하세요</option>
                      <option v-for="time in endTimeOptions" :key="time" :value="time">{{ time }}</option>
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

          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<style scoped>
.container {
  width: 80%;
  margin: 0 auto;
}

#content {
  flex: 1;
  margin-left: 200px; /* 사이드바 너비만큼 왼쪽 여백 추가 */
  padding: 150px 0 150px 0;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

#content table {
  width: 100%; /* 테이블이 남은 공간을 여유롭게 차지하도록 */
  border-collapse: collapse;
}

#content h1 {
  font-size: 24px;
  margin: 50px 0;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 40px;
}

table, th, td {
  border: 1px solid #ddd;
}

th, td {
  padding: 25px;
  text-align: left;
}

th {
  background-color: #f1f1f1;
}

/* 공고 등록 버튼 스타일 */
.register-button {
  background-color: #212b36;
  color: white;
  border-radius: 5px;
  padding: 13px 10px;
  margin-left: auto;
  margin-bottom: 10px;
  border: none;
  cursor: pointer;
  /* font-weight: bold; */
  font-size: 16px;
  /* transition: background-color 0.3s ease; */
}

.register-button:hover {
  background-color: #37404a;
}

/* 테이블 행 hover 시 색깔 변화 */
.hoverable-row {
  transition: background-color 0.3s ease;
}

.hoverable-row:hover {
  background-color: #f6f6f6; /* 마우스 올렸을 때 약간 어둡게 변경 */
  cursor: pointer;
}

.pagination {
  display: flex;
  justify-content: center;
  margin: 20px 0;
}

.pagination button {
  background-color: #f1f1f1;
  border: none;
  border-radius: 5px;
  padding: 10px 15px;
  margin: 0 5px;
  cursor: pointer;
}

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