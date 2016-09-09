/**
 * 
 */
package com.excelsiorsoft.java_util_concurrent.synchronization;


/**
 * @author Simeon
 *
 */
public class Synchronization_LockingStrategy {

	//private final Lock lock = new ReentrantLock();
	private int index = 0;
	private final Object mutex = new Object();

	
	public static void main(String []args) throws InterruptedException {
		
		int toInc = 10;
		int noOfThreads = 2;
		
		int repetitions = 10_000;
		long accumulatedTime = 0;
		
		for(int i=0; i<repetitions;i++) {
		long took = new Synchronization_LockingStrategy().withSynchronized(toInc, noOfThreads);
		accumulatedTime += took;
		}
		
		long avgTime = accumulatedTime/repetitions;
		
		System.out.println("Finished "+repetitions+ " repetitions of incrementing "+toInc+" numbers with " + noOfThreads + " threads in " + accumulatedTime / 1E9 +" sec");
		
		System.out.println("========================\nAverage Time per repetition: "+(double)avgTime / 1E9+" sec");
		//System.out.println("========================\nAverage Time: "+avgTime);
		
	}
	
	public long withSynchronized(int toInc, int noOfThreads) throws InterruptedException {
		
		long startTime = System.nanoTime();
		
Thread[] threadsCreated = new Thread[noOfThreads];
		
		for(int th=0; th<noOfThreads; th++) {
			
			Runnable task = () -> {
				for(int i=0; i<toInc;i++) {
					synchronized (mutex) {
						int myId = index++;
						System.out.println(Thread.currentThread()+": " + myId);
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
		
		/*Thread t1 = new Thread(() -> {
			for(int i=0; i<toInc;i++) {
				synchronized (mutex) {
					int myId = index++;
					System.out.println(Thread.currentThread()+": " + myId);
				}
				

			}

		}); 
		t1.setName("t1");
		t1.setPriority(Thread.NORM_PRIORITY - 1);
		t1.start();;
		
		
		Thread t2 = new Thread(() -> {
			for(int i=0; i<toInc;i++) {
				synchronized (mutex) {
					int myId = index++;
					System.out.println(Thread.currentThread()+": " + myId);
				}
				

			}

		}); 
		t2.setName("t2");
		t2.setPriority(Thread.NORM_PRIORITY );
		t2.start();
		
		t1.join();
		t2.join();*/
		
		long took = (System.nanoTime() - startTime);
		System.out.println("----------------------------\nestimatedTime: " +took+"\n----------------------------");
		return took;
	}
	
	
	
		
	

}
