<script setup>
import { ref, onMounted, nextTick } from 'vue';
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import { UseCompanyStore } from '@/stores/UseCompanyStore';
import { UseBaseInfoStore } from '@/stores/UseBaseInfoStore';
// import { useRouter } from 'vue-router';

const companyStore = UseCompanyStore();
const baseInfoStore = UseBaseInfoStore();
// const router = useRouter(); // router 호출

// 템플릿 ref 정의
const imageUploadInput = ref(null);
// const imageCountDisplay = ref(null);
// const imagePreviewContainer = ref(null);
const priceInput = ref(null);  // 초기값을 null로 설정
const uploadedImages = ref([]); // 업로드 된 이미지 배열
const imageCount = ref(0); // 이미지 개수를 추적하기 위한 변수
const type = ref(companyStore.companyInfo.type ? companyStore.companyInfo.type : '');

// 이미지 업로드 핸들러
const handleImageUpload = (event) => {
  const files = Array.from(event.target.files);

  // 최대 10개의 파일까지만 업로드 허용
  if (uploadedImages.value.length + files.length > 10) {
    alert('최대 10개의 이미지만 업로드할 수 있습니다.');
    return;
  }

  files.forEach((file) => {
    const isFileAlreadyUploaded = uploadedImages.value.some((uploadedFile) => uploadedFile.file.name === file.name && uploadedFile.file.size === file.size);

    if (!isFileAlreadyUploaded) {
      const reader = new FileReader();
      reader.onload = (e) => {
        // 이미지 미리보기 추가
        uploadedImages.value.push({
          file,
          url: e.target.result,
        });

        // 스토어에 파일 추가
        companyStore.files.push(file);

        // 이미지 개수 업데이트
        imageCount.value = uploadedImages.value.length;
      };
      reader.readAsDataURL(file); // 이미지 파일을 읽음
    } else {
      console.log("중복된 파일이 있습니다:", file.name);
    }
  });
};

// 이미지 삭제 핸들러
const removeImage = (index) => {
  uploadedImages.value.splice(index, 1); // 로컬 이미지 배열에서 삭제
  companyStore.files.splice(index, 1); // 스토어에서 삭제
  imageCount.value = uploadedImages.value.length; // 이미지 개수 업데이트
};


// 가격 입력 핸들러
const handlePriceInput = (event) => {
  const value = event.target.value.replace(/[^0-9]/g, '');
  priceInput.value.value = value;  // DOM 요소의 value 속성에 직접 접근
};


// 선택된 복리후생 리스트
const selectedSubcategories = ref([]); // 선택된 소분류
const selectedCategories = ref([]); // 선택된 대분류

const categories = ref([]); // 대분류와 소분류 데이터

// 소분류 선택/해제 함수
const toggleSubcategory = (subcategoryCode, categoryCode) => {
  if (selectedSubcategories.value.includes(subcategoryCode)) {
    // 이미 선택된 소분류라면 제거
    selectedSubcategories.value = selectedSubcategories.value.filter(
      (code) => code !== subcategoryCode
    );

    // 대분류에서 해당 카테고리 삭제
    if (!hasSelectedSubcategory(categoryCode)) {
      selectedCategories.value = selectedCategories.value.filter(
        (code) => code !== categoryCode
      );
    }
  } else {
    // 소분류 선택
    selectedSubcategories.value.push(subcategoryCode);

    // 대분류 추가
    if (!selectedCategories.value.includes(categoryCode)) {
      selectedCategories.value.push(categoryCode);
    }
  }
};

// 대분류에 선택된 소분류가 있는지 확인하는 함수
const hasSelectedSubcategory = (categoryCode) => {
  const category = categories.value.find((cat) => cat.code === categoryCode);
  if (!category) return false;
  return category.subcategories.some((subcategory) =>
    selectedSubcategories.value.includes(subcategory.code)
  );
};


// 등록 버튼 클릭 시 전처리
const saveSelection = () => {
  // 대분류와 소분류 배열을 합침
  const combinedCategories = [...selectedCategories.value, ...selectedSubcategories.value];

  // console.log(selectedCategories.value + selectedSubcategories.value);
  // console.log(companyStore.companyInfo);

  companyStore.createCompany(combinedCategories, type); // 스토어 저장 처리
};


