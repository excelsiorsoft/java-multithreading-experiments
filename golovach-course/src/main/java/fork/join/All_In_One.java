package fork.join;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Spliterator;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.function.LongConsumer;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

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
		
	public void viaStreams()  {

		long start = System.nanoTime();
		
		long result = LongStream.range(0, 1_000_000)
				.parallel()
				/*.filter(x -> x % 5 != 0)
				.filter(x -> x % 3 != 0)*/
				.filter(x -> (x %3 != 0) && (x %5 != 0))
				.sum();

		long end = System.nanoTime();
		System.out.println("viaStreams(): "+result + "; "+ (end - start)/1_000_000 +" ms");
	}
	
	public void viaStreamsIterate()  {

		long start = System.nanoTime();
		//boxing, unboxing - ineffective
		long result = Stream.iterate(0, k -> k++).limit(1_000_000)
				.parallel()
				.filter(x -> x % 5 != 0)
				.filter(x -> x % 3 != 0)
				.reduce(0, (x, y)-> x + y);

		long end = System.nanoTime();
		System.out.println("viaStreamsIterate(): "+result + "; "+ (end - start)/1_000_000 +" ms");
	}
	
	
	public void spliterators()  {

		//sequential: Collection -> Iterator
		//parallel:   Stream -> Spliterator 
		
		LongRange longRange = new LongRange(0, 1_000_000);
		
		long start = System.nanoTime();
		
		long result = StreamSupport.stream(longRange, true)
				.parallel()
				.filter(x -> (x %3 != 0) && (x %5 != 0))
				.reduce(0L, (x, y)->x+y);
		

		long end = System.nanoTime();
		System.out.println("spliterators(): "+result + "; "+ (end - start)/1_000_000 +" ms");
	}
	
	class LongRange implements Spliterator.OfLong{

		private long from;
		private long to;
		
		
		public LongRange(long from, long to){
			this.from = from;
			this.to = to;
		}
		
		@Override
		public boolean tryAdvance(Consumer<? super Long> action) {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public long estimateSize() {
			return to - from;
		}

		@Override
		public int characteristics() {
			
			return 0
					
		//			Spliterator.CONCURRENT
					| Spliterator.DISTINCT
					| Spliterator.IMMUTABLE
					| Spliterator.NONNULL
		//			| Spliterator.ORDERED
					| Spliterator.SIZED
		//			| Spliterator.SORTED
					| Spliterator.SUBSIZED;
					
					
					
		}

		@Override
		public /*java.util.Spliterator.OfLong*/ LongRange  trySplit() {
			if(to - from >1) {
				return new LongRange(from, from = ((from + to)>>>1));
			}else {
				return null;
			}
		}

		/* (non-Javadoc)
		 * @see java.util.Spliterator.OfLong#tryAdvance(java.util.function.LongConsumer)
		 * 
		 * If empty - return false
		 * If NOT empty - process one elem + return true
		 */
		@Override
		public boolean tryAdvance(LongConsumer consumer) {
			if(to > from) {					//[10, 20) -> [11, 20), true
				consumer.accept(from++);
				return true;
			}else {							//[20, 20) -> [20, 20), false
				return false;
			}
		}
		
		/* (non-Javadoc)
		 * @see java.util.Spliterator#getComparator()
		 * 
		 * If this Spliterator's source is SORTED by a Comparator,
		 * returns that Comparator
		 * If the source is sorted in natural order, returns null.
		 * Otherwise, if the source is NOT sorted, throws IllegalStateException
		 */
		@Override
		public Comparator<? super Long>getComparator(){
			return null;
		}
		
	}

}
