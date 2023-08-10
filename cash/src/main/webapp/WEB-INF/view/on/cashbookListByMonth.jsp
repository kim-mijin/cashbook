<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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

	<div class="container mt-3">
		<div class="text-center">
			<a href="${pageContext.request.contextPath}/on/cashbookListByMonth?targetYear=${targetYear}&targetMonth=${targetMonth - 1}"><span class="badge rounded-pill bg-primary">&lt;&lt;&nbsp;</span></a>
			<a href="${pageContext.request.contextPath}/on/cashbook?targetYear=${targetYear}&targetMonth=${targetMonth}"><span class="h1 text-weight-bold">${targetYear}년 ${targetMonth + 1}월</span></a>
			<a href="${pageContext.request.contextPath}/on/cashbookListByMonth?targetYear=${targetYear}&targetMonth=${targetMonth + 1}"><span class="badge rounded-pill bg-primary">&nbsp;&gt;&gt;</span></a>
		</div>
		
		<div class="text-right">
			<button class="btn btn-primary" id="excelBtn">엑셀 다운로드</button>
		</div>
		
		<div class="table-responsive-md">
			<table class="table table-bordered mt-3">
				<thead>
					<tr class="text-center table-secondary text-white border">
						<th>수입/지출</th>
						<th>날짜</th>
						<th>내용</th>
						<th>금액</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="c" items="${list}">
						<tr class="data">
							<td class="category text-center">${c.category}</td>
							<td class="text-center">${c.cashbookDate}</td>
							<td class="text-left">${c.memo}</td>
							<td class="text-right"><fmt:formatNumber value="${c.price}"></fmt:formatNumber>원</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
	
	<!-- 자바스크립트 -->
	<script>
		const targetYear = '<c:out value = "${targetYear}"/>';
		const targetMonth = '<c:out value = "${targetMonth}"/>'
		
		let data = [];
		$('#excelBtn').click(function(){
			$.ajax({
				async: false,
				url: '${pageContext.request.contextPath}/on/excel',
				type: 'get',
				data: {
					targetYear : targetYear,
					targetMonth : targetMonth
				},
				success:function(result){// data : json -> js 배열 -> sheetjs 자료구조 -> 다운로드
					console.log('ajax 성공');
					$(result).each(function(index, item){
						let arr = [];
						arr.push(item.category);
						arr.push(item.cashbookDate);
						arr.push(item.memo);
						arr.push(item.price);
						
						data.push(arr);
					});
				},
				error:function(){
					console.log('ajax 실패');
				}
			});
			// 1) 엑셀파일 생성
			let book = XLSX.utils.book_new(); 
			// 2) 1안에 빈시트와 이름 생성
			book.SheetNames.push('new');
			// 3) 시트생성
			let sheet = XLSX.utils.aoa_to_sheet(data);
			// 4) 2번과 3번을 연결
			book.Sheets['new'] = sheet;
			// 5) book -> 기계어파일로
			let source = XLSX.write(book,{bookType:'xlsx',type:'binary'});
			// 6) 다운로드
			// 6-1) source 크기의 빈 스트림 생성
			let buf = new ArrayBuffer(source.length);
			// 6-2) 8비트(1byte) 배열로 버프 랩핑 -> 1byte씩 옮길려고..
			let buf2 = new Uint8Array(buf);
			
			for(let i=0; i<source.length; i++){
				buf2[i] = source.charCodeAt(i) & 0xFF; 
			}
			
			let b = new Blob([buf],{type:"application/octet-stream"});
			saveAs(b,"cashbook.xlsx");
		});
	</script>
	<!-- 자바스크립트 -->
</body>
</html>