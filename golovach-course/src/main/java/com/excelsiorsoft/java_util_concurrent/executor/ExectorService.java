package com.excelsiorsoft.java_util_concurrent.executor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ExectorService {

	public static void main(String[] args) throws Exception {
	ExecutorService execService = java.util.concurrent.Executors.newCachedThreadPool();
	
	Future<Integer> future0 = execService.submit(new Callable<Integer>() {
		public Integer call() {
			return 42;
		}
	});
	
	Future<Integer> future1 = execService.submit(new Callable<Integer>() {
		public Integer call()  {
			return null;
			//while(true);
		}
	});
	
	Thread.sleep(1000);
	
	System.out.println("future0.isDone: "+future0.isDone());
	System.out.println("future1.isDone: "+future1.isDone());//returns true for `null` and false for the `while(true)` loop
	
	}
}
