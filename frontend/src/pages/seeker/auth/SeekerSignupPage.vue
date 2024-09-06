<template>
  <div id="body" class="jkJoin">
    <div id="wrap">
        <div id="header">
            <div class="joinTop">
                <h2>
                    <span class="slogun_1">IRS</span>
                </h2>
            </div>

            <ul class="gnb f_clear">
                <li><a href="https://www.jobkorea.co.kr">홈</a></li>
                <li><a href="/help/">고객센터</a></li>
            </ul>

            <h3 class="skip">회원 형태별 가입</h3>
            <ul class="snb f_clear">
                <li class="person on"><a
                        href="https://www.jobkorea.co.kr/Join/M_Regist?OEM_No=1&amp;Org_OEM_No=1">개인회원</a></li>
                <li class="corp "><a href="https://www.jobkorea.co.kr/Join/GI_Regist?OEM_No=1&amp;Org_OEM_No=1">기업회원</a>
                </li>
            </ul>
        </div>



        <input type="hidden" id="hdnEduId" value="">
        <div id="container" class="mbrRegist">
            <h3 class="skip">개인회원 가입 정보</h3>
            <fieldset>
                <legend>개인회원 가입</legend>
                <form action="/Join/M_Regist" id="frm" method="post" name="frm"> <!-- 약관 동의 -->
                    <input data-val="true" data-val-required="<p class=&quot;failure&quot;>필수 정보입니다.</p>" id="M_Id"
                        name="M_Id" type="hidden" value=""><input id="OEM_No" name="OEM_No" type="hidden"
                        value="1"><input id="DI_Code" name="DI_Code" type="hidden" value=""><input id="getCertifynum"
                        name="getCertifynum" type="hidden" value=""><input id="CertifyReCall" name="CertifyReCall"
                        type="hidden" value=""><input id="ReSubmit" name="ReSubmit" type="hidden" value=""><input
                        id="CertifyCode" name="CertifyCode" type="hidden" value=""><input id="Aptitute_Stat"
                        name="Aptitute_Stat" type="hidden" value=""><input id="CheckParamString" name="CheckParamString"
                        type="hidden" value="8d3bc0d8e98202294a311db74c5eb05d"><input id="Re_Url" name="Re_Url"
                        type="hidden" value=""> <!-- 소셜 로그인 -->
                    <div class="row_group row_group_social">
                        <h4>소셜로 간편하게 로그인하세요</h4>
                        <div class="list-social">
                            <ul>
                                <li><a href="javascript:;" id="btnNvLogin" class="naver" onclick="_LA.EVT('4287')">네이버
                                        로그인</a></li>
                                <li><a href="javascript:;" id="btnKaLogin" class="kakao" onclick="_LA.EVT('4286')">카카오
                                        로그인</a></li>
                                <li><a href="javascript:;" id="btnKaLogin" class="google" onclick="_LA.EVT('4288')">구글
                                        로그인</a></li>
                            </ul>
                        </div>
                    </div>
                    <div class="row_group mbr_info">
                        <h4>회원가입하고 다양한 혜택을 누리세요!</h4>
                        <div class="row mbr_id">
                            <div class="col_1">
                                <label for="idcheck">이메일</label>
                            </div>
                            <div class="col_2">
                                <input type="text" id="idcheck" name="idcheck" maxlength="16" class="dev-id" value=""
                                    style="ime-mode:disabled;">
                                <div class="notice_msg" id="notice_msg_id"></div>
                            </div>
                        </div>
                        <div class="row mbr_passwd">
                            <div class="col_1">
                                <label for="M_Pwd">비밀번호(8~16자의 영문, 숫자, 특수기호)</label>
                            </div>
                            <div class="col_2">
                                <input type="password" id="M_Pwd" name="M_Pwd" class="dev-password" maxlength="16"
                                    style="ime-mode:disabled;">
                                <button type="button" class="btnHelp" title="안전한 비밀번호 작성법">?</button>
                                <div class="lyHelp">
                                    <dl>
                                        <dt>안전한 비밀번호 작성법</dt>
                                        <dd>
                                            <ol>
                                                <li>8~16자의 영문 대소문, 숫자, 특수기호만 사용가능 (공백입력 불가)</li>
                                                (사용 가능한 특수문자는 ! "# $ % &amp; ( ) * + , - . / : ; ? @ [ ] ^ _ { } ~ 입니다.)
                                                <li>3자 이상 연속 영문/숫자 조합은 사용불가 (AAA. 111)</li>
                                                <li>아이디, 반복되는 영문/숫자 조합은 사용불가 (1234, ABCD)</li>
                                                <li>키보드의 연속 패턴은 사용하지 마세요. (ASDF) </li>
                                                <li>비밀번호는 주기적으로 변경하여 안전하게 관리하기</li>
                                            </ol>
                                        </dd>
                                    </dl>
                                </div>
                                <div class="notice_msg" id="notice_msg_pwd"></div>
                            </div>
                            <div class="col_3">
                                <button type="button" class="mbrBtnAuth dev-password-dp"><span>표시</span></button>
                            </div>
                        </div>
                        <div class="row mbr_name">
                            <div class="col_1">
                                <label for="M_Name" class="mbr_name">이름(실명)</label>
                            </div>
                            <div class="col_2">
                                <input type="text" id="M_Name" name="M_Name" class="mbr_name devReadOnly dev-name"
                                    maxlength="12">
                                <div class="notice_msg" id="notice_msg_name"></div>
                            </div>
                        </div>
                        <div class="row mbr_nickname">
                            <div class="col_1">
                                <label for="M_Nickname" class="mbr_nickname">닉네임</label>
                            </div>
                            <div class="col_2">
                                <input type="text" id="M_Nickname" name="M_Nickname" class="mbr_nickname devReadOnly dev-nickname"
                                    maxlength="12">
                                <div class="notice_msg" id="notice_msg_nickname"></div>
                            </div>
                        </div>
                        <div class="row mbr_age">
                            <div class="col_1">
                                <label for="Born" class="mbr_age">생년월일(예시 : 20000131)</label>
                            </div>
                            <div class="col_2">
                                <input type="text" id="Born" name="Born" class="mbr_age devReadOnly dev-born"
                                    maxlength="8">
                                <div class="notice_msg" id="notice_msg_age"></div>
                            </div>
                            <div class="col_3">
                                <div class="select">
                                    <input type="radio" id="boy" name="M_Gender" value="0" class="dev-gender"><label
                                        for="boy">남자</label>
                                    <input type="radio" id="girl" name="M_Gender" value="1" class="dev-gender"><label
                                        for="girl">여자</label>
                                </div>
                                <div class="notice_msg" id="notice_msg_gender"></div>
                            </div>
                        </div>
                        <div class="row mbr_phone">
                            <div class="col_1">
                                <label for="M_Phone">휴대폰번호</label>
                            </div>
                            <div class="col_2">
                                <input type="text" id="M_Phone" name="M_Phone" size="4" maxlength="13"
                                    class="dev-phone">
                                <div class="notice_msg" id="notice_msg_phone"></div>
                                <input type="hidden" id="M_Phone1" name="M_Phone1">
                                <input type="hidden" id="M_Phone2" name="M_Phone2">
                                <input type="hidden" id="M_Phone3" name="M_Phone3">
                            </div>
                        </div>
                    </div>
                    <h4 class="skip">약관 동의</h4>
                    <div class="row_group line_all policy">
                        <div class="row policy_check_all">
                            <input type="checkbox" id="lb_chk_all" class="mbrCheckOff">
                            <label for="lb_chk_all" class="chk_all"><span class="txt">필수동의 항목 및 개인정보 수집 및 이용 동의(선택), 광고성
                                    정보 수신<br>(선택)에 모두 동의합니다.</span></label>
                        </div>
                        <div class="row policy_check_service required">
                            <input type="checkbox" id="lb_chk_age" name="Y15_Older_Agree" class="mbrCheckOn" value="1">
                            <label for="lb_chk_age" class="chk_age"><strong>[필수]</strong> 만 15세 이상입니다 </label>
                        </div>
                        <div class="row policy_check_service required">
                            <input type="checkbox" id="lb_chk_service" name="Service_Agree" class="mbrCheckOn"
                                value="1">
                            <label for="lb_chk_service" class="chk_service"><strong>[필수]</strong> 이용약관 동의 <a
                                    href="#DevPolicyService" class="mbrBtnPolicy">내용보기</a></label>
                            <div id="DevPolicyService" class="policyTplBox">
                                <div class="pvsSec pvsCntTp">
                                    <ol>
                                        <li>
                                            <dl>
                                                <dt><a name="gg01" id="gg01"></a><strong>제 1 조 (목적) </strong></dt>
                                                <dd>본 약관은 잡코리아(이하 "회사")가 운영하는 "서비스"를 이용함에 있어 "회사"와 회원간의 이용 조건 및 제반 절차,
                                                    권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 한다.</dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg02" id="gg02"></a><strong>제 2 조 (용어의 정의) </strong></dt>
                                                <dd>
                                                    <p>이 약관에서 사용하는 용어의 정의는 아래와 같다.</p>
                                                    <ol>
                                                        <li>
                                                            ① "사이트"라 함은 회사가 서비스를 "회원"에게 제공하기 위하여 컴퓨터 등 정보통신설비를 이용하여 설정한
                                                            가상의 영업장 또는 회사가 운영하는 웹사이트, 모바일웹, 앱 등의 서비스를 제공하는 모든 매체를 통칭하며,
                                                            통합된 하나의 회원 계정(아이디 및 비밀번호)을 이용하여 서비스를 제공받을 수 있는 아래의 사이트를 말한다.
                                                            <ul>
                                                                <li>- www.jobkorea.co.kr</li>
                                                                <li>- www.albamon.com</li>
                                                                <li>- m.jobkorea.co.kr</li>
                                                                <li>- m.albamon.com</li>
                                                                <li>- learning.jobkorea.co.kr</li>
                                                            </ul>
                                                        </li>
                                                        <li>② "서비스"라 함은 회사가 운영하는 사이트를 통해 개인이 구직, 교육 등의 목적으로 등록하는 자료를
                                                            DB화하여 각각의 목적에 맞게 분류 가공, 집계하여 정보를 제공하는 서비스와 사이트에서 제공하는 모든 부대
                                                            서비스를 말한다.</li>
                                                        <li>③ "회원"이라 함은 "회사"가 제공하는 서비스를 이용하거나 이용하려는 자로, 페이스북 등 외부 서비스
                                                            연동을 통해 "회사"와 이용계약을 체결한자 또는 체결하려는 자를 포함하며, 아이디와 비밀번호의 설정 등
                                                            회원가입 절차를 거쳐 회원가입확인 이메일 등을 통해 회사로부터 회원으로 인정받은 "개인회원"을 말한다.
                                                        </li>
                                                        <li>④ "아이디"라 함은 회원가입 시 회원의 식별과 회원의 서비스 이용을 위하여 "회원"이 선정하고 "회사"가
                                                            부여하는 문자와 숫자의 조합을 말한다.</li>
                                                        <li>⑤ "비밀번호"라 함은 위 제4항에 따라 회원이 회원가입시 아이디를 설정하면서 아이디를 부여받은 자와
                                                            동일인임을 확인하고 "회원"의 권익을 보호하기 위하여 "회원"이 선정한 문자와 숫자의 조합을 말한다.
                                                        </li>
                                                        <li>⑥ "비회원"이라 함은 회원가입 절차를 거치지 않고 "회사"가 제공하는 서비스를 이용하거나 하려는 자를
                                                            말한다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg03" id="gg03"></a><strong>제 3 조 (약관의 명시와 개정) </strong>
                                                </dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회사"는 이 약관의 내용과 상호, 영업소 소재지, 대표자의 성명, 사업자등록번호, 연락처 등을
                                                            "회원"이 알 수 있도록 초기 화면에 게시하거나 기타의 방법으로 "회원"에게 공지해야 한다.</li>
                                                        <li>② "회사"는 약관의 규제에 관한 법률, 전기통신기본법, 전기통신사업법, 정보통신망 이용촉진 및 정보보호
                                                            등에 관한 법률 등 관련법을 위반하지 않는 범위에서 이 약관을 개정할 수 있다.</li>
                                                        <li>③ "회사"가 약관을 개정할 경우에는 적용일자 및 개정사유를 명시하여 현행약관과 함께 그 개정약관의 적용일자
                                                            7일 전부터 적용일자 전일까지 공지한다. 단 "회원"의 권리, 의무에 중대한 영향을 주는 변경의 경우에는
                                                            적용일자 30일 전부터 공지하도록 한다.</li>
                                                        <li>④ "회원"은 변경된 약관에 대해 거부할 권리가 있다. "회원"은 변경된 약관이 공지된 지 15일 이내에
                                                            거부의사를 표명할 수 있다. "회원"이 거부하는 경우 본 서비스 제공자인 "회사"는 15일의 기간을 정하여
                                                            "회원"에게 사전 통지 후 당해 "회원"과의 계약을 해지할 수 있다. 만약, "회원"이 거부의사를 표시하지
                                                            않거나, 전항에 따라 시행일 이후에 "서비스"를 이용하는 경우에는 동의한 것으로 간주한다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg04" id="gg04"></a><strong>제 4 조 (약관의 해석) </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>① 이 약관에서 규정하지 않은 사항에 관해서는 약관의 규제에 관한 법률, 전기통신기본법, 전기통신사업법,
                                                            정보통신망 이용촉진 및 정보보호 등에 관한 법률 등의 관계법령에 따른다.</li>
                                                        <li>② 각 사이트 및 서비스 이용약관이 있는 경우에는 서비스 이용약관이 우선한다.</li>
                                                        <li>③ "회원"이 "회사"와 개별 계약을 체결하여 서비스를 이용하는 경우에는 개별 계약이 우선한다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg05" id="gg05"></a><strong>제 5 조 (이용계약의 성립) </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회사"의 서비스 이용계약(이하 "이용계약")은 서비스를 이용하고자 하는 자가 본 약관과
                                                            개인정보처리방침을 읽고 "동의" 또는 "확인" 버튼을 누른 경우 본 약관에 동의한 것으로 간주한다.</li>
                                                        <li>② 제1항 신청에 있어 "회사"는 "회원"의 종류에 따라 전문기관을 통한 실명확인 및 본인인증을 요청할 수
                                                            있으며, "회원"은 본인인증에 필요한 이름, 생년월일, 연락처 등을 제공하여야 한다.</li>
                                                        <li>③ "회원"이 이용신청(회원가입 신청) 작성 후에 "회사"가 웹상의 안내 및 전자메일로 "회원"에게
                                                            통지함으로써 이용계약이 성립된다.</li>
                                                        <li>④ 페이스북 등 외부 서비스와의 연동을 통해 이용계약을 신청할 경우, 본 약관과 개인정보처리방침, 서비스
                                                            제공을 위해 "회사"가 "회원"의 외부 서비스 계정 정보 접근 및 활용에 "동의" 또는 "확인" 버튼을
                                                            누르면 "회사"가 웹상의 안내 및 전자메일로 "회원"에게 통지함으로써 이용계약이 성립된다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg06" id="gg06"></a><strong>제 6 조 (이용신청의 승낙과 제한) </strong>
                                                </dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회사"는 전조의 규정에 의한 이용신청 고객에 대하여 업무수행상 또는 기술상 지장이 없는 경우에는
                                                            원칙적으로 접수순서에 따라 서비스 이용을 승낙한다.</li>
                                                        <li>
                                                            ② "회사"는 아래사항에 해당하는 경우에 대해서는 서비스 이용신청을 승낙하지 아니한다.
                                                            <ol>
                                                                <li>1. "회사"가 제시하는 내용을 기재하지 않은 경우</li>
                                                                <li>2. 만 15세 미만의 아동이 신청한 경우</li>
                                                                <li>3. 이용자의 귀책사유로 인하여 승인이 불가능하거나 기타 규정한 제반 사항을 위반하여 신청하는
                                                                    경우</li>
                                                            </ol>
                                                        </li>
                                                        <li>
                                                            ③ "회사"는 아래사항에 해당하는 경우에는 그 신청에 대하여 승낙제한 사유가 해소될 때까지 승낙을 유보할 수
                                                            있다.
                                                            <ol>
                                                                <li>1. "회사"가 설비의 여유가 없는 경우</li>
                                                                <li>2. "회사"의 기술상 지장이 있는 경우</li>
                                                                <li>3. 기타 "회사"의 귀책사유로 이용승낙이 곤란한 경우</li>
                                                            </ol>
                                                        </li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg07" id="gg07"></a><strong>제 7 조 (서비스의 내용) </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>
                                                            ① "회사"는 제2조 2항의 서비스를 제공할 수 있으며 그 내용은 다음 각 호와 같다.
                                                            <ol>
                                                                <li>1. 이력서 등록 및 인재정보 게재 서비스</li>
                                                                <li>2. 온라인 입사지원 서비스</li>
                                                                <li>3. 헤드헌팅/아웃소싱 서비스</li>
                                                                <li>4. 구인/구직과 관련된 제반 서비스</li>
                                                                <li>5. 기업리뷰 등록과 조회 서비스</li>
                                                                <li>6. 기업정보 조회와 관련된 제반 서비스</li>
                                                                <li>7. 교육과 관련된 제반 서비스</li>
                                                                <li>8. 이용자간의 교류와 소통에 관련한 서비스</li>
                                                                <li>9. 자료거래에 관련한 서비스</li>
                                                                <li>10. 기타"회사"가 추가 개발, 편집, 재구성하거나 제휴계약 등을 통해 “회원”에게 제공하는
                                                                    일체의 서비스</li>
                                                            </ol>
                                                        </li>
                                                        <li>② "회사"는 필요한 경우 서비스의 내용을 추가 또는 변경할 수 있다. 단, 이 경우 "회사"는 추가 또는
                                                            변경내용을 "회원"에게 공지해야 한다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg08" id="gg08"></a><strong>제 8 조 (회원, 이력서 및 게시물 등의 정보)
                                                    </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회원"의 이력서는 개인이 회원가입 또는 이력서 작성 및 수정시 희망한 형태로 등록 및 제공된다.
                                                        </li>
                                                        <li>② "회사"는 "회원"이 이력서의 인재정보 등록/미등록 지정, 이력서상의 연락처 제공 여부를 자유롭게 선택할
                                                            수 있도록 하여야 한다.</li>
                                                        <li>③ "회사"는 "회원"이 이력서의 인재정보 등록 및 제공을 희망했을 경우, 기업회원, 서치펌회원의 이력서
                                                            열람 및 각종 포지션 제안에 동의한 것으로 간주하며 "회사"는 기업회원, 서치펌회원에게 유료로 이력서 열람
                                                            서비스를 제공할 수 있다. 다만, 연락처 각 항목이 비공개로 지정된 경우 해당 항목별 연락처를 노출할 수
                                                            없다.</li>
                                                        <li>④ "회사"는 안정적인 서비스를 제공하기 위해 테스트 및 모니터링 용도로 "사이트" 운영자가 이력서 및
                                                            게시물 등의 정보를 열람하도록 할 수 있다.</li>
                                                        <li>⑤ "회사"는 "회원"의 선택에 의하여 등록 및 제공되는 이력서 및 게시물 등의 정보를 기준으로 구직활동에
                                                            보다 유익한 서비스를 제공하기 위하여 이를 개발, 편집, 재구성한 통계 자료로 활용 할 수 있다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg9" id="gg9"></a><strong>제 9 조 (제휴를 통한 서비스) </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회사"는 제휴 관계를 체결한 여타 인터넷 웹 사이트 및 채용박람회 또는 신문, 잡지 등의 오프라인
                                                            매체를 통해 사이트에 등록한 "회원"의 이력서 정보가 열람될 수 있도록 서비스를 제공할 수 있다. 단, 제휴
                                                            서비스를 통해 노출되는 이력서의 연락처 정보는 “회원”이 이력서 등록 시 선택한 연락처 공개 여부에 따라
                                                            제공된다.</li>
                                                        <li>② "회사"는 제휴를 통해 타 사이트 및 매체에 등록될 수 있음을 고지해야 하며, 제휴 사이트 목록을
                                                            사이트내에서 상시 열람할 수 있도록 해야 한다. 단, 등록의 형태가 "회사"가 직접 구축하지 않고, 제휴사가
                                                            xml 또는 api 형태로 "회사"로부터 제공 받아 구축한 매체 목록은 본 약관외 별도의 제휴리스트에서
                                                            열람할 수 있도록 한다.</li>
                                                        <li>③ "회사"는 "회원"이 공개를 요청한 이력서에 한해 제휴를 맺은 타 사이트에 이력서 정보를 제공한다. (본
                                                            약관 시행일 현재에는 제휴를 통한 타 사이트 및 매체는 없다.)</li>
                                                        <li>④ 본 조 제 3항 제휴를 통한 사이트의 변동사항 발생 시 공지사항을 통해 고지 후 진행 한다. </li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg10" id="gg10"></a><strong>제 10 조 (서비스의 요금) </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회원" 가입과 이력서 등록은 무료이다. 다만 기업회원 또는 사이트에 방문한 기업체에게 자신의 이력서
                                                            정보를 보다 효과적으로 노출시키기 위한 유료서비스 및 인성, 적성 검사 등 회원 가입 목적 외 기타 서비스를
                                                            이용하기 위한 별도의 서비스는 유료로 제공될 수 있다. </li>
                                                        <li>② "회사"는 유료서비스를 제공할 경우 사이트에 요금에 대해서 공지를 하여야 한다. </li>
                                                        <li>③ "회사"는 유료서비스 이용금액을 서비스의 종류 및 기간에 따라 "회사"가 예고 없이 변경할 수 있다.
                                                            다만, 변경 이전에 적용 또는 계약한 금액은 소급하여 적용하지 아니한다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>

                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg11" id="gg11"></a><strong>제 11 조 (서비스 요금의 환불)</strong>
                                                </dt>
                                                <dd>
                                                    <ol>
                                                        <li>
                                                            ① "회사"는 다음 각 호에 해당하는 경우 이용요금을 환불한다. 단, 각 당사자의 귀책사유에 따라 환불
                                                            조건이 달라질 수 있다.
                                                            <ol>
                                                                <li>1. 유료서비스 이용이 개시되지 않은 경우</li>
                                                                <li>2. 네트워크 또는 시스템 장애로 서비스 이용이 불가능한 경우</li>
                                                                <li>3. 유료서비스 신청 후 “회원”의 사정에 의해 서비스가 취소될 경우</li>
                                                            </ol>
                                                        </li>
                                                        <li>② "회사"가 본 약관 제19조에 따라 가입해지/서비스중지/자료삭제를 취한 경우, “회사”는 “회원”에게
                                                            이용요금을 환불하지 않으며, 별도로 “회원”에게 손해배상을 청구할 수 있다.</li>
                                                        <li>③ 이용료를 환불받고자 하는 회원은 고객센터로 환불을 요청해야 한다.</li>
                                                        <li>④ "회사"는 환불 요건에 부합하는 것으로 판단될 경우, 각 서비스 환불 안내에 따라 유료이용 계약 당시
                                                            상품의 정가 기준으로 서비스 제공된 기간에 해당하는 요금을 차감한 잔액을 환불한다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg12" id="gg12"></a><strong>제 12 조 (서비스 이용시간) </strong>
                                                </dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회사"는 특별한 사유가 없는 한 연중무휴, 1일 24시간 서비스를 제공한다. 다만, "회사"는 서비스의
                                                            종류나 성질에 따라 제공하는 서비스 중 일부에 대해서는 별도로 이용시간을 정할 수 있으며, 이 경우
                                                            "회사"는 그 이용시간을 사전에 "회원"에게 공지 또는 통지하여야 한다. </li>
                                                        <li>② "회사"는 자료의 가공과 갱신을 위한 시스템 작업시간, 장애해결을 위한 보수작업 시간, 정기 PM작업,
                                                            시스템 교체작업, 회선 장애 등이 발생한 경우 일시적으로 서비스를 중단할 수 있으며 계획된 작업의 경우
                                                            공지란에 서비스 중단 시간과 작업 내용을 알려야 한다. 다만, "회사"가 사전에 통지할 수 없는 부득이한
                                                            사유가 있는 경우 사후에 통지할 수 있다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg13" id="gg13"></a><strong>제 13 조 (서비스 제공의 중지) </strong>
                                                </dt>
                                                <dd>
                                                    <ol>
                                                        <li>
                                                            ① "회사"는 다음 각 호에 해당하는 경우 서비스의 제공을 중지할 수 있다.
                                                            <ol>
                                                                <li>1. 설비의 보수 등 "회사"의 필요에 의해 사전에 "회원"들에게 통지한 경우 </li>
                                                                <li>2. 기간통신사업자가 전기통신서비스 제공을 중지하는 경우 </li>
                                                                <li>3. 기타 불가항력적인 사유에 의해 서비스 제공이 객관적으로 불가능한 경우</li>
                                                            </ol>
                                                        </li>
                                                        <li>② 전항의 경우, "회사"는 기간의 정함이 있는 유료서비스 이용자들에게는 그 이용기간을 연장하거나 환불 등의
                                                            방법으로 손실을 보상하여야 한다. </li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg14" id="gg14"></a><strong>제 14 조 (정보의 제공 및 광고의 게재)
                                                    </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회사"는 "회원"에게 서비스 이용에 필요가 있다고 인정되거나 서비스 개선 및 회원대상의 서비스 소개
                                                            등의 목적으로 하는 각종 정보에 대해서 전자우편이나 서신우편을 이용한 방법으로 제공할 수 있다. </li>
                                                        <li>② "회사"는 제공하는 서비스와 관련되는 정보 또는 광고를 서비스 화면, 홈페이지, 전자우편 등에 게재할 수
                                                            있으며, 광고가 게재된 전자우편을 수신한 "회원"은 수신거절을 "회사"에게 할 수 있다.</li>
                                                        <li>③ "회사"는 서비스상에 게재되어 있거나 본 서비스를 통한 광고주의 판촉활동에 "회원"이 참여하거나 교신
                                                            또는 거래를 함으로써 발생하는 모든 손실과 손해에 대해 책임을 지지 않는다.</li>
                                                        <li>④ 본 서비스의 "회원"은 서비스 이용 시 노출되는 광고게재에 대해 동의 하는 것으로 간주한다. </li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg15" id="gg15"></a><strong>제 15 조 (자료내용의 책임과 "회사"의 정보 수정
                                                        권한) </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>① 자료내용은 "회원"이 등록한 개인정보 및 이력서와 사이트에 게시한 게시물을 말한다.</li>
                                                        <li>② "회원"은 자료 내용 및 게시물을 사실에 근거하여 성실하게 작성해야 하며, 만일 자료의 내용이 사실이
                                                            아니거나 부정확하게 작성되어 발생하는 모든 책임은 "회원"에게 있다. "회사"는 "회원"이 작성한 게시물
                                                            등의 정확성 및 진실성을 보증하지 아니하며, 게시물에 대한 일체의 책임을 부담하지 아니한다.</li>
                                                        <li>③ 모든 자료내용의 관리와 작성은 "회원" 본인이 하는 것이 원칙이나 사정상 위탁 또는 대행관리를 하더라도
                                                            자료내용의 책임은 "회원"에게 있으며 "회원"은 주기적으로 자신의 자료를 확인하여 항상 정확하게 관리가
                                                            되도록 노력해야 한다. </li>
                                                        <li>④ "회사"는 "회원"이 등록한 자료 내용에 오자, 탈자 또는 사회적 통념에 어긋나는 문구와 내용이 있을
                                                            경우 이를 언제든지 수정할 수 있다. </li>
                                                        <li>⑤ "회원"이 등록한 자료으로 인해 타인(또는 타법인)으로부터 허위사실 및 명예훼손 등으로 삭제 요청이
                                                            접수된 경우 "회사"는 "회원"에게 사전 통지 없이 본 자료를 삭제할 수 있으며 삭제 후 메일 등의 방법으로
                                                            통지할 수 있다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg16" id="gg16"></a><strong>제 16 조 (자료 내용의 활용 및 취급)
                                                    </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회원"이 선택하거나 입력한 정보는 취업 및 관련 동향의 통계 자료로 구성, 활용될 수 있으며 그 자료는
                                                            매체를 통한 언론 배포 또는 제휴사에게 제공될 수 있다. 단, 개인을 식별할 수 있는 형태가 아니어야 한다.
                                                        </li>
                                                        <li>② 제8조 제3항에 따라 "사이트"에서 정당한 절차를 거쳐 기업회원, 서치펌회원이 열람한 "회원"의 이력서
                                                            정보는 해당 기업의 인사자료이며 이에 대한 관리 권한은 해당 기업의 정책에 의한다.</li>
                                                        <li>③ "회사"는 "사이트"의 온라인 입사지원 시스템을 통해 지원한 "회원"의 이력서 열람 여부 및 기업회원이
                                                            제공한 채용정보에 입사지원한 구직자들의 각종 통계데이터를 "회원"에게 제공할 수 있다.</li>
                                                        <li>④ "회원"이 사이트에 게시한 게시물(게시물에 대한 댓글, 사진, 각종 파일을 포함하며, 이하
                                                            "자료내용")은 "회사"가 서비스 홍보 목적을 위해 활용할 수 있다.</li>
                                                        <li>⑤ "자료내용"의 저작권은 해당 "자료내용"의 저작자에게 귀속된다. 다만 "회원"은 서비스를 통해
                                                            "자료내용"을 게시함으로써 "회사"에 대하여 이에 대한 이용, 복사, 복제, 처리, 각색, 변경, 공개,
                                                            전송, 게재 또는 배포할 수 있는 독점적이며 무상의 이용권한(2차적 저작물을 작성할 수 있는 권리 포함)을
                                                            부여한 것으로 간주된다.</li>
                                                        <li>⑥ "자료내용"은 검색결과 내지 서비스 및 관련 프로모션 등에 노출될 수 있으며, 해당 노출을 위해 필요한
                                                            범위 내에서는 일부 수정, 복제, 편집되어 게시될 수 있다. 이 경우, "회사"는 저작권법 규정을 준수하며,
                                                            "회원"은 언제든지 해당 "자료내용"에 대해 삭제, 비공개 등을 요구할 수 있다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg17" id="gg17"></a><strong>제 17 조 ("회사"의 의무) </strong>
                                                </dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회사"는 본 약관에서 정한 바에 따라 계속적, 안정적으로 서비스를 제공할 수 있도록 최선의 노력을
                                                            다해야 한다. </li>
                                                        <li>② "회사"는 서비스와 관련한 "회원"의 불만사항이 접수되는 경우 이를 즉시 처리하여야 하며, 즉시 처리가
                                                            곤란한 경우에는 그 사유와 처리일정을 서비스 화면 또는 기타 방법을 통해 동 "회원"에게 통지하여야 한다.
                                                        </li>
                                                        <li>③ "회사"는 유료 결제와 관련한 결제 사항 정보를 1년 이상 보존한다. 다만 회원 자격이 없는 회원은
                                                            예외로 한다. </li>
                                                        <li>④ 천재지변 등 예측하지 못한 일이 발생하거나 시스템의 장애가 발생하여 서비스가 중단될 경우 이에 대한
                                                            손해에 대해서는 "회사"가 책임을 지지 않는다. 다만 자료의 복구나 정상적인 서비스 지원이 되도록 최선을
                                                            다할 의무를 진다. </li>
                                                        <li>⑤ "회원"의 자료를 본 서비스 이외의 목적으로 제3자에게 제공하거나 열람시킬 경우 반드시 "회원"의 동의를
                                                            얻어야 한다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg18" id="gg18"></a><strong>제 18 조 ("회원"의 의무) </strong>
                                                </dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회원"은 관계법령과 본 약관의 규정 및 기타 "회사"가 통지하는 사항을 준수하여야 하며, 기타
                                                            "회사"의 업무에 방해되는 행위를 해서는 안 된다. </li>
                                                        <li>② "회원"이 신청한 유료서비스는 등록 또는 신청과 동시에 "회사"와 채권, 채무 관계가 발생하며,
                                                            "회원"은 이에 대한 요금을 지정한 기일 내에 납부하여야 한다. </li>
                                                        <li>③ "회원"이 결제 수단으로 신용카드를 사용할 경우 비밀번호 등 정보 유실 방지는 "회원" 스스로 관리해야
                                                            한다. 단, "사이트"의 결함에 따른 정보유실의 발생에 대한 책임은 "회원"의 의무에 해당하지 아니한다.
                                                        </li>
                                                        <li>④ "회원"은 서비스를 이용하여 얻은 정보를 "회사"의 사전동의 없이 복사, 복제, 번역, 출판, 방송
                                                            기타의 방법으로 사용하거나 이를 타인에게 제공할 수 없다. </li>
                                                        <li>
                                                            ⑤ "회원"은 본 서비스를 건전한 취업 및 경력관리 이외의 목적으로 사용해서는 안되며 이용 중 다음 각 호의
                                                            행위를 해서는 안 된다.
                                                            <ol>
                                                                <li>1. 다른 회원의 아이디를 부정 사용하는 행위</li>
                                                                <li>2. 범죄행위를 목적으로 하거나 기타 범죄행위와 관련된 행위</li>
                                                                <li>3. 타인의 명예를 훼손하거나 모욕하는 행위</li>
                                                                <li>4. 타인의 지적재산권 등의 권리를 침해하는 행위</li>
                                                                <li>5. 해킹행위 또는 바이러스의 유포 행위</li>
                                                                <li>6. 타인의 의사에 반하여 광고성 정보 등 일정한 내용을 계속적으로 전송하는 행위</li>
                                                                <li>7. 서비스의 안정적인 운영에 지장을 주거나 줄 우려가 있다고 판단되는 행위</li>
                                                                <li>8. 사이트의 정보 및 서비스를 이용한 영리 행위</li>
                                                                <li>9. 그밖에 선량한 풍속, 기타 사회질서를 해하거나 관계법령에 위반하는 행위</li>
                                                            </ol>
                                                        </li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg19" id="gg19"></a><strong>제 19 조 ("회원"의 가입해지/서비스중지/자료삭제)
                                                    </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회원"은 언제든지 회원탈퇴 또는 이력서 등록을 해지하기 위해 고객센터 또는 회원탈퇴 메뉴 등을 통하여
                                                            이용계약 해지 신청을 할 수 있으며, "회사"는 관련법 등이 정하는 바에 따라 이를 처리한다.</li>
                                                        <li>
                                                            ② 다음의 사항에 해당하는 경우 "회사"는 사전 동의없이 가입해지나 서비스 중지, 이력서 삭제 조치를 취할
                                                            수 있다.
                                                            <ol>
                                                                <li>1. 회원의 의무를 성실하게 이행하지 않았을 때 </li>
                                                                <li>2. 규정한 유료서비스 이용 요금을 납부하지 않았을 때</li>
                                                                <li>3. 본 서비스 목적에 맞지 않는 분야에 정보를 활용하여 사회적 물의가 발생한 때</li>
                                                                <li>4. 회원이 등록한 정보의 내용이 사실과 다르거나 조작되었을 때</li>
                                                                <li>5. 본 서비스와 관련하여 회사 또는 제3자의 명예를 훼손하였을 때</li>
                                                                <li>6. 기타 위 각호에 준하는 사유가 발생하였을 때</li>
                                                                <li>7. 회원의 정보가 허위로 기재되었거나 타인의 명의를 이용한 것으로 확인되었을 때</li>
                                                                <li>8. 만 15세 미만의 아동의 정보로 확인되었을 때</li>
                                                            </ol>
                                                        </li>
                                                        <li>③ "회원"이 유료서비스를 이용하는 중 "회사"의 책임에 의해 정상적인 서비스가 제공되지 않을 경우
                                                            "회원"은 본 서비스의 해지를 요청할 수 있으며 "회사"는 기간의 정함이 있는 유료서비스의 경우에는
                                                            해지일까지 이용일수를 1일 기준금액으로 계산하여 이용금액을 공제 후 환급하고, 이용건수의 정함이 있는
                                                            유료서비스의 경우에는 기 사용분을 1건 기준금액으로 계산하여 이용금액을 공제후 환급한다. </li>
                                                        <li>④ "회사"는 회원 가입이 해지된 경우에는 개인정보처리방침 중, 04.개인정보의 보유 및 이용기간 규정에
                                                            따른다.</li>
                                                        <li>⑤ "회사"는 성희롱,&nbsp;욕설,&nbsp;위협,&nbsp;사실과 다른 주장,&nbsp;무리한 요구,
                                                            폭언,&nbsp;협박,&nbsp;공포심 또는 불안감 유발,&nbsp;허위사실의 유포,&nbsp;기타 위계나
                                                            위력,&nbsp;서비스의 고의적 악용 등의 방법으로 서비스의 운영을 방해하거나 고객 응대 근로자의 정신적
                                                            충격을 유발하는 "회원"에게 고객센터 전화 안내 서비스를 제공하지 않을 수 있다.&nbsp;해당 "회원"은
                                                            헬프데스크 메일이나 1:1&nbsp;문의 프로세스를 이용할 수 있으며,&nbsp;해당 프로세스에 따라 전화
                                                            안내 제한에 대한 이의신청을 할 수 있다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg20" id="gg20"></a><strong>제 20 조 (손해배상) </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회사"가 이 약관의 규정을 위반한 행위로 "회원"에게 손해를 입히거나 기타 "회사"가 제공하는 모든
                                                            서비스와 관련하여 "회사"의 책임 있는 사유로 인해 이용자에게 손해가 발생한 경우 "회사"는 그 손해를
                                                            배상하여야 한다. </li>
                                                        <li>② "회사"는 책임 있는 사유로 제공한 정보가 사실과 달라 "회원"이 손해를 입었을 경우에 "회사"는 그
                                                            손해를 배상하여야 한다. </li>
                                                        <li>③ "회원"이 이 약관의 규정을 위반한 행위로 "회사" 및 제3자에게 손해를 입히거나 "회원"의 책임 있는
                                                            사유에 의해 "회사" 및 제3자에게 손해를 입힌 경우에는 "회원"은 그 손해를 배상하여야 한다.</li>
                                                        <li>④ 타 회원(개인회원, 기업회원, 서치펌회원 모두 포함)의 귀책사유로 "회원"의 손해가 발생한 경우
                                                            "회사"는 이에 대한 배상 책임이 없다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg21" id="gg21"></a><strong>제 21 조 (양도 금지) </strong></dt>
                                                <dd>
                                                    <p>“회원”의 서비스 받을 권리는 제3자에게 양도, 대여, 증여 등으로 사용할 수 없다.</p>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg22" id="gg22"></a><strong>제 22 조 (이용요금 오류의 조정) </strong>
                                                </dt>
                                                <dd>
                                                    <p>"회사"는 이용요금과 관련하여 오류가 있는 경우에 "회원"의 요청, 또는 "회사"의 사전 통지에 의하여 다음에
                                                        해당하는 조치를 취한다. </p>
                                                    <ol>
                                                        <li>1. 과다납입한 요금에 대하여는 그 금액을 반환한다. 다만, "회원"이 동의할 경우 다음 달에 청구할
                                                            요금에서 해당 금액만큼을 감하여 청구한다. </li>
                                                        <li>2. 요금을 반환받아야 할 "회원"이 요금체납이 있는 경우에는 반환해야 할 요금에서 이를 우선 공제하고
                                                            반환한다. </li>
                                                        <li>3. "회사"는 과소청구액에 대해서는 익월에 합산청구한다.</li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg23" id="gg23"></a><strong>제 23 조 ("회원"의 개인정보보호) </strong>
                                                </dt>
                                                <dd>"회사"는 "회원"의 개인정보보호를 위하여 노력해야 한다. "회원"의 개인정보보호에 관해서는 정보통신망이용촉진 및 정보보호
                                                    등에 관한 법률, 개인정보보호법에 따르고, "사이트"에 "개인정보처리방침"을 고지한다.</dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl>
                                                <dt><a name="gg24" id="gg24"></a><strong>제 24 조 (신용정보의 제공 활용 동의)
                                                    </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회사"가 회원가입과 관련하여 취득한 "회원"의 개인신용정보를 타인에게 제공하거나 활용하고자 할 때에는
                                                            신용정보의 이용 및 보호에 관한 법률 제23조의 규정에 따라 사전에 그 사유 및 해당기관 또는 업체명 등을
                                                            밝히고 해당 "회원"의 동의를 얻어야 한다. </li>
                                                        <li>② 본 서비스와 관련하여 "회사"가 "회원"으로부터 신용정보의 이용 및 보호에 관한 법률에 따라 타인에게
                                                            제공 활용에 동의를 얻은 경우 "회원"은 "회사"가 신용정보 사업자 또는 신용정보 집중기관에 정보를 제공하여
                                                            "회원"의 신용을 판단하기 위한 자료로 활용하거나, 공공기관에서 정책자료로 활용되도록 정보를 제공하는 데
                                                            동의한 것으로 간주한다. </li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li>
                                            <dl class="nobg">
                                                <dt><a name="gg25" id="gg25"></a><strong>제 25 조 (분쟁의 해결) </strong></dt>
                                                <dd>
                                                    <ol>
                                                        <li>① "회사"와 "회원"은 서비스와 관련하여 발생한 분쟁을 원만하게 해결하기 위하여 필요한 모든 노력을 하여야
                                                            한다. </li>
                                                        <li>② 전항의 노력에도 불구하고, 동 분쟁에 관한 소송은 "회사"의 주소지 관할법원으로 한다. </li>
                                                    </ol>
                                                </dd>
                                            </dl>
                                        </li>
                                        <li class="etc">
                                            <dl class="nobg">
                                                <dt><strong>부칙</strong></dt>
                                                <dd>
                                                    <ul>
                                                        <li>- 이 약관은 2024년 2월 5일부터 시행한다.</li>
                                                    </ul>
                                                </dd>
                                            </dl>
                                        </li>
                                    </ol>
                                </div>
                            </div>
                        </div>
                        <div class="row policy_check_privacy required">
                            <input type="checkbox" id="lb_chk_privacy" name="Priacy_Agree" class="mbrCheckOn" value="1">
                            <label for="lb_chk_privacy" class="chk_privacy"><strong>[필수]</strong> 개인정보 수집 및 이용 동의 <a
                                    href="#DevPolicyPrivacy" class="mbrBtnPolicy">내용보기</a></label>
                            <div id="DevPolicyPrivacy" class="policyTplBox">
                                <div class="pvsSec pvsCntTp">
                                    <!-- 7.0 변경 -->
                                    <p>
                                        잡코리아 및 알바몬 서비스 이용을 위해 아래와 같이 개인정보를 수집 및 이용합니다. <br>
                                        동의를 거부할 권리가 있으며, 동의 거부 시 잡코리아 및 알바몬 회원서비스 이용이 불가합니다.
                                    </p>

                                    <table class="policy-table">
                                        <colgroup>
                                            <col width="33%">
                                            <col width="33%">
                                            <col width="*">
                                        </colgroup>
                                        <thead>
                                            <tr>
                                                <th>목적</th>
                                                <th>항목</th>
                                                <th>보유 및 이용기간</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                        <tbody>
                                            <tr>
                                                <td>
                                                    본인여부 확인, 각종 맞춤형 서비스 제공, 서비스 개선 및 신규 서비스 개발을 위한 통계 활용, 계약이행 및 약관변경 등의
                                                    고지를 위한 연락, 본인의사확인 및 민원 등의 고객불만처리
                                                </td>
                                                <td>
                                                    이름, 아이디, 비밀번호, 생년월일, 성별, 휴대폰번호, 이메일
                                                </td>
                                                <td rowspan="2" style="text-decoration: underline;">
                                                    회원 탈퇴 시 즉시 파기
                                                </td>
                                            </tr>
                                        </tbody>

                                    </table>
                                    <!-- //7.0 변경 -->
                                </div>
                            </div>
                        </div>
                        <div class="row policy_check_privacyOptional optional">
                            <input type="checkbox" id="lb_chk_privacyOptional" name="PriacyOptional_Agree"
                                class="mbrCheckOn" value="1">
                            <label for="lb_chk_privacyOptional" class="chk_privacyOptional">[선택] 개인정보 수집 및 이용 동의 <a
                                    href="#DevPolicyPrivacyOptional" class="mbrBtnPolicy">내용보기</a></label>
                            <div id="DevPolicyPrivacyOptional" class="policyTplBox">
                                <div class="pvsSec pvsTpList">
                                    <h2 class="pvsHeading2">
                                        <strong>
                                            1. 수집 이용 목적
                                        </strong>
                                    </h2>
                                    <p class="depth_01">
                                        <u>맞춤공고, 서비스 안내, 마케팅 발송을 목적으로 활용</u>
                                    </p>
                                    <h2 class="pvsHeading2"><strong>2. 수집하는 개인정보 항목</strong></h2>
                                    <p class="depth_01">
                                        전화번호, 이메일, 푸시토큰, 서비스 이용 기록
                                    </p>
                                    <h2 class="pvsHeading2"><strong>3. 개인정보 보유 및 이용기간</strong></h2>
                                    <p class="depth_01">
                                        <u>회원탈퇴 시 즉시 파기</u>
                                    </p>
                                    <h2 class="pvsHeading2"><strong>4. 수신동의 거부 및 철회방법 안내</strong></h2>
                                    <p class="depth_01">
                                        본 동의는 거부하실 수 있습니다. 다만 거부 시 동의를 통해 제공 가능한 각종 혜택, 이벤트 안내를 받아보실 수 없습니다.
                                        더 이상 맞춤공고, 서비스 안내, 마케팅 발송을 원하시지 않는 경우 회원정보수정 페이지에서 수신여부를 변경하실 수 있습니다.
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="row check_email optional">
                            <input type="checkbox" id="lb_chk_email" name="Event_Agree_Stat" class="mbrCheckOn"
                                value="1">
                            <label for="lb_chk_email" class="chk_privacy">[선택] 광고성 정보 이메일 수신 동의 <a
                                    href="#DevPolicyEMail" class="mbrBtnPolicy">내용보기</a></label>
                            <div id="DevPolicyEMail" class="policyTplBox">
                                <div class="pvsSec pvsTpList">
                                    <h2 class="pvsHeading2"><strong>1. 수집 및 이용 목적</strong></h2>
                                    <p class="depth_01">회원이 수집 및 이용에 동의한 개인정보를 활용하여, 이메일을 통해 회원에게 유용한 혜택, 이벤트, 광고 정보를
                                        전송할 수 있습니다.</p>

                                    <h2 class="pvsHeading2"><strong>2. 수집하는 개인정보 항목</strong></h2>
                                    <p class="depth_01">필수 동의사항에서 개인정보 수집 및 이용에 동의한 항목</p>

                                    <h2 class="pvsHeading2"><strong>3. 보유 및 이용기간 </strong></h2>
                                    <p class="depth_01">
                                        회원탈퇴를 요청하거나 개인정보의 수집 및 이용에 대한 동의를 철회하는 경우, 수집 및 이용목적이 달성되거나 이용기간이 종료한 경우 개인정보를
                                        지체 없이 파기합니다.<br>
                                        단, 상법 등 관계법령의 규정에 의하여 보존할 필요가 있는 경우 법령에서 규정한 보존기간 동안 거래내역과 최소한의 기본정보를 보유합니다.
                                    </p>
                                    <h2 class="pvsHeading2"><strong>4. 수신동의 거부 및 철회방법 안내 </strong></h2>
                                    <p class="depth_01">
                                        본 동의는 거부하실 수 있습니다. 다만 거부 시 동의를 통해 제공 가능한 각종 혜택, 이벤트 안내를 받아보실 수 없습니다. <br>
                                        본 수신동의를 철회하고자 할 경우에는 메일링/문자메시지 설정 페이지에서 수신여부를 변경하실 수 있습니다.
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div class="row check_sms optional">
                            <input type="checkbox" id="lb_chk_sms" name="SMS_Agree_Stat" class="mbrCheckOn" value="1">
                            <label for="lb_chk_sms" class="chk_privacy">[선택] 광고성 정보 SMS 수신 동의 <a href="#DevPolicySMS"
                                    class="mbrBtnPolicy">내용보기</a></label>
                            <div id="DevPolicySMS" class="policyTplBox">
                                <div class="pvsSec pvsTpList">
                                    <h2 class="pvsHeading2"><strong>1. 수집 및 이용 목적</strong></h2>
                                    <p class="depth_01">회원이 수집 및 이용에 동의한 개인정보를 활용하여, SMS/MMS 등 다양한 전자적 전송매체를 통해 회원에게 유용한
                                        혜택, 이벤트, 광고 정보를 전송할 수 있습니다.</p>

                                    <h2 class="pvsHeading2"><strong>2. 수집하는 개인정보 항목</strong></h2>
                                    <p class="depth_01">필수 동의사항에서 개인정보 수집 및 이용에 동의한 항목</p>

                                    <h2 class="pvsHeading2"><strong>3. 보유 및 이용기간 </strong></h2>
                                    <p class="depth_01">
                                        회원탈퇴를 요청하거나 개인정보의 수집 및 이용에 대한 동의를 철회하는 경우, 수집 및 이용목적이 달성되거나 이용기간이 종료한 경우 개인정보를
                                        지체 없이 파기합니다.<br>
                                        단, 상법 등 관계법령의 규정에 의하여 보존할 필요가 있는 경우 법령에서 규정한 보존기간 동안 거래내역과 최소한의 기본정보를 보유합니다.
                                    </p>

                                    <h2 class="pvsHeading2"><strong>4. 수신동의 거부 및 철회방법 안내 </strong></h2>
                                    <p class="depth_01">
                                        본 동의는 거부하실 수 있습니다. 다만 거부 시 동의를 통해 제공 가능한 각종 혜택, 이벤트 안내를 받아보실 수 없습니다.<br>
                                        본 수신동의를 철회하고자 할 경우에는 메일링/문자메시지 설정 페이지에서 수신여부를 변경하실 수 있습니다.
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 회원 가입 버튼 -->
                    <div class="row_group line_none regist_complete" style="position:relative;">
                        <div class="row">
                            <button type="submit" class="mbrBtnRegist"><span>가입하기</span></button>
                        </div>
                    </div>
                </form>
            </fieldset>
            <div class="familysite">
                <p class="text">가입 후 이메일 인증까지 완료하여야 계정이 활성화됩니다.</p>
            </div>

        </div>
        <div id="footer">
            <p id="copyright">© SABUJAKS LLC. All rights reserved.</p>
            <map name="copyright"><area shape="rect" coords="10,0,80,19" href="https://www.jobkorea.co.kr"
                    target="_blank" alt="잡코리아"></map>
        </div>
    </div>


