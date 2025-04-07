<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<!-- url로는 http://localhost:8003/ea/views/2_standardAction/02_forward.jsp 찍혀 있음 // 외부로부터 찐파일을 숨기고 싶을 때 jsp:forward 사용,, 주로 include와 forward 많이 사용-->
	<h1>여기는 02_forward.jsp 페이지야</h1>
	
	<jsp:forward page="footer.jsp"/>

</body>
</html>