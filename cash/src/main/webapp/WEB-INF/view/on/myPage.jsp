<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="/resources/inc/head.jsp"></c:import>
</head>
<body>
	<!-- 네비게이션바 시작 -->
	<div>
		<c:import url="/resources/inc/header.jsp"></c:import>
	</div>
	<!-- 네비게이션바 끝 -->

	<div class="container">
		<div class=mt-3 mb-3>
			<h1>${memberId}님의 통계</h1>
		</div>
		
		<div>
			<canvas id="target1" style="width:100%;max-width:700px"></canvas>
		</div>
		<div>
			<canvas id="target2" style="width:100%;max-width:700px"></canvas>
		</div>	
		</div>
	
	<script>
		const targetYear = '<c:out value="${targetYear}"/>';
		const targetMonth = '<c:out value="${targetMonth}"/>';
		const x = [];
		const y = [];
		
		$.ajax({
			async : false, // true(비동기:기본값), false(동기)
			url : '${pageContext.request.contextPath}/on/chart',
			type : 'get',
			data : {
				targetYear : targetYear,
				targetMonth : 5
			},
			success : function(model) {
				console.log(model);
				console.log('ajax 성공');
				// chart모델 생성
				$(model).each(function(index, item){
					x.push(item.category);
					y.push(item.sumPrice);
				})
			}
		});
				
				
		new Chart("target1", {
			  type: "bar",
			  data: {
			    labels: x,
			    datasets: [{
			      data: y
			    }]
			  },
		});
	</script>

</body>
</html>