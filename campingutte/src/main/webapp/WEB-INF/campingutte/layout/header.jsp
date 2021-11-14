<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<link rel="stylesheet" href="resource/css/styles.css">
<script src="resource/js/scripts.js"></script>

<link rel="stylesheet" href="resource/css/header_footer-layout.css">

<style type="text/css">
	/*
        .login a {
        text-decoration: none;
        color: #BDBDBD;
        font-size: 13px;
        }
        
        .login {
        	margin-top: -15px;
    		padding-right: 18px;
        }
     */   
</style>

<header class="header-bg header">
            
            <!-- 로그인 바 -->
            
            	<div class="login">
					<div style="text-align: right;">
						<c:if test="${not empty sessionScope.member}">
							<span style="color:cyan;">${sessionScope.member.memberName} &nbsp;님</span>
							&nbsp;&nbsp;
						</c:if>	
								
						<a href="${pageContext.request.contextPath}/">Home&nbsp;|&nbsp;</a>
						<c:if test="${empty sessionScope.member}">
							<a href="${pageContext.request.contextPath}/member/signup.do">회원가입</a>
							<span style="color: white;">&nbsp;|&nbsp;</span>
							<a href="${pageContext.request.contextPath}/member/login.do">로그인</a>
						</c:if>
						<c:if test="${not empty sessionScope.member}">					
							<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
							<span style="color: white;">&nbsp;|&nbsp;</span>
							<a href="${pageContext.request.contextPath}/member/update.do?mode=update">정보수정</a>
						</c:if>
								
					</div>
				</div>
            
            
            
          
	            <!-- Navigation-->
	      	 
	            <nav class="navbar navbar-expand-lg navbar-dark">
	                <div class="container px-5">
	                	<img class="logoicon" src="/campingutte/resource/images/tent_icon_color.png" style="width: 60px; height: 60px;">
	                    <a class="navbar-brand" href="${pageContext.request.contextPath}/">캠핑어때</a>
	                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
	                    
	                    
	                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
	                        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
	                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/">Home</a></li>
	                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/book/campList.do">지역별 검색</a></li>
	                            <li class="nav-item"><a class="nav-link" href="contact.html">공지사항</a></li>
	                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/book/campList.do">예약하기</a></li>
	                            
	                            <c:if test="${sessionScope.member.memberId=='admin'}">
	                            <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/admin/adminmain.do">관리페이지</a></li>
	                            </c:if>
	               
	                            <li class="nav-item"><a class="nav-link" href="pricing.html">마이페이지</a></li>
	                            
	                            
	                            <li class="nav-item dropdown">
	                                <a class="nav-link dropdown-toggle" id="navbarDropdownBlog" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">커뮤니티</a>
	                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownBlog">
	                                    <li><a class="dropdown-item" href="blog-home.html">추천명소</a></li>
	                                    <li><a class="dropdown-item" href="blog-post.html">캠핑장터</a></li>
	                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/community/list.do">자유게시판</a></li>
	                                </ul>
	                            </li>
	                            <li class="nav-item dropdown">
	                                <a class="nav-link dropdown-toggle" id="navbarDropdownPortfolio" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">고객센터</a>
	                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownPortfolio">
	                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/cs/faq.do">자주하는 질문</a></li>
	                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/cs/list.do">1:1 상담</a></li>
	                                    <li><a class="dropdown-item" href="${pageContext.request.contextPath}/cs/help.do">고객센터 안내</a></li>
	                                </ul>
	                            </li>
	                        </ul>
	                    </div>
	                </div>
	            </nav>
	         
</header>	           