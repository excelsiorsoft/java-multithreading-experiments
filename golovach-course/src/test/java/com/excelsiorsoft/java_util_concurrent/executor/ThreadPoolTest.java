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
		
		poolingExecutor.execute(getTask(taskNumber.getAndIncrement()));
		poolingExecutor.execute(getTask(taskNumber.getAndIncrement()));
	}

	private Runnable getTask(final long taskNumber) {
		return new Runnable() {
			
			@Override
			public void run() {
				System.out.println("Creating task-"+ taskNumber);
				
			}};
	
	}

}
