-- insert attachment 반복적 (넘어온 첨부파일 개수만큼)
INSERT
  INTO ATTACHMENT
  (
    FILE_NO
  , REF_BNO
  , ORIGIN_NAME
  , CHANGE_NAME
  , FILE_PATH
  , FILE_LEVEL  
  )
  VALUES
  (
    SEQ_FNO.NEXTVAL
  , SEQ_BNO.CURRVAL -- board와 attachment 하나의 트랜젝션에 묶어서 TKDYD
  , 첨부파일원본명
  , 첨부파일수정명
  , 저장경로
  , 대표이미지일경우1|상세이미지일경우2
  )