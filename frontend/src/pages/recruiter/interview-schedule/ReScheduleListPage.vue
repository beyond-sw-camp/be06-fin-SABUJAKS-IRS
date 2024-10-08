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

</style>