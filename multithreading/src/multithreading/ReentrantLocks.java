package multithreading;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLocks {

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();
	private final int UPPER_LIMIT = 5;
	private final int LOWER_LIMIT = 0;
	private List<Integer> list = new ArrayList<>();
	private int value = 0;

	public void producer() throws InterruptedException {

		try {
			// acquire the lock
			lock.lock();

			while (true) {
				if (list.size() == UPPER_LIMIT) {
					// As soon as list size becomes equal to upper limit, wait (unlock) and go to
					// consumer
					System.out.println("Waiting - sending to consumer");
					value = 0;
					condition.await();
				} else {
					System.out.println("Producer adding value " + value + " to list");
					list.add(value);
					value++;
					// gotta keep notifying
					condition.signal();
				}
			}

		} finally {
			// unlock
			lock.unlock();
		}

	}

	public void consumer() throws InterruptedException {
		Thread.sleep(2000);

		try {
			// acquire the lock
			lock.lock();

			while (true) {
				if (list.size() == LOWER_LIMIT) {
					// As soon as list size becomes equal to lower limit, wait (unlock) and go to
					// producer
					System.out.println("Waiting - sending to producer");
					condition.await();
				} else {
					System.out.println("Consumer removing value " + list.remove(list.size() - 1) + " from list");
					// gotta keep notifying
					condition.signal();
				}
			}

		} finally {
			// unlock
			lock.unlock();
		}
	}

	public static void main(String[] args) {

		ReentrantLocks reentrantLocks = new ReentrantLocks();

		Thread producerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					reentrantLocks.producer();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		});

		Thread consumerThread = new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					reentrantLocks.consumer();
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
