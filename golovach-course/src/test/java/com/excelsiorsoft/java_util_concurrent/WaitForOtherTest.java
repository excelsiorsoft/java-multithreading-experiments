package com.excelsiorsoft.java_util_concurrent;

import static org.junit.Assert.*;

import org.junit.Test;

public class WaitForOtherTest {

	WaitForOther cut  = new WaitForOther();
	
	@Test
	public void testfreezeOtherThread() throws Exception {
		cut.freezeOtherThread();
	}
	
	@Test
	public void testStoppingViaMonitorWait() throws Exception {
		/*Thread x = */cut.stoppingViaMonitorWait();
//		x.join();
		Thread.sleep(9000);
	}
	
	@Test
	public void testPlayPingPong() throws Exception {
		cut.playPingPong();
	}

}
