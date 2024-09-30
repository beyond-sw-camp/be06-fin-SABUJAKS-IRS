<template>
  <div class="body-arp">
    <SeekerHeaderComponent></SeekerHeaderComponent>
    <section class="main-content">
      <h2>채용</h2>
      <div class="filter-section">
        <!-- 기업형태 필터 -->
        <div class="filter-item" data-filter="기업형태">
          <button class="dropdown-btn" @click="toggleDropdown('기업형태')">기업형태</button>
          <div class="filter-dropdown" v-show="dropdownOpen['기업형태']">
            <label><input type="checkbox" value="대기업" @change="updateFilters('기업형태', '대기업')" /> 대기업</label>
            <label><input type="checkbox" value="중소기업" @change="updateFilters('기업형태', '중소기업')" /> 중소기업</label>
            <label><input type="checkbox" value="공공기관/공기업" @change="updateFilters('기업형태', '공공기관/공기업')" />
              공공기관/공기업</label>
            <label><input type="checkbox" value="외국계기업" @change="updateFilters('기업형태', '외국계기업')" /> 외국계기업</label>
            <label><input type="checkbox" value="중견기업" @change="updateFilters('기업형태', '중견기업')" /> 중견기업</label>
            <label><input type="checkbox" value="비영리단체/협회/재단" @change="updateFilters('기업형태', '비영리단체/협회/재단')" />
              비영리단체/협회/재단</label>
            <label><input type="checkbox" value="벤처기업" @change="updateFilters('기업형태', '벤처기업')" /> 벤처기업</label>
          </div>
        </div>
        <!-- 채용형태 필터 -->
        <div class="filter-item" data-filter="채용형태">
          <button class="dropdown-btn" @click="toggleDropdown('채용형태')">채용형태</button>
          <div class="filter-dropdown" v-show="dropdownOpen['채용형태']">
            <label><input type="checkbox" value="신입" @change="updateFilters('채용형태', '신입')" /> 신입</label>
            <label><input type="checkbox" value="경력" @change="updateFilters('채용형태', '경력')" /> 경력</label>
          </div>
        </div>
        <!-- 모집직무 필터 -->
        <div class="filter-item" data-filter="모집직무">
          <button class="dropdown-btn" @click="toggleDropdown('모집직무')">모집직무</button>
          <div class="filter-dropdown" v-show="dropdownOpen['모집직무']">
            <label><input type="checkbox" value="영업/고객상담" @change="updateFilters('모집직무', '영업/고객상담')" /> 영업/고객상담</label>
            <label><input type="checkbox" value="경영/사무" @change="updateFilters('모집직무', '경영/사무')" /> 경영/사무</label>
            <label><input type="checkbox" value="마케팅/광고/홍보" @change="updateFilters('모집직무', '마케팅/광고/홍보')" />
              마케팅/광고/홍보</label>
            <label><input type="checkbox" value="생산/제조" @change="updateFilters('모집직무', '생산/제조')" /> 생산/제조</label>
            <label><input type="checkbox" value="연구개발/설계" @change="updateFilters('모집직무', '연구개발/설계')" /> 연구개발/설계</label>
            <label><input type="checkbox" value="IT/인터넷" @change="updateFilters('모집직무', 'IT/인터넷')" /> IT/인터넷</label>
          </div>
        </div>
        <!-- 근무지역 필터 -->
        <div class="filter-item" data-filter="근무지역">
          <button class="dropdown-btn" @click="toggleDropdown('근무지역')">근무지역</button>
          <div class="filter-dropdown" v-show="dropdownOpen['근무지역']">
            <label><input type="checkbox" value="지역 제한없음" @change="updateFilters('근무지역', '지역 제한없음')" /> 지역 제한없음</label>
            <label><input type="checkbox" value="서울" @change="updateFilters('근무지역', '서울')" /> 서울</label>
            <label><input type="checkbox" value="부산" @change="updateFilters('근무지역', '부산')" /> 부산</label>
            <label><input type="checkbox" value="대구" @change="updateFilters('근무지역', '대구')" /> 대구</label>
            <label><input type="checkbox" value="인천" @change="updateFilters('근무지역', '인천')" /> 인천</label>
            <label><input type="checkbox" value="광주" @change="updateFilters('근무지역', '광주')" /> 광주</label>
          </div>
        </div>
        <div class="search-container">
          <!-- <input type="text" id="search-input" placeholder="공고명, 기업, 직무, 키워드 검색" />
          <button id="search-btn">검색</button> -->
          <input ref="searchInput" type="text" v-model="searchKeyword" placeholder="공고명, 모집분야 키워드 *토글초기화*"
            @keyup.enter="handleSearch" class="search-input" />
          <button @click="handleSearch" class="search-btn">검색</button>
        </div>
      </div>

      <!-- 선택된 필터들 -->
      <div class="selected-filters">
        <div id="selected-filters-list">
          <span v-for="filter in selectedFilters" :key="filter.name + filter.value" @click="removeFilter(filter)">
            {{ filter.name }}: {{ filter.value }} ✕
          </span>
        </div>
        <a href="#" id="reset-filters" class="reset-filters" @click.prevent="resetFilters">초기화🔄️</a>
      </div>

      <!-- 검색 결과 -->
      <div class="results-header">
        <span>검색 결과 {{ announcementCount }}건</span>
        <span class="sort-by">최신순 ▼</span>
      </div>

      <table class="job-listing">
        <thead>
          <tr>
            <th>번호</th>
            <th>기업명</th>
            <th>공고명</th>
            <th>모집분야</th>
            <th>채용형태</th>
            <th>근무지역</th>
            <th>마감일</th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="(announcement, index) in announcementStore.announcements2" :key="announcement.announcementIdx"
            @click="goToDetailPage(announcement.announcementIdx)" class="hoverable-row">
            <td>{{ index + 1 }}</td>
            <td>{{ announcement.companyName }}</td>
            <td>{{ announcement.announcementTitle }}</td>
            <td>{{ announcement.jobTitle }}</td>
            <td>{{ announcement.careerBase }}</td>
            <td>{{ announcement.region }}</td>
            <td>~ {{ announcement.announcementEnd }}</td>
          </tr>
        </tbody>
      </table>
    </section>
    <SeekerFooterComponent></SeekerFooterComponent>
  </div>
