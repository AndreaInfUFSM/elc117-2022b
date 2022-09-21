import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// Simple HTTP request example using JDK APIs only. 
// This code can be shortened using specialized APIs (Apache HttpClient, etc.)
public class HttpURLConnectionExample {

  // All code inside the main method ?!
  // Ok for a short example, but this is not 
  // a good practice of object orientation
  public static void main(String[] args) throws Exception {
    String urlstr = "https://api.github.com/users/andreainfufsm";
        
    URL url = new URL(urlstr);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");

    System.out.println("\nSending GET request to URL: " + urlstr);
    int responseCode = con.getResponseCode();
    System.out.println("Response Code: " + responseCode);

    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    StringBuffer response = new StringBuffer();
    String inputLine;
		    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    System.out.println(response.toString());
  }
}

