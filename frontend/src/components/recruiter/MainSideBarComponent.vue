<script setup>
import { ref } from 'vue';
import { useRouter } from 'vue-router';

// 면접 하위메뉴 토글 상태 관리
const isInterviewMenuOpen = ref(true);

// 메뉴 클릭 시 토글 함수
const toggleInterviewMenu = () => {
  isInterviewMenuOpen.value = !isInterviewMenuOpen.value;
};

const router = useRouter();

const goAndReload = (path) => {
  router.push(path).then(() => {
    window.location.reload();
  });
};

</script>

<template>
  <div class="sidebar">
    <ul>
      <li @click="goAndReload('/recruiter/announce')">
        공고
      </li>
      <li @click="goAndReload('/recruiter/resume')">
        지원서
      </li>
      <!-- 면접 메뉴 클릭 시 하위메뉴 표시 및 화살표 아이콘 변경 -->
      <li @click="toggleInterviewMenu" class="interview">
        면접
        <span class="icon">
          ▼
        </span>
      </li>
      <ul v-if="isInterviewMenuOpen" class="submenu">
        <li @click="goAndReload('/recruiter/interview-schedule/new')">
          신입
        </li>
        <li @click="goAndReload('/recruiter/interview-schedule/exp')">
          경력
        </li>
        <li @click="goAndReload('/recruiter/interview-schedule/reschedule')">
          일정조율
        </li>
        <li @click="goAndReload('/recruiter/interview-evaluate/form')">
          면접 평가표
        </li>
        <li @click="goAndReload('/recruiter/interview-evaluate/result')">
        면접 평가
      </li>
      </ul>

      <li @click="goAndReload('/recruiter/total-process')">
        최종결과
      </li>
      <li @click="goAndReload('/recruiter/mypage')">
        마이페이지
      </li>
    </ul>
  </div>
</template>

<style scoped>
.sidebar {
  width: 200px;
  height: 100%; /* 사이드바가 전체 높이를 차지하도록 */
  background-color: #212b36;
  color: white;
  padding-top: 20px;
  box-sizing: border-box;
  position: fixed; /* 사이드바를 고정하여 화면 왼쪽 끝까지 차지하도록 */
  top: 100px; /* header 높이만큼 아래에 위치하도록 */
  left: 0;
  border-radius: 0;
  z-index: 1000;
}

.sidebar img {
  width: 40px;
  margin-bottom: 20px;
}

.sidebar a {
  color: #ffffff;
  text-decoration: none;
  margin: 10px 0;
}

.sidebar ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.sidebar li {
  padding: 15px 40px;
  cursor: pointer;
}

.sidebar li:hover {
  background-color: #1a232d;
}

/* 하위 메뉴 스타일 */
.submenu {
  padding-left: 60px !important; /* 하위 메뉴 들여쓰기 */
}

.submenu li {
  padding: 10px 0;
}

.icon {
  font-size: 0.7rem;
}

</style>
