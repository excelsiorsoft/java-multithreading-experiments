package com.excelsiorsoft.java_util_concurrent;

public class Coordination {
	
	private  volatile int counter =  0;
	//private  volatile boolean finish1 = false;
	//private  volatile boolean finish2 = false;
	
public static void main(String ... args) throws Exception {
	new Coordination().volatileWithCoordination();
}

public synchronized void inc() {
	counter++;
}

public void volatileWithCoordination() throws Exception {
		
	Thread th1 = new Thread(new Runnable() {

		@Override
		public void run() {
			for(int k = 0; k < 10_000_000; k++) {
				synchronized(Coordination.this) {
					//inc();
				counter++;
				}
			}
			//finish2 = true;
		}}); 
		
		Thread th2 = new Thread(new Runnable() {

			@Override
			public void run() {
				for(int k = 0; k < 10_000_000; k++) {
					//synchronized(this) {
						inc();
					//counter++;
					//}
				}
				//finish2 = true;
			}}); 
		
		th1.start();
		th2.start();
		
			//while(!finish1 || !finish2);
			
			th1.join();
			th2.join();
		System.out.println("counter: "+counter); 
		
	}

}
