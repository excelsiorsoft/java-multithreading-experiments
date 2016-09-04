package com.excelsiorsoft.java_util_concurrent;

public class WaitForOther {
	
	private static volatile boolean in = false;// need volatile for transitive closure to occur
	
	public static void main(String[] args) {
		
		final Object monitor = new Object();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized(monitor) {
					in = true;
					try {
						Thread.sleep(8000);
					}catch(InterruptedException ignore) {/**/}
				}
				
			}}).start();
		
		System.out.println("На старт!");
		while(!in); //spin lock / busy waiting
		System.out.println("Внимание!");
		synchronized(monitor) {
			System.out.println("Марш!");
		}
		
	}

}
