package com.excelsiorsoft.java_util_concurrent.executor;

import java.util.concurrent.Executor;

public class Executors {

	public static void main(String[] args) {
		Executor executor = java.util.concurrent.Executors
				.newCachedThreadPool();

		for (int i = 0; i < 3; i++) {
			executor.execute(createTask());
		}
	}

	private static Runnable createTask() {
		return new Runnable() {

			@Override
			public void run() {
				System.out.println("Hello from "+Thread.currentThread());
				
			}
			
		};
	}

}
