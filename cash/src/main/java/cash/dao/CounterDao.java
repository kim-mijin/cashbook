package cash.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CounterDao {
	//오늘 날짜의 방문자수가 0일때
	public void insertCounter(Connection conn) throws SQLException {
		PreparedStatement stmt = null;
		try {
			String sql = "INSERT INTO counter (counter_date, counter_num) VALUES (CURDATE(), 1)";
			stmt = conn.prepareStatement(sql);
			int row = stmt.executeUpdate();
			System.out.println(row + "CounterDao.insertCounter() row");
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//오늘 날짜의 방문자 수가 0이 아닐 때
	public void updateCounter(Connection conn) throws SQLException {
		PreparedStatement stmt = null;
		try {
			String sql = "UPDATE counter SET counter_num = counter_num+1 WHERE counter_date = CURDATE()";
			stmt = conn.prepareStatement(sql);
			int row = stmt.executeUpdate();
			System.out.println(row + "CounterDao.updateCounter() row");
		} finally {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	//오늘날짜 방문자수 반환
	public int selectTodayCounter(Connection conn) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int todayCount = 0;
		try {
			String sql = "SELECT counter_num counterNum FROM counter WHERE counter_date = CURDATE()";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				todayCount = rs.getInt("counterNum");
			}
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return todayCount;
	}
	
	//누적 방문자수 반환
	public int selectTotalCounter(Connection conn) throws SQLException {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int totalCount = 0;
		try {
			String sql = "SELECT SUM(counter_num) totalCount FROM counter";
			stmt = conn.prepareStatement(sql);
			rs = stmt.executeQuery();
			if(rs.next()) {
				totalCount = rs.getInt("totalCount");
			}
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return totalCount;
	}
}
