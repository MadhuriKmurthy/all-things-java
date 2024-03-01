package interthreadcomm;

public class SynchronizedKeyword {

	public static int counter = 0;

	// Method 1: To have synchronized keyword in the method signature
	// Problem : Every object has a single intrinsic lock
	public static synchronized void inc() {
		counter++;
	}

	// Method 2: To have a synchronized block
	// (this) - Class Level Locking
	// (Object) - Object Level Locking
	public void incSyncBlock() {
		synchronized (this) {
			counter++;
		}
	}

	public static void main(String[] args) {

		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {			
				for(int i = 0; i < 100; i++) {
					inc();
				}			
			}
		});
		
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {			
				for(int i = 0; i < 100; i++) {
					inc();
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

		SynchronizedKeyword synchronizedKeyword = new SynchronizedKeyword();
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {			
				for(int i = 0; i < 100; i++) {
					synchronizedKeyword.incSyncBlock();
				}			
			}
		});
		
		Thread t4 = new Thread(new Runnable() {
			@Override
			public void run() {			
				for(int i = 0; i < 100; i++) {
					synchronizedKeyword.incSyncBlock();
				}			
			}
		});
		
		t3.start();
		t4.start();
		
		try {
			t3.join();
			t4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("Counter after t3 and t4 :" + counter);		
		
	}

}
