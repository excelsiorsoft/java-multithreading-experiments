package com.excelsiorsoft.hardware_math.jmm;

public class StopFlag {

	static int data = 0;
	static volatile boolean run = true;

	public static void main(String[] args) throws Exception {
		
		Thread newThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (run);
				System.out.println("data: "+data);
			}

		});
		
		//newThread.setDaemon(true);
		newThread.start();
		
		
		//System.out.println(Thread.currentThread().isDaemon());
		//System.out.println(newThread.isDaemon());
		//Thread.sleep(1000);
		
		run = false;
		data = 1;
	}

}
