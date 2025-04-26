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
    	history = borrower.getHistory();
    	onHold = borrower.onHold();
    	checkedOut = borrower.checkedOut();
    }

    /*
     * @pre - the Book is in the library, and available
     * public void checkOutBook(Book b, Library l)
     * 		checks the Book b out from Library l
     */
    public void checkOutBook(Book b, Library l) {
    	// use the library method for checking out
    	l.checkout(b);
    	// update checkedOut
    	checkedOut.add(b);
        return;
    }
    
    /*
     * @pre - the Book is in the library, and unavailable
     * public void checkinBook(Book b, Library l)
     * 		checks the Book b in to Library l
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
     * public void checkinBook(Book b, Library l)
     * 		puts the Book b to a hold list in Library l
     */
    public void putBookOnHold(Book b, Library l) {
    	// use the library method for holding?
    	onHold.add(0, b);
    	l.hold(b, this);
    }
    
    // chronological
    
    /*
	 * public ArrayList<Book>  getHistory() -
	 * 		returns list of the Books Borrower checked out before
	 */
    public ArrayList<Book> getHistory(){
    	return new ArrayList<>(history);
    }
    
    /*
	 * public ArrayList<Book> getHistoryByTitle() -
	 * 		returns list of the Books Borrower checked out before ordered by the Book titles
	 */
    public ArrayList<Book> getHistoryByTitle(){
    	ArrayList<Book> copy = new ArrayList<>(history);
    	Collections.sort(copy, Book.titleFirstComparator());
    	return copy;
    }
    
    /*
	 * public ArrayList<Book> getHistoryByTitle() -
	 * 		returns list of the Books Borrower checked out before ordered by the Book authors
	 */
    public ArrayList<Book> getHistoryByAuthor(){
    	ArrayList<Book> copy = new ArrayList<>(history);
    	Collections.sort(copy, Book.authorFirstComparator());
    	return copy;
    }
    
    /*
	 * public ArrayList<Book> checkedOut() -
	 * 		returns list of the Books Borrower checked out from Library
	 */
    public ArrayList<Book> checkedOut(){
    	return new ArrayList<>(checkedOut);
    }
    
    /*
	 * public ArrayList<Book> onHold() -
	 * 		returns list of the Books Borrower put on hold in Library
	 */
    public ArrayList<Book> onHold(){
    	return new ArrayList<>(onHold);
    }
    
    /*
	 * public boolean hasBook(Book b) -
	 * 		determines if Borrower borrowed the Book b
	 */
    public boolean hasBook(Book b) {
    	return checkedOut.contains(b);
    }

    /*
	 * void checkOutHold(Book b) -
	 * 		checks out the Book b from the on hold list in Library
	 * 
	 * Note: used by the library after it's been officially checked out to update the user
	 */
	void checkOutHold(Book b) {
		onHold.remove(b);
		checkedOut.add(b);
	}
	
	/*
	 * @pre - this user has this book
	 * 
	 * public int getHoldPosition(Book b, Library l)
	 * 		returns the position Borrower is at for the Book b in the on hold list from Library l
	 */
	public int getHoldPosition(Book b, Library l) {
		return l.getHoldPosition(b, this);
	}  
}
