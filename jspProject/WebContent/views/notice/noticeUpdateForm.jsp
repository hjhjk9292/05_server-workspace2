<%@page import="com.kh.notice.model.vo.Notice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Notice n = (Notice)request.getAttribute("n");
	// 글번호, 제목, 내용, 작성자아이디, 작성일
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

    #update-form table{border: 1px solid white;}
    #update-form input, #update-form textarea{
        width: 100%;
        box-sizing: border-box;
    }
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>

    <div class="outer" align="center">
        <br>
        <h2 align="center">공지사항 수정하기</h2>
        <br>

        <form action="<%= contextPath %>/update.no" id="update-form" method="post"> <!-- get방식은 용량의 제한이 있기 때문에 글이 잘릴 수 있음-->
        
        	<input type="hidden" name="num" value="<%= n.getNoticeNo() %>"> <!-- 게시글 숨겨서 가져가기 -->

            <table>
                <tr>
                    <th width="50">제목</th>
                    <td width="450">
                        <input type="text" name="title" required value="<%= n.getNoticeTitle() %>"></td>
                </tr>
                <tr>
                    <th>내용</th>
                    <td></td>
                </tr>
                <tr>
                    <td colspan="2">

                        <textarea rows="10" name="content" style="resize: none;" required><%= n.getNoticeContent() %></textarea>

                    </td>
                </tr>
            </table>
            <br><br>

            <button type="submit">수정하기</button>
            <button type="button" onclick="history.back()">뒤로가기</button> <!-- history.back() 뒤로 갈 수 있는 메소드-->
        </form>
    </div>
</body>
</html>