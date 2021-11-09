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


.body-container tr {
	border-bottom: 1px solid #ccc;
}


.table-form td {
	padding: 7px 0;

}

.table-form tr > td:first-child {
	width: 110px; text-align: center; background: #eee; 
}

.table-form tr > td:nth-child(2) {
	padding-left: 10px;
}

.table-form {
	border-collapse: collapse;
	border-top: 2px solid #ccc;
	border-bottom: 2px solid #ccc;
}


.table {
	width: 100%;
	margin: 10px auto;
}

.boxTF {
	border:1px solid #999;
	width: 96%;
	padding: 5px 5px;
	border-radius:4px;
}

.boxTA {
	border:1px solid #999;
	width: 96%;
	height:150px;
	padding:3px 5px;
	border-radius:4px;
    background-color:#fff;
	resize : none;
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
function sendOk() {
    var f = document.roomWriteForm;
	var str;
	
    str = f.roomName.value.trim();
    if(!str) {
        alert("객실 이름을 입력하세요. ");
        f.roomName.focus();
        return;
    }

    str = f.stdPers.value.trim();
    if(!str) {
        alert("기본예약인원을 입력하세요. ");
        f.stdPers.focus();
        return;
    }
    
    str = f.maxPers.value.trim();
    if(!str) {
        alert("최대예약인원을 입력하세요. ");
        f.maxPers.focus();
        return;
    }

    str = f.stdPrice.value.trim();
    if(!str) {
        alert("1박당 기본요금 내용을 입력하세요. ");
        f.stdPrice.focus();
        return;
    }
    
    str = f.extraPrice.value.trim();
    if(!str) {
        alert("초과인원요금을 입력하세요. ");
        f.extraPrice.focus();
        return;
    }

    str = f.roomDetail.value.trim();
    if(!str) {
        alert("객실 설명을 입력하세요. (객실 소개, 방 개수, 화장실 개수, 취사가능여부 등..)");
        f.roomDetail.focus();
        return;
    }

    f.action = "${pageContext.request.contextPath}/bbs/${mode}_ok.do";
    f.submit();
}


</script>


</head>
<body>

<main>
	<div class="body-container" >
		<div class="body-title">
			<h3>객실 등록</h3>
		</div>
		
		<form name="roomWriteForm" method="post" enctype="multipart/form-data">
		<table class="table table-form">
		<!--캠핑장번호시퀀스처리? -->
			<!-- 캠핑장번호를 가져올때-->
			<!-- 
			<tr class="campNo">
				<td>캠핑장번호</td>
				<td><input type="text" name="campNo" class="sel4" placeholder="차량수를 입력해주세요"></td>
			</tr>
			 -->
			<tr class="roomName">
				<td>객실이름</td>
				<td><input type="text" name="roomName" class="boxTF" placeholder="객실이름을 입력해주세요"></td>
			</tr>
			
			<tr class="stdPers">
				<td>기본예약인원</td>
				<td><input type="text" name="stdPers" class="boxTF" placeholder="기본예약인원을 입력해주세요"></td>
			</tr>
			
			<tr class="maxPers">
				<td>최대예약인원</td>
				<td><input type="text" name="maxPers" class="boxTF" placeholder="최대예약인원을 입력해주세요"></td>
			</tr>
			
			<tr class="stdPrice">
				<td>1박 기본요금</td>
				<td><input type="text" name="stdPrice" class="boxTF" placeholder="1박 기본요금 사항입니다."></td>
			</tr>
			
			<tr class="extraPrice">
				<td>초과인원요금</td>
				<td><input type="text" name="extraPrice" class="boxTF" placeholder="초과인원요금을 입력해주세요"></td>
			</tr>
			
			<tr class="roomDetail">
				<td>설명</td>
				<td>
					<textarea name="roomDetail" class="boxTA"></textarea>
				</td>
			</tr>
			<tr> 
				<td>이미지</td>
				<td> 
					<input type="file" name="selectFile" accept="image/*" multiple="multiple" class="boxTF">
				</td>
			</tr>
			<c:if test="${mode=='roomUpdate'}">
				<tr>
					<td>등록이미지</td>
					<td> 
						<div class="img-box">
							<c:forEach var="vo" items="${listFile}">
								<img src="${pageContext.request.contextPath}"
									onclick="deleteFile('파일번호');">
							</c:forEach>
						</div>
					</td>
				</tr>
			</c:if>
		</table>
		<table class="table">
			<tr>
				<td align="center">
					<button type="button" class="btn" onclick="sendOk();">등록</button>
					<button type="reset" class="btn">다시입력</button>
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/admin/roomList.do';">${mode=='roomUpdate'?'수정취소':'등록취소'}</button>
					<c:if test="${mode=='romeUpdate'}">
						<input type="hidden" name="num" value="">
						<input type="hidden" name="page" value="">
					</c:if>
				</td>
			</tr>
		</table>
		</form>
	</div>		
</main>
</body>
</html>