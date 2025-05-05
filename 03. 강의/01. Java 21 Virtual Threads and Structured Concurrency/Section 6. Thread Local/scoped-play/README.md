## Thread Local
- virtual thread는 platform thread와는 다른 경량 스레드지만, ThreadLocal은 platform/virtual에 상관없이 각 스레드의 독립 저장소를 유지를 한다.

### 정리된 내용
| 항목                      | 초기값 설정       | 스레드 종류       | child thread에서 `user.get()` 초기 상태     |
|-------------------------|------------------|------------------|------------------------------------------|
| `ThreadLocalPlay`        | 없음            | Virtual Thread   | `null`                                   |
| `ThreadLocalSimplePlay`  | 없음            | Main Thread only | N/A                                      |
| `ThreadLocalInitializerPlay` | `withInitial` 사용 | Platform Thread | `"anonymous"` (자동 생성)                |

### 참고
- main에서 set한 값이 왜 다른 스레드에 안 보이는 이유
    - 그 이유는 ThreadLocal은 "현재 스레드 전용" 저장소이기 때문에,
    - main에서 set한 값은 다른 스레드 (virtual이든 platform이든)에서는 보이지 않습니다.