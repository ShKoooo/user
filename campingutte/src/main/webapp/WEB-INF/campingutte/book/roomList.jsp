<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="roomList">
	<span>객실 ${roomCount}개</span>
	<span>[목록, ${pageNo}/${roomTotal_page} 페이지]</span>
</div>

<div class="list-container">
	<c:forEach var="dto" items="${listRoom}">
	<div class="room-list">
			<img  src="tvshow.jpg">
			<div class="room-list1">
			<p>객실이름 : ${dto.roomName}</p>
			<p>기준인원 : ${dto.stdPers} / 최대인원 : ${dto.maxPers}</p>
			<p>1박당 가격 : ${dto.stdPrice}</p>
			</div>
		</div>
		</c:forEach>	
</div>

<div class="page-box">
	${roomPaging}
</div>
