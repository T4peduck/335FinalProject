package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import model.*;

public class DataControllerTest {
	
	@Test
	void testLoadAndSave() {
		Library lib = new Library();
		ArrayList<Author> authors = new ArrayList<Author>();
		authors.add(new Author("Stephen King", 1, 1));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "IDK"));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "IDK"));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "IDK"));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "IDK"));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "IDK"));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "IDK"));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "IDK"));
		
		DataController.saveBookData(lib);
		
		Library lib2 = new Library();
		DataController.loadBookData(lib2);
		
		ArrayList<Book> bList = lib2.getAllBooksByTitle();
		
		assertEquals(bList.size(), 7);
	}
}
