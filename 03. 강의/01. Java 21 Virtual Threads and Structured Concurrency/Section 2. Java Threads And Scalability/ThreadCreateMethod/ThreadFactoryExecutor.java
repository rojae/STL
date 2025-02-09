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
        // Executors.newThreadPerTaskExecutor(factory)��
        // Executors.newVirtualThreadPerTaskExecutor�� �������� ����
        // newVirtualThreadPerTaskExecutor : Virtual Thread pool�� �����Ͽ� ������
        // newThreadPerTaskExecutor : Virutal Thread Factory�� �����Ͽ�, Exector�� ������ �� ������ OS Thread�� ��ȯ�Ͽ� ������
        // �� �������� ���� ������ �ٸ���.
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
