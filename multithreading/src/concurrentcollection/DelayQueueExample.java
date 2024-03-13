package concurrentcollection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

class DelayedWorker implements Delayed {

	// duration(delay) is going to define the time the given delay worker will be
	// delayed
	// before we can access it with the poll method.
	private long duration;
	private String message;

	public DelayedWorker(long duration, String message) {
		super();
		this.duration = System.currentTimeMillis() + duration;
		this.message = message;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "DelayWorker [duration=" + getDelay(TimeUnit.MILLISECONDS) + ", message=" + message + "]";
	}

	@Override
	public int compareTo(Delayed o) {
		// compare with different objects based on duration.

		if (this.duration < ((DelayedWorker) o).getDuration()) {
			// this object duration is smaller than the other objects duration
			return -1;
		}
		if (this.duration > ((DelayedWorker) o).getDuration()) {
			// this objects duration is greater than the other objects duration
			return 1;
		}
		// same durations
		return 0;
	}

	@Override
	public long getDelay(TimeUnit unit) {
		return unit.convert(this.duration - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
	}

}

public class DelayQueueExample {

	public static void main(String[] args) {

		BlockingQueue<DelayedWorker> delayQueue = new DelayQueue<DelayedWorker>();

		// wait for x milliseconds as the delay
		try {
			// these can br inserted by different threads
			delayQueue.put(new DelayedWorker(2000, "This is the first message.."));
			delayQueue.put(new DelayedWorker(5000, "This is the second message.."));
			delayQueue.put(new DelayedWorker(7000, "This is the third message.."));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Remove from the queue
		while (!delayQueue.isEmpty()) {
			try {
				// This waits for 2000 secs to print the first message, 5000 secs for second and
				// 7000 secs for third
				System.out.println(delayQueue.take());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
