package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.util.HashMap;

public class LibraryController {
	
	public static boolean addBookToLibrary(JSONObject response) {
		//no books from search were found
		if(response.get("count").equals("0")) {
			return false;
		}
		
		JSONArray books = response.getJsonArray("results");
		response.g
		
		
		return true;
	}
}
