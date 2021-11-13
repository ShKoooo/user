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

<script type="text/javascript">
$(function(){
	$("#checkIn").datepicker({
		showMonthAfterYear:true,
		minDate:0
	});
});

$(function(){
	$("#checkOut").datepicker({
		showMonthAfterYear:true,
		minDate:"+2D"
	});
});

//숙박일 수 계산
function dateCalcul(){
	var start = $("input[name=srtDate]").datepicker('getDate');
	var end = $("input[name=endDate]").datepicker('getDate');
	
	var day = (end - start)/1000/60/60/24;
}

// 인원수 증가 및 감소
$(function(){
	$("body").on("click", ".btnPlus", function(){
		var count = parseInt($(this).parent("div").find("input[type=text]").val());
		
		
		count=count+1;
		
		$(this).parent("div").find("input[type=text]").val(count);
		
		var total = 0;
		$(this).closest(".peopleCount").find("input").each(function(){
			total += parseInt($(this).val());
			
		});
		$(".people").val(total);
		
	});
	
	$("body").on("click", ".btnMinus", function(){
		var count = parseInt($(this).parent("div").find("input[type=text]").val());
		count=count-1;
		
		if(count < 0) {
			return false;
		}
		$(this).parent("div").find("input[type=text]").val(count);
		
		var total = 0;
		$(this).closest(".peopleCount").find("input").each(function(){
			total += parseInt($(this).val());
			
		});
		$(".people").val(total);
		
	});
	
});


// 검색
function searchList(){
	var f = document.campList;
	f.submit();
}


</script>
<style type="text/css">

.col-lg-3 > input {
 	margin-bottom: 12px;
}

.headcount {
	box-sizing: border-box;
	display: block;
	padding: 0.375rem 0.75rem;
	font-size: 1rem;
    font-weight: 400;
    line-height: 1.5;
}

.btnPlus {
	border: none;
}

.btnMinus {
	border: none;
}
</style>
<title>검색리스트</title>

</head>
<body class="d-flex flex-column">
<!-- header(메뉴바) 부분 -->
<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
	<main class="flex-shrink-0">
		
		<!-- Page Content-->
		<section class="py-5">
			<form name="campList" method="post">

		    <div class="container px-5 my-5">
		        <div class="row gx-5">
		            <div class="col-lg-3">
		            <input type="text" id="checkIn" name="srtDate" value="${srtDate}" readonly="readonly" placeholder="체크인" class="form-control font-size-h5 font-weight-bolder">
		            <input type="text" id="checkOut" name="endDate" value="${endDate}" readonly="readonly" placeholder="체크아웃" class="form-control font-size-h5 font-weight-bolder">
		            <input type="text" name="addr1"  value="${addr1}" placeholder="지역" class="form-control font-size-h5 font-weight-bolder">
		            <input type="text" name="campName" value="${campName}" placeholder="캠핑장명" class="form-control font-size-h5 font-weight-bolder">
		            <div class="peopleCount">
			            <div>
			            	<span style="margin-right: 50px;">성인</span>
				            <button type="button" class="btnMinus">-</button>
				            <input type="text" name="adult" id="adult" readonly="readonly" value="1" style="border: none; padding: 0; text-align: center;">
				            <button type="button" class="btnPlus">+</button>
						</div>
						<div>
							<span style="margin-right: 50px;">아동</span>
				            <button type="button" class="btnMinus">-</button>
				            <input type="text" name="kid" id="kid" readonly="readonly" value="0" style="border: none; padding: 0; text-align: center;">
				            <button type="button" class="btnPlus">+</button>
						</div>
					</div>
					<div>
					
						<input type="hidden" class="people" name="people" value="${people}" readonly="readonly">
					</div>
		            <div><button class="btn btn-primary btn-lg" id="submitButton" type="button" style="width: 100%" onclick="searchList();">숙소 검색</button></div>
		                <div class="d-flex align-items-center mt-lg-5 mb-4">
		                    <img class="img-fluid rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." />
		                    <div class="ms-3">
		                        <div class="fw-bold">Valerie Luna</div>
		                        <div class="text-muted">News, Business</div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-9">
		                <!-- Post content-->
		                <article>
		                    <!-- Post header-->
		                    <header class="mb-4">
		                        <!-- Post title-->
		                        <h1 class="fw-bolder mb-1">캠핑장/글램핑/카라반 검색&예약</h1>
		                        <!-- Post meta content-->
		                        
		                        <div class="text-muted fst-italic mb-2">January 1, 2021</div>
		                        <hr>
		                        <!-- Post categories-->
		                        <a class="badge bg-secondary text-decoration-none link-light" href="#!">Web Design</a>
		                        <a class="badge bg-secondary text-decoration-none link-light" href="#!">Freebies</a>
		                    </header>
		                    <!-- Preview image figure-->
		                    <figure class="mb-4"><img class="img-fluid rounded" src="https://dummyimage.com/900x400/ced4da/6c757d.jpg" alt="..." /></figure>
		                    <!-- Post content-->
		                    
			                    <table>
			                    
			                   	<c:forEach var="dto" items="${list}">
									<tr>
										<td>${dto.campNo}</td>
										<td>${dto.campName}</td>
									</tr>
									
								</c:forEach>
								</table>

		                    <section class="mb-5">
		                        <p class="fs-5 mb-4">Science is an enterprise that should be cherished as an activity of the free human mind. Because it transforms who we are, how we live, and it gives us an understanding of our place in the universe.</p>
		                        <p class="fs-5 mb-4">The universe is large and old, and the ingredients for life as we know it are everywhere, so there's no reason to think that Earth would be unique in that regard. Whether of not the life became intelligent is a different question, and we'll see if we find that.</p>
		                        <p class="fs-5 mb-4">If you get asteroids about a kilometer in size, those are large enough and carry enough energy into our system to disrupt transportation, communication, the food chains, and that can be a really bad day on Earth.</p>
		                        <h2 class="fw-bolder mb-4 mt-5">I have odd cosmic thoughts every day</h2>
		                        <p class="fs-5 mb-4">For me, the most fascinating interface is Twitter. I have odd cosmic thoughts every day and I realized I could hold them to myself or share them with people who might be interested.</p>
		                        <p class="fs-5 mb-4">Venus has a runaway greenhouse effect. I kind of want to know what happened there because we're twirling knobs here on Earth without knowing the consequences of it. Mars once had running water. It's bone dry today. Something bad happened there as well.</p>
		                    </section>
		                </article>

		            </div>
		        </div>
		    </div>
		    </form>
		</section>
	</main>
        <!-- footer 부분 -->
		<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
         
    </body>
</html>