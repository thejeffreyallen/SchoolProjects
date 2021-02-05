import javax.swing.JFrame;

/**
 * Project 04: Reader of Books
 * 
 * This is the driver class for the reader of books program.
 *
 * @author Jeff Allen
 * @version Spring 2019
 * 
 */
public class ReaderOfBooks {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Reader of Books");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ReaderOfBooksPanel panel = new ReaderOfBooksPanel();
		frame.getContentPane().add(panel);

		frame.pack();
		frame.setVisible(true);
	}
}
