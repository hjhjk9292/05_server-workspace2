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
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardDeleteController
 */
@WebServlet("/delete.bo")
public class BoardDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        // 1. 삭제할 게시글 번호 가져오기
        int boardNo = Integer.parseInt(request.getParameter("bno"));
        // 2. 해당 게시글에 첨부파일이 있는지 확인
        Attachment at = new BoardService().selectAttachment(boardNo);

        // 3. 게시글 및 첨부파일 삭제 처리
        int result = new BoardService().deleteBoard(boardNo);

        if (result > 0) { // 삭제 성공
            // 첨부파일이 있다면 실제 파일 삭제
            String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles");
            new File(savePath + at.getChangeName()).delete();
            request.getSession().setAttribute("confirm", "게시글을 정말 삭제하시겠습니까?");

            // 성공 메시지를 세션에 저장하고 리스트 페이지로 이동
            request.getSession().setAttribute("alertMsg", "게시글이 성공적으로 삭제되었습니다.");
            response.sendRedirect(request.getContextPath() + "/list.bo?cpage=1");

        } else { // 삭제 실패
			
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
