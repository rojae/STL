package ThreadCreateMethod;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Create VirutalThread Using ExecutorService.
 */
public class ThreadExecutor {
    
    public static void main(String args[]){
        System.out.println("Start main");

        try(ExecutorService srv = Executors.newVirtualThreadPerTaskExecutor()){
            srv.submit(VirtualMethodsPlay::handleUserRequest);
            srv.submit(VirtualMethodsPlay::handleUserRequest);
        }

        System.out.println("End main");
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
