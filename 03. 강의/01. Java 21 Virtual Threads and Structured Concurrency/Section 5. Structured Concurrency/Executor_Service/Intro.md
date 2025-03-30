# 동시성의 구조화 Intro

## 기존 구조의 문제
절차적으로 코드를 작성하는 것이, 가독성과 Thread End 시점을 파악하기가 수월하기 떄문에 Virtual Thread가 WebFlux보다 여러가지 장점이 있다고 생각이든다.

그런데 아래소스를 보자
Section 4에서 작성한 소스인데, 아래 소스에는 몇 가지 문제가 있다.

```java
public String concurrentCallWithFutures() throws InterruptedException, ExecutionException
{
    try(ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()){
        long start = System.currentTimeMillis();
    
        Future<String> dbFuture = service.submit(this::dbCall);
        Future<String> restFuture = service.submit(this::restCall);

        String result = String.format("[%s, %s]]", dbFuture.get(), restFuture.get());

        long end = System.currentTimeMillis();

        System.out.println("time = " + (end - start));
        System.out.println(result);
        return result;
    }
}

private String dbCall1(){
    try{
        NetworkCaller caller = new NetworkCaller("data1");
        return caller.makeCall(2);
    }
    catch(Exception e){
        e.printStackTrace();
        return null;
    }
}
```


## 어떤 문제일까?

아래 코드에서 Exception이 발생하게 되면 null을 반환한다.

이 경우에는 동시에 이루어지는 Thread인 "service.submit(this::restCall)"에
취소 Signal을 전송할 수가 없고, 상위 Thread 함수인 "concurrentCallWithFutures"에 Exception을 전달을 하기가 매우 모호하다는 점이다.
```java
private String dbCall1(){
    try{
        ...
    }
    catch(Exception e){
        e.printStackTrace();
        return null;
    }
}
```

## 그래서 어떻게 해결할건데 (JDK Classes)
- Structured Task Scope
- SubTask


