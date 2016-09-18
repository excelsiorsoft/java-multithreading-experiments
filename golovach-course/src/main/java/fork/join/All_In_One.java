package fork.join;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;

public class All_In_One { //associativity of summation is taken advantage of here, so this quality is important



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
		System.out.println("nonEffectiveIterativeParallelism(): "+result + "; "+ (end - start)/1_000_000 +" ms");
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
	System.out.println("betterImplOfIterativeParallelism(): "+result + "; "+ (end - start)/1_000_000 +" ms");
	pool.shutdown();

}

private static long calcMoreEffective(/*AtomicLong accum,*/ int from, int to) {
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
	System.out.println("latchInsteadOfInvokeAll(): "+result + "; "+ (end - start)/1_000_000 +" ms");
	pool.shutdown();

}

//a lot faster
public void recursivelyViaForkJoin() throws InterruptedException {//recursive parallelism
	
	AtomicLong result = new AtomicLong(0);
	long start = System.nanoTime();
	
	new ForkJoinPool().invoke(new TaskRunnableAnalog(1, 1_000_000, result)); //invoke returns T, not Future of T
	
	long end = System.nanoTime();
	System.out.println("recursivelyViaForkJoin(): "+result.get() + "; "+ (end - start)/1_000_000 +" ms");

}

	public static class TaskRunnableAnalog extends RecursiveAction {

		private static final long serialVersionUID = 1L;
		private final int from;
		private final int to;
		private final AtomicLong result;

		public TaskRunnableAnalog(int from, int to, AtomicLong result) {
			this.from = from;
			this.to = to;
			this.result = result;
		}

		@Override
		protected void compute() { // Runnable analog

			if (to - from < 10_000) {
				result.addAndGet(calcMoreEffective(from, to));
			} else {
				int mid = (from + to) >>> 1;
				TaskRunnableAnalog taskLeft = new TaskRunnableAnalog(from, mid,
						result);
				TaskRunnableAnalog taskRight = new TaskRunnableAnalog(mid, to,
						result);
				invokeAll(taskLeft, taskRight);
			}
		}

	}
	
	
	public void recursivelyViaForkJoinRecursiveTask() throws InterruptedException {//recursive parallelism
		
		AtomicLong result = new AtomicLong(0);
		long start = System.nanoTime();
		
		new ForkJoinPool().invoke(new TaskRunnableAnalog(1, 1_000_000, result)); //invoke returns T, not Future of T
		
		long end = System.nanoTime();
		System.out.println("recursivelyViaForkJoinRecursiveTask(): "+result.get() + "; "+ (end - start)/1_000_000 +" ms");

	}

		public static class TaskCallableAnalog extends RecursiveTask<Long> {

			private static final long serialVersionUID = 1L;
			private final int from;
			private final int to;
			private final AtomicLong result;

			public TaskCallableAnalog(int from, int to, AtomicLong result) {
				this.from = from;
				this.to = to;
				this.result = result;
			}

			@Override
			protected Long compute() { // Callable analog

				if (to - from < 10_000) {
					return calcMoreEffective(from, to);
				} else {
					int mid = (from + to) >>> 1;
					TaskCallableAnalog taskLeft = new TaskCallableAnalog(from, mid,	result);
					TaskCallableAnalog taskRight = new TaskCallableAnalog(mid, to,	result);
					taskLeft.fork();  							//start asynchronously
					taskRight.fork();							//start asynchronously
					return taskLeft.join() + taskRight.join(); //analogous to Future.get()
				}
			}

		}

}
