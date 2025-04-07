package com.kh.board.controller;

import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Attachment;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8"); // post일 때 인코딩 처리 안하면 한글이 깨짐!
		
		// 일반 방식이 아닌 multipart/form-data로 전송하는 경우
		// request로 부터 값 뽑기 불가함
		// String boardTitle = request.getParameter("title");
		// String category = request.getParameter("category");
		
		// enctype이 multipart/form-data로 잘 전송되었을 경우 전반적인 내용들이 수행되도록
		if(ServletFileUpload.isMultipartContent(request)) { //ㅡ 사용자가 요청한 값은 request에 담기는데 내가 요청 받은 것이 isMultipartContent 가 맞는지 확인하는 메소드
			
			// 파일 업로드를 위한 라이버르러ㅣ : cos.jar (com.oreilly.servlet의 약자)
			// http://www.servlet.com 접속해서 다운로드
			
			// 1. 전달되는 파일을 처리할 작업 내용 (전달되는 파일의 용량 제한, 전달된 파일을 저장시킬 폴더 경로)
			// 1_1) 전달되는 파일의 용량 제한(int maxSize) => 10Mbyte
			
			/*
			 * byte => kbyte => mbyte => gbyte => tbyte ...
			 * 
			 * 1kbyte == 1024byte
			 * 1mbyte == 1024kbyte = 1024*1024 byte
			 * 10mbyte == 10*1024*1024byte
			 * 
			 */
			
			int maxSize = 10*1024*1024; // 10메가바이트로 제한하겠다.
			
			// 1_2) 전달된 파일을 저장시킬 폴더의 경로 알아내기 (String savePath)
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles/"); // 마지막에 / 꼭 있어야함
			System.out.println(savePath);
			
			/*
			 * 2. 전달된 파일의 파일명 수정 및 서버에 업로드 작업 ㅡ 파일명이 겹칠 수 있기 때문에 사용자가 flower.png 올렸으나 또 다른 사용자가 flower.png 올리면 덮어씌워지기 때문에 파일명 수정해서 저장되게
			 * 		>> HttpServletRequest request => MultipartRequest multiRequest 변환
			 * 
			 * 		아래 구문 한 줄 실행만으로 넘어온 첨부파일이 해당 폴더에 무조건 업로드 됨!!!
			 * 
			 * 		단, 업로드 시 파일명은 수정해주는게 일반적임! 그래서 파일명 수정시켜주는 객체 제시해야됨
			 * 		=> 같은 파일명이 존재할 경우 덮어씌워질 수 있고, 파일명에 한글/특수문자/띄어쓰기가 포함될 경우 서버에 따라 문제 발생될 수 있음
			 * 
			 * 		기본적으로 파일명이 안겹치도록 수정 작업해주는 객체 있음
			 * 		=> FileRenamePolicy 객체 (cos.jar에서 제공하는 객체) => 인터페이스!
			 * 		=> 내부적으로 해당 클래스에 rename() 메소드가 실행되면서 파일명 수정된 후 업로드
			 * 		   rename(원본파일){
			 * 			기존에 동일한 파일명이 존재할 경우
			 * 			파일명 뒤에 카운팅된 숫자를 붙여줌
			 * 			ex) aaa.jpg, aaa1.jpg, aaa2.jpg
			 * 				꽃.png, 꽃1.png
			 * 				return 수정파일 ㅡ 원본파일명과 이름이 같으면 수정파일을 반환함
			 * 		   }
			 * 
			 * 		나만의 방식으로 절대 안겹치도록 rename 할 수 있게
			 * 		나만의 FileRenamePolicy 클래스(rename 메소드 재정의) 만들기!
			 * 		com.kh.common.MyFileRenamePolicy 클래스 만들기
			 * 
			 */
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy()); // ㅡ MyFileRenamePolicy() 객체가 파일명 자동으로 변경해주는 메소드를 호출
			
			
			// 3. DB에 기록할 데이터들 뽑아서 VO에 주섬주섬 담기
			//	  > 카테고리 번호, 제목, 내용, 작성자회원번호 뽑아서 Board 테이블 insert
			//	  > 넘어온 첨부파일이 있다면 원본명, 수정명, 저장경로 Attachment 테이블 insert
			String category = multiRequest.getParameter("category"); // 반드시 multiRequest로 작성!★
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			String boardWriter = multiRequest.getParameter("userNo");
			
			Board b = new Board();
			b.setCategory(category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			b.setBoardWriter(boardWriter);
			
			Attachment at = null; // 처음에는 null로 초기화, 넘어온 첨부파일이 있다면 생성
			// multiRequest.getOriginalFileName("키"); 넘어온 첨부파일이 있었을 경우 "원본명" | 없었을 경우 null
			if(multiRequest.getOriginalFileName("upfile") != null) { // 넘어온 첨부파일이 있을 경우
				at = new Attachment(); //ㅡ at를 생성 // at에 들어갈 값 세팅ㄱ
				at.setOriginName(multiRequest.getOriginalFileName("upfile")); //ㅡ at.setOriginName(원본명);
				at.setChangeName(multiRequest.getFilesystemName("upfile")); //ㅡ 받아온 파일이 우리 시스템에서는 어떤 이름으로 들어가 있는거야?
				at.setFilePath("resources/board_upfiles/"); //ㅡ 하드코딩으로 넣을것임.. 핵심 마지막에 / 필수!!
			};
			
			// 4. 서비스 요청(요청처리)
			int result = new BoardService().insertBoard(b, at);
			
			// 5. 응답뷰 지정 ㅡ 포워딩
			// 성공 => /jsp/list.bo?cpage=1 url 재요청 => 목록페이지 ㅡ페이징바 있어서 cpage 작성해줘야함
			if(result > 0) {
				request.getSession().setAttribute("alertMsg", "일반게시판 등록 성공!");
				response.sendRedirect(request.getContextPath() + "/list.bo?cpage=1"); // ㅡ sendRedirect가 url 재요청
			}else {
				// 실패 => 첨부파일 있었다면 업로드된 파일 찾아서 삭제시킨 후 => 에러페이지
				if(at != null) { // ㅡ 저장된 파일의 경로와 파일이름(수정된 이름)알아야 함
					new File(savePath + at.getChangeName()).delete(); // ㅡ java.io에 있는 File을 import해서 delete 메소드 사용
				}
				
				request.setAttribute("errorMsg", "일반게시판 등록 실패!");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
			
			
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
