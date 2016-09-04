package com.excelsiorsoft.hardware.cache;

import static com.excelsiorsoft.hardware.cache.CacheLineDemo.ARRAY_SIZE;
import static org.junit.Assert.*;

import org.junit.Test;

import com.excelsiorsoft.hardware.cache.CacheLineDemo;

public class CacheLineDemoTest {

	CacheLineDemo cut = new CacheLineDemo();
	
	@Test
	public void test() {
		byte[] array = new byte[ARRAY_SIZE];

		for (int testIndex = 0; testIndex < 10; testIndex++) {
			cut.testMethod(array);
			System.out.println("----------------");
		}
	}

}
