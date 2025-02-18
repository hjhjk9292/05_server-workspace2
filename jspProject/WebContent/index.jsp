<%@page import="com.kh.common.JDBCTemplate"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	
	<!-- 
		* 회원 서비스
					|C(Insert)|R(Select)|U(Update)|D(Delete)
		=========================================================
			로그인	|		  |	   o    |		  |
		   회원가입	|	 o	  |			|		  |
		  마이페이지	|		  |	   o	|		  |
		   정보변경	|		  |			|	 o	  |
		   회원탈퇴	|		  |			|	 o	  |
	   [아이디중복체크] |		  |	   o	|		  |
		
		* 공지사항 서비스 
		공지사항 리스트 조회(R) / 상세조회(R)
		공지사항 작성(C) / 공지사항 수정(U) / 공지사항 삭제(U)
		
		* 일반게시판 서비스
		  게시판 리스트 조회 (R) - 페이징 처리 / 상세 조회(R)
		  게시판 작성(C) / 게시판 수정(U) / 게시판 삭제(U)
		  [댓글리스트 조회(R) / 댓글 작성(C)]
		
		* 사진게시판 서비스
		  게시판 리스트 조회(R) - 썸네일 형식 / 상세 조회(R)
		  게시판 작성(C) - 첨부파일 업로드(C)
	 -->
	 <%@ include file="views/common/menubar.jsp" %>

</body>
</html>