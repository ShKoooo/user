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
</head>
<body>

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

</body>
</html>