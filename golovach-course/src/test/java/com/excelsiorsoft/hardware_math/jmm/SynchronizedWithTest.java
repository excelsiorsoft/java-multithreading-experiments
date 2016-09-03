package com.excelsiorsoft.hardware_math.jmm;

import static org.junit.Assert.*;

import org.junit.Test;

public class SynchronizedWithTest {

	SynchronizedWith cut = new SynchronizedWith();
	
	@Test
	public void testvolatileWrite() throws Exception {
		cut.volatileWrite();
	}
	
	@Test
	public void testIsAlive() throws Exception {
		cut.isAlive();;
	}

}
