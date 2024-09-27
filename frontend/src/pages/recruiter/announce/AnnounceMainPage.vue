<template>
  <MainHeaderComponent></MainHeaderComponent>
  <div class="container">
    <MainSideBarComponent></MainSideBarComponent>
    <div id="content">
      <h1>공고 관리</h1>
      <table>
        <thead>
          <tr>
            <th>번호</th>
            <th>공고 기간</th>
            <th>공고명</th>
            <th>신입/경력</th>
            <th>지원자수</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(announcement, index) in announcements" :key="announcement.announcementIdx">
            <td>{{ index + 1 }}</td>
            <td>{{ formatDate(announcement.announcementStart) }} - {{ formatDate(announcement.announcementEnd) }}</td>
            <td>{{ announcement.announcementTitle }}</td>
            <td>{{ announcement.careerBase }}</td>
            <td>{{ announcement.seekerNum }}</td>
          </tr>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script setup>
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import { ref, onMounted } from 'vue';
import axios from 'axios';
import { backend } from '@/const';

// 공고 리스트 상태
const announcements = ref([]);

// 데이터 가져오는 함수
const fetchAnnouncements = async () => {
  try {
    const response = await axios.get(`${backend}/announcement/recruiter/read-all/resume`, {
      headers: { 'Content-Type': 'application/json' },
      withCredentials: true
    });
    announcements.value = response.data.result; // 백엔드에서 가져온 데이터를 저장
  } catch (error) {
    console.error('공고 목록을 불러오는 중 오류 발생:', error);
  }
};

// 날짜 포맷 함수 (yyyy-mm-dd 형식으로 포맷팅)
const formatDate = (dateString) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = (`0${date.getMonth() + 1}`).slice(-2); // 두 자리수로 유지
  const day = (`0${date.getDate()}`).slice(-2);
  return `${year}.${month}.${day}`;
};

// 컴포넌트가 로드될 때 데이터를 가져옴
onMounted(() => {
  fetchAnnouncements();
});
</script>

<style scoped>
.container {
  width: 80%;
  margin: 0 auto;
}

#content {
  flex: 1;
  margin-left: 200px; /* 사이드바 너비만큼 왼쪽 여백 추가 */
  padding: 0 0 150px 0;
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
</style>
