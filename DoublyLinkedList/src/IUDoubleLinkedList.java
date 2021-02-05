import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * A double linked list based implementation of the IndexedUnsortedList
 * interface
 *
 * @author Jeff Allen
 *
 * @param <T> Generic type of the objects in the IndexedUnsortedList
 */
public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
	private int count; // Variable to keep track of list length
	private int modCount; // Variable to keep track of modifications made to list
	private DLLNode<T> head; // Variable to keep track of the front of the list
	private DLLNode<T> tail; // Variable to keep track of the back of the list

	/**
	 * Default constructor - Make a new empty single linked list
	 *
	 */
	public IUDoubleLinkedList() {
		head = null; // Initialize head, tail to null
		tail = null;
		count = 0; // Initialize count, modCount to zero
		modCount = 0;
	}
	
	public void ListMove(T x, T y) throws Exception{
	int xIndex = indexOf(x);
	int yIndex = indexOf(y);
	System.out.println(xIndex +" " +yIndex);
	if((xIndex == -1 || yIndex == -1) || (head == tail || head == null))
	{
		throw new Exception();
	}

	DLLNode<T> tempX = head;
	DLLNode<T> tempY = head;
	for(int i = 0; i < xIndex; i++)
	{
		tempX = tempX.getNext();
	}
	for(int j = 0; j < yIndex; j++)
	{
		tempY = tempY.getNext();
	}
	if(tempX.getNext() == tempY)
	{
		System.out.println("No need to move");
		return;
	}
	else
	{
		
		if(tempX != head && tempX != tail)
		{
			tempX.getPrevious().setNext(tempX.getNext());
			tempX.getNext().setPrevious(tempX.getPrevious());
		}
		else if(tempX == tail)
		{
			tempX.getPrevious().setNext(tempX.getNext());
			tail = tempX.getPrevious();
		}
		else
		{
			tempX.getNext().setPrevious(tempX.getPrevious());
			head = tempX.getNext();
		}
		tempX.setPrevious(tempY.getPrevious());
		tempX.getPrevious().setNext(tempX);
		tempX.setNext(tempY);
		tempY.setPrevious(tempX);
		
		}
	}

	/**
	 * Secondary constructor - Make a new single linked list with element as the
	 * first node.
	 *
	 * @param element - Element to be added to the list
	 */
	public IUDoubleLinkedList(T element) {
		DLLNode<T> node = new DLLNode<T>(element); // Instantiate a new node using element parameter
		head = node; // Set head, tail to first node
		tail = node;
		count++; // Increase list length by 1
	}

	@Override
	public void addToFront(T element) {
		DLLNode<T> newNode = new DLLNode<T>(element); // Instantiate a new node which holds a reference to the element
		// to be added.
		if (count > 0) { // Check that list is not empty
			newNode.setNext(head); // Attach new node to the front of the list
			head.setPrevious(newNode);
			head = newNode; // Update head to first node
		} else if (count == 0) { // Check if list is empty
			head = newNode; // Set head, tail to first node
			tail = newNode;
		}
		count++; // Increase list length by 1
		modCount++;
	}

	@Override
	public void addToRear(T element) {
		DLLNode<T> newNode = new DLLNode<T>(element); // Instantiate a new node which holds a reference to the element
		// to be added.
		if (count == 0) { // Check if list is empty
			head = newNode; // Set head, tail to first node
			tail = newNode;
		} else { // If list is not empty
			tail.setNext(newNode); // Attach new node to back of the list
			newNode.setPrevious(tail);
			tail = newNode; // Update tail to last node
		}
		count++; // Increase list length by 1
		modCount++;
	}

	@Override
	public void add(T element) {
		DLLNode<T> newNode = new DLLNode<T>(element); // Instantiate a new node which holds a reference to the element
		// to be added.
		if (count == 0) { // Check if list is empty
			head = newNode; // Set head, tail to first node
			tail = newNode;
		} else { // If list is not empty
			tail.setNext(newNode); // Attach new node to back of the list
			newNode.setPrevious(tail);
			tail = newNode;
		}
		count++; // Increase list length by 1
		modCount++;
	}

	@Override
	public void addAfter(T element, T target) {
		boolean found = false; // Boolean variable to verify that target is in the list
		DLLNode<T> current = head; // Instantiate node to keep track of position in list and set it to the first
		// node
		DLLNode<T> next = null; // Instantiate node to keep track of node following the current position
		DLLNode<T> newNode = new DLLNode<T>(element); // Instantiate a new node which holds a reference to the element
		// to be added.
		while (!found && current != null) { // Check that target is in the list
			if (current.getElement() == target) {
				found = true; // Target element has been found
				if (current == tail) { // Check to see if target is at the back of the list
					current.setNext(newNode); // Add new node to back of the list
					newNode.setPrevious(current);
					tail = newNode; // update tail to last node
				} else { // If target is not at the back of the list
					next = current.getNext(); // Add new node after target
					current.setNext(newNode);
					newNode.setPrevious(current);
					newNode.setNext(next);
					next.setPrevious(newNode);
				}
			}
			current = current.getNext(); // Move one node forward in the list
		}
		if (!found) { // If target was not found in the list
			throw new NoSuchElementException(element + " cannot be found in list.");
		}
		count++; // Increase list length by 1
		modCount++;
	}

	@Override
	public void add(int index, T element) {
		DLLNode<T> current = head; // Instantiate node to keep track of position in list and set it to the first
		// node
		DLLNode<T> previous = null; // Instantiate node to keep track of node before the current position
		DLLNode<T> newNode = new DLLNode<T>(element); // Instantiate a new node which holds a reference to the element
		// to be added.
		if (index < 0 || index > count) { // Check if index is NOT in range of list length
			throw new IndexOutOfBoundsException("Index " + index + " is not in range for the length of the list");
		}
		if (index == 0 && count != 0) { // Check if index is the first element in list
			newNode.setNext(head); // Add new node to front of list
			head.setPrevious(newNode);
			head = newNode; // Update head to first node in list
		}
		if (index == count && count > 0) { // Check if index is equal to the length of list and that the list has
			// multiple elements
			tail.setNext(newNode); // Add new node to back of the list
			newNode.setPrevious(tail);
			tail = newNode; // Update tail to last node in list
		}
		if (count == 0 && index == 0) { // Check if list is empty
			head = newNode; // Set head, tail to new node
			tail = newNode;
		}
		if (index > 0 && index < count) { // Check if index is in range of list length
			for (int i = 0; i < index; i++) { // Step through list one node at a time "index" number of times
				previous = current;
				current = current.getNext();
			}
			previous.setNext(newNode); // Add new node at the passed in index
			newNode.setNext(current);
			newNode.setPrevious(previous);
			current.setPrevious(newNode);
		}
		count++; // Increase list length by 1
		modCount++;
	}

	@Override
	public T removeFirst() {
		if (isEmpty()) { // Check if list is empty
			throw new NoSuchElementException("List is empty");
		}
		DLLNode<T> current = head; // Instantiate node to keep track of position in list and set it to the first
		// node
		DLLNode<T> next = current.getNext();
		head = next; // Update head to second node in list
		current.setNext(null); // Remove link from first node
		count--; // Decrease list length by 1
		if (count > 0) {
			head.setPrevious(null);
		}
		modCount++; // Increase modCount by 1
		return current.getElement(); // Return removed element
	}

	@Override
	public T removeLast() {
		DLLNode<T> current = head; // Instantiate node to keep track of position in list and set it to the first
		// node
		DLLNode<T> previous = null; // Instantiate node to keep track of node before the current position
		T element = null; // Temporary element to store removed element
		if (isEmpty()) { // Check if list is empty
			throw new NoSuchElementException("List is empty");
		}
		for (int i = 0; i < count - 1; i++) { // Step through list until current and previous are the last and second to
			// last nodes respectively
			current = current.getNext();
			previous = current.getPrevious();
		}
		element = current.getElement(); // Set temporary element equal to last element
		tail = previous; // Update tail to the second to last node
		if (tail == null) { // Check to see if list contains only one node
			head = null;
		} else {
			current.setPrevious(null); // Remove link to last node
			previous.setNext(null);
		}
		count--; // Decrease list length by 1
		modCount++; // Increase modifications to list by 1
		return element; // Return removed element
	}

	@Override
	public T remove(T element) {
		boolean found = false; // Boolean variable to verify that target is in the list
		DLLNode<T> current = head; // Instantiate node to keep track of position in list and set it to the first
		// node
		DLLNode<T> next = null;
		DLLNode<T> previous = null; // Instantiate node to keep track of node before the current position
		T temp = null; // Temporary variable to store element
		while (!found && current != null) { // Check that element is in the list
			if (current.getElement() == element) {
				found = true; // Target was found
				temp = current.getElement(); // Set temporary variable equal to element
				if (current == head && count > 1) { // Check if the first node contains a reference to element
					next = head.getNext();
					next.setPrevious(null);
					head.setNext(null);
					head = next; // Update head to the second node in the list
				} else if (current == head && count == 1) {
					head = tail = null; // Update head to the second node in the list
				} else { // If element is not found in the first node
					if (current != tail && current != null && previous != null) { // Link the nodes that surround the
						// node containing the element
						next = current.getNext();
						previous.setNext(next);
						next.setPrevious(previous);
					} else if (current == tail && current != head) {
						previous = current.getPrevious();
						current.setPrevious(null);
						previous.setNext(null);
						tail = previous;
					}
				}
				current.setNext(null); // Unlink node that is being removed
				current.setPrevious(null);
			}
			previous = current; // Step forward through list one time while element is not found
			current = current.getNext();
		}
		if (!found) { // If element is not in the list
			throw new NoSuchElementException(element + " cannot be found in list.");
		}
		count--; // Decrease list length by 1
		modCount++; // Increase modifications to list by 1
		return temp; // return removed element
	}

	@Override
	public T remove(int index) {
		DLLNode<T> current = head; // Instantiate node to keep track of position in list and set it to the first
		// node
		DLLNode<T> previous = null;
		DLLNode<T> next = null;
		T element = null; // Temporary variable to store the element at index
		if (index < 0 || index >= count) { // Check if index is NOT in range of list
			throw new IndexOutOfBoundsException("Invalid index - remove at index method ");
		}
		if (head == tail) { // Check if only one node in list
			element = head.getElement();
			head = tail = null;
		} else if (index == 0) { // Check if removing the first node
			element = head.getElement(); // Get the element to return
			current = head;
			next = current.getNext();
			head.setNext(null);
			next.setPrevious(null);
			head = next;
		} else if (count == 2 && index == 1) { // Check if list has more than 2 nodes
			element = tail.getElement(); // Get the element to return
			head.setNext(null); // remove node at tail of list and update tail to head
			tail.setPrevious(null);
			tail = head;
		} else if (count > 2) {
			current = head;
			for (int i = 0; i < index; i++) { // move forward through list to the node at index
				previous = current;
				current = current.getNext();
			}
			if (current == tail) {
				element = tail.getElement(); // Get the element to return
				tail.setPrevious(null); // remove tail node and update tail variable to new tail
				previous.setNext(null);
				tail = previous;
			} else if (current.getNext() == tail) {
				previous.setNext(tail); // connect previous to tail and remove the node that precedes tail
				tail.setPrevious(previous);
				current.setNext(null);
				current.setPrevious(null);
				element = current.getElement(); // Get the element to return
			} else {
				next = current.getNext(); // connect nodes immediately before and after the node to be removed
				previous.setNext(next);
				next.setPrevious(previous);
				current.setNext(null);
				current.setPrevious(null);
				element = current.getElement(); // Get the element to return
			}
		}
		count--; // Decrease list length by 1
		modCount++; // Increase modifications to list by 1
		return element; // Return removed element
	}

	@Override
	public void set(int index, T element) {
		DLLNode<T> current = head; // Instantiate node to keep track of position in list and set it to the first
		// node
		if (index < count && index >= 0) { // Check that index is in range of the list
			for (int i = 0; i < index; i++) { // Step through the list to reach the node at the index
				current = current.getNext();
			}
			current.setElement(element); // Set the current element at the index to the new element
		} else { // If index is NOT in range of the list
			throw new IndexOutOfBoundsException("Index " + index + " is not in range for the length of the list");
		}
	}

	@Override
	public T get(int index) {
		if ((index < 0 || index >= count)) { // Check if the index is NOT in range of the list
			throw new IndexOutOfBoundsException("Index out of bounds for " + index);
		}
		DLLNode<T> current = head; // Instantiate node to keep track of position in list and set it to the first
		// node
		for (int i = 0; i < index; i++) { // Step through the list to reach the node at the index
			current = current.getNext();
		}
		return current.getElement(); // Return the element at the index
	}

	@Override
	public int indexOf(T element) {
		int index = 0; // Value to return
		boolean found = false; // Boolean variable to verify that target is in the list
		DLLNode<T> current = head; // Instantiate node to keep track of position in list and set it to the first
		// node
		while (current != null && index > -1) { // Step through the list one node at a time
			if (current.getElement() == element) {
				found = true; // element has been located
				return index; // returns index of element in list
			}
			index++; // Decrease index by 1
			current = current.getNext(); // Step to next node
		}
		if (!found) { // If element is not found
			index = -1;
		}
		return index; // returns -1
	}

	@Override
	public T first() {
		if (isEmpty()) { // Check if list is empty
			throw new NoSuchElementException("List is empty");
		}
		return head.getElement(); // Return the first element in the list
	}

	@Override
	public T last() {
		if (isEmpty()) { // Check if list is empty
			throw new NoSuchElementException("List is empty");
		}
		return tail.getElement(); // Return the last element in the list
	}

	@Override
	public boolean contains(T target) {
		DLLNode<T> temp = head; // Instantiate node to keep track of position in list and set it to the first
		// node
		while (!(temp == null)) { // Step through list one node at a time
			if (temp.getElement() == target) {
				return true; // Target found in list, return true
			}
			temp = temp.getNext(); // Step to next node
		}
		return false; // Target was NOT found, return false
	}

	@Override
	public boolean isEmpty() {
		return (count == 0); // Returns true if list is empty, else returns false
	}

	@Override
	public int size() {
		return count; // Returns length of list
	}

	@Override
	public Iterator<T> iterator() {
		return new IUDLLListIterator(); // Instantiate and return a new iterator
	}

	@Override
	public ListIterator<T> listIterator() {
		return new IUDLLListIterator(); // Instantiate and return a new iterator
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		return new IUDLLListIterator(startingIndex); // Instantiate and return a new iterator starting at startingIndex
	}

	/**
	 * Double linked list implementation of the listIterator interface
	 *
	 * @author Jeff Allen
	 *
	 */
	public class IUDLLListIterator implements ListIterator<T> {
		private DLLNode<T> current; // Node variable to keep track of current node in list
		private DLLNode<T> previous; // Node variable to keep track of previous node in list
		private int index; // Integer to keep track of index (position) in list
		private boolean canRemove; // Boolean variable to tell whether a node can be removed
		private T elementToRemove; // T variable to store element from node which will be removed

		/**
		 * Default listIterator constructor
		 */
		public IUDLLListIterator() {
			current = head; // initialize variables
			previous = null;
			index = 0;
			canRemove = false;
		}

		/**
		 * Secondary listIterator constructor
		 *
		 * @param startingIndex - position in list to begin iterating from
		 */
		public IUDLLListIterator(int startingIndex) {
			if (startingIndex < 0 || startingIndex > count) {
				throw new IndexOutOfBoundsException();
			}
			current = head; // initialize variables
			previous = null;
			index = 0;
			canRemove = false;
			for (index = 0; index < startingIndex; index++) { // move previous and current nodes through the list until
				// reaching the startingIndex
				previous = current;
				current = current.getNext();
			}
		}

		@Override
		public boolean hasNext() {
			return (current != null); // Returns true if current is NOT null, else false
		}

		@Override
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException(); // if current is null
			} else {
				elementToRemove = current.getElement(); // get the current element
				previous = current; // step forward once in the list
				current = current.getNext();
				index++;
				canRemove = true; // able to call remove() method
			}
			return elementToRemove;
		}

		@Override
		public boolean hasPrevious() {
			return (previous != null); // Returns true if previous is NOT null, else false
		}

		@Override
		public T previous() {
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			} else {
				elementToRemove = previous.getElement(); // get the previous element
				current = previous; // step backwards once in the list
				previous = previous.getPrevious();
				index--;
				canRemove = true; // able to call remove() method
			}
			return elementToRemove; // return the previous element
		}

		@Override
		public int nextIndex() {
			if (index == count) { // if the index is at the end of list
				return count;
			}
			return index;
		}

		@Override
		public int previousIndex() {
			if (!hasPrevious()) { // if the index is at the beginning of list
				return -1;
			}
			return index - 1;
		}

		@Override
		public void remove() {
			if (!canRemove) { // Check that element can be removed
				throw new IllegalStateException(
						"Can not remove element. Only one call to remove() after each next() or previous() method call.");
			}
			if (head == tail) { // Check if only one node in list
				current = previous = null;
			}
			if (head.getElement() == elementToRemove && hasNext()) { // Check if removing first node
				previous = null;
				current = head.getNext();
			} else if (tail.getElement() == elementToRemove && hasPrevious()) { // Check if removing last node
				current = null;
				previous = tail.getPrevious();
			} else if (hasNext() && hasPrevious()) { // Check if removing from the middle of list
				if (current.getElement() == elementToRemove && count > 2) { // shift current and previous nodes in order
					// to avoid breaking the list when removing
					current = current.getNext();
				} else if (current.getElement() == elementToRemove && count < 3) {
					current = null;
				} else if (previous.getElement() == elementToRemove && count > 2) {
					previous = previous.getPrevious();
				} else if (previous.getElement() == elementToRemove && count < 3) {
					previous = null;
				}
			}
			IUDoubleLinkedList.this.remove(elementToRemove); // remove the node containing the element
			count--; // decrease list size
			canRemove = false; // Can't remove until next() or previous() is called again
		}

		@Override
		public void add(T element) {
			DLLNode<T> newNode = new DLLNode<T>(element); // Instantiate a new node which holds a reference to the
			// element to be added.
			if (head == null && tail == null) { // Check if list is empty
				addToFront(element);
				previous = head;
				index++;
			} else if (hasNext() && !hasPrevious()) { // if iterator is at the beginning of the list
				head.setNext(newNode);
				newNode.setPrevious(head);
				newNode.setNext(current);
				current.setPrevious(newNode);
				previous = newNode;
				index++;
			} else if (!hasNext() && hasPrevious()) { // if iterator is at the end of the list
				addToRear(element);
				previous = tail;
				current = null;
				index++;
			} else if (head == tail) { // if the list contains a single node
				addToFront(element);
				current = head.getNext();
				previous = head;
				index++;
			} else if (hasNext() && hasPrevious()) { // if adding in the middle of the list
				previous.setNext(newNode);
				newNode.setPrevious(previous);
				newNode.setNext(current);
				current.setPrevious(newNode);
				previous = newNode;
				index++;
			} else {
				addToFront(element);
				current = head.getNext();
				previous = head;
				index++;
			}
			count++; // Increase list length by 1
			canRemove = false;
		}

		@Override
		public void set(T element) {
			if (!canRemove) {
				throw new IllegalStateException(
						"Can not remove element. Only one call to remove() after each next() or previous() method call.");
			}
			current.setElement(element); // Set the current element at the index to the new element
		}
	}
}
