package model;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Collections;

public class Library {

	private HashMap<String, ArrayList<Book>> availableBooks;
	private HashMap<String, ArrayList<Book>> unavailableBooks;

	private HashMap<String, ArrayList<Book>> booksByAuthor;
	private HashMap<Book, ArrayList<Borrower>> holds;

	// keep track of how often a book is checked out?
	private HashMap<Book, Integer> checkoutNums;

	public Library() {
		borrowerList = new HashMap<>();
		librarianList = new HashMap<>();

		availableBooks = new HashMap<>();
		unavailableBooks = new HashMap<>();
		booksByAuthor = new HashMap<>();
	}

	// TODO: possibly should also be package private, since they are Borrower
	// specific
	public int checkout(Book b) {
		String title = b.getTitle().toLowerCase();
		ArrayList<Book> foundBooks = availableBooks.get(title);
		if (foundBooks == null) {
			return 0; // 0 - cannot check out this book
		}
		for (Book book : foundBooks) {
			if (book.equals(b)) {
				// make it unavailable
				foundBooks.remove(b);

				if (foundBooks.size() == 0) { // remove key if necessary
					availableBooks.remove(title);
				}

				// add to unavailable book list
				foundBooks = unavailableBooks.get(title);

				if (foundBooks == null) {
					ArrayList<Book> list = new ArrayList<>();
					list.add(b);
					unavailableBooks.put(title, list);
				} else {
					foundBooks.add(b);
				}
				// show it was successfully checked out
				return 1;
			}
		}
		return 0;
	}

	/*
	 * @pre - the book put on hold should be checked out currently
	 */
	public void hold(Book b, Borrower user) {
		ArrayList<Borrower> waiting = holds.get(b);
		// no one currently waiting: create new pair
		if (waiting == null) {
			waiting = new ArrayList<>();
			waiting.add(user);
			holds.put(b, waiting);
		} else {
			// someone is already waiting, add current person to the end of the list
			waiting.add(user);
		}
	}

	public void updateHolds(Book b) {
		ArrayList<Borrower> waiting = holds.get(b);
		// no one is waiting for this book
		if (waiting == null) {
			return;
		} else {
			// check out to the first person
			Borrower user = waiting.remove(0);
			user.checkOutHold(b); // check in to the user

			checkout(b); // check it out of the library

			// remove from holds if no one is waiting anymore
			if (waiting.size() == 0) {
				holds.remove(b);
			}
		}
	}

	/*
	 * @pre - Book b is a book that has been checked out, so it is unavailable. This
	 * should be checked when a borrower tries to check in a book.
	 */
	public void checkin(Book b) {
		String title = b.getTitle().toLowerCase();
		ArrayList<Book> foundBooks = unavailableBooks.get(title);

		for (Book book : foundBooks) {
			if (book.equals(b)) {
				// remove it from unavailable books
				foundBooks.remove(b);

				if (foundBooks.size() == 0) { // remove key if necessary
					unavailableBooks.remove(title);
				}

				// add to available book list
				foundBooks = availableBooks.get(title);

				if (foundBooks == null) {
					ArrayList<Book> list = new ArrayList<>();
					list.add(b);
					availableBooks.put(title, list);
				} else {
					foundBooks.add(b);
				}
				// check if it will be checked out to a waiting person
				updateHolds(b);
			}
		}
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

	/*
	 * package private methods for the other classes in the model to use
	 */

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
		
		// remove from holds
		holds.remove(b);
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
