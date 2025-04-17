package model;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class Borrower extends User {
	private HashMap<String, ArrayList<Book>> history;
	private HashMap<String, ArrayList<Book>> historyByAuthor;
	private HashMap<String, ArrayList<Book>> historyByGenre;
	
	private ArrayList<Book> checkedOut;
	private ArrayList<Book> onHold;
	
    public Borrower (String username, String password) throws NoSuchAlgorithmException {
        super(username, password);
        history = new HashMap<>();
        historyByAuthor = new HashMap<>();
        historyByGenre = new HashMap<>();
        checkedOut = new ArrayList<>();
    }
    
    public Borrower (Borrower borrower) {
    	super(borrower);
    }

    public void checkOutBook(String id) {
    	// use the library method for checking out
    	
    	// update checkedOut
        return null;
    }
    
    public void checkinBook() {
    	// use library method to check in
    	
    	// update histories and cheked out
    	
    	// automatically check out next book on hold if available??
    }

    public void putBookOnHold(String id) {
    	// use the library method for holding?
        return null;
    }
    
    public ArrayList<Book> getRecommendations(){
    	// find their most common genre from history
    	
    	// call a library method for getting books from this genre
    }
    
    // geters? History with the different comparators, searching history
    
    // calling library methods they have access to: searches, etc
}
