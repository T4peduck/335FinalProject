package model;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//TODO: may need to add "@pre to a couple"
//TODO: may need a public method to search and see if book alr in libary
public class ParseBook {
    private static final String APIENDPOINT = "https://gutendex.com/books/?";
    private static String URIbuilder = APIENDPOINT;
    private static HttpClient client = HttpClient.newHttpClient();
    private static JSONParser parser = new JSONParser();
    
/*
 * THESE FUNCTIONS BUILD AND SEND THE REQUEST
 * -----------------------------------------------
 */
 
    /*
     * Makes the request to the API and returns a JSONObject with resulting
     * info from call
     */
    private static JSONObject makeRequest() throws Exception{
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(URIbuilder)).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());

        URIbuilder = APIENDPOINT;

        return (JSONObject) parser.parse(response.body());
    }

    /*
     * cleans a string so it uses HTML URL Encoding of spaces (%20)
     * instead of actual spaces
     */
    private static String replaceSpaces(String s) {
        int i;
        while((i = s.indexOf(" ")) != -1) {
            s = s.substring(0, i) + "%20" + s.substring(i+1);
        }

        return s;
    }
    
    /*
     * These methods add terms to the search query
     */

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
        URIbuilder += "topic=" + replaceSpaces(topic) + "&";
    }

    /*
     * THIS FUNCTION RETURNS A LIST OF BOOKS THAT MATCHED SEARCH
     * AND ADDS THE THE BOOKS MODEL.LIBRARYTEXT AS A TEXT FILE
     * ----------------------------------------------------------
     * returns a book object holding the metadata for the book
     */
    public static ArrayList<Book> downloadBooks() {
    	JSONObject result = null;
    	
    	try {
			result = makeRequest();
		} catch (Exception e) {e.printStackTrace();}
    	    	
    	if(result == null) {
    		System.out.println("ERROR: no results found");
    		return null;
    	}
 
    	//arr holds all the books from the query
    	JSONArray arr = (JSONArray) result.get("results");
    	if(arr.isEmpty()) {
    		System.out.println("ERROR: no results found");
    		return null;
    	}
    	
    	ArrayList<Book> resultList = new ArrayList<>();
    	
    	//iterates through and creates book Objects from the JSON data
    	for(Object n: arr) {
			JSONObject book = (JSONObject) n;			
			Book b = makeBook(book);
			String bookurl = "https://www.gutenberg.org/cache/epub/" + 
							book.get("id") + "/pg" + book.get("id") + ".txt";
			
			if(!resultList.contains(b)) {
				//downloads the books from Project Gutenberg
				addBookToLibaryFolder(b, bookurl);
				resultList.add(b);
			}
    	}
    	
    	System.out.println("BOOK(S) SUCCESSFULY DOWNLOADED");
    	
    	return resultList;
    }
    
    /*
     * makes the book to return
     */
    private static Book makeBook(JSONObject book) {
    	String title = book.get("title").toString();
    	ArrayList<Author> alist = formatAuthor(book);
    	String summary;
    	if(((JSONArray) book.get("summaries")).isEmpty()) {
    		summary = "";
    	} else {
    		summary = ((JSONArray) book.get("summaries")).get(0).toString();
    	}
    	String id = book.get("id").toString();
    	
    	return new Book(title, alist, id, summary, "model/LibraryText/" + cleanTitle(title) + ".txt");
    }
    
    /*
     * removes illegal-file characters from title
     */
    private static String cleanTitle(String title) {
    	String rstr = "";
    	for(int i = 0; i < title.length(); i++) {
    		if(!"*\"\\/<>:|?;,. ".contains(Character.toString(title.charAt(i)))) {
    			rstr += Character.toString(title.charAt(i));
    		}
    	}
    	return rstr;
    }
    
    /*
     * creates a list of authors if book has multiple
     * NOTE: If birthyear and deathyear are null, will set them to 0;
     */
    private static ArrayList<Author> formatAuthor(JSONObject book) {
    	ArrayList<Author> alist = new ArrayList<Author>();
    	JSONArray authorList = (JSONArray) book.get("authors");
    	
    	for(Object a : authorList) {
    		JSONObject author = (JSONObject) a;
    		String name = (String) author.get("name");
    		String birthYear = "";
    		String deathYear = "";
    		
    		if(author.get("birth_year") != null) {
    			birthYear = author.get("birth_year").toString();
    		} else {
    			birthYear = "0";
    		}
    		
    		if(author.get("death_year") != null) {
    			deathYear = author.get("death_year").toString();
    		} else {
    			deathYear = "0";
    		}
    		
    		alist.add(new Author(name, Integer.parseInt(birthYear), Integer.parseInt(deathYear)));
    	}
    	return alist;
    }
    
    /*
     * Downloads the book from Project Gutenberg and adds it
     * to the Model.LibraryText folder
     */
    private static void addBookToLibaryFolder(Book b, String bookurl) {
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(bookurl)).GET().build();
        try {
        	//casts the response to a type of input stream (which can be read line by line)
			HttpResponse<InputStream> response = client.send(request, BodyHandlers.ofInputStream());
			InputStream is = response.body();
			
			//casts to a buffered reader so readLine can be used easily
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			//using a printwriter as has a println() function, so \n perserved
			FileWriter pw = new FileWriter(new File(b.filePath));
			
			String line = "";
			while((line = br.readLine()) != null) {
				pw.write(line + "\n");
			}
			
			br.close();
			pw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	return;
    }
}
