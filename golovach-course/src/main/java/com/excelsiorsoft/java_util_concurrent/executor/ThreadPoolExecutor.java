package com.excelsiorsoft.java_util_concurrent.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolExecutor implements Executor {
	
	private final Thread[] pool;
	//private final BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(256);//bounded
	private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>();//unbounded
	
	public ThreadPoolExecutor(int threadCount) {
		
		this.pool = new Thread[threadCount];
		
		for(int k = 0; k < threadCount;k++) {
			
			pool[k]	 = new Thread(new Runnable() {

				@Override
				public void run() {
					while(true) {
						Runnable task;
						try {
							task = taskQueue.take(); //blocking
							task.run();
						} catch (InterruptedException e) {break;}
						
					}
					
				}});
			
			pool[k].start();
			
		}
	}
	


	@Override
	public void execute(Runnable command) {

		if(!taskQueue.offer(command)) {
			System.out.println("Rejected!");
		}
		
	}

}


