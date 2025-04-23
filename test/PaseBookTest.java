package test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import model.Author;
import model.Book;
import model.ParseBook;

class PaseBookTest {
	
	@Test
	void testSetters() {
		ArrayList<Book> frank = null;
		ArrayList<Book> empty = null;
		ArrayList<Book> idSearch = null;
		ArrayList<Book> topicSearch = null;
		
		Author f = new Author("Shelley, Mary Wollstonecraft", 1797, 1851);
		try {
			//ParseBook.addAuthorAndTitle("Mary", "Shelley", "Frankenstein");
			//frank = ParseBook.downloadBooks();
			
			//ParseBook.addAuthorAndTitle("ADFAHSJ", "asdfjhaksdfh", "askjdfhaksdhfkla");
			//empty = ParseBook.downloadBooks();
			
			ParseBook.addId("84");
			idSearch = ParseBook.downloadBooks();
			
			ParseBook.addTopic("Science Fiction");
			ParseBook.addAuthorAndTitle("Mary", "", "");
			topicSearch = ParseBook.downloadBooks();
			
			
		} catch (Exception e) {
			System.exit(1);
		}
		
		System.out.println(frank.get(0).authors.get(0));
		System.out.println(f);
		assertTrue(frank != null);
		assertTrue(frank.get(0).authors.get(0).equals(f));
		
		assertTrue(empty == null);
		
		assertTrue(idSearch.get(0).authors.get(0).equals(f));
		
		assertTrue(topicSearch.get(0).authors.get(0).equals(f));
	}

}