</div>
</template>

<script setup>
</script>

<style scoped>
html,
#body,
div,
span,
applet,
object,
iframe,
h1,
h2,
h3,
h4,
h5,
h6,
p,
blockquote,
pre,
a,
abbr,
acronym,
address,
big,
cite,
code,
del,
dfn,
em,
img,
ins,
kbd,
q,
s,
samp,
small,
strike,
strong,
sub,
sup,
tt,
var,
b,
u,
i,
center,
dl,
dt,
dd,
ol,
ul,
li,
fieldset,
form,
label,
legend,
table,
caption,
tbody,
tfoot,
thead,
tr,
th,
td,
article,
aside,
canvas,
details,
embed,
figure,
figcaption,
footer,
header,
hgroup,
menu,
nav,
output,
ruby,
section,
summary,
time,
mark,
audio,
video,
label {
    margin: 0;
    padding: 0;
    border: 0;
    font-size: 100%;
    font: inherit;
    vertical-align: baseline;
}

/* HTML5 display-role reset for older browsers */
article,
aside,
details,
figcaption,
figure,
footer,
header,
hgroup,
menu,
nav,
section {
    display: block;
}

#body {
    line-height: 1;
}

ol,
ul {
    list-style: none;
}

blockquote,
q {
    quotes: none;
}

