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
<title>Insert title here</title>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<style type="text/css">
.table-list tr:first-child{
	background: #eee;
}
.table-list th, .table-list td {
	text-align: center;
}
.table-list .left {
	text-align: left; padding-left: 5px; 
}

.table-list .num {
	width: 160px; color: #787878;
}
.table-list .subject {
	color: #787878;
}
.table-list .name {
	width: 160px; color: #787878;
}
.table-list .date {
	width: 220px; color: #787878;
}
.table-list .hit {
	width: 70px; color: #787878;
}

.setCenter {
	align-content: center;
}


.body-container {
    margin: 15px auto 15px;
    width: 800px;
    min-height: 450px;
}

/* button */

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


/* review */

.reply {
	clear: both; padding: 20px 0 10px;
}
.reply .bold {
	font-weight: 600;
}

.reply .form-header {
	padding-bottom: 7px;
}
.reply-form  td {
	padding: 2px 0 2px;
}
.reply-form textarea {
	width: 100%; height: 75px;
}
.reply-form button {
	padding: 8px 25px;
}

.reply .reply-info {
	padding-top: 25px; padding-bottom: 7px;
}
.reply .reply-info  .reply-count {
	color: #3EA9CD; font-weight: 700;
}

.reply .reply-list tr td {
	padding: 7px 5px;
}
.reply .reply-list .bold {
	font-weight: 600;
}

.reply .deleteReply, .reply .deleteReplyAnswer {
	cursor: pointer;
}
.reply .notifyReply {
	cursor: pointer;
}

.reply-list .list-header {
	border: 1px solid #ccc; background: #eee;
}
.reply-list td {
	padding-left: 7px; padding-right: 7px;
}

</style>

<script type="text/javascript">
<c:if test="${sessionScope.member.memberId==dto.memberId || sessionScope.member.memberId=='admin'}">
	function deleteBoard() {
	    if(confirm("예약을 취소 하시겠습니까 ? ")) {
		    var query = "bookNo=${dto.bookNo}&${query}";
		    var url = "${pageContext.request.contextPath}/book/bookDelete.do?" + query;
	    	location.href = url;
	    }
	}
</c:if>
</script>


<script type="text/javascript">
// AJAX

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

// 리뷰 리스트
$(function()) {
	listPage(1);
}

function listPage(page) {
	var url = "${pageContext.request.contextPath}/review/listReview.do";
	var query = "bookNo=${dto.bookNo}&pageNo="+page;
	var selector = "#listReply";
	
	var fn = function(data) {
		$(selector).html(data);
	};
	
	ajaxFun(url,"get",query,"html",fn);
}

// TODO

//리뷰 삭제
$(function() {
	$("body").on("click",".deleteReply", function() {
		if (!confirm("리뷰를 삭제하시겠습니까?")) {
			return false;
		}
		
		var replyNum = $(this).attr("data-replyNum");
		var pageNo = $(this).attr("data-pageNo");
		
		var url = "${pageContext.request.contextPath}/review/deleteReview.do"
		var query = "reviewNo="+reviewNo;
		
		var fn = function(data) {
			listPage(pageNo);
		};
		
		ajaxFun(url,"post",query,"json",fn);
	});
});

// 리뷰 등록
$(function() {
	$(".btnSendReply").click(function() {
		var bookNoR = "${dto.bookNo}";
		var $tb = $(this).closest("table");
		var content = $tb.find("textarea").val().trim();
		var star = $("#star option:selected").val();
		
		console.log(bookNoR);
		console.log(content);
		console.log(star);
		
		if (! content) {
			$tb.find("textarea").focus();
			return false;
		}
		content = encodeURIComponent(content);
		
		var url = "${pageContext.request.contextPath}/review/insertReview.do";
		var query = "bookNoR="+bookNoR+"&comment="+content+"&star="+star;
		
		console.log(url);
		console.log(query);
		
		var fn = function(data) {
			var state = data.state;

			$tb.find("textarea").val("");
			
			if (state === "true") {
				listPage(1); // 1페이지 불러오기 (새로고침?)
			} else {
				alert("리뷰 등록 실패..");
			}
		};
		
		ajaxFun(url,"post",query,"json",fn);
	});
});

</script>


<jsp:include page="/WEB-INF/campingutte/layout/staticHeader.jsp"/>
<link rel="icon" href="data:;base64,iVBORw0KGgo=">

</head>

