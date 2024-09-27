<script setup>
import {defineEmits, defineProps} from "vue";

const props = defineProps({
  isGoBack: {
    type: Boolean,
    required: false
  },
});

const emit = defineEmits([
  'goBack',
]);

const goBack = () => {
  emit('loadInterviewScheduleList');
};

</script>

<template>
  <button v-if="!props.isGoBack" @click="goBack" class="custom-back-button">뒤로가기</button>
</template>

<style scoped>
.custom-back-button {
  background-color: #f1f1f1;
  color: white;
  padding: 10px 20px;
  font-size: 16px;
  border: none;
  border-radius: 25px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  transition: background-color 0.3s ease, transform 0.2s ease;
}

.custom-back-button:hover {
  background-color: #e0e0e0;
}

.custom-back-button:active {
  background-color: #cccccc;
}
</style>