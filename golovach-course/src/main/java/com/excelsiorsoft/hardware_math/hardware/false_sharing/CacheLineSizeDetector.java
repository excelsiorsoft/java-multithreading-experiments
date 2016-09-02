package com.excelsiorsoft.hardware_math.hardware.false_sharing;

public class CacheLineSizeDetector {

	public static void main(String[] args) {
		byte[] array = new byte[64 * 1024]; // this is 64 kilobytes

		for (int testIndex = 0; testIndex < 10; testIndex++) {
			testFunction(array);
			System.out.println("-------------------");
		}

	}

	private static void testFunction(byte[] array) {

		for (int len = 8192; len <= array.length; len += 8192) {// start with
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
