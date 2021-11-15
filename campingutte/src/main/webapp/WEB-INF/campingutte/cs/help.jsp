<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 캠핑어때 고객센터 </title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />

<style type="text/css">
.body {
	margin: 60px;
	vertical-align: center;
	text-align: center;
}

.title {
	font-size: 28px;
	vertical-align: center;
	text-align: center;
}


.content {
	font-size: 22px;
	vertical-align: center;	
	text-align: center;
}

.content-grey {
	background: #EAEAEA;
}



</style>


</head>
<body>



<header>
	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
</header>


<main>
	<div class="body"> 
		<div class="title"> 
			<h3> 고객센터 안내 </h3>
		</div>
		<div style="font-size: 15px; color: #BDBDBD;">
			<p>
				C U S T O M E R &nbsp;&nbsp; C E N T E R
			</p>
		</div>
		<div class="content">
				<img src="/campingutte/resource/images/cshelp.png">
		</div>
		<div class="content-grey">
				<p style="font-size: 50px; color: orange;">
					1 5 6 6 - 1 2 3 4
				</p>
				<p style="font-size: 20px; color: orange; margin-top: -77px;">
					(ARS)
				</p>
				<p style="font-size: 17px; color: #8C8C8C; margin-top: -30px;">
					업무시간 : AM 09:00 - PM 18:00 / 토요일, 일요일 휴무
				</p>
				<p style="font-size: 17px; color: #8C8C8C; margin-top: -30px;">
					캡쳐시간 : AM 11:00 / PM 14:00
				</p>
		</div>
	</div>
<br>
<br>

</main>




<footer>
	<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
</footer>


</body>
</html>