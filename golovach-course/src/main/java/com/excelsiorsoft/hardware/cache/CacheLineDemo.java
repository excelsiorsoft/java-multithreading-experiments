package com.excelsiorsoft.hardware.cache;

public class CacheLineDemo {

	public final static int ARRAY_SIZE = 16 * 1024 *1024;
	
	/*public static void main(String ... args) {
		byte[] array = new byte[ARRAY_SIZE];
		
		for(int testIndex = 0; testIndex < 10; testIndex++) {
			testMethod(array);
			System.out.println("----------------");
		}
	}*/

	public /*static*/ void testMethod(byte[] array) {
		
		for (int stepSize = 4; stepSize <= 512; stepSize *=2) {
			int sum = 0;
			
			long t0= System.nanoTime();
			for(int n = 0; n < 1000; n++) {
				for(int k = 0; k < array.length; k+= stepSize) {
					sum += array[2];
				}
			}
			long t1 = System.nanoTime();
			
			if(sum > 0) {//to trick JVM into no-optimization mode
				throw new Error();
			}
			
			int step_count = ARRAY_SIZE / stepSize;
			System.out.println(stepSize + ":\tdT / step_count: "+(t1 - t0)/step_count);
		}
		
	}
	
	
	
}
