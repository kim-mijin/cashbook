package cash.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cash.dao.CashbookDao;
import cash.dao.HashtagDao;
import cash.vo.Cashbook;
import cash.vo.Hashtag;

public class CashbookService {
	/*
		CashbookService의 메소드를 실행하면 CashbookDao, HashtagDao에 있는 메소드를 사용하기 위하여 CashbookDao, HashtagDao의 새로운 인스턴스를 생성하는데
		CashbookService의 필드로 선언하여 메서드 실행마다 새로운 인스턴스를 만들지 않고 해당 만들어진 CashbookDao, HashtagDao필드를 사용
	*/
	private CashbookDao cashbookDao;
	private HashtagDao hashtagDao;
	
	//가계부 달력 및 해당월 해시태그목록 출력
	public Map<String, Object> getCashbookCalendar(String memberId, int targetYear, int targetMonth){
		List<Cashbook> cashbookList = new ArrayList<Cashbook>();
		List<Map<String, Object>> tagList = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false); //conn 자동 커밋 해제
			
			//월별 가계부 출력
			this.cashbookDao = new CashbookDao();
			cashbookList = cashbookDao.selectCashbookListByMonth(conn, memberId, targetYear, targetMonth);
			
			//월별 해쉬태그 목록 출력
			this.hashtagDao = new HashtagDao();
			tagList = hashtagDao.selectWordCountByMonth(conn, memberId, targetYear, targetMonth);
			
			//Map으로 묶기
			map = new HashMap<String, Object>();
			map.put("cashbookList", cashbookList);
			map.put("tagList", tagList);
			
