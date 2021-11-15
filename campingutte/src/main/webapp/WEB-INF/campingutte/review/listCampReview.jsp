<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class='reply-info'>
	<span class='reply-count'>리뷰 ${reviewCount}개</span>
	<span>[목록, ${rPageNo}/${rTotal_page} 페이지]</span>
</div>

<table class='table reply-list'>
	<c:forEach var="dto" items="${listReview}">
		<tr class='list-header'>
			<td width='50%'>
				<span class='bold'>${dto.memberId}</span>
			</td>
			<td width='50%' align='right'>
				<span>${dto.reviewDate}</span> |
				<c:choose>
					<c:when test="${sessionScope.member.memberId == dto.memberId || sessionScope.member.memberId == 'admin'}">
						<span class='deleteReply' data-replyNum='${dto.reviewNo}'>삭제</span>
					</c:when>
					<c:otherwise>
						<span class="notifyReply">신고</span>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td colspan='2'>
				<b>별점</b>&nbsp;&nbsp;
				<c:if test="${dto.reviewStar=='1'}">★</c:if>
				<c:if test="${dto.reviewStar=='2'}">★★</c:if>
				<c:if test="${dto.reviewStar=='3'}">★★★</c:if>
				<c:if test="${dto.reviewStar=='4'}">★★★★</c:if>
				<c:if test="${dto.reviewStar=='5'}">★★★★★</c:if>
			</td>
		</tr>
		<tr>	
			<td colspan='2' valign='top'>${dto.reviewComment}</td>
		</tr>
	</c:forEach>	
</table>

<div class="page-box">
	${rPaging}
</div>	