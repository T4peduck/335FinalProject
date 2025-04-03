package model;
public class Borrower extends User {
    public Borrower (String username, String password) {
        super(username, password);
    }

    public Book checkOutBook(String id) {
        return null;
    }

    public Book putBookOnHold(String id) {
        return null;
    }
}
