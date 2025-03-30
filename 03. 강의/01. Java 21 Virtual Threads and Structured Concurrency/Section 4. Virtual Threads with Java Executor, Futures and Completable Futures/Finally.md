# Scaliability Solution

## Scaliability Solution Format
> { Optimized Scalable Application } + { Vertical Scaling } + { Horizontal Scaling }
- Optimized Scalable Application : 어플리케이션의 확장성을 최적화하기 위한 작업
- Vertical Scaling : (수직 확장) 좀 더 파워풀한 성능을 가진 CPU, Memory
- Horizontal Scaling : (수평 확장) 어플리케이션의 인스턴스 확장을 통해서, 좀 더 많은 사용자를 받음

이때 Java Virtual Thread는 Optimized Scalable Application을 향상시키기 위한 기술이다.

이것은 즉 Vertical Scaling과 Horizontal Scaling이 기존에 비해서 부족하더라도 Virtual Thread를 통해서 좀 더 많은 트래픽을 받을 수 있다는 말이다.

----
## 예시
- AS-IS : 6 Instance => 3,000 user
- TO-BE : 2 Instance => 3,000 user

> 플랫폼 쓰레드가 감당할 수 있는 성능이 비약적으로 상향되었기 때문이다. 
> (jconsole로 확인이 가능)
