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
<link rel="stylesheet" href="resource/css/styles.css">
<script src="resource/js/scripts.js"></script>

<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />

<link href="${pageContext.request.contextPath}/resource/css/paginate.css" rel="stylesheet" />

<style type="text/css">

.admain-title {
	margin: 30px 0;
	text-align: center;

}

</style>
</head>
<body>
<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
<div class="admain-title">
<h3>관리자 - 관리 목록</h3>
</div>
	<div class="list-group" style="max-width: 540px; margin: 10px auto;">
	  <a href="${pageContext.request.contextPath}/admin/campWrite.do" class="list-group-item list-group-item-action">캠핑장 등록하기</a>
	  <a href="${pageContext.request.contextPath}/admin/campList.do" class="list-group-item list-group-item-action">캠핑장 리스트</a>
	  <a href="${pageContext.request.contextPath}/admin/roomWrite.do" class="list-group-item list-group-item-action">객실 등록하기</a>
	  <a href="${pageContext.request.contextPath}/admin/roomList.do" class="list-group-item list-group-item-action">객실 리스트</a>
	  <a href="${pageContext.request.contextPath}/admin/campTypeWrite.do" class="list-group-item list-group-item-action">타입 등록하기</a>
	  <a href="${pageContext.request.contextPath}/admin/campTypeList.do" class="list-group-item list-group-item-action">타입 리스트</a>
	</div>

<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
</body>
</html>