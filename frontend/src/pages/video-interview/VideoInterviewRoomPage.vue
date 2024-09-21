<template>
  <div>
    <!-- 지원자 화면 -->
    <header class="header">
      <img class="header-logo" src="../../assets/img/irs_white.png" />
      <div>
        <button @click="leaveSession" class="exitBtn" type="button" id="buttonLeaveSession" value="Leave session" >
        면접 나가기
        </button>
        <button @click="handleTogglePubsAudio" class="soundBtn">
          {{ audioMuted ? '음소거 해제' : '음소거' }}
        </button>
      </div>

    </header>
    <div v-if="(session != null) && (userType == 'ROLE_SEEKER')" class="seeker-wrapper" >
        <div id="video-container" class="video-container">
          <UserVideo 
            class="video" 
            :isSubscriber="false" 
            :stream-manager="publisher" 
            @click="updateMainVideoStreamManager(publisher)" />
            <UserVideo 
            class="video" 
            :isSubscriber="true" 
            v-for="sub in subscribers" 
            :key="sub.stream.connection.connectionId" 
            :stream-manager="sub"
            :audio-muted="sub.audioMuted"
            @toggle-audio="handleToggleSubsAudio" 
            @click="updateMainVideoStreamManager(sub)"
          />
        </div>
        <!-- <div id="main-video" class="main-video-container">
          <UserVideo class="video" :stream-manager="mainStreamManager" />
        </div> -->
    </div>
    <div v-if="(session != null) && (userType == 'ROLE_ESTIMATOR')" class="estimator-wrapper">
      <div id="video-container" class="video-container">
          <UserVideo 
            class="video" 
            :isSubscriber="false" 
            :stream-manager="publisher" 
            @click="updateMainVideoStreamManager(publisher)" />
            <UserVideo 
            class="video" 
            :isSubscriber="true" 
            v-for="sub in subscribers" 
            :key="sub.stream.connection.connectionId" 
            :stream-manager="sub"
            :audio-muted="sub.audioMuted"
            @toggle-audio="handleToggleSubsAudio" 
            @click="updateMainVideoStreamManager(sub)"
          />
        </div>
      <div class="evaluate-container">
        <div class="evaluate-seeker-list">
          <div v-for="testUser in testUsers" :key="testUser.index" class="evaluate-seeker">
            <span @click="handleClickSeeker(testUser)" class="evaluate-button">{{ testUser }}</span>
          </div>
        </div>
        <div v-if="showEvaluateMenus" class="evaluate-menu">
          <button  class="evaluate-button">지원서 보기</button>
          <div class="resume">
          
          </div>
          <button @click="showEvaluate" class="evaluate-button">면접자 평가</button>
          <div v-show="showEvaluateForm" class="evaluate">
            <label>{{ evaluateForm.q1 }}</label>
            <label>{{ evaluateForm.q2 }}</label>
            <label>{{ evaluateForm.q3 }}</label>
            <label>{{ evaluateForm.q4 }}</label>
            <label>{{ evaluateForm.q5 }}</label>
            <label>{{ evaluateForm.q6 }}</label>
            <label>{{ evaluateForm.q7 }}</label>
            <label>{{ evaluateForm.q8 }}</label>
            <label>{{ evaluateForm.q9 }}</label>
            <label>{{ evaluateForm.q10 }}</label>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute, useRouter } from "vue-router";
import { UseVideoInterviewStore } from "@/stores/UseVideoInterviewStore";
import { UseAuthStore } from "@/stores/UseAuthStore";
import { UseInterviewEvaluateStore } from "@/stores/UseInterviewEvaluateStore";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/components/video-interview/UserVideo.vue";
import { useToast } from "vue-toastification";

