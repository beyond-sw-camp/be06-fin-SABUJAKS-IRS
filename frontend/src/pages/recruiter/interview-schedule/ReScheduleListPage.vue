<script setup>

import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import ReScheduleList from "@/components/recruiter/ReScheduleList.vue";
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import {onMounted, ref} from "vue";
import {UseInterviewScheduleStore} from "@/stores/UseInterviewScheduleStore";

const isModalOpen = ref(false);
const modalTitle = ref('');
const totalReSchedules = ref(0);
const reSchedules = ref([]);
// const isInterviewScheduleList = ref(true);
// const isReScheduleDetail = ref(true);
// const careerBase = ref("전체");
// const reqData = ref({});
const pageNum = ref(1);
const announcementIdxInfo = ref(0);
const announcementUuidInfo = ref("");


const interviewScheduleStore = UseInterviewScheduleStore(); // Store 인스턴스

onMounted(async () => {
  announcementIdxInfo.value = interviewScheduleStore.announcementIdx;
  announcementUuidInfo.value = interviewScheduleStore.announcementUuid;

  reSchedules.value = await interviewScheduleStore.readAllReSchedule(announcementIdxInfo.value, pageNum.value);
  totalReSchedules.value = await interviewScheduleStore.getTotalReSchedule(announcementIdxInfo.value);
})


const setModalTitle = (title) => {
  if (!isModalOpen.value) {  // 모달이 열려있지 않을 때만 실행
    modalTitle.value = title;
  }
}


</script>

<template>
  <MainHeaderComponent/>
  <div class="container">
    <MainSideBarComponent/>
    <ReScheduleList
        @interviewScheduleInfo="interviewScheduleInfo"
        :title="'일정 조율 신청 내역'"
        :titleModal="setModalTitle"
        :reSchedules="reSchedules"
        :totalReSchedules="totalReSchedules"
        :announcementIdx="announcementIdxInfo"
        :announcementUuid="announcementUuidInfo">
    </ReScheduleList>
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
  padding: 10px 15px;
  margin: 0 5px;
  cursor: pointer;
}

.pagination button.active {
  background-color: #212b36;
  color: white;
}

</style>