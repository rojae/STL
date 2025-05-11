package kr.rojae.threadlocal;

import kr.rojae.threadlocal.user.User;

/*
 * - Child thread will see the thread local values of the Parent. 
 * - Thread local map is automatically copied when the child thread is created
 * - No deep copy unless childValue() method is used.
 */
public class InheritableThreadLocalPlay {
    
    /**
     * InheritableThreadLocal can be used to pass values to child threads
     * - Child thread will see the thread local values of the Parent.
     */
    public static final InheritableThreadLocal<User> user = new InheritableThreadLocal<>()
    {
       
       /**
        * Initial value is set to "anonymous"
        * - This is a shallow copy (using the new keyword)
        */
       @Override
       protected User initialValue() { 
          return new User("anonymous"); 
       }

       /**
        * When the child thread is created, the parent value is copied to the child thread
        * - This is a shallow copy (using the new keyword)
        * @param parentValue
        * @return
        */
       @Override
       protected User childValue(User parentValue) { 
          return new User(parentValue.getId()); 
       }
   };
    
    /**
     * [main] User => null
     * [main] Modified User => [kr.rojae.threadlocal.user.User@c39f790, main]
     * [bob-thread] User => [kr.rojae.threadlocal.user.User@c39f790, main]
     * [bob-thread] Modified User => [kr.rojae.threadlocal.user.User@c39f790, bobby]
     * [main] User => [kr.rojae.threadlocal.user.User@c39f790, bobby]
     * @param args
     * @throws InterruptedException
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
            
            user.get().setId("bobby");
            print("Modified User => " + user.get());
            
        });
        
        thread.join();
        print("User => " + user.get());
       
    }
    
    private static void print(String m) {
        System.out.printf("[%s] %s\n", Thread.currentThread().getName(), m);
    }

}
