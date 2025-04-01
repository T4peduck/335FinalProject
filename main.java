import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;


public class main {
    public static void main(String[] args) {
        System.out.println("hopefully api works");
        
        try{
            HttpClient client       = HttpClient.newHttpClient();
            HttpRequest request     = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books/")).GET().build();

            HttpResponse response = client.send(request, BodyHandlers.ofString());

            System.out.println(response.body());

        } catch (Exception e) {
            System.exit(1);
        }
    }
}
