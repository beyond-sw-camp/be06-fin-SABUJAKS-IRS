<template>
    <div class="sidebar">
        <!-- 메이페이지 내 좌측 사이드바 -->
        <div class="profile">
            <img src="@/assets/img/profile_photo_ex.png" alt="프로필 사진" class="profile-img">
        </div>
        <nav>
            <ul>
                <li><a href="#">홈</a></li>
                <li><a href="#">지원서 등록</a></li>
                <li><a href="#">공고별 지원서 관리</a></li>
                <li><a href="#">통합 지원서 관리</a></li>
                <li><a href="#">알림 관리</a></li>
                <li><a href="#">일정 관리</a></li>
            </ul>
        </nav>
    </div>
</template>

<script setup>

</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
    /* box-sizing: border-box; */
}

.sidebar {
    width: 20%;
    height: 400px;
    background-color: #f9fafb;
    padding: 20px;
    border-radius: 10px;
    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);

}

.profile {
    text-align: center;
}

.profile-img {
    width: 100px;
    height: 100px;
    border-radius: 50%;
    margin-bottom: 10px;
}

.btn {
    background-color: #212b36;
    color: white;
    padding: 10px 20px;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    margin-top: 10px;
}

.btn:hover {
    background-color: #212b36;
}

nav ul {
    list-style: none;
    margin-top: 20px;
}

nav ul li {
    margin-bottom: 15px;
}

nav ul li a {
    text-decoration: none;
    color: #333;
    font-size: 16px;
}
</style>