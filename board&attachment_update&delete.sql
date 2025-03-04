-- 첨부파일이 있든 없든 공통적으로 수행시켜야되는 sql문
UPDATE BOARD
   SET CATEGORY_NO = ?
     , BOARD_TITLE = ?
     , BOARD_CONTENT = ?
 WHERE BOARD_NO = ?
 
 
;

-- CASE1. 기존에 첨부파일이 있었음에도 불구하고 새로운 첨부파일이 넘어왔을 경우
-- UPDATE ATTACHMENT -- UPDATE문 작성 시 PRIMARY KEY로 WHERE절 작성해주는 것이 안전(REF 해도 되지만,,)

UPDATE ATTACHMENT
   SET ORIGIN_NAME = 새로운첨부파일의원본명
     , CHANGE_NAME = 새로운첨부파일의수정명
     , FILE_PATH = 새로운첨부파일의저장경로
 WHERE FILE_NO = 기존첨부파일번호


;

-- CASE2. 기존의 첨부파일 없었는데, 새로운 첨부파일이 넘어왔을 경우
-- INSERT ATTACHMENT
INSERT
  INTO ATTACHMENT
  (
    FILE_NO
  , REF_BNO
  , ORIGIN_NAME
  , CHANGE_NAME
  , FILE_PATH
  )
  VALUES
  (
    SEQ_FNO.NEXTVAL
  , 현재수정하고있는게시글번호
  , 새로운첨부파일의원본명
  , 새로운첨부파일의수정명
  , 새로운첨부파일의저장경로  
  )


;

-- 게시글 삭제 -- board, attachment status = 'N'
    UPDATE BOARD
       SET STATUS = 'N'
     WHERE BOARD_NO = ?

;

    UPDATE ATTACHMENT
       SET STATUS = 'N'
     WHERE FILE_NO = ?
     
;
