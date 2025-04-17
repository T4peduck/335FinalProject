package model;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class Library {
	private HashMap<String, Borrower> borrowerList;
	private HashMap<String, Librarian> librarianList;

	private HashMap<String, ArrayList<Book>> availableBooks;
	private HashMap<String, ArrayList<Book>> unavailableBooks;

	private HashMap<String, ArrayList<Book>> booksByAuthor;

	// keep track of how often a book is checked out?
	private HashMap<Book, int> checkoutNums;

	public Library() {
		borrowerList = new HashMap<>();
		librarianList = new HashMap<>();

		availableBooks = new HashMap<>();
		unavailableBooks = new HashMap<>();
		booksByAuthor = new HashMap<>();
	}

	// TODO: add a copy for Users? or construct users HERE instead of outside of
	// this class
	public void addLibrarian(Librarian l) {
		librarianList.put(l.getUserName(), new Librarian(l));
	}

	public void addBorrower(Borrower b) {
		borrowerList.put(b.getUserName(), new Borrower(b));
	}

	// also take strings, or a Book?

	// TODO: possibly should also be package private, since they are Borrower
	// specific
	public void checkout(Borrower b) {

	}

	public void hold(Book b) {

	}

	public void checkin(Book b) {

	}

	/*
	 * searchAllBooksByTitle(String title) - returns an arrayList of copies of all
	 * books available or unavailable, with the given title (case-insensitive)
	 */
	public ArrayList<Book> searchAllBooksByTitle(String title) {
		// normalize title
		title = title.toLowerCase().trim();

		ArrayList<Book> found = new ArrayList<>();
		// copy available books with this title
		ArrayList<Book> avail = availableBooks.get(title);
		if (avail != null) {
			found.addAll(avail);

		}
		// copy unavailable books with this title
		ArrayList<Book> unavail = availableBooks.get(title);
		if (unavail != null) {
			found.addAll(unavail);

		}
		return found;
	}

	/*
	 * searchAvailBooksByTitle(String title) - returns an arrayList with a copy of
	 * all available books with the given title (case-insensitive)
	 */
	public ArrayList<Book> searchAvailBooksByTitle(String title) {
		// normalize title
		title = title.toLowerCase().trim();

		ArrayList<Book> found = new ArrayList<>();
		// copy available books with this title
		ArrayList<Book> avail = availableBooks.get(title);
		if (avail != null) {
			found.addAll(avail);
		}
		return found;
	}

	/*
	 * searchAllBooksByAuthor(String author) - returns an arrayList with a copy of
	 * all books (available or unavailable) with the given author (case-insensitive)
	 */
	public ArrayList<Book> searchAllBooksByAuthor(String author) {
		// normalize author
		author = author.toLowerCase().trim();

		ArrayList<Book> found = new ArrayList<>();
		// copy available books with this title
		ArrayList<Book> books = booksByAuthor.get(author);
		if (books != null) { {
				found.addAll(books);
			
		}
		return found;
	}

	/*
	 * searchAvaillBooksByAuthor(String author) - returns an arrayList with a copy
	 * of all available books with the given author (case-insensitive)
	 */
	public ArrayList<Book> searchAvailBooksByAuthor(String author) {
		// normalize author
		author = author.toLowerCase().trim();

		ArrayList<Book> found = new ArrayList<>();
		// copy available books with this title
		ArrayList<Book> books = booksByAuthor.get(author);
		if (books != null) {
			found.addAll(books);

		}
		return found;
	}

	/*
	 * getAllBooksByAuthor() - returns a sorted ArrayList of all books in the
	 * library sorted by author first, then title (full of copies)
	 */
	public ArrayList<Book> getAllBooksByAuthor() {
		ArrayList<Book> sorted = getAllBooks();
		Collections.sort(sorted, Book.authorFirstComparator());
		return sorted;
	}

	/*
	 * getAllBooksByTitle() - returns a sorted ArrayList of all books in the library
	 * sorted by title first, then author (full of copies)
	 */
	public ArrayList<Book> getAllBooksByTitle() {
		ArrayList<Book> sorted = getAllBooks();
		Collections.sort(sorted, Book.titleFirstComparator());
		return sorted;
	}

	/*
	 * getAllBooks() - returns an ArrayList of copies of all books in the library
	 */
	private ArrayList<Book> getAllBooks() {
		ArrayList<Book> books = new ArrayList<>();
		for (ArrayList<Book> list : booksByAuthor.values()) {

			listCopy.addAll(list);

		}
		return books;
	}

	// TODO: will we be keeping track of callNumber?
	public ArrayList<Book> searchAllBooksByCallNumber(String callNumber) {
		return new ArrayList<Book>();
	}

	public ArrayList<Book> searchAvailBooksByCallNumber(String callNumber) {
		return new ArrayList<Book>();
	}

	/*
	 * package private methods for the other classes in the model to use
	 */
	ArrayList<Borrower> getBorrowerList() {
		return new ArrayList<Borrower>(borrowerList.values());
	}


	// Librarian deals with finding/creating Book object
	void addBook(Book b) {
		String title = b.getTitle().toLowerCase();
		String author = b.getAuthor().toLowerCase();

		ArrayList<Book> booksWithTitle = availableBooks.get(title);
		if (booksWithTitle == null) {
			ArrayList<Book> list = new ArrayList<>();
			list.add(b);
			availableBooks.put(title, list);
		} else {
			booksWithTitle.add(b);
		}

		ArrayList<Book> booksWithAuthor = booksByAuthor.get(author);
		if (booksWithAuthor == null) {
			ArrayList<Book> list = new ArrayList<>();
			list.add(b);
			booksByAuthor.put(title, list);
		} else {
			booksWithAuthor.add(b);
		}

	}

	void removeBook(Book b) {
		String title = b.getTitle().toLowerCase();
		String author = b.getAuthor().toLowerCase();

		// remove from availbooks
		ArrayList<Book> booksWithTitle = availableBooks.get(title);
		if (booksWithTitle != null) {
			for (Book book : booksWithTitle) {
				if (b.equals(book)) {
					booksWithTitle.remove(book);
					break;
				}
			}
			// remove totally if no more books with this title
			if (booksWithTitle.size() == 0) {
				availableBooks.remove(title);
			}
		}

		// remove from unavailbooks
		booksWithTitle = unavailableBooks.get(title);
		if (booksWithTitle != null) {
			// remove from unavailbooks
			for (Book book : booksWithTitle) {
				if (b.equals(book)) {
					booksWithTitle.remove(book);
					break;
				}
			}
			// remove totally if no more books with this title
			if (booksWithTitle.size() == 0) {
				unavailableBooks.remove(title);
			}
		}

		// remove from authorlist
		ArrayList<Book> booksWithAuthor = booksByAuthor.get(author);
		if (booksWithAuthor != null) {
			for (Book book : booksWithAuthor) {
				if (b.equals(book)) {
					booksWithAuthor.remove(book);
					break;
				}
			}
			// remove totally if no more books with this author
			if (booksWithAuthor.size() == 0) {
				booksByAuthor.remove(author);
			}
		}
	}

	ArrayList<Book> getAvailBooks() {
		ArrayList<Book> books = new ArrayList<>();
		for (ArrayList<Book> list : availableBooks.values()) {
			books.addAll(list);
		}
		return books;
	}

	ArrayList<Book> getUnavailBooks() {
		ArrayList<Book> books = new ArrayList<>();
		for (ArrayList<Book> list : unavailableBooks.values()) {
			books.addAll(list);
		}
		return books;
	}
}
