package VirtualThreadTest;

import java.util.ArrayList;

public class VirtualThread2{
    
    private static final long MAX_THREAD = 2000000;

    public static void handleUserRequest(){
        ///System.out.println("Start Sub Thread -- " + Thread.currentThread());
        try{
            Thread.sleep(2000);
        }
        catch(Exception e){
            // 메모리 할당이 불가능하여, 오류가 발생함
            // Exception in thread "main" java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached
            e.printStackTrace();
        }
        //System.out.println("End Sub Thread -- " + Thread.currentThread());
    }
    
    public static void main(String args[]) throws InterruptedException{
        var threads = new ArrayList<Thread>();

        System.out.println("Start Main Thread --" + Thread.currentThread());
        
        for(int i = 0; i < MAX_THREAD; i++){
            //System.out.println("count : " + i);
            threads.add(startThread());
        }
        
        // join on the thread (모든 쓰레드가 종료될때까지 대기함)
        for(Thread thread : threads){
            thread.join();
        }

        System.out.println("End Main Thread --" + Thread.currentThread());
    }

    public static Thread startThread(){
        return Thread.startVirtualThread(() -> handleUserRequest());
    }
}