</template>

<script setup>
import SeekerHeaderComponent from "@/components/seeker/SeekerHeaderComponent.vue";
import SeekerFooterComponent from "@/components/seeker/SeekerFooterComponent.vue";
import { useRouter } from 'vue-router';
import { ref, onMounted, computed, watch } from 'vue';
import { UseAnnouncementStore } from "@/stores/UseAnnouncementStore";

const router = useRouter();
const announcementStore = UseAnnouncementStore();

// announcements2 리스트의 길이 = 공고 수를 계산
const announcementCount = computed(() => announcementStore.announcements2.length);

// 검색 키워드와 선택된 필터 저장
const searchKeyword = ref('');
const selectedFilters = ref([]);

// 드롭다운 상태 저장
const dropdownOpen = ref({
  '기업형태': false,
  '채용형태': false,
  '모집직무': false,
  '근무지역': false
  // 다른 필터 항목들 추가 가능
});

// 드롭다운 열기/닫기 토글 함수
const toggleDropdown = (filterName) => {
  dropdownOpen.value[filterName] = !dropdownOpen.value[filterName];
};

// 필터 업데이트 함수 (드롭다운 이름과 필터 값 함께 저장)
const updateFilters = (filterName, filterValue) => {
  const exists = selectedFilters.value.find(f => f.name === filterName && f.value === filterValue);

  if (!exists) {
    selectedFilters.value.push({ name: filterName, value: filterValue });
  } else {
    selectedFilters.value = selectedFilters.value.filter(f => !(f.name === filterName && f.value === filterValue));
  }
};

// // 필터 제거 함수
// const removeFilter = (filter) => {
//   selectedFilters.value = selectedFilters.value.filter(f => f !== filter);
// };

// 필터 제거 함수
const removeFilter = (filter) => {
  selectedFilters.value = selectedFilters.value.filter(item => !(item.name === filter.name && item.value === filter.value));
};

// 필터 초기화 함수
const resetFilters = () => {
  selectedFilters.value = [];
};


