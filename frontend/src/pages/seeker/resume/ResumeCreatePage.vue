<template>
  <SeekerHeaderComponent/>
  <div class="main-container pt-150">
    <div class="content">
      <!-- 인적사항 -->
      <div v-if="showPersonalInfo" id="personal-info" class="add-container">
        <h2>인적사항</h2>
        <PersonalInfoComponent @updateData="handlePersonalInfoUpdate" />
      </div>

      <!-- 학력 -->
      <div v-if="showEducation" id="education" class="add-container">
        <h2>학력</h2>
        <div v-for="(activity, index) in educationActivities" :key="activity.id" class="activity-wrapper">
          <EducationComponent @updateData="handleEducationUpdate(index, $event)" class="relative-component" />
          <button v-if="educationActivities.length > 1" @click="removeActivity(index, 'resume_001')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_001')" class="form-actions">+ 추가</div>
      </div>

      <!-- 경력 -->
      <div v-if="showPersonalHistory" id="personal-history" class="add-container">
        <h2>경력</h2>
        <div v-for="(activity, index) in personalHistoryActivities" :key="activity.id" class="activity-wrapper">
          <PersonalHistoryComponent @updateData="handlePersonalHistoryUpdate(index, $event)" class="relative-component" />
          <button v-if="personalHistoryActivities.length > 1" @click="removeActivity(index, 'resume_002')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_002')" class="form-actions">+ 추가</div>
      </div>

      <!-- 인턴·대외활동 -->
      <div v-if="showInternsActivity" id="interns-activity" class="add-container">
        <h2>인턴·대외활동</h2>
        <div v-for="(activity, index) in internsActivities" :key="activity.id" class="activity-wrapper">
          <InternsActivityComponent @updateData="handleInternsActivityUpdate(index, $event)" class="relative-component" />
          <button v-if="internsActivities.length > 1" @click="removeActivity(index, 'resume_003')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_003')" class="form-actions">+ 추가</div>
      </div>

      <!-- 교육이수 -->
      <div v-if="showTraining" id="training" class="add-container">
        <h2>교육이수</h2>
        <div v-for="(activity, index) in trainingActivities" :key="activity.id" class="activity-wrapper">
          <TrainingComponent @updateData="handleTrainingUpdate(index, $event)" class="relative-component" />
          <button v-if="trainingActivities.length > 1" @click="removeActivity(index, 'resume_004')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_004')" class="form-actions">+ 추가</div>
      </div>

      <!-- 자격증 -->
      <div v-if="showCertification" id="certification" class="add-container">
        <h2>자격증</h2>
        <div v-for="(activity, index) in certificationActivities" :key="activity.id" class="activity-wrapper">
          <CertificationComponent @updateData="handleCertificationUpdate(index, $event)" class="relative-component" />
          <button v-if="certificationActivities.length > 1" @click="removeActivity(index, 'resume_005')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_005')" class="form-actions">+ 추가</div>
      </div>
      
      <!-- 수상 -->
      <div v-if="showAward" id="award" class="add-container">
        <h2>수상</h2>
        <div v-for="(activity, index) in awardActivities" :key="activity.id" class="activity-wrapper">
          <AwardComponent @updateData="handleAwardUpdate(index, $event)" class="relative-component" />
          <button v-if="awardActivities.length > 1" @click="removeActivity(index, 'resume_006')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_006')" class="form-actions">+ 추가</div>
      </div>

      <!-- 해외경험 -->
      <div v-if="showStudyingAbroad" id="studying-abroad" class="add-container">
        <h2>해외경험</h2>
        <div v-for="(activity, index) in studyingAbroadActivities" :key="activity.id" class="activity-wrapper">
          <StudyingAbroadComponent @updateData="handleStudyingAboardUpdate(index, $event)" class="relative-component" />
          <button v-if="studyingAbroadActivities.length > 1" @click="removeActivity(index, 'resume_007')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_007')" class="form-actions">+ 추가</div>
      </div>

      <!-- 어학 -->
      <div v-if="showLanguage" id="language" class="add-container">
        <h2>어학</h2>
        <div v-for="(activity, index) in languageActivities" :key="activity.id" class="activity-wrapper">
          <LanguageComponent @updateData="handleLanguageUpdate(index, $event)" class="relative-component" />
          <button v-if="languageActivities.length > 1" @click="removeActivity(index, 'resume_008')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_008')" class="form-actions">+ 추가</div>
      </div>

      <!-- 포트폴리오 -->
      <div v-if="showPortfolio" id="portfolio" class="add-container">
        <h2>포트폴리오</h2>
        <div v-for="(activity, index) in portfolioActivities" :key="activity.id" class="activity-wrapper">
          <PortfolioComponent @updateData="handlePortfolioUpdate(index, $event)" class="relative-component" />
          <button v-if="portfolioActivities.length > 1" @click="removeActivity(index, 'resume_009')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_009')" class="form-actions">+ 추가</div>
      </div>

      <div v-if="showPreferentialEmp" id="preferential-emp" class="add-container">
        <h2>취업우대·병역</h2>
        <PreferentialEmpComponent @updateData="handlePreferentialEmpUpdate" />
      </div>

      <!-- 자기소개서 -->
      <div v-if="showCustomLetter" id="customLetter" class="add-container">
        <h2>자기소개서</h2>
        <div v-for="(activity, index) in customLetterActivities" :key="activity.id" class="activity-wrapper">
          <CustomLetterComponent @updateData="handleCustomLetterUpdate(index, $event)" class="relative-component" />
          <button v-if="customLetterActivities.length > 1" @click="removeActivity(index, 'resume_011')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_011')" class="form-actions">+ 추가</div>
      </div>

    </div>

    

    <!-- 사이드바 -->
    <div class="resume-sidebar">
      <ul>
        <li>
          <span class="icon personal-info-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('personal-info')">인적사항</span>
        </li>
        <li>
          <span class="icon education-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('education')">학력</span>
          <button @click="confirmAndToggle('resume_001')" :class="toggleBtnClass('resume_001')"></button>
        </li>
        <li>
          <span class="icon personal-history-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('personal-history')">경력</span>
          <button @click="confirmAndToggle('resume_002')" :class="toggleBtnClass('resume_002')"></button>
        </li>
        <li>
          <span class="icon interns-activity-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('interns-activity')">인턴·대외활동</span>
          <button @click="confirmAndToggle('resume_003')" :class="toggleBtnClass('resume_003')"></button>
        </li>
        <li>
          <span class="icon education-completion-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('training')">교육이수</span>
          <button @click="confirmAndToggle('resume_004')" :class="toggleBtnClass('resume_004')"></button>
        </li>
        <li>
          <span class="icon certification-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('certification')">자격증</span>
          <button @click="confirmAndToggle('resume_005')" :class="toggleBtnClass('resume_005')"></button>
        </li>
        <li>
          <span class="icon award-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('award')">수상</span>
          <button @click="confirmAndToggle('resume_006')" :class="toggleBtnClass('resume_006')"></button>
        </li>
        <li>
          <span class="icon studying-abroad-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('studying-abroad')">해외경험</span>
          <button @click="confirmAndToggle('resume_007')" :class="toggleBtnClass('resume_007')"></button>
        </li>
        <li>
          <span class="icon language-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('language')">어학</span>
          <button @click="confirmAndToggle('resume_008')" :class="toggleBtnClass('resume_008')"></button>
        </li>
        <li>
          <span class="icon portfolio-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('portfolio')">포트폴리오</span>
          <button @click="confirmAndToggle('resume_009')" :class="toggleBtnClass('resume_009')"></button>
        </li>
        <li>
          <span class="icon preferential-employment-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('preferential-emp')">취업우대·병역</span>
          <button @click="confirmAndToggle('resume_010')" :class="toggleBtnClass('resume_010')"></button>
        </li>
        <li>
          <span class="icon cover-letter-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('customLetter')">자기소개서</span>
          <button @click="confirmAndToggle('resume_011')" :class="toggleBtnClass('resume_011')"></button>
        </li>
      </ul>
      <div class="sidebar-bottom-btns">
        <button class="save-btn" @click="handleCreate">등록하기</button>
      </div>
    </div>
  </div>
