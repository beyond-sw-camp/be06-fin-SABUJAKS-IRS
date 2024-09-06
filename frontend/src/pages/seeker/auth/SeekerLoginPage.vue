<template>

    <body id="login" class="login  bg-gray">


        <div id="wrap">

            <div id="container">
                <div class="logtop logtop_login">
                    <p class="lnb"><a href="http://www.jobkorea.co.kr/">IRS홈</a> <a
                            href="http://www.jobkorea.co.kr/Customer_C/CC_Main.asp">고객센터</a></p>
                </div>

                <div class="secLogin">
                    <h1 class="hd"><span class="logo"></span></h1>
                    <div class="logWrap" id="loginFormWrite">
                        <h2 class="blind">로그인</h2>
                        <fieldset class="login-form">
                            <!-- 로그인 입력 폼 - 에러 처리 <div class="row-input error"> -->
                            <section class="login-input ">
                                <label for="M_ID" id="lb_id" class="label-form label-id">아이디</label>
                                <input type="text" class="inpTxt  input-id" placeholder="아이디" name="M_ID" id="M_ID"
                                    size="16" maxlength="20" title="아이디 입력" style="ime-mode:inactive;" value=""
                                    required="">
                                <label for="M_PWD" id="lb_pw" class="label-form label-password">비밀번호</label>
                                <input type="password" class="inpTxt input-password" placeholder="비밀번호" name="M_PWD"
                                    id="M_PWD" size="16" title="비밀번호 입력" required="">
                                <button type="submit" class="login-button">로그인</button>
                                <div class="text-error"></div>
                                <!-- CapsLock 레이어 - <div class="login-capslock" 에 on 클래스 추가 시 활성화 -->
                                <div class="login-capslock" id="ipNotice">
                                    <em>Caps Lock</em>이 켜져 있습니다.<span class="mainIcn mainIcnArrUp"></span>
                                </div>
                            </section>

                            <!-- 로그인 유지, IP 보안 ON/OFF -->
                            <section class="login-convenience">
                                <div class="login-convenience-keep">
                                    <div class="keep-id" style="display: block;">
                                        <input type="checkbox" name="lb_idSave" id="lb_idSave" value="Y" class="skip">
                                        <label for="lb_idSave">로그인 유지</label>
                                    </div>
                                </div>

                            </section>


                            <section class="login-social">
                                <ul>
                                    <li><a href="javascript:;" id="btnNvLogin" class="naver" onclick="_LA.EVT('4287')"
                                            style="display: block;">네이버 로그인</a></li>
                                    <li><a href="javascript:;" id="btnKaLogin" class="kakao" onclick="_LA.EVT('4286')"
                                            style="display: block;">카카오 로그인</a></li>
                                    <li><a href="javascript:;" id="btnKaLogin" class="google" onclick="_LA.EVT('4288')"
                                            style="display: block;">구글 로그인</a></li>
                                </ul>
                            </section>
                            <section class="login-join">
                                <a id="link_regist" href="https://www.jobkorea.co.kr/Join/M_Regist" target="_new"
                                    data-co="https://www.jobkorea.co.kr/Join/GI_Regist"
                                    data-gg="https://www.jobkorea.co.kr/Join/M_Regist">회원가입</a>
                            </section>
                        </fieldset>
                    </div>
                </div><!-- // content -->
                <!-- container //-->

                <div id="footer">
                    <p id="copyright">ⓒ SABUJAKS LLC. All Rights Reserved.</p>
                </div><!-- footer //-->

            </div>
            <div style="position:absolute; left:-5000px; overflow:hidden;">
                <!-- End Adobe Marketing Cloud Tag Management code -->
            </div>


            <!-- <script type="text/javascript" src="/include/Ssl_Script/Ssl_Bottom.js"></script>
<script>
        /* usage */
        (function ($) {
            $(window).load(function () {
                (function (i, s, o, g, r, a, m) {
                    i['GoogleAnalyticsObject'] = r; i[r] = i[r] || function () {
                        (i[r].q = i[r].q || []).push(arguments)
                    }, i[r].l = 1 * new Date(); a = s.createElement(o),
                        m = s.getElementsByTagName(o)[0]; a.async = 1; a.src = g; m.parentNode.insertBefore(a, m)
                })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

                ga('create', 'UA-75522609-1', 'jobkorea.co.kr', { 'allowLinker': true });
                ga('require', 'linker');
                ga('linker:autoLink', ['jobbusan.co.kr']);
                ga('send', 'pageview');
            });
        })(jQuery);
    </script>
    <script type="text/javascript">
    </script> -->
        </div>
    </body>

</template>

<script setup>
</script>

<style></style>