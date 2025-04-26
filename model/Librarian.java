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

    /*
	 * public ArrayList<Borrower> getBorrowerList() -
	 * 		returns the list of existing Borrowers who hold accounts to access to Library
	 */
    public ArrayList<Borrower> getBorrowerList() {
    	ArrayList<Borrower> borrowerList = new ArrayList<>();
    	for (Borrower borrower : borrowers)
    		borrowerList.add(new Borrower(borrower));
    	
        return borrowerList;
    }
    
    /*
     * @pre - Borrower borrower is an existing user
     * 
     * public void removeBorrower(Borrower borrower)
     * 		remove Borrower borrower's account, so it no longer has access to Library
     */
    public void removeBorrower(Borrower borrower) {
    	borrowers.remove(borrower);
    }
    
    /*
	 * public void addBook(Book book, Library library) -
	 * 		adds Book book to Library library
	 */
    public void addBook(Book book, Library library) {
    	library.addBook(book);
    }
    
    /*
	 * public void removeBook(Book book, Library library) -
	 * 		removes Book book from Library library
	 */
    public void removeBook(Book book, Library library) {
    	library.removeBook(book);
    }
    
    /*
	 * public void getAvailBookListByAuthor(Library library) -
	 * 		returns all available Books sorted by Author
	 */
    public ArrayList<String> getAvailBookListByAuthor(Library library) {
    	ArrayList<String> bookList = new ArrayList<>();
    	for (Book book : library.getAvailBooksByAuthor())
    		bookList.add(book.toString());
    	
        return bookList;
    }
    
    /*
	 * public void getAvailBookListByTitle(Library library) -
	 * 		returns all available Books sorted by titles
	 */
    public ArrayList<String> getAvailBookListByTitle(Library library) {
    	ArrayList<String> bookList = new ArrayList<>();
    	for (Book book : library.getAvailBooksByTitle())
    		bookList.add(book.toString());
    	
        return bookList;
    }
    
    /*
	 * public void getUnavailBookListByAuthor(Library library) -
	 * 		returns all unavailable Books sorted by Author
	 */
    public ArrayList<String> getUnavailBookListByAuthor(Library library) {
    	ArrayList<String> bookList = new ArrayList<>();
    	for (Book book : library.getUnavailBooksByAuthor())
    		bookList.add(book.toString());
    	
        return bookList;
    }
    
    /*
	 * public void getUnavailBookListByTitle(Library library) -
	 * 		returns all unavailable Books sorted by titles
	 */
    public ArrayList<String> getUnavailBookListByTitle(Library library) {
    	ArrayList<String> bookList = new ArrayList<>();
    	for (Book book : library.getUnavailBooksByTitle())
    		bookList.add(book.toString());
    	
        return bookList;
    }
    
    /*
	 * public void recommend(Book book, Library library) -
	 * 		recommends Book book in Library library
	 */
    public void recommend(Book book, Library library) {
    	library.recommend(getUserName(), book);
    }
    
    /*
	 * public void removeRecommend(Book book, Library library) -
	 * 		removes the specified recommendation from Library library
	 */
    public void removeRecommend(Book book, Library library) {
    	library.removeRecommend(getUserName(), book);
    }
    
    /*
	 * public ArrayList<Book> getRecommendations(Library library) -
	 * 		returns the Books recommended by Librarian in Library library
	 */
    public ArrayList<Book> getRecommendations(Library library){
    	return library.getRecommendationsByLibrarian(getUserName());
    }
}
