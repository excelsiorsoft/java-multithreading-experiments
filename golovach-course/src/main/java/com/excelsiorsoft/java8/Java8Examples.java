package com.excelsiorsoft.java8;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class Java8Examples {

	public static void main(String[] args) throws Exception {

		new Thread(Java8Examples::printHello).start();

		new Thread(() -> System.out.println("Lambda!")).start();

		Arrays.asList("A", "BB", "CCC").parallelStream().map(/*
															 * str ->
															 * str.length()
															 */String::length)
				.filter(k -> k % 2 == 1).forEach(System.out::println);

		System.out.println("=========================");

		Stream.iterate(2L, k -> k + 3).limit(20).forEach(System.out::println);
		;

		System.out.println("=========================");

		Stream.iterate(2L, k -> k + 1).filter(k -> k % 3 == 2).limit(20)
				.forEach(System.out::println);
		;

		System.out.println("=========================");

		Stream.iterate(0L, k -> k + 1).parallel().filter(k -> k % 3 == 2)
				.map(k -> "~" + k).limit(10)
				.forEach(k -> System.out.println(k));

		System.out.println("=========================");

		Stream.iterate(0L, k -> k + 1).parallel().filter(k -> k % 3 == 2)
				.map(k -> "~" + k).limit(10).forEach(new Consumer<String>() {

					@Override
					public void accept(String t) {
						System.out.println(Thread.currentThread());

					}

				});

		System.out.println("=========================");

		F myF = (x, y) -> x + y;
		F myF1 = (int x, int y) -> x + y;
		F<Integer> myF2 = (x, y) -> x + y;

		System.out.println(myF2);
		System.out.println((F<Integer>) (x, y) -> x + y);

		System.out.println((F<Integer>) null);

		// System.out.println(null);

		F<String> myF3 = (x, y) -> y + x;
		System.out.println(myF3);

		System.out.println("=========================");

		Consumer<String> consumer = s -> System.out.println(s.charAt(0));

		Consumer<String> cons = System.out::println;

		System.out.println("=========================");
		System.out.println(new Java8Examples().new User().toXml());

		System.out.println("=========================");
		
		Arrays.asList("1", "2 33", "4 55 666").stream();
		
		Supplier<Double> random = Math::random;
		for(int i = 0; i<5; i++) {
			System.out.println(random.get());
		}
		
		MyFunction<String, Integer> converter = Integer::valueOf;
		
		
	}

	public static void printHello() {
		System.out.print("Method!\n");
	}

	@FunctionalInterface
	interface F<T> {
		int add(int k, int p);
	}

	interface G<T> {
		T f(T k, T p);

		default void ff0() {
		}

		static void ff1() {
		}

		default void ff2() {
		}

		static void ff3() {
		}
	}

	interface Parent {
		void f();

		default void g() {
		};
	}

	class Child implements Parent {

		@Override
		public void f() {
		}

		// g is not required any longer in Java 8, default replaces that need

	}

	interface Xmlizer {
		default String toXml() throws Exception
				 {
			Class<? extends Xmlizer> clazz = this.getClass();
			Field[] fields = clazz.getDeclaredFields();

			String result = "";
			result += "<" + clazz.getSimpleName() + ">";
			for (Field field : fields) {
				field.setAccessible(true);
				
				result += field.getName() + " = "
						+ field.get(this) + "\n";
				
			}
			result += "</" + clazz.getSimpleName() + ">";
			return result;
		}
	}

	public class User implements Xmlizer {

		private int age = 42;
		private String name = "Masha";
	}
	
	interface MyFunction<T extends Comparable<?>, R>{
		
		R apply(T arg);
	}

}
