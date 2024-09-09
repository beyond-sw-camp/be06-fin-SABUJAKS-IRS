<template>
    <header class="header-cp">
        <div class="header-container">
            <div class="logo"><img src="@/assets/img/irs_white.png" style="width: 80px;"></div>
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
</template>

<script>
    // // 드롭다운 토글 버튼과 메뉴 가져오기
    // var dropdownToggle = document.getElementById("dropdown-toggle");
    // var dropdownMenu = document.getElementById("dropdown-menu");

    // // '로그인 한 회원 ▼' 클릭 시 드롭다운 메뉴 토글
    // dropdownToggle.onclick = function (event) {
    //     event.preventDefault();  // 링크 기본 동작 방지
    //     // 드롭다운 메뉴가 보이는지 여부에 따라 display 설정
    //     if (dropdownMenu.style.display === "block") {
    //         dropdownMenu.style.display = "none";
    //     } else {
    //         dropdownMenu.style.display = "block";
    //     }
    // }

    // // 페이지 다른 곳 클릭 시 드롭다운 메뉴 닫기
    // window.onclick = function (event) {
    //     if (!event.target.matches('#dropdown-toggle')) {
    //         dropdownMenu.style.display = "none";
    //     }
    // }
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
}

.header-cp {
    background-color: #212b36;
    border-bottom: 1px solid #ddd;
}

.header-container {
    max-width: 1200px;
    height: 60px;
    margin: 0 auto;
    display: flex;
    justify-content: space-between;
    align-items: center;
}

.header-container h1 {
    font-size: 24px;
    color: #333;
}

.logo {
    padding-top: 10px;
    color: white;
    font-size: 24px;
    font-weight: bold;
}

.header-right {
    position: relative;
    /* 부모 요소를 기준으로 드롭다운 위치 설정 */
}

.header-right a {
    margin-left: 20px;
    text-decoration: none;
    color: #e6e6e6;
    font-size: 14px;
}

/* 드롭다운 메뉴 스타일 */
.dropdown-menu {
    display: none;
    /* 처음에는 숨김 */
    position: absolute;
    background-color: white;
    box-shadow: 0 8px 16px rgba(0, 0, 0, 0.2);
    padding: 10px;
    border-radius: 5px;
    z-index: 100;
    width: 120px;
    top: 100%;
    /* 클릭한 요소 아래에 위치 */
    right: 0;
    /* 오른쪽에 맞춰 정렬 */
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
    /* color: white; */
}

.dropdown-menu a {
    color: #212b36;
    margin: 0;
}

.logout-btn:hover {
    /* background-color: #83a5ea; */
}
</style>