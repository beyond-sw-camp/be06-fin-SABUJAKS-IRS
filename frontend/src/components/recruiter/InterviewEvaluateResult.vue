<template>
    <div id="content">
      <!-- props.title을 직접 사용합니다 -->
      <h1>면접 평가 조회</h1>
      <table class="review-table">
        <tbody>
        <tr>
          <th>번호</th>
          <th>공고기간</th>
          <th>공고명</th>
          <th>조회</th>
        </tr>
        <!--      <tr @click="handleRowClick('경력')">-->
        <tr v-for="(announcement, index) in props.announcements" :key="announcement.idx">
          <td>{{ startNumberForPage - index }}</td>
          <td>{{ formatDate(announcement.announcementStart) }} - {{ formatDate(announcement.announcementEnd) }}</td>
          <td>{{ announcement.title }}</td>
          <td><button @click="handleSearchButton(announcement.idx)" class="searchbtn">조회</button></td>       
        </tr>
        </tbody>
      </table>
  
      <div id="size-buttons">
        <button v-for="page in totalPages" :key="page" @click="handlePageClick(page)" :class="{ active: currentPage === page }">{{ page }}</button>
      </div>
    </div>
  
  
  </template>
  <script setup>
  import {defineProps, defineEmits, computed, ref} from 'vue';
  import { useRouter } from 'vue-router';
  const router = useRouter();
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
  
  const handleSearchButton = (announcementIdx) => {
    router.push(`/recruiter/interview-evaluate/result/${announcementIdx}`)
  }
  
  const postsPerPage = 10;
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
    z-index: 12;
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
  .searchbtn {
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

  #size-buttons button.active {
    background-color: #212b36;
    color: white;
  }
  </style>
  