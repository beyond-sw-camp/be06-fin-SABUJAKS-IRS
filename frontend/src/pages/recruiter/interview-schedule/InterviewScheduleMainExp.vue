<script setup>
import { ref } from 'vue';
import MainHeaderComponent from '../../../components/recruiter/MainHeaderComponent.vue';
import MainSideBarComponent from '../../../components/recruiter/MainSideBarComponent.vue';
import '@/assets/css/grid.css';
import { UseInterviewScheduleStore } from '@/stores/UseInterviewScheduleStore';
import InterviewScheduleMain from "../../../components/recruiter/InterviewScheduleMain.vue";
import InterviewScheduleList from "@/components/recruiter/InterviewScheduleList.vue";

const isInterviewScheduleMain = ref(true);
const isInterviewScheduleList = ref(false);

const interviewScheduleStore = UseInterviewScheduleStore(); // Store 인스턴스
const careerBase = ref('경력');
const isModalOpen = ref(false);
const modalTitle = ref('');
const participantEmail = ref('');
const selectedEmails = ref([]);
const selectedFilters = ref([]);
const interviewers = ref(['서시현', '구은주', '박종성', '서재은', '한별', '곽동현', '유송연', '강태성', '오규림', '송나경']);
const selectedInterviewers = ref([]);
const interviewDate = ref('');
const startTime = ref('');
const endTime = ref('');
const teamList = [
  { name: '1팀', idx: 1 },
  { name: '2팀', idx: 2 },
  { name: '3팀', idx: 3 },
  { name: '4팀', idx: 4 },
  { name: '5팀', idx: 5 }
];
const team = ref(''); // 선택된 팀의 Idx 값을 저장
const timeOptions = ['09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00'];
const showCalendar = ref(true); // 캘린더 기본으로 표시
const showInterviewerList = ref(false); // 후보자 목록은 기본적으로 숨김

const interviewType = ref(''); // 선택된 면접 유형 (대면 또는 온라인)

const handleCheckboxChange = (type) => {
  if (interviewType.value === type) {
    interviewType.value = ''; // 이미 선택된 타입을 클릭했을 경우 해제
  } else {
    interviewType.value = type; // 새 타입을 선택
  }
};

const interviewScheduleLists = (announceIdx) => {
  isInterviewScheduleList.value = true;
  isInterviewScheduleMain.value = false;
}

const createVideoInterview = () => {

}

const setModalTitle = (title) => {
  if (!isModalOpen.value) {  // 모달이 열려있지 않을 때만 실행
    modalTitle.value = title;
  }
}

