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
* { margin: 0; padding: 0; }

.container {
	/*
	display: grid;
	grid-template-rows: repeat(auto-fill, auto);
	grid-template-columns: repeat(auto-fill, minmax(1519px, 1fr));	
	*/
	width: 900px;
	margin: auto;
	
	background:
}

.bwrite > ul li {
	list-style: none;
}

.btitle {
	padding-top: 70px;
	text-align: center;
	height: 150px;
	font-size: 35px;
	
	line-height: 2;
	
}

.bwrite {
	display: block;
	width: 800px;
	text-align: center;
}

.bwrite li {
	float: left;
	
}

.bwrite ul {
	
	margin: 30px;
	height: 30px;
	
	background: #FF9090;
  	width: 800px;
  	padding: 20px;
  	border-radius: 30px;
}


.col-title {
	width: 120px;
	padding-left:15px;
}

.col-input {
	width: 480px;
}

.pe-2 {
	
	
}

input {
	border-radius: 20px;
	border: 1px solid white;
}





/*
.people {
	clear: both;
	margin-left: 100px;
	margin-right: 100px;
	z-index: 1;
	position: relative;
	
	top: -195px;
	
	border-top: 2px solid black;
	font-weight: bold;
}
.blist {
	margin: 65px 0;
	z-index: 1;
	
	position: relative;
	top: -195px;
	
	background: white;
	
	text-align: 
}

.blist ul {
	list-style: none;
	
	width: 700px;
}

.blist li {
	float: left;
	height: 50.5px;
	line-height: 35px;
	text-align: center;
	border-bottom: 1px solid #D5D5D5;
	padding-top: 15px;
	font-size: 13px;
}

.blist a {
	text-decoration: none;
	color: #4C4C4C;
}
.blist .num { width: 120px; }
.blist .sep { width: 190px; text-align: left; padding-left: 50px; }
.blist .title { width: 460px; text-align: left; }
.blist .date { width: 150px; }

.blist .title a:hover {
	color: #4374D9;
	text-decoration: underline; 
}

.blist .sbold {
	font-weight: bold;
	color: black;
}

.blist .sbold a {
	color: black;
}

.title img {
	margin-left: 10px;
	width: 16px;
	height: 16px;
}

.page {
	clear: both;
	text-align: center;
	padding: 60px 0 0;
	position: relative;
	top: -197px;
	
}

.pagenum a {
	text-decoration: none;
	color: #4C4C4C;
}

.numButton1 {
	color: black;
	font-weight: bold;
}

.numButton2 {
	padding: 10px;
}

.pageButton1 {
	padding-right: 7px;
	font-size: medium;
}

.pageButton2 {
	padding-right: 80px;
}

.pageButton3 {
	padding-left: 80px;
}

.pageButton4 {
	padding-left: 7px;
}
*/


</style>
</head>
<body>

<main>
	<div class="container">

		<div class="btitle">
			<p>	[△△ 캠핑장] □□□ 객실 </p>
			<p> 예약 일자 </p>
		</div>
				
			<div class="bwrite">
				<ul class="people">
					<li class="pe-1">인원수</li>
					<li class="pe-2"> <input type="text" name="word" class="sel4" placeholder="인원수를 입력해주세요"> </li>
				</ul>
			
				<ul class="car">
					<li class="car-1">차량수</li>
					<li class="car-2"> <input type="text" name="word" class="sel4" placeholder="차량수를 입력해주세요"> </li>
				</ul>
				
				<ul class="bookname">
					<li class="bn-1">*</li>
					<li class="bn-2">예약자 명</li>
					<li class="bn-3"> <input type="text" name="word" class="sel4" placeholder="필수입력 사항입니다."> </li>
				</ul>
				
				<ul class="tel">
					<li class="tel-1">*</li>
					<li class="tel-2">연락처</li>
					<li class="tel-3"> <input type="text" name="word" class="sel4" placeholder="필수입력 사항입니다."> </li>
				</ul>
				
				<ul>
					<li class="col-title">이메일</li>
					<li class="col-input">
						<select name="selectEmail" class="selectField">
							<option value="">선 택</option>
							<option value="naver.com">네이버 메일</option>
							<option value="hanmail.net">한 메일</option>
							<option value="hotmail.com">핫 메일</option>
							<option value="gmail.com">지 메일</option>
							<option value="direct">직접입력</option>
						</select>
						<input type="text" name="email1" maxlength="30" class="boxTF" style="width: 33%;"> @ 
						<input type="text" name="email2" maxlength="30" class="boxTF" readonly="readonly" style="width: 33%;">
					</li>
				</ul>
				
				<ul class="yo">
					<li class="yo-1">요청사항</li>
					<li class="yo-2"> <input type="text" name="word" class="sel4" placeholder="필수입력 사항입니다."> </li>
				</ul>
			</div>
			
			<div class="button">
				<button>예약완료</button>
				<button type="reset">다시입력</button>
				<button>예약취소</button>
			</div>
			
		
</div>	
	
</main>

</body>
</html>