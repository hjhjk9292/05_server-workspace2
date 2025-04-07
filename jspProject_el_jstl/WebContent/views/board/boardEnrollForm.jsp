<%@page import="com.kh.board.model.vo.Category"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	ArrayList<Category> list = (ArrayList<Category>)request.getAttribute("list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer{
        background-color: black;
        color: white;
        width: 1000px;
        height: 500px;
        margin: auto;
        margin-top: 50px;
    }
    #enroll-form table{border:1px solid white;}
    #enroll-form input, #enroll-form textarea{
        width: 100%;
        box-sizing: border-box;
    }
</style>
</head>
<body>
	<jsp:include page="../common/menubar.jsp"/>

    <div class="outer">
        <br>
        <h2 align="center">일반게시판 작성하기</h2>
        <br>

        <form id="enroll-form" action="insert.bo" method="post" enctype="multipart/form-data">
		<!-- file을 넘기려면 ★ 반드시 1. post로 작성 2. enctype="multipart/form-data" 작성 필요 ★-->
		
             <!-- 카테고리, 제목, 내용, 첨부파일 한 개, 로그인한 회원 번호 -->
             <input type="hidden" name="userNo" value="${ loginUser.userNo}">
             <table align="center">
                <!-- (tr>th+td)*4 -->
                <tr>
                    <th>카테고리</th>
                    <td>
                        <select name="category">
                            <!-- Category 테이블로부터 조회해올꺼임 -->
                            <% for(Category c : list) { %>
                            <option value="<%= c.getCategoryNo() %>"><%= c.getCategoryName() %></option>
                            <% } %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <th>제목</th>
                    <td width="500"><input type="text" name="title" required></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td><textarea rows="10" name="content" style="resize: none;" required></textarea></td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    <td><input type="file" name="upfile"></td>
                </tr>
             </table>
             <br>

             <div align="center">
                <button type="submit">작성하기</button>
                <button type="reset">취소하기</button>
             </div>

        </form>
    </div>

</body>
</html>