<template>
    <MainHeaderComponent></MainHeaderComponent>
    <div class="container-detail">
        <MainSideBarComponent></MainSideBarComponent>
        <div id="content">
            <div class="resume-view-wrapper">
                <div class="resume-view-container">
                    <div class="resume-subject">{{ resumeStore.resumeDetail.resumeTitle }}</div>
                    <div class="base profile image">
                        <div class="container">
                            <div v-if="showPersonalInfo" class="photo"><img name="user_photo" :src="resumeStore.resumeDetail.personalInfo.profileImg">
                            </div>
                            <div v-if="showPersonalInfo" class="info-container">
                                <div class="info-general">
                                    <div class="item name">{{ resumeStore.resumeDetail.personalInfo.name }}</div>
                                    <div class="item sex">{{ resumeStore.resumeDetail.personalInfo.gender }}</div>
                                    <div class="item year">{{ resumeStore.resumeDetail.personalInfo.birth }}</div>
                                    <div class="item age">(만 {{ calculateAge(resumeStore.resumeDetail.personalInfo.birth) }}세)</div>
                                </div>
                                <div class="info-detail">
                                    <div class="item">
                                        <div class="label">휴대폰</div>
                                        <div class="value">{{ resumeStore.resumeDetail.personalInfo.phone }}</div>
                                    </div>
                                    <div class="item">
                                        <div class="label">Email</div>
                                        <div class="value">
                                            <a href="mailto:eunjoo.nine@gmail.com">{{ resumeStore.resumeDetail.personalInfo.email }}</a>
                                        </div>
                                    </div>
                                    <div class="item-address">
                                        <div class="label">주소</div>
                                        <div class="value">{{ resumeStore.resumeDetail.personalInfo.address }}</div>
                                    </div>


                                </div>
                            </div>
                        </div>
                    </div>

                    <div v-if="showEducation" class="base education">
                        <h2 class="header_title">학력</h2>
                        <div class="list list-education">
                            <div class="item" v-for="(education, index) in resumeStore.resumeDetail.educations" :key="index">
                            <div class="date">
                                <div v-if="education.schoolDiv === '고등학교' && !education.qualificationExam" class="term"> ~ {{ education.graduatedAt }}</div>
                                <div v-if="education.schoolDiv === '고등학교' && education.qualificationExam" class="term"> ~ {{ education.passedAt }}</div>
                                <div v-if="education.schoolDiv !== '고등학교' && !education.qualificationExam" class="term">{{ education.enteredAt }} ~ {{ education.graduatedAt }}</div>
                                <div class="state">{{ education.status }}</div>
                            </div>
                            <div class="content">
                                <div class="content-header">
                                <div v-if="education.schoolDiv !== '대학원'" class="name">{{ education.schoolName }} ({{ education.graduationStatus }})</div>
                                <div v-else class="name">{{ education.schoolName }} ({{ education.degree }} {{ education.graduationStatus }})</div>
                                <div class="position position2">{{ education.schoolDiv }}</div>
                                <div class="line">{{ education.major }}</div>
                                </div>
                                <div v-if="education.schoolDiv !== '고등학교'" class="content-body">
                                <div class="info">
                                    <div class="item">
                                    <div class="label">전공명</div>
                                    <div class="value">{{ education.majorName }} {{ education.transfer ? "(편입)" : "" }}</div>
                                    </div>
                                </div>
                                <div class="info">
                                    <div class="item">
                                    <div class="label">학점</div>
                                    <div class="value">{{ education.grade }} / {{ education.totalGrade }}</div>
                                    </div>
                                </div>
                                <div class="info">
                                    <div class="item">
                                    <div class="label">{{ education.majorType}}</div>
                                    <div class="value">{{ education.otherMajor }}</div>
                                    </div>
                                </div>
                                </div>
                            </div>
                            </div>
                        </div>
                    </div>

                    <div v-if="showPersonalHistory" class="base career">
                        <h2 class="header_title">경력</h2>
                        <div class="list list-education">
                            <div class="item" v-for="(personalHistory, index) in resumeStore.resumeDetail.personalHistories" :key="index">
                                <div class="date">
                                    <div class="term">{{ personalHistory.enteredAt }} ~ {{ personalHistory.quitAt }}</div>
                                    <div class="term-calculator">{{ calculateDuration(personalHistory.enteredAt, personalHistory.quitAt) }}</div>
                                </div>
                                <div class="content">
                                    <div class="content-header">
                                        <div class="name">
                                            {{ personalHistory.companyName }} </div>
                                            <div v-if="personalHistory.empStatus" class="position position1">재직중</div>
                                    </div>
                                    <div class="content-body">
                                        <div class="info">
                                            <div class="item">
                                                <div class="label">부서명</div>
                                                <div class="value">{{ personalHistory.deptName }} </div>
                                            </div>
                                            <div class="item">
                                                <div class="label">직급/직책</div>
                                                <div class="value">{{ personalHistory.position }} </div>
                                            </div>
                                            <div class="item">
                                                <div class="label">담당직무</div>
                                                <div class="value">{{ personalHistory.job }} </div>
                                            </div>
                                            <div class="item">
                                                <div class="label">연봉</div>
                                                <div class="value">{{ personalHistory.salary }} </div>
                                            </div>
                                            <div class="item">
                                                <div class="label">담당업무</div>
                                                <div class="value">{{ personalHistory.work }} </div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>

                        </div>
                    </div>


                    <div v-if="showInternsActivity" class="base intern">
                        <h2 class="header_title">인턴·대외활동</h2>
                        <div class="list list-intern">
                            <div class="item" v-for="(internsActivity, index) in resumeStore.resumeDetail.internsActivities" :key="index">
                                <div class="date">
                                    <div class="term">{{ internsActivity.startAt }} ~ {{ internsActivity.endAt }}</div>
                                    <div class="term-calculator">{{ calculateDuration(internsActivity.startAt, internsActivity.endAt) }}</div>
                                </div>

                                <div class="content">
                                    <div class="content-header">
                                        <div class="name">{{ internsActivity.organization }}</div>
                                        <div class="position position3">{{ internsActivity.activityDiv }}</div>
                                    </div>
                                    <div class="content-body">
                                        {{ internsActivity.contents }}</div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div v-if="showTraining" class="base learn">
                        <h2 class="header_title">교육이수</h2>
                        <div class="list list-learn">
                            <div class="item" v-for="(training, index) in resumeStore.resumeDetail.trainings" :key="index">
                                <div class="date">{{ training.startAt }} ~ {{ training.endAt }}</div>
                                <div class="content">
                                    <div class="content-header">
                                        <div class="name">{{ training.trainingName }}</div>
                                        <div class="agency">{{ training.organization }}</div>
                                    </div>
                                    <div class="content-body">
                                        {{ training.contents }}</div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div v-if="showCertification" class="base certificate">
                        <h2 class="header_title">자격증</h2>
                        <div class="list list-certificate">
                            <div class="item" v-for="(certification, index) in resumeStore.resumeDetail.certifications" :key="index">
                                <div class="date">{{ certification.takingAt }}</div>
                                <div class="content">
                                    <div class="content-header">
                                        <div class="name">{{ certification.certName }}</div>
                                        <div class="agency">{{ certification.organization }}</div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </div>


                    <div v-if="showAward" class="base awards">
                        <h2 class="header_title">수상</h2>
                        <div class="list list-awards">
                            <div class="item" v-for="(award, index) in resumeStore.resumeDetail.awards" :key="index">
                                <div class="date">
                                    {{ award.year }}
                                </div>
                                <div class="content">
                                    <div class="content-header">
                                        <div class="name">{{ award.awardName }}</div>
                                        <div class="agency">{{ award.organization }}</div>
                                    </div>
                                    <div class="content-body">
                                        {{ award.contents }}</div>
                                </div>
                            </div>
                        </div>
                    </div>


                    <div v-if="showStudyingAbroad" class="base experience">
                        <h2 class="header_title">해외경험</h2>
                        <div class="list list-experience">
                            <div class="item" v-for="(studyingAbroad, index) in resumeStore.resumeDetail.studyingAbroads" :key="index">
                                <div class="date">
                                    <div class="term">{{ studyingAbroad.startAt }} ~ {{ studyingAbroad.endAt }}</div>
                                    <div class="term-calculator">{{ calculateDuration(studyingAbroad.startAt, studyingAbroad.endAt) }}</div>
                                </div>
                                <div class="content">
                                    <div class="content-header">
                                        <div class="name">{{ studyingAbroad.countryName }}</div>
                                    </div>
                                    <div class="content-body">
                                        {{ studyingAbroad.contents }}</div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div v-if="showLanguage" class="base career">
                        <h2 class="header_title">어학</h2>
                        <div class="list list-education">
                            <div class="item" v-for="(language, index) in resumeStore.resumeDetail.languages" :key="index">
                                <div class="date">
                                    <div class="term" style="font-weight: bold; font-size: 16px;">{{ language.languageName }}</div>
                                </div>
                                <div class="content">
                                    <div class="content-body">
                                        <div class="info">
                                            <div v-if="language.testDiv === '공인시험'" class="item">
                                                <div class="label">공인시험</div>
                                                <div class="value">{{ language.officialTest }}</div>
                                            </div>
                                            <div v-if="language.testDiv === '공인시험' && language.selectScore !== '취득'" class="item">
                                                <div class="label">급수/점수</div>
                                                <div class="value">{{ language.score }}{{  language.selectScore }}</div>
                                            </div>
                                            <div v-if="language.testDiv === '공인시험' && language.selectScore === '취득'" class="item">
                                                <div class="label">{{ language.testDiv }}</div>
                                                <div class="value">{{ language.conversationLevel }}</div>
                                            </div>
                                            <div v-if="language.testDiv === '회화능력'" class="item">
                                                <div class="label">{{ language.testDiv }}</div>
                                                <div class="value">{{ language.conversationLevel }}</div>
                                            </div>
                                            <div v-if="language.testDiv === '공인시험'" class="item">
                                                <div class="label">취득년월</div>
                                                <div class="value">{{ language.takingAt }}</div>
                                            </div>
                                        </div>
                                    </div>

                                </div>
                            </div>

                        </div>
                    </div>
                    
                    <div v-if="showCustomLetter" class="base introduction">
                        <h2 class="header_title">자기소개서</h2>
                        <div id="SummaryAjax">
                        </div>
                        <ul class="list list-introduction">
                            <li v-for="(customLetter, index) in resumeStore.resumeDetail.customLetters" :key="index" class="item">
                                <div class="header_title" style="font-weight: bold; margin-bottom: 10px;">{{ customLetter.title }} ({{ customLetter.charNum }}자)</div>
                                <div class="content" id="pfl_original">
                                    {{ customLetter.contents }}</div>
                            </li>
                        </ul>
                    </div>

                    <div v-if="showPortfolio" class="base portfolio">
                        <h2 class="header_title">포트폴리오</h2>
                        <table class="table table-attachments">
                            <colgroup>
                                <col style="width:100px;">
                                <col style="width:320px;">
                            </colgroup>
                            <tbody>
                                <tr v-for="(portfolio, index) in resumeStore.resumeDetail.portfolios" :key="index" >
                                    <th>{{ portfolio.portfolioDiv }}</th>
                                    <td>
                                        <a :href="portfolio.portfolioUrl" target="_blank">
                                            <i class="icon url" aria-hidden="true"></i>
                                            <div class="name">{{ portfolio.portfolioUrl }}</div>
                                        </a>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div v-if="showPreferentialEmp" class="base jobpreference">
                        <h2 class="header_title">취업우대사항</h2>
                        <table class="table table-special">
                            <caption><span class="skip">취업우대사항</span></caption>
                            <colgroup>
                                <col style="width:180px;">
                                <col style="width:160px;">
                                <col style="width:180px;">
                                <col style="width:160px;">
                                <col style="width:180px;">
                                <col style="width:160px;">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th>보훈대상 여부</th>
                                    <td>{{ resumeStore.resumeDetail.preferentialEmp.veterans ? 'O' : 'X'}}</td>
                                    <th>취업보호대상 여부</th>
                                    <td>{{ resumeStore.resumeDetail.preferentialEmp.protection ? 'O' : 'X'}}</td>
                                    <th>고용지원금대상 여부</th>
                                    <td>{{ resumeStore.resumeDetail.preferentialEmp.subsidy ? 'O' : 'X'}}</td>
                                </tr>
                                <tr>
                                    <th>병역사항</th>
                                    <td v-if="resumeStore.resumeDetail.preferentialEmp.militaryClass === '군필'" colspan="3">
                                        {{ resumeStore.resumeDetail.preferentialEmp.military 
                                        ? resumeStore.resumeDetail.preferentialEmp.militaryClass : 'X'}}
                                        ({{ resumeStore.resumeDetail.preferentialEmp.militaryStart }} ~ {{ resumeStore.resumeDetail.preferentialEmp.militaryEnd }}) 
                                        [{{ resumeStore.resumeDetail.preferentialEmp.militaryRank }} / {{ resumeStore.resumeDetail.preferentialEmp.militaryType }}]
                                    </td>
                                    <td v-else colspan="3">
                                        {{ resumeStore.resumeDetail.preferentialEmp.military 
                                        ? resumeStore.resumeDetail.preferentialEmp.militaryClass : 'X'}} </td>
                                    <th>장애여부</th>
                                    <td>{{ resumeStore.resumeDetail.preferentialEmp.disability ? resumeStore.resumeDetail.preferentialEmp.disabilityDegree : 'X'}}</td>
                                </tr>
                            </tbody>
                        </table>
                    </div>

                    <div v-if="showPersonalInfo" class="sign">
                        <div class="message">위의 모든 기재사항은 사실과 다름없음을 확인합니다.</div>

                        <div class="writer">작성자 : {{ resumeStore.resumeDetail.personalInfo.name }}</div>
                        <div class="warning">
                            <div class="description">이 지원서는 {{ formatDate(resumeStore.resumeDetail.resumedAt) }}에 제출된 지원서 입니다.<br>위조된 문서를 등록하여 취업활동에 이용
                                시 법적
                                책임을 지게 될 수 있습니다.<br>IRS는 구직자가 등록 한 문서에 대해 보증하거나 별도의 책임을 지지 않으며<br>첨부된 문서를 신뢰하여 발생한
                                법적
                                분쟁에 책임을 지지 않습니다.<br>또한 구인/구직 목적 외 다른 목적으로 이용시 이력서 삭제 혹은 비공개 조치가 될 수 있습니다.</div>
                        </div>
                    </div>

                </div>
            </div>
            <div v-if="resumeStore.resumeDetail.docPassed == null" id="size-buttons">
                <button @click="updateDocPassed(1)" class="success">합격</button>
                <button @click="updateDocPassed(0)" class="failure">불합격</button>
            </div>
            <div v-else id="size-buttons" class="status-container">
                <div v-if="resumeStore.resumeDetail.docPassed" class="status success">서류 합격</div>
                <div v-else class="status failure">서류 불합격</div>
            </div>
        </div>
    </div>
