# E-Library
This project is our submission for our final project in CSC335, it represents a library system composed of text files and corresponding book objects representing books and user objects representing both the Librarians running the library and the Borrowers checking out book from. The Librarians and Borrowers have access to different tools, representing the different ways they interact with the Library system, with the Librarian having greater authority over the borrowers. This lets the hypothetical librarians manage the "behind the scenes" of the library, while the Borrowers can only interact very minimally. Additionally, we are represnting the resovoir of books using a folder of textfiles of different books, who's file location plus important metadata is saved in a hashmap of Book Objects in Library.java.

In order to add books to the library and textfiles to the actual folder, we use Project Gutenberg, a free public E-Library, and its associated API (https://gutendex.com/). From this, the Library.java class builds an api call using public methods in ParseBooks.java, then downloads the books and adds the metadata that result from the call. All of this is done using public methods of ParseBooks.java in order to perserve encapsulation. From here, the library can then hold all the books, as well as sort them in a variety of ways depending on how the Borrower would like to view them. 

In order to view the books as a user, we are using a GUI. This way it'll be far easier to interact with our internal system at hand. Both the Librarian and Borrower have a different GUI to represent the different ways they can interact with the Library System.

NOTE: As project Gutenberg is a Free Public Library, a vast majority of the books are those that are out of copyright, so sadly no <u>Love Hypothesis<u>. Additionally, if a certain time perdiod is searched for, say 2010-2020, and text that obviously predate it are returned, its still working. Some texts are reuploaded to Project Gutenberg several times, for example <u>The Modern Prometheus<u> is on there 8 time. Lastly, while books in other languages can be searched for, it may have some unintended consequences.

In this project, we used Project Gutenberg, a free public elibrary, and the associated API https://gutendex.com/.


