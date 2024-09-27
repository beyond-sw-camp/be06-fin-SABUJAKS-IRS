<script setup>
import { ref, onMounted } from 'vue';
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";

// 템플릿 ref 정의
const imageUploadInput = ref(null);
const imageCountDisplay = ref(null);
const imagePreviewContainer = ref(null);
const priceInput = ref(null);  // 초기값을 null로 설정

// 업로드된 이미지 배열
const uploadedImages = ref([]);

// 이미지 업로드 핸들러
const handleImageUpload = (event) => {
  const files = Array.from(event.target.files);
  if (uploadedImages.value.length + files.length > 10) {
    alert('최대 10개의 이미지만 업로드할 수 있습니다.');
    return;
  }

  files.forEach((file, index) => {
    const reader = new FileReader();
    reader.onload = (e) => {
      const imgElement = document.createElement('div');
      imgElement.classList.add('image-preview');
      imgElement.innerHTML = `
        <img src="${e.target.result}" alt="Uploaded Image" style="width: 200px; height: 200px;"/>
        <button class="remove-image-btn" data-index="${uploadedImages.value.length + index}">X</button>
      `;
      imagePreviewContainer.value.appendChild(imgElement);
    };
    reader.readAsDataURL(file);
    uploadedImages.value.push(file);
  });

  updateImageCount();
};

// 이미지 삭제 핸들러
const handleImagePreviewClick = (event) => {
  if (event.target.classList.contains('remove-image-btn')) {
    const index = event.target.getAttribute('data-index');
    uploadedImages.value.splice(index, 1);
    updateImageCount();
    event.target.parentElement.remove();
  }
};

// 이미지 개수 업데이트
const updateImageCount = () => {
  imageCountDisplay.value.textContent = `${uploadedImages.value.length} / 10`;
};

// 가격 입력 핸들러
const handlePriceInput = (event) => {
  const value = event.target.value.replace(/[^0-9]/g, '');
  priceInput.value.value = value;  // DOM 요소의 value 속성에 직접 접근
};

// onMounted로 DOM 이벤트 등록
onMounted(() => {
  imageUploadInput.value.addEventListener('change', handleImageUpload);
  imagePreviewContainer.value.addEventListener('click', handleImagePreviewClick);

  // priceInput의 DOM 요소에 접근
  const priceInputElement = priceInput.value; // priceInput의 DOM 요소 가져오기
  priceInputElement.addEventListener('input', handlePriceInput); // 이벤트 리스너 추가
});
</script>

<template>
  <MainHeaderComponent />
  <div class="container padding-0">
    <MainSideBarComponent />
    <div id="content">
      <h1>마이페이지</h1>
      <div class="image-upload-container">
        <div>
          <input
              ref="imageUploadInput"
              id="image-upload"
              name="media"
              type="file"
              multiple
              accept="image/png, image/jpeg, image/jpg"
              class="hidden"
          />
          <label for="image-upload" class="product-image-btn">
            <div class="flex flex-col">
              <img src="@/assets/img/img_upload.png" style="width: 80px; height: 80px" />
              <p ref="imageCountDisplay" id="image-count">0 / 10</p>
            </div>
          </label>
        </div>
        <div ref="imagePreviewContainer" id="image-preview-container" class="image-preview-container"></div>
      </div>

      <div class="company-info">
        <div class="form-group">
          <label for="industry">산업</label>
          <input type="text" id="industry" placeholder="산업을 입력하세요" />
        </div>
        <div class="form-group">
          <label for="company-type">기업구분</label>
          <input type="text" id="company-type" placeholder="기업구분을 입력하세요" />
        </div>
        <div class="form-group">
          <label for="capital">자본금</label>
          <input type="text" id="capital" placeholder="자본금을 입력하세요" />
        </div>
        <div class="form-group">
          <label for="ceo">대표자</label>
          <input type="text" id="ceo" placeholder="대표자 이름을 입력하세요" />
        </div>
        <div class="form-group">
          <label for="employee-count">사원수</label>
          <input type="text" id="employee-count" placeholder="사원수를 입력하세요" />
        </div>
        <div class="form-group">
          <label for="establishment-date">설립일</label>
          <input type="date" id="establishment-date" placeholder="설립일을 입력하세요" />
        </div>
        <div class="form-group">
          <label for="revenue">매출액</label>
          <input type="text" ref="priceInput" id="revenue" placeholder="매출액을 입력하세요" />
        </div>
        <div class="form-group">
          <label for="main-business">주요사업</label>
          <input type="text" id="main-business" placeholder="주요사업을 입력하세요" />
        </div>
        <div class="form-group">
          <label for="homepage">홈페이지</label>
          <input type="text" id="homepage" placeholder="홈페이지 URL을 입력하세요" />
        </div>
        <div class="form-group">
          <label for="address">주소</label>
          <input type="text" id="address" placeholder="주소를 입력하세요" />
        </div>
        <div class="form-group">
          <label for="copy">복리후생</label>
          <select id="copy">
            <option value="">선택하세요</option>
            <option value="option1">옵션 1</option>
            <option value="option2">옵션 2</option>
            <option value="option3">옵션 3</option>
          </select>
        </div>

        <button class="submit-button">수정</button>
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

.image-preview img{
  border: none;
}

.company-info input {

}

.company-info .form-group {
  margin: 20px auto;
}

.company-info input[type="text"], .company-info input[type="date"] {
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
</style>

<style>
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
</style>