</template>

<script setup>
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";

import { ref, onMounted } from 'vue';
import { UseResumeStore } from '@/stores/UseResumeStore';
import { useRoute } from 'vue-router';

const resumeStore = UseResumeStore();
const route = useRoute();

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

onMounted(async () => {
    await resumeStore.read(route.params.resumeIdx);

    showPersonalInfo.value = true;
    showEducation.value = resumeStore.resumeDetail.codes.includes("resume_001");
    showPersonalHistory.value = resumeStore.resumeDetail.codes.includes("resume_002");
    showInternsActivity.value = resumeStore.resumeDetail.codes.includes("resume_003");
    showTraining.value = resumeStore.resumeDetail.codes.includes("resume_004");
    showCertification.value = resumeStore.resumeDetail.codes.includes("resume_005");
    showAward.value = resumeStore.resumeDetail.codes.includes("resume_006");
    showStudyingAbroad.value = resumeStore.resumeDetail.codes.includes("resume_007");
    showLanguage.value = resumeStore.resumeDetail.codes.includes("resume_008");
    showPortfolio.value = resumeStore.resumeDetail.codes.includes("resume_009");
    showPreferentialEmp.value = resumeStore.resumeDetail.codes.includes("resume_010");
    showCustomLetter.value = resumeStore.resumeDetail.codes.includes("resume_011");
    
});

