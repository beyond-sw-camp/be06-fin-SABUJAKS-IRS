<script setup>
import { defineProps } from 'vue';

// props를 정의합니다.
const props = defineProps({
  title: {
    type: String,
    required: true
  },
  reScheduleList: {
    type: Array,
    required: false
  },
});

// const emit = defineEmits([
//     'openModal',
//     'createVideoInterview'
// ]);



</script>

<template>
  <div id="content">
    <!-- props.title을 직접 사용합니다 -->
    <h1>{{ props.title }}</h1>
    <div class="col-12">
    </div>
    <table class="review-table">
      <tr>
        <th>번호</th>
        <th>기존면접일정</th>
        <th>요청면접일정</th>
        <th>요청 면접자</th>
        <th>조율 여부</th>
      </tr>
      <tr>
        <td>1</td>
        <td>2024.09.24 - 2024.10.24</td>
        <td>2024.09.24 - 2024.10.24</td>
        <td>서시현</td>
        <td>조율중</td>
      </tr>
    </table>
  </div>

</template>

<style scoped>
.review-table th:nth-child(1) {
  width: 10%; /* 비율 조정 */
}

.review-table th:nth-child(2) {
  width: 20%; /* 비율 조정 */
}

.review-table th:nth-child(3) {
  width: 20%; /* 비율 조정 */
}

.review-table th:nth-child(4) {
  width: 20%; /* 비율 조정 */
}

.review-table th:nth-child(5) {
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
