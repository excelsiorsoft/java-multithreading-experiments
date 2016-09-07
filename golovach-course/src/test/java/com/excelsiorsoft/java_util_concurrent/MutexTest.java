package com.excelsiorsoft.java_util_concurrent;

import static org.junit.Assert.*;

import org.junit.Test;

import com.excelsiorsoft.java_util_concurrent.synchronization.Mutex;

public class MutexTest {

	Mutex cut = new Mutex();
	
	@Test
	public void competitionForALock() throws Exception {
		cut.competingForALock();
	}
	
	@Test
	public void noCompetitionForALock() throws Exception {
		cut.noCompetitionForALock();
	}
	
	@Test
	public void checkAtomicity() throws Exception {
		cut.atomicity();
	}


}
