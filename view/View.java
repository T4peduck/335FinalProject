package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Scrollable;
import javax.swing.border.EmptyBorder;

import model.Book;
import model.Borrower;
import model.Library;
import model.User;

public class View extends JFrame {
	
	public class BookButton extends JButton {
		private Book b;
		
		public BookButton(String s) {
			super(s);
		}
		
		public Book getBook() {
			return b;
		}
		
		private void setBook(Book b) {
			this.b = b;
		}
	}
	
	private class BookPanel extends JPanel implements Scrollable {
		
		private int height;
		private int width;
		
		public BookPanel(int width) {
			super();
			this.width = width;
			this.height = 50;
		}
		
		public Dimension getPreferredScrollableViewportSize() {
			return super.getPreferredSize();
		}

		@Override
		public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
			return 0;
		}

		@Override
		public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
			return 0;
		}

		@Override
		public boolean getScrollableTracksViewportWidth() {
			return true;
		}

		@Override
		public boolean getScrollableTracksViewportHeight() {
			return false;
		}
		
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(this.width, this.height);
		}
		
		@Override
		public Component add(Component component) {
			super.add(component);
			this.height += component.getPreferredSize().height;
			return(component);
		}
		
	}
	
	private static Library library = new Library();
	private User currentUser;
	private Controller controller;
	private JPanel mainPanel;
	private JScrollPane scroller = null;
	
	public View() {
		controller = new Controller(library, this);
		this.setTitle("Home Page");
		this.setSize(400, 400);
		this.setUpHome(true);
	}
	
	private void setUpHome(boolean first) {
		if(!first)
			this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		JLabel welcomeLabel = new JLabel("Welcome to the 335 E-Library");
		welcomeLabel.setBorder(new EmptyBorder(0, (400 - welcomeLabel.getWidth()) / 2, 0, (400 - welcomeLabel.getWidth()) / 2));
		mainPanel.add(welcomeLabel);
		
		JLabel label = new JLabel("Please select an option from the three below");
		label.setBorder(new EmptyBorder(0, (400 - label.getWidth()) / 2, 150, (400 - label.getWidth()) / 2));
		mainPanel.add(label);
		
		JButton accButton = new JButton("Create Account");
		accButton.setActionCommand("acc");
		if(accButton.getActionListeners().length == 0)
			accButton.addActionListener(controller);
		mainPanel.add(accButton);
		
		JButton logButton = new JButton("Login");
		logButton.setActionCommand("log");
		if(logButton.getActionListeners().length == 0)
			logButton.addActionListener(controller);
		mainPanel.add(logButton);
		
		JButton staffLogButton = new JButton("Staff Login");
		staffLogButton.setActionCommand("stafflog");
		staffLogButton.addActionListener(controller);
		mainPanel.add(staffLogButton);
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				System.exit(0);
			}
		});
		
		this.setVisible(true);
	}
	
	private void setUpLogin(boolean first) {
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		JButton back = new JButton("Go Back");
		back.setActionCommand("backtohome");
		back.addActionListener(controller);
		mainPanel.add(back);
		
		if(first == false) {
			JLabel errorLabel = new JLabel("Invalid Login. Please enter a valid login or return to the main page and create an account");
			errorLabel.setBorder(new EmptyBorder(0, (this.getWidth() - errorLabel.getWidth()) / 2, 100, (this.getWidth() - errorLabel.getWidth()) / 2));
			mainPanel.add(errorLabel);
		}
		else {
			JLabel loginLabel = new JLabel("Please enter your login in the fields below");
			loginLabel.setBorder(new EmptyBorder(0, (this.getWidth() - loginLabel.getWidth()) / 2, 100, (this.getWidth() - loginLabel.getWidth()) / 2));
			mainPanel.add(loginLabel);
		}
		
		JLabel usernameLabel = new JLabel("Username:");
		mainPanel.add(usernameLabel);
		
		JTextField usernameField = controller.text;
		usernameField.setEditable(true);
		usernameField.setText("");
		usernameField.setToolTipText("");
		usernameField.setActionCommand("UNentered");
		if(usernameField.getActionListeners().length == 0)
			usernameField.addActionListener(controller);
		mainPanel.add(usernameField);
		
		this.setVisible(true);
	}
	
	private void setUpLoginPW() {
		controller.text.removeActionListener(controller);
		controller.text.setEditable(false);
		
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(25, (this.getWidth() - emptyLabel.getWidth()) /2, 25, (this.getWidth() - emptyLabel.getWidth()) /2));
		mainPanel.add(emptyLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		mainPanel.add(passwordLabel);
		
		JPasswordField passwordField = controller.password;
		passwordField.setActionCommand("PWentered");
		if(passwordField.getActionListeners().length == 0)
			passwordField.addActionListener(controller);
		passwordField.setToolTipText("");
		passwordField.setText("");
		mainPanel.add(passwordField);
		
		this.setVisible(true);
	}
	
	private void setUpStaffLogin(boolean first) {
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		JButton back = new JButton("Go Back");
		back.setActionCommand("backtohome");
		back.addActionListener(controller);
		mainPanel.add(back);
		
		if(first == false) {
			JLabel errorLabel = new JLabel("Invalid Login. Please enter a valid login or return to the main page and create an account");
			errorLabel.setBorder(new EmptyBorder(0, (this.getWidth() - errorLabel.getWidth()) / 2, 100, (this.getWidth() - errorLabel.getWidth()) / 2));
			mainPanel.add(errorLabel);
		}
		else {
			JLabel loginLabel = new JLabel("Please enter your login in the fields below");
			loginLabel.setBorder(new EmptyBorder(0, (this.getWidth() - loginLabel.getWidth()) / 2, 100, (this.getWidth() - loginLabel.getWidth()) / 2));
			mainPanel.add(loginLabel);
		}
		
		JLabel usernameLabel = new JLabel("StaffID:");
		mainPanel.add(usernameLabel);
		
		JTextField usernameField = controller.text;
		usernameField.setEditable(true);
		usernameField.setText("");
		usernameField.setToolTipText("");
		usernameField.setActionCommand("StaffUNentered");
		if(usernameField.getActionListeners().length == 0)
			usernameField.addActionListener(controller);
		mainPanel.add(usernameField);
		
		this.setVisible(true);
	}
	
	private void setUpStaffLoginPW() {
		controller.text.removeActionListener(controller);
		controller.text.setEditable(false);
		
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(25, (this.getWidth() - emptyLabel.getWidth()) /2, 25, (this.getWidth() - emptyLabel.getWidth()) /2));
		mainPanel.add(emptyLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		mainPanel.add(passwordLabel);
		
		JPasswordField passwordField = new JPasswordField(20);
		passwordField.setActionCommand("StaffPWentered");
		if(passwordField.getActionListeners().length == 0)
			passwordField.addActionListener(controller);
		passwordField.setToolTipText("");
		passwordField.setText("");
		mainPanel.add(passwordField);
		
		this.setVisible(true);
	}
	
	private void setUpCreateAccount(boolean first) {
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		JButton back = new JButton("Go Back");
		back.setActionCommand("backtohome");
		back.addActionListener(controller);
		mainPanel.add(back);
		
		if(!first) {
			JLabel errorLabel = new JLabel("Error: Invalid Username or Username already taken");
			errorLabel.setBorder(new EmptyBorder(0, (this.getWidth() - errorLabel.getWidth()) / 2, 100, (this.getWidth() - errorLabel.getWidth()) / 2));
			mainPanel.add(errorLabel);
			controller.text.setText("");
		}
		else {
			JLabel loginLabel = new JLabel("Please enter your login in the fields below");
			loginLabel.setBorder(new EmptyBorder(0, (this.getWidth() - loginLabel.getWidth()) / 2, 100, (this.getWidth() - loginLabel.getWidth()) / 2));
			mainPanel.add(loginLabel);
		}
		
		JLabel usernameLabel = new JLabel("Username:");
		mainPanel.add(usernameLabel);
		
		JTextField usernameField = controller.text;
		usernameField.setEditable(true);
		usernameField.setToolTipText("Enter a valid, alphanumeric username");
		usernameField.setActionCommand("NewUN");
		if(usernameField.getActionListeners().length == 0)
			usernameField.addActionListener(controller);
		mainPanel.add(usernameField);
		
		this.setVisible(true);
	}
	
	private void setUpCreateAccountPW(boolean first) {
		if(!first) {
			setUpCreateAccount(true);
			JLabel errorLabel = new JLabel("Error: Invalid Password.");
			JLabel emptyLabel = new JLabel("");
			emptyLabel.setBorder(new EmptyBorder(50 - errorLabel.getHeight(), (this.getWidth() - emptyLabel.getWidth()) /2, 0, (this.getWidth() - emptyLabel.getWidth()) /2));
			mainPanel.add(emptyLabel);
			errorLabel.setBorder(new EmptyBorder(0, (this.getWidth() - errorLabel.getWidth()) / 2, 0, (this.getWidth() - errorLabel.getWidth()) / 2));
			mainPanel.add(errorLabel);
			controller.password.setText("");
			controller.text.removeActionListener(controller);
			controller.text.setEditable(false);
		}
		else {
			JLabel emptyLabel = new JLabel("");
			emptyLabel.setBorder(new EmptyBorder(70, (this.getWidth() - emptyLabel.getWidth()) /2, 0, (this.getWidth() - emptyLabel.getWidth()) /2));
			mainPanel.add(emptyLabel);
			controller.text.removeActionListener(controller);
			controller.text.setEditable(false);
		}
		
		JLabel passwordLabel = new JLabel("Password:");
		mainPanel.add(passwordLabel);
		
		JPasswordField passwordField = controller.password;
		passwordField.setToolTipText("Enter a valid, alphanumeric passowrd");
		passwordField.setActionCommand("NewPW");
		if(passwordField.getActionListeners().length == 0)
			passwordField.addActionListener(controller);
		mainPanel.add(passwordField);
		
		this.setVisible(true);
	}
	
	private void setUpBorrowerMain() {
		if(scroller != null)
			this.remove(scroller);
		scroller = null;
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		JMenuBar menuBar = new JMenuBar();
		JLabel welcomeMessage = new JLabel("Welcome, " + currentUser.getUserName());
		menuBar.add(welcomeMessage);
		JButton logoutButton = new JButton("Logout");
		logoutButton.setActionCommand("logout");
		logoutButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - welcomeMessage.getWidth() - logoutButton.getWidth() - 187));
		menuBar.add(emptyLabel);
		menuBar.add(logoutButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(50, this.getWidth(), 0, 0));
		mainPanel.add(emptyLabel);
		
		JLabel menuLabel = new JLabel("Please select from the options below");
		menuLabel.setBorder(new EmptyBorder(0, (this.getWidth() - menuLabel.getWidth()) / 2, 50, (this.getWidth() - menuLabel.getWidth()) / 2));
		mainPanel.add(menuLabel);
		
		JButton libraryButton = new JButton("My Library");
		libraryButton.setActionCommand("mylibrary");
		libraryButton.addActionListener(controller);
		JButton searchButton = new JButton("Search");
		searchButton.setActionCommand("search");
		searchButton.addActionListener(controller);
		JButton listButton = new JButton("List Library");
		listButton.setActionCommand("list");
		listButton.addActionListener(controller);
		JButton recommendButton = new JButton("Recommendations");
		recommendButton.setActionCommand("recommended");
		recommendButton.addActionListener(controller);
		mainPanel.add(libraryButton);
		mainPanel.add(searchButton);
		mainPanel.add(listButton);
		mainPanel.add(recommendButton);
		
		this.setVisible(true);
	}
	
	private void setUpStaffMain() {
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		JMenuBar menuBar = new JMenuBar();
		JLabel welcomeMessage = new JLabel("Welcome, " + currentUser.getUserName());
		menuBar.add(welcomeMessage);
		JButton logoutButton = new JButton("Logout");
		logoutButton.setActionCommand("logout");
		logoutButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - welcomeMessage.getWidth() - logoutButton.getWidth() - 187));
		menuBar.add(emptyLabel);
		menuBar.add(logoutButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(50, this.getWidth(), 0, 0));
		mainPanel.add(emptyLabel);
		
		JLabel menuLabel = new JLabel("Please select from the options below");
		menuLabel.setBorder(new EmptyBorder(0, (this.getWidth() - menuLabel.getWidth()) / 2, 50, (this.getWidth() - menuLabel.getWidth()) / 2));
		mainPanel.add(menuLabel);
		
		this.setVisible(true);
	}
	
	private void setUpUserLibrary() {
		this.remove(mainPanel);
		mainPanel = new BookPanel(this.getWidth());
		
		
		JMenuBar menuBar = new JMenuBar();
		JLabel libraryMessage = new JLabel("Your Library");
		menuBar.add(libraryMessage);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("borrowermain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - libraryMessage.getWidth() - menuButton.getWidth() - 200));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		JLabel checkedOutMessage = new JLabel("Checked Out:");
		checkedOutMessage.setBorder(new EmptyBorder(0, (this.getWidth() - checkedOutMessage.getWidth()) / 2, 0, (this.getWidth() - checkedOutMessage.getWidth()) / 2));
		mainPanel.add(checkedOutMessage);
		
		ArrayList<Book> checkedOut = new ArrayList<Book>();
		
		for(Book b : /*currentUser.getCheckedOut()*/ checkedOut) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			BookButton returnButton = new BookButton("Return");
			returnButton.addActionListener(controller);
			returnButton.setActionCommand("return");
			returnButton.setBook(b);
			mainPanel.add(returnButton);
			emptyLabel = new JLabel();
			emptyLabel.setBorder(new EmptyBorder(0, 0, 25, this.getWidth()));
			mainPanel.add(emptyLabel);
		}
		
		JLabel onHoldMessage = new JLabel("On Hold:");
		onHoldMessage.setBorder(new EmptyBorder(0, (this.getWidth() - onHoldMessage.getWidth()) / 2, 0, (this.getWidth() - onHoldMessage.getWidth()) / 2));
		mainPanel.add(onHoldMessage);
		
		for(Book b : /*currentUser.getHolds()*/ checkedOut) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			JLabel holdsLabel = new JLabel("Holds in front of you: ");
			holdsLabel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
			mainPanel.add(holdsLabel);
			emptyLabel = new JLabel();
			emptyLabel.setBorder(new EmptyBorder(0, 0, 25, this.getWidth()));
			mainPanel.add(emptyLabel);
		}
		
		JLabel historyMessage = new JLabel("Previously Checked Out:");
		historyMessage.setBorder(new EmptyBorder(0, (this.getWidth() - historyMessage.getPreferredSize().width) / 2, 0, (this.getWidth() - historyMessage.getPreferredSize().width) / 2));
		mainPanel.add(historyMessage);
		
		for(Book b : /*currentUser.getHistory()*/ checkedOut) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			BookButton checkOutButton = new BookButton("Check out");
			checkOutButton.addActionListener(controller);
			checkOutButton.setActionCommand("checkout");
			checkOutButton.setBook(b);
			mainPanel.add(checkOutButton);
			emptyLabel = new JLabel();
			emptyLabel.setBorder(new EmptyBorder(0, 0, 25, this.getWidth()));
			mainPanel.add(emptyLabel);
		}
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroller);
		
		this.setVisible(true);
	}
	
	private void setUpBorrowerSearch() {
		if(scroller != null)
			this.remove(scroller);
		scroller = null;
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		
		JMenuBar menuBar = new JMenuBar();
		JLabel libraryMessage = new JLabel("Search");
		menuBar.add(libraryMessage);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("borrowermain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - libraryMessage.getWidth() - menuButton.getWidth() - 155));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(50, this.getWidth(), 0, 0));
		mainPanel.add(emptyLabel);
		
		JLabel menuLabel = new JLabel("Please select from the options below");
		menuLabel.setBorder(new EmptyBorder(0, (this.getWidth() - menuLabel.getWidth()) / 2, 50, (this.getWidth() - menuLabel.getWidth()) / 2));
		mainPanel.add(menuLabel);
		
		JButton byAuthorButton = new JButton("Search by Author");
		byAuthorButton.setActionCommand("byauthor");
		byAuthorButton.addActionListener(controller);
		JButton byTitleButton = new JButton("Search by Title");
		byTitleButton.setActionCommand("bytitle");
		byTitleButton.addActionListener(controller);
		mainPanel.add(byAuthorButton);
		mainPanel.add(byTitleButton);
		
		this.setVisible(true);
	}
	
	private void setUpSearchBar(boolean title) {
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu searchMenu = new JMenu("Search Type Menu");
		JMenuItem menuItem = new JMenuItem("Search by Title");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("bytitle");
		searchMenu.add(menuItem);
		menuItem = new JMenuItem("Search by Author");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("byauthor");
		searchMenu.add(menuItem);
		menuBar.add(searchMenu);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("borrowermain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - 230));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		JLabel instructionLabel = new JLabel("Enter Full Keyword Below");
		instructionLabel.setBorder(new EmptyBorder(0, (this.getWidth() - instructionLabel.getPreferredSize().width) / 2, 50, (this.getWidth() - instructionLabel.getPreferredSize().width) / 2));
		mainPanel.add(instructionLabel);
		
		if(title) {
			JLabel titleLabel = new JLabel("Enter title: ");
			titleLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
			JTextField text = controller.text;
			text.setText("");
			text.setEditable(true);
			text.setToolTipText("Enter the full title of the desired book");
			text.setActionCommand("titlesearchentered");
			if(text.getActionListeners().length == 0)
				text.addActionListener(controller);
			mainPanel.add(titleLabel);
			mainPanel.add(text);
		}
		else {
			JLabel titleLabel = new JLabel("Enter author: ");
			titleLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
			JTextField text = controller.text;
			text.setText("");
			text.setEditable(true);
			text.setToolTipText("Enter the full name of the author of the desired book");
			text.setActionCommand("authorsearchentered");
			if(text.getActionListeners().length == 0)
				text.addActionListener(controller);
			mainPanel.add(titleLabel);
			mainPanel.add(text);
		}
			
		
		this.setVisible(true);
	}
	
	private void setUpSearchResults(String search, boolean title) {
		this.remove(mainPanel);
		mainPanel = new BookPanel(this.getWidth());
		
		JMenuBar menuBar = new JMenuBar();
		JLabel searchLabel = new JLabel("Search Results");
		menuBar.add(searchLabel);
		JMenu backMenu = new JMenu("Go Back");
		JMenuItem menuItem = new JMenuItem("Back to Search");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("search");
		backMenu.add(menuItem);
		menuItem = new JMenuItem("Main Menu");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("borrowermain");
		backMenu.add(menuItem);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, this.getWidth() - 10 - searchLabel.getPreferredSize().width - backMenu.getPreferredSize().width, 0, 0));
		menuBar.add(emptyLabel);
		menuBar.add(backMenu);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		if(title) {
			JLabel titleLabel = new JLabel("Enter title: ");
			titleLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
			JTextField text = controller.text;
			text.setToolTipText("Enter the full title of the desired book");
			text.removeActionListener(controller);
			text.setEditable(false);
			mainPanel.add(titleLabel);
			mainPanel.add(text);
		}
		else {
			JLabel titleLabel = new JLabel("Enter author: ");
			titleLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
			JTextField text = controller.text;
			text.setToolTipText("Enter the full name of the author of the desired book");
			text.removeActionListener(controller);
			text.setEditable(false);
			mainPanel.add(titleLabel);
			mainPanel.add(text);
		}
		
		JLabel resultsLabel = new JLabel("Search Results:");
		resultsLabel.setBorder(new EmptyBorder(0, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2, 25, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2));
		mainPanel.add(resultsLabel);
		
		ArrayList<Book> searchResults;
		ArrayList<Book> availResults;
		
		if(title) {
			searchResults = library.searchAllBooksByTitle(search);
			availResults = library.searchAvailBooksByTitle(search);
		}
		else {
			searchResults = library.searchAllBooksByAuthor(search);
			availResults = library.searchAvailBooksByAuthor(search);
		}
		
		if(searchResults.size() == 0) {
			JLabel noResultsLabel = new JLabel("Search returned no results");
			noResultsLabel.setBorder(new EmptyBorder(0, (this.getWidth() - noResultsLabel.getPreferredSize().width) / 2, 0, (this.getWidth() - noResultsLabel.getPreferredSize().width) / 2));
			mainPanel.add(noResultsLabel);
		}
		else {
			JLabel availLabel = new JLabel("Available Books:");
			availLabel.setBorder(new EmptyBorder(0, (this.getWidth() - availLabel.getPreferredSize().width) / 2, 10, (this.getWidth() - availLabel.getPreferredSize().width) / 2));
			mainPanel.add(availLabel);
			if(availResults.size() == 0) {
				JLabel noAvailLabel = new JLabel("No Available Books Meet this Query");
				noAvailLabel.setBorder(new EmptyBorder(0, (this.getWidth() - noAvailLabel.getPreferredSize().width) / 2, 10, (this.getWidth() - noAvailLabel.getPreferredSize().width) / 2));
				mainPanel.add(noAvailLabel);
			}
			else {
				for(Book b : availResults) {
					JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
					bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
					mainPanel.add(bookLabel);
					BookButton checkOutButton = new BookButton("Check out");
					checkOutButton.addActionListener(controller);
					checkOutButton.setActionCommand("checkout");
					checkOutButton.setBook(b);
					mainPanel.add(checkOutButton);
					emptyLabel = new JLabel();
					emptyLabel.setBorder(new EmptyBorder(0, 0, 25, this.getWidth()));
					mainPanel.add(emptyLabel);
				}
			}
			JLabel notAvailLabel = new JLabel("Books Not Currently Available:");
			notAvailLabel.setBorder(new EmptyBorder(0, (this.getWidth() - notAvailLabel.getPreferredSize().width) / 2, 10, (this.getWidth() - notAvailLabel.getPreferredSize().width) / 2));
			mainPanel.add(notAvailLabel);
			boolean anyBooksNotAvailable = false;
			for(Book b : searchResults) {
				if(!availResults.contains(b)) {
					anyBooksNotAvailable = true;
				}
			}
			if(anyBooksNotAvailable) {
				for(Book b : searchResults) {
					if(!availResults.contains(b)) {
						JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
						bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
						mainPanel.add(bookLabel);
						BookButton holdButton = new BookButton("Place Hold");
						holdButton.addActionListener(controller);
						holdButton.setActionCommand("putonhold");
						holdButton.setBook(b);
						mainPanel.add(holdButton);
						emptyLabel = new JLabel();
						emptyLabel.setBorder(new EmptyBorder(0, 0, 25, this.getWidth()));
						mainPanel.add(emptyLabel);
					}
				}
			}
			else {
				JLabel noUnavailLabel = new JLabel("No Unavailable Books Meet this Query");
				noUnavailLabel.setBorder(new EmptyBorder(0, (this.getWidth() - noUnavailLabel.getPreferredSize().width) / 2, 10, (this.getWidth() - noUnavailLabel.getPreferredSize().width) / 2));
				mainPanel.add(noUnavailLabel);
			}
		}
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroller);
		
		this.setVisible(true);
	}
	
	private void setUpListMenu() {
		if(scroller != null)
			this.remove(scroller);
		scroller = null;
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		
		JMenuBar menuBar = new JMenuBar();
		JLabel libraryMessage = new JLabel("List Library");
		menuBar.add(libraryMessage);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("borrowermain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - libraryMessage.getWidth() - menuButton.getWidth() - 180));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(50, this.getWidth(), 0, 0));
		mainPanel.add(emptyLabel);
		
		JLabel menuLabel = new JLabel("Please select from the options below");
		menuLabel.setBorder(new EmptyBorder(0, (this.getWidth() - menuLabel.getWidth()) / 2, 50, (this.getWidth() - menuLabel.getWidth()) / 2));
		mainPanel.add(menuLabel);
		
		JButton byAuthorButton = new JButton("List All, Sorted By Author");
		byAuthorButton.setActionCommand("listauthor");
		byAuthorButton.addActionListener(controller);
		JButton byTitleButton = new JButton("List All, Sorted by Title");
		byTitleButton.setActionCommand("listtitle");
		byTitleButton.addActionListener(controller);
		mainPanel.add(byAuthorButton);
		mainPanel.add(byTitleButton);
		
		this.setVisible(true);
	}
	
	/*
	 * void setUpListResults(boolean all, boolean title) -- sets up the view to show the results
	 * of listing the library. If title is true, lists the library by title. If title is false, lists the
	 * library by author.
	 */
	private void setUpListResults(boolean title) {
		this.remove(mainPanel);
		mainPanel = new BookPanel(this.getWidth());
		
		JMenuBar menuBar = new JMenuBar();
		JLabel listLabel = new JLabel("List Results");
		menuBar.add(listLabel);
		JMenu backMenu = new JMenu("Go Back");
		JMenuItem menuItem = new JMenuItem("Back to List");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("list");
		backMenu.add(menuItem);
		menuItem = new JMenuItem("Main Menu");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("borrowermain");
		backMenu.add(menuItem);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, this.getWidth() - 15 - listLabel.getPreferredSize().width - backMenu.getPreferredSize().width, 0, 0));
		menuBar.add(emptyLabel);
		menuBar.add(backMenu);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		JLabel resultsLabel = new JLabel("List Results:");
		resultsLabel.setBorder(new EmptyBorder(0, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2, 25, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2));
		mainPanel.add(resultsLabel);
		
		ArrayList<Book> books;
		
		if(title)
			books = library.getAllBooksByTitle();
		else
			books = library.getAllBooksByAuthor();
		
		Borrower borrower = (Borrower) currentUser;
		for(Book b : books) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			if(borrower.hasBook(b)) {
				BookButton alreadyCheckedOutButton = new BookButton("Already Checked Out");
				mainPanel.add(alreadyCheckedOutButton);
			}
			else if(library.searchAvailBooksByTitle(b.title).contains(b)) {
				BookButton checkoutButton = new BookButton("Checkout");
				checkoutButton.addActionListener(controller);
				checkoutButton.setActionCommand("checkout");
				checkoutButton.setBook(b);
				mainPanel.add(checkoutButton);
			}
			else {
				BookButton holdButton = new BookButton("Place Hold");
				holdButton.addActionListener(controller);
				holdButton.setActionCommand("putonhold");
				holdButton.setBook(b);
				mainPanel.add(holdButton);
			}
			emptyLabel = new JLabel();
			emptyLabel.setBorder(new EmptyBorder(0, 0, 25, this.getWidth()));
			mainPanel.add(emptyLabel);
		}
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroller);
		
		this.setVisible(true);
	}
	
	private void setUpRecommendations() {
		this.remove(mainPanel);
		mainPanel = new BookPanel(this.getWidth());
		
		
		JMenuBar menuBar = new JMenuBar();
		JLabel recommendationsMessage = new JLabel("Your Recommendations");
		menuBar.add(recommendationsMessage);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("borrowermain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - 255));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroller);
		
		JLabel resultsLabel = new JLabel("The Following Books are Recommended Based on Other Users' Activity:");
		resultsLabel.setBorder(new EmptyBorder(0, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2, 25, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2));
		mainPanel.add(resultsLabel);
		
		Borrower borrower = (Borrower) currentUser;
		ArrayList<Book> books = library.mostPopular();
		
		for(Book b : books) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			if(borrower.hasBook(b)) {
				BookButton alreadyCheckedOutButton = new BookButton("Already Checked Out");
				mainPanel.add(alreadyCheckedOutButton);
			}
			else if(library.searchAvailBooksByTitle(b.title).contains(b)) {
				BookButton checkoutButton = new BookButton("Checkout");
				checkoutButton.addActionListener(controller);
				checkoutButton.setActionCommand("checkout");
				checkoutButton.setBook(b);
				mainPanel.add(checkoutButton);
			}
			else {
				BookButton holdButton = new BookButton("Place Hold");
				holdButton.addActionListener(controller);
				holdButton.setActionCommand("putonhold");
				holdButton.setBook(b);
				mainPanel.add(holdButton);
			}
			emptyLabel = new JLabel();
			emptyLabel.setBorder(new EmptyBorder(0, 0, 25, this.getWidth()));
			mainPanel.add(emptyLabel);
		}
		
		this.setVisible(true);
	}
	
	public void search(String search, boolean title) {
		this.setTitle("Search Results");
		this.setSize(600, 400);
		this.setUpSearchResults(search, title);
	}
	
	public void list(boolean title) {
		this.setTitle("List Results");
		this.setSize(600, 400);
		this.setUpListResults(title);
	}
	
	public void changePage(String page) {
		if(page.toLowerCase().equals("home")) {
			this.setTitle("Home Page");
			this.setSize(400, 400);
			this.setUpHome(false);
		}
		else if(page.toLowerCase().equals("login")) {
			this.setTitle("Login");
			this.setSize(600, 400);
			this.setUpLogin(true);
		}
		else if(page.toLowerCase().equals("loginagain")) {
			this.setTitle("Login");
			this.setSize(600, 400);
			this.setUpLogin(false);
		}
		else if(page.toLowerCase().equals("loginpw")) {
			this.setUpLoginPW();
		}
		else if(page.toLowerCase().equals("stafflogin")) {
			this.setTitle("Login");
			this.setSize(600, 400);
			this.setUpStaffLogin(true);
		}
		else if(page.toLowerCase().equals("staffloginagain")) {
			this.setTitle("Login");
			this.setSize(600, 400);
			this.setUpStaffLogin(false);
		}
		else if(page.toLowerCase().equals("staffloginpw")) {
			this.setUpStaffLoginPW();
		}
		else if(page.toLowerCase().equals("createacc")) {
			this.setTitle("Create Account");
			this.setSize(600, 400);
			this.setUpCreateAccount(true);
		}
		else if(page.toLowerCase().equals("createaccagain")) {
			this.setTitle("Create Account");
			this.setSize(600, 400);
			this.setUpCreateAccount(false);
		}
		else if(page.toLowerCase().equals("createaccpw")) {
			this.setUpCreateAccountPW(true);
		}
		else if(page.toLowerCase().equals("createaccpwagain")) {
			this.setUpCreateAccountPW(false);
		}
		else if(page.toLowerCase().equals("staffmain")) {
			this.setTitle("Main Menu");
			this.setSize(600, 400);
			this.setUpStaffMain();
		}
		else if(page.toLowerCase().equals("borrowermain")) {
			this.setTitle("Main Menu");
			this.setSize(600, 400);
			this.setUpBorrowerMain();
		}
		else if(page.toLowerCase().equals("homeagain")) { 
			this.currentUser = null;
			this.setTitle("Home Page");
			this.setSize(400, 400);
			this.setUpHome(false);
		}
		else if(page.toLowerCase().equals("mylibrary")) {
			this.setTitle("Library");
			this.setSize(600, 400);
			this.setUpUserLibrary();
		}
		else if(page.toLowerCase().equals("borrowersearch")) {
			this.setTitle("Search");
			this.setSize(600, 400);
			this.setUpBorrowerSearch();
		}
		else if(page.toLowerCase().equals("searchbartitle")) {
			this.setTitle("Search");
			this.setSize(600, 400);
			this.setUpSearchBar(true);
		}
		else if(page.toLowerCase().equals("searchbarauthor")) {
			this.setTitle("Search");
			this.setSize(600, 400);
			this.setUpSearchBar(false);
		}
		else if(page.toLowerCase().equals("listmenu")) {
			this.setTitle("List");
			this.setSize(600, 400);
			this.setUpListMenu();
		}
		else if(page.toLowerCase().equals("recommendations")) {
			this.setTitle("Recommendations");
			this.setSize(600, 400);
			this.setUpRecommendations();
		}
	}
	
	public void loginUser(User u, boolean staff) {
		this.currentUser = u;
		if(staff) {
			this.changePage("staffmain");
		}
		else {
			this.changePage("borrowermain");
		}
	}
	
	public static void main(String[] args) {
		View view = new View();
		/*User u = null;
		try {
			u = new Borrower("Etefen", "Etefen");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		view.loginUser(u, false);*/
	}
}
