package com.excelsiorsoft.java8;

import java.util.concurrent.CompletableFuture;

public class MoreCompletableFutures {

	public static void main(String[] args) {
		
		MoreCompletableFutures c = new MoreCompletableFutures();
		
		c.yep();
	}

	private void yep() {
		CompletableFuture<String> f0 = CompletableFuture.supplyAsync(() -> {sleep(3000);return "A";});
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> {sleep(1000);return "B";});
	}
	
	private void sleep(long dt) {
		try {
			Thread.sleep(dt);
		} catch (InterruptedException ignre) {}
	}
	
}
