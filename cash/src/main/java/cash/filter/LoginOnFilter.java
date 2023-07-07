package cash.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/on/*") //로그인 상태에서만 접근 가능한 서블릿이 요청될 때
public class LoginOnFilter extends HttpFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		System.out.println("LoginOnFilter 서블렛 접근 전 실행");
		
		//로그인이 안되어 있으면 login 페이지로 리다이렉션
		HttpServletRequest req = (HttpServletRequest)request; //request(부모타입)를 HttpServletRequest(자식타입)로 형변환
		HttpServletResponse res = (HttpServletResponse)response;
		HttpSession session = req.getSession();
		
		if(session.getAttribute("loginMember") == null) {
			res.sendRedirect(req.getContextPath()+"/off/login");
			return;
		}
		
		chain.doFilter(request, response);
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
