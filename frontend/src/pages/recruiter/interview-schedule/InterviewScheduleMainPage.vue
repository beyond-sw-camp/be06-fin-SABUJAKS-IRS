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
            <h1>면접 관리</h1>
            <table class="review-table">
                <tr>
                    <th>번호</th>
                    <th>신입/경력</th>
                </tr>
                <tr class="" data-type="신입" onclick="openModal(this);">
                    <td>1</td>
                    <td>신입</td>
                </tr>
                <tr class="" data-type="경력" onclick="openModal(this);">
                    <td>2</td>
                    <td>경력</td>
                </tr>
            </table>
        </div>

        <div id="myModal" class="modal">
            <div class="modal-content" id="draggableModal">
                <span class="close" style="float: right;">&times;</span>
                <h2 id="modal-title"></h2> <!-- 제목 추가 -->
                <div class="col-12 row mt-50 modal-section">
                    <div class="modal-left col-5 margin-auto">
                        <div class="form-group col-12 row">
                            <div class="col-11">
                                <label for="applicant">후보자 <span class="required">*</span></label>
                                <input type="text" id="applicant" placeholder="후보자를 추가해주세요." disabled>
                            </div>
                            
                            <div class="col-1 add-button-section">
                                <button class="add-button" id="add-email-button">+</button>
                            </div>
                        </div>
                        <div id="selected-filters-list"></div>
                        <div class="form-group col-12 row">
                            <div class="col-11">
                                <label for="participants">면접 참가자</label>
                                <input type="text" id="participants" placeholder="이메일을 입력해 주세요.">
                            </div>
                            <div class="col-1 add-button-section">
                                <button class="add-email">+</button>
                            </div>
                        </div>
                        <div id="selected-emails-list"></div>
                        <div class="form-group">
                            <!-- <label for="interview-date">면접일정<span class="required"></span></label> -->
                            <div class="col-12">
                                <!-- calender -->
                                <div class="form-group">
                                    <label for="interview-date" class="subtitle">날짜 <span class="required">*</span></label>
                                
                                    <input type="date" id="interview-date" placeholder="">
                                </div>

                                <!-- 시작시간 -->
                                <div class="form-group">
                                    <label for="interview-date" class="subtitle">시작시간 <span class="required">*</span></label>
                                
                                    <select class="time-select interview-calender">
                                        <option value="">시간을 선택하세요</option>
                                        <option value="09:00">09:00</option>
                                        <option value="10:00">10:00</option>
                                        <option value="11:00">11:00</option>
                                        <option value="12:00">12:00</option>
                                        <option value="13:00">13:00</option>
                                        <option value="14:00">14:00</option>
                                        <option value="15:00">15:00</option>
                                        <option value="16:00">16:00</option>
                                        <option value="17:00">17:00</option>
                                    </select>
                                </div>

                                <!-- 종료시간 -->
                                <div class="form-group">
                                    <label for="interview-date" class="subtitle">종료시간 <span class="required">*</span></label>
                            
                                    <select class="time-select interview-calender">
                                        <option value="">시간을 선택하세요</option>
                                        <option value="10:00">10:00</option>
                                        <option value="11:00">11:00</option>
                                        <option value="12:00">12:00</option>
                                        <option value="13:00">13:00</option>
                                        <option value="14:00">14:00</option>
                                        <option value="15:00">15:00</option>
                                        <option value="16:00">16:00</option>
                                        <option value="17:00">17:00</option>
                                        <option value="18:00">18:00</option>
                                    </select>
                                </div>

                            </div>
                            
                        </div>

                        <div class="col-12">
                            <button class="submit-button">등록</button>
                        </div>
                    </div>
                    <div class="modal-right col-5 margin-auto">
                        <div class="calendar">
                            <!-- 캘린더는 여기에 구현 -->
                            <p>2024년 3월</p>
                            <div class="calendar-grid">
                                <div>일</div>
                                <div>월</div>
                                <div>화</div>
                                <div>수</div>
                                <div>목</div>
                                <div>금</div>
                                <div>토</div>
                                <!-- 날짜를 여기에 추가 -->
                            </div>
                        </div>
                        
                        <div id="interviewer">
                            <div id="interviewers-list">
                                <h3>면접자 목록</h3>
                                <form id="nameForm">
                                    <label><input type="checkbox" name="name" value="서시현"> 서시현</label><br>
                                    <label><input type="checkbox" name="name" value="구은주"> 구은주</label><br>
                                    <label><input type="checkbox" name="name" value="박종성"> 박종성</label><br>
                                    <label><input type="checkbox" name="name" value="서재은"> 서재은</label><br>
                                    <label><input type="checkbox" name="name" value="한별"> 한별</label><br>
                                    <label><input type="checkbox" name="name" value="곽동현"> 곽동현</label><br>
                                    <label><input type="checkbox" name="name" value="서시현"> 서시현</label><br>
                                    <label><input type="checkbox" name="name" value="구은주"> 구은주</label><br>
                                    <label><input type="checkbox" name="name" value="박종성"> 박종성</label><br>
                                    <label><input type="checkbox" name="name" value="서재은"> 서재은</label><br>
                                    <label><input type="checkbox" name="name" value="한별"> 한별</label><br>
                                    <label><input type="checkbox" name="name" value="곽동현"> 곽동현</label><br>
                                    <label><input type="checkbox" name="name" value="서시현"> 서시현</label><br>
                                    <label><input type="checkbox" name="name" value="구은주"> 구은주</label><br>
                                    <label><input type="checkbox" name="name" value="박종성"> 박종성</label><br>
                                    <label><input type="checkbox" name="name" value="서재은"> 서재은</label><br>
                                    <label><input type="checkbox" name="name" value="한별"> 한별</label><br>
                                    <label><input type="checkbox" name="name" value="곽동현"> 곽동현</label><br>
                                </form>
                            </div>
                            <div class="add-button-section">
                                <button class="submit-button" id="select-button" type="submit">선택</button>
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
import "../../../assets/css/style.css"
import "../../../assets/css/grid.css"