blockquote:before,
blockquote:after,
q:before,
q:after {
    content: '';
    content: none;
}

table {
    border-collapse: collapse;
    border-spacing: 0;
}

input,
label {
    margin: 0;
    padding: 0;
    vertical-align: middle;
}

button {
    overflow: visible;
    margin: 0;
    padding: 0;
    border: none;
    background: none;
    cursor: pointer;
    font: inherit;
    -webkit-border-radius: 0;
    -moz-border-radius: 0;
    border-radius: 0;
}

/* Avoid grey background on IE 10 anchors / links */
a:focus {
    background: transparent;
}

#body {
    font-size: 12px;
    letter-spacing: 0px;
    font-weight: normal;
    text-align: center;
    background: #fff;
    color: #333;
}

.skip,
.blind,
legend {
    position: absolute;
    top: -5000em;
    width: 1px;
    height: 1px;
    overflow: hidden;
    line-height: 0;
    font-size: 0;
}

a {
    text-decoration: none;
}

/*f_clear */
.f_clear:after {
    content: ".";
    height: 0;
    display: block;
    visibility: hidden;
    clear: both;
}

* html .f_clear {
    height: 1px;
}

.f_clear {
    display: block;
    min-height: 1px;
}

#wrap {
    width: 620px;
    margin: 0 auto;
    text-align: left;
}

