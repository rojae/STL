package ThreadCreateMethod;

import java.lang.Thread.Builder.OfVirtual;

/**
 * Builder 방식으로 Virtual Threaed를 생성하는 방법
 * Builder 방식으로 Virtual Thread를 생성하는 방법은 Thread-Safe하지 못하다. (왜일까.. 테스트해봐야 할 듯)
 * - 이는 Builder 객체를 공유하고 있기 때문이며, Thread-Safe한 방식인 Thread-Local로 개발되어야 한다.
 * - 각기 다른 Builder를 사용하거나, VirutalThreadFactory를 사용해야함.
 */
public class ThreadBuilder {
    
    public static void main(String args[]) throws InterruptedException{
        // Create Thread Using Builder
        OfVirtual vBuilder = Thread.ofVirtual().name("userThreaed", 0);

        // Start two Static Thread
        Thread vThread1 = vBuilder.start(VirtualMethodsPlay::handleUserRequest);
        Thread vThread2 = vBuilder.start(VirtualMethodsPlay::handleUserRequest);
        Thread vThread3 = vBuilder.start(VirtualMethodsPlay::handleUserRequest);
        Thread vThread4 = vBuilder.start(VirtualMethodsPlay::handleUserRequest);

        // Termination Signal
        vThread1.join();
        vThread2.join();
        vThread3.join();
        vThread4.join();
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
