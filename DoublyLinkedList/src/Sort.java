import java.util.Comparator;
import java.util.Iterator;

/**
 * Class for sorting lists that implement the IndexedUnsortedList interface,
 * using ordering defined by class of objects in list or a Comparator. As
 * written uses Mergesort algorithm.
 *
 * @author CS221
 */
public class Sort {
	/**
	 * Returns a new list that implements the IndexedUnsortedList interface. As
	 * configured, uses WrappedDLL. Must be changed if using your own
	 * IUDoubleLinkedList class.
	 * 
	 * @return a new list that implements the IndexedUnsortedList interface
	 */
	private static <T> IndexedUnsortedList<T> newList() {
		return new IUDoubleLinkedList<T>(); // TODO: replace with your IUDoubleLinkedList for extra-credit
	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface using
	 * compareTo() method defined by class of objects in list. DO NOT MODIFY THIS
	 * METHOD
	 * 
	 * @param      <T> The class of elements in the list, must extend Comparable
	 * @param list The list to be sorted, implements IndexedUnsortedList interface
	 * @see IndexedUnsortedList
	 */
	public static <T extends Comparable<T>> void sort(IndexedUnsortedList<T> list) {
//		System.out.println("Before MergeSort:");
//		for(T t : list) {
//			System.out.println(t);
//		}
		mergesort(list);

	}

	/**
	 * Sorts a list that implements the IndexedUnsortedList interface using given
	 * Comparator. DO NOT MODIFY THIS METHOD
	 * 
	 * @param      <T> The class of elements in the list
	 * @param list The list to be sorted, implements IndexedUnsortedList interface
	 * @param c    The Comparator used
	 * @see IndexedUnsortedList
	 */
	public static <T> void sort(IndexedUnsortedList<T> list, Comparator<T> c) {
		mergesort(list, c);
	}

	/**
	 * Mergesort algorithm to sort objects in a list that implements the
	 * IndexedUnsortedList interface, using compareTo() method defined by class of
	 * objects in list. DO NOT MODIFY THIS METHOD SIGNATURE
	 * 
	 * @param      <T> The class of elements in the list, must extend Comparable
	 * @param list The list to be sorted, implements IndexedUnsortedList interface
	 */
	private static <T extends Comparable<T>> void mergesort(IndexedUnsortedList<T> list) {
		// TODO: Implement recursive mergesort algorithm
		IndexedUnsortedList<T> left = newList();
		IndexedUnsortedList<T> right = newList();
		int low = 0;
		int high = list.size()-1;
		int mid = (list.size() / 2);
		int i = 0;
		for(T t : list) {
			if(i++ < mid)
				left.add(t);
			else
				right.add(t);
		}
		if (low < high) {
			mergesort(left);
			mergesort(right);
			for(int j = 0; j < high+1; j++) {
				list.removeFirst();
			}
			merge(left, right, list, mid);

		}
	}

	/**
	 * Mergesort algorithm to sort objects in a list that implements the
	 * IndexedUnsortedList interface, using the given Comparator. DO NOT MODIFY THIS
	 * METHOD SIGNATURE
	 * 
	 * @param      <T> The class of elements in the list
	 * @param list The list to be sorted, implements IndexedUnsortedList interface
	 * @param c    The Comparator used
	 */
	private static <T> void mergesort(IndexedUnsortedList<T> list, Comparator<T> c) {
		// TODO: Implement recursive mergesort algorithm using Comparator
		IndexedUnsortedList<T> left = new IUDoubleLinkedList<T>();
		IndexedUnsortedList<T> right = new IUDoubleLinkedList<T>();
		int low = 0;
		int high = list.size()-1;
		int mid = (list.size() / 2);
		int i = 0;
		for(T t : list) {
			if(i++ < mid)
				left.add(t);
			else
				right.add(t);
		}
		if (low < high) {
			mergesort(left, c);
			mergesort(right, c);
			for(int j = 0; j < high+1; j++) {
				list.removeFirst();
			}
			merge(left, right, list, c);

		}
	}

	private static <T extends Comparable<T>> void merge(IndexedUnsortedList<T> left, IndexedUnsortedList<T> right,
			IndexedUnsortedList<T> list, int mid) {
		while (!(left.isEmpty()) && !(right.isEmpty())) {
			if (left.first().compareTo(right.first()) <= 0)
				list.add(left.removeFirst());
			else
				list.add(right.removeFirst());
		}
		while (!(left.isEmpty()))
			list.add(left.removeFirst());
		while (!(right.isEmpty()))
			list.add(right.removeFirst());

	}

	private static <T> void merge(IndexedUnsortedList<T> left, IndexedUnsortedList<T> right,
			IndexedUnsortedList<T> list, Comparator<T> c) {
		while (!(left.isEmpty()) && !(right.isEmpty())) {
			if (c.compare(left.first(), right.first()) < 0)
				list.add(left.removeFirst());
			else
				list.add(right.removeFirst());
		}
		while (!(left.isEmpty()))
			list.add(left.removeFirst());
		while (!(right.isEmpty()))
			list.add(right.removeFirst());

	}

}