#header {
    position: relative;
    padding-bottom: 15px;
}

#header h1 {
    padding: 18px 0 0 2px;
}

#header .gnb {
    position: absolute;
    top: 22px;
    right: 0;
}

#header .gnb li {
    float: left;
    padding-left: 7px;
}

#header .gnb li:first-child {
    padding-right: 8px;
    background: none;
}

#header .gnb li a {
    font-size: 13px;
    letter-spacing: 0px;
    color: #888;
}

#header .joinTop {
    text-align: center;
    margin: 60px 0 48px 0;
}

#header .joinTop h2 {
    overflow: hidden;
    width: 620px;
    white-space: nowrap;
}

#header .joinTop h2 {
    font-size: 38px;
    letter-spacing: -1px;
    font-weight: normal;
}

#header .joinTop h2 strong {
    font-weight: normal;
    color: #39f;
}

#header .joinTop p {
    color: #666;
    font-size: 13px;
    letter-spacing: 0px;
    font-weight: normal;
    line-height: 1.5;
    margin-top: 22px;
}

#header .joinTop p strong {
    display: block;
    color: #000;
    font-size: 16px;
    letter-spacing: 0px;
    font-weight: bold;
}

#header .snb {
    margin-top: 41px;
}

#header .snb li {
    float: left;
    width: 124px;
    height: 74px;
}

#header .snb .gg a {
    background-position: 0 0;
}

#header .snb .gi a {
    background-position: -124px 0;
}

#header .snb .hr a {
    background-position: -249px 0;
}

#header .snb .hh a {
    background-position: -373px 0;
}

#header .snb .edu a {
    background-position: -496px 0;
}

#header .snb .gg span {
    background-position: 0 -88px;
}

#header .snb .gi span {
    background-position: -124px -88px;
}

#header .snb .hr span {
    background-position: -248px -88px;
}

#header .snb .hh span {
    background-position: -373px -88px;
}

#header .snb .edu span {
    background-position: -496px -88px;
}

#footer {
    margin-top: 40px;
    padding: 20px 0;
    border-top: 1px solid #eaeaea;
    text-align: center;
}

