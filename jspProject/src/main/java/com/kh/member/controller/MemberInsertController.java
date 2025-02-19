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
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) [인코딩 작업(post 방식이고 한글값 넘어올 때)]
		request.setCharacterEncoding("utf-8");
		
		// 2) [요청 시 전달값 뽑아서 변수 및 객체에 기록하기]
		String userId = request.getParameter("userId"); // "user03"
		String userPwd = request.getParameter("userPwd"); // "pass03"
		String userName = request.getParameter("userName"); // "차은우"
		String phone = request.getParameter("phone"); // "010-1111-2222" | ""
		String email = request.getParameter("email"); // "~~@naver.com" | ""
		String address = request.getParameter("address"); // "서울" | ""
		String[] interestArr = request.getParameterValues("interest"); // ["운동","등산"] | null
		
		// String[]			--> String
		// ["운동","등산"]		--> "운동,등산"
		
		String interest = "";
		if(interestArr != null) { // 취미를 선택했다면
			interest = String.join(",", interestArr);
		}
		
		// 기본생성자로 생성 후 setter 메소드 이용해서 담기
		// "아싸리 매개변수 생성자를 이용해서 생성과 동시에 담기"
		Member m = new Member(userId, userPwd, userName, phone, email, address, interest); // 변수명 필드부와 동일하게 만들면 편리
				
		// 3) [요청처리(서비스 메소드 호출 및 결과 받기)]
		int result = new MemberService().insertMember(m);
		
		// 4) 처리된 결과를 가지고 사용자가 보게 될 응답뷰 지정 후 포워딩 또는 url 재요청
		if(result > 0) {
			// 성공 => /jsp url 재요청 => index 페이지
			
			HttpSession session = request.getSession();
			session.setAttribute("alertMsg", "성공적으로 회원가입 되었습니다.");
			
			response.sendRedirect(request.getContextPath()); // 동적으로 jsp 가져오는 것
		}else {
			// 실패 => 에러문구가 보여지는 에러페이지 ㅡ 에러페이지가 초면일 수 있으니 forward
			request.setAttribute("errorMsg", "회원가입 실패했습니당.");
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
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
