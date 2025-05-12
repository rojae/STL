package kr.rojae.scopedvalue;

import kr.rojae.user.ScopedUserHandler;
import kr.rojae.user.User;

/*
 * Advantages
 *   - Scoped Value only available for use within the dynamic scope of the method 
 *      - during the bounded period of execution of a method
 *      - bound during start of scope and unbounded during end of scope (even exception)
 *   - Rebinding allowed but cannot modify Scoped Value
 *   - No cleanup required. automatically handled
 */
public class ScopedValuePlay {
    
    public static final ScopedValue<User> user = ScopedValue.newInstance();
    
    /**
     * ScopedValue 사용법
     * [main] user is Bound => false
     * [main] handle - user is Bound => true
     * [main] handle - User => [kr.rojae.user.User@3ab39c39, bob]
     * [main] Result => true
     * [main] user is Bound => false
     */
    public static void main(String[] args) throws Exception {

        print("user is Bound => " + user.isBound());

        User bob = new User("bob");
        
        // 올바른 사용법 (Java 21+ 공식)
        boolean result = ScopedValue
            .where(user, bob)             // 바인딩
            .call(ScopedValuePlay::handleUser);  // 실행
        
        // boolean result 
        //     = ScopedValue.callWhere(user, bob, ScopedValuePlay::handleUser);
                        
        // boolean result = handleUser();
        print("Result => " + result);
        print("user is Bound => " + user.isBound());
    }
    
    private static boolean handleUser() {
        ScopedUserHandler handler = new ScopedUserHandler();
        return handler.handle();
    }

    public static void print(String m) {
        System.out.printf("[%s] %s\n", Thread.currentThread().getName(), m);
    }
    
}
