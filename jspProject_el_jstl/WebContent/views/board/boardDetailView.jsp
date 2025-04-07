<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
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
	<jsp:include page="../common/menubar.jsp"/>

    <div class="outer">
        <br>
        <h2 align="center">일반게시판 상세보기</h2>
        <br>

        <table id="detail-area" border="1" align="center">
            <!-- (tr>th+td+th+td)*4 -->
            <tr>
                <th width="70">카테고리</th>
                <td width="70">${ b.category }</td>
                <th width="70">제목</th>
                <td width="350">${ b.boardTitle }</td>
            </tr>
            <tr>
                <th>작성자</th>
                <td>${ b.boardWriter }</td>
                <th>작성일</th>
                <td>${ b.createDate }</td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3">
                    <p style="height: 200px;">
                        ${ b.boardContent }
                    </p>
                </td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <td colspan="3">
                	<c:choose>
                		<c:when test="${ empty at }">
                			<!-- case1. 첨부파일이 없을 경우-->
                    		첨부파일이 없습니다.
                		</c:when>
                		<c:otherwise>
                			<!-- case2. 첨부파일이 있을 경우-->
                    		<a download="${ at.originName }" href="${ pageContext.request.contextPath }/${ at.filePath }${ at.changeName }">${ at.originName }</a>
                		</c:otherwise>
                	</c:choose>
                </td>
            </tr>

        </table>
       
        
        <br>

        <div align="center">
            <a href="list.bo?cpage=1" class="btn btn-sm btn-secondary">목록가기</a>

			<c:if test="${ not empty loginUser && loginUser.userId eq b.boardWriter }">
            <!-- 로그인한 사용자가 게시글의 작성자일 경우-->
            <a href="updateForm.bo?bno=${ b.boardNo }" class="btn btn-sm btn-warning">수정하기</a>
            <a href="delete.bo?bno=${ b.boardNo }" class="btn btn-sm btn-danger">삭제하기</a>
            </c:if>
        </div>

        <br><br>
        
        <div id="reply-area">

            <table border="1" align="center">
                <thead>

                    <tr>
                        <th>댓글작성</th>
                        <c:choose>
                        	<c:when test="${ not empty loginUser }">
                        		<td>
	                            	<textarea id="replyContent" rows="3" cols="50" style="resize: none;"></textarea>
	                        	</td>
	                        	<td><button onclick="insertReply()">댓글등록</button></td>
                        	</c:when>
                        	<c:otherwise>
                        		<td>
	                            	<textarea rows="3" cols="50" style="resize: none;" readonly>로그인 후 이용 가능한 서비스 입니다.</textarea>
	                        	</td>
	                        	<td><button disabled>댓글등록</button></td>
                        	</c:otherwise>
                        </c:choose>
                    </tr>

                </thead>
                <tbody>

                </tbody>
            </table>
            
            <script>
            	$(function(){
            		// 댓글 조회
            		selectReplyList();
            		
            		setInterval(selectReplyList,10000); // setInterval(주기적으로 실행할함수이름, ms 시간);
            		
            	})
            	
            	// ajax으로 댓글 작성용 함수
            	function insertReply(){
            		$.ajax({
            			url:"rinsert.bo",
            			data:{
            				content:$("#replyContent").val(),
            				bno:${ b.boardNo },
            			},
            			type:"post",
            			success:function(result){
            				
            				if(result >0){ // 댓글 작성 성공 => 갱신된 댓글 리스트 조회
            					selectReplyList();
            					$("#replyContent").val(""); // textarea 초기화
            				}
            				
            			}, error:function(){
            				console.log("댓글 작성용 ajax 통신 실패");
            			}
            		})
            	}
            	
            	
            	
            	//ajax으로 해당 게시글에 달린 댓글 목록 조회용 함수
            	function selectReplyList(){
            		$.ajax({
            			url:"rlist.bo",
            			data:{bno:${ b.boardNo }},
            			success:function(list){
            				
            				console.log(list); // list에 뭐가 들어있는지 확인부터 하기
            				
            				let result = "";
            				for(let i=0; i<list.length; i++){
            					result += "<tr>"
            								+ "<td>" + list[i].replyWriter + "</td>"
            								+ "<td>" + list[i].replyContent + "</td>"
            								+ "<td>" + list[i].createDate + "</td>"
            							+ "</tr>"
            				}
            				
            				$("#reply-area tbody").html(result);
            				
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