package concepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class BinarySemaphore {

	// binary semaphore with 1 permit always
	static Semaphore semaphore = new Semaphore(1);

	private static void runProcess() {

		try {
			semaphore.acquire();

			System.out.println("Printing...");
			Thread.sleep(2000);

		} catch (InterruptedException e1) {
			e1.printStackTrace();
		} finally {
			semaphore.release();
		}

	}

	public static void main(String[] args) {

		// Create multiple threads with Executors
		ExecutorService executorService = Executors.newCachedThreadPool();

		for (int i = 0; i < 12; i++) {
			executorService.execute(new Runnable() {

				@Override
				public void run() {
					runProcess();
				}

			});
		}

	}

}
