<template>
<div>
  <MainHeaderComponent></MainHeaderComponent>
  <div class="container">
    <MainSideBarComponent></MainSideBarComponent>
    <div class="content">
      <h1>면접 평가 조회</h1>
      <table>
        <tbody>
          <tr>
            <th>번호</th>
            <th>지원자 ID</th>
            <th>지원자 이름</th>
            <th>지원자 이메일</th>
            <th>상세보기</th>
            <th>합격/불합격</th>
          </tr>
          <tr v-for="([key, result], index) in Object.entries(interviewEvaluateResult)" :key="key">
            <td>{{ index + 1 }}</td>
            <td>{{ key }}</td>
            <td>{{ result[0].seekerName || '정보 없음' }}</td>
            <td>{{ result[0].seekerEmail }}</td>
            <td><button @click="openSearchEvaluateResultModal(result)" class="searchbtn">조회</button></td>
            <td>
                <button @click="handleResultButton(key, 1)" class="searchbtn">합격</button>
                <button @click="handleResultButton(key, 0)" class="searchbtn">불합격</button>
            </td>
          </tr>
        </tbody>
    </table>
      <div v-if="isModalVisible" class="modal-wrapper" @click="closeSearchEvaluateResultModal">
        <div class="modal-container" @click.stop>
          <span class="modal-closebtn" @click="closeSearchEvaluateResultModal">×</span>
          <h2>면접 평가 상세</h2>
          <h3>평가자별 면접 결과</h3>
          <div v-for="(evaluate, index) in evaluateList" :key="index">
            <h4>면접관 {{ index + 1 }} - 이메일: {{ evaluate.estimatorEmail }}</h4>
            <label>총점: {{ evaluate.totalScore }}</label>
            <label>의견: {{ evaluate.comments }}</label>
            <div v-for="(value, questionKey) in evaluate.interviewEvaluateFormReadRes" :key="questionKey">
              <label>{{ questionKey }}: {{ value }} 점수: {{ evaluate.interviewEvaluateResultReadRes[`r${questionKey.charAt(1)}`] }}</label>
            </div>
          </div>
      </div>
    </div>
  </div>
</div>
</div>
</template>

<script setup>
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import { onMounted, ref } from "vue";
import MainHeaderComponent from "../../../components/recruiter/MainHeaderComponent.vue";
import { UseInterviewEvaluateStore } from "@/stores/UseInterviewEvaluateStore";
import { UseTotalProcessStore } from "@/stores/UseTotalProcessStore"
import { useRoute } from "vue-router";
import { useToast } from "vue-toastification";
const totalProcessStore = UseTotalProcessStore();
const interviewEvaluateStore = UseInterviewEvaluateStore();

const interviewEvaluateResult = ref({});
const route = useRoute();
const toast = useToast();
const evaluateList = ref({})
const isModalVisible = ref();

onMounted(async () => {
    const response = await interviewEvaluateStore.readAllEvaluate(route.params.announcementIdx, route.params.interviewNum);
    if (response.success) {
        interviewEvaluateResult.value = response.result.interviewEvaluateReadAllResMap;
        console.log(interviewEvaluateResult.value)
        toast.success("지원자들의 면접 평가 결과를 불러왔습니다.");
    } else {
        toast.error("평가 데이터를 불러오는 데 실패했습니다.");
    }
});

const openSearchEvaluateResultModal = (result) => { 
  isModalVisible.value = true;
  evaluateList.value = result;
};

const closeSearchEvaluateResultModal = () => { isModalVisible.value = false; };

const handleResultButton = async (seekerIdx, isPass) => {
  const requestBody = {
    announcementIdx: route.params.announcementIdx,
    seekerIdx: seekerIdx,
    isPass: isPass,
    interviewNum: route.params.interviewNum,
  }
  const response = await totalProcessStore.create(requestBody)
  if(response.success){
    toast.success(`지원자의 ${route.params.interviewNum}차 면접 평가 결과를 저장했습니다.`)
  } else {
    toast.error(`지원자의 ${route.params.interviewNum}차 면접 평가 결과 저장에 실패했습니다.`)
  }
}

</script>

<style scoped>
.container {
  width: 80%;
  margin: 0 auto;
  margin-left: 200px;
  padding: 150px 0;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

.content {
  flex: 1;
  margin-left: 200px;
  padding: 0 0 150px 0;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

.content h1 {
  font-size: 24px;
  margin: 50px 0;
}

table {
    width: 100%;
    border: 1px solid #ddd;
    border-collapse: collapse;
    margin-bottom: 40px;
    display: table;
    box-sizing: border-box;
    text-indent: initial;
    unicode-bidi: isolate;
    border-spacing: 2px;
}

tbody {
    display: table-row-group;
    vertical-align: middle;
    unicode-bidi: isolate;
    border-color: inherit;
}

tr {
    display: table-row;
    vertical-align: inherit;
    unicode-bidi: isolate;
    border-color: inherit;
}

td {
    border: 1px solid #ddd;
    padding: 25px;
    text-align: center;
}

th {
    background-color: #f1f1f1;
    padding: 25px;
    text-align: center;
    border: 1px solid #ddd;
    display: table-cell;
    vertical-align: inherit;
    font-weight: bold;
    text-align: -internal-center;
    unicode-bidi: isolate;
}

.modal-wrapper {
display: flex;
justify-content: center;
align-items: center;
position: fixed;
top: 0;
left: 0;
width: 100%;
height: 100%;
background-color: rgba(0, 0, 0, 0.5);
z-index: 9;
}

.modal-container {
background-color: white;
padding: 20px;
border-radius: 8px;
width: 80%;
height: 80%;
overflow-y: scroll;
}

.modal-container h2 {
margin: 0 0 20px;
}

.modal-container label {
display: block;
margin: 15px 0;
font-size: 1.1rem;
font-weight: 600;
}

.modal-container input {
width: calc(100% - 40px);
padding: 10px;
border: 1px solid #ccc;
border-radius: 4px;
}

.modal-closebtn {
color: #aaa;
float: right;
font-size: 28px;
font-weight: bold;
}

.moadl-submitbtn {
  width: 100%;
  margin-top: 20px;
  padding: 10px;
  background-color: #232b16;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}
.createbtn {
  width: fit-content;
  background-color: #2A3845;
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 10px;
  font-size: 0.8rem;
  font-weight: bold;
  color: white;
  cursor: pointer;
  height: 100%;
  transition: background-color 0.3s;
  display: inline-block;
  text-decoration: none;
}

.createbtn:hover {
  background-color: #B4C7D0;
}
</style>
