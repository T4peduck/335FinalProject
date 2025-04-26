package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public final class Book {
	@Override
	public int hashCode() {
		return Objects.hash(authors.get(0), title);
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
		return Objects.equals(authors.get(0), other.authors.get(0))
				&& Objects.equals(title, other.title);
	}

	public final String title;
	public final List<Author> authors;
	public final String id;
	

	public final String summary;
	public final String filePath;
	
    public Book(String title, ArrayList<Author> authors, String id, 
    			String summary, String filePath) {
        this.title = title;
        this.authors = Collections.unmodifiableList(new ArrayList<Author>(authors));
        this.id = id;
        this.summary = summary;
        this.filePath = filePath;
    }
	
	public static Comparator<Book> authorFirstComparator() {
		return new Comparator<Book>() {
			public int compare(Book book1, Book book2) {
				
				int comp = book1.authors.get(0).compareTo(book2.authors.get(0));
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
				
				return book1.authors.get(0).compareTo(book2.authors.get(0));
			}
		};
	}
	
	@Override
	public String toString() {
		String rstr = title + " by ";
		ArrayList<String> authorStrs = new ArrayList<>();
		for(Author a: authors) {
			authorStrs.add(a.NAME);
		}
		rstr += String.join(", ", authorStrs);
		return rstr;
	}
   
}
