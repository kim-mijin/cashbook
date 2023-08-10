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

@WebServlet("/on/addCashbook")
public class AddCashbookController extends HttpServlet {
	//입력폼
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 유효성 검사 : 로그인이 되어있지 않으면 로그인컨트롤러로 리다이렉션 -> Filter로 처리
		Object o = request.getSession().getAttribute("loginMember");
		String memberId = "";
		if(o instanceof Member) {
			memberId = ((Member)o).getMemberId();
		}
		
		//request 매개값
		request.setCharacterEncoding("utf-8");
		
		int targetYear = Integer.parseInt(request.getParameter("targetYear"));
		int targetMonth = Integer.parseInt(request.getParameter("targetMonth")); //0~11
		int targetDate = Integer.parseInt(request.getParameter("targetDate"));
		String targetMonthStr = "";
		String targetDateStr = "";
		if(targetMonth < 9) {
			targetMonthStr = "0"+(targetMonth + 1);
		}
		if(targetDate < 10) {
			targetDateStr = "0"+targetDate;
		}
		String cashbookDate = targetYear + "-" + targetMonthStr + "-" + targetDateStr;
		System.out.println(cashbookDate + " <--AddCashbookController doGet cashbookDate");
		request.setAttribute("memberId", memberId);
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("targetDate", targetDate);
		request.setAttribute("cashbookDate", cashbookDate);
		
		//입력폼으로 forward
		request.getRequestDispatcher("/WEB-INF/view/on/addCashbook.jsp").forward(request, response);
	}
	
	//입력액션
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//사용자가 입력한 매개값
		//인코딩
		request.setCharacterEncoding("utf-8");
		
		String targetYear = request.getParameter("targetYear");
		String targetMonth = request.getParameter("targetMonth");; //0~11월
		String targetDate = request.getParameter("targetDate");;
		String cashbookDate = request.getParameter("cashbookDate");
		String memberId = request.getParameter("memberId");
		String category = request.getParameter("category");
		int price = Integer.parseInt(request.getParameter("price"));
		String memo = request.getParameter("memo");
		
		//request 매개값을 Cashbook VO타입으로 묶기
		Cashbook cashbook = new Cashbook();
		cashbook.setMemberId(memberId);
		cashbook.setCategory(category);
		cashbook.setCashbookDate(cashbookDate);
		cashbook.setPrice(price);
		cashbook.setMemo(memo);
		
		//가계부 입력 메서드 실행
		CashbookService addCashbookService = new CashbookService();
		String msg = addCashbookService.addCashbook(cashbook); //실행 성공하면 null, 예외 발생하면 예외 메시지 반환
		
		request.setAttribute("msg", msg);
		
		//cashbookDaily로 리다이렉트
		response.sendRedirect(request.getContextPath()+"/on/cashbookListByDate?targetYear="+targetYear+"&targetMonth="+targetMonth+"&targetDate="+targetDate);
	}

}
