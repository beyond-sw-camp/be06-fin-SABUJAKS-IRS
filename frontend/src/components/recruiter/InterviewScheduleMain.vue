<script setup>
import { defineProps, defineEmits, ref } from 'vue';

// props를 정의합니다.
const props = defineProps({
  title: {
    type: String,
    required: true
  },
  careerBase: {
    type: String,
    required: true
  }
});

const emit = defineEmits(['interviewScheduleList']);

const announceIdx = ref(0);


const handleRowClick = (announceIdx) => {
  emit('interviewScheduleList', announceIdx);
  emit('titleModal', props.careerBase);
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
      <tr @click="handleRowClick(announceIdx)">
        <td>1</td>
        <td>2024.09.24 - 2024.10.24</td>
        <td>백엔드 엔지니어 신입공채</td>
        <td>24</td>
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
