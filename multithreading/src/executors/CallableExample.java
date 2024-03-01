package executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

class Processor implements Callable<String> {

	private int id;

	public Processor(int id) {
		super();
		this.id = id;
	}

	@Override
	public String call() throws Exception {
		Thread.sleep(2000);
		System.out.println("Processor processing...");
		return "id : " + this.id;
	}

}

public class CallableExample {

	public static void main(String[] args) {

		ExecutorService executorService = Executors.newFixedThreadPool(2);

		List<Future<String>> futures = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			futures.add(executorService.submit(new Processor(i)));
		}
		
		for(Future<String> future : futures) {
			try {
				System.out.println(future.get());
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		
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
