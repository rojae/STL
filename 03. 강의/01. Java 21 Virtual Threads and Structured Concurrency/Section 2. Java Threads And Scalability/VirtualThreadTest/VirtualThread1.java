package VirtualThreadTest;
public class VirtualThread1{
    
    private static final long MAX_THREAD = 10000;

    public static void handleUserRequest(){
        System.out.println("Start Sub Thread -- " + Thread.currentThread());
        try{
            Thread.sleep(1000);
        }
        catch(Exception e){
            // �޸� �Ҵ��� �Ұ����Ͽ�, ������ �߻���
            // Exception in thread "main" java.lang.OutOfMemoryError: unable to create native thread: possibly out of memory or process/resource limits reached
            e.printStackTrace();
        }
        System.out.println("End Sub Thread -- " + Thread.currentThread());
    }
    
    public static void main(String args[]){
        System.out.println("Start Main Thread --");

        for(int i = 0; i < MAX_THREAD; i++){
            //System.out.println("count : " + i);
            startThread();
        }
        

        System.out.println("End Main Thread --");
    }

    public static void startThread(){
        Thread.startVirtualThread(() -> handleUserRequest());
    }
}