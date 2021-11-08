<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 캠핑어때 회원가입 </title>
<jsp:include page="/WEB-INF/campingutte/layout/staticHeader.jsp"/>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<style type="text/css">
.table-form td {
	padding: 7px 0;
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
	border: 1px solid #222;
}

.help-block, .block {
	margin-top: 5px;
}
.msg-box {
	text-align: center; color: blue;
}
</style>


<script type="text/javascript">
function memberOk() {
	var f = document.memberForm;
	var str;

	str = f.memberId.value;
	if( !/^[a-z][a-z0-9_]{4,9}$/i.test(str) ) { 
		alert("아이디를 다시 입력 하세요. ");
		f.memberId.focus();
		return;
	}

	str = f.memberPwd.value;
	if( !/^[a-z][a-z0-9_]{4,9}$/i.test(str) ) { 
		alert("패스워드를 다시 입력 하세요. ");
		f.memberPwd.focus();
		return;
	}

	if( str != f.memberPwd2.value ) {
        alert("패스워드가 일치하지 않습니다. ");
        f.memberPwd.focus();
        return;
	}
	
    str = f.memberName.value;
    if( !/^[가-힣]{2,5}$/.test(str) ) {
        alert("이름을 다시 입력하세요. ");
        f.memberName.focus();
        return;
    }

    str = f.memberBirth.value;
    if( !str ) {
        alert("생년월일를 입력하세요. ");
        f.memberBirth.focus();
        return;
    }
    
    str = f.memberTel.value;
    if( !str ) {
        alert("전화번호를 입력하세요. ");
        f.memberTel.focus();
        return;
    }
    
    str = f.memberEmail.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.memberEmail.focus();
        return;
    }

   	f.action = "${pageContext.request.contextPath}/member/${mode}_ok.do";
    f.submit();
}

/*
function changeEmail() {
    var f = document.memberForm;
	    
    var str = f.selectEmail.value;
    if(str!="direct") {
        f.memberEmail2.value=str; 
        f.memberEmail2.readOnly = true;
        f.memberEmail1.focus(); 
    }
    else {
        f.memberEmail2.value="";
        f.memberEmail2.readOnly = false;
        f.memberEmail1.focus();
    }
}
*/
</script>

</head>
<body>


<header>
	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
</header>

	
<main>
    <div class="body-container">
        <div class="body-title">
        	<h3> ⛺️ 캠핑어때 회원가입 </h3>
        </div> <br>
        
		<form name="memberForm" method="post">
		<table class="table table-border table-form">
			<tr>
				<td>아&nbsp;이&nbsp;디</td>
				<td>
					<p>
						<input type="text" name="memberId" id="memberId" maxlength="10" class="boxTF" value="${dto.memberId}" style="width: 50%;" ${mode=="update" ? "readonly='readonly' ":""}>
					</p>
					<p class="help-block">아이디는 5~10자 이내로 입력하세요.</p>
				</td>
			</tr>
		
			<tr>
				<td>패스워드</td>
				<td>
					<p>
						<input type="password" name="memberPwd" class="boxTF" maxlength="10" style="width: 50%;">
					</p>
					<p class="help-block">패스워드를 5~10자 이내로 입력하세요.</p>
				</td>
			</tr>
		
			<tr>
				<td>패스워드 확인</td>
				<td>
					<p>
						<input type="password" name="memberPwd2" class="boxTF" maxlength="10" style="width: 50%;">
					</p>
					<p class="help-block">패스워드를 한번 더 입력해주세요.</p>
				</td>
			</tr>
		
			<tr>
				<td>이&nbsp;&nbsp;&nbsp;&nbsp;름</td>
				<td>
					<input type="text" name="memberName" maxlength="10" class="boxTF" value="${dto.memberName}" style="width: 50%;" ${mode=="update" ? "readonly='readonly' ":""}>
				</td>
			</tr>
		
			<tr>
				<td>생년월일</td>
				<td>
					<input type="date" name="memberBirth" class="boxTF" value="${dto.memberBirth}" style="width: 50%;">
				</td>
			</tr>
		
			<tr>
				<td>이 메 일</td>
				<td>
				<!--  
					  <select name="selectEmail" class="selectField" onchange="changeEmail();">
							<option value="">선 택</option>
							<option value="naver.com"   ${dto.memberEmail2=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
							<option value="hanmail.net" ${dto.memberEmail2=="hanmail.net" ? "selected='selected'" : ""}>한 메일</option>
							<option value="gmail.com"   ${dto.memberEmail2=="gmail.com" ? "selected='selected'" : ""}>지 메일</option>
							<option value="hotmail.com" ${dto.memberEmail2=="hotmail.com" ? "selected='selected'" : ""}>핫 메일</option>
							<option value="direct">직접입력</option>
					  </select>
				-->
					  <input type="text" name="memberEmail" maxlength="30" class="boxTF" value="${dto.memberEmail}" style="width: 50%;">
				</td>
			</tr>
			
			<tr>
				<td>전화번호</td>
				<td>
					  <input type="text" name="memberTel" maxlength="11" class="boxTF" value="${dto.memberTel}" style="width: 50%;"> -
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
							<option value="제주도" ${dto.memberAddr=="제주도" ? "selected='selected'" : ""}>제주도</option>
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
							&nbsp;캠핑어때랑 함께 하시겠습니까?
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
<jsp:include page="/WEB-INF/campingutte/layout/staticFooter.jsp"/>



</body>
</html>