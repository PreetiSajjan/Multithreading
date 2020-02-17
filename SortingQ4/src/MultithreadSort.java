import java.util.Arrays;
import java.util.Scanner;

/**
 * Implements the recursive merge sort algorithm
 * with multithreading approach
 * @author Preeti Sajjan
 */
class MultithreadSort{
	
	int[] array;
	
	/**
	 *Creating a method sort which assigns the user input to array and further calls sort method
	 *@param array is the user input which needs to be sorted
	 */
	public void sort(int[] array) { // array length must be a power of 2
		this.array = array;
		sort(0, array.length);
	} 
	 
	/**
	 * Creating sort method which recursively calls itself with the concept of multithreading 
	 * dividing the array into halves for sorting and then merging them
	 * @param low is the lowest index of array
	 * @param n is the length i.e highest index of array
	 */
	private void sort(int low, int n) {
		if (n > 1) {
			//Split the array in halves
			int mid = n >> 1;
			
			//Creating Thread th1
			Thread th1 = new Thread() {
				public void run(){
					//Sorting the first half
					sort(low, mid);
				}				
			};
			//Creating Thread th2
			Thread th2 = new Thread() {
				public void run(){
					//Sorting the second half
					sort(low + mid, mid);
				}				
			};
			//Initiating both the threads with the help of start()
			th1.start();
			th2.start();
			
			//Joining both the threads to combine the result
			try {
				th1.join();
				th2.join();
			} catch (InterruptedException e) {
				// Handling the Interrupted Exception if raised while joining the threads
				e.printStackTrace();
			}
			
			//Merge the halves
			combine(low, n, 1);
		}
	} 
	 
	/**
	 * Creating combine method which recursively calls itself merging the divided array after comparing and sorting
	 * @param low is the lowest index of divided array
	 * @param n is the highest index of divided array
	 * @param st is number of left shifts to be done to merge the array
	 */
	private void combine(int low, int n, int st) {
	    int m = st << 1;
		if (m < n) {
			combine(low, n, m);
			combine(low + st, n, m);
			for (int i = low + st; i + st < low + n; i += m)
				compareAndSwap(i, i + st);
		} else
			compareAndSwap(low, low + st);    
	} 
	 
	/**
	 * Creating compareAndSwap method which compares the elements and further calls swap method to swap them
	 * @param i is lower index of the pair
	 * @param j is higher index of the pair
	 */
	private void compareAndSwap(int i, int j) {
	    if (array[i] > array[j])
			swap(i, j);
	} 
	 
	/**
	 * Creating swap method to swap the elements
	 * @param i is lower index of the pair
	 * @param j is higher index of the pair
	 */
	private void swap(int i, int j) {
	    int h = array[i];
		array[i] = array[j];
		array[j] = h;
	}  
	
	public static void main(String args[]) {
		
		//Creating an object of the class Sort
		MultithreadSort sort_obj = new MultithreadSort();
		
		System.out.println("\nSize of the array should be in powers of 2 with atleast 16 elements\n");
		Scanner out = new Scanner(System.in);
		
		System.out.println("Enter the length of array: ");
		int n = out.nextInt();
		
		//checking if the length of the array is in powers of 2 and with atleast 16 elements in it
		if((n >= 16) && ((n&(n-1)) == 0)) {
			
			//Reading the input from user
			int[] InputArray = new int[n];
			System.out.println("Enter the integer array:"); 
			for(int i = 0; i < n; i++)
				InputArray[i] = out.nextInt();			 
			
			System.out.println("Original Array: "+ Arrays.toString(InputArray));
			//passing the input array for sorting
			sort_obj.sort(InputArray);
			System.out.println("Sorted Array: "+ Arrays.toString(InputArray));
			
		}
		else
			System.out.println("Array length restrictions: Size of array should be in powers of 2 with atleast 16 elements in it.");
		
		//Closing the scanner to avoid resource leakage
		out.close();
	}
}
