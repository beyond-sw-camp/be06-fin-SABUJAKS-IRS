<template>
  <body class="body-i">
    <header class="header-i">
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
        <div class="container1">
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
                    <h1>통합 지원서 관리</h1>
                    <button class="btn edit-profile">지원서 수정하기</button>
                </div>

                <div class="content">
                    <div class="resume-view-wrapper">
                        <div class="resume-view-container">
                            <!-- <div class="resume-subject">침착한 인재입니다.</div> -->
                            <div class="base profile image">
                                <div class="container">
                                    <div class="photo"><img name="user_photo" src="../../../assets/img/logo.png">
                                    </div>
                                    <div class="info-container">
                                        <div class="info-general">
                                            <div class="item name">구은주</div>
                                            <div class="item sex">여</div>
                                            <div class="item year">2000년 </div>
                                            <div class="item age">(만 23세)</div>
                                        </div>
                                        <div class="info-detail">
                                            <div class="item">
                                                <div class="label">휴대폰</div>
                                                <div class="value">010-8508-9877</div>
                                            </div>
                                            <div class="item">
                                                <div class="label">Email</div>
                                                <div class="value">
                                                    <a href="mailto:eunjoo.nine@gmail.com">eunjoo.nine@gmail.com</a>
                                                </div>
                                            </div>
                
                
                                        </div>
                                    </div>
                                </div>
                
                
                                <div class="summary col-4">
                
                                    <div class="item ">
                                        <div class="header">학력</div>
                                        <div class="description ellipsis">
                                            청운대학교 (인천) </div>
                                        <div class="options">
                                            <div class="option">대학교(4년)</div>
                                            <div class="option">졸업</div>
                                        </div>
                                    </div>
                
                                    <div class="item">
                                        <div class="header">경력</div>
                                        <div class="description ellipsis">
                                            한화시스템 </div>
                
                                        <div class="options">
                                            <div class="option">퇴사</div>
                                            <div class="option">총 7개월</div>
                                        </div>
                                    </div>
                
                                    <div class="item">
                                        <div class="header">인턴·대외활동 / 해외경험</div>
                                        <div class="description ellipsis">AI머신러닝 학습 동아리</div>
                                        <div class="description ellipsis">미국 해외경험</div>
                                    </div>
                
                                    <div class="item">
                                        <div class="header">자격증 / 어학</div>
                                        <div class="description ellipsis">정보처리기사</div>
                                        <div class="description ellipsis">영어-회화가능</div>
                                    </div>
                                </div>
                            </div>
                            <input type="hidden" id="hidAplctName" value="구은주">
                
                
                
                
                
                            <div class="base education">
                                <h2 class="header">학력</h2>
                                <div class="list list-education">
                
                                    <div class="item">
                                        <div class="date">
                                            <div class="term">2020. 03 ~ 2024. 08</div>
                                            <div class="state">졸업</div>
                                        </div>
                                        <div class="content">
                                            <div class="content-header">
                                                <div class="name">
                                                    청운대학교 (인천) </div>
                                                <div class="line">
                                                    컴퓨터공학과
                                                </div>
                                            </div>
                                            <div class="content-body">
                                                <div class="info">
                                                    <div class="item">
                                                        <div class="label">학점</div>
                                                        <div class="value">4.2 / 4.5</div>
                                                    </div>
                
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                
                
                
                            <div class="base career">
                                <div class="headers">
                                    <h2 class="header">경력</h2>
                                    <div class="term">
                                    </div>
                                </div>
                                <div class="list list-career">
                                    <div class="item">
                                        <div class="date">
                                            <div class="term">2024. 03 ~ 2024. 09</div>
                                            <div class="term-calculator">7개월</div>
                                        </div>
                                        <div class="content">
                                            <div class="content-header">
                                                <div class="name">
                                                    한화시스템 </div>
                
                                                <div class="is-private"></div>
                                                <div class="position">백엔드 플랫폼 개발부 </div>
                                            </div>
                                            <div class="content-body">
                                                <div class="description">
                                                </div>
                                                <div class="info">
                                                    <div class="item">
                                                        <div class="label">주요직무</div>
                                                        <div class="value">SI개발 </div>
                                                    </div>
                                                </div>
                                            </div>
                
                                        </div>
                                    </div>
                
                                </div>
                            </div>
                
                
                            <div class="base intern">
                                <h2 class="header">인턴·대외활동</h2>
                                <div class="list list-intern">
                                    <div class="item">
                                        <div class="date">
                                            <div class="term">2023. 03 ~ 2024. 06</div>
                                            <div class="term-calculator">1년 4개월</div>
                                        </div>
                
                                        <div class="content">
                                            <div class="content-header">
                                                <div class="name">AI머신러닝 학습 동아리</div>
                                                <div class="position position3">동아리</div>
                                            </div>
                                            <div class="content-body">
                                                머신러닝에 대해 동아리 원들과 학습하는 동아리를 운영했었습니다. </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                
                
                            <div class="base learn">
                                <h2 class="header">교육</h2>
                                <div class="list list-learn">
                                    <div class="item">
                                        <div class="date">2024. 04 ~ 2024. 10</div>
                                        <div class="content">
                                            <div class="content-header">
                                                <div class="name">한화시스템 부트캠프</div>
                                                <div class="agency">플레이데이터</div>
                                            </div>
                                            <div class="content-body">
                                                백엔드 프론트엔드에 대해 공부했습니다. </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                
                
                            <div class="base certificate">
                                <h2 class="header">자격증</h2>
                                <div class="list list-certificate">
                                    <div class="item pdf-page-break">
                                        <div class="date">2023. 04</div>
                                        <div class="content">
                                            <div class="content-header">
                                                <div class="name">정보처리기사</div>
                                                <div class="agency">한국산업인력공단</div>
                                            </div>
                                        </div>
                                    </div>
                
                                </div>
                            </div>
                
                
                            <div class="base awards">
                                <h2 class="header">수상</h2>
                                <div class="list list-awards">
                                    <div class="item">
                                        <div class="date">
                                            2024년
                                        </div>
                                        <div class="content">
                                            <div class="content-header">
                                                <div class="name">웃음이 맑은 상</div>
                                                <div class="agency">삼성전자</div>
                                            </div>
                                            <div class="content-body">
                                                웃음이 맑아서 수여합니다. </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                
                
                            <div class="base experience">
                                <h2 class="header">해외경험</h2>
                                <div class="list list-experience">
                                    <div class="item pdf-page-break">
                                        <div class="date">
                                            <div class="term">2023. 08 ~ 2024. 03</div>
                                            <div class="term-calculator">8개월</div>
                                        </div>
                                        <div class="content">
                                            <div class="content-header">
                                                <div class="name">미국</div>
                                            </div>
                                            <div class="content-body">
                                                어학연수 다녀왔습니다. </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                
                
                            <div class="base language">
                                <h2 class="header">어학</h2>
                                <div class="list list-language">
                                    <div class="item pdf-page-break">
                                        <div class="language">영어</div>
                                        <div class="content">
                                            <div class="content-body">
                                                <p>회화 능력 : 일상회화 가능</p>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                
                
                            <input data-val="true" data-val-required="ThreeLineStat 필드가 필요합니다." id="ThreeLineStat"
                                name="ThreeLineStat" type="hidden" value="False">
                            <div class="base introduction">
                                <h2 class="header">자기소개서</h2>
                                <div id="SummaryAjax">
                                </div>
                                <ul class="list list-introduction">
                                    <li class="item">
                                        <div class="header">성장배경</div>
                                        <div class="content" id="pfl_original">
                                            자애로운 어머니와 인정이 많으신 아버지 밑에서 자라... 침착한 사람으로 성장했습니다. </div>
                                    </li>
                                    <li class="item">
                                        <div class="header">장단점</div>
                                        <div class="content" id="pfl_original">
                                            저는 수영을 잘합니다.<br>하지만 축구는 못합니다. </div>
                                    </li>
                                </ul>
                            </div>
                
                
                
                            <div class="base portfolio">
                                <h2 class="header">포트폴리오</h2>
                                <table class="table table-attachments">
                                    <caption><span class="skip">첨부파일</span></caption>
                                    <colgroup>
                                        <col style="width:150px;">
                                        <col style="width:320px;">
                                        <col style="width:150px;">
                                        <col style="width:320px;">
                                    </colgroup>
                                    <tbody>
                
                                        <tr>
                                            <th>포트폴리오</th>
                                            <td>
                                                <a href="http://dkfoejfkdjfwo/" target="_blank">
                                                    <i class="icon url" aria-hidden="true"></i>
                                                    <div class="name">http://dkfoejfkdjfwo</div>
                                                </a>
                                            </td>
                                            <th>포트폴리오</th>
                                            <td colspan="3">
                                                <a href="https://file2.jobkorea.co.kr/Net/Mng/UserDown/ResumeAttach?idx=10233512">
                                                    <i class="icon jpg" aria-hidden="true"></i>
                                                    <div class="name">qkek.jpg</div>
                                                </a>
                                            </td>
                
                                        </tr>
                                        <tr>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                
                
                            <div class="base jobpreference">
                                <h2 class="header">취업우대사항</h2>
                                <table class="table table-special">
                                    <caption><span class="skip">취업우대사항</span></caption>
                                    <colgroup>
                                        <col style="width:149px;">
                                        <col style="width:165px;">
                                        <col style="width:153px;">
                                        <col style="width:160px;">
                                        <col style="width:153px;">
                                        <col style="width:160px;">
                                    </colgroup>
                                    <tbody>
                                        <tr>
                                            <th>보훈대상 여부</th>
                                            <td>보훈 대상자</td>
                                            <th>취업보호대상 여부</th>
                                            <td>취업보호 대상자</td>
                                            <th>고용지원금대상 여부</th>
                                            <td>대상</td>
                                        </tr>
                                        <tr>
                                            <th>병역사항</th>
                                            <td colspan="3">
                                                [군필] 제대 </td>
                                            <th>장애여부</th>
                                            <td>중증</td>
                
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                
                
                
                
                            <div class="base hopework" id="js-hopeworkAnchor">
                                <h2 class="header">희망근무조건</h2>
                                <table class="table table-hopework">
                                    <caption><span class="skip">희망근무조건</span></caption>
                                    <tbody>
                                        <tr>
                                            <th scope="row">희망근무지</th>
                                            <td>인천전지역, 서울전지역, 경기전지역</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">희망연봉</th>
                                            <td>면접 후 결정</td>
                                        </tr>
                                        <tr>
                                            <th scope="row">지원분야</th>
                                            <td>
                                                <div class="content is-label">
                                                    <div class="item">
                                                        <div class="label">직무</div>
                                                        <div class="value">
                                                            <ul class="list-hopework">
                
                                                                <li>
                                                                    <div class="item">백엔드개발자</div>
                
                                                                </li>
                
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>
                
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                
                
                            <div class="sign">
                                <div class="message">위의 모든 기재사항은 사실과 다름없음을 확인합니다.</div>
                
                                <div class="writer">작성자 : 구은주</div>
                                <div class="warning">
                                    <div class="description">이 이력서는 2024년 09월 05일 (목)에 최종 수정된 이력서 입니다.<br>위조된 문서를 등록하여 취업활동에 이용 시 법적
                                        책임을 지게 될 수 있습니다.<br>(주)IRS는 구직자가 등록 한 문서에 대해 보증하거나 별도의 책임을 지지 않으며<br>첨부된 문서를 신뢰하여 발생한 법적
                                        분쟁에 책임을 지지 않습니다.<br>또한 구인/구직 목적 외 다른 목적으로 이용시 이력서 삭제 혹은 비공개 조치가 될 수 있습니다.</div>
                                </div>
                            </div>
                
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

