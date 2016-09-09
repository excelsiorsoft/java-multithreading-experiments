package com.excelsiorsoft.java_util_concurrent.synchronization;

import java.util.concurrent.atomic.AtomicInteger;

public class Atomics {

	private AtomicInteger index = new AtomicInteger(0);
	Object mutex = new Object();
	
	public static void main(String [] args) {
		new Atomics().updateIndexViaCAS();
	}

	private  void updateIndexViaCAS() {
		
		Runnable target = () -> {
			for (int i = 0; i < 10; i++) {
				cas: while (true) {
					synchronized (mutex) {
						int oldValue = index.get();
						int newValue = oldValue + 1;
						if (index.compareAndSet(oldValue, newValue)) {
							System.out.println(Thread.currentThread() + ": "+ newValue); // order is not guaranteed?
							break cas;
						}
					}
				}
			}
		};
		
		/*Runnable target = () -> {
			for (int i = 0; i < 10; i++) {
				int oldValue = index.get();
				int newValue = oldValue + 1;
				do {
					oldValue = index.get();
				} while (!index.compareAndSet(oldValue, newValue));
				System.out.println(Thread.currentThread() + ": "+newValue);
			}
		};*/
		
		for(int t=0; t<2;t++) {
			Thread th = new Thread(target);
			th.start();
		}
		
		
	}
}
