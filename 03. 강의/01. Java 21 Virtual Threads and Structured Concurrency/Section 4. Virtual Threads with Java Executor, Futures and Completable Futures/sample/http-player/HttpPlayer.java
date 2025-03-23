import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.stream.IntStream;

public class HttpPlayer {
    public static void main(String args[]) {

        final int NUMBER_USERS = 1;

        ThreadFactory factory = Thread.ofVirtual().name("request-handler-", 0).factory();

        // scalability issue (time slow)
        //ThreadFactory factory = Thread.ofPlatform().name("request-handler-", 0).factory();

        // ExecutorService 생성
        try (ExecutorService executor = Executors.newThreadPerTaskExecutor(factory)) {
        // try (ExecutorService executor = Executors.newFixedThreadPool(500, factory)) {
            IntStream.range(0, NUMBER_USERS).forEach(j -> {
                executor.submit(new UserRequestHandler());
            });
        }
    }
}
