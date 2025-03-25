import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;


public class HttpPlayer {
    
    public static void main(String args[]){
        final int NUMBER_USERS = 1;
        ThreadFactory factory = Thread.ofVirtual().name("request-hanlder-", 0).factory();

        // ExecutorService
        try (ExecutorService executor = Executors.newThreadPerTaskExecutor(factory)) {
            IntStream.range(0, NUMBER_USERS).forEach(j -> {
                executor.submit(new UserRequestHandler());
            });
        }
    }
}
