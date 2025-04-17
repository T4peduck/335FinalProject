package model;

import java.util.Comparator;
import java.util.Objects;

public class Book {
	@Override
	public int hashCode() {
		return Objects.hash(author, title);
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
		return Objects.equals(author, other.author)
				&& Objects.equals(title, other.title);
	}

	public final String title;
	public final String author;

	public final String summary;
	public final String filePath;
	
    public Book(String title, String author, 
    			String summary, String filePath) {
        this.title = title;
        this.author = author;
        this.summary = summary;
        this.filePath = filePath;
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
