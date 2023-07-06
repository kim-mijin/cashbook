package cash.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import cash.dao.MemberDao;
import cash.service.MemberService;

@WebServlet("/idCk")
public class IdValidationController extends HttpServlet {
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		String memberId = request.getParameter("memberId");
		System.out.println(memberId + " <--IdValidation memberId");
		
		//아이디 유효성 검사 dao
		MemberService memberService = new MemberService();
		int idCount = memberService.idCheck(memberId);
		System.out.println(idCount + " <--IdValidation idCount");
		Gson gson = new Gson();
		String jsonStr = gson.toJson(idCount);
		out.print(jsonStr);
	}

}
