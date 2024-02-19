package concepts;

/**
 * Using a volatile boolean flag: We can also use a volatile boolean flag to
 * make our code thread safe. A volatile variable is directly stored in the main
 * memory so that threads cannot have locally cached values of it. A situation
 * may arise when more than one threads are accessing the same variable and the
 * changes made by one might not be visible to other threads. In such a
 * situation, we can use a volatile boolean flag.
 */
public class StopAThread1 {

	public volatile boolean terminated = false;

	public boolean isTerminated() {
		return terminated;
	}

	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	public static void main(String[] args) {

		StopAThread1 stopAThread1 = new StopAThread1();

		// create a thread which runs until terminated
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!stopAThread1.isTerminated()) {
					System.out.println("Thread is running....");
				}
			}
		});

		// start the thread
		thread.start();

		// sleep for sometime
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// terminate the thread
		stopAThread1.setTerminated(true);

		// join the thread
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Thread terminated!");

	}

}
