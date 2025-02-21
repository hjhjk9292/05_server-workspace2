package com.kh.notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.vo.Member;
import com.kh.notice.model.service.NoticeService;
import com.kh.notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeInsertController
 */
@WebServlet("/insert.no")
public class NoticeInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		
		String noticeTitle = request.getParameter("title");
		String noticeContent = request.getParameter("content");
		
		// 로그인한 회원 정보 얻어내는 방법
		// 1. input type="hidden" 으로 애초에 요청 시 숨겨서 전달하기
		// 2. session 영역에 담겨있는 회원객체로 뽑기
		HttpSession session = request.getSession();
		
		int userNo = ((Member)session.getAttribute("loginUser")).getUserNo();// Object.getUserNo => 이거 안됨 .. object를 member로 형변환!
		
		Notice n = new Notice();
		n.setNoticeTitle(noticeTitle);
		n.setNoticeContent(noticeContent);
		// n.setNoticeWriter(userNo + ""); // userNo -> String으로 만들어줌
		n.setNoticeWriter(String.valueOf(userNo));
		
		new NoticeService().insertNotice(n);
		
		int result = new NoticeService().insertNotice(n);
		
		if(result > 0) { // 성공 => /jsp/list.no url 재요청 
			// => 목록 페이지 보여지도록 alert(성공적으로 공지사항 등록됐습니다!)
			session.setAttribute("alertMsg", "성공적으로 공지사항 등록됐습니다!");
			response.sendRedirect(request.getContextPath() + "/list.no");
			
		}else { // 실패 => 에러문구(공지사항 등록 실패) 담아서 에러페이지 포워딩 (* 포워딩 할 때는 request에 담아야한다!! 그래야 응답할 때 1회성으로 사용~)
			request.setAttribute("errorMsg", "공지사항 등록 실패");
			request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
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
