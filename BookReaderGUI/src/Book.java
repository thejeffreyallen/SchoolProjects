import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The book class. Sets up and defines parameters for the Book object
 *
 * @author Jeff Allen
 * @version 1.0 CS121 Spring 2019
 */
public class Book implements BookInterface {
	private String title;
	private String author;
	private String genre;
	private String filename;
	private String text = "";
	private boolean isValid;

	public Book(String title, String author) {
		// Book constructor, takes in two Strings as parameters
		this.title = title;
		this.author = author;
	}

	/**
	 * @return returns the book title as a string
	 */
	public String getTitle() {
		// returns a book's title
		return title;
	}

	/**
	 * @param takes in a string and sets the book title to the given string.
	 */
	public void setTitle(String title) {
		// sets a book's title to the given String parameter
		this.title = title;
	}

	/**
	 * @return returns the book author as a string
	 */
	public String getAuthor() {
		// returns a book's author
		return author;
	}

	/**
	 * @param takes in a string and sets the book author to the given string.
	 */
	public void setAuthor(String author) {
		// sets a book's author to the given String parameter
		this.author = author;

	}

	/**
	 * @return returns the book genre as a string
	 */
	public String getGenre() {
		// returns a book's genre
		return genre;
	}

	/**
	 * @param takes in a string and sets the book genre to the given string.
	 */
	public void setGenre(String genre) {
		// sets a book's genre to the given String parameter
		this.genre = genre;

	}

	/**
	 * @return returns the book's file name as a string
	 */
	public String getFilename() {
		// returns a book's filename
		return filename;
	}

	/**
	 * @param takes in a string and sets the book's file name to the given string.
	 */
	public void setFilename(String filename) {
		// sets a book's filename to the given String parameter
		this.filename = filename;
	}

	/**
	 * @return returns the boolean value of isTrue
	 */
	public boolean isValid() {
		// checks if the book's parameters are not null and if the .txt file is valid.
		// Sets and returns the boolean value of isTrue
		if (genre != null && author != null && title != null && filename != null) {
			File file = new File(filename);
			if (file.exists() == true && file.isFile()) {
				isValid = true;
			} else {
				isValid = false;
			}
		}
		return isValid;
	}

	/**
	 * @return returns the book's text in a readable form as a string.
	 */
	public String getText() {
		// Sets the text of a .txt file to the String variable "text" and returns the
		// variable
		Scanner scan;
		try {
			scan = new Scanner(new File(filename));
			while (scan.hasNextLine()) {
				text += scan.nextLine() + "\n";
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("File unable to be opened");
		}
		return text;
	}

	public String toString() {
		// Formats the book's information to be printed nicely.
		return "Author: " + author + " \nTitle: " + title + " \nGenre: " + genre + " \nFile Name: " + filename;
	}
}
