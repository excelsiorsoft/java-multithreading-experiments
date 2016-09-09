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
public class SynchronizationLockingStrategy {

	private final Lock lock = new ReentrantLock();
	private int index = 0;

	
	public static void main(String []args) {
		new SynchronizationLockingStrategy().withTryLock();
	}
	
	public void withTryLock() {
		
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
	}
	
	
	
		
	

}
