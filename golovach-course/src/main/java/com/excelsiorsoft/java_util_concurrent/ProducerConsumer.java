package com.excelsiorsoft.java_util_concurrent;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {

	public static void main(String... strings) {
		
		final BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(16);
		
		//Producer
		new Thread(new Runnable() {
			public void run() {
				int counter = 0;

				while (true) {
					
					try {
						Thread.sleep(300);
						queue.put(++counter);
						System.out.println("put: "+counter);
					} catch (InterruptedException ignore) {	}
				}
			}
		}).start();
		
		
		//Consumer
		new Thread(new Runnable() {
			public void run() {
				
				int data = 0;
				while (true) {
					
					try {

						data = queue.take(); //block thread
						//Integer data = queue.poll(); null
						//Integer data = queue.remove(); NoSuchElementException
						System.out.println("took: "+ data);
					} catch (InterruptedException ignore) {	}
						process(data);
				}
			}

			private void process(int data) {
				
				System.out.println("Process: "+ data+"\n=================");
			}
		}).start();
	}
	
	
	
}
