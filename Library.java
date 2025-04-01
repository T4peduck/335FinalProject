
import java.util.HashMap;
import java.util.ArrayList;

public class Library {
    private HashMap<String, Borrower> borrowerList;
    private HashMap<String, Librarian> librarianList;
    
    private HashMap<String, ArrayList<Book>> availableBooks;
    private HashMap<String, ArrayList<Book>> unavaiableBooks;

    public Library() {
        borrowerList = new HashMap<>();
        librarianList = new HashMap<>();

        availableBooks = new HashMap<>();
        unavaiableBooks = new HashMap<>();
    }
}