// 모달 열기 함수에서 무한 호출 방지
const openModal = () => {
  if (!isModalOpen.value) {  // 모달이 열려있지 않을 때만 실행
    isModalOpen.value = true;
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
  // 후보자 선택 후 캘린더를 다시 보여줌
  selectedFilters.value = [...selectedInterviewers.value]; // 선택한 면접자 필터 업데이트
  showInterviewerList.value = false;
  showCalendar.value = true; // 캘린더 다시 표시
};

// 선택된 필터 삭제
const removeFilter = (filter) => {
  selectedFilters.value = selectedFilters.value.filter(item => item !== filter);
  selectedInterviewers.value = selectedInterviewers.value.filter(item => item !== filter); // 체크박스 해제
};

const submitForm = () => {
  // const selectedSpanValues = selectedFilters.value;
  const selectedSpanValues = [1, 2];
  const participantEmails = selectedEmails.value// 참가자 이메일
  const selectedDate = interviewDate.value;
  const selectedStartTime = startTime.value;
  const selectedEndTime = endTime.value;
  const selectedType = interviewType.value;
  const selectedTeamIdx = team.value;

  alert(`
    선택된 필터: ${selectedSpanValues}
    면접관 이메일: ${participantEmails}
    날짜: ${selectedDate}
    방식: ${selectedType}
    시작 시간: ${selectedStartTime}
    종료 시간: ${selectedEndTime}
    팀 배정: ${selectedTeamIdx}
  `);

  // 데이터 객체 생성
  const interviewData = {
    seekerList: selectedSpanValues,
    interviewerList: participantEmails,
    isOnline: selectedType,
    interviewDate: selectedDate,
    interviewStart: selectedStartTime,
    interviewEnd: selectedEndTime,
    careerBase: "경력",
    teamIdx: selectedTeamIdx,
  };

  console.log("Selected Team: ", selectedTeamIdx);

  // Store의 createInterviewSchedule 함수 호출
  interviewScheduleStore.createInterviewSchedule(interviewData)
      .then(() => {
        alert('면접 일정이 성공적으로 등록되었습니다.');
      })
      .catch((error) => {
        console.error('면접 일정 등록 중 오류 발생:', error);
      });
};
</script>


<template>
  <MainHeaderComponent/>
  <div class="container">
    <MainSideBarComponent/>
    <!-- InterviewScheduleMainNew에서 이벤트를 받아 모달을 제어 -->
    <InterviewScheduleMain
        v-if="isInterviewScheduleMain"
        @interviewScheduleList="interviewScheduleLists"
        :title="careerBase">
    </InterviewScheduleMain>
    <InterviewScheduleList
        v-if="isInterviewScheduleList"
        @openModal="openModal"
        @createVideoInterview="createVideoInterview"
        :title="'면접일정'"
        :titleModal="setModalTitle">
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
                <input type="text" id="applicant" placeholder="후보자를 추가해주세요." disabled>
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
            <div class='demo-app calendar' v-if="showCalendar">
              <div class='demo-app-main'>
                <FullCalendar
                    class='demo-app-calendar'
                    :options='calendarOptions'
                >
                </FullCalendar>
              </div>
            </div>
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

<!--<script>-->
<!--import {defineComponent} from 'vue'-->
<!--import FullCalendar from '@fullcalendar/vue3'-->
<!--import dayGridPlugin from '@fullcalendar/daygrid'-->
<!--import timeGridPlugin from '@fullcalendar/timegrid'-->
<!--import interactionPlugin from '@fullcalendar/interaction'-->
<!--import {createEventId} from './event-utils'-->

<!--export default defineComponent({-->
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
<!--        // initialEvents: INITIAL_EVENTS, // alternatively, use the `events` setting to fetch from a feed-->
<!--        editable: true,-->
<!--        selectable: true,-->
<!--        selectMirror: true,-->
<!--        dayMaxEvents: true,-->
<!--        weekends: true,-->
<!--        select: this.handleDateSelect,-->
<!--        eventClick: this.handleEventClick,-->
<!--        eventsSet: this.handleEvents-->
<!--        /* you can update a remote database when these fire:-->
<!--        eventAdd:-->
<!--        eventChange:-->
<!--        eventRemove:-->
<!--        */-->
<!--      },-->
<!--      currentEvents: [],-->
<!--    }-->
<!--  },-->
<!--  methods: {-->
<!--    handleWeekendsToggle() {-->
<!--      this.calendarOptions.weekends = !this.calendarOptions.weekends // update a property-->
<!--    },-->
<!--    handleDateSelect(selectInfo) {-->
<!--      let title = prompt('새로운 일정을 등록해주세요.')-->
<!--      let calendarApi = selectInfo.view.calendar-->

<!--      calendarApi.unselect() // clear date selection-->

<!--      if (title) {-->
<!--        calendarApi.addEvent({-->
<!--          id: createEventId(),-->
<!--          title,-->
<!--          start: selectInfo.startStr,-->
<!--          end: selectInfo.endStr,-->
<!--          allDay: selectInfo.allDay-->
<!--        })-->
<!--      }-->
<!--    },-->

<!--    handleEvents(events) {-->
<!--      this.currentEvents = events-->
<!--    },-->
<!--  }-->
<!--})-->
<!--</script>-->
<script>
import { defineComponent } from 'vue'
import FullCalendar from '@fullcalendar/vue3'
import dayGridPlugin from '@fullcalendar/daygrid'
import timeGridPlugin from '@fullcalendar/timegrid'
import interactionPlugin from '@fullcalendar/interaction'
import { createEventId } from './event-utils'

export default defineComponent({
  components: {
    FullCalendar,
  },
  data() {
    return {
      calendarOptions: {
        plugins: [
          dayGridPlugin,
          timeGridPlugin,
          interactionPlugin // needed for dateClick
        ],
        locale: 'ko',
        headerToolbar: {
          left: 'prev,next today',
          center: 'title',
          right: 'dayGridMonth,dayGridWeek,dayGridDay'
        },
        events: [],
        initialView: 'dayGridMonth',
        editable: true,
        selectable: true,
        selectMirror: true,
        dayMaxEvents: true,
        weekends: true,
        select: this.handleDateSelect,
        eventClick: this.handleEventClick,
        eventsSet: this.handleEvents
      },
      currentEvents: [],
    }
  },
  mounted() {
    this.fetchInterviewSchedules();
  },
  methods: {
    async fetchInterviewSchedules() {
      const interviewScheduleStore = UseInterviewScheduleStore(); // 스토어 인스턴스 생성
      try {
        const schedules = await interviewScheduleStore.readAllExpInterviewSchedule();

        if (!Array.isArray(schedules)) {
          console.error('Received schedules is not an array:', schedules);
          return;
        }

        this.currentEvents = [];

        for (const schedule of schedules) {
          this.currentEvents.push({
            title: schedule.isOnline ? '온라인 면접' : '대면 면접',
            start: schedule.interviewDate + "T" + schedule.interviewStart + ":00",
            end: schedule.interviewDate + "T" + schedule.interviewEnd + ":00",
            allDay: false
          });
        }

        this.calendarOptions.events = this.currentEvents;

      } catch (error) {
        console.error('Error fetching interview schedules:', error);
      }
    },

    handleWeekendsToggle() {
      this.calendarOptions.weekends = !this.calendarOptions.weekends;
    },
    handleDateSelect(selectInfo) {
      let title = prompt('새로운 일정을 등록해주세요.')
      let calendarApi = selectInfo.view.calendar

      calendarApi.unselect()

      if (title) {
        calendarApi.addEvent({
          id: createEventId(),
          title,
          start: selectInfo.startStr,
          end: selectInfo.endStr,
          allDay: selectInfo.allDay
        })
      }
    },

    handleEvents(events) {
      this.currentEvents = events
    },
  }
})
</script>



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