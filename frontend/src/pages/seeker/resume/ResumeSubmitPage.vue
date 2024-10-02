<template>
  <SeekerHeaderComponent/>
  <div class="main-container pt-150">
    <div class="content">
      <!-- 지원서 제목 -->
        <div class="add-container">
          <div class="resumeTitle">
            <input maxlength="100" name="resumeTitle" v-model="resumeTitle" placeholder="지원서 제목" type="text" value="">
          </div>
        </div>

      <!-- 인적사항 -->
      <div v-if="showPersonalInfo" id="personal-info" class="add-container">
        <h2>인적사항</h2>
        <PersonalInfoComponent @updateData="handlePersonalInfoUpdate" :data="personalInfo" />
      </div>

      <!-- 학력 -->
      <div v-if="showEducation" id="education" class="add-container">
        <h2>학력</h2>
        <div v-for="(activity, index) in educationActivities" :key="activity.id" class="activity-wrapper">
          <EducationComponent @updateData="handleEducationUpdate(index, $event)" class="relative-component" :data="activity.data" />
          <button v-if="educationActivities.length > 1" @click="removeActivity(index, 'resume_001')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_001')" class="form-actions">+ 추가</div>
      </div>

      <!-- 경력 -->
      <div v-if="showPersonalHistory" id="personal-history" class="add-container">
        <h2>경력</h2>
        <div v-for="(activity, index) in personalHistoryActivities" :key="activity.id" class="activity-wrapper">
          <PersonalHistoryComponent @updateData="handlePersonalHistoryUpdate(index, $event)" class="relative-component" :data="activity.data" />
          <button v-if="personalHistoryActivities.length > 1" @click="removeActivity(index, 'resume_002')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_002')" class="form-actions">+ 추가</div>
      </div>

      <!-- 인턴·대외활동 -->
      <div v-if="showInternsActivity" id="interns-activity" class="add-container">
        <h2>인턴·대외활동</h2>
        <div v-for="(activity, index) in internsActivities" :key="activity.id" class="activity-wrapper">
          <InternsActivityComponent @updateData="handleInternsActivityUpdate(index, $event)" class="relative-component" :data="activity.data" />
          <button v-if="internsActivities.length > 1" @click="removeActivity(index, 'resume_003')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_003')" class="form-actions">+ 추가</div>
      </div>

      <!-- 교육이수 -->
      <div v-if="showTraining" id="training" class="add-container">
        <h2>교육이수</h2>
        <div v-for="(activity, index) in trainingActivities" :key="activity.id" class="activity-wrapper">
          <TrainingComponent @updateData="handleTrainingUpdate(index, $event)" class="relative-component" :data="activity.data"/>
          <button v-if="trainingActivities.length > 1" @click="removeActivity(index, 'resume_004')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_004')" class="form-actions">+ 추가</div>
      </div>

      <!-- 자격증 -->
      <div v-if="showCertification" id="certification" class="add-container">
        <h2>자격증</h2>
        <div v-for="(activity, index) in certificationActivities" :key="activity.id" class="activity-wrapper">
          <CertificationComponent @updateData="handleCertificationUpdate(index, $event)" class="relative-component" :data="activity.data" />
          <button v-if="certificationActivities.length > 1" @click="removeActivity(index, 'resume_005')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_005')" class="form-actions">+ 추가</div>
      </div>
      
      <!-- 수상 -->
      <div v-if="showAward" id="award" class="add-container">
        <h2>수상</h2>
        <div v-for="(activity, index) in awardActivities" :key="activity.id" class="activity-wrapper">
          <AwardComponent @updateData="handleAwardUpdate(index, $event)" class="relative-component" :data="activity.data" />
          <button v-if="awardActivities.length > 1" @click="removeActivity(index, 'resume_006')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_006')" class="form-actions">+ 추가</div>
      </div>

      <!-- 해외경험 -->
      <div v-if="showStudyingAbroad" id="studying-abroad" class="add-container">
        <h2>해외경험</h2>
        <div v-for="(activity, index) in studyingAbroadActivities" :key="activity.id" class="activity-wrapper">
          <StudyingAbroadComponent @updateData="handleStudyingAboardUpdate(index, $event)" class="relative-component" :data="activity.data" />
          <button v-if="studyingAbroadActivities.length > 1" @click="removeActivity(index, 'resume_007')" class="remove-btn"></button>
        </div>
        <div @click="addActivity('resume_007')" class="form-actions">+ 추가</div>
      </div>

      <!-- 어학 -->
      <div v-if="showLanguage" id="language" class="add-container">
        <h2>어학</h2>
        <div v-for="(activity, index) in languageActivities" :key="activity.id" class="activity-wrapper">
          <LanguageComponent @updateData="handleLanguageUpdate(index, $event)" class="relative-component" :data="activity.data" />
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
        <PreferentialEmpComponent @updateData="handlePreferentialEmpUpdate" :data="preferentialEmp" />
      </div>

      <!-- 자기소개서 -->
      <div v-if="showCustomLetter" id="customLetter" class="add-container">
        <h2>자기소개서</h2>
        <div v-for="(activity, index) in customLetterActivities" :key="activity.id" class="activity-wrapper">
          <CustomLetterFormComponent @updateData="handleCustomLetterUpdate(index, $event)" class="relative-component" :data="activity.data" />
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
        <li v-if="showEducation">
          <span class="icon education-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('education')">학력</span>
        </li>
        <li v-if="showPersonalHistory">
          <span class="icon personal-history-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('personal-history')">경력</span>
        </li>
        <li v-if="showInternsActivity">
          <span class="icon interns-activity-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('interns-activity')">인턴·대외활동</span>
        </li>
        <li v-if="showTraining">
          <span class="icon education-completion-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('training')">교육이수</span>
        </li>
        <li v-if="showCertification">
          <span class="icon certification-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('certification')">자격증</span>
        </li>
        <li v-if="showAward">
          <span class="icon award-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('award')">수상</span>
        </li>
        <li v-if="showStudyingAbroad">
          <span class="icon studying-abroad-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('studying-abroad')">해외경험</span>
        </li>
        <li v-if="showLanguage">
          <span class="icon language-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('language')">어학</span>
        </li>
        <li v-if="showPortfolio">
          <span class="icon portfolio-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('portfolio')">포트폴리오</span>
        </li>
        <li v-if="showPreferentialEmp">
          <span class="icon preferential-employment-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('preferential-emp')">취업우대·병역</span>
        </li>
        <li v-if="showCustomLetter">
          <span class="icon cover-letter-icon"></span>
          <span class="span-scroll" @click.stop="scrollToSection('customLetter')">자기소개서</span>
        </li>
      </ul>
      <div class="sidebar-bottom-btns">
        <button class="save-btn" @click="handleSubmit">제출하기</button>
      </div>
    </div>
  </div>
