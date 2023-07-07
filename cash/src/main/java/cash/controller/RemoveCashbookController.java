package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.dao.CashbookDao;
import cash.dao.HashtagDao;
import cash.service.CashbookService;
import cash.vo.Member;

/**
 * Servlet implementation class removeCashbookController
 */
@WebServlet("/on/removeCashbook")
public class RemoveCashbookController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 유효성 검사 -> Filter로 처리
		//request 매개값 (cashbookNo)
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		
		//삭제 메서드 실행
		CashbookService cashbookService = new CashbookService();
		String msg = cashbookService.removeCashbook(cashbookNo);
		
		//cashbookListByDate 컨트롤러로 리다이렉션
		response.sendRedirect(request.getContextPath()+"/on/cashbookListByDate?targetYear="+targetYear+"&targetMonth="+targetMonth+"&targetDate="+targetDate);
		
	}
}
