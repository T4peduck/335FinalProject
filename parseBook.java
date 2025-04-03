import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//TODO: may need to add "@pre to a couple"
public class ParseBook {
    private static String APIENDPOINT = "https://gutendex.com/books/?";
    private static String URIbuilder = APIENDPOINT;
    private static HttpClient client = HttpClient.newHttpClient();
    private static JSONParser parser = new JSONParser();

    public static JSONObject makeRequest() throws Exception{
        System.out.println(URIbuilder);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URIbuilder)).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        URIbuilder = APIENDPOINT;

        return (JSONObject) parser.parse(response.body());
    }

    private static String replaceSpaces(String s) {
        int i;
        while((i = s.indexOf(" ")) != -1) {
            s = s.substring(0, i) + "%20" + s.substring(i+1, 0);
        }

        return s;
    }

    public static void addAuthorAndTitle(String firstName, String lastName, String title) {
        URIbuilder += "search=" + replaceSpaces(firstName) + "%20" +
                                replaceSpaces(lastName) + "%20" +
                                replaceSpaces(title) + "&";
    }

    public static void addId(String id) {
        URIbuilder += "ids=" + id + "&";
    }

    public static void addYearStart(String yearStart) {
        URIbuilder += "author_year_start=" + yearStart + "&";
    }

    public static void addYearEnd(String yearEnd) {
        URIbuilder += "author_year_end=" + yearEnd + "&";
    }

    public static void addTopic(String topic) {
        URIbuilder += "topic=" + topic + "&";
    }

    



}
