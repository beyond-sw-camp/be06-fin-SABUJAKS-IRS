<template>
  <body class="bbodyy">
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
                    <h1>회원 정보</h1>
                    <button class="btn edit-profile">내 프로필 수정하기</button>
                </div>

                <div class="content">
                    <!-- 기본 정보 -->
                    <div class="info-section">
                        <h2>기본 정보</h2>
                        <div class="info-row">
                            <span class="label">이메일</span>
                            <span class="value">eunjoo.nine@gmail.com</span>
                        </div>
                        <div class="info-row">
                            <span class="label">이름</span>
                            <span class="value">구은주</span>
                        </div>
                        <div class="info-row">
                            <span class="label">닉네임</span>
                            <span class="value">juu</span>
                        </div>
                        <div class="info-row">
                            <span class="label">성별</span>
                            <span class="value">여</span>
                        </div>
                        <div class="info-row">
                            <span class="label">생년월일</span>
                            <span class="value">생년월일을 입력해주세요</span>
                        </div>
                        <div class="info-row">
                            <span class="label">핸드폰</span>
                            <span class="value">010-0000-0000</span>
                        </div>
                        <div class="info-row">
                            <span class="label">주소</span>
                            <span class="value">oo광역시 oo구</span>
                        </div>
                        <div class="info-row">
                            <span class="label">소셜 로그인 타입</span>
                            <span class="value">x</span>
                        </div>
                        <div class="info-row">
                            <span class="label">프로필 사진</span>
                            <span class="value">
                                <img src="profile_photo_ex.png" alt="프로필 사진" class="profile-img">
                            </span>
                        </div>
                        <div class="info-row">
                            <span class="label">생성날짜</span>
                            <span class="value">2024-09-04 13:34:35</span>
                        </div>
                        <div class="info-row">
                            <span class="label">수정날짜</span>
                            <span class="value">-</span>
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