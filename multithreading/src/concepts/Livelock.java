package concepts;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Livelock {

	// Initialize 2 locks
	private Lock lock1 = new ReentrantLock(true);
	private Lock lock2 = new ReentrantLock(true);

	private void worker1() {

		while (true) {

			try {
				// try to acquire lock1
				lock1.tryLock(50, TimeUnit.MILLISECONDS);
				System.out.println("Worker 1 acquired lock 1");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Do operations on thread
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// try to acquire lock2, if not able to acquire the lock, then continue
			if (lock2.tryLock()) {
				System.out.println("Worker 1 acquired lock 2");
				// unlock
				lock2.unlock();
			} else {
				System.out.println("Worker 1 unable to acquire lock 2..");
				continue;
			}

			break;
		}
		// unlock both locks
		lock2.unlock();
		lock1.unlock();

	}

	private void worker2() {

		while (true) {

			try {
				// try to acquire lock2
				lock2.tryLock(50, TimeUnit.MILLISECONDS);
				System.out.println("Worker 2 acquired lock 2");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			// Do operations on thread
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// try to acquire lock2, if not able to acquire the lock, then continue
			if (lock1.tryLock()) {
				System.out.println("Worker 2 acquired lock 1");
				// unlock
				lock1.unlock();
			} else {
				System.out.println("Worker 2 unable to acquire lock 1..");
				continue;
			}

			break;
		}
		// unlock both locks
		lock1.unlock();
		lock2.unlock();

	}

	public static void main(String[] args) {

		Livelock livelock = new Livelock();

		Thread t1 = new Thread(livelock::worker1, "worker1");
		Thread t2 = new Thread(livelock::worker2, "worker2");

		t1.start();
		t2.start();

	}

}
