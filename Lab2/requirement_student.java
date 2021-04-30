class Threadsrequirement {
	public static void main(String... args) throws InterruptedException {
		BookStock b = new BookStock(10);

		Supplier supplier = new Supplier(b);
		StoreBranch gizaBranch = new StoreBranch(b), cairoBranch = new StoreBranch(b), alexBranch = new StoreBranch(b);

		/*
		 * TODO 1: Create 4 threads, 1 thread for Supplier, named "Supplier". 3 threads
		 * for StoreBranches and name them: "Giza Branch", "Cairo Branch", and
		 * "Alex Branch".
		 */
		Thread supplierThread = new Thread(supplier);
		Thread gizaThread = new Thread(gizaBranch), cairoThread = new Thread(cairoBranch),
				alexThread = new Thread(alexBranch);

		supplierThread.setName("Supplier");
		gizaThread.setName("Giza Branch");
		cairoThread.setName("Cairo Branch");
		alexThread.setName("Alex Branch");

		/*
		 * TODO-2: Run the 4 threads.
		 */
		supplierThread.start();
		cairoThread.start();
		alexThread.start();
		gizaThread.start();
	}
}

class BookStock {
	private int bookCount;
	private final int maxCount;

	public BookStock(int max) {
		this.maxCount = max;
	}

	public void produce() {
		bookCount++;
	}

	public void consume() {
		bookCount--;
	}

	public int getCount() {
		return bookCount;
	}

	public final int getMaxCount() {
		return maxCount;
	}
}

/*
 * TODO-3: Should the class Supplier extend any class or implement any
 * interface? Yes, we have 2 options to be able to use the threads in Java. The
 * first option is to implement the Runnable interface so you can create new
 * thread and pass it. The second option is to extend the Thread class so you
 * can deal with the objects directly.
 */
class Supplier implements Runnable {
	private BookStock b;

	public Supplier(BookStock b) {
		this.b = b;
	}

	/*
	 * TODO-4: Is there a function missing here? What does this function do? Yes,
	 * the run() funtion which is the main code called into the thread
	 */
	public void run() {
		doWork();
	}

	public void doWork() {
		while (true) {
			synchronized (b) {
				try {
					/*
					 * TODO-5: How to make the supplier stop producing when it reaches maxCount,
					 * without adding extra sleeps or busy waiting ? Check Example 11 in the lab
					 * code examples.
					 */
					// Supplier waits when it reach max count
					if (b.getCount() == b.getMaxCount())
						b.wait();

					b.produce();

					// Notify the stores threads that now they can start consuming
					b.notifyAll();

					System.out.println(Thread.currentThread().getName() + " provided a book, total " + b.getCount());

				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + "is awaken");
			}
		}
	}

}

/*
 * TODO-6: Should the class StoreBranch extend any class or implement any
 * interface? Yes, we have 2 options to be able to use the threads in Java. The
 * first option is to implement the Runnable interface so you can create new
 * thread and pass it. The second option is to extend the Thread class so you
 * can deal with the objects directly.
 */
class StoreBranch implements Runnable {
	private BookStock b;

	public StoreBranch(BookStock b) {
		this.b = b;
	}

	/*
	 * TODO-7: Is there a function missing here? What does this function do? * Yes,
	 * the run() funtion which is the main code called into the thread
	 */
	public void run() {
		doWork();
	}

	public void doWork() {
		while (true) {
			synchronized (b) {
				try {
					/*
					 * TODO-8: How to make the store branch stop consuming when the store is empty,
					 * without adding extra sleeps or busy waiting ? Check Example 11 in lab code
					 * examples.
					 */
					// Store waits while there is no books
					if (b.getCount() == 0)
						b.wait();

					// Consume a book
					b.consume();

					// Notify the supplier thread that now it can start producing
					b.notifyAll();

					System.out.println(Thread.currentThread().getName() + " sold a book");

				} catch (InterruptedException e) {
					System.out.println(e.getMessage());
				}
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				System.out.println(Thread.currentThread().getName() + "is awaken");
			}
		}
	}
}