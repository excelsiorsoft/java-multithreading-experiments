package com.excelsiorsoft.java_util_concurrent;

public class WaitForOther {

	private volatile boolean in = false;// need volatile for transitive closure
										// to occur

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

	public void playPingPong() throws Exception {

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
							

						} catch (InterruptedException ignore) {/**/
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

	}

}
