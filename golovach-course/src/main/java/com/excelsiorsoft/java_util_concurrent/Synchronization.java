package com.excelsiorsoft.java_util_concurrent;

public class Synchronization {

	public static void main(String[] args) { //synchronization is reentrant
		synchronized (Synchronization.class) {
			f();
		}
	}

	private static void f() {
		synchronized (Synchronization.class) {
		g();
		}
	}

	private static void g() {
		synchronized (Synchronization.class) {
		h();
		}

	}

	private synchronized static void h() {
		System.out.println("Holds lock on class: " +Thread.holdsLock(Synchronization.class));

	}

}