#footer address a {
    overflow: hidden;
    position: absolute;
    top: 0;
    left: 114px;
    width: 67px;
    height: 19px;
    white-space: nowrap;
    text-indent: -999em;
}

#footer .copyright {
    color: #6A6A6A;
}

#footer .copyright strong a {
    display: inline-block;
    color: #6A6A6A;
    font-weight: 600;
}

/* mbr_common.css 에서 사용중인 css 시작 */
/* 헤더 슬로건 */
#body {
    background-color: #f5f6f8;
}

#wrap {
    width: 640px !important;
    margin: 0 auto;
    text-align: left;
    box-sizing: border-box;
}

#header {
    padding-bottom: 0px !important;
    padding-top: 43px;
}

#header h1 {
    display: none;
}

a {
    text-decoration: none;
}

#header .joinTop {
    text-align: center;
    box-sizing: border-box;
    min-height: 154px;
}

.jkJoin #header .joinTop {
    margin: 0;
}

#header .joinTop h2 {
    overflow: hidden;
    width: 640px;
    white-space: nowrap;
    box-sizing: border-box;
    padding-top: 26px;
}

#header .joinTop h2>span {
    text-indent: -99999px;
    overflow: hidden;
}

#header .joinTop h2>span {
    width: 300px;
    height: 300px;
    margin: 5px auto 0px;
    background-image: url("../../../assets/img/irs_black.png");
    background-size: 300px 300px;
    background-repeat: no-repeat;
    display: block;
}

#header .joinTop h2>span.slogun_1 {
    background-position: 50% 6px;
}

#header .joinTop h2>span.slogun_2 {
    background-position: 50% -38px;
}

#header .joinTop h2>span.slogun_3 {
    background-position: 50% -95px;
}

#header .joinTop h2>span.slogun_4 {
    background-position: 50% -138px;
}

#header .joinTop h2>span.slogun_5 {
    background-position: 50% -187px;
}

#header .joinTop h2>span.slogun_6 {
    background-position: 50% -235px;
}

#header .joinTop h2 {
    font-size: 38px;
    letter-spacing: -1px;
    font-weight: normal;
}

#header .joinTop h2 strong {
    font-weight: normal;
    color: #39f;
}

#header .joinTop p {
    color: #666;
    font-size: 13px;
    letter-spacing: 0px;
    font-weight: normal;
    line-height: 1.5;
    margin-top: 22px;
}

#header .joinTop p strong {
    display: block;
    color: #000;
    font-size: 16px;
    letter-spacing: 0px;
    font-weight: bold;
}

/* 탭 메뉴*/
#header .snb {
    margin: 0 0 0;
    position: relative;
}

/*#header .snb li span,*/
#header .snb>li {
    float: left;
    width: 248px;
    height: 60px;
    box-sizing: border-box;
    font-weight: normal;
    text-align: center;
}

#header .snb>li.on>a {
    font-size: 22px;
    letter-spacing: -0.5px;
    font-weight: bold;
    color: #fff;
    background-color: #212b36;
}

#header .snb>li>a {
    overflow: hidden;
    display: block;
    line-height: 60px;
    background: none;
    white-space: nowrap;
    text-indent: 0;
    color: #666;
    box-sizing: border-box;
    color: #6a6a6a;
    font-size: 18px;
    letter-spacing: 0px;
    border-bottom: 2px solid #212b36;
    background-color: #fff;
}

#header .snb>.person.on {
    width: 392px;
}

#header .snb>.corp.on {
    width: 392px;
}

#header .snb>.corp .snb_type {
    display: none;
}

#header .snb>.corp.on {
    margin-bottom: 20px;
    border-top: none;
    border-right: none;
}

#header .snb>.corp.on>a+.snb_type {
    display: block;
    position: absolute;
    width: 640px;
    top: 80px;
    left: 0;
}

#header .snb>.corp.on>a+.snb_type li {
    float: left;
    position: relative;
    width: 160px;
    height: 50px;
    margin-left: -1px;
    text-align: center;
    color: #333;
    font-size: 14px;
    letter-spacing: 0px;
    border: 1px solid #e1e5eb;
    box-sizing: border-box;
}

#header .snb>.corp.on>a+.snb_type li a {
    display: block;
    height: 48px;
    padding-top: 13px;
    color: #333;
    font-size: 15px;
    letter-spacing: 0px;
    background-image: none;
    text-indent: 0;
    box-sizing: border-box;
    background-color: #fff;
}

#header .snb>.corp.on>a+.snb_type li.on {
    z-index: 10;
    border-color: #3399fe;
}

#header .snb>.corp.on>a+.snb_type li.on a:before {
    content: '';
    display: inline-block;
    position: relative;
    top: 6px;
    width: 12px;
    height: 10px;
    margin-right: 5px;
    vertical-align: top;
    background: url('../../../assets/img/sprite-icon.png') no-repeat 0 -104px;
}

#header .snb>.corp.on>a+.snb_type li.gi {
    margin-left: 0;
}

#header .snb>.corp.on>a+.snb_type li.hr {
    width: 161px;
}

#header .snb>.corp.on>a+.snb_type li.hh {
    width: 161px;
}

#header .snb>.corp.on>a+.snb_type li.edu {
    width: 161px;
}

#header .snb>.corp.on>a+.snb_type .gi.on a {
    background-position: 32px 50%;
}

#header .snb>.corp.on>a+.snb_type .hr.on a {
    background-position: 23px 50%;
}

#header .snb>.corp.on>a+.snb_type .hh.on a {
    background-position: 14px 50%;
}

#header .snb>.corp.on>a+.snb_type .edu.on a {
    background-position: 15px 50%;
}

/* 회원가입 공통 */
.mbrRegist .row_group {
    position: relative;
    width: 640px;
    padding: 0 32px;
    margin-bottom: 12px;
    border: 1px solid #e8e8e8;
    background-color: #fff;
    box-sizing: border-box;
}

.mbrRegist .row_group h4 {
    font-size: 18px;
    line-height: 26px;
    letter-spacing: 0;
    color: #222;
    margin-bottom: 10px;
    padding-top: 32px;
}

.mbrRegist .row_group .subTx {
    position: absolute;
    right: 32px;
    top: 32px;
    font-size: 14px;
    line-height: 22px;
    letter-spacing: 0;
    color: #9e9e9e;
}

.mbrRegist .row_group .subTx strong {
    font-size: 16px;
    letter-spacing: 0px;
    color: #ff3333;
}

.mbrRegist .row_group .headingGuide {
    position: absolute;
    right: 29px;
    top: 42px;
    font-size: 12px;
    letter-spacing: 0;
    color: #afb0b2;
}

.mbrRegist .row_group .headingGuide i {
    margin-right: 2px;
    color: #ff3333;
}

.mbrRegist .row_group .headingButtons {
    position: absolute;
    right: 29px;
    top: 42px;
    font-size: 0;
    text-align: right;
}

.mbrRegist .row_group .headingButtons .button {
    display: inline-block;
    font-size: 11px;
    letter-spacing: 0;
    color: #9d9d9d;
    text-decoration: underline;
}

.mbrRegist .row_group.line_none.noBtLinePd {
    padding: 5px 0 0;
}

.mbrRegist .row_group h4.first_msg_title {
    color: #0057FF;
    font-size: 14px;
}

.mbrRegist .row_group h4.first_msg_title strong {
    font-weight: 700;
}

.mbrRegist .row:after {
    content: ".";
    height: 0;
    display: block;
    visibility: hidden;
    clear: both;
}

.mbrRegist .rows {
    font-size: 0;
    position: relative;
}

.mbrRegist .rows .row {
    display: inline-block;
    zoom: 1;
}

.mbrRegist .row {
    position: relative;
    display: block;
    margin-bottom: 10px;
    font-size: 0;
}

.mbrRegist .row .emailTxt {
    margin-bottom: 10px;
    padding-left: 120px;
}

.mbrRegist .mbr_name {
    z-index: 100;
}

.mbrRegist .mbr_age {
    display: flex;
    gap: 16px;
    z-index: 100;
}

.mbrRegist .mbr_age .col_1 label {
    display: inline-block;
}

.mbrRegist .mbr_age .col_2 {
    width: 288px;
}

.mbrRegist .col_3 i.icon.required {
    margin-left: 5px;
    font-size: 16px;
    letter-spacing: 0;
    color: #ff3333;
}

.mbrRegist .mbr_age .col_3 {
    position: relative;
}

.mbrRegist .mbr_age .col_3 .notice_msg {
    left: 44px;
}

.mbrRegist .mbr_age .col_3 .select {
    display: flex;
}

.mbrRegist .mbr_age .col_3 .select input {
    display: none;
}

.mbrRegist .mbr_age .col_3 .select label {
    border: 1px solid #d2d2d2;
    cursor: pointer;
}

.mbrRegist .mbr_age .col_3 .select label:nth-child(4) {
    border-left: 1px solid transparent;
    margin-left: -1px
}

.mbrRegist .mbr_age .col_3 .select input[type="radio"]+label {
    display: block;
    box-sizing: border-box;
    text-align: center;
    width: 64px;
    height: 62px;
    padding: 19px 16px;
    color: #d2d2d2;
    font-size: 15px;
    line-height: 24px;
}

.mbrRegist .mbr_age .col_3 .select input[type="radio"]:checked+label {
    border-color: #222 !important;
    color: #222;
}

/* 입력 폼 z-index 초기화 */
.mbrRegist .rows,
.mbrRegist .mbr_name,
.mbrRegist .mbr_age,
.mbrRegist .mbr_id,
.mbrRegist .mbr_passwd,
.mbrRegist .mbr_email,
.mbrRegist .mbr_phone,
.mbrRegist .authentication_check,
.mbrRegist .mbr_email,
.mbrRegist .mbr_agree,
.mbrRegist .mbr_passwd {
    z-index: auto !important;
}

.mbrRegist .col_1 {
    z-index: 10;
    position: absolute;
    left: 12px;
    top: 23px;
    transition: all 0.2s ease-out;
}

.mbrRegist .col_1 i.icon.required {
    margin-left: 5px;
    font-size: 16px;
    letter-spacing: 0px;
    color: #ff3333;
}

.mbrRegist .col_1 label {
    color: #d2d2d2;
    font-size: 16px;
    letter-spacing: 0;
    transition: all 0.2s ease-out;
}

.mbrRegist .col_1.focus {
    top: 9px;
}

.mbrRegist .col_1.focus label {
    font-size: 11px;
    letter-spacing: 0px;
}

.mbrRegist .col_1.focus label i.icon.required {
    font-size: 11px;
    letter-spacing: 0px;
}

.mbrRegist .col_2 {
    position: relative;
    width: 100%;
    height: 62px;
    padding: 0 12px;
    box-sizing: border-box;
    border: 1px solid #d2d2d2;
}

.mbrRegist .col_2.focus {
    padding-top: 19px;
}

.mbrRegist .col_2.focus input {
    height: 41px;
}

.mbrRegist .mbr_zipcode .notice_msg,
.mbrRegist .col_3 .notice_msg,
.mbrRegist .col_2 .notice_msg {
    display: none;
    z-index: 9100;
    position: absolute;
    left: -1px;
    top: 56px;
    min-width: 100px;
    padding: 4px 8px;
    border: 1px solid #ffbcb9;
    background-color: #ffeeec;
    color: #6a6a6a;
    box-sizing: border-box;
    white-space: nowrap;
    font-size: 12px;
    line-height: 16px;
    letter-spacing: 0px;
}

.mbrRegist .col_2 .notice_limit {
    display: block;
    z-index: 9100;
    position: absolute;
    left: -1px;
    top: 60px;
    width: auto;
    padding: 7px 10px 6px;
    border: 1px solid #ffbcb9;
    background-color: #ffeeec;
    color: #6a6a6a;
    box-sizing: border-box;
    font-size: 12px;
    letter-spacing: 0px;
}

.mbrRegist .mbr_zipcode .notice_msg:after,
.mbrRegist .col_3 .notice_msg:after,
.mbrRegist .col_2 .notice_msg:after,
.mbrRegist .col_2 .notice_limit:after {
    content: '';
    display: block;
    position: absolute;
    left: 9px;
    top: -11px;
    width: 19px;
    height: 11px;
    background: url('../../../assets/img/sprite-icon.png') no-repeat 0 -145px;
}

.mbrRegist .mbr_zipcode .notice_msg.success,
.mbrRegist .col_2 .notice_msg.success {
    border-color: #b7dbff;
    background-color: #f7fbff;
}

.mbrRegist .mbr_zipcode .notice_msg.success:after,
.mbrRegist .col_2 .notice_msg.success:after {
    width: 18px;
    background: url('../../../assets/img/sprite-icon.png') no-repeat 0 -124px;
}

.mbrRegist .col_2.focus .select_wrap {
    height: 41px;
    padding-top: 10px;
}

.mbrRegist input[type=text],
.mbrRegist input[type=password] {
    display: block;
    width: 100%;
    height: 60px;
    border: 0;
    outline: 0;
    box-sizing: border-box;
    font-size: 16px;
    letter-spacing: 0px;
    color: #333;
    background: transparent;
}

.mbrRegist input[type=text]::-ms-clear,
.mbrRegist input[type=password]::-ms-clear {
    display: none;
}

.mbrRegist input[type=text]:-moz-placeholder,
.mbrRegist input[type=password]:-moz-placeholder {
    color: #999999;
    font-size: 12px;
    letter-spacing: 0px;
    font-weight: normal;
}

/* ie */
.mbrRegist input[type=text]:-ms-input-placeholder,
.mbrRegist input[type=password]:-ms-input-placeholder {
    color: #999999;
    font-size: 12px;
    letter-spacing: 0px;
    font-weight: normal;
}

.mbrRegist input[type=text]::-moz-placeholder,
.mbrRegist input[type=password]::-moz-placeholder {
    color: #999999;
    font-size: 12px;
    letter-spacing: 0px;
}

