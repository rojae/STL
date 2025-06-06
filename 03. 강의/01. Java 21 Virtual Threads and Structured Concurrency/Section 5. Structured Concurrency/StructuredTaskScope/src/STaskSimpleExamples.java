import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.StructuredTaskScope.Subtask.State;
import java.util.concurrent.TimeoutException;

public class STaskSimpleExamples {
    
    public static void main(String[] args) throws Exception {
        System.out.println("Main : Started");
        
        // Simulate interrupt to the Main Thread before Child threads complete

        // main thread is interrupted before the child threads complete
        interruptMain();

        // many of example below are similar to each other.
        exampleCompleteAllTasks4();
        
        System.out.println("Main : Completed");
    }
    
    private static void exampleShutdownOnFailure() throws InterruptedException, ExecutionException {
        
        try(var scope = new StructuredTaskScope.ShutdownOnFailure()) {
            
            var dataTask = new LongRunningTask("dataTask", 3,  "row1", false);
            var restTask = new LongRunningTask("restTask", 10, "json2", false);
            
            // Start running the tasks in parallel 
            Subtask<TaskResponse> dataSubTask = scope.fork(dataTask);
            Subtask<TaskResponse> restSubTask = scope.fork(restTask);
        
            // Wait till first Child Task fails. Send cancellation to
            // all other Child Tasks
            scope.join();
            scope.throwIfFailed();
            
            // Handle Success Child Task Results
            System.out.println(dataSubTask.get());
            System.out.println(restSubTask.get());
        }
        
    }

    
    /**
     * expedia-task is success and hotwire-task is success
     * @throws InterruptedException
     */
	private static void exampleCompleteAllTasks1() throws InterruptedException {
        
        try(var scope = new StructuredTaskScope<TaskResponse>()) {
            
            var expTask = new LongRunningTask("expedia-task", 3,  "100$", false);
            var hotTask = new LongRunningTask("hotwire-task", 10, "110$", false);
            
            // Start running the tasks in parallel 
            Subtask<TaskResponse> expSubTask = scope.fork(expTask);
            Subtask<TaskResponse> hotSubTask = scope.fork(hotTask);

			// Code to simulate random exception being thrown
			// This should still terminate the child threads
//            if (true) {
//            	Thread.sleep(Duration.ofSeconds(2));
//            	throw new RuntimeException("Some Exception");
//            }
            
            // Wait for all tasks to complete (success or not)
            scope.join();
            
            // Handle Child Task Results (might have succeeded or failed)
            State expState = expSubTask.state();
            if (expState == State.SUCCESS) 
                System.out.println(expSubTask.get());
            else if (expState == State.FAILED) 
                System.out.println(expSubTask.exception());
            
            State hotState = hotSubTask.state();
            if (hotState == State.SUCCESS) 
                System.out.println(hotSubTask.get());
            else if (hotState == State.FAILED)
                System.out.println(hotSubTask.exception());
        }
        
    }

    /**
     * expedia-task is falure and hotwire-task is success
     * exampleCompleteAllTasks2() is similar to exampleCompleteAllTasks1()
     * but uses a different way to handle the child task results.
     * @throws InterruptedException
     */
    private static void exampleCompleteAllTasks2() throws InterruptedException {
        
        try(var scope = new StructuredTaskScope<TaskResponse>()) {
            
            var expTask = new LongRunningTask("expedia-task", 3,  "100$", true);
            var hotTask = new LongRunningTask("hotwire-task", 10, "110$", false);
            
            // Start running the tasks in parallel 
            Subtask<TaskResponse> expSubTask = scope.fork(expTask);
            Subtask<TaskResponse> hotSubTask = scope.fork(hotTask);

            // Wait for all tasks to complete (success or not)
            scope.join();
            
            // Handle Child Task Results (might have succeeded or failed)
            State expState = expSubTask.state();
            if (expState == State.SUCCESS) 
                System.out.println(expSubTask.get());
            else if (expState == State.FAILED) 
                System.out.println(expSubTask.exception());
            
            State hotState = hotSubTask.state();
            if (hotState == State.SUCCESS) 
                System.out.println(hotSubTask.get());
            else if (hotState == State.FAILED)
                System.out.println(hotSubTask.exception());
        }
        
    }

    /**
     * expedia-task is falure and hotwire-task is falure
     * exampleCompleteAllTasks2() is similar to exampleCompleteAllTasks1()
     * but uses a different way to handle the child task results.
     * @throws InterruptedException
     */
    private static void exampleCompleteAllTasks3() throws InterruptedException {
        
        try(var scope = new StructuredTaskScope<TaskResponse>()) {
            
            var expTask = new LongRunningTask("expedia-task", 3,  "100$", true);
            var hotTask = new LongRunningTask("hotwire-task", 10, "110$", true);
            
            // Start running the tasks in parallel 
            Subtask<TaskResponse> expSubTask = scope.fork(expTask);
            Subtask<TaskResponse> hotSubTask = scope.fork(hotTask);
            
            // Wait for all tasks to complete (success or not)
            scope.join();
            
            // Handle Child Task Results (might have succeeded or failed)
            State expState = expSubTask.state();
            if (expState == State.SUCCESS) 
                System.out.println(expSubTask.get());
            else if (expState == State.FAILED) 
                System.out.println(expSubTask.exception());
            
            State hotState = hotSubTask.state();
            if (hotState == State.SUCCESS) 
                System.out.println(hotSubTask.get());
            else if (hotState == State.FAILED)
                System.out.println(hotSubTask.exception());
        }
        
    }

    /**
     * interrupted exception is thrown in the main thread (scope.joinUntil's deadline is 2 seconds)
     * @throws InterruptedException
     */
    private static void exampleCompleteAllTasks4() throws InterruptedException, TimeoutException {
        
        try(var scope = new StructuredTaskScope<TaskResponse>()) {
            
            var expTask = new LongRunningTask("expedia-task", 3,  "100$", true);
            var hotTask = new LongRunningTask("hotwire-task", 10, "110$", true);
            
            // Start running the tasks in parallel 
            Subtask<TaskResponse> expSubTask = scope.fork(expTask);
            Subtask<TaskResponse> hotSubTask = scope.fork(hotTask);
            
            // Wait for all tasks to complete (success or not)
            scope.joinUntil(Instant.now().plus(Duration.ofSeconds(2)));
            
            // Handle Child Task Results (might have succeeded or failed)
            State expState = expSubTask.state();
            if (expState == State.SUCCESS) 
                System.out.println(expSubTask.get());
            else if (expState == State.FAILED) 
                System.out.println(expSubTask.exception());
            
            State hotState = hotSubTask.state();
            if (hotState == State.SUCCESS) 
                System.out.println(hotSubTask.get());
            else if (hotState == State.FAILED)
                System.out.println(hotSubTask.exception());
        }
    }

    /**
     * interrupted exception is thrown in the main thread
     * @throws InterruptedException
     */
    private static void interruptMain() {
        Thread mainThread = Thread.currentThread();
        Thread.ofPlatform().start(() -> {
            
            try {
                Thread.sleep(Duration.ofSeconds(2));
                mainThread.interrupt();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }
    
    

    
    

}
