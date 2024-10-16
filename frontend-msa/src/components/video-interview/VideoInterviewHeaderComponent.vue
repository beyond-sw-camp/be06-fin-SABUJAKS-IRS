<template>
<div class="header">
    <a href="/">
      <img class="header-logo" src="../../assets/img/irs_white.png">
    </a>
</div>
</template>

<script setup>
</script>

<style scoped>
.header {
  background-color: #f8f9fa; 
  color: #333; 
  padding: 20px; 
  text-align: center; 
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100px;
  width: 100%;
  background-color: #212b36;
  padding: 0 20px;
  box-sizing: border-box;
  position: fixed;
  z-index: 2;
}

.header-logo {
    color: white;
    font-size: 24px;
    font-weight: bold;
    width: 150px;
}
</style>