<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>캠핑어때</title>
<jsp:include page="/WEB-INF/campingutte/layout/staticHeader.jsp"/>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
</head>
<body>
<div class="home">
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
			<img src="/campingutte/resource/images/tvshow.jpg">
		</div>
		<div class="theme-sub">
		<a href="#">드라마에 나온 핫한 캠핑장</a>
		</div>
	</div>
	<div class="theme">
		<div class="theme-img">
			<img src="/campingutte/resource/images/horse.jpg">
		</div>
		<div class="theme-sub">
		<a href="#">액티브한 즐길거리가 가득한 캠핑장</a>
		</div>
	</div>
	<div class="theme">
		<div class="theme-img">
			<img src="/campingutte/resource/images/children.jpg">
		</div>
		<div class="theme-sub">
		<a href="#">가족과 함께 떠나기 좋은 캠핑장</a>
		</div>
	</div>
	<div class="theme">
		<div class="theme-img">
			<img src="/campingutte/resource/images/doggy.jpg">
		</div>
		<div class="theme-sub">
		<a href="#">반려동물과 함께 즐기는 캠핑장</a>
		</div>
	</div>
</div>


<div class="preview">
	<div class="pre-review">
		<p> 캠핑리뷰 </p>
	</div>
	<div class="pre-comm">
		<p> 커뮤니티 </p>
	</div>
	<div class="pre-svc">
		<p> 고객센터 </p>
	</div>
</div>

<footer>
	<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
</footer>
<jsp:include page="/WEB-INF/campingutte/layout/staticFooter.jsp"/>
</div>
</body>
</html>