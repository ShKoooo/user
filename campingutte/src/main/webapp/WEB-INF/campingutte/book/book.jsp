<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
    <head>
    	<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
		<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<script type="text/javascript" src="https://code.jquery.com/ui/1.8.8/i18n/jquery.ui.datepicker-ko.js"></script>
      	<script src="${pageContext.request.contextPath}/resource/js/scripts.js"></script>
      	<!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
      	<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css" type="text/css"/>
    	<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />
		<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
		
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>예약하기</title>
        
        <script type="text/javascript">
        // 약관 아코디언
		$(function(){
			// $("#accordion").accordion();
			
			// 처음에 활성화되지 않고 활성화된 것 누르면 닫히도록
			$("#accordion").accordion({active:false, collapsible:true});
		});
		
        // 수량 계산
		$("body").on("click", ".fas fa-minus", function(){
			var qty = parseInt($(this).parent().children(".quantity")).val();
			var price = parseInt()
		});
</script>
<style type="text/css">
#accordion > ul > li {
	list-style: disc;
}
</style>
    </head>

    <body class="d-flex flex-column">
	
        <main class="flex-shrink-0">
        <!-- header(메뉴바) 부분 -->
		<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
            <!-- Page content-->
            <section class="py-5">
                <div class="container px-5">
                    <!-- Contact form-->
                    <div class="bg-light rounded-3 py-5 px-4 px-md-5 mb-5">
                        <div class="text-center mb-5">
                            <div class="feature bg-primary bg-gradient text-white rounded-3 mb-3"><i class="fas fa-calendar-check"></i></div>
                            <h3 class="fw-bolder">[더시크릿]데일리카라반피크닉WithCampOnStyle</h3>
                            <p class="lead fw-normal text-muted mb-0" style="text-align: left; display: inline;">
                            11/08(월) ~ 11/09(화)&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;</p>
                            &ensp;&ensp;&ensp;&ensp;&ensp;&ensp;&ensp;<p style="text-align: right; display: inline;"><img class="logoicon" src="/campingutte/resource/images/tent_icon_color.png" style="width: 30px; height: 30px;">캠핑가기 1 일 전</p>
                        </div>
                        
                        <hr>
                        
                        <div class="row gx-5 justify-content-center">
                        	<h5> 옵션 선택 </h5>
                        	<div>
                        	<table width="100%">
                        		<tr>
                        			<th width="80%" style="text-align: center;"><strong>옵션</strong></th>
                        			<th width="20%"><strong>수량</strong></th>
                        		</tr>
                        	<tbody>
                        		<tr>
                        			<td>
                        				<span style="font-size: 20px;">장작세트 - </span>
                        				<span class="price" style="font-size: 20px;">19500원</span><br>
                        				<span style="font-size: 1rem; line-height: 0.1em;">바베큐를 위한 장작세트<br>
											장작세트구성 장작1박스+석쇄불판+불피워주기 서비스<br>
											(고기구워먹기 한시간전에 주문요망)</span>
                        			</td>
                        			<td>
                        				<a href=""><i class="fas fa-minus"></i></a>
                        				<span class="quantity">1</span>
                        				<a href=""><i class="fas fa-plus"></i></a>
                        			</td>
                        		</tr>
                        		<tr>
                        			<td>
                        				<span style="font-size: 20px;">장작세트 - </span>
                        				<span class="price" style="font-size: 20px;">19500원</span><br>
                        				<span style="font-size: 1rem; line-height: 0.1em;">바베큐를 위한 장작세트<br>
											장작세트구성 장작1박스+석쇄불판+불피워주기 서비스<br>
											(고기구워먹기 한시간전에 주문요망)</span>
                        			</td>
                        			<td>
                        				<a href=""><i class="fas fa-minus"></i></a>
                        				<span>1</span>
                        				<a href=""><i class="fas fa-plus"></i></a>
                        			</td>
                        		</tr>
                        		<tr>
                        			<td>
                        				<span style="font-size: 20px;">장작세트 - </span>
                        				<span class="price" style="font-size: 20px;">19500원</span><br>
                        				<span style="font-size: 1rem; line-height: 0.1em;">바베큐를 위한 장작세트<br>
											장작세트구성 장작1박스+석쇄불판+불피워주기 서비스<br>
											(고기구워먹기 한시간전에 주문요망)</span>
                        			</td>
                        			<td>
                        				<a href=""><i class="fas fa-minus"></i></a>
                        				<span>1</span>
                        				<a href=""><i class="fas fa-plus"></i></a>
                        			</td>
                        		</tr>
                        	</tbody>
                        	</table>
                        	</div>
                        	<br>
                        	<hr>
                        	<br>
                        	<h5> 예약자 질의응답 </h5>
                            <div class="col-lg-8 col-xl-6" style="width: 1200px;">
                                <!-- * * * * * * * * * * * * * * *-->
                                <!-- * * SB Forms Contact Form * *-->
                                <!-- * * * * * * * * * * * * * * *-->
                                <!-- This form is pre-integrated with SB Forms.-->
                                <!-- To make this form functional, sign up at-->
                                <!-- https://startbootstrap.com/solution/contact-forms-->
                                <!-- to get an API token!-->
                                <form id="contactForm" data-sb-form-api-token="API_TOKEN">
                                    <!-- Name input-->
                                    <p>1. ***취소/환불 규정 및 예약변경 불가 안내*** 1. 이용일(방문일) 8일 이내에 예약 취소 시 (예약 후 바로 취소해도) 취소 수수료 발생합니다.<br> 
                                    2. 우천 및 개인사정으로 날짜변경 불가능하며, 취소 후 재예약해야 합니다. 취소/환불 규정 확인하셨나요? (필수)</p>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="name" type="text" placeholder="" data-sb-validations="required" />
                                        <label for="name">취소/환불 규정 확인했습니다.</label>
                                        <div class="invalid-feedback" data-sb-feedback="name:required">필수 입력 사항입니다.</div>
                                    </div>
                                    <p>2. 본인 포함 총 몇 분이 방문하시나요? (예약금액은 기준인원에 대한 금액입니다) (필수)</p>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="name" type="text" placeholder="" data-sb-validations="required" />
                                        <label for="name">본인 포함 인원수</label>
                                        <div class="invalid-feedback" data-sb-feedback="name:required">필수 입력 사항입니다.</div>
                                    </div>
                                    <p>3. 더시크릿가든 캠프지라운드 취소수수료 기준은 소비자보호원 기준에 따릅니다. 환불기준에 동의하시나요? (필수)</p>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="name" type="text" placeholder="" data-sb-validations="required" />
                                        <label for="name">네 동의합니다.</label>
                                        <div class="invalid-feedback" data-sb-feedback="name:required">필수 입력 사항입니다.</div>
                                    </div>
                                    <p>4. 저녁9시 이후는 매너타임입니다.소곤소곤대화 가능하신가여? (필수)</p>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="name" type="text" placeholder="" data-sb-validations="required" />
                                        <label for="name">넵!!!</label>
                                        <div class="invalid-feedback" data-sb-feedback="name:required">필수 입력 사항입니다.</div>
                                    </div>
                                    <p>5. 등유팬히터(동계용) 사용시 난방비(23,000원유료) 청구됩니다.등유팬히터를 현장에서 사용하시려면 꼭 예약해주세여.등유을 미리 채워드립니다. (선택)</p>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="name" type="text" placeholder="" />
                                        <label for="name">선택 사항</label>
                                        <div class="invalid-feedback">필수 입력 사항입니다.</div>
                                    </div>
                                    <br>
                                    <hr>
                                    <br>
                                    <h5> 예약자 정보 </h5>
                                    <!-- Name input-->
                                    <p>예약자 명 (필수)</p>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="name" type="text" placeholder="" data-sb-validations="required" />
                                        <label for="name">이름을 입력해주세요</label>
                                        <div class="invalid-feedback" data-sb-feedback="name:required">이름을 입력해주세요.</div>
                                    </div>
                                    <!-- Phone number input-->
                                    <p>휴대폰 번호 (필수)</p>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="phone" type="tel" placeholder="(123) 456-7890" data-sb-validations="required" />
                                        <label for="phone">Phone number</label>
                                        <div class="invalid-feedback" data-sb-feedback="phone:required">전화번호를 입력해주세요.</div>
                                    </div>
                                    <!-- Email address input-->
                                    <p>이메일 (선택)</p>
                                    <div class="form-floating mb-3">
                                        <input class="form-control" id="email" type="email" placeholder="name@example.com" />
                                        <label for="email">Email address</label>
                                        <div class="invalid-feedback">선택사항입니다.</div>
                                        <div class="invalid-feedback" data-sb-feedback="email:email">Email is not valid.</div>
                                    </div>
                                    
                                    <!-- Message input-->
                                    <p>예약요청사항 (선택)</p>
                                    <div class="form-floating mb-3">
                                        <textarea class="form-control" id="message" placeholder="Enter your message here..." style="height: 10rem"></textarea>
                                        <label for="message">Message</label>
                                        <div class="invalid-feedback"></div>
                                    </div>
                                    <!-- Submit success message-->
                                    <!---->
                                    <!-- This is what your users will see when the form-->
                                    <!-- has successfully submitted-->
                                    <div class="d-none" id="submitSuccessMessage">
                                        <div class="text-center mb-3">
                                            <div class="fw-bolder">Form submission successful!</div>
                                            To activate this form, sign up at
                                            <br />
                                            <a href="https://startbootstrap.com/solution/contact-forms">https://startbootstrap.com/solution/contact-forms</a>
                                        </div>
                                    </div>
                                    <br>
                                    <hr>
                                    <br>
                                    <h5> 주의사항 및 약관동의 </h5>
                                    
                                    <x><input type="checkbox"> 전체 이용 약관에 동의합니다.(필수)</x>
                                    <div id="accordion">
                                    	<h6><input type="checkbox" data-iconpos="bottom">취소 및 환불 수수료에 동의합니다.</h6>
                                    	<ul>
                                    		<li>예약취소는 구매한 사이트 "MYPAGE" 혹은 "예약확인/취소"에서 가능합니다.</li>
											<li>취소수수료는 예약시점과는 무관하게 '입실일로부터 남은 날짜' 기준으로 부과됩니다. 신중한 예약바랍니다.</li>
											<li>예약 이용일 변경 불가합니다. (취소수수료 확인 후) 기존 예약건 취소 및 재예약하셔야 합니다.</li>
											<li>중복예약 취소, 업체 요청에 의한 취소, 법령에 의한 취소 등은 반드시 캠핑톡 고객센터(070-4336-1824) 또는 해당 숙소를 통하여 도움을 받으십시오.</li>
											<li>미성년자는 예약이 불가하며, 보호자 동반 없이 이용 불가합니다.</li>
											<li style="list-style:none; padding-left:0px;">※ 기상 상황 및 코로나 등 전염병 관련 예약 취소 안내</li>
											<li>우천으로 인한 환불 및 날짜 변경 불가합니다.</li>
											<li>천재지변 또는 기상악화 시, 현장(캠핑장/펜션)상황과 정책에 따라 당일 오전 현장 판단하에 연기 또는 취소 결정됩니다.</li>
											<li>코로나 등 전염병 관련 예약 취소는 병원/행정기관 등 유관기관에서 발급한 '예약자 본인 확인 가능한 증빙 서류 제출 시' 업체와 협의 후 가능합니다.</li>
											<li>사전 협의 없이 예약자가 직접 예약 취소 시 환불 규정에 따라 환불되며, 결제업체(결제수단)의 정책에 따라 취소수수료 환불이 불가할 수 있습니다.</li>
											<li>취소 시 결제금액에서 취소수수료를 제외한 금액이 환불되며, 취소수수료는 총 결제금액 기준으로 책정됩니다.</li>
											<li>취소 신청 후 간편결제 사업자 또는 은행/신용카드사에 따라 환불 절차에 일정 시간이 소요됩니다.</li>
											<li>영업일 기준(토/일/공휴일 제외)으로 실시간 계좌이체 2~3일, 신용카드 3~5일 소요됩니다.</li>
											<li>환불 관련 자세한 문의는 고객센터(070-4336-1824)로 문의하시기 바랍니다.</li>
											<li>캠핑톡(주)는 중개플랫폼사로, 현장에서 발생된 숙박업체와의 분쟁으로 인한 취소 및 환불에 관여하지 않습니다.</li>
											<li>취소수수료는 아래와 같습니다.</li>
										</ul>
									<h6>이용 시 주의사항에 동의합니다.</h6>
										<ul>
											<li style="list-style:none; padding-left:0px;">[알립니다]</li>
										<br>
											<li>__주변시설 이용 시 시설물의 훼손, 분실에 대한 책임은 이용인에게 있으며 시설 파손 시 수리비용을 청구할 수 있습니다. __무분별한 오락 및 음주, 고성방가 등으로 불쾌감을 주는 행동으로 퇴실될 수 있으니 삼가주시기 바랍니다. __잠깐 방문하시는 분도 예외 없이 추가 인원으로 적용됩니다. 쾌적한 캠핑장 조성을 위해 협조해주시기 바랍니다.</li>
											<li style="list-style:none; padding-left:0px;">[예약 공통 주의사항]</li>
											<li>예약관리는 특성상 약간의 시간차에 의하여 오차가 발생할수 있습니다.</li>
											<li>오차에 의한 중복예약 발생시 먼저 예약된 예약건이 우선시 되며 이 경우, 취소수수료 없이 전액 환불처리됩니다.</li>
											<li>숙소의 요청에 따라 일부 요금은 현장에서 결제가 진행될 수 있습니다.</li>
											<li>각 숙박시설 정보는 예약을 위한 참고 자료입니다. 숙박시설 내 자체 변동이나 기타 사유로 인해 실제와 차이가 있을 수 있으며, 이에 대해 캠핑톡(주)는 책임을 지지 않습니다.</li>
											<li>고객님의 요청사항은 숙박시설에 전달되나, 최종 반영 여부는 예약하신 숙박시설의 결정사항이므로 캠핑톡(주)에서 보장할 수 없는 사항임을 유의하여 주시기 바랍니다.</li>
											<li>객실요금은 기준인원에 대한 요금이며 인원 추가시 추가요금이 발생하며 숙소 사정에 따라 현장결제 할 수도 있습니다. 최대인원 이외의 인원은 입실은 불가합니다.</li>
											<li>예약시 신청하신 인원이외에 추가인원은 입실이 거부될 수 있습니다. 예약인원 초과로 인한 입실 거부시 환불 불가 정책이 적용되오니, 유의하시기 바랍니다.</li>
											<li>예약 이후 모든 변경은 해당 예약 취소후 다시 예약하셔야 합니다. 예약변경을 위한 취소시에도 취소수수료가 부과되오니 신중하게 예약하시기 바랍니다.</li>
											<li>캠핑톡(주)에서는 이용수칙과 관련하여 모든 숙소에 대하여 통일된 규정을 제공하지 않습니다.</li>
										</ul>
									<h6>개인 정보 수집 및 이용에 동의합니다.</h6>
										<ul>
											<li>분류 : 필수정보</li>
											<li>수집 및 이용동의목적 : 계약의 이행 및 서비스 제공, 예약, 구매, 관심상품 내역, 결제대금의 청구, 상담, 불만, 민원처리, 고지/안내사항 전달, 상품/서비스 이용실적 정보 통계 분석, 상품/서비스 서비스 개선 및 추천, 불법 · 부정이용방지, 개인정보 유효기간제 준수</li>
											<li>항목 : 예약내역(예약일시, 결제금액, 업체명), 디바이스 ID, 휴대폰 번호, 서비스이용기록, IP 주소, 접속로그, Cookie, 광고식별자, 단말기 OS와 버전, 단말기 모델명, 브라우저 버전, 예약자 및 구매자의 이름, 휴대폰 번호, (필요한 서비스의 경우)생년월일, (예약자와 방문자가 다른 경우) 방문자 및 탑승자의 이름, 휴대폰 번호, (필요한 서비스의 경우)생년월일, (예약확인서 발급 시) 예약자의 이메일 주소, (현금 환불 요청 시) 계좌번호 및 예금주명</li>
											<li>보유 및 이용기간 : 이용종료일로부터 상담 등 민원처리, 정산 및 환불 처리를 위해 30일 후 삭제 [관계법령에 따라 보존할 필요가 있는 경우 해당 법령에서 요구하는 기한까지 보관 (예: 구매 회원의 경우 5년간, IP 주소의 경우 3개월)]</li>
										</ul>
									<h6>개인 정보 제 3자 제공에 동의합니다.</h6>
										<ul>
											<li>제공받는 자 : 더시크릿가든 캠프지라운드 (상호 : 더시크릿가든캠프지라운드)</li>
											<li>제공 목적 : 예약 · 구매한 상품 · 서비스의 제공 및 계약의 이행(이용자 및 이용정보 확인, 정산 등), 민원처리 등 소비자 분쟁해결</li>
											<li>제공 항목 : 예약번호, 예약자 정보(예약자명, 휴대폰 번호) 또는 방문자 정보(방문자명, 휴대폰 번호)</li>
											<li>이용 및 보유기간 : 개인정보 이용목적 달성 시까지</li>
											<li>단, 관계법령에 의하여 보존할 필요성이 있는 경우 그 시점까지 보존 후 지체 없이 파기</li>
											<li>위 개인정보 제3자 제공 동의를 거부할 수 있으며, 거부할 경우 서비스 이용이 제한됩니다.</li>
										</ul>
									<h6>예약자는 성인이며, 서비스 이용약관에 동의합니다.</h6>
										<ul>
											<li>고객님께서는 전자상거래법 제8조 2항에 따른 위의 고지사항(이용 시 주의사항, 취소수수료 정책 등) 및 서비스 이용약관을 확인하고 이에 동의합니다.</li>
											<li>이용 약관은 사이트 하단의 "이용약관"에서 확인가능합니다.</li>
											<li>만 19세가 되는 해의 1월 1일을 맞지하지 않으신 고객님께서는 예약이 불가능합니다. 또한 동법 30조 8항에 의거 미성년자 혼숙은 법령으로 엄격히 금지되어있습니다.</li>
										
										</ul>

                                
                                
								</div>
								<br>
								<hr>
                               	<br>
								<h5> 결제금액 정보 </h5>
								<hr>
								<ul>
									<li style="list-style:none; padding-left:0px;">[객실요금]</li>
									<li>2021년 11월 29일 (월) / 100,000원 * 1개 = 100,000원</li>
									<li>객실요금 합계 100,000 원</li>
									<li style="list-style:none; padding-left:0px;">[옵션]</li>
									<li>지금결제: 장작세트 19,500원 * 1개 = 19,500원</li>
									<li style="text-align: right; list-style:none; padding-left:0px; font-size: 19px; font-weight: bold;">총 금액 : 119,500 원</li>
								</ul>
                                    <!-- Submit error message-->
                                    <!---->
                                    <!-- This is what your users will see when there is-->
                                    <!-- an error submitting the form-->
                                    <div class="d-none" id="submitErrorMessage"><div class="text-center text-danger mb-3">Error sending message!</div></div>
                                    <!-- Submit Button-->
                                    <div class="d-grid"><button class="btn btn-primary btn-lg disabled" id="submitButton" type="submit">Submit</button></div>
                                </form>
                            </div>
                        </div>
                    </div>
                   
                </div>
            </section>
        </main>
        
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
        <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
        <!-- * *                               SB Forms JS                               * *-->
        <!-- * * Activate your form at https://startbootstrap.com/solution/contact-forms * *-->
        <!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
        <script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
        <!-- footer 부분 -->
		<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>      
    </body>
</html>
