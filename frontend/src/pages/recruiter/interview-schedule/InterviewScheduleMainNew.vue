<script setup>
import {onMounted, ref, watch} from 'vue';
import MainHeaderComponent from '../../../components/recruiter/MainHeaderComponent.vue';
import MainSideBarComponent from '../../../components/recruiter/MainSideBarComponent.vue';
import '@/assets/css/grid.css';
import {UseInterviewScheduleStore} from '@/stores/UseInterviewScheduleStore';
import InterviewScheduleMain from "../../../components/recruiter/InterviewScheduleMain.vue";
import InterviewScheduleList from "@/components/recruiter/InterviewScheduleList.vue";

const isInterviewScheduleMain = ref(true);
const isInterviewScheduleList = ref(false);

const interviewScheduleStore = UseInterviewScheduleStore(); // Store 인스턴스
const careerBase = ref('신입');
const isModalOpen = ref(false);
const modalTitle = ref('');
const participantEmail = ref('');
const selectedEmails = ref([]);
const selectedFilters = ref([]);
const interviewers = ref([]);
const selectedInterviewers = ref([]);
const interviewDate = ref('');
const startTime = ref('');
const endTime = ref('');
const selectedIdxs = ref([]);
const teamList = [
  {name: '1팀', idx: 1},
  {name: '2팀', idx: 2},
  {name: '3팀', idx: 3},
  {name: '4팀', idx: 4},
  {name: '5팀', idx: 5}
];
const team = ref(''); // 선택된 팀의 Idx 값을 저장
const startTimeOptions = ref([]);
const endTimeOptions = ref([]);
const bookedTimes = ref([]);
const showCalendar = ref(true); // 캘린더 기본으로 표시
const showInterviewerList = ref(false); // 후보자 목록은 기본적으로 숨김
const announcements = ref([]);
const totalAnnouncements = ref(0);
const interviewSchedules = ref([]);
const totalInterviewSchedules = ref(0);
const announcementIdxInfo = ref(0);
const announcementUuidInfo = ref("");
const interviewType = ref(''); // 선택된 면접 유형 (대면 또는 온라인)
const interviewNum = ref(0); // 인터뷰 차수
const reqData = ref({});
const uuidData = ref({});
const pageNum = ref(1);

onMounted(async () => {
  announcements.value = await interviewScheduleStore.readAllAnnouncement(careerBase.value, pageNum.value);
  totalAnnouncements.value = await interviewScheduleStore.getTotalAnnouncement(careerBase.value);

  console.log(totalAnnouncements.value);
  console.log(announcements);
})

const loadAnnouncementList = async (btnNum) => {
  announcements.value = await interviewScheduleStore.readAllAnnouncement(careerBase.value, btnNum);
}

const handleCheckboxChange = (type) => {
  if (interviewType.value === type) {
    interviewType.value = ''; // 이미 선택된 타입을 클릭했을 경우 해제
  } else {
    interviewType.value = type; // 새 타입을 선택
  }
};

const handleInterviewNumCheckboxChange = (num) => {
  if (interviewNum.value === num) {
    interviewNum.value = 0; // 이미 선택된 경우 해제
  } else {
    interviewNum.value = num; // 새 타입 선택
  }
};


const interviewScheduleLists = async (announcementIdx, announcementUuid) => {
  isInterviewScheduleList.value = true;
  isInterviewScheduleMain.value = false;

  reqData.value = {
    careerBase: careerBase.value,
    announcementIdx: announcementIdx,
  };

  interviewSchedules.value = await interviewScheduleStore.readAllInterviewSchedule(reqData.value, pageNum.value);
  totalInterviewSchedules.value = await interviewScheduleStore.getTotalInterviewSchedule(reqData.value);

  console.log(totalInterviewSchedules.value);
  announcementIdxInfo.value = announcementIdx;
  announcementUuidInfo.value = announcementUuid;
}

// defineExpose로 reqData를 노출
// eslint-disable-next-line no-undef
defineExpose({
  reqData,
});

const loadInterviewScheduleList = async (btnNumValue) => {
  const response = await interviewScheduleStore.readAllInterviewSchedule(reqData.value, btnNumValue);
  interviewSchedules.value = response; // 데이터를 가져온 후 interviewSchedules에 값을 할당
};

const createVideoInterview = async (interviewScheduleUuid, interviewScheduleInfo) => {
  uuidData.value = {
    announceUUID: announcementUuidInfo.value,
    params: {customSessionId: interviewScheduleUuid},
    interviewScheduleInfo: interviewScheduleInfo
  }
  const response = await interviewScheduleStore.createVideoInterview(uuidData.value);
  if (response !== 0 && response !== undefined) {
    alert("면접방이 생성되었습니다.")
  }
}

