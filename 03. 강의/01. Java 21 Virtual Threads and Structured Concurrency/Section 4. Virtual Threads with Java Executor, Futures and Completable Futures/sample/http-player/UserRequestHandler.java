// import java.util.Arrays;
// import java.util.concurrent.Callable;
// import java.util.concurrent.CompletableFuture;
// import java.util.concurrent.ExecutionException;
// import java.util.concurrent.ExecutorService;
// import java.util.concurrent.Executors;
// import java.util.concurrent.Future;
// import java.util.stream.Collectors;

// public class UserRequestHandler implements Callable<String>{

//     @Override
//     public String call() throws InterruptedException, ExecutionException {
//         try(ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()){
//             String output = CompletableFuture
//                 .supplyAsync(this::dbCall, service)
//                 .thenCombine(
//                     CompletableFuture.supplyAsync(this::restCall, service)
//                     , (result1, result2) -> {
//                     return "[" + result1 + "," + result2 + "]";
//                 })
//                 .thenApply(
//                     result -> {
//                         // both dbcall and restcall completed
//                         String externalResult = externalCall();
//                         return externalResult;
//                     }
//                 )
//                 .join();
            
//             System.out.println(output);
//             return output;
//         }
//     }

//     /**
//      * concurrentCallFunctional
//      * @return
//      * @throws InterruptedException
//      * @throws ExecutionException
//      */
//     public String concurrentCallFunctional() throws InterruptedException, ExecutionException {
//         try(ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()){
//             long start = System.currentTimeMillis();

//             String result = service.invokeAll(Arrays.asList(this::dbCall, this::restCall))
//                 .stream()
//                 .map(f -> {
//                     try{
//                         return (String) f.get();
//                     }
//                     catch(Exception e){
//                         return null;
//                     }
//                 })
//                 .collect(Collectors.joining(","));

//             long end = System.currentTimeMillis();
//             System.out.println("time = " + (end - start));

//             return "[" + result + "]";
//         }
//     }


//     /**
//      * concurrentCallWithFutures
//      * @return
//      * @throws InterruptedException
//      * @throws ExecutionException
//      */
//     public String concurrentCallWithFutures() throws InterruptedException, ExecutionException{
//         try(ExecutorService service = Executors.newVirtualThreadPerTaskExecutor()){
//             long start = System.currentTimeMillis();
            
//             Future<String> dbFuture = service.submit(this::dbCall);
//             Future<String> restFuture = service.submit(this::restCall);

//             String result = String.format("[%s, %s]]", dbFuture.get(), restFuture.get());

//             long end = System.currentTimeMillis();

//             System.out.println("time = " + (end - start));
//             System.out.println(result);
//             return result;
//         }
//     }

//     /**
//      * sequentialCode
//      * @return
//      * @throws InterruptedException
//      */
//     public String sequentialCode() throws InterruptedException {
//         long start = System.currentTimeMillis();

//         //////////////////////////////////////////
//         // sequential code
//         String result1 = dbCall();          // 2sec
//         String result2 = restCall();        // 5sec

//         // JVM Test - using jconsole
//         //Thread.sleep(Duration.ofMinutes(10));
//         String result = String.format("[%s, %s]]", result1, result2);
//         //////////////////////////////////////////

//         long end = System.currentTimeMillis();
//         System.out.println("time = " + (end - start));
//         System.out.println(result);

//         return result;
//     }

//     private String dbCall(){
//         try{
//             NetworkCaller caller = new NetworkCaller("data");
//             return caller.makeCall(2);
//         }
//         catch(Exception e){
//             e.printStackTrace();
//             return null;
//         }
//     }

//     private String restCall(){
//         try{
//             NetworkCaller caller = new NetworkCaller("rest");
//             return caller.makeCall(5);
//         }
//         catch(Exception e){
//             e.printStackTrace();
//             return null;
//         }
//     }

//     private String externalCall(){
//         try{
//             NetworkCaller caller = new NetworkCaller("external");
//             return caller.makeCall(5);
//         }
//         catch(Exception e){
//             e.printStackTrace();
//             return null;
//         }
//     }
    
// }
