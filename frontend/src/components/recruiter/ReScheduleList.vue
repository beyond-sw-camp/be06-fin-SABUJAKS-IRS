<script setup>
import {defineEmits, defineProps} from 'vue';

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
console.log(props.announcementIdx);
console.log(props.announcementUuid);
// const announceIdx = ref(0);

const handleRowClick = (schedule) => {
  emit('interviewScheduleInfo', schedule, props.announcementIdx, props.announcementUuid);
}
</script>

<template>
  <div id="content">
    <h1>{{ props.title }}</h1>
    <div class="col-12"></div>
    <table class="review-table">
      <tr>
        <th>번호</th>
        <th>기존면접일자</th>
        <th>기존면접시간</th>
        <th>요청면접일자</th>
        <th>요청 면접자</th>
        <th>조율 여부</th>
      </tr>
      <tr v-for="(schedule, index) in props.reSchedules" :key="schedule.idx" @click="handleRowClick(schedule)">
        <td>{{ index + 1 }}</td>
        <td>{{ schedule.interviewScheduleRes.interviewDate }}</td>
        <td>{{ schedule.interviewScheduleRes.interviewStart }} ~ {{ schedule.interviewScheduleRes.interviewEnd }}</td>
        <td>{{ schedule.interviewStart }} ~ {{ schedule.interviewEnd }}</td>
        <td>{{ schedule.seekerInfoGetRes.name }}</td>
        <td>{{ schedule.status ? '완료' : '조율중' }}</td>
      </tr>
    </table>
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
