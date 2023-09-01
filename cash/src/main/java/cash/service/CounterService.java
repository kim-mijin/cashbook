package cash.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cash.dao.CounterDao;

public class CounterService {
	private CounterDao counterDao;
	//오늘 방문자 수 입력
	public void addCounter() {
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false);
			
			this.counterDao = new CounterDao();
			counterDao.insertCounter(conn);
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("CounterService.getTotalVisitors 예외발생");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//오늘 방문자 수 +1
	public void modifyCounter() {
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false);
			
			this.counterDao = new CounterDao();
			counterDao.updateCounter(conn);
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("CounterService.getTotalVisitors 예외발생");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//오늘 방문자수
	public int getTodayCounter() {
		int todayCount = 0;
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false);
			
			this.counterDao = new CounterDao();
			todayCount = counterDao.selectTodayCounter(conn);
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("CounterService.getTotalVisitors 예외발생");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return todayCount;
	}
	
	//누적 방문자수
	public int getTotalCounter() {
		int totalCount = 0;
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false);
			
			this.counterDao = new CounterDao();
			totalCount = counterDao.selectTotalCounter(conn);
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("CounterService.getTotalVisitors 예외발생");
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return totalCount;
	}
}
