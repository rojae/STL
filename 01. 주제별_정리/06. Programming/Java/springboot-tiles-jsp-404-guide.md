
# 🚫 Spring Boot + Tiles 3 환경에서 JSP 404 오류 원인 및 해결책

## ❗ 문제 요약
Spring Boot 실행 JAR 환경에서 다음과 같은 Tiles 설정을 사용할 때:

```xml
<definition name=".empty/*" template="/WEB-INF/tiles/empty.jsp">
    <put-attribute name="body" value="/{1}.jsp" />
</definition>
```

컨트롤러 예:

```java
@GetMapping("/login")
public String loginForm(@CurrentUser Account account) {
    return ".empty/blog/login";
}
```

JSP 파일 위치:
```
src/main/webapp/blog/login.jsp
```

하지만 JAR 실행 시 **404 Not Found** 오류 발생.

## 🧠 원인 분석
### 1. JSP가 JAR에 포함되지 않음
- JAR 패키징 시 `src/main/webapp`은 자동 포함되지 않음
- 내장 Tomcat이 JSP를 찾지 못해 Tiles include 실패 → 404

### 2. 내장 톰캣의 JSP 처리 비활성화
- Spring Boot 2.4+에서는 기본 JSP 서블릿(DefaultServlet)이 비활성화

### 3. ViewResolver 충돌 (Tiles vs. InternalResourceViewResolver)
- 아래 설정이 남아 있으면 IRVR이 Tiles보다 먼저 동작

```properties
spring.mvc.view.prefix=/WEB-INF/jsp/
spring.mvc.view.suffix=.jsp
```

## ✅ 해결 방법

### ✅ 1. JSP를 포함시키는 방식 변경

#### ☑️ WAR 패키징 사용 (권장)
```xml
<packaging>war</packaging>
```

#### ☑️ JAR 유지 시 META-INF/resources 로 이동
```
src/main/resources/META-INF/resources/blog/login.jsp
```

### ✅ 2. 빈 레이아웃 Tiles 템플릿 추가

**empty.jsp**
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>
<tiles:insertAttribute name="body" />
```

### ✅ 3. application.properties 정리

```properties
# spring.mvc.view.prefix=/WEB-INF/jsp/
# spring.mvc.view.suffix=.jsp
server.servlet.register-default-servlet=true
```

ViewResolver 수동 설정 (선택) :

```java
@Bean
public TilesViewResolver tilesViewResolver() {
    TilesViewResolver resolver = new TilesViewResolver();
    resolver.setOrder(1); return resolver;
}
```

## 📂 예시 구조

```
src/
├── main/
│   └── resources/
│       └── META-INF/resources/
│           └── blog/login.jsp
│   └── webapp/
│       └── WEB-INF/tiles/empty.jsp
```

## 📦 JAR 안에 포함되었는지 확인
```bash
jar tf target/rlog-1.0.jar | grep login.jsp
```

## ✅ 체크리스트

| 항목 | 상태 |
|------|------|
| JSP가 ServletContext 경로에 존재 | ✅ |
| empty.jsp 존재 및 Tiles 정의 정상 | ✅ |
| prefix/suffix 제거 또는 우선순위 조정 | ✅ |
| default-servlet 설정 활성화 | ✅ |

## 📚 참고

- [Spring Boot JSP 제한 사항](https://docs.spring.io/spring-boot/docs/current/reference/html/web.html#web.servlet.spring-mvc.view-resolvers)
- [Tiles + JSP JAR 배포 StackOverflow](https://stackoverflow.com/questions/36350008/jsp-in-spring-boot-jar)
