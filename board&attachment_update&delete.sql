-- ÷�������� �ֵ� ���� ���������� ������ѾߵǴ� sql��
UPDATE BOARD
   SET CATEGORY_NO = ?
     , BOARD_TITLE = ?
     , BOARD_CONTENT = ?
 WHERE BOARD_NO = ?
 
 
;

-- CASE1. ������ ÷�������� �־������� �ұ��ϰ� ���ο� ÷�������� �Ѿ���� ���
-- UPDATE ATTACHMENT -- UPDATE�� �ۼ� �� PRIMARY KEY�� WHERE�� �ۼ����ִ� ���� ����(REF �ص� ������,,)

UPDATE ATTACHMENT
   SET ORIGIN_NAME = ���ο�÷�������ǿ�����
     , CHANGE_NAME = ���ο�÷�������Ǽ�����
     , FILE_PATH = ���ο�÷��������������
 WHERE FILE_NO = ����÷�����Ϲ�ȣ


;

-- CASE2. ������ ÷������ �����µ�, ���ο� ÷�������� �Ѿ���� ���
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
  , ��������ϰ��ִ°Խñ۹�ȣ
  , ���ο�÷�������ǿ�����
  , ���ο�÷�������Ǽ�����
  , ���ο�÷��������������  
  )


;

-- �Խñ� ���� -- board, attachment status = 'N'
    UPDATE BOARD
       SET STATUS = 'N'
     WHERE BOARD_NO = ?

;

    UPDATE ATTACHMENT
       SET STATUS = 'N'
     WHERE FILE_NO = ?
     
;
