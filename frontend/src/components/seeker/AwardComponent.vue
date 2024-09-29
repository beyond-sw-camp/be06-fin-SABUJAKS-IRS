<template>
    <div class="form-container">
        <div class="form-grid">
            <div class="form-item name-item">
                <label for="awardName">수상명 <span class="required">*</span></label>
                <input type="text" id="awardName" v-model="awardName" />
            </div>
            <div class="form-item">
                <label for="organization">수여기관</label>
                <input type="text" id="organization" v-model="organization" />
            </div>
    
            <div class="form-item">
                <label for="year">수상연도</label>
                <input type="text" id="year" v-model="year" placeholder="2023" maxlength="4" class="small-input"
                @input="$event.target.value = $event.target.value.replace(/[^0-9]/g, '')" />
            </div>
    
            <div class="form-item contents-item">
                <label for="contents">수여내용</label>
                <textarea id="contents" v-model="contents" class="expandable-textarea" rows="3" placeholder="수여 내용 및 결과를 자세히 입력해주세요."></textarea>
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

const award = props.data || {
    awardName: null,
    organization: null,
    year: null,
    contents: null
};

const awardName = ref(award.awardName);
const organization = ref(award.organization);
const year = ref(award.year);
const contents = ref(award.contents);

const emit = defineEmits(['updateData']);
emit('updateData', props.data);


const updateData = () => {
  const data = {
    awardName: awardName.value,
    organization: organization.value,
    year: year.value,
    contents: contents.value
  };
  emit('updateData', data);
};

watch(
  [awardName, organization, year, contents],
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
