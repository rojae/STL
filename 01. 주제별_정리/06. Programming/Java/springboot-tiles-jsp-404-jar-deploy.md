# Spring Boot + Tiles 3에서 JSP 404 오류 원인 및 해결책

## 404 오류 발생 원인 분석

Spring Boot의 **실행가능 JAR(embedded Tomcat)** 환경에서 JSP를 사용하면 경로 문제로 뷰를 찾지 못해 404 오류가 발생할 수 있습니다. Spring Boot 공식 문서에서도 \*“실행 가능한 JAR을 사용할 경우 JSP는 지원되지 않는다”\*고 명시합니다. 이는 JAR로 패키징하면 `src/main/webapp`의 JSP 파일이 포함되지 않거나 올바르게 로드되지 않기 때문입니다. 구체적으로:

- **JSP가 JAR에 포함되지 않음**: Maven/Gradle로 JAR 패키징 시 `src/main/webapp` 폴더의 JSP가 클래스패스에 포함되지 않아, **내장 Tomcat이 해당 JSP 파일을 찾지 못합니다**. 결과적으로 Tiles가 정의한 `/blog/login.jsp` 경로를 리소스로 찾을 수 없어서 404가 발생합니다.
- **내장 톰캣의 JSP 처리 이슈**: Spring Boot 2.4부터는 내장 컨테이너의 DefaultServlet(정적 리소스 및 JSP 서블릿)이 기본 등록되지 않으므로, JSP 처리가 비활성화될 수 있습니다. 이 설정이 없으면 Tiles가 JSP를 include하려 해도 톰캣이 처리하지 않아 뷰를 찾지 못하게 됩니다.

위 로그에서 **TilesViewPreparer 호출 후 DispatcherServlet이 404를 리턴**한 것은, Tiles 정의는 매칭되었으나 결국 JSP를 **로드하지 못해** 뷰 해결에 실패했음을 보여줍니다. 즉, `.empty/blog/login` Tiles 정의에 따라 `/WEB-INF/tiles/empty.jsp` 레이아웃을 사용하고 `{1}`에 해당하는 `/blog/login.jsp`를 body로 넣으려 했지만, **내장 JAR 환경에서 해당 JSP 경로를 찾을 수 없어** 404가 난 것입니다.

## `spring.mvc.view.prefix` 설정이 남아있을 때의 문제점

`application.properties`에 `spring.mvc.view.prefix=/WEB-INF/jsp/` (및 suffix 설정)이 남아있는 경우, \*\*기본 JSP 뷰리졸버(InternalResourceViewResolver)\*\*가 활성화됩니다. Tiles를 사용할 때 이 기본 뷰리졸버가 동시에 존재하면 **뷰 해석 우선순위 충돌**이 일어날 수 있습니다.

Spring MVC에서는 \*\*InternalResourceViewResolver(IRVR)\*\*가 항상 모든 뷰이름에 대해 시도하려는 특성이 있으므로(특히 순서가 높을 경우) 반드시 **가장 마지막 순서**로 둬야 합니다. 그러나 Spring Boot는 `spring.mvc.view.prefix`가 있으면 자동으로 IRVR을 등록하며, 특별히 우선순위를 조정하지 않으면 Tiles보다 먼저 동작할 수 있습니다. 그 결과:

- 컨트롤러에서 `.empty/blog/login` 같은 Tiles 뷰 이름을 리턴해도, IRVR이 이를 **`/WEB-INF/jsp/.empty/blog/login.jsp`** 경로로 해석하려고 시도할 수 있습니다. 당연히 이런 JSP 파일은 없으므로 IRVR은 뷰를 찾지 못하거나 잘못된 뷰로 간주하게 됩니다.
- **IRVR이 먼저 처리**하면 TilesViewResolver까지 요청이 도달하지 않을 수 있습니다 (예전 버전에서는 IRVR이 존재 여부와 상관없이 모든 뷰 이름을 처리하려고 했습니다). 다행히 Spring 5+에서는 뷰 파일 존재 여부를 체크하여 없으면 다음 뷰리졸버로 넘기지만, 설정에 따라 IRVR이 Tiles보다 우선하면 Tiles가 아예 적용되지 않는 문제도 생길 수 있습니다.
- 요약하면, **Tiles와 기본 JSP ViewResolver가 공존**하면 경로 충돌이나 **중복 해석**으로 인해 뷰 해결 실패(404)나 예상치 못한 뷰 출력 문제가 발생할 수 있습니다.

