package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static com.kh.common.JDBCTemplate.*;
import com.kh.member.model.vo.Member;

public class MemberDao {

	private Properties prop = new Properties(); // xml 을 읽어들이려고 prop이라는 객체를 만들기
	
	public MemberDao() {
		String filePath = MemberDao.class.getResource("/db/sql/member-mapper.xml").getPath(); // MemberDao 파일의 class 파일 경로 있는 곳 가서 자원을 얻어와라(db안의~~)
	
		try {
			prop.loadFromXML(new FileInputStream(filePath)); // 파일을 읽는다! ㅡ loadFormXML로 작성해줘야함
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	public Member loginMember(Connection conn, String userId, String userPwd) {
		// select문 => ResultSet 객체(한행) => Member 객체
		Member m = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("loginMember");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) { // 조회된 결과가 있다면 => 한 행
				m = new Member(rset.getInt("user_no"),
							   rset.getString("user_id"),
							   rset.getString("user_pwd"),
							   rset.getString("user_name"),
							   rset.getString("phone"),
							   rset.getString("email"),
							   rset.getString("address"),
							   rset.getString("interest"),
							   rset.getDate("enroll_date"),
							   rset.getDate("modify_date"),
							   rset.getString("status"));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return m; // null 이거나 뭐라도 있거나
	}
	
	public int insertMember(Connection conn, Member m) { // db연결 가능한 객체와 사용자가 입력한 정보 담겨있음
		// insert문 => 처리된 행수 => 트랜젝션 처리
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertMember");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getAddress());
			pstmt.setString(7, m.getInterest());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	public int updateMember(Connection conn, Member m) {
		// update문 => 처리된 행수 => 트랜젝션 처리
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateMember"); // 키값에 해당
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문(? 값 채워야함)
			
			pstmt.setString(1, m.getUserName());
			pstmt.setString(2, m.getPhone());
			pstmt.setString(3, m.getEmail());
			pstmt.setString(4, m.getAddress());
			pstmt.setString(5, m.getInterest());
			pstmt.setString(6, m.getUserId());
			
			result = pstmt.executeUpdate(); // 쿼리 돌리고 result에 담지 않으면 계속 값이 0인 상태임 꼭 담아주기!
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	public Member selectMember(Connection conn, String userId) {
		// select문 => ResultSet(한행) => Member 객체
		Member m = null;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectMember"); // selectMember 키를 가진 쿼리를 가져와라,,
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문(? 채워줘야함)
			
			pstmt.setString(1, userId);
			
			rset = pstmt.executeQuery(); // rset에 데이터가 담김
			
			if(rset.next()) { // 커서 옮기기,, 바뀐 정보들이 담김ㄱ
				m = new Member(rset.getInt("user_no"),
						   rset.getString("user_id"),
						   rset.getString("user_pwd"),
						   rset.getString("user_name"),
						   rset.getString("phone"),
						   rset.getString("email"),
						   rset.getString("address"),
						   rset.getString("interest"),
						   rset.getDate("enroll_date"),
						   rset.getDate("modify_date"),
						   rset.getString("status"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return m; // 갱신된 멤버 객체 반환
		
	}
	
	public int updatePwdMember(Connection conn, String userId, String userPwd, String updatePwd) {
		// update문(DML) => 처리된 행수 => 트랜젝션 처리
		
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updatePwdMember");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			
			pstmt.setString(1, updatePwd);
			pstmt.setString(2, userId);
			pstmt.setString(3, userPwd);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
		
	}
	
	
	public int deleteMember(Connection conn, String userId, String userPwd) {
	    int result = 0;
	    
	    PreparedStatement pstmt = null;
	    
	    String sql = prop.getProperty("deleteMember");
	    
	    try {
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, userId);
	        pstmt.setString(2, userPwd);
	        
	        result = pstmt.executeUpdate();  // executeUpdate() 사용 // 실행 결과 행 개수를 result 변수에 저장
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        close(pstmt);
	    }
	    
	    return result; // 삭제된 행 개수 반환
	}

	
	
	
}
