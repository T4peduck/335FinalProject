import java.util.ArrayList;
import org.json.simple.JSONObject;

public class bookResponseHandler {
    private String body;
    
    private int count;
    private String next;
    private String previous;

    /*
     * Index : peice of data
     * 0 : int id
     * 1 : String title
     * 2 : ArrayList<Strings> Authors, birth_year, death_year
     * 3 : Summaries
     * 4 : ArrayList<Strings> Translators
     * 5 : ArrayList<Strings> Subjects
     * 6 : ArrayList<Strings> bookShelves
     * 7 : ArrayList<Strings> languages
     * 8 : bool copyright
     * 9 : media_type
     * 10: ArrayList<Strings> formats
     * 11: download count
     */
    private ArrayList<Object> results;
    

    public bookResponseHandler(String json) {
        this.body = json;
        String[] parts = json.split(",");
    }

    private void bodyParser() {
        body.indexOf("\"count\":");
    }
    
    private int parseCount() {
        int countInd = body.indexOf("\"count\":");
        String res = "";
        for(int i = countInd + 1; body.charAt(i) != ','; i++) {
            res += body.charAt(i);
            System.out.println(res);
        }
        return 0;
    }


    
}