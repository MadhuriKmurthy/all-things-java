package multithreading;

import java.util.ArrayList;
import java.util.List;

public class WaitAndNotify {

	private final Object lock = new Object();
	private final int UPPER_LIMIT = 5;
	private final int LOWER_LIMIT = 0;
	private List<Integer> list = new ArrayList<>();
	private int value = 0;

	public void producer() throws InterruptedException {

		synchronized (lock) {

			while (true) {
				if (list.size() == UPPER_LIMIT) {
					// As soon as list size becomes equal to upper limit, wait (unlock) and go to
					// consumer
					System.out.println("Waiting - sending to consumer");
					value = 0;
					lock.wait();
				} else {
					System.out.println("Producer adding value " + value + " to list");
					list.add(value);
					value++;
					// gotta keep notifying
					lock.notify();
				}
			}

		}

	}

	public void consumer() throws InterruptedException {

		synchronized (lock) {

			while (true) {
				if (list.size() == LOWER_LIMIT) {
					// As soon as list size becomes equal to lower limit, wait (unlock) and go to
					// producer
					System.out.println("Waiting - sending to producer");
					lock.wait();
				} else {
					System.out.println("Consumer removing value "+list.remove(list.size() - 1)+" from list");
					// gotta keep notifying
					lock.notify();
				}
			}

		}
	}

	public static void main(String[] args) {

		WaitAndNotify waitAndNotify = new WaitAndNotify();

		Thread producerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					waitAndNotify.producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		Thread consumerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					waitAndNotify.consumer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});
		
		producerThread.start();
		consumerThread.start();
		
		try {
			producerThread.join();
			consumerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


	}

}
