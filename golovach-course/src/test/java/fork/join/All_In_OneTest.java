package fork.join;

import static org.junit.Assert.*;

import org.junit.Test;

public class All_In_OneTest {

	All_In_One cut =new All_In_One();
	
	@Test
	public void nonEffectiveIterativeParallelism() throws InterruptedException {
		cut.nonEffectiveIterativeParallelism();
	}
	
	@Test
	public void betterImplOfIterativeParallelism() throws InterruptedException {
		cut.betterImplOfIterativeParallelism();
	}
	
	@Test
	public void latchInsteadOfInvokeAll() throws InterruptedException {
		cut.latchInsteadOfInvokeAll();
	}
	
	@Test
	public void recursivelyViaForkJoin() throws InterruptedException {
		cut.recursivelyViaForkJoin(); //a lot faster
	}
	
	@Test
	public void recursivelyViaForkJoinRecursiveTask() throws InterruptedException {
		cut.recursivelyViaForkJoinRecursiveTask(); //a lot faster
	}
	
	@Test

	public void viaStreams()  {//slower??
		cut.viaStreams(); 
	}
	
	@Test
	public void viaStreamsIterate()  {
		cut.viaStreamsIterate(); 
	}
	
	@Test
	public void spliterators()  {
		cut.spliterators(); 
	}
	
}
