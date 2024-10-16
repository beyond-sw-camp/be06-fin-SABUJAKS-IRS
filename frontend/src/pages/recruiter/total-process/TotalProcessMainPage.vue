<template>
  <MainHeaderComponent></MainHeaderComponent>
  <div class="container">
    <MainSideBarComponent></MainSideBarComponent>
    <div id="content">
      <h1>최종결과</h1>
      <table class="">
        <tbody>
        <tr>
          <th>번호</th>
          <th>공고 기간</th>
          <th>공고명</th>
          <th>신입/경력</th>
          <th>지원자수</th>
        </tr>
        <tr
            v-for="(announcement, index) in paginatedAnnouncements"
            :key="index"
            @click="handleRowClick(announcement)"
            class="hoverable-row"
        >
          <td>{{ announcementStore.announcementsPage.totalElements - ((currentPage - 1) * pageSize + index) }}</td>
          <td>{{ formatDate(announcement.announcementStart) }} - {{ formatDate(announcement.announcementEnd) }}</td>
          <td>{{ announcement.announcementTitle }}</td>
          <td>{{ announcement.careerBase }}</td>
          <td>{{ announcement.seekerNum }}</td>
        </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button
            v-for="page in totalPages"
            :key="page"
            @click="fetchAnnouncements(page)"
            :class="{ active: currentPage === page }"
        >
          {{ page }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import { UseAnnouncementStore } from '@/stores/UseAnnouncementStore';
import { useRouter } from 'vue-router';
import { onMounted, ref, computed } from 'vue';

const announcementStore = UseAnnouncementStore();
const router = useRouter();

const formatDate = (dateString) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = (`0${date.getMonth() + 1}`).slice(-2); // 두 자리수로 유지
  const day = (`0${date.getDate()}`).slice(-2);
  return `${year}.${month}.${day}`;
};

const currentPage = ref(1);
const pageSize = 10;

onMounted(async () => {
  await fetchAnnouncements(currentPage.value);
});

const fetchAnnouncements = async (page) => {
  currentPage.value = page;
  await announcementStore.fetchAnnouncements(page - 1, pageSize);
};

const paginatedAnnouncements = computed(() => {
  return announcementStore.announcements;
});

const totalPages = computed(() => {
  return announcementStore.announcementsPage.totalPages || 0;
});

const handleRowClick = (announcement) => {
  router.push(`/recruiter/total-process/list/${announcement.announcementIdx}`);
};
</script>

<style scoped>
.container {
  width: 80%;
  margin: 0 auto;
}

#content {
  flex: 1;
  margin-left: 200px; /* 사이드바 너비만큼 왼쪽 여백 추가 */
  padding: 150px 0;
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

.button-container {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 40px;
}

.register-button {
  width: 100px;
  background-color: #f1f1f1;
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 10px 20px;
  font-size: 0.8rem;
  font-weight: bold;
  color: #000;
  cursor: pointer;
  transition: background-color 0.3s;
}

.register-button:hover {
  background-color: #e0e6ed;
}

#size-buttons {
  display: flex;
  justify-content: center;
}

#size-buttons button {
  margin-right: 10px;
  padding: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

#size-buttons button:hover {
  background-color: #ddd;
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
  border-radius: 5px;
  padding: 10px 15px;
  margin: 0 5px;
  cursor: pointer;
}

/* .pagination button.active {
    background-color: #212b36;
    color: white;
} */
</style>