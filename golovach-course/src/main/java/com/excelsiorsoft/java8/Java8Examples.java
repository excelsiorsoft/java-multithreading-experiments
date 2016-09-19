package com.excelsiorsoft.java8;

import static java.util.stream.Stream.of;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
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

		
		Supplier<Double> random = Math::random;
		for(int i = 0; i<5; i++) {
			System.out.println(random.get());
		}
		
		MyFunction<String, Integer> converter = Integer::valueOf;
		
		Function<String, Integer> func = str -> Integer.valueOf(str);
		
		Function<String, Integer> func1 =  Integer::valueOf;
		
		Predicate <Double> p= arg -> arg > 1;
		
		System.out.println("=========================");
		
		Function<String, Stream<String>> f = s -> Stream.of(s.split(" "));
		
		Arrays.asList("1", "2 33", "4 55 666").stream().flatMap(f).forEach(System.out::println);;
		
		System.out.println("=========================");
		
		Stream<Double> gen_doubles = Stream.generate(Math::random);
		
		Stream<Double> it_doubles = Stream.iterate(1d, g -> g + 1.0).limit(5); 
		it_doubles.forEach(System.out::println);;
		
		System.out.println("=========================");
		
		it_doubles = Stream.iterate(1d, g -> g + 1.0).limit(10); double sum = it_doubles.reduce(0d, (x, y) -> x+y); System.out.println(sum);
		it_doubles = Stream.iterate(1d, g -> g + 1.0).limit(10);  sum = it_doubles.reduce(0d, (x, y) -> x-y); System.out.println(sum);
		it_doubles = Stream.iterate(1d, g -> g + 1.0).limit(10);  sum = it_doubles.parallel().reduce(0d, (x, y) -> x+y); System.out.println(sum);
		//`-` is not an associative function => cannot parallelize
		it_doubles = Stream.iterate(1d, g -> g + 1.0).limit(10);  sum = it_doubles.parallel().reduce(0d, (x, y) -> x-y); System.out.println(sum);
		
		System.out.println("=========================");
		
		Optional<Double> summation = Stream.iterate(1d, g -> g + 1d).parallel().limit(3).reduce((x, y) -> x+y); //reduce without an identity
		System.out.println(Stream.iterate(1d, g -> g + 1d).parallel().limit(3).reduce((x, y) -> x+y));
		
		//sum = it_doubles.parallel().reduce(0d, (x, y) -> x-y); System.out.println(sum);
		
		System.out.println("=========================");
		
		Integer sum0 = of(1,2, 3).reduce(0, (x, y) -> x+y);
		System.out.println(sum0);
		

		Optional<Integer> sum1 = of(1,2, 3).reduce( (x, y) -> x+y);
		System.out.println(sum1);
		
		Optional<Integer> sum2 = of(1,2, 3).filter(x->x>10).reduce( (x, y) -> x+y);
		System.out.println(sum2);
		
		
		Optional<Integer> s = of(1,2,3).reduce((x, y)-> x +y);
		Optional<Integer> sqr =s.map(x -> x*x);
		Optional<String> str = sqr.map(new Function<Integer, String>() {
			@Override
			public String apply(Integer t) {
				return "#" + t;
			}
		});
	System.out.println(str);
	System.out.println(str.get());
	str.ifPresent(System.out::println);
	
	System.out.println("=========================");
	
	Integer k = 42;
	Optional<Integer> optK = Optional.of(k);
	Optional<Integer> empty = Optional.empty();

	
	Optional<Integer> optP = optK.map(x->x+1);
	optP.ifPresent(System.out::println);
	
	
	System.out.println("=========================");
	
	
	ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();
	int cpuCount = Runtime.getRuntime().availableProcessors();
	
	map.put("a", "a");
	map.put("b", "c");
	map.put("c", "c");
	map.put("d", "k");
	
	
	String result0 =  map.search(cpuCount, (key, value)->key.equals(value)? key:null);//search only returns first match
	
	System.out.println("result0: "+result0);
	
	System.out.println("=========================");
	
	String result1 = map.reduceKeys(cpuCount, (key0, key1)->key0 + key1);
	System.out.println("result1: "+result1);
	
	System.out.println("=========================");
	
	Random rnd = new Random(0);
	Stream<Integer> stream = Stream.generate(()-> rnd.nextInt(8));
	stream.distinct().limit(4).forEach(System.out::println);
	
	System.out.println("=========================");
	Stream<Integer> stream0 = Stream.generate(()->{
		System.out.println("get()"); //get is printed here
		return 0;
	});
	
	Stream<Integer> stream1 = stream0.filter(x -> x != 42);
	Stream<String> stream2 = stream1.map(x -> " "+x);
	Stream<String> stream3 = stream2.limit(3);
	
	stream3.forEach(System.out::println); //0 is printed here lazyly, if this line is commented, the `get()` will NOT get printed
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