const setModalTitle = (title) => {
  if (!isModalOpen.value) {  // 모달이 열려있지 않을 때만 실행
    modalTitle.value = title;
  }
}

const openModal = async () => {
  if (!isModalOpen.value) {  // 모달이 열려있지 않을 때만 실행
    isModalOpen.value = true;

    const response = await interviewScheduleStore.getSeeker(announcementIdxInfo.value);

    // response가 배열일 경우
    if (response !== 0 && response !== undefined) {
      response.forEach((seeker) => {
        interviewers.value.push({name: seeker.name, idx: seeker.idx});
      });
    }

  }
};

const closeModal = () => {
  isModalOpen.value = false;
  resetModal();
};

const resetModal = () => {
  participantEmail.value = '';
  selectedEmails.value = [];
  selectedFilters.value = [];
  interviewDate.value = '';
  startTime.value = '';
  endTime.value = '';
  team.value = '';
  selectedInterviewers.value = [];
  showCalendar.value = true; // 모달을 닫을 때 캘린더가 다시 보이게 설정
  showInterviewerList.value = false; // 모달을 닫을 때 후보자 목록은 숨김
};

const addParticipantEmail = () => {
  const email = participantEmail.value.trim();
  if (email && !selectedEmails.value.includes(email)) {
    selectedEmails.value.push(email);
    participantEmail.value = '';
  }
};

const removeEmail = (email) => {
  selectedEmails.value = selectedEmails.value.filter(item => item !== email);
};

const addEmail = () => {
  // 캘린더를 숨기고 후보자 목록을 보여줌
  showCalendar.value = !showCalendar.value;
  if (showCalendar.value === false) {
    showInterviewerList.value = true;
  } else {
    showInterviewerList.value = false;
  }
};

const selectInterviewers = () => {

  selectedIdxs.value = interviewers.value
      .filter(interviewer => selectedInterviewers.value.includes(interviewer.name))
      .map(interviewer => interviewer.idx);

  // 후보자 선택 후 캘린더를 다시 보여줌
  selectedFilters.value = [...selectedInterviewers.value]; // 선택한 면접자 필터 업데이트
  console.log(selectedIdxs.value);
  showInterviewerList.value = false;
  showCalendar.value = true; // 캘린더 다시 표시
};

// 선택된 필터 삭제
const removeFilter = (filter) => {
  selectedFilters.value = selectedFilters.value.filter(item => item !== filter);
  selectedInterviewers.value = selectedInterviewers.value.filter(item => item !== filter); // 체크박스 해제
};

const submitForm = async () => {
  // const selectedSpanValues = selectedFilters.value;
  if (confirm("면접 일정을 등록하시겠습니까?")) {
    const selectedIdxValues = selectedIdxs.value;
    const participantEmails = selectedEmails.value// 참가자 이메일
    const selectedDate = interviewDate.value;
    const selectedStartTime = startTime.value;
    const selectedEndTime = endTime.value;
    const selectedType = interviewType.value;
    const selectedNum = interviewNum.value;
    const selectedTeamIdx = team.value;

    // 데이터 객체 생성
    const interviewData = {
      seekerList: selectedIdxValues,
      estimatorList: participantEmails,
      isOnline: selectedType,
      interviewDate: selectedDate,
      interviewStart: selectedStartTime,
      interviewEnd: selectedEndTime,
      careerBase: "신입",
      teamIdx: selectedTeamIdx,
      interviewNum: selectedNum,
      announcementIdx: announcementIdxInfo.value
    };

    // Store의 createInterviewSchedule 함수 호출
    await interviewScheduleStore.createInterviewSchedule(interviewData)
        .then(() => {
          if (confirm('면접 일정이 성공적으로 등록되었습니다.')) {
            // 면접 일정 리스트 업데이트
            closeModal();
            interviewScheduleLists(announcementIdxInfo, announcementUuidInfo);
          }
        })
        .catch((error) => {
          console.error('면접 일정 등록 중 오류 발생:', error);
        });

  }
};