특히 현재 상황에서는 `prefix=/WEB-INF/jsp/`로 설정돼 있지만 실제 JSP는 `/blog/login.jsp`에 있으므로, IRVR이 **잘못된 경로**(`/WEB-INF/jsp/blog/login.jsp`)를 찾다가 실패할 가능성이 큽니다. 이는 Tiles가 없을 때 `.jsp` 뷰를 보여주기 위해 설정했던 prefix가 오히려 Tiles 사용 시 혼선을 준 사례입니다.

## `.empty/*` 정의로 JSP를 레이아웃 없이 렌더링하기 위한 해결책

문제를 해결하고 `.empty/*` Tiles 정의를 통해 JSP를 레이아웃 없이 출력하려면 **다음 조치들을 취해야 합니다**:

1. **JSP 파일의 패키징 방식 수정**: 실행 JAR 환경에서 JSP를 찾도록 하려면 프로젝트 구조를 조정해야 합니다. 두 가지 방법이 있습니다:

   - **WAR 패키징으로 변경**: Maven이나 Gradle의 packaging을 `war`로 설정합니다. Spring Boot에서는 WAR로 빌드해도 `java -jar`로 실행 가능하며, JSP를 포함한 웹자원이 정상적으로 패키징됩니다. 이 방식이 Spring Boot 공식 권장 해결책입니다. WAR로 빌드하면 `src/main/webapp`의 JSP (`/blog/login.jsp` 및 `/WEB-INF/tiles/empty.jsp`)가 WAR에 포함되어 정상적으로 로드됩니다.
   - **JSP를 클래스패스로 이동**: 만약 JAR 패키징을 유지해야 한다면, JSP 파일을 클래스패스 리소스 경로로 옮겨야 합니다. 예를 들어 `src/main/resources/META-INF/resources/WEB-INF/jsp/` 이하로 JSP를 위치시키고, `application.properties`에서 `spring.mvc.view.prefix=/WEB-INF/jsp/` 및 `suffix=.jsp`로 맞춥니다. 이렇게 하면 내장 Tomcat이 JAR 내부에서도 JSP를 **`/WEB-INF/jsp/..`**** 경로**로 인식하여 로드할 수 있습니다. (Spring Boot 실행 JAR는 `META-INF/resources`를 웹 루트로 매핑합니다.)\
     ※ 위 방법 중 하나를 적용하면, **JSP 파일이 내장 톰캣의 검색 경로에 포함**되어 `.empty` Tiles 정의에서 지정한 경로를 찾을 수 있게 됩니다. 예를 들어, JSP를 `META-INF/resources/WEB-INF/jsp/blog/login.jsp`로 옮겼다면 `.empty/blog/login` 정의의 `value="/{1}.jsp"`를 `value="/WEB-INF/jsp/{1}.jsp"`처럼 수정하여 Tiles가 `/WEB-INF/jsp/blog/login.jsp`를 include하도록 정의를 바꾸는 것이 좋습니다. 또는 prefix 설정대로 Tiles 대신 IRVR가 처리하게 할 수도 있지만, 보안상 JSP는 WEB-INF 안에 두는 편이 안전합니다.

2. **뷰리졸버 우선순위 및 설정 정리**: Tiles를 사용할 경우 **기본 JSP 뷰리졸버를 비활성화하거나 우선순위를 조정**해야 합니다.

   - 가장 간단한 방법은 `spring.mvc.view.prefix`와 `.suffix` 설정을 **제거**하여 Spring Boot가 IRVR를 자동 등록하지 않도록 하는 것입니다. 대신 `TilesViewResolver`를 명시적으로 설정하고, 필요하다면 IRVR를 수동으로 추가하되 `order`를 가장 낮게 줘서 Tiles 이후에만 동작하게 합니다.
   - 혹은 `spring.mvc.view.prefix`를 그대로 두더라도, `spring.mvc.view.order=2` 와 같이 InternalResourceViewResolver의 순서를 TilesViewResolver(예: order=1) 뒤로 낮출 수 있습니다. (Spring Boot 2.x에서는 기본 IRVR의 order가 \*\*후순위(직접 설정하지 않으면 `Ordered.LOWEST_PRECEDENCE`)\*\*로 되어 있으나, 확실히 하기 위해 수동 설정을 권장합니다.)
   - 또한 `.empty/*`와 같이 Tiles가 처리해야 할 뷰 이름 패턴이 IRVR에 의해 가로채지지 않도록, **IRVR의 viewNames 속성에 별도 패턴을 주는 방법**도 있습니다. 예를 들어 IRVR를 사용할 경우 `resolver.setViewNames("*.jsp")` 등으로 뷰 이름에 `.jsp`가 직접 포함된 경우만 처리하게 하고, Tiles 정의 이름(예: `.empty/...`)은 IRVR가 건너뛰도록 구성할 수 있습니다.

