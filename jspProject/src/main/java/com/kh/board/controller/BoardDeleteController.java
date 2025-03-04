package com.kh.board.controller;

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
		
		if(ServletFileUpload.isMultipartContent(request)) {
			int boardNo = Integer.parseInt(multiRequest.getParameter("bno"));
			
			Board b = bService.selectBoard(boardNo);
			Attachment at = bService.selectAttachment(boardNo);
			
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles");
			
			MultipartRequest multiRequest = new MultipartRequest(request, savePath);
			
			int result = new BoardService().deleteBoard(boardNo, at);
			
			if(result > 0) {			
				request.getSession().setAttribute("confirm", "");
				response.sendRedirect(request.getContextPath() + "/list.bo?num=" + boardNo); 
				
			}else {
				request.setAttribute("errorMsg", "게시글 삭제 실패");
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
