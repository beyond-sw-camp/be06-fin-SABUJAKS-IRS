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
  <div class="container padding-0">
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
<style>
ul{padding: 0;}
li {
  list-style: none; /* 기본 리스트 스타일 제거 */
}
.review-table th:nth-child(1) {
  width: 20%;
}
</style>