// 검색 또는 필터링 처리
const handleSearch = () => {
  if (searchKeyword.value) {
    announcementStore.searchAnnouncements(searchKeyword.value);
  } else if (selectedFilters.value.length > 0) {
    announcementStore.filterAnnouncementsByFilters(selectedFilters.value);
  } else {
    console.error("검색어 또는 필터를 선택하세요.");
  }
};

// watch로 선택된 필터가 변경되었을 때 자동으로 백엔드에 요청
watch(selectedFilters, (newFilters) => {
  if (!searchKeyword.value && newFilters.length) {
    handleSearch();
  }
});


// // 공고 검색 처리 함수
// const handleSearch = () => {
//   if (searchKeyword.value) {
//     announcementStore.searchAnnouncements(searchKeyword.value);
//   } else {
//     console.error("검색어를 입력하세요.");
//   }
// };


// 필터 드롭다운 및 선택한 필터 업데이트 함수
// const initializeFilters = () => {
//   const filterItems = document.querySelectorAll(".filter-item");
//   filterItems.forEach((item) => {
//     item.addEventListener("click", function () {
//       const filterName = this.getAttribute("data-filter");
//       const dropdown = document.getElementById(filterName);

//       // 다른 드롭다운이 열려 있으면 닫기
//       document.querySelectorAll(".filter-dropdown").forEach((drop) => {
//         if (drop !== dropdown) {
//           drop.style.display = "none";
//         }
//       });

//       // 클릭한 드롭다운을 토글
//       if (dropdown.style.display === "block") {
//         dropdown.style.display = "none";
//       } else {
//         dropdown.style.display = "block";
//       }
//     });
//   });

//   // 각 체크박스 이벤트
//   const checkboxes = document.querySelectorAll('.filter-dropdown input[type="checkbox"]');
//   checkboxes.forEach((checkbox) => {
//     checkbox.addEventListener("change", function () {
//       const filterValue = this.value;

//       if (this.checked) {
//         // 체크된 경우 배열에 추가
//         selectedFilters.value.push(filterValue);
//       } else {
//         // 체크 해제된 경우 배열에서 제거
//         selectedFilters.value = selectedFilters.value.filter((item) => item !== filterValue);
//       }

//       updateSelectedFilters();
//     });
//   });
// };

// 선택된 필터 항목을 화면에 표시
// const updateSelectedFilters = () => {
//   const selectedList = document.getElementById("selected-filters-list");
//   selectedList.innerHTML = "";

//   selectedFilters.value.forEach((filter) => {
//     const filterSpan = document.createElement("span");
//     filterSpan.textContent = filter + " ✕";
//     filterSpan.style.cursor = "pointer";

//     // 필터 항목을 클릭하면 해당 항목 해제
//     filterSpan.addEventListener("click", () => {
//       selectedFilters.value = selectedFilters.value.filter((item) => item !== filter);
//       updateSelectedFilters();
//       document.querySelector(`.filter-dropdown input[value="${filter}"]`).checked = false;
//     });

//     selectedList.appendChild(filterSpan);
//   });
// };

// 초기화 버튼
// const resetFilters = () => {
//   selectedFilters.value = [];
//   updateSelectedFilters();

//   // 모든 체크박스 해제
//   document.querySelectorAll('.filter-dropdown input[type="checkbox"]').forEach((checkbox) => {
//     checkbox.checked = false;
//   });
// };

// 검색 버튼 동작
// const handleSearch = () => {
//   const searchText = document.getElementById("search-input").value.toLowerCase();
//   if (searchText) {
//     alert("검색 기능 실행: " + searchText);
//   } else {
//     alert("검색어를 입력하세요.");
//   }
// };




// 공고 상세 페이지로 이동
const goToDetailPage = (announcementIdx) => {
  router.push(`/seeker/announce/detail/${announcementIdx}`);
};

