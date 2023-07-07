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

@WebFilter("/*") //모든 서블릿이 요청될 때
public class EncodingFilter extends HttpFilter implements Filter {
    
	//Filter인터페이스의 디폴트 메서드
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//요청 서블릿이 실행되기 전에 요청값 인코딩
		request.setCharacterEncoding("utf-8");
		
		chain.doFilter(request, response);
		
	}

	//Filter인터페이스의 디폴트 메서드
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
