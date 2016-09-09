/**
 * 
 */
package com.excelsiorsoft.java_util_concurrent.synchronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Simeon
 *
 */
public class WaitFree_LockingStrategy {

	private final Lock lock = new ReentrantLock();
	private int index = 0;

	
	public static void main(String []args) throws InterruptedException {
			
		int toInc = 10;
		int noOfThreads = 2;
		
		int repetitions = 10_000;
		long accumulatedTime = 0;
		
		for(int i=0; i<repetitions;i++) {
		long took = new WaitFree_LockingStrategy().withTryLock(toInc, noOfThreads);
		accumulatedTime += took;
		}
		
		long avgTime = accumulatedTime/repetitions;
		System.out.println("Finished "+repetitions+ " repetitions of incrementing "+toInc+" numbers with " + noOfThreads + " threads in " + accumulatedTime / 1E9 +" sec");
		
		System.out.println("========================\nAverage Time per repetition: "+(double)avgTime / 1E9+" sec");
		
		
	}
	
	public long withTryLock(int toInc, int noOfThreads) throws InterruptedException {
		
		long startTime = System.nanoTime();
		
		Thread[] threadsCreated = new Thread[noOfThreads];
		
		for(int th=0; th<noOfThreads; th++) {
			
			Runnable task = () -> {
				for(int i=0; i<toInc;i++) {
					label:
					if (lock.tryLock()) {
						try{ int myId = index++;
						System.out.println(Thread.currentThread()+": " + myId);
						break label;
						}finally{lock.unlock();}
					} else {
						
						System.out.println(Thread.currentThread()+" couldn't acquire lock. " );
					}

				}

			};
			
			Thread t = new Thread(task);
			t.setName("t"+th);
			threadsCreated[th] = t;
			//t.setPriority(Thread.NORM_PRIORITY - 1);
			t.start();
		}
		
		
		for(Thread th: threadsCreated) {
			th.join();
		}
		
/*		Thread t1 = new Thread(() -> {
			for(int i=0; i<toInc;i++) {
				label:
				if (lock.tryLock()) {
					try{ int myId = index++;
					System.out.println(Thread.currentThread()+": " + myId);
					break label;
					}finally{lock.unlock();}
				} else {
					
					System.out.println(Thread.currentThread()+" couldn't acquire lock. " );
				}

			}

		}); 
		t1.setName("t1");
		//t1.setPriority(Thread.NORM_PRIORITY - 1);
		t1.start();;
		
		
		Thread t2 = new Thread(() -> {
			for(int i=0; i<toInc;i++) {
				label:
				if (lock.tryLock()) {
					try{int myId = index++;
					System.out.println(Thread.currentThread()+": " + myId);
					break label;
					}finally{lock.unlock();}
				} else {
					System.out.println(Thread.currentThread()+" couldn't acquire lock. " );
				}

			}

		}); 
		t2.setName("t2");
		//t2.setPriority(Thread.NORM_PRIORITY );
		t2.start();
		
		t1.join();
		t2.join();*/
		
		long took = (System.nanoTime() - startTime);
		System.out.println("----------------------------\nestimatedTime: " +took/1E9+" sec\n----------------------------");
		return took;
	}
	
	
	
		
	

}
