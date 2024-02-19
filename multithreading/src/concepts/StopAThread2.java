package concepts;

/**
 * Using Thread.interrupt() method: Whenever an interrupt has been sent to a
 * thread, it should stop whatever task it is performing. It is very likely that
 * whenever the thread receives an interrupt, it is to be terminated. This
 * action can be done by using the interrupt() method. Whenever
 * Thread.interrupt() is called, it sets a flag known as the interrupt status to
 * true. This means that the thread has to stop performing further execution.
 * The default value of this flag is false.
 */
public class StopAThread2 {

	public static void main(String[] args) {

		// create a thread which runs until interrupted
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				while (!Thread.interrupted()) {
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

		// interrupt the thread
		thread.interrupt();

		// join the thread
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Thread terminated!");
	}

}
