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
            @click="updateMainVideoStreamManager(publisher)" 
          />
          <UserVideo 
            class="video" 
            :isSubscriber="true" 
            v-for="sub in subscribers" 
            :key="sub.stream.connection.connectionId" 
            :stream-manager="sub"
            :audio-muted="sub.audioMuted"
            @handle-toggle-audio="handleToggleSubsAudio" 
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
            @click="updateMainVideoStreamManager(publisher)"
          />
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
          <div v-for="user in Object.values(resumeList)" :key="user.personalInfo.name" class="evaluate-seeker">
          <span 
            @click="handleShowEvaluateMenus(user)" 
            :class="['evaluate-button', { 'active-button': currentUser === user }]">
            {{ user.personalInfo.name }}
        </span>
      </div>

        </div>
        <div v-if="showEvaluateMenus" class="evaluate-menu">
          <button 
            @click="handleShowResumeInfo" 
            :class="['evaluate-button', { 'active-button': showResumeInfo }]">
            지원서 보기</button>
          <button 
            @click="handleShowEvaluateForm" 
            :class="['evaluate-button', { 'active-button': showEvaluateForm }]">
            면접자 평가</button>
        </div>
        <div v-if="showEvaluateMenus && showResumeInfo" class="resume">
            {{ currentUserResume }}
          </div>
          <div v-if="showEvaluateMenus && showEvaluateForm" class="evaluate">
            <div v-if="showEvaluateMenus && currentUser">
              <h3>
                  {{ currentUser.personalInfo.name }}의 평가
                  (합계: {{ calculateTotalScore(currentUser.personalInfo.name) }})
              </h3>
              <div v-for="(question, index) in Object.entries(evaluateForm)" :key="index+1" class="evaluate-form-item">
                <label v-if="question[1]">{{ question[1] }}</label>
                <div v-if="question[1]" class="score-options">
                  <div v-for="score in [1, 2, 3, 4, 5]" :key="score" class="score-option">
                    <input
                      type="radio"
                      :value="score"
                      v-model="currentUserScores[currentUser.personalInfo.name][index+1]"
                    />
                    <label>{{ score }}</label>
                  </div>
                </div>
              </div>
              <div class="comment-section">
              <label for="comment">의견:</label>
              <textarea
                id="comment"
                v-model="currentUserComments[currentUser.personalInfo.name]"
                rows="4"
                placeholder="의견을 입력해주세요"
              ></textarea>
            </div>
              <button class="evaluate-submitbtn" @click="handleCreateEvaluate(currentUser)">평가 제출</button>
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

const route = useRoute();
const router = useRouter()
const toast = useToast();

const authStore = UseAuthStore();
const videoInterviewStore = UseVideoInterviewStore();
const interviewEvaluateStore = UseInterviewEvaluateStore();

const OV = ref(null);
const session = ref(null);
let mainStreamManager = ref(null);
const publisher = ref(null);
const subscribers = ref([]);

const audioMuted = ref(false);
const showEvaluateMenus = ref(false);
const showEvaluateForm = ref(false);
const showResumeInfo = ref(false);
const userName = ref("")
const userType = ref("")
const evaluateForm = ref({});

const resumeList = ref({});
const currentUser = ref({});
const currentUserResume = ref("");
const currentUserScores = ref({});
const currentUserComments = ref({});

onMounted(async() => {
  await joinSession(route.params.announceUUID, route.params.videoInterviewUUID);
  if(userType.value == "ROLE_ESTIMATOR") {
    await handleGetInterviewForm(route.params.announceUUID, route.params.videoInterviewUUID)
    await handleReadAllResumeInfo(route.params.announceUUID, route.params.videoInterviewUUID)
  }
});

const handleShowEvaluateMenus = async (user) => {
  if(currentUser.value != user) {
    showEvaluateMenus.value = true;
  } else {
    showEvaluateMenus.value = !showEvaluateMenus.value; 
  }
  if (!currentUserScores.value[user.personalInfo.name]) {
    currentUserScores.value[user.personalInfo.name] = {};
  }
  if (!currentUserComments.value[user.personalInfo.name]) {
      currentUserComments.value[user.personalInfo.name] = "";
  }
  currentUser.value = user;
  currentUserResume.value = JSON.stringify(user, null, 2);
}

const handleShowEvaluateForm = () => {
  if(showResumeInfo.value) {
    showResumeInfo.value = false;
    showEvaluateForm.value = true;
  } else {
    showEvaluateForm.value = !showEvaluateForm.value;
  }
};