			conn.commit();
		} catch(Exception e) {
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
		
		return map;
	}
	
	//월별 가계부 출력
	public List<Cashbook> getMonthlyCashbook(String memberId, int targetYear, int targetMonth){
		List<Cashbook> list = new ArrayList<Cashbook>();
		
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false);
			
			this.cashbookDao = new CashbookDao();
			list = cashbookDao.selectCashbookListByMonth(conn, memberId, targetYear, targetMonth);
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("printMonthlyCashbook 가계부출력 예외발생");
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
		return list;
	}
	
	//월 카테고리별 합계 출력
	public List<Map<String,Object>> getSumByCategory(String memberId, int targetYear, int targetMonth){
		List<Map<String, Object>> list = new ArrayList<>();
		
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false);
			
			this.cashbookDao = new CashbookDao();
			list = cashbookDao.selectSumByCategory(conn, memberId, targetYear, targetMonth);
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("printMonthlyCashbook 가계부출력 예외발생");
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
		return list;
	}
	
	//일별 가계부 출력
	public List<Cashbook> getDailyCashbook(String memberId, int targetYear, int targetMont, int targetDate){
		List<Cashbook> list = new ArrayList<Cashbook>();
		
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false);
			
			this.cashbookDao = new CashbookDao();
			list = cashbookDao.selectCashbookListByDate(conn, memberId, targetYear, targetMont, targetDate);
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("printDailyCashbook 가계부출력 예외발생");
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
		return list;
	}
	
	//태그별 가계부 출력, 페이징
	public Map<String, Object> getCashbookListByTagPage(String memberId, String hashtag, int startIdx, int rowPerPage){
		List<Cashbook> list = null;
		int cnt = -1;
		Map<String, Object> map = null;
		
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false);
			
			this.cashbookDao = new CashbookDao();
			list = cashbookDao.selectCashbookListByTag(conn, memberId, hashtag, startIdx, rowPerPage);
			cnt = cashbookDao.selectCashbookCountByTag(conn, memberId, hashtag);
			map = new HashMap<String, Object>();
			map.put("list", list);
			map.put("cnt", cnt);
			
			conn.commit();
		} catch(Exception e) {
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
		
		return map;
	}

	//가계부번호별 출력
	public Cashbook getCashbookByNo(int cashbookNo) {
		Cashbook cashbook = null;
		
		Connection conn = null;
				
		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false);
			
			this.cashbookDao = new CashbookDao();
			cashbook = cashbookDao.selectCashbookByNo(conn, cashbookNo);
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("printCashbookByNo 예외발생");
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
		
		return cashbook;
	}
	
	//가계부 입력
	public String addCashbook(Cashbook cashbook) {
		String msg = null;
		Connection conn = null;

		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			
			//자동커밋 끄기
			conn.setAutoCommit(false);
			
			this.cashbookDao = new CashbookDao();
			int cashbookNo = cashbookDao.insertCashbook(conn, cashbook);
			
			//입력 성공 시 -> 해쉬태그가 존재한다면 -> 해쉬태그 추출 -> 해쉬태그 입력(반복)
			//해쉬태그 추출 알고리즘
			//# #구디 #구디 #자바
			this.hashtagDao = new HashtagDao();
			String rplMemo = cashbook.getMemo().replace("#", " #"); //#을 공백+#으로 바꿔줌 (#자바##파이썬 -> #자바 # #파이썬)
			
			Set<String> set = new HashSet<String>(); //중복된 해쉬태그를 방지하기 위해 set자료구조 사용
			
			for(String ht1 : rplMemo.split(" ")) { //공백으로 문자열을 나누어 배열로 저장한다.
				//#이 붙지 않는 단어도 해쉬태그로 저장되므로 #이 붙은 문자열만 ht2에 저장
				if(ht1.contains("#")) { //if(ht1.startsWith("#")
					String ht2 = ht1.replace("#", ""); //#을 없앤다
					if(ht2.length() > 0) { //#제거 후 남은 문자열이 있는 경우
						set.add(ht2); //set자료구조는 중복된 값이 add되지 않는다
					}
				}
			}
			
			for(String s : set) {
				Hashtag hashtag = new Hashtag();
				hashtag.setCashbookNo(cashbookNo);
				hashtag.setWord(s);
				hashtagDao.insertHashtag(conn, hashtag);
			}
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("addCashbook 예외발생");
			msg = "addCashbook 예외발생";
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
		
		return msg;
	}
	
	//가계부 수정
	public String modifyCashbook(Cashbook cashbook) {
		String msg = null;
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false);
			
			this.cashbookDao = new CashbookDao();
			cashbookDao.updateCashbook(conn, cashbook);

			//수정 성공 시 -> 기존 해쉬태그 삭제 -> 해쉬태그가 존재한다면 -> 해쉬태그 추출 -> 해쉬태그 입력(반복)
			//해쉬태그 추출 알고리즘
			//# #구디 #구디 #자바
			this.hashtagDao = new HashtagDao();
			hashtagDao.deleteHashtag(conn, cashbook.getCashbookNo()); //기존 해쉬태그삭제
			
			String rplMemo = cashbook.getMemo().replace("#", " #"); //#을 공백+#으로 바꿔줌 (#자바##파이썬 -> #자바 # #파이썬)
			Set<String> set = new HashSet<String>(); //중복된 해쉬태그를 방지하기 위해 set자료구조 사용
			for(String ht1 : rplMemo.split(" ")) { //공백으로 문자열을 나누어 배열로 저장한다.
				//#이 붙지 않는 단어도 해쉬태그로 저장되므로 #이 붙은 문자열만 ht2에 저장
				if(ht1.contains("#")) { //if(ht1.startsWith("#")
					String ht2 = ht1.replace("#", ""); //#을 없앤다
					if(ht2.length() > 0) { //#제거 후 남은 문자열이 있는 경우
						set.add(ht2); //set자료구조는 중복된 값이 add되지 않는다
					}
				}
			}
			
			for(String s : set) {
				Hashtag hashtag = new Hashtag();
				hashtag.setCashbookNo(cashbook.getCashbookNo());
				hashtag.setWord(s);
				hashtagDao.insertHashtag(conn, hashtag);
			}
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("modifyCashbook 예외 발생");
			e.printStackTrace();
			try {
				conn.rollback(); //예외 발생 시 롤백
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close(); //conn 자원 반납
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		return msg;
	}
	
	//가계부 삭제
	public String removeCashbook(int cashbookNo) {
		String msg = null;
		Connection conn = null;
		
		try {
			String dbUrl = "jdbc:mariadb://43.202.82.110:3306/cash";
			String dbUser = "root";
			String dbPw = "java1234";
			conn = DriverManager.getConnection(dbUrl, dbUser, dbPw);
			conn.setAutoCommit(false);
			
			//삭제 메서드 실행 순서는 자식테이블인 hashtag먼저한다
			//해쉬태그 삭제
			this.hashtagDao = new HashtagDao();
			hashtagDao.deleteHashtag(conn, cashbookNo);

			//삭제 메서드 실행
			this.cashbookDao = new CashbookDao();
			cashbookDao.deleteCashbook(conn, cashbookNo);
			
			conn.commit();
		} catch(Exception e) {
			System.out.println("removeCashbook 예외 발생");
			e.printStackTrace();
			try {
				conn.rollback(); //예외 발생 시 롤백
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				conn.close(); //conn 자원 반납
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		}
		
		return msg;
	}
}
