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

@WebServlet("/login") //LoginController서블릿을 login이름으로 맵핑 -> login에 요청하면 LoginController가 응답
public class LoginController extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//session 인증 검사 코드 : 로그인이 된 사람은 cashbook으로 리다이렉션
		HttpSession session = request.getSession();
		if(session.getAttribute("memberId") != null) { 
			response.sendRedirect(request.getContextPath()+"/cashbook");
			return;
		}
		request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response); //login으로 get방식 요청이 들어오면 login.jsp로 forward
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//login으로 post방식 요청이 들어오면 아래 코드 실행
		String memberId = request.getParameter("memberId");
		String memberPw = request.getParameter("memberPw");
		
		Member member = new Member(memberId, memberPw, null, null); //vo에 필드를 매개변수로 하는 생성자를 만들어두었다.
		MemberService memberService = new MemberService();
		Member loginMember = memberService.login(member); //로그인을 위한 메서드 실행
		//null이면 로그인 실패
		
		if(loginMember == null) {
			System.out.println("로그인 실패");
			response.sendRedirect(request.getContextPath()+"/login"); //로그인 실패하면 login서블릿으로 리다이렉션
			return;
		} 
		
		//로그인 성공 : session 사용
		HttpSession session = request.getSession(); //request의 세션을 받아온다
		System.out.println("로그인 성공");
		session.setAttribute("loginMember", loginMember); //세션에 login정보 저장
		response.sendRedirect(request.getContextPath()+"/cashbook"); //로그인 성공하면 cashbook서블릿으로 리다이렉션
	}

}
