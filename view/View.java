/*
 * File: View.java
 * Author: Ethan Cushner
 * Purpose: Creates a GUI environment to interact with our 335 E-Library
 */

package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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

public class View extends JFrame {
	
	// A JButton which can hold a book as an instance variable
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
	
	// A JButton which can hold a String for a user's username as an instance variable
	public class UserButton extends JButton {
		private String username;
		
		public UserButton(String s) {
			super(s);
		}
		
		public String getUsername() {
			return username;
		}
		
		private void setUsername(String username) {
			this.username = username;
		}
	}
	
	// A JButton which can hold both a username and a book as instance variables
	public class BookUserButton extends JButton {
		private Book b;
		private String username;
		
		public BookUserButton(String s) {
			super(s);
		}
		
		public Book getBook() {
			return b;
		}
		
		private void setBook(Book b) {
			this.b = b;
		}
		
		public String getUsername() {
			return username;
		}
		
		private void setUsername(String username) {
			this.username = username;
		}
	}
	
	// A JPanel that implements the Scrollable interface to create a scrollable page.
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
	
	private Controller controller;
	private JPanel mainPanel;
	private JScrollPane scroller = null;
	
	public View() {
		controller = new Controller(this);
		this.setTitle("Home Page");
		this.setSize(400, 400);
		this.setUpHome(true);
	}
	
	// Sets up the home page
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
		
