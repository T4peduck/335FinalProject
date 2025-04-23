/*
 * Used for testing API and parsing of ParseBook.java
 */
package test;

import org.json.simple.JSONObject;
import org.junit.Test;

import model.Author;
import model.Book;
import model.ParseBook;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.json.simple.JSONArray;

class TestParseBook {
	private ArrayList<Book> frank = new ArrayList<>();
	
	try {
		ParseBook.addAuthorAndTitle("Mary", "Shelley", "Frankenstein");
		frank = ParseBook.downloadBooks();
	} catch (Exception e) {
		System.exit(1);
	}
	
	@Test
	void testSetters() {
		Author a = new Author("Mary Shelley", 1797, 1851);
		assertFalse(frank == null);
		assertTrue(frank.get(0).authors.get(0).equals(a)));
	}
}
