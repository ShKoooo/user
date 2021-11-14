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
    var f = document.typeWriteForm;
	var str;
	
    str = f.typeNo.value.trim();
    if(!str) {
        alert("유형번호를 입력하세요. ");
        f.subject.focus();
        return;
    }

    str = f.typeName.value.trim();
    if(!str) {
        alert("유형이름을 입력하세요. ");
        f.content.focus();
        return;
    }
  

    f.action = "${pageContext.request.contextPath}/admin/campTypeWrite_ok.do";
    f.submit();
}

</script>
</head>
<body>

<main>
	<div class="body-container" >
		<div class="body-title">
			<h3>캠핑장 유형 등록</h3>
		</div>
		<form name="typeWriteForm" method="post">
		<table  class="table table-form">
			<tr>
				<td>
					유형번호 : <input type="text" maxlength="30" name="typeNo" class="boxTF" value="${dto.typeNo}" style="width: 20%;"> 
					유형이름 : <input type="text" maxlength="30" name="typeName" class="boxTF" value="${dto.typeName}" style="width: 20%;">
				</td>
			</tr>
		</table>
		
		<table class="table">
			<tr>
				<td align="center">
					<button type="button" class="btn" onclick="sendOk();">등록</button>
					<button type="reset" class="btn">다시입력</button>
					<button type="button" class="btn" onclick="location.href='${pageContext.request.contextPath}/admin/campTypeList.do';">등록 취소</button>
				</td>
			</tr>
		</table>
		</form>
	</div>
</main>
</body>
</html>