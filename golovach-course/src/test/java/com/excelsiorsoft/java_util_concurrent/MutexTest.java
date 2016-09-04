package com.excelsiorsoft.java_util_concurrent;

import static org.junit.Assert.*;

import org.junit.Test;

public class MutexTest {

	Mutex cut = new Mutex();
	
	@Test
	public void test() throws Exception {
		cut.competingForALock();
	}

}
