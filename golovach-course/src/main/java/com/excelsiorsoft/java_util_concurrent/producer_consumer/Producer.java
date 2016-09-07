package com.excelsiorsoft.java_util_concurrent.producer_consumer;

import static java.lang.System.currentTimeMillis;

public class Producer implements Runnable {
	private int id;
	private int value;
	private final int period;
	private final SingleElementBuffer buffer;
	
	public Producer(int id, int value/*starting value*/, int period, SingleElementBuffer buffer) {
		this.id = id;
		this.buffer = buffer;
		this.period = period;
		this.value  = value;
	}

	@Override
	public void run() {
		while(true) {
			try {
				System.out.println(currentTimeMillis()+": "+value+" produced by P#"+id);
				buffer.put(value++);
				Thread.sleep(period);
			}catch(InterruptedException ignore) {/**/}
		}
		
	}
	
	

}
