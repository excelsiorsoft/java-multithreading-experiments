package com.excelsiorsoft.hardware_math.hardware.ILP;

public class InstructionLevelParallelism_1_1 {

	public static void main(String ... args){
		
		new InstructionLevelParallelism_1_1().squaring();
	}
	
	public long squaring() {
		long t0 = System.currentTimeMillis();
		double d0 = 0;
		
		for (int k = 0; k < 100_000_000; k++){
			
			//check bytecode for below to see why timing differs
			d0 *= d0;  			//faster
			//d0 *= d0 * d0;  	//slower
		}
		
		long t1 = System.currentTimeMillis();
		long took = (t1 - t0);
		System.out.println("took: "+took + " ms");
		System.out.println("result: " +d0);
		
		return took;
	}
	
	public long repeated_squaring() {
		long t0 = System.currentTimeMillis();
		double d0 = 0;
		
		for (int k = 0; k < 100_000_000; k++){
			
			//check bytecode for below to see why timing differs
			d0 *= d0;  			//faster
			d0 *= d0;  			//faster
			//d0 *= d0 * d0;  	//slower
		}
		
		long t1 = System.currentTimeMillis();
		long took = (t1 - t0);
		System.out.println("took: "+took + " ms");
		System.out.println("result: " +d0);
		return took;
	}

	
}

	