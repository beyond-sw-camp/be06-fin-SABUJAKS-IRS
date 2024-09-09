<template>
    <div class="body-n">
        <SeekerHeaderComponent></SeekerHeaderComponent>
        <div class="main_div">
            <div class="container-n">
                <!-- 사이드 바 -->
                <SeekerSideBarComponent></SeekerSideBarComponent>

                <!-- 메인 컨텐츠 -->
                <div class="main-content">
                    <div class="header">
                        <h1>알림 관리</h1>
                    </div>

                    <div class="content">
                        <div class="timeline">
                            <div class="timeline-item">
                                <div class="timeline-date">2024.09.03</div>
                                <div class="timeline-content">
                                    한화 시스템 서류절차 통과되어 면접 일정 조회 이메일이 발송되었습니다.
                                    <button class="delete-btn">삭제</button>
                                    <button class="schedule-btn">면접 일정 조율</button> <!-- 날짜 선택 버튼 추가 -->
                                </div>
                            </div>

                            <div class="timeline-item">
                                <div class="timeline-date">2024.09.02</div>
                                <div class="timeline-content">
                                    엔코아 인턴 지원 완료되었습니다.
                                    <button class="delete-btn">삭제</button>
                                    <button class="schedule-btn">면접 일정 조율</button> <!-- 날짜 선택 버튼 추가 -->
                                </div>
                            </div>
                        </div>

                        <!-- 모달창 HTML -->
                        <div id="schedule-modal" class="modal">
                            <div class="modal-content">
                                <span class="close-btn">&times;</span>
                                <h2>면접 날짜 선택</h2>
                                <p>면접 날짜를 선택하세요:</p>
                                <input type="date" id="interview-date">
                                <button class="submit-btn">확인</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import SeekerHeaderComponent from "@/components/seeker/SeekerHeaderComponent.vue";
import SeekerSideBarComponent from "@/components/seeker/SeekerSideBarComponent.vue";

// // 모달창 가져오기
// var modal = document.getElementById("schedule-modal");
// var scheduleBtns = document.querySelectorAll(".schedule-btn");
// var closeBtn = document.querySelector(".close-btn");

// // '면접 일정 조율' 버튼 클릭 시 모달 열기
// scheduleBtns.forEach(function (btn) {
//     btn.addEventListener('click', function () {
//         // modal.style.display = "block";
//         modal.style.display = "flex"; // 모달창 보이기
//     });
// });

// // 'x' 버튼 클릭 시 모달 닫기
// closeBtn.onclick = function () {
//     modal.style.display = "none";
// }

// // 모달 외부 클릭 시 모달 닫기
// window.onclick = function (event) {
//     if (event.target === modal) {
//         modal.style.display = "none";
//     }
// }
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

.body-n {
    font-family: 'Arial', sans-serif;
    background-color: #f4f4f4;
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
    display: none;
    position: fixed;
    z-index: 1000;
    left: 0;
    top: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.4);
    justify-content: center;
    align-items: center;
}


.modal-content {
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    width: 300px;
    text-align: center;
}

.close-btn {
    float: right;
    font-size: 20px;
    cursor: pointer;
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
</style>