package model;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

public class Borrower extends User {
	private ArrayList<Book> history;
	
	private ArrayList<Book> checkedOut;
	private ArrayList<Book> onHold;
	
    public Borrower (String username, String password) throws NoSuchAlgorithmException {
        super(username, password);
        history = new ArrayList<>();
        onHold = new ArrayList<>();
        checkedOut = new ArrayList<>();
    }
    
    public Borrower (Borrower borrower) {
    	super(borrower);
    }

    /*
     * @pre - the Book is in the library, and available
     */
    public void checkOutBook(Book b, Library l) {
    	// use the library method for checking out
    	l.checkout(b);
    	// update checkedOut
    	checkedOut.add(b); // Do we deal with the escaping reference here?
        return;
    }
    
    /*
     * @pre - the Book is in the library, and unavailable
     */
    public void checkinBook(Book b, Library l) {
    	// use library method to check in
    	l.checkin(b);
    	checkedOut.remove(b);
    	// update histories and checked out
    	history.add(b);
    	// automatically check out next book on hold if available??
    }

    /*
     * @pre - the Book is in the library, and unavailable
     */
    public void putBookOnHold(Book b, Library l) {
    	// use the library method for holding?
    	onHold.add(0, b);
    	l.hold(b, this);
    }
    
    // getters? History with the different comparators, searching history
    
    // chronological
    public ArrayList<Book> getHistory(){
    	return new ArrayList<>(history);
    }
    
    public ArrayList<Book> getHistoryByTitle(){
    	ArrayList<Book> copy = new ArrayList<>(history);
    	Collections.sort(copy, Book.titleFirstComparator());
    	return copy;
    }
    
    public ArrayList<Book> getHistoryByAuthor(){
    	ArrayList<Book> copy = new ArrayList<>(history);
    	Collections.sort(copy, Book.authorFirstComparator());
    	return copy;
    }
    
    public ArrayList<Book> checkedOut(){
    	return new ArrayList<>(checkedOut);
    }
    
    public ArrayList<Book> onHold(){
    	return new ArrayList<>(onHold);
    }
    
    public boolean hasBook(Book b) {
    	return checkedOut.contains(b);
    }

    // used by the library after it's been officially checked out to update the user
	void checkOutHold(Book b) {
		onHold.remove(b);
		checkedOut.add(b);
	}
    
    
    
}
