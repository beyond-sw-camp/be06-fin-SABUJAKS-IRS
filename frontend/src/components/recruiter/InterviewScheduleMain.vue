<script setup>
import {defineProps, defineEmits, computed, ref} from 'vue';
import router from "@/router";
import {UseInterviewScheduleStore} from "@/stores/UseInterviewScheduleStore";

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
const isLoading = ref(false); // 로딩 상태
const tempAnnouncements = ref([]); // 임시 데이터 저장소

const totalPages = computed(() => {
  return props.totalAnnouncements ? Math.ceil(props.totalAnnouncements / postsPerPage) : 0;
});

// 페이지 버튼 클릭 시 호출되는 메서드
const currentPage = ref(1); // 현재 페이지

const handlePageClick = async (pageNumber) => {
  isLoading.value = true; // 로딩 시작
  currentPage.value = pageNumber; // 페이지 번호 업데이트

  // 임시 데이터 설정 (기존 데이터 유지)
  tempAnnouncements.value = [...props.announcements];

  emit('loadAnnouncementList', pageNumber);

  // 로딩 종료 후에 실제 데이터로 교체
  tempAnnouncements.value = [...props.announcements];
  isLoading.value = false; // 로딩 종료
};

// const announceIdx = ref(0);
const interviewScheduleStore = UseInterviewScheduleStore(); // Store 인스턴스
const handleRowClick = (announcementIdx, announcementUuid) => {
  // Store에 데이터 저장
  interviewScheduleStore.setAnnouncementIdx(announcementIdx);
  interviewScheduleStore.setAnnouncementUuid(announcementUuid);
  interviewScheduleStore.setCareerBase(props.careerBase);

  console.log(props.title);
  if (props.title === "전체") {
    // emit('interviewScheduleList', props.announcementIdx, props.announcementUuid);
    router.push({
      path: "/recruiter/interview-schedule/reschedule/list",
    });
  } else {
    router.push({
      path: "/recruiter/interview-schedule/list",
    });
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

<template>
  <div id="content">
    <!-- props.title을 직접 사용합니다 -->
    <h1>{{ props.title }}</h1>
    <table class="review-table">
      <tbody>
      <tr>
        <th>번호</th>
        <th>공고기간</th>
        <th>공고명</th>
        <th>면접생성수</th>
      </tr>
      <!--      <tr @click="handleRowClick('경력')">-->
      <tr v-for="(announcement, index) in props.announcements" :key="announcement.idx"
          @click="handleRowClick(announcement.idx, announcement.uuid)">
        <td>{{ props.totalAnnouncements - index }}</td>
        <td>{{ formatDate(announcement.announcementStart) }} - {{ formatDate(announcement.announcementEnd) }}</td>
        <td>{{ announcement.title }}</td>
        <td>{{ props.title === "전체" ? announcement.countReSchedule : announcement.countInterviewSchedule }}</td>
      </tr>
      </tbody>
    </table>

    <div class="pagination">
      <button v-for="page in totalPages" :key="page" @click="handlePageClick(page)" :class="{ active: currentPage === page }">{{ page }}</button>
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
  border-radius: 5px;
  padding: 10px 15px;
  margin: 0 5px;
  cursor: pointer;
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

.review-table tr:hover {
  background-color: #e6e6e6;
  cursor: pointer;
}

.pagination button.active {
  background-color: #212b36;
  color: white;
}
</style>