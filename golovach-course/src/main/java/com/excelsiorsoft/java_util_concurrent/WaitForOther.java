package com.excelsiorsoft.java_util_concurrent;

import java.io.UnsupportedEncodingException;

public class WaitForOther {
	
	private volatile boolean in = false;// need volatile for transitive closure to occur
	
	public void freezeOtherThread() throws Exception {
		
		final Object monitor = new Object();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized(monitor) {
					in = true;
					try {
						Thread.sleep(8000); //Thread.sleep DOES NOT release the monitor

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
	
public void stoppingViaMonitorWait() throws Exception {
		
		final Object monitor = new Object();
		
		new Thread(new Runnable() {

			@Override
			public void run() {
				synchronized(monitor) {
					in = true;
					try {
						monitor.wait();		//this releases monitor
						Thread.sleep(8000); //No-op

					}catch(InterruptedException ignore) {/**/}
				}
				
			}}).start();
		
		System.out.println("Ready!");
		while(!in); //spin lock / busy waiting
		System.out.println("Set!");
		synchronized(monitor) {
			System.out.println("Go!");
		}
		
	}

}
