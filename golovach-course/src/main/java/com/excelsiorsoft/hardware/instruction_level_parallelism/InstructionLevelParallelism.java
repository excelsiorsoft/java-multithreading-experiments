package com.excelsiorsoft.hardware.instruction_level_parallelism;

public class InstructionLevelParallelism {

	public static void main(String ... args){
		for(int i =0; i<10; i++) {
			new InstructionLevelParallelism().squaring();
		}
	}
	
	public long squaring() {
		long t0 = System.currentTimeMillis();
		double d0 = 0;
		
		for (int k = 0; k < 100_000_000; k++){
			
			//check bytecode for below to see why timing differs
			d0 *= d0;  			//faster
			//d0 = d0 * d0;  	//slower
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
	
	public long squaringSeparateTwoNums() {
		long t0 = System.currentTimeMillis();
		double d0 = 0;
		double d1 = 0;
		for (int k = 0; k < 100_000_000; k++){
			
			//check bytecode for below to see why timing differs
			d0 *= d0;  			//faster
			d1 *= d1;  			//faster
			//d0 *= d0 * d0;  	//slower
		}
		
		long t1 = System.currentTimeMillis();
		long took = (t1 - t0);
		System.out.println("took: "+took + " ms");
		System.out.println("result: " +d0);
		return took;
	}
	
	public long squaringSeparateTenNums() {
		long t0 = System.currentTimeMillis();
		
		double d0 = 0;
		double d1 = 0;
		double d2 = 0;
		double d3 = 0;
		double d4 = 0;
		double d5 = 0;
		double d6 = 0;
		double d7 = 0;
		double d8 = 0;
		double d9 = 0;
		double d10 = 0;
		
		for (int k = 0; k < 100_000_000; k++){
			
			//check bytecode for below to see why timing differs
			d0 *= d0;  			//faster
			d1 *= d1;  			//faster
			d2 *= d2;  			//faster
			d3 *= d3;  			//faster
			d4 *= d4;  			//faster
			d5 *= d5;  			//faster
			d6 *= d6;  			//faster
			d7 *= d7;  			//faster
			d8 *= d8;  			//faster
			d9 *= d9;  			//faster
			d10 *= d10;  			//faster
			//d0 *= d0 * d0;  	//slower
		}
		
		long t1 = System.currentTimeMillis();
		long took = (t1 - t0);
		System.out.println("took: "+took + " ms");
		System.out.println("result: " +d0);
		return took;
	}

	
}

	