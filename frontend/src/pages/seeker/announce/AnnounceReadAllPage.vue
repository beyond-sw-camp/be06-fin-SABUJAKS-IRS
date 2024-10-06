<template>
  <div class="body-arp">
    <SeekerHeaderComponent></SeekerHeaderComponent>
    <section class="main-content">
<!--    <section id="wrap">-->
      <h2>직무 전체</h2>
      <div class="filter-section">
        <!-- 기업형태 필터 -->
        <!-- <div class="filter-item" data-filter="기업형태">
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
        </div> -->
        <!-- 채용형태 필터 -->
        <div class="filter-item" data-filter="채용형태">
          <button class="dropdown-btn" @click.stop="toggleDropdown('채용형태')">채용형태</button>
          <div class="filter-dropdown" v-show="dropdownOpen['채용형태']">
            <label>
              <input type="checkbox" value="신입" v-model="checkedFilters['채용형태']"
                @change="updateFilters('채용형태', '신입'); closeDropdown('채용형태')" />
              신입</label>
            <label><input type="checkbox" value="경력" v-model="checkedFilters['채용형태']"
                @change="updateFilters('채용형태', '경력'); closeDropdown('채용형태')" /> 경력</label>
          </div>
        </div>
        <!-- 모집직무 필터 -->
        <div class="filter-item" data-filter="모집직무">
          <button class="dropdown-btn" @click.stop="toggleDropdown('모집직무')">모집직무</button>
          <div class="filter-dropdown" v-show="dropdownOpen['모집직무']">
            <!-- 큰 카테고리 이름을 동적으로 렌더링 -->
            <div v-for="category in categoryData" :key="category">
              <label>
                <input type="checkbox" :value="category" v-model="checkedFilters['모집직무']"
                  @change="updateFilters('모집직무', category); closeDropdown('모집직무')" />
                {{ category }}
              </label>
            </div>
          </div>
        </div>
        <!-- 근무지역 필터 -->
        <div class="filter-item" data-filter="근무지역">
          <button class="dropdown-btn" @click.stop="toggleDropdown('근무지역')">근무지역</button>
          <div class="filter-dropdown" v-show="dropdownOpen['근무지역']">
            <label><input type="checkbox" value="해외지역" v-model="checkedFilters['근무지역']"
                @change="updateFilters('근무지역', '해외지역'); closeDropdown('근무지역')" /> 해외지역</label>
            <label><input type="checkbox" value="재택근무 가능" v-model="checkedFilters['근무지역']"
                @change="updateFilters('근무지역', '재택근무 가능'); closeDropdown('근무지역')" /> 재택근무 가능</label>
            <label><input type="checkbox" value="서울" v-model="checkedFilters['근무지역']"
                @change="updateFilters('근무지역', '서울'); closeDropdown('근무지역')" /> 서울</label>
            <label><input type="checkbox" value="부산" v-model="checkedFilters['근무지역']"
                @change="updateFilters('근무지역', '부산'); closeDropdown('근무지역')" /> 부산</label>
            <label><input type="checkbox" value="대구" v-model="checkedFilters['근무지역']"
                @change="updateFilters('근무지역', '대구'); closeDropdown('근무지역')" /> 대구</label>
            <label><input type="checkbox" value="인천" v-model="checkedFilters['근무지역']"
                @change="updateFilters('근무지역', '인천'); closeDropdown('근무지역')" /> 인천</label>
            <label><input type="checkbox" value="광주" v-model="checkedFilters['근무지역']"
                @change="updateFilters('근무지역', '광주'); closeDropdown('근무지역')" /> 광주</label>
          </div>
        </div>
        <div class="search-container">
          <!-- <input type="text" id="search-input" placeholder="공고명, 기업, 직무, 키워드 검색" />
          <button id="search-btn">검색</button> -->
          <input ref="searchInput" type="text" v-model="searchKeyword" placeholder="공고명&모집분야 키워드 검색"
            @keyup.enter="handleSearch" class="search-input" />
          <button @click="handleSearch" class="search-btn">검색</button>
        </div>
      </div>

      <!-- 선택된 필터들 -->
      <div class="selected-filters pb-30">
        <div id="selected-filters-list">
          <span v-for="filter in selectedFilters" :key="filter.name + filter.value" @click="removeFilter(filter)">
            {{ filter.name }}: {{ filter.value }} ✕
          </span>
        </div>
        <a href="#" id="reset-filters" class="reset-filters" @click.prevent="resetFilters"> 필터 초기화🔄️</a>
        <p> 키워드로 검색 시, 토글을 초기화 해주세요. 필터로 검색 시, 카테고리당 하나의 필터만 추가하세요.</p>
      </div>

      <!-- 검색 결과 -->
      <div class="results-header pt-30">
        <span>검색 결과 {{ announcementCount }}건</span>
        <div class="sort-dropdown">
          <button class="dropdown-btn2" @click="toggleSortDropdown">{{ selectedSortOption }} ▼</button>
          <ul v-if="sortDropdownOpen" class="sort-options">
            <li @click="sortAnnouncements('전체')">전체</li>
            <li @click="sortAnnouncements('최신 등록순')">최신 등록순</li>
            <li @click="sortAnnouncements('마감 임박순')">마감 임박순</li>
            <li @click="sortAnnouncements('마감된 공고 제외순')">마감된 공고 제외순</li>
          </ul>
        </div>
      </div>

      <!-- 공고 목록 -->
      <div id="recruitWrap" class="recruitWrap">
        <div class="secContents">
          <div id="searchLog"></div>
          <div id="searchList" class="recruitList">
            <ul data-pageno="1" data-count="20" data-totalcount="3641" data-totalcounttext="3,641">
              <li class="option" v-for="(announcement) in announcementStore.announcements2"
                  :key="announcement.announcementIdx" @click="goToDetailPage(announcement.announcementIdx)">
                <a href="" class="listCell" target="_blank" data-gno="45672569">
                  <div class="rLogo">
                    <img src="@/assets/img/announce/no-background.jpg" alt="㈜365위더스">
                  </div>

                  <div class="listCont">
                    <div class="rCor">
                      <span class="corNm">{{ announcement.companyName }}</span>
                    </div>
                    <div class="rTit">{{ announcement.announcementTitle }}</div>
                    <div class="rInfo">
                      <span class="rPart">
                          {{ announcement.jobTitle }}
                      </span>
                    </div>
                    <div class="rSkillBox">
                      <span class="rSkill">
                          <span class="rAddress">{{ announcement.region }}</span>
                      </span>
                    </div>

                  </div>
                  <div class="listFoot">

                    <div class="dead-line">
                      <span class="rPeriod">{{ announcement.announcementStart }} ~ {{ announcement.announcementEnd }}</span>
                    </div>
                  </div>
                </a>
              </li>
            </ul>
          </div>
        </div>
      </div>
    </section>
    <SeekerFooterComponent></SeekerFooterComponent>
  </div>
