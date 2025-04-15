/*
 * Used for testing API and parsing of ParseBook.java
 */
package model;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

public class TestParseBook {
    public static void main(String[] args) {
        System.out.println("hopefully api works");
        
        try{
            ParseBook.addAuthorAndTitle("Mary", "Shelley", "Frankenstein");
            JSONObject obj = ParseBook.makeRequest();
            
            //System.out.println(obj);

            JSONArray arr = (JSONArray) obj.get("results");
            
            JSONObject result = (JSONObject) arr.get(0);
            System.out.println(result.get("ids"));
            System.out.println(result.get("title"));
            System.out.println(result.get("authors"));
            System.out.println(result.get("summaries"));
            System.out.println(result.get("translators"));
            System.out.println(result.get("subjects"));
            System.out.println(result.get("bookshelves"));
            System.out.println(result.get("language"));
            System.out.println(result.get("formats"));
           
            String bookurl = (String) ((JSONObject) result.get("formats"))
					.get("text/html");
            
            System.out.println(bookurl);

            
            
            
            ParseBook.addId("4");
            JSONObject obj2 = ParseBook.makeRequest();

            //System.out.println(obj2);

        } catch (Exception e) {
            System.exit(1);
        }
    }
}
