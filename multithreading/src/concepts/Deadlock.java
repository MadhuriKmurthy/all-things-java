package concepts;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Deadlock {

	// Initialize 2 locks
	private Lock lock1 = new ReentrantLock(true);
	private Lock lock2 = new ReentrantLock(true);

	private void worker1() {

		// Acquire lock1
		lock1.lock();
		System.out.println("Worker 1 acquired lock 1");

		// Do operations on thread
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Now also acquire lock2
		lock2.lock();
		System.out.println("Worker 1 acquires lock 2");

		// Do operations on thread
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Unlock both threads
		lock2.unlock();
		lock1.unlock();
	}
	
	private void worker2() {

		// Acquire lock2
		lock2.lock();
		System.out.println("Worker 2 acquired lock 2");

		// Do operations on thread
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Now also acquire lock1
		lock1.lock();
		System.out.println("Worker 2 acquires lock 1");

		// Do operations on thread
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Unlock both threads
		lock1.unlock();
		lock2.unlock();
	}

	public static void main(String[] args) {
		
		Deadlock deadlock = new Deadlock();
		
		Thread t1 = new Thread(deadlock::worker1, "worker1");
		Thread t2 = new Thread(deadlock::worker2, "worker2");
		
		t1.start();
		t2.start();
	}

}
