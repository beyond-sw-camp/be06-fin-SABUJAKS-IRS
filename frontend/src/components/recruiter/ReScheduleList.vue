<script setup>
import {computed, defineEmits, defineProps, ref} from 'vue';
import router from "@/router";
import {UseInterviewScheduleStore} from "@/stores/UseInterviewScheduleStore";

// props를 정의합니다.
const props = defineProps({
  title: {
    type: String,
    required: false
  },
  reSchedules: {
    type: Array,
    required: false,
    default: () => [] // 기본값을 빈 배열로 설정
  },
  totalReSchedules: {
    type: Number,
    required: true
  },
  announcementIdx: {
    type: Number,
    required: false,
  },
  announcementUuid: {
    type: String,
    required: false,
  }
});

const emit = defineEmits(['interviewScheduleInfo']);
const interviewScheduleStore = UseInterviewScheduleStore();

const postsPerPage = 10; // 페이지당 게시글 수
const totalPages = computed(() => {
  return props.totalReSchedules ? Math.ceil(props.totalReSchedules / postsPerPage) : 0;
});

const currentPage = ref(1); // 현재 페이지

const handlePageClick = (pageNumber) => {
  currentPage.value = pageNumber;
  emit('loadInterviewScheduleList', pageNumber);
  window.scrollTo({top: 0});
};

// 각 페이지의 시작 번호 계산
const startNumberForPage = computed(() => {
  return props.totalReSchedules - (currentPage.value - 1) * postsPerPage;
});

// 현재 페이지에 맞는 면접 일정 slice
const paginatedReSchedules = computed(() => {
  return props.reSchedules;
});

const handleRowClick = (schedule) => {
  // emit('interviewScheduleInfo', schedule, props.announcementIdx, props.announcementUuid);
  interviewScheduleStore.setScheduleInfo(schedule);

  console.log(interviewScheduleStore.scheduleInfo);

  router.push({
    path: "/recruiter/interview-schedule/reschedule/detail/" + schedule.idx,
  })
}
</script>

<template>
  <div id="content">
    <h1>{{ props.title }}</h1>
    <div class="col-12"></div>
    <table class="review-table">
      <tbody>
      <tr>
        <th>번호</th>
        <th>기존면접일자</th>
        <th>기존면접시간</th>
        <th>요청면접일자</th>
        <th>요청 면접자</th>
        <th>조율 여부</th>
      </tr>
      <tr v-for="(schedule, index) in paginatedReSchedules" :key="schedule.idx" @click="handleRowClick(schedule)">
        <td>{{ startNumberForPage - index }}</td>
        <td>{{ schedule.interviewScheduleRes.interviewDate }}</td>
        <td>{{ schedule.interviewScheduleRes.interviewStart }} ~ {{ schedule.interviewScheduleRes.interviewEnd }}</td>
        <td>{{ schedule.interviewStart }} ~ {{ schedule.interviewEnd }}</td>
        <td>{{ schedule.seekerInfoGetRes.name }}</td>
        <td>{{ schedule.status ? '완료' : '조율중' }}</td>
      </tr>
      </tbody>
    </table>

    <div class="pagination">
      <button v-for="page in totalPages" :key="page" @click="handlePageClick(page)">{{ page }}</button>
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
  width: 25%;
}

.review-table th:nth-child(5) {
  width: 15%;
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
</style>
