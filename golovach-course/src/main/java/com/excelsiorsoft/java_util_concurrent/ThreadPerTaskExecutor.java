package com.excelsiorsoft.java_util_concurrent;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

public class ThreadPerTaskExecutor {

	public static void main(String[]args) {
		
		Executor executor = getExecutor();
		executor.execute(getTask());
		executor.execute(getTask());
	}

	private static Executor getExecutor() {
		return new Executor() {

			private final AtomicLong index = new AtomicLong(7);
			@Override
			public void execute(Runnable command) {
				
				Thread t = new Thread(command);
				t.setDaemon(true);
				t.setPriority(Thread.NORM_PRIORITY+3);
				t.setName("Thread-"+index.getAndIncrement());
				t.start();
				
			}};
	}
	
	private static Runnable getTask() {
		return new Runnable() {

			@Override
			public void run() {
				Thread thread = Thread.currentThread();
				System.out.println("Hello from "+ thread);
				
			}};
	};
	
}
