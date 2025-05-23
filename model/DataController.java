package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/*
 * FILE FORMAT
 * -------------------------
 * bookTitle 
 * bookAuthor1-bookAuthor2-...-bookAuthorN
 * bookId
 * bookFilePath
 * bookSummary
 * [new line]
 * *next book*
 * 
 * AUTHOR FORMAT
 * -------------------
 * Name,BirthYear,DeathYear
 */

/*
 * Saves the Library Data to a text file so data persist
 * between uses
 */
public class DataController {
	private static String FILEPATH = "BookData.txt";
	
	/*
	 * Saves the book metadata to bookData.txt from Library lib
	 */
	public static void saveBookData(Library lib) {
		try {
			FileWriter bw = new FileWriter(new File(FILEPATH));
			ArrayList<Book> books = lib.getAllBooksByTitle();
			
			int i = 0;
			
			//SAVES AVAILABLE BOOKS
			for(Book b: books) {
				bw.write(b.title + "\n");
				
				for(Author a: b.authors) {
					bw.write(a.NAME + "~" + a.BIRTH_YEAR + "~" + a.DEATH_YEAR + "=");
				}
				bw.write("\n");
				
				bw.write(b.id + "\n");
				
				bw.write(b.filePath + "\n");
				
				//ASSUMING SUMMARIES DO NOT HAVE BLANK LINES
				bw.write(b.summary + "\n\n");
				i++;
			}
			
			System.out.println(i + " BOOKS SAVED");
			
			bw.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/*
	 * Loads book metadata from bookData.txt to Library lib
	 */
	public static void loadBookData(Library lib) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(FILEPATH));
			
			int i = 0;
			
			String line = br.readLine();
			while(line != null && line != "") {
				i++;
				String title = line;
				
				String[] aList = br.readLine().split("=");
				ArrayList<Author> authors = new ArrayList<>();
				for(String aStr: aList) {
					String[] aInfo = aStr.split("~");
					Author a = new Author(aInfo[0], Integer.parseInt(aInfo[1]), Integer.parseInt(aInfo[2]));
					authors.add(a);
				}
				
				String id = br.readLine();
				
				String filepath = br.readLine();
		
				String summary = br.readLine();
				while(!(line = br.readLine()).equals("")) {
					summary += line;
				}
				
				Book b = new Book(title, authors, id, summary, filepath);
				
				lib.addBook(b);
				
				line = br.readLine();
			}

			System.out.println(i + " BOOKS LOADED");
			
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	/*
	 * Removes book b textFile from model/LibraryText
	 * 
	 * @PRE b.filepath is a real file in model/LibraryText
	 */
	public static void removeBook(Book b) {
		File bookFile = new File(b.filePath);
		if(bookFile.delete()) {
			System.out.println(b.toString() + " SUCCESSFULLY DELETED");
		} else {
			System.out.println(b.toString() + " NOT DELETED");
		}
		
	}
}