3. **내장 톰캣의 JSP 지원 설정**: Spring Boot 2.4 이상에서는 JSP 처리를 위해 **Default Servlet 등록**이 필요합니다. `application.properties`에 다음을 추가하세요:

   ```properties
   server.servlet.register-default-servlet=true
   ```

   이 설정은 내장 톰캣의 기본 서블릿(정적 리소스 제공 및 JSP용 Jasper 서블릿)을 활성화하여, Tiles가 포함하려는 JSP를 제대로 처리하도록 해줍니다. (tomcat-embed-jasper 의존성과 JSTL 라이브러리도 이미 포함되어 있어야 함은 기본 전제입니다.)

4. **Tiles 템플릿 구성 확인**: `.empty/*` Tiles 정의를 빈 레이아웃으로 사용하려는 경우, `WEB-INF/tiles/empty.jsp` 파일이 **실제로 존재하고 해당 경로로 접근 가능**해야 합니다. 이 파일은 머리글이나 메뉴 없이 `<tiles:insertAttribute name="body"/>`만 포함하는 **빈 레이아웃 JSP**여야 합니다. empty.jsp 또한 JSP이므로 위의 JSP 경로 문제 해결책에 따라 클래스패스에 포함되어야 하며, 필요하면 empty.jsp도 `META-INF/resources/WEB-INF/tiles/`로 옮기거나 WAR에 포함되도록 합니다. TilesConfigurer에서 정의 파일에 `.empty/*` 정의가 잘 등록돼 있는지도 재확인하세요 (Tiles 설정의 definitions XML에 `<definition name=".empty/*" ...>`이 포함되어 있어야 합니다).

위 조치들을 적용하면, **TilesViewResolver가 ****`.empty/blog/login`**** 뷰 이름을 올바르게 Tiles 정의로 해석**하고, empty 레이아웃 JSP에 `blog/login.jsp`를 body로 include하여 **레이아웃 없이 해당 JSP 내용만 출력**할 수 있습니다. 결과적으로 JAR 실행환경에서도 404 없이 정상적으로 JSP 뷰가 렌더링됩니다.

## 추가 설명: Spring Boot JAR vs WAR의 ServletContext 경로 동작

- **WAR 패키지 배포**: `src/main/webapp` 내의 리소스들은 WAR에 그대로 포함되어, **웹 애플리케이션 루트 경로**로 인식됩니다. `/WEB-INF/tiles/empty.jsp`나 `/blog/login.jsp` 모두 외부 톰캣이든 내장 톰캣이든 접근 가능한 경로로 존재하게 됩니다.
- **JAR 패키지 실행**: 실행가능 JAR는 특수한 ClassLoader를 통해 동작하므로, `webapp` 디렉토리가 클래스패스에 자동 포함되지 않습니다. 대신 Spring Boot는 `static/`, `templates/`, `META-INF/resources/` 등의 클래스패스 리소스를 웹루트로 매핑합니다. 따라서 JSP처럼 **ServletContext 경로가 필요한 자원**은 `META-INF/resources` 하위로 넣어주어야 `ServletContext.getResource("/...")`나 `RequestDispatcher.forward()`가 올바르게 동작합니다. 이 점을 이해하고 JSP 위치를 조정하는 것이 핵심입니다.

以上 조치를 통해 **Tiles 3 기반의 JSP 뷰**를 Spring Boot 환경에서 문제없이 서비스할 수 있습니다. 정리하면, **JSP 파일의 위치/패키징 문제 해결**, **중복 뷰리졸버 정리**, **내장 톰캣 JSP 서블릿 활성화**가 404 오류 해결과 빈 레이아웃 적용의 포인트입니다.

**참고 자료:** Spring Boot 공식 문서의 JSP 제한 사항, Stack Overflow의 Spring Boot JSP 실행 관련 답변 및 뷰리졸버 우선순위 설명 등을 참조했습니다.

