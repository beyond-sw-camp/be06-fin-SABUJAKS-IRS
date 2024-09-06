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

    <section class="main-content">
      <h2>채용</h2>
      <div class="filter-section">
        <div class="filter-item" data-filter="기업형태">
          <button class="dropdown-btn">기업형태</button>
          <div class="filter-dropdown" id="기업형태">
            <label><input type="checkbox" value="대기업" /> 대기업</label>
            <label><input type="checkbox" value="중소기업" /> 중소기업</label>
            <label
              ><input type="checkbox" value="공공기관/공기업" />
              공공기관/공기업</label
            >
            <label
              ><input type="checkbox" value="외국계기업" /> 외국계기업</label
            >
            <label><input type="checkbox" value="중견기업" /> 중견기업</label>
            <label
              ><input type="checkbox" value="비영리단체/협회/재단" />
              비영리단체/협회/재단</label
            >
          </div>
        </div>
        <div class="filter-item" data-filter="채용형태">
          <button class="dropdown-btn">채용형태</button>
          <div class="filter-dropdown" id="채용형태">
            <label><input type="checkbox" value="신입" /> 신입</label>
            <label><input type="checkbox" value="경력직" /> 경력직</label>
            <label><input type="checkbox" value="인턴" /> 인턴</label>
            <label><input type="checkbox" value="계약직" /> 계약직</label>
          </div>
        </div>
        <div class="filter-item" data-filter="모집직무">
          <button class="dropdown-btn">모집직무</button>
          <div class="filter-dropdown" id="모집직무">
            <label
              ><input type="checkbox" value="영업/고객상담" />
              영업/고객상담</label
            >
            <label><input type="checkbox" value="경영/사무" /> 경영/사무</label>
            <label
              ><input type="checkbox" value="마케팅/광고/홍보" />
              마케팅/광고/홍보</label
            >
            <label><input type="checkbox" value="생산/제조" /> 생산/제조</label>
            <label
              ><input type="checkbox" value="연구개발/설계" />
              연구개발/설계</label
            >
            <label><input type="checkbox" value="IT/인터넷" /> IT/인터넷</label>
          </div>
        </div>
        <div class="filter-item" data-filter="근무지역">
          <button class="dropdown-btn">근무지역</button>
          <div class="filter-dropdown" id="근무지역">
            <label
              ><input type="checkbox" value="지역 제한없음" /> 지역
              제한없음</label
            >
            <label><input type="checkbox" value="서울" /> 서울</label>
            <label><input type="checkbox" value="부산" /> 부산</label>
            <label><input type="checkbox" value="대구" /> 대구</label>
            <label><input type="checkbox" value="인천" /> 인천</label>
            <label><input type="checkbox" value="광주" /> 광주</label>
          </div>
        </div>
        <div class="search-container">
          <input
            type="text"
            id="search-input"
            placeholder="공고명, 기업, 직무 검색"
          />
          <button id="search-btn">검색</button>
        </div>
      </div>

      <!-- <div class="search-container">
            <input type="text" id="search-input" placeholder="공고명, 기업, 직무 검색">
            <button id="search-btn">검색</button>
        </div> -->

      <div class="selected-filters">
        <div id="selected-filters-list"></div>
        <a href="#" id="reset-filters" class="reset-filters">초기화🔄️</a>
      </div>

      <div class="results-header">
        <span>검색 결과 1556건</span>
        <span class="sort-by">최신순 ▼</span>
      </div>

      <table class="job-listing">
        <thead>
          <tr>
            <th>기업명</th>
            <th>공고명</th>
            <th>채용형태</th>
            <th>근무지역</th>
            <th>마감일</th>
            <th>조회수</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>주식회사 더파운즈</td>
            <td>[신입/경력] 더파운더즈/아누아 대규모 채용</td>
            <td>신입</td>
            <td>서울</td>
            <td>~09.19</td>
            <td>3385</td>
          </tr>
          <tr>
            <td>빔산토리코리아</td>
            <td>빔산토리코리아 서울지역 담당 주류영업</td>
            <td>신입</td>
            <td>서울</td>
            <td>~09.04</td>
            <td>830</td>
          </tr>
          <tr>
            <td>현대자동차</td>
            <td>[현대자동차] 9월 신입사원 채용</td>
            <td>신입</td>
            <td>서울 외</td>
            <td>~09.14</td>
            <td>9999+</td>
          </tr>
        </tbody>
      </table>
    </section>
    
  </body>
