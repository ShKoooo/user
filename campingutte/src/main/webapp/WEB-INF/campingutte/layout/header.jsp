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
            
            
            
          
	            <!-- Navigation-->
	      	 
	            <nav class="navbar navbar-expand-lg navbar-dark">
	                <div class="container px-5">
	                	<img class="logoicon" src="/campingutte/resource/images/tent_icon_color.png" style="width: 60px; height: 60px;">
	                    <a class="navbar-brand" href="index.html">캠핑어때</a>
	                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation"><span class="navbar-toggler-icon"></span></button>
	                    
	                    
	                    <div class="collapse navbar-collapse" id="navbarSupportedContent">
	                        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
	                            <li class="nav-item"><a class="nav-link" href="index.html">Home</a></li>
	                            <li class="nav-item"><a class="nav-link" href="about.html">About</a></li>
	                            <li class="nav-item"><a class="nav-link" href="contact.html">Contact</a></li>
	                            <li class="nav-item"><a class="nav-link" href="pricing.html">Pricing</a></li>
	                            <li class="nav-item"><a class="nav-link" href="faq.html">FAQ</a></li>
	                            <li class="nav-item dropdown">
	                                <a class="nav-link dropdown-toggle" id="navbarDropdownBlog" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Blog</a>
	                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownBlog">
	                                    <li><a class="dropdown-item" href="blog-home.html">Blog Home</a></li>
	                                    <li><a class="dropdown-item" href="blog-post.html">Blog Post</a></li>
	                                </ul>
	                            </li>
	                            <li class="nav-item dropdown">
	                                <a class="nav-link dropdown-toggle" id="navbarDropdownPortfolio" href="#" role="button" data-bs-toggle="dropdown" aria-expanded="false">Portfolio</a>
	                                <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdownPortfolio">
	                                    <li><a class="dropdown-item" href="portfolio-overview.html">Portfolio Overview</a></li>
	                                    <li><a class="dropdown-item" href="portfolio-item.html">Portfolio Item</a></li>
	                                </ul>
	                            </li>
	                        </ul>
	                    </div>
	                </div>
	            </nav>
	         
</header>	           