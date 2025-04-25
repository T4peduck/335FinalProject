package test;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import model.Author;
import model.Book;
import model.Library;
import model.User;

class UserTest {
	private Library library = new Library();
	private Author author = new Author("Arthur", 2030, 1999);
	private ArrayList<Author> authors = new ArrayList<>();
	private ArrayList<String> genres = new ArrayList<>();
	
	@Test
	void testSearchBookByTitle() throws NoSuchAlgorithmException {
		authors.add(author);
		genres.add("Fantasy");
		Book book = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		User user = new User("lix2_an_wei", "1a2B3c!");
		library.addBook(book);
		assertEquals(user.searchBookByTitle("The Lord of the Rings", library).get(0), book);
		assertEquals(user.searchBookByTitle("Wrong Book", library).size(), 0);
		User userCpy = new User(user);
		assertEquals(userCpy.searchBookByTitle("The Lord of the Rings", library).get(0), book);
		assertEquals(userCpy.searchBookByTitle("Wrong Book", library).size(), 0);
	}

	@Test
	void testSearchBookByAuthor() throws NoSuchAlgorithmException {
		authors.add(author);
		genres.add("Fantasy");
		Book book = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		User user = new User("lix2_an_wei", "1a2B3c!");
		library.addBook(book);
		assertEquals(user.searchBookByAuthor("Arthur", library).get(0), book);
		assertEquals(user.searchBookByAuthor("Wrong Author", library).size(), 0);
		User userCpy = new User(user);
		assertEquals(userCpy.searchBookByAuthor("Arthur", library).get(0), book);
		assertEquals(userCpy.searchBookByAuthor("Wrong Author", library).size(), 0);
	}

	@Test
	void testCheckAvailable() throws NoSuchAlgorithmException {
		authors.add(author);
		genres.add("Fantasy");
		Book book = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		User user = new User("lix2_an_wei", "1a2B3c!");
		library.addBook(book);
		assertTrue(user.checkAvailable("12345", library));
		assertFalse(user.checkAvailable("6789", library));
		User userCpy = new User(user);
		assertTrue(userCpy.checkAvailable("12345", library));
		assertFalse(userCpy.checkAvailable("6789", library));
	}

	@Test
	void testPasswordMatched() throws NoSuchAlgorithmException {
		authors.add(author);
		User user = new User("lix2_an_wei", "1a2B3c!");
		assertTrue(user.passwordMatched("1a2B3c!"));
		assertFalse(user.passwordMatched("wRongkey123?"));
		User userCpy = new User(user);
		assertTrue(userCpy.passwordMatched("1a2B3c!"));
		assertFalse(userCpy.passwordMatched("wRongkey123?"));
	}
	
	@Test
	void testGetPassword() throws NoSuchAlgorithmException {
		User user = new User("lix2_an_wei", "1a2B3c!");
		User samePassword = new User("person2", "1a2B3c!");
		assertFalse(Arrays.equals(user.getPassword(), samePassword.getPassword()));
		User userCpy = new User(user);
		assertTrue(Arrays.equals(user.getPassword(), userCpy.getPassword()));
	}
	
	@Test
	void testGetSalt() throws NoSuchAlgorithmException {
		User user = new User("lix2_an_wei", "1a2B3c!");
		User samePassword = new User("person2", "1a2B3c!");
		assertFalse(Arrays.equals(user.getSalt(), samePassword.getSalt()));
		User userCpy = new User(user);
		assertTrue(Arrays.equals(user.getSalt(), userCpy.getSalt()));
	}

	@Test
	void testSetPassword() throws NoSuchAlgorithmException {
		authors.add(author);
		User user = new User("lix2_an_wei", "1a2B3c!");
		user.setPassword("%newK1%");
		assertFalse(user.passwordMatched("1a2B3c!"));
		assertTrue(user.passwordMatched("%newK1%"));
		User userCpy = new User(user);
		assertFalse(userCpy.passwordMatched("1a2B3c!"));
		assertTrue(userCpy.passwordMatched("%newK1%"));
	}

}
