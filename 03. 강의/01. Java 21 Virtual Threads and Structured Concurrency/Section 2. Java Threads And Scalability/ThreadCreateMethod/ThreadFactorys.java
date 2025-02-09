package ThreadCreateMethod;

import java.util.concurrent.ThreadFactory;

public class ThreadFactorys {
    
    public static void main(String args[]) throws InterruptedException{
        // Create Thread Factory
        ThreadFactory factory = Thread.ofVirtual().name("factoryThread", 0).factory();
        
        Thread vThread1 = factory.newThread(VirtualMethodsPlay::handleUserRequest);
        vThread1.start();

        Thread vThread2 = factory.newThread(VirtualMethodsPlay::handleUserRequest);
        vThread2.start();

        vThread1.join();
        vThread2.join();
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
