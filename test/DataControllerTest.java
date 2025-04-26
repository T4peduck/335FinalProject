package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import model.*;

public class DataControllerTest {
	
	@Test
	void testLoadAndSave() {
		Library lib = new Library();
		ArrayList<Author> authors = new ArrayList<Author>();
		authors.add(new Author("Stephen King", 1, 1));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "model/LibraryText/IDK.txt"));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "model/LibraryText/IDK.txt"));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "model/LibraryText/IDK.txt"));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "model/LibraryText/IDK.txt"));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "model/LibraryText/IDK.txt"));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "model/LibraryText/IDK.txt"));
		lib.addBook(new Book("It", authors, "IDK", "IDK", "model/LibraryText/IDK.txt"));
		
		DataController.saveBookData(lib);
		
		Library lib2 = new Library();
		DataController.loadBookData(lib2);
		
		ArrayList<Book> bList = lib2.getAllBooksByTitle();
		
		assertEquals(bList.size(), 7);
		
		try {
			Librarian l = new Librarian("", "", new ArrayList<Borrower>());
			l.removeBook(new Book("It", authors, "IDK", "IDK", "model/LibraryText/IDK.txt"), lib);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		DataController.saveBookData(lib);
	}
	
	@Test
	void testDeleteBook() {
		ArrayList<Author> authors = new ArrayList<Author>();
		File p = new File("model/LibraryText/IDK.txt");
		try {
			FileWriter fw = new FileWriter(p);
			fw.write("hi\n");
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Book b = new Book("It", authors, "IDK", "IDK", "model/LibraryText/IDK.txt");
		assertTrue(p.exists());
		
		DataController.removeBook(b);
		
		File f = new File("model/LibraryText/IDK.txt");
		assertFalse(f.exists());
		
		
		
	}
}
