<script setup>
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import {useRoute} from "vue-router";
import {onMounted, ref} from "vue";
import {UseInterviewScheduleStore} from "@/stores/UseInterviewScheduleStore";

const route = useRoute();
const scheduleIdx = route.params.idx;
const interviewScheduleStore = UseInterviewScheduleStore();
const scheduleInfo = ref({});

onMounted(async () => {
  scheduleInfo.value = await interviewScheduleStore.readInterviewSchedule(scheduleIdx);
});
</script>

<template>
  <MainHeaderComponent/>
  <div class="container">
    <MainSideBarComponent/>
    <div id="content">
      <h1>면접 일정 상세</h1>
      <table class="review-table">
        <tbody>
        <tr>
          <th>면접 정보</th>
          <th>상세 내용</th>
        </tr>
        <tr>
          <td>팀</td>
          <td>{{ scheduleInfo.teamIdx }}</td>
        </tr>
        <tr>
          <td>면접자</td>
          <td>
            <ul>
              <li v-for="seeker in scheduleInfo.seekerList" :key="seeker.idx">{{ seeker.name }}</li>
            </ul>
          </td>
        </tr>
        <tr>
          <td>면접일자</td>
          <td>{{ scheduleInfo.interviewDate }}</td>
        </tr>
        <tr>
          <td>면접시간</td>
          <td>{{ scheduleInfo.interviewStart }} ~ {{ scheduleInfo.interviewEnd }}</td>
        </tr>
        <tr>
          <td>면접관 이메일</td>
          <td>
            <ul>
              <li v-for="estimator in scheduleInfo.estimatorList" :key="estimator.idx">{{ estimator.email }}</li>
            </ul>
          </td>
        </tr>
        <tr>
          <td>면접 방식</td>
          <td>{{ scheduleInfo.isOnline ? '온라인' : '대면' }}</td>
        </tr>
        </tbody>
      </table>
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
  padding: 10px 15px;
  margin: 0 5px;
  cursor: pointer;
}

.pagination button.active {
  background-color: #212b36;
  color: white;
}
ul{padding: 0;}
li {
  list-style: none; /* 기본 리스트 스타일 제거 */
}
.review-table th:nth-child(1) {
  width: 20%;
}
</style>