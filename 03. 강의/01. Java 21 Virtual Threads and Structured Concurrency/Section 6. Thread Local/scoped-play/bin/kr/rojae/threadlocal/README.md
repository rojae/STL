## Thread Local
- virtual thread는 platform thread와는 다른 경량 스레드지만, ThreadLocal은 platform/virtual에 상관없이 각 스레드의 독립 저장소를 유지를 한다.

### 정리된 내용
| 항목                      | 초기값 설정       | 스레드 종류       | child thread에서 `user.get()` 초기 상태     |
|-------------------------|------------------|------------------|------------------------------------------|
| `ThreadLocalPlay`        | 없음            | Virtual Thread   | `null`                                   |
| `ThreadLocalSimplePlay`  | 없음            | Main Thread only | N/A                                      |
| `ThreadLocalInitializerPlay` | `withInitial` 사용 | Platform Thread | `"anonymous"` (자동 생성)                |
| `ThreadLocalInitializerPlay` | `initialValue` 사용 | Platform Thread | `"anonymous"` (자동 생성)                |

### ThreadLocal의 단점

`ThreadLocal`은 **스레드마다 독립적인 값을 저장할 수 있는 강력한 도구**지만, 무분별한 사용이나 설계 미숙으로 인해 여러 가지 **단점과 위험 요소**를 동반할 수 있습니다.

---

#### ❗️1. 메모리 누수 (Memory Leak) 위험
- `ThreadLocal`은 값을 **스레드의 생명주기와 함께 유지**합니다.
- 특히 **서블릿 컨테이너나 스레드 풀 환경(Tomcat 등)**에서는 스레드가 재사용되기 때문에,
  - ThreadLocal에 저장된 값이 회수되지 않으면 **GC로 수거되지 못해 누수**가 발생할 수 있습니다.
- `remove()`를 명시적으로 호출하지 않으면 **사용하지 않는 값이 계속 남아 있음**

> 💡 **해결책**: 작업 종료 후 `ThreadLocal.remove()`를 반드시 호출해야 합니다.

---

#### ❗️2. 가독성 · 디버깅 어려움
- 변수의 흐름이 코드에 명시되지 않기 때문에 **값이 언제, 어디서 설정됐는지 알기 어려움**
- 여러 계층(예: `Controller → Service → DAO`)을 거쳐 전달되면 **숨겨진 전역 변수처럼 작동**
- 디버깅 시 ThreadLocal 값의 상태를 추적하기 어렵고, **문제 원인 파악이 지연될 수 있음**

---

#### ❗️3. 테스트 어려움
- 테스트 환경에서는 스레드 수명이 짧고 단절되어, ThreadLocal 값이 **예상대로 유지되지 않음**
- 단위 테스트 시 스레드 간 값이 공유되지 않아 **`NullPointerException` 발생 가능**
- ThreadLocal을 사용하는 로직은 **Mocking이나 격리 테스트가 까다로움**

---

#### ❗️4. 스레드 풀과의 부조화
- `Executors`, `Spring ThreadPoolTaskExecutor`, `Tomcat` 등은 **스레드를 재사용**
- 이전 요청에서 설정된 `ThreadLocal` 값이 **다음 요청에도 남아 있는 상황 발생**
  - 특히 **인증 정보, 사용자 정보**가 남아 있으면 **보안상 큰 문제** 발생 가능

---

#### ❗️5. 불필요한 의존성 증가
- `Service`나 `Util` 클래스가 `ThreadLocal`에 의존하면, 해당 클래스는 **암묵적인 컨텍스트에 종속**
- 의존성 주입(DI) 방식보다 설계가 불투명해지며, **테스트 및 유지보수 난이도 증가**
- 결과적으로 **재사용성과 유연성이 저하**

---

> ✅ ThreadLocal은 **꼭 필요한 상황에서만 제한적으로 사용**하고,  
> 사용 후에는 반드시 `remove()`를 호출하는 등 **명확한 라이프사이클 관리**가 중요합니다.


### 참고
- main에서 set한 값이 왜 다른 스레드에 안 보이는 이유
    - 그 이유는 ThreadLocal은 "현재 스레드 전용" 저장소이기 때문에,
    - main에서 set한 값은 다른 스레드 (virtual이든 platform이든)에서는 보이지 않습니다.
    - 예외적으로 InheritableThreadLocal을 사용하게 된다면, 값을 복사하여 새로운 child value 객체를 생성한다.
