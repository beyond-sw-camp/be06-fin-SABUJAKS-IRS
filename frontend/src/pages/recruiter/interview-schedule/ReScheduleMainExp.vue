<script setup>
import {onMounted, ref} from 'vue';
import MainHeaderComponent from '../../../components/recruiter/MainHeaderComponent.vue';
import MainSideBarComponent from '../../../components/recruiter/MainSideBarComponent.vue';
import '@/assets/css/grid.css';
import { UseInterviewScheduleStore } from '@/stores/UseInterviewScheduleStore';
import InterviewScheduleMain from "../../../components/recruiter/InterviewScheduleMain.vue";
import ReScheduleList from "@/components/recruiter/ReScheduleList.vue";
import ReScheduleDetail from "@/components/recruiter/ReScheduleDetail.vue";


const isInterviewScheduleMain = ref(true);
const isInterviewScheduleList = ref(false);
const isReScheduleDetail = ref(false);

const interviewScheduleStore = UseInterviewScheduleStore(); // Store 인스턴스
const careerBase = ref("전체");
const isModalOpen = ref(false);
const modalTitle = ref('');
const announcements = ref([]);
const totalAnnouncements = ref(0);
const interviewSchedules = ref([]);
const totalReSchedules = ref(0);
const announcementIdxInfo = ref(0);
const announcementUuidInfo = ref("");
const reSchedules = ref([]);
const interviewScheduleDetail = ref([]);


const reqData = ref({});
const pageNum = ref(1);

onMounted(async () => {
  announcements.value = await interviewScheduleStore.readAllAnnouncement(careerBase.value, pageNum.value);
  totalAnnouncements.value = await interviewScheduleStore.getTotalAnnouncement(careerBase.value);

  console.log(announcements);
  console.log(totalAnnouncements.value);
})

const loadAnnouncementList = async (btnNum) => {
  announcements.value = await interviewScheduleStore.readAllAnnouncement(careerBase.value, btnNum);
}

const interviewScheduleLists = async (announcementIdx, announcementUuid) => {
  isInterviewScheduleList.value = true;
  isInterviewScheduleMain.value = false;

  reqData.value = {
    careerBase: careerBase.value,
    announcementIdx: announcementIdx,
  };

  reSchedules.value = await interviewScheduleStore.readAllReSchedule(reqData.value, pageNum.value);
  totalReSchedules.value = await interviewScheduleStore.getTotalReSchedule(reqData.value);

  announcementIdxInfo.value = announcementIdx;
  announcementUuidInfo.value = announcementUuid;
}

// const announcementUuid = (announcementUuid) => {
//   announcementUuidInfo.value = announcementUuid;
// }

const setModalTitle = (title) => {
  if (!isModalOpen.value) {  // 모달이 열려있지 않을 때만 실행
    modalTitle.value = title;
  }
}

// 모달 열기 함수에서 무한 호출 방지
const interviewScheduleInfo = async (interviewScheduleInfo, announcementIdx, announcementUuid) => {
  isInterviewScheduleList.value = false;
  isReScheduleDetail.value = true;

  interviewSchedules.value = interviewScheduleInfo;

  interviewScheduleDetail.value = await interviewScheduleStore.readInterviewSchedule(interviewScheduleInfo.interviewScheduleRes.idx);
  announcementIdxInfo.value = announcementIdx;
  announcementUuidInfo.value = announcementUuid;
};

const updateData = async (updateData, updateInfo, pageUpdateData) => {
  const updateResult = await interviewScheduleStore.updateInterviewSchedule(updateInfo);
  const createResult = await interviewScheduleStore.createInterviewSchedule(updateData);

  if (createResult && updateResult) {
    alert('성공했습니다!');

    console.log(pageUpdateData.announcementIdx);
    console.log(pageUpdateData.announcementUuid);
    window.scrollTo({ top: 0});
    await interviewScheduleLists(pageUpdateData.announcementIdx);
  }
}
</script>


<template>
  <MainHeaderComponent/>
  <div class="container">
    <MainSideBarComponent/>
    <!-- InterviewScheduleMainNew에서 이벤트를 받아 모달을 제어 -->
    <InterviewScheduleMain
        v-if="isInterviewScheduleMain"
        @interviewScheduleList="interviewScheduleLists"
        @loadAnnouncementList="loadAnnouncementList"
        :title="careerBase"
        :announcements="announcements"
        :totalAnnouncements="totalAnnouncements">
    </InterviewScheduleMain>

    <ReScheduleList
        v-if="isInterviewScheduleList"
        @interviewScheduleInfo="interviewScheduleInfo"
        :title="'일정 조율 신청 내역'"
        :titleModal="setModalTitle"
        :reSchedules="reSchedules"
        :totalReSchedules="totalReSchedules"
        :announcementIdx="announcementIdxInfo"
        :announcementUuid="announcementUuidInfo">
    </ReScheduleList>

    <ReScheduleDetail
        v-if="isReScheduleDetail"
        @updateData="updateData"
        :title="'면접일정 세부내역'"
        :reScheduleInfo="interviewSchedules"
        :interviewScheduleDetail="interviewScheduleDetail"
        :announcementIdx="announcementIdxInfo"
        :announcementUuid="announcementUuidInfo">
    </ReScheduleDetail>
  </div>
</template>



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