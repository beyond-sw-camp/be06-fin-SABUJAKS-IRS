<template>
  <div class="body-n">
    <SeekerHeaderComponent></SeekerHeaderComponent>
    <div class="main_div">
      <div class="container-n pt-150">
        <!-- 사이드 바 -->
        <SeekerSideBarComponent></SeekerSideBarComponent>

        <!-- 메인 컨텐츠 -->
        <div class="main-content">
          <div class="header">
            <h1>알림 관리</h1>
          </div>

          <div class="content">
            <div class="timeline">
              <!-- 알림 리스트 반복 출력 -->
              <div v-for="(alarm, index) in alarms" :key="alarm.idx" class="timeline-item">
                <div class="timeline-date">{{ formatDate(alarm.createdAt) }}</div>
                <div class="timeline-content row" :class="{ read: alarm.status === true }"
                  @click="toggleDetail(index, alarm.idx)">
                  <div class="margin-v-auto">
                    {{ alarm.type }}
                  </div>
                  <div class="ml-auto">
                    <button class="schedule-btn" @click.stop="openScheduleModal(alarm)">면접 일정 조율</button>
                  </div>
                </div>

                <!-- 상세 내용 (숨겨진 상태에서 애니메이션으로 열림) -->
                <transition name="fade">
                  <div
                    :style="{ maxHeight: isDetailOpen(index) ? '100%' : '0px', padding: isDetailOpen(index) ? '50px 0' : '0px' }"
                    class="timeline-detail">
                    <div class="margin-v-auto" v-html="alarm.message"></div>
                  </div>
                </transition>

              </div>
            </div>

            <!-- 모달창 HTML -->
            <div v-if="isModalOpen" id="myModal" class="modal">
              <div class="modal-content" id="draggableModal">
                <span class="close" @click="closeModal">&times;</span>
                <h2>일정 조율</h2>
                <div class="col-12 modal-section">
                  <div class="form-group">
                    <div class="col-12">
                      <label for="start-date" class="subtitle mb-30">시작 날짜<span class="required">*</span></label>
                      <input type="date" id="start-date" v-model="selectedDates[0]">
                    </div>
                  </div>
                  <div class="form-group">
                    <div class="col-12">
                      <label for="end-date" class="subtitle mb-30">종료 날짜<span class="required">*</span></label>
                      <input type="date" id="end-date" v-model="selectedDates[1]">
                    </div>
                  </div>
                </div>
                <div class="col-12">
                  <button class="submit-button" @click="submitSchedule">등록</button>
                </div>
              </div>
            </div>
          </div>

        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import SeekerHeaderComponent from "@/components/seeker/SeekerHeaderComponent.vue";
import SeekerSideBarComponent from "@/components/seeker/SeekerSideBarComponent.vue";
import { UseMypageNotificationStore } from "@/stores/UseMypageNotificationStore";
import { UseInterviewScheduleStore } from "@/stores/UseInterviewScheduleStore";
import { UseAuthStore } from '@/stores/UseAuthStore';
// import { useRouter } from 'vue-router';

const authStore = UseAuthStore();
// const router = useRouter();

const mypageNotificationStore = UseMypageNotificationStore();
const interviewScheduleStore = UseInterviewScheduleStore();
const alarms = ref([]);
const isModalOpen = ref(false);
const selectedDates = ref([null, null]);  // 두 개의 날짜를 저장
const selectedAlarm = ref(null);

// 알림 리스트 불러오기
onMounted(async () => {
  alarms.value = await mypageNotificationStore.readAllAlarm();

  if (authStore.isLoggedIn) {
    if (alarms.value) {
      // 최신순으로 정렬 (createdAt을 기준으로 내림차순)
      alarms.value.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
    }
  } 


});

// 모달 열기
const openScheduleModal = (alarm) => {
  selectedAlarm.value = alarm;
  console.log(selectedAlarm.value);
  isModalOpen.value = true;
};

// 모달 닫기
const closeModal = () => {
  isModalOpen.value = false;
  selectedAlarm.value = null;
  selectedDates.value = [null, null];  // 모달 닫을 때 날짜 초기화
};

// 면접 일정 제출
const submitSchedule = async () => {
  if (selectedDates.value[0] && selectedDates.value[1] && selectedAlarm.value) {
    // selectedAlarm.value에서 interviewScheduleIdx만 추출
    const interviewScheduleIdx = selectedAlarm.value.interviewScheduleIdx;

    const scheduleData = {
      interviewStart: selectedDates.value[0],
      interviewEnd: selectedDates.value[1],
      interviewScheduleIdx: interviewScheduleIdx  // 인터뷰 일정 ID만 포함
    };

    // createReSchedule 메서드 호출 (적절한 API 호출로 변경)
    const result = await interviewScheduleStore.createReSchedule(scheduleData);

    if (result) {
      alert("일정 조율 요청이 완료되었습니다.");
      closeModal();
    } else {
      alert("일정 조율 요청에 실패했습니다. 관리자에게 문의하세요.");
    }

  } else {
    alert('날짜와 알람 정보를 모두 입력해 주세요.');
  }
};


