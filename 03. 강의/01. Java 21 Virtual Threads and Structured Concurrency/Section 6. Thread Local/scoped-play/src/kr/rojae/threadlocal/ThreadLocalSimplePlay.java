package kr.rojae.threadlocal;

import kr.rojae.user.User;
import kr.rojae.user.UserHandler;

/**
 * Simple example with a Single Thread. Demonstrates use of Thread Local
 * as an implicit parameter in whole method stack
 */
public class ThreadLocalSimplePlay {

    public static ThreadLocal<User> user = new ThreadLocal<User>();

    /**
     * [main] User => null
     * [main] User => [kr.rojae.threadlocal.user.User@224aed64, anonymous]
     * [main] User => [kr.rojae.threadlocal.user.User@224aed64, anonymous]
     */
    public static void main(String[] args) {
        
        print("User => " + user.get());

        // Main thread sets the user 
        user.set(new User("anonymous"));
        print("User => " + user.get());
        
        handleUser();
    }

    private static void handleUser() {
        UserHandler handler = new UserHandler();
        handler.handle();
    }

    public static void print(String m) {
        System.out.printf("[%s] %s\n", Thread.currentThread().getName(), m);
    }

}