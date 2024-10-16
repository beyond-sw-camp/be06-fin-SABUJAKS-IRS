<template>
    <div class="form-container">
        <div class="form-grid">
            <div class="form-item name-item">
                <label for="certName">자격증명 <span class="required">*</span></label>
                <input type="text" id="certName" v-model="certName" />
            </div>
            <div class="form-item">
                <label for="organization">발행처</label>
                <input type="text" id="organization" v-model="organization" />
            </div>
    
            <div class="form-item">
                <label for="takingAt">취득월</label>
                <input type="text" id="takingAt" v-model="takingAt" maxlength="7" placeholder="2023.03" class="small-input" 
                @input="$event.target.value = $event.target.value.replace(/[^0-9.]/g, '')"/>
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

const certification = props.data || {
    certName: null,
    organization: null,
    takingAt: null
};

const certName = ref(certification.certName);
const organization = ref(certification.organization);
const takingAt = ref(certification.takingAt);

const emit = defineEmits(['updateData']);
emit('updateData', props.data);


const updateData = () => {
  const data = {
    certName: certName.value,
    organization: organization.value,
    takingAt: takingAt.value
  };
  emit('updateData', data);
};

watch(
  [certName, organization, takingAt],
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

.name-item {
    grid-column: span 2;
}

.small-input {
    width: 80px;
}

</style>
