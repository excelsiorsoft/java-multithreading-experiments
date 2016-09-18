package fork.join;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class BetterImplOfIterativeParallelism {

	public static void main(String[] args) throws Exception {
		
		AtomicLong result = new AtomicLong(0);
		int cpuCount = Runtime.getRuntime().availableProcessors();
		ExecutorService pool = Executors.newFixedThreadPool(cpuCount);
		
		long start = System.nanoTime();
		List<Callable<Void>>taskList = new ArrayList<>();
		
		for(int k = 0; k <100;k++) {
			final int finalK = k;  //effectively final is allowed as well
			taskList.add(()->{
				//[0...10,000), [10,000...20,000), ...
				long localResult = calc(/*result, */10_000 * finalK, 10_000 * (finalK + 1));
				result.addAndGet(localResult);
				return null;
			});
		}
		
		pool.invokeAll(taskList); //blocking call
		long end = System.nanoTime();
		System.out.println(result + "; "+ (end - start)/1_000_000 +" ms");
		pool.shutdown();

	}
	
	

	private static long calc(/*AtomicLong accum,*/ int from, int to) {
		long result = 0;
		for(int index = from; index < to; index++) {
			if(index % 3 !=0 && index %5 != 0) {
				/*accum.addAndGet(index);*/
				result += index;
			}
		}
		return result;
	}
	
}
