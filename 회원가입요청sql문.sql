-- ȸ������ ��û �� ����� sql��
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