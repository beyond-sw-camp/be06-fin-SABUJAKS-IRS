<script setup>
import {defineProps, defineEmits, computed, ref} from 'vue';
import router from "@/router";

const props = defineProps({
  title: {
    type: String,
    required: true
  },
  interviewSchedules: {
    type: Array,
    required: false
  },
  totalInterviewSchedules: {
    type: Number,
    required: true
  },
  announcementIdx: {
    type: Number,
    required: false
  },
  announcementUuid: {
    type: String,
    required: false
  }
});

const emit = defineEmits([
  'openModal',
  'createVideoInterview'
]);

const postsPerPage = 10; // 페이지당 게시글 수
const totalPages = computed(() => {
  return props.totalInterviewSchedules ? Math.ceil(props.totalInterviewSchedules / postsPerPage) : 0;
});

const currentPage = ref(1); // 현재 페이지
const isLoading = ref(false); // 로딩 상태

const handlePageClick = async (pageNumber) => {
  isLoading.value = true; // 로딩 시작
  currentPage.value = pageNumber; // 페이지 번호 업데이트

  emit('loadAnnouncementList', pageNumber);
  window.scrollTo({top: 0});
  isLoading.value = false; // 로딩 종료
};

// 각 페이지의 시작 번호 계산
const startNumberForPage = computed(() => {
  return isLoading.value ? '-' : props.totalInterviewSchedules - (currentPage.value - 1) * postsPerPage;
});

const handleRowClick = (type) => {
  emit('openModal', type);
}

const createVideoInterview = (uuid, interviewScheduleInfo, event) => {
  event.stopPropagation();  // 이벤트 버블링 방지
  emit('createVideoInterview', uuid, interviewScheduleInfo);
}

const formatDate = (datetime) => {
  const date = new Date(datetime);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');
  return `${year}-${month}-${day}`;
}

const getUniqueSeekers = (seekerList) => {
  const uniqueSeekers = new Map();
  seekerList.forEach(seeker => {
    uniqueSeekers.set(seeker.idx, seeker);
  });
  return Array.from(uniqueSeekers.values());
}

const handleScheduleClick = (schedule) => {
  router.push(`/recruiter/interview-schedule/detail/${schedule.idx}`);
}

const handleCreateAllVideoInterview = () => {
  // emit('createAllVideoInterview', paginatedInterviewSchedules, props.announcementUuid);
  emit('createAllVideoInterview');
}
</script>

<template>
  <div id="content">
    <h1>{{ props.title }}</h1>
    <div class="col-12">
      <div class="interview-add">
        <button @click="handleCreateAllVideoInterview()">면접방일괄생성</button>
      </div>
      <div class="interview-add mr-10">
        <button @click="handleRowClick('경력')">면접일정생성</button>
      </div>
    </div>
    <table class="review-table">
      <tbody>
      <tr>
        <th>번호</th>
        <th>면접일</th>
        <th>면접시간</th>
        <th>팀</th>
        <th>참가자</th>
        <th>면접방생성</th>
      </tr>
      <tr v-for="(schedule, index) in props.interviewSchedules" :key="schedule.uuid"
          @click="handleScheduleClick(schedule)">
        <td>{{ startNumberForPage - index }}</td>
        <td>{{ formatDate(schedule.interviewDate) }}</td>
        <td>{{ schedule.interviewStart }} - {{ schedule.interviewEnd }}</td>
        <td>{{ schedule.teamIdx }} 팀</td>
        <td>
          <ul>
            <li v-for="seeker in getUniqueSeekers(schedule.seekerList)" :key="seeker.idx">
              {{ seeker.name }}
            </li>
          </ul>
        </td>
        <td>
          <button class="create-video-interview" @click="createVideoInterview(schedule.uuid, schedule, $event)">방 생성
          </button>
        </td>
      </tr>
      </tbody>
    </table>

    <div id="size-buttons">
      <button v-for="page in totalPages" :key="page" @click="handlePageClick(page)">{{ page }}</button>
    </div>
  </div>
</template>

<style scoped>
.review-table th:nth-child(1) {
  width: 10%;
}

.review-table th:nth-child(2) {
  width: 20%;
}

.review-table th:nth-child(3) {
  width: 20%;
}

.review-table th:nth-child(4) {
  width: 10%;
}

.review-table th:nth-child(5) {
  width: 20%;
}

.review-table th:nth-child(6) {
  width: 10%;
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

.review-table tr:hover {
  background-color: #e6e6e6;
  cursor: pointer;
}

.create-video-interview {
  background-color: #212b36;
  color: white;
  padding: 10px;
  border-radius: 5px;
}
</style>