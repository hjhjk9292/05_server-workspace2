<%@page import="com.kh.board.model.vo.Attachment"%>
<%@page import="com.kh.board.model.vo.Board"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	Board b = (Board)request.getAttribute("b");
	// 글번호, 카테고리명, 제목, 내용, 작성자아이디, 작성일 ㅡ 써두면 프로젝트 시 좋음
	Attachment at = (Attachment)request.getAttribute("at");
	// 첨부파일이 없다면 null
	// 첨부파일이 있다면 파일번호, 원본명, 수정명, 저장경로
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
        height: auto;
        margin: auto;
        margin-top: 50px;
    }
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>

    <div class="outer">
        <br>
        <h2 align="center">일반게시판 상세보기</h2>
        <br>

        <table id="detail-area" border="1" align="center">
            <!-- (tr>th+td+th+td)*4 -->
            <tr>
                <th width="70">카테고리</th>
                <td width="70"><%= b.getCategory() %></td>
                <th width="70">제목</th>
                <td width="350"><%= b.getBoardTitle() %></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td><%= b.getBoardWriter() %></td>
                <th>작성일</th>
                <td><%= b.getCreateDate() %></td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3">
                    <p style="height: 200px;">
                        <%= b.getBoardContent() %>
                    </p>
                </td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <td colspan="3">
                	<% if(at == null) { %>
	                    <!-- case1. 첨부파일이 없을 경우-->
	                    첨부파일이 없습니다.
                    <% }else { %>
	                    <!-- case2. 첨부파일이 있을 경우-->
	                    <a download="<%= at.getOriginName() %>" href="<%= contextPath %>/<%= at.getFilePath() + at.getChangeName() %>"><%= at.getOriginName() %></a>
                    <% } %>
                </td>
            </tr>

        </table>
       
        
        <br>

        <div align="center">
            <a href="<%= contextPath %>/list.bo?cpage=1" class="btn btn-sm btn-secondary">목록가기</a>

			<% if(loginUser != null && loginUser.getUserId().equals(b.getBoardWriter())) { %>
            <!-- 로그인한 사용자가 게시글의 작성자일 경우-->
            <a href="<%= contextPath %>/updateForm.bo?bno=<%= b.getBoardNo() %>" class="btn btn-sm btn-warning">수정하기</a>
            <a href="<%= contextPath %>/delete.bo?bno=<%= b.getBoardNo() %>" class="btn btn-sm btn-danger">삭제하기</a>
            <% } %>
        </div>

        <br><br>
        
        <div id="reply-area">

            <table border="1" align="center">
                <thead>

                    <tr>
                        <th>댓글작성</th>
                        <td>
                            <textarea rows="3" cols="50" style="resize: none;"></textarea>
                        </td>
                        <td><button>댓글등록</button></td>
                    </tr>

                </thead>
                <tbody>

                    <tr>
                        <td>admin</td>
                        <td>재밌네욯ㅎㅎㅎ</td>
                        <td>25/03/07 12:24</td>
                    </tr>
                    <tr>
                        <td>admin</td>
                        <td>ㅋㅋㅋㅋ재밌네요</td>
                        <td>25/03/07 12:24</td>
                    </tr>


                </tbody>
            </table>
            
            <script>
            	$(function(){
            		// 댓글 조회
            		selectReplyList();
            	})
            	
            	//ajax으로 해당 게시글에 달린 댓글 목록 조회용 함수
            	function selectReplyList(){
            		$.ajax({
            			url:"rlist.bo",
            			data:{bno:<%= b.getBoardNo() %>},
            			success:function(result){
            				
            				console.log(result);
            				
            				let value = "";
            				for(let i=0; i<result.length; i++){
            					value += "<tr>"
            								+ "<td>" + result[i].replyWriter + "</td>"
            								+ "<td>" + result[i].replyContent + "</td>"
            								+ "<td>" + result[i].createDate + "</td>"
            							+ "</tr>"
            				}
            				
            				$("#reply-area table tbody").html(value);
            				
            			},error:function(){
            				console.log("댓글목록 조회용 ajax 통신 실패")
            			}
            		})
            	}
            </script>
        
        </div>
        
    </div>

</body>
</html>