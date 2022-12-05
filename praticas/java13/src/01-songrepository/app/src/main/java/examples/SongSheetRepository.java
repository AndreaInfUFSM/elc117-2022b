package examples;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * CRUD operations for Song objects.
 * Uses Google Sheets and apispreadsheets.com.
 * For simplicity purposes, error handling is based on checking and printing response codes
 * (no exception handling, no logging).
 */

public class SongSheetRepository {

  // From: https://www.apispreadsheets.com
  // API URL: https://api.apispreadsheets.com/data/fzMynCiZEOH4NYK5/
  private static final String BASE_URL = "https://api.apispreadsheets.com/data";
  private static final String FILE_ID = "fzMynCiZEOH4NYK5";

  /**
   * Creates a given song in the repository.
   * @param song
   * @throws Exception
   */
  public void create(Song song) throws Exception {

    String urlstr = BASE_URL + "/" + FILE_ID + "/"; // see apispreadsheets.com

    int responseCode = this.sendPostRequest(urlstr, new Gson().toJson(song));
    if (responseCode != HttpURLConnection.HTTP_CREATED) {
      System.out.println("Unexpected POST (create) response code: " + responseCode);
    }
  }

  /**
   * Returns all Song instances from the repository.
   * @return A list of Song instances
   * @throws Exception
   */
  public List<Song> readAll() throws Exception {

    String urlstr = BASE_URL + "/" + FILE_ID + "/"; // see apispreadsheets.com
    JsonObject response = this.sendGetRequest(urlstr);
    JsonArray jsonarr = response.getAsJsonArray("data"); // see apispreadsheets.com
    Song[] objarr = new GsonBuilder().create().fromJson(jsonarr, Song[].class);
    return Arrays.asList(objarr);
  }

  /**
   * Updates a Song instance identified by a given key-value pair.
   * @param key The name of a Song attribute
   * @param value The existing value stored in the Song attribute
   * @param song The Song instance to be saved
   * @throws Exception
   */
  public void update(String key, String value, Song song) throws Exception {

    // See apispreadsheets.com
    String urlstr = BASE_URL + "/" + FILE_ID + "/";
    String query = "query\":\"select * from " + FILE_ID + " where " + key + "='" + value + "'";
    String json = "{\"data\":" + (new Gson().toJson(song)) + ", \"" + query + "\"" + "}";

    int responseCode = this.sendPostRequest(urlstr, json);
    if (responseCode != HttpURLConnection.HTTP_CREATED) {
      System.out.println("Unexpected POST (update) response code: " + responseCode);
    }
  }


  /**
   * Deletes the Song instance identified by a given key-value pair.
   * @param key The name of a Song attribute
   * @param value The existing value stored in the Song attribute
   * @throws Exception
   */
  public void delete(String key, String value) throws Exception {

    // See apispreadsheets.com
    String query = "query=delete%20from%20" + FILE_ID + "%20where%20" + key + "='" + value + "'";
    String urlstr = BASE_URL + "/" + FILE_ID + "/?" + query;
    this.sendGetRequest(urlstr);
  }

  /**
   * Private method to send a GET request to a given URL.
   * @param urlstr Service URL
   * @return A GSON JsonObject containing the response data
   * @throws Exception
   */
  private JsonObject sendGetRequest(String urlstr) throws Exception {

    URL url = new URL(urlstr);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");
    BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
    String response = rd.lines().collect(Collectors.joining());
    con.disconnect();

    if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
      System.out.println("Unexpected GET response code: " + response);
    }
    JsonElement jelement = JsonParser.parseString(response);
    return jelement.getAsJsonObject();
  }


  /**
   * Private method to send a POST request to a given URL.
   * @param urlstr Service URL
   * @param json JSON-formatted data to send
   * @return The response code
   * @throws Exception
   */
  private int sendPostRequest(String urlstr, String json) throws Exception {

    URL url = new URL(urlstr);
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("POST");

    con.setDoOutput(true);
    OutputStream os = con.getOutputStream();
    os.write(json.getBytes());
    os.flush();
    os.close();
    con.disconnect();

    int responseCode = con.getResponseCode();
    //System.out.println("POST response code: " + responseCode);

    return responseCode;
  }

}
