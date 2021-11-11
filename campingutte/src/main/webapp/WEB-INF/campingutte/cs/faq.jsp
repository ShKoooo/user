<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />

<style type="text/css">

.titleform {
	font-weight: 300;
}
.qnaform {
	margin: 50px;
}
.qna {
	list-style: none;
}

.qna input {
	display: none;
}

.qna label {
	cursor: pointer;
	color: #5D5D5D;
	font-size: 25px;
}

.qna label::before {
	display: block;
	content: "▶︎";
	float: before;
	float: left;
}

.qna input:checked + label::before {
	content: "▼";
	float: left;
}

.qna div { /* 내용 영역 - 기본 감춤 상태 */
  display: none;
  overflow: hidden;
  	color: #353535;
	font-size: 20px;
	margin-top: -10px;
	margin-bottom: 15px;
}
.qna input:checked + label + div { /* 내용 영역 펼침 */
  display: block;
}

.hr {
	color: #BDBDBD;
}

.textstyle {
	padding: 10px;
}

</style>


<script type="text/javascript" src="https://code.jquery.com/jquery-latest.js"></script>

</head>
<body>

<header>
	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
</header>

<form class="qnaform">

<div class="titleform"> 
	<h3> ⛺ 캠핑어때 자주하는 질문 </h3>
</div><br>
	<ul class="qna">
		<li>
			<input type="checkbox" id="qna-1">
			<label for="qna-1" class="qnasub"> 예약을 취소하고 싶어요. </label>
			<div>
				<p> 예약은 2일 전까지만 취소가 가능합니다. </p>
			</div>
			<hr>
		</li>
		<li>
			<input type="checkbox" id="qna-2">
			<label for="qna-2" class="qnasub"> 입실일을 변경하고 싶어요. </label>
			<div>
				<p> 예약이 완료된 이후에는 입실일 변경이 불가능합니다.  </p>
			</div>
			<hr>
		</li>
		<li>
			<input type="checkbox" id="qna-3">
			<label for="qna-3" class="qnasub"> 비회원도 예약이 가능한가요? </label>
			<div>
				<p> 캠핑어때는 회원 가입 이용자에게만 서비스가 제공됩니다. </p>
			</div>
			<hr>
		</li>
		<li>
			<input type="checkbox" id="qna-4">
			<label for="qna-4" class="qnasub"> 천재지변으로 인한 예약 변경은 어떻게 하나요? </label>
			<div class="textstyle">
				<p> 일반적인 우천때문에 예약 변경은 불가능하며, 지진, 해일, 호우주의보 등 캠핑 이용에 심각한 안전 우려가 염려될 경우 예약 당사 사업장과 협의 후 예약 변경이 가능합니다.  </p>
			</div>
			<hr>
		</li>

		<li>
			<input type="checkbox" id="qna-5">
			<label for="qna-5" class="qnasub"> 취소 수수료는 어떻게 되나요? </label>
			<div>
				<p> 예약 3일 전까지 100% 환불, 2일 전까지 50% 환불, 하루 전부터는 환불이 불가능합니다.  </p>
			</div>
			<hr>
		</li>
		<li>
			<input type="checkbox" id="qna-6">
			<label for="qna-6" class="qnasub"> 예약 취소했는데 언제 환불되나요? </label>
			<div>
				<p> 예약을 취소하고부터 공휴일 제외 일주일 뒤에 환불이 진행됩니다. </p>
			</div>
			<hr>
		</li>
		<li>
			<input type="checkbox" id="qna-3">
			<label for="qna-3" class="qnasub"> 캠핑어때는 무료 사이트인가요? </label>
			<div>
				<p> 캠핑어때는 서비스 중계 사이트이며, 예약시에는 해당 업소의 가격에 따라 금액이 발생합니다. </p>
			</div>
			<hr>
		</li>
		<li>
			<input type="checkbox" id="qna-7">
			<label for="qna-7" class="qnasub"> 여러 객실을 한번에 예약할 수 있나요? </label>
			<div>
				<p> 한번에 예약은 불가능하지만, 여러번 예약이 가능합니다. </p>
			</div>
			<hr>
		</li>
		
	</ul>
</form>

	
<footer>
	<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>