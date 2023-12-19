package gps;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;

public class APICaller extends Thread {

  private String url;
  private String data;
  private HttpResponse<InputStream> response;

  public APICaller(String url, String data) {
    this.url = url;
    this.data = data;
  }

  public void run() {
    try {
      HttpRequest request = HttpRequest.newBuilder()
              .uri(new URI(url))
              .method("GET", HttpRequest.BodyPublishers.ofString(data))
              .header("Content-Type", "application/json")
              .build();
      response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofInputStream());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public HttpResponse<InputStream> getResponse() {
    return response;
  }
}
