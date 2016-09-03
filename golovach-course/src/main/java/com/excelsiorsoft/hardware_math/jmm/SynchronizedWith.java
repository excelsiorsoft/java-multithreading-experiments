package com.excelsiorsoft.hardware_math.jmm;

public class SynchronizedWith {

	 private int data = 0;
	 private volatile boolean run = true;

	public  void volatileWrite() throws Exception {
		
		Thread newThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (run);
				System.out.println(Thread.currentThread().getName()+"; data: "+data);
				System.out.println(Thread.currentThread().getName()+" exiting");
			}

		});
		
		//newThread.setDaemon(true);
		newThread.setName("newThread");
		newThread.start();
		Thread.sleep(1000);
		System.out.println(Thread.currentThread().getName()+"; data: "+data);
		
		//System.out.println(Thread.currentThread().isDaemon());
		//System.out.println(newThread.isDaemon());
		//Thread.sleep(1000);
		
		run = false;
		Thread.sleep(3000);
		data = 1;
		
		try{}finally {System.out.println(Thread.currentThread().getName()+" exiting...");}
	}
	
	
	public void isAlive() {
		
		Thread newThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " setting run to false");
				run = false;
				
			}});
		
		newThread.start();
		
		while(newThread.isAlive()) {
			System.out.println(Thread.currentThread().getName() + "run: "+run);
		}
	}
	
}