const calculateAge = (birthDate) => {
    const birth = new Date(birthDate.replace(/\./g, '-')); // 문자열 변환
    const today = new Date();
    
    let age = today.getFullYear() - birth.getFullYear();
    const monthDiff = today.getMonth() - birth.getMonth();

    // 생일이 지나지 않았으면 나이를 하나 줄임
    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
        age--;
    }
    
    return age;
};


// 개월 수를 계산하고 년과 개월로 반환하는 메서드
const calculateDuration = (enteredAt, quitAt) => {
  const start = new Date(enteredAt.replace('.', '-') + '-01'); // "YYYY-MM-DD" 형식으로 변환
  const end = new Date(quitAt.replace('.', '-') + '-01'); // "YYYY-MM-DD" 형식으로 변환

  const yearDiff = end.getFullYear() - start.getFullYear();
  const monthDiff = end.getMonth() - start.getMonth();

  const totalMonths = yearDiff * 12 + monthDiff;

  const years = Math.floor(totalMonths / 12);
  const months = totalMonths % 12;

  if(years == 0) {
    return `${months}개월`;
  }

  return `${years}년 ${months}개월`;
};

// 날짜를 'YYYY년 MM월 DD일 (요일)' 형식으로 변환하는 메서드
const formatDate = (dateString) => {
  const date = new Date(dateString);
  
  const year = date.getFullYear();
  const month = date.getMonth() + 1; // getMonth()는 0부터 시작하므로 +1 필요
  const day = date.getDate();

  const weekDays = ['일', '월', '화', '수', '목', '금', '토'];
  const dayOfWeek = weekDays[date.getDay()]; // getDay()로 요일 인덱스 얻음

  return `${year}년 ${String(month).padStart(2, '0')}월 ${String(day).padStart(2, '0')}일 (${dayOfWeek})`;
};

