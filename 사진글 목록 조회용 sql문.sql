-- ������ ��� ��ȸ�� sql 
-- JOIN�Ұǵ� ATTACHMENT���� STATUS�� �ֱ� ������ BOARD ���̺��̶�� �ǹ��� ��ĪB ���̱�
-- ���Ῥ���ڷ� �̾��ְ� TITLEIMG ��Ī �ٿ��ֱ�
SELECT
       BOARD_NO
     , BOARD_TITLE
     , COUNT
     , FILE_PATH || CHANGE_NAME "TITLEIMG"
  FROM BOARD B
  JOIN ATTACHMENT ON (BOARD_NO = REF_BNO)
 WHERE BOARD_TYPE = 2
   AND B.STATUS = 'Y'
   AND FILE_LEVEL = 1
 ORDER
    BY BOARD_NO DESC