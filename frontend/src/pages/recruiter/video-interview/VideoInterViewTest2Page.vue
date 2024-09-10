<template>
  <div>
    <hr>
    <h1>화상 면접 프로세스</h1>
    <p>면접 일정에서 화상 면접을 생성하고 일정을 생성하면 리스트 목록의 항목들에 면접방 생성 버튼이 나옴</p>
    <p>=> 추후 이 부분은 일괄선택 처리(배치처리?)</p>
    <p>면접방 생성 버튼을 누르면 해당 공고의 announceUUID 와 면접일정쪽 interviewScheduleUUID 전송됨 </p>
    
    <p>ex) 면접1팀(A,B,C) 09:00~10:00 지원자(D) / 10:00~11:00 지원자(E) / 11:00~12:00 지원자 (F) -> 면접일정 UUID는 같아야함</p>
    <p>ex) 면접2팀(G,H,I) 09:00~10:00 지원자(J) / 10:00~11:00 지원자(K) / 11:00~12:00 지원자 (L) -> 면접일정 UUID는 같아야함</p>
    <p>즉 면접팀이 같으면 면접일정의 UUID가 같아야하고 면접팀이 같아도 날짜가 다르면 UUID는 달라야한다.</p>
    <p>면접방 항목은 공고제목(공고), 면접방 식별번호(면접일정) 면접 시작일(면접일정)</p>
    <div class="ex-roomlist">
      <p class="ex-roomitem">면접1팀 | 면접방 식별번호 | 면접 시작일 | [참여버튼]</p>
      <p class="ex-roomitem">면접2팀 | 면접방 식별번호 | 면접 시작일 | [참여버튼]</p>
    </div>
    <hr>
    <h1>화상 면접방 생성 TEST</h1>
    <form @submit.prevent="submitCreateForm">
      <!-- UUID1 입력 -->
      <p> announceUUID 공고를 구분한다. => 라우팅에 사용</p>
      <p>ex) video-interview/room/announceUUID => 접속하면 공고별 면접 대기방이 나온다.</p>
      <label for="announceUUID">announceUUID </label>
      <input type="text" v-model="announceUUID" placeholder="Enter announceUUID" />

      <!-- UUID2 입력 -->
      <p>ex) video-interview/room/announceUUID/interviewScheduleUUID => 다음은 면접방에 접속했을 때의 링크이다.</p>
      <label for="interviewScheduleUUID">interviewScheduleUUID </label>
      <input type="text" v-model="interviewScheduleUUID" placeholder="Enter interviewScheduleUUID" />

      <!-- 제출 버튼 -->
      <button type="submit">Submit</button>
    </form> 
    <p>지원자에게 전송되는 이메일은 다음과 같다.</p>
    <p>ex) http://localhost:8080/api/video-interview/room/{{ announceUUID }}/{{ interviewScheduleUUID }} => Get 요청을 컨트롤러가 받음</p>
    <p>백엔드에서 redirect 처리  /video-interview/room/{announceId} 이동</p>
    <p>dto 는 아래와 같다.</p>
    <span>{</span>
      <span>announceUUID: {{ announceUUID }}</span><br>
      <span>params: { customSessionId: {{ interviewScheduleUUID }}}</span>
      <span>}</span>
    <p> result: {{ createResult }} </p>
    <hr>
    <h1>화상 면접방 목록 조회 TEST</h1>
    <p>지원자나 면접관이 이메일을 누르면 </p>
    <p>라우팅을 이용해 /video-interview/room/{announceId} 페이지로 이동</p>
    <p>공고에 매핑된 화상면접방을 조회함</p>
    <p>아래는 예시이고, 라우팅 처리 해야됨</p>
    <form class="ex-form" @submit.prevent="submitSearchAllForm">
      <input type="text" v-model="announceUUID" placeholder="Enter annoucneUUID" />
      <button type="submit">Submit</button>
    </form>
    <!-- searchResult 데이터를 v-for로 출력 -->
    <div v-if="searchResult.length">
      <h2>화상 면접방 목록</h2>
      <ul>
        <li v-for="room in searchResult" :key="room.idx">
          면접방 ID: {{ room.idx }} | 공고 ID: {{ room.announceUUID }} | 면접방 ID: {{ room.interviewScheduleUUID }}
          <button>면접방 참여</button>
        </li>
      </ul>
    </div>
    
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { UseVideoInterviewStore } from '@/stores/UseVideoInterviewStore'; // Pinia 스토어 import

const announceUUID = ref('');
const interviewScheduleUUID = ref('');
const createResult = ref('');
const searchResult = ref([]);
const videoInterviewStore = UseVideoInterviewStore();

const submitCreateForm = async () => {
  try {
    const videoInterviewCreateRoomReq = {
      announceUUID: announceUUID.value,
      params: { customSessionId: interviewScheduleUUID.value},
    };
    // Pinia 스토어의 createVideoInterviewRoom 액션 호출
    const response = await videoInterviewStore.createVideoInterview(videoInterviewCreateRoomReq);
    console.log(response)
    createResult.value = response.result;
  } catch (error) {
    console.error(error);
  }
};

const submitSearchAllForm = async () => {
  try {
    // Pinia 스토어의 searchAllVideoInterview 액션 호출
    const response = await videoInterviewStore.searchAllVideoInterview(announceUUID.value);
    console.log(response)
    searchResult.value = response.result;
  } catch (error) {
    console.error(error);
  }
};

</script>

<style>
  /* 간단한 스타일링 */
  .ex-roomlist{
    padding: 10px;
    display: flex;
    flex-direction: column;
  }
  .ex-roomitem{
    padding: 10px;
    width: fit-content;
    border: 1px solid black;
  }
  .ex-form {
    display: flex;
    flex-direction: row;
  }
</style>
