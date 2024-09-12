<script setup>
import { defineProps, defineEmits } from 'vue';

// props를 정의합니다.
const props = defineProps({
  title: {
    type: String,
    required: true
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
      <tr>
        <th>번호</th>
        <th>면접일정</th>
        <th>??????</th>
        <th>면접방생성</th>
      </tr>
      <tr>
        <td>1</td>
        <td>2024.09.24 - 2024.10.24</td>
        <td>백엔드 엔지니어 신입공채</td>
        <td><button @click="createVideoInterview(uuid)">방 생성</button></td>
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
  width: 30%; /* 비율 조정 */
}

.review-table th:nth-child(4) { /* 두 번째 열 (신입/경력) */
  width: 20%; /* 비율 조정 */
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
