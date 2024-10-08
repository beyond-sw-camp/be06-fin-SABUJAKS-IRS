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

</style>