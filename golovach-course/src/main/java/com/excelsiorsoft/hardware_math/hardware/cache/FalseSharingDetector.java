package com.excelsiorsoft.hardware_math.hardware.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FalseSharingDetector {
	//typical cache line size is 64 bytes
	volatile static long value0 = 0; //a long takes 8 bytes (64 bits)
	volatile static long value1 = 0;
	volatile static long value2 = 0;
	volatile static long value3 = 0;
	volatile static long value4 = 0;
	volatile static long value5 = 0;
	volatile static long value6 = 0;
	volatile static long value7 = 0;
	volatile static long value8 = 0;//should fit on a next cache line
	
	public static void main (String... args)throws InterruptedException{
		
		ExecutorService pool = Executors.newFixedThreadPool(2);
		final CountDownLatch latch0 = new CountDownLatch(2);
		final CountDownLatch latch2 = new CountDownLatch(2);
		
		pool.submit(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				latch0.countDown(); //Thread #1 ready
				latch0.await();		//Wait for a start signal
				long t0  = System.nanoTime();
				for (int k=0; k <100_000_000;k++) {
					value0 = value0 * k;
				}
				long t1 = System.nanoTime();
				System.out.println((t1 - t0)/ 100000 + " ms");
				latch2.countDown(); //Thread #1 finished
				return null;
			}});
		pool.submit(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				latch0.countDown(); //Thread #2 is ready
				latch0.await(); //wait for a start signal
				long t0 = System.nanoTime();
				for (int k= 0; k < 100_000_000; k++) {
					value1 *=  k;
				}
				long t1 = System.nanoTime();
				System.out.println((t1 - t0)/ 100000 + " ms");
				latch2.countDown(); //Thread #2 finished
				return null;
			}});
		
		latch2.await(); //Wait for #1 & #2 to finish
	}

}
