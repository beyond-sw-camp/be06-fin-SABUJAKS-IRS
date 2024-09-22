<script setup>
import { defineProps, defineEmits } from 'vue';

// props를 정의합니다.
const props = defineProps({
  title: {
    type: String,
    required: true
  },
  careerBase: {
    type: String,
    required: true
  },
  announcements: {
    type: Array,
    required: false
  }
});

const emit = defineEmits(['interviewScheduleList']);

// const announceIdx = ref(0);

const handleRowClick = (announcementIdx, announcementUuid) => {
  emit('interviewScheduleList', announcementIdx);
  emit('announcementUuid', announcementUuid);
  emit('titleModal', props.careerBase);
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
    <table class="review-table">
      <tr>
        <th>번호</th>
        <th>공고기간</th>
        <th>공고명</th>
        <th>지원자수</th>
      </tr>
<!--      <tr @click="handleRowClick('경력')">-->
      <tr v-for="(announcement, index) in props.announcements" :key="announcement.idx" @click="handleRowClick(announcement.idx, announcement.uuid)">
        <td>{{ index + 1 }}</td>
        <td>{{ formatDate(announcement.announcementStart) }} - {{ formatDate(announcement.announcementEnd) }}</td>
        <td>{{ announcement.jobTitle }}</td>
        <td>{{ announcement.applicantCount }}</td>
      </tr>
    </table>
  </div>

</template>

<style scoped>
.review-table th:nth-child(1) { /* 첫 번째 열 (번호) */
  width: 10%; /* 비율 조정 */
}

.review-table th:nth-child(2) { /* 두 번째 열 (신입/경력) */
  width: 40%; /* 비율 조정 */
}

.review-table th:nth-child(3) { /* 두 번째 열 (신입/경력) */
  width: 40%; /* 비율 조정 */
}

.review-table th:nth-child(4) { /* 두 번째 열 (신입/경력) */
  width: 10%; /* 비율 조정 */
}

.review-table tr:hover {
  background-color: #e6e6e6;
  cursor: pointer;
}
</style>
