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

<link rel="icon" href="data:;base64,iVBORw0KGgo=">


<style type="text/css">
.table-list tr:first-child{
	background: white;
}
.table-list th, .table-list td {
	text-align: center;
	background: white;
}
.table-list .left {
	text-align: left; padding-left: 5px; 
}

.table-list .num {
	width: 60px; color: #787878;
}
.table-list .subject {
	color: #787878;
}
.table-list .name {
	width: 100px; color: #787878;
}
.table-list .date {
	width: 100px; color: #787878;
}
.table-list .hit {
	width: 70px; color: #787878;
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
	text-align: center;
}

.liststyle {
	vertical-align: center;
	text-align: center;
	margin-right: 1px;
}


</style>
<script type="text/javascript">
function searchList() {
	var f = document.searchForm;
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
			<h3><i class="fas fa-chalkboard"></i> 게시판 </h3>
		</div>
        <br>
        
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
						<a href="${articleUrl}&num=${dto.commNo}">${dto.commSubject}</a>
					</td>
					<td>${dto.memberName}</td>
					<td>${dto.commDate}</td>
					<td>${dto.commHitCount}</td>
				</tr>
			</c:forEach>
		</table>
		
		<div class="liststyle" style="vertical-align: center;" >
			${dataCount == 0 ? "등록된 게시물이 없습니다." : paging }
		</div>
		
		<table class="table">
			<tr>
				<td width="100">
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/community/list.do';">새로고침</button>
				</td>
				<td align="center">
					<form name="searchForm" action="${pageContext.request.contextPath}/community/list.do" method="post">
						<select name="condition" class="selectField">
							<option value="all"      ${condition=="all"?"selected='selected'":"" }>제목+내용</option>
							<option value="memberName" ${condition=="memberName"?"selected='selected'":"" }>작성자</option>
							<option value="commDate"  ${condition=="commDate"?"selected='selected'":"" }>등록일</option>
							<option value="commSubject"  ${condition=="commSubject"?"selected='selected'":"" }>제목</option>
							<option value="commContent"  ${condition=="commContent"?"selected='selected'":"" }>내용</option>
						</select>
						<input type="text" name="keyword" value="${keyword}" class="boxTF">
						<button type="button" class="btn" onclick="searchList();">검색</button>
					</form>
				</td>
				<td align="right" width="100">
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/community/write.do';">글올리기</button>
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