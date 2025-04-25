package model;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Librarian extends User{
	private ArrayList<Borrower> borrowers;
    public Librarian(String username, String password, ArrayList<Borrower> borrowers) throws NoSuchAlgorithmException {
        super(username, password);
        // The borrowers are assumed to be passed in by View
        this.borrowers = borrowers;
    }
    
    public Librarian (Librarian librarian) {
    	super(librarian);
    	borrowers = librarian.getBorrowerList();
    }

    // This won't allow any access to the Borrowers
    public ArrayList<Borrower> getBorrowerList() {
    	ArrayList<Borrower> borrowerList = new ArrayList<>();
    	for (Borrower borrower : borrowers)
    		borrowerList.add(new Borrower(borrower));
    	
        return borrowerList;
    }

    public Borrower inspectBorrower() {
        return null;
    }

    /*
     * @pre - Borrower borrower is an existing user
     */
    public void removeBorrower(Borrower borrower) {
    	borrowers.remove(borrower);
    }
    
    public void addBook(Book book, Library library) {
    	library.addBook(book);
    }
    
    public void removeBook(Book book, Library library) {
    	library.removeBook(book);
    }
    
    public ArrayList<String> getAvailBookListByAuthor(Library library) {
    	ArrayList<String> bookList = new ArrayList<>();
    	for (Book book : library.getAvailBooksByAuthor())
    		bookList.add(book.toString());
    	
        return bookList;
    }
    
    public ArrayList<String> getAvailBookListByTitle(Library library) {
    	ArrayList<String> bookList = new ArrayList<>();
    	for (Book book : library.getAvailBooksByTitle())
    		bookList.add(book.toString());
    	
        return bookList;
    }
    
    public ArrayList<String> getUnavailBookListByAuthor(Library library) {
    	ArrayList<String> bookList = new ArrayList<>();
    	for (Book book : library.getUnavailBooksByAuthor())
    		bookList.add(book.toString());
    	
        return bookList;
    }
    
    public ArrayList<String> getUnavailBookListByTitle(Library library) {
    	ArrayList<String> bookList = new ArrayList<>();
    	for (Book book : library.getUnavailBooksByTitle())
    		bookList.add(book.toString());
    	
        return bookList;
    }
    
    public void recommend(Book book, Library library) {
    	library.recommend(getUserName(), book);
    }
    
    public void removeRecommend(Book book, Library library) {
    	library.removeRecommend(getUserName(), book);
    }
}
