# E-Library
This project is our submission for our final project in CSC335, it represents a library system composed of text files and corresponding book objects representing books and user objects representing both the Librarians running the library and the Borrowers checking out book from. The Librarians and Borrowers have access to different tools, representing the different ways they interact with the Library system, with the Librarian having greater authority over the borrowers. This lets the hypothetical librarians manage the "behind the scenes" of the library, while the Borrowers can only interact very minimally. Additionally, we are represnting the resovoir of books using a folder of textfiles of different books, who's file location plus important metadata is saved in a hashmap of Book Objects in Library.java.

In order to access the E-Library, each user must create a borrower account in order to utilize the full range of library services. Once registered, users can log back into their accounts as needed. During account creation, the user is required to enter a valid password that includes at least one uppercase letter, one lowercase letter, one digit, and one special character. The special character includes: ~!@#$%^&*()_+. The system uses the MD5 hashing algorithm to cryptographically hash the password. To enhance security and prevent predictable random number generation, a salt is generated using the SecureRandom class.

In order to add books to the library and textfiles to the actual folder, we use Project Gutenberg, a free public E-Library, and its associated API (https://gutendex.com/). From this, the Library.java class builds an api call using public methods in ParseBooks.java, then downloads the books and adds the metadata that result from the call. All of this is done using public methods of ParseBooks.java in order to perserve encapsulation. From here, the library can then hold all the books, as well as sort them in a variety of ways depending on how the Borrower would like to view them. 

In order to view the books as a user, we are using a GUI. This way it'll be far easier to interact with our internal system at hand. Both the Librarian and Borrower have a different GUI to represent the different ways they can interact with the Library System.

NOTE: As project Gutenberg is a Free Public Library, a vast majority of the books are those that are out of copyright, so sadly no <u>Love Hypothesis</u>. Additionally, if a certain time period is searched for, say 2010-2020, and text that obviously predate it are returned, its still working. Some texts are reuploaded to Project Gutenberg several times in different years, for example <u>The Modern Prometheus</u> is on there 8 time. Lastly, while books in other languages can be searched for, it may have some unintended consequences.

For Testing Uses: StaffID: StaffMember  Staff Password: StaffPW8*
Other StaffID: Admin Staff Password: Admin1!

## DESIGN:

In the backend, we utilized several major classes: Library, Book, User, Borrower, and Librarian.

Library: Holds all the information about books held in the library. Utilizes hashmaps for quick searching by title and author, and for quick updating of checkout numbers (keyed by Book). Since Books are immutable, they are tracked as available or unavailable as well. ArrayLists are used as values, in the cases where there are multiple books with the same title (which is possible, but not common), and when there are multiple books from the same author (which we expected to occur). We utilized Comparators to sort the books by popularity, as well as defining Book comparators to sort the ArrayLists of books in different ways.

Book: an immutable class that holds information about the book: title, authors, id, summary, and filepath. Since these are immutable, it makes it a lot easier to access the information (held as public final variables), and ensure that our Library methods are well encapsulated since returning a Book can never be an escaping reference.

User: the superclass for both types of users: Borrowers and Librarians. This defines the methods for signing in, and creating a new username and password. The input password string is not stored in the field. Instead, the password is hashed to an array of bytes for sensitive information protection. The security features are as described. We decided to have the User subclasses be associated, so that though neither is fully composed of the other, they have related methods so that the subclass user can access library methods by calling a method of their own class, which then affects the library by calling a library method on the passed in library argument.

View: The view creates the GUI environment using Java’s Swing library for displaying all of the information of our backend, information it obtains through the controller. 

Controller: The controller implements the ActionListener interface, allowing it to listen to all commands from the view. With these commands, it gets information from or modifies the library  and/or view accordingly. This holds all the backend data (Users and the Library) and supplies information to the view.

To manipulate the data represented by the Backend Database, we utilized: ParseBook.java and DataController.java.

ParseBook: This class encapsulates the main way to send requests and get data back. There are a couple private methods to build the request sent out, adding important information like the title, author, time range the book was created in, and topic of the book. After the request is sent, the information received in the form of a HTTP Request, which is then parsed as a JSON File using a Simple JSON Library and ultimately returned as an ArrayList of Books. This allows the backend to only focus on using a select few methods, those that build the request and those that return the data from the request. Additionally, the backend also adds a text file containing the book to the model/LibraryText folder.

DataController: This class preserves the metadata of books in between runs of the program, so the references to the actual text files aren't lost. To do so, it uses two methods, one that saves the metadata when the view is closed and another that loads the metadata when the view is opened. Lastly, there’s another method to delete book text files from model/Library/ when the corresponding book is deleted from the database. 

## DESIGN PATTERNS:
Observer – The observer design pattern was used as best as possible in the making of the GUI. While there is no static data to reflect changes from other input in the GUI, all commands that make changes to the backend are immediately reflected in the information displayed on screen.

## AVOIDING ANTI-PATTERNS:
The main anti-pattern we found ourselves trying to avoid is repeated code (which we combated by liberal use of private helper methods), temporary fields, and primitive obsession.

Within the library, many different primitive values were calculated purely based on the information held in the various HashMap fields.

We used several static classes to avoid creating objects for tools that didn’t need objects to represent them. This was mainly used by DataController.java and ParseBook.java.

We avoid using string representation for an object. Instead, we return the copy of the objects to allow the data to be accessed.

## INPUT VALIDATION:
For the frontend, all input was validated to be correct before it was sent to backend classes. If user input was not correct and would cause an error, an appropriate error message was printed and the user was prompted for new input. This is because the backend is designed in the manner of design by contract, whereby incorrect input can create errors but what is correct input is specified so that the View could be designed appropriately. All other input sent to the backend came from data collected from the backend and thus would be valid.

## RUNNING THE PROGRAM: 
To run our program, compile and run View.java.
