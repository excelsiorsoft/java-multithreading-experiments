package com.excelsiorsoft.hardware_math.jmm;

public class IllegalStateExceptions {

	public static final class IllegalNotification{
		
		public static void main(String ... args) {//expected java.lang.IllegalMonitorStateException
			new Object().notify();
		}
	}
	
	public static final class IllegalWait{
		
		public static void main(String ... args) throws Exception {//expected java.lang.IllegalMonitorStateException
			new Object().wait();
		}
	}
	
}
