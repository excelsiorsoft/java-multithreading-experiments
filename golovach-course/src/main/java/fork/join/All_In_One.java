package fork.join;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class All_In_One {

	public void nonEffectiveIterativeParallelism() throws InterruptedException {
		
		AtomicLong result = new AtomicLong(0);
		int cpuCount = Runtime.getRuntime().availableProcessors();
		ExecutorService pool = Executors.newFixedThreadPool(cpuCount);
		
		long start = System.nanoTime();
		List<Callable<Void>>taskList = new ArrayList<>();
		
		for(int k = 0; k <100;k++) {
			final int finalK = k;  //effectively final is allowed as well
			taskList.add(()->{
				//[0...10,000), [10,000...20,000), ...
				calcNonEffective(result, 10_000 * finalK, 10_000 * (finalK + 1));
				return null; //callable requires this
			});
		}
		
		pool.invokeAll(taskList); //blocking call
		long end = System.nanoTime();
		System.out.println(result + "; "+ (end - start)/1_000_000 +" ms");
		pool.shutdown();
	}

	private void calcNonEffective(AtomicLong accum, int from, int to) {
		
		for(int index = from; index < to; index++) {
			if(index % 3 !=0 && index %5 != 0) {
				accum.addAndGet(index);
			}
		}
		
	}
	
	
public void betterImplOfIterativeParallelism() throws InterruptedException {
	
	AtomicLong result = new AtomicLong(0);
	int cpuCount = Runtime.getRuntime().availableProcessors();
	ExecutorService pool = Executors.newFixedThreadPool(cpuCount);
	
	long start = System.nanoTime();
	List<Callable<Void>>taskList = new ArrayList<>();
	
	for(int k = 0; k <100;k++) {
		final int finalK = k;  //effectively final is allowed as well
		taskList.add(()->{
			//[0...10,000), [10,000...20,000), ...
			long localResult = calcMoreEffective(/*result, */10_000 * finalK, 10_000 * (finalK + 1));
			result.addAndGet(localResult);
			return null; //callable requires this
		});
	}
	
	pool.invokeAll(taskList); //blocking call
	long end = System.nanoTime();
	System.out.println(result + "; "+ (end - start)/1_000_000 +" ms");
	pool.shutdown();

}

private long calcMoreEffective(/*AtomicLong accum,*/ int from, int to) {
		long result = 0;
		for(int index = from; index < to; index++) {
			if(index % 3 !=0 && index %5 != 0) {
				/*accum.addAndGet(index);*/
				result += index;
			}
		}
		return result;
	}
	

public void latchInsteadOfInvokeAll() throws InterruptedException {
	
	AtomicLong result = new AtomicLong(0);
	int cpuCount = Runtime.getRuntime().availableProcessors();
	ExecutorService pool = Executors.newFixedThreadPool(cpuCount);
	
	long start = System.nanoTime();
	//List<Callable<Void>>taskList = new ArrayList<>();
	final int NUM_OF_THREADS = 100;
	final CountDownLatch latch = new CountDownLatch(NUM_OF_THREADS);
	
	for(int k = 0; k < NUM_OF_THREADS;k++) {
		final int finalK = k;  //effectively final is allowed as well
		pool.submit(()->{
			
			long localResult = calcMoreEffective(/*result, */10_000 * finalK, 10_000 * (finalK + 1));
			result.addAndGet(localResult);
			latch.countDown();
		});
	}
	
	latch.await();
	
	//pool.invokeAll(taskList); //blocking call
	long end = System.nanoTime();
	System.out.println(result + "; "+ (end - start)/1_000_000 +" ms");
	pool.shutdown();

}

}