const OV = ref(null);
const session = ref(null);
let mainStreamManager = ref(null);
const publisher = ref(null);
const subscribers = ref([]);
const authStore = UseAuthStore();
const videoInterviewStore = UseVideoInterviewStore();
const interviewEvaluateStore = UseInterviewEvaluateStore();
const route = useRoute();
const router = useRouter()
const toast = useToast();
const userName = ref("");
const userType = ref("");
const evaluateForm = ref({q1: "", q2: "", q3: "", q4: "", q5: "", q6: "", q7: "", q8: "", q9: "", q10: ""});
const audioMuted = ref(false);
const testUsers = ref(["박종성", "구은주", "서재은", "서시현"]);
const showEvaluateMenus = ref(false);
const showEvaluateForm = ref(false);
const currentUser = ref(null);
const handleClickSeeker = async (testUser) => {
  currentUser.value = testUser;
  if(showEvaluateMenus.value == false)showEvaluateMenus.value = true;
  else( showEvaluateMenus.value = false)
}
const showEvaluate = () => {
  showEvaluateForm.value = !showEvaluateForm.value;
};

onMounted(async() => {
  await joinSession(route.params.announceUUID, route.params.videoInterviewUUID);
  await getInterviewForm(route.params.announceUUID, route.params.videoInterviewUUID)
});

const getInterviewForm = async (announceUUID, videoInterviewUUID) => {
  try {
    const response = await interviewEvaluateStore.searchFormforEstimator(announceUUID, videoInterviewUUID)
    evaluateForm.value.q1 = response.result.q1;
    evaluateForm.value.q2 = response.result.q2;
    evaluateForm.value.q3 = response.result.q3;
    evaluateForm.value.q4 = response.result.q4;
    evaluateForm.value.q5 = response.result.q5;
    evaluateForm.value.q6 = response.result.q6;
    evaluateForm.value.q7 = response.result.q7;
    evaluateForm.value.q8 = response.result.q8;
    evaluateForm.value.q9 = response.result.q9;
    evaluateForm.value.q10 = response.result.q10;
  } catch (error) {
    toast.error(error);
  }
}
const handleToggleSubsAudio = (connection) => {
  if (session.value) {
    const subscriber = subscribers.value.find(sub => sub.stream.connection.connectionId === connection.connectionId);
    if (subscriber) {
      const isAudioEnabled = subscriber.stream.getMediaStream().getAudioTracks().some(track => track.enabled);
      
      subscriber.stream.getMediaStream().getAudioTracks().forEach(track => {
        track.enabled = !isAudioEnabled;
      });

      subscriber.audioMuted = !isAudioEnabled;
    }
  }
};


const handleTogglePubsAudio = () => {
  audioMuted.value = !audioMuted.value;
  if (publisher.value) {
    publisher.value.publishAudio(!audioMuted.value);
  }
};

const handleSessionToken = async (announceUUID, videoInterviewUUID) => {
  try {
    userName.value = authStore.name
    userType.value = authStore.role
    console.log(announceUUID, videoInterviewUUID, userName.value, userType.value)
    const requestBody = {
      announceUUID: announceUUID,
      videoInterviewUUID: videoInterviewUUID,
      params: { customSessionId: videoInterviewUUID },
    };
    const response = await videoInterviewStore.getSessionToken(requestBody);
    return response.result.sessionToken;
  } catch (error) {
    toast.error(error);
  }
};

const updateMainVideoStreamManager = (stream) => {
  if (mainStreamManager.value === stream) {
    return;
  } else {
    mainStreamManager.value = stream;
  }
};

