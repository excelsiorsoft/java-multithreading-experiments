package com.excelsiorsoft.java_memory_model;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excelsiorsoft.java_memory_model.SynchronizedWith;

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
	public void testSynchronizationOfNonVolatileInNewThread() throws Exception {
		cut.synchronizationOfNonVolatileInNewThread();
	}
	
	@Test
	public void testSynchronizationOfVolatileInNewThread() throws Exception {
		cut.synchronizationOfVolatileInNewThread();
	}

	
	@Test
	public void testSynchronizationInBothThreads() throws Exception {
		cut.synchronizationInBothThreads();
	}
}
