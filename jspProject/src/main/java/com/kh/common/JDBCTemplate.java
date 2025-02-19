package com.kh.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	
	// 1. Connection 객체 생성 한 후 Connection 객체를 반환시켜주는 getConnection 메소드
	public static Connection getConnection() {
		
		Connection conn = null;
		
		Properties prop = new Properties(); // Map 계열 컬렉션(key-value)
		
		// 읽어들이고자 하는 driver.properties 파일의 물리적인 경로(ㅡ실제로 있는 곳은 src인데, 실질적으로 was 올라가는 경로는 WebContent>WEB-INF>classes에 파일 있음
		String filePath = JDBCTemplate.class.getResource("/db/driver/driver.properties").getPath(); // JDBCTemplate의 class파일 있는 곳 어디냐?
		// "C:/05_server-workspace2/jspProject/WebContent/WEB-INF/classes/db/driver/driver.properties → sysout으로 검색해보면 이 파일의 물리적인 경로 가져옴
		
		try {
			prop.load(new FileInputStream(filePath)); // // prop.load(입력용스트림 생성구문); → prop.load(new FileInputStream("dirver.properties 경로")); → surround with multicatch
		} catch (IOException e1) {
			e1.printStackTrace();
		}  
		
		try {
			// jdbc driver 등록
			Class.forName(prop.getProperty("driver")); // → 원래는 Class.forName("oracle.jdbc.driver.OracleDriver"); 로 작성했었음 이제는 외부 파일(driver.properties)에서 읽어옴 
			
			// 접속하고자 하는 db의 url, 계정명, 비번 제시해서 Connection 객체 생성
			conn = DriverManager.getConnection(prop.getProperty("url"), 
											   prop.getProperty("username"),
											   prop.getProperty("password"));
			conn.setAutoCommit(false); // 오토커밋을 끄는 코드,,꼭 작성해주어야함(안하면 맘대로 commit, rollback됨)
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return conn;
		
	}
	
	// 2_1. Connection 객체 전달 받아서 commit 시켜주는 commit 메소드
	public static void commit(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()) { // conn이 닫겨있지 않고 conn이 null일 경우만 commit
				conn.commit();				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 2_2. Connection 객체 전달 받아서 rollback 시켜주는 rollback 메소드
	public static void rollback(Connection conn) {
		try {
			if(conn != null & !conn.isClosed()) {
				conn.rollback();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3_1. Connection 객체 전달 받아서 반납시켜주는 close 메소드 // close메소드에서 오버로딩 적용
	public static void close(Connection conn) {
		try {
			if(conn != null && !conn.isClosed()){
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3_2. Statement 객체 전달 받아서 반납시켜주는 close 메소드
	public static void close(Statement stmt) {
		try {
			if(stmt != null && stmt.isClosed()) {
				stmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 3_3. ResultSet 객체 전달 받아서 반납시켜주는 close 메소드
	public static void close(ResultSet rset) {
		try {
			if(rset != null && !rset.isClosed()) {
				rset.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
