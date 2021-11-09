<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> 캠핑어때 자유게시판 </title>
<jsp:include page="/WEB-INF/campingutte/layout/staticHeader.jsp"/>

<link rel="icon" href="data:;base64,iVBORw0KGgo=">

<style type="text/css">
.table-article tr > td {
	padding-left: 5px; padding-right: 5px;
}


</style>

<script type="text/javascript">

</script>

</head>
<body>

<header>
	<jsp:include page="/WEB-INF/campingutte/layout/header.jsp"></jsp:include>
</header>

<main>
	<div class="body-container" style="width: 700px;">
		<div class="body-title">
			<h3><i class="fas fa-chalkboard"></i> 게시판 </h3>
		</div>
        
		<table class="table table-border table-article">
			<tr>
				<td colspan="2" align="center">
					${dto.commSubject}
				</td>
			</tr>
			
			<tr>
				<td width="50%">
					이름 : ${dto.memberName}
				</td>
				<td align="right">
					${dto.commDate} | 조회 ${dto.commHitCount}
				</td>
			</tr>
			
			<tr style="border-bottom: none;">
				<td colspan="2" valign="top" height="200">
					${dto.commContent}
				</td>
			</tr>
			
		</table>
		</div>
</main>

<footer>
	<jsp:include page="/WEB-INF/campingutte/layout/footer.jsp"></jsp:include>
</footer>
</body>
</html>