// 날짜 포맷팅 함수
const formatDate = (dateString) => {
  const [datePart, timePart] = dateString.split('T');
  const [year, month, day] = datePart.split('-');
  const [hours, minutes] = timePart.split(':');

  return `${year}-${month}-${day} ${hours}:${minutes}`;
};

const detailOpen = ref([]);

const toggleDetail = async (index, alarmIdx) => {
  detailOpen.value[index] = !detailOpen.value[index];

  // 상태를 서버에 업데이트
  const isUpdated = await mypageNotificationStore.updateStatus(alarmIdx);

  if (isUpdated) {
    // 상태가 업데이트되면, 로컬 상태를 업데이트
    alarms.value = alarms.value.map(alarm =>
      alarm.idx === alarmIdx ? { ...alarm, status: true } : alarm
    );
  }
};

const isDetailOpen = (index) => {
  return detailOpen.value[index];
};


</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.body-n {
  font-family: 'Arial', sans-serif;
  background-color: white;
  justify-content: center;
  align-items: center;
  height: 100vh;
}

.main_div {
  width: 100%;
  background-color: rgba(255, 255, 255, 0);
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px 0;
  /* 수직 간격을 추가 */
}

.container-n {
  display: flex;
  width: 100%;
  max-width: 1200px;
  background-color: rgba(255, 255, 255, 0);
  /* box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); */
  border-radius: 10px;
  margin: 20px;
  /* 수직, 수평 여백 추가 */
  gap: 20px;
  /* 사이드바와 메인 컨텐츠 사이의 간격 추가 */
}

/* 타임라인 스타일 */
.timeline {
  position: relative;
  padding: 20px 0;
}

.timeline-item :hover {
  cursor: pointer;
}

.timeline-item {
  position: relative;
  padding-left: 30px;
  margin-bottom: 20px;
}

.timeline-item::before {
  content: '';
  position: absolute;
  left: 15px;
  top: 0;
  width: 10px;
  height: 10px;
  background-color: #007bff;
  border-radius: 50%;
}

.timeline-date {
  font-size: 12px;
  color: #999;
  margin-bottom: 5px;
}

.timeline-content {
  background-color: white;
  padding: 15px;
  border-radius: 5px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.delete-btn {
  background-color: white;
  color: #555;
  padding: 8px 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
  cursor: pointer;
}

.delete-btn:hover {
  background-color: #f2f2f2;
}

.main-content {
  width: 80%;
  padding: 30px;
  border-radius: 10px;
  background-color: white;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.header h1 {
  font-size: 24px;
  font-weight: bold;
}


/* 모달창 기본 스타일 */
.modal {
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

.modal-content {
  background-color: white;
  padding: 40px;
  border-radius: 8px;
  width: 30%;
}

.modal-section {
  flex: 1;
  /* 이 부분이 모달의 컨텐츠를 채우도록 설정 */
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

h2 {
  margin: 0 0 20px;
}

.form-group {
  margin-bottom: 25px;
}

label {
  display: block;
  margin-bottom: 15px;
  font-size: 1.1rem;
  font-weight: 600;
}

label .subtitle {
  font-weight: 400 !important;
}

input[type="text"] {
  width: calc(100% - 40px);
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.add-button,
.add-email {
  padding: 10px;
  background-color: #232b16;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.required {
  color: red;
}

.schedule-btn {
  background-color: white;
  color: #555;
  padding: 8px 16px;
  border: 1px solid #ddd;
  border-radius: 5px;
  cursor: pointer;
}

.schedule-btn:hover {
  background-color: #f2f2f2;
}

.timeline-detail {
  max-height: 0;
  overflow: hidden;
  transition: max-height 0.5s ease, padding 0.5s ease;
}

.fade-enter-active,
.fade-leave-active {
  transition: max-height 0.5s ease, padding 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  max-height: 500px;
  /* 최대 높이를 조정 */
  padding: 10px 0;
  /* 여백 추가 */
}

.read {
  color: gray;
  /* 읽은 알림은 글자색이 회색으로 */
}

.submit-button {
  width: 100%;
  margin-top: auto;
  padding: 10px;
  background-color: #232b16;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.main_div {
  width: 100%;
  background-color: #ffffff;
  /* 배경색 설정 */
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px 0;
}
</style>