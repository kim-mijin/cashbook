package cash.dao;

import java.sql.Connection;



import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import cash.vo.Cashbook;

public class CashbookDao {
	//가계부 입력 : cashbook_no 키값 반환 -> 해쉬태그 테이블에 저장
	public int insertCashbook(Connection conn, Cashbook cashbook) throws Exception {
		int cashbookNo = 0;
		
		PreparedStatement stmt = null;
		ResultSet rs = null; //입력 후 생성된 키 값 반환
		
		try {
			String sql = "INSERT INTO cashbook (member_id, category, cashbook_date, price, memo, createdate, updatedate) "
					+"VALUES (?, ?, ?, ?, ?, NOW(), NOW())";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, cashbook.getMemberId());
			stmt.setString(2, cashbook.getCategory());
			stmt.setString(3, cashbook.getCashbookDate());
			stmt.setInt(4, cashbook.getPrice());
			stmt.setString(5, cashbook.getMemo());
			System.out.println(stmt + " <--CashbookDao insertCashbook stmt");
			int row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("cashbook 입력 성공");
				rs = stmt.getGeneratedKeys();
				if(rs.next()) {
					cashbookNo = rs.getInt(1); 
				}
			} else {
				System.out.println("cashbook 입력 실패");
			}
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return cashbookNo;
	}
	
	//해쉬태그별 가계부 목록 조회
	public List<Cashbook> selectCashbookListByTag(String memberId, String hashtag, int startIdx, int rowPerPage){
		List<Cashbook> list = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			String sql = "SELECT c.cashbook_no cashbookNo, member_id memberId, category, cashbook_date cashbookDate, price, memo, c.createdate, c.updatedate "
					+ "FROM cashbook c "
					+ "INNER JOIN hashtag h "
					+ "ON c.cashbook_no = h.cashbook_no "
					+ "WHERE c.member_id = ? AND h.word = ? "
					+ "ORDER BY cashbookDate DESC LIMIT ?, ? ";
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, hashtag);
			stmt.setInt(3, startIdx);
			stmt.setInt(4, rowPerPage);
			System.out.println(stmt + " <--HashtagDao selectCashbookByHashtag stmt");
			rs = stmt.executeQuery();
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setMemberId(rs.getString("memberId"));
				c.setCategory(rs.getString("category"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				c.setPrice(rs.getInt("price"));
				c.setMemo(rs.getString("memo"));
				c.setCreatedate(rs.getString("createdate"));
				c.setUpdatedate(rs.getString("updatedate"));
				list.add(c);
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return list;
	}
	
	//해쉬태그별 가계부 목록 페이징을 위한 전체 데이터 수 구하기
	public int selectCashbookCountByTag(String memberId, String hashtag) {
		int cnt = 0;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			String sql = "SELECT COUNT(*) "
					+ "FROM cashbook c INNER JOIN hashtag h "
					+ "ON c.cashbook_no = h.cashbook_no "
					+ "WHERE c.member_id = ? AND h.word = ? ";
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setString(2, hashtag);
			System.out.println(stmt + " <--CashbookDao selectCashbookCountByTag stmt");
			rs = stmt.executeQuery();
			if(rs.next()) {
				cnt = rs.getInt(1);
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return cnt;
	}
	
	//월별 가계부 목록 조회
	public List<Cashbook> selectCashbookListByMonth(String memberId, int targetYear, int targetMonth){
		List<Cashbook> list = new ArrayList<Cashbook>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			String sql = "SELECT cashbook_no cashbookNo, category, price, cashbook_date cashbookDate "
					+ "FROM cashbook "
					+ "WHERE member_id = ? AND YEAR(cashbook_date) = ? AND MONTH(cashbook_date) = ? "
					+ "ORDER BY cashbook_date ASC";
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth + 1);
			rs = stmt.executeQuery();
			System.out.println(stmt + " <--stmt");
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setCategory(rs.getString("category"));
				c.setPrice(rs.getInt("price"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				list.add(c);
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	//일별 가계부 목록 조회
	public List<Cashbook> selectCashbookListByDate(String memberId, int targetYear, int targetMonth, int targetDate){
		List<Cashbook> list = new ArrayList<Cashbook>();
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			String sql = "SELECT cashbook_no cashbookNo, member_id memberId, category, cashbook_date cashbookDate, price, memo, createdate, updatedate "
					+ "FROM cashbook "
					+ "WHERE member_id = ? AND YEAR(cashbook_date) = ? AND MONTH(cashbook_date) = ? AND DAY(cashbook_date) = ? "
					+ "ORDER BY createdate ASC";
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth + 1);
			stmt.setInt(4, targetDate);
			System.out.println(stmt + " <--CashbookDao selectCashbookListByDate stmt");
			rs = stmt.executeQuery();
			while(rs.next()) {
				Cashbook c = new Cashbook();
				c.setCashbookNo(rs.getInt("cashbookNo"));
				c.setMemberId(rs.getString("memberId"));
				c.setCategory(rs.getString("category"));
				c.setCashbookDate(rs.getString("cashbookDate"));
				c.setPrice(rs.getInt("price"));
				c.setMemo(rs.getString("memo"));
				c.setCreatedate(rs.getString("createdate"));
				c.setUpdatedate(rs.getString("updatedate"));
				list.add(c);
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	//가계부 번호로 상세 내용 조회
	public Cashbook selectCashbookByNo(int cashbookNo) {
		Cashbook cashbook = null;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String driver = "org.mariadb.jdbc.Driver";
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			String sql = "SELECT cashbook_no cashbookNo, member_id memberId, category, cashbook_date cashbookDate, price, memo, createdate, updatedate "
					+ "FROM cashbook "
					+ "WHERE cashbook_no = ?";
			Class.forName(driver);
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			System.out.println(stmt + " <--CashbookDao selectCashbookByNo stmt");
			rs = stmt.executeQuery();
			if(rs.next()) {
				cashbook = new Cashbook();
				cashbook.setCashbookNo(rs.getInt("cashbookNo"));
				cashbook.setMemberId(rs.getString("memberId"));
				cashbook.setCategory(rs.getString("category"));
				cashbook.setCashbookDate(rs.getString("cashbookDate"));
				cashbook.setPrice(rs.getInt("price"));
				cashbook.setMemo(rs.getString("memo"));
				cashbook.setCreatedate(rs.getString("createdate"));
				cashbook.setUpdatedate(rs.getString("updatedate"));
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
				conn.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return cashbook;
	}
	
	//가계부 삭제
	public int deleteCashbook(Connection conn, int cashbookNo) {
		int row = 0;
		
		PreparedStatement stmt = null;
		
		try {
			String sql = "DELETE FROM cashbook WHERE cashbook_no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			System.out.println(stmt + " <--CashbookDao deleteCashbook stmt");
			row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("cashbook 삭제 성공");
			} else {
				System.out.println("cashbook 삭제 실패");
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				stmt.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return row;
	}
	
	//가계부 수정 : 가계부 메모 수정 시 해당 가계부 번호의 해쉬태그 삭제 후 입력 메서드 실행
	public int updateCashbook(Connection conn, Cashbook cashbook) throws Exception {
		int row = 0;
		
		PreparedStatement stmt = null;
		
		try {
			String sql = "UPDATE cashbook SET category = ?, price = ?, memo = ?, updatedate = NOW() "
						+ "WHERE cashbook_no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, cashbook.getCategory());
			stmt.setInt(2, cashbook.getPrice());
			stmt.setString(3, cashbook.getMemo());
			stmt.setInt(4, cashbook.getCashbookNo());
			System.out.println(stmt + " <--CashbookDao updateCashbook stmt");
			row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("cashbook 수정 성공");
			} else {
				System.out.println("cashbook 수정 실패");
			}
		} finally {
			try {
				stmt.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return row;
	}
}
