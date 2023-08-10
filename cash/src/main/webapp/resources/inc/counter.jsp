<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
현재 방문자 : ${currCounter}명 <!-- session.getAttribute("currCounter") -->
 | 오늘 방문자 : ${todayCounter}명 <!-- request.getAttribute("todayCounter") -->
 | 누적 방문자 : ${totalCounter}명 <!-- request.getAttribute("totalCounter") -->
