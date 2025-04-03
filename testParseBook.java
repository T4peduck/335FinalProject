/*
 * Used for testing API and parsing of ParseBook.java
 */

import org.json.simple.JSONObject;

public class testParseBook {
    public static void main(String[] args) {
        System.out.println("hopefully api works");
        
        try{
            ParseBook.addAuthorAndTitle("Mary", "Shelley", "Frankenstein");
            JSONObject obj = ParseBook.makeRequest();

            System.out.println(obj.get("results"));

            ParseBook.addId("4");
            JSONObject obj2 = ParseBook.makeRequest();

            System.out.println(obj2);

        } catch (Exception e) {
            System.exit(1);
        }
    }
}
