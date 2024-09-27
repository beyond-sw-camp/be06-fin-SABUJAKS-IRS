<script setup>
import {defineProps, defineEmits, computed, ref} from 'vue';

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

const handlePageClick = (pageNumber) => {
  currentPage.value = pageNumber;
  emit('loadInterviewScheduleList', pageNumber);
  window.scrollTo({top: 0});
};

// 각 페이지의 시작 번호 계산
const startNumberForPage = computed(() => {
  return props.totalInterviewSchedules - (currentPage.value - 1) * postsPerPage;
});

// 현재 페이지에 맞는 면접 일정 slice
const paginatedInterviewSchedules = computed(() => {
  return props.interviewSchedules;
});

const handleRowClick = (type) => {
  emit('openModal', type);
}

const createVideoInterview = (uuid) => {
  emit('createVideoInterview', uuid);
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

</script>

<template>
  <div id="content">
    <h1>{{ props.title }}</h1>
    <div class="col-12">
      <div class="interview-add">
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
      <tr v-for="(schedule, index) in paginatedInterviewSchedules" :key="schedule.uuid">
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
          <button @click="createVideoInterview(schedule.uuid)">방 생성</button>
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
</style>
