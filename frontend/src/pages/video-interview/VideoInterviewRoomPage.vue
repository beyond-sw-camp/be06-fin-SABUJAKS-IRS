<template>
  <div>
    <!-- 지원자 화면 -->
    <header class="header">
      <img class="header-logo" src="../../assets/img/irs_white.png" />
      <button 
        class="exitbtn" type="button" id="buttonLeaveSession" 
        @click="leaveSession" value="Leave session" >
        면접 나가기
      </button>
    </header>
    <div v-if="(session != null) & (userType == 'ROLE_SEEKER')" class="seeker-wrapper" >
      <div class="vip-video">
        <div id="video-container" class="col-md-6">
          <UserVideo :stream-manager="publisher" @click="updateMainVideoStreamManager(publisher)" />
          <UserVideo v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" 
            @click="updateMainVideoStreamManager(sub)"
          />
        </div>
      </div>
      <div class="vip-video">
        <div id="main-video" class="col-md-6">
          <p>abcd</p>
          <UserVideo :stream-manager="mainStreamManager" />
        </div>
      </div>
    </div>
    <div v-if="(session != null) & (userType == 'ROLE_ESTIMATOR')" class="vie-wrapper"
    >
      <div class="vie-video">
        <img class="vie-img" src="../../assets/img/irs_black.png" alt="" />
      </div>
      <div class="vie-evaluate">
        <div class="vie-menu">
          <button class="vie-menubtn">지원자 정보</button>
          <button class="vie-menubtn">지원서 보기</button>
          <button class="vie-menubtn">면접자 평가</button>
        </div>
        <div class="vie-content"></div>
      </div>
    </div>
  </div>
  <!-- <div class="vip-wrapper">
    <div id="session" v-if="session">
        <div id="video-container" class="col-md-6">
            <UserVideo :stream-manager="publisher" @click="updateMainVideoStreamManager(publisher)" />
            <UserVideo v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click="updateMainVideoStreamManager(sub)" />
        </div>
    </div> -->
  <!-- <div id="session" v-if="session && userType=='estimator'">
        <div id="session-header">
          <input class="btn btn-large btn-danger" type="button" id="buttonLeaveSession" @click="leaveSession" value="Leave session" />
      </div>
        <div id="main-video" class="col-md-6">
          <UserVideo :stream-manager="mainStreamManager" />
        </div>
        <div id="video-container" class="col-md-6">
          <UserVideo :stream-manager="publisher" @click="updateMainVideoStreamManager(publisher)" />
          <UserVideo v-for="sub in subscribers" :key="sub.stream.connection.connectionId" :stream-manager="sub" @click="updateMainVideoStreamManager(sub)" />
        </div>
    </div> -->
</template>

<script setup>
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { UseVideoInterviewStore } from "@/stores/UseVideoInterviewStore";
import { OpenVidu } from "openvidu-browser";
import UserVideo from "@/components/video-interview/UserVideo.vue";
import { useToast } from "vue-toastification";
// OpenVidu 관련 상태
const OV = ref(null);
const session = ref(null);
let mainStreamManager = ref(null);
const publisher = ref(null);
const subscribers = ref([]);

const videoInterviewStore = UseVideoInterviewStore();
const route = useRoute();
// const router = useRouter()
const toast = useToast();
// const yourname = ref('');
// const announceUUID = ref('');
// const videoInterviewRoomUUID = ref('');
const userName = ref("");
const userType = ref("");

onMounted(async() => {
  joinSession(route.params.announceUUID, route.params.videoInterviewUUID);
});

