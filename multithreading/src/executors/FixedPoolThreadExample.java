package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Work implements Runnable {

	private int ID;

	public Work(int id) {
		this.ID = id;
	}

	@Override
	public void run() {

		System.out.println("Task with id " + ID + " is in work -- thread id: " + Thread.currentThread().getName());
		long duration = (long) Math.random() * 5;

		try {
			// another way to sleep.
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}

public class FixedPoolThreadExample {

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		for (int i = 1; i <= 100; i++) {
			executorService.execute(new Work(i));
		}

		// Terminating the executor
		shutdownAndAwaitTermination(executorService);
	}

	private static void shutdownAndAwaitTermination(ExecutorService pool) {
		// Disable new tasks from being submitted
		pool.shutdown();
		try {
			// Wait a while for existing tasks to terminate
			if (!pool.awaitTermination(60, TimeUnit.SECONDS)) {
				// Cancel currently executing tasks forcefully
				pool.shutdownNow();
				// Wait a while for tasks to respond to being cancelled
				if (!pool.awaitTermination(60, TimeUnit.SECONDS))
					System.err.println("Pool did not terminate");
			}
		} catch (InterruptedException ex) {
			// (Re-)Cancel if current thread also interrupted
			pool.shutdownNow();
			// Preserve interrupt status
			Thread.currentThread().interrupt();
		}
	}

}
