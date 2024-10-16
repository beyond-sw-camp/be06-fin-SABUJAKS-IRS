<template>
  <div class="login-footer">
    <div class="inner" itemscope="" itemtype="http://schema.org/Organization">
      <a href="/" class="logo" aria-label="사람인 홈">
        <img src="../../assets/img/irs_black.png" style="width: 100px">
      </a>
      <div class="customer">
        <p>IRS 고객센터 <span>02-1234-1234</span> (평일 <span>09:00~19:00</span>, 주말·공휴일 휴무)</p>
        <div class="btns"> 이메일 : <span>tjtlgus4@naver.com</span>, Fax : <span>02-1234-1234</span>(대표),
          <span>02-2222-3333</span>(세금계산서)
        </div>
      </div>
      <div class="copyright">
        <p>(주)사람인, 우 : 08378, 구로구 디지털로 34길 43, 14층, 대표 : 서시현<br>사업자등록 : 113-86-00917, 직업정보제공사업 : 서울 관악 제 2005-6호, 통신판매업
          : 제 2005-02339호<br>Copyright (c) (주)IRS. All rights reserved.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
</script>

<style scoped>
.login-footer {
  display: block;
  border: 0;
  font: inherit;
  vertical-align: baseline;
  margin: 40px 0 0;
  padding: 20px 0;
  border-top: 1px solid #eaeaea;
  text-align: center;
}

.copyright {
  display: inline-block;
  color: #6A6A6A;
  font-weight: 600;
}
</style>