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
<style type="text/css">
.body-container {
    margin: 0 auto 15px;
    width: 800px;
    min-height: 450px;
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


</style>
<script type="text/javascript">
<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
function deleteBoard() {
    if(confirm("게시글을 삭제 하시겠습니까 ? ")) {
	    var query = "num=${dto.num}&page=${page}";
	    var url = "${pageContext.request.contextPath}/" + query;
    	location.href = url;
    }
}
</c:if>

</script>
</head>
<body>

<div class="body-container">
	<div class="body-title">
		<h3>등록된 캠핑장 관리</h3>
	</div>
	
	<form name="campList" method="post">
		<table class="table">
			<tr>
				<th width="150">캠핑장명</th>
				<th width="250">전화번호</th>
				<th width="200">유형</th>
				<th>변경</th>
			</tr>
			<tr>
				<td>캠핑장 이름~~</td>
				<td>010-1111-1111</td>
				<td>유형:캠핑장</td>
				<td>
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/admin/campUpdate.do?num=${dto.num}&page=${page}';">수정</button>
					<button type="button" class="btn" onclick="deleteBoard();">삭제</button>
				</td>
			</tr>
		</table>
	</form> 
</div>


</body>
</html>