package cash.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.dao.CashbookDao;
import cash.dao.HashtagDao;
import cash.service.CashbookService;
import cash.vo.Cashbook;
import cash.vo.Hashtag;
import cash.vo.Member;

@WebServlet("/on/modifyCashbook")
public class ModifyCashbookController extends HttpServlet {
	//뷰 페이지
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 유효성 검사 -> 필터로 처리
		Object o = request.getSession().getAttribute("loginMember"); 
		String memberId = ""; 
		if(o instanceof Member) { memberId = ((Member)o).getMemberId(); }
		 
		//request 매개값
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		
		String monthStr = "";
		String dateStr = "";
		if(targetMonth<9) {
			monthStr = "0" + (targetMonth + 1);
		} else {
			monthStr = "" + targetMonth;
		}
		if(targetDate < 10) {
			dateStr = "0" + targetDate;
		} else {
			dateStr = "" + targetDate;
		}
		String targetDayStr = targetYear + "-" + monthStr + "-" + dateStr;
		System.out.println(targetDayStr);
		
		//가계부를 가져오는 메서드 실행
		CashbookService cashbookService = new CashbookService();
		Cashbook cashbook = cashbookService.getCashbookByNo(cashbookNo);
		
		//request에 담기
		request.setAttribute("memberId", memberId);
		request.setAttribute("cashbook", cashbook);
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("targetDate", targetDate);
		request.setAttribute("targetDayStr", targetDayStr);
		
		//뷰 페이지로 포워드
		request.getRequestDispatcher("/WEB-INF/view/on/modifyCashbook.jsp").forward(request, response);
	}
	
	//가계부 수정 액션
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 유효성 검사
		HttpSession session = request.getSession();
		if(session.getAttribute("loginMember") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		Object o = session.getAttribute("loginMember"); 
		String memberId = ""; 
		if(o instanceof Member) { 
			memberId = ((Member)o).getMemberId(); 
		}
		
		//request 매개값
		request.setCharacterEncoding("utf-8");
		
		int cashbookNo = Integer.parseInt(request.getParameter("cashbookNo"));
		String category = request.getParameter("category");
		String memo = request.getParameter("memo");
		int price = Integer.parseInt(request.getParameter("price"));
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		
		//cashbook타입에 담기
		Cashbook cashbook = new Cashbook();
		cashbook.setCashbookNo(cashbookNo);
		cashbook.setCategory(category);
		cashbook.setPrice(price);
		cashbook.setMemo(memo);
		
		//수정 메서드 실행
		CashbookService cashbookSerivce = new CashbookService();
		
		//request속성에 값 저장
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("targetDate", targetDate);
		
		//리다이렉션
		response.sendRedirect(request.getContextPath()+"/on/cashbookListByDate?targetYear="+targetYear+"&targetMonth="+targetMonth+"&targetDate="+targetDate);
		
	}
}
