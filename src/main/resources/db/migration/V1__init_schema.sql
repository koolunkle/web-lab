-- 회원 테이블
CREATE TABLE member
(
    id         BIGINT       NOT NULL AUTO_INCREMENT,
    login_id   VARCHAR(30)  NOT NULL,
    password   VARCHAR(100) NOT NULL,
    name       VARCHAR(10)  NOT NULL,
    birth_date DATE         NOT NULL,
    gender     VARCHAR(5)   NOT NULL,
    email      VARCHAR(30)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_member_login_id UNIQUE (login_id)
);

-- 회원 권한 테이블
CREATE TABLE member_role
(
    id        BIGINT      NOT NULL AUTO_INCREMENT,
    role      VARCHAR(30) NOT NULL,
    member_id BIGINT,
    PRIMARY KEY (id),
    CONSTRAINT fk_member_role_member_id FOREIGN KEY (member_id) REFERENCES member (id)
);

-- Refresh Token 테이블 (회원당 1개 유지, PK = member_id)
CREATE TABLE refresh_token
(
    member_id  BIGINT       NOT NULL,
    token_hash VARCHAR(64)  NOT NULL,
    expires_at DATETIME(6)  NOT NULL,
    PRIMARY KEY (member_id)
);
