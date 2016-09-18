package fork.join;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class IterativeParallelism {

	public static void main(String[] args) throws Exception {
		
		AtomicLong result = new AtomicLong(0);
		int cpuCount = Runtime.getRuntime().availableProcessors();
		ExecutorService pool = Executors.newFixedThreadPool(cpuCount);
		
		List<Callable<Void>>taskList = new ArrayList<>();
		
		for(int k = 0; k <100;k++) {
			final int finalK = k;
			taskList.add(()->{
				calc(result, 10_000 * finalK, 10_000 * (finalK + 1));
				return null;
			});
		}
		
		pool.invokeAll(taskList);
		System.out.println(result);
		pool.shutdown();
	}
	
	

	private static void calc(AtomicLong accum, int from, int to) {
		
		for(int index = from; index < to; index++) {
			if(index % 3 !=0 && index %5 != 0) {
				accum.addAndGet(index);
			}
		}
		
	}
	
}
