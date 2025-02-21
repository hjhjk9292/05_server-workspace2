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
        height: 500px;
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

        <form id="mypage-form" action="<%= contextPath %>/update.me" method="post">
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
                <button type="submit" class="btn btn-sm btn-secondary">정보변경</button>
                <button type="button" class="btn btn-sm btn-warning" data-toggle="modal" data-target="#updatePwdModal">비밀번호변경</button>
                <button type="button" class="btn btn-sm btn-danger" data-toggle="modal" data-target="#deleteModal">회원탈퇴</button>
            </div>

        </form>
    </div>
    
    
    
    
    <!-- 회원 탈퇴용 모달 -->
	<div class="modal" id="deleteModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">회원 탈퇴</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body" align="center">
	        
	        <form action="<%= contextPath %>/delete.me" method="post">
	        	<b> 탈퇴 후 복구가 불가능합니다. <br> 정말로 탈퇴하시겠습니까?</b>
	        	<br><br>
	        	
	        	<input type="hidden" name="userId" value="<%= loginUser.getUserId() %>">  
	        	비밀번호 : <input type="password" name="userPwd" required> <br><br>
	        	<button type="submit" class="btn btn-sm btn-danger" onclick="deleteMember()">탈퇴하기</button>
	        	
	        	<!-- 
	        		요청 시 실행할 sql문
	        		UPDATE MEMBER
	        		   SET STATUS = 'N'
	        		     , MODIFY_DATE = SYSDATE
	        		 WHERE USER_ID = 현재로그인한회원아이디
	        		   AND USER_PWD = 사용자가입력한비번
	        		   
	        		 (정보변경, 비밀번호 변경처럼 갱신된 회원 다시 조회할 필요 없음)
	        		 
	        		 성공했을 경우 => 메인페이지 alert(성공적으로 회원탈퇴되었습니다. 그동안 이용해주셔서 감사합니다.)
	        		 				단, 로그아웃 되어있어야함 (세션에 loginUser 라는 키값을 지우면됨(삭제))
	        		 
	        		 실패했을 경우 => 마이페이지 alert(회원탈퇴실패!)
	        	 -->
   
	        </form>



	      </div>
	
	    </div>
	  </div>
	</div>

    
    
    
    
    <!-- 비밀번호 변경용 모달 -->
	<div class="modal" id="updatePwdModal">
	  <div class="modal-dialog">
	    <div class="modal-content">
	
	      <!-- Modal Header -->
	      <div class="modal-header">
	        <h4 class="modal-title">비밀번호 변경</h4>
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	      </div>
	
	      <!-- Modal body -->
	      <div class="modal-body" align="center">
	        
	        <form action="<%= contextPath %>/updatePwd.me" method="post">
	        
	        	<input type="hidden" name="userId" value="<%= userId %>"> <!-- 입력 안 받고도 값을 가져가고 싶을 때 hidden사용 -->
	        
	        	<table>
	        		<tr>
	        			<td>현재 비밀번호</td>
	        			<td><input type="password" name="userPwd"></td>
	        		</tr>
	        		<tr>
	        			<td>변경할 비밀번호</td>
	        			<td><input type="password" name="updatePwd"></td>
	        		</tr>
	        		<tr>
	        			<td>변경할 비밀번호 확인</td>
	        			<td><input type="password" name="checkPwd"></td>
	        		</tr>
	        	</table>
	        	<br>
	        	
	        	<button type="submit" class="btn btn-sm btn-secondary" onclick="return validatePwd()">비밀번호 변경</button>
	        
	        </form>

			<script>
				function validatePwd(){
					if($("input[name=updatePwd]").val() != $("input[name=checkPwd]").val()){ // input중에서도 name속성의 값이 checkPwd인 것의 val
						alert("변경할 비밀번호가 일치하지 않습니다.");
						return false; // 비밀번호가 맞지 않으면 전달(submit)이 안될것임,,
					} 
					
				}
			</script>




	      </div>
	
	    </div>
	  </div>
	</div>

</body>
</html>