		// Have the code go through cleanup and terminate upon closing the GUI window
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent windowEvent) {
				controller.exit();
				System.exit(0);
			}
		});
		
		this.setVisible(true);
	}
	
	// Sets up the first half of the user login page for entering username
	// first tells the method whether or not this is the first time to the page -- if not, prints according error message
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
			errorLabel.setForeground(new Color(139, 0 ,0));
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
	
	// Sets up the second half of the user login page for entering password
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
		
		emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(25, (this.getWidth() - emptyLabel.getWidth()) /2, 25, (this.getWidth() - emptyLabel.getWidth()) /2));
		mainPanel.add(emptyLabel);
		
		JButton resetButton = new JButton("Reset Password");
		resetButton.setActionCommand("resetpw");
		resetButton.addActionListener(controller);
		mainPanel.add(resetButton);
		
		this.setVisible(true);
	}
	
	// Sets up the first half of the staff login page for entering username
	// first tells the method whether or not this is the first time to the page -- if not, prints according error message
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
			errorLabel.setForeground(new Color(139, 0, 0));
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
	
	// Sets up the second half of the staff login page for entering password
	private void setUpStaffLoginPW() {
		controller.text.removeActionListener(controller);
		controller.text.setEditable(false);
		
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(25, (this.getWidth() - emptyLabel.getWidth()) /2, 25, (this.getWidth() - emptyLabel.getWidth()) /2));
		mainPanel.add(emptyLabel);
		
		JLabel passwordLabel = new JLabel("Password:");
		mainPanel.add(passwordLabel);
		
		JPasswordField passwordField = controller.password;
		passwordField.setActionCommand("StaffPWentered");
		if(passwordField.getActionListeners().length == 0)
			passwordField.addActionListener(controller);
		passwordField.setToolTipText("");
		passwordField.setText("");
		mainPanel.add(passwordField);
		
		this.setVisible(true);
	}
	
	// Sets up the first page of the user create account for entering username
	// first tells the method whether or not this is the first time to the page -- if not, prints according error message
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
			JLabel loginLabel = new JLabel("Please enter your new account info in the fields below");
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
	
	// Sets up the second page of the user create account for entering password
	// first tells the method whether or not this is the first time to the page -- if not, prints according error message
	private void setUpCreateAccountPW(boolean first) {
		if(!first) {
			setUpCreateAccount(true);
		}
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(50, (this.getWidth() - emptyLabel.getWidth()) /2, 0, (this.getWidth() - emptyLabel.getWidth()) /2));
		mainPanel.add(emptyLabel);
		controller.password.setText("");
		controller.text.removeActionListener(controller);
		controller.text.setEditable(false);
		
		JLabel passwordInstructions = new JLabel("Passwords must contain at least 1 uppercase letter, lowercase letter,");
		passwordInstructions.setBorder(new EmptyBorder(0, (this.getWidth() - passwordInstructions.getPreferredSize().width) / 2, 0, (this.getWidth() - passwordInstructions.getPreferredSize().width) / 2));
		mainPanel.add(passwordInstructions);
		JLabel passwordInstructions2 = new JLabel("numerical character, and special character");
		passwordInstructions2.setBorder(new EmptyBorder(0, (this.getWidth() - passwordInstructions2.getPreferredSize().width) / 2, 0, (this.getWidth() - passwordInstructions2.getPreferredSize().width) / 2));
		mainPanel.add(passwordInstructions2);
		
		JLabel passwordLabel = new JLabel("Password:");
		mainPanel.add(passwordLabel);
		
		JPasswordField passwordField = controller.password;
		passwordField.setToolTipText("Enter a valid password containing at least 1 uppercase letter, lowercase letter, numerical character, and special character");
		passwordField.setActionCommand("NewPW");
		passwordField.setText("");
		if(passwordField.getActionListeners().length == 0)
			passwordField.addActionListener(controller);
		mainPanel.add(passwordField);
		
		if(!first) {
			JLabel errorLabel = new JLabel("Error: Invalid Password.");
			errorLabel.setBorder(new EmptyBorder(0, (this.getWidth() - errorLabel.getWidth()) / 2, 0, (this.getWidth() - errorLabel.getWidth()) / 2));
			errorLabel.setForeground(new Color(139, 0, 0));
			mainPanel.add(errorLabel);
		}
		
		this.setVisible(true);
	}
	
	// Sets up the first part of the reset password page for entering the old password
	// first tells the method whether or not this is the first time to the page -- if not, prints according error message
	private void setUpResetPassword(boolean first) {
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		setUpCreateAccount(true);
		
		if(!first) {
			JLabel errorLabel = new JLabel("Error: Incorrect Password.");
			JLabel emptyLabel = new JLabel("");
			emptyLabel.setBorder(new EmptyBorder(35 - errorLabel.getPreferredSize().height, (this.getWidth() - emptyLabel.getWidth()) /2, 0, (this.getWidth() - emptyLabel.getWidth()) /2));
			mainPanel.add(emptyLabel);
			errorLabel.setBorder(new EmptyBorder(0, (this.getWidth() - errorLabel.getWidth()) / 2, 0, (this.getWidth() - errorLabel.getWidth()) / 2));
			mainPanel.add(errorLabel);
			controller.password.setText("");
			controller.text.removeActionListener(controller);
			controller.text.setEditable(false);
		}
		else {
			JLabel emptyLabel = new JLabel("");
			emptyLabel.setBorder(new EmptyBorder(35, (this.getWidth() - emptyLabel.getWidth()) /2, 0, (this.getWidth() - emptyLabel.getWidth()) /2));
			mainPanel.add(emptyLabel);
			controller.text.removeActionListener(controller);
			controller.text.setEditable(false);
		}
		
		JLabel passwordLabel = new JLabel("Old Password:");
		mainPanel.add(passwordLabel);
		
		JPasswordField passwordField = controller.password;
		passwordField.setToolTipText("");
		passwordField.setActionCommand("oldpwentered");
		passwordField.setText("");
		if(passwordField.getActionListeners().length == 0)
			passwordField.addActionListener(controller);
		mainPanel.add(passwordField);
		
		this.setVisible(true);
	}
	
	// Sets up the second page of the reset password page for entering a new password
	// first tells the method whether or not this is the first time to the page -- if not, prints according error message
	private void setUpNewPassword(boolean first) {
		this.setUpResetPassword(true);
		this.remove(controller.password);
		
		JPasswordField oldPassword = new JPasswordField(20);
		oldPassword.setText("aaaaaaaaaaaa");
		oldPassword.setEditable(false);
		mainPanel.add(oldPassword);
		
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(35, (this.getWidth() - emptyLabel.getWidth()) /2, 0, (this.getWidth() - emptyLabel.getWidth()) /2));
		mainPanel.add(emptyLabel);
		
		JLabel passwordLabel = new JLabel("New Password:");
		mainPanel.add(passwordLabel);
		
		JPasswordField newPassword = controller.password;
		newPassword.setToolTipText("Enter a valid password containing at least 1 uppercase letter, lowercase letter, numerical character, and special character");
		newPassword.setActionCommand("resetpwnewpw");
		if(newPassword.getActionListeners().length == 0)
			newPassword.addActionListener(controller);
		mainPanel.add(newPassword);
		
		this.setVisible(true);
	}
	
	// Sets up the main menu page for a Borrower
	private void setUpBorrowerMain() {
		if(scroller != null)
			this.remove(scroller);
		scroller = null;
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		JMenuBar menuBar = new JMenuBar();
		JLabel welcomeMessage = new JLabel("Welcome, " + controller.getUserName());
		menuBar.add(welcomeMessage);
		JButton logoutButton = new JButton("Logout");
		logoutButton.setActionCommand("logout");
		logoutButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - welcomeMessage.getPreferredSize().width - logoutButton.getPreferredSize().width - 20));
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
		JButton mostPopularButton = new JButton("Most Popular");
		mostPopularButton.setActionCommand("mostpopular");
		mostPopularButton.addActionListener(controller);
		mainPanel.add(libraryButton);
		mainPanel.add(searchButton);
		mainPanel.add(listButton);
		mainPanel.add(recommendButton);
		mainPanel.add(mostPopularButton);
		
		this.setVisible(true);
	}
	
	// Sets up the main menu page for a Librarian
	private void setUpStaffMain() {
		if(scroller != null)
			this.remove(scroller);
		scroller = null;
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		JMenuBar menuBar = new JMenuBar();
		JLabel welcomeMessage = new JLabel("Welcome, " + controller.getUserName());
		menuBar.add(welcomeMessage);
		JButton logoutButton = new JButton("Logout");
		logoutButton.setActionCommand("logout");
		logoutButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - welcomeMessage.getPreferredSize().width - logoutButton.getPreferredSize().width - 20));
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
		
		JButton libraryButton = new JButton("Add Books to Library");
		libraryButton.setActionCommand("addbooks");
		libraryButton.addActionListener(controller);
		JButton searchButton = new JButton("Search");
		searchButton.setActionCommand("staffsearch");
		searchButton.addActionListener(controller);
		JButton listButton = new JButton("List Library");
		listButton.setActionCommand("stafflist");
		listButton.addActionListener(controller);
		JButton recommendButton = new JButton("Recommendations");
		recommendButton.setActionCommand("staffrecommended");
		recommendButton.addActionListener(controller);
		JButton mostPopularButton = new JButton("Most Popular");
		mostPopularButton.setActionCommand("staffmostpopular");
		mostPopularButton.addActionListener(controller);
		JButton usersButton = new JButton("Manage Borrowers");
		usersButton.setActionCommand("manageusers");
		usersButton.addActionListener(controller);
		mainPanel.add(libraryButton);
		mainPanel.add(searchButton);
		mainPanel.add(listButton);
		mainPanel.add(recommendButton);
		mainPanel.add(mostPopularButton);
		mainPanel.add(usersButton);
		
		this.setVisible(true);
	}
	
	//Sets up a page for a borrower to see their individual checkouts, holds, and history
	private void setUpUserLibrary() {
		if(scroller != null) 
			this.remove(scroller);
		scroller = null;
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
		
		for(Book b : controller.getCheckedOut()) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			BookButton returnButton = new BookButton("Return");
			returnButton.addActionListener(controller);
			returnButton.setActionCommand("return");
			returnButton.setBook(b);
			mainPanel.add(returnButton);
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}
		
		JLabel onHoldMessage = new JLabel("On Hold:");
		onHoldMessage.setBorder(new EmptyBorder(0, (this.getWidth() - onHoldMessage.getWidth()) / 2, 0, (this.getWidth() - onHoldMessage.getWidth()) / 2));
		mainPanel.add(onHoldMessage);
		
		for(Book b : controller.getHolds()) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			JLabel holdsLabel = new JLabel("Holds in front of you: " + controller.getHoldPosition(b));
			mainPanel.add(holdsLabel);
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}
		
		JLabel historyMessage = new JLabel("Previously Checked Out:");
		historyMessage.setBorder(new EmptyBorder(0, (this.getWidth() - historyMessage.getPreferredSize().width) / 2, 0, (this.getWidth() - historyMessage.getPreferredSize().width) / 2));
		mainPanel.add(historyMessage);
		
		for(Book b : controller.getHistory()) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroller);
		
		this.setVisible(true);
	}
	
	// Sets up the first part of the borrower's search page, where a borrower chooses search type
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
	
	// Sets up the second part of borrower searching, where the user can enter an appropriate query
	// title tells the method whether the search is by title (true) or by author (false)
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
	
	// Sets up the final page of the borrower search, the results of the search
	// search is the query entered in the previous page
	// title tells the method whether the search is by title (true) or by author (false)
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
			searchResults = controller.searchAllBooksByTitle(search);
			availResults = controller.searchAvailBooksByTitle(search);
		}
		else {
			searchResults = controller.searchAllBooksByAuthor(search);
			availResults = controller.searchAvailBooksByAuthor(search);
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
					emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
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
						if(controller.getHoldPosition(b) != 0) {
							JButton alreadyButton = new JButton("Already on Hold");
							mainPanel.add(alreadyButton);
						}
						else {
							BookButton holdButton = new BookButton("Place Hold");
							holdButton.addActionListener(controller);
							holdButton.setActionCommand("putonhold");
							holdButton.setBook(b);
							mainPanel.add(holdButton);
						}
						emptyLabel = new JLabel();
						emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
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
	
	// Sets up the menu for listing for a borrower, which lets the borrower choose whether to sort by title or author
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
	 * void setUpListResults(boolean title) -- sets up the view to show the results
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
			books = controller.getAllBooksByTitle();
		else
			books = controller.getAllBooksByAuthor();
		
		for(Book b : books) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			int status = controller.checkBookStatus(b);
			if(status == -1) {
				BookButton alreadyCheckedOutButton = new BookButton("Already Checked Out");
				mainPanel.add(alreadyCheckedOutButton);
			}
			else if(status == 1) {
				BookButton checkoutButton = new BookButton("Checkout");
				checkoutButton.addActionListener(controller);
				checkoutButton.setActionCommand("checkout");
				checkoutButton.setBook(b);
				mainPanel.add(checkoutButton);
			}
			else {
				if(controller.getHoldPosition(b) != 0) {
					JButton alreadyOnHoldButton = new JButton("Already on Hold");
					mainPanel.add(alreadyOnHoldButton);
				}
				else {
					BookButton holdButton = new BookButton("Place Hold");
					holdButton.addActionListener(controller);
					holdButton.setActionCommand("putonhold");
					holdButton.setBook(b);
					mainPanel.add(holdButton);
				}
			}
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroller);
		
		this.setVisible(true);
	}
	
	// Sets up the user recommendations page, which shows the user all books recommended by librarians
	private void setUpRecommendations() {
		this.remove(mainPanel);
		mainPanel = new BookPanel(this.getWidth());
		
		
		JMenuBar menuBar = new JMenuBar();
		JLabel recommendationsMessage = new JLabel("Recommended");
		menuBar.add(recommendationsMessage);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("borrowermain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - 260));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroller);
		
		JLabel resultsLabel = new JLabel("The Following Books are the Recommended by Our Librarians:");
		resultsLabel.setBorder(new EmptyBorder(0, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2, 25, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2));
		mainPanel.add(resultsLabel);
	
		ArrayList<Book> books = controller.getRecommended();
		
		for(Book b : books) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			int status = controller.checkBookStatus(b);
			if(status == -1) {
				BookButton alreadyCheckedOutButton = new BookButton("Already Checked Out");
				mainPanel.add(alreadyCheckedOutButton);
			}
			else if(status == 1) {
				BookButton checkoutButton = new BookButton("Checkout");
				checkoutButton.addActionListener(controller);
				checkoutButton.setActionCommand("checkout");
				checkoutButton.setBook(b);
				mainPanel.add(checkoutButton);
			}
			else {
				if(controller.getHoldPosition(b) != 0) {
					JButton alreadyOnHoldButton = new JButton("Already on Hold");
					mainPanel.add(alreadyOnHoldButton);
				}
				else {
					BookButton holdButton = new BookButton("Place Hold");
					holdButton.addActionListener(controller);
					holdButton.setActionCommand("putonhold");
					holdButton.setBook(b);
					mainPanel.add(holdButton);
				}
			}
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}
		
		this.setVisible(true);
	}
	
	// Sets up the user most popular page, which shows up to 10 of the most checked out books in the library
	private void setUpMostPopular() {
		this.remove(mainPanel);
		mainPanel = new BookPanel(this.getWidth());
		
		
		JMenuBar menuBar = new JMenuBar();
		JLabel recommendationsMessage = new JLabel("Most Popular");
		menuBar.add(recommendationsMessage);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("borrowermain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - 190));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroller);
		
		JLabel resultsLabel = new JLabel("The Following Books are the Most Popular in Our Library:");
		resultsLabel.setBorder(new EmptyBorder(0, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2, 25, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2));
		mainPanel.add(resultsLabel);
	
		ArrayList<Book> books = controller.mostPopular();
		
		for(Book b : books) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			int status = controller.checkBookStatus(b);
			if(status == -1) {
				BookButton alreadyCheckedOutButton = new BookButton("Already Checked Out");
				mainPanel.add(alreadyCheckedOutButton);
			}
			else if(status == 1) {
				BookButton checkoutButton = new BookButton("Checkout");
				checkoutButton.addActionListener(controller);
				checkoutButton.setActionCommand("checkout");
				checkoutButton.setBook(b);
				mainPanel.add(checkoutButton);
			}
			else {
				if(controller.getHoldPosition(b) != 0) {
					JButton alreadyOnHoldButton = new JButton("Already on Hold");
					mainPanel.add(alreadyOnHoldButton);
				}
				else {
					BookButton holdButton = new BookButton("Place Hold");
					holdButton.addActionListener(controller);
					holdButton.setActionCommand("putonhold");
					holdButton.setBook(b);
					mainPanel.add(holdButton);
				}
			}
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}
		
		this.setVisible(true);
	}
	
	// Sets up first part of the add books page, which lets a librarian choose whether to add by title and author or a time range
	private void setUpAddBooks() {
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);

		JMenuBar menuBar = new JMenuBar();
		JLabel libraryMessage = new JLabel("Add Books");
		menuBar.add(libraryMessage);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("staffmain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - libraryMessage.getPreferredSize().width - menuButton.getPreferredSize().width - 20));
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
		
		JButton specificButton = new JButton("Add with Author and Title");
		specificButton.setActionCommand("addspecific");
		specificButton.addActionListener(controller);
		JButton rangeButton = new JButton("Add with Time Range");
		rangeButton.setActionCommand("addrange");
		rangeButton.addActionListener(controller);
		mainPanel.add(specificButton);
		mainPanel.add(rangeButton);
		
		this.setVisible(true);
	}
	
	// Sets up the first page for adding a specific book, which allows the librarian to input the book's title
	// first tells the method whether or not this is the first time to the page -- if not, prints according error message
	private void setUpAddSpecific(boolean first) {
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu searchMenu = new JMenu("Add Type Menu");
		JMenuItem menuItem = new JMenuItem("Add by Specifics");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("addspecific");
		searchMenu.add(menuItem);
		menuItem = new JMenuItem("Add by Range");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("addrange");
		searchMenu.add(menuItem);
		menuBar.add(searchMenu);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("staffmain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - 220));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		if(first) {
			emptyLabel = new JLabel("");
			emptyLabel.setBorder(new EmptyBorder(50, this.getWidth(), 0, 0));
			mainPanel.add(emptyLabel);
		}
		else {
			JLabel errorLabel = new JLabel("Error: Invalid Input Entered");
			errorLabel.setForeground(new Color(139, 0, 0));
			mainPanel.add(errorLabel);
			emptyLabel = new JLabel();
			emptyLabel.setBorder(new EmptyBorder(30, this.getWidth(), 0, 0));
			mainPanel.add(emptyLabel);
		}
		
		
		
		JLabel titleLabel = new JLabel("Enter title: ");
		titleLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
		mainPanel.add(titleLabel);
		
		JTextField text = controller.text;
		text.setText("");
		text.setEditable(true);
		text.setToolTipText("");
		text.setActionCommand("addtitleentered");
		if(text.getActionListeners().length == 0)
			text.addActionListener(controller);
		mainPanel.add(text);
		
		this.setVisible(true);
	}
	
	// Sets up the second page for adding a specific book, which allows the librarian to enter the author of the book
	// first tells the method whether or not this is the first time to the page -- if not, prints according error message
	private void setUpAddAuthor(String title) {
		this.setUpAddSpecific(true);
		mainPanel.remove(controller.text);
		
		JTextField titleField = new JTextField(20);
		titleField.setText(title);
		titleField.setEditable(false);
		mainPanel.add(titleField);
		
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(50, this.getWidth(), 0, 0));
		mainPanel.add(emptyLabel);
		
		JLabel authorLabel = new JLabel("Enter author: ");
		authorLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
		mainPanel.add(authorLabel);
		
		JTextField authorField = controller.text;
		authorField.setText("");
		authorField.setEditable(true);
		authorField.setToolTipText("Enter the primary author's name, first and last separated by a space");
		authorField.setActionCommand("addauthorentered");
		if(authorField.getActionListeners().length == 0)
			authorField.addActionListener(controller);
		mainPanel.add(authorField);
		
		this.setVisible(true);
	}
	
	// Sets up the first part of the add by time range page, which allows a librarian to input the start of a time range
	// first tells the method whether or not this is the first time to the page -- if not, prints according error message
	private void setUpAddRange(boolean first) {
		this.remove(mainPanel);
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu searchMenu = new JMenu("Add Type Menu");
		JMenuItem menuItem = new JMenuItem("Add by Specifics");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("addspecific");
		searchMenu.add(menuItem);
		menuItem = new JMenuItem("Add by Range");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("addrange");
		searchMenu.add(menuItem);
		menuBar.add(searchMenu);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("staffmain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - 220));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(40, this.getWidth(), 0, 0));
		mainPanel.add(emptyLabel);
		
		if(!first) {
			JLabel errorLabel = new JLabel("Error: Invalid Input");
			errorLabel.setBorder(new EmptyBorder(0, (this.getWidth() - errorLabel.getPreferredSize().width) / 2, 0, (this.getWidth() - errorLabel.getPreferredSize().width) / 2));
			mainPanel.add(errorLabel);
		}
		
		JLabel titleLabel = new JLabel("Enter start of range: ");
		titleLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
		mainPanel.add(titleLabel);
		
		JTextField text = controller.text;
		text.setText("");
		text.setEditable(true);
		text.setToolTipText("Enter a string of numbers corresponding to a year");
		text.setActionCommand("addstartrangeentered");
		if(text.getActionListeners().length == 0)
			text.addActionListener(controller);
		mainPanel.add(text);
		
		this.setVisible(true);
	}
	
	// Sets up the second part of the add by time range page, which allows the librarian to input the end of the time range
	// first tells the method whether or not this is the first time to the page -- if not, prints according error message
	private void setUpAddEndRange(String start) {
		this.setUpAddRange(true);
		mainPanel.remove(controller.text);
		
		JTextField startField = new JTextField(20);
		startField.setText(start);
		startField.setEditable(false);
		mainPanel.add(startField);
		
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(50, this.getWidth(), 0, 0));
		mainPanel.add(emptyLabel);
		
		JLabel endLabel = new JLabel("Enter end of range: ");
		endLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
		mainPanel.add(endLabel);
		
		JTextField endField = controller.text;
		endField.setText("");
		endField.setEditable(true);
		endField.setToolTipText("Enter a string of numbers corresponding to a year");
		endField.setActionCommand("addendrangeentered");
		if(endField.getActionListeners().length == 0)
			endField.addActionListener(controller);
		mainPanel.add(endField);
		
		this.setVisible(true);
	}
	
	// Sets up the first part of the staff search page, which allows a librarian to choose between a search by title and a search by author
	private void setUpStaffSearch() {
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
		menuButton.setActionCommand("staffmain");
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
		byAuthorButton.setActionCommand("staffbyauthor");
		byAuthorButton.addActionListener(controller);
		JButton byTitleButton = new JButton("Search by Title");
		byTitleButton.setActionCommand("staffbytitle");
		byTitleButton.addActionListener(controller);
		mainPanel.add(byAuthorButton);
		mainPanel.add(byTitleButton);
		
		this.setVisible(true);
	}
	
	// Sets up the search bar for staff searching, where a librarian can input a proper query
	// title tells the method whether the search is by title (true) or by author (false)
	private void setUpStaffSearchBar(boolean title) {
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
		menuButton.setActionCommand("staffmain");
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
			text.setActionCommand("stafftitlesearchentered");
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
			text.setActionCommand("staffauthorsearchentered");
			if(text.getActionListeners().length == 0)
				text.addActionListener(controller);
			mainPanel.add(titleLabel);
			mainPanel.add(text);
		}
			
		
		this.setVisible(true);
	}
	
	// Sets up the page to show the results of the librarians search
	// search is the query entered
	// title tells the method whether the search is by title (true) or by author (false)
	private void setUpStaffSearchResults(String search, boolean title) {
		this.remove(mainPanel);
		mainPanel = new BookPanel(this.getWidth());
		
		JMenuBar menuBar = new JMenuBar();
		JLabel searchLabel = new JLabel("Search Results");
		menuBar.add(searchLabel);
		JMenu backMenu = new JMenu("Go Back");
		JMenuItem menuItem = new JMenuItem("Back to Search");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("staffsearch");
		backMenu.add(menuItem);
		menuItem = new JMenuItem("Main Menu");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("staffmain");
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
			searchResults = controller.searchAllBooksByTitle(search);
			availResults = controller.searchAvailBooksByTitle(search);
		}
		else {
			searchResults = controller.searchAllBooksByAuthor(search);
			availResults = controller.searchAvailBooksByAuthor(search);
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
					JLabel infoLabel = new JLabel("Number of Checkouts: " + controller.getCheckoutNum(b));
					bookLabel.setBorder(new EmptyBorder(0, (this.getWidth() - bookLabel.getPreferredSize().width) / 2, 0, (this.getWidth() - bookLabel.getPreferredSize().width) / 2));
					infoLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
					mainPanel.add(bookLabel);
					mainPanel.add(infoLabel);
					if(controller.searchAvailBooksByTitle(b.title).contains(b)) {
						BookButton checkOutButton = new BookButton("Remove Book from Library");
						checkOutButton.addActionListener(controller);
						checkOutButton.setActionCommand("remove");
						checkOutButton.setBook(b);
						mainPanel.add(checkOutButton);
					}
					if(!controller.mostPopular().contains(b)) {
						BookButton recommendButton = new BookButton("Recommend Book");
						recommendButton.setActionCommand("recommend");
						recommendButton.addActionListener(controller);
						recommendButton.setBook(b);
						mainPanel.add(recommendButton);
					}
					emptyLabel = new JLabel();
					emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
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
						JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME + " Number of Holds: " + controller.getNumHolds(b) + " Number of checkouts: " + controller.getCheckoutNum(b));
						bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
						mainPanel.add(bookLabel);
						if(!controller.mostPopular().contains(b)) {
							BookButton recommendButton = new BookButton("Recommend Book");
							recommendButton.setActionCommand("recommend");
							recommendButton.addActionListener(controller);
							recommendButton.setBook(b);
							mainPanel.add(recommendButton);
						}
						emptyLabel = new JLabel();
						emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
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
	
	// Sets up the menu for staff listing, which lets a librarian choose between listing sorted by title or by author
	private void setUpStaffList() {
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
		menuButton.setActionCommand("staffmain");
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
		byAuthorButton.setActionCommand("stafflistauthor");
		byAuthorButton.addActionListener(controller);
		JButton byTitleButton = new JButton("List All, Sorted by Title");
		byTitleButton.setActionCommand("stafflisttitle");
		byTitleButton.addActionListener(controller);
		mainPanel.add(byAuthorButton);
		mainPanel.add(byTitleButton);
		
		this.setVisible(true);
	}
	
	// Sets up the page to show the results of listing for staff
	// title tells the method whether the search is by title (true) or by author (false)
	private void setUpStaffListResults(boolean title) {
		this.remove(mainPanel);
		mainPanel = new BookPanel(this.getWidth());
		
		JMenuBar menuBar = new JMenuBar();
		JLabel listLabel = new JLabel("List Results");
		menuBar.add(listLabel);
		JMenu backMenu = new JMenu("Go Back");
		JMenuItem menuItem = new JMenuItem("Back to List");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("stafflist");
		backMenu.add(menuItem);
		menuItem = new JMenuItem("Main Menu");
		menuItem.addActionListener(controller);
		menuItem.setActionCommand("staffmain");
		backMenu.add(menuItem);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, this.getWidth() - 20 - listLabel.getPreferredSize().width - backMenu.getPreferredSize().width, 0, 0));
		menuBar.add(emptyLabel);
		menuBar.add(backMenu);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		JLabel resultsLabel = new JLabel("List Results:");
		resultsLabel.setBorder(new EmptyBorder(0, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2, 25, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2));
		mainPanel.add(resultsLabel);
		
		ArrayList<Book> books;
		
		if(title)
			books = controller.getAllBooksByTitle();
		else
			books = controller.getAllBooksByAuthor();
		
		for(Book b : books) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, (this.getWidth() - bookLabel.getPreferredSize().width) / 2, 0, (this.getWidth() - bookLabel.getPreferredSize().width) / 2));
			mainPanel.add(bookLabel);
			String information = "Available: ";
			if(controller.searchAvailBooksByTitle(b.title).contains(b)) 
				information += "Yes. Number of Checkouts: " + controller.getCheckoutNum(b);
			else
				information += "No. Number of Holds: " + controller.getNumHolds(b) + " Number of Checkouts: " + controller.getCheckoutNum(b);
			JLabel informationLabel = new JLabel(information);
			informationLabel.setBorder(new EmptyBorder(0, 0, 0, 5));
			mainPanel.add(informationLabel);
			if(controller.searchAvailBooksByTitle(b.title).contains(b)) {
				BookButton removeButton = new BookButton("Remove Book from Library");
				removeButton.setActionCommand("remove");
				removeButton.addActionListener(controller);
				removeButton.setBook(b);
				mainPanel.add(removeButton);
			}
			if(!controller.getLibrarianRecommended().contains(b)) {
				BookButton recommendButton = new BookButton("Recommend Book");
				recommendButton.setActionCommand("recommend");
				recommendButton.addActionListener(controller);
				recommendButton.setBook(b);
				mainPanel.add(recommendButton);
			}
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroller);
		
		this.setVisible(true);
	}
	
	// Sets up the page for the staff to see the up to 10 most popular books in the library
	private void setUpStaffMostPopular() {
		this.remove(mainPanel);
		mainPanel = new BookPanel(this.getWidth());
		
		
		JMenuBar menuBar = new JMenuBar();
		JLabel recommendationsMessage = new JLabel("Most Popular");
		menuBar.add(recommendationsMessage);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("staffmain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - 190));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroller);
		
		JLabel resultsLabel = new JLabel("The Following Books are the Most Popular in the Library:");
		resultsLabel.setBorder(new EmptyBorder(0, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2, 25, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2));
		mainPanel.add(resultsLabel);
	
		ArrayList<Book> books = controller.mostPopular();
		
		for(Book b : books) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}
		
		this.setVisible(true);
	}
	
	// Sets up the page for a librarian to see their recommended and stop recommending books on the list if they so choose
	private void setUpStaffRecommended() {
		if(scroller != null)
			this.remove(scroller);
		scroller = null;
		this.remove(mainPanel);
		mainPanel = new BookPanel(this.getWidth());
		
		
		JMenuBar menuBar = new JMenuBar();
		JLabel recommendationsMessage = new JLabel("Recommended");
		menuBar.add(recommendationsMessage);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("staffmain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - 210));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroller);
		
		JLabel resultsLabel = new JLabel("These are the Books You Currently Recommend:");
		resultsLabel.setBorder(new EmptyBorder(0, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2, 25, (this.getWidth() - resultsLabel.getPreferredSize().width) / 2));
		mainPanel.add(resultsLabel);
		
		for(Book b: controller.getLibrarianRecommended()) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
			mainPanel.add(bookLabel);
			BookButton stopRecommendButton = new BookButton("Stop Recommending");
			stopRecommendButton.setActionCommand("stoprecommend");
			stopRecommendButton.addActionListener(controller);
			stopRecommendButton.setBook(b);
			mainPanel.add(stopRecommendButton);
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}
		
		this.setVisible(true);
	}
	
	// Sets up the page for the librarians to be able to see all active accounts
	private void setUpManageUsers() {
		if(scroller != null)
			this.remove(scroller);
		scroller = null;
		this.remove(mainPanel);
		mainPanel = new BookPanel(this.getWidth());
		
		JMenuBar menuBar = new JMenuBar();
		JLabel manageMessage = new JLabel("Manage Borrowers");
		menuBar.add(manageMessage);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("staffmain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - manageMessage.getPreferredSize().width - 20));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		JLabel messageLabel = new JLabel("Active Borrower Accounts");
		messageLabel.setBorder(new EmptyBorder(0, (this.getWidth() - messageLabel.getPreferredSize().width) / 2, 0, (this.getWidth() - messageLabel.getPreferredSize().width) / 2));
		mainPanel.add(messageLabel);
		
		for(String s : controller.getUsernames()) {
			JLabel usernameLabel = new JLabel(s);
			usernameLabel.setBorder(new EmptyBorder(0, 0, 0, 10));
			mainPanel.add(usernameLabel);
			UserButton userButton = new UserButton("Library");
			userButton.setActionCommand("viewuserlibrary");
			userButton.setUsername(s);
			userButton.addActionListener(controller);
			mainPanel.add(userButton);
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}

		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroller);
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(scroller);
		
		this.setVisible(true);
	}
	
	// Sets up the page for the librarian to be able to see a user's library, including checkouts, holds, and history
	// username is the username of the currently viewed user's library
	private void setUpManageUserLibrary(String username) {
		if(scroller != null) 
			this.remove(scroller);
		scroller = null;
		this.remove(mainPanel);
		mainPanel = new BookPanel(this.getWidth());
		
		
		JMenuBar menuBar = new JMenuBar();
		JLabel libraryMessage = new JLabel(username + "'s Library");
		menuBar.add(libraryMessage);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("staffmain");
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
		
		for(Book b : controller.getCheckedOut()) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			BookUserButton returnButton = new BookUserButton("Force to Return");
			returnButton.addActionListener(controller);
			returnButton.setActionCommand("forcereturn");
			returnButton.setUsername(username);
			returnButton.setBook(b);
			mainPanel.add(returnButton);
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}
		
		JLabel onHoldMessage = new JLabel("On Hold:");
		onHoldMessage.setBorder(new EmptyBorder(0, (this.getWidth() - onHoldMessage.getWidth()) / 2, 0, (this.getWidth() - onHoldMessage.getWidth()) / 2));
		mainPanel.add(onHoldMessage);
		
		for(Book b : controller.getHolds()) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			JLabel holdsLabel = new JLabel("Holds in front of them: " + controller.getHoldPosition(b));
			mainPanel.add(holdsLabel);
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}
		
		JLabel historyMessage = new JLabel("Previously Checked Out:");
		historyMessage.setBorder(new EmptyBorder(0, (this.getWidth() - historyMessage.getPreferredSize().width) / 2, 0, (this.getWidth() - historyMessage.getPreferredSize().width) / 2));
		mainPanel.add(historyMessage);
		
		for(Book b : controller.getHistory()) {
			JLabel bookLabel = new JLabel(b.title + " by " + b.authors.get(0).NAME);
			bookLabel.setBorder(new EmptyBorder(0, 0, 0, 25));
			mainPanel.add(bookLabel);
			emptyLabel = new JLabel();
			emptyLabel.setPreferredSize(new Dimension(this.getWidth(), 25));
			mainPanel.add(emptyLabel);
		}
		
		scroller = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.add(scroller);
		
		this.setVisible(true);
	}
	
	// Calls for changing the page to view a user's library
	public void openUserLibrary(String username) {
		this.setTitle(username +"'s Library");
		this.setSize(800, 600);
		this.setUpManageUserLibrary(username);
	}
	
	// Allows the controller to switch the add page while taking in the previously input data
	public void switchAddPage(String page, String input1) {
		if(page.toLowerCase().equals("addauthor")) {
			this.setTitle("Add Books");
			this.setSize(600, 400);
			this.setUpAddAuthor(input1);
		}
		else if(page.toLowerCase().equals("addendrange")) {
			this.setTitle("Add Books");
			this.setSize(600, 400);
			this.setUpAddEndRange(input1);
		}
	}
	
	// Calls for search results to be displayed for borrowers
	public void search(String search, boolean title) {
		this.setTitle("Search Results");
		this.setSize(800, 600);
		this.setUpSearchResults(search, title);
	}
	
	// Calls for search results to be displayed for librarians
	public void staffSearch(String search, boolean title) {
		this.setTitle("Search Results");
		this.setSize(800, 600);
		this.setUpStaffSearchResults(search, title);
	}
	
	// Calls for list results to be displayed for borrowers
	public void list(boolean title) {
		this.setTitle("List Results");
		this.setSize(800, 600);
		this.setUpListResults(title);
	}
	// Calls for list results to be displayed for librarians
	public void staffList(boolean title) {
		this.setTitle("List Results");
		this.setSize(800, 600);
		this.setUpStaffListResults(title);
	}
	
	// Calls to change the current page according to the String passed in
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
			this.setTitle("Home Page");
			this.setSize(400, 400);
			this.setUpHome(false);
		}
		else if(page.toLowerCase().equals("mylibrary")) {
			this.setTitle("Library");
			this.setSize(800, 600);
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
		else if(page.toLowerCase().equals("mostpopular")) {
			this.setTitle("Most Popular");
			this.setSize(600, 400);
			this.setUpMostPopular();
		}
		else if(page.toLowerCase().equals("resetpassword")) {
			this.setTitle("Reset Password");
			this.setSize(600, 400);
			this.setUpResetPassword(true);
		}
		else if(page.toLowerCase().equals("resetpwagain")) {
			this.setTitle("Reset Password");
			this.setSize(600, 400);
			this.setUpResetPassword(false);
		}
		else if(page.toLowerCase().equals("resetpwnewpw")) {
			this.setTitle("Reset Password");
			this.setSize(600, 400);
			this.setUpNewPassword(true);
		}
		else if(page.toLowerCase().equals("resetpwnewpwagain")) {
			this.setTitle("Reset Password");
			this.setSize(600, 400);
			this.setUpNewPassword(false);
		}
		else if(page.toLowerCase().equals("addbooks")) {
			this.setTitle("Add Books");
			this.setSize(600, 400);
			this.setUpAddBooks();
		}
		else if(page.toLowerCase().equals("addspecific")) {
			this.setTitle("Add Books");
			this.setSize(600, 400);
			this.setUpAddSpecific(true);
		}
		else if(page.toLowerCase().equals("addrange")) {
			this.setTitle("Add Books");
			this.setSize(600, 400);
			this.setUpAddRange(true);
		}
		else if(page.toLowerCase().equals("addrangeagain")) {
			this.setTitle("Add Books");
			this.setSize(600, 400);
			this.setUpAddRange(false);
		}
		else if(page.toLowerCase().equals("staffsearch")) {
			this.setTitle("Search");
			this.setSize(600, 400);
			this.setUpStaffSearch();
		}
		else if(page.toLowerCase().equals("staffsearchbartitle")) {
			this.setTitle("Search");
			this.setSize(600, 400);
			this.setUpStaffSearchBar(true);
		}
		else if(page.toLowerCase().equals("staffsearchbarauthor")) {
			this.setTitle("Search");
			this.setSize(600, 400);
			this.setUpStaffSearchBar(false);
		}
		else if(page.toLowerCase().equals("stafflist")) {
			this.setTitle("List Library");
			this.setSize(600, 400);
			this.setUpStaffList();
		}
		else if(page.toLowerCase().equals("staffmostpopular")) {
			this.setTitle("Most Popular");
			this.setSize(600, 400);
			this.setUpStaffMostPopular();
		}
		else if(page.toLowerCase().equals("staffrecommended")) {
			this.setTitle("Recommended");
			this.setSize(600, 400);
			this.setUpStaffRecommended();
		}
		else if(page.toLowerCase().equals("addspecificagain")) {
			this.setTitle("Add Books");
			this.setSize(600, 400);
			this.setUpAddSpecific(false);
		}
		else if(page.toLowerCase().equals("manageusers")) {
			this.setTitle("Manage Borrowers");
			this.setSize(600, 400);
			this.setUpManageUsers();
		}
	}
	
	// Logs in a user
	// staff tells the method if it is a staff member (true) or a borrower(false) logging in
	public void loginUser(boolean staff) {
		if(staff) {
			this.changePage("staffmain");
		}
		else {
			this.changePage("borrowermain");
		}
	}
	
	public static void main(String[] args) {
		View view = new View();
	}
}
