package com.excelsiorsoft.java_util_concurrent.executor;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.omg.CORBA.portable.IndirectionException;

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
		};
		
		Callable<Integer> task3 = new Callable<Integer>() {
			public Integer call()  {
				for(int i = 0;i<10000000;i++);
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
		

		
		List<Callable<Integer>> tasks = Arrays.asList(task1, task2, task3);



		List<Future<Integer>> allBlockingFutures = execService.invokeAll(tasks); //blocking
		
		//execService.shutdownNow();
		
		for(Future<Integer> future:allBlockingFutures) {
			System.out.println(future +" -> "+ future.get());
		}
		
		execService.shutdownNow();
		System.out.println("\nFinished Execution\n");

	}
}
