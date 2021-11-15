<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 캠핑어때 자유게시판 </title>
<jsp:include page="/WEB-INF/campingutte/layout/staticHeader.jsp"/>
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />

<link rel="icon" href="data:;base64,iVBORw0KGgo=">


<style type="text/css">
.table-article tr > td {
	padding-left: 5px; padding-right: 5px;
}

.body-container {
	margin: 50px;
	width: 90%; 
	vertical-align: center; 
}

.btn {
	border-width: 1px;
	border-color: #aaa;
	border-style: solid;
	vertical-align: middle;
	text-align: center;
}

</style>

<script type="text/javascript">
<c:if test="${sessionScope.member.memberId==dto.memberId || sessionScope.member.memberId=='admin'}">
function deleteBoard() {
    if(confirm("게시글을 삭제 하시 겠습니까 ? ")) {
	    var query = "notiNo=${dto.notiNo}&${query}";
	    var url = "${pageContext.request.contextPath}/notice/delete.do?" + query;
    	location.href = url;
    }
}
</c:if>

</script>

</head>
<body>

<header>
	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
</header>

<main>
	<div class="body-container">
		<div class="body-title" style="vertical-align: center;">
			<h3><i class="fas fa-chalkboard" style="vertical-align: center;"></i> 게시판 </h3>
		</div>
        <hr>
		<table class="table table-border table-article" style="vertical-align: center;">
			<tr>
				<td colspan="2" align="center" style="font-weight: 800px; font-size: 27px;">
					${dto.notiSubject}
				</td>
			</tr>
			
			<tr>
				<td width="50%">
					이름 : ${dto.memberName}
				</td>
				<td align="right">
					${dto.notiDate} | 조회 ${dto.notiHitCount}
				</td>
			</tr>
			
			<tr style="border-bottom: none;">
				<td colspan="2" valign="top" height="200">
					${dto.notiContent}
				</td>
			</tr>
		</table>
		<table class="table">
			<tr>
				<td width="50%">
					<c:choose>
						<c:when test="${sessionScope.member.memberId==dto.memberId}">
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/notice/update.do?notiNo=${dto.notiNo}&page=${page}';">수정</button>
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
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/notice/list.do'">리스트</button>
				</td>
			</tr>
		</table>
		
		</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>