</template>

<script setup>
import SeekerHeaderComponent from "@/components/seeker/SeekerHeaderComponent.vue";
import SeekerFooterComponent from "@/components/seeker/SeekerFooterComponent.vue";
import { useRouter } from 'vue-router';
import { ref, onMounted, computed } from 'vue';
import { UseAnnouncementStore } from "@/stores/UseAnnouncementStore";

const router = useRouter();
const announcementStore = UseAnnouncementStore();

// announcements2 리스트의 길이 = 공고 수를 계산
const announcementCount = computed(() => announcementStore.announcements2.length);

// 검색 키워드와 선택된 필터 저장
const searchKeyword = ref('');
const selectedFilters = ref([]);

// 체크박스의 선택 상태 저장
const checkedFilters = ref({
  '기업형태': [],
  '채용형태': [],
  '모집직무': [],
  '근무지역': []
});

// 드롭다운 상태 저장
const dropdownOpen = ref({
  '기업형태': false,
  '채용형태': false,
  '모집직무': false,
  '근무지역': false
  // 다른 필터 항목들 추가 가능
});

const categoryData = ref([
  "기획·전략",
  "교육",
  "고객상담·TM",
  "IT·개발",
  "마케팅",
  "영업",
  "인사",
  "재무·회계",
  "법무",
  "홍보·PR",
  "디자인",
  "생산·제조",
  "연구개발(R&D)",
  "물류",
  "구매",
  "건설·토목",
  "의료",
  "연구",
  "법무·특허",
  "서비스",
  "비서·총무",
  "전략기획",
  "엔지니어",
  "유통·판매",
  "리스크관리",
  "품질관리",
  "공공행정",
  "광고·미디어",
  "항공",
  "호텔·외식"
]);


// 드롭다운 열기/닫기 토글 함수
const toggleDropdown = (filterName) => {
  dropdownOpen.value[filterName] = !dropdownOpen.value[filterName];
};

const closeDropdown = (filterName) => {
  dropdownOpen.value[filterName] = false;
};

// 드롭다운 외부 클릭 시 닫기
const handleOutsideClick = () => {
  Object.keys(dropdownOpen.value).forEach((key) => {
    dropdownOpen.value[key] = false;
  });
};

// 이벤트가 드롭다운 안에서 발생하면 창이 닫히지 않도록 중지
document.addEventListener('click', (event) => {
  if (!event.target.closest('.filter-item')) {
    handleOutsideClick();
  }
});

// 필터 업데이트 함수 (드롭다운 이름과 필터 값 함께 저장)
const updateFilters = (filterName, filterValue) => {
  const exists = selectedFilters.value.find(f => f.name === filterName && f.value === filterValue);

  if (!exists) {
    selectedFilters.value.push({ name: filterName, value: filterValue });
  } else {
    selectedFilters.value = selectedFilters.value.filter(f => !(f.name === filterName && f.value === filterValue));
  }
};


// 필터 제거 함수
const removeFilter = (filter) => {
  // 선택된 필터 리스트에서 제거
  selectedFilters.value = selectedFilters.value.filter(item => !(item.name === filter.name && item.value === filter.value));

  // 체크박스 상태도 해제
  checkedFilters.value[filter.name] = checkedFilters.value[filter.name].filter(f => f !== filter.value);
};

// 필터 초기화 함수
const resetFilters = () => {
  selectedFilters.value = [];
  Object.keys(checkedFilters.value).forEach(key => {
    checkedFilters.value[key] = [];
  });
};


// 검색 또는 필터링 처리
const handleSearch = () => {
  if (searchKeyword.value) {
    // 검색어가 있을 경우 검색 결과를 initialAnnouncements에 저장
    announcementStore.searchAnnouncements(searchKeyword.value).then(() => {
      initialAnnouncements.value = [...announcementStore.announcements2]; // 현재 상태 저장
      console.log("키워드 검색 후: ", initialAnnouncements);
    });
  } else if (selectedFilters.value.length > 0) {
    // 필터가 있을 경우 필터 결과를 initialAnnouncements에 저장
    announcementStore.filterAnnouncementsByFilters(selectedFilters.value).then(() => {
      initialAnnouncements.value = [...announcementStore.announcements2]; // 현재 상태 저장
      console.log("필터 적용 후: ", initialAnnouncements);
    });
  } else {
    // 검색어와 필터가 없는 경우 전체 공고 초기값 복원
    if (initialAnnouncements.value.length > 0) {
      announcementStore.announcements2 = [...initialAnnouncements.value];
    } else {
      console.error("검색어 또는 필터를 선택하세요.");
      alert('검색어 또는 필터를 선택하세요.');
    }
    console.log("전체 복원 후: ", initialAnnouncements);
  }
};

// watch로 선택된 필터가 변경되었을 때 자동으로 백엔드에 요청
// watch(selectedFilters, (newFilters) => {
//   if (!searchKeyword.value && newFilters.length) {
//     handleSearch();
//   }
// });


// 오른쪽 최신순 필터 기능

// 정렬 드롭다운 상태
const sortDropdownOpen = ref(false);
const selectedSortOption = ref('정렬'); // 기본 정렬 옵션
const initialAnnouncements = ref([]); // 초기 공고 리스트를 저장할 변수

// 드롭다운 열기/닫기 함수
const toggleSortDropdown = () => {
  sortDropdownOpen.value = !sortDropdownOpen.value;
};