// onMounted로 DOM 이벤트 등록
onMounted(async () => {
  // companyInfo 초기화
  companyStore.resetCompanyInfo();

  await nextTick(); // DOM이 완전히 렌더링된 후에 실행

  // 페이지 입장 시 files 배열을 초기화 (배열 내부의 요소들을 제거)
  companyStore.files.splice(0);
  uploadedImages.value.splice(0);

  if (imageUploadInput.value) {
    imageUploadInput.value.addEventListener('change', handleImageUpload);
  }

  // if (imagePreviewContainer.value) {
  //   imagePreviewContainer.value.addEventListener('click', handleImagePreviewClick);
  // }

  if (priceInput.value) {
    const priceInputElement = priceInput.value; // priceInput의 DOM 요소 가져오기
    priceInputElement.addEventListener('input', handlePriceInput); // 이벤트 리스너 추가
  }

  companyStore.readCompany(); // 등록된 기업 정보 있나 조회
  const keyword = "benefit"
  await baseInfoStore.fetchCategories(keyword); // 카테고리 데이터를 백엔드에서 가져옴
  categories.value = baseInfoStore.categories; // 스토어에서 불러온 데이터를 categories에 할당

});
</script>

<template>
  <MainHeaderComponent />
  <div class="container padding-100 0 0 0" style="margin: 150px 0 200px 200px;">
    <MainSideBarComponent />
    <div id="content" style="padding: 100px 100px;">
      <!-- 기업 정보 등록 섹션 -->
      <div v-if="companyStore.companyInfo.saved === 'N'" id="createSections">
        <h1>기업 정보 등록</h1>
        <!-- 이미지 업로드 섹션 -->
        <div class="image-upload-container">
          <div>
            <input ref="imageUploadInput" id="image-upload" name="media" type="file" multiple
              accept="image/png, image/jpeg, image/jpg" class="hidden" @change="handleImageUpload" />
            <label for="image-upload" class="product-image-btn">
              <div class="flex flex-col">
                <img src="@/assets/img/img_upload.png" style="width: 80px; height: 80px" />
                <p id="image-count">{{ imageCount }} / 10</p>
              </div>
            </label>
          </div>

          <!-- 업로드된 이미지 미리보기 -->
          <div class="image-preview-container">
            <div v-for="(image, index) in uploadedImages" :key="index" class="image-preview">
              <img :src="image.url" alt="Uploaded Image" style="width: 200px; height: 200px;" />
              <button class="remove-image-btn" @click="removeImage(index)">X</button>
            </div>
          </div>
        </div>

        <div class="company-info">
          <!-- 기업 인증 테이블에서 받아오는 정보 -->
          <div style="display: flex; justify-items: center;">
            <div class="form-group">
              <label for="ceo">대표자 명</label>
              <input type="text" v-model="companyStore.companyInfo.ceoName" id="ceo" placeholder="대표자 명" readonly />
            </div>
            <div class="form-group">
              <label for="crn">사업자 등록 번호</label>
              <input type="text" v-model="companyStore.companyInfo.crn" id="ceo" placeholder="사업자 등록번호" readonly />
            </div>
            <div class="form-group">
              <label for="opened-at">개업 일자</label>
              <input type="text" v-model="companyStore.companyInfo.openedAt" id="ceo" placeholder="개업 일자" readonly />
            </div>
          </div>
          <!-- 입력하는 정보 -->
          <div class="form-group">
            <label for="industry">산업</label>
            <input type="text" v-model="companyStore.companyInfo.industry" id="industry"
              placeholder="산업을 입력하세요 (예: 솔루션, 서비스 등)" />
          </div>
          <div class="form-group">
            <label for="company-name">기업 명</label>
            <input type="text" v-model="companyStore.companyInfo.name" id="company-name" placeholder="기업 이름을 입력하세요" />
          </div>
          <div class="form-group">
            <label for="company-type">기업 구분</label>
            <select v-model="type" id="company-type" class="form-control styled-select" style="width: 220px;">
              <option value="" disabled>기업 구분을 선택하세요</option>
              <option value="대기업">대기업</option>
              <option value="중견기업">중견기업</option>
              <option value="중소기업">중소기업</option>
              <option value="공공기관/공기업">공공기관/공기업</option>
              <option value="외국계기업">외국계기업</option>
              <option value="비영리단체/협회/재단">비영리단체/협회/재단</option>
              <option value="벤처기업">벤처기업</option>
            </select>
          </div>
          <div class="form-group">
            <label for="company-info">기업 소개</label>
            <input type="text" v-model="companyStore.companyInfo.companyInfo" id="company-info"
              placeholder="기업 소개를 입력하세요" />
          </div>
          <div class="form-group">
            <label for="capital">자본금</label>
            <input type="text" v-model="companyStore.companyInfo.capital" id="capital" placeholder="자본금을 입력하세요" />
          </div>
          <div class="form-group">
            <label for="employee-count">사원수</label>
            <input type="text" v-model="companyStore.companyInfo.totalEmp" id="employee-count"
              placeholder="사원수를 입력하세요" />
          </div>
          <div class="form-group">
            <label for="establishment-date">설립일</label>
            <input type="date" v-model="companyStore.companyInfo.establishDate" id="establishment-date" style="width: 200px;"
              placeholder="설립일을 입력하세요" />
          </div>
          <div class="form-group">
            <label for="revenue">매출액</label>
            <input type="number" style="padding: 15px 10px; border-radius: 5px;" v-model="companyStore.companyInfo.sales" ref="priceInput" id="revenue"
              placeholder="매출액을 입력하세요" />
          </div>
          <div class="form-group">
            <label for="main-business">주요사업</label>
            <input type="text" v-model="companyStore.companyInfo.business" id="main-business"
              placeholder="주요사업을 입력하세요 (예: ~에 관한 전반적인 it시스템 사업을 합니다)" />
          </div>
          <div class="form-group">
            <label for="homepage">홈페이지</label>
            <input type="text" v-model="companyStore.companyInfo.homeUrl" id="homepage" placeholder="홈페이지 URL을 입력하세요" />
          </div>
          <div class="form-group">
            <label for="address">기업 주소</label>
            <input type="text" v-model="companyStore.companyInfo.address" id="address" placeholder="기업 주소를 입력하세요" />
          </div>

          <br>
          <br>

          <!-- 복리후생 추가 -->
          <h2>기업 복리후생 추가</h2>
          <!-- 대분류 및 소분류 복리후생 -->
          <div style="padding: 10px 10px;">
            <div v-for="(category, index) in categories" :key="index" class="category-box">
              <h3>{{ category.description }}</h3>
              <div class="subcategories">
                <button class="button-be" v-for="(subcategory, subIndex) in category.subcategories" :key="subIndex"
                  :class="{ 'selected': selectedSubcategories.includes(subcategory.code) }"
                  @click="toggleSubcategory(subcategory.code, category.code)">
                  {{ subcategory.description }}
                </button>
              </div>
            </div>
          </div>

          <button @click="saveSelection" class="submit-button">등록하기</button>
        </div>
      </div>

      <!-- 기업 정보 조회 섹션 -->
      <div v-else id="readSections" style="margin: 0 0 100px 0;">
        <h1>{{ companyStore.companyInfo.name }} 기업 정보</h1>
        <div class="image-preview-container">
          <!-- 이미지 리스트를 보여줌 -->
          <div style="margin-bottom: 20px;" v-for="(imgUrl, index) in companyStore.companyInfo.imgUrlList" :key="index"
            class="image-preview">
            <img :src="imgUrl" alt="Company Image" style="width: 150px; height: 150px; margin: 5px;" />
          </div>
        </div>

        <div class="company-info">
          <div style="display: flex;">
            <div class="form-group">
              <label for="ceo">대표자 명</label>
              <p>{{ companyStore.companyInfo.ceoName }}</p>
            </div>
            <div class="form-group">
              <label for="crn">사업자 등록 번호</label>
              <p>{{ companyStore.companyInfo.crn }}</p>
            </div>
            <div class="form-group">
              <label for="opened-at">개업 일자</label>
              <p>{{ companyStore.companyInfo.openedAt }}</p>
            </div>
          </div>

          <hr style="border: 0.5px solid #abadb8; margin: 30px 0;">

          <!-- 복리후생 박스 -->
          <label style="margin-top: 50px;" for="ceo">기업 복리후생</label>
          <div class="benefits-box">
            <!-- 복리후생 대분류 및 소분류 표시 -->
            <div v-for="(benefit, index) in companyStore.companyBenefits" :key="index" style="margin-bottom: 10px;">
              <strong class="category-label">{{ benefit.category }} > </strong>
              <span v-for="(subcategory, subIndex) in benefit.subcategories" :key="subIndex" class="subcategory-label">
                {{ subcategory }}
              </span>
            </div>
          </div>

          <!-- <hr style="border: 0.5px solid #abadb8; margin: 30px 0;"> -->

          <div class="form-group">
            <label for="industry">산업</label>
            <p>{{ companyStore.companyInfo.industry }}</p>
          </div>
          <div class="form-group">
            <label for="company-name">기업 명</label>
            <p>{{ companyStore.companyInfo.name }}</p>
          </div>
          <div class="form-group">
            <label for="company-type">기업 구분</label>
            <p>{{ companyStore.companyInfo.type }}</p>
          </div>
          <div class="form-group">
            <label for="company-info">기업 소개</label>
            <p>{{ companyStore.companyInfo.companyInfo }}</p>
          </div>
          <div class="form-group">
            <label for="capital">자본금</label>
            <p>{{ companyStore.companyInfo.capital }}</p>
          </div>
          <div class="form-group">
            <label for="employee-count">사원수</label>
            <p>{{ companyStore.companyInfo.totalEmp }}</p>
          </div>
          <div class="form-group">
            <label for="establishment-date">설립일</label>
            <p>{{ companyStore.companyInfo.establishDate }}</p>
          </div>
          <div class="form-group">
            <label for="revenue">매출액</label>
            <p>{{ companyStore.companyInfo.sales }}</p>
          </div>
          <div class="form-group">
            <label for="main-business">주요 사업</label>
            <p>{{ companyStore.companyInfo.business }}</p>
          </div>
          <div class="form-group">
            <label for="homepage">홈페이지</label>
            <p>{{ companyStore.companyInfo.homeUrl }}</p>
          </div>
          <div class="form-group">
            <label for="address">기업 주소</label>
            <p>{{ companyStore.companyInfo.address }}</p>
          </div>
        </div>
        <!-- <button class="submit-button">수정하기</button> -->
      </div>
    </div>
  </div>
