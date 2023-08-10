<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<c:import url="/resources/inc/head.jsp"></c:import>
<script>
	//수정 전 카테고리 표시
	let category = '<c:out value="${cashbook.category}"/>'
	$("#category").val(category).prop("selected", true);
</script>
</head>
<body>
	<!-- 네비게이션바 시작 -->
	<div>
		<c:import url="/resources/inc/header.jsp"></c:import>
	</div>
	<!-- 네비게이션바 끝 -->

	<div class="container mt-5">
	<!------------------------------------------------------------------ 수정폼 시작 ------------------------------------------------------------------>
		<form method="post" action="${pageContext.request.contextPath}/on/modifyCashbook">
			<div class="h1 font-weight-normal text-center">가계부 수정하기</div>
			<input type="hidden" name="cashbookNo" value="${cashbook.cashbookNo}">
			<input type="hidden" name="targetYear" value="${targetYear}">
			<input type="hidden" name="targetMonth" value="${targetMonth}">
			<input type="hidden" name="targetDate" value="${targetDate}">
			
			<div class="table-responsive-md mt-3">
				<table class="table table-bordered shadow-sm p-3 mb-3 bg-body border">
					<tr>
						<th class="table-secondary text-white text-center" width=10%><label for="cashbookDate">날짜</label></th>
						<td><input class="form-control" type="date" name="cashbookDate" id="cashbookDate" value="${targetDayStr}" readonly></td>
					</tr>
					<tr>
						<th class="table-secondary text-white text-center"><label for="memberId"><strong>아이디</strong></label></th>
						<td><input class="form-control mb-1" type="text" name="memberId" id="cashbookDate" value="${cashbook.memberId}" readonly></td>
					</tr>
					<tr>
						<th class="table-secondary text-white text-center"><label for="category"><strong>수입/지출</strong></label></th>
						<td>
							<select class="form-control mb-1" id="category" name="category" required>
								<option value="수입">수입</option>
								<option value="지출">지출</option>
							</select>
						</td>
					</tr>
					<tr>
						<th class="table-secondary text-white text-center"><label for="price"><strong>금액</strong></label></th>
						<td><input class="form-control mb-1" type="number" name="price" id="price" value="${cashbook.price}" min="0" required></td>
					</tr>
					<tr>
						<th class="table-secondary text-white text-center"><label for="memo"><strong>메모</strong></label></th>
						<td><textarea class="form-control mb-1" id="memo" name="memo" required rows="10">${cashbook.memo}</textarea></td>
					</tr>
				</table>
			</div>
			
			<div class="text-center">
				<button class="btn btn-primary mt-1 mb-3" type="submit">수정하기</button>
			</div>
		</form>
	</div>
	<!------------------------------------------------------------------ 수정폼 끝 ------------------------------------------------------------------>
</body>
</html>