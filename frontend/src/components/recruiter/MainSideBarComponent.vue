<script>

</script>

<template>
  <div class="sidebar">
    <ul>
      <li>공고</li>
      <li>지원서</li>
      <li>면접</li>
      <li>마이페이지</li>
    </ul>
  </div>
</template>

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
}

.sidebar img {
  width: 40px;
  margin-bottom: 20px;
}

.sidebar a {
  color: #ffffff;
  text-decoration: none;
  margin: 10px 0;
  font-size: 18px;
}

.sidebar ul {
  list-style-type: none;
  padding: 0;
  margin: 0;
}

.sidebar li {
  padding: 15px 20px;
  cursor: pointer;
}

.sidebar li:hover {
  background-color: #1a232d;
}

</style>