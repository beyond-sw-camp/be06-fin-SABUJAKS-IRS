<template>
<div class="header">
    <a href="/"><img class="header-logo" src="../../assets/img/irs_white.png"></a>
</div>
</template>

<script setup>
</script>

<style scoped>
/* video interview main header -> vimh */
.header {
  background-color: #f8f9fa; /* 배경색 */
  color: #333; /* 글자색 */
  padding: 20px; /* 안쪽 여백 */
  text-align: center; /* 텍스트 정렬 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
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