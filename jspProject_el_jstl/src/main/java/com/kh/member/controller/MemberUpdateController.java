package com.kh.member.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberUpdateController
 */
@WebServlet("/update.me")
public class MemberUpdateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 인코딩 설정
		request.setCharacterEncoding("utf-8");
		
		// 요청 시 전달값 뽑아서 변수 및 객체에 담기
		String userId = request.getParameter("userId");
		String userName = request.getParameter("userName");
		String phone = request.getParameter("phone");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		String[] interestArr = request.getParameterValues("interest");

		String interest = "";
		if(interestArr !=null) {
			interest = String.join(",", interestArr);
		}
		
		Member m = new Member(userId, userName, phone, email, address, interest);
		
		// 요청 처리
		Member updateMem = new MemberService().updateMember(m);
		
		// 응답뷰 지정
		if(updateMem == null) { // 실패
			
			// 에러문구 담아서 에러페이지 포워딩
			request.setAttribute("errorMsg", "회원정보 수정에 실패했습니다.");
			request.getRequestDispatcher("views/common/erroPage.jsp").forward(request, response); // forward 매우중요!
			
		}else { // 성공
			
			// session에 담겨있는 loginUser를 바꿔치기
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", updateMem); // 이 구문 하나만으로 loginUser에 새로운 정보가 담김
			session.setAttribute("alertMsg", "성공적으로 회원정보를 수정했습니다."); // alert 문구
			
			// /jsp/myPage.me url 재요청 => myPage.jsp 포워딩
			response.sendRedirect(request.getContextPath() + "/myPage.me"); // request.getContextPath() == /jsp
			
			
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
