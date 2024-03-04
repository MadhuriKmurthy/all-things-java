package concurrentcollection;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Worker implements Runnable {

	private int id;
	private List<String> output;
	private CountDownLatch countDownLatch;

	public Worker(int id, List<String> output, CountDownLatch countDownLatch) {
		this.id = id;
		this.output = output;
		this.countDownLatch = countDownLatch;
	}

	@Override
	public void run() {
		// perform some operation
		doSomeWork();
		output.add("Counted down by :" + this.id);

		// Decrements the count of the latch, releasing all waiting threads if
		// the count reaches zero
		countDownLatch.countDown();
	}

	private void doSomeWork() {
		try {
			Thread.sleep(2000);
			System.out.println(Thread.currentThread().getName()+ " and task "+ this.id + " at work!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

public class CountdownLatchExample {

	public static void main(String[] args) {

		// Initialize a countdown latch with count of 5
		CountDownLatch countDownLatch = new CountDownLatch(5);

		// Initialize a executor service
		ExecutorService executorService = Executors.newSingleThreadExecutor();

		List<String> output = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			executorService.submit(new Worker(i, output, countDownLatch));
		}

		// await
		try {
			countDownLatch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		//main thread is waiting for 5 other threads to finish execution, then it continues
		System.out.println("All tasks have been finished....");
		
		output.forEach(s -> System.out.println(s));
		executorService.shutdown();
	}

}
