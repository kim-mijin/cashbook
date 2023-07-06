package cash.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import cash.vo.Member;

public class MemberDao {
	//로그인
	public Member selectMemberById(Connection conn, Member paramMember) throws Exception {
		Member returnMember = null;
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT member_id memberId FROM member WHERE member_id = ? AND member_pw = PASSWORD(?)";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paramMember.getMemberId());
			stmt.setString(2, paramMember.getMemberPw());
			rs = stmt.executeQuery();
			if(rs.next()) {
				returnMember = new Member();
				returnMember.setMemberId(rs.getString("memberId"));
			}
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		return returnMember;
	}
	
	//아이디 중복검사
	public int memberIdCheck(Connection conn, String memberId) throws Exception {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		int idCount = 0;
		try {
			String sql = "SELECT COUNT(*) FROM member WHERE member_id = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, memberId);
			rs = stmt.executeQuery();
			if(rs.next()) {
				idCount = rs.getInt(1);
			}
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch(Exception e2) {
				e2.printStackTrace();
			}
		}

		return idCount;
	}
	
	//회원가입
	public int insertMember(Connection conn, Member paramMember) throws Exception {
		int row = 0;
		
		PreparedStatement stmt = null;
		String sql = "INSERT into member SET member_id = ?, member_pw = PASSWORD(?), createdate=NOW(), updatedate=NOW()";
		
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, paramMember.getMemberId());
			stmt.setString(2, paramMember.getMemberPw());
			row = stmt.executeUpdate();
			if(row == 1) {
				System.out.println("member 회원가입 성공");
			} else {
				System.out.println("member 회원가입 실패");
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
