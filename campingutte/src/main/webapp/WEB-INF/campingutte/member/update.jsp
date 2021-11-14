<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>캠핑어때</title>
<jsp:include page="/WEB-INF/campingutte/layout/staticHeader.jsp"/>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />


<style type="text/css">
main .container {
	margin: 80px auto 50px;
	width: 380px;
	padding: 10px;
	min-height: 200px; ; 
}

.title-body {
	padding: 0px 0;
	text-align: center;
	color: black;
}

.title-body .article-title {
    font-size: 30px;
    font-weight: 600;
    text-align: center;
    vertical-align: center;
}

.form-body {
	text-align: center;
}

.form-body .inputTF {
	width: 350px;
	height: 45px;
	padding: 0px;
	padding-left: 15px;
	border:1px solid #666;
	margin-bottom: 5px;
}

.form-table {
	border: 5px solid white;
}
.form-table tr:first-child td {
	padding-top: 20px;
}

.form-table td {
	padding-left: 1px; padding-right: 25px;
}
.form-table tr:last-child td {
	padding-bottom: 20px;
}

.msg-box {
	text-align: center; color: blue;
}

.btnConfirm {
	width: 368px;
	padding: 10px;
	padding-right: 17px;
	margin-bottom: 10px;

}

.button {
	padding: 5px;
}
</style>

<script type="text/javascript">
function sendOk() {
	var f = document.pwdForm;

	var str = f.memberPwd.value;
	if(!str) {
		alert("패스워드를 입력하세요. ");
		f.memberPwd.focus();
		return;
	}

	f.action = "${pageContext.request.contextPath}/member/pwd_ok.do";
	f.submit();
}
</script>

<body>


<header>
	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
</header>
<main>


		<div class="container">
		<div class="title-body" style="color: black; margin-bottom: -30px;">
			<h3> 패스워드 재확인 </h3>
		</div>
		<div class="form-body">
			<form name="pwdForm" method="post" action="">
				<table class="table form-table">
					<tr>
						<td align="center">
							정보 수정을 위해 패스워드를 입력해주세요.
						</td>
					</tr>
					<tr align="center"> 
						<td> 
							<input type="text" name="memberId" class="inputTF"
								tabindex="1"
								value="${sessionScope.member.memberId}"
								readonly="readonly">
						</td>
					</tr>
					<tr align="center"> 
					    <td>
							<input type="password" name="memberPwd" class="inputTF" maxlength="20"
								placeholder="패스워드" 
								tabindex="2">
					    </td>
					</tr>
					<tr align="center"> 
					    <td>
							<button type="button" onclick="sendOk();" class="btnConfirm">확인</button>
							<input type="hidden" name="mode" value="${mode}">
					    </td>
					</tr>
				</table>
				
				<table class="table">
					<tr>
						<td class="msg-box">${message}</td>
					</tr>
				</table>
			</form>           
		</div>
	</div>


</main>
<footer>
	<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>