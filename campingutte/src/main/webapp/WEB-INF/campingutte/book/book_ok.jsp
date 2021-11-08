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
        <meta name="description" content />
        <meta name="author" content />
        <title>예약 내역 확인</title>
        
        <script type="text/javascript">
		$(function(){
			// $("#accordion").accordion();
			
			// 처음에 활성화되지 않고 활성화된 것 누르면 닫히도록
			$("#accordion").accordion({active:false, collapsible:true});
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
						<br>
						<h3 class="fw-bolder"> 예약 내역 </h3>
						<!-- Name input-->
						<p>예약자 명 : 홍길동</p>
						
						<!-- Phone number input-->
						<p>휴대폰 번호 : 010-1111-1111</p>
						
						<!-- Email address input-->
						<p>이메일 (선택) : aaa@aaa.com</p>
						
						
						<!-- Message input-->
						<p>예약요청사항 (선택)</p>
						<p>ddddddddddddddddddddddddddddddddddd</p>
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
						<h3 class="fw-bolder"> 결제금액 정보 </h3>
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
						<div><button class="btn btn-primary btn-lg disabled" id="submitButton" type="button">HOME</button></div>
						<div><button class="btn btn-primary btn-lg disabled" id="submitButton" type="button">객실 더 보러가기</button></div>
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
