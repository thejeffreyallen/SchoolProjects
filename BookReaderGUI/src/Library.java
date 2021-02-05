import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The library class which sets up and manages a library of book objects in an
 * arraylist. Contains a method that Loads a book list from csv file.
 *
 * @author Jeff Allen
 * @version 1.0 CS121 Spring 2019
 */
public class Library implements LibraryInterface {
	public String toString = "";
	private ArrayList<Book> books = new ArrayList<Book>();
	public ArrayList<Book> bookCopy;

	String author;
	String title;
	String csvName;
	String genre;
	String DELIMITERS = ",";

	/**
	 * @return a copy of the book arraylist
	 */
	public ArrayList<Book> getBooks() {
		// Makes and returns a copy of the ArrayList "books."
		bookCopy = new ArrayList<Book>(books);
		return bookCopy;
	}

	/**
	 * @param takes a Book object as a parameter
	 */
	public void addBook(Book goodBook) {
		// adds a new book, goodBook to the arrayList "books"
		books.add(goodBook);

	}

	/**
	 * @param takes an integer as a parameter
	 */
	public void removeBook(int index) {
		// checks if index is in range and removes the book at the given index
		if (index >= 0 && index < books.size()) {
			books.remove(index);
		}
	}

	/**
	 * @param takes an integer as a parameter
	 */
	public Book getBook(int index) {
		// checks if index is in range and returns the book at the given index
		if (index >= 0 && index < books.size()) {
			return books.get(index);
		} else {
			return null;
		}

	}
	/**
	 * @return returns the contents of the library as a string
	 */
	public String toString() {
		// Prints the library contents to a formated string.
		for (int i = 0; i < books.size(); i++) {
			toString += "Index: " + i + "\n" + books.get(i).toString() + "\n\n";
		}
		return toString;
	}

	@SuppressWarnings("resource")
	/**
	 * @param takes a String as a parameter
	 */
	public void loadLibraryFromCSV(String csvFilename) {
		// Checks that the file is valid and creates a new library from the contents of a csv file.
		File file = new File(csvFilename);
		if (file.exists() && file.isFile()) {
			books.removeAll(books);
			try {
				Scanner scan = new Scanner(file);
				while (scan.hasNextLine()) {
					String line = scan.nextLine();
					Scanner lineScan = new Scanner(line);
					lineScan.useDelimiter(DELIMITERS);
					while (lineScan.hasNextLine()) {
						title = lineScan.next();
						author = lineScan.next();
						genre = lineScan.next();
						csvName = lineScan.next();
						Book csv = new Book(title, author);
						csv.setFilename(csvName);
						csv.setGenre(genre);
						books.add(csv);
					}
				}

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
