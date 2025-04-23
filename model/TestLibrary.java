package model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class TestLibrary {
	Library l = new Library();

	@Test
	void test() {
		Author melville = new Author("Herman Melville", 0, 0);
		ArrayList<Author> authors = new ArrayList<>();
		authors.add(melville);
		Book mobyDick = new Book("Moby Dick", authors, "", "", "");
		
		Author tolstoy = new Author("Leo Tolstoy", 0, 0);
		authors = new ArrayList<>();
		authors.add(tolstoy);
		Book warAndPeace = new Book("War and Peace", authors, "", "", "");
		Book anna = new Book("Anna Karenina", authors, "", "", "");
		Book ivan = new Book("The Death of Ivan Ilyich", authors, "", "", "");
		
		l.addBook(mobyDick);
		l.addBook(warAndPeace);
		l.addBook(anna);
		l.addBook(ivan);
		
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
		
		assertEquals(3, l.searchAllBooksByAuthor("leo tolstoy").size());
		assertEquals(1, l.searchAllBooksByAuthor("HERMAN MELVILLE").size());
		assertEquals(0, l.searchAllBooksByAuthor("na").size());
		
		assertEquals(4, l.getAvailBooks().size());
		assertEquals(0, l.getUnavailBooks().size());
		
		l.checkout(anna);
		
		assertEquals(3, l.getAvailBooks().size());
		assertEquals(1, l.getUnavailBooks().size());
		
		assertEquals(anna, l.getUnavailBooks().get(0));
		assertEquals(0, l.searchAvailBooksByTitle("Anna Karenina").size());
		
		l.checkin(anna);
		
		assertEquals(4, l.getAvailBooks().size());
		assertEquals(0, l.getUnavailBooks().size());
	}

}
