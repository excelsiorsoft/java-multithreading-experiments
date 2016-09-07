package com.excelsiorsoft.java_util_concurrent.executor;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;

public class DirectExecutor {

	public static void main(String[] args) {

		Executor executor = getExecutor();
		executor.execute(getTask());
		executor.execute(getTask());

		System.out.println("\n----------------------------\nFinally, the wait is over. " + Thread.currentThread());
	}

	private static Executor getExecutor() {
		return new Executor() {

			@Override
			public void execute(Runnable command) {
				System.out.println("Be prepared to wait....\nLong.... .... .....");
				command.run(); //blocking!!

			}
		};

	}

	private static Runnable getTask() {
		return new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {}
				Thread thread = Thread.currentThread();
				System.out.println("Hello from " + thread);

			}
		};
	};

}
