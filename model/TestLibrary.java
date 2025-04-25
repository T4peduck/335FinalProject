package model;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestLibrary {
	private Book mobyDick = null;
	private Book warAndPeace = null;
	private Book anna = null;
	private Book ivan = null;
	private Book GoT, CoK, SoW, FfC, DwD;
	private Book dummy, sameName;
	private Library l = null;
	
	@BeforeEach
	void setup() {
		l = new Library();
		Author melville = new Author("Herman Melville", 0, 0);
		ArrayList<Author> authors = new ArrayList<>();
		
		authors.add(melville);
		mobyDick = new Book("Moby Dick", authors, "", "", "");
		
		authors = new ArrayList<>();
		authors.add(new Author("dummy", 0, 0));
		
		dummy = new Book("", authors, "", "", "");
		sameName = new Book("Moby Dick", authors, "", "", "");
		
		Author tolstoy = new Author("Leo Tolstoy", 0, 0);
		authors = new ArrayList<>();
		authors.add(tolstoy);
		warAndPeace = new Book("War and Peace", authors, "", "", "");
		anna = new Book("Anna Karenina", authors, "", "", "");
		ivan = new Book("The Death of Ivan Ilyich", authors, "", "", "");
		
		Author grrm = new Author("George R.R. Martin", 0, 0);
		authors = new ArrayList<>();
		authors.add(grrm);
		
		GoT = new Book("A Game of Thrones", authors, "", "", "");
		CoK = new Book("A Clash of Kings", authors, "", "", "");
		SoW = new Book("A Storm of Swords", authors, "", "", "");
		FfC = new Book("A Feast for Crows", authors, "", "", "");
		DwD = new Book("A Dance with Dragons", authors, "", "", "");
		
		l.addBook(mobyDick);
		l.addBook(warAndPeace);
		l.addBook(anna);
		l.addBook(ivan);
		
		l.addBook(GoT);
		l.addBook(CoK);
		l.addBook(SoW);
		l.addBook(FfC);
		l.addBook(DwD);
	}
	
	
	@Test
	void test() {
		String output = "";
		String expected = "A Clash of Kings\n"
						+ "A Dance with Dragons\n"
						+ "A Feast for Crows\n"
						+ "A Game of Thrones\n"
						+ "A Storm of Swords\n"
						+ "Anna Karenina\n"
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
		expected =
				 "A Clash of Kings\n"
				+ "A Dance with Dragons\n"
				+ "A Feast for Crows\n"
				+ "A Game of Thrones\n"
				+ "A Storm of Swords\n"
				+ "Moby Dick\n"
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
		String expected, output;
		assertEquals(3, l.searchAllBooksByAuthor("leo tolstoy").size());
		
		output = "";
		expected ="Anna Karenina\n"
				+ "The Death of Ivan Ilyich\n"
				+ "War and Peace\n";
		for (Book b: l.searchAllBooksByAuthor("leo tolstoy")) {
			output += b.title + "\n";
		}
		assertEquals(expected, output);
		output = "";
		expected ="Moby Dick by Herman Melville\n";
		for (Book b: l.searchAllBooksByAuthor("HERMAN MELVILLE")) {
			output += b.toString() + "\n";
		}
		assertEquals(expected, output);
		
		output = "";
		expected ="A Clash of Kings by George R.R. Martin\n"
				+ "A Dance with Dragons by George R.R. Martin\n"
				+ "A Feast for Crows by George R.R. Martin\n"
				+ "A Game of Thrones by George R.R. Martin\n"
				+ "A Storm of Swords by George R.R. Martin\n";
		for (Book b: l.searchAllBooksByAuthor("GeORGE R.R. martin")) {
			output += b.toString() + "\n";
		}
		assertEquals(expected, output);
		
		assertEquals(0, l.searchAllBooksByAuthor("na").size());
		
		output = "";
		expected ="A Clash of Kings\n"
				+ "A Dance with Dragons\n"
				+ "A Feast for Crows\n"
				+ "A Game of Thrones\n"
				+ "A Storm of Swords\n"
				+ "Anna Karenina\n"
				+ "Moby Dick\n"
				+ "The Death of Ivan Ilyich\n"
				+ "War and Peace\n";
		for (Book b: l.getAllBooksByTitle()) {
			output += b.title + "\n";
		}
		assertEquals(expected, output);
		
		output = "";
		for (Book b: l.getAvailBooksByTitle()) {
			output += b.title + "\n";
		}
		assertEquals(expected, output);
		
		assertEquals(0, l.getUnavailBooksByTitle().size());
	}
	
	@Test
	void testCheckout() {
		l.checkout(anna);
		
		assertEquals(0, l.checkout(dummy));
		assertEquals(0, l.checkout(sameName));
		
		assertEquals(8, l.getAvailBooksByTitle().size());
		assertEquals(1, l.getUnavailBooksByTitle().size());
		assertEquals(anna, l.getUnavailBooksByTitle().get(0));
		
		assertEquals(0, l.searchAvailBooksByTitle("Anna Karenina").size());
		
		l.checkout(mobyDick);
		l.checkout(SoW);
	
		String expected =
				 "A Storm of Swords\n"
				+ "Anna Karenina\n"
				+ "Moby Dick\n";
		
		String output = "";
		for (Book b: l.getUnavailBooksByTitle()) {
			output += b.title + "\n";
		}
		assertEquals(expected, output);
		
		expected =	"A Clash of Kings\n"
				+ "A Dance with Dragons\n"
				+ "A Feast for Crows\n"
				+ "A Game of Thrones\n"
				+ "The Death of Ivan Ilyich\n"
				+ "War and Peace\n";
		
		output = "";
		for (Book b: l.getAvailBooksByAuthor()) {
			output += b.title + "\n";
		}
		assertEquals(expected, output);
		
		expected =	"A Storm of Swords\n"
				 + "Moby Dick\n"
				 + "Anna Karenina\n";

		output = "";
		for (Book b: l.getUnavailBooksByAuthor()) {
			output += b.title + "\n";
		}
		assertEquals(expected, output);
		
		l.checkout(GoT);
		expected =	"A Game of Thrones by George R.R. Martin\n"
				+"A Storm of Swords by George R.R. Martin\n";

		output = "";
		for (Book b: l.searchUnavailBooksByAuthor("GEORGE r.r. mARTIN")) {
			output += b.toString() + "\n";
		}
		assertEquals(expected, output);
		
		expected =	"A Game of Thrones by George R.R. Martin\n";

		output = "";
		for (Book b: l.searchUnavailBooksByTitle("a game OF THRONES")) {
			output += b.toString() + "\n";
		}
		assertEquals(expected, output);
		
		// author not in library
		assertEquals(0, l.searchUnavailBooksByAuthor("NA").size());
		
		// book is in library, but is available
		assertEquals(0, l.searchUnavailBooksByTitle("a feast for crows").size());
		
		// book is not in library
		assertEquals(0, l.searchUnavailBooksByTitle("NA").size());
		
		l.checkin(anna);
		
		// author is in library, but all books are available
		assertEquals(0, l.searchUnavailBooksByAuthor("LEO TOLSTOY").size());
	}
	
	@Test
	void testCheckin() {
		l.checkout(anna);
		l.checkin(anna);
		
		assertEquals(1, l.searchAvailBooksByTitle("Anna Karenina").size());
		assertEquals(9, l.getAvailBooksByTitle().size());
		assertEquals(0, l.getUnavailBooksByTitle().size());
	}
	
	@Test
	void test_getMostPopular() {
		l.checkout(anna);
		l.checkin(anna);
		
		assertEquals(1, l.getMostPopular().size());
		
		l.checkout(anna); // checkout 2x
		l.checkout(mobyDick);
		l.checkin(mobyDick);
		l.checkout(mobyDick); // checkout 2x
		l.checkout(ivan); // checkout 1x
		l.checkout(warAndPeace);
		l.checkin(warAndPeace);
		l.checkout(warAndPeace); // checkout 2x
		
		assertEquals(4, l.getMostPopular().size());
		
		String expected = "Anna Karenina by Leo Tolstoy\n"
				+ "Moby Dick by Herman Melville\n"
				+ "War and Peace by Leo Tolstoy\n"
				+ "The Death of Ivan Ilyich by Leo Tolstoy\n";
		
		String output = "";
		
		for (Book b: l.getMostPopular()) {
			output += b.toString() + "\n";
		}
		
		assertEquals(expected, output );
		
		l.removeBook(mobyDick);
		
		assertEquals(0, l.searchAllBooksByTitle("MOBY DICK").size());
		assertEquals(0, l.searchAvailBooksByAuthor("Herman melville").size());
		
		l.checkin(ivan);
		
		l.removeBook(ivan);
		
		l.checkin(warAndPeace);
		
		assertEquals(0, l.searchAllBooksByTitle("the death of ivan ilyitch").size());
		assertEquals(1, l.searchAvailBooksByAuthor("leo tolstoy").size());
		
		assertEquals(2, l.getMostPopular().size());

	}
	
	@Test
	void testHolds() {
		
	}
	
	@Test
	void testRecs() {
		
	}
	
	@Test
	void testRemove() {
		
	}

}
