package com.excelsiorsoft.java_util_concurrent;

public class WaitForOther {

	private volatile boolean in = false;// need volatile for transitive closure
										// to occur

	private volatile int counter =  0;
	private volatile boolean finish1 = false;
	private volatile boolean finish2 = false;
	
	public void freezeOtherThread() throws Exception {

		final Object monitor = new Object();

		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (monitor) {
					in = true;
					try {
						Thread.sleep(8000); // Thread.sleep DOES NOT release the
											// monitor

					} catch (InterruptedException ignore) {/**/
					}
				}

			}
		}).start();

		System.out.println("На старт!");
		while (!in); // spin lock / busy waiting
		System.out.println("Внимание!");
		synchronized (monitor) {
			System.out.println("Марш!");

		}

	}

	public /*Thread*/ void stoppingViaMonitorWait() throws Exception {

		final Object monitor = new Object();

		Thread newThread = new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (monitor) {
					in = true;
					try {
						monitor.wait(); // this releases monitor
						 //Thread.sleep(8000); //No-op
						System.out.println("Resumed in "
								+ Thread.currentThread().getName());

					} catch (InterruptedException ignore) {/**/}
				}

			}
		}); 
		newThread.start();

		System.out.println("Ready!");
		while (!in); // spin lock / busy waiting
		System.out.println("Set!");
		synchronized (monitor) {
			System.out.println("Go!");
			monitor.notifyAll();
		}

		//return newThread;
	}
	
	public /*Thread*/ void backAndForthViaMonitorWait() throws Exception {

		final Object monitor = new Object();

		Thread newThread = new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (monitor) {
					in = true;
					try {
						monitor.wait(); // this releases monitor
						 //Thread.sleep(8000); //No-op
						System.out.println("Resumed in "
								+ Thread.currentThread().getName());

					} catch (InterruptedException ignore) {/**/}
				}

			}
		}); 
		newThread.start();

		System.out.println("Ready!");
		while (!in); // spin lock / busy waiting
		System.out.println("Set!");
		synchronized (monitor) {
			System.out.println("Go!");
			monitor.notifyAll();
			System.out.println("After notifyAll");
		}
		System.out.println("Exiting...");

		//return newThread;
	}

	/*public void playPingPong() throws Exception {

		final Object monitor = new Object();
		Thread.currentThread().setPriority(5);;

		Thread pongThread = new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized (monitor) {
					System.out.println("\tPong!");
					for (;;) {

						in = true;
						try {
							System.out.println("\tPong!");
							monitor.wait(); // this releases monitor
							// Thread.sleep(8000); //No-op
							

						} catch (InterruptedException ignore) {
						}
					}

				}
			}
		}); 
		pongThread.setPriority(5);
		pongThread.start();

		System.out.println("Ping!");
		while (!in); // spin lock / busy waiting
		// System.out.println("Ping!");
		//for (;;) {
			synchronized (monitor) {
				for(;;) {
				//System.out.println("Ping!");
				monitor.notifyAll();
				}
			}
		//}

	}*/

	
	
	
public void coordination() throws Exception {
		
		final Object coordinator = new Object();
	
		Thread th1 = new Thread(new Runnable() {

			@Override
			public void run() {
				for(int k = 0; k < 10_000_000; k++) {
					synchronized(coordinator) {
					counter++;
					}
				}
				//finish1 = true;
				
			}}); th1.start();
		
		Thread th2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for(int k = 0; k < 10_000_000; k++) {
					synchronized(coordinator) {
					counter++;
					}
				}
				//finish2 = true;
			}}); th2.start();
		
			//while(!finish1 || !finish2);
			
			th1.join();
			th2.join();
		System.out.println("counter: "+counter); //wrong result - not 20_000_000 expected
		
	}

public void joinIsEquivalentToVolatileFlags() throws Exception {
	
	final Object coordinator = new Object();

	Thread th1 = new Thread(new Runnable() {

		@Override
		public void run() {
			for(int k = 0; k < 10_000_000; k++) {
				synchronized(coordinator) {
				counter++;
				}
			}
			finish1 = true;
			
		}}); th1.start();
	
	Thread th2 = new Thread(new Runnable() {

		@Override
		public void run() {
			for(int k = 0; k < 10_000_000; k++) {
				synchronized(coordinator) {
				counter++;
				}
			}
			finish2 = true;
		}}); th2.start();
	
		while(!finish1 || !finish2);
		
		//th1.join();
		//th2.join();
	System.out.println("counter: "+counter); //wrong result - not 20_000_000 expected
	
}

public void coordinationWithSynchAndVolatile() throws Exception {
	
	final Object coordinator = new Object();

	Thread th1 = new Thread(new Runnable() {

		@Override
		public void run() {
			for(int k = 0; k < 10_000_000; k++) {
				//synchronized(coordinator) {
				counter++;
				//}
			}
			//finish1 = true;
			
		}}); th1.start();
	
	Thread th2 = new Thread(new Runnable() {

		@Override
		public void run() {
			for(int k = 0; k < 10_000_000; k++) {
				synchronized(coordinator) {
				counter++;
				}
			}
			//finish2 = true;
		}}); th2.start();
	
		//while(!finish1 || !finish2);
		
		th1.join();
		th2.join();
	
	while(true) {
		synchronized(coordinator) {
			System.out.println("counter: "+counter); //how does one peek to continuously change values/
		}
	}
}

public void nothingCanGoBetweenCandDsequenceOfEvents() {
	
	final Object monitor = new Object();
	
	new Thread(new Runnable() {

		@Override
		public void run() {
			synchronized(monitor) {
				in = true;
				try {
					System.out.println("X");
					monitor.wait();
					System.out.println("Y");
				}catch(InterruptedException ignore) {}
			}
			
		}}).start();
	
	System.out.println("A");
	while(!in);
	System.out.println("B");
	synchronized(monitor) {
		System.out.println("C");
		monitor.notify();
		System.out.println("D");// D will always appear before Y because the main thread holds the monitor, even though Th-0 is notified
								//Th-0 goes from the wait set into the blocked set of the JVM awaiting scheduling.

		
	}
	
	System.out.println("E");
}

public void notKnownWhatWillBeBetweenCnadDsequencOfEvents() throws InterruptedException {
	
	final Object monitor = new Object();
	
	new Thread(new Runnable() {

		@Override
		public void run() {
			synchronized(monitor) {
				in = true;
				try {
					System.out.println("X");
					monitor.wait();
					System.out.println("Y");
					monitor.notify();
				}catch(InterruptedException ignore) {}
			}
			
		}}).start();
	
	System.out.println("A");
	while(!in);
	System.out.println("B");
	synchronized(monitor) {
		System.out.println("C");
		monitor.notify();// --> doesn't throw anything
		monitor.wait(); //--> throws interrupted Exception
		System.out.println("D");// D will always appear before Y because the main thread holds the monitor, even though Th-0 is notified
								//Th-0 goes from the wait set into the blocked set of the JVM awaiting scheduling.

		
	}
	
	System.out.println("E");
}

}
