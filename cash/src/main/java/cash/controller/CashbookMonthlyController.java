package cash.controller;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cash.service.CashbookService;
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/on/cashbookListByMonth")
public class CashbookMonthlyController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 인증 검사 (null인 경우 login으로 리다이렉션 후 종료) -> 필터로 처리
		Object o = request.getSession().getAttribute("loginMember");
		String memberId = "";
		if(o instanceof Member) {
			memberId = ((Member)o).getMemberId();
		}
		
		Calendar today = Calendar.getInstance();
		int targetYear = today.get(Calendar.YEAR);
		int targetMonth = today.get(Calendar.MONTH);
		if(request.getParameter("targetYear") != null
				&& request.getParameter("targetMonth") != null) {
			targetYear = Integer.parseInt(request.getParameter("targetYear"));
			targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		}
		
		//해당 날짜의 모델값 불러오기 
		CashbookService cashbookService = new CashbookService();
		List<Cashbook> list = cashbookService.getMonthlyCashbook(memberId, targetYear, targetMonth);
		
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("list", list);
		
		//달력을 출력하는 뷰로 포워드
		request.getRequestDispatcher("/WEB-INF/view/on/cashbookListByMonth.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
