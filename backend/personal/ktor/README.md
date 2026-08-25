# ktor-server-study

[Ktor](https://ktor.io/)로 만든 간단한 Todo CRUD API 예제 프로젝트입니다. 메모리 저장소, OpenAPI(Swagger) 문서 자동 생성, `.http` 요청 예제까지 포함한 최소 구성으로 Ktor 서버 개발 흐름을 익히기 위해 만들었습니다.

## 기술 스택

| 구분 | 내용 |
|---|---|
| 언어 | Kotlin 2.4.10 |
| 런타임 | Java 17 |
| 프레임워크 | Ktor 3.5.2 (Netty 엔진) |
| 빌드 도구 | Gradle (Kotlin DSL) + Version Catalog (`gradle/libs.versions.toml`) |
| 직렬화 | kotlinx.serialization (JSON) |
| API 문서 | [ktor-openapi](https://github.com/SMILEY4/ktor-openapi-tools) + Swagger UI (code-first) |
| 로깅 | Logback |

## 프로젝트 구조

```
src/main/kotlin/com/example/
  Application.kt              # 엔트리 포인트 (EngineMain), 모듈 조립
  plugins/
    Serialization.kt          # ContentNegotiation + kotlinx.serialization JSON
    StatusPages.kt            # 예외 -> HTTP 상태 코드 매핑
    OpenApi.kt                # OpenAPI 스펙 생성 + Swagger UI 라우트
    Routing.kt                # 루트 라우팅 구성
  models/
    Todo.kt                   # Todo 데이터 모델
  repositories/
    TodoRepository.kt         # 메모리(ConcurrentHashMap) 기반 저장소
  routes/
    TodoRoutes.kt              # /todos CRUD 라우트 (OpenAPI 문서화 포함)
src/main/resources/
  application.conf            # 서버 포트, 모듈 설정
  logback.xml                 # 로깅 설정
src/test/kotlin/com/example/
  ApplicationTest.kt          # testApplication 기반 CRUD 통합 테스트
http/
  todo.http                   # IntelliJ HTTP Client용 API 요청 예제
```

## 실행 방법

```bash
./gradlew run
```

기본적으로 `http://localhost:8080` 에서 서버가 기동됩니다.

## 빌드 및 테스트

```bash
./gradlew build
```

## API

메모리에 저장되는 간단한 Todo 리소스에 대한 CRUD API입니다.

| Method | Path | 설명 |
|---|---|---|
| GET | `/todos` | Todo 목록 조회 |
| GET | `/todos/{id}` | Todo 단건 조회 |
| POST | `/todos` | Todo 생성 |
| PUT | `/todos/{id}` | Todo 수정 |
| DELETE | `/todos/{id}` | Todo 삭제 |

요청/응답 예시는 [`http/todo.http`](./http/todo.http) 파일에서 바로 실행해 확인할 수 있습니다 (IntelliJ HTTP Client 사용).

### 요청 예시

```bash
curl -X POST http://localhost:8080/todos \
  -H "Content-Type: application/json" \
  -d '{"title":"Buy milk"}'
```

## API 문서 (Swagger)

서버 실행 후 아래 주소에서 확인할 수 있습니다.

- Swagger UI: `http://localhost:8080/swagger`
- OpenAPI 스펙(JSON): `http://localhost:8080/openapi.json`

라우트 정의에 문서화 DSL을 함께 작성하는 code-first 방식이라, 코드와 API 문서가 항상 일치합니다.
