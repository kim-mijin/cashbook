<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="/resources/inc/head.jsp"></c:import>
</head>

<body>
	<!-- 네비게이션바 시작 -->
	<div>
		<c:import url="/resources/inc/header.jsp"></c:import>
	</div>
	<!-- 네비게이션바 끝 -->
	
	<div class="container login mt-5 shadow p-3 mb-5 bg-white rounded">
		<!-- 로그인폼 시작 -->
		<form class="form-signin" method="post" action="${pageContext.request.contextPath}/off/login"> <!-- el(expression language) 사용하여 자바코드 대체하기 -->
			<div class="h1 font-weight-normal text-center mb-5">로그인</div>
			<label for="memberId" class="sr-only mb-1">아이디</label>
			<input type="text" id="memberId" class="form-control mb-1" name="memberId" placeholder="아이디" value="user1" required autofocus>
			<label for="memberPw" class="sr-only mt-3 mb-1">비밀번호</label>
			<input type="password" id="memberPw" class="form-control mb-1" name="memberPw" placeholder="비밀번호" value="1234" required>
			<div class="checkbox mb-3 text-right">
				<label>
					<input type="checkbox" name="idCookie" value="y"> 아이디 저장
				</label>
			</div>
			<div class="text-center">
				<button class="btn btn-m btn-primary btn-block" type="submit">로그인</button>
			</div>
		</form>
		<!-- 로그인폼 끝 -->
	</div>
</body>

</html>