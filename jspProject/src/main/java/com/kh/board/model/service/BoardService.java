package com.kh.board.model.service;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.model.vo.PageInfo;

import static com.kh.common.JDBCTemplate.*;

public class BoardService {

	public int selectListCount() {
		Connection conn = getConnection();
		int listCount = new BoardDao().selectListCount(conn);
		close(conn);
		return listCount;
	}
	
	public ArrayList<Board> selectList(PageInfo pi){
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDao().selectList(conn, pi);
		close(conn);
		return list;
	}
	
	public ArrayList<Category> selectCategoryList(){
		Connection conn = getConnection();
		ArrayList<Category> list = new BoardDao().selectCategoryList(conn);
		close(conn);
		return list;
	}
	
	public int insertBoard(Board b, Attachment at) { 
		Connection conn = getConnection();
		
		int result1 = new BoardDao().insertBoard(conn, b);
		int result2 = 1; // 0으로 값을 정하면 까다로워짐
		
		if(at != null) {
			result2 = new BoardDao().insertAttachment(conn, at);
		}
		
		if(result1 > 0 && result2 >0) {
			commit(conn);
		}else {
			rollback(conn); // 둘 중에 하나라도 안되면 rollback
		}
		
		return result1 * result2; // ㅡ result값이 2개일 경우 곱하면 됨,, 둘중에 하나라도 실패하면 0
				
	}
	
	public int increaseCount(int boardNo) {
		Connection conn = getConnection();
		int result = new BoardDao().increaseCount(conn, boardNo); 
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		return result;
	}
	
	public Board selectBoard(int boardNo) {
		Connection conn = getConnection();
		Board b = new BoardDao().selectBoard(conn, boardNo);
		
		close(conn);
		return b;
		
	}
	
	public Attachment selectAttachment(int boardNo) {
		Connection conn = getConnection();
		Attachment at = new BoardDao().selectAttachment(conn, boardNo);
		
		close(conn);
		return at;
	}
	
}
