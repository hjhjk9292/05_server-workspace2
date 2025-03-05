-- 사진 게시글 작성 요청 시 실행할 sql문
-- insert board
INSERT
  INTO BOARD
  (
    BOARD_NO
  , BOARD_TYPE
  , BOARD_TITLE
  , BOARD_CONTENT
  , BOARD_WRITER
  )
  VALUES
  (
    SEQ_BNO.NEXTVAL
  , 2
  , 사용자가입력한제목
  , 사용자가입력한내용
  , 로그인한회원번호
  )
  
;