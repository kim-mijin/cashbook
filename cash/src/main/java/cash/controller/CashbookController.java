package cash.controller;

import java.io.IOException;

import java.util.Calendar;
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
import cash.vo.Cashbook;
import cash.vo.Member;

@WebServlet("/cashbook")
public class CashbookController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 인증 검사 (null인 경우 login으로 리다이렉션 후 종료)
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
		
		//view에 넘겨줄 달력정보(모델값) : 이번달의 1일
		Calendar firstDay = Calendar.getInstance(); //오늘날짜
		
		//출력하고자 하는 년도와 월이 매개값으로 없으면 오늘 날짜 기준
		int targetYear = firstDay.get(Calendar.YEAR);
		int targetMonth = firstDay.get(Calendar.MONTH);
		firstDay.set(Calendar.DATE, 1); //이번달의 1일
		
		//출력하고자 하는 년도와 월이 매개값으로 넘어온 경우에는 해당 년도, 월의 달력을 출력 
		if(request.getParameter("targetYear") != null && request.getParameter("targetMonth") != null) {
			targetYear = Integer.parseInt(request.getParameter("targetYear"));
			targetMonth = Integer.parseInt(request.getParameter("targetMonth"));
			firstDay.set(Calendar.YEAR, targetYear); 
			//API에서 자동으로 year를 먼저 세팅해야 month값이 -1, 12일때 자동으로 month, year값이 적용된다 -> targetYear와 targetMonth의 값은 바뀌지 않으므로 아래에서 변수에 다시 저장
			firstDay.set(Calendar.MONTH, targetMonth); //0~11(1월~12월)
		}
		// *** api가 적용된 targetYear와 targetMonth값을 저장 ***
		targetYear = firstDay.get(Calendar.YEAR);
		targetMonth = firstDay.get(Calendar.MONTH);
		System.out.println(targetYear + " <--targetYear");
		System.out.println(targetMonth + " <--targetMonth");
		
		//달력출력 시 시작 공백 -> //1일의 요일(1(일)~7(토)) - 1
		int beginBlank = firstDay.get(Calendar.DAY_OF_WEEK) - 1;
		System.out.println(beginBlank + " <--beginBlank");
		
		//달력 출력 시 마지막의 공백
		//마지막 날짜
		int lastDate = firstDay.getActualMaximum(Calendar.DATE);
		System.out.println(lastDate + " <--lastDate");
		//달력출력 시 마지막 공백 -> 전체 출력 셀의 개수가 7로 나누어 떨어져야 한다
		int endBlank = 0;
		if((beginBlank + lastDate) % 7 != 0) {
			endBlank = 7 - ((beginBlank + lastDate) % 7);
		}
		System.out.println(endBlank + " <--endBlank");
		int totalCell = beginBlank + lastDate + endBlank;
		System.out.println(totalCell + " <--totalCell");
		
		//모델을 호출(DAO 타겟 수입, 지출 데이터)
		CashbookDao cashbookDao = new CashbookDao();
		HashtagDao hashtagDao = new HashtagDao();
		List<Cashbook> list = cashbookDao.selectCashbookListByMonth(memberId, targetYear, targetMonth);
		List<Map<String,Object>> htList = hashtagDao.selectWordCountByMonth(memberId, targetYear, targetMonth+1);
		
		//뷰에 값을 넘기기(request 속성)
		//넘겨야 할 매개값이 많고 자주 사용될 것이라면 dto타입을 만들거나 맵으로 넘길 수 있다.
		//그러나 가독성을 위해서 당분간은 개별값으로 넘긴다
		request.setAttribute("targetYear", targetYear);
		request.setAttribute("targetMonth", targetMonth);
		request.setAttribute("lastDate", lastDate);
		request.setAttribute("totalCell", totalCell);
		request.setAttribute("beginBlank", beginBlank);
		request.setAttribute("endBlank", endBlank);
		
		request.setAttribute("list", list);
		request.setAttribute("htList", htList);
		
		//달력을 출력하는 뷰로 포워드
		request.getRequestDispatcher("/WEB-INF/view/cashbook.jsp").forward(request, response);
	}

}
