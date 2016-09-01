package com.excelsiorsoft.hardware_math.hardware.ILP;

public class InstructionLevelParallelism_1_1 {

	public static void main(String ... args){
		
		long t0 = System.currentTimeMillis();
		double d0 = 0;
		
		for (int k = 0; k < 100_000_000; k++){
			
			//check bytecode for below
			d0 *= d0;  			//faster
			//d0 *= d0 * d0;  	//slower
		}
		
		long t1 = System.currentTimeMillis();
		System.out.println("time: "+(t1 - t0));
		System.out.println(d0);
	}
	
}
