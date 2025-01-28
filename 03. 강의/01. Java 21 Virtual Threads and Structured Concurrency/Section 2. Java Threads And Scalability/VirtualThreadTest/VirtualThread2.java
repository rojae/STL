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
            // �޸� �Ҵ��� �Ұ����Ͽ�, ������ �߻���
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
        
        // join on the thread (��� �����尡 ����ɶ����� �����)
        for(Thread thread : threads){
            thread.join();
        }

        System.out.println("End Main Thread --" + Thread.currentThread());
    }

    public static Thread startThread(){
        return Thread.startVirtualThread(() -> handleUserRequest());
    }
}