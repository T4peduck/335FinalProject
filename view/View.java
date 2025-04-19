package view;

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.security.NoSuchAlgorithmException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import model.Library;
import model.User;

public class View extends JFrame {
	
	private static Library library = new Library();
	private User currentUser;
	private Controller controller;
	private JPanel mainPanel;
	
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
		//mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		this.add(mainPanel);
		mainPanel.setBackground(new Color(0, 0, 0));
		
		JLabel label = new JLabel("Please select an option from the three below");
		label.setBorder(new EmptyBorder(0, (400 - label.getWidth()) / 2, 150, (400 - label.getWidth()) / 2));
		label.setForeground(new Color(255, 255, 255));
		label.setSize(400, 100);
		mainPanel.add(label);
		
		JButton accButton = new JButton("Create Account");
		//accButton.setBackground(new Color(50, 50, 50));
		//accButton.setForeground(new Color (100, 0, 0));
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
		
		JPasswordField passwordField = new JPasswordField(20);
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
		usernameField.setText("");
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
		mainPanel = new JPanel();
		this.add(mainPanel);
		
		JMenuBar menuBar = new JMenuBar();
		JLabel libraryMessage = new JLabel("Your Library");
		menuBar.add(libraryMessage);
		JButton menuButton = new JButton("Main Menu");
		menuButton.setActionCommand("borrowermain");
		menuButton.addActionListener(controller);
		JLabel emptyLabel = new JLabel("");
		emptyLabel.setBorder(new EmptyBorder(0, 0, 0, this.getWidth() - libraryMessage.getWidth() - menuButton.getWidth() - 185));
		menuBar.add(emptyLabel);
		menuBar.add(menuButton);
		menuBar.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
		mainPanel.add(menuBar);
		
		this.setVisible(true);
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
			this.setSize(600, 600);
			this.setUpStaffMain();
		}
		else if(page.toLowerCase().equals("borrowermain")) {
			this.setTitle("Main Menu");
			this.setSize(600, 600);
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
			this.setSize(1920, 1080);
			this.setUpUserLibrary();
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
		User u = null;
		try {
			u = new User("Etefen", "Etefen");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		view.loginUser(u, false);
	}
}
