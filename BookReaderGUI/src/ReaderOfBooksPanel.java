import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;
import javax.swing.JScrollBar;

@SuppressWarnings("serial")
/**
 * The reader of books panel manages both the library and reader panels
 *
 * @author Jeff Allen
 * @version 1.0 CS121 Spring 2019
 */
public class ReaderOfBooksPanel extends JPanel {
	BookButtonListener bbl = new BookButtonListener();

	LibraryPanel library = new LibraryPanel(bbl);
	ReaderPanel reader = new ReaderPanel();

	public ReaderOfBooksPanel() {
		this.setPreferredSize(new Dimension(800, 600));
		setLayout(new BorderLayout());
		add(library, BorderLayout.WEST);
		add(reader, BorderLayout.CENTER);

	}
	/**
	 * @Param "e" represents a given book button. 
	 */
	private class BookButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// Display the selected book to a textPane in the "content" section.
			reader.setBookText(((BookButton) e.getSource()).getBook());
			reader.setBookInfo(((BookButton) e.getSource()).getBook());
			reader.readerScrollPane.getVerticalScrollBar().setValue(0);

		}

	}
}
