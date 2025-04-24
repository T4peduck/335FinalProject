package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Borrower;
import model.Librarian;
import model.Library;
import model.User;
import view.View.BookButton;

public class Controller implements ActionListener{
	
	private static Library library;
	private HashMap<String, Borrower> borrowerList;
	private HashMap<String, Librarian> librarianList;
	private View view;
	private User currentUser;
	public JTextField text;
	public JPasswordField password;
	
	public Controller(Library library, View view) {
		this.library = library;
		this.view = view;
		text = new JTextField(20);
		password = new JPasswordField(20);
		borrowerList = new HashMap<String, Borrower>();
		librarianList = new HashMap<String, Librarian>();
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
						view.loginUser(borrowerList.get(text.getText()), false);
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
			//check if valid login
			//if valid login goto staff main menu
			//else
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
				currentUser = u;
				view.loginUser(u, false);
			}
			else
				view.changePage("createaccpwagain");
		}
		else if(command.equals("logout")) {
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
		else if(command.equals("search")) {
			view.changePage("borrowersearch");
		}
		else if(command.equals("borrowermain")) {
			view.changePage("borrowermain");
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
	}
}
