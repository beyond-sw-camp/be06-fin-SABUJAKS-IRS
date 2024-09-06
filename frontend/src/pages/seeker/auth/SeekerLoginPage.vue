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

<style scoped>
html {
    overflow-y: scroll
}

body,
div,
dl,
dt,
dd,
ul,
ol,
li,
h1,
h2,
h3,
h4,
h5,
h6,
form,
fieldset,
input,
textarea,
button,
p,
table,
th,
td {
    padding: 0;
    margin: 0
}

li {
    list-style: none
}

form,
fieldset,
button {
    border: 0 none
}

label {
    cursor: pointer;
    vertical-align: middle
}

input[type=text] {
    outline: none
}

input[type=text]:-ms-clear {
    display: none
}

button {
    overflow: visible;
    background: rgba(0, 0, 0, 0);
    cursor: pointer
}

.skip,
.blind {
    position: absolute;
    overflow: hidden;
    width: 1px;
    height: 1px;
    clip: rect(0 0 0 0);
    margin: -1px
}

a {
    color: #494949;
    text-decoration: none;
    cursor: pointer
}

a:link {
    color: #494949;
    text-decoration: none;
    cursor: pointer
}

a:visited {
    color: #494949;
    text-decoration: none;
    cursor: pointer
}

a:focus,
a:focus-visible,
input:focus,
input:focus-visible {
    outline: none
}

.skip,
.blind {
    clip: unset;
    margin: unset
}

.skip,
.blind,
legend {
    position: absolute;
    top: -5000em;
    width: 0px;
    height: 0px;
    overflow: hidden;
    line-height: 0;
    font-size: 0px;
    letter-spacing: 0px
}

a {
    color: #6a6a6a
}

a:link,
a:visited {
    color: #6a6a6a
}

a:hover,
a:active,
a:focus {
    color: #06c
}

#login.bg-gray {
    background-color: #f8f8f8
}

#login #wrap {
    width: 100%
}

#login #wrap #footer {
    width: 868px;
    text-align: center;
    padding: 18px 0;
    border: unset !important;
    margin-top: unset !important;
    background: #f8f8f8 !important
}

#login #wrap #footer #copyright {
    font-size: 13px;
    letter-spacing: 0px;
    line-height: 24px;
    font-weight: 400;
    color: #9e9e9e
}

#login #container {
    background: #f8f8f8
}

#login .logtop {
    position: relative;
    width: 1030px;
    margin: 0 auto 20px;
    padding-top: 30px
}

#login .logtop .lnb {
    display: -webkit-box;
    display: -webkit-flex;
    display: -moz-box;
    display: flex;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -moz-box-align: center;
    align-items: center;
    gap: 20px;
    color: #6a6a6a;
    font-size: 14px;
    letter-spacing: 0px;
    font-style: normal;
    font-weight: 400;
    line-height: 22px;
    -webkit-box-pack: end;
    -webkit-justify-content: end;
    -moz-box-pack: end;
    justify-content: end
}

#login .logtop .lnb a {
    font-size: 14px;
    letter-spacing: 0px
}

#login .logtop .lnb .txtBar {
    color: #ccc;
    padding: 0 5px
}

#login .secLogin {
    width: 1030px;
    margin: 0 auto;
    padding: 0 120px;
    background: #fff;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box
}

#login .secLogin:after {
    content: "";
    display: flex;
    height: 0;
    clear: both
}

#login .secLogin .hd {
    color: #000;
    text-align: center;
    font-size: 16px;
    letter-spacing: 0px;
    font-style: normal;
    font-weight: 400;
    line-height: 24px;
    display: -webkit-box;
    display: -webkit-flex;
    display: -moz-box;
    display: flex;
    -webkit-box-orient: vertical;
    -webkit-box-direction: normal;
    -webkit-flex-direction: column;
    -moz-box-orient: vertical;
    -moz-box-direction: normal;
    flex-direction: column;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -moz-box-align: center;
    align-items: center
}

#login .secLogin .hd .logo {
    display: inline-block;
    width: 300px;
    height: 300px;
    background: url(/src/assets/img/irs_black.png) no-repeat;
    background-size: contain
}

#login .secLogin .logWrap {
    margin: 0 auto;
    width: 400px;
    border-right: 1px solid #f4f4f4
}

.login-form {
    min-height: 270px
}

.login-form input {
    -webkit-appearance: none
}

.login-form input::-webkit-input-placeholder {
    font-weight: 400;
    color: #9e9e9e
}

.login-form .login-input {
    position: relative;
    text-align: left
}

.login-form .login-input .inpTxt {
    width: -webkit-calc(100% - 114px - 12px);
    width: -moz-calc(100% - 114px - 12px);
    width: calc(100% - 114px - 12px);
    color: #333;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    display: -webkit-box;
    display: -webkit-flex;
    display: -moz-box;
    display: flex;
    height: 48px;
    padding: 0px 16px 0px 48px;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -moz-box-align: center;
    align-items: center;
    border-radius: 8px;
    border: 1px solid #d2d2d2;
    background: #fff;
    font-size: 14px;
    letter-spacing: 0px;
    font-weight: 400;
    line-height: 22px
}

.login-form .login-input .inpTxt:focus {
    border-color: #222
}

.login-form .login-input .input-id {
    margin-bottom: 12px
}

.login-form .login-input .input-id.corp {
    margin-bottom: 12px
}

.login-form .login-input .input-password {
    margin-bottom: 12px
}

.login-form .login-input .label-form {
    display: block;
    width: 48px;
    height: 48px;
    position: absolute;
    text-indent: -9999px;
    overflow: hidden
}

