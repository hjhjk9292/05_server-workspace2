package com.kh.member.model.service;

import java.sql.Connection;

import static com.kh.common.JDBCTemplate.*;
import com.kh.member.model.dao.MemberDao;
import com.kh.member.model.vo.Member;

public class MemberService {
	
	public Member loginMember(String userId, String userPwd) {
		
		Connection conn = getConnection();
		Member m = new MemberDao().loginMember(conn, userId, userPwd);
		
		close(conn);
		return m ;
	}
	
	public int insertMember(Member m) { // m에 사용자가 가입할 때 입력한 정보가 담겨있음
		Connection conn = getConnection();
		int result = new MemberDao().insertMember(conn, m);
		
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return result;
	}
	
	public Member updateMember(Member m) { // 반환형 코드 짜면서 유동적으로 바꾸기(int->Member)
		
		Connection conn = getConnection();
		int result = new MemberDao().updateMember(conn, m);
		
		// 갱신된 회원 객체 담을 변수
		Member updateMem = null;		
		if(result > 0) {
			commit(conn); // 장원일 확정 됐지만, loginUser에는 아직 장원영
			// 갱신된 회원 객체 다시 조회해오기
			updateMem = new MemberDao().selectMember(conn, m.getUserId());
			
		}else {
			rollback(conn);
		}
		
		close(conn);
		
		return updateMem; // null(잘안됐을때) | 갱신된회원객체
		
	}
	
	public Member updatePwdMember(String userId, String userPwd, String updatePwd) { // DML이라고 해서 항상 int가 반환형은 아닐 수 있음!
		Connection conn = getConnection();
		int result = new MemberDao().updatePwdMember(conn, userId, userPwd, updatePwd);
		
		Member updateMem = null;
		if(result > 0) {
			commit(conn); // 비번 변경 확정
			updateMem = new MemberDao().selectMember(conn, userId);
		}else {
			rollback(conn);
		}
		
		close(conn); // conn은 항상 service에서 닫기!
		// return result;
		return updateMem;
	}
	
	
	public int deleteMember(String userId, String userPwd) {
		
		Connection conn = getConnection();
	    int result = new MemberDao().deleteMember(conn, userId, userPwd);

	    if (result > 0) {
	        commit(conn);
	    } else {
	        rollback(conn);
	    }

	    close(conn);
	    return result;
	}


}
