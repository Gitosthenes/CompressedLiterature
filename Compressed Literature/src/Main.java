import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 
 * @author Alex Bledsoe & Anthony Trang
 * @version Feb 13, 2018
 */
public class Main {

	public static void main(String[] args) {
		testMyPriorityQueue();
	}
	
	/**
	 * Tests MyPriorityQueue for binary heap structure and order properties by initializing a
	 * random collection of letters and creating a MyPriorityQueue from it. The letters should
	 * then be printed back out to the console in alphabetical order.
	 */
	private static void testMyPriorityQueue() {
		Random rand = new Random();
		List<Character> charList = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
												 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
		ArrayList<Character> test = new ArrayList<>();
		for (int i = 1; i <= 64; i++) {
			test.add(charList.get(rand.nextInt(charList.size())));
		}
		MyPriorityQueue<Character> testPQ = new MyPriorityQueue<>(test);

		while (!testPQ.isEmpty()) {
			System.out.println("Size Before Removal: " + testPQ.size() + ", Min Element: " + testPQ.remove());			
		}
	}
}