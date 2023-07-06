package cash.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import cash.dao.MemberDao;
import cash.vo.Member;

public class MemberService {
	//로그인
	public Member login(Member paramMember) {
		Member member = null;
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false); //자동 커밋 해제
			
			MemberDao memberDao = new MemberDao();
			member = memberDao.selectMemberById(conn, paramMember);
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("login 예외발생");
			try {
				conn.rollback(); //예외발생 시 쿼리 롤백
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close(); //자원반납
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return member;
	}
	
	//아이디 중복검사
	public int idCheck(String memberId){
		int idCount = 0;
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false); //자동 커밋 해제
			
			MemberDao memberDao = new MemberDao();
			idCount = memberDao.memberIdCheck(conn, memberId);
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("signup 예외발생");
			try {
				conn.rollback(); //예외발생 시 쿼리 롤백
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close(); //자원반납
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return idCount;
	}
	
	//회원가입
	public int signup(Member paramMember) {
		int row = 0;
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://127.0.0.1:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false); //자동 커밋 해제
			
			MemberDao memberDao = new MemberDao();
			row = memberDao.insertMember(conn, paramMember);
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("signup 예외발생");
			try {
				conn.rollback(); //예외발생 시 쿼리 롤백
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close(); //자원반납
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return row;
	}
}
