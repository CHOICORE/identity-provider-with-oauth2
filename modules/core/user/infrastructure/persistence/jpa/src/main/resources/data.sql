INSERT INTO `USER` ( USER_ID
                   , EMAIL
                   , PASSWORD
                   , PHONE_NUMBER
                   , PASSWORD_EXPIRATION_AT
                   , FAILED_LOGIN_ATTEMPTS
                   , LAST_LOGGED_IN_AT
                   , EMAIL_VERIFIED
                   , EMAIL_VERIFIED_AT
                   , PHONE_NUMBER_VERIFIED
                   , PHONE_NUMBER_VERIFIED_AT
                   , REGISTERED_AT)
VALUES ( '2'
       , '2'
       , '{noop}2'
       , null
       , now()
       , 0
       , now()
       , true
       , now()
       , true
       , now()
       , now());

INSERT INTO `USER` ( USER_ID
                   , EMAIL
                   , PASSWORD
                   , PHONE_NUMBER
                   , PASSWORD_EXPIRATION_AT
                   , FAILED_LOGIN_ATTEMPTS
                   , LAST_LOGGED_IN_AT
                   , EMAIL_VERIFIED
                   , EMAIL_VERIFIED_AT
                   , PHONE_NUMBER_VERIFIED
                   , PHONE_NUMBER_VERIFIED_AT
                   , REGISTERED_AT)
VALUES ( '1'
       , '1'
       , '{noop}1'
       , '1'
       , DATEADD('DAY', 1, NOW())
       , 0
       , now()
       , true
       , now()
       , true
       , now()
       , now());

INSERT INTO TERMS_AND_CONDITIONS( SUBJECT
                                , CONTENT
                                , DESCRIPTION
                                , VERSION
                                , USED
                                , MANDATORY
                                , REGISTERED_AT)
VALUES ( '개인정보 수집 및 이용 필수 동의'
       , '개인정보 수집 및 이용 필수 동의 설명'
       , '개인정보 수집 및 이용 필수 동의 내용'
       , '0.0.1'
       , true
       , true
       , now()),
       ( '서비스 이용 약관 동의'
       , '서비스 이용 약관 동의 설명'
       , '서비스 이용 약관 동의 내용'
       , '0.0.1'
       , true
       , true
       , now()),
       ( '개인정보 수집 및 이용 선택 동의'
       , '개인정보 수집 및 이용 선택 동의 설명'
       , '개인정보 수집 및 이용 선택 동의 내용'
       , '0.0.1'
       , false
       , true
       , now());

-- INSERT INTO `USER`

INSERT INTO AUTHORITY( NAME
                     , SCOPE
                     , DESCRIPTION
                     , REGISTERED_AT)
VALUES ('USER', 'ACCOUNT', '사용자 권한', now()),
       ('ADMIN', 'ACCOUNT', '관리자 권한', now());