// 정렬 옵션 선택 시 처리
const sortAnnouncements = (option) => {
  selectedSortOption.value = option;
  sortDropdownOpen.value = false;

  switch (option) {
    case '전체': {
      // 초기 상태로 되돌림
      announcementStore.announcements2 = [...initialAnnouncements.value];
      break;
    }
    case '최신 등록순': {
      announcementStore.announcements2 = [...initialAnnouncements.value].reverse();
      break;
    }
    case '마감 임박순': {
      announcementStore.announcements2 = [...initialAnnouncements.value].sort((a, b) => {
        return new Date(a.announcementEnd) - new Date(b.announcementEnd);
      });
      break;
    }
    case '마감된 공고 제외순': {
      const now = new Date();
      announcementStore.announcements2 = initialAnnouncements.value.filter(announcement => {
        return !announcement.announcementEnd || new Date(announcement.announcementEnd) >= now;
      });
      break;
    }
    default:
      break;
  }
};


// 공고 상세 페이지로 이동
const goToDetailPage = (announcementIdx) => {
  router.push(`/seeker/announce/detail/${announcementIdx}`);
};

// 컴포넌트가 로드될 때 데이터를 가져옴
onMounted(() => {
  // 전체 공고 조회 함수
  announcementStore.readAll().then(() => {
    initialAnnouncements.value = [...announcementStore.announcements2]; // 초기값 저장
    console.log(initialAnnouncements);
  });
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
  background-color: #fff;
  justify-content: center;
  align-items: center;
  height: 100vh;
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
  width: 90%;
  padding: 10px;
  border-radius: 5px;
  border: 1px solid #ccc;
  outline: none;
  transition: all 0.3s ease;
}

.search-input:focus {
  border-color: #212b36;
  box-shadow: 0 6px 10px rgba(0, 123, 255, 0.2);
}

.search-btn {
  padding: 10px 20px;
  margin-left: 10px;
  border: none;
  border-radius: 5px;
  background-color: #212b36;
  color: white;
  cursor: pointer;
  transition: background-color 0.3s ease, box-shadow 0.3s ease;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
}

.search-btn:hover {
  background-color: #37404a;
  /* box-shadow: 0 6px 12px rgba(0, 123, 255, 0.3); */
}

.search-btn:focus {
  outline: none;
}

.main-content {
  overflow: auto;
  max-width: 1240px;
  margin: 0 auto;
  background-color: #fff;
  padding-top: 150px;
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
}

.filter-item {
  flex: 1;
  position: relative;
  margin-right: 5px;
}

.dropdown-btn {
  padding: 8px 15px;
  width: 130px;
  border: 1px solid #d2d2d2;
  background-color: #fff;
  border-radius: 5px;
  cursor: pointer;
  font-size: 14px;
}

.dropdown-btn:hover,
.dropdown-btn:active,
.dropdown-btn:focus{
  background-color: #232b36;
  color: white;
}


.filter-dropdown {
  position: absolute;
  top: 40px;
  left: 0;
  width: 100%;
  /* 버튼 크기와 동일하게 */
  background-color: #ffffff;
  border: 1px solid #ccc;
  /* 부드러운 테두리 색상 */
  padding: 8px 12px;
  /* 적당한 내부 여백 */
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  /* 좀 더 부드러운 그림자 */
  z-index: 2;
  max-height: 200px;
  overflow-y: auto;
  border-radius: 5px;
  /* 모서리를 부드럽게 */
  font-size: 14px;
  /* 텍스트 크기 조정 */
}

.filter-dropdown label {
  display: block;
  padding: 8px 0;
  /* 항목 간 여백을 줘서 더 깔끔하게 */
  cursor: pointer;
}

.filter-dropdown label:hover {
  background-color: #f5f5f5;
  /* 항목 hover 시 배경색 */
}

.selected-filters {
  border-bottom: 1px solid #e8e8e8;
}

.selected-filters span {
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

.reset-filters {
  margin-top: 10px;
  color: #212b36;
  text-decoration: none;
  font-size: 14px;
}

.results-header {
  display: flex;
  justify-content: space-between;
  /* align-items: center; */
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
  flex: 8;
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


/* 정렬 관련 */

.dropdown-btn2 {
  /* padding: 8px 15px;
  width: 136.89px;
  border: 1px solid #ffffff;
  background-color: #cbdaea;
  cursor: pointer;
  font-size: 14px; */
  background-color: #212b36;
  color: white;
  padding: 10px;
  border: none;
  width: 166px;
  border-radius: 5px;
  cursor: pointer;
}

.dropdown-btn2:hover {
  background-color: #37404a;
}

.sort-options {
  position: absolute;
  bottom: 40px;
  /* 드롭다운이 위쪽으로 나타나도록 설정 */
  left: 0;
  background-color: white;
  list-style: none;
  padding: 10px 0;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  width: 165px;
  font-size: 0.8rem;
  z-index: 2;
  margin: 0;
  display: flex;
  flex-direction: column;
}

.sort-dropdown {
  position: relative;
}

.sort-options li {
  padding: 10px 20px;
  cursor: pointer;
  transition: background-color 0.2s ease;
}

.sort-options li:hover {
  background-color: #f0f0f0;
  /* 마우스 오버 시 배경색 변경 */
}

.dropdown-btn2:focus,
.sort-options li:focus {
  outline: none;
}

/* .main_product {
  margin-top: 20px;
} */

/* .prd_list .option {
  padding: 10px;
  border: 1px solid #ddd;
  margin-bottom: 15px;
  transition: background-color 0.3s ease;
}

.prd_list .option:hover {
  background-color: #f9f9f9;
}

.prd_list .option .logo {
  margin-bottom: 10px;
} */


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

SeekerFooterComponent {
  background-color: #212b36;
  color: white;
  padding: 20px;
  text-align: center;
  /* 푸터가 항상 하단에 위치 */
}

/* 상품 공통 */
.main_product .btn_scrap {
  display: flex;
  position: absolute;
  right: 24px;
  z-index: 3;
  width: 24px;
  height: 24px;
  flex-flow: row nowrap;
  justify-content: center;
  align-content: center;
  align-items: center
}

.main_product .btn_scrap .ic path {
  stroke: #6b768b
}

.main_product .btn_scrap.on .ic path {
  fill: #fff15c;
  stroke: #ad9100
}

.main_product .logo {
  display: grid;
  position: relative;
  align-items: center;
  align-self: start;
  justify-self: center;
  grid-area: logo
}

.main_product .logo img {
  position: absolute;
  top: 50%;
  left: 50%;
  max-width: 100%;
  max-height: 100%;
  transform: translate(-50%, -50%);
  object-fit: contain
}

.main_product .logo .text {
  display: block;
  overflow: hidden;
  width: 100%;
  color: #2d2d2d;
  font-size: 12px;
  font-weight: 700;
  line-height: 20px;
  text-align: center;
  text-overflow: ellipsis;
  white-space: nowrap
}

.main_product .logo.left {
  justify-self: start
}

.main_product .logo.left img {
  left: 0;
  transform: translateY(-50%)
}

.main_product .c_name {
  display: block;
  overflow: hidden;
  max-width: 100%;
  font-size: 14px;
  font-weight: 700;
  line-height: 20px;
  text-overflow: ellipsis;
  white-space: nowrap;
  grid-area: c_name
}

.main_product .title,
.main_product .title_ex {
  display: -webkit-box;
  overflow: hidden;
  max-height: 44px;
  font-size: 15px;
  line-height: 22px;
  text-overflow: ellipsis;
  word-wrap: break-word;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  grid-area: title
}

.main_product .date {
  display: block;
  padding-right: 28px;
  color: #67738e;
  font-size: 12px;
  line-height: 20px;
  white-space: nowrap;
  justify-self: end;
  align-self: end;
  grid-area: date
}

.main_product .date.noscrap {
  padding-right: 0
}

.main_product .date .soon {
  color: #ff5656
}

.main_product .tags {
  display: flex;
  flex-flow: row wrap;
  justify-content: start;
  align-items: start;
  gap: 8px;
  grid-area: tags
}

.main_product .tags span {
  padding: 2px 6px;
  border-radius: 12px;
  color: #373f57;
  font-size: 13px;
  line-height: 20px;
  white-space: nowrap;
  background: #eff5ff
}

.main_product .badge {
  display: grid;
  padding: 2px 8px;
  border-radius: 4px;
  background: #eff5ff;
  grid-template-columns: 14px 1fr;
  gap: 2px;
  justify-content: start;
  align-items: center;
  justify-self: start;
  align-self: end;
  grid-area: badge
}

.main_product .badge svg {
  width: 14px;
  height: 14px
}

.main_product .badge span {
  overflow: hidden;
  max-width: 100%;
  color: #475067;
  font-size: 12px;
  line-height: 20px;
  text-overflow: ellipsis;
  white-space: nowrap
}

/*사용*/
.main_product .banner_list,
.main_product .prd_list {
  display: grid;
  width: 100%;
  gap: 20px;
  grid-template-columns: repeat(4, 1fr)
}

.main_product .banner_list li,
.main_product .prd_list li {
  position: relative;
  z-index: 1
}

.main_product .banner_list li a,
.main_product .prd_list li a {
  display: grid;
  position: absolute;
  z-index: 1;
  padding: 23px;
  width: 100%;
  height: 100%;
  border-width: 1px;
  border-style: solid;
  border-color: #d7dce5;
  border-radius: 16px;
  box-sizing: border-box;
  color: #292e41;
  background-color: #fff;
  align-content: start
}

.main_product .prd_list li:hover {
  z-index: 2
}

.main_product .prd_list li:hover a {
  box-shadow: 0 4px 10px 0 rgba(14, 12, 31, 0.10)
}

.main_product .prd_list li.option:hover a {
  box-shadow: 0 4px 20px 0 rgba(14, 12, 31, 0.15)
}

.main_product .prd_list li:not(.option):hover a {
  border-color: #2d67ff
}

.main_product .prd_list.plus li a {
  border-color: #2d67ff
}

.main_product .prd_list.plus li:not(.option):hover a {
  padding: 22px;
  border-width: 2px
}

.main_product .prd_list li.option a:before {
  position: absolute;
  top: -1px;
  right: -1px;
  left: -1px;
  height: 16px;
  border-width: 3px 1px 0 1px;
  border-style: solid;
  border-color: inherit;
  border-radius: 16px 16px 0 0;
  box-sizing: border-box;
  content: ""
}

.main_product .prd_list li.option:hover a:before {
  display: none
}

.main_product .prd_platinum .prd_list {
  grid-auto-rows: 270px
}

.main_product .prd_platinum .prd_list li.option:hover {
  height: 442px
}

.main_product .prd_platinum .prd_list .logo .text {
  font-size: 18px;
  line-height: 26px
}

.main_product .prd_platinum .prd_list li.option .title_ex {
  display: none;
  font-size: 18px;
  line-height: 26px
}

.main_product .prd_platinum .prd_list:not(.plus) li a {
  grid-template-columns: 1fr auto;
  gap: 0 8px;
  grid-template-areas: "logo logo" "c_name c_name" "title title" "badge date";
  grid-template-rows: 64px 32px 52px 72px
}

.main_product .prd_platinum .prd_list:not(.plus) li.option a {
  border-color: #2d67ff
}

.main_product .prd_platinum .prd_list:not(.plus) li.option:hover a {
  grid-template-areas: "logo logo" "c_name c_name" "title title" "bg_ex bg_ex" "tags tags" "badge date";
  grid-template-rows: 64px 32px 78px 128px 56px 32px
}

.main_product .prd_platinum .prd_list:not(.plus) .btn_scrap {
  bottom: 24px
}

.main_product .prd_platinum .prd_list:not(.plus) .logo {
  width: 160px;
  height: 40px
}

.main_product .prd_platinum .prd_list:not(.plus) .c_name {
  font-size: 16px;
  line-height: 24px
}

.main_product .prd_platinum .prd_list:not(.plus) .title {
  max-height: 52px;
  font-size: 18px;
  line-height: 26px
}

.main_product .prd_platinum .prd_list:not(.plus) .bg_ex {
  display: none;
  width: 96px;
  height: 96px;
  border-radius: 96px;
  grid-area: bg_ex;
  justify-self: end;
  align-self: center
}

.main_product .prd_platinum .prd_list:not(.plus) .bg_ex img {
  width: 96px;
  height: 96px;
  border-radius: 96px
}

.main_product .prd_platinum .prd_list:not(.plus) .tags {
  display: none
}

.main_product .prd_platinum .prd_list:not(.plus) li.option:hover .bg_ex {
  display: block
}

.main_product .prd_platinum .prd_list:not(.plus) li.option:hover .tags {
  display: flex;
  align-self: end
}

.main_product .prd_platinum .prd_list:not(.plus) li.option:hover .title {
  display: none
}

.main_product .prd_platinum .prd_list:not(.plus) li.option:hover .title_ex {
  display: -webkit-box;
  max-height: 78px;
  -webkit-line-clamp: 3
}

.main_product .prd_platinum .prd_list:not(.plus) li.option:hover .date {
  padding-bottom: 0
}

.main_product .prd_platinum .prd_list.plus li a {
  padding: 15px 23px;
  border-color: #d7dce5;
  grid-template-rows: 56px 52px 130px;
  grid-template-areas: "logo logo" "title title" "badge date";
  grid-template-columns: 1fr auto
}

.main_product .prd_platinum .prd_list.plus li:hover a {
  padding: 14px 22px;
  border-color: #2d67ff;
  box-shadow: 0 4px 20px 0 rgba(14, 12, 31, 0.15);
  cursor: pointer;
}

.main_product .prd_platinum .prd_list.plus li.option a:before {
  border-color: transparent;
  background-clip: content-box, border-box;
  background-origin: border-box
}

.main_product .prd_platinum .prd_list.plus li.option:nth-child(1) a:before,
.main_product .prd_platinum .prd_list.plus li.option:nth-child(4n+4) a:before {
  background-image: linear-gradient(#fff, #fff), linear-gradient(to right, #9f02ff 0%, #2d67ff 100%)
}

.main_product .prd_platinum .prd_list.plus li.option:nth-child(2) a:before,
.main_product .prd_platinum .prd_list.plus li.option:nth-child(4n+5) a:before {
  background-image: linear-gradient(#fff, #fff), linear-gradient(to right, #2d67ff 0%, #02c6ff 100%)
}

.main_product .prd_platinum .prd_list.plus li.option:nth-child(3) a:before,
.main_product .prd_platinum .prd_list.plus li.option:nth-child(4n+6) a:before {
  background-image: linear-gradient(#fff, #fff), linear-gradient(to right, #02c6ff 0%, #00be4c 100%)
}

.main_product .prd_platinum .prd_list.plus li.option:nth-child(4) a:before,
.main_product .prd_platinum .prd_list.plus li.option:nth-child(4n+7) a:before {
  background-image: linear-gradient(#fff, #fff), linear-gradient(to right, #00be4c 0%, #ffe15f 100%)
}

.main_product .prd_platinum .prd_list.plus li.expanded {
  grid-row: 1 / span 2
}

.main_product .prd_platinum .prd_list.plus li.option:hover a,
.main_product .prd_platinum .prd_list.plus li.expanded a {
  padding: 24px;
  height: 560px;
  border: 0
}

.main_product .prd_platinum .prd_list.plus li.expanded a {
  box-shadow: none
}

.main_product .prd_platinum .prd_list.plus .btn_scrap {
  right: 20px;
  bottom: 18px;
  z-index: 2
}

.main_product .prd_platinum .prd_list.plus .btn_scrap:not(.on) .ic path {
  stroke: #fff
}

.main_product .prd_platinum .prd_list.plus .logo {
  width: 160px;
  height: 40px
}

.main_product .prd_platinum .prd_list.plus .date {
  z-index: 1;
  padding: 2px 32px 2px 8px;
  border-radius: 4px;
  color: #fff;
  line-height: 24px;
  background: rgba(0, 0, 0, 0.6);
  transform: translateX(8px);
  align-self: end
}

.main_product .prd_platinum .prd_list.plus .date.noscrap {
  padding-right: 8px
}

.main_product .prd_platinum .prd_list.plus .date .soon {
  color: #fff
}

.main_product .prd_platinum .prd_list.plus .title {
  max-height: 52px;
  font-size: 18px;
  line-height: 26px
}

.main_product .prd_platinum .prd_list.plus .bg {
  overflow: hidden;
  position: absolute;
  bottom: 0;
  left: 0;
  width: 298px;
  height: 129px
}

.main_product .prd_platinum .prd_list.plus .bg img {
  position: absolute;
  top: 0;
  left: 43%;
  width: 260px;
  height: 129px;
  border-radius: 0 0 16px 16px;
  transform: translateX(-50%)
}

.main_product .prd_platinum .prd_list.plus .badge {
  z-index: 1;
  border-radius: 12px;
  background: rgba(0, 0, 0, 0.6);
  transform: translateX(-8px);
  gap: 4px
}

.main_product .prd_platinum .prd_list.plus .badge span {
  color: #fff
}

.main_product .prd_platinum .prd_list.plus .c_name,
.main_product .prd_platinum .prd_list.plus .bg_ex,
.main_product .prd_platinum .prd_list.plus .cont {
  display: none
}

.main_product .prd_platinum .prd_list.plus li:hover .bg {
  width: 296px;
  height: 128px
}

.main_product .prd_platinum .prd_list.plus li.expanded a,
.main_product .prd_platinum .prd_list.plus li.option:hover a {
  padding: 16px;
  grid-template-rows: 72px 112px 32px 100px 140px 70px;
  grid-template-areas: "date" "logo" "c_name" "title" "cont" "badge"
}

.main_product .prd_platinum .prd_list.plus li.expanded .bg,
.main_product .prd_platinum .prd_list.plus li.option:hover .logo,
.main_product .prd_platinum .prd_list.plus li.option:hover .bg {
  display: none
}

.main_product .prd_platinum .prd_list.plus li.expanded .btn_scrap,
.main_product .prd_platinum .prd_list.plus li.option:hover .btn_scrap {
  top: 16px;
  right: 16px
}

.main_product .prd_platinum .prd_list.plus li.expanded .btn_scrap .ic path,
.main_product .prd_platinum .prd_list.plus li.option:hover .btn_scrap:not(.on) .ic path {
  stroke: #fff
}

.main_product .prd_platinum .prd_list.plus li.expanded .btn_scrap.on .ic path,
.main_product .prd_platinum .prd_list.plus li.option:hover .btn_scrap.on .ic path {
  fill: #fff15c;
  stroke: #ad9100
}

.main_product .prd_platinum .prd_list.plus li.expanded .date,
.main_product .prd_platinum .prd_list.plus li.option:hover .date {
  z-index: 2;
  padding: 0 32px 0;
  background: none;
  transform: none;
  align-self: start
}

.main_product .prd_platinum .prd_list.plus li.expanded .logo,
.main_product .prd_platinum .prd_list.plus li.option:hover .logo {
  display: grid;
  position: relative;
  z-index: 2;
  padding: 20px 10px;
  width: 76px;
  height: 56px;
  border-radius: 96px;
  background: #fff;
  align-self: start;
  justify-self: center;
  grid-area: logo
}

.main_product .prd_platinum .prd_list.plus li.expanded .logo img,
.main_product .prd_platinum .prd_list.plus li.option:hover .logo img {
  position: absolute;
  top: 50%;
  left: 50%;
  max-width: 76px;
  max-height: 56px;
  transform: translate(-50%, -50%);
  object-fit: contain
}

.main_product .prd_platinum .prd_list.plus li.expanded .logo .text,
.main_product .prd_platinum .prd_list.plus li.option:hover .logo .text {
  font-size: 12px;
  line-height: 200px
}

.main_product .prd_platinum .prd_list.plus li.expanded .c_name,
.main_product .prd_platinum .prd_list.plus li.option:hover .c_name {
  display: block;
  overflow: hidden;
  z-index: 2;
  max-width: 100%;
  color: #fff;
  font-size: 16px;
  font-weight: 700;
  line-height: 24px;
  text-overflow: ellipsis;
  white-space: nowrap;
  justify-self: center
}

.main_product .prd_platinum .prd_list.plus li.expanded .title,
.main_product .prd_platinum .prd_list.plus li.option:hover .title {
  display: none
}

.main_product .prd_platinum .prd_list.plus li.expanded .title_ex,
.main_product .prd_platinum .prd_list.plus li.option:hover .title_ex {
  display: -webkit-box;
  z-index: 2;
  max-height: 52px;
  color: #fff;
  font-size: 18px;
  font-weight: 700;
  line-height: 26px;
  text-align: center;
  -webkit-line-clamp: 2
}

.main_product .prd_platinum .prd_list.plus li.expanded .cont,
.main_product .prd_platinum .prd_list.plus li.option:hover .cont {
  display: -webkit-box;
  overflow: hidden;
  z-index: 2;
  margin: 0 8px;
  max-height: 140px;
  color: #fff;
  font-size: 14px;
  line-height: 20px;
  text-align: center;
  text-overflow: ellipsis;
  word-wrap: break-word;
  -webkit-line-clamp: 7;
  -webkit-box-orient: vertical;
  grid-area: cont
}

.main_product .prd_platinum .prd_list.plus li.expanded .badge,
.main_product .prd_platinum .prd_list.plus li.option:hover .badge {
  transform: translateX(0)
}

.main_product .prd_platinum .prd_list.plus li.expanded .bg_ex,
.main_product .prd_platinum .prd_list.plus li.option:hover .bg_ex {
  display: block;
  position: absolute;
  bottom: 0;
  left: 0;
  width: 260px;
  height: 560px;
  border-radius: 16px
}

.main_product .prd_platinum .prd_list.plus li.expanded .bg_ex:after,
.main_product .prd_platinum .prd_list.plus li.option:hover .bg_ex:after {
  display: block;
  position: absolute;
  bottom: 0;
  left: 0;
  width: 260px;
  height: 560px;
  border-radius: 16px;
  background: linear-gradient(to bottom, rgba(79, 128, 255, 0.24), rgba(0, 24, 152, 0.6));
  content: ""
}

.main_product .prd_platinum .prd_list.plus li.expanded .bg_ex img,
.main_product .prd_platinum .prd_list.plus li.option:hover .bg_ex img {
  width: 100%;
  height: 100%;
  border-radius: 16px
}






.main_product .prd_platinum .prd_list.plus li.expanded .bg_ex img,
.main_product .prd_platinum .prd_list.plus li.option:hover .bg_ex img {
  width: 100%;
  height: 100%;
  border-radius: 16px
}

body.celebrate_renew #wrap {
  overflow: auto;
}

body.celebrate_renew .recruitWrap .recruitList ul {
  gap: 32px;
}

#wrap {
  position: relative;
  min-width: 1270px;
  margin: 0 auto;
  background: #f2f4f7;
}

#wrap {
  z-index: 10;
  min-width: 1240px;
  margin: 0;
  background-color: #fff;
}

.recruitWrap {
  position: relative;
}

.secContents {
  position: relative;
  z-index: 1;
  width: 1240px;
  margin: 0 auto;
  padding: 100px 0;
}

.recruitWrap .secContents {
  width: 1240px;
  margin: 0 auto;
  padding: 0;
}

body.celebrate_renew .recruitWrap .recruitList ul {
  gap: 32px;
}

.recruitWrap .recruitList ul {
  display: -webkit-box;
  display: -webkit-flex;
  display: -moz-box;
  display: flex;
  -webkit-flex-wrap: wrap;
  flex-wrap: wrap;
  margin: 0;
}

body.celebrate_renew .recruitWrap .recruitList ul li {
  margin: 0;
}

.recruitWrap .recruitList ul > li:nth-child(4n + 1) {
  margin-left: 0;
}

.recruitWrap .recruitList ul > li {
  margin: 0 16px 32px;
}

li, ul {
  list-style: none;
}

recruitWrap .recruitList ul .listCell {
  position: relative;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  width: 286px;
  height: 350px;
  border: 1px solid #e8e8e8;
  border-radius: 8px;
  cursor: pointer;
  -webkit-box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.05);
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.05);
  -webkit-transition: 0.3s;
  -moz-transition: 0.3s;
  transition: 0.3s;
}

a, a:active, a:focus, a:hover, a:link, a:visited {
  color: #333;
  text-decoration: none;
}

a, img {
  display: block;
}

.recruitWrap .recruitList ul .listCell .rLogo.no-image {
  background: url(@/assets/img/announce/no-background.jpg) no-repeat 50%;
}

.recruitWrap .recruitList ul .listCell .rLogo {
  overflow: hidden;
  position: relative;
  z-index: -1;
  height: 130px;
  border-radius: 8px 8px 0 0;
}

.recruitWrap .recruitList ul .listCell .listCont {
  margin-top: -1px;
  padding: 25px 24px 16px;
  border-top: 1px solid rgba(0, 0, 0, 0.05);
}

.recruitWrap .recruitList ul .listCell .listCont .rCor {
  display: -webkit-box;
  display: -webkit-flex;
  display: -moz-box;
  display: flex;
  -webkit-box-align: center;
  -webkit-align-items: center;
  -moz-box-align: center;
  align-items: center;
}

.recruitWrap .recruitList ul .listCell .listCont .rCor .corNm {
  overflow: hidden;
  display: inline-block;
  font-size: 15px;
  letter-spacing: 0;
  max-width: -webkit-calc(100% - 23px);
  max-width: -moz-calc(100% - 23px);
  max-width: calc(100% - 23px);
  margin: 0 7px 0 0;
  font-weight: 700;
  line-height: 22px;
  color: #171717;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.recruitWrap .recruitList ul .listCell .rIcon {
  overflow: inherit;
}

.recruitWrap .rIcon.heart_off, .recruitWrap .rIcon .tplBtnFavOff {
  width: 16px;
  height: 14px;
}

.recruitWrap .rIcon {
  display: inline-block;
  position: relative;
  background-repeat: no-repeat;
  background-position: 0 0;
  -webkit-background-size: 100% auto;
  background-size: 100% auto;
  line-height: normal;
  text-align: center;
  vertical-align: middle;
  white-space: nowrap;
}

.recruitWrap .recruitList ul .listCell .listCont .rTit {
  margin: 12px 0 0;
  font-size: 16px;
  letter-spacing: 0;
  line-height: 26px;
  color: #171717;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.recruitWrap .recruitList ul .listCell .listCont .rInfo {
  overflow: hidden;
  margin: 4px 0 0;
  white-space: nowrap;
  text-overflow: ellipsis;
}

.recruitWrap .recruitList ul .listCell .listCont .rInfo span {
  position: relative;
  font-size: 13px;
  letter-spacing: 0;
  padding: 0 8px 0 0;
  line-height: 22px;
  color: #6a6a6a;
}

.recruitWrap .recruitList ul .listCell .listCont .rInfo span:after {
  display: inline-block;
  position: absolute;
  top: 50%;
  right: 0;
  width: 1px;
  height: 10px;
  background-color: #d2d2d2;
  -webkit-transform: translateY(-50%);
  -moz-transform: translateY(-50%);
  -ms-transform: translateY(-50%);
  transform: translateY(-50%);
  content: '';
}
.recruitWrap .recruitList ul .listCell .listCont .rInfo span:last-child:after {
  display: none;
}
.recruitWrap .recruitList ul .listCell .listCont .rInfo .rPeriod {
  padding: 0 8px 0 0;
}
.recruitWrap .recruitList ul .listCell .listCont .rInfo .rAddress,
.recruitWrap .recruitList ul .listCell .listCont .rInfo .rSkill {
  padding: 0 0 0 8px;
}
.recruitWrap .recruitList ul .listCell .listCont .rSkillBox {
  overflow: hidden;
  display: -webkit-box;
  display: -webkit-flex;
  display: -moz-box;
  display: flex;
  -webkit-box-align: center;
  -webkit-align-items: center;
  -moz-box-align: center;
  align-items: center;
  -webkit-flex-wrap: wrap;
  flex-wrap: wrap;
  height: 16px;
  margin: 12px 0 0;
}
.recruitWrap .recruitList ul .listCell .listCont .rSkillBox .rSkill {
  display: inline-block;
  max-width: 236px;
  font-size: 0;
}
.recruitWrap .recruitList ul .listCell .listCont .rSkillBox .rSkill .tag {
  max-width: 100%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.recruitWrap
.recruitList
ul
.listCell
.listCont
.rSkillBox
.rSkill
.tag
+ .tag {
  margin-left: 8px;
}
.recruitWrap
.recruitList
ul
.listCell
.listCont
.rSkillBox
.rSkill
.tag-skil
+ .tag-benefit {
  position: relative;
  margin-left: 0;
  padding-left: 17px;
}
.recruitWrap
.recruitList
ul
.listCell
.listCont
.rSkillBox
.rSkill
.tag-skil
+ .tag-benefit:before {
  display: inline-block;
  position: absolute;
  top: 50%;
  left: 8px;
  width: 2px;
  height: 2px;
  border-radius: 50%;
  background-color: #9e9e9e;
  -moz-transform: translateY(-50%);
  -ms-transform: translateY(-50%);
  -webkit-transform: translateY(-50%);
  transform: translateY(-50%);
  content: '';
}
.recruitWrap .recruitList ul .listCell .listCont .rSkillBox span {
  display: inline-block;
  font-size: 12px;
  letter-spacing: 0;
  position: relative;
  line-height: 16px;
  color: #9e9e9e;
}
.recruitWrap .recruitList ul .listCell .listFoot {
  margin: 0 24px;
  padding: 16px 0 24px;
  border-top: 1px solid #e8e8e8;
}
.recruitWrap .recruitList ul .listCell .listFoot .rAssist {
  font-size: 14px;
  letter-spacing: 0;
  display: -webkit-box;
  display: -webkit-flex;
  display: -moz-box;
  display: flex;
  -webkit-box-align: center;
  -webkit-align-items: center;
  -moz-box-align: center;
  align-items: center;
  -webkit-box-pack: justify;
  -webkit-justify-content: space-between;
  -moz-box-pack: justify;
  justify-content: space-between;
  position: relative;
  padding: 0 0 0 20px;
  line-height: 22px;
  color: #171717;
}
.recruitWrap .recruitList ul .listCell .listFoot .rAssist:before {
  display: inline-block;
  position: absolute;
  top: 50%;
  left: 0;
  background-image: url("data:image/svg+xml;charset=utf-8,%3Csvg width='14' height='14' fill='none' xmlns='http://www.w3.org/2000/svg'%3E%3Cg clip-path='url(%23clip0_796_175538)'%3E%3Cpath d='M7 13.781C3.264 13.781.219 10.736.219 7 .219 3.264 3.264.219 7 .219c3.736 0 6.781 3.045 6.781 6.781 0 3.736-3.045 6.781-6.781 6.781z' fill='%23FBC400'/%3E%3Cpath d='M7 13.563A6.565 6.565 0 0013.563 7 6.565 6.565 0 007 .437 6.565 6.565 0 00.437 7 6.565 6.565 0 007 13.563zM7 14c-3.859 0-7-3.141-7-7s3.141-7 7-7 7 3.141 7 7-3.141 7-7 7z' fill='%23FBC400'/%3E%3Cpath d='M11.314 6.799h-.84l.481-1.619a.427.427 0 00-.385-.577.492.492 0 00-.63.28c0 .017-.018.043-.018.07l-1.05 3.972h-.035L7.674 5.066a.625.625 0 00-.648-.481.625.625 0 00-.647.499l-1.19 3.85h-.035L4.139 4.988c-.079-.36-.377-.438-.692-.377a.455.455 0 00-.42.473c0 .044.01.079.027.122l.481 1.602h-.84a.374.374 0 00-.394.358v.07a.376.376 0 00.333.42h1.155l.551 1.812c.087.34.42.568.77.525a.709.709 0 00.77-.525l1.111-3.553h.035l1.111 3.553c.088.34.42.568.77.525a.696.696 0 00.77-.534l.534-1.803h1.094c.21 0 .376-.166.385-.367v-.053a.374.374 0 00-.324-.42h-.061l.009-.017z' fill='%23fff'/%3E%3C/g%3E%3Cdefs%3E%3CclipPath id='clip0_796_175538'%3E%3Cpath fill='%23fff' d='M0 0h14v14H0z'/%3E%3C/clipPath%3E%3C/defs%3E%3C/svg%3E");
  background-repeat: no-repeat;
  width: 16px;
  height: 16px;
  -webkit-background-size: cover;
  background-size: cover;
  -webkit-transform: translateY(-50%);
  -moz-transform: translateY(-50%);
  -ms-transform: translateY(-50%);
  transform: translateY(-50%);
  content: '';
}
.recruitWrap .recruitList ul .listCell .listFoot .rPeriod {
  font-size: 12px;
  letter-spacing: 0;
  line-height: 16px;
  color: #6a6a6a;
}
.recruitWrap .recruitList ul .listCell .rIcon {
  overflow: inherit;
}
.recruitWrap .recruitList ul .listCell .rIcon:hover:before {
  display: block;
}
.recruitWrap .recruitList ul .listCell .rIcon:before {
  display: none;
  position: absolute;
  top: -25.5px;
  left: 50%;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  padding: 2px 6px;
  border: 0.5px solid #d2d2d2;
  border-radius: 4px;
  background-color: #fff;
  -webkit-box-shadow: 0 1px 6px rgba(0, 0, 0, 0.08);
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.08);
  -webkit-transform: translateX(-50%);
  -moz-transform: translateX(-50%);
  -ms-transform: translateX(-50%);
  transform: translateX(-50%);
  content: '공고 스크랩';
}
.recruitWrap .recruitList .none > p {
  font-weight: 500;
  font-size: 18px;
  letter-spacing: 0;
  color: #9e9e9e;
}
.recruitWrap .recruitList .none > p:last-child {
  font-size: 14px;
  letter-spacing: 0;
  margin-top: 8px;
  font-weight: 400;
}
.recruitWrap .rIcon {
  display: inline-block;
  position: relative;
  background-repeat: no-repeat;
  background-position: 0 0;
  -webkit-background-size: 100% auto;
  background-size: 100% auto;
  line-height: normal;
  text-align: center;
  vertical-align: middle;
  white-space: nowrap;
}
.recruitWrap .rIcon.heart_off,
.recruitWrap .rIcon .tplBtnFavOff {
  width: 16px;
  height: 14px;
}
.recruitWrap .rIcon,
.recruitWrap .rIcon {
  position: absolute;
  top: 10px;
  background-image: url("data:image/svg+xml;charset=utf-8,%3Csvg xmlns='http://www.w3.org/2000/svg' width='19' height='18'%3E%3Cg data-name='다운로드 (4)'%3E%3Cpath data-name='패스 59' d='M9.506 14.6l-5.562 2.9 1.062-6.152L.5 6.992 6.719 6.1 9.5.5l2.781 5.6 6.219.892-4.506 4.356 1.062 6.152z' fill='rgba(23,23,23,0.3)'/%3E%3Cpath data-name='패스 60' d='M9.5 0a.73.73 0 01.654.4l2.54 5.1 5.68.816a.73.73 0 01.4 1.247l-4.113 3.97.97 5.6a.73.73 0 01-1.059.77l-5.07-2.648-5.08 2.65a.732.732 0 01-1.059-.772l.97-5.6L.22 7.563a.729.729 0 01.4-1.247L6.3 5.5 8.839.4A.73.73 0 019.5 0zm0 2.37L7.444 6.5a.73.73 0 01-.55.4l-4.583.658 3.318 3.2a.729.729 0 01.213.649l-.784 4.533 4.11-2.143a.732.732 0 01.677 0l4.1 2.142-.784-4.53a.729.729 0 01.213-.649l3.318-3.2-4.586-.66a.73.73 0 01-.55-.4z' fill='%23fff' fill-rule='evenodd'/%3E%3C/g%3E%3C/svg%3E");
  background-repeat: no-repeat;
  right: 10px;
  width: 20px;
  height: 19px;
}
.recruitWrap .rIcon,
.recruitWrap .rIcon,
.recruitWrap .rIcon {
  position: absolute;
  top: 10px;
  right: 10px;
  width: 20px;
  height: 19px;
}
.recruitWrap {
  margin: 80px 0;
}
.recruitWrap .foot_banner a {
  display: block;
}
.recruitWrap .foot_banner img {
  width: 100%;
}
.recruitWrap .recruitList ul > li {
  margin: 0 16px 32px;
}
.recruitWrap .recruitList ul > li:nth-child(4n + 1) {
  margin-left: 0;
}
.recruitWrap .recruitList ul > li:nth-child(4n) {
  margin-right: 0;
}
.recruitWrap .recruitList ul .listCell {
  position: relative;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  width: 286px;
  height: 350px;
  border: 1px solid #e8e8e8;
  border-radius: 8px;
  cursor: pointer;
  -webkit-box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.05);
  box-shadow: 2px 2px 10px rgba(0, 0, 0, 0.05);
  -webkit-transition: 0.3s;
  -moz-transition: 0.3s;
  transition: 0.3s;
}
.recruitWrap .recruitList ul .listCell:hover {
  -webkit-transform: translateY(-8px);
  -moz-transform: translateY(-8px);
  -ms-transform: translateY(-8px);
  transform: translateY(-8px);
}

.recruitWrap .recruitList ul {
  display: -webkit-box;
  display: -webkit-flex;
  display: -moz-box;
  display: flex;
  -webkit-flex-wrap: wrap;
  flex-wrap: wrap;
  margin: 0;
}

.dead-line {float: inline-end;}

</style>