<body class="d-flex flex-column">

<main class="flex-shrink-0">
<!-- header(메뉴바) 부분 -->
<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>

<div class="body-container" style="width: 700px;">
	<div class="body-title">
		<h3><i class="fas fa-chalkboard"></i> 예약 확인 </h3>
	</div>
	      
	<table class="table table-border table-article">
		<tr>
			<td> <%-- colspan="2" align="center" --%>
				<b>예약번호</b>
			</td>
			<td>
				${dto.bookNo}
			</td>
			<td>
				<b>예약일자</b> 			
			</td>
			<td>
				${dto.bookDate}
			</td>
		</tr>
		<tr>
			<td>
				<b>전화번호</b> 
			</td>
			<td>
				${dto.bookTel }
			</td>
			<td>
				<b>이메일</b> 
			</td>
			<td>
				${dto.bookEmail}
			</td>
		</tr>
		<tr>
			<td>
				<b>예약자명</b> 
			</td>
			<td>
				${dto.bookName}
			</td>
			<td>
				<b>아이디</b> 	
			</td>
			<td>
				${dto.memberId}
			</td>
		</tr>
		<tr>
			<td>
				<b>객실번호</b> 
			</td>
			<td>
				${dto.roomNo}
			</td>
			<td>
				<b>숙박인원</b> 
			</td>
			<td>
				${dto.people} (명)
			</td>
		</tr>
		<tr>
			<td>
				<b>체크인</b> 
			</td>
			<td>
				${dto.bookSrtdate}
			</td>
			<td>
				<b>체크아웃</b> 
			</td>
			<td>
				${dto.bookEnddate}
			</td>
		</tr>
		<tr>
			<td>
				<b>결제금액</b> 
			</td>
			<td colspan="3">
				${dto.totalPrice} (원)
			</td>
		</tr>
		
		<tr style="border-bottom: none;">
			<td colspan="4" valign="top" height="200">
				<b>요청사항</b><br>${dto.bookRequest}
			</td>
		</tr>
		
	</table>
	
	<table class="table">
		<tr>
			<td width="50%">
				<c:choose>
					<c:when test="${sessionScope.member.memberId==dto.memberId}">
						<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/book/bookUpdate.do?bookNo=${dto.bookNo}&page=${page}';">수정</button>
					</c:when>
					<c:otherwise>
						<button type="button" class="btn" disabled="disabled">수정</button>
					</c:otherwise>
				</c:choose>
		    	
				<c:choose>
		    		<c:when test="${sessionScope.member.memberId==dto.memberId || sessionScope.member.memberId=='admin'}">
		    			<button type="button" class="btn" onclick="deleteBoard();">삭제</button>
		    		</c:when>
		    		<c:otherwise>
		    			<button type="button" class="btn" disabled="disabled">삭제</button>
		    		</c:otherwise>
		    	</c:choose>
			</td>
			<td align="right">
				<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/book/bookList.do?${query}';">리스트</button>
			</td>
		</tr>
	</table>
	
	<div class="reply">
			<form name="replyForm" method="post">
				<div class='form-header'>
					<span class="bold">리뷰쓰기</span><span> - 타인을 비방하거나 개인정보를 유출하는 글의 게시를 삼가 주세요.</span>
				</div>
				
				<table class="table reply-form">
					<tr>
						<td>
							<b>별점</b>&nbsp;&nbsp;
							<select id="star" name="star">
								<option value="1">★</option>
								<option value="2">★★</option>
								<option value="3">★★★</option>
								<option value="4">★★★★</option>
								<option value="5">★★★★★</option>
							</select>
						</td>
					</tr>
					<tr>
						<td>
							<textarea class='boxTA' name="content"></textarea>
						</td>
					</tr>
					<tr>
					   <td align='right'>
					        <button type='button' class='btn btnSendReply'>리뷰 등록</button>
					    </td>
					 </tr>
				</table>
			</form>
			
			<div id="listReply"></div>
		</div>
</div>

</main>

<!-- Bootstrap core JS-->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
<!-- Core theme JS-->
<script src="js/scripts.js"></script>
<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
<!-- * *                               SB Forms JS                               * *-->
<!-- * * Activate your form at https://startbootstrap.com/solution/contact-forms * *-->
<!-- * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *-->
<script src="https://cdn.startbootstrap.com/sb-forms-latest.js"></script>
<!-- footer 부분 -->
<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>     

</body>
</html>