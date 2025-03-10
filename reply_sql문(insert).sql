INSERT
  INTO REPLY
  (
    REPLY_NO
  , REPLY_CONTENT
  , REF_BNO
  , REPLY_WRITER
  )
  VALUES
  (
    SEQ_RNO.NEXTVAL
  , 사용자가 입력한 댓글 내용
  , 현재 보고 있는 게시글 번호
  , 로그인한 회원번호
  )