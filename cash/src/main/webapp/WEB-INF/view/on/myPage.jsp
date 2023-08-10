<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<c:import url="/resources/inc/head.jsp"></c:import>
</head>
<body>
	${memberId}님의 통계
	
	<div>
		<canvas id="target1" style="width:100%;max-width:700px"></canvas>
	</div>
	<div>
		<canvas id="target2" style="width:100%;max-width:700px"></canvas>
	</div>	
	
	
	<script>
		const x = [];
		const y = [];
		
		$.ajax({
			async : false, // true(비동기:기본값), false(동기)
			url : '${pageContext.request.contextPath}/on/chart',
			type : 'get',
			success : function(model) {
				// chart모델 생성
				x.push(item.localName);
				y.push(item.cnt);
			});
				
				
		new Chart("target1", {
			  type: "bar",
			  data: {
			    labels: x,
			    datasets: [{
			      // backgroundColor: barColors,
			      data: y
			    }]
			  },
			  // options: {...}
		});
	</script>

</body>
</html>