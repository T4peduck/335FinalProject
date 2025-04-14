package model;

import java.util.Comparator;
import java.util.Objects;

public class Book {
	@Override
	public int hashCode() {
		return Objects.hash(author, callNumber, title);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(author, other.author) && Objects.equals(callNumber, other.callNumber)
				&& Objects.equals(title, other.title);
	}

	private String title;
	private String author;
	private String callNumber;
	
    public Book(String title, String author, String callNumber) {
        this.title = title;
        this.author = author;
        this.callNumber = callNumber;
    }

	public Book(Book b) {
		this.title = b.title;
		this.author = b.author;
		this.callNumber = b.callNumber;
	}

	public String getTitle() {
		return this.title;
	}
	
	public String getAuthor() {
		return this.author;
	}
	
	public String getCallNumber() {
		return this.callNumber;
	}
	
	public static Comparator<Book> authorFirstComparator() {
		return new Comparator<Book>() {
			public int compare(Book book1, Book book2) {
				
				int comp = book1.author.compareTo(book2.author);
				
				if (comp != 0) {
					return comp;
				}
				
				return book1.title.compareTo(book2.title);
			}
		};
	}
	
	public static Comparator<Book> titleFirstComparator() {
		return new Comparator<Book>() {
			public int compare(Book book1, Book book2) {
				
				int comp = book1.title.compareTo(book2.title);
				
				if (comp != 0) {
					return comp;
				}
				
				return book1.author.compareTo(book2.author);
			}
		};
	}
    
   
    
}
