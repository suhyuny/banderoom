<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<script src="<%=request.getContextPath() %>/js/jquery-3.6.0.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/css/base.css">
<style>
	#serinfoupdate{
		position:relative;
		top:15px;
		left:880px;
	}
</style>
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
	<c:import url="/header.do" />
	<div id="wrapper">
		<div id="page-title">
		<c:if test="${param.idx==1}">
			이용약관
		</c:if>
		<c:if test="${param.idx==2}">
			개인정보처리방침
		</c:if>
		<c:if test="${param.idx==3}">
			운영정책
		</c:if>
		</div>
		<div id="page-content">
			<div class="inner-box">
				<div class="inner-box-content">
					<c:if test="${param.idx==1}">
					${serviceInfoVO.content}
					</c:if>
					<c:if test="${param.idx==2}">
					${serviceInfoVO.content}
					</c:if>
					<c:if test="${param.idx==3}">
					${serviceInfoVO.content}
					</c:if>
				</div>
			</div>
		<form action="serinfoupdate.do" method="get">
	
			<button class="normal-button accent-button" id="serinfoupdate" style="margin-left: 15px;">수정하기</button>
		
		</form>
		</div>		
	</div>
	
</body>
</html>