<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	
	<!-- 컨테이너 시작 -->
	<div class="container">
		<div class="mt-3">
			<h1>#${hashtag}</h1>
		</div>
		<!-- 가계부 목록 시작 -->
		<div>
			<!-- 보기 선택 시작 -->
			<div class="row mb-3">
				<div class="col-10"></div>
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
			<div class="table-responsive-md">
				<table class="table table-bordered shadow-sm p-3 mb-3 bg-body border">
					<thead>
						<tr class="table-secondary border text-center">
							<th>수입/지출</th>
							<th>날짜</th>
							<th>메모</th>
							<th>금액</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="c" items="${list}">
							<tr class="text-center">
								<td>${c.category}</td>
								<td>${c.cashbookDate}</td>
								<td class="text-left">${c.memo}</td>
								<td class="text-right" id="price"><fmt:formatNumber value="${c.price}"></fmt:formatNumber>원</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
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
	
	<script>
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
	</script>
</body>
</html>