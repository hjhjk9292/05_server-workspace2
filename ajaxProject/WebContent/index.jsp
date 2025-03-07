<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

</head>
<body>
	<h1>AJAX 개요</h1>
	
	<p>
		Asynchronous JavaScript And XML의 약자로 <br>
		서버로부터 데이터를 가져와 전체 페이지를 새로 고치지 않고 일부만 로드할 수 있게 하는 기법 <br>
		우리가 기존에 a태그로 요청 및 form submit 요청 방식은 "동기식 요청" 이었음 => 응답페이지가 돌아와야만 볼 수 있음 (페이지 깜빡거림) <br><br>
		
		* 동기식 / 비동기식 <br>
		> 동기식
		<ul>
			<li>요청 처리 후 그에 해당하는 응답페이지가 돌아와야만 그 다음 작업 가능</li>
			<li>서버에 요청한 결과까지의 시간이 지연되면 무작정 계속 기다려야됨 (흰페이지로 보여질꺼임)</li>
			<li>전체 페이지를 응답해주기 때문에 기본적으로 페이지가 깜빡거림</li>
		</ul>
		
		> 비동기식(ajax)
		<ul>
			<li>현재 페이지를 그대로 유지하면서 중간중간마다 추가적으로 필요한 요청을 보내줄 수 있음</li>
			<li>요청을 보냈다고 해서 다른 페이지로 넘어가지 않음 (현재 페이지 그대로 유지)</li>
			<li>요청을 보내놓고 그에 해당하는 응답(데이터)이 돌아올 때 까지 현재 페이지에서 다른 작업을 할 수 있음</li>
		</ul>
		ex) 실시간 급상승 검색어 로딩, 검색어 자동완성, 아이디 중복체크, 찜하기/해제하기, 추천, 댓글, 무한스크롤링(페이징 대체) 등등...
		<br><br>
		
		* 비동기식의 단점 <br>
		- 현재 페이지 지속적으로 리소스가 쌓임 => 페이지가 느려질 수 있음 <br>
		- 에러 발생 시 디버깅 어려움 <br>
		- 요청 처리 후에 돌아온 데이터를 가지고 현재 페이지에 새로운 요소 만들어서 뿌려줘야 될꺼임 = dom 요소들을 새로이 만들어내는 구문을 잘 익혀둬야됨
	</p>
	
	* AJAX 구현 방식 => 순수 JavaScript 방식 / jQuery 방식(코드가 간결하고 사용하기 쉬움)
	
	<pre>
		* jQuery 방식으로 AJAX 통신
		
		$.ajax({
			속성명:속성값,
			속성명:속성값,
			속성명:속성값,
			...
		})
		
		* 주요 속성
		- url : 요청할 url (필수속성!!!)
		- type|method : 요청 전송 방식 (get/post)
		- data : 요청시 전달할 값
		- success : ajax 통신 성공했을 때 실행할 함수 정의
		- error : ajax 통신 실패했을 때 실행할 함수 정의
		- complete : ajax 통신 실패했든 성공했든 간에 무조건 실행할 함수 정의
		
		* 부수적인 속성
		- async : 서버와의 비동기 처리 방식 설정 여부 (기본값 true)
		- contentType : request 의 데이터 인코딩 방식 정의 (보내는 측의 데이터 인코딩)
		- dataType : 서버에서 response 로 오는 데이터의 데이터 형 설정, 값이 없다면 스마트하게 판단함
		                xml : 트리 형태의 구조
		                json : 맵 형태의 데이터 구조 (일반적인 데이터 구조)
		                script : javascript 및 일반 String 형태의 데이터
		                html : html 태그 자체를 return 하는 방식
		                text : String 데이터
		- accept : 파라미터의 타입을 설정 (사용자 특화 된 파라미터 타입 설정 가능)
		- beforeSend : ajax 요청을 하기 전 실행되는 이벤트 callback 함수 (데이터 가공 및 header 관련 설정)
		- cache : 요청 및 결과값을 scope 에서 갖고 있지 않도록 하는 것 (기본값 true)
		- contents : jQuery 에서 response 의 데이터를 파싱하는 방식 정의
		- context : ajax 메소드 내 모든 영역에서 파싱 방식 정의
		- crossDomain : 타 도메인 호출 가능 여부 설정 (기본값 false)
		- dataFilter : response 를 받았을 때 정상적인 값을 return 할 수 있도록 데이터와 데이터 타입 설정
		- global : 기본 이벤트 사용 여부 (ajaxStart, ajaxStop) (버퍼링 같이 시작과 끝을 나타낼 때, 선처리 작업)
		- password : 서버에 접속 권한 (비밀번호) 가 필요한 경우
		- processData : 서버로 보내는 값에 대한 형태 설정 여부 (기본 데이터를 원하는 경우 false 설정)
		- timeout : 서버 요청 시 응답 대기 시간 (milisecond)
	</pre>
	
	<h1>jQuery 방식을 이용한 AJAX 테스트</h1>
	
	<h3>1. 버튼 클릭 시 get방식으로 서버에 요청 및 응답</h3>
	
	입력 : <input type="text" id="input1">
	<button id="btn1">전송</button>
	<br>
	
	응답 : <span id="output1">현재응답없음</span>
	
	<script>
		$(function(){
			$("#btn1").click(function(){
				
				// 기존에 동기식 통신
				// location.href = 'jqAjax1.do?input=' + $("#input1").val()
						
				// 비동기식 통신
				$.ajax({
					url:"jqAjax1.do",
					data:{input:$("#input1").val()},
					type:"get",
					success:function(result){ // 매개변수에 돌아온 응답데이터 담김
						console.log("ajax 통신 성공")
						console.log(result)
						$("#output1").text(result);
					},
					error:function(){
						console.log("ajax 통신 실패!!!")
					},
					complete:function(){
						console.log("ajax 통신 성공여부와 관련없이 무조건 출력!")
					},
				})
				
				
			})
		})
	</script>

</body>
</html>