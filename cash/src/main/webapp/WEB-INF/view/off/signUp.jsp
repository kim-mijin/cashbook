<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<c:import url="/resources/inc/head.jsp"></c:import>
</head>
<body>
	<!-- 네비게이션 시작 -->
	<div>
		<c:import url="/resources/inc/header.jsp"></c:import>
	</div>
	<!-- 네비게이션 끝 -->

	<div class="login container mt-5 shadow p-3 mb-5 bg-white rounded">
		<form class="form-signin"  method="post" action="${pageContext.request.contextPath}/off/signUp">
			<div class="h1 mb-5 font-weight-normal text-center">회원가입</div>
			<div>
				<label for="memberId" class="sr-only mb-1">아이디</label>
				<div class="row">
					<div class="col-9">
						<input type="text" id="memberId" class="form-control mb-1" name="memberId" required>
					</div>
					<div class="col-3">
						<button class="btn btn-m btn-outline-primary" type="button" id="idCkBtn">중복검사</button>
					</div>
				</div>
				<div id="idValMsg"></div>
			</div>
			<div>
				<label for="memberPw" class="sr-only mt-3 mb-1">비밀번호</label>
				<input type="password" id="memberPw" class="form-control mb-3" name="memberPw" required>
				<div id="pwValMsg"></div>
			</div>
			<div class="text-center">
				<button class="btn btn-m btn-primary btn-block"  type="submit">회원가입</button>
			</div>
		</form>
	</div>
	<!-- 스크립트 시작 -->
	<script>
		//중복검사
		$("#idCkBtn").click(function(){
			$.ajax({
				url : "./idCk",
				data : {memberId: $("#memberId").val()},
				type : "post",
				success : function(param){
					console.log(param);
					if(param == 0){ //true인 경우 (아이디 사용 가능한 경우)
						$("#idValMsg").text("사용 가능한 아이디입니다");
					} else { //false인 경우
						$("#idValMsg").text("이미 사용 중인 아이디입니다");
						$("#memberId").val("");
					} 
				},
			})
		})
		
		//회원가입 입력값 유효성 검사
		//아이디는 10자까지 입력가능
		$("#memberId").keyup(function(){
			const MAX_LENGTH = 10;
			let len = $("#memberId").val().length;
			if(len > MAX_LENGTH){
				len = $("#memberId").val().substring(0, MAX_LENGTH);
				$("#memberId").val(len);
				$("#memberId").focus();
			}			
		})
		
		//비밀번호는 15자까지 입력가능
		$("#memberPw").keyup(function(){
			const MAX_LENGTH = 15;
			let len = $("#memberPw").val().length;
			if(len > MAX_LENGTH){
				len = $("#memberPw").val().substring(0, MAX_LENGTH);
				$("#memberPw").val(len);
				$("#memberPw").focus();
			}			
		})
	</script>
	<!-- 스크립트 끝 -->
</body>
</html>