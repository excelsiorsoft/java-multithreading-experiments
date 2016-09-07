package com.excelsiorsoft.java_util_concurrent.producer_consumer;

public class _3Producers0ConsumerRunner {

	public static void main(String... args) {
		SingleElementBuffer buffer = new SingleElementBuffer();

		new Thread(new Producer(1, 1, 300, buffer)).start(); 
		new Thread(new Producer(2, 1, 300, buffer)).start();
		new Thread(new Producer(3, 1, 300, buffer)).start();
	}
}
