package com.kh.board.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.model.vo.PageInfo;

import static com.kh.common.JDBCTemplate.*;

public class BoardDao {
	
	private Properties prop = new Properties();
	
	public BoardDao() {
		
		try {
			prop.loadFromXML(new FileInputStream( BoardDao.class.getResource("/db/sql/board-mapper.xml").getPath() ));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public int selectListCount(Connection conn) {
		// select문 => ResultSet (한개) => int
		int listCount = 0;
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("slelctListCount");
		
		try {
			pstmt = conn.prepareStatement(sql); // 완성된 sql문
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt("count"); // 101
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return listCount;
	}

	public ArrayList<Board> selectList(Connection conn, PageInfo pi) {
		// select문 => ResultSet(여러행) => ArrayList<Board>
		ArrayList<Board> list = new ArrayList<Board>(); // []
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectList");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			
			/*
			 * currentPage : 1 => 시작값 : 1  | 끝값 : 10
			 * currentPage : 2 => 시작값 : 11 | 끝값 : 20
			 * currentPage : 3 => 시작값 : 21 | 끝값 : 30
			 * 
			 * 시작값 : (currentPage -1) * boardLimit + 1
			 * 끝값 : 시작값 + boardLimit - 1
			 */
			
			int startRow = (pi.getCurrentPage() - 1) * pi.getBoardLimit() + 1; // currentPage 와 boardLimit은 pi에 담겨있음
			int endRow = startRow + pi.getBoardLimit() - 1;
			
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Board(rset.getInt("board_no"),
								   rset.getString("category_name"),
								   rset.getString("board_title"),
								   rset.getString("user_id"),
								   rset.getInt("count"),
								   rset.getString("create_date")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list; 
	}
	
	public ArrayList<Category> selectCategoryList(Connection conn) {
		// select문 => ResultSet(여러행) => ArrayList<Category>
		ArrayList<Category> list = new ArrayList<Category>(); // []
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectCategoryList");
		
		try {
			pstmt = conn.prepareStatement(sql); // 완성된 sql문
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				list.add(new Category(rset.getInt("category_no"),
									  rset.getString("category_name")));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	public int insertBoard(Connection conn, Board b) {
		// insert문 => 처리된 행수 => 트랜젝션 처리
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertBoard");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			pstmt.setInt(1, Integer.parseInt(b.getCategory())); // ㅡ 형변환(문자열->숫자)
			pstmt.setString(2, b.getBoardTitle());
			pstmt.setString(3, b.getBoardContent());
			pstmt.setInt(4, Integer.parseInt(b.getBoardWriter())); // ㅡ 형변혼(문자열->숫자)
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	public int insertAttachment(Connection conn, Attachment at) {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			
			pstmt.setString(1, at.getOriginName());
			pstmt.setString(2, at.getChangeName());
			pstmt.setString(3, at.getFilePath());
			
			result = pstmt.executeUpdate(); // ㅡresult에 처리된 행수 담김
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	public int increaseCount(Connection conn, int boardNo) {
		// update문 => 처리된 행수 => 트랜젝션 처리
		
		int result = 0;
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("increaseCount");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성된 sql문
			pstmt.setInt(1, boardNo);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	public Board selectBoard(Connection conn, int boardNo) {
		// select문 => ResultSet(한행) => Board 객체
		Board b = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("selectBoard");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성 sql문
			pstmt.setInt(1, boardNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				b = new Board(rset.getInt("board_no"),
							  rset.getString("category_name"),
							  rset.getString("board_title"),
							  rset.getString("board_content"),
							  rset.getString("user_id"),
							  rset.getString("create_date"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return b;
	}
	
	public Attachment selectAttachment(Connection conn, int boardNo) {
		// select문 => ResultSet(한행) => Attachment 객체
		Attachment at = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String Sql = prop.getProperty("selectAttachment");
		try {
			pstmt = conn.prepareStatement(Sql);
			pstmt.setInt(1, boardNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				at = new Attachment(); // ㅡ매개변수 생성자 안 만들고 기본생성자로 만들기(+ setter 메소드 활용)
				at.setFileNo(rset.getInt("file_no"));
				at.setOriginName(rset.getString("origin_name"));
				at.setChangeName(rset.getString("change_name"));
				at.setFilePath(rset.getString("file_path"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(pstmt);
		}
		return at;
	}
	
}