</template>

<style scoped>
.image-upload-container img {
  width: 100px;
  height: 100px;
  border: 1px solid #212b36;
}

.image-preview img {
  border: none;
}

.company-info input {}

.company-info .form-group {
  margin: 20px auto;
}

.company-info input[type="text"],
.company-info input[type="date"] {
  height: 40px;
  font-size: 1rem;
}

.company-info input[type="date"] {
  width: calc(100% - 40px);
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
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
  border-radius: 5px;
}

.styled-select, input[type="number"] {
  /* width: calc(100% - 40px); */
  padding: 20px 5px;
  width: 200px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #fff;
  background-size: 15px;
  font-size: 16px;
}

/* .submit-button {
  width: 100%;
  margin-top: 20px;
  padding: 10px;
  background-color: #212b36;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
} */

.submit-button {
  margin-top: 50px;
  padding: 15px 30px;
  width: 100%;
  background-color: #212b36;
  color: white;
  border: none;
  border-radius: 5px;
  font-size: 16px;
  font-weight: bold;
  cursor: pointer;
}

.submit-button:hover {
  background-color: #37404a;
}

.button-be.selected {
  background-color: #212b36;
  color: white;
}

.button-be:hover {
  background-color: #37404a;
  color: white;
}

.button-be {
  padding: 10px 20px;
  margin: 5px;
  border-radius: 5px;
  border: 1px solid #ccc;
  background-color: #f1f1f1;
  cursor: pointer;
  transition: background-color 0.3s ease;
  font-size: 16px;
}

