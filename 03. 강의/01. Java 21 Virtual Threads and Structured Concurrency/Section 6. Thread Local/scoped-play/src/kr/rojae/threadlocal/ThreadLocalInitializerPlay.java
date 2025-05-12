package kr.rojae.threadlocal;

import kr.rojae.user.User;


/*
 * Thread locals can be created with an initializer 
 *  - Initial value would be determined by running the supplier
 *  - Supplier is called when calling get()
 *  - After remove() if you call get() => supplier will be called
 *  
 */
public class ThreadLocalInitializerPlay {
    
    public static final ThreadLocal<User> user = ThreadLocal.withInitial(() -> new User("anonymous"));
    
    /**
     * [main] User => [kr.rojae.threadlocal.user.User@224aed64, anonymous]
     * [main] Modified User => [kr.rojae.threadlocal.user.User@224aed64, main]
     * [bob-thread] User => [kr.rojae.threadlocal.user.User@224aed64, anonymous]
     * [bob-thread] Modified User => [kr.rojae.threadlocal.user.User@467b0a76, bob]
     * [main] User => [kr.rojae.threadlocal.user.User@224aed64, main]
     */
    public static void main(String[] args) throws InterruptedException {
        
        print("User => " + user.get());

        // Main thread sets the user 
        user.set(new User("main"));
        
        print("Modified User => " + user.get());

        // Start a Child Thread for "bob"
        Thread thread = Thread.ofPlatform().start(() -> {
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
