package com.excelsiorsoft.java_util_concurrent.executor;

import static org.junit.Assert.*;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicLong;


import org.junit.Test;

public class ThreadPoolTest {

	@Test
	public void test() {

		AtomicLong taskNumber = new AtomicLong(0);

		Executor poolingExecutor = new ThreadPoolExecutor(2);

		for (int i = 0; i < 20; i++) {
			
			Runnable task = createTask(taskNumber.getAndIncrement());
			System.out.println("Created "+task +"\n");
			poolingExecutor.execute(task);
			System.out.println("Successfully submitted " + task+"\n");
			
		}

	}

	private Runnable createTask(final long taskNumber) {

		return new Runnable/* WithSource */() {

			public String source;

			@Override
			public void run() {
				System.out.println("Executing "+toString());

			}

			public String getSource() {
				return source;
			}
			
			@Override
			public String toString() {
				return "[Task taskNumber="+taskNumber+"]";
			}

		};

	}

	

}
