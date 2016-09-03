package com.excelsiorsoft.hardware_math.jmm;

public class SynchronizedWith {

	 private int data = 0;
	 private volatile boolean run = true;
	 private volatile boolean non_volatile_run = true;

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
	
	
	public void isAliveOnNonVolatile() {
		
		Thread newThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " setting non_volatile_run to false");
				non_volatile_run = false;
				
			}});
		
		newThread.start();
		
		while(newThread.isAlive()) {//isAlive serves as a synchronization-with point
			System.out.println(Thread.currentThread().getName() + "; non_volatile_run: "+non_volatile_run);
		}
		System.out.println(Thread.currentThread().getName() + "; non_volatile_run: "+non_volatile_run);
	}
	
public void isAliveOnVolatile() {
		
		Thread newThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " setting run to false");
				run = false;
				System.out.println(Thread.currentThread().getName() + " run:"+run);
				
			}});
		
		newThread.start();
		
		while(newThread.isAlive()) {//isAlive serves as a synchronization-with point
			System.out.println(Thread.currentThread().getName() + "; run: "+run);
		}
		System.out.println(Thread.currentThread().getName() + "; non_volatile_run: "+non_volatile_run);
		System.out.println(Thread.currentThread().getName() + "; run: "+run);
	}
	
	public void joinOnNonVolatile() throws Exception {
		
		Thread newThread = new Thread(new Runnable() {

			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + " setting run to false");
				non_volatile_run = false;
				
			}});
		
		newThread.start();
		
		System.out.println(Thread.currentThread().getName() + " before joined; non_volatile_run: "+non_volatile_run);
		newThread.join();
			System.out.println(Thread.currentThread().getName() + " after joined; non_volatile_run: "+non_volatile_run);
		
	}
	
}
