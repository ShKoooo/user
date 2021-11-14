<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="resource/css/styles.css">
<script src="resource/js/scripts.js"></script>

<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />

<link href="${pageContext.request.contextPath}/resource/css/paginate.css" rel="stylesheet" />

<meta charset="UTF-8">

<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<style type="text/css">
.body-container {
    margin: 0 auto 15px;
    width: 800px;
}
.body-container:before, .body-container:after{
	content: "";
	display: block;
	clear: both;	
}

.body-title {
    color: #424951;
    padding-top: 25px;
    padding-bottom: 5px;
    margin: 0 0 25px 0;
    border-bottom: 1px solid #ddd;
}

.body-title h3 {
    font-size: 23px;
    min-width: 300px;
    font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
    font-weight: bold;
    margin: 0 0 -5px 0;
    padding-bottom: 5px;
    display: inline-block;
    border-bottom: 3px solid #424951;
}

.table {
	width: 100%;
	border-spacing: 0;
	border-collapse: collapse;
}

.table th, .table td {
	padding: 10px 0;
}

.table tr {
	border-bottom: 1px solid #ccc;
}

.table tr:first-child {
	border-top: 2px solid #ccc;
	background: #eee;
}

.table td, .table th {
	border: 1px solid #ccc;
}

.table td {
	padding: 7px 0;
	text-align: center;
}

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

th {	/* 테이블 안 글씨 가운데 정렬 */ 
	text-align: center;
}


</style>


<script type="text/javascript">
function deleteBoard(campNo) {
    if(confirm("게시글을 삭제 하시겠습니까 ? ")) {
	    var query = "campNo="+campNo+"&page=${page}";
	    var url = "${pageContext.request.contextPath}/admin/campDelete.do?" + query;
	    location.href = url;
    }
}


</script>
</head>
<body class="d-flex flex-column h-100">
<!-- header(메뉴바) 부분 -->
<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>

<div class="body-container">
	<div class="body-title">
		<h3>등록된 캠핑장 관리</h3>
	</div>
	
	<form name="campList" method="post">
		<p style="text-align: right; margin: 1px">${dataCount}개(${page}/${total_page} 페이지)</p>
		
		<table class="table">
			<tr>
				<th width="100">캠핑장번호</th>
				<th width="150">캠핑장명</th>
				<th width="150">유형번호</th>
				<th width="250">유형이름</th>
				<th>변경</th>
			</tr>
			<c:forEach var="dto" items="${list}">
				<tr>
					<td>${dto.campNo}</td>
					<td>${dto.campName}</td>
					<td>${dto.typeNo}</td>
					<td>${dto.typeName}</td>
					<td>
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/admin/campUpdate.do?campNo=${dto.campNo}&page=${page}';">수정</button>
						<button type="button" class="btn" onclick="deleteBoard('${dto.campNo}');">삭제</button>
					</td>
				</tr>
			</c:forEach>
		</table>
		
		<div class="page-box">
			${dataCount == 0 ? "등록된 캠핑장이 없습니다." : paging}
		</div>
		
	</form> 
</div>

<!-- footer 부분 -->
<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>

</body>
</html>