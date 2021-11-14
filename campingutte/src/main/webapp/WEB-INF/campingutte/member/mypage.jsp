<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 캠핑어때 마이페이지 </title>
<jsp:include page="/WEB-INF/campingutte/layout/staticHeader.jsp"/>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />

<script type="text/javascript">
</script>
<style type="text/css">

.signup-form {
	background: white;
	text-align: center;  	
}

.table-form td {
	padding: 5px 0;
}
.table-form tr td:first-child{
	background: white;
	text-align: center;
	width: 120px;
	font-weight: 500;
}
.table-form tr td:nth-child(2) {
	text-align: left; padding-left: 10px;
}

.table-form input[type=text]:focus, .table-form input[type=date]:focus, .table-form input[type=password]:focus {
	border: 1px solid black;
}

.help-block, .block {
	margin-top: -20px;
}
.msg-box {
	text-align: center; color: blue;
}

.boxTF {
	margin: 0px 0;
	border-width: 2px;
	border-color: #aaa;
	border-style: solid;
	height: 40px;
	vertical-align: middle;
	text-align: center;
}

.btn {
	border-width: 1px;
	border-color: #aaa;
	border-style: solid;
}

.button {

}
</style>



</head>
<body>


<header>
	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
</header>

<main>
    <div class="signup-form">
    <br>
        <div class="body-title">
        	<h3> ⛺️ 마이페이지 </h3>
        </div> <br>
        
		<form name="memberForm" method="post" style="display: inline-block; width: 50%">
		<table class="table table-border table-form" style="display: inline-block; width: 50%">
			<tr>
				<td>아&nbsp;이&nbsp;디</td>
				<td>
					${member.memberId}
				</td>
			</tr>
			<tr>
				<td>이&nbsp;&nbsp;&nbsp;&nbsp;름</td>
				<td>
					${member.memberName}
				</td>
			</tr>
		
			<tr>
				<td>생년월일</td>
				<td>
					${dto.memberBirth}
				</td>
			</tr>
		
			<tr>
				<td>이 메 일</td>
				<td>
					${dto.memberEmail}			
				</td>
			</tr>
			
			<tr>
				<td>전화번호</td>
				<td>
					${dto.memberTel}
				</td>
			</tr>
			
			<tr>
				<td valign="top">주&nbsp;&nbsp;&nbsp;&nbsp;소</td>
				<td>
					${dto.memberAddr} <br> ${dto.memberAddr2}			
				</td>
			</tr>
				<tr>
			</table>
			</form>
			<form>
				<a type="button" class="btn" style="vertical-align: center; text-align: center;" 
				href="${pageContext.request.contextPath}/member/update.do?mode=update">정보수정</a>
			</form>
			<br>
    </div>
  <br>
    
</main>

<footer>
	<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>