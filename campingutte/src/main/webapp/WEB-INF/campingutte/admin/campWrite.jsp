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
    var f = document.campWriteForm;
	var str;
	
    str = f.campName.value.trim();
    if(!str) {
        alert("캠핑장 이름을 입력하세요. ");
        f.campName.focus();
        return;
    }
    
    str = f.campAddr1.value.trim();
    if(!str) {
        alert("기본주소를 입력하세요. (ㅇㅇ시 ㅇㅇ구 ㅇㅇ동) ");
        f.subject.focus();
        return;
    }

    str = f.campAddr2.value.trim();
    if(!str) {
        alert("상세주소를 입력하세요. ");
        f.content.focus();
        return;
    }
    
    str = f.campTel.value.trim();
    if(!str) {
        alert("전화번호를 입력하세요. ");
        f.campTel.focus();
        return;
    }
    
    str = f.campDetail.value.trim();
    if(!str) {
        alert("캠핑장 설명을 입력하세요. ");
        f.campDetail.focus();
        return;
    }

    str = f.campAdd.value.trim();
    if(!str) {
        alert("캠핑장 부대시설을 입력하세요. ");
        f.campAdd.focus();
        return;
    }
    
    str = f.campType.value.trim();
    if(!str) {
        alert("캠핑 유형을 입력하세요. (캠핑장, 카라반, 글램핑..etc) ");
        f.campType.focus();
        return;
    }
    
    f.action = "${pageContext.request.contextPath}/goods/${mode}_ok.do";
    f.submit();
}


</script>

</head>
<body>

<main>
	<div class="body-container" >
		<div class="body-title">
			<h3>캠핑장 등록</h3>
		</div>
		
		<form name="campWriteForm" method="post" enctype="multipart/form-data">
		<table class="table table-form">
		<!--캠핑장번호시퀀스처리? -->
			
			<tr class="campName">
				<td>캠핑장이름</td>
				<td><input type="text" name="campName" class="boxTF" placeholder="캠핑장 이름을 입력해주세요." value=""></td>
			</tr>
			
			<tr class="campAddr1">
				<td>기본주소</td>
				<td><input type="text" name="campAddr1" class="boxTF" placeholder="필수입력 사항입니다." value=""></td>
			</tr>
			
			<tr class="campAddr2">
				<td>상세주소</td>
				<td><input type="text" name="campAddr2" class="boxTF" placeholder="필수입력 사항입니다." value=""></td>
			</tr>
			
			<tr class="campTel">
				<td>전화번호</td>
				<td><input type="text" name="campTel" class="boxTF" placeholder="필수입력 사항입니다." value=""></td>
			</tr>
			
			<tr class="campDetail">
				<td>설명</td>
				<td>
					<textarea name="campDetail" class="boxTA"></textarea>
				</td>
			</tr>
			
			<tr class="campAdd">
				<td>부대시설</td>
				<td><input type="text" name="campAdd" class="boxTF" placeholder="ex) 샤워장, 바베큐, 화장실 ..etc" value=""></td>
			</tr>
			<tr class="campType">
				<td>유형이름</td>
				<td><input type="text" name="campType" class="boxTF" placeholder="ex) 캠핑장 or 글램핑" value=""></td>
			</tr>
			<tr> 
				<td>이미지</td>
				<td> 
					<input type="file" name="selectFile" accept="image/*" multiple="multiple" class="boxTF">
				</td>
			</tr>
			<c:if test="${mode=='campUpdate'}">
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
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/goods/campList.do';">${mode=='campUpdate'?'수정취소':'등록취소'}</button>
					<c:if test="${mode=='campUpdate'}">
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