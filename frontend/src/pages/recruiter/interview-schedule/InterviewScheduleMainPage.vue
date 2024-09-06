<template>
    <MainHeaderComponent></MainHeaderComponent>
    <div class="container">
        <MainSideBarComponent></MainSideBarComponent>
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
</template>

<script setup>
import MainHeaderComponent from '../../../components/recruiter/MainHeaderComponent.vue';
import MainSideBarComponent from '../../../components/recruiter/MainSideBarComponent.vue';
import '@/assets/css/grid.css';

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

<style scoped>
.container {
  width: 80%;
  margin: 0 auto;
}
#content {
  margin-left: 200px;
  padding: 20px;
}

#content h1 {
  font-size: 24px;
  margin: 50px 0;
}

table {
  width: 100%;
  border-collapse: collapse;
  margin-bottom: 40px;
}

table, th, td {
  border: 1px solid #ddd;
}

th, td {
  padding: 25px;
  text-align: left;
}

th {
  background-color: #f1f1f1;
}

#size-buttons button {
  margin-right: 10px;
  padding: 10px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

#size-buttons button:hover {
  background-color: #ddd;
}

#user-profile span {
  margin-right: 10px;
}

#user-profile a {
  color: #ffffff;
  text-decoration: none;
  border: 1px solid #ffffff;
  padding: 5px 10px;
  border-radius: 5px;
}

#content {
  flex: 1;
  margin-left: 200px; /* 사이드바 너비만큼 왼쪽 여백 추가 */
  padding: 150px 0;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

#content table {
  width: 100%; /* 테이블이 남은 공간을 여유롭게 차지하도록 */
  border-collapse: collapse;
}

.review-table th:nth-child(1) { /* 첫 번째 열 (번호) */
  width: 20%; /* 비율 조정 */
}

.review-table th:nth-child(2) { /* 두 번째 열 (신입/경력) */
  width: 80%; /* 비율 조정 */
}
/* 공통 모달

*/
/******** 면접 일정 모달창 CSS *********/
.modal {
  display: none;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
}

.modal-content {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 80%;
  height: 80%;
}

.modal-section {
  height: 90%;
}

.close {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}
.close:hover,
.close:focus {
  color: black;
  text-decoration: none;
  cursor: pointer;
}

h2 {
  margin: 0 0 20px;
}

.form-group {
  margin-bottom: 25px;
}

label {
  display: block;
  margin-bottom: 15px;
  font-size: 1.1rem;
  font-weight: 600;
}

label .subtitle {
  font-weight: 400 !important;
}

input[type="text"] {
  width: calc(100% - 40px);
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.add-button, .add-email {
  padding: 10px;
  background-color: #232b16;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.required {
  color: red;
}

.calendar {
  margin-top: 20px;
}

.calendar-grid {
  display: grid;
  grid-template-columns: repeat(7, 1fr);
  gap: 5px;
  margin-top: 10px;
}

.calendar-grid div {
  padding: 10px;
  text-align: center;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.submit-button {
  width: 100%;
  margin-top: 20px;
  padding: 10px;
  background-color: #232b16;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

.add-button-section {
  display: flex !important;
  flex-direction: column;
  justify-content: end;
}

#interview-date, .interview-calender {
  width: 100%;
  height: 30px;
  text-align: center;
  font-size: 1rem;
}

#interviewer {
  display: none;
  overflow: auto;
  height: 100%;
}

#interviewers-list {
  max-height: 40%;
  overflow-y: auto;
  padding: 10px;
  border: 1px solid #ccc;
}

#selected-filters-list, #selected-emails-list {
  margin-bottom: 20px;
}

#selected-filters-list span, #selected-emails-list span{
  background-color: #e0e0e0;
  padding: 5px 10px;
  margin-right: 10px;
  margin-bottom: 10px;
  border-radius: 20px;
  display: inline-block;
  font-size: 14px;
  color: #333;
  cursor: pointer;
}
</style>