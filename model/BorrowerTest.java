package model;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class BorrowerTest {
	
	private Library library = new Library();
	private Author a1 = new Author("Arthur", 2030, 1999);
	private Author a2 = new Author("weirdo", 1973, 2025);
	private ArrayList<Author> authors = new ArrayList<>();
	private ArrayList<String> genres = new ArrayList<>();
	
	@Test
	void testCheckOutBook() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book b1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower u1 = new Borrower("lix2_an_wei", "1a2B3c!");
		library.addBook(b1);
		u1.checkOutBook(b1, library);
		assertTrue(library.searchUnavailBooksByTitle("The Lord of the Rings").contains(b1));
		assertFalse(library.searchAvailBooksByTitle("The Lord of the Rings").contains(b1));
		assertTrue(u1.hasBook(b1));
		Borrower userCpy = new Borrower(u1);
		assertTrue(userCpy.hasBook(b1));
	}

	@Test
	void testCheckInBook() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book b1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower u1 = new Borrower("lix2_an_wei", "1a2B3c!");
		library.addBook(b1);
		u1.checkOutBook(b1, library);
		u1.checkinBook(b1, library);
		assertFalse(library.searchUnavailBooksByTitle("The Lord of the Rings").contains(b1));
		assertTrue(library.searchAvailBooksByTitle("The Lord of the Rings").contains(b1));
		assertFalse(u1.hasBook(b1));
		Borrower userCpy = new Borrower(u1);
		assertFalse(userCpy.hasBook(b1));
	}

	@Test
	void testPutBookOnHold() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book b1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower u1 = new Borrower("lix2_an_wei", "1a2B3c!");
		library.addBook(b1);
		u1.putBookOnHold(b1, library);
		assertEquals(u1.onHold().get(0), b1);
		Borrower userCpy = new Borrower(u1);
		assertEquals(userCpy.onHold().get(0), b1);
	}

	@Test
	void testGetHistory() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book b1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Book b2 = new Book("The Founder of Diabolism", authors, genres, "103115", "summary2", "mxtx");
		Borrower u1 = new Borrower("lix2_an_wei", "1a2B3c!");
		library.addBook(b1);
		library.addBook(b2);
		assertEquals(u1.getHistory().size(), 0);
		u1.checkOutBook(b1, library);
		assertEquals(u1.getHistory().size(), 0);
		u1.checkinBook(b1, library);
		assertEquals(u1.getHistory().get(0), b1);
		u1.checkOutBook(b2, library);
		assertEquals(u1.getHistory().size(), 1);
		u1.checkinBook(b2, library);
		assertEquals(u1.getHistory().size(), 2);
		assertEquals(u1.getHistory().get(0), b1);
		assertEquals(u1.getHistory().get(1), b2);
		u1.checkOutBook(b1, library);
		u1.checkinBook(b1, library);
		assertEquals(u1.getHistory().size(), 3);
		assertEquals(u1.getHistory().get(0), b1);
		assertEquals(u1.getHistory().get(2), b1);
	}

	@Test
	void testGetHistoryByTitle() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book b1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Book b2 = new Book("The Founder of Diabolism", authors, genres, "103115", "summary2", "mxtx");
		Book b3 = new Book("The G", authors, genres, "7147107", "summary3", "dir");
		Book b4 = new Book("The G", authors, genres, "173465", "summary4", "dir");
		
		ArrayList<Author> differentName = new ArrayList<>();
		differentName.add(a2);
		Book b5 = new Book("The G", differentName, genres, "173465", "summary4", "dir");
		Borrower u1 = new Borrower("lix2_an_wei", "1a2B3c!");
		library.addBook(b1);
		library.addBook(b2);
		library.addBook(b3);
		library.addBook(b4);
		library.addBook(b5);
		
		u1.checkOutBook(b1, library);
		u1.checkOutBook(b2, library);
		u1.checkOutBook(b3, library);
		
		u1.checkinBook(b1, library);
		u1.checkinBook(b2, library);
		u1.checkinBook(b3, library);
		
		assertEquals(u1.getHistoryByTitle().get(0), b2);
		assertEquals(u1.getHistoryByTitle().get(1), b3);
		assertEquals(u1.getHistoryByTitle().get(2), b1);
		
		
		Borrower u2 = new Borrower(u1);
		assertEquals(u2.getHistoryByTitle().get(0), b2);
		assertEquals(u2.getHistoryByTitle().get(1), b3);
		assertEquals(u2.getHistoryByTitle().get(2), b1);
		
		
		Borrower u3 = new Borrower("User3", "1a2B3c!");
		u3.checkOutBook(b1, library);
		u3.checkOutBook(b2, library);
		u3.checkOutBook(b3, library);
		u3.checkinBook(b2, library);
		u3.checkinBook(b3, library);
		u3.checkinBook(b1, library);
		assertEquals(u3.getHistoryByTitle().get(0), b2);
		assertEquals(u3.getHistoryByTitle().get(1), b3);
		assertEquals(u3.getHistoryByTitle().get(2), b1);
		
		Borrower u4 = new Borrower("User4", "1a2B3c!");
		u4.checkOutBook(b1, library);
		u4.checkOutBook(b2, library);
		u4.checkOutBook(b3, library);
		u4.checkinBook(b1, library);
		u4.checkinBook(b3, library);
		u4.checkinBook(b2, library);
		assertEquals(u4.getHistoryByTitle().get(0), b2);
		assertEquals(u4.getHistoryByTitle().get(1), b3);
		assertEquals(u4.getHistoryByTitle().get(2), b1);
		
		Borrower u5 = new Borrower("User5", "1a2B3c!");
		u5.checkOutBook(b1, library);
		u5.checkOutBook(b2, library);
		u5.checkOutBook(b3, library);
		u5.checkinBook(b2, library);
		u5.checkinBook(b1, library);
		u5.checkinBook(b3, library);
		assertEquals(u5.getHistoryByTitle().get(0), b2);
		assertEquals(u5.getHistoryByTitle().get(1), b3);
		assertEquals(u5.getHistoryByTitle().get(2), b1);
		
		Borrower u6 = new Borrower("User6", "1a2B3c!");
		u6.checkOutBook(b1, library);
		u6.checkOutBook(b2, library);
		u6.checkOutBook(b3, library);
		u6.checkinBook(b3, library);
		u6.checkinBook(b1, library);
		u6.checkinBook(b2, library);
		assertEquals(u6.getHistoryByTitle().get(0), b2);
		assertEquals(u6.getHistoryByTitle().get(1), b3);
		assertEquals(u6.getHistoryByTitle().get(2), b1);
		
		Borrower u7 = new Borrower("User7", "1a2B3c!");
		u7.checkOutBook(b1, library);
		u7.checkOutBook(b2, library);
		u7.checkOutBook(b3, library);
		u7.checkinBook(b3, library);
		u7.checkinBook(b2, library);
		u7.checkinBook(b1, library);
		assertEquals(u7.getHistoryByTitle().get(0), b2);
		assertEquals(u7.getHistoryByTitle().get(1), b3);
		assertEquals(u7.getHistoryByTitle().get(2), b1);
		
		Borrower u8 = new Borrower("User8", "1a2B3c!");
		u8.checkOutBook(b3, library);
		u8.checkOutBook(b4, library);
		u8.checkinBook(b3, library);
		u8.checkinBook(b4, library);
		assertEquals(u8.getHistoryByTitle().get(0), b3);
		assertEquals(u8.getHistoryByTitle().get(1), b4);
		
		Borrower u9 = new Borrower("User8", "1a2B3c!");
		u9.checkOutBook(b3, library);
		u9.checkOutBook(b5, library);
		u9.checkinBook(b3, library);
		u9.checkinBook(b5, library);
		assertEquals(u9.getHistoryByTitle().get(0), b3);
		assertEquals(u9.getHistoryByTitle().get(1), b5);
		
		Borrower u10 = new Borrower("User8", "1a2B3c!");
		u10.checkOutBook(b3, library);
		u10.checkOutBook(b5, library);
		u10.checkinBook(b5, library);
		u10.checkinBook(b3, library);
		assertEquals(u10.getHistoryByTitle().get(0), b3);
		assertEquals(u10.getHistoryByTitle().get(1), b5);
	}

	@Test
	void testGetHistoryByAuthor() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		ArrayList<Author> authors2 = new ArrayList<>();
		ArrayList<Author> authors3 = new ArrayList<>();
		authors2.add(a2);
		Author a3 = new Author("noname", 0, 0);
		authors3.add(a3);
		Book b1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Book b2 = new Book("The Founder of Diabolism", authors2, genres, "103115", "summary2", "mxtx");
		Book b3 = new Book("The G", authors3, genres, "7147107", "summary3", "dir");
		Book b4 = new Book("The G", authors, genres, "173465", "summary4", "dir");
		Book b5 = new Book("The G", authors, genres, "173465", "summary4", "dir");
		Borrower u1 = new Borrower("lix2_an_wei", "1a2B3c!");
		library.addBook(b1);
		library.addBook(b2);
		library.addBook(b3);
		library.addBook(b4);
		library.addBook(b5);
		
		u1.checkOutBook(b1, library);
		u1.checkOutBook(b2, library);
		u1.checkOutBook(b3, library);
		
		u1.checkinBook(b1, library);
		u1.checkinBook(b2, library);
		u1.checkinBook(b3, library);
		
		assertEquals(u1.getHistoryByAuthor().get(0), b1);
		assertEquals(u1.getHistoryByAuthor().get(1), b3);
		assertEquals(u1.getHistoryByAuthor().get(2), b2);
		
		
		Borrower u2 = new Borrower(u1);
		assertEquals(u2.getHistoryByAuthor().get(0), b1);
		assertEquals(u2.getHistoryByAuthor().get(1), b3);
		assertEquals(u2.getHistoryByAuthor().get(2), b2);
		
		
		Borrower u3 = new Borrower("User3", "1a2B3c!");
		u3.checkOutBook(b1, library);
		u3.checkOutBook(b2, library);
		u3.checkOutBook(b3, library);
		u3.checkinBook(b2, library);
		u3.checkinBook(b3, library);
		u3.checkinBook(b1, library);
		assertEquals(u3.getHistoryByAuthor().get(0), b1);
		assertEquals(u3.getHistoryByAuthor().get(1), b3);
		assertEquals(u3.getHistoryByAuthor().get(2), b2);
		
		Borrower u4 = new Borrower("User4", "1a2B3c!");
		u4.checkOutBook(b1, library);
		u4.checkOutBook(b2, library);
		u4.checkOutBook(b3, library);
		u4.checkinBook(b1, library);
		u4.checkinBook(b3, library);
		u4.checkinBook(b2, library);
		assertEquals(u4.getHistoryByAuthor().get(0), b1);
		assertEquals(u4.getHistoryByAuthor().get(1), b3);
		assertEquals(u4.getHistoryByAuthor().get(2), b2);
		
		Borrower u5 = new Borrower("User5", "1a2B3c!");
		u5.checkOutBook(b1, library);
		u5.checkOutBook(b2, library);
		u5.checkOutBook(b3, library);
		u5.checkinBook(b2, library);
		u5.checkinBook(b1, library);
		u5.checkinBook(b3, library);
		assertEquals(u5.getHistoryByAuthor().get(0), b1);
		assertEquals(u5.getHistoryByAuthor().get(1), b3);
		assertEquals(u5.getHistoryByAuthor().get(2), b2);
		
		Borrower u6 = new Borrower("User6", "1a2B3c!");
		u6.checkOutBook(b1, library);
		u6.checkOutBook(b2, library);
		u6.checkOutBook(b3, library);
		u6.checkinBook(b3, library);
		u6.checkinBook(b1, library);
		u6.checkinBook(b2, library);
		assertEquals(u6.getHistoryByAuthor().get(0), b1);
		assertEquals(u6.getHistoryByAuthor().get(1), b3);
		assertEquals(u6.getHistoryByAuthor().get(2), b2);
		
		Borrower u7 = new Borrower("User7", "1a2B3c!");
		u7.checkOutBook(b1, library);
		u7.checkOutBook(b2, library);
		u7.checkOutBook(b3, library);
		u7.checkinBook(b3, library);
		u7.checkinBook(b2, library);
		u7.checkinBook(b1, library);
		assertEquals(u7.getHistoryByAuthor().get(0), b1);
		assertEquals(u7.getHistoryByAuthor().get(1), b3);
		assertEquals(u7.getHistoryByAuthor().get(2), b2);
		
		Borrower u8 = new Borrower("User8", "1a2B3c!");
		u8.checkOutBook(b1, library);
		u8.checkOutBook(b4, library);
		u8.checkinBook(b1, library);
		u8.checkinBook(b4, library);
		assertEquals(u8.getHistoryByAuthor().get(0), b4);
		assertEquals(u8.getHistoryByAuthor().get(1), b1);
		
		Borrower u9 = new Borrower("User8", "1a2B3c!");
		u9.checkOutBook(b1, library);
		u9.checkOutBook(b4, library);
		u9.checkinBook(b4, library);
		u9.checkinBook(b1, library);
		assertEquals(u9.getHistoryByAuthor().get(0), b4);
		assertEquals(u9.getHistoryByAuthor().get(1), b1);
		
		Borrower u10 = new Borrower("User8", "1a2B3c!");
		u10.checkOutBook(b4, library);
		u10.checkOutBook(b5, library);
		u10.checkinBook(b4, library);
		u10.checkinBook(b5, library);
		assertEquals(u10.getHistoryByAuthor().get(0), b4);
		assertEquals(u10.getHistoryByAuthor().get(1), b5);
		
		Borrower u11 = new Borrower("User8", "1a2B3c!");
		u11.checkOutBook(b4, library);
		u11.checkOutBook(b5, library);
		u11.checkinBook(b5, library);
		u11.checkinBook(b4, library);
		assertEquals(u11.getHistoryByAuthor().get(0), b4);
		assertEquals(u11.getHistoryByAuthor().get(1), b5);
	}

	@Test
	void testCheckedOut() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book b1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower u1 = new Borrower("lix2_an_wei", "1a2B3c!");
		library.addBook(b1);
		assertEquals(u1.checkedOut().size(), 0);
		u1.checkOutBook(b1, library);
		assertEquals(u1.checkedOut().get(0), b1);
		u1.checkinBook(b1, library);
		assertEquals(u1.checkedOut().size(), 0);
	}
	
	@Test
	void testCheckOutHold() throws NoSuchAlgorithmException {
		authors.add(a1);
		genres.add("Fantasy");
		Book b1 = new Book("The Lord of the Rings", authors, genres, "12345", "summary", "path");
		Borrower u1 = new Borrower("lix2_an_wei", "1a2B3c!");
		library.addBook(b1);
		u1.putBookOnHold(b1, library);
		u1.checkOutHold(b1);
		assertFalse(u1.onHold().contains(b1));
		assertTrue(u1.checkedOut().contains(b1));
	}

}
