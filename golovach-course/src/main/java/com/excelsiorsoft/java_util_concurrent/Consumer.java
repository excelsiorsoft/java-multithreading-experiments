package com.excelsiorsoft.java_util_concurrent;

import static java.lang.System.currentTimeMillis;

public class Consumer implements Runnable{

	private final int id;
	private final SingleElementBuffer buffer;
	public Consumer(int id, SingleElementBuffer buffer) {
		super();
		this.id = id;
		this.buffer = buffer;
	}
	@Override
	public void run() {
		while(true) {
			Integer elem;
			try {
				elem = buffer.get();
				System.out.println(" "+ currentTimeMillis()+ ": "+elem+" consumed by "+id);
			} catch (InterruptedException ignore) {}
			
		}
		
	}
	
	
	
}
