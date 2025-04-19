package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

import model.Library;

public class Controller implements ActionListener{
	
	private Library library;
	private View view;
	public JTextField text;
	public JPasswordField password;
	
	public Controller(Library library, View view) {
		this.library = library;
		this.view = view;
		text = new JTextField(20);
		password = new JPasswordField(20);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		if(command.equals("log")) {
			view.changePage("login");
		}
		else if(command.equals("acc")) {
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
			//check if valid login
			//if valid login, go to main menu
			//else
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
			//check if username valid
			//if valid
			view.changePage("createaccpw");
			//else
			//view.changePage("createaccagain");
		}
		else if(command.equals("NewPW")) {
			//check if password valid
			//if valid, go to main menu
			//else
			view.changePage("createaccpwagain");
		}
		else if(command.equals("logout")) {
			view.changePage("home");
		}
		else if(command.equals("mylibrary")) {
			view.changePage("mylibrary");
		}
		else if(command.equals("list")) {
			view.changePage("list");
		}
		else if(command.equals("recommended")) {
			view.changePage("recommended");
		}
		else if(command.equals("search")) {
			view.changePage("search");
		}
		else if(command.equals("borrowermain")) {
			view.changePage("borrowermain");
		}
	}
}