/* firefox 19+ */
.mbrRegist input[type=text]::-webkit-input-placeholder,
.mbrRegist input[type=password]::-webkit-input-placeholder {
    color: #999999;
    font-size: 12px;
    letter-spacing: 0px;
    font-weight: normal;
}

.mbrRegist input[type=password]::-ms-reveal {
    display: none;
}

.mbrRegist .select_wrap {
    padding-top: 19px;
    height: 60px;
    box-sizing: border-box;
}

.mbrRegist .select_wrap:after {
    content: '';
    display: block;
    position: absolute;
    right: 12px;
    top: 50%;
    width: 11px;
    height: 6px;
    background: url('../../../assets/img/sprite-icon.png') no-repeat 0 -30px;
    -webkit-transform: translateY(-50%);
    transform: translateY(-50%);
}

.mbrRegist .select_wrap select {
    width: 100%;
    border: none;
    border-radius: 0;
    background: transparent;
    font-size: 16px;
    letter-spacing: 0px;
    color: #333;
    outline: none;
    cursor: pointer;
    -webkit-appearance: none;
    -moz-appearance: none;
    -o-appearance: none;
    appearance: none;
}

.mbrRegist .select_wrap select::-ms-value {
    background: transparent;
    color: #333;
}

.mbrRegist .select_wrap select.selected {
    font-weight: bold;
    color: #333;
}

/* ie10이상 select박스 화살표 디자인 수정 */
.mbrRegist select::-ms-expand {
    opacity: 0
}

/* 약관동의 */
.mbrRegist .policy {
    padding: 0px;
}

.mbrRegist .policy .row {
    padding: 0 32px;
    box-sizing: border-box;
    margin-bottom: 0;
}

.mbrRegist .policy .policy_check_all {
    padding: 32px 32px 22px 32px;
    position: relative;
    margin-bottom: 0;
}

.mbrRegist .policy .policy_check_service {
    margin-bottom: 10px;
}

.mbrRegist .policy .policy_check_privacy {
    margin-bottom: 10px;
}

.mbrRegist .policy .policy_check_privacyOptional {
    margin-bottom: 10px;
}

.mbrRegist .policy .check_email {
    margin-bottom: 10px;
}

.mbrRegist .policy .check_sms {
    margin-bottom: 26px;
}

.mbrRegist .policy .required+.optional {
    padding: 22px 0 0 0;
    margin-bottom: 10px;
    border-top: 1px dashed #d2d2d2;
    box-sizing: border-box;
    width: calc(100% - 64px);
    margin: 0 auto 10px;
}

.mbrRegist .policy input[type="checkbox"] {
    overflow: hidden;
    position: absolute;
    clip: rect(0 0 0 0);
    clip: rect(0, 0, 0, 0);
    width: 1px;
    height: 1px;
    margin: -1px;
    border: 0;
    padding: 0;
}

.mbrRegist .policy label {
    position: relative;
    display: block;
    z-index: 1;
    font-size: 16px;
    line-height: 24px;
    letter-spacing: 0;
    color: #6a6a6a;
    cursor: pointer;
}

.mbrRegist .policy label:after {
    content: '';
    display: block;
    position: absolute;
    right: 0;
    top: 2px;
    width: 20px;
    height: 20px;
    background: #fff url('../../../assets/svg/agree_uncheck_mini.svg') no-repeat center;
}

.mbrRegist .policy input:checked+label:after {
    background-image: url('../../../assets/svg/agree_check_mini.svg');
}

.mbrRegist .policy .policy_check_all label {
    font-size: 18px;
    letter-spacing: 0px;
    color: #333;
    cursor: pointer;
    border-bottom: 1px solid #e8e8e8;
    padding-bottom: 22px;
}

.mbrRegist .policy .policy_check_all label:after {
    content: '';
    display: block;
    position: absolute;
    right: 0;
    top: 0;
    width: 30px;
    height: 30px;
    background: #fff url('../../../assets/svg/join-uncheck-square.svg') no-repeat center;
}

.mbrRegist .policy .policy_check_all label .txt {
    display: block;
    width: 532px;
    font-size: 16px;
    line-height: 26px;
    color: #000;
    font-weight: 400;
}

.mbrRegist .policy .policy_check_all input:checked+label:after {
    background: url('../../../assets/svg/join-check-square.svg') no-repeat center;
}

.mbrRegist .policy label strong {
    font-size: 16px;
    letter-spacing: 0px;
    font-weight: normal;
    color: #FF4500;
}

.mbrRegist .policy label.chk_entrust strong {
    color: #666
}

.mbrRegist .policy .mbrBtnPolicy {
    display: block;
    margin-left: 0;
    padding-right: 12px;
    font-size: 12px;
    line-height: 18px;
    letter-spacing: 0;
    font-weight: 400;
    color: #6a6a6a;
    position: absolute;
    right: 36px;
    top: 3px;
}

.mbrRegist .policy .mbrBtnPolicy:before {
    content: '';
    display: block;
    position: absolute;
    right: -4px;
    top: 0;
    width: 16px;
    height: 16px;
    background: url('../../../assets/svg/system_arrowDown.svg')center no-repeat;
}

.mbrRegist .policy .mbrBtnPolicy.on:before {
    transform: rotate(180deg);
}

.mbrRegist .policy .policyTplBox {
    display: none;
    overflow-y: scroll;
    height: 122px;
    margin-top: 10px;
    padding: 0 15px 15px 15px;
    border: 1px solid #eaeaea;
    background-color: #fafafa;
    line-height: 2;
    color: #888;
    font-size: 12px;
    letter-spacing: 0px;
}

.mbrRegist .policy .notice_msg p:first-child {
    padding-top: 14px;
}

.mbrRegist .policy .pvsSec.pvsCntTp>p:first-child,
.mbrRegist .policy .pvsSec.pvsTpList>p:first-child {
    margin-top: 25px;
}

.mbrRegist .policy .notice_msg {
    display: none;
    z-index: 9100;
    position: absolute;
    right: 28px;
    top: 74px;
    min-width: 100px;
    padding: 5px 7px 6px;
    border: 1px solid #ffbcb9;
    background-color: #ffeeec;
    color: #555;
    box-sizing: border-box;
    white-space: nowrap;
    font-size: 12px;
    letter-spacing: 0px;
}

.mbrRegist .policy .notice_msg:after {
    content: '';
    display: block;
    position: absolute;
    right: 9px;
    top: -11px;
    width: 19px;
    height: 11px;
    background: url('../../../assets/img/sprite-icon.png') no-repeat 0 -145px;
}

.mbrRegist .policy .notice_msg p:first-child {
    padding-top: 0;
}

.mbrRegist .policy .policy-table {
    table-layout: auto;
    width: 100%;
    border-top: 1px solid #ddd;
    border-left: 1px solid #ddd;
}

.mbrRegist .policy .policy-table th {
    padding: 5px;
    font-weight: bold;
    text-align: center;
    border-bottom: 1px solid #ddd;
    border-right: 1px solid #ddd;
    line-height: 1.6;
}

.mbrRegist .policy .policy-table td {
    padding: 5px;
    border-bottom: 1px solid #ddd;
    border-right: 1px solid #ddd;
    line-height: 1.6;
}

.jkJoin_company .mbrRegist .mbr_id {
    width: 225px;
    margin-right: 10px;
}

.jkJoin_company .member-switch .mbr_passwd,
.jkJoin_company .member-switch .mbr_passwd_recheck {
    width: 519px;
}

.jkJoin_searchfirm .mbrRegist .mbr_id {
    width: 225px;
    margin-right: 10px;
}

.jkJoin_searchfirm .mbrRegist .mbr_passwd {
    width: 284px;
}

.jkJoin_hh .mbrRegist .mbr_id {
    width: 225px;
    margin-right: 10px;
}

.jkJoin_hh .mbrRegist .mbr_passwd {
    width: 284px;
}

.jkJoin_edu .mbrRegist .mbr_id {
    width: 225px;
    margin-right: 10px;
}

.jkJoin_edu .mbrRegist .mbr_passwd {
    width: 284px;
}

/* 비밀번호 */
.mbrRegist .mbr_passwd {
    z-index: 20;
}

.mbrRegist .mbr_passwd .btnHelp {
    position: absolute;
    right: 12px;
    transform: translateY(-50%);
    top: 50%;
    width: 24px;
    height: 24px;
    background: url('../../../assets/svg/user_join_tootip_icon.svg') no-repeat center;
}

.mbrRegist .mbr_passwd {
    width: 504px;
}

.mbrRegist .mbr_passwd .col_3 {
    position: absolute;
    right: -72px;
    top: 0;
    width: 72px;
    height: 62px;
}

.mbrRegist .mbr_passwd .lyHelp {
    display: none;
    z-index: 10000;
    left: 55px;
    top: -180px;
    width: 440px;
    padding: 14px 12px 15px 12px
}

.jkJoin_company .mbrRegist .mbr_passwd .lyHelp {
    left: -180px;
}

.jkJoin_searchfirm .mbrRegist .mbr_passwd .lyHelp {
    left: -180px;
}

.jkJoin_hh .mbrRegist .mbr_passwd .lyHelp {
    left: -180px;
}

.jkJoin_edu .mbrRegist .mbr_passwd .lyHelp {
    left: -180px;
}

.jkJoin_company .member-switch .mbr_passwd .lyHelp {
    left: auto;
    right: -5px;
}

.mbrRegist .mbr_passwd .lyHelp dl dt {
    margin-bottom: 10px;
    font-size: 14px;
    letter-spacing: 0px;
    color: #333;
    font-weight: normal;
}

.mbrRegist .mbr_passwd .lyHelp dl ol {
    padding-left: 0;
    list-style: none;
}

.mbrRegist .mbr_passwd .lyHelp dl ol li {
    position: relative;
    margin-top: 8px;
    padding-left: 13px;
    font-size: 13px;
    letter-spacing: 0px;
    color: #484848;
}

.mbrRegist .mbr_passwd .lyHelp dl ol li:before {
    content: '';
    display: block;
    position: absolute;
    left: 4px;
    top: 8px;
    width: 2px;
    height: 2px;
    background-color: #747474;
}

.mbrRegist .mbr_passwd .lyHelp dl ol li:first-child {
    margin-top: 0;
}

.mbrRegist .mbr_passwd .lyHelp:after {
    content: '';
    position: absolute;
    right: 34px;
    bottom: -9px;
    width: 16px;
    height: 9px;
    background: url('../../../assets/img/sprite-icon.png') no-repeat 0 -46px;
}

.mbrRegist .mbrBtnAuth {
    display: block;
    position: absolute;
    right: 0;
    top: 0;
    width: 72px;
    height: 62px;
    vertical-align: top;
}

.mbrRegist .mbrBtnAuth span {
    display: flex;
    justify-content: flex-start;
    align-items: center;
    padding-top: 0;
    padding-left: 16px;
    width: 72px;
    height: 62px;
    font-size: 14px;
    letter-spacing: 0;
    color: #9e9e9e;
    text-align: left;
    box-sizing: border-box;
}

.mbrRegist .mbrBtnAuth span:before {
    content: '';
    display: block;
    width: 20px;
    height: 20px;
    background: url('../../../assets/svg/pwd_check.svg') no-repeat center;
    margin-right: 6px;
}

.mbrRegist .mbrBtnAuth.selected span {
    color: #3399ff;
}

.mbrRegist .mbrBtnAuth.selected span:before {
    background: url('../../../assets/svg/pwd_checkbox_circle.svg') no-repeat center;
}

/* 기업형태 */
.mbrRegist .mbr_co_type .col_1 {
    z-index: 0;
}

/* Company */
.mbrRegist .company_name {
    width: 387px;
    margin-right: 10px;
}

/* Boss Name */
.mbrRegist .company_bossname {
    width: 177px;
}

/* Customer-Center */
.mbrRegist .company_customer-center {
    display: block;
    font-size: 12px;
    letter-spacing: 0px;
    color: #666666;
    padding: 5px 0 20px;
}

.mbrRegist .company_customer-center em {
    color: #FF3333;
}

/* 우편번호 */
.mbrRegist .mbr_zipcode label {
    color: #999;
}

.mbrRegist .mbr_zipcode input {
    width: 100%;
}

.mbrRegist .mbr_zipcode .col_2 {
    width: auto;
}

.mbrRegist .mbr_zipcode .col_3 {
    float: left;
    margin: 0 0 0 10px;
}

.mbrRegist .mbr_zipcode .mbrBtnAuth {
    width: 82px;
    height: 30px;
    border: 1px solid #e6e6e6;
    vertical-align: top;
}

.mbrRegist .mbr_zipcode .icon-search {
    display: block;
    position: absolute;
    right: 10px;
    top: 22px;
    width: 18px;
    height: 17px;
    background: url('../../../assets/img/sprite-icon.png') no-repeat 0 -166px;
}

/* 휴대폰 */
.mbrRegist .mbr_phone select {
    float: left;
}

.mbrRegist .mbr_phone .delimiter {
    float: left;
    margin: 8px 2px 0;
    color: #aaa;
}

.mbrRegist .mbr_phone_1 input[type=text] {
    width: 219px;
}

.mbrRegist .mbr_phone .col_3 {
    position: relative;
    padding-top: 10px;
}

.mbrRegist .mbr_phone .col_3 input[type="checkbox"] {
    position: absolute;
    right: 0;
    top: 0;
    width: 23px;
    height: 23px;
    margin: 0;
    padding: 0;
    outline: none;
    -webkit-appearance: none;
    -moz-appearance: none;
    -o-appearance: none;
    appearance: none;
    -webkit-border-radius: 0;
    -moz-border-radius: 0;
    border-radius: 0;
}

.mbrRegist .mbr_phone .col_3 label {
    position: relative;
    display: block;
    height: 23px;
    font-size: 13px;
    letter-spacing: 0px;
    color: #666;
    box-sizing: border-box;
}

.mbrRegist .mbr_phone .col_3 label:after {
    content: '';
    display: block;
    position: absolute;
    right: 0;
    top: 0;
    width: 20px;
    height: 20px;
    background: url('../../../assets/img/sprite-icon.png') no-repeat 0 -253px;
}

