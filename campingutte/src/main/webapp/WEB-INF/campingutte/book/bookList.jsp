<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>예약 목록</title>

<style type="text/css">
.table-list tr:first-child{
	background: #eee;
}
.table-list th, .table-list td {
	text-align: center;
}
.table-list .left {
	text-align: left; padding-left: 5px; 
}

.table-list .num {
	width: 160px; color: #787878;
}
.table-list .subject {
	color: #787878;
}
.table-list .name {
	width: 100px; color: #787878;
}
.table-list .date {
	width: 220px; color: #787878;
}
.table-list .hit {
	width: 70px; color: #787878;
}
</style>

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
				<th class="num">예약번호</th>
				<th class="name">예약자명</th>
				<th class="date">예약일</th>
				<th class="date">시작일</th>
				<th class="date">종료일</th>
			</tr>
			
			<c:forEach var="dto" items="${list}">
				<tr>
					<td>${dto.bookNo}</td>
					<td>${dto.bookName}</td>
					<%--
						<td class="left">
							<a href="${articleUrl}&num=${dto.num}">${dto.subject}</a>
						</td>
					--%>
					<td>${dto.bookDate}</td>
					<td>${dto.bookSrtdate}</td>
					<td>${dto.bookEnddate}</td>
				</tr>
			</c:forEach>
		</table>
		
		<div class="page-box">
			${dataCount == 0 ? "예약 내역이 없습니다." : paging}
		</div>
		
		<table class="table">
			<tr>
				<td width="100">
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/book/bookList.do';">새로고침</button>
				</td>
			</tr>
		</table>	

	</div>
</main>

<%--
	<footer>
	    <jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
	</footer>
	
	<jsp:include page="/WEB-INF/campingutte/layout/staticFooter.jsp"/>
 --%>

</body>
</html>