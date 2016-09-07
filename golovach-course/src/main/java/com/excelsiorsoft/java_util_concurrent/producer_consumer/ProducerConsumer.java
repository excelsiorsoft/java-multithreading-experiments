package com.excelsiorsoft.java_util_concurrent.producer_consumer;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {

	public static void main(String... strings) {
		
		final BlockingQueue<String> queue = new ArrayBlockingQueue<>(16);
		
		//3 producers
		for( int p = 0; p<3;p++) {
			final int fp = p;
			
			//Producer
			new Thread(new Runnable() {
				public void run() {
					int counter = 0;

					while (true) {
						
						try {
							Thread.sleep(300+1000*fp);
							String data = "p#"+fp+":"+(++counter);
							queue.put(data);
							//queue.add(++counter); //true or exception if cannot
							//queue.offer(++counter);
							System.out.println("put: "+data);
						} catch (InterruptedException ignore) {	}
					}
				}
			}).start();
			
		}
		
		//Consumer
				new Thread(new Runnable() {
					public void run() {
						
						String data = null;
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

					private void process(String data) {
						
						System.out.println("Process: "+ data+"\n=================");
					}
				}).start();
		
		
		
	}
	
	
	
}
