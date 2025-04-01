import java.time.Duration;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Thread's Task
 */
public class LongRunningTask implements Callable<TaskResponse> {
    
    private final String name;
    private final int time;
    private final String output;
    private final boolean fail;

    public LongRunningTask(String name, int time, String output, boolean fail){
        this.name = name;
        this.time = time;
        this.output = output;
        this.fail = fail;
    }

    @Override
    public TaskResponse call() throws Exception{
        long start = System.currentTimeMillis();

        print("Start");
        
        int numSecs = 0;
        while (numSecs++ < time) {
            
            if (Thread.interrupted()) {
                throwInterruptedException();
            }
            
            print("Working .. " + numSecs);
            
             // process data (Code not shown) which uses CPU for 0.2 secs 
            
            try {
                Thread.sleep(Duration.ofSeconds(1));
            }
            catch (InterruptedException intExp) {
                throwInterruptedException();
            }

            // process data (Code not shown) which uses CPU for 0.2 secs 
        }

        if(fail){
            throwExceptionOnFailure();
        }

        print("Completed");
        long end = System.currentTimeMillis();

        return new TaskResponse(this.name, this.output, (end - start));
    }

    private void throwInterruptedException() throws InterruptedException {
        print("Interrupted");
        throw new InterruptedException(name +  " : Interrupted");
    }

    private void throwExceptionOnFailure() throws InterruptedException {
        print("Failure");
        throw new InterruptedException(name +  " : Failure");
    }

    private void print(String message){
        System.out.printf("> %s : %s\n", this.name, message);
    }

    public static void main(String args[]) throws InterruptedException{
        System.out.println("> Main : Started");

        LongRunningTask task = new LongRunningTask("LongTask1", 10, "json-response1", false);

        try(var srv = Executors.newFixedThreadPool(2)){
            Future<TaskResponse> taskFeature = srv.submit(task);
            Thread.sleep(Duration.ofSeconds(5));
            taskFeature.cancel(true);
        }

        System.out.println("> Main : Completed");
    }

}