const handleShowResumeInfo = () => {
  if(showEvaluateForm.value) {
    showEvaluateForm.value = false;
    showResumeInfo.value = true;
  } else {
    showResumeInfo.value = !showResumeInfo.value;
  }
}

const calculateTotalScore = (userName) => {
  const scores = currentUserScores.value[userName];
  return Object.values(scores).reduce((total, score) => total + (score || 0), 0);
};

const handleCreateEvaluate = async(user) => {
  const userName = user.personalInfo.name;
  const scores = currentUserScores.value[userName] || {};
  const totalScore = calculateTotalScore(userName);
  const comments = currentUserComments.value[userName] || "";
  const requestBody = {
    userEmail: user.personalInfo.email,
    scores: scores,
    totalScore: totalScore,
    comments: comments,
    announcementUUID: route.params.announceUUID,
    videoInterviewUUID: route.params.videoInterviewUUID
  };
  console.log(requestBody);
  const response = await interviewEvaluateStore.createEvaluate(requestBody);
  if(response.success) { toast.success(`${userName} 지원자의 면접 평가가 등록되었습니다.`) } 
  else { toast.error(`${userName} 지원자의 면접 평가가 등록에 실패했습니다.`) }
};


const handleGetInterviewForm = async (announceUUID, videoInterviewUUID) => {
    const response = await interviewEvaluateStore.searchForm(announceUUID, videoInterviewUUID)
    if(response.success) {
      evaluateForm.value = response.result
      toast.success("면접 평가항목을 불러오는데 성공했습니다.")
    } else {
      toast.error("면접 평가항목이 존재하지 않습니다.")
    }
}

const handleReadAllResumeInfo = async(announceUUID, videoInterviewUUID) => {
  const response = await interviewEvaluateStore.readAllResumeInfo(announceUUID, videoInterviewUUID)
  if(response.success) {
    resumeList.value = response.result.interviewEvaluateReadResumeInfoResMap
    toast.success("지원자들의 지원서 정보를 불러오는데 성공했습니다.")
  } else {
    toast.error("지원자들의 지원서 정보를 불러오는데 실패했습니다.")
  }
}

const handleToggleSubsAudio = (connection) => {
  if (session.value) {
    const subscriber = subscribers.value.find(sub => sub.stream.connection.connectionId === connection.connectionId);
    if (subscriber) {
      const isAudioEnabled = subscriber.stream.getMediaStream().getAudioTracks().some(track => track.enabled);
      subscriber.stream.getMediaStream().getAudioTracks().forEach(track => { track.enabled = !isAudioEnabled; });
      subscriber.audioMuted = !isAudioEnabled;
    }
  }
};

const handleTogglePubsAudio = () => {
  audioMuted.value = !audioMuted.value;
  if (publisher.value) { publisher.value.publishAudio(!audioMuted.value); }
};

const handleSessionToken = async (announceUUID, videoInterviewUUID) => {
  try {
    userName.value = authStore.userInfo.name;
    userType.value = authStore.userInfo.role;
    const requestBody = {
      announceUUID: announceUUID,
      videoInterviewUUID: videoInterviewUUID,
      params: { customSessionId: videoInterviewUUID },
    };
    console.log('1')
    const response = await videoInterviewStore.getSessionToken(requestBody);
    console.log(`Session token: ${response.result.sessionToken}`);
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
    console.log('2');
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
    await session.value.connect(token, { clientData: userName.value },);
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
    console.log(error);
    // router.push(`/video-interview/${route.params.announceUUID}`)
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
  position: absolute;
  top: 5px;
  right: 5px;
  background-color: rgba(0, 0, 0, 0.5); 
  color: white;
  padding: 5px 10px;
  border-radius: 5px;
  font-size: 18px;
  text-align: center;
  cursor: pointer;
  z-index: 999; 
}

.evaluate-container {
  position: relative;
  top: 0;
  right: 0;
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

.evaluate-container::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
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

.active-button {
  background-color: #007bff !important;
  color: white !important;
  border: 1px solid #007bff !important;
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

.evaluate {
  display: flex;
  flex-direction: column;
  gap: 10px;
}
.evaluate-form-item{
  margin: 10px;
}

.score-options {
  display: flex;
  gap: 10px;
  margin-top: 20px;
  align-items: center;
  justify-content: space-between;
}

.score-option {
  display: flex;
  flex-direction: row;
}
.score-option label {
  display: flex;
  align-items: center;
  cursor: pointer;
}

.score-option input[type="radio"] {
  margin-right: 5px;
}

.evaluate-submitbtn{
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

.comment-section {
  margin: 20px 0;
}

.comment-section textarea {
  margin: 20px 0;
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  resize: none;
}
</style>