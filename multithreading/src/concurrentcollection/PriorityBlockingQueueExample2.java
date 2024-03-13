package concurrentcollection;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;

class Person implements Comparable<Person> {

	private int age;
	private String name;

	public Person(int age, String name) {
		this.age = age;
		this.name = name;
	}

	@Override
	public int compareTo(Person o) {
		return Integer.compare(this.age, o.age);
	}

	@Override
	public String toString() {
		return "Person [age=" + age + ", name=" + name + "]";
	}

}

class FirstWorker2 implements Runnable {

	private BlockingQueue<Person> priorityBlockingQueue;

	public FirstWorker2(BlockingQueue<Person> priorityBlockingQueue) {
		this.priorityBlockingQueue = priorityBlockingQueue;
	}

	@Override
	public void run() {
		try {
			priorityBlockingQueue.put(new Person(39, "Rachale"));
			priorityBlockingQueue.put(new Person(45, "Joey"));
			priorityBlockingQueue.put(new Person(18, "Ross"));
			Thread.sleep(2000);
			priorityBlockingQueue.put(new Person(17, "Chandler"));
			Thread.sleep(2000);
			priorityBlockingQueue.put(new Person(29, "Phoebe"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}

class SecondWorker2 implements Runnable {

	private BlockingQueue<Person> priorityBlockingQueue;

	public SecondWorker2(BlockingQueue<Person> priorityBlockingQueue) {
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

public class PriorityBlockingQueueExample2 {

	public static void main(String[] args) {
		BlockingQueue<Person> blockingQueue = new PriorityBlockingQueue<Person>();

		FirstWorker2 first = new FirstWorker2(blockingQueue);
		SecondWorker2 second = new SecondWorker2(blockingQueue);

		new Thread(first).start();
		new Thread(second).start();
	}

}
