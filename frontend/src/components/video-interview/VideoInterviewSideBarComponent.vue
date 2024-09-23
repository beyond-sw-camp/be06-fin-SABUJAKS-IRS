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
  height: 100%;
  background-color: #212b36;
  color: white;
  padding-top: 20px;
  box-sizing: border-box;
  position: fixed;
  top: 100px;
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