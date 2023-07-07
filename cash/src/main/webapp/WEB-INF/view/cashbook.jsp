<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!-- jsp컴파일시 자바코드로 변환되는 c:...(제어문 코드) 커스텀태그 사용 가능 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><!-- 문자열 관련 메소드 사용 (substring) -->
<%
	/* int targetYear = (int)request.getAttribute("targetYear");
	int targetMonth = (int)request.getAttribute("targetMonth");
	int lastDate = (int)request.getAttribute("lastDate");
	int totalCell = (int)request.getAttribute("totalCell");
	int beginBlank = (int)request.getAttribute("beginBlank");
	int endBlank = (int)request.getAttribute("endBlank"); */
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>cashbook</title>
<!-- 부트스트랩 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.0/jquery.min.js"></script>
<script>
	$(document).ready(function(){
		let price = $("#price").text();
		console.log(typeof(price));
		let won = price.replace(/\B(?=(\d{3})+(?!\d))/g, ",");
		console.log(won);
		$("#price").text(won);
	})
</script>
</head>
<body>
	<div class="container">
		<!-- 네비게이션 -->
		<div>
			<a href="${pageContext.request.contextPath}/on/logout">로그아웃</a>
			<a href="${pageContext.request.contextPath}/on/cashbook">가계부</a>
		</div>
		
		<!-- 방문자수 -->
		<div>
			<c:import url="/counter"></c:import>
		</div>
	
		<!-- 변수값 or 반환값 : EL(Expression Language)사용 달러{...} -->
		<!-- 
			속성값 대신 EL사용 : 달러{표현식}
			request.getAttribute("endBlank") -> requestScope.targetYear (requestScope 생략가능)
			형변환 연산이 필요 없다(EL이 자동으로 처리)
		 -->
		 
		<!-- 자바코드(제어문) : JSTL 사용 -->
		<h1>
			${targetYear}년 ${targetMonth + 1}월
		</h1>
		<div>
			<a href="${pageContext.request.contextPath}/on/cashbook?targetYear=${targetYear}&targetMonth=${targetMonth - 1}">이전달</a>
			<a href="${pageContext.request.contextPath}/on/cashbook?targetYear=${targetYear}&targetMonth=${targetMonth + 1}">다음달</a>
		</div>
		
		<!-- --------------------------------------------------- 가계부 달력 시작 ------------------------------------------------------------- -->
		<div>
			<h2>이달의 해시태그</h2>
			<div>
				<c:forEach var="m" items="${htList}">
					<a href="${pageContext.request.contextPath}/on/cashbookListByTag?hashtag=${m.word}">${m.word}(${m.cnt})</a>
				</c:forEach>
			</div>
		</div>
		
		<div>
			<table class="table table-bordered">
				<tr>
					<th>일요일</th>
					<th>월요일</th>
					<th>화요일</th>
					<th>수요일</th>
					<th>목요일</th>
					<th>금요일</th>
					<th>토요일</th>
				</tr>
				<tr>
					<c:forEach var="i" begin="0" end="${totalCell-1}" step="1">
						<c:set var="d" value="${i-beginBlank+1}"></c:set>
						
						<c:if test="${i!=0 && i%7 == 0}">
							</tr><tr>
						</c:if>
						
						<c:if test="${d < 1 || d > lastDate}">
							<td>&nbsp;</td>
						</c:if>
						
						<c:if test="${!( d < 1 || d > lastDate)}">
							<td>
								<div><a href="${pageContext.request.contextPath}/on/cashbookListByDate?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${d}">${d}</a></div>
								<c:forEach var="c" items="${list}">
									<c:if test="${d == fn:substring(c.cashbookDate, 8, 10)}">
										<div>
											<c:if test="${c.category == '수입'}">
												<span>${c.price}</span>
											</c:if>
											<c:if test="${c.category == '지출'}">
												<span>-</span><span id="price" style="color:red">${c.price}</span>
											</c:if>
										</div>
									</c:if>
								</c:forEach>
							</td>
						</c:if>
					</c:forEach>
			</table>
		</div>
	</div>
</body>
</html>