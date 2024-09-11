<template>
<div>
    <div class="vip-wrapper">
        <div class="vip-video">
            <img class="vip-img" src="../../../assets/img/irs_black.png" alt="">
            <img class="vip-img" src="../../../assets/img/irs_black.png" alt="">
            <img class="vip-img" src="../../../assets/img/irs_black.png" alt="">
        </div>
        <br>
        <br>
        <br>
        <div class="vip-video">
            <img class="vip-img" src="../../../assets/img/irs_black.png" alt="">
        </div>
    </div>
</div>
</template>

<script setup>
</script>

<style scoped>
.vip-wrapper {
    position: absolute;
    width: 100%;
    height: 100%;
    padding: 0;
    display: flex;
    flex-direction: column;
    margin: 0 auto;
}

.vip-video {
    width: 100%;
    margin-top: 110px;
    display: flex;
    justify-content: center;
}

.vip-img {
    width: fit-content;
    height: 300px;
    border: 1px solid black;
}
</style>