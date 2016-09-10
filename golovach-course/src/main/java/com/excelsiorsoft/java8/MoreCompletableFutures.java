package com.excelsiorsoft.java8;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class MoreCompletableFutures {

	public static void main(String[] args) throws IOException {
		
		MoreCompletableFutures c = new MoreCompletableFutures();
		c.yep();

	}

	private void yep() throws IOException {
		CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> {sleep(3000);return "A";});
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {sleep(1000);return "B";});
		
		f0.acceptEitherAsync(f1, System.out::println);
		
		f0.thenAcceptBothAsync(f1, (x,y)-> System.out.println(x + "#" +y));
		
		System.in.read();//must be present to stop main thread from exiting
		
		

	}
	
	private  void sleep(long dt) {
		try {
			Thread.sleep(dt);
		} catch (InterruptedException ignre) {}
	}
	
	
	private static class Infinity{
		public static void main(String[] args) {
			
			double a=1;
			double b=a+1;
			double c = b/0;
			double d = c+1;
			System.out.println(d);//prints Infinity
			double e= 1 - d;
			System.out.println(e);
			double f = d/e;
			System.out.println(f);
		}
	}
	
}
