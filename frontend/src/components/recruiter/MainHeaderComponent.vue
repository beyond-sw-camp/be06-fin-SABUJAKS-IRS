<script>

</script>

<template>
  <header class="header">
    <div class="logo"><img src="@/assets/img/irs_white.png" style="width: 150px;"></div>
    <div class="user-info">
      <div class="user-name">John Doe</div>
      <button class="logout-button">Logout</button>
    </div>
  </header>
</template>

<style scoped>
header {
  background-color: #f8f9fa; /* 배경색 */
  color: #333; /* 글자색 */
  padding: 20px; /* 안쪽 여백 */
  text-align: center; /* 텍스트 정렬 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1); /* 그림자 효과 */
}

header h1 {
  font-size: 2em; /* 제목 크기 */
  margin: 0; /* 기본 여백 제거 */
}

header nav {
  display: flex; /* 내비게이션 바를 플렉스 박스로 */
  justify-content: center; /* 중앙 정렬 */
}

header nav a {
  margin: 0 15px; /* 링크 사이 간격 */
  text-decoration: none; /* 밑줄 제거 */
  color: #007bff; /* 링크 색상 */
}

header nav a:hover {
  color: #0056b3; /* 호버 시 색상 변화 */
}

.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 100px;
  width: 100%;
  background-color: #212b36;
  padding: 0 20px;
  box-sizing: border-box;
  position: fixed;
  z-index: 999;
}

.logo {
  color: white;
  font-size: 24px;
  font-weight: bold;
}

.user-info {
  display: flex;
  align-items: center;
}

.user-name {
  color: white;
  margin-right: 20px;
  font-size: 18px;
}

.logout-button {
  background-color: #ffffff;
  color: #212b36;
  border: none;
  padding: 10px 20px;
  cursor: pointer;
  font-size: 16px;
}
</style>