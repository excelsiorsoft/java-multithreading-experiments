package com.excelsiorsoft.java_util_concurrent.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ExectorService {

	public static void main(String[] args) throws Exception {
		ExecutorService execService = java.util.concurrent.Executors
				.newCachedThreadPool();

		Callable<Integer> task1 = new Callable<Integer>() {
			public Integer call() {
				return 42;
			}
		};
		
		Callable<Integer> task2 = new Callable<Integer>() {
			public Integer call() {
				return null;
			}
		}

		;
		Future<Integer> future0 = execService.submit(task1);

		Future<Integer> future1 = execService.submit(task2);

		Thread.sleep(1000);

		System.out.println("future0.isDone: " + future0.isDone());
		System.out.println("future1.isDone: " + future1.isDone());// returns
																	// true for
																	// `null`
																	// and false
																	// for the
																	// `while(true)`
																	// loop
		
		System.out.println("future0.get(): "+future0.get());
		System.out.println("future1.get(): "+future1.get()); //blocks for `while(true)`
		System.out.println("\nFinished Execution\n");
		
		List<Callable<Integer>> tasks = new ArrayList<>();
		tasks.add(task1);
		tasks.add(task2);
		
		List<Future<Integer>> allBlockingFutures = execService.invokeAll(tasks); //blocking
		
		for(Future<Integer> future:allBlockingFutures) {
			System.out.println(future +" -> "+ future.get());
		}

	}
}
