package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.Author;
import model.Book;
import model.Library;

class BorrowerTest {
	
	Library library = new Library();
	Author author = new Author("Arthur", 2030, 1999);
	
	@Test
	void testCheckOutBook() {
		// checkOutBook(Book b, Library l)
		
		fail("Not yet implemented");
	}

	@Test
	void testCheckinBook() {
		fail("Not yet implemented");
	}

	@Test
	void testPutBookOnHold() {
		fail("Not yet implemented");
	}

	@Test
	void testGetHistory() {
		fail("Not yet implemented");
	}

	@Test
	void testGetHistoryByTitle() {
		fail("Not yet implemented");
	}

	@Test
	void testGetHistoryByAuthor() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckedOut() {
		fail("Not yet implemented");
	}

	@Test
	void testOnHold() {
		fail("Not yet implemented");
	}

	@Test
	void testHasBook() {
		fail("Not yet implemented");
	}

	@Test
	void testCheckOutHold() {
		fail("Not yet implemented");
	}

}