</template>
  
  
<script setup>
import { ref, onMounted  } from 'vue';
import { UseResumeStore } from '@/stores/UseResumeStore';
import { useRoute, useRouter } from 'vue-router';
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
import CustomLetterFormComponent from '@/components/seeker/CustomLetterFormComponent.vue';

const resumeStore = UseResumeStore();
const route = useRoute();
const router = useRouter();

const resumeTitle = ref(null);
const showPersonalInfo = ref(false);
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

const personalInfo = ref({});
const preferentialEmp = ref({});


onMounted(async () => {
  const response = await resumeStore.readSubmitInfo(route.params.announcementIdx);

  showPersonalInfo.value = true;
  showEducation.value = response.result.codes.includes("resume_001");
  showPersonalHistory.value = response.result.codes.includes("resume_002");
  showInternsActivity.value = response.result.codes.includes("resume_003");
  showTraining.value = response.result.codes.includes("resume_004");
  showCertification.value = response.result.codes.includes("resume_005");
  showAward.value = response.result.codes.includes("resume_006");
  showStudyingAbroad.value = response.result.codes.includes("resume_007");
  showLanguage.value = response.result.codes.includes("resume_008");
  showPortfolio.value = response.result.codes.includes("resume_009");
  showPreferentialEmp.value = response.result.codes.includes("resume_010");
  showCustomLetter.value = response.result.codes.includes("resume_011");
  // 자기소개서 (맞춤)
  if(showCustomLetter.value) {
    customLetterActivities.value = response.result.customLetterForms.map((customLetterForm, index) => ({
      id: index,
      data: customLetterForm
    }));
  }

  if(response.result.integrated) {
    // 인적사항
    if(showPersonalInfo.value && response.result.personalInfo) {
      personalInfo.value = response.result.personalInfo;
    }
    
    // 학력
    if(showEducation.value && response.result.educations) {
      educationActivities.value = response.result.educations.map((education, index) => ({
        id: index,
        data: education
      }));
    }

    // 경력
    if(showPersonalHistory.value && response.result.personalHistories) {
      personalHistoryActivities.value = response.result.personalHistories.map((personalHistory, index) => ({
        id: index,
        data: personalHistory
      }));
    }

    // 인턴&대외활동
    if(showInternsActivity.value && response.result.internsActivities) {
      internsActivities.value = response.result.internsActivities.map((internsActivity, index) => ({
        id: index,
        data: internsActivity
      }));
    }

    // 교육이수
    if(showTraining.value && response.result.trainings) {
      trainingActivities.value = response.result.trainings.map((training, index) => ({
        id: index,
        data: training
      }));
    }

    // 자격증
    if(showCertification.value && response.result.certifications) {
      certificationActivities.value = response.result.certifications.map((certification, index) => ({
        id: index,
        data: certification
      }));
    }

    // 수상
    if(showAward.value && response.result.awards) {
      awardActivities.value = response.result.awards.map((award, index) => ({
        id: index,
        data: award
      }));
    }

    // 해외경험
    if(showStudyingAbroad.value && response.result.studyingAbroads) {
      studyingAbroadActivities.value = response.result.studyingAbroads.map((studyingAbroad, index) => ({
        id: index,
        data: studyingAbroad
      }));
    }

    // 어학
    if(showLanguage.value && response.result.languages) {
      languageActivities.value = response.result.languages.map((language, index) => ({
        id: index,
        data: language
      }));
    }

    // // 포트폴리오
    // if(showPortfolio.value) {
    //   portfolioActivities.value = response.result.portfolios.map((portfolio, index) => ({
    //     id: index,
    //     data: portfolio
    //   }));
    // }

    // 취업우대&병역
    if(showPreferentialEmp.value) {
      preferentialEmp.value = response.result.preferentialEmp;
    }

  }



});

