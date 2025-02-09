package ThreadCreateMethod;

/**
 * 정적으로 Virtual Thread를 생성하는 방법
 */
public class ThreadStatic {
    public static void main(String args[]) throws InterruptedException{
        // Start New Virtual Thread
        Thread vThread1 = Thread.startVirtualThread(()-> handleUserRequest());

        // With Naming
        Thread vThread2 = Thread.ofVirtual().name("vThread2").start(() -> handleUserRequest());

        // Make Sure Thread Termination
        vThread1.join();
        vThread2.join();
    }

    public static void handleUserRequest(){
        try {
            System.out.println("Start Thread - " + Thread.currentThread());
            Thread.sleep(1000);
            System.out.println("End Thread - " + Thread.currentThread());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
