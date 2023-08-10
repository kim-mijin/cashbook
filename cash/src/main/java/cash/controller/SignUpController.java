package cash.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cash.dao.MemberDao;
import cash.service.MemberService;
import cash.vo.Member;

@WebServlet("/off/signUp")
public class SignUpController extends HttpServlet {
	
	//회원가입 폼
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 유효성 검사(null일 때) : 로그인 되어 있으면 calendar로 가기 -> Filter로 처리
		
		//로그인 안되어 있으면 뷰 페이지로 포워드
		request.getRequestDispatcher("/WEB-INF/view/off/signUp.jsp").forward(request, response);
	}
	
	//회원가입 액션
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//request.getParameter()
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		//회원가입 dao호출
		MemberService memberService = new MemberService();
		int row = memberService.signup(member);
		if(row == 0) { //회원가입 실패 시
			//addMember.jsp로 이동하는 controller를 리다이렉트
			response.sendRedirect(request.getContextPath()+"/off/signUp");
		} else if (row == 1){ //회원 가입 성공 시
			//login.jsp로 이동하는 controller를 리다이렉트
			response.sendRedirect(request.getContextPath()+"/off/login");			
		} else {
			System.out.println("sign up fails");
		}
	}
}
