<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cashbook list by month</title>
<!-- 부트스트랩 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
</head>
<body>
	<div class="container">
		<h1>${targetYear}년 ${targetMonth + 1}월 ${targetDate}일</h1>
		<table class="table table-bordered">
			<thead>
				<tr>
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
					<tr class="data">
						<td class="category">${c.category}</td>
						<td>${c.cashbookDate}</td>
						<td>${c.memo}</td>
						<td>${c.price}</td>
						<td>${fn:substring(c.createdate, 0, 10)}</td>
						<td>${fn:substring(c.updatedate, 0, 10)}</td>
						<td><a id="modifyBtn" class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/on/modifyCashbook?cashbookNo=${c.cashbookNo}&targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}">수정</a></td>
						<td><a id="removeBtn" class="btn btn-sm btn-primary" href="${pageContext.request.contextPath}/on/removeCashbook?cashbookNo=${c.cashbookNo}&targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}">삭제</a></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div><!-- 가계부 입력버튼 -->
			<a class="btn btn-primary" href="${pageContext.request.contextPath}/on/addCashbook?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${targetDate}">추가하기</a>
		</div>
	</div>
</body>
</html>