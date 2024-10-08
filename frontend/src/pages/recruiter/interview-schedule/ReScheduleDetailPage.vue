<script setup>

import ReScheduleDetail from "@/components/recruiter/ReScheduleDetail.vue";
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import { onMounted, ref, watch } from "vue";
import { UseInterviewScheduleStore } from "@/stores/UseInterviewScheduleStore";
import {useRouter} from "vue-router";
import { useToast } from "vue-toastification";

const interviewSchedules = ref(null);  // 초기값을 null로 설정하여 로딩 상태를 구분
const interviewScheduleDetail = ref(null);  // 초기값을 null로 설정
const announcementIdxInfo = ref(0);
const announcementUuidInfo = ref("");

const router = useRouter();
const toast = useToast();
const interviewScheduleStore = UseInterviewScheduleStore();

onMounted(async () => {
  // 데이터 로딩 중
  announcementIdxInfo.value = interviewScheduleStore.announcementIdx || 0;
  announcementUuidInfo.value = interviewScheduleStore.announcementUuid || "";

  // scheduleInfo가 undefined일 가능성이 있기 때문에 기본값 설정
  interviewSchedules.value = interviewScheduleStore.scheduleInfo || {};
  console.log("Loaded interviewSchedules:", interviewSchedules.value);

  if (interviewSchedules.value.interviewScheduleRes) {
    interviewScheduleDetail.value = await interviewScheduleStore.readInterviewSchedule(interviewSchedules.value.interviewScheduleRes.idx);
    console.log("Loaded interviewScheduleDetail:", interviewScheduleDetail.value);
  }
});

// 데이터 변화 감지
watch(interviewSchedules, (newVal) => {
  console.log("Updated interviewSchedules:", newVal);
});

const updateData = async (updateData, updateInfo) => {
  const updateResult = await interviewScheduleStore.updateInterviewSchedule(updateInfo);
  const createResult = await interviewScheduleStore.createInterviewSchedule(updateData);

  if (createResult && updateResult) {
    toast.success(`일정 조율 확정에 성공했습니다.`)

    await router.push({
      path: "/recruiter/interview-schedule/reschedule",
    })
  }
}

</script>

<template>
  <MainHeaderComponent/>
  <div class="container">
    <MainSideBarComponent/>

    <!-- 데이터가 준비되었을 때만 ReScheduleDetail 렌더링 -->
    <ReScheduleDetail
        v-if="interviewSchedules && interviewScheduleDetail"
        @updateData="updateData"
        :title="'면접일정 세부내역'"
        :reScheduleInfo="interviewSchedules"
        :interviewScheduleDetail="interviewScheduleDetail"
        :announcementIdx="announcementIdxInfo"
        :announcementUuid="announcementUuidInfo">
    </ReScheduleDetail>

    <!-- 로딩 중일 때의 상태를 표시 -->
    <div v-else>
      Loading interview schedule details...
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
  padding: 10px 15px;
  margin: 0 5px;
  cursor: pointer;
}

.pagination button.active {
  background-color: #212b36;
  color: white;
}

</style>