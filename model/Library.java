package model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Library {

	private HashMap<String, ArrayList<Book>> availableBooks;
	private HashMap<String, ArrayList<Book>> unavailableBooks;

	private HashMap<String, ArrayList<Book>> booksByAuthor;
	private HashMap<Book, ArrayList<Borrower>> holds;

	// keep track of how often a book is checked out?
	private HashMap<Book, Integer> checkoutNums;
	
	private HashMap<String, ArrayList<Book>> librarianRecs;

	public Library() {
		availableBooks = new HashMap<>();
		unavailableBooks = new HashMap<>();
		booksByAuthor = new HashMap<>();
		checkoutNums = new HashMap<>();
		holds = new HashMap<>();
		librarianRecs = new HashMap<>();
	}

	/*
	 * checkout(Book b) - makes the book unavailable, and returns an int of whether or not this book was
	 * 		successfully checked out
	 */
	int checkout(Book b) {
		String title = b.title.toLowerCase();
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
				// update checkout number
				if (checkoutNums.get(b) != null) {
					checkoutNums.replace(b, checkoutNums.get(b) + 1);
				}

				// Add to wating with no holds for the GUI to work
				if(holds.get(b) == null) {
					ArrayList<Borrower> waiting = new ArrayList<Borrower>();
					holds.put(b, waiting);
				}
				
				// show it was successfully checked out
				return 1;
			}
		}
		return 0;
	}

	/*
	 * @pre - the book put on hold should be checked out currently
	 *      - the user should not have the book on hold already
	 * 
	 * void hold(Book b, Borrower user) - adds the user to the list of users with this
	 * book on hold (added to the end)
	 */
	void hold(Book b, Borrower user) {
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
	
	/*
	 * @pre - this book is currently checked out
	 * int getHoldPosition(Book b, Borrower borrower) - returns the current hold position of
	 * 		this borrower for the book given.
	 */
	public int getHoldPosition(Book b, Borrower borrower) {
		// fix to non-zero indexing
		return holds.get(b).indexOf(borrower) + 1;
	}
	
	/*
	 * public int getNumHolds(Book b) - returns the number of people who currently have a hold for this 
	 * 		book
	 */
	public int getNumHolds(Book b) {
		if (holds.get(b) == null) {
			return 0;
		}
		return holds.get(b).size();
	}

	/*
	 * @pre - this book was just checked in, and is current available
	 * 
	 * private void updateHolds(Book b) - checks if the book (which has been recently
	 * 		checked in) is on hold. If so, it automatically checks it out to the first person
	 * 		on the hold list, and updates the hold list accordingly.
	 */
	private void updateHolds(Book b) {
		ArrayList<Borrower> waiting = holds.get(b);
		// no one is waiting for this book
		if (waiting == null || waiting.size() == 0) {
			return;
		} 
		// remove it because no one is "next up"
		if (waiting.size() == 0) {
			holds.remove(b);
		}
		else {
			// check out to the first person
			Borrower user = waiting.remove(0);
			user.checkOutHold(b); // check in to the user

			checkout(b); // check it out of the library
		}
	}

	/*
	 * @pre - Book b is a book that has been checked out, so it is unavailable. This
	 * should be checked when a borrower tries to check in a book.
	 * 
	 * void checkin(Book b) - checks the book back into the library, making it available.
	 * 		if anyone has it on hold, checks it out to the next person waiting
	 */
	void checkin(Book b) {
		String title = b.title.toLowerCase();
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
				break;
			}
		}
	}

	/*
	 * public ArrayList<Book> searchAllBooksByTitle(String title) - returns an arrayList 
	 *  	of all books available or unavailable, with the given title (case-insensitive)
	 *  	 sorted by author
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
		ArrayList<Book> unavail = unavailableBooks.get(title);
		if (unavail != null) {
			found.addAll(unavail);
		}
		Collections.sort(found, Book.titleFirstComparator());
		return found;
	}

	/*
	 * public ArrayList<Book> searchAvailBooksByTitle(String title) - returns an arrayList of
	 * 		all available books with the given title (case-insensitive)  sorted by author
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
		Collections.sort(found, Book.titleFirstComparator());
		return found;
	}
	
	/*
	 * public ArrayList<Book> searchAvailBooksByTitle(String title) - returns an arrayList of
	 * 		all available books with the given title (case-insensitive), sorted by author
	 */
	public ArrayList<Book> searchUnavailBooksByTitle(String title) {
		// normalize title
		title = title.toLowerCase().trim();

		ArrayList<Book> found = new ArrayList<>();
		// copy available books with this title
		ArrayList<Book> unavail = unavailableBooks.get(title);
		if (unavail != null) {
			found.addAll(unavail);
		}
		Collections.sort(found, Book.titleFirstComparator());
		return found;
	}

	/*
	 * public ArrayList<Book> searchAllBooksByAuthor(String author) - returns an arrayList of
	 * 		all books (available or unavailable) with the given author (case-insensitive)
	 * 		sorted by title
	 */
	public ArrayList<Book> searchAllBooksByAuthor(String author) {
		// normalize author
		author = author.toLowerCase().trim();

		ArrayList<Book> found = new ArrayList<>();

		// copy available books with this title
		ArrayList<Book> books = booksByAuthor.get(author);
		if (books != null) {
			found.addAll(books);
		}
		Collections.sort(found, Book.titleFirstComparator());
		return found;
	}

	/*
	 * public ArrayList<Book> searchAvaillBooksByAuthor(String author) - returns an arrayList
	 * 		of all available books with the given author (case-insensitive) sorted by title
	 */
	public ArrayList<Book> searchAvailBooksByAuthor(String author) {
		// normalize author
		author = author.toLowerCase().trim();

		ArrayList<Book> found = new ArrayList<>();
		// copy available books with this title
		ArrayList<Book> books = booksByAuthor.get(author);
		if (books != null) {
			for (Book b: books) {
				ArrayList<Book> avail = availableBooks.get(b.title.toLowerCase());
				if (avail != null &&avail.contains(b)) {
					found.add(b);
				}
			}
		}
		Collections.sort(found, Book.titleFirstComparator());
		return found;
	}
	
	/*
	 * searchAvaillBooksByAuthor(String author) - returns an arrayList 
	 * 		of all unavailable books with the given author (case-insensitive)
	 * 		sorted by title
	 */
	public ArrayList<Book> searchUnavailBooksByAuthor(String author) {
		// normalize author
		author = author.toLowerCase().trim();

		ArrayList<Book> found = new ArrayList<>();
		// copy available books with this title
		ArrayList<Book> books = booksByAuthor.get(author);
		if (books != null) {
			for (Book b: books) {
				ArrayList<Book> unavail = unavailableBooks.get(b.title.toLowerCase());
				if (unavail != null && unavail.contains(b)) {
					found.add(b);
				}
			}
		}
		Collections.sort(found, Book.titleFirstComparator());
		return found;
	}
	
	/*
	 * public Book searchAllBookByID(String id) - returns the book (available or unavailable) 
	 * 		if it is in the library. Returns null if it is not in the library
	 */
	public Book searchAllBookByID(String id) {
		Book book = null;
		for (Book b : getAllBooks()) {
			if (b.id.equals(id)) {
				book = b;
			}
		}
		
		return book;
	}
	
	/*
	 * public Book searchAvailBookByID(String id) - returns the book with the given ID
	 * 		if it is in the library and available. Returns null if it is not in the library and available
	 */
	public Book searchAvailBookByID(String id) {
		Book book = null;
		for (Book b : getAvailBooks()) {
			if (b.id.equals(id)) {
				book = b;
			}
		}
		
		return book;
	}
	
	/*
	 * public Book searchUnavailBookByID(String id) - returns the book with the given ID
	 * 		if it is in the library and unavailable. Returns null if it is not in the library and unavailable
	 */
	public Book searchUnavailBookByID(String id) {
		Book book = null;
		for (Book b : getUnavailBooks()) {
			if (b.id.equals(id)) {
				book = b;
			}
		}
		
		return book;
	}

	/*
	 * public ArrayList<Book> getAllBooksByAuthor() - returns a sorted ArrayList of all books in the
	 * 		library sorted by author first, then title
	 */
	public ArrayList<Book> getAllBooksByAuthor() {
		ArrayList<Book> sorted = getAllBooks();
		Collections.sort(sorted, Book.authorFirstComparator());
		return sorted;
	}

	/*
	 * public ArrayList<Book> getAvailooksByAuthor() - returns a sorted ArrayList of all available books
	 * 		in the library sorted by author first, then title
	 */
	public ArrayList<Book> getAvailBooksByAuthor() {
		ArrayList<Book> available = getAvailBooks();
		Collections.sort(available, Book.authorFirstComparator());
		return available;
	}

	/*
	 * public ArrayList<Book> getUnavailooksByAuthor() - returns a sorted ArrayList of all 
	 * 		unavailable books in the library sorted by author first, then title
	 */
	public ArrayList<Book> getUnavailBooksByAuthor() {
		ArrayList<Book> unavailable = getUnavailBooks();
		Collections.sort(unavailable, Book.authorFirstComparator());
		return unavailable;
	}
	
	/*
	 * unavailable books in the getRecBooksByAuthor() - returns a sorted ArrayList of all 
	 * 		recommended books in the library sorted by author first, then title
	 */
	public ArrayList<Book> getRecBooksByAuthor() {
		ArrayList<Book> recs = getRecBooks();
		Collections.sort(recs, Book.authorFirstComparator());
		return recs;
	}

	/*
	 * public ArrayList<Book> getAllBooksByTitle() - returns a sorted ArrayList of all books
	 * 		 in the library sorted by title first, then author
	 */
	public ArrayList<Book> getAllBooksByTitle() {
		ArrayList<Book> sorted = getAllBooks();
		Collections.sort(sorted, Book.titleFirstComparator());
		return sorted;
	}

	/*
	 * public ArrayList<Book> getAllBooksByTitle() - returns a sorted ArrayList of all available
	 * 		 books in the library sorted by title first, then author
	 */
	public ArrayList<Book> getAvailBooksByTitle() {
		ArrayList<Book> available = getAvailBooks();
		Collections.sort(available, Book.titleFirstComparator());
		return available;
	}

	/*
	 * public ArrayList<Book> getUnavailBooksByTitle() - returns a sorted ArrayList of all unavailable
	 *    books in the library sorted by title first, then author
	 */
	public ArrayList<Book> getUnavailBooksByTitle() {
		ArrayList<Book> unavailable = getUnavailBooks();
		Collections.sort(unavailable, Book.titleFirstComparator());
		return unavailable;
	}
	
	/*
	 * public ArrayList<Book> getRecBooksByTitle() - returns a sorted ArrayList of all recommended 
	 * 		books in the library sorted by title first, then author
	 */
	public ArrayList<Book> getRecBooksByTitle() {
		ArrayList<Book> recs = getRecBooks();
		Collections.sort(recs, Book.titleFirstComparator());
		return recs;
	}

	/*
	 * private ArrayList<Book> getAllBooks() - returns an ArrayList of copies of all books in the library
	 */
	private ArrayList<Book> getAllBooks() {
		ArrayList<Book> books = new ArrayList<>();
		for (ArrayList<Book> list : booksByAuthor.values()) {
			books.addAll(list);
		}
		return books;
	}

	/*
	 * private ArrayList<Book> getAvailBooks() - returns an ArrayList of copies of all available 
	 * 		books in the library
	 */
	private ArrayList<Book> getAvailBooks() {
		ArrayList<Book> books = new ArrayList<>();
		for (ArrayList<Book> list : availableBooks.values()) {
			books.addAll(list);
		}
		return books;
	}

	/*
	 * private ArrayList<Book> getUnavailBooks() - returns an ArrayList of copies of all unavailable
	 * 		 books in the library
	 */
	private ArrayList<Book> getUnavailBooks() {
		ArrayList<Book> books = new ArrayList<>();
		for (ArrayList<Book> list : unavailableBooks.values()) {
			books.addAll(list);
		}
		return books;
	}
	
	/*
	 * private ArrayList<Book> getRecBooks() - returns an ArrayList of copies of all 
	 * 		recommended books in the library
	 */
	private ArrayList<Book> getRecBooks() {
		Set<Book> books = new HashSet<>();
		for (ArrayList<Book> list : librarianRecs.values())
			books.addAll(new HashSet<>(list));
		
		return new ArrayList<>(books);
	}

	// package private methods for the other classes in the model to use
	 
	/*
	 * public void addBook(Book b) - adds this book to the library, appropriately initializing 
	 * 		all information related to it, and making it available to be checked out
	 */
	public void addBook(Book b) {
		String title = b.title.toLowerCase();
		String author = b.authors.get(0).NAME.toLowerCase();

		ArrayList<Book> booksWithTitle = availableBooks.get(title);
		if (booksWithTitle == null) {
			ArrayList<Book> list = new ArrayList<>();
			list.add(b);
			availableBooks.put(title, list);
		} else {
			//avoids adding duplicates
			if(booksWithTitle.contains(b)) {
				return;
			}
			booksWithTitle.add(b);
		}

		ArrayList<Book> booksWithAuthor = booksByAuthor.get(author);
		if (booksWithAuthor == null) {
			ArrayList<Book> list = new ArrayList<>();
			list.add(b);
			booksByAuthor.put(author, list);
		} else {
			booksWithAuthor.add(b);
		}
		
		checkoutNums.put(b, 0);
	}

	/*
	 * @pre- this book is currently available, so that it is not checked out
	 * 
	 * removeBook(Book b) - removes the book completely from the library
	 */
	void removeBook(Book b) {
		String title = b.title.toLowerCase();
		String author = b.authors.get(0).NAME.toLowerCase();

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
		
		// remove from recommendations
		for (ArrayList<Book> list: librarianRecs.values()) {
			list.remove(b);
		}
		
		// remove from counts
		checkoutNums.remove(b);

		// remove from holds
		holds.remove(b);
		
		// remove book from database
		DataController.removeBook(b);

	}

	/* 
	 * @pre - this recommender has not already recommended the book
	 * 
	 * void recommend(String recommender, Book book) - adds this book to the recommended
	 * 		list for this recommender
	 */
	void recommend(String recommender, Book book) {
		if (librarianRecs.containsKey(recommender))
			librarianRecs.get(recommender).add(book);
		
		else {
			ArrayList<Book> recs = new ArrayList<>();
			recs.add(book);
			librarianRecs.put(recommender, recs);
		}
	}
	
	/*
	 * public ArrayList<Book> getRecommendationsByLibrarian(String recommender) -
	 * 		returns an arraylist of all books recommended by this librarian, sorted by title, then author
	 */
	public ArrayList<Book> getRecommendationsByLibrarian(String recommender) {
		if (librarianRecs.containsKey(recommender)) {
			ArrayList<Book> recs = librarianRecs.get(recommender);
			Collections.sort(recs, Book.titleFirstComparator());
			return recs;
		}
		return new ArrayList<>();
	}
	
	/*
	 * void removeRecommend(String recommender, Book book) - removes this book from the given librarian's
	 * 		list of recommendations (or does nothing if this user hasn't recommended anything or has not
	 * 		recommended this book)
	 */
	void removeRecommend(String recommender, Book book) {
		// Won't crash if recommender doesn't exist
		if (librarianRecs.containsKey(recommender)) {
			librarianRecs.get(recommender).remove(book);
			if (librarianRecs.get(recommender).size() == 0)
				librarianRecs.remove(recommender);
		}
	}
	
	/*
	 * void removeLibraryRec(String recommender) - removes all recommended books for this recommender
	 */
	void removeLibraryRec(String recommender) {
		librarianRecs.remove(recommender);
	}
	
	/*
	 * HashMap<Book, Integer> getCheckoutNums() - returns a copy of the checkoutNums
	 * 		hashmap, which tracks how many times each book has been checked out (only if it has been
	 * 		checked out at least once). It will be accurate only up to the current moment
	 */
	HashMap<Book, Integer> getCheckoutNums(){
		return new HashMap<>(checkoutNums);
	}
	
	public Integer getCheckoutNum(Book b) {
		return checkoutNums.get(b);
	}
	
	/*
	 * public ArrayList<Book> getMostPopular() - returns an arraylist of the top 10 (or less if
	 * there are less than 10 books in the library) books by checkout numbers. Ties are alphabetically
	 * by title, then alphabetically by primary author.
	 */
	public ArrayList<Book> getMostPopular() {
		// sort the "entry set" which allows you to sort each PAIR
		
		ArrayList<Map.Entry<Book, Integer>> sorted = new ArrayList<>();
		
		for (Map.Entry<Book, Integer> entry: checkoutNums.entrySet()) {
			if (entry.getValue()> 0) {
				sorted.add(entry);
			}
		}
		
		// sort the pairs in descending order by integer (the value part of the pair)
		sorted.sort(new Comparator<Map.Entry<Book, Integer>>() {
			// anonymous inner class comparator: count -> title -> primary author name
		    @Override
		    public int compare(Map.Entry<Book, Integer> e1, Map.Entry<Book, Integer> e2) {
		    	int comp = e2.getValue().compareTo(e1.getValue()); // descending order
		    	if (comp == 0) {
		    		comp = e1.getKey().title.compareTo(e2.getKey().title); // ascending order
		    		if (comp == 0) {
		    			comp = e1.getKey().authors.get(0).NAME.compareTo(e2.getKey().authors.get(0).NAME);
		    		}
		    	}
		        return comp;
		    }
		});
		
		// extract only the top 10 (or if there are less than that many) books from the sorted pairs
		int max = 10;
		if (sorted.size() < 10) max = sorted.size();
		
		ArrayList<Book> mostPopular = new ArrayList<>();
		for (int i = 0; i < max; i++) {
			mostPopular.add(i, sorted.get(i).getKey());
		}
		
		return mostPopular;
	}


}