</template>


<script setup>
import { ref, onMounted  } from 'vue';
import { UseResumeStore } from '@/stores/UseResumeStore';
import { useRouter } from 'vue-router';
import { useToast } from 'vue-toastification';

const router = useRouter();
const toast = useToast();

import SeekerHeaderComponent from '@/components/seeker/SeekerHeaderComponent.vue' // 헤더
import PersonalInfoComponent from '@/components/seeker/PersonalInfoComponent.vue'; // 인적사항
import EducationComponent from '@/components/seeker/EducationComponent.vue'; // 학력
import PersonalHistoryComponent from '@/components/seeker/PersonalHistoryComponent.vue'; // 경력
import InternsActivityComponent from '@/components/seeker/InternsActivityComponent.vue'; // 인턴·대외활동
import TrainingComponent from '@/components/seeker/TrainingComponent.vue'; // 교육이수
import CertificationComponent from '@/components/seeker/CertificationComponent.vue'; // 자격증
import AwardComponent from '@/components/seeker/AwardComponent.vue'; // 수상
import StudyingAbroadComponent from '@/components/seeker/StudyingAbroadComponent.vue'; // 해외경험
import LanguageComponent from '@/components/seeker/LanguageComponent.vue';// 어학
import PortfolioComponent from '@/components/seeker/PortfolioComponent.vue';// 포트폴리오
import PreferentialEmpComponent from '@/components/seeker/PreferentialEmpComponent.vue'; // 취업우대·병역
import CustomLetterComponent from '@/components/seeker/CustomLetterComponent.vue';// 자기소개서

