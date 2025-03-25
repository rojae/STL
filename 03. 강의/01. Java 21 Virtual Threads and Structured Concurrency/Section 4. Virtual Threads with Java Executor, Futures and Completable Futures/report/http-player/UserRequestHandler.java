import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserRequestHandler implements Callable<String>{
    
    @Override
    public String call() throws InterruptedException, ExecutionException {
        try(ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()){
            String output = CompletableFuture
                .supplyAsync(this::dbCall1, service)
                .thenCombine(
                    CompletableFuture.supplyAsync(this::dbCall2, service)
                    , (result1, result2) -> {
                        return "dbCall1=" + result1 + "\n"+ "dbCall2=" + result2;
                })
                .thenApply(
                    result -> {
                        String restCall1 = restCall1();
                        return result + "\n" + "restCall1="+restCall1;
                    }
                )
                .thenApply(
                    result -> {
                        String restCall2 = restCall2();
                        return result + "\n" + "restCall2="+restCall2;
                    }
                )
                .join();
            
            System.out.println(output);
            return output;
        }
    }


    private String dbCall1(){
        try{
            NetworkCaller caller = new NetworkCaller("data1");
            return caller.makeCall(2);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String dbCall2(){
        try{
            NetworkCaller caller = new NetworkCaller("data2");
            return caller.makeCall(3);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String restCall1(){
        try{
            NetworkCaller caller = new NetworkCaller("rest1");
            return caller.makeCall(4);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String restCall2(){
        try{
            NetworkCaller caller = new NetworkCaller("rest2");
            return caller.makeCall(5);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}
