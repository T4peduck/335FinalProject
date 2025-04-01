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
                .uri(URI.create("https://gutendex.com/books")).build();

            client.sendAsync(request, BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println);
        
            System.out.println(request);

        } catch (Exception e) {
            System.exit(1);
        }
    }
}