const showPersonalInfo = ref(true);
const showEducation = ref(false);
const showPersonalHistory = ref(false);
const showPreferentialEmp = ref(false);
const showInternsActivity = ref(false);
const showTraining = ref(false);
const showCertification = ref(false);
const showStudyingAbroad = ref(false);
const showLanguage = ref(false);
const showPortfolio = ref(false);
const showAward = ref(false);
const showCustomLetter = ref(false);

const initializeButtonStates = () => {
  toggleBtnState.value['resume_001'] = showEducation.value;
  toggleBtnState.value['resume_002'] = showPersonalHistory.value;
  toggleBtnState.value['resume_003'] = showInternsActivity.value;
  toggleBtnState.value['resume_004'] = showTraining.value;
  toggleBtnState.value['resume_005'] = showCertification.value;
  toggleBtnState.value['resume_006'] = showAward.value;
  toggleBtnState.value['resume_007'] = showStudyingAbroad.value;
  toggleBtnState.value['resume_008'] = showLanguage.value;
  toggleBtnState.value['resume_009'] = showPortfolio.value;
  toggleBtnState.value['resume_010'] = showPreferentialEmp.value;
  toggleBtnState.value['resume_011'] = showCustomLetter.value;
};

// 컴포넌트가 마운트될 때 초기화
onMounted(() => {
  initializeButtonStates();
});


const educationActivities = ref([{}]);
const personalHistoryActivities = ref([{}]);
const internsActivities = ref([{}]);
const trainingActivities = ref([{}]);
const certificationActivities = ref([{}]);
const awardActivities = ref([{}]);
const studyingAbroadActivities = ref([{}]);
const languageActivities = ref([{}]);
const portfolioActivities = ref([{}]);
const customLetterActivities = ref([{}]);


const scrollToSection = (sectionId) => {
  const element = document.getElementById(sectionId);
  if (element) {
    const headerOffset = 100; // 고정된 헤더의 높이 (예: 100px)
    const elementPosition = element.getBoundingClientRect().top + window.pageYOffset;
    const offsetPosition = elementPosition - headerOffset;

    window.scrollTo({
      top: offsetPosition,
      behavior: 'smooth'
    });
  }
};

