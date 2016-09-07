package com.excelsiorsoft.java_util_concurrent.executor;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadPoolExecutor implements Executor {
	
	private final Thread[] pool;
	//private final BlockingQueue<Runnable> taskQueue = new ArrayBlockingQueue<>(256);//bounded
	private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<Runnable>(1);//unbounded
	
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
							Thread.sleep(10);
							task.run();
						} catch (InterruptedException e) {break;}
						
					}
					
				}});
			
			pool[k].start();
			
		}
	}
	


	

	@Override
	public void execute(Runnable command) {

		/*System.out.println("Attempting to submit a task "+command+" to a queue via `offer`\n");
		if(!taskQueue.offer(command)) {
			System.out.println("Task "+command+" was rejected during `offer` due to overcapacity!");
		}*/
		
		try {
			
			System.out.println("Attempting to submit "+command+" to a queue via `put`\n");
			taskQueue.put(command);//if queue is full - blocking
			
		}catch(InterruptedException ie) {}
		
		/*try{
			System.out.println("Attempting to submit a task "+command+" to a queue via `add`\n");
			taskQueue.add(command);
		}catch(IllegalStateException e) {
			System.out.println("Task "+command+"was rejected during `add` due to queue capacity restrictions");
		}*/
	}

}


