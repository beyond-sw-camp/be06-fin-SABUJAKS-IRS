<template>
    <MainHeaderComponent></MainHeaderComponent>
    <div class="container">
        <MainSideBarComponent></MainSideBarComponent>
        <div id="content">
            <h1 style="margin-top: 50px;" v-if="announcementDetail.title">{{ announcementDetail.title }} 공고 상세 정보</h1>
            <button class="custom-button" @click="goToForm">조립된 지원서 폼 확인</button>

            <!-- 모달 템플릿 -->
            <div v-if="showModal" class="modal">
                <div class="modal-content">
                    <span class="close-button" @click="showModal = false">&times;</span>

                    <!-- 지원서 항목 리스트 -->
                    <div>
                        <h3>지원서 항목</h3>
                        <ul class="styled-list" style="display: flex;">
                            <li style="margin: 10px 10px;" v-for="(form, index) in customFormStore.custom.customFormList" :key="index">{{ form }}
                            </li>
                        </ul>
                    </div>
                    <br>

                    <!-- 서술형 항목 리스트 -->
                    <div
                        v-if="customFormStore.custom.customLetterList && customFormStore.custom.customLetterList.length > 0">
                        <h3>자기소개서 문항</h3>
                        <ul class="styled-list">
                            <li style="border-bottom: 1px solid #e0e0e0;" v-for="(letter, index) in customFormStore.custom.customLetterList" :key="index">
                                {{ letter.split(' / ')[0] }} <br />
                                <small>글자 제한: {{ letter.split(' / ')[1] }}자</small>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

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
                <p v-if="announcementDetail.deadlineDocument"><strong>서류전형 발표일:</strong> {{ announcementDetail.deadlineDocument }}</p>
                <p v-if="announcementDetail.deadlineFinal"><strong>최종합격 발표일:</strong> {{ announcementDetail.deadlineFinal }}</p>

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
import { useRoute, useRouter } from 'vue-router';
import { UseAnnouncementStore } from '@/stores/UseAnnouncementStore';
import { UseCustomFormStore } from "@/stores/UseCustomFormStore";
import { useToast } from 'vue-toastification';

const route = useRoute();
const router = useRouter();
const announcementStore = UseAnnouncementStore();
const customFormStore = UseCustomFormStore();
const announcementDetail = ref({});
const showModal = ref(false); // 모달 상태 설정

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

// 지원서 폼 조회하고, 받아온 값이 있으면 모달창으로 확인, 없으면 지원서폼 조립 페이지로 이동
const goToForm = async () => {

    try {
        await customFormStore.readForm(route.params.announcementIdx);

        if (customFormStore.custom && customFormStore.custom.customFormList && customFormStore.custom.customFormList.length > 0) {
            // 조회 성공 시 모달을 표시
            showModal.value = true;
            console.log(customFormStore.custom);
        }

        // if (customFormStore.custom.customFormList.length > 0) {
        //     // 조회 성공 시 모달을 표시
        //     showModal.value = true; // 모달 표시를 위한 상태 설정 (예: ref를 활용)
        //     console.log(customFormStore.custom);
        // }
    } catch (error) {
        // 에러 캐치 시 페이지 이동
        const toast = useToast();
        // console.log(error);
        const errorMessage = error.message + ' 조립 페이지로 이동합니다.';
        toast.error(errorMessage);

        setTimeout(() => {
            router.push({
                name: 'AnnouncementCreateStep2',
                params: { announcementIdx: route.params.announcementIdx, title: announcementDetail.value.title },
            });
        }, 2000); // 2초 후 페이지 이동
    }
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
    margin-left: 158px;
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

/* 지원서 폼 확인 버튼 스타일 */
.custom-button {
    background-color: #212b36;
    color: white;
    border-radius: 5px;
    padding: 13px 10px;
    margin-left: auto;
    margin-bottom: 10px;
    border: none;
    cursor: pointer;
    /* font-weight: bold; */
    font-size: 16px;
    /* transition: background-color 0.3s ease; */
}

.custom-button:hover {
    background-color: #37404a;
}

/* 모달 스타일 */
.modal {
    display: flex;
    justify-content: center;
    align-items: center;
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 9999;
}

.modal-content {
    position: relative;
    background-color: #fff;
    padding: 20px;
    border-radius: 8px;
    width: 60%;
    max-width: 600px;
    text-align: center;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.2);
}

.close-button {
    position: absolute;
    top: 10px;
    right: 15px;
    font-size: 24px;
    font-weight: bold;
    cursor: pointer;
    color: #333;
}

.close-button:hover {
    color: #000;
}

.styled-list {
    list-style-type: none; /* 기본 목록 스타일 제거 */
    padding: 0;
    margin: 0;
}

.styled-list li {
    padding: 10px 0;
    /* border-bottom: 1px solid #e0e0e0; */
    font-size: 16px;
    color: #333;
}

.styled-list li:last-child {
    border-bottom: none; 
    /* 마지막 항목은 구분선 제거 */
}

.styled-list small {
    font-size: 14px;
    color: #666;
}

</style>
