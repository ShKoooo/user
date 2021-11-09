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
<style type="text/css">
.body-container {
    margin: 0 auto 15px;
    width: 800px;
    min-height: 450px;
}
.body-container:before, .body-container:after{
	content: "";
	display: block;
	clear: both;	
}

.body-title {
    color: #424951;
    padding-top: 25px;
    padding-bottom: 5px;
    margin: 0 0 25px 0;
    border-bottom: 1px solid #ddd;
}

.body-title h3 {
    font-size: 23px;
    min-width: 300px;
    font-family:"Malgun Gothic", "맑은 고딕", NanumGothic, 나눔고딕, 돋움, sans-serif;
    font-weight: bold;
    margin: 0 0 -5px 0;
    padding-bottom: 5px;
    display: inline-block;
    border-bottom: 3px solid #424951;
}

.table {
	width: 100%;
	border-spacing: 0;
	border-collapse: collapse;
}
.table th, .table td {
	padding: 10px 0;
}

.table-border tr {
	border-bottom: 1px solid #ccc; 
}
.table-border tr:first-child {
	border-top: 2px solid #ccc;
}

.img-box {
	max-width: 700px;
	padding: 5px;
	box-sizing: border-box;
	border: 1px solid #ccc;
	display: flex; /* 자손요소를 flexbox로 변경 */
	flex-direction: row; /* 정방향 수평나열 */
	flex-wrap: nowrap;
	overflow-x: auto;
}

.img-box img {
	width: 100px; height: 100px;
	margin-right: 5px;
	flex: 0 0 auto;
	cursor: pointer;
}

.photo-layout img {
	width: 570px; height: 450px;
}

.btn {
	color: #333;
	border: 1px solid #333;
	background-color: #fff;
	padding: 4px 10px;
	border-radius: 4px;
	font-weight: 500;
	cursor:pointer;
	font-size: 14px;
	font-family: "맑은 고딕", 나눔고딕, 돋움, sans-serif;
	vertical-align: baseline;
}
.btn:hover, .btn:active, .btn:focus {
	background-color: #e6e6e6;
	border-color: #adadad;
	color:#333;
}
.btn[disabled], fieldset[disabled] .btn {
	pointer-events: none;
	cursor: not-allowed;
	filter: alpha(opacity=65);
	-webkit-box-shadow: none;
	box-shadow: none;
	opacity: .65;
}
</style>
<script type="text/javascript">
<c:if test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
function deleteBoard() {
    if(confirm("게시글을 삭제 하시겠습니까 ? ")) {
	    var query = "num=${dto.num}&page=${page}";
	    var url = "${pageContext.request.contextPath}/goods/roomDelete.do" + query;
    	location.href = url;
    }
}
</c:if>
</script>
</head>
<body>

<main>
	<div class="body-container" style="width: 700px;">
		<div class="body-title">
			<h3><i class="fas fa-chalkboard"></i> 객실 상세 </h3>
		</div>
        
		<table class="table table-border table-article">
			<tr>
				<td align="center">
					객실이름
				</td>
			</tr>
			
			<tr>
				<td>
					기준인원 : 2
				</td>
			</tr>
			<tr>
				<td>
					최대인원 : 4
				</td>
			</tr>
			<tr>
				<td>
					1박당 기본요금 :
				</td>
			</tr>
			<tr>
				<td>
					초과 인원요금 :
				</td>
			</tr>
			<tr>
				<td valign="top" height="200">
					이 객실은 바로 앞 바다~뷰~
				</td>
			</tr>
			<tr>
				<td height="110">
					<div class="img-box">
						<c:forEach var="vo" items="${listFile}">
							<img src="${pageContext.request.contextPath}"
								onclick="imageViewer('${pageContext.request.contextPath}');">
						</c:forEach>
					</div>
				</td>	
			</tr>
			<!-- 
			<tr>
				<td colspan="2">
					이전글 :
					<c:if test="${not empty preReadDto}">
						<a href="${pageContext.request.contextPath}/bbs/article.do?${query}&num=${preReadDto.num}">${preReadDto.subject}</a>
					</c:if>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					다음글 :
					<c:if test="${not empty nextReadDto}">
						<a href="${pageContext.request.contextPath}/bbs/article.do?${query}&num=${nextReadDto.num}">${nextReadDto.subject}</a>
					</c:if>
				</td>
			</tr>
			 -->
		</table>
		
		<table class="table">
			<tr>
				<td width="50%">
					<c:choose>
						<c:when test="${sessionScope.member.userId==dto.userId}">
							<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/goods/roomUpdate.do?num=${dto.num}&page=${page}';">수정</button>
						</c:when>
						<c:otherwise>
							<button type="button" class="btn" disabled="disabled">수정</button>
						</c:otherwise>
					</c:choose>
			    	
					<c:choose>
			    		<c:when test="${sessionScope.member.userId==dto.userId || sessionScope.member.userId=='admin'}">
			    			<button type="button" class="btn" onclick="deleteBoard();">삭제</button>
			    		</c:when>
			    		<c:otherwise>
			    			<button type="button" class="btn" disabled="disabled">삭제</button>
			    		</c:otherwise>
			    	</c:choose>
				</td>
				<td align="right">
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/goods/roomList.do?${query}';">리스트</button>
				</td>
			</tr>
		</table>
	</div>
</main>
</body>
</html>