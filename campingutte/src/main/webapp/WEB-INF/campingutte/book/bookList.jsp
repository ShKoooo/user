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
<title>예약 목록</title>

<style type="text/css">
.table-list tr:first-child{
	background: #eee;
}
.table-list th, .table-list td {
	text-align: center;
}
.table-list .left {
	text-align: left; padding-left: 5px; 
}

.table-list .num {
	width: 160px; color: #787878;
}
.table-list .subject {
	color: #787878;
}
.table-list .name {
	width: 160px; color: #787878;
}
.table-list .date {
	width: 220px; color: #787878;
}
.table-list .hit {
	width: 70px; color: #787878;
}

.setCenter {
	align-content: center;
}


.body-container {
    margin: 15px auto 15px;
    width: 800px;
    min-height: 450px;
}

/* paginate */

.page-box {
	clear: both;
	padding: 20px 0;
	text-align: center;
}
.paginate {
	clear: both;
	text-align: center;
	white-space: nowrap;
	font-size: 14px;	
}
.paginate a {
	border: 1px solid #ccc;
	color: #000;
	font-weight: 600;
	text-decoration: none;
	padding: 3px 7px;
	margin-left: 3px;
	vertical-align: middle;
}
.paginate a:hover, .paginate a:active {
	color: #6771ff;
}
.paginate span {
	border: 1px solid #e28d8d;
	color: #cb3536;
	font-weight: 600;
	padding: 3px 7px;
	margin-left: 3px;
	vertical-align: middle;
}
.paginate :first-child {
	margin-left: 0;
}

/* button */

.btn {
	color: #333;
	border: 1px solid #333;
	background-color: #fff;
	padding: 4px 10px;
	border-radius: 4px;
	font-weight: 500;
	cursor:pointer;
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.btn:hover, .btn:active, .btn:focus {
	background-color: #e6e6e6;
	border-color: #adadad;
	color:#333;
}
.btn[disabled], fieldset[disabled] .btn {
	pointer-events: none;
	cursor: not-allowed;
	filter: alpha(opacity=65);
	-webkit-box-shadow: none;
	box-shadow: none;
	opacity: .65;
}

</style>

<jsp:include page="/WEB-INF/campingutte/layout/staticHeader.jsp"/>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
</head>
<%--
<body>
 --%>

<%-- =============================================================== --%>
<body class="d-flex flex-column">

<main class="flex-shrink-0">
<!-- header(메뉴바) 부분 -->
<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
<!-- Page content-->
    <div class="body-container" style="width: 980px;">
		<div class="body-title">
			<h3>예약 목록</h3>
		</div>
		
		<table class="table">
			<tr>
				<td width="50%">
					${ldataCount}개(${page}/${total_page} 페이지)
				</td>
				<td align="right">&nbsp;</td>
			</tr>
		</table>
		
		<table class="table table-border table-list">
			<tr>
				<th class="num">예약번호</th>
				<th class="name">예약자명</th>
				<th class="date">예약일</th>
				<th class="date">체크인</th>
				<th class="date">체크아웃</th>
			</tr>
			
			<c:forEach var="dto" items="${list}">
				<tr>
					<td>${dto.bookNo}</td>
					<td>${dto.bookName}</td>
					<%--
						<td class="left">
							<a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
						</td>
					--%>
					<td>${dto.bookDate}</td>
					<td>${dto.bookSrtdate}</td>
					<td>${dto.bookEnddate}</td>
				</tr>
			</c:forEach>
		</table>
		
		<div class="page-box">
			${ldataCount == 0 ? "예약 내역이 없습니다." : paging}
		</div>
		
		<table class="table">
			<tr>
				<td width="100">
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/book/bookList.do';">새로고침</button>
				</td>
			</tr>
		</table>	

	</div>
	<!-- Page content 끝 -->
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

<%-- =============================================================== --%>
</html>