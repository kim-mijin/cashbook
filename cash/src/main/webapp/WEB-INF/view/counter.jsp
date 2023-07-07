<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>counter</title>
</head>
<body>
	<div>
		현재 방문자 수: ${currCounter} <!-- session.getAttribute("currCounter") -->
	</div>
	<div>
		오늘 방문자 수: ${todayCounter} <!-- request.getAttribute("todayCounter") -->
	</div>
	<div>
		누적 방문자 수: ${totalCounter} <!-- request.getAttribute("totalCounter") -->
	</div>
</body>
</html>