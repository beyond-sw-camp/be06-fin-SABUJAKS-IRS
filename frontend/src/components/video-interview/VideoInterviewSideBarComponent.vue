<template>
  <div class="sidebar">
    <ul>
      <li> 화상면접 </li>
    </ul>
  </div>

</template>

<script setup>
</script>

<style scoped>
.sidebar {
  width: 200px;
  height: 100%; /* 사이드바가 전체 높이를 차지하도록 */
  background-color: #212b36;
  color: white;
  padding-top: 20px;
  box-sizing: border-box;
  position: fixed; /* 사이드바를 고정하여 화면 왼쪽 끝까지 차지하도록 */
  top: 100px; /* header 높이만큼 아래에 위치하도록 */
  left: 0;
  border-radius: 0;
} 
.sidebar ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.sidebar li {
  padding: 15px 40px;
  cursor: pointer;
}

.sidebar li:hover {
  background-color: #1a232d;
}
</style>