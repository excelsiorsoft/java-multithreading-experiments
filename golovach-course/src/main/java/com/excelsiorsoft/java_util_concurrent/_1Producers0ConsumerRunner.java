package com.excelsiorsoft.java_util_concurrent;

public class _1Producers0ConsumerRunner {

	public static void main(String... args) {
		SingleElementBuffer buffer = new SingleElementBuffer();

		new Thread(new Producer(1, 1, 300, buffer)).start(); 
		new Thread(new Producer(1, 10, 300, buffer)).start(); //blocks on 2nd produced element
		new Thread(new Producer(1, 100, 300, buffer)).start(); //blocks on 2nd produced element
		
	}
}
