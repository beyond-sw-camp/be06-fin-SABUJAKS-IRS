<template>
<div>
    <VideoInterviewMainHeaderComponent></VideoInterviewMainHeaderComponent>
    <div class="wrapper">
        <VideoInterviewMainSideBarComponent></VideoInterviewMainSideBarComponent>
        <div class="container">
            <h1 class="t1">공고 정보</h1>
            <p>(주) 한화 시스템,백엔드 엔지니어 신입공채</p>
            <h1 class="t1">지원자 정보</h1>
            <table class="table">
                <tbody>
                    <tr>
                        <th>지원자이름</th>
                        <th>지원번호</th>
                        <th>면접방식별번호</th>
                        <th>면접일정</th>
                        <th>면접시간</th>
                    </tr>
                    <tr>
                        <td>박박이</td>
                        <td>123123123</td>
                        <td>111222333</td>
                        <td>2024/12/12</td>
                        <td>09:00~10:00</td>
                    </tr>
                </tbody>
            </table>
            <h1 class="t1">화상 면접 주의 사항</h1>
            <p>* 지원자는 정해진 시간에 정해진 면접방과 일정에 맞춰 참여 바람</p>
            <h1 class="t1">화상면접방 목록</h1>
            <div v-if="searchAllResult != null">
                <table>
                <tbody>
                    <tr><th>번호</th>
                        <th>공고ID</th>
                        <th>면접방ID</th>
                        <th>면접팀(미정)</th>
                        <th>시작일(미정)</th>
                        <th>면접참여</th>
                    </tr>
                    <tr class="announce" v-for="videoInterview in searchAllResult" :key="videoInterview.idx">
                        <td>{{ videoInterview.idx }}</td>
                        <td>{{ videoInterview.announceUUID }}</td>
                        <td>{{ videoInterview.videoInterviewRoomUUID }}</td>
                        <td> 1팀 </td>
                        <td> 2024/09/11 </td>
                        <td>
                            <a class="joinbtn" :href='`/video-interview/${videoInterview.announceUUID}/${videoInterview.videoInterviewRoomUUID}`'>
                                면접 참여
                            </a>
                        </td>
                    </tr>
                </tbody>
            </table>
            </div>
        </div>
    </div>
</div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { UseVideoInterviewStore } from '@/stores/UseVideoInterviewStore'; 
import { useRoute, useRouter } from 'vue-router';
import VideoInterviewMainHeaderComponent from '@/components/video-interview/VideoInterviewHeaderComponent.vue';
import VideoInterviewMainSideBarComponent from '@/components/video-interview/VideoInterviewSideBarComponent.vue';
import { useToast } from "vue-toastification";

const searchAllResult = ref([]);
const videoInterviewStore = UseVideoInterviewStore();
const route = useRoute()
const router = useRouter()
const toast = useToast()

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

onMounted(() => { handleSearchAll(route.params.announceUUID); })
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
    margin: 25px 0;
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

table th:nth-child(1) {
    width: 10%;
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