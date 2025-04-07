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
 * Servlet implementation class LoginController
 */
@WebServlet("/login.me")
public class LoginController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) 전달값에 한글이 있을 경우 인코딩 처리 해야됨 (post방식에만)
		// request.setCharacterEncoding("UTF-8");
		
		// 2) 요청 시 전달값 뽑아서 변수 또는 객체 기록하기
		String userId = request.getParameter("userId");
		String userPwd = request.getParameter("userPwd");
		
		
		// 3) 요청처리(db에 sql문 실행)
		//    해당 요청을 처리하는 서비스 클래스의 메소드 호출 및 결과 받기
		Member loginUser = new MemberService().loginMember(userId, userPwd);
		// System.out.println(loginUser); ㅡ console에 찍는 게 많으면 시스템에 부담되니까 확인했으면 잠금 처리
		
		
		// 4) 처리된 결과를 가지고 사용자가 보게될 응답뷰(jsp) 지정 후 포워딩 또는 url 재요청
		
		/*
		 * 응답페이지에 전달할 값이 있을 경우 어딘가에 담아야됨!! (담을 수 있는 영역 == JSP 내장객체 4종류) ㅡ 2,3번은 꼭 알기★ / 아래에서 위로 갈수록 영역이 넓어짐
		 * 1) application : 여기에 담긴 데이터는 웹 애플리케이션 전역에서 다 꺼내서 쓸 수 있음 ㅡ 가장 넓은 영역
		 * 2) session : 여기에 담긴 데이터는 내가 직접 지우기 전까지, 세션이 만료(서버가 멈추거나, 브라우저 종료)되기 전까지
		 * 				어떤 jsp든, 어떤 servlet 이던 꺼내 쓸 수 있음 ㅡ 로그인 시 다른 페이지 이동해도 로그인이 유지될 수 있도록 하기 위해 사용,, 구름이라고 생각,,
		 * 3) request : 여기에 담긴 데이터는 현재 이 request 객체를 "포워딩한 응답 jsp에서만" 꺼내 쓸 수 있음 (일회성 느낌) ㅡ 로그인 후 다른 페이지 이동하면 다시 로그인해야함
		 * 4) page : 해당 jsp에서 담고 그 jsp에서만 꺼내 쓸 수 있음 ㅡ 잘 안 씀
		 * 
		 * 공통적으로 데이터를 담고자 한다면 .setAttribute("키", 벨류)
		 * 		    데이터를 꺼내고자 한다면 .getAttribute("키") : Object 타입으로 벨류ㅡ가 반환됨
		 * 			데이터를 지우고자 한다면 .removeAttribute("키") ㅡ 키값에 해당하는 값을 지움
		 */
		
		
		
		if(loginUser == null) {
			// 조회결과없음 == 로그인 실패! => 에러문구가 보여지는 에러페이지 응답
			request.setAttribute("errorMsg", "로그인 실패했습니다!");
			// 응답페이지(jsp)에게 위임 시 필요한 객체 (RequestDispatcher)
			RequestDispatcher view = request.getRequestDispatcher("views/common/errorPage.jsp");
			view.forward(request, response);
			
		}else {
			// 조회결과있음 == 로그인 성공! => 메인페이지(index) 응답
			
			// 로그인한 회원정보 loginUser)를 session에 담아버리기(왜? 여기저기서 갖다쓰도록 ㅡ mail을 누르던,웹툰을 누르던,,)
			// session에 접근하고자 한다면 우선은 session 객체를 얻어와야함
			HttpSession session = request.getSession(); // Session(); 얻어와서 session에 담음
			session.setAttribute("loginUser", loginUser);
			
			// 1. 포워딩 방식 응답뷰 출력
			//	  해당 선택된 jsp가 보여질 뿐 url에는 여전히 현재 이 서블릿 매핑값이 남아있음
			//	  localhost:8001/jsp/login.me
			//    내가 이 사이트에 들어와서 한번도 본적이 없는 화면이다! => 포워딩 ㅡ 사용자의 입장에서 해당 페이지가 초면일 때(ex.에러 페이지)
			// RequestDispatcher view = request.getRequestDispatcher("index.jsp");
			// view.forward(request, response);
			
			// 2. url 재요청 방식 (sendRedirect 방식)
			//    기존에 저 페이지를 응답하는 url이 존재한다면 사용 가능
			//    localhost:8001/jsp/
			//    내가 이 사이트에 들어와서 이 화면을 한번이라도 본적이 있다! => url 재요청(ex.메인 페이지)
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
