import java.util.concurrent.Callable;

public class UserRequestHandler implements Callable<String>{

    @Override
    public String call() {
        // sequential code
        String result1 = dbCall();
        String result2 = restCall();
        
        String result = String.format("[%s, %s]]", result1, result2);

        System.out.println(result);

        return result;
    }

    private String dbCall(){
        try{
            NetworkCaller caller = new NetworkCaller("data");
            return caller.makeCall(2);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String restCall(){
        try{
            NetworkCaller caller = new NetworkCaller("rest");
            return caller.makeCall(5);
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
}
