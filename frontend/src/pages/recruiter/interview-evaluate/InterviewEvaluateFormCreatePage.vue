<template>
<div>
  <MainHeaderComponent></MainHeaderComponent>
    <div class="container">
      <MainSideBarComponent></MainSideBarComponent>
      <!-- <div class="container">
        <InterviewEvaluateFormMain
        v-if="isInterviewScheduleMain"
        @loadAnnouncementList="loadAnnouncementList"
        :title="careerBase"
        :announcements="announcements"
        :totalAnnouncements="totalAnnouncements">
        </InterviewEvaluateFormMain>
      </div> -->
      <div class="content">
        <h1>면접 평가표 생성</h1>
        <table class="announcement-table">
        <tbody>
        <tr>
          <th>번호</th>
          <th>공고기간</th>
          <th>공고명</th>
          <th>생성</th>
        </tr>
        <tr v-for="(announcement, index) in announcements" :key="announcement.idx">
          <td>{{ startNumberForPage - index }}</td>
          <td>{{ formatDate(announcement.announcementStart) }} - {{ formatDate(announcement.announcementEnd) }}</td>
          <td>{{ announcement.title }}</td>
          <td><button @click="openCreateFormModal(announcement.idx)" class="createbtn">생성</button></td>       
        </tr>
        <div v-if="showModal" class="modal-wrapper" @click="closeCreateFormModal">
            <div class="modal-container" @click.stop>
              <span class="modal-closebtn" @click="closeCreateFormModal">×</span>
              <h2></h2>
              <label>평가항목 1</label>
              <input v-model="q1" type="text" placeholder="면접평가항목을 입력해주세요" />
              
              <label>평가항목 2</label>
              <input v-model="q2" type="text" placeholder="면접평가항목을 입력해주세요" />
              
              <label>평가항목 3</label>
              <input v-model="q3" type="text" placeholder="면접평가항목을 입력해주세요" />
              
              <label>평가항목 4</label>
              <input v-model="q4" type="text" placeholder="면접평가항목을 입력해주세요" />
              
              <label>평가항목 5</label>
              <input v-model="q5" type="text" placeholder="면접평가항목을 입력해주세요" />
              
              <label>평가항목 6</label>
              <input v-model="q6" type="text" placeholder="면접평가항목을 입력해주세요" />
              
              <label>평가항목 7</label>
              <input v-model="q7" type="text" placeholder="면접평가항목을 입력해주세요" />
              
              <label>평가항목 8</label>
              <input v-model="q8" type="text" placeholder="면접평가항목을 입력해주세요" />
              
              <label>평가항목 9</label>
              <input v-model="q9" type="text" placeholder="면접평가항목을 입력해주세요" />
              
              <label>평가항목 10</label>
              <input v-model="q10" type="text" placeholder="면접평가항목을 입력해주세요" />

              <button class="moadl-submitbtn" @click="handleCreateForm">면접 평가표 등록</button>
            </div>
        </div>
        </tbody>
      </table>
      <div id="size-buttons">
        <button v-for="page in totalPages" :key="page" @click="handlePageClick(page)">{{ page }}</button>
      </div>
    </div>
  </div>
</div>
</template> 

<script setup>
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import { computed, onMounted, ref } from "vue";
import MainHeaderComponent from "../../../components/recruiter/MainHeaderComponent.vue";
import { UseInterviewScheduleStore } from "@/stores/UseInterviewScheduleStore";
import { UseInterviewEvaluateStore } from "@/stores/UseInterviewEvaluateStore";
import { useToast } from "vue-toastification"

const toast = useToast();
const interviewScheduleStore = UseInterviewScheduleStore();
const interviewEvaluateStore = UseInterviewEvaluateStore();

const totalAnnouncements = ref(0);
const announcements = ref([]);

// pagination 설정
const careerBase = ref("전체");
const currentPage = ref(1);
const postsPerPage = 10; 
const totalPages = computed(() => { return totalAnnouncements.value ? Math.ceil(totalAnnouncements.value / postsPerPage) : 0;});

// 면접 평가표 생성 모달
const showModal = ref(false);
const announceIdx = ref(null)
const q1 = ref('')
const q2 = ref('')
const q3 = ref('')
const q4 = ref('')
const q5 = ref('')
const q6 = ref('')
const q7 = ref('')
const q8 = ref('')
const q9 = ref('')
const q10 = ref('')

onMounted(async () => {
  announcements.value = await interviewScheduleStore.readAllAnnouncement( careerBase.value, currentPage.value );
  totalAnnouncements.value = await interviewScheduleStore.getTotalAnnouncement( careerBase.value );
});

const handlePageClick = (pageNumber) => {
  currentPage.value = pageNumber;
  loadAnnouncementList(pageNumber);
};

const startNumberForPage = computed(() => {
  return totalAnnouncements.value - (currentPage.value - 1) * postsPerPage;
});

const loadAnnouncementList = async (pageNumber) => {
  announcements.value = await interviewScheduleStore.readAllAnnouncement( careerBase.value, pageNumber);
};

const openCreateFormModal = (idx) => { 
  showModal.value = true;
  announceIdx.value = idx;
};

const closeCreateFormModal = () => { 
  showModal.value = false; 
};

const handleCreateForm = async() => {    
    const requestBody = {
        announceIdx: announceIdx.value,
        q1: q1.value,
        q2: q2.value,
        q3: q3.value,
        q4: q4.value,
        q5: q5.value,
        q6: q6.value,
        q7: q7.value,
        q8: q8.value,
        q9: q9.value,
        q10: q10.value,
    }
    const response = await interviewEvaluateStore.createForm(requestBody);
    if(response.success) {
      toast.success("면접 평가표 등록에 성공했습니다.")
      showModal.value = false;
    }
}

const formatDate = (datetime) => {
  const date = new Date(datetime);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  return `${year}-${month}-${day}`;
}

</script>
<style>
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

.announcement-table th:nth-child(1) { /* 첫 번째 열 (번호) */
  width: 10%; /* 비율 조정 */
}

.announcement-table th:nth-child(2) { /* 두 번째 열 (신입/경력) */
  width: 40%; /* 비율 조정 */
}

.announcement-table th:nth-child(3) { /* 두 번째 열 (신입/경력) */
  width: 40%; /* 비율 조정 */
}

.announcement-table th:nth-child(4) { /* 두 번째 열 (신입/경력) */
  width: 10%; /* 비율 조정 */
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
    background-color: #212b36;
    border: 1px solid #ddd;
    border-radius: 10px;
    color: white;
    font-weight: bold;
    border: none;
    cursor: pointer;
}

.moadl-submitbtn:hover {
    background-color: #90959a;
}

.createbtn {
    width: fit-content;
    background-color: #212b36;
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
    background-color: #90959a;
}
</style>
  