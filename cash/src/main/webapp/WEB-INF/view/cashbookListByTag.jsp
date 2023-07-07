<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hashtag list</title>
<!-- 부트스트랩 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- jQuery -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		//보기 선택 시 submit
		$("#rowPerPage").change(function(){
			$("form").submit();
		})
		//보기값 표시 고정
		let rowPerPage = '<c:out value="${rowPerPage}"/>';
		$("#rowPerPage").val(rowPerPage).prop("selected", true)
		
		//페이지네이션 첫번째 
		let currentPage = '<c:out value="${currentPage}"/>';
		let lastPage = '<c:out value="${lastPage}"/>';
		let startPageInBlock = '<c:out value="${startPageInBlock}"/>';
		let endPageInBlock = '<c:out value="${endPageInBlock}"/>';
		//첫번째 페이지블럭에서 이전버튼 비활성화
		if(startPageInBlock == 1){
			$("#previous").addClass("disabled");
		}
		//마지막 페이지버튼에서 다음버튼 비활성화
		if(endPageInBlock == lastPage){
			$("#next").addClass("disabled");
		}
		//현재페이지 표시
		console.log($("#currPage a").text());
		if($("#currPage a").text() == currentPage){
			$("#currPage").addClass("active");
		}
	})
</script>
</head>
<body>
	<!-- 컨테이너 시작 -->
	<div class="container">
		<!-- 네이게이션바 시작 -->
		<div>
			<a href="${pageContext.request.contextPath}/on/logout">로그아웃</a>
			<a href="${pageContext.request.contextPath}/on/cashbook">가계부</a>
		</div>
		<!-- 네이게이션바 끝 -->
	
		<h1>#${hashtag}</h1>
		<!-- 가계부 목록 시작 -->
		<div>
			<!-- 보기 선택 시작 -->
			<div class="row">
				<div class="col-2">
					<form class="form-group" method="get" action="${pageContext.request.contextPath}/on/cashbookListByTag">
						<input type="hidden" name="hashtag" value="${hashtag}">
						<input type="hidden" name="currentPage" value="1">
						<select class="form-control" id="rowPerPage" name="rowPerPage">
							<option value="10">10개씩</option>
							<option value="15">15개씩</option>
							<option value="30">30개씩</option>
						</select>
					</form>
				</div>
			</div>
			<!-- 보기 선택 끝 -->
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>수입/지출</th>
						<th>날짜</th>
						<th>메모</th>
						<th>금액</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="c" items="${list}">
						<tr>
							<td>${c.category}</td>
							<td>${c.cashbookDate}</td>
							<td>${c.memo}</td>
							<td id="price">${c.price}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<!-- 가계부 목록 끝 -->
		<!-- 페이지네이션 시작 -->
		<div>
			<nav aria-label="Page navigation">
			  <ul class="pagination justify-content-center">
			  	<!-- 첫번째 블럭에서는 비활성화 -->
			    <li id="previous" class="page-item"> 
			      <a class="page-link" href="${pageContext.request.contextPath}/on/cashbookListByTag?currentPage=${startPageInBlock-1}&rowPerPage=${rowPerPage}&hashtag=${hashtag}" tabindex="-1">이전</a>
			    </li>
			    <!-- pagePerBlock만큼 반복 -->
			    <c:forEach var="i" begin="${startPageInBlock}" end="${endPageInBlock}" step="1">
			    	<li id="currPage" class="page-item">
			    		<a class="page-link" href="${pageContext.request.contextPath}/on/cashbookListByTag?currentPage=${currentPage}&rowPerPage=${rowPerPage}&hashtag=${hashtag}">${i}</a>
			    	</li>
			    </c:forEach>
			    <!-- 마지막 블럭에서는 비활성화 -->
			    <li id="next" class="page-item"> 
			      <a class="page-link" href="${pageContext.request.contextPath}/on/cashbookListByTag?currentPage=${endPageInBlock+1}&rowPerPage=${rowPerPage}&hashtag=${hashtag}">다음</a>
			    </li>
			  </ul>
			</nav>
		</div>
		<!-- 페이지네이션 끝 -->
	</div>
	<!-- 컨테이너 끝 -->
</body>
</html>