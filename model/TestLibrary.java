package model;

import static org.junit.jupiter.api.Assertions.*;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestLibrary {
	private Book mobyDick = null;
	private Book warAndPeace = null;
	private Book anna = null;
	private Book ivan = null;
	private Book GoT, CoK, SoW, FfC, DwD;
	private Book dummy, sameName;
	private Library l = null;
	private Borrower b1, b2, b3, b4;
	
	@BeforeEach
	void setup() {
		l = new Library();
		Author melville = new Author("Herman Melville", 0, 0);
		ArrayList<Author> authors = new ArrayList<>();
		
		authors.add(melville);
		mobyDick = new Book("Moby Dick", authors, "35624", "", "");
		
		authors = new ArrayList<>();
		authors.add(new Author("dummy", 0, 0));
		
		dummy = new Book("", authors, "", "", "");
		sameName = new Book("Moby Dick", authors, "", "", "");
		
		Author tolstoy = new Author("Leo Tolstoy", 0, 0);
		authors = new ArrayList<>();
		authors.add(tolstoy);
		warAndPeace = new Book("War and Peace", authors, "123523", "", "");
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
		
		try {
			b1 = new Borrower("user1", "pass");
			b2 = new Borrower("user2", "pass");
			b3 = new Borrower("user3", "pass");
			b4 = new Borrower("user4", "pass");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		l.checkout(ivan);// checkout 1x
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
		
		l.checkin(mobyDick);
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
		l.checkout(anna);
		
		l.hold(anna, b1);
		l.hold(anna, b2);
		l.hold(anna, b3);
		l.hold(anna, b4);
		
		assertEquals(1, l.getHoldPosition(anna, b1));
		assertEquals(2, l.getHoldPosition(anna, b2));
		assertEquals(3, l.getHoldPosition(anna, b3));
		assertEquals(4, l.getHoldPosition(anna, b4));
		
		assertEquals(4, l.getNumHolds(anna));
		
		l.checkin(anna);
		assertEquals(1, l.getHoldPosition(anna, b2));
		assertEquals(2, l.getHoldPosition(anna, b3));
		assertEquals(3, l.getHoldPosition(anna, b4));
		
		assertEquals(1, l.searchUnavailBooksByTitle("Anna karenina").size());
		
		assertEquals(3, l.getNumHolds(anna));
		
		l.checkin(anna);
		l.checkin(anna);
		l.checkin(anna);
	}
	
	@Test
	void testRecs() {
		l.recommend("Marissa", CoK);
		l.recommend("Marissa", GoT);
		l.recommend("Marissa", FfC);
		l.recommend("Marissa", DwD);
		l.recommend("Marissa", SoW);
		
		String output, expected;
		output = "";
		expected =
				 "A Clash of Kings\n"
				+ "A Dance with Dragons\n"
				+ "A Feast for Crows\n"
				+ "A Game of Thrones\n"
				+ "A Storm of Swords\n";
		for (Book b: l.getRecommendationsByLibrarian("Marissa")) {
			output += b.title + "\n";
		}
		
		assertEquals(expected, output);
		
		
		assertEquals(0, l.getRecommendationsByLibrarian("").size());
		
		l.recommend("someone", anna);
		
		output = "";
		expected =
				 "A Clash of Kings\n"
				+ "A Dance with Dragons\n"
				+ "A Feast for Crows\n"
				+ "A Game of Thrones\n"
				+ "A Storm of Swords\n"
				+ "Anna Karenina\n";
		for (Book b: l.getRecBooksByTitle()) {
			output += b.title + "\n";
		}
		
		assertEquals(expected, output);
		
		l.recommend("someone", mobyDick);
		l.removeRecommend("Marissa", CoK);
		l.removeRecommend(" ", CoK);
		l.removeLibraryRec(" ");
		
		l.recommend("blank", CoK);
		l.removeRecommend("blank", CoK);
		
		output = "";
		for (Book b: l.getRecBooksByAuthor()) {
			output += b.title + "\n";
		}
		
		expected = "A Dance with Dragons\n"
				+ "A Feast for Crows\n"
				+ "A Game of Thrones\n"
				+ "A Storm of Swords\n"
				+ "Moby Dick\n"
				+ "Anna Karenina\n";
		
		assertEquals(expected, output);
		
		l.removeLibraryRec("someone");
		
		l.recommend("someone else", DwD);
		output = "";
		for (Book b: l.getRecBooksByAuthor()) {
			output += b.title + "\n";
		}
		
		expected = "A Dance with Dragons\n"
				+ "A Feast for Crows\n"
				+ "A Game of Thrones\n"
				+ "A Storm of Swords\n";
		
		assertEquals(expected, output);
	}
	
	@Test
	void testRemove() {
		l.recommend("Marissa", CoK);
		l.recommend("Marissa", GoT);
		l.recommend("Marissa", FfC);
		l.recommend("Marissa", DwD);
		l.recommend("Marissa", SoW);
		
		l.checkout(CoK);
		l.checkin(CoK);
		
		l.checkout(CoK); // checked out twice
		l.checkin(CoK);
		
		l.checkout(GoT); // checked out once
		
		l.checkout(anna);
		l.checkin(anna);
		l.checkout(anna); // checked out twice
		
		l.removeBook(CoK);
	
		// check it's removed from recommendations
		String output, expected;
		output = "";
		for (Book b: l.getRecBooksByAuthor()) {
			output += b.title + "\n";
		}
		
		expected = "A Dance with Dragons\n"
				+ "A Feast for Crows\n"
				+ "A Game of Thrones\n"
				+ "A Storm of Swords\n";
		
		assertEquals(expected, output);
		
		// check it's removed from all lists
		output = "";
		for (Book b: l.searchAllBooksByAuthor("george r.r. martin")) {
			output += b.title + "\n";
		}
		
		expected = "A Dance with Dragons\n"
				+ "A Feast for Crows\n"
				+ "A Game of Thrones\n"
				+ "A Storm of Swords\n";
		
		assertEquals(expected, output);
		
		// check it's removed from popularity
		output = "";
		for (Book b: l.getMostPopular()) {
			output += b.title + "\n";
		}
		
		expected  = "Anna Karenina\n"
					+ "A Game of Thrones\n";
		assertEquals(expected, output);
		
		// check its removed from avail books
		assertEquals(0, l.searchAvailBooksByTitle("A CLASH OF KINGS").size());
		assertEquals(0, l.searchUnavailBooksByTitle("A CLASH OF KINGS").size());
	}
	
	@Test
	void testCheckoutNums() {
		l.checkout(CoK);
		l.checkin(CoK);
		l.checkout(CoK);
		
		l.checkout(anna);
		
		for (int i = 0; i < 13; i++) {
			l.checkout(GoT);
			l.checkin(GoT);
		}
		
		assertEquals(2, l.getCheckoutNum(CoK));
		assertEquals(1, l.getCheckoutNum(anna));
		assertEquals(13, l.getCheckoutNum(GoT));
		
		HashMap<Book, Integer> checkouts = l.getCheckoutNums();
		
		assertEquals(checkouts.entrySet().size(), 9);
		
		assertEquals(2, checkouts.get(CoK));
		assertEquals(13, checkouts.get(GoT));
		assertEquals(0, checkouts.get(FfC));
		assertEquals(0, checkouts.get(SoW));
		assertEquals(0, checkouts.get(DwD));
		assertEquals(1, checkouts.get(anna));
		assertEquals(0, checkouts.get(ivan));
		assertEquals(0, checkouts.get(warAndPeace));
		assertEquals(0, checkouts.get(mobyDick));
	}
	
	@Test
	
	void testSearchByID() {
		l.checkout(warAndPeace); // id: 123523
		l.checkout(mobyDick); // id: 35624
	
		assertEquals(mobyDick, l.searchAllBookByID("35624"));
		assertEquals(warAndPeace, l.searchAllBookByID("123523"));
		
		assertEquals(null,l.searchAvailBookByID("35624") );
		
		assertEquals(mobyDick, l.searchUnavailBookByID("35624"));
		assertEquals(warAndPeace, l.searchUnavailBookByID("123523"));
		
		l.checkin(mobyDick);
		assertEquals(mobyDick, l.searchAvailBookByID("35624"));
	}

}