// // 모달 관련 스크립트
// var modal = document.getElementById("myModal");
// var closeBtn = document.getElementsByClassName("close")[0];

// // 이름 클릭 시 모달 열기
// function openModal(row) {
//     var type = row.getAttribute('data-type'); // 클릭한 행의 데이터 타입 가져오기
//     document.getElementById('modal-title').innerText = type; // 모달 제목 설정
//     modal.style.display = "flex"; // 모달 열기
// }

// // 모달 닫기
// closeBtn.onclick = function() {
//     modal.style.display = "none";
//     // 모달의 모든 입력 필드 초기화
//     document.querySelectorAll('#myModal input[type="text"], #myModal input[type="date"], #myModal select').forEach(input => {
//         input.value = '';
//     });

//     // 모든 체크박스 해제
//     document.querySelectorAll('#interviewers-list input[type="checkbox"]').forEach(checkbox => {
//         checkbox.checked = false;
//     });

//     // 선택된 필터 목록 초기화
//     selectedFilters = [];
//     updateSelectedFilters();

//     // 추가된 이메일 목록 초기화
//     selectedEmails = [];
//     updateEmailList();

//     // 면접자 목록이 보이도록 초기화
//     const modalRight = document.querySelector('.modal-right');
//     const interviewersList = document.getElementById('interviewer');
//     const calendar = modalRight.querySelector('.calendar');
    
//     calendar.style.display = 'block';
//     interviewersList.style.display = 'none';
// }

// document.querySelectorAll('.add-button').forEach(button => {
//     button.addEventListener('click', function() {
//         const modalRight = document.querySelector('.modal-right');
//         const interviewersList = document.getElementById('interviewer');
//         const calendar = modalRight.querySelector('.calendar');
        
//         if(calendar.style.display == 'none') {
//             // 캘린더는 숨기고 면접자 목록은 보이게 설정
//             calendar.style.display = 'block';
//             interviewersList.style.display = 'none';
//         } else {
//             // 캘린더는 숨기고 면접자 목록은 보이게 설정
//             calendar.style.display = 'none';
//             interviewersList.style.display = 'block';
//         }
        
//     });
// });


// // 학생 고르는 스크립트
// // 선택한 필터 항목을 저장하는 배열
// let selectedFilters = [];

// // 선택 버튼 클릭 시 선택한 항목 업데이트
// document.getElementById('select-button').addEventListener('click', function() {
//     selectedFilters = []; // 이전 선택 초기화
//     document.querySelectorAll('#interviewers-list input[type="checkbox"]:checked').forEach(checkbox => {
//         selectedFilters.push(checkbox.value);
//     });
//     updateSelectedFilters();
//     const modalRight = document.querySelector('.modal-right');
//     const interviewersList = document.getElementById('interviewer');
//     const calendar = modalRight.querySelector('.calendar');

//     calendar.style.display = 'block';
//     interviewersList.style.display = 'none';
// });

// // 선택된 필터 항목을 화면에 표시하는 함수
// function updateSelectedFilters() {
//     const selectedList = document.getElementById('selected-filters-list');
//     selectedList.innerHTML = '';

//     selectedFilters.forEach(filter => {
//         const filterSpan = document.createElement('span');
//         filterSpan.textContent = filter + ' ✕';
//         filterSpan.style.marginRight = '10px';
//         filterSpan.style.cursor = 'pointer';

//         // 필터 항목을 클릭하면 해당 항목을 해제
//         filterSpan.addEventListener('click', function() {
//             selectedFilters = selectedFilters.filter(item => item !== filter);
//             updateSelectedFilters();

//             // 해당 체크박스도 해제
//             document.querySelector(`#interviewers-list input[value="${filter}"]`).checked = false;
//         });

//         selectedList.appendChild(filterSpan);
//     });
// }

// // 이메일 추가하는 스크립트
// let selectedEmails = [];

// // 이메일 추가 버튼 클릭 시 이메일을 추가하는 함수
// document.querySelector('.add-email').addEventListener('click', function() {
//     const emailInput = document.getElementById('participants');
//     const email = emailInput.value.trim();
    
//     if (email && !selectedEmails.includes(email)) {
//         selectedEmails.push(email);
//         updateEmailList();
//         emailInput.value = ''; // 입력란 초기화
//     }
// });

// // 추가된 이메일 목록을 화면에 업데이트하는 함수
// function updateEmailList() {
//     const emailList = document.getElementById('selected-emails-list');
//     emailList.innerHTML = '';

//     selectedEmails.forEach(email => {
//         const emailSpan = document.createElement('span');
//         emailSpan.classList.add('email-item');
//         emailSpan.textContent = email + ' ✕';
//         emailSpan.addEventListener('click', function() {
//             selectedEmails = selectedEmails.filter(item => item !== email);
//             updateEmailList();
//         });

//         emailList.appendChild(emailSpan);
//     });
// }
</script>

<style>

</style>