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
</head>
<body>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
	<c:import url="/header.do" />
	<div id="wrapper">
		<div id="page-title">
			타이틀 들어갑니다.${serviceInfoVO.title}
		</div>
		<div id="page-content">
			<div class="inner-box">
				<div class="inner-box-content">
				내용입니다.${serviceInfoVO.content}
				</div>
			</div>
		</div>
		
		<!-- 여기까지 틀이고 밑에는 요소 공통사항 
		<div>
			위까지는 틀이고 밑에는 요소 공통사항
			<br><br><br>
			버튼 세로 크기 수정시 border-radius도 수정해야함<br>
			<br>
			<button>버튼</button> 
			일반 버튼 (버튼이 여러개 줄줄이 배치될 시 하나만 강조 컬러 넣을것)<br><br>
			<button class="accent-button">버튼</button> 강조 버튼 (button class="accent-button")<br><br>
			<br><br><br>
			내부 박스 틀과 예시
			<div class="inner-box">
				<div class="inner-box-content">
				박스에 들어갈 내용
				</div>
				<div class="inner-box-button-wrap">
					<button>일반버튼</button>
					<button class="accent-button" style="margin-left: 15px;">강조버튼</button>
				</div>
			</div>
			<br><br>
		</div>
		<!-- 여기까지 -->
		
	</div>
	
</body>
</html>