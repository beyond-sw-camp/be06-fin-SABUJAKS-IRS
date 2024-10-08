<template>
<div>
  <MainHeaderComponent></MainHeaderComponent>
  <div class="container">
    <MainSideBarComponent></MainSideBarComponent>
    <div class="content">
      <h1>면접 평가 조회</h1>
      <table class="announcement-table">
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
      <div class="pagination">
        <button 
          v-for="page in totalPages" 
          :key="page" 
          @click="handlePageClick(page)"
          :class="{ active: currentPage === page }"
          >{{ page }}</button>
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
.container {
  width: 80%;
  margin: 0 auto;
  margin-left: 200px;
  padding: 150px 0;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

.content{
  flex: 1;
  margin-left: 200px;
  padding: 0 0 150px 0;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

.content h1{
  font-size: 24px;
  margin: 50px 0;
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
    border-radius: 5px;
}

.pagination button.active {
    background-color: #212b36;
    color: white;
}

.announcement-table th:nth-child(1) { /* 첫 번째 열 (번호) */
  width: 10%; /* 비율 조정 */
}

.announcement-table th:nth-child(2) { 
  width: 40%;
}


table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 40px;
    border: 1px solid #ddd;
}

tr {
    display: table-row;
    vertical-align: inherit;
    unicode-bidi: isolate;
    border-color: inherit;
}

td {
    border: 1px solid #ddd;
    padding: 25px;
    text-align: center;
}

th {
    background-color: #f1f1f1;
    padding: 25px;
    text-align: center;
    border: 1px solid #ddd;
    display: table-cell;
    vertical-align: inherit;
    font-weight: bold;
    text-align: -internal-center;
    unicode-bidi: isolate;
}

.searchbtn {
    width: fit-content;
    background-color: #212b36;
    border: 1px solid #ddd;
    border-radius: 10px;
    padding: 10px;
    font-size: 0.8rem;
    font-weight: bold;
    color: white;
    cursor: pointer;
    height: 100%;
    transition: background-color 0.3s;
    display: inline-block;
    text-decoration: none;
}

.searchbtn:hover {
    background-color: #90959a;
}
</style>
    