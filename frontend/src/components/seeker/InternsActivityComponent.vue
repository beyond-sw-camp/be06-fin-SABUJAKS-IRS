<template>
    <div class="form-container">
        <div class="form-grid">
            <div class="form-item">
                <label for="activityDiv">활동구분 <span class="required">*</span></label>
                <select id="activityDiv" v-model="activityDiv">
                    <option disabled value="">선택해주세요</option>
                    <option value="인턴">인턴</option>
                    <option value="아르바이트">아르바이트</option>
                    <option value="동아리">동아리</option>
                    <option value="자원봉사">자원봉사</option>
                    <option value="사회활동">사회활동</option>
                    <option value="교내활동">교내활동</option>
                    <option value="기타">기타</option>
                </select>
            </div>
            <div class="form-item name-item">
                <label for="organization">회사/기관/단체명 <span class="required">*</span></label>
                <input type="text" id="organization" v-model="organization" />
            </div>
    
            <div class="form-item form-grid-2">
                <div class="form-item">
                <label for="startAt">시작년월</label>
                <input type="text" id="startAt" v-model="startAt" maxlength="7" placeholder="2023.03" class="small-input" 
                @input="$event.target.value = $event.target.value.replace(/[^0-9.]/g, '')"/>
                </div>
                <div class="form-item">
                <label for="endAt">종료년월</label>
                <input type="text" id="endAt" v-model="endAt" maxlength="7" placeholder="2023.03" class="small-input"
                @input="$event.target.value = $event.target.value.replace(/[^0-9.]/g, '')" />
                </div>
            </div>
    
            <div class="form-item contents-item">
                <label for="contents">활동내용</label>
                <textarea id="contents" v-model="contents" class="expandable-textarea" rows="3" placeholder="직무와 관련된 경험에 대해 (상황 - 노력 - 결과)순으로 작성하는 것이 좋습니다."></textarea>
            </div>        
        </div>
    </div>
</template>

<script setup>
import { ref, defineEmits, watch, defineProps } from 'vue';

const props = defineProps({
    data: {
        type: Object
    }
});

const internsActivity = props.data || {
    activityDiv: '',
    organization: null,
    startAt: null,
    endAt: null,
    contents: null
};

const activityDiv = ref(internsActivity.activityDiv);
const organization = ref(internsActivity.organization);
const startAt = ref(internsActivity.startAt);
const endAt = ref(internsActivity.endAt);
const contents = ref(internsActivity.contents);

const emit = defineEmits(['updateData']);
emit('updateData', props.data);


const updateData = () => {
  const data = {
    activityDiv: activityDiv.value,
    organization: organization.value,
    startAt: startAt.value,
    endAt: endAt.value,
    contents: contents.value
  };
  emit('updateData', data);
};

watch(
  [activityDiv, organization, startAt, endAt, contents],
  updateData,
);

</script>
    
<style scoped>
.form-container {
    padding: 15px;
    border: 1px solid #e0e0e0;
    max-width: 800px;
    background-color: #ffffff;
}

.form-grid {
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-gap: 15px;
}

.form-item {
    display: flex;
    flex-direction: column;
}

.form-item label {
    margin-bottom: 5px;
}

.form-item input,
.form-item select {
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
}

.required {
    color: red;
}

.form-grid-2 {
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    grid-gap: 15px;
}

.small-input {
    width: 80px;
}

.name-item {
    grid-column: span 2;
}

.expandable-textarea {
    display: block;
    width: 100%;
    box-sizing: border-box;
    padding: 8px;
    border: 1px solid #ddd;
    border-radius: 4px;
    resize: vertical; /* 세로 방향으로만 크기 조절 가능 */
}

.contents-item {
    grid-column: span 4;
}

.contents-item .expandable-textarea {
    height: 40px;
}

.contents-item .expandable-textarea:focus {
    height: 80px;
    border-color: #007bff;
}

</style>
    