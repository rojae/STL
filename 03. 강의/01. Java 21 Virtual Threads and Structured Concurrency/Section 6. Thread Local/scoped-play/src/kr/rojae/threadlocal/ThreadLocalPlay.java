package kr.rojae.threadlocal;

import kr.rojae.threadlocal.user.User;

/*
 * Main and Child Thread can set different User object in Threadlocal.
 * Demonstrates how Thread Locals work when multiple threads are in 
 * play.
 */
public class ThreadLocalPlay {
    
    public static final ThreadLocal<User> user = new ThreadLocal<User>();
    
    /**
     * [main] User => null
     * [main] Modified User => [kr.rojae.threadlocal.user.User@224aed64, main]
     * [bob-thread] User => null
     * [bob-thread] Modified User => [kr.rojae.threadlocal.user.User@467b0a76, bob]
     * [main] User => [kr.rojae.threadlocal.user.User@224aed64, main]
     */
    public static void main(String[] args) throws InterruptedException {
        print("User => " + user.get());

        // Main thread sets the user 
        user.set(new User("main"));
        
        print("Modified User => " + user.get());

        // Start a Child Thread for "bob"
        Thread thread = Thread.ofVirtual().start(() -> {
            Thread.currentThread().setName("bob-thread");
            
            print("User => " + user.get());
            user.set(new User("bob"));
            print("Modified User => " + user.get());
        });
        
        thread.join();
        
        print("User => " + user.get());
       
    }
    
    private static void print(String m) {
        System.out.printf("[%s] %s\n", Thread.currentThread().getName(), m);
    }

}
