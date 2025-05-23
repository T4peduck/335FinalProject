package model;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;


public class User {
    private String username;
    private byte[] password;
    private byte[] salt;

    public User(String username, String password) throws NoSuchAlgorithmException {
        this.username = username;
        setPassword(password);
    }
    
    public User(User user) {
    	username = user.getUserName();
    	password = user.getPassword();
    	salt = user.getSalt();
    }
    
    /*
	 * public ArrayList<Book> searchBookByTitle(String name, Library library) -
	 * 		returns an arraylist of all books with the specified title
	 */
	public ArrayList<Book> searchBookByTitle(String name, Library library) {
        return library.searchAllBooksByTitle(name);
    }
	
	/*
	 * public ArrayList<Book> searchBookByAuthor(String name, Library library) -
	 * 		returns an arraylist of all books written by the specified author
	 */
	public ArrayList<Book> searchBookByAuthor(String author, Library library) {
        return library.searchAllBooksByAuthor(author);
    }

	/*
	 * public boolean checkAvailable(String id, Library library) -
	 * 		determines if the Book with the id is available in library
	 */
    public boolean checkAvailable(String id, Library library) {
        return library.searchAvailBookByID(id) != null;
    }
    
    /*
	 * public boolean passwordMatched(String password) -
	 * 		determines if the password matches the User's password
	 */
    public boolean passwordMatched(String password) throws NoSuchAlgorithmException {
    	return Arrays.equals(getHashPassword(password, salt), this.password);
    }
    
    /*
	 * public String getUserName() -
	 * 		returns the username of User
	 */
    public String getUserName() {
    	return username;
    }
    
    /*
	 * public byte[] getPassword() -
	 * 		returns the hashed password in bytes
	 */
    public byte[] getPassword() {
		return password.clone();
	}

    /*
	 * public byte[] getSalt() -
	 * 		returns the salt used for cryptographic hash
	 */
	public byte[] getSalt() {
		return salt.clone();
	}
    
	/*
     * @pre - password.matches("(?=.*[a-z].*)(?=.*[A-Z].*)(?=.*\\d.*)(?=.*[~!@#$%^&*()_+].*)(?!.*\\s.*).+")
     * 
     * public void setPassword(String password) -
	 * 		sets the initial password to the User or reset the password
     */
	public void setPassword(String password) throws NoSuchAlgorithmException {
    	SecureRandom saltGen = new SecureRandom();
        salt = new byte[2];
        saltGen.nextBytes(salt);
        this.password = getHashPassword(password, salt);
    }
        
	/*
     * @pre - salt is the same one that hashes the password
     * 
     * private byte[] getHashPassword(String password, byte[] salt) -
	 * 		generates and returns the hashed password in bytes
     */
	private byte[] getHashPassword(String password, byte[] salt) throws NoSuchAlgorithmException {
		MessageDigest hashFunct = MessageDigest.getInstance("MD5");
		byte[] digest = new byte[2 + password.length()];
		for (int i = 0; i < 2; i++)
			digest[i] = salt[i];

		for (int i = 2; i < 2 + password.length(); i++)
			digest[i] = password.getBytes()[i - 2];

		digest = hashFunct.digest(digest);
		return digest;
	}
}
