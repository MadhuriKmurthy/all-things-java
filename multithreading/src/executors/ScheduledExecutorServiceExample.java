package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/***
 * An {@link ExecutorService} that can schedule commands to run after a given
 * delay, or to execute periodically.
 *
 * <p>
 * The {@code schedule} methods create tasks with various delays and return a
 * task object that can be used to cancel or check execution. The
 * {@code scheduleAtFixedRate} and {@code scheduleWithFixedDelay} methods create
 * and execute tasks that run periodically until cancelled.
 *
 */
class Downloader implements Runnable {

	@Override
	public void run() {
		System.out.println("Updating and downloading data from web.....");
	}
}

public class ScheduledExecutorServiceExample {

	public static void main(String[] args) {

		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

		/*
		 * Submits a periodic action that becomes enabled first after the given initial
		 * delay, and subsequently with the given period; that is, executions will
		 * commence after {@code initialDelay}, then {@code initialDelay + period}, then
		 * {@code initialDelay + 2 * period}, and so on.
		 */
		scheduledExecutorService.scheduleAtFixedRate(new Downloader(), 1000, 3000, TimeUnit.MILLISECONDS);

	}

}
