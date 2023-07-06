<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>modify cashbook</title>
<!-- 부트스트랩 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
	//수정 전 카테고리 표시
	let category = '<c:out value="${cashbook.category}"/>'
	$("#category").val(category).prop("selected", true);
</script>
</head>
<body>
<div class="container">
	<header>
		<!-- 네비게이션바 -->
		
	</header>
	
	<!------------------------------------------------------------------ 수정폼 시작 ------------------------------------------------------------------>
	<div>
		<div class="d-flex">
			<form method="post" action="${pageContext.request.contextPath}/modifyCashbook">
				<input type="hidden" name="cashbookNo" value="${cashbook.cashbookNo}">
				<input type="hidden" name="targetYear" value="${targetYear}">
				<input type="hidden" name="targetMonth" value="${targetMonth}">
				<input type="hidden" name="targetDate" value="${targetDate}">
				
				<div class="row form-group">
					<div class="col-lg-5"><label for="cashbookDate">날짜</label></div>
					<div class="col-lg-7"><input class="form-control" type="date" name="cashbookDate" id="cashbookDate" value="${cashbook.cashbookDate}" readonly></div>
				</div>
				<div class="row form-group">
					<div class="col-lg-5"><label for="memberId">아이디</label></div>
					<div class="col-lg-7"><input class="form-control" type="text" name="memberId" id="cashbookDate" value="${cashbook.memberId}" readonly></div>
				</div>
				<div class="row form-group">
					<div class="col-lg-5"><label for="category">수입/지출</label></div>
					<div class="col-lg-7">
						<select class="form-control" id="category" name="category" required>
							<option value="수입">수입</option>
							<option value="지출">지출</option>
						</select>
					</div>
				</div>
				<div class="row form-group">
					<div class="col-lg-5"><label for="price">금액</label></div>
					<div class="col-lg-7"><input class="form-control" type="number" name="price" id="price" value="${cashbook.price}" min="0" required></div>
				</div>
				<div class="row form-group">
					<div class="col-lg-5"><label for="memo">메모</label></div>
					<div class="col-lg-7"><textarea class="form-control" id="memo" name="memo" required>${cashbook.memo}</textarea></div>
				</div>
				<div>
					<button class="btn btn-primary" type="submit">수정하기</button>
				</div>
			</form>
		</div>
	</div>
	<!------------------------------------------------------------------ 수정폼 끝 ------------------------------------------------------------------>
</div>
</body>
</html>