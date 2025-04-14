package model;
import java.util.ArrayList;

public class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public ArrayList<Book> searchBook(String name, String author) {
        return null;
    }

    public boolean checkAvailable(String id) {
        return false;
    }
    
    public String getUserName() {
    	return username;
    }
}