// 전체 시간대를 생성하는 함수 (30분 단위)
const generateTimeSlots = (start, end, interval) => {
  const times = [];
  let current = new Date(`1970-01-01T${start}:00`);
  const endTime = new Date(`1970-01-01T${end}:00`);

  console.log(current);
  console.log(endTime);

  while (current <= endTime) {
    times.push(current.toTimeString().slice(0, 5)); // HH:mm 형식으로 변환
    current.setMinutes(current.getMinutes() + interval);
  }

  console.log("times: ", times);

  return times;
};
//
// // 예약된 시간에 따라 시작 시간 옵션 설정
// const setStartTimeOptions = () => {
//   if (bookedTimes.value.length === 0) {
//     startTimeOptions.value = generateTimeSlots('09:00', '18:00', 30); // 전체 시간대
//
//     console.log("startTimeOptions-length==0: ", startTimeOptions.value);
//   } else {
//     // 예약된 시간대를 제외한 시작 시간 옵션 생성
//     const allTimeSlots = generateTimeSlots('09:00', '18:00', 30);
//     const bookedStartTimes = bookedTimes.value.map(time => time.interviewStart);
//     startTimeOptions.value = allTimeSlots.filter(time => !bookedStartTimes.includes(time));
//
//     console.log("startTimeOptions: ", startTimeOptions.value);
//   }
// };
// 예약된 시간에 따라 시작 시간 옵션 설정
const setStartTimeOptions = () => {
  if (bookedTimes.value.length === 0) {
    startTimeOptions.value = generateTimeSlots('09:00', '24:00', 30); // 전체 시간대
  } else {
    // 예약된 시간대 제외한 시작 시간 옵션 생성
    const allTimeSlots = generateTimeSlots('09:00', '24:00', 30);

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

  console.log("startTimeOptions: ", startTimeOptions.value);
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
      bookedTimes.value = await interviewScheduleStore.getAvailableTime(newDate, newTeam, announcementIdxInfo.value);
      console.log(bookedTimes.value);
      setStartTimeOptions(); // 예약된 시간대에 따라 시작 시간 옵션 설정
    } catch (error) {
      console.error('시간 정보 가져오기 실패:', error);
    }
  }
});
</script>


<template>
  <MainHeaderComponent/>
  <div class="container">
    <MainSideBarComponent/>

    <InterviewScheduleMain
        v-if="isInterviewScheduleMain"
        @interviewScheduleList="interviewScheduleLists"
        @loadAnnouncementList="loadAnnouncementList"
        :title="careerBase"
        :announcements="announcements"
        :totalAnnouncements="totalAnnouncements">
    </InterviewScheduleMain>

    <InterviewScheduleList
        v-if="isInterviewScheduleList"
        @openModal="openModal"
        @createVideoInterview="createVideoInterview"
        @loadInterviewScheduleList="loadInterviewScheduleList"
        :title="'면접일정'"
        :titleModal="setModalTitle"
        :interviewSchedules="interviewSchedules"
        :totalInterviewSchedules="totalInterviewSchedules"
        :announcementIdx="announcementIdxInfo"
        :announcementUuid="announcementUuidInfo">
    </InterviewScheduleList>


    <div v-if="isModalOpen" id="myModal" class="modal">
      <div class="modal-content" id="draggableModal">
        <span class="close" @click="closeModal">&times;</span>
        <h2>{{ modalTitle }}</h2>
        <div class="col-12 row mt-50 modal-section">
          <div class="modal-left col-5 margin-auto">
            <div class="form-group col-12 row">
              <div class="col-11">
                <label for="applicant">후보자 <span class="required">*</span></label>
                <input type="text" id="applicant" placeholder="+ 버튼을 눌러 후보자를 추가해주세요." disabled>
              </div>
              <div class="col-1 add-button-section">
                <button class="add-button" @click="addEmail">+</button>
              </div>
            </div>
            <div id="selected-filters-list">
              <span v-for="filter in selectedFilters" :key="filter" @click="removeFilter(filter)">
                  {{ filter }} ✕
              </span>
            </div>
            <div class="form-group col-12 row">
              <div class="col-11">
                <label for="participants">면접관 추가</label>
                <input type="text" id="participants" placeholder="이메일을 입력 후 + 버튼을 눌러주세요." v-model="participantEmail">
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
            <!--            <div class='demo-app calendar' v-if="showCalendar">-->
            <!--              <div class='demo-app-main'>-->
            <!--                <FullCalendar-->
            <!--                    class='demo-app-calendar'-->
            <!--                    :options='calendarOptions'-->
            <!--                >-->
            <!--                </FullCalendar>-->
            <!--              </div>-->
            <!--            </div>-->
            <!-- 후보자 목록 -->
            <div v-if="showInterviewerList" id="interviewer">
              <div id="interviewers-list">
                <h3>면접자 목록</h3>
                <form id="nameForm">
                  <label v-for="interviewer in interviewers" :key="interviewer.idx">
                    <input type="checkbox" :value="interviewer.name" v-model="selectedInterviewers"> {{
                      interviewer.name
                    }}
                    <input type="hidden" :value="interviewer.idx">
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
<!--<script>-->
<!--import {defineComponent, getCurrentInstance, watch} from 'vue'-->
<!--import FullCalendar from '@fullcalendar/vue3'-->
<!--import dayGridPlugin from '@fullcalendar/daygrid'-->
<!--import timeGridPlugin from '@fullcalendar/timegrid'-->
<!--import interactionPlugin from '@fullcalendar/interaction'-->
<!--import {createEventId} from './event-utils'-->