.mbrRegist .mbr_phone .col_3 label.on:after {
    background-position: 0 -253px;
}

.mbrRegist .mbr_phone .buttonSendCertification {
    display: block;
    position: absolute;
    right: 0;
    top: 0;
    width: 144px;
    height: 60px;
    border-left: 1px solid #ddd;
    background-color: #f2f4f7;
    box-sizing: border-box;
    text-align: center;
}

.mbrRegist .mbr_phone .buttonSendCertification span {
    display: block;
    height: 60px;
    padding: 18px 22px;
    box-sizing: border-box;
    font-size: 16px;
    line-height: 24px;
    letter-spacing: 0;
    color: #6a6a6a;
}

/* 이메일 */
.mbrRegist .mbr_email input[type=text]:first-child {
    margin: 0;
}

.mbrRegist .mbr_email .delimiter {
    float: left;
    margin: 8px 0 0 1px;
    color: #333;
}

.mbrRegist .mbr_email .col_3 {
    position: relative;
    padding-top: 10px;
}

.mbrRegist .mbr_email .col_3 input[type="checkbox"] {
    position: absolute;
    right: 0;
    top: 0;
    width: 23px;
    height: 23px;
    margin: 0;
    padding: 0;
    outline: none;
    -webkit-appearance: none;
    -moz-appearance: none;
    -o-appearance: none;
    appearance: none;
    -webkit-border-radius: 0;
    -moz-border-radius: 0;
    border-radius: 0;
}

.mbrRegist .mbr_email .col_3 label {
    position: relative;
    display: block;
    height: 23px;
    font-size: 13px;
    letter-spacing: 0px;
    color: #666;
    box-sizing: border-box;
}

.mbrRegist .mbr_email .col_3 label:after {
    content: '';
    display: block;
    position: absolute;
    right: 0;
    top: 0;
    width: 20px;
    height: 20px;
    background: url('../../../assets/img/sprite-icon.png') no-repeat 0 -283px;
}

.mbrRegist .mbr_email .col_3 label.on:after {
    background-position: 0 -253px;
}

/* 회원가입 버튼 */
.mbrRegist .regist_complete {
    padding: 0;
    border: 0;
    background-color: transparent;
}

.mbrRegist .regist_complete .row {
    margin-bottom: 0;
}

.mbrRegist .regist_complete .row .mbrBtnRegist {
    background-color: #212b36;
    text-align: center;
    height: 100%;
    display: block;
    padding: 16px;
    box-sizing: border-box;
    font-size: 18px !important;
    line-height: 26px !important;
    font-weight: 700;
    color: #fff;
    width: 100%;
}


.mbrRegist .mbrBtnRegist {
    width: 100%;
    padding: 0;
    vertical-align: middle;
    height: 75px;
}

.mbrRegist .mbrBtnRegist span {
    display: block;
    box-sizing: border-box;
    font-size: 18px !important;
    line-height: 26px !important;
    font-weight: 700;
    color: #fff;
    width: 100%;
    height: 100%;
}

.mbrRegist .regist_complete .goJoinFaq {
    display: block;
    margin: 7px 0 0;
    text-align: right;
}

.mbrRegist .regist_complete .goJoinFaq a {
    font-size: 11px;
    letter-spacing: 0px;
    color: #9d9d9d;
    text-decoration: underline;
}

/* Family Site */
.mbrRegist .familysite {
    margin-top: 10px;
    font-weight: bold;
    text-align: center;
    color: #333;
}

.mbrRegist .familysite ul {
    width: 100%;
    margin: 9px 0 0;
    text-align: center;
    font-size: 0;
}

.mbrRegist .familysite ul li {
    display: inline-block;
    position: relative;
    overflow: hidden;
    height: 15px;
    padding-left: 13px;
    margin-left: 13px;
    box-sizing: border-box;
    vertical-align: top;
}

.mbrRegist .familysite ul li.albamon {
    margin-left: 0;
    padding-left: 0;
}

.mbrRegist .familysite ul li.albamon:before {
    display: none;
    padding-left: 0;
    margin-left: 0;
}

.mbrRegist .familysite ul li.albamon a {
    width: 56px;
    height: 15px;
    background-position: -10px -1px;
}

.mbrRegist .familysite ul li.hrpartners {
    width: 83px;
}

.mbrRegist .familysite ul li.devjob {
    width: 98px;
}

.mbrRegist .familysite ul li.campusmon a {
    width: 71px;
    height: 15px;
    background-position: -185px -2px;
}

.mbrRegist .familysite ul li.jobbusan {
    width: 90px;
}

.mbrRegist .familysite ul li a {
    overflow: hidden;
    display: block;
    height: 15px;
    white-space: nowrap;
    text-indent: -999em;
}

.mbrRegist .list-social {
    display: inline-block;
}

.mbrRegist .list-social ul:after {
    content: '';
    display: block;
    clear: both;
}

.mbrRegist .list-social li {
    float: left;
}

.mbrRegist .list-social li:nth-child(n+2) {
    margin-left: 16px;
}

.mbrRegist .list-social li a {
    display: block;
    width: 40px;
    height: 40px;
    border-radius: 50%;
    background: no-repeat center;
    background-size: 100%;
    font-size: 0;
    text-indent: -9999px;
}

.mbrRegist .list-social li a.naver {
    background: url('data:image/svg+xml;base64,PHN2ZyBpZD0iaWNvX3Nuc19uYXZlcl80MCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgd2lkdGg9IjQwIiBoZWlnaHQ9IjQwIiB2aWV3Qm94PSIwIDAgNDAgNDAiPg0KICA8ZGVmcz4NCiAgICA8Y2xpcFBhdGggaWQ9ImNsaXAtcGF0aCI+DQogICAgICA8cmVjdCBpZD0i7IKs6rCB7ZiVXzIyMiIgZGF0YS1uYW1lPSLsgqzqsIHtmJUgMjIyIiB3aWR0aD0iNDAiIGhlaWdodD0iNDAiIGZpbGw9Im5vbmUiLz4NCiAgICA8L2NsaXBQYXRoPg0KICA8L2RlZnM+DQogIDxnIGlkPSLqt7jro7lfMjc3IiBkYXRhLW5hbWU9Iuq3uOujuSAyNzciIGNsaXAtcGF0aD0idXJsKCNjbGlwLXBhdGgpIj4NCiAgICA8cGF0aCBpZD0i7Yyo7IqkXzUwIiBkYXRhLW5hbWU9Iu2MqOyKpCA1MCIgZD0iTTIwLDQwaDBBMjAsMjAsMCwxLDEsNDAsMjAsMjAuMDU5LDIwLjA1OSwwLDAsMSwyMCw0MCIgZmlsbD0iIzAzYzc1YSIvPg0KICAgIDxwYXRoIGlkPSLtjKjsiqRfNTEiIGRhdGEtbmFtZT0i7Yyo7IqkIDUxIiBkPSJNMjIuNiwyMC41MWwtNS40MTItNy43NDhIMTIuN1YyNy4yMzhoNC43VjE5LjQ5MWw1LjQxMyw3Ljc0N0gyNy4zVjEyLjc2MkgyMi42WiIgZmlsbD0iI2ZmZiIvPg0KICA8L2c+DQo8L3N2Zz4NCg==');
}

.mbrRegist .list-social li a.kakao {
    background: url('data:image/svg+xml;base64,PHN2ZyBpZD0iaWNvX3Nuc19rYWthb180MCIgeG1sbnM9Imh0dHA6Ly93d3cudzMub3JnLzIwMDAvc3ZnIiB4bWxuczp4bGluaz0iaHR0cDovL3d3dy53My5vcmcvMTk5OS94bGluayIgd2lkdGg9IjQwIiBoZWlnaHQ9IjQwIiB2aWV3Qm94PSIwIDAgNDAgNDAiPg0KICA8ZGVmcz4NCiAgICA8Y2xpcFBhdGggaWQ9ImNsaXAtcGF0aCI+DQogICAgICA8cmVjdCBpZD0i7IKs6rCB7ZiVXzIyNCIgZGF0YS1uYW1lPSLsgqzqsIHtmJUgMjI0IiB3aWR0aD0iNDAiIGhlaWdodD0iNDAiIGZpbGw9Im5vbmUiLz4NCiAgICA8L2NsaXBQYXRoPg0KICA8L2RlZnM+DQogIDxnIGlkPSLqt7jro7lfMjgzIiBkYXRhLW5hbWU9Iuq3uOujuSAyODMiIGNsaXAtcGF0aD0idXJsKCNjbGlwLXBhdGgpIj4NCiAgICA8cGF0aCBpZD0i7Yyo7IqkXzU1IiBkYXRhLW5hbWU9Iu2MqOyKpCA1NSIgZD0iTTQwLDIwQTIwLDIwLDAsMSwxLDIwLDAsMjAsMjAsMCwwLDEsNDAsMjAiIGZpbGw9IiNmZWU1MDAiLz4NCiAgICA8cGF0aCBpZD0i7Yyo7IqkXzU2IiBkYXRhLW5hbWU9Iu2MqOyKpCA1NiIgZD0iTTE1LDYuMjVjLTUuMjQzLDAtOS40OTMsMy4zLTkuNDkzLDcuMzY5YTcuMDk0LDcuMDk0LDAsMCwwLDQuMTQ4LDYuMDg5TDguNiwyMy41NzNhLjM4Ny4zODcsMCwwLDAsLjI4MS40NzEuMzgxLjM4MSwwLDAsMCwuMzEzLS4wNTVsNC42MjItMy4wNmMuMzkxLjAzNi43ODYuMDU5LDEuMTkuMDU5LDUuMjQxLDAsOS40OTMtMy4zLDkuNDkzLTcuMzY5UzIwLjI0NCw2LjI1LDE1LDYuMjUiIHRyYW5zZm9ybT0idHJhbnNsYXRlKDQuOTk3IDUuNzUpIi8+DQogIDwvZz4NCjwvc3ZnPg0K');
}

.mbrRegist .list-social li a.facebook {
    background: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iNDAiIGhlaWdodD0iNDAiIHZpZXdCb3g9IjAgMCA0MCA0MCI+DQogIDxkZWZzPg0KICAgIDxjbGlwUGF0aCBpZD0iY2xpcC1wYXRoIj4NCiAgICAgIDxyZWN0IGlkPSLsgqzqsIHtmJVfMjQiIGRhdGEtbmFtZT0i7IKs6rCB7ZiVIDI0IiB3aWR0aD0iOC44NCIgaGVpZ2h0PSIxNy41IiBmaWxsPSIjZmZmIi8+DQogICAgPC9jbGlwUGF0aD4NCiAgPC9kZWZzPg0KICA8ZyBpZD0iaWNvX3Nuc19mYWNlYm9va18zMiIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoLTE0ODUgMzMpIj4NCiAgICA8Y2lyY2xlIGlkPSLtg4Dsm5BfOCIgZGF0YS1uYW1lPSLtg4Dsm5AgOCIgY3g9IjIwIiBjeT0iMjAiIHI9IjIwIiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgxNDg1IC0zMykiIGZpbGw9IiMzYTVjYTkiLz4NCiAgICA8ZyBpZD0i6re466O5XzUwIiBkYXRhLW5hbWU9Iuq3uOujuSA1MCIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMTUwMSAtMjIpIj4NCiAgICAgIDxnIGlkPSLqt7jro7lfNDIiIGRhdGEtbmFtZT0i6re466O5IDQyIiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgwKSIgY2xpcC1wYXRoPSJ1cmwoI2NsaXAtcGF0aCkiPg0KICAgICAgICA8cGF0aCBpZD0i7Yyo7IqkXzE2IiBkYXRhLW5hbWU9Iu2MqOyKpCAxNiIgZD0iTS4yODIsOS4zMkgyLjJ2Ny45YS4yODIuMjgyLDAsMCwwLC4yODIuMjgySDUuNzM1YS4yODIuMjgyLDAsMCwwLC4yODItLjI4MlY5LjM1OEg4LjIyM2EuMjgyLjI4MiwwLDAsMCwuMjgtLjI1TDguODM4LDYuMmEuMjgyLjI4MiwwLDAsMC0uMjgtLjMxNUg2LjAxOFY0LjA2M2MwLS41NDkuMy0uODI4Ljg3OS0uODI4SDguNTU4YS4yODIuMjgyLDAsMCwwLC4yODItLjI4MlYuMjg0QS4yODIuMjgyLDAsMCwwLDguNTU4LDBINi4yNjljLS4wMTYsMC0uMDUyLDAtLjEsMEE0LjM4Niw0LjM4NiwwLDAsMCwzLjMsMS4wODFhMy4wMDgsMy4wMDgsMCwwLDAtMSwyLjY3M1Y1Ljg4NkguMjgyQS4yODIuMjgyLDAsMCwwLDAsNi4xNjh2Mi44N2EuMjgyLjI4MiwwLDAsMCwuMjgyLjI4MiIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMCkiIGZpbGw9IiNmZmYiLz4NCiAgICAgIDwvZz4NCiAgICA8L2c+DQogIDwvZz4NCjwvc3ZnPg0K');
}

