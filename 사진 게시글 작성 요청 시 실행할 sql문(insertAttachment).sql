-- insert attachment �ݺ��� (�Ѿ�� ÷������ ������ŭ)
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
  , SEQ_BNO.CURRVAL -- board�� attachment �ϳ��� Ʈ�����ǿ� ��� TKDYD
  , ÷�����Ͽ�����
  , ÷�����ϼ�����
  , ������
  , ��ǥ�̹����ϰ��1|���̹����ϰ��2
  )