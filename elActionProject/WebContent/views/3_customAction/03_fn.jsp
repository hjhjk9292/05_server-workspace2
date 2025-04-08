<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>JSTL Functions Library</h1>
	
	<c:set var="str" value="How are you?" />
	
	str : ${ str } <br>
	
	문자열의 길이 : ${ str.length() } <br>
	문자열의 길이 : ${ fn:length(str) } <br> <!-- ArrayList도 제시 가능함 => 리스트의 사이즈 // fn 라이브러리가 el구문에서도 사용할 수 있는 메소드를 제공,, -->
	
	모두 대문자로 출력 : ${ fn:toUpperCase(str) } <br>
	모두 소문자로 출력 : ${ fn:toLowerCase(str) } <br>
	
	are의 시작인덱스 : ${ fn:indexOf(str, 'are') } <br>
	
	are => were 변경 : ${ fn:replace(str, "are", "were") } <br> <!-- 실제 원본 데이터를 바꾸는 것이 아닌 바뀌는 것처럼 보이는 것 -->
	
	<c:if test="${ fn:contains(str, 'are') }">
		포함되어있음
	</c:if>
	
</body>
</html>