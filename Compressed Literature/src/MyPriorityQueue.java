import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * MyPriorityQueue is a priority queue implementation using a
 * binary heap to order and structure the data inside of an array.
 * 
 * @author Alex Bledsoe & Anthony Trang
 * @version Feb 13, 2018
 */
public class MyPriorityQueue<T>{

	//Static constants:
	/** The initial size of the array when an empty queue is instantiated. */
	private static final int DEFAULT_SIZE = 32;
	
	//Instance fields:
	/** The current size of the queue. */
	private int myCurrentSize;
	/** The generic array that stores the objects held in the queue. */
	private T[] myArray;
	
	//Public methods:
	/**
	 * No-arg constructor passes null to overloaded constructor.
	 */
	public MyPriorityQueue() {
		this(null);
	}
	
	/**
	 * Constructor creates:
	 *  - An Empty queue if was null passed as an argument.
	 *  - A queue populated by the items in the collection passed in
	 *    if one was passed in
	 * 
	 * @param theCollection A collection of objects to be organized into
	 * 						a priority queue.
	 */
	@SuppressWarnings("unchecked")
	public MyPriorityQueue(Collection<T> theCollection) {
		if (theCollection == null) {
			myCurrentSize = 0;
			myArray = (T[]) new Object[DEFAULT_SIZE];
		} else {		
			myCurrentSize = theCollection.size();
			myArray = (T[]) new Object[myCurrentSize + 10];
			
			int i = 1;
			for (final T item : theCollection) {
				myArray[i++] = item; 
			}
			buildHeap();
		}
	}
	
	/**
	 * Adds an object to its' proper spot in the queue, 
	 * doubling the size of the array if needed.
	 * 
	 * @param theItem the object to add to the queue.
	 * @throws NullPointerException if theItem parameter is null.
	 */
	@SuppressWarnings("unchecked")
	public void add(T theItem) {
		final T safeItem = Objects.requireNonNull(theItem);
		if (myCurrentSize + 1 == myArray.length) {
			doubleArray();
		}
		int hole = ++myCurrentSize;
		myArray[0] = theItem;
		for ( ; ((Comparable<T>) safeItem).compareTo(myArray[hole / 2]) < 0; hole /= 2) {
			myArray[hole] = myArray[hole / 2];
		}
		myArray[hole] = safeItem;
	}
	
	/**
	 * Returns the objects at the head of the queue WITHOUT
	 * removing it, or throws and exception if the queue is empty. 
	 * 
	 * The object at the head of the queue always 
	 * has the minimum value.
	 * 
	 * @return The object at the head of the queue.
	 * @throws NoSuchElementException if the queue is empty.
	 */
	public T element() {
		if (isEmpty()) {
			throw new NoSuchElementException();
		} else {
			return myArray[1];
		}
	}
	
	/**
	 * Returns the objects at the head of the queue AND ALSO
	 * REMOVES IT FROM THE QUEUE, or throws and exception if the 
	 * queue is empty. 
	 * 
	 * The object at the head of the queue always 
	 * has the minimum value.
	 * 
	 * @return The object at the head of the queue.
	 * @throws NoSuchElementException if the queue is empty.
	 */
	public T remove() {
		final T currentMin = element();
		myArray[1] = myArray[myCurrentSize--];
		trickleDown(1);
		return currentMin;
	}
	
	/**
	 * @return the current size of the queue.
	 */
	public int size() {
		return myCurrentSize;
	}
	
	/**
	 * @return true if the queue has no elements in it; false otherwise.
	 */
	public boolean isEmpty() {
		return myCurrentSize == 0;
	}
	
	//Private helper methods:
	/**
	 * Maintains binary heap order property by comparing a node's two children for the smallest, 
	 * then comparing that child to the node. If the node is larger than the smallest child, it
	 * is replaced by that child. This process repeats until the node has found its' proper spot
	 * in the queue.
	 * 
	 * @param theHole The spot in the heap that needs to be filled (as an index in the array). 
	 * 				  This hole "trickles down" until the binary heap structure property is restored.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void trickleDown(int theHole) {
		final T temp = myArray[theHole];
		int child;
		
		for ( ; theHole * 2 <= myCurrentSize; theHole = child) {
			child = theHole * 2;
			if (child != myCurrentSize && ((Comparable) myArray[child + 1]).compareTo(myArray[child]) < 0) {
				child++;
			}
			if (((Comparable) myArray[child]).compareTo(temp) < 0) {
				myArray[theHole] = myArray[child];
			} else {
				break;
			}
		}
		myArray[theHole] = temp;
	}
	
	/**
	 * Builds a heap from scratch by starting from the bottom and building up.
	 * 
	 * *Note there is no need perform trickleDown on a leaf node so buildHeap starts
	 *  with the farthest node down with at least one child.
	 */
	private void buildHeap() {
		for (int i = myCurrentSize / 2; i > 0; i--) {
			trickleDown(i);
		}
	}
	
	/**
	 * Doubles the size of the array field so more objects can be stored in the queue.
	 */
	@SuppressWarnings("unchecked")
	private void doubleArray() {
		T[] temp = (T[]) new Object[myCurrentSize * 2];
		for (int i = 1; i < myCurrentSize; i++) {
			temp[i] = myArray[i];
		}
		myArray = temp;
	}
}