.mbrRegist .list-social li a.google {
    background: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iNDAiIGhlaWdodD0iNDAiIHZpZXdCb3g9IjAgMCA0MCA0MCI+DQogIDxkZWZzPg0KICAgIDxjbGlwUGF0aCBpZD0iY2xpcC1wYXRoIj4NCiAgICAgIDxyZWN0IGlkPSLsgqzqsIHtmJVfMzYiIGRhdGEtbmFtZT0i7IKs6rCB7ZiVIDM2IiB3aWR0aD0iMTcuNjQiIGhlaWdodD0iMTgiIGZpbGw9Im5vbmUiLz4NCiAgICA8L2NsaXBQYXRoPg0KICA8L2RlZnM+DQogIDxnIGlkPSJpY29fc25zX2dvb2dsZV8zMiIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoLTc4NiAtNjA1NikiPg0KICAgIDxnIGlkPSLtg4Dsm5BfNSIgZGF0YS1uYW1lPSLtg4Dsm5AgNSIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoNzg2IDYwNTYpIiBmaWxsPSIjZmZmIiBzdHJva2U9IiNlM2U1ZTgiIHN0cm9rZS13aWR0aD0iMSI+DQogICAgICA8Y2lyY2xlIGN4PSIyMCIgY3k9IjIwIiByPSIyMCIgc3Ryb2tlPSJub25lIi8+DQogICAgICA8Y2lyY2xlIGN4PSIyMCIgY3k9IjIwIiByPSIxOS41IiBmaWxsPSJub25lIi8+DQogICAgPC9nPg0KICAgIDxnIGlkPSLqt7jro7lfOTMiIGRhdGEtbmFtZT0i6re466O5IDkzIiB0cmFuc2Zvcm09InRyYW5zbGF0ZSg3OTcgNjA2NykiPg0KICAgICAgPGcgaWQ9Iuq3uOujuV85MiIgZGF0YS1uYW1lPSLqt7jro7kgOTIiIGNsaXAtcGF0aD0idXJsKCNjbGlwLXBhdGgpIj4NCiAgICAgICAgPHBhdGggaWQ9Iu2MqOyKpF8zMSIgZGF0YS1uYW1lPSLtjKjsiqQgMzEiIGQ9Ik0xNy42NCw5LjJhMTAuMjc1LDEwLjI3NSwwLDAsMC0uMTY0LTEuODQxSDlWMTAuODVoNC44NDRhNC4xNTEsNC4xNTEsMCwwLDEtMS44MDksMi43MDh2Mi4yNjZoMi45MjFBOC43OTMsOC43OTMsMCwwLDAsMTcuNjQsOS4yIiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgwIDApIiBmaWxsPSIjNDI4NWY0Ii8+DQogICAgICAgIDxwYXRoIGlkPSLtjKjsiqRfMzIiIGRhdGEtbmFtZT0i7Yyo7IqkIDMyIiBkPSJNOSwxOGE4LjU5MSw4LjU5MSwwLDAsMCw1Ljk1Ni0yLjE3NmwtMi45MjEtMi4yNjdBNS40MDksNS40MDksMCwwLDEsMy45NiwxMC43MThILjk2NXYyLjMyNEE4Ljk5Myw4Ljk5MywwLDAsMCw5LDE4IiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgwIDApIiBmaWxsPSIjMzRhODUzIi8+DQogICAgICAgIDxwYXRoIGlkPSLtjKjsiqRfMzMiIGRhdGEtbmFtZT0i7Yyo7IqkIDMzIiBkPSJNMy45NiwxMC43MWE1LjI1NSw1LjI1NSwwLDAsMSwwLTMuNDJWNC45NjZILjk2NWE4LjkxNCw4LjkxNCwwLDAsMCwwLDguMDY4TDMuMywxMS4yMTdsLjY2My0uNTA3IiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgwIDApIiBmaWxsPSIjZmJiYzA1Ii8+DQogICAgICAgIDxwYXRoIGlkPSLtjKjsiqRfMzQiIGRhdGEtbmFtZT0i7Yyo7IqkIDM0IiBkPSJNOSwzLjU4NGE0LjkxMSw0LjkxMSwwLDAsMSwzLjQ0NSwxLjM0MmwyLjU3Ny0yLjU3N0E4LjYzMyw4LjYzMywwLDAsMCw5LDAsOC45ODUsOC45ODUsMCwwLDAsLjk2NSw0Ljk2N0wzLjk2LDcuMjlBNS4zNzMsNS4zNzMsMCwwLDEsOSwzLjU4NCIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMCAwKSIgZmlsbD0iI2VhNDMzNSIvPg0KICAgICAgPC9nPg0KICAgIDwvZz4NCiAgPC9nPg0KPC9zdmc+DQo=');
}

.mbrRegist .list-social li a.apple {
    background: url('data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHhtbG5zOnhsaW5rPSJodHRwOi8vd3d3LnczLm9yZy8xOTk5L3hsaW5rIiB3aWR0aD0iNDAiIGhlaWdodD0iNDAiIHZpZXdCb3g9IjAgMCA0MCA0MCI+DQogIDxkZWZzPg0KICAgIDxjbGlwUGF0aCBpZD0iY2xpcC1wYXRoIj4NCiAgICAgIDxyZWN0IGlkPSLsgqzqsIHtmJVfMTEiIGRhdGEtbmFtZT0i7IKs6rCB7ZiVIDExIiB3aWR0aD0iMTQuMjUiIGhlaWdodD0iMTcuNSIgZmlsbD0iI2ZmZiIvPg0KICAgIDwvY2xpcFBhdGg+DQogIDwvZGVmcz4NCiAgPGcgaWQ9Imljb19zbnNfYXBwbGVfNDAiIHRyYW5zZm9ybT0idHJhbnNsYXRlKC0xNDQ3IDE2NCkiPg0KICAgIDxnIGlkPSLqt7jro7lfMjkiIGRhdGEtbmFtZT0i6re466O5IDI5IiB0cmFuc2Zvcm09InRyYW5zbGF0ZSgxNDQ3IC0xNjQpIj4NCiAgICAgIDxjaXJjbGUgaWQ9Iu2DgOybkF81IiBkYXRhLW5hbWU9Iu2DgOybkCA1IiBjeD0iMjAiIGN5PSIyMCIgcj0iMjAiLz4NCiAgICA8L2c+DQogICAgPGcgaWQ9Iuq3uOujuV8zMSIgZGF0YS1uYW1lPSLqt7jro7kgMzEiIHRyYW5zZm9ybT0idHJhbnNsYXRlKDE0NjAgLTE1NC4wNSkiPg0KICAgICAgPGcgaWQ9Iuq3uOujuV8xOSIgZGF0YS1uYW1lPSLqt7jro7kgMTkiIGNsaXAtcGF0aD0idXJsKCNjbGlwLXBhdGgpIj4NCiAgICAgICAgPHBhdGggaWQ9Iu2MqOyKpF8xMCIgZGF0YS1uYW1lPSLtjKjsiqQgMTAiIGQ9Ik0xMS45LDQ1LjNhMy45NTIsMy45NTIsMCwwLDEsMS44ODktMy4zMjcsNC4xLDQuMSwwLDAsMC0zLjItMS43MzljLTEuMzY0LS4xMzctMi42NjIuOC0zLjM1Mi44cy0xLjc1OS0uNzgyLTIuODg4LS43NTlBNC4yODMsNC4yODMsMCwwLDAsLjczMyw0Mi40NjdjLTEuNTQzLDIuNjc4LS40LDYuNjQ3LDEuMTEsOC44MThDMi41NzgsNTIuMzQ4LDMuNDUzLDUzLjU0Miw0LjYsNTMuNWMxLjExLS4wNDMsMS41MjktLjcxNiwyLjg3LS43MTZzMS43MTYuNzE2LDIuODkuN2MxLjE5MS0uMDI1LDEuOTQ4LTEuMDg4LDIuNjc2LTIuMTUxYTkuNzA3LDkuNzA3LDAsMCwwLDEuMjEzLTIuNDkxQTMuODYxLDMuODYxLDAsMCwxLDExLjksNDUuMyIgdHJhbnNmb3JtPSJ0cmFuc2xhdGUoMCAtMzYpIiBmaWxsPSIjZmZmIi8+DQogICAgICAgIDxwYXRoIGlkPSLtjKjsiqRfMTEiIGRhdGEtbmFtZT0i7Yyo7IqkIDExIiBkPSJNNzAuMTUsMi43OTJBMy44MzgsMy44MzgsMCwwLDAsNzEuMDYxLDBhMy45MTksMy45MTksMCwwLDAtMi41NzYsMS4zMjQsMy42NjcsMy42NjcsMCwwLDAtLjkzLDIuNzFBMy4yNDgsMy4yNDgsMCwwLDAsNzAuMTUsMi43OTIiIHRyYW5zZm9ybT0idHJhbnNsYXRlKC02MC40NTIgMC4wMDEpIiBmaWxsPSIjZmZmIi8+DQogICAgICA8L2c+DQogICAgPC9nPg0KICA8L2c+DQo8L3N2Zz4NCg==');
}

.mbrRegist .row_group_social {
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 28px 32px;
    height: 96px;
}

.mbrRegist .row_group_social h4 {
    display: inline-block;
    margin-bottom: 0;
    padding-top: 0;
}

.mbr_info {
    padding-bottom: 20px !important;
}

.mbr_info h4 {
    margin-bottom: 19px;
    padding-top: 35px;
}

.input {
    position: relative;
    display: inline-block;
    zoom: 1;
    width: 100%;
    height: 62px;
    border: 1px solid #ddd;
    box-sizing: border-box;
    vertical-align: top;
    text-align: left;
    background-color: #fff;
}

/* Input Component */
.input .icon-search {
    display: block;
    position: absolute;
    right: 10px;
    top: 22px;
    width: 18px;
    height: 17px;
    background: url('../../../assets/img/sprite-icon.png') no-repeat 0 -166px;
}

.input.error {
    z-index: 1000;
    border-color: #eb8f8a;
}

.input.focus {
    z-index: 100;
    border-color: #3399ff;
}

.input div.label,
.input label {
    position: absolute;
    left: 15px;
    top: 21px;
    color: #999;
    font-size: 16px;
    letter-spacing: 0px;
    transition: all .2s ease-out;
}

.input input {
    display: block;
    width: 100%;
    height: 60px;
    padding: 0 15px;
    border: 0;
    color: #333;
    outline: none;
    box-sizing: border-box;
    font-size: 16px;
    letter-spacing: 0px;
}

.input .value {
    width: 100%;
    height: 60px;
    padding: 0 15px;
    font-size: 16px;
    letter-spacing: 0px;
    color: #333;
}

.input.is-label div.label i.icon.required,
.input.is-label label i.icon.required {
    margin-left: 5px;
    font-size: 16px;
    letter-spacing: 0px;
    color: #ff3333;
}

.input.is-label.is-value {
    padding-top: 19px;
}

.input.is-label.is-value .label,
.input.is-label.is-value label {
    top: 9px;
    font-size: 11px;
    letter-spacing: 0px;
    color: #a8a8a8;
}

.input.is-label.is-value div.label i.icon.required,
.input.is-label.is-value label i.icon.required {
    font-size: 11px;
    letter-spacing: 0px;
}

.input.is-label.is-value input {
    height: 41px;
}

.input.is-label.is-value .value {
    height: 41px;
    padding-top: 10px;
    box-sizing: border-box;
}

.input.is-label.focus {
    padding-top: 19px;
}

.input.is-label.focus label {
    top: 9px;
    font-size: 11px;
    letter-spacing: 0px;
}

.input.is-label.focus label i.icon.required {
    font-size: 11px;
    letter-spacing: 0px;
}

.input.is-label.focus input {
    height: 41px;
}

/* 200305 기업 회원가입 하단 회사 소개 */
.mbrRegist .familysite .text {
    font-size: 13px;
    letter-spacing: 0px;
    font-weight: normal;
}

.disabled {
    background: #f4f4f4;
}

.disabled>input {
    background: transparent;
}

.mbrRegist .attach-file {
    cursor: pointer;
}

.mbrRegist .attach-file .col_2 {
    min-height: 62px;
    height: inherit;
}

.mbrRegist .attach-file .input-file {
    position: absolute;
    display: flex;
    right: 0;
    top: 0;
    top: 0;
    bottom: 0;
    width: 119px;
    height: 100%;
    border-left: 1px solid #ddd;
    background-color: #fafbfc;
    text-align: center;
    font-size: 16px;
    letter-spacing: 0px;
    color: #666;
    line-height: 22px;
    box-sizing: border-box;
    justify-content: center;
    align-items: center;
}

.mbrRegist .attach-file label {
    cursor: pointer;
}

.mbrRegist .attach-file input {
    width: 0.1px;
    height: 0.1px;
    opacity: 0;
    overflow: hidden;
    position: absolute;
    z-index: -1;
}

.mbrRegist .attached {
    padding: 16px 120px 14px 0;
    font-size: 0;
}

.mbrRegist .attach-item {
    display: inline-block;
    position: relative;
    padding: 3px 21px 3px 0;
    margin-right: 20px;
    vertical-align: top;
}

.mbrRegist .attach-item .name {
    font-size: 16px;
    letter-spacing: 0px;
    color: #000;
    line-height: 22px;
}

.mbrRegist .attach-item button {
    overflow: hidden;
    display: block;
    position: absolute;
    right: 0;
    top: 5px;
    width: 17px;
    height: 17px;
    font-size: 0;
    color: transparent;
}

.mbrRegist .attach-file-help {
    margin: 11px 0 8px;
    cursor: auto;
}

.mbrRegist .attach-file-help p {
    font-size: 13px;
    letter-spacing: 0px;
    color: #666;
    line-height: 20px;
}

.mbrRegist .attach-file-help p:last-child {
    margin-top: 5px;
}

.mbrRegist .foreign .attach-file-help p:last-child {
    margin-top: 0px;
}

.mbrRegist .attach-file-help p a {
    text-decoration: underline;
    color: inherit
}

.mbrRegist .attach-file-help strong {
    font-weight: bold;
}

.policy-table {
    width: 100%;
    margin: 5px 0 8px 0;
    background: #fff;
    font-size: 12px;
    letter-spacing: 0px;
    color: #888888;
    line-height: 24px;
}

.policy-table,
.policy-table th,
.policy-table td {
    border: 1px solid #eaeaea;
}

.policy-table caption.skip {
    display: none;
}

.policy-table caption {
    text-align: left;
    padding: 0 0 5px 0;
}

.policy-table th {
    padding: 7px 0 5px 0;
    background-color: #f7f7f7;
    text-align: center;
}

.policy-table td {
    padding: 15px;
    vertical-align: top;
}

.policy-table td.end {
    text-align: center;
}
</style>