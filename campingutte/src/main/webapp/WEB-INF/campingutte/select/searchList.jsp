<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.8.8/i18n/jquery.ui.datepicker-ko.js"></script>
<script src="${pageContext.request.contextPath}/resource/js/scripts.js"></script>
<!-- Favicon-->
<link rel="icon" type="image/x-icon" href="assets/favicon.ico" />
<!-- Bootstrap icons-->
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css" rel="stylesheet" />
<link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/smoothness/jquery-ui.css" type="text/css"/>
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">

<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
<meta name="description" content="" />
<meta name="author" content="" />

<script type="text/javascript">
$(function(){
	$("#checkIn").datepicker({
		showMonthAfterYear:true,
		defaultDate:"2021-11-02",
		// minDate:"2021-11-01", maxDate:"2021-11-10"
		minDate:0, maxDate:"+5D"
	});
});

$(function(){
	$("#checkOut").datepicker({
		showMonthAfterYear:true,
		defaultDate:"2021-11-02",
		// minDate:"2021-11-01", maxDate:"2021-11-10"
		minDate:0, maxDate:"+5D"
	});
});
</script>
<style type="text/css">

.col-lg-3 > input {
 margin-bottom: 12px;
}

</style>
<title>검색리스트</title>

</head>
<body class="d-flex flex-column">
<!-- header(메뉴바) 부분 -->
<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
	<main class="flex-shrink-0">
		
		<!-- Page Content-->
		<section class="py-5">
		    <div class="container px-5 my-5">
		        <div class="row gx-5">
		            <div class="col-lg-3">
		            <input type="text" id="checkIn" readonly="readonly" placeholder="체크인" class="form-control font-size-h5 font-weight-bolder">
		            <input type="text" id="checkOut" readonly="readonly" placeholder="체크아웃" class="form-control font-size-h5 font-weight-bolder">
		            <input type="text" placeholder="지역" class="form-control font-size-h5 font-weight-bolder">
		            <input type="text" placeholder="캠핑장명" class="form-control font-size-h5 font-weight-bolder">
		                <div class="d-flex align-items-center mt-lg-5 mb-4">
		                    <img class="img-fluid rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." />
		                    <div class="ms-3">
		                        <div class="fw-bold">Valerie Luna</div>
		                        <div class="text-muted">News, Business</div>
		                    </div>
		                </div>
		            </div>
		            <div class="col-lg-9">
		                <!-- Post content-->
		                <article>
		                    <!-- Post header-->
		                    <header class="mb-4">
		                        <!-- Post title-->
		                        <h1 class="fw-bolder mb-1">Welcome to Blog Post!</h1>
		                        <!-- Post meta content-->
		                        <div class="text-muted fst-italic mb-2">January 1, 2021</div>
		                        <!-- Post categories-->
		                        <a class="badge bg-secondary text-decoration-none link-light" href="#!">Web Design</a>
		                        <a class="badge bg-secondary text-decoration-none link-light" href="#!">Freebies</a>
		                    </header>
		                    <!-- Preview image figure-->
		                    <figure class="mb-4"><img class="img-fluid rounded" src="https://dummyimage.com/900x400/ced4da/6c757d.jpg" alt="..." /></figure>
		                    <!-- Post content-->
		                    <section class="mb-5">
		                        <p class="fs-5 mb-4">Science is an enterprise that should be cherished as an activity of the free human mind. Because it transforms who we are, how we live, and it gives us an understanding of our place in the universe.</p>
		                        <p class="fs-5 mb-4">The universe is large and old, and the ingredients for life as we know it are everywhere, so there's no reason to think that Earth would be unique in that regard. Whether of not the life became intelligent is a different question, and we'll see if we find that.</p>
		                        <p class="fs-5 mb-4">If you get asteroids about a kilometer in size, those are large enough and carry enough energy into our system to disrupt transportation, communication, the food chains, and that can be a really bad day on Earth.</p>
		                        <h2 class="fw-bolder mb-4 mt-5">I have odd cosmic thoughts every day</h2>
		                        <p class="fs-5 mb-4">For me, the most fascinating interface is Twitter. I have odd cosmic thoughts every day and I realized I could hold them to myself or share them with people who might be interested.</p>
		                        <p class="fs-5 mb-4">Venus has a runaway greenhouse effect. I kind of want to know what happened there because we're twirling knobs here on Earth without knowing the consequences of it. Mars once had running water. It's bone dry today. Something bad happened there as well.</p>
		                    </section>
		                </article>
		                <!-- Comments section-->
		                <section>
		                    <div class="card bg-light">
		                        <div class="card-body">
		                            <!-- Comment form-->
		                            <form class="mb-4"><textarea class="form-control" rows="3" placeholder="Join the discussion and leave a comment!"></textarea></form>
		                            <!-- Comment with nested comments-->
		                            <div class="d-flex mb-4">
		                                <!-- Parent comment-->
		                                <div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." /></div>
		                                <div class="ms-3">
		                                    <div class="fw-bold">Commenter Name</div>
		                                    If you're going to lead a space frontier, it has to be government; it'll never be private enterprise. Because the space frontier is dangerous, and it's expensive, and it has unquantified risks.
		                                    <!-- Child comment 1-->
		                                    <div class="d-flex mt-4">
		                                        <div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." /></div>
		                                        <div class="ms-3">
		                                            <div class="fw-bold">Commenter Name</div>
		                                            And under those conditions, you cannot establish a capital-market evaluation of that enterprise. You can't get investors.
		                                        </div>
		                                    </div>
		                                    <!-- Child comment 2-->
		                                    <div class="d-flex mt-4">
		                                        <div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." /></div>
		                                        <div class="ms-3">
		                                            <div class="fw-bold">Commenter Name</div>
		                                            When you put money directly to a problem, it makes a good headline.
		                                        </div>
		                                    </div>
		                                </div>
		                            </div>
		                            <!-- Single comment-->
		                            <div class="d-flex">
		                                <div class="flex-shrink-0"><img class="rounded-circle" src="https://dummyimage.com/50x50/ced4da/6c757d.jpg" alt="..." /></div>
		                                <div class="ms-3">
		                                    <div class="fw-bold">Commenter Name</div>
		                                    When I look at the universe and all the ways the universe wants to kill us, I find it hard to reconcile that with statements of beneficence.
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </section>
		            </div>
		        </div>
		    </div>
		</section>
	</main>
        <!-- footer 부분 -->
		<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
        <!-- Bootstrap core JS-->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
        <!-- Core theme JS-->
        <script src="js/scripts.js"></script>
         
    </body>
</html>