<!-- <style>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

.body-i {
    font-family: 'Arial', sans-serif;
    background-color: #f4f4f4;
    justify-content: center;
    align-items: center;
    height: 100vh;
}

.header-i {
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

.container1 {
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
    height: 500px;
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
    width: 100%;
    padding: 30px;
    border-radius: 10px;
    background-color: #F2F4F7;
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
    /* background-color: #f9fafb; */
    padding: 10px;
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

.table { width: 100%; border-top: 1px solid #000; background-color: #fff; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.table th { height: 51px; text-align: center; background-color: #fcfcfc; border-left: 1px solid #edeef0; border-right: 1px solid #edeef0; border-bottom: 1px solid #edeef0;font-size:12px;letter-spacing: 0px; color: #000; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.table td { padding: 0 18px; border-bottom: 1px solid #edeef0;font-size:14px;letter-spacing: 0px; color: #000; text-align: left; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.table tr th:first-child {border-left: 0;}
.table .content:first-child { padding-top: 16px; }
.table .content:last-child { margin-bottom: 16px; }
.table .content.is-label .item { padding-left: 84px; }
.table .content .item { position: relative; }
.table .content .label { position: absolute; left: 0; top: 0; width: 76px;font-size:14px;letter-spacing: 0px; color: #000; line-height: 1.9; }
.table .content .label:after { content: ''; position: absolute; right: 0; top: 0; width: 1px; height: 11px; background-color: #ddd; -webkit-transform: translateY(9px); -moz-transform: translateY(9px); -ms-transform: translateY(9px); -o-transform: translateY(9px); transform: translateY(9px); }
.table .content .value {font-size:14px;letter-spacing: 0px; color: #000; line-height: 1.9; }
.table-hopework th { width: 149px; }
.resume-view-wrapper { position: relative; width: 100%; text-align: center; }
.resume-view-container { position: relative; width: 940px; margin: 0 auto; padding-bottom: 75px; text-align: left; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.body-i.resume-view-print .resume-view-container { padding-top: 30px; }
.body-i.resume-view { background-color: #f2f4f7; }
.resume-print { overflow: hidden; margin-bottom: 10px; }
.resume-print .button-print { position: relative; float: right; display: block; width: 133px; height: 44px; background-color: #002AFF;font-size:14px;letter-spacing: 0px; color: #fff; }
.resume-print .button-print:before { content: ''; display: block; position: absolute; left: 30px; top: 50%; width: 16px; height: 15px; background: url("/content/images/text_user/resume/view/icon-print.png") no-repeat; -webkit-transform: translateY(-50%); -moz-transform: translateY(-50%); -ms-transform: translateY(-50%); -o-transform: translateY(-50%); transform: translateY(-50%); }
.resume-print .button-print span { display: block; width: 133px; height: 44px; padding: 11px 0 0 53px; text-align: left; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.body-i.resume-view-print { background-color: #fff; }
.body-i.resume-view-print .list { border-left: 1px solid #ddd; border-right: 1px solid #ddd; border-bottom: 1px solid #ddd; }
.body-i.resume-view-print .table { border-left: 1px solid #ddd; border-right: 1px solid #ddd; }
.body-i.resume-view-print .profile .container { border: 1px solid #ddd; }
.body-i.resume-view-print .profile .summary { border: 1px solid #ddd; }
.body-i.resume-preview .resume-view-wrapper { background-color: #f2f4f7; }
.body-i.resume-preview .resume-view-container { padding-top: 104px; }
.body-i.resume-preview .resume-header { z-index: 9000; position: fixed; width: 100%; height: 67px; padding: 20px 0 0 28px; border-bottom: 1px solid #e8e9ed; font-size: 0; background-color: #fff; text-align: left; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.body-i.resume-preview .resume-header h1.header { display: inline-block; vertical-align: top;font-size:20px;letter-spacing: -0.5px; color: #333; }
.body-i.resume-preview .resume-header .subtitle { display: inline-block; margin-left: 13px; vertical-align: top;font-size:14px;letter-spacing: 0px; color: #333; -webkit-transform: translateY(7px); -moz-transform: translateY(7px); -ms-transform: translateY(7px); -o-transform: translateY(7px); transform: translateY(7px); }
.body-i.resume-preview .resume-header .buttons { z-index: 10; position: absolute; right: 28px; top: 11px; }
.body-i.resume-preview .resume-header .button-print { position: relative; float: right; display: block; width: 133px; height: 44px; background-color: #002AFF;font-size:14px;letter-spacing: 0px; color: #fff; }
.body-i.resume-preview .resume-header .button-print:before { content: ''; display: block; position: absolute; left: 30px; top: 50%; width: 16px; height: 15px; background: url("/content/images/mtc/resume/icon-print.png") no-repeat; transform: translateY(-50%);}
.body-i.resume-preview .resume-header .button-print span { display: block; width: 133px; height: 44px; line-height: 44px; padding: 0 0 0 53px; text-align: left; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.body-i.resume-preview .resume-page { height: 100%; }
.print-footer { overflow: hidden; padding: 100px 0 30px; }
.print-footer .print-number { text-align: right;font-size:14px;letter-spacing: 0px; color: #888; }
.hidden { display: none; }
.resume-subject { margin-bottom: 24px;font-size:30px;letter-spacing: -0.5px; color: #000; line-height: 1.4; }
.resume-resolution { position: relative; width: 100%; padding-left: 84px; margin-bottom: 30px; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.resume-resolution .label { position: absolute; left: 0; top: 1px; width: 76px; height: 20px; color: #333;font-size:12px;letter-spacing: 0px; border: 1px solid #cbccd0; text-align: center; line-height: 20px;}
.resume-resolution .value {font-size:14px;letter-spacing: 0px; color: #333; line-height: 1.55; }
.body-i.resume-preview .resume-subject { padding-top: 40px; }
.resume-view-container .base { overflow: hidden; position: relative; margin-bottom: 33px; }
.resume-view-container .base.skill { margin-top: -34px;}
.resume-view-container .base.career div.headers { font-size: 0; }
.resume-view-container .base.career div.headers .term { float: left; margin-left: 15px; vertical-align: top; font-size: 0; -webkit-transform: translateY(10px); -moz-transform: translateY(10px); -ms-transform: translateY(10px); -o-transform: translateY(10px); transform: translateY(10px); }
.resume-view-container .base.career div.headers .total { float: left; vertical-align: top;font-size:14px;letter-spacing: 0px; color: #ff5b5b; }
.resume-view-container .base.career div.headers .detail { float: left; margin-left: 6px; vertical-align: top;font-size:14px;letter-spacing: 0px; color: #000; white-space: nowrap; }
.resume-view-container .base.career div.headers .detail em { color: #ff5b5b; }
.resume-view-container .base.career div.headers h2.header { float: left; display: inline-block; vertical-align: top; }
.resume-view-container .base.career div.headers:after { content: ''; display: block; clear: both; }
.resume-view-container h2.header { margin-bottom: 13px;font-size:22px;letter-spacing: -0.5px; color: #000; }
.resume-view-container .categorys { margin-bottom: 10px; font-size: 0; }
.resume-view-container .categorys .item { position: relative; display: inline-block; padding-left: 17px; margin-left: 16px; vertical-align: top;font-size:14px;letter-spacing: 0px; color: #000; }
.resume-view-container .categorys .item:before { content: ''; position: absolute; left: 0; top: 0; width: 1px; height: 11px; background-color: #ddd; -webkit-transform: translateY(4px); -moz-transform: translateY(4px); -ms-transform: translateY(4px); -o-transform: translateY(4px); transform: translateY(4px); }
.resume-view-container .categorys .item:first-child { margin-left: 0; padding-left: 0; }
.resume-view-container .categorys .item:first-child:before { display: none; }
.resume-view-container .warning {padding-top:21px; text-align:center}
.resume-view-container .warning .description {font-size:12px;letter-spacing: 0px; color: #888; line-height: 1.8;}
.profile { overflow: hidden; position: relative; }
.profile.image .container { height: 180px; padding-left: 170px; }
.profile.searching .container { padding-left: 170px; }
.profile .container { overflow: hidden; position: relative; width: 940px; padding: 0 0 20px 40px; margin-bottom: 10px; background-color: #fff; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.profile .photo { z-index: 10; position: absolute; left: 0; top: 0; width: 140px; height: 182px; }
.profile .photo img { width: 140px; height: 182px; }
.profile .info-general { padding-top: 15px; margin-bottom: 13px; font-size: 0; }
.profile .info-general .item { display: inline-block;font-size:14px;letter-spacing: 0px; color: #888; margin-left: 7px; vertical-align: top; }
.profile .info-general .name { margin-left: 0;font-size:20px;letter-spacing: -0.5px; color: #000; }
.profile .info-general .year, .profile .info-general .sex, .profile .info-general .age { -webkit-transform: translateY(8px); -moz-transform: translateY(8px); -ms-transform: translateY(8px); -o-transform: translateY(8px); transform: translateY(8px); }
.profile .info-detail { width: 650px; }
.profile .info-detail .item { position: relative; display: inline-block; width: 300px; padding-left: 83px; margin-bottom: 11px; vertical-align: top; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.profile .info-detail .item-full { display: block; width: 100%; }
.profile .info-detail .label { position: absolute; left: 0; top: 0; width: 68px;font-size:14px;letter-spacing: 0px; color: #888; }
.profile .info-detail .label:after { content: ''; display: block; position: absolute; right: 0; top: 0; width: 1px; height: 11px; background-color: #ddd; -webkit-transform: translateY(6px); -moz-transform: translateY(6px); -ms-transform: translateY(6px); -o-transform: translateY(6px); transform: translateY(6px); }
.profile .info-detail .value {font-size:14px;letter-spacing: 0px; color: #000; }
.profile .summary { position: relative; overflow: hidden; height: 150px; background-color: #fff; font-size: 0; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.profile .summary.col-4 .item { width: 235px; }
.profile .summary.col-4 .item:first-child { width: 233px; }
.profile .summary.is-few { height: auto; }
.profile .summary.is-few .item { height: auto; padding: 27px 15px; }
.profile .summary.is-few .item:after { height: 50px; }
.profile .summary .item { float: left; position: relative; display: inline-block; height: 150px; padding: 27px 15px 0; vertical-align: top; text-align: center; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.profile .summary .item:after { content: ''; display: block; position: absolute; left: 0; top: 50%; width: 1px; height: 89px; background-color: #edeef0; -webkit-transform: translateY(-50%); -moz-transform: translateY(-50%); -ms-transform: translateY(-50%); -o-transform: translateY(-50%); transform: translateY(-50%); }
.profile .summary .item:first-child:after { display: none; }
.profile .summary .item.item-intern .description, .profile .summary .item.item-certificate .description { margin-bottom: 0; }
.profile .summary .item.is-singleline div.header { margin-bottom: 25px; }
.profile .summary .item.newcomer .description { color: #39f; }
.profile .summary div.header { margin-bottom: 7px;font-size:14px;letter-spacing: 0px; color: #888; text-align: center; }
.profile .summary .description { margin-bottom: 5px;font-size:14px;letter-spacing: 0px; color: #000; line-height: 1.6; }
.profile .summary .description.ellipsis { overflow: hidden; white-space: nowrap; -o-text-overflow: ellipsis; text-overflow: ellipsis; }
.profile .summary .description.etc { padding-top: 2px;font-size:13px;letter-spacing: 0px; color: #888; }
.profile .summary .options { font-size: 0; }
.profile .summary .options .option { margin-bottom: 5px;font-size:13px;letter-spacing: 0px; color: #39f; }
.table-attachments {table-layout:fixed;}
.table-attachments td {padding:0;}
.table-attachments td > a {position:relative; display:block; width:100%; padding:0 15px 0 32px; box-sizing:border-box;}
.table-attachments td > a:hover {color:#000;}
.table-attachments td .name {overflow:hidden; width:100%; white-space:nowrap; text-overflow:ellipsis; color:#000;}
.table-attachments td .icon {overflow:hidden; position:absolute; left:12px; top:2px; width:16px; height:16px; background-image:url('/content/images/text_user/resume/view/sprite-icon-file.png'); background-repeat:no-repeat;}
.table-attachments td .icon.docx, .table-attachments td .icon.doc { background-position: 0 -118px; }
.table-attachments td .icon.pptx, .table-attachments td .icon.ppt { background-position: 0 -164px; }
.table-attachments td .icon.xlsx, .table-attachments td .icon.xls, .table-attachments td .icon.excel { background-position: 0 -210px; }
.table-attachments td .icon.pdf { background-position: 0 -256px; }
.table-attachments td .icon.hwp { background-position: 0 -303px; }
.table-attachments td .icon.zip { background-position: 0 -528px; }
.table-attachments td .icon.jpg, .table-attachments td .icon.jpeg { background-position: 0 -623px; }
.table-attachments td .icon.gif { background-position: 0 -573px; }
.table-attachments td .icon.psd { background-position: 0 -673px; }
.table-attachments td .icon.png { background-position: 0 -723px; }
.table-attachments td .icon.txt { width: 13px; background-position: -2px -773px; }
.table-attachments td .icon.swf { background-position: 0 -823px; }
.table-attachments td .icon.ai { background-position: 0 -873px; }
.table-attachments td .icon.url { background-position: 0 -973px; }
.table-attachments td .icon.rtf { background-position: 0 -1089px; }
.table-attachments td .icon.gul { background-position: 0 -1023px; }
.table-attachments td .icon.alz { width: 13px; background-position: -2px -1155px; }
@media print { .resumeHeader { display: none; }
.resume-header { display: none; }
.resume-print { display: none; }
.resume-view-wrapper { width: 100%;}
.resume-view-container { padding-top: 0 !important; }
.sidemenu { display: none; }
.profile .container { width: 100%; }
.profile .summary { padding-top: 25px; border: 1px solid #ddd; }
.profile .summary.col-5 .item { width: 20%; }
.profile .summary.col-4 .item { overflow: hidden; width: 25%; }
.profile .summary .item { height: auto; min-height: 96px; padding-top: 0; border-left: 1px solid #ddd; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.profile .summary .item:first-child { border-left: 0; }
.profile .info-detail .item { width: 40%; }
.table-special th { width: 16.666%; }
.table-special th:first-child { width: 15%; }
.table-special th:nth-child(2) { width: 17%; }
.table-special th:nth-child(3) { width: 17%; }
.table-special td { width: 16.666%; padding: 0; text-align: center; }
.table-special td:first-child { width: 15%; }
.table-special td:nth-child(4) { width: 20%; }
.table-special td:nth-child(6) { width: 16%; }
.base.last-child { page-break-after: auto; }
.table-attachments th {width:15%;}
.table-attachments td {width:35%;}
 }
.resume-application { overflow: hidden; position: relative; padding: 40px 0; border-bottom: 1px solid #ddd; }
.resume-application-title {position: relative; margin-bottom: 16px;font-size:16px;letter-spacing: 0px; color: #000; display: -webkit-box; -webkit-box-orient: vertical; overflow: hidden; text-overflow: ellipsis; -webkit-line-clamp: 2; line-height: 24px;}
.resume-application-title .backdrop { position: absolute; left: 0; top: 0; width: 100%; background-color: #f2f4f7; display: -webkit-box; -webkit-box-orient: vertical; overflow: hidden; text-overflow: ellipsis; -webkit-line-clamp: 2;}
.resume-application-title .backdrop.nowrap { overflow-y: scroll; }
.resume-application-meta { overflow: hidden; position: relative; }
.resume-application-meta .item {height: 22px; display: flex; align-items: center;}
.resume-application-meta .item + .item { margin-top: 6px; }
.resume-application-meta .label {width: 52px;font-size:14px;letter-spacing: 0px; color: #666; }
.resume-application-meta .value {padding-left: 12px;font-size:14px;letter-spacing: 0px; color: #222; }
.resume-application-meta .value:before { content: ''; display: inline-block; width: 1px; height: 12px; background-color: #D9DADB; margin-right: 12px;}
.resume-application-meta.email .item { padding-left: 80px; }
.sign { padding-top: 20px; text-align: center; }
.sign .message { margin-bottom: 13px;font-size:22px;letter-spacing: -0.5px; color: #000; }
.sign .writer {font-size:14px;letter-spacing: 0px; color: #000; }
.caution-company { overflow: hidden; position: relative; margin-top: 60px; padding-top: 45px; border-top: 1px solid #ddd; }
.caution-company .item { position: relative; padding-left: 10px; margin-bottom: 12px;font-size:14px;letter-spacing: 0px; color: #888; }
.caution-company .item:before { content: ''; display: block; position: absolute; left: 0; top: 10px; width: 2px; height: 2px; background-color: #aaa; }
.resume-view-print-company .resume-meta { overflow: hidden; position: relative; margin-bottom: 8px; padding-top: 3px; font-size: 0; }
.resume-view-print-company .resume-meta .item { display: inline-block; position: relative; padding-left: 17px; margin-left: 16px;font-size:14px;letter-spacing: 0px; color: #000; vertical-align: top; }
.resume-view-print-company .resume-meta .item:before { content: ''; display: block; position: absolute; left: 0; top: 4px; width: 1px; height: 12px; background-color: #d3d3d3; }
.resume-view-print-company .resume-meta .item:first-child { padding-left: 0; margin-left: 0; }
.resume-view-print-company .resume-meta .item:first-child:before { display: none; }
.personality-test .container { overflow: hidden; position: relative; width: 100%; padding: 24px 39px 34px; border: 1px solid #ddd; background: #fff; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.personality-test h3.header { margin-bottom: 13px;font-size:16px;letter-spacing: 0px; color: #000; }
.personality-test table { width: 100%; margin-bottom: 26px; }
.personality-test table th {font-size:12px;letter-spacing: 0px; color: #000; text-align: center; background-color: #f8f8f8; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.personality-test table td { padding: 11px 0 15px;font-size:14px;letter-spacing: 0px; color: #000; line-height: 1.9; vertical-align: top; text-align: center; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.personality-test table.col th { height: 52px; border-top: 1px solid #000; border-bottom: 1px solid #edeef0; }
.personality-test table.col td { border-bottom: 1px solid #ddd; }
.personality-test table.col td.job { padding: 11px 15px 15px; text-align: left; }
.personality-test table.row { border-top: 1px solid #000; }
.personality-test table.row th { padding-top: 16px; border-bottom: 1px solid #ddd; vertical-align: top; }
.personality-test table.row td { padding: 11px 20px 15px; border-bottom: 1px solid #ddd; text-align: left; }
.personality-test .progress { overflow: hidden; position: relative; width: 100%; height: 94px; padding-top: 36px; background: url("/content/images/text_user/resume/view/bg-progress.png") no-repeat; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.personality-test .progress .bar { height: 30px; padding-top: 4px; background-color: #002AFF; color: #fff;font-size:14px;letter-spacing: 0px; text-align: center; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.list { overflow: hidden; position: relative; border-top: 1px solid #000; background-color: #fff; }
.list.list-introduction { padding: 27px 38px 33px; }
.list.list-introduction div.header { font-weight: bold; }
.list li.item { margin-bottom: 31px; }
.list li.item:last-child { margin-bottom: 0; }
.list li.item div.header { margin-bottom: 9px;font-size:18px;letter-spacing: 0px; color: #000; line-height: 1.9; }
.list li.item .content {font-size:14px;letter-spacing: 0px; color: #000; line-height: 1.8; word-break: break-all;}
.list div.item { overflow: hidden; position: relative; padding-left: 200px; padding-right: 40px; border-top: 1px solid #edeef0; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.list div.item:first-child { border-top: 0; }
.list div.item.etc { border-color: #b2b2b2; }
.list div.item.etc div.header { position: absolute; left: 38px; top: 24px; }
.list .date { position: absolute; left: 38px; top: 23px;font-size:14px;letter-spacing: 0px; color: #000; }
.list .date .term { margin-bottom: 7px;font-size:14px;letter-spacing: 0px; color: #000; }
.list .date .term-calculator {font-size:13px;letter-spacing: 0px; color: #002AFF; }
.list .content-header { margin-bottom: 8px; font-size: 0; line-height: 1.55; }
.list .content-header .name { position: relative;  display: inline;font-size:16px;letter-spacing: 0px; font-weight: bold; color: #000;  word-break: break-all; }
.list .content-header .agency { display: inline; margin-left: 11px;font-size:14px;letter-spacing: 0px; color: #666;  }
.list .content-body {font-size:14px;letter-spacing: 0px; color: #000; line-height: 22px; }
.list .content-body p {font-size:14px;letter-spacing: 0px; color: #000; line-height: 1.8; }
.list.list-education div.item { padding-top: 21px; padding-bottom: 17px; }
.list.list-education div.item .line { display: inline; margin-left: 11px;font-size:14px;letter-spacing: 0px; color: #666; }
.list.list-education div.item.no-description { padding-top: 34px; }
.list.list-education > div.item { min-height: 90px; }
.list.list-education .content-header .name { top: -2px; }
.list.list-education .state {font-size:13px;letter-spacing: 0px; color: #002AFF; }
.list.list-education .info .item { min-height: auto; margin-bottom: 7px; padding: 0 0 0 73px; border-top: 0; }
.list.list-education .info .label { position: absolute; left: 0; top: 0; width: 61px;font-size:14px;letter-spacing: 0px; color: #666; line-height: 1.8; }
.list.list-education .info .label:after { content: ''; display: block; position: absolute; right: 0; top: 0; width: 1px; height: 12px; background-color: #ddd; -webkit-transform: translateY(8px); -moz-transform: translateY(8px); -ms-transform: translateY(8px); -o-transform: translateY(8px); transform: translateY(8px); }
.list.list-education .info .value {font-size:14px;letter-spacing: 0px; color: #666; line-height: 1.8; }
.list.list-career div.item { padding-top: 21px; padding-bottom: 17px; }
.list.list-career > div.item { min-height: 90px; }
.list.list-career .content-header .is-private { display: inline; margin-left: 2px;font-size:14px;letter-spacing: 0px; color: #000; vertical-align: baseline; }
.list.list-career .content-header .position { display: inline; margin-left: 11px;font-size:14px;letter-spacing: 0px; color: #666; vertical-align: baseline; }
.list.list-career .content-body .description { margin-bottom: 9px; }
.list.list-career .info .item { min-height: auto; padding: 0 0 0 73px; border-top: 0; }
.list.list-career .info .label { position: absolute; left: 0; top: 0; width: 61px;font-size:14px;letter-spacing: 0px; color: #666; }
.list.list-career .info .label:after { content: ''; display: block; position: absolute; right: 0; top: 0; width: 1px; height: 12px; background-color: #ddd; -webkit-transform: translateY(12px); -moz-transform: translateY(12px); -ms-transform: translateY(12px); -o-transform: translateY(12px); transform: translateY(12px); }
.list.list-career .info .value {font-size:14px;letter-spacing: 0px; color: #666; }
.list.list-career div.career-description { min-height: auto; padding: 18px 40px 19px 200px; border-color: #b2b2b2; }
.list.list-career div.career-description div.header { position: absolute; left: 40px; top: 21px;font-size:14px;letter-spacing: 0px; color: #000; }
.list.list-career div.career-description .description {font-size:14px;letter-spacing: 0px; color: #000; line-height: 1.8; }
.list.list-intern div.item { padding-top: 21px; padding-bottom: 17px; }
.list.list-intern > div.item { min-height: 90px; }
.list.list-intern .content-header { margin-bottom: 4px; }
.list.list-intern .position { overflow: hidden; display: inline-block; height: 20px; line-height:18px;vertical-align:-3px;margin-left: 9px; padding:0 14px;font-size:12px;letter-spacing: 0px; border: 1px solid #000; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.list.list-intern .position.position1 { color: #ff5b5b; border-color: #fa9899; }
.list.list-intern .position.position2 { color: #7ba71f; border-color: #abc675; }
.list.list-intern .position.position3 { color: #3189ba; border-color: #7eb4d2; }
.list.list-intern .position.position4 { color: #6772df; border-color: #9fa6e9; }
.list.list-intern .position.position5 { color: #4db59d; border-color: #8fcec1; }
.list.list-intern .position.position6 { color: #ff830a; border-color: #f9bc8d; }
.list.list-intern .position.position7 { color: #0029AD; border-color: #0029AD; }
.list.list-certificate .item { min-height: auto; padding-top: 20px; padding-bottom: 14px; }
.list.list-certificate .item.first-child { min-height: auto; }
.list.list-certificate .item.etc { padding-top: 17px; padding-bottom: 20px; }
.list.list-certificate .item.etc div.header { top: 23px;font-size:14px;letter-spacing: 0px; color: #000; }
.list.list-certificate .item.etc .description {font-size:14px;letter-spacing: 0px; color: #000; line-height: 1.8; }
.list.list-certificate .date { top: 23px; }
.list.list-learn div.item { min-height: 97px; padding-top: 20px; padding-bottom: 17px; }
.list.list-learn div.item:first-child { min-height: 96px; }
.list.list-awards .date { top: 21px; }
.list.list-awards div.item { min-height: 97px; padding-top: 21px; padding-bottom: 16px; }
.list.list-awards div.item:first-child { min-height: 96px; }
.list.list-experience div.item { min-height: 90px; padding-top: 20px; padding-bottom: 20px; }
.list.list-experience div.item:first-child { min-height: 89px; }
.list.list-experience .content-header { margin-bottom: 0; }
.list.list-language div.item { min-height: 94px; padding-top: 17px; }
.list.list-language .language { position: absolute; left: 39px; top: 21px;font-size:16px;letter-spacing: 0px; font-weight: bold; color: #000; }
.list.list-language .content { margin-bottom: 19px; }
.list.list-language .content-header { margin-bottom: 5px; }
.list.list-language .category { display: inline-block; vertical-align: top;font-size:16px;letter-spacing: 0px; color: #000; font-weight: bold; }
.list.list-language .passdate { position: relative; top: 2px; display: inline-block; margin-left: 8px; vertical-align: top;font-size:14px;letter-spacing: 0px; color: #000; }
.list.list-portfolio { overflow: hidden; font-size: 0; }
.list.list-portfolio div.item { float: left; overflow: hidden; display: inline-block; width: 50%; height: 67px; padding: 21px 0 0 61px; vertical-align: top; border-top: 0; border-bottom: 1px solid #edeef0; -webkit-box-sizing: border-box; -moz-box-sizing: border-box; box-sizing: border-box; }
.list.list-portfolio div.item:last-child, .list.list-portfolio div.item:nth-last-child(2):nth-child(odd) { border-bottom: 0; }
.list.list-portfolio div.item i.icon { overflow: hidden; display: block; position: absolute; left: 40px; top: 23px; width: 16px; height: 16px; margin-right: 5px; vertical-align: top; background: url("/content/images/text_user/resume/view/sprite-icon-file.png") no-repeat; }
.list.list-portfolio div.item i.icon.docx, .list.list-portfolio div.item i.icon.doc { background-position: 0 -118px; }
.list.list-portfolio div.item i.icon.pptx, .list.list-portfolio div.item i.icon.ppt { background-position: 0 -164px; }
.list.list-portfolio div.item i.icon.xlsx, .list.list-portfolio div.item i.icon.xls, .list.list-portfolio div.item i.icon.excel { background-position: 0 -210px; }
.list.list-portfolio div.item i.icon.pdf { background-position: 0 -256px; }
.list.list-portfolio div.item i.icon.hwp { background-position: 0 -303px; }
.list.list-portfolio div.item i.icon.zip { background-position: 0 -528px; }
.list.list-portfolio div.item i.icon.jpg, .list.list-portfolio div.item i.icon.jpeg { background-position: 0 -623px; }
.list.list-portfolio div.item i.icon.gif { background-position: 0 -573px; }
.list.list-portfolio div.item i.icon.psd { background-position: 0 -673px; }
.list.list-portfolio div.item i.icon.png { background-position: 0 -723px; }
.list.list-portfolio div.item i.icon.txt { width: 13px; background-position: -2px -773px; }
.list.list-portfolio div.item i.icon.swf { background-position: 0 -823px; }
.list.list-portfolio div.item i.icon.ai { background-position: 0 -873px; }
.list.list-portfolio div.item i.icon.url { background-position: 0 -973px; }
.list.list-portfolio div.item i.icon.rtf { background-position: 0 -1089px; }
.list.list-portfolio div.item i.icon.gul { background-position: 0 -1023px; }
.list.list-portfolio div.item i.icon.alz { width: 13px; background-position: -2px -1155px; }
.list.list-portfolio div.item .name { overflow: hidden; display: block;font-size:14px;letter-spacing: 0px; color: #000; white-space: nowrap; -o-text-overflow: ellipsis; text-overflow: ellipsis; }

.skip,
.blind {
  position: absolute !important;
  overflow: hidden;
  width: 1px;
  height: 1px;
  clip: rect(0 0 0 0);
  margin: -1px;
}

#size-buttons button {
  color: white;
  border: none;
  padding: 12px 20px; /* 버튼 크기 키움 */
  margin-right: 10px;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  font-weight: bold; /* 글자 볼드체 */
}
.pass-button {
  background-color: #001f3f; /* 어두운 네이비 색상 */
}

.pass-button:hover {
  background-color: #001a33; /* 호버 시 색상 변경 */
}

.fail-button {
    background-color: #dc3545;
}

.fail-button:hover {
    background-color: #c82333;
}
</style> -->