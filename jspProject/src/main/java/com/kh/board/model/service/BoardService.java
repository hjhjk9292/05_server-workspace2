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
	
	public int updateBoard(Board b, Attachment at) {
		Connection conn = getConnection();
		int result1 = new BoardDao().updateBoard(conn, b);
		
		int result2 = 1;
		if(at != null) { // 새로운 첨부파일이 있었을 경우
			
			if(at.getFileNo() != 0) { // 기존의 첨부파일이 있었을 경우 => Update Attachment
				result2 = new BoardDao().updateAttachment(conn, at);
			}else { // 기존의 첨부파일 없었을 경우 => Insert Attachment
				result2 = new BoardDao().insertNewAttachment(conn, at);
			}
			
		}
		
		if(result1 > 0 && result2 > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result1 * result2;
		
	}
	
	public int deleteBoard(int boardNo) {
		Connection conn = getConnection();
		Attachment at = new BoardDao().selectAttachment(conn, boardNo);
        int result1 = 0;
        int result2 = 0;
        
        if(at != null){
        	result1 = new BoardDao().deleteBoard(conn, boardNo);
        	result2 = new BoardDao().deleteAttachment(conn, boardNo);
	        // 트랜잭션 처리 (둘 다 성공해야 commit)
	        if (result1 > 0 && result2 > 0) {
	            commit(conn);
	        } else {
	            rollback(conn);
	        }
	
	        close(conn);
        }

        return result1 * result2; // 둘 다 성공해야 1 이상의 값 반환	

    }

	
	public int insertThumbnailBoard(Board b, ArrayList<Attachment> list) {
		Connection conn = getConnection();
		
		int result1 = new BoardDao().insertThBoard(conn, b); // 썸네일 길어서 Th로 
		int result2 = new BoardDao().insertAttachmentList(conn, list);
		
		if(result1 > 0 && result2 > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		return result1 * result2;
		
	}
	
	public ArrayList<Board> selectThumbnailList(){
		Connection conn = getConnection();
		ArrayList<Board> list = new BoardDao().selectThumbnailList(conn);
		
		close(conn);
		return list;
		
	}
	
}