.login-form .login-input .label-id {
    background: url(../../../assets/svg/system_my.svg) no-repeat 16px 12px
}

.login-form .login-input .label-password {
    background: url(../../../assets/svg/system_lock_closed.svg) no-repeat 16px 12px
}

.login-form .login-input .login-button {
    position: absolute;
    top: 0;
    right: 0;
    width: 114px;
    height: 108px;
    display: -webkit-box;
    display: -webkit-flex;
    display: -moz-box;
    display: flex;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -moz-box-pack: center;
    justify-content: center;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -moz-box-align: center;
    align-items: center;
    border-radius: 8px;
    background: #212b36;
    color: #fff;
    text-align: center;
    font-size: 16px;
    letter-spacing: 0px;
    font-weight: 500;
    line-height: 22px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box
}

.login-form .login-input .text-error {
    display: none
}

.login-form .login-input.error input {
    border-color: red
}

.login-form .login-input.error .input-password {
    margin-bottom: unset
}

.login-form .login-input.error .text-error {
    display: block;
    color: red;
    font-size: 12px;
    letter-spacing: 0px;
    font-weight: 400;
    line-height: 18px;
    margin: 8px 0
}

.login-form .login-input .login-capslock {
    display: none;
    position: absolute;
    left: 0px;
    top: 107px;
    font-size: 12px;
    letter-spacing: 0px;
    color: #666;
    padding: 5px 5px 6px 5px;
    border: 1px solid #9ea7bc;
    background: #fff;
    white-space: nowrap;
    z-index: 1
}

.login-form .login-input .login-capslock.on {
    display: block
}

.login-form .login-input .login-capslock em {
    font-weight: normal;
    color: #39f
}

/* .login-form .login-input .login-capslock .mainIcn {
    display: inline-block;
    background: url("/content/images/main/main_sp_icn.png?2018041814") no-repeat;
    line-height: normal;
    vertical-align: middle
} */

.login-form .login-input .login-capslock .mainIcnArrUp {
    position: absolute;
    left: 22px;
    top: -5px;
    width: 7px;
    height: 5px;
    background-position: -30px 0
}

.login-form .login-convenience {
    position: relative;
    text-align: left;
    color: #222;
    font-size: 14px;
    letter-spacing: 0px;
    font-weight: 400;
    line-height: 22px;
    display: -webkit-box;
    display: -webkit-flex;
    display: -moz-box;
    display: flex;
    -webkit-box-pack: justify;
    -webkit-justify-content: space-between;
    -moz-box-pack: justify;
    justify-content: space-between;
    padding-bottom: 12px;
    -webkit-box-shadow: 0 1px #f4f4f4;
    box-shadow: 0 1px #f4f4f4;
    margin-top: 12px
}

.login-form .login-convenience .login-convenience-keep {
    display: -webkit-box;
    display: -webkit-flex;
    display: -moz-box;
    display: flex;
    -webkit-box-align: center;
    -webkit-align-items: center;
    -moz-box-align: center;
    align-items: center;
    gap: 8px
}

.login-form .login-convenience .login-convenience-keep .keep-id {
    cursor: pointer
}

.login-form .login-convenience .login-convenience-keep .keep-id label {
    position: relative;
    color: #222;
    font-size: 14px;
    letter-spacing: 0px;
    font-style: normal;
    font-weight: 400;
    line-height: 22px;
    padding-left: 32px
}

.login-form .login-convenience .login-convenience-keep .keep-id input[type=checkbox] {
    border: none
}

.login-form .login-convenience .login-convenience-keep .keep-id input[type=checkbox]:checked+label::before {
    background-image: url("../../../assets/svg/checkedbox_circle.svg")
}

.login-form .login-convenience .login-convenience-keep .keep-id label::before {
    content: "";
    cursor: pointer;
    display: inline-block;
    position: absolute;
    top: -4px;
    left: 0px;
    width: 24px;
    height: 24px;
    border: none;
    background-image: url("../../../assets/svg/checkBox_circle.svg");
    background-size: cover
}


.login-form .login-social {
    display: table;
    width: auto;
    margin: 0 auto 0;
    padding-top: 20px
}

.login-form .login-social ul:after {
    content: "";
    display: block;
    clear: both
}

.login-form .login-social li {
    float: left
}

.login-form .login-social li:nth-child(n+2) {
    margin-left: 20px
}

.login-form .login-social li a {
    display: block;
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background: no-repeat center;
    background-size: 100%;
    font-size: 0px;
    letter-spacing: 0px;
    text-indent: -9999px
}

.login-form .login-social li a.naver {
    background-image: url("../../../assets/svg/sns_naver_large.svg");
    background-repeat: no-repeat
}

.login-form .login-social li a.kakao {
    background-image: url("../../../assets/svg/sns_kakao_large.svg");
    background-repeat: no-repeat
}



.login-form .login-social li a.google {
    background-image: url("../../../assets/svg/sns_google_large.svg");
    background-repeat: no-repeat
}


.login-form .login-join {
    position: relative;
    padding-top: 20px;
    text-align: center;
    display: -webkit-box;
    display: -webkit-flex;
    display: -moz-box;
    display: flex;
    -webkit-box-pack: center;
    -webkit-justify-content: center;
    -moz-box-pack: center;
    justify-content: center;
    gap: 16px
}

.login-form .login-join a {
    font-size: 14px;
    letter-spacing: 0px
}

.login-form .login-join a:last-child {
    color: #0057ff
}
</style>