<template>
  <MainHeaderComponent></MainHeaderComponent>
  <div class="container">
      <MainSideBarComponent></MainSideBarComponent>
          <div id="content">
              <h1>지원자 리스트</h1>
              <table>
                  <tbody>
                  <tr>
                    <th>번호</th>
                    <th>이름</th>
                    <th>지원서 제목</th>
                    <th>지원 날짜</th>
                    <th>서류 결과</th>
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
                        <td v-if="resume.docPassed == null"></td>
                        <td v-else>{{ resume.docPassed ? "합격" : "불합격" }}</td>
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
import { onMounted } from 'vue';
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

</style>