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
	public void testIsAliveOnNonVolatile() throws Exception {
		cut.isAliveOnNonVolatile();;
	}
	
	@Test
	public void testIsAliveOnVolatile() throws Exception {
		cut.isAliveOnVolatile();
	}
	
	@Test
	public void testJoinOnNonVolatile() throws Exception {
		cut.joinOnNonVolatile();
	}
	
	@Test
	public void testJoinOnVolatile() throws Exception {
		cut.joinOnVolatile();
	}
	
	@Test
	public void testSynchronizationInNewThread() throws Exception {
		cut.synchronizationInNewThread();
	}

}
