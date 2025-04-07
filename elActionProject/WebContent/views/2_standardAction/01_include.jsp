<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<h3>jsp:include</h3>
	<p>또 다른 페이지를 포함하고자 할 때 쓰는 태그</p>
	
	<h4>1. 기존의 include 지시어 이용한 방식 (정적 include 방식 == 컴파일 시 애초에 포함되어 있는 형태)</h4>
	
	<%-- 
	<%@ include file="footer.jsp" %>
	<br><br>
	
	특징 : include 하고 있는 페이지 상에 선언되어 있는 변수를 현재 이 페이지에서도 사용 가능 <br>
	include한 페이지의 year 변수 값 : <%= year %> <br><br>
	
	=> 단, 현재 이 페이지에서 동일한 이름의 변수를 선언할 수 없음 <br>
	<% int year = 2025; %>
	--%>
	
	<h4>2. JSP 표준 액션 태그를 이용한 방식 (동적 include 방식 == 런타임 시 포함되는 형태)</h4>
	
	<jsp:include page="footer.jsp"/>
	<br>
	
	특징1 : include 하고 있는 페이지에 선언된 변수를 공유하지 않음 <br>
	=> 동일한 이름의 변수로 재선언 가능 <br><br>
	<% int year = 2026; %>
	
	특징2 : 포함 시 include 하는 페이지로 값 전달할 수 있음 <br>
	<jsp:include page="footer.jsp">
		<jsp:param value="Hello" name="test"/>
	</jsp:include>
	
	<br>
	
	<jsp:include page="footer.jsp">
		<jsp:param value="Bye" name="test"/>
	</jsp:include>

</body>
</html>