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
	background: #e6e6e6;
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
	if( !/^(?=.*[a-z])(?=.*[!@#$%^*+=-]|.*[0-9]).{5,10}$/i.test(str) ) { 
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
    
    str = f.memberTel1.value;
    if( !str ) {
        alert("전화번호를 입력하세요. ");
        f.memberTel1.focus();
        return;
    }

    str = f.memberTel2.value;
    if( !/^\d{3,4}$/.test(str) ) {
        alert("숫자만 가능합니다. ");
        f.memberTel2.focus();
        return;
    }

    str = f.tel3.value;
    if( !/^\d{4}$/.test(str) ) {
    	alert("숫자만 가능합니다. ");
        f.tel3.focus();
        return;
    }
    
    str = f.memberEmail1.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.memberEmail1.focus();
        return;
    }

    str = f.memberEmail2.value.trim();
    if( !str ) {
        alert("이메일을 입력하세요. ");
        f.memberEmail2.focus();
        return;
    }

   	f.action = "${pageContext.request.contextPath}/member/${mode}_ok.do";
    f.submit();
}

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
</script>

</head>
<body>


<header>
	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
</header>

	
<main>
    <div class="body-container">
        <div class="body-title">
            <h3><i class="fas fa-user"></i> ${title} </h3>
        </div>
        
		<form name="memberForm" method="post">
		<table class="table table-border table-form">
			<tr>
				<td>아&nbsp;이&nbsp;디</td>
				<td>
					<p>
						<input type="text" name="memberId" id="memberId" maxlength="10" class="boxTF" value="${dto.memberId}" style="width: 50%;" ${mode=="update" ? "readonly='readonly' ":""}>
					</p>
					<p class="help-block">아이디는 5~10자 이내이며, 첫글자는 영문자로 시작해야 합니다.</p>
				</td>
			</tr>
		
			<tr>
				<td>패스워드</td>
				<td>
					<p>
						<input type="password" name="memberPwd" class="boxTF" maxlength="10" style="width: 50%;">
					</p>
					<p class="help-block">패스워드는 5~10자 이내이며, 하나 이상의 숫자나 특수문자가 포함되어야 합니다.</p>
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
					  <select name="selectEmail" class="selectField" onchange="changeEmail();">
							<option value="">선 택</option>
							<option value="naver.com"   ${dto.memberEmail2=="naver.com" ? "selected='selected'" : ""}>네이버 메일</option>
							<option value="hanmail.net" ${dto.memberEmail2=="hanmail.net" ? "selected='selected'" : ""}>한 메일</option>
							<option value="gmail.com"   ${dto.memberEmail2=="gmail.com" ? "selected='selected'" : ""}>지 메일</option>
							<option value="hotmail.com" ${dto.memberEmail2=="hotmail.com" ? "selected='selected'" : ""}>핫 메일</option>
							<option value="direct">직접입력</option>
					  </select>
					  <input type="text" name="memberEmail1" maxlength="30" class="boxTF" value="${dto.memberEmail1}" style="width: 33%;"> @ 
					  <input type="text" name="memberEmail2" maxlength="30" class="boxTF" value="${dto.memberEmail2}" style="width: 33%;" readonly="readonly">
				</td>
			</tr>
			
			<tr>
				<td>전화번호</td>
				<td>
					  <select name="memberTel1" class="selectField">
							<option value="">선 택</option>
							<option value="010" ${dto.memberTel1=="010" ? "selected='selected'" : ""}>010</option>
							<option value="02"  ${dto.memberTel1=="02"  ? "selected='selected'" : ""}>02</option>
							<option value="031" ${dto.memberTel1=="031" ? "selected='selected'" : ""}>031</option>
							<option value="032" ${dto.memberTel1=="032" ? "selected='selected'" : ""}>032</option>
							<option value="033" ${dto.memberTel1=="033" ? "selected='selected'" : ""}>033</option>
							<option value="041" ${dto.memberTel1=="041" ? "selected='selected'" : ""}>041</option>
							<option value="042" ${dto.memberTel1=="042" ? "selected='selected'" : ""}>042</option>
							<option value="043" ${dto.memberTel1=="043" ? "selected='selected'" : ""}>043</option>
							<option value="044" ${dto.memberTel1=="044" ? "selected='selected'" : ""}>044</option>
							<option value="051" ${dto.memberTel1=="051" ? "selected='selected'" : ""}>051</option>
							<option value="052" ${dto.memberTel1=="052" ? "selected='selected'" : ""}>052</option>
							<option value="053" ${dto.memberTel1=="053" ? "selected='selected'" : ""}>053</option>
							<option value="054" ${dto.memberTel1=="054" ? "selected='selected'" : ""}>054</option>
							<option value="055" ${dto.memberTel1=="055" ? "selected='selected'" : ""}>055</option>
							<option value="061" ${dto.memberTel1=="061" ? "selected='selected'" : ""}>061</option>
							<option value="062" ${dto.memberTel1=="062" ? "selected='selected'" : ""}>062</option>
							<option value="063" ${dto.memberTel1=="063" ? "selected='selected'" : ""}>063</option>
							<option value="064" ${dto.memberTel1=="064" ? "selected='selected'" : ""}>064</option>
							<option value="070" ${dto.memberTel1=="070" ? "selected='selected'" : ""}>070</option>
					  </select>
					  <input type="text" name="memberTel2" maxlength="4" class="boxTF" value="${dto.memberTel2}" style="width: 33%;"> -
					  <input type="text" name="memberTel3" maxlength="4" class="boxTF" value="${dto.memberTel3}" style="width: 33%;">
				</td>
			</tr>
			
			<tr>
				<td valign="top">주&nbsp;&nbsp;&nbsp;&nbsp;소</td>
				<td>
					<p>
						<input type="text" name="memberAddr" id="memberAddr" maxlength="50" class="boxTF" value="${dto.memberAddr}" readonly="readonly" style="width: 96%;">
					</p>
					<p class="block">
						<input type="text" name="memberAddr2" id="memberAddr2" maxlength="50" class="boxTF" value="${dto.memberAddr2}" style="width: 96%;">
					</p>
				</td>
			</tr>
			
		</table>
		
		<table class="table">
			<c:if test="${mode=='member'}">
				<tr>
					<td align="center">
						<span>
							<input type="checkbox" name="terms" value="1" checked="checked" onchange="form.btnOk.disabled = !checked">
							약관에 동의하시겠습니까 ?
						</span>
						<span><a href="">약관보기</a></span>
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