<template>
  <div class="body-s">
    <SeekerHeaderComponent></SeekerHeaderComponent>
    <div class="main_div">
        <div class="container-s">
            <!-- 사이드 바 -->
            <SeekerSideBarComponent></SeekerSideBarComponent>

            <!-- 메인 컨텐츠 -->
            <div class="main-content">
                <div class="header">
                    <h1>일정 관리</h1>
                </div>

                <div class="content">
                    <div id='calendar'></div>
                </div>
            </div>


        </div>
    </div>
</div>
</template>

<script setup>
import SeekerHeaderComponent from "@/components/seeker/SeekerHeaderComponent.vue";
import SeekerSideBarComponent from "@/components/seeker/SeekerSideBarComponent.vue";

// document.addEventListener('DOMContentLoaded', function () {
//             var calendarEl = document.getElementById('calendar');

//             var calendar = new FullCalendar.Calendar(calendarEl, {
//                 timeZone: 'UTC',
//                 initialView: 'dayGridMonth',
//                 events: 'https://fullcalendar.io/api/demo-feeds/events.json',
//                 editable: true,
//                 selectable: true
//             });

//             calendar.render();
//         });
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

.body-s {
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
    padding: 20px 0; /* 수직 간격을 추가 */
}

.container-s {
    display: flex;
    width: 100%;
    max-width: 1200px;
    background-color: rgba(255, 255, 255, 0);
    /* box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); */
    border-radius: 10px;
    margin: 20px; /* 수직, 수평 여백 추가 */
    gap: 20px; /* 사이드바와 메인 컨텐츠 사이의 간격 추가 */
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

.content {
    background-color: #f9fafb;
    padding: 20px;
    border-radius: 10px;
}

#calendar {
    max-width: 1100px;
    margin: 40px auto;
}
</style>