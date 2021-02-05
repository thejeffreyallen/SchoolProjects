
import javax.swing.JButton;

@SuppressWarnings("serial")
/**
 * The book button class. Takes in a book object and sets the button text to the
 * book title. Also adds a tool tip when the button is hovered over that
 * contains the book information held by the button.
 * 
 * @author Jeff Allen
 * @version 1.0 CS121 Spring 2019
 */
public class BookButton extends JButton {

	Book b;

	public BookButton(Book b) {
		this.b = b;
		if (b.getTitle().length() <= 20) {
			this.setText(b.getTitle());
		} else {
			this.setText(b.getTitle().substring(0, 18));
		}

		this.setToolTipText(b.toString());
	}
	/**
	 * @return returns the Book object held by the Book button.
	 */
	public Book getBook() {
		return b;

	}
}
