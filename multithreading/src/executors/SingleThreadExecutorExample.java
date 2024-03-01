package executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Task implements Runnable {

	private int ID;
	
	public Task(int id) {
		this.ID = id;
	}
	
	@Override
	public void run() {
		
		System.out.println("Task with id " + ID + " is in work -- thread id: " + Thread.currentThread().getName());
		long duration = (long) Math.random() * 5;
		
		try {
			// another way to sleep.
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
}
public class SingleThreadExecutorExample {

	public static void main(String[] args) {
	
		// single thread will execute the tasks sequentionally
		// if that one fails/stop -  it will create new one
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		
		for(int i = 0; i < 5; i++) {
			executorService.execute(new Task(i));
		}
	}

}
