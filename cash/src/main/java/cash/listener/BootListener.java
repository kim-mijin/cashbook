package cash.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class BootListener implements ServletContextListener {
	//톰캣이 켜질때 실행되는 메서드
	@Override
    public void contextInitialized(ServletContextEvent sce)  { 
        System.out.println("BootListener.contextInitialized() 실행확인");
        
        //톰캣이 실행될 때 현재 접속자 수는 0으로 세팅
        ServletContext application = sce.getServletContext();
        application.setAttribute("currCounter", 0);
        
		/*
		 MariaDB 드라이버는 모든 dao에서 사용하므로 매번 메모리에 올릴 필요없이 한 번만 올라가면 종료 전까지 사용가능
		 톰캣이 켜질 때 드라이버를 로딩하면 된므로 Listener로 처리
		 */
		//MariaDB 드라이버 로딩
		try {
			Class.forName("org.mariadb.jdbc.Driver"); 
		} catch (ClassNotFoundException e) {
			System.out.println("MariaDB 드라이버 로딩 실패");
			e.printStackTrace();
		}
		System.out.println("MariaDB 드라이버 로딩 성공");
    }
	
}
