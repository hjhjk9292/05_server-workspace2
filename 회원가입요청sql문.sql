-- 회원가입 요청 시 실행될 sql문
INSERT
  INTO MEMBER
  (
    USER_NO
  , USER_ID
  , USER_PWD
  , USER_NAME
  , PHONE
  , EMAIL
  , ADDRESS
  , INTEREST
  )
  VALUES
  (
    SEQ_UNO.NEXTVAL
  , ?
  , ?
  , ?
  , ?
  , ?
  , ?
  , ?
  )