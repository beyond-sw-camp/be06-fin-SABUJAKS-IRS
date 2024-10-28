<template>
  <MainHeaderComponent></MainHeaderComponent>
  <div class="container">
    <MainSideBarComponent></MainSideBarComponent>
    <div id="content">
      <h1>지원자 리스트</h1>
      <div class="col-12">
      </div>
      <table>
        <tbody>
          <tr>
            <th>번호</th>
            <th>이름</th>
            <th>지원서 제목</th>
            <th>지원 날짜</th>
            <th>결과</th>
          </tr>
          <tr
            v-for="(resume, index) in paginatedResumes"
            :key="index"
            @click="handleRowClick(resume)"
            class="hoverable-row"
          >
            <td>{{ resumeStore.resumeListPage.totalElements - ((currentPage - 1) * pageSize + index) }}</td>
            <td>{{ resume.seekerName }}</td>
            <td>{{ resume.resumeTitle }}</td>
            <td>{{ formatDate(resume.resumedAt) }}</td>
            <td>{{ checkApplicationResult(resume.resumeResult) }}</td>
          </tr>
        </tbody>
      </table>
      <div class="pagination">
        <button 
          v-for="page in totalPages" 
          :key="page" 
          @click="fetchResumes(page)" 
          :class="{ active: currentPage === page }"
        >
          {{ page }}
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import { onMounted, ref, computed } from 'vue';
import { UseResumeStore } from '@/stores/UseResumeStore';
import { useRoute, useRouter } from 'vue-router';

const resumeStore = UseResumeStore();
const route = useRoute();
const router = useRouter();

const currentPage = ref(1);
const pageSize = 10;

onMounted(async () => {
    await fetchResumes(currentPage.value);
});

const fetchResumes = async (page) => {
    currentPage.value = page;
    await resumeStore.readAllRecruiter(router, route.params.announcementIdx, page - 1, pageSize);
};

const paginatedResumes = computed(() => {
    return resumeStore.resumeList;
});

const totalPages = computed(() => {
    return resumeStore.resumeListPage.totalPages || 0;
});

const formatDate = (dateString) => {
    return dateString.split('T')[0];
}

const handleRowClick = (resume) => {
    router.push(`/recruiter/resume/detail/${resume.resumeIdx}`);
};

// 결과를 확인하는 함수
const checkApplicationResult = (resumeResult) => {
    if (resumeResult === false) {
        return '서류 불합격';
    } else if (resumeResult === true) {
        return '서류 합격';
    }
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

.interview-add {
    float: right;
    margin: 10px 0;
}

.interview-add button {
    background-color: #212b36;
    color: white;
    padding: 10px;
    border-radius: 5px;
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

.pagination button.active {
    background-color: #212b36;
    color: white;
}
</style>
