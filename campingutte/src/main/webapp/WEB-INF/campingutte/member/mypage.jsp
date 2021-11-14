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
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
function login() {
	location.href="${pageContext.request.contextPath}/member/login.do";
}
</script>

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
        
		<form name="memberForm" method="post" style="display: inline-block;">
		<table class="table table-border table-form" style="display: inline-block;">
			<tr>
				<td>아&nbsp;이&nbsp;디</td>
				<td>
					<p>
						${dto.memberId}
					</p>
				</td>
			</tr>
		
			<tr>
		
			<tr>
				<td>이&nbsp;&nbsp;&nbsp;&nbsp;름</td>
				<td>
					<input type="text" name="memberName" maxlength="10" class="boxTF" value="${dto.memberName}" style="width: 50%;" ${mode=="update" ? "readonly='readonly' ":""}>
				</td>
			</tr>
		
			<tr>
				<td>생년월일</td>
				<td>
					<input type="date" name="memberBirth" class="boxTF" value="${dto.memberBirth}" style="width: 50%; vertical-align: center;">
				</td>
			</tr>
		
			<tr>
				<td>이 메 일</td>
				<td>
					  <input type="text" name="memberEmail" maxlength="30" class="boxTF" value="${dto.memberEmail}" style="width: 80%;">
				</td>
			</tr>
			
			<tr>
				<td>전화번호</td>
				<td>
					<input type="text" name="memberTel" maxlength="11" class="boxTF" value="${dto.memberTel}" style="width: 70%;" onkeypress="onlyNumber();" placeholder="예) 01012341234"/>
				</td>
			</tr>
			
			<tr>
				<td valign="top">주&nbsp;&nbsp;&nbsp;&nbsp;소</td>
				<td>
					  <select name="memberAddr" class="selectField">
							<option value="">선 택</option>
							<option value="서울시" ${dto.memberAddr=="서울시" ? "selected='selected'" : ""}>서울시</option>
							<option value="강원도" ${dto.memberAddr=="강원도" ? "selected='selected'" : ""}>강원도</option>
							<option value="경기도" ${dto.memberAddr=="경기도" ? "selected='selected'" : ""}>경기도</option>
							<option value="경상도" ${dto.memberAddr=="경상도" ? "selected='selected'" : ""}>경상도</option>
							<option value="충청도" ${dto.memberAddr=="충청도" ? "selected='selected'" : ""}>충청도</option>
							<option value="제주도" ${dto.memberAddr=="제주도" ? "selected=t'selected'" : ""}>제주도</option>
						</select>
						<input type="text" name="memberAddr2" id="memberAddr2" maxlength="50" class="boxTF" value="${dto.memberAddr2}" style="width: 96%;">
				</td>
			</tr>
			
		</table>
		
		<table class="table">
			<c:if test="${mode=='member'}">
				<tr>
					<td align="center">
						<span>
							<input type="checkbox" name="terms" value="1" checked="checked" onchange="form.btnOk.disabled = !checked">
							&nbsp;캠핑어때 회원가입을 하시겠습니까?
						</span>
					</td>
				</tr>
			</c:if>
					
			<tr>
				<td align="center">
				    <button type="button" class="btn" name="btnOk" onclick="memberOk();"> ${mode=="member"?"회원가입":"정보수정"} </button>
				    <button type="reset" class="btn"> 다시입력 </button>
				    <button type="button" class="btn" 
				    	onclick="javascript:location.href='${pageContext.request.contextPath}/';"> ${mode=="member"?"가입취소":"수정취소"} </button>
				</td>
			</tr>
			
			<tr>
				<td align="center">
					<span class="msg-box">${message}</span>
				</td>
			</tr>
		</table>
		</form>
      
    </div>

</main>


<footer>
	<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
</footer>

</body>
</html>