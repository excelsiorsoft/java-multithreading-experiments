package com.excelsiorsoft.hardware.instruction_level_parallelism;


/**
 * Run from the console: 

    $ java -Xms1025k -Xmx1025k -XshowSettings:vm  MemoryTest


 * @author Simeon
 *
 */


public class MemoryTest {
	public static void main(String args[]) {
		System.out.println("Used Memory   :  "
				+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime()
						.freeMemory()) + " bytes");
		
		System.out.println("Free Memory   : "
				+ Runtime.getRuntime().freeMemory() + " bytes");
		
		System.out.println("Total Memory  : "
				+ Runtime.getRuntime().totalMemory() + " bytes");
		
		System.out.println("Max Memory    : "
				+ Runtime.getRuntime().maxMemory() + " bytes");
	}
}
