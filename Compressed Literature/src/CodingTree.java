import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * CodingTree carries out the various stages
 * of Huffman's encoding algorithm.
 * 
 * @author Alex Bledsoe & Anthony Trang
 * @version Feb 13, 2018
 */
public class CodingTree {
	
	//Static constants:
	/** The number of different characters in the extended ASCII table. */
	private static final int NUM_ASCII_VALUES = 1112064;
	
	//Instance fields:
	/** A map that contains the path to each character in the Huffman Tree. */
	public Map<Character, String> codes;
	/** The original contents of the text file written in their bit codes. */
	public String bits;
	
	/**
	 * Constructor acts as the controller for encoding 
	 * the message that was passed to it.
	 */
	public CodingTree(String theMessage) {
		
		//Initialize public fields.
		codes = new HashMap<>();
		bits = "";
		
		// Get character frequencies.
		int[] charFrequency = getCharFrequency(theMessage);
		
		/* Build a Huffman tree using the character frequencies, keeping
		 * a reference to the root of the tree. */
		Node root = buildHuffmanTree(charFrequency);
		
		//Creates a map with the binary codes for each character.
		writeHashMap(root, "");
		
		//Uses the map of codes to convert the original string into a binary string.
		writeBits(theMessage);
	}

	/**
	 * Gets the frequency of each character in the message in the form of an array.
	 * 
	 * The index represents the character's  ASCII value, and the value stored 
	 * at that index represents the frequency of that character in the message.
	 * 
	 * @param theMessage a string with all of the text form the file.
	 * @return an array with frequencies for each character.
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
	 * Creates a HuffmanTree using an array of character frequencies.
	 * 
	 * A single node is created and added to an ArrayList for each character.
	 * The ArrayList is ordered using a PriorityQueue (least frequent characters have the highest priority),
	 * and a Huffman tree is built from the queue. The root of the tree is then returned.
	 * 
	 * @param theCharFrequency an array of character frequencies.
	 * @return the root of the Huffman tree.
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
	 * Recursively traverses the entire Huffman tree to create codes for each character.
	 * Every left or right down the tree adds a '0' or '1' to the end of that character's
	 * code (respectively) and is only added to the map when it hits a leaf (character) node.
	 * 
	 * @param root the root of the Huffman tree.
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
	 * Writes a new string identical to the original message, but using 
	 * a character's bit code instead of the character itself.
	 * 
	 * @param theMessage a string with all of the text form the file.
	 */
	private void writeBits(String theMessage) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < theMessage.length(); i++) {
			char thisChar = theMessage.charAt(i);
			sb.append(codes.get(thisChar));
		}
		bits = sb.toString();
	}
	
	/**
	 * A Node object holds information on the frequency of
	 * a specific character in the text file, including the
	 * character itself and the amount of times it appears
	 * in the file.
	 *
	 * @author Alex Bledsoe & Anthony Trang
	 * @version Feb 13, 2018
	 */
	public class Node implements Comparable<Node> {
		
		//Instance fields:
		/** The character this node is keeping track of. */
		private char myChar;
		/** The number of times myChar shows up in the file. */
		private int myCount;
		/** The left child of this node. */
		private final Node myLeft;
		/** The right child of this node. */
		private final Node myRight;
		
		/**
		 * Constructor passes its' parameters to the overloaded constructor.
		 * 
		 * @param theChar
		 * @param theCount
		 */
		public Node(char theChar, int theCount) {
			this(theChar, theCount, null, null);
		}
		
		/**
		 * Overloaded constructor initializes the instance fields.
		 * 
		 * @param theChar the character this node is counting.
		 * @param theCount the frequency of that character.
		 * @param theLeft the left child of this node.
		 * @param theRight the right child of this node.
		 */
		public Node(char theChar, int theCount, Node theLeft, Node theRight) {
			myChar = theChar;
			myCount = theCount;
			myLeft = theLeft;
			myRight = theRight;
		}
		
		/** @return The character this node is counting. */
		public char getChar() {
			return myChar;
		}
		
		/** @return The number of times the character shows up in the file. */
		public int getCount() {
			return myCount;
		}
		
		/** @return The left child of this node. */
		public Node getLeft() {
			return myLeft;
		}
		
		/** @return The right child of this node. */
		public Node getRight() {
			return myRight;
		}
		
		/** @return true if this node has no children; false otherwise. */
		public boolean isLeaf() {
			return (myLeft == null && myRight == null);
		}

		/**
		 * Compares the count field with another node.
		 * 
		 * @return a negative number if this node's count is less than the other node's.
		 * 		   a positive number if this node's count is greater than the other node's.
		 * 		   0 if the two nodes have the same count.
		 */
		@Override
		public int compareTo(final Node theOther) {
			return myCount - theOther.getCount();
		}		
	}
}