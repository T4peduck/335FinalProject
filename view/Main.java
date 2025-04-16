package view;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Scanner;

import model.Borrower;
import model.Library;
import model.User;

public class Main {
	private static Library library;

	public static void main(String[] args) throws NoSuchAlgorithmException {
		// TODO Auto-generated method stub
		library = new Library();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Type username: (alphanumeric)");
		String username = scanner.next();
		while (!username.matches("(\\d|[A-Z]|[a-z])+")) {
			System.out.println("Invalid Username");
			username = scanner.next();
		}
		
		System.out.println("Type password: Include uppercase letters / lowercase letters / digits / special characters / Exclude space");
		String password = scanner.next();
		while (!password.matches("(?=.*[a-z].*)(?=.*[A-Z].*)(?=.*\\d.*)(?=.*[~!@#$%^&*()_+].*)(?!.*\\s.*).+")) {
			password = scanner.next();
		}
		
		System.out.println("Account Created");
	}
}
