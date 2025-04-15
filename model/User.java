package model;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;


public class User {
    private String username;
    private String password;
    private byte[] salted;

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
    
    public void hashPassword() throws NoSuchAlgorithmException {
    	try {
    		byte[] salt;
        	SecureRandom saltGen = new SecureRandom();
            salt = new byte[2];
            saltGen.nextBytes(salt);
            MessageDigest hashFunct = MessageDigest.getInstance("MD5");
            salted = new byte[2 + password.length()];
            for (int i = 0; i < 2; i++)
                salted[i] = salt[i];

            for (int i = 2; i < 2 + password.length(); i++)
                salted[i] = password.getBytes()[i - 2];
    	}
    	
    	catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    	
    }
}
