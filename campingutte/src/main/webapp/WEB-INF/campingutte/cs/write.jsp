<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>캠핑어때 1:1문의 글쓰기</title>
<jsp:include page="/WEB-INF/campingutte/layout/staticHeader.jsp"/>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />

<style type="text/css">
.table-form td {
	padding: 7px 0;
}
.table-form p {
	line-height: 200%;
}
.table-form tr > td:first-child {
	width: 110px; text-align: center; background: #eee;
}
.table-form tr > td:nth-child(2) {
	padding-left: 10px;
}

.table-form input[type=text], .table-form input[type=file], .table-form textarea {
	width: 96%;
}

.bodyclass {
	margin: 50px;
	vertical-align: center;
}

.btn {
	border-width: 1px;
	border-color: #aaa;
	border-style: solid;
	vertical-align: middle;
	text-align: center;r
}


</style>
<script type="text/javascript">
function sendOk() {
    var f = document.boardForm;
	var str;
	
    str = f.compSubject.value.trim();
    if(!str) {
        alert("제목을 입력하세요. ");
        f.compSubject.focus();
        return;
    }

    str = f.compContent.value.trim();
    if(!str) {
        alert("내용을 입력하세요. ");
        f.content.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/cs/${mode}_ok.do";
    f.submit();
}
</script>

</head>

<body>

<header>
	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
</header>


<main>
	<div class="bodyclass" style="width: 90%;">
		<div class="body-title">
			<h3><i class="fas fa-chalkboard-teacher"></i> 글쓰기 </h3>
		</div>
        
		<form name="boardForm" method="post">
			<table class="table table-border table-form">
				<tr> 
					<td>제&nbsp;&nbsp;&nbsp;&nbsp;목</td>
					<td> 
						<input type="text" name="compSubject" maxlength="100" class="boxTF" value="${dto.compSubject}">
					</td>
				</tr>
				
				<tr> 
					<td>작성자</td>
					<td> 
						<p>${sessionScope.member.memberName}</p>
					</td>
				</tr>
				
				<tr> 
					<td valign="top">내&nbsp;&nbsp;&nbsp;&nbsp;용</td>
					<td> 
						<textarea name="compContent" class="boxTA">${dto.compContent}</textarea>
					</td>
				</tr>
			</table>
				
			<table class="table">
				<tr> 
					<td align="center">
						<button type="button" class="btn" onclick="sendOk();">${mode=='update'?'수정완료':'등록하기'}</button>
						<button type="reset" class="btn">다시입력</button>
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/cs/list.do';">${mode=='update'?'수정취소':'등록취소'}</button>
						<c:if test="${mode=='update'}">
							<input type="hidden" name="boardNum" value="${dto.compNo}">
							<input type="hidden" name="page" value="${page}">
						</c:if>
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