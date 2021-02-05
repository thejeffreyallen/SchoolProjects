import java.util.Scanner;

/**
 * This program is a library manager you can add, remove, print, and read books
 * in the library.
 * 
 * @author jeffallen2
 * @version Spring 2019
 */
public class LibraryOfBooks {

	@SuppressWarnings("resource") // added because the scanner is never closed
	public static void main(String[] args) {
		Scanner scan;
		scan = new Scanner(System.in);
		boolean running = true;
		// New library to hold all the book objects.
		LibraryInterface library = new Library();

		// Welcome screen and initial menu
		System.out.println("        Welcome to the Library        ");
		System.out.println("\n****************************************");
		System.out.println("*             Library Menu             *");
		System.out.println("****************************************");
		System.out.println("(P)rint library");
		System.out.println("(A)dd book");
		System.out.println("(D)elete book");
		System.out.println("(R)ead book");
		System.out.println("(Q)uit program");
		System.out.print("\nPlease enter a command (Press M for menu): ");
		// Main Program loop
		while (running == true) {
			String select = scan.nextLine();
			// Switch statements to respond to user input.
			switch (select) {
			case "m":
			case "M":
				// Prints the selection menu
				System.out.println("\n****************************************");
				System.out.println("*             Library Menu             *");
				System.out.println("****************************************");
				System.out.println("(P)rint library");
				System.out.println("(A)dd book");
				System.out.println("(D)elete book");
				System.out.println("(R)ead book");
				System.out.println("(Q)uit program");
				System.out.print("\nPlease enter a command (Press M for menu): ");
				break;
			case "p":
			case "P":
				// Prints the entire library to the console
				System.out.println(library.toString());
				System.out.print("\nPlease enter a command (Press M for menu): ");
				break;
			case "a":
			case "A":
				// Add a new book to the library.
				System.out.print("Please enter the name of a book to add: ");
				String title = scan.nextLine();
				System.out.print("Please enter the name of the book's author: ");
				String author = scan.nextLine();
				System.out.print("Please enter the genre of the book: ");
				String genre = scan.nextLine();
				System.out.print("Please enter the file name of the book: ");
				String filename = scan.nextLine();
				Book b = new Book(title, author);
				b.setGenre(genre);
				b.setFilename(filename);
				library.addBook(b);
				System.out.println("Book successfully added");
				System.out.print("\nPlease enter a command (Press M for menu): ");
				break;
			case "d":
			case "D":
				// Delete a book from the library
				System.out.print("Please enter the index of the book you'd like to delete: ");
				String indexString = scan.nextLine();
				int index = Integer.parseInt(indexString);
				// if statement to make sure that the index is in range.
				if (index <= library.getBooks().size()) {
					library.removeBook(index);
					System.out.println("Book successfully deleted.");
				} else {
					System.out.println("Book does not exist at the given index");
				}

				System.out.print("\nPlease enter a command (Press M for menu): ");
				break;
			case "r":
			case "R":
				// Print the contents of a book's .txt file to the console.
				System.out.print("Please enter the index of the book you'd like to read: ");
				indexString = scan.nextLine();
				index = Integer.parseInt(indexString);
				// Check that the .txt file is valid
				if (index >= 0 && index < library.getBooks().size() && library.getBook(index).isValid() == true) {
					System.out.println(library.getBook(index).getText());
				} else {
					System.out.println("Book currently unavailable");
				}
				System.out.print("\nPlease enter a command (Press M for menu): ");
				break;
			case "q":
			case "Q":
				// Quit the program
				running = false;
				System.out.println("Goodbye...");
				System.exit(0);
				break;
			default:
				// if a selection is not included in the switch statements, ask for a valid
				// selection.
				System.out.print("Please enter a valid selection: ");
				running = true;
			}
		}
	}

}
