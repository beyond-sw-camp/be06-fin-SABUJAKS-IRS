<template>
  <MainHeaderComponent></MainHeaderComponent>
  <div class="container">
      <MainSideBarComponent></MainSideBarComponent>
          <div id="content">
              <h1>지원자 리스트</h1>
              <div class="col-12">
                <div class="interview-add">
                  <button @click="sendResult()">서류결과전송</button>
                </div>
              </div>
              <table>
                  <tbody>
                  <tr>
                    <th>번호</th>
                    <th>이름</th>
                    <th>지원서 제목</th>
                    <th>지원 날짜</th>
                    <th>결과</th>
                    <!-- <th>비고 <input type="checkbox"></th> -->
                  </tr>
                  <tr
                        v-for="(resume, index) in resumeStore.resumeList"
                        :key="index"
                        @click="handleRowClick(resume)"
                        style="cursor: pointer"
                    >
                    <td>{{ index + 1 }}</td>
                        <td>{{ resume.seekerName }}</td>
                        <td>{{ resume.resumeTitle }}</td>
                        <td>{{ formatDate(resume.resumedAt) }}</td>
                        <td>{{ checkApplicationResult(resume.resumeResult)}}</td>
                  </tr>
                  </tbody>
              </table>
              <!-- <div class="button-container">
                  <button class="register-button">결과전송</button>
              </div> -->
              <!-- <div id="size-buttons">
                  <button>1</button>
                  <button>2</button>
                  <button>3</button>
              </div> -->
        </div>
    </div>
</template>

<script setup>
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import {onMounted} from 'vue';
import { UseResumeStore } from '@/stores/UseResumeStore';
import { useRoute, useRouter } from 'vue-router';

const resumeStore = UseResumeStore();
const route = useRoute();
const router = useRouter();



onMounted(async () => {
    await resumeStore.readAllRecruiter(route.params.announcementIdx);
    console.log(resumeStore.resumeList);
});

const formatDate = (dateString) => {
    return dateString.split('T')[0];
}

const handleRowClick = (resume) => {
    router.push(`/recruiter/resume/detail/${resume.resumeIdx}`);
};

// 결과를 확인하는 함수
const checkApplicationResult = (resumeResult) => {
  if (resumeResult === false) {
    return '서류 불합격';
  } else if (resumeResult === true) {
    return '서류 합격';
  }
    // if (finalResult === false) {
    //     return '최종 불합격';
    // } else if (finalResult === true) {
    //     return '최종 합격';
    // } else if (interviewTwoResult === false) {
    //     return '2차 면접 불합격';
    // } else if (interviewTwoResult === true) {
    //     return '2차 면접 합격';
    // } else if (interviewOneResult === false) {
    //     return '1차 면접 불합격';
    // } else if (interviewOneResult === true) {
    //     return '1차 면접 합격';
    // } else if (resumeResult === false) {
    //     return '서류 불합격';
    // } else if (resumeResult === true) {
    //     return '서류 합격';
    // } else {
    //     return '대기';
    // }
};

const sendResult = async () => {
  if (confirm("서류결과를 일괄 전송하시겠습니까?")) {
    if (await resumeStore.sendResult(resumeStore.resumeList)) {
      window.location.reload();
    }  else {
      alert("일괄 처리 과정에서 오류가 발생했습니다. 관리자에게 문의하십시오.")
    }
  }

}
</script>

<style scoped>

.container {
  width: 80%;
  margin: 0 auto;
}

#content {
  flex: 1;
  margin-left: 200px; /* 사이드바 너비만큼 왼쪽 여백 추가 */
  padding: 0 0 150px 0;
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