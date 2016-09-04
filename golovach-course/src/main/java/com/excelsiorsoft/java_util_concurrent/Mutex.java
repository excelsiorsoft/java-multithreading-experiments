package com.excelsiorsoft.java_util_concurrent;

public class Mutex {

	public static void main(String[] args) throws Exception {
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

}
