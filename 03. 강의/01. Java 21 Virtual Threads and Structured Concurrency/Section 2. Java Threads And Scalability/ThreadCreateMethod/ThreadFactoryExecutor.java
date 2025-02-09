package ThreadCreateMethod;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class ThreadFactoryExecutor {
    
    public static void main(String args[]){
        System.out.println("Start Main");

        ThreadFactory factory = Thread.ofVirtual().name("executorFactory", 0).factory();
    
        ////////////////////////////////////////////////////////////////////////////////        
        // Note
        ////////////////////////////////////////////////////////////////////////////////        
        // Executors.newThreadPerTaskExecutor(factory)은
        // Executors.newVirtualThreadPerTaskExecutor와 동일하지 않음
        // newVirtualThreadPerTaskExecutor : Virtual Thread pool을 생성하여 관리함
        // newThreadPerTaskExecutor : Virutal Thread Factory를 전달하여, Exector를 생성할 수 있으나 OS Thread로 변환하여 수행함
        // 즉 내부적인 동작 수행이 다르다.
        try(ExecutorService srv = Executors.newThreadPerTaskExecutor(factory)){
            srv.submit(VirtualMethodsPlay::handleUserRequest);
            srv.submit(VirtualMethodsPlay::handleUserRequest);
        }

        System.out.println("End Main");
    }

    class VirtualMethodsPlay {
        public static void handleUserRequest() {
            try {
                System.out.println("Start Thread - " + Thread.currentThread());
                Thread.sleep(1000);
                System.out.println("End Thread - " + Thread.currentThread());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
