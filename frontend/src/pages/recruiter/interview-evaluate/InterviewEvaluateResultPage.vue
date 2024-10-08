<template>
    <div>
      <MainHeaderComponent></MainHeaderComponent>
      <div class="">
        <MainSideBarComponent></MainSideBarComponent>
        <div class="container">
          <div id="content">
      <!-- props.title을 직접 사용합니다 -->
      <h1>면접 평가 조회</h1>
      <table class="review-table">
        <tbody>
        <tr>
          <th>번호</th>
          <th>공고기간</th>
          <th>공고명</th>
          <th>1차 조회</th>
          <th>2차 조회</th>
        </tr>
        <!--      <tr @click="handleRowClick('경력')">-->
        <tr v-for="(announcement, index) in announcements" :key="announcement.idx">
          <td>{{ startNumberForPage - index }}</td>
          <td>{{ formatDate(announcement.announcementStart) }} - {{ formatDate(announcement.announcementEnd) }}</td>
          <td>{{ announcement.title }}</td>
          <td><button @click="handleSearchButton(announcement.idx, 1)" class="searchbtn">1차 조회</button></td>
          <td><button @click="handleSearchButton(announcement.idx, 2)" class="searchbtn">2차 조회</button></td>          
        </tr>
        </tbody>
      </table>
  
      <div id="size-buttons">
        <button v-for="page in totalPages" :key="page" @click="handlePageClick(page)">{{ page }}</button>
      </div>
    </div>
        </div>
      </div>
    </div>
</template>
<script setup>
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import { computed, onMounted, ref } from "vue";
import MainHeaderComponent from "../../../components/recruiter/MainHeaderComponent.vue";
import { UseInterviewScheduleStore } from '@/stores/UseInterviewScheduleStore';
import { useRouter } from "vue-router";
  
const interviewScheduleStore = UseInterviewScheduleStore();
const router = useRouter();

const totalAnnouncements = ref(0);
const announcements = ref([]);
const careerBase = ref('전체');
const pageNum = ref(1);
const currentPage = ref(1);
const postsPerPage = 10; 

onMounted(async () => {
    announcements.value = await interviewScheduleStore.readAllAnnouncement(careerBase.value, pageNum.value);
    totalAnnouncements.value = await interviewScheduleStore.getTotalAnnouncement(careerBase.value);
})
const startNumberForPage = computed(() => {
  return totalAnnouncements.value - (currentPage.value - 1) * postsPerPage;
});
const totalPages = computed(() => {
  return totalAnnouncements.value ? Math.ceil(totalAnnouncements.value / postsPerPage) : 0;
});

const handlePageClick = async(pageNumber) => {
  currentPage.value = pageNumber; // 페이지 번호 업데이트
  announcements.value = await interviewScheduleStore.readAllAnnouncement(careerBase.value, pageNumber);
};

const formatDate = (datetime) => {
  const date = new Date(datetime);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  return `${year}-${month}-${day}`;
}
const handleSearchButton = async (announcementIdx, interviewNum) => {
    router.push(`/recruiter/interview-evaluate/result/${announcementIdx}/${interviewNum}`);
}

</script> 
<style scoped>
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
    