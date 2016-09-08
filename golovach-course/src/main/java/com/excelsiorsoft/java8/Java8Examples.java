package com.excelsiorsoft.java8;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.stream.Stream;




public class Java8Examples {
	
	public static void main(String[]args) {
		
		new Thread(Java8Examples::printHello).start();
		
		new Thread(()->System.out.println("Lambda!")).start();
		

		Arrays.asList("A", "BB", "CCC").parallelStream()
										.map(/*str -> str.length()*/String::length)
										.filter(k->k%2 == 1)
										.forEach(System.out::println);

		
		System.out.println("=========================");
		
		 Stream.iterate(2L, k->k+3).limit(20).forEach(System.out::println);;
		 
		 System.out.println("=========================");
			
		 Stream.iterate(2L, k->k+1).filter(k -> k%3==2).limit(20).forEach(System.out::println);;
		 
		 System.out.println("=========================");
		 
		 Stream.iterate(0L, k -> k+1).parallel().filter(k -> k%3 ==2).map(k-> "~"+k).limit(10).forEach(k->System.out.println(k));
		 
		 System.out.println("=========================");
		 
		Stream.iterate(0L, k -> k + 1).parallel().filter(k -> k % 3 == 2)
				.map(k -> "~" + k).limit(10).forEach(new Consumer<String>() {

					@Override
					public void accept(String t) {
						System.out.println(Thread.currentThread());

					}

				});
		
		 System.out.println("=========================");
		 
		 F myF = ( x, y) -> x + y;
		 F myF1 = ( int x, int y) -> x + y;
		 F<Integer> myF2 = (x, y) -> x + y;
		 
		 System.out.println(myF2);
		 System.out.println((F<Integer>)(x, y) -> x + y);
		 
		 System.out.println((F<Integer>)null);
		 
		 //System.out.println(null);
		 
		 F<String>myF3 = (x, y)-> y+x;
		 System.out.println(myF3);
		 
	}

	
	public static void printHello() {
		System.out.print("Method!\n");
	}
	
	@FunctionalInterface
	interface F<T>{
		 int add(int k, int p);
	 }
}
