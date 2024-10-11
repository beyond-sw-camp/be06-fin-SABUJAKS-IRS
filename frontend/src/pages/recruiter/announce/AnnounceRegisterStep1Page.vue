<script>
import MainHeaderComponent from '../../../components/recruiter/MainHeaderComponent.vue';
import MainSideBarComponent from '../../../components/recruiter/MainSideBarComponent.vue';

import { ref, computed, onMounted, watch } from "vue";
import { UseAnnouncementStore } from '@/stores/UseAnnouncementStore';
import { UseAuthStore } from '@/stores/UseAuthStore';
import { UseBaseInfoStore } from '@/stores/UseBaseInfoStore';
import { useRouter } from 'vue-router';
import { useToast } from "vue-toastification";

export default {
  components: {
    MainHeaderComponent,
    MainSideBarComponent,
  },

  setup() {
    const announcementStore = UseAnnouncementStore();
    const authStore = UseAuthStore();
    const baseInfoStore = UseBaseInfoStore();
    const router = useRouter();
    const toast = useToast();


    // 1. 항목 추가 필드 상태 (true/false)
    const fields = ref({
      mainDuty: false,
      department: false,
      position: false,
      requirement: false,
    });

    // 2. 선택 필드 추가/삭제 상태 관리
    const fields2 = ref({
      workDays: false,
      workTime: false,
    });


    // 셀렉트 폼 관련 js ***********************************************************************************

    const isImageUpload = ref(true); // 이미지 업로드 양식 여부
    const imageUrl = ref(''); // 이미지 URL, 기본값 빈 문자열로 설정, 미리보기용

    // 양식 선택 함수
    const selectFormType = (type) => {
      // if (type === 'imageUpload') {
      //   isImageUpload.value = true;
      // } else {
      //   isImageUpload.value = false;
      // }
      isImageUpload.value = type;
    };

    const fileName = ref(''); // 선택된 파일 이름 저장 변수

    // 이미지 미리보기 함수 수정
    const previewImage = (event) => {
      const file = event.target.files[0];
      if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
          imageUrl.value = e.target.result;
          announcementStore.file = file; // 스토어의 파일 변수 업데이트
          fileName.value = file.name; // 파일 이름 저장
        };
        reader.readAsDataURL(file);
      }
    };

    // 이미지 초기화 함수
    const resetImage = () => {
      imageUrl.value = ''; // 이미지 미리보기 초기화
      fileName.value = ''; // 파일 이름 초기화
      announcementStore.file = null; // 스토어의 파일 변수 초기화

      const fileInput = document.querySelector('input[type="file"]');
      if (fileInput) {
        fileInput.value = ''; // 파일 선택 초기화
      }
    };

    // 1. 모집분야 폼 관련 *************************************************************************************

    // 카테고리 및 소분류 데이터
    const categoryData = ref({
      "기획·전략": ["사업기획", "전략수립", "경영기획", "프로젝트관리", "사업분석", "시장조사", "리스크관리"],
      "교육": ["교육기획", "학습상담", "학원생관리", "교육운영", "교육컨설팅", "교육콘텐츠기획"],
      "고객상담·TM": ["고객상담", "TM상담", "해피콜", "클레임관리", "고객데이터관리", "서비스품질관리"],
      "IT·개발": ["프론트엔드개발", "백엔드개발", "모바일개발", "웹개발", "데이터베이스관리", "시스템엔지니어", "소프트웨어테스트"],
      "마케팅": ["디지털마케팅", "콘텐츠마케팅", "마케팅전략", "브랜드관리", "광고기획", "소셜미디어관리", "시장분석"],
      "영업": ["B2B영업", "B2C영업", "영업기획", "영업관리", "신규사업개발", "영업지원", "채널관리"],
      "인사": ["인사기획", "채용", "인재육성", "조직문화관리", "노무관리", "성과관리", "복리후생"],
      "재무·회계": ["재무관리", "회계", "세무", "재무기획", "자금관리", "리스크관리", "재무분석"],
      "법무": ["법무지원", "계약관리", "소송관리", "기업법무", "지적재산권관리", "컴플라이언스", "규제대응"],
      "홍보·PR": ["미디어관리", "언론대응", "홍보전략", "브랜드PR", "내부커뮤니케이션", "이벤트기획"],
      "디자인": ["그래픽디자인", "UI/UX디자인", "웹디자인", "광고디자인", "브랜드디자인", "제품디자인", "패키지디자인"],
      "생산·제조": ["생산관리", "공정관리", "설비관리", "품질관리", "자재관리", "안전관리", "생산계획"],
      "연구개발(R&D)": ["제품개발", "기술연구", "시장조사", "특허관리", "신제품기획", "기술기획", "기술동향분석"],
      "물류": ["물류관리", "재고관리", "유통기획", "운송관리", "창고관리", "국제물류", "SCM관리"],
      "구매": ["구매관리", "원가관리", "공급업체관리", "구매계약", "수입관리", "원자재구매"],
      "건설·토목": ["건축설계", "토목설계", "현장관리", "공사관리", "안전관리", "프로젝트관리", "자재관리"],
      "의료": ["간호", "진료지원", "의료기기관리", "환자관리", "의료코디네이터", "약사", "의무기록관리"],
      "연구": ["임상연구", "바이오연구", "의약품연구", "화학분석", "기초과학연구", "실험실관리", "기술연구"],
      "법무·특허": ["특허출원", "지식재산권관리", "법무상담", "계약관리", "소송대응", "컴플라이언스"],
      "서비스": ["고객서비스", "서비스기획", "리셉션", "클레임처리", "CS운영", "서비스교육"],
      "비서·총무": ["비서업무", "일정관리", "총무관리", "사무지원", "자산관리", "문서관리", "회의관리"],
      "전략기획": ["사업전략수립", "시장분석", "재무분석", "위험관리", "프로젝트관리", "경쟁사분석", "장기계획수립"],
      "엔지니어": ["기계설계", "전기설계", "시스템엔지니어", "자동화설비관리", "설비보전", "기술지원", "제품개발"],
      "유통·판매": ["판매관리", "매장관리", "상품기획", "유통기획", "재고관리", "프로모션기획", "매출분석"],
      "리스크관리": ["금융리스크관리", "재무리스크관리", "위험분석", "규제대응", "자산운용", "보안관리"],
      "품질관리": ["QC", "QA", "품질보증", "제품검사", "공정관리", "불량분석", "고객불만처리"],
      "공공행정": ["행정기획", "정책분석", "공공사업관리", "국제협력", "정책평가", "공공서비스개선"],
      "광고·미디어": ["광고기획", "미디어플래닝", "방송제작", "광고대행", "PR전략", "콘텐츠기획", "미디어구매"],
      "항공": ["항공기정비", "승무원", "공항운영", "화물운송", "비행기정비", "항공사운영"],
      "호텔·외식": ["호텔운영", "레스토랑운영", "조리", "객실관리", "외식기획", "행사기획", "프론트관리"]
    });

    // const jobCategories = ref([]); // 모집직무 대분류와 소분류 데이터

    // // 상태값
    // const showCategoryDropdown = ref(false);
    // const selectedCategory = ref(''); // 선택된 대분류 코드
    // const selectedSubcategories = ref([]); // 선택된 소분류 코드 리스트
    // const selectedCategories = ref({}); // 저장된 대분류 및 소분류 코드들

    const jobCategories = ref([]); // 서버에서 받아온 데이터 저장
    const selectedCategory = ref(''); // 선택된 대분류 코드
    const selectedSubcategories = ref([]); // 선택된 소분류 코드 리스트
    const selectedCategories = ref({}); // 저장된 대분류 및 소분류 코드들
    const showCategoryDropdown = ref(false); // 카테고리 선택 드롭다운 표시 여부

    // const showCategoryDropdown = ref(false); // 카테고리 선택 드롭다운 표시 여부
    // const selectedCategory = ref(''); // 선택된 대분류 카테고리
    // const selectedSubcategories = ref([]); // 선택된 소분류 카테고리들
    // const selectedCategories = ref({}); // 저장된 대분류 및 소분류 카테고리

    // 카테고리 선택 드롭다운 토글 함수
    const toggleCategoryDropdown = () => {
      showCategoryDropdown.value = !showCategoryDropdown.value; // 열기/닫기 토글
    };

    // // 선택된 대분류 및 소분류를 저장하는 함수
    // const addSelectedCategories = () => {
    //   if (selectedCategory.value && selectedSubcategories.value.length) {
    //     selectedCategories.value = {
    //       ...selectedCategories.value,
    //       [selectedCategory.value]: [...(selectedCategories.value[selectedCategory.value] || []), ...selectedSubcategories.value],
    //     };
    //     selectedCategory.value = '';
    //     selectedSubcategories.value = [];
    //     showCategoryDropdown.value = false;

    //   }
    // };

    // 선택된 대분류 및 소분류를 저장하는 함수
    const addSelectedCategories = () => {
      if (selectedCategory.value && selectedSubcategories.value.length) {
        const selectedCategoryObj = jobCategories.value.find(cat => cat.code === selectedCategory.value);

        if (selectedCategoryObj) {
          // 소분류 코드와 설명을 함께 저장
          const subcategoryDetails = selectedSubcategories.value.map(subCode => {
            const subcategory = selectedCategoryObj.subcategories.find(sub => sub.code === subCode);
            return { code: subcategory.code, description: subcategory.description };
          });

          // 대분류 설명을 키로, 소분류의 code와 description 객체 리스트를 값으로 추가
          selectedCategories.value = {
            ...selectedCategories.value,
            [selectedCategoryObj.description]: [
              ...(selectedCategories.value[selectedCategoryObj.description] || []),
              ...subcategoryDetails,
            ],
          };
        }
        selectedCategory.value = '';
        selectedSubcategories.value = [];
        showCategoryDropdown.value = false;
      }
    };

    // // 소분류 삭제 함수
    // const removeSubcategory = (category, sub) => {
    //   selectedCategories.value[category] = selectedCategories.value[category].filter((item) => item !== sub);
    //   if (!selectedCategories.value[category].length) {
    //     delete selectedCategories.value[category]; // 대분류에 소분류가 없으면 대분류 삭제
    //   }
    // };

    // 소분류 삭제 함수
    const removeSubcategory = (category, sub) => {
      selectedCategories.value[category] = selectedCategories.value[category].filter(item => item.code !== sub.code);
      if (!selectedCategories.value[category].length) {
        delete selectedCategories.value[category];
      }
    };

    // 하나의 체크박스만 선택되도록 하는 함수 - 경력
    const exclusiveCheckbox = (checkedKey, otherKey) => {
      if (announcementStore.formData[checkedKey]) {
        announcementStore.formData[otherKey] = false; // 하나 선택 시 다른 체크박스 해제
      }
    };

    // 필드 토글 함수 - 항목 추가
    const toggleField = (fieldKey) => {
      fields.value[fieldKey] = !fields.value[fieldKey]; // 상태를 반전
    };

    const autoResize = (event) => {
      const textarea = event.target;
      textarea.style.height = 'auto';  // 현재 높이를 초기화
      textarea.style.height = `${textarea.scrollHeight}px`;  // 새로운 높이 설정
    };

    // 2. 지원자격/근무조건 폼 관련 *****************************************************************************

    // 근무형태 체크박스 그룹
    const employmentTypes = ref({
      fullTime: false,
      contract: false,
      partTime: false,
      intern: false,
      freelancer: false,
      part: false,
    });

    // 체크박스 하나만 선택할 수 있도록 제어
    const handleExclusiveCheckboxGroup = (selectedType) => {
      Object.keys(employmentTypes.value).forEach((type) => {
        employmentTypes.value[type] = type === selectedType;
      });
    };

    // 선택된 체크박스 값 저장
    const getSelectedJobType = () => {
      const selectedType = Object.keys(employmentTypes.value).find(type => employmentTypes.value[type]);

      // 선택된 값이 있으면 해당하는 한글 문자열을 저장
      if (selectedType) {
        switch (selectedType) {
          case 'fullTime':
            announcementStore.formData.jobType = '정규직';
            break;
          case 'contract':
            announcementStore.formData.jobType = '계약직';
            break;
          case 'partTime':
            announcementStore.formData.jobType = '아르바이트';
            break;
          case 'intern':
            announcementStore.formData.jobType = '인턴직';
            break;
          case 'freelancer':
            announcementStore.formData.jobType = '프리랜서';
            break;
          case 'part':
            announcementStore.formData.jobType = '파트';
            break;
          default:
            announcementStore.formData.jobType = '';
        }
      } else {
        announcementStore.formData.jobType = ''; // 선택된 값이 없으면 빈 문자열 저장
      }
    };


    // 근무 요일 선택 옵션
    const workDaysOptions = [
      '주 5일(월~금)', '주 6일(월~토)', '주 3일(격일제)', '유연근무제', '면접 후 결정',
    ];

    // 출퇴근 시간 옵션
    const timeOptions = [
      '07시', '08시', '09시', '10시', '11시', '12시', '13시', '14시', '15시', '16시',
      '17시', '18시', '19시', '20시', '21시', '22시', '23시', '24시',
    ];

    // 근무 요일 선택란 토글
    const toggleSelectBox = (fieldKey) => {
      fields2.value[fieldKey] = !fields2.value[fieldKey];
    };

    // 출퇴근 시간 선택란 토글
    const toggleWorkTimeSelect = () => {
      fields2.value.workTime = !fields2.value.workTime;
    };

    // 3. 기업 복리후생 폼 관련 *********************************************************************************

    // 선택된 복리후생 대분류 및 소분류
    const selectedBenefitCategory = ref('');
    const selectedBenefitSubcategories = ref([]);
    const selectedBenefits = ref({});

    // 기업 복리후생 자동 추가 함수
    const addBenefit = () => {
      if (selectedBenefitCategory.value && selectedBenefitSubcategories.value.length) {
        selectedBenefits.value = {
          ...selectedBenefits.value,
          [selectedBenefitCategory.value]: [
            ...(selectedBenefits.value[selectedBenefitCategory.value] || []),
            ...selectedBenefitSubcategories.value,
          ],
        };
        selectedBenefitCategory.value = '';
        selectedBenefitSubcategories.value = [];
      }
    };

    // 4. 인사담당자/기업정보 폼 관련 ***************************************************************************

    // ref로 managerInfo 객체 감싸기
    const managerInfo = ref({ ...announcementStore.managerInfo });

    // managerInfo를 스토어의 변화에 따라 업데이트
    watch(
      () => announcementStore.managerInfo,
      (newValue) => {
        console.log("Manager Info Updated:", newValue);
        managerInfo.value = newValue; // Spread 연산자 제거
      },
      { immediate: true } // 컴포넌트 로드 시 바로 적용
    );

    // 데이터를 전송할 때 비공개 체크된 항목 처리
    const prepareDataForSave = () => {
      // 담당자 이름 비공개 처리
      announcementStore.formData.managerName =
        announcementStore.managerInfo.isManagerNamePrivate ? '비공개' : announcementStore.managerInfo.managerName;

      // 연락처 비공개 처리
      announcementStore.formData.managerContact =
        announcementStore.managerInfo.isPhonePrivate
          ? '비공개'
          : `${announcementStore.managerInfo.phone1}-${announcementStore.managerInfo.phone2}-${announcementStore.managerInfo.phone3}`;

      // 이메일 비공개 처리
      announcementStore.formData.managerEmail =
        announcementStore.managerInfo.isEmailPrivate ? '비공개' : announcementStore.managerInfo.managerEmail;
    };

    // 5. 채용절차 폼 관련 *************************************************************************************

    const selectedPeriod = ref('');
    // const activeProcessSteps = ref([]);
    const processSteps = ref(['서류전형', '면접전형 > 1차', '면접전형 > 2차', '최종합격']); // 전체 전형 절차 목록
    const activeProcessSteps = ref([]); // 활성화된 전형 절차 목록
    const times = ['07시', '08시', '09시', '10시', '11시', '12시', '13시', '14시', '15시', '16시', '17시', '18시', '19시', '20시', '21시', '22시', '23시', '24시'];

    // const processSteps = computed(() => {
    //   const steps = ['서류전형', '면접전형 > 1차', '최종합격'];
    //   if (announcementStore.formData.interviewCount === '2') {
    //     steps.splice(2, 0, '면접전형 > 2차');
    //   }
    //   return steps;
    // });

    // 면접 횟수가 변경되면 전형 절차 자동 설정
    const updateProcessSteps = () => {
      const interviewCount = announcementStore.formData.interviewCount;

      if (interviewCount === '1') {
        activeProcessSteps.value = ['서류전형', '면접전형 > 1차', '최종합격'];
      } else if (interviewCount === '2') {
        activeProcessSteps.value = ['서류전형', '면접전형 > 1차', '면접전형 > 2차', '최종합격'];
      } else {
        activeProcessSteps.value = []; // 초기화
      }
    };

    // 1개월2개월 버튼클릭시 마감일 자동설정 함수
    const setActiveButton = (period) => {
      selectedPeriod.value = period;

      // formData가 정의되었는지 확인
      if (!announcementStore.formData) {
        console.error('formData가 정의되지 않았습니다.');
        return;
      }

      const startDate = new Date(announcementStore.formData.startDate || new Date());
      const endDate = new Date(startDate);

      if (period === '1개월') {
        endDate.setMonth(endDate.getMonth() + 1);
      } else if (period === '2개월') {
        endDate.setMonth(endDate.getMonth() + 2);
      }
      announcementStore.formData.endDate = formatDate(endDate);
    };

    // 전형절차 버튼클릭으로 해당 단계 활성화, 비활성화
    // const toggleProcessStep = (step) => {
    //   if (activeProcessSteps.value.includes(step)) {
    //     activeProcessSteps.value = activeProcessSteps.value.filter(s => s !== step);
    //   } else {
    //     activeProcessSteps.value.push(step);
    //   }
    // };

    // 활성화된 전형 절차를 문자열로 변환하여 히든 인풋에 반영
    const formattedProcessSteps = computed(() => activeProcessSteps.value.join(', '));

    // 날짜형식변환
    const formatDate = (date) => {
      const d = new Date(date);
      const year = d.getFullYear();
      const month = (`0${d.getMonth() + 1}`).slice(-2);
      const day = (`0${d.getDate()}`).slice(-2);
      return `${year}-${month}-${day}`;
    };

    // 시간 형식 변환 함수 (18시 -> 18:00:00)
    const formatTime = (time) => {
      // 시간 부분만 추출 (e.g., "18시" -> "18")
      const hour = time.replace('시', '').padStart(2, '0');
      return `${hour}:00:00`;
    };

    // 날짜와 시간을 받아서 "YYYY-MM-DD HH:MM:SS" 형식으로 변환하는 함수
    const formatDateTime = (date, time) => {
      const formattedDate = formatDate(date); // 날짜 형식 변환
      const formattedTime = formatTime(time); // 시간 형식 변환
      return `${formattedDate} ${formattedTime}`; // "YYYY-MM-DD HH:MM:SS" 형식으로 결합
    };


    // 에러 메시지를 포함한 모달 창을 보여주는 함수
    const showErrorModal = (message) => {
      alert(`에러: ${message}`);
      // 실제로는 Vue의 Modal 컴포넌트를 사용하는 방식으로 대체 가능
      // 예: this.$bvModal.msgBoxOk(message, { title: '에러 발생' });
    };


    // 컴포넌트가 마운트될 때 실행
    onMounted(async () => {
      try {
        // addBenefit();

        // 채용 담당자 정보와 기업 복리후생 정보를 동시에 가져오기
        await announcementStore.fetchManagerInfo();
        await announcementStore.fetchCompanyBenefits(announcementStore.managerInfo.managerEmail);

        const keyword = "job"
        await baseInfoStore.fetchCategories(keyword); // 모집직무 카테고리 데이터를 백엔드에서 가져옴
        jobCategories.value = baseInfoStore.categories; // 스토어에서 불러온 데이터를 jobCategories에 할당
        // console.log(jobCategories.value);

        const today = new Date();
        announcementStore.formData.startDate = formatDate(today); // 페이지 로드 시 오늘 날짜로 초기화

      } catch (error) {
        toast.error("먼저 기업 정보를 등록 해 주세요.");
        console.log(error);
        router.push(`/recruiter/mypage`);
      }
    });

    // 모집직무 카테고리 코드 추출
    const getSelectedCategoryCodes = () => {
      // 모든 소분류 코드만 모아 쉼표로 나열된 문자열을 반환
      return Object.values(selectedCategories.value)
        .flat()
        .map(sub => sub.code)
        .join(',');
    };


    // 저장하기 전 작업
    const alreadyFun = () => {
      getSelectedJobType();  // 근무형태 체크박스 값 가져오기
      prepareDataForSave(); // 인사담당자 정보 비공개 처리
      announcementStore.formData.processSteps = formattedProcessSteps.value; // 전형절차 문자열 저장
      // startDate, startTimeRegi, endDate, endTimeRegi를 각각 묶어서 저장
      announcementStore.formData.announcementStart = formatDateTime(announcementStore.formData.startDate, announcementStore.formData.startTimeRegi);
      announcementStore.formData.announcementEnd = formatDateTime(announcementStore.formData.endDate, announcementStore.formData.endTimeRegi);

      // 모집직무 출력
      const jobCategory = getSelectedCategoryCodes();

      announcementStore.createAnnouncement(jobCategory, fields.value, fields2.value, router); // 스토어 저장 처리
    }


    return {
      announcementStore,
      authStore,

      // 입력값 기본 세팅
      fields,
      fields2,

      // 양식 선택과 이미지 업로드 관련 
      isImageUpload,
      imageUrl,
      selectFormType,
      previewImage,
      resetImage,

      // 1. 모집분야 섹션 관련
      categoryData,
      jobCategories,
      showCategoryDropdown,
      selectedCategory,
      selectedSubcategories,
      selectedCategories,
      toggleCategoryDropdown,
      addSelectedCategories,
      removeSubcategory,
      exclusiveCheckbox,
      toggleField,
      autoResize,
      getSelectedCategoryCodes,

      // 2. 지원자격/근무조건 섹션 관련
      employmentTypes,
      handleExclusiveCheckboxGroup,
      workDaysOptions,
      timeOptions,
      toggleSelectBox,
      toggleWorkTimeSelect,

      // 3. 기업 복리후생 섹션 관련
      selectedBenefitCategory,
      selectedBenefitSubcategories,
      selectedBenefits,
      addBenefit,

      // 4. 인사담당자/기업정보 섹션 관련
      managerInfo,
      prepareDataForSave,

      // 5. 채용절차 섹션 관련
      selectedPeriod,
      times,
      processSteps,
      activeProcessSteps,
      formattedProcessSteps,
      updateProcessSteps,
      setActiveButton,
      // toggleProcessStep,

      showErrorModal,
      alreadyFun,
    };
  },
};
</script>

