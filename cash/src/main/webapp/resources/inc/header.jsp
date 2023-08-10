<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-expand-lg bg-primary" data-bs-theme="dark">
	<div class="container-fluid">
		<a class="navbar-brand" href="${pageContext.request.contextPath}/on/cashbook"><b>JJACK JJACK</b></a>
		<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarColor01" aria-controls="navbarColor01" aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
	    <div class="collapse navbar-collapse" id="navbarColor01">
			<ul class="navbar-nav me-auto">
				<c:if test="${loginMember == null}">
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/off/login">로그인</a>
					</li>
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/off/signUp">회원가입</a>
					</li>
				</c:if>
					<c:if test="${loginMember != null}">
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/on/cashbook">가계부</a>	
					</li>
					<li class="nav-item">
						<a class="nav-link" href="${pageContext.request.contextPath}/on/logout">로그아웃</a>
					</li>
				</c:if>
			</ul>
		</div>
	</div>
</nav>