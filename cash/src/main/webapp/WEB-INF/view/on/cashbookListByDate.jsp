<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<c:import url="/resources/inc/head.jsp"></c:import>
</head>
<body>
	<!-- 네비게이션바 시작 -->
	<div>
		<c:import url="/resources/inc/header.jsp"></c:import>
	</div>
	<!-- 네비게이션바 끝 -->

	<div class="container mt-3">
		<div class="text-center">
			<h1>${targetYear}년 ${targetMonth + 1}월 ${targetDate}일</h1>
		</div>
		
		<div class="text-right"><!-- 가계부 입력버튼 -->
			<a class="btn btn-primary" href="${pageContext.request.contextPath}/on/addCashbook?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}">추가하기</a>
		</div>
		<div class="table-responsive-md">
			<table class="table table-bordered mt-3">
				<thead>
					<tr class="text-center table-secondary text-white border">
						<th>수입/지출</th>
						<th>날짜</th>
						<th>내용</th>
						<th>금액</th>
						<th>작성일</th>
						<th>수정일</th>
						<th>수정</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="c" items="${list}">
						<tr class="data text-center">
							<td class="category">${c.category}</td>
							<td>${c.cashbookDate}</td>
							<td>${c.memo}</td>
							<td class="text-right"><fmt:formatNumber value="${c.price}"></fmt:formatNumber>원</td>
							<td>${fn:substring(c.createdate, 0, 10)}</td>
							<td>${fn:substring(c.updatedate, 0, 10)}</td>
							<td><a id="modifyBtn" class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/on/modifyCashbook?cashbookNo=${c.cashbookNo}&targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}">수정</a></td>
							<td><a id="removeBtn" class="btn btn-sm btn-outline-primary" href="${pageContext.request.contextPath}/on/removeCashbook?cashbookNo=${c.cashbookNo}&targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}">삭제</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
</html>