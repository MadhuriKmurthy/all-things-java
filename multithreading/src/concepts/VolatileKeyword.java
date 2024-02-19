package concepts;

public class VolatileKeyword {

	private static volatile int myInt = 0;

	public static void main(String[] args) {

		Thread changeMaker = new Thread(new Runnable() {
			@Override
			public void run() {
				// local copy of the shared variable
				int localCopy = myInt;
				while (myInt < 5) {
					// increment the shared variable
					myInt = localCopy++;
					System.out.println("Change Maker :: myInt is now " + myInt);

					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		});

		Thread changeListener = new Thread(new Runnable() {
			@Override
			public void run() {
				// local copy of the shared variable
				int localCopy = myInt;

				while (localCopy < 5) {
					//read the shared variable, if its not the same as local copy
					if (localCopy != myInt) {
						localCopy = myInt;
						System.out.println("Change Listener :: myInt is now " + myInt);
					}

				}
			}
		});

		changeMaker.start();
		changeListener.start();
		
	}

}
