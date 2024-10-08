<script setup>
import {onMounted, ref} from 'vue';
import MainHeaderComponent from '../../../components/recruiter/MainHeaderComponent.vue';
import MainSideBarComponent from '../../../components/recruiter/MainSideBarComponent.vue';
import '@/assets/css/grid.css';
import {UseInterviewScheduleStore} from '@/stores/UseInterviewScheduleStore';
import InterviewScheduleMain from "../../../components/recruiter/InterviewScheduleMain.vue";


const isInterviewScheduleMain = ref(true);

const interviewScheduleStore = UseInterviewScheduleStore(); // Store 인스턴스
const careerBase = ref("전체");

const announcements = ref([]);
const totalAnnouncements = ref(0);


const pageNum = ref(1);

onMounted(async () => {
  announcements.value = await interviewScheduleStore.readAllAnnouncement(careerBase.value, pageNum.value);
  totalAnnouncements.value = await interviewScheduleStore.getTotalAnnouncement(careerBase.value);
})

const loadAnnouncementList = async (btnNum) => {
  announcements.value = await interviewScheduleStore.readAllAnnouncement(careerBase.value, btnNum);
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
        :totalAnnouncements="totalAnnouncements">
    </InterviewScheduleMain>
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