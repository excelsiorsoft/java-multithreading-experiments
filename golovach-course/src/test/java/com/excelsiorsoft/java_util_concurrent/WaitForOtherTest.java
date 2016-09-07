package com.excelsiorsoft.java_util_concurrent;

import org.junit.Ignore;
import org.junit.Test;

import com.excelsiorsoft.java_util_concurrent.synchronization.WaitForOther;

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
		//http://stackoverflow.com/questions/39322831/inter-thread-communication-in-java
	}
	
	@Test
	public void testBackAndForthViaMonitorWait() throws Exception {
		cut.backAndForthViaMonitorWait();
		//Thread.sleep(9000);
		
	}
	
	@Test
	@Ignore
	public void testPlayPingPong() throws Exception {
		//cut.playPingPong();
	}
	
	
	
	@Test
	public void testCoordination() throws Exception {
		cut.coordination();
	}
	
	@Test
	public void testJoinIsEquivalentToVolatileFlags() throws Exception {
		cut.joinIsEquivalentToVolatileFlags();
	}
	
	@Test
	public void testCoordinationWithSynchAndVolatile() throws Exception {
		cut.coordinationWithSynchAndVolatile();
	}
	
	
	@Test
	public void testSequenceOfEvents() throws Exception {
		cut.nothingCanGoBetweenCandDsequenceOfEvents();

	}
	
	
	@Test
	public void testNotKnownWhatWillBeBetweenCnadDsequencOfEvents() throws Exception {
		cut.notKnownWhatWillBeBetweenCnadDsequencOfEvents();

	}
}
