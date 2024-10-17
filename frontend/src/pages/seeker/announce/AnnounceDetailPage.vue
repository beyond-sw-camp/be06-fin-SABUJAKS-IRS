<template>
    <SeekerHeaderComponent></SeekerHeaderComponent>
    <div class="ad-wrapper">
        <section class="ad-container">
            <section class="ad-summary">
                <div class="ads-title">
                    <span class="ad-text1">{{ announcementDetail.companyName }}</span>
                    {{ announcementDetail.title }}
                </div>
                <div class="ads-content">
                    <div class="ads-announce-info">
                        <h4 style="margin-bottom: 10px;" class="ad-text2">모집분야</h4>
                        <div class="ad-list">
                            <span class="ad-text3">직무 카테고리 <br><span class="ad-text4">{{ announcementDetail.jobCategory
                                    }}</span></span>
                            <span class="ad-text3">모집분야명 <br><span class="ad-text4">{{ announcementDetail.jobTitle
                                    }}</span></span>
                            <span class="ad-text3">채용형태 <span class="ad-text4">{{ announcementDetail.careerBase
                                    }}</span></span>
                            <span class="ad-text3">모집인원 <span class="ad-text4">{{ announcementDetail.recruitedNum
                                    }}</span></span>
                        </div>
                    </div>
                    <div class="ads-announce-info">
                        <h4 style="margin-bottom: 10px;" class="ad-text2">지원자격/근무조건</h4>
                        <div class="ad-list">
                            <span class="ad-text3">고용형태 <span class="ad-text4">{{ announcementDetail.jobType
                                    }}</span></span>
                            <span class="ad-text3">급여 <br><span class="ad-text4">{{ announcementDetail.salary
                                    }}</span></span>
                            <span class="ad-text3">지역 <br>
                                <span class="ad-text4">{{ announcementDetail.region }}</span>
                                <!-- <button type="button" class="ad-smbtn"><span>지도</span></button> -->
                            </span>
                        </div>
                    </div>
                    <div class="ads-company-info">
                        <h4 style="margin-bottom: 10px;" class="ad-text2">기업정보</h4>
                        <div class="ad-logo">
                            <a href="">
                                <!-- companyStore.imgUrlList에 이미지가 있을 때만 img 태그를 렌더링 -->
                                <img v-if="companyStore.imgUrlList && companyStore.imgUrlList.length > 0"
                                    :src="companyStore.imgUrlList[0]" alt="기업 로고" class="center-cropped-image"
                                    style="width: 90px; height: 90px; object-fit: cover; border-radius: 5px;">
                            </a>
                        </div>
                        <div class="ad-list">
                            <span class="ad-text3">산업(업종) <span class="ad-text4">{{ announcementDetail.companyIndustry
                                    }}</span></span>
                            <span class="ad-text3">기업명 <span class="ad-text4">{{ announcementDetail.companyName
                                    }}</span></span>
                            <span class="ad-text3">기업형태 <span class="ad-text4">{{ announcementDetail.companyType
                                    }}</span></span>
                            <span class="ad-text3">자본금 <span class="ad-text4">{{ announcementDetail.companyCapital
                                    }}</span></span>
                            <span class="ad-text3">사원수 <span class="ad-text4">{{ announcementDetail.companyTotalEmp
                                    }}</span></span>
                            <span class="ad-text3">설립년도 <span class="ad-text4">{{
                                announcementDetail.companyEstablishDate
                                    }}</span></span>
                            <span class="ad-text3">매출액 <span class="ad-text4">{{
                                formatSales(announcementDetail.companySales)
                                    }}</span></span>
                            <span class="ad-text3">주요사업 <span class="ad-text4">{{ announcementDetail.companyBusiness
                                    }}</span></span>
                            <span class="ad-text3">홈페이지
                                <a :href="announcementDetail.companyUrl" target="_blank" class="ad-link">{{ announcementDetail.companyUrl }}</a>
                            </span>
                            <span class="ad-text3">기업주소 <span class="ad-text4">{{ announcementDetail.companyAddress
                                    }}</span></span>
                            <!-- <a href="" class="ad-smbtn"><span>기업소개</span></a> -->
                            <!-- <a href="" class="ad-smbtn"><span>진행중인
                                    채용보기</span></a> -->
                        </div>
                    </div>
                </div>
                <div class="ads-content">
                    <div class="ads-welfare-info">
                        <h4 style="margin-bottom: 10px;" class="ad-text2">복리 후생</h4>
                        <div class="ad-list">
                            <!-- category와 subcategories를 동적으로 렌더링 -->
                            <div v-for="(benefit, index) in announcementDetail.companyBenefitsDataList" :key="index">
                                <span class="ad-text3">{{ benefit.category }} >
                                    <span class="ad-text4">
                                        <!-- subcategories 배열을 쉼표로 구분하여 나열 -->
                                        {{ benefit.subcategories.join(', ') }}
                                    </span>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="ads-period-info">
                        <h4 style="margin-bottom: 10px;" class="ad-text2"> 지원기간 </h4>
                        <span class="ad-text3"> 남은시간 <span class="ad-text5">{{ remainingTime }}</span></span>
                        <span class="ad-text3"> 시작일 <span class="ad-text4">{{ announcementDetail.announcementStart
                                }}</span></span>
                        <span class="ad-text3"> 마감일 <span class="ad-text4">{{ announcementDetail.announcementEnd
                                }}</span></span>
                    </div>
                </div>

            </section>
            <div class="ad-apply">
                <button type="button" class="ad-bgbtn" :disabled="remainingTime === '지원기간이 마감되었습니다.'"
                    @click="remainingTime !== '지원기간이 마감되었습니다.' && goToSubmitPage(announcementDetail.announcementIdx)">
                    <span>&nbsp;{{ remainingTime === '지원기간이 마감되었습니다.' ? '지원 마감' : '즉시지원' }}</span>
                </button>
            </div>
            <!-- 공고 이미지가 있을 때 -->
            <div class="image-display-container" v-if="announcementDetail.imgUrl">
                <img :src="announcementDetail.imgUrl" alt="Main Image" class="large-image" />
            </div>

            <!-- 공고 이미지가 없을 때 -->
            <div class="ad-detail" v-else>
                <h4 class="add-title">상세요강</h4>
                <br>
                <div class="image-preview-container">
                    <!-- 이미지 리스트 중 두 번째 요소부터 보여줌 -->
                    <div v-for="(imgUrl, index) in companyStore.imgUrlList.slice(1)" :key="index + 1"
                        class="image-preview">
                        <img :src="imgUrl" alt="Company Image" />
                    </div>
                </div>
                <div class="add-header">
                    <span class="ad-text6">{{ announcementDetail.companyName }}</span>
                    <span class="ad-text6">{{ announcementDetail.title }}</span>
                    <span class="ad-text7">{{ announcementDetail.jobTitle }}</span>
                </div>
                <p class="add-intro">
                    소개<br>
                    {{ announcementDetail.intro }}
                </p>
                <br>
                <p class="add-heading">
                    <img src="../../../assets/img/announce/check-circle.png">
                    <span>포지션 및 자격요건</span>
                </p>
                <p class="add-desc">
                    <br>
                    <span v-for="(condition, index) in splitConditions(announcementDetail.positionQuali)" :key="index">
                        {{ condition.trim() }}<br />
                    </span>
                </p>
                <br>
                <p class="add-heading">
                    <img src="../../../assets/img/announce/check-circle.png">
                    <span>근무 조건</span>
                </p>
                <p class="add-desc">
                    <br>
                    <span v-for="(condition, index) in splitConditions(announcementDetail.conditions)" :key="index">
                        {{ condition.trim() }}<br />
                    </span>
                </p>
                <br>
                <p class="add-heading">
                    <img src="../../../assets/img/announce/check-circle.png">
                    <span>추가 복지 및 혜택</span>
                </p>
                <p class="add-desc"><br>{{ announcementDetail.benefits }}
                </p>
                <br>
                <p class="add-heading">
                    <img src="../../../assets/img/announce/check-circle.png">
                    <span>전형 절차</span>
                </p>
                <p class="add-desc">
                    <br>
                    면접 횟수 : {{ announcementDetail.interviewNum }}<br>
                    절차 : {{ announcementDetail.process }}<br>
                    서류전형 발표일 : {{ announcementDetail.deadlineDocument}}<br>
                    최종합격 발표일 : {{ announcementDetail.deadlineFinal}}
                </p>
                <br>
                <p class="add-heading">
                    <img src="../../../assets/img/announce/check-circle.png">
                    <span>인사담당자 정보</span>
                </p>
                <p class="add-desc">
                    <br>
                    인사담당자명 : {{ announcementDetail.managerName }}<br>
                    인사담당자 연락처 : {{ announcementDetail.managerContact }}<br>
                    인사담당자 이메일 : {{ announcementDetail.managerEmail }}<br>
                </p>
                <br>
                <p class="add-heading">
                    <img src="../../../assets/img/announce/check-circle.png">
                    <span>유의 사항</span>
                </p>
                <p class="add-desc"><br>{{ announcementDetail.note }}
                </p>
            </div>
        </section>
        <SeekerFooterComponent></SeekerFooterComponent>
    </div>
