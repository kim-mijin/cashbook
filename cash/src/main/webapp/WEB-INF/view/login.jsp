<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>login</title>
<!-- 부트스트랩 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body>
	<div class="container">
		<!-- 네비게이션바 시작 -->
		<div>
			<a href="${pageContext.request.contextPath}/off/signUp">회원가입</a>
		</div>
		<!-- 네비게이션바 시작 -->
		<!-- 로그인폼 시작 -->
		<h1>로그인</h1>
		<form method="post" action="${pageContext.request.contextPath}/off/login"> <!-- el(expression language) 사용하여 자바코드 대체하기 -->
			<div>
				<label for="memberId">아이디</label>
				<input type="text" id="memberId" name="memberId" value="${loginId}" required><!-- 쿠키에 저장된 아이디가 있으면 보여주기 -->
			</div>
			<div>
				<label for="memberPw">비밀번호</label>
				<input type="password" id="memberPw" name="memberPw" required>
			</div>
			<div>
				<label for="idCookie">아이디 저장</label>
				<input type="checkbox" id="idCookie" name="idCookie" value="y">
			</div>
			<div>
				<button type="submit">로그인</button>
			</div>
		</form>
		<!-- 로그인폼 끝 -->
	</div>
</body>

</html>