const toggleBtnState = ref({}); // 사이드바 버튼 상태를 관리할 변수

// 토글 버튼의 클래스를 결정하는 함수
const toggleBtnClass = (section) => {
  return toggleBtnState.value[section] ? 'toggle-btn toggle-minus' : 'toggle-btn toggle-plus';
};

const confirmAndToggle = (section) => {
  const isMinus = toggleBtnClass(section) === 'toggle-btn toggle-minus';

  if (isMinus) {
    const result = window.confirm('정말로 삭제하시겠습니까?');
    if (result) {
      toggle(section);
    }
  } else {
    toggle(section);
  }
};

// 섹션별로 토글 상태를 관리하는 함수
const toggle = (itemName) => {
  toggleBtnState.value[itemName] = !toggleBtnState.value[itemName];

  if (itemName === 'resume_001') showEducation.value = !showEducation.value;
  else if (itemName === 'resume_002') showPersonalHistory.value = !showPersonalHistory.value;
  else if (itemName === 'resume_003') showInternsActivity.value = !showInternsActivity.value;
  else if (itemName === 'resume_004') showTraining.value = !showTraining.value;
  else if (itemName === 'resume_005') showCertification.value = !showCertification.value;
  else if (itemName === 'resume_006') showAward.value = !showAward.value;
  else if (itemName === 'resume_007') showStudyingAbroad.value = !showStudyingAbroad.value;
  else if (itemName === 'resume_008') showLanguage.value = !showLanguage.value;
  else if (itemName === 'resume_009') showPortfolio.value = !showPortfolio.value;
  else if (itemName === 'resume_010') showPreferentialEmp.value = !showPreferentialEmp.value;
  else if (itemName === 'resume_011') showCustomLetter.value = !showCustomLetter.value;

};

const resumeStore = UseResumeStore();


const addActivity = (section) => {
  if (section === 'resume_001') {
    educationActivities.value.push({
      id: Date.now()
    });
    resumeStore.addItem('educations', {
      id: Date.now()
    });
  } else if (section === 'resume_002') {
    personalHistoryActivities.value.push({
      id: Date.now()
    });
    resumeStore.addItem('personalHistories', {
      id: Date.now()
    });
  } else if (section === 'resume_003') {
    internsActivities.value.push({
      id: Date.now()
    });
    resumeStore.addItem('internsActivities', {
      id: Date.now()
    });
  } else if (section === 'resume_004') {
    trainingActivities.value.push({
      id: Date.now()
    });
    resumeStore.addItem('trainings', {
      id: Date.now()
    });
  } else if (section === 'resume_005') {
    certificationActivities.value.push({
      id: Date.now()
    });
    resumeStore.addItem('certifications', {
      id: Date.now()
    });
  } else if (section === 'resume_006') {
    awardActivities.value.push({
      id: Date.now()
    });
    resumeStore.addItem('awards', {
      id: Date.now()
    });
  } else if (section === 'resume_007') {
    studyingAbroadActivities.value.push({
      id: Date.now()
    });
    resumeStore.addItem('studyingAbroads', {
      id: Date.now()
    });
  } else if (section === 'resume_008') {
    languageActivities.value.push({
      id: Date.now()
    });
    resumeStore.addItem('languages', {
      id: Date.now()
    });
  } else if (section === 'resume_009') {
    portfolioActivities.value.push({
      id: Date.now()
    });
    resumeStore.addItem('portfolios', {
      id: Date.now()
    });
  } else if (section === 'resume_011') {
    customLetterActivities.value.push({
      id: Date.now()
    });
    resumeStore.addItem('customLetters', {
      id: Date.now()
    });
  }
};

