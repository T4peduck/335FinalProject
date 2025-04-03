import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.*;



public class main {
    public static void main(String[] args) {
        System.out.println("hopefully api works");
        
        try{
            HttpClient client       = HttpClient.newHttpClient();
            HttpRequest request     = HttpRequest.newBuilder()
                .uri(URI.create("https://gutendex.com/books/?ids=1")).GET().build();

            HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

            System.out.println(response.body());
            JSONParser parser = new JSONParser();
            JSONObject JSONResult = (JSONObject) parser.parse(response.body());

            
            System.out.println(JSONResult);
            System.out.println(JSONResult.get("results"));
            System.out.println(JSONResult.get("count")); 

        } catch (Exception e) {
            System.exit(1);
        }
    }
}
