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
        height: 500px;
        margin: auto;
        margin-top: 50px;
    }
</style>
</head>
<body>
	<jsp:include page="../common/menubar.jsp"/>

    <!-- .outer>br+h2[align=center] -->
    <div class="outer" align="center">
        <br>
        <h2 align="center">공지사항 상세보기</h2>
        <br>

        <table id="detail-area" border="1">
            <!-- (tr>th+td+th+td)*3 -->
            <tr>
                <th width="70">제목</th>
                <td colspan="3" width="430">${ notice.noticeTitle }</td>
            </tr>
            <tr>
                <th>작성자</th>
                <td>${ notice.noticeWriter }</td>
                <th>작성일</th>
                <td>${ notice.createDate }</td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3">
                    <p style="height: 150px;">
                        ${ notice.noticeContent }
                    </p>
                </td>
            </tr>
        </table>
        <br><br>

        <div>
            <a href="list.no" class="btn btn-sm btn-secondary">목록가기</a>

			<c:if test="${ not empty loginUser and loginUser.userId eq notice.noticeWriter }">
            <a href="updateForm.no?num=${ notice.noticeNo }" class="btn btn-sm btn-warning">수정하기</a>
            <a href="delete.no?num=${ notice.noticeNo }" class="btn btn-sm btn-danger">삭제하기</a>
            </c:if>
        </div>
    </div>


</body>
</html>