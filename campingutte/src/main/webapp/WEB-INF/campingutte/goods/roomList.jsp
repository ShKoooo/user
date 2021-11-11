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
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.8.8/i18n/jquery.ui.datepicker-ko.js"></script>
<style type="text/css">
.room-list > img {
	width: 300px;
	height: 200px;
}

.room-list {
	display: flex;
	width: 800px;
	border: 1px solid black;
	border-radius: 10px;
}

.room-list1 {
	display: flex;
	flex-direction: column;
	justify-content: center;
	margin-left: 20px;
}

.roomDetail {
	width: 800px;
	min-height: 600px;
	margin: 10px 0;
	display: flex;
	border: 1px solid blue;
	border-radius: 10px;
	flex-direction: column;
	align-items: center;
}

.roomDetail > p > img {
	width:600px;
	height: 300px;
}

</style>


<script type="text/javascript">
$(function(){
	$("#accordion").accordion({active:false, collapsible:true});
});

<%-- 슬라이더 하고싶은데 할수있으려나...
$(function(){
	$(".slider").slider({
        speed : 500
        ,delay: 2500
        /* ,paginationType : 'thumbnails' */ // 아래부분에 작은 이미지 출력
    });
	
	$(".slider li").click(function(){
		var num = $(this).attr("data-num");
		location.href="${articleUrl}&num="+num;
	});
});
--%>

</script>

</head>
<body>

	<div class="list-container">
		<div id="accordion">
		<div class="room-list">
			<img  src="tvshow.jpg">
			<div class="room-list1">
			<p>객실이름 : ${dto.roomName}</p>
			<p>기준인원 : ${dto.stdPers} / 최대인원 : ${dto.maxPers}</p>
			<p>1박당 가격 : ${dto.stdPrice}</p>
			</div>
		</div>
		<div class="roomDetail">
			<p>객실상세</p>
			<p><img alt="" src=""></p>
			<p>객실이름 : ${dto.roomName}</p>
			<p>기준인원 : ${dto.stdPers}</p>
			<p>최대인원 : ${dto.maxPers}</p>
			<p>초과인원 1박당 요금: ${dto.extraPrice}</p>
			<p>설명 : ${dto.roomDetail}</p>
			<p>객실 이용 날짜 : </p>
			<button type="button" class="btn" click="sendOk()">예약하기</button>
		</div>
		</div>
		<div>
		
		</div>
	</div>


</body>
</html>