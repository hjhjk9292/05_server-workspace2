package com.kh.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class JqAjaxController2
 */
@WebServlet("/jqAjax2.do")
public class JqAjaxController2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		
		// 요청처리가 다 됐다는 가정하에 응답할 데이터
		// v1. 하나의 문자열
		/*
		String responseData = "이름 : " + name + ", 나이 : " + age;
		
		response.setContentType("text/html; charset=utf-8"); // 응답할 데이터에 한글이 있을 경우
		response.getWriter().print(responseData);
		*/
		
		// v2. 응답데이터가 여러개일 경우
		/*
		response.setContentType("text/html; charset=utf-8");
		response.getWriter().print(name);
		response.getWriter().print(age);
		=> success:function의 매개변수 연이어서 하나의 문자열로 담겨있을꺼임
		*/
		
		/*
		 * JSON (JavaScript Object Notation : 자바스크립트 객체 표기법)
		 * - ajax 통신 시 데이터 전송에 자주 사용되는 포맷 형식 중 하나
		 * 
		 * 		> [value, value, value, ...] => 자바스크립트에서의 배열 객체 => JSONArray
		 * 		> {key:value, key:value, ..} => 자바스크립트에서의 일반 객체 => JSONObject
		 * 
		 * - 라이브러리 필요 (https://code.google.com/archive/p/json-simple/downloads)
		 */
		
		/*
		JSONArray jArr = new JSONArray(); // [] // ㅡ 순서 유지가 필요할 경우 JSONArray 사용 ㅡ 인덱스 개념
		jArr.add(name); // ["차은우"]
		jArr.add(age); // ["차은우", 20]
		
		response.setContentType("application/json; charset=utf-8"); // JSON 데이터를 응답 시에 여기 바꿔야됨
		response.getWriter().print(jArr);
		*/
		
		JSONObject jObj = new JSONObject(); // {}
		jObj.put("name", name); // {name:"차은우"}
		jObj.put("age", age); // {name:"차은우", age:20} // ㅡ 순서유지 안됨!! JSONObject는 속성개념
		
		response.setContentType("application/json; charset=utf-8");
		response.getWriter().print(jObj);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
