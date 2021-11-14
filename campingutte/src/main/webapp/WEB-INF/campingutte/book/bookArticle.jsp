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
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

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

<body class="d-flex flex-column">

<main class="flex-shrink-0">
<!-- header(메뉴바) 부분 -->
<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>

<div class="body-container" style="width: 700px;">
	<div class="body-title">
		<h3><i class="fas fa-chalkboard"></i> 예약 확인 </h3>
	</div>
	      
	<table class="table table-border table-article">
		<tr>
			<td colspan="2" align="center">
				${dto.bookNo}
			</td>
		</tr>
		
		<tr>
			<td width="50%">
				이름 : ${dto.bookName}
			</td>
			<td align="right">
				${dto.bookDate}
			</td>
		</tr>
		
		<tr style="border-bottom: none;">
			<td colspan="2" valign="top" height="200">
				${dto.bookRequest}
			</td>
		</tr>
		
	</table>
	
	<table class="table">
		<tr>
			<td width="50%">
				<c:choose>
					<c:when test="${sessionScope.member.memberId==dto.memberId}">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/book/bookUpdate.do?bookNo=${dto.bookNo}&page=${page}';">수정</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn" disabled="disabled">수정</button>
					</c:otherwise>
				</c:choose>
		    	
				<c:choose>
		    		<c:when test="${sessionScope.member.memberId==dto.memberId || sessionScope.member.memberId=='admin'}">
		    			<button type="button" class="btn" onclick="deleteBoard();">삭제</button>
		    		</c:when>
		    		<c:otherwise>
		    			<button type="button" class="btn" disabled="disabled">삭제</button>
		    		</c:otherwise>
		    	</c:choose>
			</td>
			<td align="right">
				<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/book/bookList.do?${query}';">리스트</button>
			</td>
		</tr>
	</table>       
</div>

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