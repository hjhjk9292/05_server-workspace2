package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberDeleteController
 */
@WebServlet("/delete.me")
public class MemberDeleteController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberDeleteController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8"); // 한글 깨짐 방지

		// 요청값 추출
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");

		// 서비스 호출 (삭제된 행 개수 반환)
		int result = new MemberService().deleteMember(userId, userPwd);

		HttpSession session = request.getSession(); // 세션 객체 한 번만 생성

		if (result > 0) { // 삭제 성공
			session.removeAttribute("loginUser"); // 로그인 정보 삭제
			session.setAttribute("alertMsg", "성공적으로 회원탈퇴되었습니다. 그동안 이용해주셔서 감사합니다.");
			
			response.sendRedirect(request.getContextPath()); // 메인 페이지로 리다이렉트
		} else { // 삭제 실패
			session.setAttribute("alertMsg", "회원탈퇴 실패!");
			response.sendRedirect(request.getContextPath());
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