<template>
  <MainHeaderComponent />
  <div class="container">
    <MainSideBarComponent />
    <div id="content2">
      <div class="container-regi">
        <div class="section">
          <h2>공고 기본 설정</h2>
          <div class="required-parents-div">
            <label class="required required2">공고 제목</label>
            <div class="required-child-div">
              <input v-model="announcementStore.formData.title" type="text"
                placeholder="직무명이 포함된 공고 제목을 지원자들이 선호해요. (ex. 하반기 기계조작원 신입채용)">
            </div>
          </div>
          <div class="required-parents-div">
            <label class="required required2">양식 선택</label>
            <div class="required-child-div">
              <div class="btn-group">
                <button :class="{ active: isImageUpload }" @click="selectFormType(true)">공고 이미지 업로드</button>
                <button :class="{ active: !isImageUpload }" @click="selectFormType(false)">공고 템플릿 작성</button>
              </div>
            </div>
          </div>
          <p style="font-size: large;">📢 이미지 업로드 양식 - 이미지를 업로드 하고 템플릿 작성 양식으로 이동하여 *필수*값을 꼭 입력한 후 등록해 주세요. <br>
            📢 템플릿 작성 양식 - 템플릿의 정보를 모두 작성 후 등록해 주세요. </p>
        </div>

        <hr style="border: 0.5px solid #abadb8; margin: 30px 0;">

        <!-- 이미지 업로드 섹션 -->
        <div v-if="isImageUpload" id="imageUpload" style="text-align: left; background-color: #f1f1f1;">
          <h3>이미지 업로드</h3>
          <div style="text-align: left;">
            <!-- 커스텀 파일 업로드 버튼 -->
            <label for="fileInput" class="custom-file-upload"
              style="background-color: #212b36; color: #fff; padding: 10px 10px 10px 35px; border-radius: 5px; cursor: pointer; display: inline-block;">
              파일 선택
            </label>
            <input type="file" id="fileInput" @change="previewImage" accept="image/*" style="display: none;" />
            <!-- <p v-if="fileName">{{ fileName }}</p> -->
            <div id="imagePreviewContainer"
              style="width: 100%; height: 100%; display: flex; justify-content: center; align-items: center; flex-direction: column; background-color: #fff;">
              <!-- 이미지 초기화 버튼 -->
              <button @click="resetImage" v-if="imageUrl"
                style="margin: 10px 0; padding: 10px 10px; border: 1px solid #abadb8; border-radius: 5px; background-color: white;">이미지
                초기화</button>
              <img v-if="imageUrl" :src="imageUrl" id="imagePreview"
                style="width: 100%; height: 100%; object-fit: contain;">
              <p v-else id="noImageText" style="color: #777;">하나의 파일로 된 이미지를 선택하세요.</p>
            </div>
          </div>
        </div>

        <!-- 템플릿 작성 폼 섹션 -->
        <div v-else id="formSections">

          <!-- 1. 모집분야 -->
          <div class="section" id="recruitmentField">
            <h2>모집분야</h2>

            <!-- 직무 카테고리 -->
            <div class="required-parents-div">
              <label class="required required2">직무 카테고리</label>
              <div class="required-child-div">
                <div class="category-group">
                  <div class="category-box">
                    <!-- 선택된 카테고리 표시 -->
                    <div v-for="(subcategories, category) in selectedCategories" :key="category">
                      {{ category }} >
                      <span v-for="sub in subcategories" :key="sub.code">
                        {{ sub.description }} <button @click="removeSubcategory(category, sub)">×</button>
                      </span>
                    </div>
                  </div>
                  <button @click="toggleCategoryDropdown" class="select-category">➕ 선택</button>
                </div>

                <!-- 대분류 선택 드롭다운 -->
                <div v-if="showCategoryDropdown" class="dropdown">
                  <div class="dropdown-container" style="margin-bottom: 20px;">
                    <!-- 대분류 선택 -->
                    <select v-model="selectedCategory">
                      <option value="">대분류 선택</option>
                      <option v-for="category in jobCategories" :key="category.code" :value="category.code">
                        {{ category.description }}
                      </option>
                    </select>

                    <!-- 소분류 선택 (대분류 선택 시 표시됨) -->
                    <select v-if="selectedCategory" v-model="selectedSubcategories" multiple>
                      <option
                        v-for="sub in (jobCategories.find(cat => cat.code === selectedCategory)?.subcategories || [])"
                        :key="sub.code" :value="sub.code">
                        {{ sub.description }}
                      </option>
                    </select>

                    <!-- 확인 버튼 -->
                    <button @click="addSelectedCategories" v-if="selectedCategory && selectedSubcategories.length > 0"
                      class="select-category" style="height: 30px; margin-top: 33px">
                      ➕ 추가
                    </button>
                  </div>
                </div>

              </div>
            </div>

            <!-- 모집분야명 -->
            <div class="required-parents-div">
              <label class="required required2">모집분야명</label>
              <div class="required-child-div">
                <input type="text" v-model="announcementStore.formData.recruitmentFieldName"
                  placeholder="oo시스템 IT 교육(부트캠프) 운영 매니저 채용" />
              </div>
            </div>

            <!-- 모집인원 -->
            <div class="required-parents-div">
              <label class="required required2">모집인원</label>
              <div class="required-child-div">
                <input type="number" v-model.number="announcementStore.formData.numberOfRecruit" min="0"
                  style="width: 100px; padding: 10px;" /> 명 모집
              </div>
            </div>

            <!-- 경력 -->
            <div class="required-parents-div">
              <label class="required required2">경력</label>
              <div class="required-child-div">
                <input type="checkbox" id="newbie" v-model="announcementStore.formData.isNewbie"
                  @change="exclusiveCheckbox('isNewbie', 'isExperienced')" /> 신입
                <input type="checkbox" id="experienced" v-model="announcementStore.formData.isExperienced"
                  style="margin-left: 20px;" @change="exclusiveCheckbox('isExperienced', 'isNewbie')" /> 경력
              </div>
            </div>

            <!-- 항목 추가 -->
            <div class="required-parents-div">
              <label>항목 추가</label>
              <div class="required-child-div">
                <div class="btn-group">
                  <button @click="toggleField('mainDuty')" :class="{ active: fields.mainDuty }">주요업무</button>
                  <button @click="toggleField('department')" :class="{ active: fields.department }">근무부서</button>
                  <button @click="toggleField('position')" :class="{ active: fields.position }">직급직책</button>
                  <button @click="toggleField('requirement')" :class="{ active: fields.requirement }">필수/우대조건</button>
                </div>
              </div>
            </div>

            <!-- 추가된 입력란 -->
            <div id="dynamicFields">
              <div v-if="fields.mainDuty" class="required-parents-div">
                <label></label>
                <div class="required-child-div">
                  <textarea type="text" style="width: 800px; font-size: 16px;" @input="autoResize($event)"
                    v-model="announcementStore.formData.mainDuty" placeholder="주요 업무 입력" name="mainDuty"></textarea>
                </div>
              </div>
              <div v-if="fields.department" class="required-parents-div">
                <label></label>
                <div class="required-child-div">
                  <textarea type="text" style="width: 800px; font-size: 16px;" @input="autoResize($event)"
                    v-model="announcementStore.formData.department" placeholder="근무 부서 입력" name="department"></textarea>
                </div>
              </div>
              <div v-if="fields.position" class="required-parents-div">
                <label></label>
                <div class="required-child-div">
                  <textarea type="text" style="width: 800px; font-size: 16px;" @input="autoResize($event)"
                    v-model="announcementStore.formData.position" placeholder="직급 직책 입력" name="position"></textarea>
                </div>
              </div>
              <div v-if="fields.requirement" class="required-parents-div">
                <label></label>
                <div class="required-child-div">
                  <textarea type="text" style="width: 800px; font-size: 16px;" @input="autoResize($event)"
                    v-model="announcementStore.formData.requirement" placeholder="필수/우대조건 입력"
                    name="requirement"></textarea>
                </div>
              </div>
            </div>
          </div>

          <hr style="border: 0.5px solid #abadb8; margin: 30px 0;">

          <!-- 2. 지원자격/근무조건 -->
          <div class="section" id="qualifications">
            <h2>지원자격/근무조건</h2>

            <!-- 근무지역 -->
            <div class="required-parents-div">
              <label class="required required2">근무지역</label>
              <div class="required-child-div">
                <input type="checkbox" v-model="announcementStore.formData.isOverseas" /> 해외지역
                <input type="checkbox" v-model="announcementStore.formData.isReworkPossible"
                  style="margin-left: 20px;" /> 재택근무 가능
                <input type="text" v-model="announcementStore.formData.workLocationCity" placeholder="서울시 동작구 보라매로"
                  style="width: 30%; margin-left: 20px; margin-right: 10px;" />
                <input type="text" v-model="announcementStore.formData.workLocationDetail" placeholder="87 플레이데이터 동작캠퍼스"
                  style="width: 30%;" />
              </div>
            </div>

            <!-- 근무형태 -->
            <div class="required-parents-div">
              <label class="required">근무형태</label>
              <div class="required-child-div">
                <input type="checkbox" v-model="employmentTypes.fullTime"
                  @change="handleExclusiveCheckboxGroup('fullTime')" /> 정규직
                <input type="checkbox" v-model="employmentTypes.contract"
                  @change="handleExclusiveCheckboxGroup('contract')" style="margin-left: 20px;" /> 계약직
                <input type="checkbox" v-model="employmentTypes.partTime"
                  @change="handleExclusiveCheckboxGroup('partTime')" style="margin-left: 20px;" /> 아르바이트
                <input type="checkbox" v-model="employmentTypes.intern" @change="handleExclusiveCheckboxGroup('intern')"
                  style="margin-left: 20px;" /> 인턴직
                <input type="checkbox" v-model="employmentTypes.freelancer"
                  @change="handleExclusiveCheckboxGroup('freelancer')" style="margin-left: 20px;" /> 프리랜서
                <input type="checkbox" v-model="employmentTypes.part" @change="handleExclusiveCheckboxGroup('part')"
                  style="margin-left: 20px;" /> 파트
              </div>
            </div>

            <!-- 급여 -->
            <div class="required-parents-div">
              <label class="required">급여</label>
              <div class="required-child-div">
                <select v-model="announcementStore.formData.salaryType" style="padding: 10px; margin-right: 10px;">
                  <option value="연봉">연봉</option>
                  <option value="월급">월급</option>
                  <option value="주급">주급</option>
                  <option value="일급">일급</option>
                  <option value="시급">시급</option>
                  <option value="건당">건당</option>
                </select>
                <input type="number" v-model="announcementStore.formData.salaryAmount" min="0"
                  style="width: 100px; padding: 10px;" /> 만원
              </div>
            </div>

            <!-- 근무시간 -->
            <div class="required-parents-div">
              <label class="required">근무시간</label>
              <div class="required-child-div">
                <input type="number" v-model="announcementStore.formData.workHoursPerWeek" min="0"
                  style="width: 50px; margin-right: 5px;" /> 시간 / 주
                <input type="checkbox" v-model="announcementStore.formData.isNegotiable" style="margin-left: 20px;" />
                면접 후 결정
              </div>
            </div>

            <!-- 항목 추가 -->
            <div class="required-parents-div">
              <label>항목 추가</label>
              <div class="required-child-div">
                <div class="btn-group">
                  <button @click="toggleSelectBox('workDays')" :class="{ active: fields2.workDays }">근무요일</button>
                  <button @click="toggleSelectBox('workTime')" :class="{ active: fields2.workTime }">출퇴근 시간</button>
                </div>
              </div>
            </div>

            <!-- 추가된 입력란이 여기에 동적으로 나타날 것 -->
            <div id="dynamicFields2">
              <div v-if="fields2.workDays" class="required-parents-div">
                <label>근무 요일 선택</label>
                <div class="required-child-div">
                  <select v-model="announcementStore.formData.workDays" style="width: 200px; padding: 10px;">
                    <option v-for="option in workDaysOptions" :key="option" :value="option">{{ option }}</option>
                  </select>
                </div>
              </div>

              <div v-if="fields2.workTime" class="required-parents-div">
                <label>출퇴근 시간</label>
                <div class="required-child-div">
                  <select v-model="announcementStore.formData.startTime"
                    style="width: 200px; padding: 10px; margin-right: 15px;">
                    <option v-for="time in timeOptions" :key="time" :value="time">{{ time }}</option>
                  </select>
                  <select v-model="announcementStore.formData.endTime" style="width: 200px; padding: 10px;">
                    <option v-for="time in timeOptions" :key="time" :value="time">{{ time }}</option>
                  </select>
                </div>
              </div>
            </div>
          </div>

          <hr style="border: 0.5px solid #abadb8; margin: 30px 0;">

          <!-- 3. 기업 복리후생 -->
          <div class="section" id="benefits">
            <h2>기업 복리후생</h2>

            <!-- 복리후생 박스 -->
            <div class="benefits-box">
              <!-- 복리후생 대분류 및 소분류 표시 -->
              <div v-for="(benefit, index) in announcementStore.companyBenefits" :key="index"
                style="margin-bottom: 10px;">
                <strong class="category-label">{{ benefit.category }} > </strong>
                <span v-for="(subcategory, subIndex) in benefit.subcategories" :key="subIndex"
                  class="subcategory-label">
                  {{ subcategory }}
                </span>
              </div>
            </div>

            <!-- 추가 복지&혜택 -->
            <div class="required-parents-div">
              <label class="required">추가 복지혜택</label>
              <div class="required-child-div">
                <textarea v-model="announcementStore.formData.additionalBenefits"
                  placeholder="지원자에게 추가로 어필하고 싶은 우리 팀의 근무환경, 복지 등 혜택을 작성 해 주세요."
                  style="width: 100%; height: 150px;"></textarea>
              </div>
            </div>
          </div>

          <hr style="border: 0.5px solid #abadb8; margin: 30px 0;">

          <!-- 4. 인사담당자/기업정보 -->
          <div class="section" id="companyInfo">
            <h2>인사담당자/기업정보</h2>

            <!-- 담당자명 -->
            <div class="required-parents-div">
              <label class="required">담당자명</label>
              <div class="required-child-div">
                <input type="text" v-model="announcementStore.managerInfo.managerName" placeholder="ooo"
                  style="width: 40%;" />
                <label>
                  <input type="checkbox" v-model="announcementStore.managerInfo.isManagerNamePrivate" /> 비공개
                </label>
              </div>
            </div>

            <!-- 연락처 -->
            <div class="required-parents-div">
              <label class="required">연락처</label>
              <div class="required-child-div">
                <input type="text" v-model="announcementStore.managerInfo.phone1" placeholder="010"
                  style="width: 10%; margin-right: 10px;" />
                <input type="text" v-model="announcementStore.managerInfo.phone2" placeholder="1234"
                  style="width: 10%; margin-right: 10px;" />
                <input type="text" v-model="announcementStore.managerInfo.phone3" placeholder="5678"
                  style="width: 10%; margin-right: 3px;" />
                <label>
                  <input type="checkbox" v-model="announcementStore.managerInfo.isPhonePrivate" /> 비공개
                </label>
              </div>
            </div>

            <!-- 이메일 -->
            <div class="required-parents-div">
              <label class="required">이메일</label>
              <div class="required-child-div">
                <input type="email" v-model="announcementStore.managerInfo.managerEmail" placeholder="ex@ooo.com"
                  style="width: 40%;" />
                <label>
                  <input type="checkbox" v-model="announcementStore.managerInfo.isEmailPrivate" /> 비공개
                </label>
              </div>
            </div>

            <!-- 회사소개 -->
            <div class="required-parents-div">
              <label class="required">회사소개</label>
              <div class="required-child-div">
                <textarea v-model="announcementStore.formData.companyIntro" placeholder="회사 소개를 작성 해 주세요."
                  style="width: 100%; height: 150px;"></textarea>
              </div>
            </div>
          </div>

          <hr style="border: 0.5px solid #abadb8; margin: 30px 0;">

          <!-- 5. 채용절차 -->
          <div class="section" id="hiringProcess">
            <h2>채용절차</h2>

            <!-- 지원 접수 기간 -->
            <div class="required-parents-div">
              <label class="required required2">지원 접수 기간</label>
              <div class="required-child-div"
                style="display: flex; justify-content: left; flex-direction: column; align-items:start">
                <div class="btn-group" style="margin-bottom: 20px;">
                  <button :class="{ active: selectedPeriod === '1개월' }" @click="setActiveButton('1개월')">1개월</button>
                  <button :class="{ active: selectedPeriod === '2개월' }" @click="setActiveButton('2개월')">2개월</button>
                </div>
                <div>
                  <input type="date" v-model="announcementStore.formData.startDate" />
                  <select style="margin-right: 15px;" v-model="announcementStore.formData.startTimeRegi">
                    <option v-for="time in times" :key="time" :value="time">{{ time }}</option>
                  </select>
                  <input type="date" v-model="announcementStore.formData.endDate" />
                  <select style="margin-right: 15px;" v-model="announcementStore.formData.endTimeRegi">
                    <option v-for="time in times" :key="time" :value="time">{{ time }}</option>
                  </select>
                  <!-- <select v-model="announcementStore.formData.recruitmentType">
                    <option value="마감일지정">마감일지정</option>
                    <option value="상시모집">상시모집</option>
                  </select> -->
                </div>
              </div>
            </div>

            <!-- 면접 횟수 -->
            <div class="required-parents-div">
              <label class="required required2">면접 횟수</label>
              <div class="required-child-div">
                <select v-model="announcementStore.formData.interviewCount" @change="updateProcessSteps">
                  <option value="선택해 주세요">선택해 주세요</option>
                  <option value="1">1</option>
                  <option value="2">2</option>
                </select>
              </div>
            </div>

            <!-- 전형 절차 -->
            <div class="required-parents-div">
              <label>전형절차</label>
              <div class="required-child-div">
                <div class="btn-group">
                  <!-- @click="toggleProcessStep(step)" -->
                  <button v-for="step in processSteps" :key="step"
                    :class="{ active: activeProcessSteps.includes(step) }">
                    {{ step }}
                  </button>
                </div>
              </div>
            </div>

            <!-- 백엔드로 보낼 hidden input -->
            <input type="hidden" :value="formattedProcessSteps" name="processSteps" />
          </div>

          <hr style="border: 0.5px solid #abadb8; margin: 30px 0;">

          <!-- 6. 유의사항 -->
          <div class="section" id="precautions">
            <h2>유의사항</h2>

            <!-- 유의사항 -->
            <div class="required-parents-div">
              <label class="required">유의사항</label>
              <div class="required-child-div">
                <textarea v-model="announcementStore.formData.precautions" placeholder="유의사항을 작성 해 주세요."
                  style="width: 100%; height: 150px;"></textarea>
              </div>
            </div>
          </div>

        </div>

        <!-- 다음 스텝 버튼 -->
        <div class="submit-section">
          <p style="font-size: large;">📢 이미지 업로드 시, 템플릿 작성 양식으로 이동하여 *필수*값을 꼭 입력한 후 등록해 주세요.</p>
          <button @click="alreadyFun" id="buildFormBtn">공고 등록 후 지원서 폼 조립하기</button>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  font-family: Arial, sans-serif;
  background-color: white;
  /* background-color: #f8f9fa;  */
  margin: 0;
  width: 100%;
}

