<template>
  <div id="content">
    <!-- props.title을 직접 사용합니다 -->
    <h1>면접 평가표 생성</h1>
    <table class="review-table">
      <tbody>
      <tr>
        <th>번호</th>
        <th>공고기간</th>
        <th>공고명</th>
        <th>생성</th>
      </tr>
      <!--      <tr @click="handleRowClick('경력')">-->
      <tr v-for="(announcement, index) in props.announcements" :key="announcement.idx">
        <td>{{ startNumberForPage - index }}</td>
        <td>{{ formatDate(announcement.announcementStart) }} - {{ formatDate(announcement.announcementEnd) }}</td>
        <td>{{ announcement.title }}</td>
        <td><button @click="openCreateFormModal(announcement.idx)" class="createbtn">생성</button></td>       
      </tr>
      <div v-if="isModalVisible" class="modal-wrapper" @click="closeCreateFormModal">
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


</template>
<script setup>
import {defineProps, defineEmits, computed, ref} from 'vue';
import { UseInterviewEvaluateStore } from "@/stores/UseInterviewEvaluateStore";
const interviewEvaluateStore = UseInterviewEvaluateStore();
const isModalVisible = ref(false); // 모달 표시 여부를 관리하는 상태
const announceIdx = ref()
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

// props를 정의합니다.
const props = defineProps({
  title: {
    type: String,
    required: true
  },
  careerBase: {
    type: String,
    required: false
  },
  announcements: {
    type: Array,
    required: false,
    default: () => [] // 기본값 설정
  },
  totalAnnouncements: {
    type: Number,
    required: true
  }
});

const emit = defineEmits(['interviewScheduleList']);

const postsPerPage = 10; // 페이지당 게시글 수
const totalPages = computed(() => {
  return props.totalAnnouncements ? Math.ceil(props.totalAnnouncements / postsPerPage) : 0;
});

// 페이지 버튼 클릭 시 호출되는 메서드
const currentPage = ref(1); // 현재 페이지

const handlePageClick = (pageNumber) => {
  currentPage.value = pageNumber; // 페이지 번호 업데이트
  emit('loadAnnouncementList', pageNumber);
};

const startNumberForPage = computed(() => {
  return props.totalAnnouncements - (currentPage.value - 1) * postsPerPage;
});

// const announceIdx = ref(0);
const openCreateFormModal = (idx) => { 
    isModalVisible.value = true;
    announceIdx.value = idx;
};

const closeCreateFormModal = () => { isModalVisible.value = false; };

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
    await interviewEvaluateStore.createForm(requestBody);
}

const formatDate = (datetime) => {
  const date = new Date(datetime);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  return `${year}-${month}-${day}`;
}
</script>

<style scoped>
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
