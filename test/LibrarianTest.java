package test;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import model.Author;
import model.Book;
import model.Borrower;
import model.Librarian;
import model.Library;

class LibrarianTest {

	private Library library = new Library();
	private Author a1 = new Author("Arthur", 2030, 1999);
	private ArrayList<Author> authors = new ArrayList<>();
	private ArrayList<String> genres = new ArrayList<>();
	private ArrayList<Borrower> borrowers = new ArrayList<>();
	
	@Test
	void testRemoveBorrower() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Borrower borrow1 = new Borrower("lix2_an_wei", "1a2B3c!");
		borrowers.add(borrow1);
		Librarian l1 = new Librarian("librarian1", "1a2B3c!", borrowers);
		l1.removeBorrower(borrow1);
		assertFalse(borrowers.contains(borrow1));
	}

	@Test
	void testAddBook() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book book1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower borrow1 = new Borrower("lix2_an_wei", "1a2B3c!");
		borrowers.add(borrow1);
		Librarian l1 = new Librarian("librarian1", "1a2B3c!", borrowers);
		l1.addBook(book1, library);
		assertNotEquals(library.searchAllBookByID("12345"), null);
	}

	@Test
	void testRemoveBook() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book book1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower borrow1 = new Borrower("lix2_an_wei", "1a2B3c!");
		borrowers.add(borrow1);
		Librarian l1 = new Librarian("librarian1", "1a2B3c!", borrowers);
		l1.addBook(book1, library);
		l1.removeBook(book1, library);
		assertEquals(library.searchAllBookByID("12345"), null);
	}

	@Test
	void testGetAvailBookListByAuthor() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book book1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower borrow1 = new Borrower("lix2_an_wei", "1a2B3c!");
		borrowers.add(borrow1);
		Librarian l1 = new Librarian("librarian1", "1a2B3c!", borrowers);
		l1.addBook(book1, library);
		Librarian l2 = new Librarian(l1);
		assertEquals(l1.getAvailBookListByAuthor(library).get(0), book1.toString());
		assertEquals(l2.getAvailBookListByAuthor(library).get(0), book1.toString());
	}

	@Test
	void testGetAvailBookListByTitle() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book book1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower borrow1 = new Borrower("lix2_an_wei", "1a2B3c!");
		borrowers.add(borrow1);
		Librarian l1 = new Librarian("librarian1", "1a2B3c!", borrowers);
		l1.addBook(book1, library);
		Librarian l2 = new Librarian(l1);
		assertEquals(l1.getAvailBookListByTitle(library).get(0), book1.toString());
		assertEquals(l2.getAvailBookListByTitle(library).get(0), book1.toString());
	}

	@Test
	void testGetUnavailBookListByAuthor() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book book1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower borrow1 = new Borrower("lix2_an_wei", "1a2B3c!");
		borrowers.add(borrow1);
		Librarian l1 = new Librarian("librarian1", "1a2B3c!", borrowers);
		l1.addBook(book1, library);
		borrow1.checkOutBook(book1, library);
		assertEquals(l1.getUnavailBookListByAuthor(library).get(0), book1.toString());
	}

	@Test
	void testGetUnavailBookListByTitle() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book book1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower borrow1 = new Borrower("lix2_an_wei", "1a2B3c!");
		borrowers.add(borrow1);
		Librarian l1 = new Librarian("librarian1", "1a2B3c!", borrowers);
		l1.addBook(book1, library);
		borrow1.checkOutBook(book1, library);
		assertEquals(l1.getUnavailBookListByTitle(library).get(0), book1.toString());
	}

	@Test
	void testRecommend() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book book1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower borrow1 = new Borrower("lix2_an_wei", "1a2B3c!");
		borrowers.add(borrow1);
		Librarian l1 = new Librarian("librarian1", "1a2B3c!", borrowers);
		l1.addBook(book1, library);
		l1.recommend(book1, library);
		assertTrue(library.getRecBooksByAuthor().contains(book1));
	}

	@Test
	void testRemoveRecommend() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book book1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower borrow1 = new Borrower("lix2_an_wei", "1a2B3c!");
		borrowers.add(borrow1);
		Librarian l1 = new Librarian("librarian1", "1a2B3c!", borrowers);
		l1.addBook(book1, library);
		l1.recommend(book1, library);
		l1.removeRecommend(book1, library);
		assertFalse(library.getRecBooksByAuthor().contains(book1));
	}

}
