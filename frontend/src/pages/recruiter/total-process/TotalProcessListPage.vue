<template>
  <MainHeaderComponent></MainHeaderComponent>
  <div class="container">
    <MainSideBarComponent></MainSideBarComponent>
    <div id="content">
      <h1>지원자 리스트</h1>
      <div class="col-12">
        <div class="interview-add">
<!--          <button @click="sendResult()">결과전송</button>-->
        </div>
      </div>
      <table>
        <tbody>
        <tr>
          <th>번호</th>
          <th>이름</th>
          <th>서류 결과</th>
          <th>1차 인터뷰 결과</th>
          <th>2차 인터뷰 결과</th>
          <th>최종 결과</th>
        </tr>
        <tr
            v-for="(result, index) in paginatedResumes"
            :key="index"
            @click="handleRowClick(result)"
            class="hoverable-row"
        >
          <td>{{ totalProcessStore.totalProcessListPage.totalElements - ((currentPage - 1) * pageSize + index) }}</td>
          <td>{{ result.seekerName }}</td>
          <td :class="result.resumeResult === null ? '' : (result.resumeResult ? 'pass' : 'fail')">
            {{ result.resumeResult === null ? '' : (result.resumeResult ? '합격' : '불합격') }}
          </td>
          <td :class="result.interviewOneResult === null ? '' : (result.interviewOneResult ? 'pass' : 'fail')">
            {{ result.interviewOneResult === null ? '' : (result.interviewOneResult ? '합격' : '불합격') }}
          </td>
          <td :class="result.interviewTwoResult === null ? '' : (result.interviewTwoResult ? 'pass' : 'fail')">
            {{ result.interviewTwoResult === null ? '' : (result.interviewTwoResult ? '합격' : '불합격') }}
          </td>
          <td :class="result.finalResult === null ? '' : (result.finalResult ? 'pass' : 'fail')">
            {{ result.finalResult === null ? '' : (result.finalResult ? '합격' : '불합격') }}
          </td>
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
import {UseTotalProcessStore} from '@/stores/UseTotalProcessStore';
import { useRoute, useRouter } from 'vue-router';

const totalProcessStore = UseTotalProcessStore();
const route = useRoute();
const router = useRouter();

const currentPage = ref(1);
const pageSize = 10;

onMounted(async () => {
  await fetchResumes(currentPage.value);
});

const fetchResumes = async (page) => {
  currentPage.value = page;
  await totalProcessStore.readAllTotalProcess(router, route.params.announcementIdx, page - 1, pageSize);
};

const paginatedResumes = computed(() => {
  return totalProcessStore.totalProcessList;
});

const totalPages = computed(() => {
  return totalProcessStore.totalProcessListPage.totalPages || 0;
});

// const sendResult = async () => {
//   if (confirm("최종결과를 일괄 전송하시겠습니까?")) {
//     await totalProcessStore.sendResult(router, totalProcessStore.resumeList);
//   }
// }
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

.pass {
  color: blue; /* 합격은 파란색 */
}

.fail {
  color: red; /* 불합격은 빨간색 */
}
</style>
