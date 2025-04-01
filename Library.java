
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
    
    // also take strings, or a Book?
    public void checkout(Borrower b) {
    	
    }
    
    public String searchAllBooksByTitle(String title) {
    	return "";
    }
    
    public String searchAvailBooksByTitle(String title) {
    	return "";
    }
    
    public String searchAllBooksByAuthor(String author) {
    	return "";
    }
    
    public String searchAvailBooksByAuthor(String author) {
    	return "";
    }
    
    public String searchAllBooksByCallNumber(String callNumber) {
    	return "";
    }
    
    public String searchAvailBooksByCallNumber(String callNumber) {
    	return "";
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
