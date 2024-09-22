<template>
    <div class="form-container">
        <div class="form-grid">
            <div class="form-item name-item">
                <label for="trainingName">교육명 <span class="required">*</span></label>
                <input type="text" id="trainingName" v-model="trainingName" />
            </div>
            <div class="form-item">
                <label for="organization">교육기관</label>
                <input type="text" id="organization" v-model="organization" />
            </div>
    
            <div class="form-item form-grid-2">
                <div class="form-item">
                <label for="startAt">시작년월</label>
                <input type="text" id="startAt" v-model="startAt" placeholder="2023.03" class="small-input" />
                </div>
                <div class="form-item">
                <label for="endAt">종료년월</label>
                <input type="text" id="endAt" v-model="endAt" placeholder="2023.03" class="small-input" />
                </div>
            </div>
    
            <div class="form-item contents-item">
                <label for="contents">내용</label>
                <textarea id="contents" v-model="contents" class="expandable-textarea" rows="3" placeholder="이수하신 교육과정에 대해 적어주세요."></textarea>
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

const training = props.data || {
    trainingName: null,
    organization: null,
    startAt: null,
    endAt: null,
    contents: null
};

const trainingName = ref(training.trainingName);
const organization = ref(training.organization);
const startAt = ref(training.startAt);
const endAt = ref(training.endAt);
const contents = ref(training.contents);

const emit = defineEmits(['updateData']);
emit('updateData', props.data);


const updateData = () => {
  const data = {
    trainingName: trainingName.value,
    organization: organization.value,
    startAt: startAt.value,
    endAt: endAt.value,
    contents: contents.value
  };
  emit('updateData', data);
};

watch(
  [trainingName, organization, startAt, endAt, contents],
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
    