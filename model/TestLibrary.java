package model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestLibrary {
	private Library l = new Library();
	private Book mobyDick = null;
	private Book warAndPeace = null;
	private Book anna = null;
	private Book ivan = null;
	
	@BeforeEach
	void setup() {
		Author melville = new Author("Herman Melville", 0, 0);
		ArrayList<Author> authors = new ArrayList<>();
		authors.add(melville);
		mobyDick = new Book("Moby Dick", authors, "", "", "");
		
		Author tolstoy = new Author("Leo Tolstoy", 0, 0);
		authors = new ArrayList<>();
		authors.add(tolstoy);
		warAndPeace = new Book("War and Peace", authors, "", "", "");
		anna = new Book("Anna Karenina", authors, "", "", "");
		ivan = new Book("The Death of Ivan Ilyich", authors, "", "", "");
		
		l.addBook(mobyDick);
		l.addBook(warAndPeace);
		l.addBook(anna);
		l.addBook(ivan);
	}
	
	
	@Test
	void test() {
		String output = "";
		String expected = "Anna Karenina\n"
				+ "Moby Dick\n"
				+ "The Death of Ivan Ilyich\n"
				+ "War and Peace\n";
		ArrayList<Book> books = l.getAllBooksByTitle();
		for (Book b: books) {
			output += b.title + "\n";
		}
		assertEquals(expected, output);
		
		books = l.getAllBooksByAuthor();
		output = "";
		expected ="Moby Dick\n"
				+ "Anna Karenina\n"
				+ "The Death of Ivan Ilyich\n"
				+ "War and Peace\n";
		for (Book b: books) {
			output += b.title + "\n";
		}
		assertEquals(expected, output);
	}
	
	@Test
	void testGetters() {
		assertEquals(3, l.searchAllBooksByAuthor("leo tolstoy").size());
		assertEquals(1, l.searchAllBooksByAuthor("HERMAN MELVILLE").size());
		assertEquals(0, l.searchAllBooksByAuthor("na").size());
		
		assertEquals(4, l.getAvailBooksByTitle().size());
		assertEquals(0, l.getUnavailBooksByTitle().size());
	}
	
	@Test
	void testCheckout() {
		l.checkout(anna);
		
		assertEquals(3, l.getAvailBooksByTitle().size());
		assertEquals(1, l.getUnavailBooksByTitle().size());
		
		assertEquals(anna, l.getUnavailBooksByTitle().get(0));
		assertEquals(0, l.searchAvailBooksByTitle("Anna Karenina").size());
	}
	
	@Test
	void testCheckin() {
		l.checkout(anna);
		l.checkin(anna);
		
		assertEquals(1, l.searchAvailBooksByTitle("Anna Karenina").size());
		assertEquals(4, l.getAvailBooksByTitle().size());
		assertEquals(0, l.getUnavailBooksByTitle().size());
	}
	
	@Test
	void testRemove() {
		
	}
	
	@Test
	void test_getMostPopular() {
		l.checkout(anna);
		l.checkin(anna);
		
		assertEquals(1, l.getMostPopular().size());
		
		l.checkout(anna);
		l.checkout(mobyDick);
		l.checkin(mobyDick);
		l.checkout(mobyDick);
		l.checkout(ivan);
		assertEquals(3, l.getMostPopular().size());
		
		l.removeBook(mobyDick);
		
		assertEquals(0, l.searchAllBooksByTitle("MOBY DICK").size());
		assertEquals(0, l.searchAvailBooksByAuthor("Herman melville").size());
		
		l.checkin(ivan);
		
		l.removeBook(ivan);
		
		assertEquals(0, l.searchAllBooksByTitle("the death of ivan ilyitch").size());
		assertEquals(1, l.searchAvailBooksByAuthor("leo tolstoy").size());
		
		assertEquals(1, l.getMostPopular().size());

	}
	
	@Test
	void test_sorts() {
		
	}

}
