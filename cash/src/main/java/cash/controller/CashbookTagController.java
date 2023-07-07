package cash.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import cash.vo.Member;

@WebServlet("/on/cashbookListByTag")
public class CashbookTagController extends HttpServlet {
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 유효성 검사 : 로그인 안되어있으면 login으로 리다이렉션 -> 필터로 처리
		Object o = request.getSession().getAttribute("loginMember");
		String memberId = "";
		if(o instanceof Member) {
			memberId = ((Member)o).getMemberId();
		}
		
		//request 매개값
		String hashtag = request.getParameter("hashtag");
		int currentPage = 1; //현재페이지의 초기값은 1
		int rowPerPage = 10; //보기의 초기값은 10개씩
		if(request.getParameter("currPage") != null) {
			currentPage = Integer.parseInt(request.getParameter("currentPage"));
		}
		if(request.getParameter("rowPerPage") != null) {
			rowPerPage = Integer.parseInt(request.getParameter("rowPerPage"));
		}
		int startIdx = (currentPage-1)*rowPerPage;
		
		//DAO 메서드 실행하여 list를 변수에 저장
		CashbookService cashbookService = new CashbookService();
		Map<String, Object> m = cashbookService.getCashbookListByTagPage(memberId, hashtag, startIdx, rowPerPage);
		List<Cashbook> list = (List)m.get("list");
		
		//리스트 페이지네이션
		int totalCnt = (int)m.get("cnt");
		int lastPage = totalCnt / rowPerPage;
		if(totalCnt % rowPerPage != 0) {
			lastPage += 1;
		}
		int pagePerBlock = 10;
		//현재페이지가 있는 블럭의 시작과 마지막 페이지
		int startPageInBlock = ((currentPage - 1)/pagePerBlock)*pagePerBlock + 1;
		int endPageInBlock = startPageInBlock + pagePerBlock;
		//페이지블럭의 마지막 페이지가 lastPage보다 크면 lastPage가 그 블럭의 마지막 페이지가 된다
		if(endPageInBlock > lastPage) {
			endPageInBlock = lastPage;
		}
		System.out.println(totalCnt + " <--CashbookTagController totalCnt");
		System.out.println(lastPage + " <--CashbookTagController lastPage");
		System.out.println(startPageInBlock + " <--CashbookTagController startPageInBlock");
		System.out.println(endPageInBlock + " <--CashbookTagController endPageInBlock");
		
		//request 속성에 저장
		request.setAttribute("hashtag", hashtag);
		request.setAttribute("list", list);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("rowPerPage", rowPerPage);
		request.setAttribute("lastPage", lastPage);
		request.setAttribute("startPageInBlock", startPageInBlock);
		request.setAttribute("endPageInBlock", endPageInBlock);
		
		//해시태그 뷰 페이지(hashtagList.jsp)로 포워드
		request.getRequestDispatcher("/WEB-INF/view/cashbookListByTag.jsp").forward(request, response);
	}


}