const getCookie = async(tokenName) => {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${tokenName}=`);
  if (parts.length === 2) { return parts.pop().split(";").shift(); }
};

const setUserInfoFromToken = async() => {
  const utoken = await getCookie("UTOKEN");
  if (utoken) {
    userType.value = utoken.split("|")[1];
    userName.value = utoken.split("|")[0];
  }
};

const handleSessionToken = async (announceUUID, videoInterviewUUID) => {
  try {
    const requestBody = {
      announceUUID: announceUUID,
      videoInterviewUUID: videoInterviewUUID,
      params: { customSessionId: userName.value },
    };
    const response = await videoInterviewStore.getSessionToken(requestBody);
    toast.success(response.data);
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
    // OpenVidu 객체 초기화
    OV.value = new OpenVidu();
    session.value = OV.value.initSession();

    // 스트림 이벤트 처리
    session.value.on("streamCreated", ({ stream }) => {
      const subscriber = session.value.subscribe(stream);
      subscribers.value.push(subscriber);
    });
    session.value.on("streamDestroyed", ({ stream }) => {
      const index = subscribers.value.indexOf(stream.streamManager, 0);
      if (index >= 0) { subscribers.value.splice(index, 1); } });
      session.value.on("exception", ({ exception }) => { console.warn(exception); 
    });
   await setUserInfoFromToken();
   console.log(userName.value)
    const token = await handleSessionToken(announceUUID, videoInterviewUUID);
    if (!token || typeof token !== "string") { throw new Error("유효하지 않은 세션 토큰입니다."); }

    console.log(`${videoInterviewUUID} Session에 접속중: ${token}`);
    await session.value.connect(token, { clientData: userName.value });

    // 퍼블리셔 초기화
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

    // 스트림 퍼블리시
    mainStreamManager.value = publisher.value;
    await session.value.publish(publisher.value);
    toast.success("면접방에 오신 걸 환영합니다.\n지원자는 마이크를 끄고 대기해주시길 바랍니다.");
  } catch (error) {
    // router.push(`/video-interview/${route.params.announceUUID}`)
    console.log(error);
    toast.error("지원자는 정해진 시간에 정해진 면접방과 일정에 맞춰 참여 바랍니다.");
  }
  window.addEventListener("beforeunload", leaveSession);
};

const leaveSession = () => {
  if (session.value) {
    session.value.disconnect();
  }
  session.value = undefined;
  mainStreamManager.value = undefined;
  publisher.value = undefined;
  subscribers.value = [];
  OV.value = undefined;
  window.removeEventListener("beforeunload", leaveSession);
};
</script>

<style scoped>
.header {
  background-color: #f8f9fa; /* 배경색 */
  color: #333; /* 글자색 */
  padding: 20px; /* 안쪽 여백 */
  text-align: center; /* 텍스트 정렬 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
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

.exitbtn {
  background-color: white;
  color: red;
  border: none;
  border-radius: 5px;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 16px;
}

.exitbtn:hover {
  opacity: 70%;
}

.wrapper {
  background-color: #f1f1f1;
  width: 100%;
  height: 100%;
  padding: 0;
  display: block;
  flex-direction: column;
  margin: 0 auto;
}

.seeker-wrapper {
  position: absolute;
  width: 100%;
  height: 100%;
  padding: 0;
  display: flex;
  flex-direction: column;
  margin: 0 auto;
}

.estimator-wrapper {
  position: absolute;
  width: 100%;
  height: 100%;
  padding: 0;
  display: flex;
  flex-direction: row;
  margin: 0 auto;
}

.vip-video {
  width: 100%;
  padding-top: 50px;
  display: flex;
  justify-content: center;
}

.vip-img {
  width: fit-content;
  height: 300px;
  border: 1px solid black;
}

.vip-video {
  width: 100%;
  margin-top: 110px;
  display: flex;
  justify-content: center;
}

.vip-img {
  width: fit-content;
  height: 300px;
  border: 1px solid black;
}
/* video-interview-estimator -> vie*/


.vie-video {
  margin-top: 100px;
  position: relative;
  top: 0;
  flex-direction: column;
  width: 40%;
  align-items: center;
  justify-content: center;
  float: left;
  box-sizing: border-box;
  padding: 18px;
  display: flex;
  justify-content: center;
}

.vie-img {
  width: fit-content;
  height: fit-content;
  border: 1px solid black;
}

.vie-evaluate {
  margin-top: 100px;
  position: relative;
  top: 0;
  right: 0;
  /* -ms-overflow-style: none; */
  /* scrollbar-width: none;  */
  display: flex;
  flex-direction: column;
  width: 60%;
  float: left;
  box-sizing: border-box;
  background: black;
  color: black;
  overflow-y: scroll;
  background: white;
  padding: 18px;
}

.vie-menu {
  display: flex;
  justify-content: center;
  align-items: center;
  column-gap: 5px;
}

.vie-menubtn {
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

.vie-menubtn:hover {
  background-color: #ddd;
}

.vie-content {
  display: flex;
  flex-direction: column;
}

.vie-evaluate::-webkit-scrollbar {
  display: none; /* Chrome, Safari, Opera*/
}



</style>