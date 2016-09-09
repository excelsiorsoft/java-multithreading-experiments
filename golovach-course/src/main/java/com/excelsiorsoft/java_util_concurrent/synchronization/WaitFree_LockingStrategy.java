/**
 * 
 */
package com.excelsiorsoft.java_util_concurrent.synchronization;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Simeon
 *
 */
public class WaitFree_LockingStrategy {

	private final Lock lock = new ReentrantLock();
	private int index = 0;

	
	public static void main(String []args) throws InterruptedException {
		/*new WaitFree_LockingStrategy().withTryLock();*/
		
		
		int repetitions = 10_000;
		long accumulatedTime = 0;
		
		for(int i=0; i<repetitions;i++) {
		long took = new WaitFree_LockingStrategy().withTryLock();
		accumulatedTime += took;
		}
		
		long avgTime = accumulatedTime/repetitions;
		
		System.out.println("========================\nAverage Time: "+(double)avgTime / 1E9+" sec");
		//System.out.println("========================\nAverage Time: "+avgTime);
		
	}
	
	public long withTryLock() throws InterruptedException {
		
		long startTime = System.nanoTime();
		
		Thread t1 = new Thread(() -> {
			for(int i=0; i<10;i++) {
				if (lock.tryLock()) {
					try{ int myId = index++;
					System.out.println(Thread.currentThread()+": " + myId);
					}finally{lock.unlock();}
				} else {
					
					System.out.println(Thread.currentThread()+" couldn't acquire lock. " );
				}

			}

		}); 
		t1.setName("t1");
		t1.setPriority(Thread.NORM_PRIORITY - 1);
		t1.start();;
		
		
		Thread t2 = new Thread(() -> {
			for(int i=0; i<10;i++) {
				if (lock.tryLock()) {
					try{int myId = index++;
					System.out.println(Thread.currentThread()+": " + myId);
					}finally{lock.unlock();}
				} else {
					System.out.println(Thread.currentThread()+" couldn't acquire lock. " );
				}

			}

		}); 
		t2.setName("t2");
		t2.setPriority(Thread.NORM_PRIORITY );
		t2.start();
		
		t1.join();
		t2.join();
		
		long took = (System.nanoTime() - startTime);
		System.out.println("----------------------------\nestimatedTime: " +took+"\n----------------------------");
		return took;
	}
	
	
	
		
	

}
