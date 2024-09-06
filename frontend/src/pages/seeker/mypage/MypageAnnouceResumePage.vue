<template>
  <body>
    <header>
        <div class="header-container">
            <h1>IRS</h1>
            <div class="header-right">
                <a href="#">공고 달력</a>
                <a href="#">알림</a>
                <a href="#">마이페이지</a>
                <a href="#" id="dropdown-toggle">구은주 ▼</a>

                <!-- 드롭다운 메뉴 -->
                <div id="dropdown-menu" class="dropdown-menu">
                    <a href="#" class="logout-btn">로그아웃</a>
                </div>
            </div>
        </div>
    </header>

    <!-- <script>
        // 드롭다운 토글 버튼과 메뉴 가져오기
        var dropdownToggle = document.getElementById("dropdown-toggle");
        var dropdownMenu = document.getElementById("dropdown-menu");

        // '로그인 한 회원 ▼' 클릭 시 드롭다운 메뉴 토글
        dropdownToggle.onclick = function (event) {
            event.preventDefault();  // 링크 기본 동작 방지
            // 드롭다운 메뉴가 보이는지 여부에 따라 display 설정
            if (dropdownMenu.style.display === "block") {
                dropdownMenu.style.display = "none";
            } else {
                dropdownMenu.style.display = "block";
            }
        }

        // 페이지 다른 곳 클릭 시 드롭다운 메뉴 닫기
        window.onclick = function (event) {
            if (!event.target.matches('#dropdown-toggle')) {
                dropdownMenu.style.display = "none";
            }
        }
    </script> -->

    <div class="main_div">
        <div class="container">
            <!-- 좌측 사이드바 -->
            <div class="sidebar">
                <div class="profile">
                    <img src="profile_photo_ex.png" alt="프로필 사진" class="profile-img">
                    <!-- <button class="btn">정보 수정</button> -->
                </div>
                <nav>
                    <ul>
                        <li><a href="#">홈</a></li>
                        <li><a href="#">지원서 등록</a></li>
                        <li><a href="#">공고별 지원서 관리</a></li>
                        <li><a href="#">통합 지원서 관리</a></li>
                        <li><a href="#">알림 관리</a></li>
                        <li><a href="#">일정 관리</a></li>
                    </ul>
                </nav>
            </div>

            <!-- 메인 컨텐츠 -->
            <div class="main-content">
                <div class="header">
                    <h1>공고별 지원서 관리</h1>
                </div>
            
                <div class="content">
                    <!-- 지원서 항목 리스트 -->
                    <div class="application-item">
                        <div class="status">지원완료</div>
                        <div class="application-details">
                            <div class="application-title">백엔드 서비스 부분 지원서</div>
                            <div class="company-name">한화 시스템</div>
                            <div class="date">2024.09.03</div>
                        </div>
                        <div class="application-actions">
                            <button class="edit-btn">수정</button>
                            <button class="delete-btn">삭제</button>
                        </div>
                    </div>
            
                    <div class="application-item">
                        <div class="status">미완성</div>
                        <div class="application-details">
                            <div class="application-title">지원서2</div>
                            <div class="company-name">삼성전자</div>
                            <div class="date">2024.09.03</div>
                        </div>
                        <div class="application-actions">
                            <button class="edit-btn">수정</button>
                            <button class="delete-btn">삭제</button>
                        </div>
                    </div>
                </div>
            </div>
            
        </div>
    </div>
</body>
</template>

<script setup>

</script>

<style>

</style>