import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Main serves as the controller that uses an instance of 
 * the CodingTree class to compress files.
 * 
 * @author Alex Bledsoe & Anthony Trang
 * @version Feb 13, 2018
 */
public class Main {

	/**
	 * Main method runs through the entire compression process;
	 * reading in the file, passing it into an instance of CodingTree,
	 * outputting the codes and compressed version into text files and
	 * displaying the statistics on screen.
	 * 
	 * @param theArgs command line arguments; ignored.
	 * @throws IOException possibly.
	 */
	public static void main(final String[] theArgs) throws IOException {
		
		long startTime = System.nanoTime();
		
		//Setup to read file.
		StringBuilder sb = new StringBuilder();
		BufferedReader buffer = new BufferedReader(new InputStreamReader(new FileInputStream("test.txt")));
		
		//Read file and turn into single string.
		while (buffer.readLine() != null) {
			sb.append(buffer.readLine());
			sb.append('\n');
		}
		String message = sb.toString(); 
		buffer.close();
		
		//Pass string to instance of CodingTree to compress.
		final CodingTree shrunk = new CodingTree(message);
		
		//Output codes to text file.
		PrintWriter writer = new PrintWriter("codes.txt", "UTF-8");
		writer.println("{");
		for (char key : shrunk.codes.keySet()) {
			String value = shrunk.codes.get(key);
			writer.println(key + "=" + value + ", ");
		}
		writer.println("}");
		writer.close();

		//Output compressed binary string to binary file.
		OutputStream out = new FileOutputStream("compressed.txt");
		byte[] b = new byte[(shrunk.bits.length() / 8) + 1];
		int currentIndex = 0;
		String currentByte = "";
		for (int i = 0; i < shrunk.bits.length(); i++) {
			currentByte += shrunk.bits.charAt(i);
			if ((i + 1) % 8 == 0) {
				b[currentIndex++] = ((byte) Integer.parseInt(currentByte, 2));
				currentByte = "";
			}
		}
		if (currentByte.length() != 0) {
			b[currentIndex] = Byte.parseByte(currentByte, 2);
		}
		out.write(b);
		out.close();
		
		//Display statistics in console window.		
		long beforeSize = new File("test.txt").length();
		long afterSize = new File("compressed.txt").length();
		double ratio = ((float) afterSize) / beforeSize;
		long endTime = System.nanoTime();
		System.out.printf("Before: %,d bytes\n", beforeSize);
		System.out.printf("After: %,d bytes\n", afterSize);
		System.out.printf("Ratio: %.2f%%\n", (ratio * 100));
		System.out.printf("Time (ms): %,d ms", ((endTime - startTime) / 1000000));
		
//		testMyPriorityQueue();
	}
	
	/**
	 * Tests MyPriorityQueue for binary heap structure and order properties by initializing a
	 * random collection of letters and creating a MyPriorityQueue from it. The letters should
	 * then be printed back out to the console in alphabetical order.
	 */
	@SuppressWarnings("unused")
	private static void testMyPriorityQueue() {
		Random rand = new Random();
		List<Character> charList = Arrays.asList('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 
												 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z');
		ArrayList<Character> test = new ArrayList<>();
		for (int i = 1; i < 32; i++) {
			test.add(charList.get(rand.nextInt(charList.size())));
		}
		MyPriorityQueue<Character> testPQ = new MyPriorityQueue<>(test);

		while (!testPQ.isEmpty()) {
			System.out.println("Size Before Removal: " + testPQ.size() + ", Min Element: " + testPQ.remove());			
		}
	}
}