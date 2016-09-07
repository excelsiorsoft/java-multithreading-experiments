package com.excelsiorsoft.java_util_concurrent.synchronization;

public class Mutex {

	public void competingForALock() throws Exception {
		final Object mutex = new Object();

		for (int i = 0; i < 1000; i++) {

			// Thread.sleep(20);

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println("**in A");
						Thread.sleep(20);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					synchronized (mutex) {
						/* while (true) */
						System.out.println("A");
					}
				}

			}).start();

			new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("**in B");
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					synchronized (mutex) {
						/* while (true) */
						System.out.println("B");
					}
				}

			}).start();
		}
	}

	public void noCompetitionForALock() throws Exception {
		final Object mutex1 = new Object();
		final Object mutex2 = new Object();

		for (int i = 0; i < 1000; i++) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println("**in A");
						Thread.sleep(20);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					synchronized (mutex1) {
						/* while (true) */
						System.out.println("A");
					}
				}

			}).start();

			new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("**in B");
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					synchronized (mutex2) {
						/* while (true) */
						System.out.println("B");
					}
				}

			}).start();
		}
	}

	public void atomicity() {

		final Object mutex = new Object();
		//final Object mutex2 = new Object();

		for (int i = 0; i < 1000; i++) {

			new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						System.out.println("**in A");
						Thread.sleep(20);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					synchronized (mutex) { //same lock
						/* while (true) */
						System.out.println("+A");
						System.out.println("-A\n-----");
					}
				}

			}).start();

			new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println("**in B");
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {

						e.printStackTrace();
					}
					synchronized (mutex) { //same mutex
						/* while (true) */
						System.out.println("+B");
						System.out.println("-B\n-----");
					}
				}

			}).start();
		}
	}

}
