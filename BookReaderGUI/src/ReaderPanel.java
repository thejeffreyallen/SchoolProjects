import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
/**
 * The reader panel class. Contains info panel, content panel, navigation panel
 * and page up / down buttons.
 *
 * @author Jeff Allen
 * @version 1.0 CS121 Spring 2019
 */
public class ReaderPanel extends JPanel {

	JPanel info = new JPanel();
	JPanel content = new JPanel();
	JPanel navigation = new JPanel();
	JButton pgUp = new JButton("Page Up");
	JButton pgDwn = new JButton("Page Down");
	JTextArea read = new JTextArea();
	JScrollPane readerScrollPane = new JScrollPane(read);

	PageUpButtonListener publ = new PageUpButtonListener();
	PageDwnButtonListener pdbl = new PageDwnButtonListener();

	JLabel title = new JLabel();
	JLabel pgNumber = new JLabel();
	int top;
	int pageHeight;
	int pageUp;
	int pageDown;
	int pageNum1;
	int pageNum2;

	Insets inset = new Insets(10, 50, 10, 10);

	public ReaderPanel() {
		setLayout(new BorderLayout());
		this.setBorder(BorderFactory.createTitledBorder("Reader"));

		// information panel
		info.setBorder(BorderFactory.createTitledBorder("Information"));
		info.setLayout(new BorderLayout());
		this.add(info);
		info.add(title, BorderLayout.NORTH);

		// content panel
		content.setBorder(BorderFactory.createTitledBorder("Content"));
		content.setLayout(new BorderLayout());
		info.add(content);

		// navigation panel
		navigation.setBorder(BorderFactory.createTitledBorder("Navigation"));
		navigation.setLayout(new BorderLayout());
		content.add(navigation, BorderLayout.SOUTH);
		navigation.setLayout(new FlowLayout());
		pgUp.addActionListener(publ);
		pgDwn.addActionListener(pdbl);
		navigation.add(pgUp);
		navigation.add(pgDwn);

		read.setEditable(false);

		read.setMargin(inset);
		readerScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		readerScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		readerScrollPane.getVerticalScrollBar().addAdjustmentListener(new addAdjustmentListener());
		content.add(readerScrollPane, BorderLayout.CENTER);

	}

	public void setBookText(Book b) {
		read.setText(b.getText());
		revalidate();
	}

	public void setBookInfo(Book b) {
		title.setText("");
		title.setText("Title: " + b.getTitle() + ".      By: " + b.getAuthor() + ".   " + pgNumber.getText());
		revalidate();
	}

	private class PageUpButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			/*
			 * (non-Javadoc)
			 * 
			 * @see
			 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
			 */
			top = readerScrollPane.getVerticalScrollBar().getValue();
			pageHeight = readerScrollPane.getVerticalScrollBar().getBlockIncrement(1);
			pageUp = top - pageHeight;
			readerScrollPane.getVerticalScrollBar().setValue(pageUp);

			pageNum1 = top / pageHeight;
			pageNum2 = readerScrollPane.getVerticalScrollBar().getMaximum() / pageHeight;
			pgNumber.setText(pageNum1 + "/" + pageNum2);
			pgNumber.revalidate();
		}

	}

	private class PageDwnButtonListener implements ActionListener {

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			top = readerScrollPane.getVerticalScrollBar().getValue();
			pageHeight = readerScrollPane.getVerticalScrollBar().getBlockIncrement(1);
			pageDown = top + pageHeight;
			readerScrollPane.getVerticalScrollBar().setValue(pageDown);

			pageNum1 = top / pageHeight;
			pageNum2 = readerScrollPane.getVerticalScrollBar().getMaximum() / pageHeight;
			pgNumber.setText(pageNum1 + "/" + pageNum2);

		}

	}

	private class addAdjustmentListener implements AdjustmentListener {

		@Override
		public void adjustmentValueChanged(AdjustmentEvent e) {
			// Setting the page number for a given book based on the scroll bar position.
			top = readerScrollPane.getVerticalScrollBar().getValue();
			pageHeight = readerScrollPane.getVerticalScrollBar().getBlockIncrement(1);
			pageNum1 = (top / pageHeight);
			pageNum2 = (readerScrollPane.getVerticalScrollBar().getMaximum() / pageHeight);
			pgNumber.setText("Page " + (pageNum1 + 1) + "/ " + pageNum2);

			if (readerScrollPane.getVerticalScrollBar().getValue() != 0) {
				pgUp.setEnabled(true);
			} else {
				pgUp.setEnabled(false);
			}

			if (top + pageHeight != readerScrollPane.getVerticalScrollBar().getMaximum()) {
				pgDwn.setEnabled(true);
			} else {
				pgDwn.setEnabled(false);
			}

		}

	}

}
