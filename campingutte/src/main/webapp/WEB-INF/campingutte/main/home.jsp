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

<header>
    <jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
</header>

<div class="search">
	<p>오늘은 어디로 가볼까?</p>
	<div  class="search-form" style="float: left;">
	<select>
		<option>서울</option>
		<option>경기</option>
		<option>인천</option>
		<option>부산</option>
		<option>...</option>
	</select>
	<input type="text" placeholder="캠핑장 검색" >
	</div>
</div>

<div class="body-container">
	<div class="theme">
		<div class="theme-img">
			
		</div>
		<div class="theme-sub">
		<a>가족과 함께 떠나기 좋은 캠핑장</a>
		</div>
	</div>
	<div class="theme">
		<div class="theme-img">
			
		</div>
		<div class="theme-sub">
		<a>반려동물과 함께 즐기는 캠핑</a>
		</div>
	</div>
</div>


</body>
</html>