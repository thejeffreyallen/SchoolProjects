import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class JeffTest {
	public static Scanner scan;
	static String file = "src\\tenKInts.txt";
	public static void main(String[] args) throws Exception {
		
//		IndexedUnsortedList<Integer> list = new IUDoubleLinkedList<Integer>();
//		try {
//			scan = new Scanner(new File(file));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		while(scan.hasNextInt())
//			list.add(scan.nextInt());
		
		
		
//			list.add(6);
//			list.add(8);
//			list.add(1);
//			list.add(14);8
//			list.add(339);
//			list.add(3);
//			list.add(5);
//			list.add(2);
//			list.add(7);
//			list.add(9);
//			list.add(12);
			
			IUDoubleLinkedList<String> list2 = new IUDoubleLinkedList<String>();
//			ReverseComparableComparator<String> c = new ReverseComparableComparator<String>();
			list2.add("F");
			list2.add("D");
			list2.add("C");
			list2.add("A");
			list2.add("B");
			list2.add("E");
			list2.add("J");
			list2.add("I");
			list2.add("H");
			list2.add("G");
			list2.add("K");
		
		
		
//		System.out.println("Original list:");
//		for(Integer i : list) {
//			System.out.println(i);
//		}
//		long startTime = System.nanoTime();
//		Sort.sort(list);
//		long endTime   = System.nanoTime();
//		double totalTime = (endTime - startTime)/1e+9;
//		
//		System.out.println("");
//		System.out.println("Natural Order Sort:");
//		for(Integer i : list) {
//			System.out.println(i);
//		}
//		System.out.println("");
//		System.out.println("Time to sort: "+totalTime +" seconds");
//		
//		System.out.println("");
//		
		System.out.println("Original List:");
		for(String i : list2) {
			System.out.println(i);
		}
		System.out.println("");
		list2.ListMove("K","B");
		System.out.println("");
		System.out.println("List after move:");
		for(String i : list2) {
			System.out.println(i);
		}
		
	}
}