<!--export default defineComponent({-->
<!--  setup() {-->
<!--    const instance = getCurrentInstance();-->
<!--    const reqData = instance?.setupState?.reqData;-->
<!--    const isModalOpen = instance?.setupState?.isModalOpen;-->

<!--    // isModalOpen이 변경될 때마다 실행-->
<!--    watch(isModalOpen, (newVal) => {-->
<!--      if (newVal) {-->
<!--        // 모달이 열릴 때 reqData 값을 출력-->
<!--        console.log("reqData:", reqData.value);-->
<!--      }-->
<!--    });-->

<!--    return {-->
<!--      reqData,-->
<!--      isModalOpen,-->
<!--    };-->
<!--  },-->
<!--  components: {-->
<!--    FullCalendar,-->
<!--  },-->
<!--  data() {-->
<!--    return {-->
<!--      calendarOptions: {-->
<!--        plugins: [-->
<!--          dayGridPlugin,-->
<!--          timeGridPlugin,-->
<!--          interactionPlugin // needed for dateClick-->
<!--        ],-->
<!--        locale: 'ko',-->
<!--        headerToolbar: {-->
<!--          left: 'prev,next today',-->
<!--          center: 'title',-->
<!--          right: 'dayGridMonth,dayGridWeek,dayGridDay'-->
<!--        },-->
<!--        events: [],-->
<!--        initialView: 'dayGridMonth',-->
<!--        editable: true,-->
<!--        selectable: true,-->
<!--        selectMirror: true,-->
<!--        dayMaxEvents: true,-->
<!--        weekends: true,-->
<!--        select: this.handleDateSelect,-->
<!--        eventClick: this.handleEventClick,-->
<!--        eventsSet: this.handleEvents-->
<!--      },-->
<!--      currentEvents: [],-->
<!--    }-->
<!--  },-->
<!--  mounted() {-->
<!--    this.fetchInterviewSchedules();-->
<!--  },-->
<!--  methods: {-->
<!--    async fetchInterviewSchedules() {-->
<!--      const interviewScheduleStore = UseInterviewScheduleStore(); // 스토어 인스턴스 생성-->
<!--      try {-->
<!--        const reqData = this.$setup.reqData;-->
<!--        console.log("reqdata: "+ reqData);-->
<!--        const schedules = await interviewScheduleStore.readAllCalendarInterviewSchedule(reqData.value);-->

<!--        console.log("@@@@"+schedules);-->

<!--        if (!Array.isArray(schedules)) {-->
<!--          console.error('Received schedules is not an array:', schedules);-->
<!--          return;-->
<!--        }-->

<!--        this.currentEvents = [];-->

<!--        for (const schedule of schedules) {-->
<!--          this.currentEvents.push({-->
<!--            title: schedule.isOnline ? '온라인 면접' : '대면 면접',-->
<!--            start: schedule.interviewDate + "T" + schedule.interviewStart + ":00",-->
<!--            end: schedule.interviewDate + "T" + schedule.interviewEnd + ":00",-->
<!--            allDay: false-->
<!--          });-->
<!--        }-->

<!--        this.calendarOptions.events = this.currentEvents;-->

<!--      } catch (error) {-->
<!--        console.error('Error fetching interview schedules:', error);-->
<!--      }-->
<!--    },-->

<!--    handleWeekendsToggle() {-->
<!--      this.calendarOptions.weekends = !this.calendarOptions.weekends;-->
<!--    },-->
<!--    handleDateSelect(events) {-->
<!--      // let title = prompt('새로운 일정을 등록해주세요.')-->
<!--      // let calendarApi = selectInfo.view.calendar-->
<!--      //-->
<!--      // calendarApi.unselect()-->
<!--      //-->
<!--      // if (title) {-->
<!--      //   calendarApi.addEvent({-->
<!--      //     id: createEventId(),-->
<!--      //     title,-->
<!--      //     start: selectInfo.startStr,-->
<!--      //     end: selectInfo.endStr,-->
<!--      //     allDay: selectInfo.allDay-->
<!--      //   })-->
<!--      // }-->
<!--      this.currentEvents = events-->
<!--    },-->

<!--    handleEvents(events) {-->
<!--      this.currentEvents = events-->
<!--    },-->
<!--  }-->
<!--})-->
<!--</script>-->


<style scoped>

.modal {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 9;
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 80%;
  height: 80%;
}

.modal-section {
  height: 90%;
  overflow-y: scroll;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
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

</style>