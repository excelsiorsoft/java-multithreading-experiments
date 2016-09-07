package com.excelsiorsoft.java_util_concurrent.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class BetterThreadPoolExecutor {
	
	public static void main(String[]args) {
		
		int corePoolSize = 4;
		int maximumPoolSize = 64;
		long keepAliveTime = 10;
		TimeUnit unit = TimeUnit.SECONDS;
		BlockingQueue<Runnable> workQueue 	= new ArrayBlockingQueue<>(256);
		ThreadFactory threadFactory = new ThreadFactory() {

			private AtomicInteger counter = new AtomicInteger(0);
						
			@Override
			public Thread newThread(Runnable r) {
				
				ThreadGroup group = new ThreadGroup("gr");
				
				Thread thread = new Thread(group, r);
				thread.setPriority(Thread.NORM_PRIORITY+3);
				thread.setName("MyPool-NORM_PRIORITY-"+counter.incrementAndGet());
				return thread;
			}
			
		};
		
		RejectedExecutionHandler rejectedHandler = new RejectedExecutionHandler() {

			@Override
			public void rejectedExecution(Runnable r,ThreadPoolExecutor executor) {
				System.out.println("Execution of "+r+" was rejected.  Task queue is full.");
				
			}};
			
		Executor executor = new ThreadPoolExecutor(
				corePoolSize, maximumPoolSize, keepAliveTime, 
				unit, workQueue, threadFactory,
				rejectedHandler);
		
		for(int i = 0; i<3;i++) {executor.execute(createTask());}
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
