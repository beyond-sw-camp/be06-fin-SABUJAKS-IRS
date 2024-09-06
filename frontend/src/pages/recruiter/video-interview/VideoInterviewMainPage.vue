<template>
<div>
    <VideoInterviewMainHeaderComponent></VideoInterviewMainHeaderComponent>
    <div class="vim-wrapper">
        <VideoInterviewMainSideBarComponent></VideoInterviewMainSideBarComponent>
        <div class="vim-container">
            <h1 class="vim-text1">화상 면접</h1>
            <p>* 지원자는 정해진 시간에 정해진 면접방과 일정에 맞춰 참여 바람</p>
            <p>* 참여시, 이메일에 첨부된 비밀번호를 통해 접속</p>
            <h1 class="vim-text2">공고 정보</h1>
            <p>(주) 한화 시스템,백엔드 엔지니어 신입공채</p>
            <h1 class="vim-text2">지원자 정보</h1>
            <table class="vim-table">
                <tbody>
                    <tr>
                        <th>지원자이름</th>
                        <th>지원번호</th>
                        <th>면접방식별번호</th>
                        <th>면접일정</th>
                        <th>면접시간</th>
                    </tr>
                    <tr class="announce" onclick="location.href='./seeker_list.html';">
                        <td>박박이</td>
                        <td>123123123</td>
                        <td>111222333</td>
                        <td>2024/12/12</td>
                        <td>09:00~10:00</td>
                    </tr>
                </tbody>
            </table>
            <h1 class="vim-text2">화상면접방 목록</h1>
            <table class="vim-table">
                <tbody>
                    <tr>
                        <th>번호</th>
                        <th>면접방식별번호</th>
                        <th>면접그룹</th>
                        <th>일정</th>
                        <th>면접참여</th>
                    </tr>
                    <tr class="announce" onclick="location.href='./seeker_list.html';">
                        <td>1</td>
                        <td>111222333</td>
                        <td>1팀</td>
                        <td>2024/12/12</td>
                        <td>
                            <button class="vim-joinbtn">참여</button>
                        </td>
                    </tr>
                </tbody>
            </table>
            <div class="vim-pagination">
                <button class="vim-pagebtn">1</button>
                <button class="vim-pagebtn">2</button>
                <button class="vim-pagebtn">3</button>
            </div>
        </div>
    </div>
</div>
</template>

<script setup>
import VideoInterviewMainHeaderComponent from '../../../components/video-interview/VideoInterviewMainHeaderComponent.vue';
import VideoInterviewMainSideBarComponent from '../../../components/video-interview/VideoInterviewMainSideBarComponent.vue';
</script>

<style>
</style>