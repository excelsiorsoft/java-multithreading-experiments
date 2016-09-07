package com.excelsiorsoft.java_util_concurrent;

import java.util.concurrent.Executor;

public class ThreadPerTaskExecutor {

	public static void main(String[]args) {
		
		Executor executor = getExecutor();
		executor.execute(getTask());
		executor.execute(getTask());
	}

	private static Executor getExecutor() {
		return new Executor() {

			@Override
			public void execute(Runnable command) {
				new Thread(command).start();
				
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
