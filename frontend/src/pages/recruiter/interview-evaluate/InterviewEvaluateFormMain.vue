<template>
  <div>
    <MainHeaderComponent></MainHeaderComponent>
    <div class="wrapper">
      <MainSideBarComponent></MainSideBarComponent>
      <div class="container">
        <AnnouncementList
        v-if="isInterviewScheduleMain"
        @loadAnnouncementList="loadAnnouncementList"
        :title="careerBase"
        :announcements="announcements"
        :totalAnnouncements="totalAnnouncements">
        </AnnouncementList>

      </div>
    </div>
  </div>
</template>
  
  <script setup>
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import { onMounted, ref } from "vue";
import MainHeaderComponent from "../../../components/recruiter/MainHeaderComponent.vue";
import AnnouncementList from "@/components/recruiter/InterviewEvaluateFormMain.vue";
import { UseInterviewScheduleStore } from '@/stores/UseInterviewScheduleStore';


const interviewScheduleStore = UseInterviewScheduleStore(); // Store 인스턴스

const totalAnnouncements = ref(0);
const announcements = ref([]);

const careerBase = ref('전체');
const pageNum = ref(1);
const isInterviewScheduleMain = ref(true);



onMounted(async () => {
  announcements.value = await interviewScheduleStore.readAllAnnouncement(careerBase.value, pageNum.value);
  totalAnnouncements.value = await interviewScheduleStore.getTotalAnnouncement(careerBase.value);

  console.log(totalAnnouncements.value);
  console.log(announcements);
})
const loadAnnouncementList = async (btnNum) => {
  announcements.value = await interviewScheduleStore.readAllAnnouncement(careerBase.value, btnNum);
}


</script>
  
  <style>
.wrapper {
  width: 80%;
  margin: 0 auto;
}

.container {
  width: 80%;
  flex: 1;
  margin: 0 auto;
  margin-left: 200px;
  padding: 150px 0;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}
</style>
  