import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.StructuredTaskScope.Subtask.State;
import java.util.concurrent.TimeoutException;

public class STaskSimpleShutdownOnFaliureExample {
    
    public static void main(String[] args) throws Exception {
        System.out.println("Main : Started");
        
        // Simulate interrupt to the Main Thread before Child threads complete
        // many of example below are similar to each other.
        exampleCompleteAllTasks();
        
        System.out.println("Main : Completed");
    }

    /**
     * expedia-task is failure and hotwire-task is success
     * @throws Exception 
     */
    private static void exampleCompleteAllTasks() throws Exception {
        try(var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            
            var expTask = new LongRunningTask("expedia-task", 3,  "100$", true);
            var hotTask = new LongRunningTask("hotwire-task", 10, "110$", false);
            
            // Start running the tasks in parallel 
            Subtask<TaskResponse> expSubTask = scope.fork(expTask);
            Subtask<TaskResponse> hotSubTask = scope.fork(hotTask);
    
            // Wait till first Child Task fails. Send cancellation to
            // all other Child Tasks
            scope.join();
            scope.throwIfFailed(t -> {
                // can handle the exception customly
                return new Exception(t);
            });
            
            // Handle Success Child Task Results
            System.out.println(expSubTask.get());
            System.out.println(hotSubTask.get());
        }
    }
    
}
