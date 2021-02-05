import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * The library panel which contains the book list panel, import book panel and
 * creates all the book buttons
 *
 * @author Jeff Allen
 * @version 1.0 CS121 Spring 2019
 */
@SuppressWarnings("serial")
public class LibraryPanel extends JPanel {

	Library myLibrary = new Library();
	JPanel bookListPanel = new JPanel();
	JPanel importBookPanel = new JPanel();

	JButton load = new JButton("Load");
	LoadButtonListener l = new LoadButtonListener();
	JTextField blf = new JTextField(10);

	BookButton bb;
	ActionListener ButtonListner;
	String fileName;
	String bookTitle;
	
	/**
	 * @param takes an action listener as a parameter for use in the Reader Of Books Panel.
	 */
	public LibraryPanel(ActionListener a) {
		// library panel
		this.setPreferredSize(new Dimension(250, 300));
		setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Library"));

		// bookList panel
		bookListPanel.setPreferredSize(new Dimension(220, 300));
		bookListPanel.setLayout(new GridLayout(20, 1));
		bookListPanel.setBorder(BorderFactory.createTitledBorder("Book List"));

		JScrollPane buttonScrollPane = new JScrollPane(bookListPanel);
		buttonScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		buttonScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(buttonScrollPane);

		// import book panel
		importBookPanel.setBorder(BorderFactory.createTitledBorder("Import Books"));
		importBookPanel.setLayout(new BorderLayout());
		this.add(importBookPanel, BorderLayout.SOUTH);
		importBookPanel.add(load, BorderLayout.EAST);
		blf.setText("etext/booklist-full.csv");
		load.addActionListener(l);
		importBookPanel.add(blf, BorderLayout.CENTER);

		ButtonListner = a;
	}

	private class LoadButtonListener implements ActionListener {

		/**
		 * @param takes an action event as a parameter
		 * Loads a new library from csv
		 */
		public void actionPerformed(ActionEvent e) {
			// Loads csv file to library
			bookListPanel.removeAll();
			fileName = blf.getText();
			myLibrary.loadLibraryFromCSV(fileName);
			System.out.println(myLibrary);
			// For loop to create book buttons
			for (Book b : myLibrary.getBooks()) {
				bb = new BookButton(b);
				bookListPanel.add(bb);
				bb.addActionListener(ButtonListner);
			}
			revalidate();
		}
	}
}
