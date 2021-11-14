<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 캠핑어때 1:1문의 </title>
<jsp:include page="/WEB-INF/campingutte/layout/staticHeader.jsp"/>
<link href="${pageContext.request.contextPath}/resource/css/styles.css" rel="stylesheet" />
<link href="${pageContext.request.contextPath}/resource/css/header_footer-layout.css" rel="stylesheet" />

<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<style type="text/css">


.body-container {
	margin: 50px;
	width: 90%; 
	vertical-align: center; 
}

.table-article tr > td {
	padding-left: 5px; 
	padding-right: 5px;
	vertical-align: center;
}

.reply {
	padding-left: 5px; 
	padding-right: 5px;
	text-align: center;
	vertical-align: center;

}

.replyForm {
	padding-left: 5px; padding-right: 5px;
	text-align: center;
	vertical-align: center;
}

.tableReplyForm {
	vertical-align: center;
}

.btnConfirm {
	width: 100px;
	heigth: 70px;
	vertical-align: center;
	text-align: center;
}



</style>
<script type="text/javascript" src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script type="text/javascript">
function login() {
	location.href="${pageContext.request.contextPath}/member/login.do";
}

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
	$(".btnSendReply").click(function(){
		var compNo = "${dto.compNo}";
		var $tb = $(this).closest("table");
		var content = $tb.find("textarea").val().trim();
		if(! content) {
			$tb.find("textarea").focus();
			return false;
		}
		content = encodeURIComponent(content);
		
		var url = "${pageContext.request.contextPath}/cs/insertReply.do";
		var query = "compNo=" + compNo + "&compReplyContent=" + content;
		
		var fn = function(data){
			$tb.find("textarea").val("");
			
			var state = data.state;
			if(state === "true") {
				location.href="${pageContext.request.contextPath}/cs/article.do?compNo="+compNo+"&page=${page}";
			} else if(state === "false") {
				alert("댓글 추가가 실행되지 않음.");
			}
		};
		
		ajaxFun(url, "post", query, "json", fn);
	});
});

</script>



</head>
<body>

<header>
	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
</header>

<main>
	<div class="body-container">
		<div class="body-title" style="vertical-align: center;">
			<h3><i class="fas fa-chalkboard" style="vertical-align: center;"></i> 1:1 문의 </h3>
		</div>
        
		<table class="table table-border table-article" style="vertical-align: center;">
			<tr>
				<td colspan="2" align="center">
					${dto.compSubject}
				</td>
			</tr>
			
			<tr>
				<td width="50%">
					이름 : ${dto.memberName}
				</td>
				<td align="right">
					${dto.compDate} | 조회 ${dto.compHitCount}
				</td>
			</tr>
			
			<tr style="border-bottom: none;">
				<td colspan="2" valign="top" height="200">
					${dto.compContent}
				</td>
			</tr>
			
		</table>
		</div>

		<div class="body-container">
			<form name="body-title" method="post">
				<div>
					<span style="color: grey; font-size: 28px;">  ► 고객센터 답변 </span>
				</div>
				
				<c:if test="${empty rdto}">
					<table class="table reply-form">
						<tr>
							<td>
								<textarea class='boxTA' name="content" style="width: 85%; vertical-align: center;"></textarea>
								<button type='button' class='btn btnSendReply'>답변 올리기 </button>	
							</td>
						</tr>
					</table>
				</c:if>
			<c:if test="${not empty rdto}">
				<div id="listReply">

					<table class='table reply-list'>
							<tr class='list-header'>
								<td width='50%'>
									<span class='bold'>${rdto.memberName}</span>
								</td>
								<td width='50%' align='right'>
									<span>${rdto.compReplyDate}</span> |
								</td>
							</tr>
							<tr>
								<td colspan='2' valign='top'>${rdto.compReplyContent}</td>
							</tr>
					</table>			
				</div>
			</c:if>
			</form>
		</div>

</main>

<footer>
	<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>