<template>
<div>
    <VideoInterviewMainHeaderComponent></VideoInterviewMainHeaderComponent>
    <div class="wrapper">
        <!-- <VideoInterviewMainSideBarComponent></VideoInterviewMainSideBarComponent> -->
        <div class="container">
            
            <h1 class="t1">공고 정보</h1>
            <p class="t2">{{ videoInterviewList.length > 0 ? videoInterviewList[0].announcementTitle : '제목 없음' }}</p>
            
            <h1 class="t1">참여자 정보</h1>
            <table class="table">
                <tbody>
                    <tr>
                        <th>이름</th>
                        <th>이메일</th>
                        <th>구분</th>
                    </tr>
                    <tr>
                        <td>{{ userInfo.name }}</td>
                        <td>{{ userInfo.email }}</td>
                        <td>{{ userInfo.role === 'ROLE_SEEKER' ? '지원자' : userInfo.role === 'ROLE_ESTIMATOR' ? '면접관' : '' }}</td>
                    </tr>
                </tbody>
            </table>
            
            <h1 class="t1">화상 면접 목록</h1>
            <div v-if="videoInterviewList != null">
                <table>
                <tbody>
                    <tr>
                        <th>시작일</th>
                        <th>시작시간</th>
                        <th>종료시간</th>
                        <th>면접참여</th>
                    </tr>
                    <tr class="announce" v-for="videoInterview in videoInterviewList" :key="videoInterview.idx">
                        <td>{{ videoInterview.interviewDate }}</td>
                        <td>{{ videoInterview.interviewStart }}</td>
                        <td>{{ videoInterview.interviewEnd }}</td>
                        <td><a class="joinbtn" :href='`/video-interview/${videoInterview.announcementUUID}/${videoInterview.interviewScheduleUUID}`'>면접 참여</a></td>
                    </tr>
                </tbody>
            </table>
            </div>
            
            <h1 class="t1">화상 면접 주의 사항</h1>
            <p>* 지원자는 정해진 시간에 정해진 면접방과 일정에 맞춰 참여 바랍니다.</p>
            <p>* 면접 중에는 방해받지 않도록 조용한 장소에서 면접을 진행하세요. 가능하면 방해 요소를 제거하고, 휴대전화는 무음으로 설정바랍니다.</p>
            <p>* 면접 시작 전에 웹캠과 마이크를 테스트하여 잘 작동하는지 확인하세요. 조명은 얼굴을 잘 비출 수 있도록 조정하세요.</p>
            <p>* 면접 중 문제가 발생하거나 질문이 있으면 즉시 면접관에게 문제를 설명하고 해결 방법을 요청하세요.</p>
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
// import VideoInterviewMainSideBarComponent from '@/components/video-interview/VideoInterviewSideBarComponent.vue';
import { useToast } from "vue-toastification";

const route = useRoute()
const router = useRouter()
const toast = useToast()

const authStore = UseAuthStore();
const videoInterviewStore = UseVideoInterviewStore();

const userInfo = ref({});
const videoInterviewList = ref([]);

onMounted( async() => { 
    await handleGetUserInfo();
    await handleGetVideoInterviewList(route.params.announcementUUID); 
})

const handleGetUserInfo = async() => {
    if (authStore.isLoggedIn && authStore.userInfo.email != null) {
        userInfo.value = authStore.userInfo
    }
}

const handleGetVideoInterviewList = async (announcementUUID) => {
    const response = await videoInterviewStore.readAll(announcementUUID);
    if(response.success && authStore.isLoggedIn && authStore.userInfo.email != null){
        videoInterviewList.value = response.result;
        toast.success("면접방에 오신 걸 환영합니다.\n지원자는 정해진 시간에 정해진 면접방과 일정에 맞춰 참여 바랍니다.");
    } else {
        toast.error("해당 공고의 면접 참여자가 아닙니다.");
        router.push("/") 
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


table th:nth-child(4) {
    width: 20%;
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

.t2 {
    font-size: 20px;
    margin: 20px 0;
    font-weight: 600;
}
</style>