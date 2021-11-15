<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<div class="roomList">
	<span>객실 ${roomCount}개</span>
	<span>[목록, ${pageNo}/${roomTotal_page} 페이지]</span>
</div>

<div class="card mb-3" style="max-width: 800px;">
	<c:forEach var="dto" items="${listRoom}">
		<div class="row g-0">
           <div class="col-md-4">
			<c:forEach var="vo" items="${dto.images}" varStatus="status">
				<c:if test="${status.index == 0}">
                  	<img class="img-fluid rounded-start" src="${pageContext.request.contextPath}/uploads/admin/${vo.imgName}"/>
        		</c:if>
           </c:forEach>
        </div>
           <div class="col-md-8">
			<div class="card-body">
				<h5 class="card-title">${dto.roomName}</h5>
				<p class="card-text">기준인원 : ${dto.stdPers} / 최대인원 : ${dto.maxPers}</p>
				<p class="card-text">1박당 가격 : ${dto.stdPrice}</p>
				<button type="button" class="btn btn-warning" onclick="location.href='${pageContext.request.contextPath}/book/book.do?roomNo=${dto.roomNo}';">예약하기</button>
			</div>
	    	</div>
		</div>
	</c:forEach>	
</div>

<!-- 
<div class="list-container">
	<c:forEach var="dto" items="${listRoom}">
		<div class="room-list">
			<c:forEach var="vo" items="${dto.images}" varStatus="status">
				<c:if test="${status.index == 0}">
                  	<img class="roomImg" src="${pageContext.request.contextPath}/uploads/admin/${vo.imgName}"/>
        		</c:if>
           </c:forEach>
			<div class="room-list1">
				<p>${dto.roomName}</p>
				<p>기준인원 : ${dto.stdPers} / 최대인원 : ${dto.maxPers}</p>
				<p>1박당 가격 : ${dto.stdPrice}</p>
				<button type="button" class="bookbtn" onclick="location.href='${pageContext.request.contextPath}/book/book.do?roomNo=${dto.roomNo}';">예약하기</button>
			</div>
		</div>
	</c:forEach>	
</div>
 -->

<div class="page-box">
	${roomPaging}
</div>
