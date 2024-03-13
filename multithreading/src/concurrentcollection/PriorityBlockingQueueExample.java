package concurrentcollection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

class FirstWorker implements Runnable {

	private BlockingQueue<String> priorityBlockingQueue;

	public FirstWorker(BlockingQueue<String> priorityBlockingQueue) {
		this.priorityBlockingQueue = priorityBlockingQueue;
	}

	@Override
	public void run() {
		try {
			priorityBlockingQueue.put("D");
			priorityBlockingQueue.put("H");
			priorityBlockingQueue.put("S");
			Thread.sleep(2000);
			priorityBlockingQueue.put("A");
			Thread.sleep(2000);
			priorityBlockingQueue.put("Z");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

class SecondWorker implements Runnable {

	private BlockingQueue<String> priorityBlockingQueue;

	public SecondWorker(BlockingQueue<String> priorityBlockingQueue) {
		this.priorityBlockingQueue = priorityBlockingQueue;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(5000);
			System.out.println(priorityBlockingQueue.take());
			Thread.sleep(1000);
			System.out.println(priorityBlockingQueue.take());
			Thread.sleep(2000);
			System.out.println(priorityBlockingQueue.take());
			System.out.println(priorityBlockingQueue.take());
			System.out.println(priorityBlockingQueue.take());

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

public class PriorityBlockingQueueExample {

	public static void main(String[] args) {

		BlockingQueue<String> blockingQueue = new PriorityBlockingQueue<String>();
		
		FirstWorker first = new FirstWorker(blockingQueue);
		SecondWorker second = new SecondWorker(blockingQueue);
		
		new Thread(first).start();
		new Thread(second).start();
	
	}

}
