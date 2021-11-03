<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="home">
	<div class="header-top">
	
		<div class="header-left">
			<img class="logoicon" src="/campingutte/resource/images/tent_icon_color.png">
			<h1 class="logo font-jalnan"><a href="#">캠핑어때</a></h1>
		</div>
		
		<div class="header-right font-jalnan">
			<div style="text-align: right;">
				<c:if test="${not empty sessionScope.member}">
					<span style="color:cyan;">${sessionScope.member.memberName} &nbsp;님</span>
					&nbsp;&nbsp;
				</c:if>	
				
				<a href="#">Home&nbsp;|&nbsp;</a>
				<c:if test="${empty sessionScope.member}">
					<a href="#">회원가입 (비활성화)</a>
					<span style="color: white;">&nbsp;|&nbsp;</span>
					<a href="${pageContext.request.contextPath}/member/login.do">로그인</a>
				</c:if>
				<c:if test="${not empty sessionScope.member}">					
					<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
					<span style="color: white;">&nbsp;|&nbsp;</span>
					<a href="#">정보수정 (비활성화)</a>
				</c:if>
				
			</div>
		</div>
	</div>
	
	<div class="menu font-jalnan">
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