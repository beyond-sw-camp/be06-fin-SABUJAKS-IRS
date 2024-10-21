<script setup>
import {defineProps, defineEmits, computed, ref} from 'vue';
import router from "@/router";

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  interviewSchedules: {
    type: Array,
    required: false
  },
  totalInterviewSchedules: {
    type: Number,
    required: true
  },
  announcementIdx: {
    type: Number,
    required: false
  },
  announcementUuid: {
    type: String,
    required: false
  }
});

const emit = defineEmits([
  'openModal',
  'createVideoInterview'
]);

const postsPerPage = 10; // 페이지당 게시글 수

const totalPages = computed(() => {
  return props.totalInterviewSchedules ? Math.ceil(props.totalInterviewSchedules / postsPerPage) : 0;
});

const currentPage = ref(1); // 현재 페이지
const isLoading = ref(false); // 로딩 상태

const handlePageClick = async (pageNumber) => {
  isLoading.value = true; // 로딩 시작
  currentPage.value = pageNumber; // 페이지 번호 업데이트

  emit('loadAnnouncementList', pageNumber);
  window.scrollTo({top: 0});
  isLoading.value = false; // 로딩 종료
};

// 각 페이지의 시작 번호 계산
// const startNumberForPage = computed(() => {
//   return isLoading.value ? '-' : props.totalInterviewSchedules - (currentPage.value - 1) * postsPerPage;
// });

const handleRowClick = (type) => {
  emit('openModal', type);
}

const createVideoInterview = (uuid, interviewScheduleInfo, event) => {
  event.stopPropagation();  // 이벤트 버블링 방지
  emit('createVideoInterview', uuid, interviewScheduleInfo);
}

const formatDate = (datetime) => {
  const date = new Date(datetime);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  return `${year}-${month}-${day}`;
}

const getUniqueSeekers = (seekerList) => {
  const uniqueSeekers = new Map();
  seekerList.forEach(seeker => {
    uniqueSeekers.set(seeker.idx, seeker);
  });
  return Array.from(uniqueSeekers.values());
}

const handleScheduleClick = (schedule) => {
  router.push(`/recruiter/interview-schedule/detail/${schedule.idx}`);
}
</script>

<template>
  <div id="content">
    <h1>{{ props.title }}</h1>
    <div class="col-12">
      <div class="interview-add mr-10">
        <button @click="handleRowClick('경력')">면접일정생성</button>
      </div>
    </div>
    <table class="review-table">
      <tbody>
      <tr>
        <th>번호</th>
        <th>면접일</th>
        <th>면접시간</th>
        <th>팀</th>
        <th>참가자</th>
        <th>면접방생성</th>
      </tr>
      <tr v-for="(schedule, index) in props.interviewSchedules" :key="schedule.uuid"
          @click="handleScheduleClick(schedule)">
        <td>{{ props.totalInterviewSchedules - ((currentPage - 1) * postsPerPage + index) }}</td>
        <td>{{ formatDate(schedule.interviewDate) }}</td>
        <td>{{ schedule.interviewStart }} - {{ schedule.interviewEnd }}</td>
        <td>{{ schedule.teamIdx }} 팀</td>
        <td>
          <ul>
            <li v-for="seeker in getUniqueSeekers(schedule.seekerList)" :key="seeker.idx">
              {{ seeker.name }}
            </li>
          </ul>
        </td>
        <td class="text-center">
          <button class="create-video-interview" @click="createVideoInterview(schedule.uuid, schedule, $event)">방 생성
          </button>
        </td>
      </tr>
      </tbody>
    </table>

    <div class="pagination">
      <button v-for="page in totalPages" :key="page" @click="handlePageClick(page)" :class="{ active: currentPage === page }">{{ page }}</button>
    </div>
  </div>
</template>

<style scoped>
.container {
  width: 80%;
  margin: 0 auto;
}

#content {
  flex: 1;
  margin-left: 200px; /* 사이드바 너비만큼 왼쪽 여백 추가 */
  padding: 150px 0 150px 0;
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

/* 공고 등록 버튼 스타일 */
.register-button {
  background-color: #212b36;
  color: white;
  border-radius: 5px;
  padding: 13px 10px;
  margin-left: auto;
  margin-bottom: 10px;
  border: none;
  cursor: pointer;
  /* font-weight: bold; */
  font-size: 16px;
  /* transition: background-color 0.3s ease; */
}

.register-button:hover {
  background-color: #37404a;
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

.review-table th:nth-child(1) {
  width: 10%;
}

.review-table th:nth-child(2) {
  width: 20%;
}

.review-table th:nth-child(3) {
  width: 20%;
}

.review-table th:nth-child(4) {
  width: 10%;
}

.review-table th:nth-child(5) {
  width: 20%;
}

.review-table th:nth-child(6) {
  width: 10%;
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

.review-table tr:hover {
  background-color: #e6e6e6;
  cursor: pointer;
}

.create-video-interview {
  background-color: #212b36;
  color: white;
  padding: 10px;
  border-radius: 5px;
}

.pagination button.active {
  background-color: #212b36;
  color: white;
}
</style>