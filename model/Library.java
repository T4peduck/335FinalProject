package model;

import java.util.HashMap;
import java.util.ArrayList;

public class Library {
    private HashMap<String, Borrower> borrowerList;
    private HashMap<String, Librarian> librarianList;
    
    private HashMap<String, ArrayList<Book>> availableBooks;
    private HashMap<String, ArrayList<Book>> unavailableBooks;

    public Library() {
        borrowerList = new HashMap<>();
        librarianList = new HashMap<>();

        availableBooks = new HashMap<>();
        unavailableBooks = new HashMap<>();
    }
    
    public void addLibrarian(Librarian l) {
    	
    }
    
    public void addBorrower(Borrower b) {
    	
    }
    
    public void checkLateBooks() {
    	
    }
    
    // also take strings, or a Book?
    public void checkout(Borrower b) {
    	
    }
    
    public void hold(Borrower b) {
    	
    }
    
    public void checkin(Borrower b) {
    	
    }
    
    public ArrayList<Book> searchAllBooksByTitle(String title) {
    	return new ArrayList<Book>();
    }
    
    public ArrayList<Book> searchAvailBooksByTitle(String title) {
    	return new ArrayList<Book>();
    }
    
    public ArrayList<Book> searchAllBooksByAuthor(String author) {
    	return new ArrayList<Book>();
    }
    
    public ArrayList<Book> searchAvailBooksByAuthor(String author) {
    	return new ArrayList<Book>();
    }
    
    public ArrayList<Book> searchAllBooksByCallNumber(String callNumber) {
    	return new ArrayList<Book>();
    }
    
    public ArrayList<Book> searchAvailBooksByCallNumber(String callNumber) {
    	return new ArrayList<Book>();
    }
    
    /*
     * package private methods for the other classes in the model
     * to use
     */
    ArrayList<Borrower> getBorrowerList(){
    	return new ArrayList<Borrower>(borrowerList.values());
    	
    }
    
    ArrayList<Book> getAvailBooks(){
    	ArrayList<Book> books = new ArrayList<>();
    	for (ArrayList<Book> list:availableBooks.values()) {
    		books.addAll(list);
    	}
    	return books;
    }
    
    ArrayList<Book> getUnavailBooks(){
    	ArrayList<Book> books = new ArrayList<>();
    	for (ArrayList<Book> list:unavailableBooks.values()) {
    		books.addAll(list);
    	}
    	return books;
    }
}
