package com.kh.board.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

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
		if(ServletFileUpload.isMultipartContent(request)) {
			
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
			 */
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", 파일명수정시켜주는객체);
			
			
			// 3. DB에 기록할 데이터들 뽑아서 VO에 주섬주섬 담기
			
			// 4. 서비스 요청(요청처리)
			
			// 5. 응답뷰 지정 ㅡ 포워딩
			
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
