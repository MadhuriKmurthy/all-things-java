package concepts;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicVariables {

	// Instead of doing all these
	/*
	 * public static int counter = 0;
	 * 
	 * public static synchronized void inc() { counter++; }
	 * 
	 * public void incSyncBlock() { synchronized (this) { counter++; } }
	 */

	public static AtomicInteger counter = new AtomicInteger(0);

	public static void main(String[] args) {
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					counter.getAndIncrement();
				}
			}
		});

		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 100; i++) {
					counter.getAndIncrement();
				}
			}
		});

		t1.start();
		t2.start();

		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("Counter after t1 and t2 :" + counter);

	}

}
