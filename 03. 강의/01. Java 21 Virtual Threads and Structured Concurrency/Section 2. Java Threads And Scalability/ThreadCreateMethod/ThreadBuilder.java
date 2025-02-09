package ThreadCreateMethod;

import java.lang.Thread.Builder.OfVirtual;

/**
 * Builder ������� Virtual Threaed�� �����ϴ� ���
 * Builder ������� Virtual Thread�� �����ϴ� ����� Thread-Safe���� ���ϴ�. (���ϱ�.. �׽�Ʈ�غ��� �� ��)
 * - �̴� Builder ��ü�� �����ϰ� �ֱ� �����̸�, Thread-Safe�� ����� Thread-Local�� ���ߵǾ�� �Ѵ�.
 * - ���� �ٸ� Builder�� ����ϰų�, VirutalThreadFactory�� ����ؾ���.
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
