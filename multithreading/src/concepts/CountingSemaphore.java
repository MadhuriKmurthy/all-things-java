package concepts;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/***
 * Java Counting Semaphore maintains a specified number of passes or
 * permissions, and the Current Thread must obtain a permit to access a shared
 * resource. If a permit is already exhausted by threads other than that, it may
 * wait until the permit becomes available as a result of the release of permits
 * from various threads.
 * 
 * acquire(): This method acquires a permit if one is available, and returns
 * immediately, reducing the number of available permits by one. If the current
 * thread is interrupted while waiting for a permit then InterruptedException is
 * thrown. 
 * 
 * release(): This method acquires the given number of permits, if they
 * are available, and returns immediately, reducing the number of available
 * permits by the given amount. If the current thread is interrupted while
 * waiting for a permit then InterruptedException is thrown.
 */

public class CountingSemaphore {

	// counting semaphore with 3 permits
	static Semaphore semaphore = new Semaphore(3, true);

	private static void runProcess() {

		try {
			semaphore.acquire();

			System.out.println("Downloading...");
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
