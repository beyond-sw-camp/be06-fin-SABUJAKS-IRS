<template>
    <div class="form-container">
      <div class="form-grid">
        <div class="form-item title-item">
          <label for="title">제목 <span class="required">*</span></label>
          <input disabled type="text" id="title" v-model="title" placeholder="제목을 입력하세요." />
        </div>
  
        <div class="form-item contents-item">
          <label for="contents">내용 <span class="required">*</span> ({{ charNum }} / {{ customLetterForm.chatLimit }} 자)</label>
          <textarea id="contents" v-model="contents" class="expandable-textarea" rows="3" placeholder="해당 내용을 입력하세요."></textarea>
        </div>
      </div>
    </div>
  </template>
  
<script setup>
import { ref, computed, defineEmits, watch, defineProps } from 'vue';

const props = defineProps({
    data: {
        type: Object
    }
});

const customLetterForm = props.data || {
    title: null,
    chatLimit: null,
    charNum: 0
};

const charNum = computed(() => contents.value.length);
const title = ref(customLetterForm.title);
const contents = ref('');

const emit = defineEmits(['updateData']);
emit('updateData', props.data);


const updateData = () => {
    const data = {
        title: title.value,
        contents: contents.value,
        charNum: charNum.value
    }; 

    emit('updateData', data);
};


// 내용 길이를 제한
watch([title, contents], () => {
  if (contents.value.length > customLetterForm.chatLimit) {
    contents.value = contents.value.slice(0, customLetterForm.chatLimit);
  }
  updateData();
});

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

.title-item {
grid-column: span 4;
}

.expandable-textarea {
display: block;
width: 100%;
box-sizing: border-box;
padding: 8px;
border: 1px solid #ddd;
border-radius: 4px;
resize: vertical;
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
