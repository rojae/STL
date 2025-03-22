import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

public class NetworkCaller {

    private final String callName;
    
    public NetworkCaller(String callName){
        this.callName = callName;
    }

    public String makeCall(int seconds) throws URISyntaxException, IOException{
        System.out.println(callName + " : BEG call : " + Thread.currentThread());
		
		try {
			URI uri = new URI("http://httpbin.org/delay/" + seconds);
			try (InputStream stream = uri.toURL().openStream()) {
				return new String(stream.readAllBytes());
			}
		}
		finally {
			System.out.println(callName + " : END call : " + Thread.currentThread());
		}
    }
}
