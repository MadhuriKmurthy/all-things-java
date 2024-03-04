package concurrentcollection;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CyclicWorker implements Runnable {

	private int id;
	private Random random;
	private CyclicBarrier cyclicBarrier;

	public CyclicWorker(int id, CyclicBarrier cyclicBarrier) {
		super();
		this.id = id;
		this.random = new Random();
		this.cyclicBarrier = cyclicBarrier;
	}

	@Override
	public void run() {
		// perform some operation
		doSomeWork();
	}

	private void doSomeWork() {
		System.out.println("Thread with ID " + this.id + " starts the work..");

		try {
			Thread.sleep(random.nextInt(3000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			//each thread calls await()
			cyclicBarrier.await();
		} catch (InterruptedException | BrokenBarrierException e) {
			e.printStackTrace();
		}
		
		System.out.println("After await for thread ID " + this.id);
	}

}

public class CyclicBarrierExample {

	public static void main(String[] args) {
		// create exectors with 5
		ExecutorService executorService = Executors.newFixedThreadPool(5);

		// create a cyclic barrier with 5 parties
		// runnable method => barrier action, will run when counter becomes 0
		CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new Runnable() {
			@Override
			public void run() {
				System.out.println("All tasks have been finished..");
			}
		});

		for (int i = 0; i < 5; i++) {
			executorService.submit(new CyclicWorker(i, cyclicBarrier));
		}

		executorService.shutdown();
	}

}