const scrollToSection = (sectionId) => {
  document.getElementById(sectionId)?.scrollIntoView({ behavior: 'smooth' });
};

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
  resumeStore.updatePortfolioFile(index, data.portfolioFile);
};

const handleSubmit = async () => {
  if(resumeTitle.value == null) {
    alert("지원서 제목을 입력해주세요.");
    return;
  }
  if(resumeStore.personalInfo.name == null) {
    alert("이름을 입력해주세요.");
    return;
  }
  if(resumeStore.personalInfo.birth == null) {
    alert("생년월일을 선택해주세요.");
    return;
  }
  if(resumeStore.personalInfo.gender == '') {
    alert("성별을 선택해주세요.");
    return;
  }
  if(resumeStore.personalInfo.email == null) {
    alert("이메일을 입력해주세요.");
    return;
  }
  if(resumeStore.personalInfo.phone == null) {
    alert("휴대폰번호를 입력해주세요.");
    return;
  }
  if(resumeStore.personalInfo.address == null) {
    alert("주소를 입력해주세요.");
    return;
  }
  if(resumeStore.file == null) {
    alert("증명사진을 선택해주세요.");
    return;
  }
  resumeStore.updateShowEducation(showEducation.value);
  if(resumeStore.showEducation) {
      for (let i = 0; i < resumeStore.educations.length; i++) {
          const education = resumeStore.educations[i];
          if (!education || (education.schoolDiv === '' || !education.schoolName)) {
            alert(`학력 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowPersonalHistory(showPersonalHistory.value);
  if(resumeStore.showPersonalHistory) {
      for (let i = 0; i < resumeStore.personalHistories.length; i++) {
          const personalHistory = resumeStore.personalHistories[i];
          if (!personalHistory || (!personalHistory.companyName || !personalHistory.enteredAt || !personalHistory.job)) {
            alert(`경력 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowInternsActivity(showInternsActivity.value);
  if(resumeStore.showInternsActivity) {
      for (let i = 0; i < resumeStore.internsActivities.length; i++) {
          const internsActivity = resumeStore.internsActivities[i];
          if (!internsActivity || (internsActivity.activityDiv === '' || !internsActivity.organization)) {
            alert(`인턴 및 대외활동 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowStudyingAbroad(showStudyingAbroad.value);
  if(resumeStore.showStudyingAbroad) {
      for (let i = 0; i < resumeStore.studyingAbroads.length; i++) {
          const studyingAbroad = resumeStore.studyingAbroads[i];
          if (!studyingAbroad || (!studyingAbroad.countryName)) {
            alert(`해외경험 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowLanguage(showLanguage.value);
  if(resumeStore.showLanguage) {
      for (let i = 0; i < resumeStore.languages.length; i++) {
          const language = resumeStore.languages[i];
          if (!language || (language.testDiv === '' || language.languageName === '')) {
            alert(`어학 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowCertification(showCertification.value);
  if(resumeStore.showCertification) {
      for (let i = 0; i < resumeStore.certifications.length; i++) {
          const certification = resumeStore.certifications[i];
          if (!certification || (!certification.certName)) {
            alert(`자격증 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowTraining(showTraining.value);
  if(resumeStore.showTraining) {
      for (let i = 0; i < resumeStore.trainings.length; i++) {
          const training = resumeStore.trainings[i];
          if (!training || (!training.trainingName)) {
            alert(`교육이수 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowAward(showAward.value);
  if(resumeStore.showAward) {
      for (let i = 0; i < resumeStore.awards.length; i++) {
          const award = resumeStore.awards[i];
          if (!award || (!award.awardName)) {
            alert(`수상 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowCustomLetter(showCustomLetter.value);
  if(resumeStore.showCustomLetter) {
      for (let i = 0; i < resumeStore.customLetters.length; i++) {
          const customLetter = resumeStore.customLetters[i];
          if (!customLetter || (!customLetter.title || !customLetter.contents)) {
            alert(`자기소개서 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowPortfolio(showPortfolio.value);
  if(resumeStore.showPortfolio) {
      for (let i = 0; i < resumeStore.portfolios.length; i++) {
          const portfolio = resumeStore.portfolios[i];
          if (!portfolio || (portfolio.portfolioDiv === '' || portfolio.portfolioType === '') || (!portfolio.portfolioUrl && !portfolio.portfolioFile)) {
            alert(`포트폴리오 ${i + 1}: 필수값을 입력해주세요.`);
            return;
          }
      }
  }
  resumeStore.updateShowPreferentialEmp(showPreferentialEmp.value);

  await resumeStore.submitResume(router, route.params.announcementIdx, resumeTitle);
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
  top: 200px;
  right: 220px;
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
  align-items: center; /* 세로 정렬을 맞추기 위해 align-items 설정 */
  margin-bottom: 15px;
  position: relative; /* 버튼을 항목 텍스트와 띄우기 위해 relative 위치 설정 */
  padding-right: 40px; /* 버튼 공간을 위한 패딩 */
  text-align: left; /* 항목 텍스트 왼쪽 정렬 */
}

.resume-sidebar li .span-scroll {
  cursor: pointer; /* 클릭 가능한 커서 모양 */
  color: #3933F4;
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
  height: 22px;
  background-image: url('../../../assets/img/sprite-menu.png');
  background-repeat: no-repeat;
  margin-right: 8px;
}

.resume-sidebar .personal-info-icon {
  background-position: -60px -754px; /* 인적사항 */
}

.resume-sidebar .education-icon {
  background-position: 0 -403px;
}

.resume-sidebar .personal-history-icon {
  background-position: 0 -458px;
}

.resume-sidebar .interns-activity-icon {
  background-position: 0 -486px;
}

.resume-sidebar .education-completion-icon {
  background-position: 0 -430px;
}

.resume-sidebar .certification-icon {
  background-position: -30px -754px;
}

.resume-sidebar .award-icon {
  background-position: -30px -784px;
}

.resume-sidebar .studying-abroad-icon {
  background-position: 0 -630px;
}

.resume-sidebar .language-icon {
  background-position: 0 -514px;
}

.resume-sidebar .portfolio-icon {
  background-position: 0 -571px;
}

.resume-sidebar .preferential-employment-icon {
  background-position: 0 -662px;
}

.resume-sidebar .cover-letter-icon {
  background-position: 0 -377px; /* 자기소개서 */
}

.resume-sidebar .toggle-btn {
  background-color: transparent;
  border: none;
  width: 25px;
  height: 25px;
  cursor: pointer;
  background-repeat: no-repeat;
  position: absolute; /* 버튼을 항목의 오른쪽에 고정하기 위해 absolute 위치 설정 */
  right: 10px; /* 항목 텍스트와 버튼 사이의 여백 */
}

.resume-sidebar .toggle-minus {
  background-image: url('../../../assets/img/sprite-menu.png');
  background-position: -35px 0; /* - 버튼 위치 */
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
  background-color: #e0e0e0;
  cursor: pointer;
  border-radius: 4px;
  font-size: 16px;
}
.form-actions:hover {
  background-color: #212b36;
  color: white;
}


.resumeTitle {
  overflow: hidden;
  position: relative;
  width: 800px;
  height: 80px;
  margin-bottom: 20px;
  margin-top: 50px;
  background-color: #fff;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  border: 1px solid #dbe0e9;
}

.resumeTitle input {
  overflow: hidden;
  display: block;
  width: 100%;
  height: 100%;
  padding: 0 28px;
  border: 0;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  color: #333;
  white-space: nowrap;
  -o-text-overflow: ellipsis;
  text-overflow: ellipsis;
  font-size: 30px;
  letter-spacing: -0.5px;
}

.resumeTitle input::-ms-clear {
  display: none;
}

.resumeTitle input::-webkit-input-placeholder {
  color: #a8a8a8;
  font-size: 30px;
  letter-spacing: -0.5px;
}

.resumeTitle input::-moz-placeholder {
  color: #a8a8a8;
  font-size: 30px;
  letter-spacing: -0.5px;
}

.resumeTitle input:-moz-placeholder {
  color: #a8a8a8;
  font-size: 30px;
  letter-spacing: -0.5px;
}

.resumeTitle input:-ms-input-placeholder {
  color: #a8a8a8;
  font-size: 30px;
  letter-spacing: -0.5px;
}
</style>