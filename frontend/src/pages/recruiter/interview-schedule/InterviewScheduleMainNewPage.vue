<script setup>
import {onMounted, ref} from 'vue';
import MainHeaderComponent from '../../../components/recruiter/MainHeaderComponent.vue';
import MainSideBarComponent from '../../../components/recruiter/MainSideBarComponent.vue';
import '@/assets/css/grid.css';
import {UseInterviewScheduleStore} from '@/stores/UseInterviewScheduleStore';
import InterviewScheduleMain from "../../../components/recruiter/InterviewScheduleMain.vue";

const isInterviewScheduleMain = ref(true);

const interviewScheduleStore = UseInterviewScheduleStore(); // Store 인스턴스
const careerBase = ref('신입');

const announcements = ref([]);
const totalAnnouncements = ref(0);

const pageNum = ref(1);

onMounted(async () => {
  announcements.value = await interviewScheduleStore.readAllAnnouncement(careerBase.value, pageNum.value);
  totalAnnouncements.value = await interviewScheduleStore.getTotalAnnouncement(careerBase.value);
})

const loadAnnouncementList = async (btnNum) => {
  announcements.value = await interviewScheduleStore.readAllAnnouncement(careerBase.value, btnNum);
  console.log(announcements.value);
}

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
        :careerBase="careerBase"
        :totalAnnouncements="totalAnnouncements">
    </InterviewScheduleMain>
  </div>
</template>

<style scoped>

</style>