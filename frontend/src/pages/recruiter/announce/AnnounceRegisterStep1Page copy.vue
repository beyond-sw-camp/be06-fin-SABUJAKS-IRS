<script setup>
import MainHeaderComponent from '../../../components/recruiter/MainHeaderComponent.vue';
import MainSideBarComponent from '../../../components/recruiter/MainSideBarComponent.vue';
</script>

<template>
  <MainHeaderComponent></MainHeaderComponent>
  <div class="container">
    <MainSideBarComponent></MainSideBarComponent>
    <div id="content">
      <div class="wrap_recruit_frm">
        <div class="wrap_step_recruit">
          <!-- 공고 기본설정-->
          <section id="frmRecType" class="step_recruit">
            <div class="frm_header">
              <h2 class="tit_step">공고 기본 설정</h2>
              <div class="notice_right"><em class="required">*</em> 필수항목</div>
            </div>
            <div class="frm_body frm_rec_type">
              <div class="title_recruit" style="display: block;">
                <button type="button" class="btn_recruit ga_data_layer" data-ga_data_layer="ga_lead|pc_recruit_progressbar|btn_title" id="btn_title_recruit_layer">기존 등록했던 공고를 불러올 수 있습니다.</button>
                <div class="layer_recruit">
                  <div class="area_scroll ScrollBar">
                    <ul class="list_recruit" id="existing_recruit_list" data-speed_matching="n">
                      <li data-rec_idx="48919829" data-rec_type_cd="rtc001"><a href="javascript:void(0)"><b class="status ">진행</b> <span class="type">일반채용</span> IT 교육(부트캠프) 교육운영 지원 포지션 채용 <span class="date">08-30</span></a></li>
                      <li data-rec_idx="48577054" data-rec_type_cd="rtc001"><a href="javascript:void(0)"><b class="status end">마감</b> <span class="type">일반채용</span> IT 부트캠프 오프라인 교육 운영 관리 담당자 <span class="date">07-19</span></a></li>
                    </ul>
                  </div>
                </div>
              </div>

              <div class="items" id="recruit_title">
                <div class="area_tit">
                  <em class="required">*</em>
                  <h3 class="tit">공고 제목</h3>
                  <div class="TipBox">
                    <button type="button" class="ico_question"><svg>
                      <use xlink:href="#ico_question"></use>
                    </svg><span class="blind">도움말</span></button>
                    <div class="TipCont TopCenter">
                                    <span class="Tail"><svg class="icon_tail">
                                            <use xlink:href="#TipBox_tail"></use>
                                        </svg></span>
                      <div class="TipTxt">
                        내부 공고관리 규정에 위배되는 채용제목을 입력 시,<br>별도 안내 없이 수정 또는 변경될 수 있습니다.
                      </div>
                    </div>
                  </div>
                </div>
                <div class="area_data">
                  <div class="template_title">
                    <div class="TypoBox">
                      <input type="text" class="Typo SizeL" id="title" aria-label="공고 제목" placeholder="직무명이 포함된 공고 제목을 지원자들이 선호해요. (ex. 하반기 기계조작원 신입채용)">
                    </div>
                  </div>
                  <div class="MsgTxt Invalid" style="display:none;">공고 제목을 입력해주세요.</div>
                </div>
              </div>
            </div>
          </section>
          <!-- 모집분야 -->
          <section id="frmDivision" class="step_recruit">
            <div class="frm_header">
              <h2 class="tit_step">모집분야</h2>
              <div class="frm_set">
                <button type="button" class="BtnType SizeM GrayBg btn_add_field btn_recruit_frm ga_data_layer" data-ga_data_layer="ga_lead|pc_rec_division|btn_add">모집분야 추가
                </button>
                <div class="btn_box">
                  <button class="BtnType SizeM GrayInvert btn_order btn_down_first" data-seq="0" title="한칸 아래로 이동" style="display: none;">
                    <svg class="BtnIcon">
                      <use xlink:href="#ico_down_black"></use>
                    </svg>
                    <span class="blind">한칸 아래로 이동</span>
                  </button>

                </div>
              </div>
            </div>
            <div class="frm_body frm_division">
              <div class="items" id="jobCategory">
                <div class="area_tit">
                  <em class="required">*</em>
                  <h3 class="tit">직무·직업</h3>
                  <!-- 툴팁 -->
                  <div class="TipBox">
                    <button type="button" class="ico_question"><svg>
                      <use xlink:href="#ico_question"></use>
                    </svg><span class="blind">도움말</span></button>
                    <div class="TipCont TopLeft">
                                    <span class="Tail"><svg class="icon_tail">
                                            <use xlink:href="#TipBox_tail_side"></use>
                                        </svg></span>
                      <div class="TipTxt">
                        선택한 값에 따라 직무/직업 섹션 페이지에 노출되며, <br>구직자가 해당 키워드로 검색시 공고가 노출됩니다.
                        <br>직무와 무관한 키워드 직접입력 시 삭제될 수 있습니다.<p></p>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="area_data">
                  <div class="selected_item">
                    <div class="selected_item_box">
                      <p class="txt_placeholder">대표 직무·직업 선택</p>
                      <ul class="list_selected_item"></ul>
                    </div>
                    <button type="button" class="BtnType SizeL BlueInvert btn_add"><svg class="BtnIcon">
                      <use xlink:href="#ico_btn_plus"></use>
                    </svg>선택</button>
                  </div>
                  <div class="MsgTxt Invalid" style="display: none;">직무·직업을 선택해주세요.</div>
                </div>
              </div>
              <div id="recDivisionRestrictToolTip" class="wrap_tip">
                <!-- 아래 툴팁이 나와야 할 상황에 클래스 on 추가 -->
                <div class="TipBox Click">
                  <div class="TipCont BottomRight">
                                <span class="Tail"><svg class="icon_tail">
                                        <use xlink:href="#TipBox_tail_side"></use>
                                    </svg></span>
                    <div class="TipTxt">
                      2개 이상 모집분야 등록시<br>공고가 바로 <em class="highlight">게재되지 않습니다.</em> (대기중)
                    </div>
                    <button type="button" class="BtnClose"><span class="blind">닫기</span></button>
                  </div>
                </div>
              </div>
              <ul class="list_division list_recruit_frm" id="list_recruit_division">
                <li class="division_line_detail  expand" id="line_detail_0" data-seq="0">
                  <div class="">
                    <div class="frm_header" style="display: none;">
                      <h2 class="tit_step">모집분야1</h2>
                      <div class="frm_set">
                        <div class="btn_box">
                          <button class="BtnType SizeM GrayInvert btn_order btn_up" data-seq="0" title="한칸 아래로 이동" style="display: none;">
                            <svg class="BtnIcon">
                              <use xlink:href="#ico_down_black"></use>
                            </svg>
                            <span class="blind">한칸 아래로 이동</span>
                          </button>
                          <button class="BtnType SizeM GrayInvert btn_order btn_down" data-seq="0" title="한칸 위로 이동" style="display: flex;">
                            <svg class="BtnIcon">
                              <use xlink:href="#ico_up_black"></use>
                            </svg>
                            <span class="blind">한칸 위로 이동</span>
                          </button>
                        </div>
                      </div>
                    </div>
                    <div class="summary">
                      <div class="items">
                        <div class="area_tit" id="recruit_division_title_area_0">
                          <em class="required">*</em>
                          <h3 class="tit">모집분야명</h3>
                        </div>
                        <div class="area_data">
                          <input type="hidden" id="original_title_0" value="">
                          <div class="division_name" id="division_name_area_0" data-seq="0">
                            <div class="TypoBox">
                              <span class="label_end_time" style="display: none;">마감</span>
                              <input type="text" id="recruitment_title_0" class="Typo SizeL recruitment_title" data-seq="0" data-help_target="display_division" value="" aria-label="모집분야명" placeholder="모집분야명 입력 (ex. 디지털 마케팅, 그래픽 디자인, 경영지원 등)" maxlength="30">
                            </div>
                            <div class="MsgTxt Invalid invalid_msg_title _invalid_msg_title_0" style="display:none;"></div>
                            <div class="MsgTxt Invalid _invalid_msg_whole_tab_0" id="invalid_msg_whole_msg_0" style="display:none"></div>
                          </div>
                          <div class="area_frm_btn">
                            <button type="button" class="BtnType SizeL GrayBg btn_end chg_state_btn ga_data_layer" id="chg_state_btn_0" data-seq="0" data-btn-state="delete-tmp" data-recruitment-seq="" data-ga_data_layer="ga_lead|pc_rec_division|btn_delete" title="모집분야 삭제" style="display: none;">삭제</button>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="fulldata">
                      <div class="items">
                        <div class="area_tit">
                          <em class="required">*</em>
                          <h3 class="tit">모집인원</h3>
                        </div>
                        <div class="area_data">
                          <span class="division_collect">
                              <div class="TypoBox">
                                  <input type="text" id="collect_cnt_0" class="Typo SizeL collect_cnt _filter" aria-label="모집인원" placeholder="ex) 00명" name="collect_cnt" data-filter="numeric" data-help_target="collect_cnt" value="0" maxlength="6">
                              </div>
                              명 모집
                          </span>
                          <div class="MsgTxt Invalid _invalid_msg_collect_0" style="display:none;">모집인원을 입력해주세요.</div>
                        </div>
                      </div>
                      <div class="items">
                        <div class="area_tit">
                          <em class="required">*</em>
                          <h3 class="tit">경력</h3>
                        </div>
                        <div class="area_data type_txt">
                          <div class="InpBox"><span class="Chk SizeL career_cd_0"><input type="checkbox" id="career_cd_0_1" value="0_1" data-help_target="hope_career"><label class="Lbl" for="career_cd_0_1">신입</label></span></div>
                          <div class="InpBox"><span class="Chk SizeL career_cd_0"><input type="checkbox" id="career_cd_0_2" value="0_2" data-help_target="hope_career"><label class="Lbl" for="career_cd_0_2">경력</label></span></div>
                          <div class="InpBox"><span class="Chk SizeL career_cd_0"><input type="checkbox" id="career_cd_0_0" value="0_0" data-help_target="hope_career"><label class="Lbl" for="career_cd_0_0">경력무관</label></span></div>
                          <div class="InpBox"><span class="Chk SizeL career_cd_sub_0_0" style="display:none"><input type="checkbox" class="freshman_can_apply" id="freshman_can_apply_0" value="y"><label class="Lbl" for="freshman_can_apply_0">신입 지원 가능</label></span></div>
                          <em class="MsgTxt Invalid _invalid_msg_career_0" style="display:none;">경력을 선택해주세요.</em>
                          <div class="career_check career_cd_sub_2_0" data-sub-for="0_2" style="display:none;">
                            <div class="InpBox SizeL">
                              <select class="career_period" id="career_min_0" name="career_min_0" data-seq="0">
                                <option value="">선택</option>
                                <option value="1">1년 이상</option>
                                <option value="2">2년 이상</option>
                                <option value="3">3년 이상</option>
                                <option value="4">4년 이상</option>
                                <option value="5">5년 이상</option>
                                <option value="6">6년 이상</option>
                                <option value="7">7년 이상</option>
                                <option value="8">8년 이상</option>
                                <option value="9">9년 이상</option>
                                <option value="10">10년 이상</option>
                                <option value="11">11년 이상</option>
                                <option value="12">12년 이상</option>
                                <option value="13">13년 이상</option>
                                <option value="14">14년 이상</option>
                                <option value="15">15년 이상</option>
                                <option value="16">16년 이상</option>
                                <option value="17">17년 이상</option>
                                <option value="18">18년 이상</option>
                                <option value="19">19년 이상</option>
                                <option value="20">20년 이상</option>
                              </select>
                            </div> ~
                            <div class="InpBox SizeL">
                              <select class="career_period" id="career_max_0" name="career_max_0" data-seq="0">
                                <option value="">선택</option>
                                <option value="1">1년 이하</option>
                                <option value="2">2년 이하</option>
                                <option value="3">3년 이하</option>
                                <option value="4">4년 이하</option>
                                <option value="5">5년 이하</option>
                                <option value="6">6년 이하</option>
                                <option value="7">7년 이하</option>
                                <option value="8">8년 이하</option>
                                <option value="9">9년 이하</option>
                                <option value="10">10년 이하</option>
                                <option value="11">11년 이하</option>
                                <option value="12">12년 이하</option>
                                <option value="13">13년 이하</option>
                                <option value="14">14년 이하</option>
                                <option value="15">15년 이하</option>
                                <option value="16">16년 이하</option>
                                <option value="17">17년 이하</option>
                                <option value="18">18년 이하</option>
                                <option value="19">19년 이하</option>
                                <option value="20">20년 이하</option>
                              </select>
                            </div>
                            <div class="InpBox"><span class="Chk SizeL"><input type="checkbox" class="no_matter_work_year" id="no_matter_work_year_0"><label class="Lbl" for="no_matter_work_year_0">연수무관</label></span>
                            </div>
                          </div>
                          <div class="MsgTxt Invalid _invalid_msg_career_sub_0" style="display:none;">경력 연수를 선택해주세요.</div>
                        </div>
                      </div>
                      <input type="hidden" id="assign_task_nm_0" value="" style="height: 0px;">
                      <!-- 담당업무 삭제 -->
                      <div class="items">
                        <div class="area_tit">
                          <h3 class="tit">항목 추가</h3>
                        </div>
                        <div class="area_data etc_items">
                          <ul class="list_checkbox">
                            <li>
                              <input type="checkbox" class="task_chk" id="task_chk_0" value="0"><label class="lbl ga_data_layer" for="task_chk_0" data-ga_data_layer="ga_lead|pc_rec_division|checkbox_task">주요업무</label>
                            </li>
                            <li>
                              <input type="checkbox" class="workpost_chk" id="workpost_chk_0" value="0"><label class="lbl ga_data_layer" for="workpost_chk_0" data-ga_data_layer="ga_lead|pc_rec_division|checkbox_department">근무부서</label>
                            </li>
                            <li>
                              <input type="checkbox" class="job_grade_chk" id="job_grade_chk_0" value="0"><label class="lbl ga_data_layer" for="job_grade_chk_0" data-ga_data_layer="ga_lead|pc_rec_division|checkbox_job_grade">직급직책</label>
                            </li>
                            <li>
                              <input type="checkbox" class="preferential_chk" id="preferential_chk_0" value="0"><label class="lbl ga_data_layer" for="preferential_chk_0" data-ga_data_layer="ga_lead|pc_rec_division|checkbox_preferred">필수/우대조건</label>
                            </li>
                          </ul>
                        </div>
                      </div>
                      <div class="division_detail">
                        <div class="items option" id="task_detail_0" style="">
                          <div class="area_tit">
                            <h4 class="tit">주요업무</h4>
                          </div>
                          <div class="area_data task_input_area" data-seq="0">
                            <div class="selected_item" id="">
                              <div class="TypoBox">
                                <input type="text" id="" value="" name="" class="Typo SizeL task_input" aria-label="주요업무" placeholder="주요 업무 입력" maxlength="50">
                              </div>
                              <button type="button" class="BtnType SizeL GrayInvert btn_add btn_plus">
                                <svg class="BtnIcon">
                                  <use xlink:href="#ico_btn_plus"></use>
                                </svg>
                                <span class="blind">추가</span>
                              </button>
                            </div>

                          </div>
                        </div>
                        <!-- 근무부서 체크 시 노출 -->
                        <div class="items option" id="workpost_detail_0" style="">
                          <div class="area_tit">
                            <h4 class="tit">근무부서</h4>
                          </div>
                          <div class="area_data">
                            <div class="TypoBox">
                              <input type="text" id="work_dept_nm_0" value="" name="work_dept_nm" class="Typo SizeL work_dept_nm" aria-label="근무부서" placeholder="근무하게 될 부서 또는 근무지를 입력" maxlength="60" data-help_target="workpost">
                            </div>
                          </div>
                        </div>
                        <!-- 직급직책 체크 시 노출 -->
                        <div class="items option" id="job_grade_detail_0" style="">
                          <div class="area_tit">
                            <h4 class="tit">직급직책</h4>
                          </div>
                          <div class="area_data">
                            <div class="selected_item" id="job_grade_summary_0">
                              <div class="selected_item_box">
                                <p class="txt_placeholder">직급직책 선택</p>
                                <ul class="list_selected_item" id="job_grade_frm_box_list_0" data-seq="0"></ul>
                              </div>
                              <button type="button" class="BtnType SizeL BlueInvert btn_add job_grade_setting_btn" id="job_grade_0_setting_btn" data-seq="0">
                                <svg class="BtnIcon">
                                  <use xlink:href="#ico_btn_plus"></use>
                                </svg>선택
                              </button>
                            </div>
                          </div>
                        </div>

                        <!-- 필수/우대조건 체크 시 노출 -->
                        <div class="items option preferential" id="preferential_detail_0" style="">
                          <div class="area_tit pt5">
                            <h4 class="tit">필수/우대조건</h4>
                          </div>
                          <div class="area_data">
                            <div class="list_recommend">
                              <strong class="tit">추천</strong>
                              <ul>
                                <li><input type="checkbox" class="major_recommend" id="maj024_0" value="maj024" data-text="공학계열"><label for="maj024_0">#공학계열</label></li>
                                <li><input type="checkbox" class="preference_recommend" id="pre043_0" value="pre043" data-text="해당직무 근무경험"><label for="pre043_0">#해당직무 근무경험</label></li>
                                <li><input type="checkbox" class="preference_recommend" id="pre026_0" value="pre026" data-text="인근거주자"><label for="pre026_0">#인근거주자</label></li>
                                <li><input type="checkbox" class="language_recommend" id="7453_0" value="7453" data-text="TOEIC"><label for="7453_0">#TOEIC
                                </label></li>
                              </ul>
                            </div>
                            <div class="selected_preference" id="preference_summary_0">
                              <div class="area_bottom">
                                <p class="txt_placeholder">보유역량, 자격증, 외국어, 전공학과 등 우대사항
                                  선택</p>
                                <ul class="list_preference" id="require_preference_frm_box_list_0"></ul>
                              </div>
                              <button type="button" class="BtnType SizeL BlueInvert btn_add btn_more_ly_essential_preference ga_data_layer" data-seq="0" data-ga_data_layer="ga_lead|pc_rec_division|layer_preferred"><svg class="BtnIcon">
                                <use xlink:href="#ico_btn_plus"></use>
                              </svg> 선택</button>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </li>
              </ul>
            </div>
            <em class="MsgTxt Invalid" style="display: none;">모집분야는 최대 5개까지 등록 가능합니다.</em>
          </section>
          <!-- 근무조건 -->
          <section id="frmCondition" class="step_recruit">
            <div class="frm_header">
              <h2 class="tit_step">지원자격/근무조건</h2>
            </div>
            <div class="frm_body frm_condition">
              <div class="items" id="eduItem">
                <div class="area_tit">
                  <em class="required">*</em>
                  <h3 class="tit">지원자 학력</h3>
                </div>
                <div class="area_data">
                  <div class="condition_edu">
                    <div class="check_list">
                      <div class="InpBox">
                        <span class="Chk SizeL"><input type="checkbox" id="etc_edu_yn"><label class="Lbl" for="etc_edu_yn">기타학력</label></span>
                      </div>
                      <div class="InpBox">
                        <span class="Chk SizeL"><input type="checkbox" id="edu_graduation"><label class="Lbl" for="edu_graduation">졸업예정자 지원가능</label></span>
                      </div>
                    </div>
                    <div class="InpBox SizeL">
                      <select id="edu_select" title="학력">
                        <option value="irr">학력무관</option>

                        <option value="_6">고등학교졸업이상</option>

                        <option value="_7">대학졸업(2,3년)이상</option>

                        <option value="_8">대학교졸업(4년)이상</option>

                        <option value="_9">석사졸업이상</option>

                        <option value="_5">박사졸업</option>
                      </select>
                    </div>
                    <div class="TypoBox" style="display: none">
                      <input type="text" id="etc_education" class="Typo SizeL ga_data_layer" data-ga_data_layer="ga_lead|pc_rec_condition|btn_academic" title="기타 학력사항 입력" placeholder="기타 학력사항 입력" maxlength="20">
                    </div>
                  </div>
                </div>
              </div>
              <div class="items" id="addressArea">
                <div class="area_tit">
                  <em class="required">*</em>
                  <h3 class="tit">근무지역</h3>
                </div>
                <div class="area_data" id="address">
                  <div class="manager_location">
                    <div class="InpBox">
                      <span class="Chk SizeL"><input type="checkbox" id="overseas" name="overseas" value="overseas"><label class="Lbl" for="overseas">해외지역</label></span>
                    </div>
                    <div class="InpBox">
                      <span class="Chk SizeL"><input type="checkbox" id="chk_typ3_01" name="work-site-cd" value="site050" data-help_target="company_address"><label class="Lbl" for="chk_typ3_01">재택근무 가능</label></span>
                    </div>
                  </div>
                  <div class="input_address internal">
                                <span class="manager_address">
                                    <div class="TypoBox">
                                        <input type="text" class="Typo SizeL address01 _inputWrapper" id="address_depth1" name="address" readonly="" data-help_target="company_address" aria-label="주소" placeholder="근무지역 입력">
                                    </div>
                                </span>
                    <span class="manager_address2">
                                    <div class="TypoBox">
                                        <input type="text" class="Typo SizeL address02 _inputWrapper" id="address_depth2" name="address_detail" data-help_target="company_address" aria-label="상세주소" placeholder="상세주소 입력 : 예) 187-10 코오롱싸이언스밸리">
                                    </div>
                                </span>
                    <button type="button" class="BtnType SizeL GrayBg" id="searchAddressBtn">변경</button>
                    <!--<button type="button" class="btnSizeL colorBlack btn_map" id="showAreaMapBtn"><span class="ico_map">지도위치확인</span></button>-->
                    <div class="MsgTxt Invalid _invalid_msg" id="address_empty_msg" style="display: none;">근무지역을 입력해주세요.</div>

                    <div class="wrap_map" id="mapBox" style="display:none">
                      <strong class="tit_map">위치/지도 확인 <span class="txt" id="locationName">서울
                                            동작구</span></strong>
                      <div class="map_area" id="mapArea"></div>
                      <span class="inpChk sizeM"><input type="checkbox" id="showMap"><label class="lbl" for="showMap">채용공고에 지도표시</label></span>
                      <button type="button" class="btn_close"><span class="blind">닫기</span></button>
                    </div>
                    <div class="MsgTxt Invalid _invalid_msg" id="address_lating_msg" style="display: none;">입력하신 주소로 지도를 불러올 수가 없습니다. 주소 확인바랍니다.</div>
                  </div>
                  <div class="data_oversea oversea" style="display: none;">
                    <div class="InpBox SizeL address01">
                      <select id="overseasPost" title="국가 선택">
                        <option value="">국가 선택</option>

                        <option value="260100">가나</option>

                        <option value="260200">가봉</option>

                        <option value="231300">가이아나</option>

                        <option value="261500">감비아</option>

                        <option value="231400">과델루프</option>

                        <option value="230100">과테말라</option>

                        <option value="250100">괌</option>

                        <option value="242000">그라나다</option>

                        <option value="211700">그루지아</option>

                        <option value="240100">그리스</option>

                        <option value="220400">그린란드</option>

                        <option value="261600">기니</option>

                        <option value="261700">기니비소우</option>

                        <option value="231500">기아나(프랑스령)</option>

                        <option value="261800">나미비아</option>

                        <option value="252200">나우루공화국</option>

                        <option value="260300">나이지리아</option>

                        <option value="239900">남미기타</option>

                        <option value="260400">남아프리카공화국</option>

                        <option value="240200">네덜란드</option>

                        <option value="221200">네덜란드령 안틸레스제도</option>

                        <option value="211800">네팔</option>

                        <option value="240300">노르웨이</option>

                        <option value="250200">뉴질랜드</option>

                        <option value="250600">뉴칼레도니아섬</option>

                        <option value="261900">니제르</option>

                        <option value="231600">니카라과</option>

                        <option value="210100">대만</option>

                        <option value="240400">덴마크</option>

                        <option value="220500">도미니카공화국</option>

                        <option value="231700">도미니카연방</option>

                        <option value="240500">독일</option>

                        <option value="214400">동티모르</option>

                        <option value="212000">라오스</option>

                        <option value="262000">라이베리아</option>

                        <option value="242100">라트비아</option>

                        <option value="240600">러시아</option>

                        <option value="262200">레뉴니용</option>

                        <option value="212100">레바논</option>

                        <option value="242200">루마니아</option>

                        <option value="242300">룩셈부르크</option>

                        <option value="262100">르완다</option>

                        <option value="260500">리비아</option>

                        <option value="242400">리투아니아</option>

                        <option value="245500">리히텐슈타인</option>

                        <option value="262300">마다가스카르</option>

                        <option value="231800">마르티니크섬</option>

                        <option value="250700">마샬군도</option>

                        <option value="242500">마케도니아</option>

                        <option value="262400">말라위</option>

                        <option value="210200">말레이시아</option>

                        <option value="262500">말리</option>

                        <option value="220100">멕시코</option>

                        <option value="242600">모나코</option>

                        <option value="260600">모로코</option>

                        <option value="265500">모리셔스</option>

                        <option value="262600">모리타니아</option>

                        <option value="262700">모잠비크</option>

                        <option value="245300">몬테네그로</option>

                        <option value="245400">몰도바</option>

                        <option value="212300">몰디브</option>

                        <option value="242700">몰타</option>

                        <option value="210300">몽골</option>

                        <option value="242800">몽트세라</option>

                        <option value="220200">미국</option>

                        <option value="210400">미얀마</option>

                        <option value="252000">미크로네시아</option>

                        <option value="250800">바누아투</option>

                        <option value="212400">바레인</option>

                        <option value="220600">바베이도스</option>

                        <option value="245200">바티칸시국</option>

                        <option value="220700">바하마</option>

                        <option value="210500">방글라데시</option>

                        <option value="212500">버마</option>

                        <option value="220800">버뮤다</option>

                        <option value="232000">버진제도</option>

                        <option value="262800">베냉</option>

                        <option value="230200">베네수엘라</option>

                        <option value="210600">베트남</option>

                        <option value="240700">벨기에</option>

                        <option value="242900">벨로루시</option>

                        <option value="232100">벨리즈</option>

                        <option value="243000">보스니아헤르체고비나</option>

                        <option value="262900">보츠와나</option>

                        <option value="232200">볼리비아</option>

                        <option value="263000">부룬디</option>

                        <option value="263100">부르키나파소</option>

                        <option value="212600">부탄</option>

                        <option value="229900">북·중미기타</option>

                        <option value="252100">북마리아나군도</option>

                        <option value="212700">북한</option>

                        <option value="243100">불가리아</option>

                        <option value="230300">브라질</option>

                        <option value="212800">브루나이</option>

                        <option value="250900">사모아</option>

                        <option value="210700">사우디아라비아</option>

                        <option value="221000">사이판</option>

                        <option value="245100">산마리노</option>

                        <option value="265400">상투메프린시페</option>

                        <option value="263200">세네갈</option>

                        <option value="245000">세르비아</option>

                        <option value="263300">세이셀제도</option>

                        <option value="221300">세인트루시아</option>

                        <option value="221400">세인트빈센트그레나딘즈</option>

                        <option value="221100">세인트키츠네비스</option>

                        <option value="220900">세인트피에르미그온</option>

                        <option value="263400">세인트헬레나섬</option>

                        <option value="263500">소말리아</option>

                        <option value="251900">솔로몬제도</option>

                        <option value="260700">수단</option>

                        <option value="232500">수리남</option>

                        <option value="210800">스리랑카</option>

                        <option value="263600">스와질랜드</option>

                        <option value="240800">스웨덴</option>

                        <option value="240900">스위스</option>

                        <option value="241000">스페인</option>

                        <option value="243300">슬로바키아</option>

                        <option value="243200">슬로베니아</option>

                        <option value="212900">시리아</option>

                        <option value="263700">시에라리온</option>

                        <option value="244300">신유고연방</option>

                        <option value="210900">싱가포르</option>

                        <option value="213000">아랍에미레이트연합국</option>

                        <option value="243400">아루바</option>

                        <option value="214700">아르메니아</option>

                        <option value="230400">아르헨티나</option>

                        <option value="219900">아시아·중동기타</option>

                        <option value="243600">아이슬란드</option>

                        <option value="232600">아이티</option>

                        <option value="243700">아일랜드</option>

                        <option value="214800">아제르바이잔</option>

                        <option value="213100">아프가니스탄</option>

                        <option value="269900">아프리카기타</option>

                        <option value="232700">안길라</option>

                        <option value="244900">안도라</option>

                        <option value="243900">알바니아</option>

                        <option value="263900">알제리</option>

                        <option value="264000">앙골라</option>

                        <option value="265300">에리트레아</option>

                        <option value="244000">에스토니아</option>

                        <option value="230500">에콰도르</option>

                        <option value="260900">에티오피아</option>

                        <option value="232800">엔티가바부다</option>

                        <option value="232900">엘살바도르</option>

                        <option value="241100">영국</option>

                        <option value="213200">영국령 인도양식민지</option>

                        <option value="213300">예멘</option>

                        <option value="213400">오만</option>

                        <option value="259900">오세아니아기타</option>

                        <option value="244100">오스트리아</option>

                        <option value="230600">온두라스</option>

                        <option value="213500">요르단</option>

                        <option value="261000">우간다</option>

                        <option value="230700">우루과이</option>

                        <option value="213600">우즈베키스탄</option>

                        <option value="244200">우크라이나</option>

                        <option value="249900">유럽기타</option>

                        <option value="213700">이라크</option>

                        <option value="213800">이란</option>

                        <option value="244400">이스라엘</option>

                        <option value="261100">이집트</option>

                        <option value="241200">이탈리아</option>

                        <option value="211000">인도</option>

                        <option value="211100">인도네시아</option>

                        <option value="211200">일본</option>

                        <option value="264100">자마이카</option>

                        <option value="264200">자이르</option>

                        <option value="264300">잠비아</option>

                        <option value="264400">적도기니</option>

                        <option value="211300">중국.홍콩</option>

                        <option value="264500">중앙아프리카공화국</option>

                        <option value="264600">지부티</option>

                        <option value="244500">지브롤터</option>

                        <option value="264700">짐바브웨</option>

                        <option value="264800">차드</option>

                        <option value="241300">체코</option>

                        <option value="230800">칠레</option>

                        <option value="264900">카메룬</option>

                        <option value="265800">카보베르데</option>

                        <option value="213900">카자흐스탄</option>

                        <option value="214000">카타르</option>

                        <option value="211400">캄보디아</option>

                        <option value="220300">캐나다</option>

                        <option value="261200">케냐</option>

                        <option value="233000">케이만제도</option>

                        <option value="265000">케이프버드</option>

                        <option value="265700">코모로</option>

                        <option value="221500">코스타리카</option>

                        <option value="263800">코트디브아르</option>

                        <option value="230900">콜롬비아</option>

                        <option value="265100">콩고</option>

                        <option value="265600">콩고민주공화국</option>

                        <option value="231000">쿠바</option>

                        <option value="214100">쿠웨이트</option>

                        <option value="251000">쿠크 군도</option>

                        <option value="244600">크로아티아</option>

                        <option value="214600">키르기즈스탄</option>

                        <option value="251100">키리바시</option>

                        <option value="244700">키프로스</option>

                        <option value="214200">타지키스탄</option>

                        <option value="261300">탄자니아</option>

                        <option value="211500">태국</option>

                        <option value="233200">터크스앤카이코스제도</option>

                        <option value="241400">터키</option>

                        <option value="265200">토고</option>

                        <option value="251800">토켈라우제도</option>

                        <option value="251200">통가</option>

                        <option value="214300">투르크메니스탄</option>

                        <option value="251300">투발루</option>

                        <option value="261400">튀니지</option>

                        <option value="233300">트리니나드토바고</option>

                        <option value="231100">파나마</option>

                        <option value="233400">파라과이</option>

                        <option value="214500">파키스탄</option>

                        <option value="250300">파푸아뉴기니</option>

                        <option value="251600">팔라우</option>

                        <option value="244800">페로제도</option>

                        <option value="231200">페루</option>

                        <option value="241500">포르투갈</option>

                        <option value="233500">포클랜드제도</option>

                        <option value="241600">폴란드</option>

                        <option value="251500">폴리네시아제도</option>

                        <option value="233600">푸에르토리코</option>

                        <option value="241700">프랑스</option>

                        <option value="250400">피지</option>

                        <option value="241800">핀란드</option>

                        <option value="211600">필리핀</option>

                        <option value="251700">핏케언제도</option>

                        <option value="241900">헝가리</option>

                        <option value="250500">호주</option>
                      </select>
                    </div>
                    <div class="TypoBox">
                      <input type="text" id="overseaDetails" class="Typo SizeL address02" aria-label="상세주소" placeholder="해외 근무지역의 상세주소 입력">
                    </div>
                    <div class="MsgTxt Invalid _invalid_msg" id="overseas_empty_msg" style="display: none;">근무지역을 입력해주세요.</div>
                  </div>

                </div>
              </div>
              <div class="items" id="job_type">
                <div class="area_tit">
                  <em class="required">*</em>
                  <h3 class="tit">근무형태</h3>
                  <div class="TipBox over aiRecruitTooltip" style="display: none">
                    <button type="button"><span class="blind">도움말</span></button>
                    <div class="TipCont TopCenter">
                                    <span class="Tail"><svg class="icon_tail">
                                            <use xlink:href="#TipBox_tail"></use>
                                        </svg></span>
                      <div class="TipTxt">
                        스피드매칭 공고의 근무형태는<br>정규직만 등록 가능합니다.
                      </div>
                    </div>
                  </div>
                </div>
                <div class="area_data">
                  <div class="row">
                    <ul class="job_list_check" id="job_type_list">
                      <li>
                        <div class="InpBox">
                                            <span class="Chk SizeL">
                                                <input type="checkbox" id="job_type_1" value="1" data-help_target="job_type"><label for="job_type_1" class="Lbl">정규직</label>
                                            </span>
                        </div>
                      </li>

                      <li>
                        <div class="InpBox">
                                            <span class="Chk SizeL">
                                                <input type="checkbox" id="job_type_2" value="2" data-help_target="job_type"><label for="job_type_2" class="Lbl">계약직</label>
                                            </span>
                        </div>
                      </li>

                      <li>
                        <div class="InpBox">
                                            <span class="Chk SizeL">
                                                <input type="checkbox" id="job_type_5" value="5" data-help_target="job_type"><label for="job_type_5" class="Lbl">아르바이트</label>
                                            </span>
                        </div>
                      </li>

                      <li>
                        <div class="InpBox">
                                            <span class="Chk SizeL">
                                                <input type="checkbox" id="job_type_4" value="4" data-help_target="job_type"><label for="job_type_4" class="Lbl">인턴직</label>
                                            </span>
                        </div>
                      </li>

                      <li>
                        <div class="InpBox">
                                            <span class="Chk SizeL">
                                                <input type="checkbox" id="job_type_9" value="9" data-help_target="job_type"><label for="job_type_9" class="Lbl">프리랜서</label>
                                            </span>
                        </div>
                      </li>

                      <li>
                        <div class="InpBox">
                                            <span class="Chk SizeL">
                                                <input type="checkbox" id="job_type_14" value="14" data-help_target="job_type"><label for="job_type_14" class="Lbl">파트</label>
                                            </span>
                        </div>
                      </li>

                      <li class="hide">
                        <div class="InpBox">
                                            <span class="Chk SizeL">
                                                <input type="checkbox" id="job_type_8" value="8" data-help_target="job_type"><label for="job_type_8" class="Lbl">위촉직</label>
                                            </span>
                        </div>
                      </li>

                      <li class="hide">
                        <div class="InpBox">
                                            <span class="Chk SizeL">
                                                <input type="checkbox" id="job_type_6" value="6" data-help_target="job_type"><label for="job_type_6" class="Lbl">파견직</label>
                                            </span>
                        </div>
                      </li>

                      <li class="hide">
                        <div class="InpBox">
                                            <span class="Chk SizeL">
                                                <input type="checkbox" id="job_type_15" value="15" data-help_target="job_type"><label for="job_type_15" class="Lbl">전임</label>
                                            </span>
                        </div>
                      </li>

                      <li class="hide">
                        <div class="InpBox">
                                            <span class="Chk SizeL">
                                                <input type="checkbox" id="job_type_3" value="3" data-help_target="job_type"><label for="job_type_3" class="Lbl">병역특례</label>
                                            </span>
                        </div>
                      </li>

                      <li class="hide">
                        <div class="InpBox">
                                            <span class="Chk SizeL">
                                                <input type="checkbox" id="job_type_12" value="12" data-help_target="job_type"><label for="job_type_12" class="Lbl">교육생</label>
                                            </span>
                        </div>
                      </li>

                      <li class="hide">
                        <div class="InpBox">
                                            <span class="Chk SizeL">
                                                <input type="checkbox" id="job_type_7" value="7" data-help_target="job_type"><label for="job_type_7" class="Lbl">해외취업</label>
                                            </span>
                        </div>
                      </li>
                    </ul>
                    <a href="javascript:void(0);" class="all_more">전체보기</a>
                    <div class="optional_item work_type" id="term" style="display: none;">
                      <dl id="probation_term_wrap" style="display: none;">
                        <dt>정규직</dt>
                        <dd>수습기간
                          <div class="TypoBox">
                            <input type="text" class="Typo _filter" id="probation_term" data-filter="numeric" maxlength="2" aria-label="수습기간 입력">
                          </div>
                          개월
                        </dd>
                      </dl>
                      <dl id="contract_term_wrap" style="display: none;">
                        <dt>
                          계약직
                          <div class="TipBox">
                            <button type="button" class="ico_question"><svg>
                              <use xlink:href="#ico_question"></use>
                            </svg><span class="blind">도움말</span></button>
                            <div class="TipCont BottomCenter">
                                                    <span class="Tail"><svg class="icon_tail">
                                                            <use xlink:href="#TipBox_tail"></use>
                                                        </svg></span>
                              <div class="TipTxt">
                                <strong class="tit_tooltip">기간제법 주요내용</strong>
                                <p class="desc_tooltip">
                                  '기간제근로자'란 근로기간이 정해져 있는 근로 계약을 체결한 근로자를 말합니다. <a href="http://www.law.go.kr/LSW/LsiJoLinkP.do?docType=JO&amp;lsNm=%EA%B8%B0%EA%B0%84%EC%A0%9C+%EB%B0%8F+%EB%8B%A8%EC%8B%9C%EA%B0%84%EA%B7%BC%EB%A1%9C%EC%9E%90+%EB%B3%B4%ED%98%B8+%EB%93%B1%EC%97%90+%EA%B4%80%ED%95%9C+%EB%B2%95%EB%A5%A0&amp;joNo=000200000&amp;languageType=KO&amp;paras=1" target="_blank" class="link" title="새창으로 이동">(기간제 및 단시간근로자. 보호 등에 관한
                                  법률)</a><br>
                                  따라서, 계약직, 임시직, 일용직, 촉박직 등 명칭에 관계없이 기간의 정함이 있는 근로계약을
                                  체결한 근로자는 모두 기간제 근로자에 해당합니다.<br>
                                  2년 이상 근로계약시에는 무기근로계약으로 체결해야 합니다.
                                </p>
                              </div>
                            </div>
                          </div>
                        </dt>
                        <dd>
                          계약기간
                          <div class="TypoBox">
                            <input type="text" class="Typo _filter" id="contract_term" data-filter="numeric" maxlength="2" aria-label="계약직 계약기간">
                          </div> 개월
                          <span class="optional_inpchk">
                                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="chk_term_jt_10" value="10"><label class="Lbl" for="chk_term_jt_10">정규직 전환 가능</label></span></div>
                                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="chk_term_jt_16" value="16"><label class="Lbl" for="chk_term_jt_16">기간제</label></span></div>
                                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="chk_term_jt_17" value="17"><label class="Lbl" for="chk_term_jt_17">무기계약직</label></span></div>
                                            </span>
                        </dd>
                      </dl>
                      <dl id="internship_term_wrap" style="display: none;">
                        <dt>
                          인턴직
                          <div class="TipBox">
                            <button type="button" class="ico_question"><svg>
                              <use xlink:href="#ico_question"></use>
                            </svg><span class="blind">도움말</span></button>
                            <div class="TipCont BottomCenter">
                                                    <span class="Tail"><svg class="icon_tail">
                                                            <use xlink:href="#TipBox_tail"></use>
                                                        </svg></span>
                              <div class="TipTxt">
                                <strong class="tit_tooltip">기간제법 주요내용</strong>
                                <p class="desc_tooltip">
                                  '기간제근로자'란 근로기간이 정해져 있는 근로 계약을 체결한 근로자를 말합니다. <a href="http://www.law.go.kr/LSW/LsiJoLinkP.do?docType=JO&amp;lsNm=%EA%B8%B0%EA%B0%84%EC%A0%9C+%EB%B0%8F+%EB%8B%A8%EC%8B%9C%EA%B0%84%EA%B7%BC%EB%A1%9C%EC%9E%90+%EB%B3%B4%ED%98%B8+%EB%93%B1%EC%97%90+%EA%B4%80%ED%95%9C+%EB%B2%95%EB%A5%A0&amp;joNo=000200000&amp;languageType=KO&amp;paras=1" target="_blank" class="link" title="새창으로 이동">(기간제 및 단시간근로자. 보호 등에 관한
                                  법률)</a><br>
                                  따라서, 계약직, 임시직, 일용직, 촉박직 등 명칭에 관계없이 기간의 정함이 있는 근로계약을
                                  체결한 근로자는 모두 기간제 근로자에 해당합니다.<br>
                                  2년 이상 근로계약시에는 무기근로계약으로 체결해야 합니다.
                                </p>
                              </div>
                            </div>
                          </div>
                        </dt>
                        <dd>
                          계약기간
                          <div class="TypoBox">
                            <input type="text" class="Typo _filter" id="internship_term" data-filter="numeric" maxlength="2" aria-label="인턴직 계약기간">
                          </div>개월
                          <span class="optional_inpchk">
                                                <div class="InpBox">
                                                    <span class="Chk"><input type="checkbox" id="chk_term_jt_11" value="11"><label class="Lbl" for="chk_term_jt_11">정규직 전환 가능</label></span>
                                                </div>
                                            </span>
                        </dd>
                      </dl>
                      <dl id="dispatch_term_wrap" style="display: none;">
                        <dt>
                          파견직
                          <div class="TipBox">
                            <button type="button" class="ico_question"><svg>
                              <use xlink:href="#ico_question"></use>
                            </svg><span class="blind">도움말</span></button>
                            <div class="TipCont BottomCenter">
                                                    <span class="Tail"><svg class="icon_tail">
                                                            <use xlink:href="#TipBox_tail"></use>
                                                        </svg></span>
                              <div class="TipTxt">
                                <strong class="tit_tooltip">파견법 주요내용</strong>
                                <p class="desc_tooltip">
                                  '파견근로자'란 파견사업주가 고용한 근로자로서 근로자파견의 대상이 되는 자를 말합니다.
                                  (「파견근로자보호 등에 관한 법률」 제2조제5호)
                                  근로자 파견의 기간은 <a href="http://www.law.go.kr/LSW/LsiJoLinkP.do?docType=JO&amp;lsNm=%ED%8C%8C%EA%B2%AC%EA%B7%BC%EB%A1%9C%EC%9E%90%EB%B3%B4%ED%98%B8+%EB%93%B1%EC%97%90+%EA%B4%80%ED%95%9C+%EB%B2%95%EB%A5%A0&amp;joNo=000600000&amp;languageType=KO&amp;paras=1" target="_blank" class="link" title="새창으로 이동">제
                                  5조 제2항</a>에 해당하는 경우를 제외하고는 1년을 초과해서는 아니 됩니다.
                                </p>
                              </div>
                            </div>
                          </div>
                        </dt>
                        <dd>
                          계약기간
                          <div class="TypoBox">
                            <input type="text" class="Typo _filter" id="dispatch_term" data-filter="numeric" maxlength="2" aria-label="파견직 계약기간">
                          </div>개월
                          <span class="optional_inpchk">
                                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="chk_term_jt_23" value="23"><label class="Lbl" for="chk_term_jt_23">정규직 전환 가능</label></span></div>
                                            </span>
                        </dd>
                      </dl>
                      <dl id="military_wrap" style="display: none;">
                        <dt>병역특례</dt>
                        <dd>
                          <div class="InpBox"><span class="Chk"><input type="checkbox" id="chk_term_jt_21" value="21"><label class="Lbl" for="chk_term_jt_21">현역</label></span></div>
                          <div class="InpBox"><span class="Chk"><input type="checkbox" id="chk_term_jt_22" value="22"><label class="Lbl" for="chk_term_jt_22">보충역</label></span></div>
                          <div class="InpBox"><span class="Chk"><input type="checkbox" id="chk_term_jt_19" value="19"><label class="Lbl" for="chk_term_jt_19">전문연구요원</label></span></div>
                          <div class="InpBox"><span class="Chk"><input type="checkbox" id="chk_term_jt_20" value="20"><label class="Lbl" for="chk_term_jt_20">산업기능요원</label></span></div>
                        </dd>
                      </dl>
                    </div>
                    <div id="trainee_warn_msg" class="optional_item work_type_notice" style="display:none">
                      <dl>
                        <dd class="desc"><strong>교육 후 공고등록기업으로 채용이 진행되지 않는 일반 교육생 공고는 등록이 불가하오니
                          고객센터로 문의하시기 바랍니다.</strong></dd>
                      </dl>
                    </div>
                    <em class="MsgTxt Invalid msgarea_type _invalid_msg" style="display:none;">근무형태를
                      선택해주세요.</em>
                  </div>
                  <p class="recruit_notice_txt" style="display: none;">※ 다수의 근무형태 등록 시 근무형태별 급여 조건을
                    명시하지 않는 경우, 공고 강제 마감 및 공고 등록 제한이 될 수 있습니다.</p>
                </div>

              </div>
              <div class="items" id="salary">
                <div class="area_tit">
                  <em class="required">*</em>
                  <h3 class="tit">급여</h3>
                </div>
                <div class="area_data">
                  <div class="condition_salary">
                    <div class="InpBox SizeL">
                      <select id="salary_category" title="급여">
                        <option value="100">연봉</option>

                        <option value="101">월급</option>

                        <option value="102">주급</option>

                        <option value="103">일급</option>

                        <option value="104">시급</option>

                        <option value="105">건당</option>

                        <option value="99">면접 후 결정</option>
                      </select>
                    </div>
                    <div class="InpBox SizeL" id="select_salary_cd" style="display: none;">
                      <select id="salary_cd" title="급여 상세">
                        <option value="12">3,000~3,200만원</option>

                        <option value="13">3,200~3,400만원</option>

                        <option value="14">3,400~3,600만원</option>

                        <option value="15">3,600~3,800만원</option>

                        <option value="16">3,800~4,000만원</option>

                        <option value="17">4,000~5,000만원</option>

                        <option value="18">5,000~6,000만원</option>

                        <option value="19">6,000~7,000만원</option>

                        <option value="20">7,000~8,000만원</option>

                        <option value="21">8,000~9,000만원</option>

                        <option value="22">9,000~1억원</option>

                        <option value="23">1억원 이상</option>

                        <option value="99">면접 후 결정</option>

                        <option value="0">회사내규에 따름</option>
                      </select>
                    </div>
                    <span class="salary_directly" id="input_salary">
                                    <div class="TypoBox">
                                        <input type="text" id="salary_ipt" name="salary" class="Typo SizeL _filter" data-filter="numeric" maxlength="6" aria-label="급여 직접 입력">
                                    </div>
                                    <span class="txt" id="salary_unit">만원</span>
                                </span>
                    <span class="salary_etc" style="display: none;">
                                    <div class="TypoBox">
                                        <input type="text" id="remuneration" name="remuneration" class="Typo sizeL" aria-label="(선택) 기타 급여사항 입력" placeholder="(선택) 기타 급여사항" maxlength="50">
                                    </div>
                                </span>
                    <div class="MsgTxt Invalid _invalid_msg" style="display:none;"></div>
                    <div class="noti_salary">
                      <strong id="salary_msg">주 40시간 기준 최저연봉 <em class="highlight">약
                        24,728,880원</em> (2024년 최저시급 9,860원)</strong>
                    </div>
                    <div class="optional_item work_time">
                      <dl id="work_hour">
                        <dt><em class="required">*</em>근무시간</dt>
                        <dd>
                          <span class="unit">주</span>
                          <div class="TypoBox">
                            <input type="text" class="Typo _filter" id="work_hour_value" data-filter="numeric" maxlength="3" aria-label="소정 근로시간">
                          </div>시간
                          <div class="InpBox">
                            <span class="Chk"><input type="checkbox" id="decide_after_interview"><label class="Lbl" for="decide_after_interview">면접 후 결정</label></span>
                          </div>
                        </dd>
                      </dl>
                    </div>
                    <p class="noti_salary">
                      2024년 최저임금에 부합한 급여를 입력해주세요. 위 최저임금은 기본 소정 근로 시간 기준의 급여이므로, 그 외 수당 발생시 추가
                      지급되어야 합니다.
                      당사는 본 채용건과 관련하여 '최저임금법'을 준수합니다. 최저임금을 준수하지 않는 경우, 공고 강제 마감 및 행정처분을 받을 수
                      있습니다. <a href="http://www.minimumwage.go.kr/index.jsp" target="_blank" class="link_info" title="새창으로 이동">최저임금제도 안내</a>
                    </p>
                  </div>
                </div>
              </div>
              <div class="items">
                <div class="area_tit">
                  <h3 class="tit">항목추가</h3>
                </div>
                <div class="area_data">
                  <ul class="list_checkbox">
                    <li>
                      <input type="checkbox" class="location_chk" id="show_btn_location"><label class="lbl ga_data_layer" for="show_btn_location" data-ga_data_layer="">인근지역</label>
                    </li>
                    <li>
                      <input type="checkbox" class="work_week_chk" id="show_btn_work_week"><label class="lbl ga_data_layer" for="show_btn_work_week" data-ga_data_layer="">근무요일</label>
                    </li>
                    <li>
                      <input type="checkbox" class="work_time_chk" id="show_btn_work_time"><label class="lbl ga_data_layer" for="show_btn_work_time" data-ga_data_layer="">출퇴근 시간</label>
                    </li>
                  </ul>
                </div>
              </div>
              <div class="items option" id="locationArea" style="">
                <div class="area_tit">
                  <h3 class="tit">인근지역</h3>
                </div>
                <div class="area_data add_list">
                  <div class="data_location _location_group" data-seq="1">
                    <div class="InpBox SizeL">
                      <select class="_sel_one_depth" title="지역" disabled="">
                        <option value="">선택</option>
                        <option value="101000">서울</option>
                        <option value="102000">경기</option>
                        <option value="103000">광주</option>
                        <option value="104000">대구</option>
                        <option value="105000">대전</option>
                        <option value="106000">부산</option>
                        <option value="107000">울산</option>
                        <option value="108000">인천</option>
                        <option value="109000">강원</option>
                        <option value="110000">경남</option>
                        <option value="111000">경북</option>
                        <option value="112000">전남</option>
                        <option value="113000">전북</option>
                        <option value="114000">충북</option>
                        <option value="115000">충남</option>
                        <option value="116000">제주</option>
                        <option value="118000">세종</option>
                        <option value="117000">전국</option>
                        <option value="210000">아시아·중동</option>
                        <option value="211200">일본</option>
                        <option value="211300">중국.홍콩</option>
                        <option value="220000">북·중미</option>
                        <option value="220200">미국</option>
                        <option value="230000">남미</option>
                        <option value="240000">유럽</option>
                        <option value="250000">오세아니아</option>
                        <option value="260000">아프리카</option>
                        <option value="270000">남극대륙</option>
                        <option value="280000">기타해외</option>
                      </select>
                    </div>
                    <div class="selected_item _inputWrapper selected">
                      <p class="txt_placeholder">인근지역</p>
                      <ul class="list_selected_item _location_summary">
                        <li>동작구<button type="button" class="btn_del loc_del" id="loc_delete_101120" data-mcode="101000" value="101120" style="display: none;">삭제</button></li>
                      </ul>
                      <button type="button" class="btn_add" data-target="layer_add_location"><span class="blind">인근지역 레이어 펼침</span></button>
                      <div class="wrap_layer" aria-haspop="true">
                        <div class="layer_frm layer_add_location layer_add_location2">
                          <div class="location_scroll ScrollBar">
                            <ul class="list_location select_list" style="display:none;">
                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101010" data-mcode="101000" value="101010"><label class="Lbl" for="loc_chk_101010">강남구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101020" data-mcode="101000" value="101020"><label class="Lbl" for="loc_chk_101020">강동구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101030" data-mcode="101000" value="101030"><label class="Lbl" for="loc_chk_101030">강북구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101040" data-mcode="101000" value="101040"><label class="Lbl" for="loc_chk_101040">강서구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101050" data-mcode="101000" value="101050"><label class="Lbl" for="loc_chk_101050">관악구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101060" data-mcode="101000" value="101060"><label class="Lbl" for="loc_chk_101060">광진구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101070" data-mcode="101000" value="101070"><label class="Lbl" for="loc_chk_101070">구로구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101080" data-mcode="101000" value="101080"><label class="Lbl" for="loc_chk_101080">금천구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101090" data-mcode="101000" value="101090"><label class="Lbl" for="loc_chk_101090">노원구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101100" data-mcode="101000" value="101100"><label class="Lbl" for="loc_chk_101100">도봉구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101110" data-mcode="101000" value="101110"><label class="Lbl" for="loc_chk_101110">동대문구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101120" data-mcode="101000" value="101120" checked="" disabled=""><label class="Lbl" for="loc_chk_101120">동작구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101130" data-mcode="101000" value="101130"><label class="Lbl" for="loc_chk_101130">마포구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101140" data-mcode="101000" value="101140"><label class="Lbl" for="loc_chk_101140">서대문구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101150" data-mcode="101000" value="101150"><label class="Lbl" for="loc_chk_101150">서초구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101160" data-mcode="101000" value="101160"><label class="Lbl" for="loc_chk_101160">성동구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101170" data-mcode="101000" value="101170"><label class="Lbl" for="loc_chk_101170">성북구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101180" data-mcode="101000" value="101180"><label class="Lbl" for="loc_chk_101180">송파구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101190" data-mcode="101000" value="101190"><label class="Lbl" for="loc_chk_101190">양천구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101200" data-mcode="101000" value="101200"><label class="Lbl" for="loc_chk_101200">영등포구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101210" data-mcode="101000" value="101210"><label class="Lbl" for="loc_chk_101210">용산구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101220" data-mcode="101000" value="101220"><label class="Lbl" for="loc_chk_101220">은평구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101230" data-mcode="101000" value="101230"><label class="Lbl" for="loc_chk_101230">종로구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101240" data-mcode="101000" value="101240"><label class="Lbl" for="loc_chk_101240">중구</label></span></div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101250" data-mcode="101000" value="101250"><label class="Lbl" for="loc_chk_101250">중랑구</label></span>
                                </div>
                              </li>


                              <li>
                                <div class="InpBox"><span class="Chk"><input type="checkbox" id="loc_chk_101000" data-mcode="101000" value="101000"><label class="Lbl" for="loc_chk_101000">서울전체</label></span>
                                </div>
                              </li>
                            </ul>
                          </div>
                          <div class="noti_location">인근지역을 '서울' 내에서 선택해주세요. (최대 5개)</div>
                        </div>
                      </div>
                    </div>
                    <button type="button" class="BtnType SizeL GrayInvert btn_list_add">
                      <span class="blind">인근지역 리스트 추가</span>
                      <svg class="BtnIcon add">
                        <use xlink:href="#ico_btn_plus"></use>
                      </svg>
                      <svg class="BtnIcon delete">
                        <use xlink:href="#ico_btn_minus"></use>
                      </svg>
                    </button>
                  </div>
                  <p class="msgarea _invalid_msg" style="display:none;"><span class="msg_err">공고 노출
                                    지역을 선택해주세요.</span></p>
                  <p class="msgarea" id="locResetMsg" style="display:none;"><span class="msg_err">근무지역
                                    반영을 위하여 공고 노출 지역을 초기화하였습니다.</span></p>
                </div>
              </div>
              <div class="items option" id="workWeekArea" style="">
                <div class="area_tit">
                  <strong class="tit">근무요일</strong>
                </div>
                <div class="area_data">
                  <div class="optional_item condition_detail">
                    <div id="work_shift_part">
                      <!--<span class="inpChk ga_data_layer" data-ga_data_layer="ga_lead|pc_rec_condition|btn_week"><input type="checkbox" name="use_work_week" id="use_work_week"><label for="use_work_week" class="lbl">근무요일</label></span>-->
                      <ul class="work_day_radio" id="work_shift_cd">
                        <li>
                          <div class="InpBox">
                                                <span class="Rdo">
                                                    <input type="radio" id="work_shift_cd_wsh010" name="work_shift_cd" value="wsh010" disabled=""><label class="Lbl" for="work_shift_cd_wsh010">주 5일(월~금)</label>
                                                </span>
                          </div>
                        </li>

                        <li>
                          <div class="InpBox">
                                                <span class="Rdo">
                                                    <input type="radio" id="work_shift_cd_wsh030" name="work_shift_cd" value="wsh030" disabled=""><label class="Lbl" for="work_shift_cd_wsh030">주 6일(월~토)</label>
                                                </span>
                          </div>
                        </li>

                        <li>
                          <div class="InpBox">
                                                <span class="Rdo">
                                                    <input type="radio" id="work_shift_cd_wsh040" name="work_shift_cd" value="wsh040" disabled=""><label class="Lbl" for="work_shift_cd_wsh040">주 3일(격일제)</label>
                                                </span>
                          </div>
                        </li>

                        <li>
                          <div class="InpBox">
                                                <span class="Rdo">
                                                    <input type="radio" id="work_shift_cd_wsh050" name="work_shift_cd" value="wsh050" disabled=""><label class="Lbl" for="work_shift_cd_wsh050">유연근무제</label>
                                                </span>
                          </div>
                        </li>

                        <li>
                          <div class="InpBox">
                                                <span class="Rdo">
                                                    <input type="radio" id="work_shift_cd_wsh090" name="work_shift_cd" value="wsh090" disabled=""><label class="Lbl" for="work_shift_cd_wsh090">면접 후 결정</label>
                                                </span>
                          </div>
                        </li>
                      </ul>
                    </div>
                  </div>
                </div>
              </div>
              <div class="items option" id="workTimeArea" style="">
                <div class="area_tit">
                  <strong class="tit">출퇴근 시간</strong>
                </div>
                <div class="area_data">
                  <div class="optional_item condition_detail">
                    <div class="commute_txt" id="work_shift_time_part">
                      <!--<span class="inpChk ga_data_layer" data-ga_data_layer="ga_lead|pc_rec_condition|btn_time">
      <input type="checkbox" name="use_work_time" id="use_work_time"><label for="use_work_time" class="lbl">출퇴근 시간</label>
  </span>-->
                      <div class="work_time_check">
                        <div class="InpBox SizeL">
                          <select id="work_shift_time_start" name="work_shift_time_start" data-help_target="work_shift_start" title="근무 시작 시간" disabled="">
                            <option value="">출근시간</option>

                            <option value="00:00">00:00</option>

                            <option value="00:30">00:30</option>

                            <option value="01:00">01:00</option>

                            <option value="01:30">01:30</option>

                            <option value="02:00">02:00</option>

                            <option value="02:30">02:30</option>

                            <option value="03:00">03:00</option>

                            <option value="03:30">03:30</option>

                            <option value="04:00">04:00</option>

                            <option value="04:30">04:30</option>

                            <option value="05:00">05:00</option>

                            <option value="05:30">05:30</option>

                            <option value="06:00">06:00</option>

                            <option value="06:30">06:30</option>

                            <option value="07:00">07:00</option>

                            <option value="07:30">07:30</option>

                            <option value="08:00">08:00</option>

                            <option value="08:30">08:30</option>

                            <option value="09:00">09:00</option>

                            <option value="09:30">09:30</option>

                            <option value="10:00">10:00</option>

                            <option value="10:30">10:30</option>

                            <option value="11:00">11:00</option>

                            <option value="11:30">11:30</option>

                            <option value="12:00">12:00</option>

                            <option value="12:30">12:30</option>

                            <option value="13:00">13:00</option>

                            <option value="13:30">13:30</option>

                            <option value="14:00">14:00</option>

                            <option value="14:30">14:30</option>

                            <option value="15:00">15:00</option>

                            <option value="15:30">15:30</option>

                            <option value="16:00">16:00</option>

                            <option value="16:30">16:30</option>

                            <option value="17:00">17:00</option>

                            <option value="17:30">17:30</option>

                            <option value="18:00">18:00</option>

                            <option value="18:30">18:30</option>

                            <option value="19:00">19:00</option>

                            <option value="19:30">19:30</option>

                            <option value="20:00">20:00</option>

                            <option value="20:30">20:30</option>

                            <option value="21:00">21:00</option>

                            <option value="21:30">21:30</option>

                            <option value="22:00">22:00</option>

                            <option value="22:30">22:30</option>

                            <option value="23:00">23:00</option>

                            <option value="23:30">23:30</option>
                          </select>
                        </div>
                        <div class="InpBox SizeL">
                          <select id="work_shift_time_end" name="work_shift_time_end" data-help_target="work_shift_end" title="근무 시작 시간" disabled="">
                            <option value="">퇴근시간</option>

                            <option value="00:00">00:00</option>

                            <option value="00:30">00:30</option>

                            <option value="01:00">01:00</option>

                            <option value="01:30">01:30</option>

                            <option value="02:00">02:00</option>

                            <option value="02:30">02:30</option>

                            <option value="03:00">03:00</option>

                            <option value="03:30">03:30</option>

                            <option value="04:00">04:00</option>

                            <option value="04:30">04:30</option>

                            <option value="05:00">05:00</option>

                            <option value="05:30">05:30</option>

                            <option value="06:00">06:00</option>

                            <option value="06:30">06:30</option>

                            <option value="07:00">07:00</option>

                            <option value="07:30">07:30</option>

                            <option value="08:00">08:00</option>

                            <option value="08:30">08:30</option>

                            <option value="09:00">09:00</option>

                            <option value="09:30">09:30</option>

                            <option value="10:00">10:00</option>

                            <option value="10:30">10:30</option>

                            <option value="11:00">11:00</option>

                            <option value="11:30">11:30</option>

                            <option value="12:00">12:00</option>

                            <option value="12:30">12:30</option>

                            <option value="13:00">13:00</option>

                            <option value="13:30">13:30</option>

                            <option value="14:00">14:00</option>

                            <option value="14:30">14:30</option>

                            <option value="15:00">15:00</option>

                            <option value="15:30">15:30</option>

                            <option value="16:00">16:00</option>

                            <option value="16:30">16:30</option>

                            <option value="17:00">17:00</option>

                            <option value="17:30">17:30</option>

                            <option value="18:00">18:00</option>

                            <option value="18:30">18:30</option>

                            <option value="19:00">19:00</option>

                            <option value="19:30">19:30</option>

                            <option value="20:00">20:00</option>

                            <option value="20:30">20:30</option>

                            <option value="21:00">21:00</option>

                            <option value="21:30">21:30</option>

                            <option value="22:00">22:00</option>

                            <option value="22:30">22:30</option>

                            <option value="23:00">23:00</option>

                            <option value="23:30">23:30</option>
                          </select>
                        </div>
                        <input type="text" id="work_shift_time" name="work_shift_time" maxlength="30" class="inpTypo sizeL" title="근무시간 직접입력" placeholder="예) 1주일 순환근무, 교대 근무" style="display: none;">
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </section>
          <!-- 채용절차 -->
          <section id="frmHiring" class="step_recruit">
            <div class="frm_header">
              <h2 class="tit_step">채용절차</h2>
            </div>
            <div class="frm_body frm_hiring">
              <div class="items" id="applyPeriod">
                <div class="area_tit">
                  <em class="required">*</em>
                  <h3 class="tit">지원 접수 기간</h3>
                </div>
                <div class="area_data">
                  <div class="row">
                    <div class="btnGroup apply_period">
                      <span class="inBtn on"><button type="button" class="">1개월</button></span>
                      <!-- [dev] 클릭 시 클래스 on 추가 -->
                      <span class="inBtn"><button type="button" class="">2개월</button></span>
                    </div>
                    <!-- 툴팁 -->
                    <div class="TipBox">
                      <span class="blind">도움말</span>
                      <div class="TipCont TopLeft">
                                        <span class="Tail"><svg class="icon_tail">
                                                <use xlink:href="#TipBox_tail_side"></use>
                                            </svg></span>
                        <div class="TipTxt">
                          <p class="txt">
                            블라인드, 자사양식 공고는 MUST 서비스 이용만료일까지 공고진행이 가능합니다.</p>
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="row apply_datetime">
                    <div class="TypoBox">
                      <input type="text" id="start_date" class="Typo DatePic SizeL type_calendar" aria-label="접수 시작일" style="cursor: pointer;" readonly="" autocomplete="off">
                    </div>
                    <div class="InpBox SizeL">
                      <select id="start_hour" title="접수 시작 시간">
                        <option value="0">0시</option>
                        <option value="1">1시</option>
                        <option value="2">2시</option>
                        <option value="3">3시</option>
                        <option value="4">4시</option>
                        <option value="5">5시</option>
                        <option value="6">6시</option>
                        <option value="7">7시</option>
                        <option value="8">8시</option>
                        <option value="9">9시</option>
                        <option value="10">10시</option>
                        <option value="11">11시</option>
                        <option value="12">12시</option>
                        <option value="13">13시</option>
                        <option value="14">14시</option>
                        <option value="15">15시</option>
                        <option value="16">16시</option>
                        <option value="17">17시</option>
                        <option value="18">18시</option>
                        <option value="19">19시</option>
                        <option value="20">20시</option>
                        <option value="21">21시</option>
                        <option value="22">22시</option>
                        <option value="23">23시</option>
                      </select>
                    </div>
                    ~
                    <div class="TypoBox">
                      <input type="text" id="end_date" class="Typo DatePic SizeL type_calendar" aria-label="접수 마감일" style="cursor: pointer;" readonly="" autocomplete="off">
                    </div>
                    <div class="InpBox SizeL">
                      <select id="end_hour" title="접수 마감 시간">
                        <option value="1">1시</option>
                        <option value="2">2시</option>
                        <option value="3">3시</option>
                        <option value="4">4시</option>
                        <option value="5">5시</option>
                        <option value="6">6시</option>
                        <option value="7">7시</option>
                        <option value="8">8시</option>
                        <option value="9">9시</option>
                        <option value="10">10시</option>
                        <option value="11">11시</option>
                        <option value="12">12시</option>
                        <option value="13">13시</option>
                        <option value="14">14시</option>
                        <option value="15">15시</option>
                        <option value="16">16시</option>
                        <option value="17">17시</option>
                        <option value="18">18시</option>
                        <option value="19">19시</option>
                        <option value="20">20시</option>
                        <option value="21">21시</option>
                        <option value="22">22시</option>
                        <option value="23">23시</option>
                        <option value="24">24시</option>
                      </select>
                    </div>
                    <div class="InpBox SizeL type_close">
                      <select id="close_type" title="마감일 지정">
                        <option value="0">마감일지정</option>
                        <option value="1">채용시 마감</option>
                        <option value="2">상시채용</option>
                      </select>
                    </div>
                  </div>
                  <em class="MsgTxt Invalid" style="display:none;">공고 마감일을 시작일 보다 미래로 지정해주세요.</em>
                  <div id="trainee_warn_msg" class="optional_item" style="block">
                    <p class="MsgTxt">등록/수정하신 공고는 [사전 확인 서비스] 가 적용됩니다.</p>
                    <a href="javascript:void(0);" class="link noticeInspection">공고 사전 확인 서비스
                      &gt;</a>
                  </div>


                </div>
              </div>


              <div class="items" id="hiringProcedure">
                <div class="area_tit">
                  <h3 class="tit">전형절차</h3>
                </div>
                <div class="area_data">
                  <div class="selected_item">
                    <div class="selected_item_box">
                      <ul class="list_selected_item process">
                        <li class="upper">서류전형</li>
                        <li>1차면접</li>
                        <li class="upper">최종합격</li>
                      </ul>
                    </div>
                    <button type="button" class="BtnType SizeL BlueInvert btn_add" data-target="layer_add_job">변경</button>
                  </div>
                </div>
              </div>
              <div class="items doc_additional_all">
                <div class="area_tit">
                  <h3 class="tit">항목추가</h3>
                </div>
                <div class="area_data">
                  <ul class="list_checkbox">
                    <li>
                      <input type="checkbox" id="show_btn_reference"><label class="lbl ga_data_layer" for="show_btn_reference">제출서류</label>
                    </li>
                  </ul>
                  <ul class="doc_check required_document_chk" id="requiredDocuments" style="display:">
                    <li>
                      <div class="InpBox">
                                        <span class="Chk SizeL">
                                            <input type="checkbox" id="file_svq" data-target="file_svq" data-event_flow="btn_test"><label for="file_svq" class="Lbl">사람인
                                                인적성검사</label>
                                        </span>
                      </div>
                    </li>
                    <li>
                      <div class="InpBox">
                                        <span class="Chk SizeL">
                                            <input type="checkbox" id="file_interview" data-target="file_interview" data-event_flow="btn_interview"><label for="file_interview" class="Lbl">사전인터뷰</label>
                                        </span>
                      </div>
                    </li>
                    <li>
                      <div class="InpBox">
                                        <span class="Chk SizeL">
                                            <input type="checkbox" id="file_reference" data-target="file_reference" data-event_flow="btn_reference"><label for="file_reference" class="Lbl">참고자료</label>
                                        </span>
                      </div>
                    </li>
                    <li>
                      <div class="InpBox">
                                        <span class="Chk SizeL">
                                            <input type="checkbox" id="file_direct" data-target="file_direct" data-event_flow="btn_input"><label for="file_direct" class="Lbl">직접입력</label>
                                        </span>
                      </div>
                    </li>
                  </ul>
                  <div class="optional_item required_document_opt" style="display: none;">
                    <dl class="file_svq" style="display: none;">
                      <dt>사람인 인·적성</dt>
                      <dd>
                        <p class="description">사람인 인·적성 검사 결과가 첨부된 이력서를 선호합니다.</p>
                        <a href="https://www.saraminimage.co.kr/helpdesk/info_sri.pdf" target="_blank" class="link_svq" title="새창으로 이동">자세히 보기</a>
                      </dd>
                    </dl>
                    <dl class="file_interview" style="display: none;">
                      <dt>사전인터뷰</dt>
                      <dd>
                        <div class="interview_txt">미등록</div>
                        <button type="button" class="BtnType SizeS BlueInvert btn_add">등록</button>
                        <!-- 등록 후 버튼 명 변경으로 체인지 -->
                      </dd>
                    </dl>
                    <dl class="file_reference" id="reference" style="display: none;">
                      <dt>참고자료</dt>
                      <dd>
                        <!-- 참고자료 미등록 시 -->
                        <div class="reference_txt" style="display: block;">미등록</div>
                        <div class="reference_box selected_item_box" style="display: none;">
                          <ul class="reference_list"></ul>
                        </div>
                        <button type="button" class="BtnType SizeS BlueInvert btn_add">등록</button>
                      </dd>
                    </dl>
                    <dl class="file_direct" style="display: none;">
                      <dt>직접입력</dt>
                      <dd>
                        <div class="required_documents_txt">미등록</div>
                        <button type="button" class="BtnType SizeS BlueInvert btn_add">등록</button>
                        <!-- 등록 후 버튼 명 변경으로 체인지 -->
                      </dd>
                    </dl>

                  </div>
                </div>
              </div>
            </div>
          </section>
          <!-- 담당자 정보 -->
          <section id="frmManagerCompany" class="step_recruit">
            <div id="manager">
              <div class="frm_header">
                <h2 class="tit_step">인사담당자/기업정보</h2>
              </div>
              <div class="frm_body frm_manager">
                <div class="items" id="manager_nm_area">
                  <div class="area_tit">
                    <em class="required">*</em>
                    <h3 class="tit">담당자명</h3>
                  </div>
                  <div class="area_data">
                                <span class="manager_name">
                                    <div class="TypoBox">
                                        <input type="text" id="manager_nm" name="manager_nm" class="Typo SizeL " aria-label="담당자명 입력" placeholder="담당자명 입력" maxlength="30">
                                    </div>
                                </span>
                    <span class="check_option">
                                    <div class="InpBox">
                                        <span class="Chk">
                                            <input type="checkbox" class="manager_open_fl" id="manager_open" name="manager_open" value="manager_open">
                                            <label class="Lbl" for="manager_open">비공개</label>
                                        </span>
                                    </div>
                                </span>
                    <div class="MsgTxt Invalid _invalid_msg_manager" style="display:none;">담당자명을
                      입력해주세요.</div>
                  </div>
                </div>
                <div class="items" id="manager_tel_area">
                  <div class="area_tit">
                    <em class="required">*</em>
                    <h3 class="tit">연락처</h3>
                  </div>
                  <div class="area_data">
                                <span class="manager_phone">
                                    <div class="TypoBox">
                                        <input type="text" id="tel1" name="tel1" class="Typo SizeL _filter" aria-label="전화번호 첫번째 자리" maxlength="3" data-filter="numeric">
                                    </div>
                                </span>
                    <span class="manager_phone">
                                    <div class="TypoBox">
                                        <input type="text" id="tel2" name="tel2" class="Typo SizeL _filter" aria-label="전화번호 두번째 자리" maxlength="4" data-filter="numeric">
                                    </div>
                                </span>
                    <span class="manager_phone">
                                    <div class="TypoBox">
                                        <input type="text" id="tel3" name="tel3" class="Typo SizeL _filter" aria-label="전화번호 세번째 자리" maxlength="4" data-filter="numeric">
                                    </div>
                                </span>
                    <span class="check_option">
                                    <div class="InpBox">
                                        <span class="Chk">
                                            <input type="checkbox" class="manager_open_fl" id="tel_open" value="tel_open">
                                            <label class="Lbl" for="tel_open">비공개</label>
                                        </span>
                                    </div>
                                </span>
                    <div class="MsgTxt Invalid _invalid_msg_tel" style="display:none;"></div>
                  </div>
                </div>
                <div class="items" id="manager_email_area">
                  <div class="area_tit">
                    <em class="required">*</em>
                    <h3 class="tit">이메일</h3>
                  </div>
                  <div class="area_data">
                    <div class="manager_email">
                      <div class="TypoBox">
                        <input type="text" id="email" name="email" class="Typo SizeL " aria-label="이메일 주소 입력" placeholder="이메일 주소 입력" autocomplete="off">
                      </div>
                      <div class="wrap_layer">
                        <div class="layer_auto_email">
                          <ul class="list_email" id="list_email_auto_complete"></ul>
                        </div>
                      </div>
                    </div>
                    <span class="check_option">
                                    <div class="InpBox">
                                        <span class="Chk">
                                            <input type="checkbox" class="manager_open_fl" id="email_open" value="email_open">
                                            <label class="Lbl" for="email_open">비공개</label>
                                        </span>
                                    </div>
                                </span>
                    <div class="MsgTxt Invalid msgarea_manager _invalid_msg_email" style="display:none;"></div>
                    <div class="MsgTxt txt_noti_email" style="display: none;">
                      ※ 이메일을 공개하실 경우, 이메일을 통한 허위 입사지원의 사례가 발생할 수 있습니다.
                    </div>
                  </div>
                </div>
                <div class="items" id="recIndustry">
                  <div class="area_tit">
                    <em class="required">*</em>
                    <h3 class="tit">업종</h3>
                  </div>
                  <div class="area_data" id="industry">
                    <div class="selected_item selected" id="industry_summary">
                      <div class="selected_item_box">
                        <p class="txt_placeholder" style="display: none;">업종 선택</p>
                        <ul class="list_selected_item frm_box_list" data-help_target="industry">
                          <li class="upper ">
                            교육업<button type="button" class="btn_del _del" value="6">삭제
                            교육업</button>
                          </li>

                          <li>IT학원<button type="button" class="btn_del _del" value="60201">삭제
                            IT학원</button></li>
                        </ul>
                      </div>
                      <button type="button" class="BtnType SizeL BlueInvert btn_add btn_more_ly ga_data_layer" data-ga_data_layer="ga_lead|pc_rec_business|btn_business" id="industry_setting_btn"><svg class="BtnIcon">
                        <use xlink:href="#ico_btn_plus"></use>
                      </svg>선택</button>
                    </div>
                    <div class="MsgTxt Invalid _invalid_msg" style="display: none;">업종을 선택해주세요.
                    </div>
                  </div>
                </div>
                <div class="items">
                  <div class="area_tit">
                    <h3 class="tit">항목추가</h3>
                  </div>
                  <div class="area_data">
                    <ul class="list_checkbox">
                      <li>
                        <input type="checkbox" id="cell_show_btn"><label class="lbl ga_data_layer" for="cell_show_btn">휴대폰 번호</label>
                      </li>
                      <li>
                        <input type="checkbox" id="welfare_show_btn"><label class="lbl ga_data_layer" for="welfare_show_btn">복리후생</label>
                      </li>
                      <li>
                        <input type="checkbox" id="manager_dept_show_btn"><label class="lbl ga_data_layer" for="manager_dept_show_btn">담당자부서명</label>
                      </li>
                      <li>
                        <input type="checkbox" id="insatong_show_btn"><label class="lbl ga_data_layer" for="insatong_show_btn">인담자 톡</label>
                      </li>
                    </ul>
                  </div>
                </div>
                <div class="items option" id="cellArea" style="display: block;">
                  <div class="area_tit">
                    <strong class="tit">휴대폰 번호</strong>
                  </div>
                  <div class="area_data">
                                <span class="manager_phone">
                                    <div class="TypoBox">
                                        <input type="text" id="cell1" name="cell1" class="Typo SizeL _filter" aria-label="휴대폰 번호 첫번째 자리" maxlength="3" data-filter="numeric">
                                    </div>
                                </span>
                    <span class="manager_phone ga_data_layer" data-ga_data_layer="ga_lead|pc_rec_business|btn_phone">
                                    <div class="TypoBox">
                                        <input type="text" id="cell2" name="cell2" class="Typo SizeL _filter" aria-label="휴대폰 번호 두번째 자리" maxlength="4" data-filter="numeric">
                                    </div>
                                </span>
                    <span class="manager_phone">
                                    <div class="TypoBox">
                                        <input type="text" id="cell3" name="cell3" class="Typo SizeL _filter" aria-label="휴대폰 번호 세번째 자리" maxlength="4" data-filter="numeric">
                                    </div>
                                </span>
                    <span class="check_option">
                                    <div class="InpBox">
                                        <span class="Chk">
                                            <input type="checkbox" class="manager_open_fl" id="cell_open" value="cell_open">
                                            <label class="Lbl" for="cell_open">비공개</label>
                                        </span>
                                    </div>
                                </span>
                    <em class="MsgTxt Invalid _invalid_msg_cell" style="display:none;">휴대폰번호 형식이
                      올바르지 않습니다.</em>
                  </div>
                </div>
                <div class="items option" id="welfareArea" style="">
                  <div class="area_tit">
                    <strong class="tit">복리후생</strong>
                  </div>
                  <div class="area_data" id="welfare">
                    <div class="selected_item selected _checkItemArea">
                      <div class="selected_item_box">
                        <p class="txt_placeholder" style="display: none;">복리후생 선택</p>
                        <ul class="list_selected_item">
                          <li class="upper" id="welfare_preview_parent_corp006" style="">
                            급여제도<button type="button" class="btn_del _del" value="corp006">삭제 급여제도</button>
                          </li>
                          <li>4대 보험<button type="button" class="btn_del _del" value="corp499">삭제 4대 보험</button></li>

                          <li>연차수당<button type="button" class="btn_del _del" value="corp423">삭제 연차수당</button></li>

                          <li>휴일(특근)수당<button type="button" class="btn_del _del" value="corp422">삭제 휴일(특근)수당</button></li>

                          <li>퇴직금<button type="button" class="btn_del _del" value="corp406">삭제
                            퇴직금</button></li>

                          <li>우수사원포상<button type="button" class="btn_del _del" value="corp404">삭제 우수사원포상</button></li>

                          <li>장기근속자 포상<button type="button" class="btn_del _del" value="corp403">삭제 장기근속자 포상</button></li>

                          <li>인센티브제<button type="button" class="btn_del _del" value="corp401">삭제 인센티브제</button></li>

                          <li>퇴직연금<button type="button" class="btn_del _del" value="corp400">삭제 퇴직연금</button></li>


                          <li class="upper" id="welfare_preview_parent_corp010" style="display: none">
                            조직 문화<button type="button" class="btn_del _del" value="corp010">삭제 조직 문화</button>
                          </li>

                          <li class="upper" id="welfare_preview_parent_corp007" style="display: none">
                            선물<button type="button" class="btn_del _del" value="corp007">삭제
                            선물</button>
                          </li>

                          <li class="upper" id="welfare_preview_parent_corp012" style="display: none">
                            리프레시<button type="button" class="btn_del _del" value="corp012">삭제 리프레시</button>
                          </li>

                          <li class="upper" id="welfare_preview_parent_corp009" style="display: none">
                            근무 환경<button type="button" class="btn_del _del" value="corp009">삭제 근무 환경</button>
                          </li>

                          <li class="upper" id="welfare_preview_parent_corp008" style="">
                            교육/생활<button type="button" class="btn_del _del" value="corp008">삭제 교육/생활</button>
                          </li>
                          <li>사내동호회 운영<button type="button" class="btn_del _del" value="corp418">삭제 사내동호회 운영</button></li>


                          <li class="upper" id="welfare_preview_parent_corp011" style="">
                            출퇴근<button type="button" class="btn_del _del" value="corp011">삭제
                            출퇴근</button>
                          </li>
                          <li>야간교통비지급<button type="button" class="btn_del _del" value="corp476">삭제 야간교통비지급</button></li>


                          <li class="upper" id="welfare_preview_parent_corp005" style="">
                            지원금/보험<button type="button" class="btn_del _del" value="corp005">삭제 지원금/보험</button>
                          </li>
                          <li>본인학자금<button type="button" class="btn_del _del" value="corp486">삭제 본인학자금</button></li>

                          <li>의료비지원(가족)<button type="button" class="btn_del _del" value="corp439">삭제 의료비지원(가족)</button></li>

                          <li>각종 경조사 지원<button type="button" class="btn_del _del" value="corp417">삭제 각종 경조사 지원</button></li>

                          <li>직원대출제도<button type="button" class="btn_del _del" value="corp411">삭제 직원대출제도</button></li>

                          <li>의료비지원(본인)<button type="button" class="btn_del _del" value="corp408">삭제 의료비지원(본인)</button></li>

                          <li>건강검진<button type="button" class="btn_del _del" value="corp407">삭제 건강검진</button></li>

                        </ul>
                      </div>
                      <button type="button" class="BtnType SizeL BlueInvert btn_add btn_more_ly ga_data_layer" data-ga_data_layer="ga_lead|pc_rec_business|btn_welfare" id="welfare_setting_btn" data-layer_type="welfare"><svg class="BtnIcon">
                        <use xlink:href="#ico_btn_plus"></use>
                      </svg> 선택</button>
                    </div>
                  </div>
                </div>
                <div class="items option" id="managerDeptArea" style="display: block;">
                  <div class="area_tit">
                    <strong class="tit">담당자 부서명</strong>
                  </div>
                  <div class="area_data">
                    <div class="TypoBox">
                      <input type="text" id="manager_dept" name="manager_dept" class="Typo SizeL" aria-label="담당자 부서명" placeholder="담당자 부서명 입력" maxlength="30">
                    </div>
                  </div>
                </div>
                <div class="items option" id="insatongArea" style="">
                  <div class="area_tit">
                    <strong class="tit">인담자 톡</strong>
                  </div>
                  <div class="area_data">
                    <div class="selected_item selected">
                      <div class="selected_item_box">
                        <p class="txt_placeholder" style="display: block;">지원자에게 어필하고 싶은 우리회사
                          근무환경, 복지 등 선택</p>
                        <ul class="list_selected_item frm_box_list" style="display: none;"></ul>
                      </div>
                      <button type="button" class="BtnType SizeL BlueInvert btn_add btn_more_ly ga_data_layer" data-ga_data_layer="ga_lead|pc_rec_business|btn_business"><svg class="BtnIcon">
                        <use xlink:href="#ico_btn_plus"></use>
                      </svg> 선택</button>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </section>
          <!-- 템플릿 설정 -->
          <section id="frmTemplate" class="step_recruit">
            <div class="frm_header">
              <h2 class="tit_step blind">템플릿 설정</h2>
            </div>
            <button type="button" class="BtnType SizeML GrayBg btn_template" id="template_insert">템플릿
              반영하기</button>

          </section>
        </div>






      </div>
      <div class="wrap_recruit_frm">
        <div class="wrap_step_recruit">
          <!-- 공고관련안내 -->
          <section id="frmNotice" class="step_recruit title_none">
            <div class="frm_body frm_notice">
              <div class="temp_notice subscribe_info" id="subscriptionFlWrap">
                <div class="temp_notice_tit">인재 추천 및 구독 안내</div>
                <div class="temp_notice_check">
                  <div class="InpBox">
                                <span class="Chk">
                                    <input type="checkbox" id="subscriptionFl">
                                    <label class="Lbl" for="subscriptionFl">
                                        등록 한 공고 기반으로 <span class="highlight">인재풀 포지션 등록</span>과 <span class="highlight">인재 구독 메일</span>이 자동으로 신청됩니다.
                                        <p class="txt_info">
                                            공고 게재 후 채용조건에 딱! 맞는 추천 인재를 확인할 수 있으며, 익일 (영업일 기준)부터 추천인재를 메일로 받으실 수
                                            있습니다.
                                        </p>
                                        <p class="txt_info desc">
                                            (등록된 포지션은 인재풀 검색 조건으로 사용 가능하며, 인재 구독 메일이 5개 이상 신청되어 있는 경우 구독 메일이
                                            신청되지 않을 수 있습니다.)
                                        </p>
                                    </label>
                                </span>
                  </div>
                </div>
              </div>
              <div class="temp_notice">
                <div class="temp_notice_tit">채용 정보 공유 안내</div>
                <div class="temp_notice_check">
                  <div class="InpBox">
                                <span class="Chk">
                                    <input type="checkbox" id="partnership_fl">
                                    <label class="Lbl" for="partnership_fl">
                                        등록한 공고를 사람인의 제휴사이트(워크넷)에 동시 게재합니다. 단, 제휴사이트 규정에 따라 등록이 거절될 수도 있습니다.
                                        <div class="TipBox over">
                                            <button type="button"><span class="blind">도움말</span></button>
                                            <div class="TipCont BottomCenter">
                                                <span class="Tail"><svg class="icon_tail">
                                                        <use xlink:href="#TipBox_tail"></use>
                                                    </svg></span>
                                                <div class="TipTxt">
                                                    ※워크넷 동시 게재는 접수 시작일 <br>기준으로 최대 10일 까지 가능합니다.
                                                </div>
                                            </div>
                                        </div>
                                        <p class="txt_info">
                                            채용정보 정책에 위배될 경우, 공고가 게재되지 않을 수 있습니다.
                                            <button type="button" class="btn_share" data-uilayer-btn="recruit_law">채용절차 준수 '자동통지/파기' 시스템</button>
                                            및
                                            <button type="button" class="btn_share btn_noti_ly" data-uilayer-btn="not_save_caution">공고등록 제한 규정안내</button>
                                        </p>
                                    </label>
                                </span>
                  </div>
                </div>
              </div>
              <!-- smatch-170 -->
              <div class="temp_notice" id="agreeCommissionItems" style="display: none;">
                <div class="temp_notice_tit"><em class="required">*</em>스피드매칭 수수료 동의 안내</div>
                <div class="temp_notice_check">
                  <div class="InpBox">
                                <span class="Chk">
                                    <input type="checkbox" id="checkAgreeCommission">
                                    <label class="Lbl" for="checkAgreeCommission">
                                        동의합니다
                                        <p class="txt_info">
                                            채용 성사 시 합격자 연봉의 7% 수수료 발생(부가세 별도)에 대해 확인하였으며 이에 동의합니다.<br>
                                            단, 수수료율은 약정서를 우선합니다.
                                        </p>
                                    </label>
                                </span>
                    <em class="msgInvalid" style="display: none">수수료 동의에 체크해주세요.</em>
                  </div>
                </div>
              </div>
              <div class="notice_box">
                <span class="notice_tit">잠깐! 공고 등록 전 확인하세요.</span>
                <ul class="notice_list">
                  <li><em class="highlight">최저임금을 준수하지 않는 경우</em>, 공고 강제 마감 및 행정처분을 받을 수 있습니다.</li>
                  <li><em class="highlight">채용절차 공정화 법상 금지되는 개인 정보를 요구하는 경우</em>, 공고 강제 마감 및 행정처분을 받을
                    수 있습니다.</li>
                  <li>모집・채용에서 남녀를 차별하거나, 여성근로자를 채용할 때 직무 수행에 불필요한 조건을 제시 또는 요구하는 경우 <em class="highlight">남녀 고용 평등과 일가정 양립 지원에 관한 법률 위반</em>에 따른 500만원 이하의 벌금 부과 및
                    공고 강제 마감이 될 수 있습니다.</li>
                  <li>모집·채용에서 불합리한 연령 제한 시 <em class="highlight">고용상 연령 차별 금지 및 고령자 고용 촉진에 관한 법률
                    위반</em>에 따른 500만원 이하의 벌금 부과 및 공고 강제 마감이 될 수 있습니다.</li>
                </ul>
              </div>
            </div>
          </section>
          <!-- 등록 완료 버튼 -->
          <div class="wrap_bottom_btn">
            <div class="TipBox">
              <div class="TipCont BottomCenter" style="display:block">
                        <span class="Tail"><svg class="icon_tail">
                                <use xlink:href="#TipBox_tail"></use>
                            </svg></span>
                <div class="TipTxt">
                  공고 등록 시 [사전 확인 서비스]가 제공되며,<br>완료 시 즉시 게재됩니다.<br>
                  <em>(확인 중에는 공고가 노출되지 않습니다.)</em>
                </div>
              </div>
            </div>
            <button type="button" class="BtnType SizeML btn_entry_complete btn_next_step ga_data_layer" data-ga_data_layer="ga_lead|pc_recruit_step|btn_entry_complete">공고 등록하기</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.container {
  margin: 0;
  max-width: none;
}
#content {
  padding: 100px 0;
}
</style>