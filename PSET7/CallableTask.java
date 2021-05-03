import java.util.concurrent.Callable;
public class CallableTask implements Callable<String> {
    public String call() throws Exception {
        Thread.sleep(10000);
        System.out.println("Executing call() !!!");
		/*if(1==1)
			throw new java.lang.Exception("Thrown from call()");*/
        return "success";
    }
}
