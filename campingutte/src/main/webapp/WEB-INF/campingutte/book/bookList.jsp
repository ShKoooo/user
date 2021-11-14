<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약 목록</title>
<jsp:include page="/WEB-INF/campingutte/layout/staticHeader.jsp"/>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
</head>
<body>

<%-- /book/bookList.do --%>
<header>
	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
</header>

<main>
	<div class="body-container" style="width: 700px;">
		<div class="body-title">
			<h3>예약 목록</h3>
		</div>
		
		<table class="table">
			<tr>
				<td width="50%">
					${dataCount}개(${page}/${total_page} 페이지)
				</td>
				<td align="right">&nbsp;</td>
			</tr>
		</table>
		
		<table class="table table-border table-list">
			<tr>
				<th class="num">번호</th>
				<th class="subject">제목</th>
				<th class="name">작성자</th>
				<th class="date">작성일</th>
				<th class="hit">조회수</th>
			</tr>
			
			<c:forEach var="dto" items="${list}">
				<tr>
					<td>${dto.listNum}</td>
					<td class="left">
						<a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
						<c:if test="${dto.replyCount != 0}">(${dto.replyCount})</c:if>
					</td>
					<td>${dto.userName}</td>
					<td>${dto.reg_date}</td>
					<td>${dto.hitCount}</td>
				</tr>
			</c:forEach>
		</table>
		
		<div class="page-box">
			${dataCount == 0 ? "예약 내역이 없습니다." : paging}
		</div>
		
		<table class="table">
			<tr>
				<td width="100">
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/bbs/list.do';">새로고침</button>
				</td>
			</tr>
		</table>	

	</div>
</main>

<footer>
    <jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
</footer>

<jsp:include page="/WEB-INF/campingutte/layout/staticFooter.jsp"/>

</body>
</html>