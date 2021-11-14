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
        <title>캠핑장소개</title>
        <!-- Favicon-->
        <link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
        <!-- Bootstrap icons-->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
        <link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
		<link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />
        <!-- Core theme CSS (includes Bootstrap)-->
        <link href="css/styles.css" rel="stylesheet" />
     <style type="text/css">
	  .room-list {
		  	display: flex;
			width: 800px;
	  		margin: 10px 0;
			border: 1px solid black;
			border-radius: 10px;
	  }
	  
	  .room-list > img {
			width: 300px;
			height: 200px;
		}
		
		.room-list1 {
			display: flex;
			flex-direction: column;
			justify-content: center;
			margin-left: 20px;
		}
		
  </style>
  
  <script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>	
  <script type="text/javascript">
    function ajaxFun(url, method, query, dataType, fn) {
    	$.ajax({
    		type:method,
    		url:url,
    		data:query,
    		dataType:dataType,
    		success:function(data) {
    			fn(data);
    		},
    		beforeSend:function(jqXHR) {
    			jqXHR.setRequestHeader("AJAX", true);
    		},
    		error:function(jqXHR) {
    			if(jqXHR.status === 403) {
    				login();
    				return false;
    			} else if(jqXHR.status === 405) {
    				alert("접근을 허용하지 않습니다.");
    				return false;
    			}
    	    	
    			console.log(jqXHR.responseText);
    		}
    	});
    }
    

    $(function(){
    	listPage(1);
    });

    function listPage(page) {
    	var url = "${pageContext.request.contextPath}/book/roomList.do";
    	var query = "campNo=${dto.campNo}&roomPageNo="+page;
    	var selector = "#roomList";
    	
    	var fn = function(data){
    		$(selector).html(data);
    	};
    	ajaxFun(url, "post", query, "html", fn);
    }
    
    function listReview(page) {
    	var url = "${pageContext.request.contextPath}/review/listCampReview.do";
    	var query = "campNo=${dto.campNo}&rPageNo="+page;
    	var selector = "#listCampReview";
    	
    	var fn = function(data){
    		$(selector).html(data);
    	};
    	ajaxFun(url, "post", query, "html", fn);
    	
    }
    

    // 리뷰삭제
    $(function(){
    	$("body").on("click", ".deleteReview", function() {
    		if(! confirm("게시글을 삭제 하시겠습니까 ? ")) {
    			return false;
    		}
    		var reviewNum = $(this).attr("data-reviewNo");
    		var pageNo = $(this).attr("data-pageNo");
    		
    		var url = "${pageContext.request.contextPath}/review/deleteReview.do";
    		var query = "reviewNo=" + reviewNo;
    		
    		var fn = function(data) {
    			listPage(rpageNo);
    		};
    		
    		ajaxFun(url, "post", query, "json", fn);
    		
    	});
    });
    
    
   </script>
    
    </head>
    <body class="d-flex flex-column">
        <main class="flex-shrink-0">
            <!-- header(메뉴바) 부분 -->
           	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
            <!-- Page Content-->
            <section class="py-5">
                <div class="container px-5 my-5">
                    <div class="row gx-5">
                    <!--  
                        <div class="col-lg-3">
                            <div class="d-flex align-items-center mt-lg-5 mb-4">
                                <img class="img-fluid rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." />
                                <div class="ms-3">
                                    <div class="fw-bold">Valerie Luna</div>
                                    <div class="text-muted">News, Business</div>
                                </div>
                            </div>
                        </div>
                     -->
                        <div class="col-lg-9">
                            <!-- Post content-->
                            <article>
                                <!-- Post header-->
                                <header class="mb-4">
                                    <!-- Post title-->
                                    <h1 class="fw-bolder mb-1">캠핑장 이름 ${dto.campName}</h1>
                                    <!-- Post meta content-->
                                    <div class="text-muted fst-italic mb-2">캠핑장 주소 ${dto.campAddr1} ${dto.campAddr2}</div>
                                    <!-- Post categories-->
                                    <!-- 
                                    <a class="badge bg-secondary text-decoration-none link-light" href="#!">Web Design</a>
                                    <a class="badge bg-secondary text-decoration-none link-light" href="#!">Freebies</a>
                                	-->
                                </header>
                                <!-- Preview image figure-->
                                <figure class="mb-4"><img class="img-fluid rounded" src="https://dummyimage.com/900x400/ced4da/6c757d.jpg" alt="..." /></figure>
                                <!-- Post content-->
                                <section class="mb-5">
                                    <p class="fs-5 mb-4">캠핑장 소개 ${dto.campDetail}</p>
                                    <p class="fs-5 mb-4">부대시설 : ${dto.campAdd}</p>
                                    <p class="fs-5 mb-4">캠핑장 전화번호 : ${dto.campTel}</p>
                                    <!-- 
                                    <h2 class="fw-bolder mb-4 mt-5">I have odd cosmic thoughts every day</h2>
                                    <p class="fs-5 mb-4">For me, the most fascinating interface is Twitter. I have odd cosmic thoughts every day and I realized I could hold them to myself or share them with people who might be interested.</p>
                                    <p class="fs-5 mb-4">Venus has a runaway greenhouse effect. I kind of want to know what happened there because we're twirling knobs here on Earth without knowing the consequences of it. Mars once had running water. It's bone dry today. Something bad happened there as well.</p>
                                	 -->
                                </section>
                            </article>
                            
                  
                            <!-- Comments section-->
                            
                         
                        </div>
                    </div>
                    <div id="roomList"></div>
                    
                    <div id="listCampReview"></div>
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
