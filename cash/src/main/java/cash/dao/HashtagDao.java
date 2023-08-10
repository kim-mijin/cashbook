package cash.dao;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cash.vo.Hashtag;

public class HashtagDao {
	//월별 해쉬태그 순위별 조회
	public List<Map<String, Object>> selectWordCountByMonth(Connection conn, String memberId, int targetYear, int targetMonth){
		List<Map<String, Object>> list = new ArrayList<>();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			String sql = "SELECT word, COUNT(*) cnt "
					+ "FROM hashtag h "
					+ "INNER JOIN cashbook c "
					+ "ON h.cashbook_no = c.cashbook_no "
					+ "WHERE c.member_id = ? AND YEAR(c.cashbook_date) = ? AND MONTH(c.cashbook_date) = ? "
					+ "GROUP BY word "
					+ "ORDER BY COUNT(*) DESC ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			stmt.setInt(2, targetYear);
			stmt.setInt(3, targetMonth + 1);
			System.out.println(stmt + " <--HashtagDao selectWordCountByMonth stmt");
			rs = stmt.executeQuery();
			while(rs.next()) {
				Map<String, Object> m = new HashMap<String, Object>();
				m.put("word", rs.getString("word"));
				m.put("cnt", rs.getString("cnt"));
				list.add(m);
			}
		} catch(Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		return list;
	}
	
	//해쉬태그 입력
	public int insertHashtag(Connection conn, Hashtag hashtag) {
		int row = 0;
		
		PreparedStatement stmt = null;
		
		try {
			String sql = "INSERT INTO hashtag (cashbook_no, word, createdate, updatedate) "
					+ "VALUES (?, ?, NOW(), NOW())";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, hashtag.getCashbookNo());
			stmt.setString(2, hashtag.getWord());
			System.out.println(stmt + " <--Hashtag Dao insertHashtag stmt");
			row = stmt.executeUpdate();
			if(row > 0) {
				System.out.println("해시태그 입력 성공");
			} else {
				System.out.println("해시태그 입력 실패");
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
	
	//해쉬태그 삭제
	public int deleteHashtag(Connection conn, int cashbookNo) {
		int row = 0;
		
		PreparedStatement stmt = null;
		
		try {
			String sql = "DELETE FROM hashtag WHERE cashbook_no = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cashbookNo);
			System.out.println(stmt + " <--Hashtag Dao insertHashtag stmt");
			row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("해쉬태그 삭제 성공");
			} else {
				System.out.println("해쉬태그 삭제 실패");
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
}
