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

<style>

</style>