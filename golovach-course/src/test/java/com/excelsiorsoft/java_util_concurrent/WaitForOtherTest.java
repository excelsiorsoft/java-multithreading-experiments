package com.excelsiorsoft.java_util_concurrent;

import static org.junit.Assert.*;

import org.junit.Test;

public class WaitForOtherTest {

	WaitForOther cut  = new WaitForOther();
	
	@Test
	public void testfreezeOtherThread() throws Exception {
		cut.freezeOtherThread();
	}

}
