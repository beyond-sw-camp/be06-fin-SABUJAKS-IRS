<template>
  <body>
    <header class="header">
        <div class="logo"><img src="../../../assets/img/irs_white.png" style="width: 150px;"></div>
        <div class="user-info">
            <div class="user-name">John Doe</div>
            <button class="logout-button">Logout</button>
        </div>
    </header>
    <div class="container">
        <div class="sidebar">
            <ul>
                <li>공고</li>
                <li>지원서</li>
                <li>면접</li>
                <li>마이페이지</li>
            </ul>
        </div>
        <div id="content">
            <h1>지원서 관리</h1>
            <table class="">
                <tr>
                    <th>번호</th>
                    <th>공고 기간</th>
                    <th>공고명</th>
                    <th>신입/경력</th>
                    <th>지원자수</th>
                </tr>
                <tr class="announce" onclick="location.href='./seeker_list.html';">
                    <td>1</td>
                    <td>2024.09.24 - 2024.10.24</td>
                    <td>백엔드 엔지니어 신입공채</td>
                    <td>신입</td>
                    <td>24</td>
                </tr>
            </table>
            <div class="button-container">
                <button class="register-button">등록</button>
            </div>
            <div id="size-buttons">
                <button>1</button>
                <button>2</button>
                <button>3</button>
            </div>
        </div>
    </div>

</body>
</template>

<script setup>
import "../../../assets/css/style.css"
</script>

<style>

</style>