const removeActivity = (index, section) => {
  if (section === 'resume_001') {
    if (educationActivities.value.length > 1) {
      resumeStore.removeItem('educations', index);
      educationActivities.value.splice(index, 1);
    }
  } else if (section === 'resume_002') {
    if (personalHistoryActivities.value.length > 1) {
      resumeStore.removeItem('personalHistories', index);
      personalHistoryActivities.value.splice(index, 1);
    }
  } else if (section === 'resume_003') {
    if (internsActivities.value.length > 1) {
      resumeStore.removeItem('internsActivities', index);
      internsActivities.value.splice(index, 1);
    }
  } else if (section === 'resume_004') {
    if (trainingActivities.value.length > 1) {
      resumeStore.removeItem('trainings', index);
      trainingActivities.value.splice(index, 1);
    }
  } else if (section === 'resume_005') {
    if (certificationActivities.value.length > 1) {
      resumeStore.removeItem('certifications', index);
      certificationActivities.value.splice(index, 1);
    }
  } else if (section === 'resume_006') {
    if (awardActivities.value.length > 1) {
      resumeStore.removeItem('awards', index);
      awardActivities.value.splice(index, 1);
    }
  } else if (section === 'resume_007') {
    if (studyingAbroadActivities.value.length > 1) {
      resumeStore.removeItem('studyingAbroads', index);
      studyingAbroadActivities.value.splice(index, 1);
    }
  } else if (section === 'resume_008') {
    if (languageActivities.value.length > 1) {
      resumeStore.removeItem('languages', index);
      languageActivities.value.splice(index, 1);
    }
  } else if (section === 'resume_009') {
    if (portfolioActivities.value.length > 1) {
      resumeStore.removeItem('portfolios', index);
      resumeStore.removePortfolioFile(index);
      portfolioActivities.value.splice(index, 1);
    }
  } else if (section === 'resume_011') {
    if (customLetterActivities.value.length > 1) {
      resumeStore.removeItem('customLetters', index);
      customLetterActivities.value.splice(index, 1);
    }
  }
};


// 각 이력서 항목 업데이트
const handlePersonalInfoUpdate = (data) => {
  resumeStore.updatePersonalInfo(data);
};

const handlePreferentialEmpUpdate = (data) => {
  if(showPreferentialEmp.value) {
    resumeStore.updatePreferentialEmp(data);
  }
};

const handleCertificationUpdate = (index, data) => {
  resumeStore.updateItem('certifications', index, data);
};

const handleAwardUpdate = (index, data) => {
  resumeStore.updateItem('awards', index, data);
};

const handleStudyingAboardUpdate = (index, data) => {
  resumeStore.updateItem('studyingAbroads', index, data);
};

const handleInternsActivityUpdate = (index, data) => {
  resumeStore.updateItem('internsActivities', index, data);
};

const handleTrainingUpdate = (index, data) => {
  resumeStore.updateItem('trainings', index, data);
};

const handleLanguageUpdate = (index, data) => {
  resumeStore.updateItem('languages', index, data);
};

const handlePersonalHistoryUpdate = (index, data) => {
  resumeStore.updateItem('personalHistories', index, data);
};

const handleEducationUpdate = (index, data) => {
  resumeStore.updateItem('educations', index, data);
};

const handleCustomLetterUpdate = (index, data) => {
  resumeStore.updateItem('customLetters', index, data);
};

const handlePortfolioUpdate = (index, data) => {
  resumeStore.updateItem('portfolios', index, {
    portfolioDiv: data.portfolioDiv,
    portfolioType: data.portfolioType,
    portfolioUrl: data.portfolioUrl,
    portfolioFile: data.portfolioFile,
  });
  resumeStore.updatePortfolioFile(index, data.portfolioFile)
};

