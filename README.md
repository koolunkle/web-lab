# Kotlin Spring Security JWT Example

본 프로젝트는 Kotlin과 Spring Boot를 기반으로 JJWT 및 Spring Security를 활용하여 구축된 회원 인증 시스템 예제입니다.

## 주요 기능

### 보안
- JWT 기반 인증 및 인가: Access Token과 Refresh Token을 활용한 Stateless 인증
- Refresh Token Rotation (RTR): 토큰 재발급 시마다 Refresh Token을 교체하여 보안 강화
- 토큰 탈취 감지: 이미 사용된 Refresh Token으로 재발급 시도 시 해당 사용자의 모든 세션 무효화
- 로그아웃 및 세션 제어: 로그아웃 시 DB 내 Refresh Token 삭제를 통한 접근 차단
- 요청 제한 (Rate Limiting): 로그인 및 토큰 재발급 엔드포인트에 대한 브루트포스 공격 방지
- 권한 제어: 사용자 역할에 따른 API 접근 제어

### 운영 및 인프라
- 구조화된 로깅: MDC를 활용하여 모든 요청에 고유 ID 부여 및 로그 추적
- 데이터베이스 마이그레이션: Flyway를 통한 스키마 버전 관리
- 환경별 설정 분리: 로컬 및 운영 환경 프로필 분리
- 상태 확인: 서비스 모니터링을 위한 헬스 체크 엔드포인트 제공

### 코드 품질
- Kotlin 문법 활용: Null-safety 보장 및 간결한 코드 구성
- DTO 활용: 요청과 응답 목적에 따른 객체 분리 및 수정 범위 제한
- 공통 응답 규격: 일관된 API 응답 포맷 정의

## 기술 스택
- 언어: Kotlin
- 프레임워크: Spring Boot 3.x
- 보안: Spring Security, JJWT
- 지속성: Spring Data JPA, MariaDB
- 데이터베이스 마이그레이션: Flyway
- 라이브러리: Bucket4j, Jackson

## 시작하기

### 1. 환경 설정
- Java: 17 이상
- 데이터베이스: MariaDB
- JWT Secret: 환경 변수 `JWT_SECRET` 설정 또는 `application.yaml` 수정

### 2. 실행 방법
```bash
./gradlew bootRun
```

### 3. API 테스트
- `http/member.http` 파일을 사용하여 API 테스트 가능
- 테스트 순서: 회원가입 -> 로그인 -> 내 정보 조회/수정/로그아웃

## 프로젝트 구조
- `common`: 보안 설정, 필터, 공통 DTO, 예외 처리 등 전역 설정
- `member`: 회원 관련 도메인 로직 (엔티티, 레포지토리, 서비스, 컨트롤러)
- `db/migration`: Flyway 스키마 버전 관리 스크립트

## API 명세 요약
| 기능      |  메서드   | 엔드포인트                  |   권한   |
|:--------|:------:|:-----------------------|:------:|
| 회원가입    |  POST  | `/api/member/signup`   |   익명   |
| 로그인     |  POST  | `/api/member/login`    |   익명   |
| 토큰 재발급  |  POST  | `/api/member/refresh`  |   익명   |
| 내 정보 조회 |  GET   | `/api/member/info`     | MEMBER |
| 내 정보 수정 |  PUT   | `/api/member/info`     | MEMBER |
| 로그아웃    |  POST  | `/api/member/logout`   | MEMBER |
| 회원 탈퇴   | DELETE | `/api/member/withdraw` | MEMBER |
