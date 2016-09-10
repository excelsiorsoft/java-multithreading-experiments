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
		
		System.in.read();//to stop main thread from exiting
	}
	
	private  void sleep(long dt) {
		try {
			Thread.sleep(dt);
		} catch (InterruptedException ignre) {}
	}
	
}
