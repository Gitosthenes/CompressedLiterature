import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 
 * @author Alex Bledsoe
 * @version Feb 13, 2018
 */
public class CodingTree {
	
	private static final int NUM_ASCII_VALUES = 256;
	
	/**  */
	public Map<Character, String> codes;
	
	/**  */
	public String bits;
	
	/**
	 * 
	 */
	public CodingTree(String theMessage) {
		//Initialize public fields.
		codes = new HashMap<>();
		bits = "";
		System.out.println("3.1: " + LocalDateTime.now());//TODO
		/* Get character frequencies. The index represents the character's 
		 * ASCII value, and the value stored at that index represents the
		 * frequency of that character in the file. */
		int[] charFrequency = getCharFrequency(theMessage);
		System.out.println("3.2: " + LocalDateTime.now());//TODO
		/* Build a Huffman tree using the character frequencies, keeping
		 * a reference to the root of the tree. */
		Node root = buildHuffmanTree(charFrequency);
		System.out.println("3.3: " + LocalDateTime.now());//TODO
		//Creates a map with the binary codes for each character.
		writeHashMap(root, "");
		System.out.println("3.4: " + LocalDateTime.now());//TODO
		//Uses the map of codes to convert the original string into a binary string.
		writeBits(theMessage);
	}

	/**
	 * 
	 * 
	 * @param theMessage
	 * @return
	 */
	private int[] getCharFrequency(String theMessage) {
		int[] charFrequency = new int[NUM_ASCII_VALUES];
		for (int c = 0; c < theMessage.length(); c++) {
			char thisChar = theMessage.charAt(c);
			charFrequency[(int) thisChar]++;
		}
		return charFrequency;
	}

	/**
	 * 
	 * 
	 * @param theCharFrequency
	 * @return
	 */
	private Node buildHuffmanTree(final int[] theCharFrequency) {
		ArrayList<Node> nodes = new ArrayList<>();
		for (int i = 0; i < theCharFrequency.length; i++) {
			if (theCharFrequency[i] > 0) {
				nodes.add(new Node((char) i, theCharFrequency[i]));
			}
		}
		MyPriorityQueue<Node> pq = new MyPriorityQueue<>(nodes);
		while (pq.size() > 1) {
			Node left = pq.remove();
			Node right = pq.remove();
			Node parent = new Node((char) 0, left.getCount() + right.getCount(), left, right);
			pq.add(parent);
		}
		return pq.remove();
	}
	
	/**
	 * 
	 * 
	 * @param root
	 */
	private void writeHashMap(final Node theRoot, String theValue) {
		if (!theRoot.isLeaf()) {
			writeHashMap(theRoot.getLeft(), theValue + "0");
			writeHashMap(theRoot.getRight(), theValue + "1");
		} else {
			codes.put(theRoot.getChar(), theValue);
		}
	}
	
	/**
	 * 
	 * 
	 * @param theMessage
	 */
	private void writeBits(String theMessage) {
		for (int i = 0; i < theMessage.length(); i++) {
			char thisChar = theMessage.charAt(i);
			bits += codes.get(thisChar);
		}
	}
	
	public class Node implements Comparable<Node> {
		
		/**  */
		private char myChar;
		/** */
		private int myCount;
		/**  */
		private final Node myLeft;
		/**  */
		private final Node myRight;
		
		/**
		 * 
		 * @param theChar
		 * @param theCount
		 */
		public Node(char theChar, int theCount) {
			this(theChar, theCount, null, null);
		}
		
		public Node(char theChar, int theCount, Node theLeft, Node theRight) {
			myChar = theChar;
			myCount = theCount;
			myLeft = theLeft;
			myRight = theRight;
		}
		
		/**
		 * 
		 * @return
		 */
		public char getChar() {
			return myChar;
		}
		
		/**
		 * 
		 * @return
		 */
		public int getCount() {
			return myCount;
		}
		
		/**
		 * 
		 * @return
		 */
		public Node getLeft() {
			return myLeft;
		}
		
		/**
		 * 
		 * @return
		 */
		public Node getRight() {
			return myRight;
		}
		
		/**
		 * 
		 * @return
		 */
		public boolean isLeaf() {
			return (myLeft == null && myRight == null);
		}

		/**
		 * 
		 */
		@Override
		public int compareTo(final Node theOther) {
			return myCount - theOther.getCount();
		}		
	}
}