const updateDocPassed = async (docPassedValue) => {
    await resumeStore.updateDocPassed(route.params.resumeIdx, docPassedValue);
};

</script>


<style scoped>
.status-container {
    display: flex;
    justify-content: center;
    align-items: center;
    margin: 20px 0;
    font-size: 18px; /* 폰트 크기 조정 */
}

.status {
    padding: 10px 20px; /* 여백 추가 */
    border-radius: 5px; /* 모서리 둥글게 */
    font-weight: bold; /* 글자 두껍게 */
}

.success {
    background-color: #28a745; /* 초록색 배경 */
    color: white; /* 흰색 글자 */
    border: 1px solid #28a745; /* 경계선 */
}

.failure {
    background-color: #dc3545; /* 빨간색 배경 */
    color: white; /* 흰색 글자 */
    border: 1px solid #dc3545; /* 경계선 */
}

body {
    background-color: #f2f4f7;
}

.table {
    width: 100%;
    border-top: 1px solid #000;
    background-color: #fff;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.table th {
    height: 51px;
    text-align: center;
    background-color: #fcfcfc;
    border-left: 1px solid #edeef0;
    border-right: 1px solid #edeef0;
    border-bottom: 1px solid #edeef0;
    font-size: 12px;
    letter-spacing: 0px;
    color: #000;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.table td {
    padding: 0 18px;
    border-bottom: 1px solid #edeef0;
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
    text-align: left;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.table tr th:first-child {
    border-left: 0;
}

.table .content:first-child {
    padding-top: 16px;
}

.table .content:last-child {
    margin-bottom: 16px;
}

.table .content.is-label .item {
    padding-left: 84px;
}

.table .content .item {
    position: relative;
}

.table .content .label {
    position: absolute;
    left: 0;
    top: 0;
    width: 76px;
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
    line-height: 1.9;
}

.table .content .label:after {
    content: '';
    position: absolute;
    right: 0;
    top: 0;
    width: 1px;
    height: 11px;
    background-color: #ddd;
    -webkit-transform: translateY(9px);
    -moz-transform: translateY(9px);
    -ms-transform: translateY(9px);
    -o-transform: translateY(9px);
    transform: translateY(9px);
}

.table .content .value {
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
    line-height: 1.9;
}

.resume-view-wrapper {
    position: relative;
    width: 100%;
    text-align: center;
}

.resume-view-container {
    position: relative;
    width: 940px;
    margin: 0 auto;
    padding-bottom: 75px;
    text-align: left;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

body.resume-view-print .resume-view-container {
    padding-top: 30px;
}

body.resume-view {
    background-color: #f2f4f7;
}

.resume-print {
    overflow: hidden;
    margin-bottom: 10px;
}

.resume-print .button-print {
    position: relative;
    float: right;
    display: block;
    width: 133px;
    height: 44px;
    background-color: #002AFF;
    font-size: 14px;
    letter-spacing: 0px;
    color: #fff;
}

.resume-print .button-print span {
    display: block;
    width: 133px;
    height: 44px;
    padding: 11px 0 0 53px;
    text-align: left;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

body.resume-view-print {
    background-color: #fff;
}

body.resume-view-print .list {
    border-left: 1px solid #ddd;
    border-right: 1px solid #ddd;
    border-bottom: 1px solid #ddd;
}

body.resume-view-print .table {
    border-left: 1px solid #ddd;
    border-right: 1px solid #ddd;
}

body.resume-view-print .profile .container {
    border: 1px solid #ddd;
}

body.resume-preview .resume-view-wrapper {
    background-color: #f2f4f7;
}

body.resume-preview .resume-view-container {
    padding-top: 104px;
}

body.resume-preview .resume-header {
    z-index: 9000;
    position: fixed;
    width: 100%;
    height: 67px;
    padding: 20px 0 0 28px;
    border-bottom: 1px solid #e8e9ed;
    font-size: 0;
    background-color: #fff;
    text-align: left;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

body.resume-preview .resume-header h1.header {
    display: inline-block;
    vertical-align: top;
    font-size: 20px;
    letter-spacing: -0.5px;
    color: #333;
}

body.resume-preview .resume-header .subtitle {
    display: inline-block;
    margin-left: 13px;
    vertical-align: top;
    font-size: 14px;
    letter-spacing: 0px;
    color: #333;
    -webkit-transform: translateY(7px);
    -moz-transform: translateY(7px);
    -ms-transform: translateY(7px);
    -o-transform: translateY(7px);
    transform: translateY(7px);
}

body.resume-preview .resume-header .buttons {
    z-index: 10;
    position: absolute;
    right: 28px;
    top: 11px;
}

body.resume-preview .resume-header .button-print {
    position: relative;
    float: right;
    display: block;
    width: 133px;
    height: 44px;
    background-color: #002AFF;
    font-size: 14px;
    letter-spacing: 0px;
    color: #fff;
}

body.resume-preview .resume-header .button-print span {
    display: block;
    width: 133px;
    height: 44px;
    line-height: 44px;
    padding: 0 0 0 53px;
    text-align: left;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

body.resume-preview .resume-page {
    height: 100%;
}

.print-footer {
    overflow: hidden;
    padding: 100px 0 30px;
}

.print-footer .print-number {
    text-align: right;
    font-size: 14px;
    letter-spacing: 0px;
    color: #888;
}

.hidden {
    display: none;
}

.resume-subject {
    margin-bottom: 24px;
    font-size: 40px;
    letter-spacing: -0.5px;
    color: #000;
    line-height: 1.4;
}

.resume-resolution {
    position: relative;
    width: 100%;
    padding-left: 84px;
    margin-bottom: 30px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.resume-resolution .label {
    position: absolute;
    left: 0;
    top: 1px;
    width: 76px;
    height: 20px;
    color: #333;
    font-size: 12px;
    letter-spacing: 0px;
    border: 1px solid #cbccd0;
    text-align: center;
    line-height: 20px;
}

.resume-resolution .value {
    font-size: 14px;
    letter-spacing: 0px;
    color: #333;
    line-height: 1.55;
}

body.resume-preview .resume-subject {
    padding-top: 40px;
}

.resume-view-container .base {
    overflow: hidden;
    position: relative;
    margin-bottom: 33px;
}

.resume-view-container .base.skill {
    margin-top: -34px;
}

.resume-view-container .base.career div.headers {
    font-size: 0;
}

.resume-view-container .base.career div.headers .term {
    float: left;
    margin-left: 15px;
    vertical-align: top;
    font-size: 0;
    -webkit-transform: translateY(10px);
    -moz-transform: translateY(10px);
    -ms-transform: translateY(10px);
    -o-transform: translateY(10px);
    transform: translateY(10px);
}

.resume-view-container .base.career div.headers .total {
    float: left;
    vertical-align: top;
    font-size: 14px;
    letter-spacing: 0px;
    color: #ff5b5b;
}

.resume-view-container .base.career div.headers .detail {
    float: left;
    margin-left: 6px;
    vertical-align: top;
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
    white-space: nowrap;
}

.resume-view-container .base.career div.headers .detail em {
    color: #ff5b5b;
}

.resume-view-container .base.career div.headers h2.header {
    float: left;
    display: inline-block;
    vertical-align: top;
}

.resume-view-container .base.career div.headers:after {
    content: '';
    display: block;
    clear: both;
}

.resume-view-container h2.header {
    margin-bottom: 13px;
    font-size: 22px;
    letter-spacing: -0.5px;
    color: #000;
}

.resume-view-container .warning {
    padding-top: 21px;
    text-align: center
}

.resume-view-container .warning .description {
    font-size: 12px;
    letter-spacing: 0px;
    color: #888;
    line-height: 1.8;
}

.profile {
    overflow: hidden;
    position: relative;
}

.profile.image .container {
    height: 180px;
    padding-left: 170px;
    background-color: #F2F4F7;
}

.profile .container {
    overflow: hidden;
    position: relative;
    width: 940px;
    padding: 0 0 20px 40px;
    margin-bottom: 10px;
    background-color: #fff;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.profile .photo {
    z-index: 1;
    position: absolute;
    left: 0;
    top: 0;
    width: 140px;
    height: 182px;
}

.profile .photo img {
    width: 140px;
    height: 182px;
}

.profile .info-general {
    padding-top: 15px;
    margin-bottom: 30px;
    font-size: 0;
}

.profile .info-general .item {
    display: inline-block;
    font-size: 14px;
    letter-spacing: 0px;
    color: #888;
    margin-left: 7px;
    vertical-align: top;
}

.profile .info-general .name {
    margin-left: 0;
    font-size: 20px;
    letter-spacing: -0.5px;
    color: #000;
}

.profile .info-general .year,
.profile .info-general .sex,
.profile .info-general .age {
    -webkit-transform: translateY(8px);
    -moz-transform: translateY(8px);
    -ms-transform: translateY(8px);
    -o-transform: translateY(8px);
    transform: translateY(8px);
}

.profile .info-detail {
    width: 650px;
}

.profile .info-detail .item {
    position: relative;
    display: inline-block;
    width: 300px;
    padding-left: 83px;
    margin-bottom: 11px;
    vertical-align: top;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.profile .info-detail .item-address {
    position: relative;
    display: inline-block;
    width: 600px;
    padding-left: 83px;
    margin-bottom: 11px;
    vertical-align: top;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.profile .info-detail .item-full {
    display: block;
    width: 100%;
}

.profile .info-detail .label {
    position: absolute;
    left: 0;
    top: 0;
    width: 68px;
    font-size: 14px;
    letter-spacing: 0px;
    color: #888;
}

.profile .info-detail .label:after {
    content: '';
    display: block;
    position: absolute;
    right: 0;
    top: 0;
    width: 1px;
    height: 11px;
    background-color: #ddd;
    -webkit-transform: translateY(6px);
    -moz-transform: translateY(6px);
    -ms-transform: translateY(6px);
    -o-transform: translateY(6px);
    transform: translateY(6px);
}

.profile .info-detail .value {
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
}

.table-attachments {
    table-layout: fixed;
}

.table-attachments td {
    padding: 0;
}

.table-attachments td>a {
    position: relative;
    display: block;
    width: 100%;
    padding: 0 15px 0 32px;
    box-sizing: border-box;
}

.table-attachments td>a:hover {
    color: #000;
}

.table-attachments td .name {
    overflow: hidden;
    width: 100%;
    white-space: nowrap;
    text-overflow: ellipsis;
    color: #000;
}

.table-attachments td .icon.docx,
.table-attachments td .icon.doc {
    background-position: 0 -118px;
}

.table-attachments td .icon.pptx,
.table-attachments td .icon.ppt {
    background-position: 0 -164px;
}

.table-attachments td .icon.xlsx,
.table-attachments td .icon.xls,
.table-attachments td .icon.excel {
    background-position: 0 -210px;
}

.table-attachments td .icon.pdf {
    background-position: 0 -256px;
}

.table-attachments td .icon.hwp {
    background-position: 0 -303px;
}

.table-attachments td .icon.zip {
    background-position: 0 -528px;
}

.table-attachments td .icon.jpg,
.table-attachments td .icon.jpeg {
    background-position: 0 -623px;
}

.table-attachments td .icon.gif {
    background-position: 0 -573px;
}

.table-attachments td .icon.psd {
    background-position: 0 -673px;
}

.table-attachments td .icon.png {
    background-position: 0 -723px;
}

.table-attachments td .icon.txt {
    width: 13px;
    background-position: -2px -773px;
}

.table-attachments td .icon.swf {
    background-position: 0 -823px;
}

.table-attachments td .icon.ai {
    background-position: 0 -873px;
}

.table-attachments td .icon.url {
    background-position: 0 -973px;
}

.table-attachments td .icon.rtf {
    background-position: 0 -1089px;
}

.table-attachments td .icon.gul {
    background-position: 0 -1023px;
}

.table-attachments td .icon.alz {
    width: 13px;
    background-position: -2px -1155px;
}

@media print {
    .resumeHeader {
        display: none;
    }

    .resume-header {
        display: none;
    }

    .resume-print {
        display: none;
    }

    .resume-view-wrapper {
        width: 100%;
    }

    .resume-view-container {
        padding-top: 0 !important;
    }

    .sidemenu {
        display: none;
    }

    .profile .container {
        width: 100%;
    }

    .profile .summary {
        padding-top: 25px;
        border: 1px solid #ddd;
    }

    .profile .summary.col-5 .item {
        width: 20%;
    }

    .profile .summary.col-4 .item {
        overflow: hidden;
        width: 25%;
    }

    .profile .summary .item {
        height: auto;
        min-height: 96px;
        padding-top: 0;
        border-left: 1px solid #ddd;
        -webkit-box-sizing: border-box;
        -moz-box-sizing: border-box;
        box-sizing: border-box;
    }

    .profile .summary .item:first-child {
        border-left: 0;
    }

    .profile .info-detail .item {
        width: 40%;
    }

    .table-special th {
        width: 16.666%;
    }

    .table-special th:first-child {
        width: 15%;
    }

    .table-special th:nth-child(2) {
        width: 17%;
    }

    .table-special th:nth-child(3) {
        width: 17%;
    }

    .table-special td {
        width: 16.666%;
        padding: 0;
        text-align: center;
    }

    .table-special td:first-child {
        width: 15%;
    }

    .table-special td:nth-child(4) {
        width: 20%;
    }

    .table-special td:nth-child(6) {
        width: 16%;
    }

    .base.last-child {
        page-break-after: auto;
    }

    .table-attachments th {
        width: 15%;
    }

    .table-attachments td {
        width: 35%;
    }
}

.sign {
    padding-top: 20px;
    text-align: center;
}

.sign .message {
    margin-bottom: 13px;
    font-size: 22px;
    letter-spacing: -0.5px;
    color: #000;
}

.sign .writer {
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
}

.list {
    overflow: hidden;
    position: relative;
    border-top: 1px solid #000;
    background-color: #fff;
}

.list.list-introduction {
    padding: 27px 38px 33px;
}

.list.list-introduction div.header {
    font-weight: bold;
}

.list li.item {
    margin-bottom: 31px;
}

.list li.item:last-child {
    margin-bottom: 0;
}

.list li.item div.header {
    margin-bottom: 9px;
    font-size: 18px;
    letter-spacing: 0px;
    color: #000;
    line-height: 1.9;
}

.list li.item .content {
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
    line-height: 1.8;
    word-break: break-all;
}

.list div.item {
    overflow: hidden;
    position: relative;
    padding-left: 200px;
    padding-right: 40px;
    border-top: 1px solid #edeef0;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

ul.list-introduction {
    list-style: none;
}

.list div.item:first-child {
    border-top: 0;
}

.list div.item.etc {
    border-color: #b2b2b2;
}

.list div.item.etc div.header {
    position: absolute;
    left: 38px;
    top: 24px;
}

.list .date {
    position: absolute;
    left: 38px;
    top: 23px;
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
}

.list .date .term {
    margin-bottom: 7px;
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
}

.list .date .term-calculator {
    font-size: 13px;
    letter-spacing: 0px;
    color: #002AFF;
}

.list .content-header {
    margin-bottom: 8px;
    font-size: 0;
    line-height: 1.55;
}

.list .content-header .name {
    position: relative;
    display: inline;
    font-size: 16px;
    letter-spacing: 0px;
    font-weight: bold;
    color: #000;
    word-break: break-all;
}

.list .content-header .agency {
    display: inline;
    margin-left: 11px;
    font-size: 14px;
    letter-spacing: 0px;
    color: #666;
}

.list .content-body {
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
    line-height: 22px;
}

.list .content-body p {
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
    line-height: 1.8;
}

.list.list-education div.item {
    padding-top: 21px;
    padding-bottom: 17px;
}

.list.list-education div.item .line {
    display: inline;
    margin-left: 11px;
    font-size: 14px;
    letter-spacing: 0px;
    color: #666;
}

.list.list-education div.item.no-description {
    padding-top: 34px;
}

.list.list-education>div.item {
    min-height: 90px;
}

.list.list-education .content-header .name {
    top: -2px;
}

.list.list-education .state {
    font-size: 13px;
    letter-spacing: 0px;
    color: #002AFF;
}

.list.list-education .info .item {
    min-height: auto;
    margin-bottom: 7px;
    padding: 0 0 0 90px;
    border-top: 0;
}

.list.list-education .info .label {
    position: absolute;
    left: 0;
    top: 0;
    width: 80px;
    font-size: 14px;
    letter-spacing: 0px;
    color: #666;
    line-height: 1.8;
}

.list.list-education .info .label:after {
    content: '';
    display: block;
    position: absolute;
    right: 0;
    top: 0;
    width: 1px;
    height: 12px;
    background-color: #ddd;
    -webkit-transform: translateY(8px);
    -moz-transform: translateY(8px);
    -ms-transform: translateY(8px);
    -o-transform: translateY(8px);
    transform: translateY(8px);
}

.list.list-education .info .value {
    font-size: 14px;
    letter-spacing: 0px;
    color: #666;
    line-height: 1.8;
}

.list.list-intern div.item {
    padding-top: 21px;
    padding-bottom: 17px;
}

.list.list-intern>div.item {
    min-height: 90px;
}

.list.list-intern .content-header {
    margin-bottom: 4px;
}

.position {
    overflow: hidden;
    display: inline-block;
    height: 20px;
    line-height: 18px;
    vertical-align: -3px;
    margin-left: 9px;
    padding: 0 14px;
    font-size: 12px;
    letter-spacing: 0px;
    border: 1px solid #000;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
}

.list.list-education .position.position1 {
    color: #ff5b5b;
    border-color: #fa9899;
}

.list.list-education .position.position2 {
    color: #7ba71f;
    border-color: #abc675;
}

.list.list-intern .position.position3 {
    color: #3189ba;
    border-color: #7eb4d2;
}

.list.list-intern .position.position4 {
    color: #6772df;
    border-color: #9fa6e9;
}

.list.list-intern .position.position5 {
    color: #4db59d;
    border-color: #8fcec1;
}

.list.list-intern .position.position6 {
    color: #ff830a;
    border-color: #f9bc8d;
}

.list.list-intern .position.position7 {
    color: #0029AD;
    border-color: #0029AD;
}

.list.list-certificate .item {
    min-height: auto;
    padding-top: 20px;
    padding-bottom: 14px;
}

.list.list-certificate .item.first-child {
    min-height: auto;
}

.list.list-certificate .item.etc {
    padding-top: 17px;
    padding-bottom: 20px;
}

.list.list-certificate .item.etc div.header {
    top: 23px;
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
}

.list.list-certificate .item.etc .description {
    font-size: 14px;
    letter-spacing: 0px;
    color: #000;
    line-height: 1.8;
}

.list.list-certificate .date {
    top: 23px;
}

.list.list-learn div.item {
    min-height: 97px;
    padding-top: 20px;
    padding-bottom: 17px;
}

.list.list-learn div.item:first-child {
    min-height: 96px;
}

.list.list-awards .date {
    top: 21px;
}

.list.list-awards div.item {
    min-height: 97px;
    padding-top: 21px;
    padding-bottom: 16px;
}

.list.list-awards div.item:first-child {
    min-height: 96px;
}

.list.list-experience div.item {
    min-height: 90px;
    padding-top: 20px;
    padding-bottom: 20px;
}

.list.list-experience div.item:first-child {
    min-height: 89px;
}

.list.list-experience .content-header {
    margin-bottom: 0;
}

.skip,
.blind {
    position: absolute !important;
    overflow: hidden;
    width: 1px;
    height: 1px;
    clip: rect(0 0 0 0);
    margin: -1px;
}

#size-buttons button {
    color: white;
    border: none;
    padding: 12px 20px;
    /* 버튼 크기 키움 */
    margin-right: 10px;
    border-radius: 5px;
    cursor: pointer;
    transition: background-color 0.3s;
    font-weight: bold;
    /* 글자 볼드체 */
}
</style>