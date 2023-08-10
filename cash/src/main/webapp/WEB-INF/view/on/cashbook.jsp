<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%><!-- jsp컴파일시 자바코드로 변환되는 c:...(제어문 코드) 커스텀태그 사용 가능 -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><!-- 문자열 관련 메소드 사용 (substring) -->
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
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
<c:import url="/resources/inc/head.jsp"></c:import>

</head>
<body>
	<!-- 네비게이션 시작 -->
	<div>
		<c:import url="/resources/inc/header.jsp"></c:import>
	</div>
	<!-- 네비게이션 끝 -->
	
	<div class="container">
		<!-- 방문자수 -->
		<div class="mt-3 mb-3 text-right">
			<c:import url="/on/counter"></c:import>
		</div>
	
		<!-- 변수값 or 반환값 : EL(Expression Language)사용 달러{...} -->
		<!-- 
			속성값 대신 EL사용 : 달러{표현식}
			request.getAttribute("endBlank") -> requestScope.targetYear (requestScope 생략가능)
			형변환 연산이 필요 없다(EL이 자동으로 처리)
		 -->
		 
		<!-- 자바코드(제어문) : JSTL 사용 -->
		<div class="text-center">
			<a href="${pageContext.request.contextPath}/on/cashbook?targetYear=${targetYear}&targetMonth=${targetMonth - 1}"><span class="badge rounded-pill bg-primary">&lt;&lt;&nbsp;</span></a>
			<a href="${pageContext.request.contextPath}/on/cashbookListByMonth?targetYear=${targetYear}&targetMonth=${targetMonth}"><span class="h1 text-weight-bold">${targetYear}년 ${targetMonth + 1}월</span></a>
			<a href="${pageContext.request.contextPath}/on/cashbook?targetYear=${targetYear}&targetMonth=${targetMonth + 1}"><span class="badge rounded-pill bg-primary">&nbsp;&gt;&gt;</span></a>
		</div>
		<!-- --------------------------------------------------- 가계부 달력 시작 ------------------------------------------------------------- -->
		<div>
			<div class="card border mt-3 mb-3 shadow-sm" style="max-width: 100%;">
			<div class="card-header bg-secondary text-center text-white">이달의 해시태그</div>
			<div class="card-body text-center">
				<p class="card-text">
					<c:forEach var="m" items="${htList}">
						<a href="${pageContext.request.contextPath}/on/cashbookListByTag?hashtag=${m.word}">${m.word}(${m.cnt})</a>
					</c:forEach>
				</p>
			</div>
			</div>
		</div>
		
		<div class="table-responsive-md">
			<table class="table table-bordered shadow-sm p-3 mb-3 bg-body border">
				<thead>
					<tr class="table-secondary text-center border-white">
						<td>일요일</td>
						<td>월요일</td>
						<td>화요일</td>
						<td>수요일</td>
						<td>목요일</td>
						<td>금요일</td>
						<td>토요일</td>
					</tr>
				</thead>
				<tbody>
					<tr>
						<c:forEach var="i" begin="0" end="${totalCell-1}" step="1">
							<c:set var="d" value="${i-beginBlank+1}"></c:set>
							
							<c:if test="${i!=0 && i%7 == 0}">
								</tr><tr>
							</c:if>
							
							<c:if test="${d < 1 || d > lastDate}">
								<td id="calendar border-secondary">&nbsp;</td>
							</c:if>
							
							<c:if test="${!( d < 1 || d > lastDate)}">
								<td id="calendar">
									<div><a href="${pageContext.request.contextPath}/on/cashbookListByDate?targetYear=${targetYear}&targetMonth=${targetMonth}&targetDate=${d}"><span class="h6">${d}</span></a></div>
									<c:forEach var="c" items="${list}">
										<c:if test="${d == fn:substring(c.cashbookDate, 8, 10)}">
											<div class="text-right">
												<c:if test="${c.category == '수입'}">
													<span class="text-primary">+<fmt:formatNumber value="${c.price}"></fmt:formatNumber>원</span>
												</c:if>
												<c:if test="${c.category == '지출'}">
													<span class="text-secondary">-<fmt:formatNumber value="${c.price}"></fmt:formatNumber>원</span>
												</c:if>
											</div>
										</c:if>
									</c:forEach>
								</td>
							</c:if>
						</c:forEach>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</body>
<script>
	$.ajax({
	 async : false, //true (비동기, 기본값이다), false(동기)
	  url : '/HomeRest',
	  type : 'get',
	  data:{  id : 'id' },
	  success : function(model) {
	  	console.log('성공');
	  },
	  error : function() {
	    console.log("아작스실패");
	  }
	}); 
 </script>

</html>