const joinSession = async (announceUUID, videoInterviewUUID) => {
  try {
    OV.value = new OpenVidu();
    session.value = OV.value.initSession();
    session.value.on("streamCreated", ({ stream }) => {
      const subscriber = session.value.subscribe(stream);
      subscribers.value.push(subscriber);
    });
    session.value.on("streamDestroyed", ({ stream }) => {
      const index = subscribers.value.indexOf(stream.streamManager, 0);
      if (index >= 0) { subscribers.value.splice(index, 1); } });
      session.value.on("exception", ({ exception }) => { console.warn(exception); 
    });
    const token = await handleSessionToken(announceUUID, videoInterviewUUID);
    if (!token || typeof token !== "string") { throw new Error("유효하지 않은 세션 토큰입니다."); }

    console.log(`${videoInterviewUUID} Session에 접속중: ${token}`);
    await session.value.connect(token, { clientData: userName.value });
    publisher.value = OV.value.initPublisher(undefined, {
      audioSource: undefined,
      videoSource: undefined,
      publishAudio: true,
      publishVideo: true,
      resolution: "640x480",
      frameRate: 30,
      insertMode: "APPEND",
      mirror: false,
    });
    mainStreamManager.value = publisher.value;
    await session.value.publish(publisher.value);
    toast.success("면접방에 오신 걸 환영합니다.\n지원자는 마이크를 끄고 대기해주시길 바랍니다.");
  } catch (error) {
    router.push(`/video-interview/${route.params.announceUUID}`)
    toast.error("지원자는 정해진 시간에 정해진 면접방과 일정에 맞춰 참여 바랍니다.");
  }
  window.addEventListener("beforeunload", leaveSession);
};

const leaveSession = () => {
  if (session.value) session.value.disconnect();
  session.value = undefined;
  mainStreamManager.value = undefined;
  publisher.value = undefined;
  subscribers.value = [];
  OV.value = undefined;
  window.removeEventListener("beforeunload", leaveSession);
  router.push(`/video-interview/${route.params.announceUUID}`)
};
</script>

<style scoped>
.header {
  background-color: #f8f9fa; 
  color: #333; 
  padding: 20px; 
  text-align: center;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100px;
  width: 100%;
  background-color: #212b36;
  padding: 0 20px;
  box-sizing: border-box;
  position: fixed;
  z-index: 2;
}

.header-logo {
  color: white;
  font-size: 24px;
  font-weight: bold;
  width: 150px;
}

.exitBtn {
  background-color: white;
  color: red;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 16px;
  margin-right: 10px
}

.soundBtn{
  background-color: white;
  color: black;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 16px;
}

button:hover {
  opacity: 70%;
}

.wrapper {
  background-color: #f1f1f1;
  width: 100%;
  height: 100%;
  padding: 0;
  display: block;
  flex-direction: row;
  margin: 0 auto;
}

.seeker-wrapper {
  position: absolute;
  width: 100%;
  height: 100%;
  padding: 0;
  display: flex;
  flex-direction: column;
  margin: 100px 0;
}

.estimator-wrapper {
  position: absolute;
  width: 100%;
  height: 100%;
  padding: 0;
  display: flex;
  flex-direction: row;
  margin: 100px 0;
}

.video-container {
  position: relative;
  margin: 20px 0;
  height: min-content;
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  gap: 20px;
}

.video {
  position: relative;
  object-fit: cover;
  height: auto;
  max-width: 100%;
  border: 1px solid #ddd;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  border-radius: 10px;
}

.video-menu {
  position: absolute; /* 버튼을 절대 위치로 설정 */
  top: 5px; /* 비디오 상단에서의 위치 */
  right: 5px; /* 비디오 오른쪽에서의 위치 */
  background-color: rgba(0, 0, 0, 0.5); /* 배경색 */
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  font-size: 18px;
  text-align: center;
  cursor: pointer; /* 커서 스타일 */
  z-index: 999; /* 버튼을 다른 요소 위로 */
}

.evaluate-container {
  position: relative;
  top: 0;
  right: 0;
  /* -ms-overflow-style: none; */
  /* scrollbar-width: none;  */
  display: flex;
  flex-direction: column;
  width: 100%;
  float: left;
  box-sizing: border-box;
  background: black;
  color: black;
  overflow-y: scroll;
  background: white;
  padding: 18px;
}

.evaluate-menu {
  display: flex;
  justify-content: center;
  align-items: center;
  column-gap: 5px;
}

.evaluate-button {
  width: 100%;
  height: fit-content;
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

.button:hover {
  background-color: #ddd;
}

.evaluate-seeker-list {
  display: flex;
  justify-content: center;
  padding: 20px;
  align-items: center;
  column-gap: 5px;
  margin: 10px;
}

.evaluate-content {
  display: flex;
  flex-direction: column;
}

.evaluate-container::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}



</style>