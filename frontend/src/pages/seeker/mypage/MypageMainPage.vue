<template>
  <body class="body-m">
    <header class="header-m">
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

<style scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

.body-m {
    font-family: 'Arial', sans-serif;
    background-color: #f4f4f4;
    justify-content: center; /* 로그아웃 드롭다운에 필요한 css */
    align-items: center; /* 로그아웃 드롭다운에 필요한 css */
    height: 100vh;
}

.header-m {
    background-color: #ffffff;
    border-bottom: 1px solid #ddd;
    padding: 10px 0;
}

.header-container {
    max-width: 1200px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.header-container h1 {
    font-size: 24px;
    color: #333;
}

/* 로그아웃 드롭다운에 필요한 css */
.header-right {
    position: relative; /* 부모 요소를 기준으로 드롭다운 위치 설정 */
}

.header-right a {
    margin-left: 20px;
    text-decoration: none;
    color: #666;
    font-size: 14px;
}

.main_div {
    width: 100%;
    background-color: rgba(255, 255, 255, 0);
    display: flex;
    justify-content: center;
    align-items: center;
    padding: 20px 0; /* 수직 간격을 추가 */
}

.container {
    display: flex;
    width: 100%;
    max-width: 1200px;
    background-color: rgba(255, 255, 255, 0);
    /* box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1); */
    border-radius: 10px;
    margin: 20px; /* 수직, 수평 여백 추가 */
    gap: 20px; /* 사이드바와 메인 컨텐츠 사이의 간격 추가 */
}

.sidebar {
    width: 20%;
    height: 400px;
    background-color: #f9fafb;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
    
}

.profile {
    text-align: center;
}

.profile-img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    margin-bottom: 10px;
}

.btn {
    background-color: #212b36;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 10px;
}

.btn:hover {
    background-color: #212b36;
}

nav ul {
    list-style: none;
    margin-top: 20px;
}

nav ul li {
    margin-bottom: 15px;
}

nav ul li a {
    text-decoration: none;
    color: #333;
    font-size: 16px;
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

.edit-profile {
    background-color: #212b36;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
}

.edit-profile:hover {
    background-color: #212b36;
}

.content {
    background-color: #f9fafb;
    padding: 20px;
    border-radius: 10px;
}

.info-section {
    margin-bottom: 20px;
}

.info-section h2 {
    font-size: 18px;
    margin-bottom: 10px;
}

.info-row {
    display: flex;
    justify-content: space-between;
    margin-bottom: 10px;
}

.label {
    font-weight: bold;
    color: #333;
}

.value {
    color: #555;
}


/*  */

/* 드롭다운 메뉴 스타일 */
.dropdown-menu {
    display: none; /* 처음에는 숨김 */
    position: absolute;
    background-color: white;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    padding: 10px;
    border-radius: 5px;
    z-index: 100;
    width: 120px;
    top: 100%; /* 클릭한 요소 아래에 위치 */
    right: 0; /* 오른쪽에 맞춰 정렬 */
}

.logout-btn {
    display: block;
    /* padding: 4px 16px; */
    margin: 0 0 0 0;
    text-decoration: none;
    color: #333;
    text-align: center;
    border-radius: 4px;
    /* background-color: #83a5ea; */
    color: white;
}

.logout-btn:hover {
    /* background-color: #83a5ea; */
}
</style>