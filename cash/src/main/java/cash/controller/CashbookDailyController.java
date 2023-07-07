package cash.controller;

import java.io.IOException;

import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.dao.CashbookDao;
import cash.service.CashbookService;
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/on/cashbookListByDate")
public class CashbookDailyController extends HttpServlet {
	//뷰 페이지
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 유효성 검사 (로그인 null이면 login컨트롤러로 리다이렉션 후 종료) -> Filter로 처리
		Object o = request.getSession().getAttribute("loginMember");
		String memberId = "";
		if(o instanceof Member) {
			memberId = ((Member)o).getMemberId();
		}
		
		//요청값 유효성 검사 : targetYear, targetMonth, targetDate가 null이 아닌 경우 오늘 날짜 보여주기
		Calendar today = Calendar.getInstance();
		int targetYear = today.get(Calendar.YEAR);
		int targetMonth = today.get(Calendar.MONTH);
		int targetDate = today.get(Calendar.DATE);
		if(request.getParameter("targetYear") != null
				&& request.getParameter("targetMonth") != null
				&& request.getParameter("targetDate") != null) {
			targetYear = Integer.parseInt(request.getParameter("targetYear"));
			targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
			targetDate = Integer.parseInt(request.getParameter("targetDate"));
		}
		System.out.println(targetYear + " <--CashbookDailyController targetYear");
		System.out.println(targetMonth + " <--CashbookDailyController targetMonth"); //dao 메서드 sql 작성시에는 targetMonth + 1
		System.out.println(targetDate + " <--CashbookDailyController targetDate");
		
		//해당 날짜의 모델값 불러오기 
		CashbookService cashbookService = new CashbookService();
		List<Cashbook> list = cashbookService.getDailyCashbook(memberId, targetYear, targetMonth, targetDate);
		
		//request속성으로 모델 값 보내기
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("targetDate", targetDate);
		request.setAttribute("list", list);
		
		//cashbook.jsp 뷰 페이지로 forward
		request.getRequestDispatcher("/WEB-INF/view/cashbookListByDate.jsp").forward(request, response);
	}
}
