import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * 
 * @author Alex Bledsoe & Anthony Trang
 * @version Feb 13, 2018
 */
public class MyPriorityQueue<T>{

	//Static constants:
	/**  */
	private static final int DEFAULT_SIZE = 32;
	
	//Instance fields:
	/**  */
	private int myCurrentSize;
	/**  */
	private T[] myArray;
	
	//Public methods:
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	public MyPriorityQueue() {
		myCurrentSize = 0;
		myArray = (T[]) new Object[DEFAULT_SIZE];
	}
	
	/**
	 * 
	 * @param theCollection
	 */
	@SuppressWarnings("unchecked")
	public MyPriorityQueue(Collection<T> theCollection) {
		myCurrentSize = theCollection.size();
		myArray = (T[]) new Object[myCurrentSize + 10];
		
		int i = 1;
		for (final T item : theCollection) {
			myArray[i++] = item; 
		}
		buildHeap();
	}
	
	/**
	 * 
	 * @param theItem
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
	 * 
	 * @return
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
	 * 
	 * @return
	 */
	public T remove() {
		final T currentMin = element();
		myArray[1] = myArray[myCurrentSize--];
		trickleDown(1);
		return currentMin;
	}
	
	/**
	 * 
	 * @return
	 */
	public int size() {
		return myCurrentSize;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return myCurrentSize == 0;
	}
	
	/**
	 * 
	 */
	public void clear() {
		myCurrentSize = 0;
	}
	
	//Private helper methods:
	/**
	 * 
	 * @param theHole
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
	 * 
	 */
	private void buildHeap() {
		for (int i = myCurrentSize / 2; i > 0; i--) {
			trickleDown(i);
		}
	}
	
	/**
	 * 
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