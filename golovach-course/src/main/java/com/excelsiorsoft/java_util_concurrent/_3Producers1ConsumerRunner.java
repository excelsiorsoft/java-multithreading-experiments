package com.excelsiorsoft.java_util_concurrent;

public class _3Producers1ConsumerRunner {

	public static void main(String... args) {
		SingleElementBuffer buffer = new SingleElementBuffer();

		new Thread(new Producer(1, 1, 300, buffer)).start(); 
		new Thread(new Producer(2, 1, 300, buffer)).start();
		new Thread(new Producer(3, 1, 300, buffer)).start();
		
		new Thread(new Consumer(1,  buffer)).start();
	}
}
