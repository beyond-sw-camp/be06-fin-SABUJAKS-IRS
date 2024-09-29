<template>
  <MainHeaderComponent></MainHeaderComponent>
    <div class="container">
      <MainSideBarComponent></MainSideBarComponent>
        <div id="content">
            <h1>지원서 관리</h1>
            <table class="">
                <tbody>
                <tr>
                  <th>번호</th>
                  <th>공고 기간</th>
                  <th>공고명</th>
                  <th>신입/경력</th>
                  <th>지원자수</th>
                </tr>
                <tr
                    v-for="(announcement, index) in announcementStore.announcements"
                    :key="index"
                    @click="handleRowClick(announcement)"
                    style="cursor: pointer"
                >
                <td>{{ index + 1 }}</td>
                <td>{{ formatDate(announcement.announcementStart) }} - {{ formatDate(announcement.announcementEnd) }}</td>
                <td>{{ announcement.announcementTitle }}</td>
                <td>{{ announcement.careerBase }}</td>
                <td>{{ announcement.seekerNum }}</td>
              </tr>
                </tbody>
            </table>
            <!-- <div id="size-buttons">
                <button>1</button>
                <button>2</button>
                <button>3</button>
            </div> -->
        </div>
    </div>
</template>

<script setup>
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import { UseAnnouncementStore } from '@/stores/UseAnnouncementStore';
import { useRouter } from 'vue-router';
import { onMounted } from 'vue';

const announcementStore = UseAnnouncementStore();
const router = useRouter();

const formatDate = (dateString) => {
  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = (`0${date.getMonth() + 1}`).slice(-2); // 두 자리수로 유지
  const day = (`0${date.getDate()}`).slice(-2);
  return `${year}.${month}.${day}`;
};

// 컴포넌트가 로드될 때 데이터를 가져옴
onMounted(() => {
  announcementStore.fetchAnnouncements();
});

const handleRowClick = (announcement) => {
    router.push(`/recruiter/resume/list/${announcement.announcementIdx}`);
};
</script>

<style>
.container {
  width: 80%;
  margin: 0 auto;
}

#content {
  flex: 1;
  margin-left: 200px; /* 사이드바 너비만큼 왼쪽 여백 추가 */
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

.button-container {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 40px;
}

.register-button {
  width: 100px;
  background-color: #f1f1f1;
  border: 1px solid #ddd;
  border-radius: 10px;
  padding: 10px 20px;
  font-size: 0.8rem;
  font-weight: bold;
  color: #000;
  cursor: pointer;
  transition: background-color 0.3s;
}

.register-button:hover {
  background-color: #e0e6ed;
}

#size-buttons {
  display: flex;
  justify-content: center;
}

#size-buttons button {
  margin-right: 10px;
  padding: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

#size-buttons button:hover {
  background-color: #ddd;
}

</style>