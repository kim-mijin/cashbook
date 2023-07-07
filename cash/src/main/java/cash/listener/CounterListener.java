package cash.listener;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import cash.service.CounterService;

@WebListener //Listener는 Filter와 달리 서블릿이 요청될 때가 아니라 특정이벤트가 발생할 때 실행되므로 맵핑을 하지 않는다
public class CounterListener implements HttpSessionListener {
	private CounterService counterService;
	
	@Override
	public void sessionCreated(HttpSessionEvent se)  { 
		System.out.println(se.getSession().getId() + " 세션이 생성되었습니다");
		//방문자수 count
		//세션이 만들어지면 현재방문자 수+1 -> application 속성영역
		//db방문자수 +1 -> db
		ServletContext application = se.getSession().getServletContext(); //session이 만들어진 servlet context 받아오기
		int currCounter = (Integer)(application.getAttribute("currCounter"));
		application.setAttribute("currCounter", currCounter+1);
		
		//오늘 방문자 수가 0인 경우 1로 저장, 0이 아닌 경우는 +1
		this.counterService = new CounterService();
		int todayCount = counterService.getTodayCounter();
		if(todayCount == 0) {
			counterService.addCounter();
		} else {
			counterService.modifyCounter();
		}
    }
	
	@Override
    public void sessionDestroyed(HttpSessionEvent se)  { 
		System.out.println(se.getSession().getId() + " 세션이 종료되었습니다");
		//세션 종료하면 현재 방문자수 -1
		ServletContext application = se.getSession().getServletContext(); //session이 만들어진 servlet context 받아오기
		int currCounter = (Integer)(application.getAttribute("currCounter"));
		application.setAttribute("currCounter", currCounter-1);
    }
	
}
