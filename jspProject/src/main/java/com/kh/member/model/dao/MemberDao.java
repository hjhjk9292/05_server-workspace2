package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
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
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return m; // null 이거나 뭐라도 있거나
	}
}