const handleCreate = async () => {
  if(resumeStore.personalInfo.name == null) {
    toast.error("이름을 입력해주세요.");
    return;
  }
  if(resumeStore.personalInfo.birth == null) {
    toast.error("생년월일을 선택해주세요.");
    return;
  }
  if(resumeStore.personalInfo.gender == '') {
    toast.error("성별을 선택해주세요.");
    return;
  }
  if(resumeStore.personalInfo.email == null) {
    toast.error("이메일을 입력해주세요.");
    return;
  }
  if(resumeStore.personalInfo.phone == null) {
    toast.error("휴대폰번호를 입력해주세요.");
    return;
  }
  if(resumeStore.personalInfo.address == null) {
    toast.error("주소를 입력해주세요.");
    return;
  }
  if(resumeStore.file == null) {
    toast.error("증명사진을 선택해주세요.");
    return;
  }
  resumeStore.updateShowEducation(showEducation.value);
  if(resumeStore.showEducation) {
      for (let i = 0; i < resumeStore.educations.length; i++) {
          const education = resumeStore.educations[i];
          if (!education || (education.schoolDiv === '' || !education.schoolName)) {
            toast.error(`학력 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowPersonalHistory(showPersonalHistory.value);
  if(resumeStore.showPersonalHistory) {
      for (let i = 0; i < resumeStore.personalHistories.length; i++) {
          const personalHistory = resumeStore.personalHistories[i];
          if (!personalHistory || (!personalHistory.companyName || !personalHistory.enteredAt || !personalHistory.job)) {
            toast.error(`경력 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowInternsActivity(showInternsActivity.value);
  if(resumeStore.showInternsActivity) {
      for (let i = 0; i < resumeStore.internsActivities.length; i++) {
          const internsActivity = resumeStore.internsActivities[i];
          if (!internsActivity || (internsActivity.activityDiv === '' || !internsActivity.organization)) {
            toast.error(`인턴 및 대외활동 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowStudyingAbroad(showStudyingAbroad.value);
  if(resumeStore.showStudyingAbroad) {
      for (let i = 0; i < resumeStore.studyingAbroads.length; i++) {
          const studyingAbroad = resumeStore.studyingAbroads[i];
          if (!studyingAbroad || (!studyingAbroad.countryName)) {
            toast.error(`해외경험 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowLanguage(showLanguage.value);
  if(resumeStore.showLanguage) {
      for (let i = 0; i < resumeStore.languages.length; i++) {
          const language = resumeStore.languages[i];
          if (!language || (language.testDiv === '' || language.languageName === '')) {
            toast.error(`어학 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowCertification(showCertification.value);
  if(resumeStore.showCertification) {
      for (let i = 0; i < resumeStore.certifications.length; i++) {
          const certification = resumeStore.certifications[i];
          if (!certification || (!certification.certName)) {
            toast.error(`자격증 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowTraining(showTraining.value);
  if(resumeStore.showTraining) {
      for (let i = 0; i < resumeStore.trainings.length; i++) {
          const training = resumeStore.trainings[i];
          if (!training || (!training.trainingName)) {
            toast.error(`교육이수 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowAward(showAward.value);
  if(resumeStore.showAward) {
      for (let i = 0; i < resumeStore.awards.length; i++) {
          const award = resumeStore.awards[i];
          if (!award || (!award.awardName)) {
            toast.error(`수상 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowCustomLetter(showCustomLetter.value);
  if(resumeStore.showCustomLetter) {
      for (let i = 0; i < resumeStore.customLetters.length; i++) {
          const customLetter = resumeStore.customLetters[i];
          if (!customLetter || (!customLetter.title || !customLetter.contents)) {
            toast.error(`자기소개서 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowPortfolio(showPortfolio.value);
  if(resumeStore.showPortfolio) {
      for (let i = 0; i < resumeStore.portfolios.length; i++) {
          const portfolio = resumeStore.portfolios[i];
          if (!portfolio || (portfolio.portfolioDiv === '' || portfolio.portfolioType === '') || (!portfolio.portfolioUrl && !portfolio.portfolioFile)) {
            toast.error(`포트폴리오 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowPreferentialEmp(showPreferentialEmp.value);
  
  await resumeStore.createResume(router);
};

</script>

<style scoped>
.main-container {
  display: flex;
  justify-content: center;
  align-items: flex-start;
}

.resume-sidebar {
  position: fixed;
  top: 170px;
  right: 200px;
  width: 200px;
  background-color: #ffffff;
  padding: 15px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.resume-sidebar ul {
  list-style: none;
  padding: 0;
  margin: 0;
}

.resume-sidebar li {
  display: flex;
  align-items: center;
  /* 세로 정렬을 맞추기 위해 align-items 설정 */
  margin-bottom: 10px;
  position: relative;
  /* 버튼을 항목 텍스트와 띄우기 위해 relative 위치 설정 */
  padding-right: 40px;
  /* 버튼 공간을 위한 패딩 */
  text-align: left;
  /* 항목 텍스트 왼쪽 정렬 */
}

.resume-sidebar li .span-scroll {
  cursor: pointer; /* 클릭 가능한 커서 모양 */
  color: #212b36; 
  font-weight: bold;
}

.sidebar-bottom-btns {
    height: 50px;
}

.save-btn {
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    width: 100%;
    height: 100%;
    background-color: #212b36;
    color: #ffffff;
}

.resume-sidebar .icon {
  display: inline-block;
  width: 24px;
  height: 24px;
  background-repeat: no-repeat;
  margin-right: 8px;
}

.resume-sidebar .personal-info-icon {
  background-image: url(../../../assets/img/resume/personal-info.png);
}

.resume-sidebar .education-icon {
  background-image: url(../../../assets/img/resume/education.png);
}

.resume-sidebar .personal-history-icon {
  background-image: url(../../../assets/img/resume/personal-history.png);
}

.resume-sidebar .interns-activity-icon {
  background-image: url(../../../assets/img/resume/interns-activity.png);
}

.resume-sidebar .education-completion-icon {
  background-image: url(../../../assets/img/resume/education-completion.png);
}

.resume-sidebar .certification-icon {
  background-image: url(../../../assets/img/resume/certification.png);
}

.resume-sidebar .award-icon {
  background-image: url(../../../assets/img/resume/award.png);
}

.resume-sidebar .studying-abroad-icon {
  background-image: url(../../../assets/img/resume/studying-abroad.png);
}

.resume-sidebar .language-icon {
  background-image: url(../../../assets/img/resume/language.png);
}

.resume-sidebar .portfolio-icon {
  background-image: url(../../../assets/img/resume/portfolio.png);
}

.resume-sidebar .preferential-employment-icon {
  background-image: url(../../../assets/img/resume/preferential-employment.png);
}

.resume-sidebar .cover-letter-icon {
  background-image: url(../../../assets/img/resume/cover-letter.png);
}

.resume-sidebar .toggle-btn {
  background-color: transparent;
  border: none;
  width: 24px;
  height: 24px;
  cursor: pointer;
  background-repeat: no-repeat;
  position: absolute; /* 버튼을 항목의 오른쪽에 고정하기 위해 absolute 위치 설정 */
  right: 10px; /* 항목 텍스트와 버튼 사이의 여백 */
}

.resume-sidebar .toggle-minus {
  background-image: url(../../../assets/img/resume/subtracting-button.png);
}

.resume-sidebar .toggle-plus {
  background-image: url('../../../assets/img/sprite-menu.png');
  background-position: -35px -36px; /* + 버튼 위치 */
}

.resume-sidebar .toggle-btn:hover {
  opacity: 0.7;
}

.content {
  margin-right: 240px; /* 사이드바 너비만큼 여백 */
  width: 100%;
}

.add-container {
  padding: 10px;
  max-width: 800px;
  margin: 50px auto;
}

.activity-wrapper {
  position: relative;
}

.remove-btn {
  position: absolute;
  top: 0;
  right: 0;
  width: 35px;
  height: 35px;
  background: url('../../../assets/img/sprite-icon-x.png') no-repeat 0 -270px;
  border: none;
  cursor: pointer;
  z-index: 10;
}

.remove-btn:hover {
  opacity: 0.8;
}

.form-actions {
  text-align: center;
  padding: 15px 0;
  background-color: #212b36;
  cursor: pointer;
  border-radius: 4px;
  font-size: 16px;
  color: white;
}

.form-actions:hover {
  background-color: #37404a;
  color: white;
}
</style>