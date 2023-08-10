package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.service.CounterService;

@WebServlet("/on/counter")
public class CounterController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//오늘 방문자 수
		CounterService counterService = new CounterService();
		int todayCounter = counterService.getTodayCounter();
		
		//누적방문자 수
		int totalCounter = counterService.getTotalCounter();
		
		//request속성 영역에 담기
		request.setAttribute("todayCounter", todayCounter);
		request.setAttribute("totalCounter", totalCounter);
		
		//현재방문자수와 누적방문자수를 보여주는 뷰로 포워드
		request.getRequestDispatcher("/resources/inc/counter.jsp").forward(request, response);
	}

}
