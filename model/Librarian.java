package model;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class Librarian extends User{
    public Librarian(String username, String password) throws NoSuchAlgorithmException {
        super(username, password);
    }
    
    public Librarian (Librarian librarian) {
    	super(librarian);
    }

    public ArrayList<String> getBorrowerList() {
        return null;
    }

    public Borrower inspectBorrower() {
        return null;
    }

    public void removeBorrower() {

    }
    
    public void addBook() {
    	
    }
    
    public void removeBook() {
    	
    }
    
    public ArrayList<String> getAvailBookList() {
        return null;
    }

    public ArrayList<String> getUnavailBookList() {
        return null;
    }
}
