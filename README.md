# SKKU 캡스톤 프로젝트 2024: 서버

# 서비스 소개

좋아하는 음악을 기록하고, 공유하는 SNS 서비스입니다.

| ![E8803C2E-AC52-47F5-8833-5197198CBB93_1_201_a](https://github.com/user-attachments/assets/dcdae114-bd18-41f8-a703-69294f5c993d)| ![29D759BE-CEC8-4190-B75A-676B55BAA975_1_201_a](https://github.com/user-attachments/assets/36d109dd-7f56-4e23-ac49-aa4152528e8b) | ![4277FDD2-B605-46E0-8301-2E608FC1F077_1_201_a](https://github.com/user-attachments/assets/3639e7bb-4fb9-42f0-9b1e-e1843aeeda88) | ![CE2C9B22-D8B7-4296-8CF2-9760F56CE47F_1_201_a](https://github.com/user-attachments/assets/35c7fea1-c342-4674-a134-5f840a6990cf) |
|---------|---------|---------|---------|


## 개요
이 프로젝트는 SKKU 캡스톤 프로젝트 2024의 서버 애플리케이션입니다. Java와 Spring Boot로 개발되었으며, 웹 서비스, 보안, 데이터 영속성 등의 기능을 지원하기 위해 다양한 의존성을 포함하고 있습니다.

## 기능
- **Spring Boot**: 빠른 개발을 위한 애플리케이션 빌딩
- **Spring Data JPA**: 데이터베이스 상호작용
- **OAuth2 클라이언트**: 인증 및 인가 지원
- **Spring Security**: 애플리케이션 보안
- **OpenAPI**: API 문서화
- **MySQL**: 주요 데이터베이스
- **JWT**: JSON 웹 토큰 처리
- **AWS SDK**: Amazon S3와 상호작용

## 설치 방법

1. **레포지토리 클론**:
    ```sh
    git clone https://github.com/SKKU-Capston-Project-2024/server.git
    cd server
    ```

2. **프로젝트 빌드**:
    ```sh
    ./gradlew build
    ```

3. **애플리케이션 실행**:
    ```sh
    ./gradlew bootRun
    ```

## 설정
애플리케이션은 `build.gradle` 파일에 정의된 여러 의존성과 설정을 사용합니다. 애플리케이션이 정상적으로 작동하려면 데이터베이스 설정 및 AWS 자격 증명을 구성해야 할 수 있습니다.

## 사용법
서버를 실행한 후 컨트롤러에 정의된 지정된 엔드포인트로 이동하여 애플리케이션을 사용합니다. OpenAPI 문서를 통해 사용 가능한 API를 이해하고 테스트할 수 있습니다.


## 라이선스
이 프로젝트는 MIT 라이선스 하에 라이선스됩니다.

---

프로젝트와 사용법에 대한 더 구체적인 세부 사항을 추가하고 싶은 부분이 있으면 말씀해 주세요.