#content2 {
  margin: 0 0 0 200px;
  padding: 150px 0;
  width: 80%;
}


/* 추가된 css */
input[type="number"] {
  /* width: calc(100% - 40px); */
  padding: 20px 5px;
  width: 200px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #fff;
  background-size: 15px;
  font-size: 16px;
}

#imageUpload {
  background-color: #f5f8ff;
  border: 1px solid #ccc;
  padding: 20px;
  text-align: center;
}

#imagePreviewContainer {
  margin-top: 20px;
  border: 1px solid #ccc;
  padding: 10px;
  background-color: #fff;
  min-height: 150px;
  display: flex;
  justify-content: center;
  align-items: center;
}

#imagePreview {
  /* display: none; */
  max-width: 100%;
  max-height: 100%;
}

#noImageText {
  color: #777;
}

.container-regi {
  width: 80%;
  margin: 0px 50px 40px 158px;
  background-color: white;
  padding: 20px;
  /* border-radius: 10px; */
  /* box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); */
}

.section {
  margin: 60px 0;
}

.required-parents-div {
  display: flex;
  align-items: center;
  margin-bottom: 30px;
}

.required-child-div {
  display: flex;
  align-items: center;
  width: 100%;
  flex-wrap: wrap;
  /* 항목들이 화면에 맞춰 줄바꿈되도록 수정 */
}

