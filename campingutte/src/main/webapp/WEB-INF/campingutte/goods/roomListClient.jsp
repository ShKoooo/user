<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
    <head>
     <link rel="stylesheet" href="resource/css/styles.css">
      <script src="resource/js/scripts.js"></script>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <meta name="description" content="" />
        <meta name="author" content="" />
        <title>객실목록</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
  
    
    </head>
    <body class="d-flex flex-column">
        <main class="flex-shrink-0">
            <!-- header(메뉴바) 부분 -->
           	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
            <!-- Page Content-->
            <section class="py-5">
            
                <div class="container px-5">
                    <h1 class="fw-bolder fs-5 mb-4">객실목록</h1>
                    <div class="card border-0 shadow rounded-3 overflow-hidden">
                        <div class="card-body p-0">
                            <div class="row gx-0">
                                <div class="col-lg-6 col-xl-7"><div class="bg-featured-blog" style="background-image: url('https://dummyimage.com/700x350/343a40/6c757d')"></div></div>
                                <div class="col-lg-6 col-xl-5 py-lg-5">
                                    <div class="p-4 p-md-5">
                                        <div class="badge bg-primary bg-gradient rounded-pill mb-2">News</div>
                                        <div class="h2 fw-bolder">객실이름 : </div>
                                        <p>기준인원 : ${dto.stdPers}/최대인원 : ${dto.maxPers}</p>
                                        <p>1박 기본 요금 : ${dto.stdPrice}</p>
                                        <a class="stretched-link text-decoration-none" href="#!">
                                            예약하기
                                            <i class="bi bi-arrow-right"></i>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
    
            </section>
      
        </main>
     
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
         <!-- footer 부분 -->
		<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>      
    </body>
</html>
