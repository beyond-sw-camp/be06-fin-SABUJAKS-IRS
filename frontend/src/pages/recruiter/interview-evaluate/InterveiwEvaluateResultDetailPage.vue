<template>
    <div>
      <MainHeaderComponent></MainHeaderComponent>
      <div class="wrapper">
        <MainSideBarComponent></MainSideBarComponent>
        <div class="container">
          <h1>면접 평가 조회</h1>
          <table class="review-table">
            <tbody>
              <tr>
                <th>번호</th>
                <th>지원자 이름</th>
                <th>총점</th>
                <th>코멘트</th>
                <th>평가자 이메일</th>
              </tr>
              <!-- Object.entries를 사용하여 key와 value로 나누어 v-for 적용 -->
              <tr v-for="([key, result], index) in Object.entries(interviewEvaluateResult)" :key="key">
                <td>{{ index + 1 }}</td>
                <td>{{ result.seekerName || '정보 없음' }}</td>
                <td>{{ result.totalScore }}</td>
                <td>{{ result.comments }}</td>
                <td>{{ result.estimatorEmail }}</td>
              </tr>
            </tbody>
          </table>
        </div>
      </div>
    </div>
  </template>
  
  <script setup>
  import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
  import { onMounted, ref } from "vue";
  import MainHeaderComponent from "../../../components/recruiter/MainHeaderComponent.vue";
  import { UseInterviewEvaluateStore } from "@/stores/UseInterviewEvaluateStore";
  import { useRoute } from "vue-router";
  import { useToast } from "vue-toastification";
  
  const interviewEvaluateStore = UseInterviewEvaluateStore();
  const interviewEvaluateResult = ref({});
  const route = useRoute();
  const toast = useToast();
  
  onMounted(async () => {
      const response = await interviewEvaluateStore.readAllEvaluate(route.params.announcementIdx);
      if (response.success) {
          interviewEvaluateResult.value = response.result.interviewEvaluateReadResumeInfoResMap;
          console.log(interviewEvaluateResult.value);
          toast.success("지원자들의 면접 평가 결과를 불러왔습니다.");
      } else {
          toast.error("평가 데이터를 불러오는 데 실패했습니다.");
      }
  });
  </script>
  
  <style>
  .wrapper {
      width: 80%;
      margin: 0 auto;
  }
    
  .container {
      width: 80%;
      flex: 1;
      margin: 0 auto;
      margin-left: 200px;
      padding: 150px 0;
      display: flex;
      flex-direction: column;
      box-sizing: border-box;
  }
  </style>
  