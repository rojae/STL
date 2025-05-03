import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.StructuredTaskScope.Subtask.State;
import java.util.concurrent.TimeoutException;

public class STaskSimpleShutdownOnSuccessExample {

    public static void main(String[] args) throws Exception {
        System.out.println("Main : Started");
        
        // Simulate interrupt to the Main Thread before Child threads complete
        // many of example below are similar to each other.
        exampleShutdownOnSuccess();
        
        System.out.println("Main : Completed");
    }

    /**
     * expedia-task is success and hotwire-task is success
     * ShutdownOnSuccess is waiting for all other tasks to complete
     * (If one task success or failure then wait for all other tasks to completed result)
     * @throws Exception 
     */
    private static void exampleShutdownOnSuccess() throws Exception {
        try(var scope = new StructuredTaskScope.ShutdownOnSuccess<TaskResponse>()) {
            
            var wthr1Task = new LongRunningTask("Weather-1", 3,  "32", true);
            var wthr2Task = new LongRunningTask("Weather-2", 10, "30", true);
            
            // Start running the tasks in parallel 
            Subtask<TaskResponse> subTask1 = scope.fork(wthr1Task);
            Subtask<TaskResponse> subTask2 = scope.fork(wthr2Task);
        
            // Wait till first Child Task Succeeds. Send Cancellation
            // to all other Child Tasks
            scope.join();
            
            // Handle Successful Child Task
            TaskResponse result = scope.result(t -> {
                // can handle the exception customly
                return new Exception(t);
            });
            
            System.out.println(result);
        }
    }

}