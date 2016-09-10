package com.excelsiorsoft.java8;

import static java.nio.file.Files.readAllBytes;
import static java.nio.file.Paths.get;
import static java.util.Arrays.asList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ReadingFiles {

	static ExecutorService pool = Executors.newCachedThreadPool();
	
	public static void main(String[]args) throws IOException, InterruptedException, ExecutionException {
		ReadingFiles readingFiles = new ReadingFiles();
		readingFiles.execute();
		System.out.println("----------invokeAll()---------------");
		readingFiles.invokeAll();
		System.out.println("----------invokeAny()---------------");
		readingFiles.invokeAny();
		
		pool.shutdown();
	}
	
	public void execute() throws InterruptedException, ExecutionException {
		Future<byte[]> futBytes0  = pool.submit(() -> Files.readAllBytes(Paths.get("E:\\source\\java-multithreading-experiments\\golovach-course\\src\\test\\resources\\tmp0.txt")));
		Future<byte[]> futBytes1  = pool.submit(() -> Files.readAllBytes(Paths.get("E:\\source\\java-multithreading-experiments\\golovach-course\\src\\test\\resources\\tmp0.txt")));
		Future<byte[]> futBytes2  = pool.submit(() -> Files.readAllBytes(Paths.get("E:\\source\\java-multithreading-experiments\\golovach-course\\src\\test\\resources\\tmp0.txt")));
	
		System.out.println(futBytes0.isDone());
		byte[] bytes = futBytes0.get();
		System.out.println(futBytes0.isDone());
		
	
	}
	
	public void invokeAll() throws InterruptedException, ExecutionException {
		
		List<Future<byte[]>> listFutBytes = pool.invokeAll(
				asList(
						() -> Files.readAllBytes(Paths.get("E:\\source\\java-multithreading-experiments\\golovach-course\\src\\test\\resources\\tmp0.txt")),
						() -> Files.readAllBytes(Paths.get("E:\\source\\java-multithreading-experiments\\golovach-course\\src\\test\\resources\\tmp0.txt")),
						() -> Files.readAllBytes(Paths.get("E:\\source\\java-multithreading-experiments\\golovach-course\\src\\test\\resources\\tmp0.txt"))
					)
				);
		
		
		System.out.println(listFutBytes.get(0).isDone());
		System.out.println(listFutBytes.get(1).isDone());
		System.out.println(listFutBytes.get(2).isDone());
		
		
	
	}
	
public void invokeAny() throws InterruptedException, ExecutionException {
		
		byte[] bytes =  pool.invokeAny(
				asList(
						(Callable<byte[]>)() -> readAllBytes(get("E:\\source\\java-multithreading-experiments\\golovach-course\\src\\test\\resources\\tmp0.txt")),
						(Callable<byte[]>)() -> readAllBytes(get("E:\\source\\java-multithreading-experiments\\golovach-course\\src\\test\\resources\\tmp0.txt")),
						(Callable<byte[]>)() -> readAllBytes(get("E:\\source\\java-multithreading-experiments\\golovach-course\\src\\test\\resources\\tmp0.txt"))
					)
				);
		
		
		System.out.println(/*Arrays.toString(bytes)*/new String(bytes)); //could return any of 3 files, whichever finished its execution
		
		
		
	
	}
	
	
	
}
