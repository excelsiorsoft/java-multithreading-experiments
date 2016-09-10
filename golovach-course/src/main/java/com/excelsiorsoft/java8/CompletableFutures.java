package com.excelsiorsoft.java8;

import static java.util.concurrent.CompletableFuture.supplyAsync;

import java.util.concurrent.CompletableFuture;

public class CompletableFutures {

	public static void main(String[] args) {

		CompletableFutures completableFutures = new CompletableFutures();
		completableFutures.buildCommunism();
		completableFutures.buildCommunismShortened();
	}

	private void buildCommunismShortened() {

		supplyAsync(() -> "42")
				.thenApply(Integer::valueOf)
				.thenApply(x -> Math.PI * x * x)
				.thenAccept(System.out::println);
		
		System.out.println("Still Death");

	}

	private  void buildCommunism() {
		CompletableFuture<String> communism = CompletableFuture.supplyAsync(() -> {
			for (int k = 0; k < Long.MAX_VALUE; k++)
				;
			/*try {
				Thread.sleep(1000000);
			} catch (Exception e) {
				
			}*/
			return "42";
		});

		CompletableFuture<Integer> fridge = communism.thenApply(Integer::valueOf);
		CompletableFuture<Double> salami = fridge.thenApply( x -> Math.PI*x*x);
		salami.thenAccept(System.out::println);//will happen when the hell freezes over
		
		System.out.println("Death");
	}
	
}