.subcategories {
  display: flex;
  flex-wrap: wrap;
}

/*  */

.benefit-category {
  margin-bottom: 20px;
}

.benefit-subcategory button {
  padding: 10px;
  border-radius: 20px;
  background-color: #eaedf4;
  margin-right: 10px;
  cursor: pointer;
  border: 1px solid #ccc;
}

.benefits-box {
  /* border: 1px solid #f5f8ff; */
  /* background-color: #f5f8ff; */
  padding: 20px;
  border-radius: 5px;
  margin-bottom: 50px;
}

/* .benefit-item {
  display: flex;
  justify-content: space-between;
  margin-bottom: 10px;
} */

.benefit-item {
  display: flex;
  justify-content: center;
  min-width: 300px;
  padding: 5px;
  background-color: white;
  border: 1px solid #ccc;
  border-radius: 15px;
  margin-bottom: 10px;
}

.hidden {
  display: none;
}

.image-preview {
  position: relative;
  display: inline-block;
  margin: 10px;
}

.remove-image-btn {
  position: absolute;
  top: 0;
  right: 0;
}

.category-label {
  border: 1px solid #ccc;
  padding: 5px 10px;
  border-radius: 15px;
  margin: 5px;
  background-color: #212b36;
  color: white;
  /* font-weight: lighter; */
}

.subcategory-label {
  border: 1px solid #ccc;
  padding: 5px 10px;
  border-radius: 15px;
  margin: 5px;
  display: inline-block;
  background-color: white;
}
</style>