.required-child-div input[type="date"] {
  padding: 10px;
  margin-right: 10px;
  margin-bottom: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.required-child-div select {
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

a {
  color: #3a3f51;
  /* 링크 색상 */
  text-decoration: none;
  margin-left: 10px;
}

a:hover {
  text-decoration: underline;
  /* 링크 호버 스타일 */
}

h2 {
  font-size: 24px;
  margin: 40px 0;
}

label {
  font-size: 14px;
  font-weight: bold;
  margin: 5px 10px 5px 5px;
  width: 130px;
  display: block;
}

input[type="text"] {
  width: 100%;
  padding: 15px;
  /* margin-bottom: 20px; */
  border: 1px solid #ccc;
  border-radius: 5px;
}

input[type="email"] {
  width: 100%;
  padding: 15px;
  /* margin-bottom: 20px; */
  border: 1px solid #ccc;
  border-radius: 5px;
}

/* .required:after {
  content: "*";
  color: red;
} */

.required2:after {
  content: "*";
  color: red;
}

/* 직무 카테고리 박스 */
.category-group {
  display: flex;
  align-items: center;
  width: 100%;
}

.category-box {
  width: 80%;
  min-height: 50px;
  margin-right: 10px;
  border: 1px solid #ccc;
  padding: 10px;
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  background-color: #f9f9f9;
  border-radius: 5px;
}

.category-item {
  background-color: #e0e7ff;
  border-radius: 15px;
  padding: 5px 10px;
  display: inline-flex;
  align-items: center;
  gap: 5px;
  cursor: pointer;
}

.category-item .close-btn {
  background-color: transparent;
  border: none;
  font-size: 12px;
  color: #555;
  cursor: pointer;
}

.select-category {
  margin-left: 10px;
  background-color: #212b36;
  border: 1px solid #ccc;
  color: white;
  padding: 5px 10px;
  cursor: pointer;
  border-radius: 5px;
}

/* 드롭다운 */
.dropdown {
  margin-top: 10px;
}

/* 대분류와 소분류 드롭다운을 나란히 배치 */
.dropdown-container {
  display: flex;
  gap: 10px;
  /* 드롭다운 사이에 간격 추가 */
}

#categorySelect,
#subcategorySelect {
  width: 200px;
  /* 드롭다운의 너비를 동일하게 설정 */
  padding: 10px;
}

#categorySelect {
  /* width: 100%; */
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  background-color: #fff;
}

