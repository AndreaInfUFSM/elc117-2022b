import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpLanguageServiceExample {

  public static void main(String[] args) throws Exception {
    String baseurl = "https://script.google.com/macros/s/AKfycbxmBglz-GjF-9-RjRpAtuBS9MYPrzfy3JF63BBsXTi1SecoWASn28KMB_GcpF8bV0fu/exec?";
    HttpLanguageService http = new HttpLanguageService(baseurl);

    LanguageRecommendation lang = new LanguageRecommendation("Elixir", 2012, "andrea");
    http.postLanguageRecommendation(lang);

    String response = http.getLanguageRecommendations();
    System.out.println(response);
  }
}

class HttpLanguageService {

  private String baseurl;

  HttpLanguageService(String baseurl) {
    this.baseurl = baseurl;
  }

  public int postLanguageRecommendation(LanguageRecommendation lang) throws Exception {

    String urlstr = baseurl + 
                    "action=postLanguageRecommendation" +
                    "&name=" + lang.getName() +
                    "&firstAppeared=" + lang.getFirstAppeared() +
                    "&recommendedBy=" + lang.getRecommendedBy();
  
    URL url = new URL(urlstr);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET"); // our web service only accepts GET requests
    int responseCode = con.getResponseCode();
    return responseCode;  
  }

  public String getLanguageRecommendations() throws Exception {

    String urlstr = baseurl + "action=getLanguageRecommendations";

    URL url = new URL(urlstr);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");

    // Create buffers to read data from the server
    // (ignore ResponseCode for the sake of simplicity)
    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
    StringBuffer response = new StringBuffer();
    
    String inputLine;
    // Read the response line by line
    while ((inputLine = in.readLine()) != null) {
      response.append(inputLine);
    }
    in.close();

    return response.toString();
  }  
}





