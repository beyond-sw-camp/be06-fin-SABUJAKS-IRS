<template>
  <div>
    <h1>화상 면접방 생성 TEST</h1>
    <p>면접 일정에서 화상 면접을 생성하고 일정을 생성하면 리스트 목록의 항목들에 면접방 생성 버튼이 나옴</p>
    <p>=> 추후 이 부분은 일괄선택 처리(배치처리?)</p>
    <p>면접방 생성 버튼을 누르면 해당 공고의 고유 UUID 와 면접일정 UUID가 전송됨 </p>
    
    <p>ex) 면접1팀(A,B,C) 09:00~10:00 지원자(D) / 10:00~11:00 지원자(E) / 11:00~12:00 지원자 (F) -> 면접일정 UUID는 같아야함</p>
    <p>ex) 면접2팀(G,H,I) 09:00~10:00 지원자(J) / 10:00~11:00 지원자(K) / 11:00~12:00 지원자 (L) -> 면접일정 UUID는 같아야함</p>
    <p>즉 면접팀이 같으면 면접일정의 UUID가 같아야하고 면접팀이 같아도 날짜가 다르면 UUID는 달라야한다.</p>
    <p>면접방 항목은 공고제목(공고), 면접방 식별번호(면접일정) 면접 시작일(면접일정)</p>
    <div class="ex-roomlist">
      <p class="ex-roomitem">면접1팀 | 면접방 식별번호 | 면접 시작일 | [참여버튼]</p>
      <p class="ex-roomitem">면접2팀 | 면접방 식별번호 | 면접 시작일 | [참여버튼]</p>
    </div>
  
    <form @submit.prevent="submitForm">
      <!-- UUID1 입력 -->
      <label for="UUID1">UUID1은 공고를 구분한다. => 라우팅에 사용</label>
      <p>ex) video-interview/room/UUID1 => 접속하면 공고별 면접 대기방이 나온다.</p>
      <label for="uuid1">UUID1</label>
      <p>ex) 3216d170-01a3-4202-8098-92e18d16a7a1</p>
      <input type="text" v-model="uuid1" placeholder="Enter UUID1" />

      <!-- UUID2 입력 -->
      <p>ex) video-interview/room/UUID1/UUID2 => 다음은 면접방에 접속했을 때의 링크이다.</p>
      <label for="uuid2">UUID2</label>
      <input type="text" v-model="uuid2" placeholder="Enter UUID2" />

      <!-- 제출 버튼 -->
      <p>지원자에게 전송되는 이메일은 다음과 같다.</p>
      <p>ex) http://localhost:8080/api/video-interview/room/{{ uuid1 }}/{{ uuid2 }} => Get 요청을 컨트롤러가 받음</p>
      <button type="submit">Submit</button>
      <p>dto 는 아래와 같다.</p>
      <span>{</span>
        <span>announceUUID: {{ uuid1.valueOf }}</span>
        <span>params: { customSessionId: {{ uuid2.valueOf }}}</span>
        <span>}</span>
      <p>response는 다음과 같다.</p>
      <span>{{ result.valueOf }}</span>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { UseVideoInterviewStore } from '@/stores/UseVideoInterviewStore'; // Pinia 스토어 import

// UUID 값을 저장할 변수들
const uuid1 = ref('');
const uuid2 = ref('');
const result = ref('');

// Pinia store 사용
const videoInterviewStore = UseVideoInterviewStore();
// const getToken = async(uuid1) => {
//         const sessionId = await this.createSession(uuid1);
//         return await this.createToken(sessionId);
// };

// submitForm 함수
const submitForm = async () => {
  try {
    console.log(uuid1)
    console.log(uuid2)
    const videoInterviewCreateRoomReq = {
      announceUUID: uuid1.value,
      params: { customSessionId: uuid2.value},
    };
    // Pinia 스토어의 createVideoInterviewRoom 액션 호출
    const response = await videoInterviewStore.createVideoInterviewRoom(videoInterviewCreateRoomReq);
    result.value = response
    console.log('방 생성 성공:', response);
  } catch (error) {
    console.error('방 생성에 실패했습니다:', error);
  }
};
</script>

<style>
  /* 간단한 스타일링 */
  label {
    display: block;
    margin-top: 10px;
  }
  input {
    margin-bottom: 10px;
    display: block;
  }
  button {
    margin-top: 10px;
  }
  .ex-roomlist{
    padding: 10px;
    display: flex;
    flex-direction: column;
  }
  .ex-roomitem{
    padding: 10px;
    border: 1px solid black 
  }
</style>