// 컴포넌트가 로드될 때 데이터를 가져옴
onMounted(() => {
  // initializeFilters();

  // if (searchInput.value) {
  //   searchInput.value.addEventListener('keyup', handleSearchInput);  // DOM 요소가 존재할 때만 이벤트 리스너 추가
  // } else {
  //   console.error('Search input element is not found');
  // }

  // // 초기화 버튼 클릭 이벤트
  // document.getElementById("reset-filters").addEventListener("click", resetFilters);

  // // 검색 버튼 클릭 이벤트
  // document.getElementById("search-btn").addEventListener("click", handleSearch);

  // 전체 공고 조회 함수
  announcementStore.readAll();
});

</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;

}

.body-arp {
  font-family: Arial, sans-serif;
  line-height: 1.6;
  background-color: #F9FAFB;
  justify-content: center;
  align-items: center;
  height: 100%;
  margin: 0 auto;
  padding: 0;
}

.search-container {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 20px;
}

.search-input {
  width: 300px;
  padding: 10px;
  border-radius: 25px;
  border: 1px solid #ccc;
  outline: none;
  font-size: 16px;
  transition: all 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.search-input:focus {
  border-color: #212b36;
  box-shadow: 0 6px 10px rgba(0, 123, 255, 0.2);
}

.search-btn {
  padding: 10px 20px;
  margin-left: 10px;
  border: none;
  border-radius: 25px;
  background-color: #212b36;
  color: white;
  font-size: 16px;
  cursor: pointer;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.search-btn:hover {
  background-color: #0056b3;
  box-shadow: 0 6px 12px rgba(0, 123, 255, 0.3);
}

.search-btn:focus {
  outline: none;
}

.main-content {
  max-width: 1200px;
  margin: 60px auto;
  background-color: #fff;
  padding: 50px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.main-container> :last-child {
  justify-content: right;
}

h2 {
  font-size: 22px;
  margin-bottom: 20px;
  color: #333;
}

.filter-section {
  display: flex;
  justify-content: left;
  margin-bottom: 20px;
}

.filter-item {
  position: relative;
}

.dropdown-btn {
  padding: 8px 15px;
  width: 130px;
  border: 1px solid #ffffff;
  background-color: #cbdaea;
  cursor: pointer;
  font-size: 14px;
}

.filter-dropdown {
  /* display: none; */
  position: absolute;
  top: 40px;
  left: 0;
  background-color: #fff;
  border: 1px solid #ddd;
  padding: 10px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  z-index: 1;
  max-height: 200px;
  overflow-y: auto;
}

.filter-dropdown label {
  display: block;
  margin-bottom: 5px;
  font-size: 14px;
}

.selected-filters {
  margin-bottom: 20px;
}

.selected-filters span {
  background-color: #e0e0e0;
  padding: 5px 10px;
  margin-right: 10px;
  border-radius: 20px;
  display: inline-block;
  font-size: 14px;
  color: #333;
  cursor: pointer;
}

.reset-filters {
  color: #212b36;
  text-decoration: none;
  font-size: 14px;
}

.results-header {
  display: flex;
  justify-content: space-between;
  margin-bottom: 15px;
}

.sort-by {
  cursor: pointer;
  color: #666;
}

.job-listing {
  width: 100%;
  border-collapse: collapse;
  margin-top: 20px;
}

.job-listing th,
.job-listing td {
  padding: 12px;
  border-bottom: 1px solid #ddd;
  text-align: left;
}

.job-listing th {
  background-color: #f9f9f9;
}

.job-listing tr:hover {
  background-color: #f1f1f1;
}

.job-listing td:last-child {
  text-align: center;
}

.search-container {
  display: flex;
  align-items: center;
  margin-left: 20px;
}

#search-input {
  padding: 8px;
  width: 300px;
  border: 1px solid #ddd;
  border-radius: 4px;
}

#search-btn {
  margin-left: 10px;
  padding: 8px 15px;
  background-color: #212b36;
  color: white;
  border: none;
  cursor: pointer;
  border-radius: 4px;
}

#search-btn:hover {
  background-color: #212b36;
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
  color: white;
}

.logout-btn:hover {
  /* background-color: #83a5ea; */
}


/* 테이블 행 hover 시 색깔 변화 */
.hoverable-row {
  transition: background-color 0.3s ease;
}

.hoverable-row:hover {
  background-color: #f6f6f6;
  /* 마우스 올렸을 때 약간 어둡게 변경 */
  cursor: pointer;
}
</style>
