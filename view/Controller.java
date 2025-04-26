package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Book;
import model.Borrower;
import model.DataController;
import model.Librarian;
import model.Library;
import model.ParseBook;
import model.User;
import view.View.BookButton;
import view.View.BookUserButton;
import view.View.UserButton;

public class Controller implements ActionListener{
	
	private static Library library = new Library();
	private HashMap<String, Borrower> borrowerList;
	private HashMap<String, Librarian> librarianList;
	private ArrayList<Borrower> borrowers;
	private View view;
	private User currentUser;
	public JTextField text;
	public JPasswordField password;
	private String title;
	
	public Controller(View view) {
		this.view = view;
		text = new JTextField(20);
		password = new JPasswordField(20);
		borrowerList = new HashMap<String, Borrower>();
		librarianList = new HashMap<String, Librarian>();
		borrowers = new ArrayList<Borrower>();
		try {
			librarianList.put("StaffMember", new Librarian("StaffMember", "StaffPW8*", borrowers));
			librarianList.put("Admin", new Librarian("Admin", "Admin1!", borrowers));
		} catch (NoSuchAlgorithmException e) {
			System.exit(1);
		}

		DataController.loadBookData(library);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("log")) {
			view.changePage("login");
		}
		else if(command.equals("acc")) {
			text.setText("");
			view.changePage("createacc");
		}
		else if(command.equals("stafflog")) {
			view.changePage("stafflogin");
		}
		else if(command.equals("backtohome")) {
			view.changePage("home");
		}
		else if(command.equals("UNentered")) {
			view.changePage("loginpw");
		}
		else if(command.equals("PWentered")) {
			if(borrowerList.get(text.getText()) != null) {
				try {
					if(borrowerList.get(text.getText()).passwordMatched(String.valueOf(password.getPassword())) == true) {
						currentUser = borrowerList.get(text.getText());
						view.loginUser(false);
					}
					else
						view.changePage("loginagain");
				} catch (NoSuchAlgorithmException e1) {
					System.exit(1);
				}
			}
			else
				view.changePage("loginagain");
		}
		else if(command.equals("StaffUNentered")) {
			view.changePage("staffloginpw");
		}
		else if(command.equals("StaffPWentered")) {
			if(librarianList.get(text.getText()) != null) {
				try {
					if(librarianList.get(text.getText()).passwordMatched(String.valueOf(password.getPassword())) == true) {
						currentUser = librarianList.get(text.getText());
						view.loginUser(true);
					}
					else {
						view.changePage("staffloginagain");
					}
				} catch (NoSuchAlgorithmException e1) {
					System.exit(1);
				}
			}
			else
				view.changePage("staffloginagain");
		}
		else if(command.equals("NewUN")) {
			if(borrowerList.get(text.getText()) == null)
				view.changePage("createaccpw");
			else
				view.changePage("createaccagain");
		}
		else if(command.equals("NewPW")) {
			String newPassword = String.valueOf(password.getPassword());
			if(newPassword.matches("(?=.*[a-z].*)(?=.*[A-Z].*)(?=.*\\d.*)(?=.*[~!@#$%^&*()_+].*)(?!.*\\s.*).+")) {
				User u = null;
				try {
					u = new Borrower(text.getText(), newPassword);
				} catch (NoSuchAlgorithmException e1) {
					System.exit(1);
				}
				borrowerList.put(text.getText(), (Borrower) u);
				borrowers.add((Borrower) u);
				currentUser = u;
				view.loginUser(false);
			}
			else
				view.changePage("createaccpwagain");
		}
		else if(command.equals("resetpw")) {
			view.changePage("resetpassword");
		}
		else if(command.equals("oldpwentered")) {
			User u = borrowerList.get(text.getText());
			if(u == null) {
				view.changePage("createacc");
			}
			else {
				String oldPassword = String.valueOf(password.getPassword());
				try {
					if(u.passwordMatched(oldPassword) == true) {
						view.changePage("resetpwnewpw");
					}
					else
						view.changePage("resetpwagain");
				} catch (NoSuchAlgorithmException e1) {
					System.exit(1);
				}
			}
		}
		else if(command.equals("resetpwnewpw")) {
			User u = borrowerList.get(text.getText());
			String newPassword = String.valueOf(password.getPassword());
			if(!newPassword.matches("(?=.*[a-z].*)(?=.*[A-Z].*)(?=.*\\d.*)(?=.*[~!@#$%^&*()_+].*)(?!.*\\s.*).+"))
				view.changePage("resetpwnewpwagain");
			else {
				try {
					u.setPassword(newPassword);
				} catch (NoSuchAlgorithmException e1) {
					System.exit(1);
				}
				currentUser = u;
				view.loginUser(false);
			}
		}
		else if(command.equals("logout")) {
			currentUser = null;
			view.changePage("home");
		}
		else if(command.equals("mylibrary")) {
			view.changePage("mylibrary");
		}
		else if(command.equals("list")) {
			view.changePage("listmenu");
		}
		else if(command.equals("recommended")) {
			view.changePage("recommendations");
		}
		else if(command.equals("mostpopular"))
			view.changePage("mostpopular");
		else if(command.equals("search")) {
			view.changePage("borrowersearch");
		}
		else if(command.equals("borrowermain")) {
			view.changePage("borrowermain");
		}
		else if(command.equals("staffmain")) {
			view.changePage("staffmain");
		}
		else if(command.equals("return")) {
			BookButton b = (BookButton) e.getSource();
			Borrower borrower = (Borrower) currentUser;
			borrower.checkinBook(b.getBook(), library);
			view.changePage("mylibrary");
		}
		else if(command.equals("checkout")) {
			BookButton b = (BookButton) e.getSource();
			Borrower borrower = (Borrower) currentUser;
			borrower.checkOutBook(b.getBook(), library);
			view.changePage("mylibrary");
		}
		else if(command.equals("putonhold")) {
			BookButton b = (BookButton) e.getSource();
			Borrower borrower = (Borrower) currentUser;
			borrower.putBookOnHold(b.getBook(), library);
			view.changePage("mylibrary");
		}
		else if(command.equals("bytitle")) {
			view.changePage("searchbartitle");
		}
		else if(command.equals("byauthor")) {
			view.changePage("searchbarauthor");
		}
		else if(command.equals("titlesearchentered")) {
			view.search(text.getText(), true);
		}
		else if(command.equals("authorsearchentered")) {
			view.search(text.getText(), false);
		}
		else if(command.equals("listtitle")) {
			view.list(true);
		}
		else if(command.equals("listauthor")) {
			view.list(false);
		}
		else if(command.equals("addbooks")) {
			view.changePage("addbooks");
		}
		else if(command.equals("addspecific")) {
			view.changePage("addspecific");
		}
		else if(command.equals("addrange")) {
			view.changePage("addrange");
		}
		else if(command.equals("addtitleentered")) {
			title = text.getText();
			view.switchAddPage("addauthor", text.getText());
		}
		else if(command.equals("addauthorentered")) {
			Librarian librarian = (Librarian) currentUser;
			String[] authorNames = text.getText().split(" ");
			if(authorNames.length < 2) {
				ParseBook.addAuthorAndTitle(authorNames[0], "", title);
			}
			else {
				ParseBook.addAuthorAndTitle(authorNames[0], authorNames[1], title);
			}
				ArrayList<Book> books = ParseBook.downloadBooks();
				if(books == null) {
					view.changePage("addspecificagain");
				} else {
					for(Book b: books) {
						librarian.addBook(b, library);
					}
				}
				view.changePage("staffmain");
		}
		else if(command.equals("addstartrangeentered")) {
			boolean badInt = false;
			try {
				int i = Integer.parseInt(text.getText());
			}
			catch(NumberFormatException e1) {
				badInt = true;
				view.changePage("addrangeagain");
			}
			if(!badInt) {
				ParseBook.addYearStart(text.getText());
				view.switchAddPage("addendrange", text.getText());
			}
		}
		else if(command.equals("addendrangeentered")) {
			boolean badInt = false;
			try {
				int i = Integer.parseInt(text.getText());
			}
			catch(NumberFormatException e1) {
				badInt = true;
				view.changePage("addrangeagain");
			}
			if(!badInt) {
				Librarian librarian = (Librarian) currentUser;
				ParseBook.addYearEnd(text.getText());
				ArrayList<Book> books = ParseBook.downloadBooks();
				if(books == null) {
					view.changePage("addrangeagain");
				}
				else {
					for(Book b: books) {
						librarian.addBook(b, library);
					}
					view.changePage("staffmain");
				}
			}
		}
		else if(command.equals("staffsearch")) {
			view.changePage("staffsearch");
		}
		else if(command.equals("staffbytitle")) {
			view.changePage("staffsearchbartitle");
		}
		else if(command.equals("staffbyauthor")) {
			view.changePage("staffsearchbarauthor");
		}
		else if(command.equals("stafftitlesearchentered")) {
			view.staffSearch(text.getText(), true);
		}
		else if(command.equals("staffauthorsearchentered")) {
			view.staffSearch(text.getText(), false);
		}
		
