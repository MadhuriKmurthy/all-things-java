package concurrentcollection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

class Producer implements Runnable {

	private int counter;
	private BlockingQueue<Integer> queue;

	public Producer(int counter, BlockingQueue<Integer> queue) {
		super();
		this.counter = counter;
		this.queue = queue;
	}

	@Override
	public void run() {
		while (counter > 0) {

			try {
				System.out.println("Adding item " + counter + " to the queue..");
				queue.put(counter);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			counter--;
		}
	}

}

class Consumer implements Runnable {

	BlockingQueue<Integer> queue;

	public Consumer(BlockingQueue<Integer> queue) {
		super();
		this.queue = queue;
	}

	@Override
	public void run() {

		while (true) {

			try {
				int gotCounter = queue.take();
				System.out.println("Taking item " + gotCounter + " from the queue!");
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

}

public class BlockingQueueExample {

	public static void main(String[] args) {

		BlockingQueue<Integer> blockingQueue = new LinkedBlockingDeque<Integer>(30);

		new Thread(new Producer(30, blockingQueue)).start();
		new Thread(new Consumer(blockingQueue)).start();

	}

}