.btn-group {
  display: flex;
  /* margin-bottom: 20px; */
}

.btn-group button {
  flex: 1;
  width: 200px;
  padding: 12px 25px;
  border: 1px solid #ccc;
  cursor: pointer;
  background-color: white;
  color: #333;
  font-weight: bold;
  border-right: none;
}

.btn-group button:last-child {
  border-right: 1px solid #ccc;
}

.btn-group button:first-child {
  border-top-left-radius: 5px;
  border-bottom-left-radius: 5px;
}

.btn-group button:last-child {
  border-top-right-radius: 5px;
  border-bottom-right-radius: 5px;
}

button.active {
  background-color: #212b36;
  /* Navy color for active state */
  color: white;
  border-color: #3a3f51;
}

button.inactive {
  background-color: white;
  color: black;
  cursor: pointer;
}

#imageUpload {
  background-color: #f1f1f1;
  /* border: 1px solid #ccc; */
  padding: 40px;
  text-align: center;
  /* display: none; */
}

#formSections {
  /* display: none; */
  margin: 50px 0;
}

.benefits-box {
  /* border: 1px solid #f5f8ff; */
  /* background-color: #f5f8ff; */
  padding: 20px;
  border-radius: 5px;
  margin-bottom: 50px;
}

/* 버튼 컨테이너 스타일 */
.submit-section {
  text-align: center;
  margin-top: 30px;
}

/* 버튼 스타일 */
#buildFormBtn {
  background-color: #212b36;
  color: white;
  font-size: 16px;
  font-weight: bold;
  padding: 12px 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s ease;
}

/* 버튼 호버 스타일 */
#buildFormBtn:hover {
  background-color: #37404a;
}

.category-label {
  border: 1px solid #ccc;
  padding: 5px 10px;
  border-radius: 15px;
  margin: 5px;
  background-color: #212b36;
  color: white;
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