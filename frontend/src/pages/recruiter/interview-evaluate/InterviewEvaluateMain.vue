<template>
  <div>
    <MainHeaderComponent></MainHeaderComponent>
    <div class="wrapper">
      <MainSideBarComponent></MainSideBarComponent>
      <div class="container">
        <button @click="openCreateFormModal">면접 평가표 생성</button>
        <div v-if="isModalVisible" class="modal-wrapper" @click="closeCreateFormModal">
          <div class="modal-container" @click.stop>
            <span class="modal-closebtn" @click="closeCreateFormModal">×</span>
            <h2></h2>
            <label>평가항목 1</label>
            <input v-model="q1" type="text" placeholder="면접평가항목을 입력해주세요" />
            
            <label>평가항목 2</label>
            <input v-model="q2" type="text" placeholder="면접평가항목을 입력해주세요" />
            
            <label>평가항목 3</label>
            <input v-model="q3" type="text" placeholder="면접평가항목을 입력해주세요" />
            
            <label>평가항목 4</label>
            <input v-model="q4" type="text" placeholder="면접평가항목을 입력해주세요" />
            
            <label>평가항목 5</label>
            <input v-model="q5" type="text" placeholder="면접평가항목을 입력해주세요" />
            
            <label>평가항목 6</label>
            <input v-model="q6" type="text" placeholder="면접평가항목을 입력해주세요" />
            
            <label>평가항목 7</label>
            <input v-model="q7" type="text" placeholder="면접평가항목을 입력해주세요" />
            
            <label>평가항목 8</label>
            <input v-model="q8" type="text" placeholder="면접평가항목을 입력해주세요" />
            
            <label>평가항목 9</label>
            <input v-model="q9" type="text" placeholder="면접평가항목을 입력해주세요" />
            
            <label>평가항목 10</label>
            <input v-model="q10" type="text" placeholder="면접평가항목을 입력해주세요" />

            <button class="moadl-submitbtn" @click="handleCreateForm">면접 평가표 등록</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
  
  <script setup>
import MainHeaderComponent from "../../../components/recruiter/MainHeaderComponent.vue";
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
import { ref } from "vue";
import { UseInterviewEvaluateStore } from "@/stores/UseInterviewEvaluateStore";
const interviewEvaluateStore = UseInterviewEvaluateStore();
const isModalVisible = ref(false); // 모달 표시 여부를 관리하는 상태
const announceIdx = ref()
const q1 = ref('')
const q2 = ref('')
const q3 = ref('')
const q4 = ref('')
const q5 = ref('')
const q6 = ref('')
const q7 = ref('')
const q8 = ref('')
const q9 = ref('')
const q10 = ref('')


const openCreateFormModal = () => { 
    isModalVisible.value = true;
    announceIdx.value = 1;
};
const closeCreateFormModal = () => { isModalVisible.value = false; };

const handleCreateForm = async() => {    
    const requestBody = {
        announceIdx: announceIdx.value,
        q1: q1.value,
        q2: q2.value,
        q3: q3.value,
        q4: q4.value,
        q5: q5.value,
        q6: q6.value,
        q7: q7.value,
        q8: q8.value,
        q9: q9.value,
        q10: q10.value,
    }
    await interviewEvaluateStore.createForm(requestBody);
}

</script>
  
  <style>
.wrapper {
  width: 80%;
  margin: 0 auto;
}

.container {
  width: 80%;
  flex: 1;
  margin: 0 auto;
  margin-left: 200px;
  padding: 150px 0;
  display: flex;
  flex-direction: column;
  box-sizing: border-box;
}

.modal-wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 9;
}

.modal-container {
  background-color: white;
  padding: 20px;
  border-radius: 8px;
  width: 80%;
  height: 80%;
  overflow-y: scroll;
}

.modal-container h2 {
  margin: 0 0 20px;
}

.modal-container label {
  display: block;
  margin: 15px 0;
  font-size: 1.1rem;
  font-weight: 600;
}

.modal-container input {
  width: calc(100% - 40px);
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.modal-closebtn {
  color: #aaa;
  float: right;
  font-size: 28px;
  font-weight: bold;
}

.moadl-submitbtn {
    width: 100%;
    margin-top: 20px;
    padding: 10px;
    background-color: #232b16;
    color: white;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
</style>
  