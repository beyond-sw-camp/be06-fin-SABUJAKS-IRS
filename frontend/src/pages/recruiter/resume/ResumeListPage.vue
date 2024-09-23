<template>
  <MainHeaderComponent></MainHeaderComponent>
  <div class="container">
      <MainSideBarComponent></MainSideBarComponent>
          <div id="content">
              <h1>지원자 리스트</h1>
              <table>
                  <tbody>
                  <tr>
                    <th>번호</th>
                    <th>이름</th>
                    <th>신입/경력</th>
                    <th>결과</th>
                    <th>비고 <input type="checkbox"></th>
                  </tr>
                  <tr onclick="location.href='./seeker_detail.html';">
                    <td>1</td>
                    <td><a class="applicant-name" data-pdf="./seeker1.pdf">서시현</a></td>
                    <td>신입</td>
                    <td>합격</td>
                    <td><input type="checkbox"></td>
                  </tr>
                  </tbody>
              </table>
              <div class="button-container">
                  <button class="register-button">결과전송</button>
              </div>
              <div id="size-buttons">
                  <button>1</button>
                  <button>2</button>
                  <button>3</button>
              </div>
        </div>
    </div>
</template>

<script setup>
// // 모달 관련 스크립트
// var modal = document.getElementById("myModal");
// var pdfPreview = document.getElementById("pdfPreview");
// var closeBtn = document.getElementsByClassName("close")[0];

// // 이름 클릭 시 모달 열기
// document.querySelectorAll('.applicant-name').forEach(item => {
//     item.addEventListener('click', event => {
//         event.preventDefault();
//         var pdfPath = item.getAttribute('data-pdf');
//         pdfPreview.src = pdfPath;
//         modal.style.display = "block";
//     });
// });

// // 모달 닫기
// closeBtn.onclick = function() {
//     modal.style.display = "none";
//     pdfPreview.src = ""; // PDF 미리보기 초기화
// }

// var dragItem = document.getElementById("draggableModal");
// var modal = document.getElementById("myModal");
// var pos1 = 0, pos2 = 0, pos3 = 0, pos4 = 0;

// dragItem.onmousedown = dragMouseDown;

// function dragMouseDown(e) {
//     e = e || window.event;
//     pos3 = e.clientX;
//     pos4 = e.clientY;
//     document.onmouseup = closeDragElement;
//     document.onmousemove = elementDrag;
// }

// function elementDrag(e) {
//     e = e || window.event;
//     pos1 = pos3 - e.clientX;
//     pos2 = pos4 - e.clientY;
//     pos3 = e.clientX;
//     pos4 = e.clientY;
//     modal.style.top = (modal.offsetTop - pos2) + "px";
//     modal.style.left = (modal.offsetLeft - pos1) + "px";
// }

// function closeDragElement() {
//     document.onmouseup = null;
//     document.onmousemove = null;
// }

import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
</script>

<style>

</style>