<script setup>
import { defineProps, defineEmits } from 'vue';

// props를 정의합니다.
const props = defineProps({
  title: {
    type: String,
    required: true
  },
  interviewSchedules: {
    type: Array,
    required: false
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
    'createVideoInterview']);

const handleRowClick = (type) => {
  emit('openModal', type);
}

const createVideoInterview = (uuid) => {
  emit('createVideoInterview', uuid)
}

const formatDate = (datetime) => {
  const date = new Date(datetime);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  return `${year}-${month}-${day}`;
}

</script>

<template>
  <div id="content">
    <!-- props.title을 직접 사용합니다 -->
    <h1>{{ props.title }}</h1>
    <div class="col-12">
      <div class="interview-add">
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
      <tr v-for="(schedule, index) in props.interviewSchedules" :key="schedule.uuid">
        <td>{{ index + 1 }}</td>
        <td>{{ formatDate(schedule.interviewDate) }}</td>
        <td>{{ schedule.interviewStart }} - {{ schedule.interviewEnd }}</td>
        <td>{{ schedule.teamIdx }} 팀</td>
        <td>
          <ul>
            <li v-for="(seeker, i) in schedule.seekerList" :key="i">
              {{ seeker.name }}
            </li>
          </ul>
        </td>
        <td><button @click="createVideoInterview(schedule.uuid)">방 생성</button></td>
      </tr>
      </tbody>
    </table>
  </div>

</template>

<style scoped>
.review-table th:nth-child(1) { /* 첫 번째 열 (번호) */
  width: 10%; /* 비율 조정 */
}

.review-table th:nth-child(2) { /* 두 번째 열 (신입/경력) */
  width: 20%; /* 비율 조정 */
}

.review-table th:nth-child(3) { /* 두 번째 열 (신입/경력) */
  width: 20%; /* 비율 조정 */
}

.review-table th:nth-child(4) { /* 두 번째 열 (신입/경력) */
  width: 10%; /* 비율 조정 */
}

.review-table th:nth-child(5) { /* 두 번째 열 (신입/경력) */
  width: 20%; /* 비율 조정 */
}

.review-table th:nth-child(6) { /* 두 번째 열 (신입/경력) */
  width: 10%; /* 비율 조정 */
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
</style>
