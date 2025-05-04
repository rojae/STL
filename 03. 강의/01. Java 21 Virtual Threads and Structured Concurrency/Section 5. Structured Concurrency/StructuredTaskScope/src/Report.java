import java.util.List;
import java.util.Map;
import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.StructuredTaskScope.Subtask;
import java.util.concurrent.StructuredTaskScope.Subtask.State;
import java.util.stream.Collectors;

public class Report {
    
    public static void main(String args[]) throws Exception {
        System.out.println("Main : Started");
        
        // Create the tasks        
        var dbTask   = new LongRunningTask("dataTask",  3,  "row1", true);        
        var restTask = new LongRunningTask("restTask", 10, "json2", false);        
        var extTask  = new LongRunningTask("extTask",   5, "json2", false);
        
        // execute the sub tasks in parallel. 
        // Throw exception if interrupted or any task fails
        Map<String, TaskResponse> result = STaskScopeOnFailureExecutor.execute(List.of(dbTask, extTask, restTask));
        
        // print results of all tasks        
        result.forEach((k,v) -> {            
            System.out.printf("%s : %s\n", k, v);        
        });
        
        // extract output for an individual task
        TaskResponse extResponse = result.get("extTask");
        
        System.out.println("Main : Completed");
    }

    /*
     * Run the collection of tasks in parallel, terminate them by the end of the
     * method and return result of all tasks 
     */
    public class STaskScopeOnFailureExecutor {
        public static Map<String,TaskResponse> execute(List<LongRunningTask> tasks) throws Exception {
            try(var scope = new StructuredTaskScope.ShutdownOnFailure()) {
                // start running the tasks in parallel 
                List<Subtask<TaskResponse>> subTasks = tasks.stream().map(scope::fork).toList();
                
                // wait for first task to complete
                scope.join();
                
                // get results of all tasks
                Map<String,TaskResponse> output = subTasks.stream()
                    .filter(sub -> sub.state() == State.SUCCESS)
                    .map(Subtask::get)
                    .collect(Collectors.toMap(TaskResponse::name, r -> r));
                
                return output;
            }
        }
    }

}
