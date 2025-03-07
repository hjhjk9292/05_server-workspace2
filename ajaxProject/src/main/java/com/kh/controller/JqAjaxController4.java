package com.kh.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.google.gson.Gson;
import com.kh.model.vo.Member;

/**
 * Servlet implementation class JqAjaxController4
 */
@WebServlet("/jqAjax4.do")
public class JqAjaxController4 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JqAjaxController4() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// ArrayList<Member> list = new MemberService().selectMemberList();
		
		ArrayList<Member> list = new ArrayList<Member>(); // []
		list.add(new Member(1, "차은우", 20, "남")); // JSONObject {}
		list.add(new Member(2, "주지훈", 30, "남")); // JSONObject {}
		list.add(new Member(3, "장원영", 15, "여")); // JSONObject {}
		
		// JSONArray [{}, {}, {}] ㅡ 껍데기는 JSONArray인데 내부에는 각각의 JSONObject가 있음
		/*
		JSONArray jArr = new JSONArray(); // []
		for(Member m : list) {
			JSONObject jObj = new JSONObject(); // {}
			jObj.put("userNo", m.getUserNo());
			jObj.put("userName", m.getUserName());
			jObj.put("age", m.getAge());
			jObj.put("gender", m.getGender());
			
			jArr.add(jObj);
		}
		ㅡ 이 과정을 Gson이 내부적으로 해줌 ㄱ*/
		
		response.setContentType("application/json; charset=utf-8");
		new Gson().toJson(list, response.getWriter());

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
