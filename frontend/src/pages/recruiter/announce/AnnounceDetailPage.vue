<template>
    <MainHeaderComponent></MainHeaderComponent>
    <div class="container">
        <MainSideBarComponent></MainSideBarComponent>
        <div id="content">
            <h1 v-if="announcementDetail.title">{{ announcementDetail.title }} 공고 상세 정보</h1>
            <div class="detail-section">
                <h2>공고 정보</h2>
                <p v-if="announcementDetail.title"><strong>공고명:</strong> {{ announcementDetail.title }}</p>
                <p v-if="announcementDetail.announcementStart && announcementDetail.announcementEnd">
                    <strong>공고 기간:</strong> {{ announcementDetail.announcementStart }} - {{
                        announcementDetail.announcementEnd }}
                </p>
                <p v-if="announcementDetail.jobCategory"><strong>직무 카테고리:</strong> {{ announcementDetail.jobCategory }}
                </p>
                <p v-if="announcementDetail.jobTitle"><strong>모집분야명:</strong> {{ announcementDetail.jobTitle }}</p>
                <p v-if="announcementDetail.recruitedNum"><strong>모집 인원:</strong> {{ announcementDetail.recruitedNum }}명
                </p>
                <p v-if="announcementDetail.careerBase"><strong>경력 구분:</strong> {{ announcementDetail.careerBase }}</p>
                <p v-if="announcementDetail.positionQuali"><strong>포지션 & 자격 요건:</strong> <br><span
                        v-for="(condition, index) in splitConditions(announcementDetail.positionQuali)" :key="index">
                        {{ condition.trim() }}<br />
                    </span></p>
                <p v-if="announcementDetail.intro"><strong>회사 소개:</strong> {{ announcementDetail.intro }}</p>
                <p v-if="announcementDetail.region"><strong>근무 지역:</strong> {{ announcementDetail.region }}</p>
                <p v-if="announcementDetail.jobType"><strong>근무 형태:</strong> {{ announcementDetail.jobType }}</p>
                <p v-if="announcementDetail.salary"><strong>급여:</strong> {{ announcementDetail.salary }}</p>
                <p v-if="announcementDetail.conditions"><strong>근무 조건:</strong> <br><span
                        v-for="(condition, index) in splitConditions(announcementDetail.conditions)" :key="index">
                        {{ condition.trim() }}<br />
                    </span></p>
                <p v-if="announcementDetail.benefits"><strong>복리후생:</strong> {{ announcementDetail.benefits }}</p>
                <p v-if="announcementDetail.imgUrl"><strong>공고 이미지:</strong> <img :src="announcementDetail.imgUrl"
                        alt="공고 이미지" /></p>
                <br>
                <h2>담당자 정보</h2>
                <p v-if="announcementDetail.managerName"><strong>담당자명:</strong> {{ announcementDetail.managerName }}</p>
                <p v-if="announcementDetail.managerContact"><strong>담당자 연락처:</strong> {{
                    announcementDetail.managerContact }}</p>
                <p v-if="announcementDetail.managerEmail"><strong>담당자 이메일:</strong> {{ announcementDetail.managerEmail
                    }}</p>

                <br>
                <h2>기업 정보</h2>
                <p>(더 자세한 기업 정보는 마이페이지에서 확인하세요)</p>
                <p v-if="announcementDetail.companyName"><strong>기업명:</strong> {{ announcementDetail.companyName }}</p>
                <p v-if="announcementDetail.companyIndustry"><strong>산업:</strong> {{ announcementDetail.companyIndustry
                    }}</p>
                <p v-if="announcementDetail.companyBusiness"><strong>주요 사업:</strong> {{
                    announcementDetail.companyBusiness }}</p>
                <p v-if="announcementDetail.companyAddress"><strong>기업 주소:</strong> {{ announcementDetail.companyAddress
                    }}</p>
                <p v-if="announcementDetail.companyInfo"><strong>기업 소개:</strong> {{ announcementDetail.companyInfo }}
                </p>

                <br>
                <h2>채용 절차</h2>
                <p v-if="announcementDetail.interviewNum"><strong>면접 횟수:</strong> {{ announcementDetail.interviewNum }}
                </p>
                <p v-if="announcementDetail.process"><strong>전형 절차:</strong> {{ announcementDetail.process }}</p>

                <br>
                <h2>유의 사항</h2>
                <p v-if="announcementDetail.note"><strong>유의 사항:</strong> {{ announcementDetail.note }}</p>
            </div>
        </div>
    </div>
</template>

<script setup>
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import { ref, onMounted } from "vue";
import { useRoute } from 'vue-router';
import { UseAnnouncementStore } from '@/stores/UseAnnouncementStore';

const route = useRoute();
const announcementStore = UseAnnouncementStore();
const announcementDetail = ref({});

// 공고 세부 정보 불러오기
const fetchAnnouncementDetail = async () => {
    const announcementIdx = route.params.announcementIdx;
    announcementDetail.value = await announcementStore.fetchAnnouncementDetail(announcementIdx);
};

// 내용을 //로 나눠서 배열로 반환하는 함수
const splitConditions = (conditions) => {
    if (!conditions) return [];
    return conditions.split('//');
};

onMounted(() => {
    fetchAnnouncementDetail();
});
</script>

<style scoped>
.container {
    width: 80%;
    margin: 150px 200px;
}

#content {
    flex: 1;
    margin-left: 200px;
    padding: 0 0 150px 0;
    display: flex;
    flex-direction: column;
    box-sizing: border-box;
}

.detail-section {
    background-color: #fff;
    padding: 20px;
    /* border-radius: 10px; */
    box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
}

h1,
h2 {
    margin-bottom: 20px;
}

p {
    margin-bottom: 10px;
}

p strong {
    color: #333;
}

img {
    max-width: 100%;
    height: auto;
    border-radius: 5px;
    margin-top: 10px;
}
</style>
