<script setup>
import {computed, defineEmits, defineProps, ref} from 'vue';

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
  emit('interviewScheduleInfo', schedule, props.announcementIdx, props.announcementUuid);
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

    <div id="size-buttons">
      <button v-for="page in totalPages" :key="page" @click="handlePageClick(page)">{{ page }}</button>
    </div>
  </div>
</template>

<style scoped>
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
