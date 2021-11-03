<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="home">
	<div class="header-top">
	
		<div class="header-left">
			<h1 class="logo"><a href="#">캠핑어때</a></h1>
		</div>
		
		<div class="header-right">
			<div style="text-align: right;">
				<a href="#">Home</a>
				<c:if test="">
					<a href="#">회원가입</a>
					<a href="#">로그인</a>
				</c:if>
				<c:if test="">
					<a href="#">로그아웃</a>
					<a href="#">정보수정</a>
				</c:if>
				
			</div>
		</div>
	</div>
	
	<div class="menu">
		<ul class="nav">
			<li>
				<a href="#">메인화면</a>
			</li>
			<li>
				<a href="#">지역별 검색</a>
			</li>
			<li>
				<a href="#">커뮤니티</a>
				<ul>
					<li><a href="#">추천명소</a></li>
					<li><a href="#">캠핑장터</a></li>
					<li><a href="#">자유게시판</a></li>
				</ul>
			</li>
			<li>
				<a href="#">고객센터</a>
				<ul>
					<li><a href="#">공지사항</a></li>
					<li><a href="#">FAQ</a></li>
					<li><a href="#">1:1상담</a></li>
				</ul>
			</li>					
		<c:if test="">
			<li><a href="#">마이페이지</a>
				<ul>
					<li><a href="#">예약확인</a></li>
					<li><a href="#">결제내역</a></li>
					<li><a href="#">정보수정</a></li>
				</ul>
			</li>
		</c:if>
		
		</ul>
	</div>

</div>
