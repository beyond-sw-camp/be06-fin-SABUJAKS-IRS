<template>
  <MainHeaderComponent></MainHeaderComponent>
  <div class="container">
    <MainSideBarComponent></MainSideBarComponent>
    <div id="content">
        <h1>공고 관리</h1>
        <table>
            <tbody>
            <tr>
              <th>번호</th>
              <th>공고 기간</th>
              <th>공고명</th>
              <th>신입/경력</th>
              <th>지원자수</th>
            </tr>
            <tr>
              <td>1</td>
              <td>2024.09.24 - 2024.10.24</td>
              <td>백엔드 엔지니어 신입공채</td>
              <td>신입</td>
              <td>24</td>
            </tr>
            </tbody>
        </table>
        <div id="size-buttons">
            <button>1</button>
            <button>2</button>
            <button>3</button>
        </div>
    </div>
</div>
</template>

<script setup>
import MainHeaderComponent from "@/components/recruiter/MainHeaderComponent.vue";
import MainSideBarComponent from "@/components/recruiter/MainSideBarComponent.vue";
</script>

<style>

</style>