		else if(command.equals("remove")) {
			Librarian librarian = (Librarian) currentUser;
			BookButton b = (BookButton) e.getSource();
			librarian.removeBook(b.getBook(), library);
			view.changePage("staffmain");
		}
		else if(command.equals("stafflist")) {
			view.changePage("stafflist");
		}
		else if(command.equals("stafflisttitle")) {
			view.staffList(true);
		}
		else if(command.equals("stafflistauthor")) {
			view.staffList(false);
		}
		else if(command.equals("recommend")) {
			Librarian librarian = (Librarian) currentUser;
			BookButton b = (BookButton) e.getSource();
			librarian.recommend(b.getBook(), library);
			view.changePage("staffrecommended");
		}
		else if(command.equals("staffrecommended")) {
			view.changePage("staffrecommended");
		}
		else if(command.equals("staffmostpopular")) {
			view.changePage("staffmostpopular");
		}
		else if(command.equals("stoprecommend")) {
			Librarian librarian = (Librarian) currentUser;
			BookButton b = (BookButton) e.getSource();
			librarian.removeRecommend(b.getBook(), library);
			view.changePage("staffrecommended");
		}
		else if(command.equals("manageusers")) {
			view.changePage("manageusers");
		}
		else if(command.equals("viewuserlibrary")) {
			UserButton b = (UserButton) e.getSource();
			view.openUserLibrary(b.getUsername());
		}
		else if(command.equals("forcereturn")) {
			BookUserButton button = (BookUserButton) e.getSource();
			Borrower b = borrowerList.get(button.getUsername());
			b.checkinBook(button.getBook(), library);
		}
	}
	
	/*
	 * int checkBookStatus(Book b) -- returns -1 if the book is checked out by the User, 0 if it is checked out by
	 * a different user, and 1 if the book is available to be checked out from the library.
	 */
	public int checkBookStatus(Book b) {
		Borrower borrower = (Borrower) currentUser;
		if(borrower.hasBook(b)) {
			return -1;
		}
		else if(library.searchAvailBooksByTitle(b.title).contains(b)) {
			return 1;
		}
		return 0;
	}
	
	public String getUserName() {
		return currentUser.getUserName();
	}
	
	public ArrayList<Book> getCheckedOut() {
		Borrower borrower = (Borrower) currentUser;
		return borrower.checkedOut();
	}
	
	public ArrayList<Book> getHolds() {
		Borrower borrower = (Borrower) currentUser;
		return borrower.onHold();
	}
	
	public ArrayList<Book> getHistory() {
		Borrower borrower = (Borrower) currentUser;
		return borrower.getHistory();
	}
	
	public ArrayList<Book> searchAllBooksByTitle(String search) {
		return library.searchAllBooksByTitle(search);
	}
	
	public ArrayList<Book> searchAvailBooksByTitle(String search) {
		return library.searchAvailBooksByTitle(search);
	}

	public ArrayList<Book> searchAllBooksByAuthor(String search) {
		return library.searchAllBooksByAuthor(search);
	}

	public ArrayList<Book> searchAvailBooksByAuthor(String search) {
		return library.searchAvailBooksByAuthor(search);
	}
	
	public ArrayList<Book> getAllBooksByTitle() {
		return library.getAllBooksByTitle();
	}
	
	public ArrayList<Book> getAllBooksByAuthor() {
		return library.getAllBooksByAuthor();
	}
	
	public ArrayList<Book> mostPopular() {
		return library.getMostPopular();
	}
	
	public int getCheckoutNum(Book b) {
		return library.getCheckoutNum(b);
	}
	
	public int getHoldPosition(Book b) {
		Borrower borrower = (Borrower) currentUser;
		return library.getHoldPosition(b, borrower);
	}
	
	public int getNumHolds(Book b) {
		return library.getNumHolds(b);
	}
	
	public ArrayList<Book> getRecommended() {
		return library.getRecBooksByTitle();
	}
	
	public ArrayList<Book> getLibrarianRecommended() {
		Librarian librarian = (Librarian) currentUser;
		return librarian.getRecommendations(library);
	}
	
	public void exit() {
		DataController.saveBookData(library);
	}
	
	public ArrayList<String> getUsernames() {
		ArrayList<String> usernames = new ArrayList<String>();
		for(Borrower b : borrowerList.values()) {
			usernames.add(b.getUserName());
		}
		return usernames;
	}
}
