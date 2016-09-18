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
	

}
