package model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import java.io.BufferedReader;
import

public class LibraryController {
	private final String LIBPATH = "/model/library";
	private static BufferedReader br;
	
	
	public static boolean addBookToLibrary(JSONObject response) {
		try {
		//no books from search were found
		if(response.get("count").equals("0")) {
			return false;
		}
		
		JSONArray arr = (JSONArray) response.get("results");
		JSONObject book = (JSONObject) arr.get(0);
		
		String bookurl = (String) ((JSONObject) book.get("formats"))
												.get("text/html");
		
		
		
		return true;
		} catch (IOException e) {
			System.exit(1);
		}
	}
}
