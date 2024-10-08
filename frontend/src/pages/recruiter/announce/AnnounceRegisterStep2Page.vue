<template>
    <MainHeaderComponent></MainHeaderComponent>
    <div class="container">
        <MainSideBarComponent></MainSideBarComponent>
        <div id="content">
            <div style="display: flex; justify-content: space-between;">
                <h2>[{{ route.params.title }}] 공고<br>지원서 폼 조립</h2>
                <!-- 저장 버튼 -->
                <button @click="saveForm" class="save-button">폼 저장</button>
            </div>
            <div class="form-builder" style="margin-top: 30px;">

                <!-- 네모네모한 버튼들 -->
                <div class="form-options">
                    <button v-for="(item, index) in formItems" :key="index"
                        :class="{ 'selected': selectedForms.includes(item.code) }" @click="toggleForm(item.code)">
                        {{ item.description }}
                    </button>
                </div>

                <!-- 자기소개서 특별 기능 -->
                <div v-if="selectedForms.includes('resume_011')" class="cover-letter-section">
                    <div style="display: flex; justify-content: space-between;">
                        <h3>자기소개서 항목&글자수</h3>
                        <button style="margin-top: 15px; border-radius: 5px;" @click="addCoverLetterSection">항목
                            추가</button>
                    </div>
                    <div v-for="(section, index) in coverLetterSections" :key="index" class="cover-letter-item"
                        style="margin-top: 15px;">
                        <textarea type="text" v-model="section.title" @input="autoResize($event)" style="font-size: 18px; height: 40px; width: 600px;" placeholder="문항입력" ></textarea>
                        <input type="number" v-model="section.characterLimit" style="font-size: 18px; height: 40px; width: 100px;"
                            placeholder="글자 수 제한" />
                        <button style="padding: 10px 10px; border-radius: 5px; height: 40px; width: 60px;"
                            @click="removeCoverLetterSection(index)">삭제</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script setup>
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
// import { UseAnnouncementStore } from '@/stores/UseAnnouncementStore';
import { UseCustomFormStore } from '@/stores/UseCustomFormStore';
import { ref } from "vue";
import { useRoute, useRouter } from 'vue-router';

// const announcementStore = UseAnnouncementStore();s
const customFormStore = UseCustomFormStore();
const route = useRoute();
const router = useRouter();

// 폼 종류 리스트
const formItems = ref([
    { code: "resume_001", description: "학력" },
    { code: "resume_002", description: "경력" },
    { code: "resume_003", description: "인턴&대외활동" },
    { code: "resume_004", description: "교육이수" },
    { code: "resume_005", description: "자격증" },
    { code: "resume_006", description: "수상" },
    { code: "resume_007", description: "해외경험" },
    { code: "resume_008", description: "어학" },
    { code: "resume_009", description: "포트폴리오" },
    { code: "resume_010", description: "취업우대&병역" },
    { code: "resume_011", description: "자기소개서" },
]);

// 선택된 폼들
const selectedForms = ref([]);

// 자기소개서 항목 관리
const coverLetterSections = ref([]);

// 폼 선택/해제 토글 함수
const toggleForm = (code) => {
    if (selectedForms.value.includes(code)) {
        // 선택 해제할 경우
        selectedForms.value = selectedForms.value.filter((item) => item !== code);

        // 자기소개서('resume_011') 선택 해제 시 관련 데이터를 초기화
        if (code === 'resume_011') {
            coverLetterSections.value = []; // 자기소개서 항목 초기화
        }
    } else {
        // 선택할 경우
        selectedForms.value.push(code);
    }
};

// 자기소개서 항목 추가
const addCoverLetterSection = () => {
    coverLetterSections.value.push({
        title: "",
        characterLimit: 0,
    });
};

// 자기소개서 항목 삭제
const removeCoverLetterSection = (index) => {
    coverLetterSections.value.splice(index, 1);
};

const autoResize = (event) => {
    const textarea = event.target;
    textarea.style.height = 'auto';  // 현재 높이를 초기화
    textarea.style.height = `${textarea.scrollHeight}px`;  // 새로운 높이 설정
};

// 폼 저장 함수 호출
const saveForm = () => {
    customFormStore.saveForm(route.params.announcementIdx, selectedForms.value, coverLetterSections.value, router);
};
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
    /* box-sizing: border-box; */
}

.container {
    width: 80%;
    margin: 150px 200px;
}

#content {
    /* flex: 1; */
    margin-left: 200px;
    /* 사이드바 너비만큼 왼쪽 여백 추가 */
    padding: 0 0 150px 0;
    display: flex;
    /* flex-direction: column; */
    box-sizing: border-box;
}

/* 추가 스타일 */
.form-builder {
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #fff;
    padding: 20px;
    border-radius: 10px;
    /* box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1); */
}

.form-options {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    margin-bottom: 20px;
}

.form-options button {
    margin: 10px;
    padding: 10px 20px;
    border-radius: 5px;
    border: 1px solid #ccc;
    background-color: #f1f1f1;
    font-weight: bold;
    color: #333;
    transition: all 0.3s ease;
    cursor: pointer;
    font-size: 16px;
}

.form-options button.selected {
    background-color: #212b36;
    color: white;
    border-color: #3a3f51;
}

.form-options button:hover {
    background-color: #37404a;
    color: white;
}

.cover-letter-section {
    width: 100%;
    /* max-width: 600px; */
    margin: 20px auto;
    border-top: 1px solid #ccc;
    padding-top: 20px;
}

.cover-letter-item {
    display: flex;
    justify-content:center;
    margin-bottom: 10px;
}

.cover-letter-item input {
    margin: 0 10px;
    padding: 10px;
    border-radius: 5px;
    border: 1px solid #ccc;
    width: 70%;
}

button {
    padding: 10px 20px;
    background-color: #212b36;
    border: none;
    color: white;
    font-weight: bold;
    cursor: pointer;
    border-radius: 1px;
    transition: background-color 0.3s ease;
    font-size: 16px;
}

button:hover {
    background-color: #37404a;
}

.save-button {
    background-color: #212b36;
    color: white;
    border-radius: 5px;
    padding: 10px 30px;
    margin-top: 20px;
    font-size: 16px;
}

.save-button:hover {
    background-color: #37404a;
}
</style>