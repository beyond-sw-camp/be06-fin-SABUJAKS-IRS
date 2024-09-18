<template>
<div>
    <VideoInterviewMainHeaderComponent></VideoInterviewMainHeaderComponent>
    <div class="wrapper">
        <VideoInterviewMainSideBarComponent></VideoInterviewMainSideBarComponent>
        <div class="container">
            <h1 class="t1">공고 정보</h1>
            <p>(주) 한화 시스템,백엔드 엔지니어 신입공채</p>
            <h1 class="t1">참여자 정보</h1>
            <table class="table">
                <tbody>
                    <tr>
                        <th>참여자 이름</th>
                        <th>참여자 이메일</th>
                        <th>참여자 구분</th>
                    </tr>
                    <tr>
                        <td>{{ userName }}</td>
                        <td>{{ userEmail }}</td>
                        <td v-if="userType=='ROLE_SEEKER'">지원자</td>
                        <td v-if="userType=='ROLE_ESTIMATOR'">면접관</td>
                    </tr>
                </tbody>
            </table>
            <h1 class="t1">화상 면접 목록</h1>
            <div v-if="searchAllResult != null">
                <table>
                <tbody>
                    <tr>
                        <th>공고ID</th>
                        <th>면접방ID</th>
                        <th>시작일</th>
                        <th>시작시간</th>
                        <th>종료시간</th>
                        <th>면접참여</th>
                    </tr>
                    <tr class="announce" v-for="videoInterview in searchAllResult" :key="videoInterview.idx">
                        <td>{{ videoInterview.announceUUID }}</td>
                        <td>{{ videoInterview.videoInterviewUUID }}</td>
                        <td>{{ videoInterview.interviewDate }}</td>
                        <td>{{ videoInterview.interviewStart }}</td>
                        <td>{{ videoInterview.interviewEnd }}</td>
                        <td>
                            <a class="joinbtn" :href='`/video-interview/${videoInterview.announceUUID}/${videoInterview.videoInterviewUUID}`'>
                                면접 참여
                            </a>
                        </td>
                    </tr>
                </tbody>
            </table>
            </div>
            <h1 class="t1">화상 면접 주의 사항</h1>
            <p>* 지원자는 정해진 시간에 정해진 면접방과 일정에 맞춰 참여 바랍니다.</p>
            <p>* 면접 중에는 방해받지 않도록 조용한 장소에서 면접을 진행하세요. 가능하면 방해 요소를 제거하고, 휴대전화는 무음으로 설정하세요.</p>
            <p>* 면접 시작 전에 웹캠과 마이크를 테스트하여 잘 작동하는지 확인하세요. 조명은 얼굴을 잘 비출 수 있도록 조정하세요.</p>
            <p>* 면접 중 문제가 발생하거나 질문이 있으면 즉시 면접관에게 연락해 문제를 설명하고 해결 방법을 요청하세요.</p>
        </div>
    </div>
</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { UseVideoInterviewStore } from '@/stores/UseVideoInterviewStore'; 
import { UseAuthStore } from '@/stores/UseAuthStore';
import { useRoute, useRouter } from 'vue-router';
import VideoInterviewMainHeaderComponent from '@/components/video-interview/VideoInterviewHeaderComponent.vue';
import VideoInterviewMainSideBarComponent from '@/components/video-interview/VideoInterviewSideBarComponent.vue';
import { useToast } from "vue-toastification";

const searchAllResult = ref([]);
const videoInterviewStore = UseVideoInterviewStore();
const authStore = UseAuthStore();
const route = useRoute()
const router = useRouter()
const toast = useToast()
const userName = ref("");
const userEmail = ref("");
const userType = ref("");

onMounted( async() => { 
    await handleGetUserInfo();
    await handleSearchAll(route.params.announceUUID); 
})

const handleGetUserInfo = async() => {
    try {
        const response = await authStore.getUserInfo();
        if(response && response.success){
            userEmail.value = authStore.email
            userType.value = authStore.role
            userName.value = authStore.name
        }
    } catch (error) {
        console.error(error); 
    }
}

const handleSearchAll = async (announceUUID) => {
  try {
    const response = await videoInterviewStore.searchAll(announceUUID);
    if (response && response.success){
        searchAllResult.value = response.result;
        toast.success("면접방에 오신 걸 환영합니다.\n지원자는 정해진 시간에 정해진 면접방과 일정에 맞춰 참여 바랍니다.");
    } else{
        toast.error("로그인이 필요한 접근입니다");
        router.push("/")
    }
  } catch (error) {
    console.error(error);
  }
};


</script>

<style scoped>
.wrapper {
    width: 80%;
    margin: 0 auto;
}

.container {
    width: 80%;
    flex: 1;
    margin: 0 auto;
    margin-left: 200px;
    padding: 150px 0;
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
}

.t1 {
    font-size: 24px;
    margin: 20px 0;
}

.joinbtn {
    width: fit-content;
    background-color: #2A3845;
    border: 1px solid #ddd;
    border-radius: 10px;
    padding: 10px;
    font-size: 0.8rem;
    font-weight: bold;
    color: white;
    cursor: pointer;
    height: 100%;
    transition: background-color 0.3s;
    display: inline-block;
    text-decoration: none;
}

.joinbtn:hover {
    background-color: #B4C7D0;
}

/* 테이블 */
table {
    width: 100%;
    border: 1px solid #ddd;
    border-collapse: collapse;
    margin-bottom: 40px;
    display: table;
    box-sizing: border-box;
    text-indent: initial;
    unicode-bidi: isolate;
    border-spacing: 2px;
}

tbody {
    display: table-row-group;
    vertical-align: middle;
    unicode-bidi: isolate;
    border-color: inherit;
}


table th:nth-child(6) {
    width: 10%;
}

tr {
    display: table-row;
    vertical-align: inherit;
    unicode-bidi: isolate;
    border-color: inherit;
}

td {
    border: 1px solid #ddd;
    padding: 25px;
    text-align: center;
}

th {
    background-color: #f1f1f1;
    padding: 25px;
    text-align: center;
    border: 1px solid #ddd;
    display: table-cell;
    vertical-align: inherit;
    font-weight: bold;
    text-align: -internal-center;
    unicode-bidi: isolate;
}
</style>