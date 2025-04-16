package model;

import java.security.NoSuchAlgorithmException;

public class Borrower extends User {
    public Borrower (String username, String password) throws NoSuchAlgorithmException {
        super(username, password);
    }
    
    public Borrower (Borrower borrower) {
    	super(borrower);
    }

    public Book checkOutBook(String id) {
        return null;
    }

    public Book putBookOnHold(String id) {
        return null;
    }
}
