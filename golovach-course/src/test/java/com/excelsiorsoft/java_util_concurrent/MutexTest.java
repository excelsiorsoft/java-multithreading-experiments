package com.excelsiorsoft.java_util_concurrent;

import static org.junit.Assert.*;

import org.junit.Test;

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

}
