<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
        height: 400px;
        margin: auto;
        margin-top: 50px;
    }

    #mypage-form table{
        margin: auto;
    }

    #mypage-form input{
        margin: 5px;
    }
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %>
	
	<%
		String userId = loginUser.getUserId();
		String userName = loginUser.getUserName();
		String phone = (loginUser.getPhone() == null) ? "" : loginUser.getPhone(); // 필수입력사항 아니어서 null 존재 가능 → 삼항연산자로 표기
		String email = (loginUser.getEmail() == null) ? "" : loginUser.getEmail();
		String address = (loginUser.getAddress() == null) ? "" : loginUser.getAddress();
		String interest = (loginUser.getInterest() == null) ? "" : loginUser.getInterest();
		
	%>

    <div class="outer">
        <br>
        <h2 align="center">마이페이지</h2>

        <form id="mypage-form" action="" method="post">
            <table>
                <tr>
                    <td>* 아이디</td>
                    <td><input type="text" name="userId" maxlength="12" value="<%= userId %>" readonly></td>
                    <td><button type="button">중복확인</button></td>
                </tr>
                <tr>
                    <td>* 이름</td>
                    <td><input type="text" name="userName" maxlength="6" value="<%= userName %>" required></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;전화번호</td>
                    <td><input type="text" name="phone" placeholder="- 포함해서 입력" value="<%= phone %>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;이메일</td>
                    <td><input type="email" name="email" value="<%= email %>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;주소</td>
                    <td><input type="text" name="address" value="<%= address %>"></td>
                    <td></td>
                </tr>
                <tr>
                    <td>&nbsp;&nbsp;관심분야</td>
                    <td colspan="2">
                        <input type="checkbox" name="interest" id="sports" value="운동">
                        <label for="sports">운동</label>

                        <input type="checkbox" name="interest" id="hiking" value="등산">
                        <label for="hiking">등산</label>

                        <input type="checkbox" name="interest" id="fishing" value="낚시">
                        <label for="fishing">낚시</label>
                        <br>
                        <input type="checkbox" name="interest" id="cooking" value="요리">
                        <label for="cooking">요리</label>

                        <input type="checkbox" name="interest" id="game" value="게임">
                        <label for="game">게임</label>

                        <input type="checkbox" name="interest" id="movie" value="영화">
                        <label for="movie">영화</label>
                    </td>
                </tr>
            </table>
            
            <script>
            $(function(){
            	const interest = "<%= interest %>";
        		// "" | "운동,등산,게임"
        		
        		$("input[type=checkbox]").each(function(){
        			
        			// $(this) : 순차적으로 접근되는 체크박스 요소
        			// $(this).val() : 해당 체크박스의 value 값
        			if(interest.search($(this).val()) != -1){ // 찾았다! ㅡ "운동,등산,게임".search("운동")) // 만약 -1일 경우 찾고자 하는 문자열이 없는 것
        				$(this).attr("checked", true); // this 속성 변경해서 ㄴ 찾을 때마다 체크하겠다!
        			}
        			
        		})
            })
            	
            </script>

            <br><br>

            <div align="center">
                <button type="submit">정보변경</button>
                <button type="button">비밀번호변경</button>
                <button type="button">회원탈퇴</button>
            </div>



        </form>
    </div>

</body>
</html>