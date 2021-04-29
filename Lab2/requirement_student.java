class Threadsrequirement {
	 public static void main(String... args) throws InterruptedException {
	 	BookStock b = new BookStock (10);
		
		/*
		 * TODO 1: Create 4 threads,
		 * 1 thread for Supplier, named "Supplier".
		 * 3 threads for StoreBranches and name them: "Giza Branch", "Cairo Branch", and "Alex Branch".
	 	 */

		/*
		 * TODO-2: Run the 4 threads.
		 */
    }
}

class BookStock {
	private int bookCount;
	private final int maxCount;
	public BookStock  (int max) {
		this.maxCount = max;
	}
	public void produce() {
		bookCount++;
	}

	public void consume () {
		bookCount--;
	}

	public int getCount () {
		return bookCount;
	}
	
	public final int getMaxCount() {
		return maxCount; 
	}
}

/*
 * TODO-3: Should the class Supplier extend any class or implement any interface?
 */
class Supplier {
	private BookStock b;

	public Supplier (BookStock b) {
		this.b = b;
	}
	
	/*
	 * TODO-4: Is there a function missing here? What does this function do?
	 */
	
	public void doWork () {
		while (true) {
			/*
			 * TODO-5: How to make the supplier stop producing when it reaches maxCount,
			 *  without adding extra sleeps or busy waiting ?
			 *  Check Example 11 in the lab code examples.
			 */
			b.produce();
			System.out.println (Thread.currentThread().getName() + " provided a book, total " +b.getCount());
			try {
				Thread.sleep (200);
			} catch (InterruptedException e) {
				System.out.println (Thread.currentThread().getName() + "is awaken");
			}
        }
	}

}

/*
 * TODO-6: Should the class StoreBranch extend any class or implement any interface?
 */
class StoreBranch {
	private BookStock b;

	public StoreBranch (BookStock b) {
		this.b = b;
	}

	/*
	 * TODO-7: Is there a function missing here? What does this function do?
	 */
	
	public void doWork () {
		while (true) {
			/*
			 * TODO-8: How to make the store branch stop consuming when the store is empty,
			 *  without adding extra sleeps or busy waiting ?
			 *  Check Example 11 in lab code examples. 
			 */
			b.consume();
			System.out.println (Thread.currentThread().getName() + " sold a book");
			try {
				Thread.sleep (2000);
			} catch (InterruptedException e) {
				System.out.println (Thread.currentThread().getName() + "is awaken");
			}
        }
	}
}