</template>
<script setup>
import "./AnnounceReadAllPage.css";
</script>

<style>
</style>
// 필터 아이템을 클릭했을 때 드롭다운을 토글하는 함수
document.querySelectorAll(".filter-item").forEach((item) => {
  item.addEventListener("click", function () {
    const filterName = this.getAttribute("data-filter");
    const dropdown = document.getElementById(filterName);

    // 다른 드롭다운이 열려 있으면 닫기
    document.querySelectorAll(".filter-dropdown").forEach((drop) => {
      if (drop !== dropdown) {
        drop.style.display = "none";
      }
    });

    // 클릭한 드롭다운을 토글
    if (dropdown.style.display === "block") {
      dropdown.style.display = "none";
    } else {
      dropdown.style.display = "block";
    }
  });
});

// 선택한 필터 항목을 저장하는 배열
let selectedFilters = [];

// 각 체크박스를 클릭했을 때 선택한 항목을 업데이트
document
  .querySelectorAll('.filter-dropdown input[type="checkbox"]')
  .forEach((checkbox) => {
    checkbox.addEventListener("change", function () {
      const filterValue = this.value;

      if (this.checked) {
        // 체크된 경우 배열에 추가
        selectedFilters.push(filterValue);
      } else {
        // 체크 해제된 경우 배열에서 제거
        selectedFilters = selectedFilters.filter(
          (item) => item !== filterValue
        );
      }

      // 선택한 필터 항목 업데이트
      updateSelectedFilters();
    });
  });

// 선택된 필터 항목을 화면에 표시하는 함수
function updateSelectedFilters() {
  const selectedList = document.getElementById("selected-filters-list");
  selectedList.innerHTML = "";

  selectedFilters.forEach((filter) => {
    const filterSpan = document.createElement("span");
    filterSpan.textContent = filter + " ✕";
    filterSpan.style.marginRight = "10px";
    filterSpan.style.cursor = "pointer";

    // 필터 항목을 클릭하면 해당 항목을 해제
    filterSpan.addEventListener("click", function () {
      selectedFilters = selectedFilters.filter((item) => item !== filter);
      updateSelectedFilters();

      // 해당 체크박스도 해제
      document.querySelector(
        `.filter-dropdown input[value="${filter}"]`
      ).checked = false;
    });

    selectedList.appendChild(filterSpan);
  });
}

// 초기화 버튼 클릭 시 모든 선택을 초기화
document.getElementById("reset-filters").addEventListener("click", function () {
  selectedFilters = [];
  updateSelectedFilters();

  // 모든 체크박스 해제
  document
    .querySelectorAll('.filter-dropdown input[type="checkbox"]')
    .forEach((checkbox) => {
      checkbox.checked = false;
    });
});

// 검색 버튼 클릭 시 동작하는 함수
document.getElementById("search-btn").addEventListener("click", function () {
  const searchText = document
    .getElementById("search-input")
    .value.toLowerCase();

  // 검색어에 맞는 결과를 필터링하거나 화면에 보여줄 로직 작성
  console.log("검색어:", searchText);

  // 예시: 검색된 텍스트와 일치하는 항목을 처리 (이 부분은 실제 검색 로직에 맞게 구현하기)
  // 선택된 검색할 토글 단어를 가져와서 검색하는 기능 추가, 현재는 검색창에 입력한 글자를 검색하는 느낌
  if (searchText) {
    alert("검색 기능 실행: " + searchText);
  } else {
    alert("검색어를 입력하세요.");
  }
});

// 드롭다운 토글 버튼과 메뉴 가져오기
var dropdownToggle = document.getElementById("dropdown-toggle");
var dropdownMenu = document.getElementById("dropdown-menu");

// '로그인 한 회원 ▼' 클릭 시 드롭다운 메뉴 토글
dropdownToggle.onclick = function (event) {
  event.preventDefault(); // 링크 기본 동작 방지
  // 드롭다운 메뉴가 보이는지 여부에 따라 display 설정
  if (dropdownMenu.style.display === "block") {
    dropdownMenu.style.display = "none";
  } else {
    dropdownMenu.style.display = "block";
  }
};

// 페이지 다른 곳 클릭 시 드롭다운 메뉴 닫기
window.onclick = function (event) {
  if (!event.target.matches("#dropdown-toggle")) {
    dropdownMenu.style.display = "none";
  }
};