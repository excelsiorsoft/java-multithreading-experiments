package com.excelsiorsoft.hardware.cache;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excelsiorsoft.hardware.cache.CpuCacheSizeDetector;

public class CpuCacheSizeDetectorTest {

	CpuCacheSizeDetector cut = new CpuCacheSizeDetector();
	
	@Test
	public void L1_cache_size() {
		byte[] array = new byte[64 * 1024]; // this is 64 kilobytes

		for (int testIndex = 0; testIndex < 10; testIndex++) {
			cut.testFunction(array,8);
			System.out.println("-------------------");
		}
	}

	
	@Test
	public void L2_cache_size() {
		byte[] array = new byte[512 * 1024]; // this is 64 kilobytes

		for (int testIndex = 0; testIndex < 10; testIndex++) {
			cut.testFunction(array,64);
			System.out.println("-------------------");
		}
	}
}
