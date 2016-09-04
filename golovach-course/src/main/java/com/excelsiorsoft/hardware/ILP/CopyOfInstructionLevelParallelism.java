
package com.excelsiorsoft.hardware.ILP;

//http://stackoverflow.com/questions/39293444/performance-of-arithmetic-compound-assignment-operators-in-java/39293534?noredirect=1#comment65922265_39293534
public class CopyOfInstructionLevelParallelism {

	public static void main(String ... args){
		for(int i =0; i<10; i++) {
			new CopyOfInstructionLevelParallelism().squaring();
		}
	}
	
	public void squaring() {
		long t0 = System.currentTimeMillis();
		double d0 = 0;
		
		for (int k = 0; k < 100_000_000; k++){
			
			d0 *= d0;  			//faster
			//d0 = d0 * d0;  	//slower
		}
		
		long t1 = System.currentTimeMillis();
		long took = (t1 - t0);
		System.out.println("took: "+took + " ms");
		System.out.println("result: " +d0);
		
		
	}
	
	
}

	