</template>

<script setup>
import SeekerHeaderComponent from "@/components/seeker/SeekerHeaderComponent.vue";
import SeekerFooterComponent from "@/components/seeker/SeekerFooterComponent.vue";
import { UseAnnouncementStore } from "@/stores/UseAnnouncementStore";
import { UseCompanyStore } from '@/stores/UseCompanyStore';
import { useRoute } from 'vue-router';
import { useRouter } from 'vue-router';
import { ref, onMounted, onUnmounted } from 'vue';

const announcementStore = UseAnnouncementStore();
const companyStore = UseCompanyStore();
const route = useRoute();
const router = useRouter();


const announcementDetail = ref({}); // 상세 공고 저장 변수
const remainingTime = ref(''); // 남은 시간을 저장하는 변수

// 날짜를 파싱하는 함수 (시작일과 마감일을 Date 객체로 변환)
const parseDate = (dateStr) => {
    return new Date(dateStr.replace(' ', 'T')); // 문자열을 "T"로 분리해 ISO 형식으로 변환
};

// 남은 시간 계산 함수
const calculateRemainingTime = () => {
    const now = new Date();
    const endTime = parseDate(announcementDetail.value.announcementEnd);

    const timeDiff = endTime - now;

    if (timeDiff <= 0) {
        remainingTime.value = '지원기간이 마감되었습니다.';
        return;
    }

    const days = Math.floor(timeDiff / (1000 * 60 * 60 * 24));
    const hours = Math.floor((timeDiff % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
    const minutes = Math.floor((timeDiff % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((timeDiff % (1000 * 60)) / 1000);

    remainingTime.value = `${days}일 ${hours}시간 ${minutes}분 ${seconds}초`;
};

let interval;

// 숫자를 변환하는 함수
const formatSales = (value) => {
    const num = Number(value);
    if (isNaN(num)) return value; // 숫자가 아니면 그대로 반환

    if (num >= 1_000_000_000_000) {  // 1조 이상
        return `${(num / 1_000_000_000_000).toFixed(1)}조`;
    } else if (num >= 1_000_000_000) {  // 1억 이상
        return `${(num / 1_000_000_000).toFixed(0)}억`;
    } else if (num >= 10_000_000) {  // 1천만 이상
        return `${(num / 10_000_000).toFixed(0)}천만원`;
    } else {
        return num.toLocaleString();  // 1천만원 이하일 경우 그대로 출력
    }
};

// 내용을 //로 나눠서 배열로 반환하는 함수
const splitConditions = (conditions) => {
    if (!conditions) return [];
    return conditions.split('//');
};


// 공고 세부 정보 불러오기
const fetchAnnouncementDetail = async () => {
    const announcementIdx = route.params.announcementIdx;  // useRoute로 변경
    if (announcementIdx) {
        announcementDetail.value = await announcementStore.fetchAnnouncementDetail(announcementIdx);
        // 데이터를 받아온 후에 남은 시간을 계산
        calculateRemainingTime();
        // 1초마다 남은 시간을 업데이트
        interval = setInterval(calculateRemainingTime, 1000);
    } else {
        console.error("공고 정보가 넘어오지 않았습니다.");
    }
};

// 즉시지원 페이지로 이동하는 함수
const goToSubmitPage = (announcementIdx) => {
    if (announcementIdx) {
        router.push(`/seeker/resume/submit/${announcementIdx}`);
    } else {
        console.error('Announcement ID가 유효하지 않습니다.');
    }
};

// 컴포넌트가 로드될 때 데이터를 가져옴
onMounted(async () => {
    await fetchAnnouncementDetail();  // 공고 데이터를 먼저 가져옴
    if (announcementDetail.value.companyIdx) {
        companyStore.readCompanyImg(announcementDetail.value.companyIdx);  // 회사 이미지 불러오기
    } else {
        console.error("companyIdx 값이 유효하지 않습니다.");
    }

    console.log(announcementDetail);
});

onUnmounted(() => {
    clearInterval(interval); // 컴포넌트가 제거되면 인터벌을 해제하여 메모리 누수 방지
});

</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}

.image-display-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    /* 이미지 컨테이너의 높이를 화면 전체 높이로 설정 가능 */
    background-color: #f9fafb;
}

.large-image {
    max-width: 100%;
    /* 화면 너비에 맞게 이미지 크기를 제한 */
    height: auto;
    /* 비율을 유지하며 이미지 높이를 자동 조정 */
}

.ad-wrapper {
    display: flex;
    flex-direction: column;
    /* 자식 요소를 수직으로 배치 */
    justify-content: space-between;
    /* 상하 간격을 적절히 배치 */
    align-items: center;
    /* 수평 중앙 정렬 */
    position: relative;
    height: 100%;
    /* 화면 전체 높이 */
    background-color: #F9FAFB;
    text-align: left;
    margin-top: 100px;
}

.ad-container {
    position: relative;
    width: 960px;
    margin: 20px auto 0 auto;
    display: block;
    background-color: white;
    border: 1px solid white;
}

/* annouce-detail-summary -> ads */
.ad-summary {
    margin: 0;
    position: relative;
    float: left;
    width: 918px;
    padding: 18px;
    display: block;
}

.ads-title {
    display: flex;
    flex-direction: column;
    flex-wrap: wrap;
    margin: 0;
    font-size: 27px;
    letter-spacing: -.5px;
    color: #212b36;
    width: 689px;
    padding: 5px 0 20px;

    -webkit-box-align: center;
    word-break: break-all;
    align-items: flex-start;
}

.ads-content {
    border-top: 1px solid #212b36;
    display: flex;
    padding: 18px 0px;
    margin: 0;
}

.ads-announce-info {
    width: 300px;
    margin: 7px 10px;
    float: left;
    padding: 0;
}

.ad-list {
    display: flex;
    flex-direction: column;
    margin-top: 7px;
    margin: 0;
    padding: 0;
}

.ad-text1 {
    display: inline-block;
    font-size: 18px;
    letter-spacing: 0;
    color: #212b36;
}

.ad-text2 {
    font-size: 14px;
    letter-spacing: 0;
    color: #333;
    font-weight: 700;
}

.ad-text3 {
    width: 100%;
    display: inline-block;
    font-size: 13px;
    letter-spacing: 0;
    margin: 4px 0 5px;
    color: #999;
    vertical-align: top;
}

.ad-text4 {
    color: #212b36;
    letter-spacing: 0;
    font-weight: 400;
}

.ad-smbtn {
    font-size: 12px;
    letter-spacing: 0;
    /* line-height: 17px; */
    padding: 3px;
    color: white;
    text-decoration: none;
    background-color: #212b36;
    display: inline-flex;
    justify-content: center;
    white-space: nowrap;
    cursor: pointer;
    margin-bottom: 2px;
}

.ad-smbtn:hover,
.ad-bgbtn:hover {
    /* opacity: 70%; */
    background-color: #37404a;
}

.ad-link {
    text-decoration: none;
    color: black;
}

.ads-company-info {
    float: left;
    padding: 0;
    width: 300px;
}

.ads-welfare-info {
    float: left;
    padding: 0;
    width: 100%;
}

.ads-period-info {
    display: block;
    clear: both;
    width: fit-content;
    /* text-align: center; */
}

.ad-logo {
    position: absolute;
    top: 0;
    right: 0;
    width: 100px;
    height: 100px;
    object-fit: cover;
    margin: 18px 18px 0 0;
    padding: 0 0 10px 0;
    display: flex;
    justify-content: center;
    align-items: center;
    text-decoration: none;
}

/* .ad-link {
    text-decoration: none;
    color: black;
}

.ad-link:hover {
    text-decoration: none;
    color: rgb(57, 145, 207);
} */

/* annouce-detail-apply -> ada */
.ad-apply {
    margin: 18px 18px 0 18px;
    position: relative;
    float: left;
    width: 918px;
    padding: 18px 0;
    display: flex;
    flex-direction: row;
    justify-content: center;
    align-items: center;
    border-top: 1px solid #212b36;
    border-bottom: 1px solid #212b36;
}

.ad-text5 {
    font-size: 15px;
    color: red;
    font-weight: 900;
}

.ad-bgbtn {
    position: relative;
    overflow: inherit;
    display: flex;
    -webkit-box-align: center;
    width: 190px;
    align-items: center;
    -webkit-box-pack: center;
    background-color: #212b36;
    justify-content: center;
    font-size: 18px;
    letter-spacing: 0;
    height: 54px;
    box-sizing: border-box;
    margin: 0 3px;
    font-weight: bold;
    color: white;
    padding: 7px 10px;
    cursor: pointer;
    border: 0 none;
}

/* annouce-detail-detail -> add */
.ad-detail {
    padding: 18px 0;
    /* border-top: 1px solid #212b36; */
    margin: 18px 18px 0 18px;
    position: relative;
    float: left;
    width: 918px;
}

.add-title {
    text-align: center;
    margin: 0 0 18px 0;
    font-size: 25px;
    letter-spacing: 0;
    color: #333;
    font-weight: 700;
}


.add-header {
    padding: 50px;
    display: flex;
    flex-direction: column;
    background-repeat: no-repeat;
    background-size: 100%;
}

.ad-text6 {
    color: black;
    font-size: 40px;
    font-weight: 500;
}

.ad-text7 {
    color: black;
    font-size: 50px;
    font-weight: 700;
}

.add-intro {
    font-size: 15px;
    white-space: pre-line;
    line-height: 1.5rem;
}

.add-heading {
    line-height: 1.2;
    display: inline;
    -webkit-box-align: center;
    font-size: 30px;
    letter-spacing: -.5px;
    align-items: center;
    color: #333;
    font-weight: 800;
}

.add-desc {
    font-size: 15px;
    white-space: pre-line;
    line-height: 1.5rem;
}

.image-preview-container {
    width: 100%;
    overflow-x: auto;
    /* 수평 스크롤 가능하게 설정 */
    white-space: nowrap;
    /* 이미지가 수평으로 나열되도록 설정 */
    padding: 10px 0;
    box-sizing: border-box;
    /* padding이 포함되도록 설정 */
}

.image-preview {
    display: inline-block;
    margin-right: 10px;
    /* 이미지 사이의 간격 */
    /* position: relative;
    display: inline-block;
    margin: 10px; */
}

.image-preview img {
    width: 300px;
    /* 각 이미지의 너비를 설정 */
    height: 300px;
    /* 각 이미지의 높이를 설정 */
    object-fit: cover;
    /* 이미지를 잘라내지 않고 비율에 맞게 채움 */
    border: 1px solid #ccc;
    /* border-radius: 10px; */
    /* border: none; */
}
</style>