<%@page import="com.kh.notice.model.vo.Notice"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Notice n = (Notice)request.getAttribute("notice");
	// 글번호, 제목, 내용, 작성자아이디, 작성일 ㅡ n에 뭐가 있는지 주석으로 작성해주면 팀프로젝트 때 팀원이 확인하기에 좋음 // pk는 무조건 가져오면 좋음
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
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>

    <!-- .outer>br+h2[align=center] -->
    <div class="outer" align="center">
        <br>
        <h2 align="center">공지사항 상세보기</h2>
        <br>

        <table id="detail-area" border="1">
            <!-- (tr>th+td+th+td)*3 -->
            <tr>
                <th width="70">제목</th>
                <td colspan="3" width="430"><%= n.getNoticeTitle() %></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td><%= n.getNoticeWriter() %></td>
                <th>작성일</th>
                <td><%= n.getCreateDate() %></td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3">
                    <p style="height: 150px;">
                        <%= n.getNoticeContent() %>
                    </p>
                </td>
            </tr>
        </table>
        <br><br>

        <div>
            <a href="<%= contextPath %>/list.no" class="btn btn-sm btn-secondary">목록가기</a>

			<% if(loginUser != null && loginUser.getUserId().equals(n.getNoticeWriter())) { %>
            <!-- 현재 로그인한 사용자가 해당 글을 쓴 본인일 경우 버튼이 보이도록 ㄱ pk 활용!! 번호가 동적으로 바뀌는지 확인ㄱ 공백없이 작성하도록!!!-->
            <a href="<%= contextPath %>/updateForm.no?num=<%= n.getNoticeNo() %>" class="btn btn-sm btn-warning">수정하기</a>
            <a href="<%= contextPath %>/delete.no?num=<%= n.getNoticeNo() %>" class="btn btn-sm btn-danger">삭제하기</a>
            
            <!-- 
            	본격적인 삭제 요청!!
            	url-mapping값 마음대로 작성!
            	
            	요청 성공시 => 공지사항 목록페이지 alert "성공적으로 삭제되었습니다"
            	요청 실패시 => 에러문구 보여지는 에러페이지 포워딩
            	
             -->
            
            <% } %>
        </div>
    </div>


</body>
</html>