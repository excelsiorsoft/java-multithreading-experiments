package com.excelsiorsoft.hardware_math.hardware.cache;

public class CpuCacheSizeDetector {



	public /*static*/ void testFunction(byte[] array, int s) {

		int size = s*1024;
		for (int len = size; len <= array.length; len += size) {// start with
																// 8kb, and then
																// add addt'l
																// 8kb on each
																// iteration

			long t0 = System.nanoTime();

			for (int n = 0; n < 100; n++) {
				for (int index = 0; index < len; index += 64) {// every 64 bytes
																// of array (which is a default x86 cache line size),
																// write a 1
																// into it
					array[index] = 1;
				}
			}
			long dT = System.nanoTime() - t0;

			System.out.println("len: " + len + ", dT: " + dT + ", 10*dT/len: "
					+ (10 * dT / len));
		}

	}
}
