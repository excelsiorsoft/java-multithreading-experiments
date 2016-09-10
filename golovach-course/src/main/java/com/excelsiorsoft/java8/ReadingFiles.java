package com.excelsiorsoft.java8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ReadingFiles {

	ExecutorService pool = Executors.newCachedThreadPool();
	
	public static void main(String[]args) throws IOException, InterruptedException, ExecutionException {
		new ReadingFiles().execute();
	}
	
	public void execute() throws InterruptedException, ExecutionException {
		Future<byte[]> futBytes0  = pool.submit(() -> Files.readAllBytes(Paths.get("E:\\source\\java-multithreading-experiments\\golovach-course\\src\\test\\resources\\tmp0.txt")));
		Future<byte[]> futBytes1  = pool.submit(() -> Files.readAllBytes(Paths.get("E:\\source\\java-multithreading-experiments\\golovach-course\\src\\test\\resources\\tmp0.txt")));
		Future<byte[]> futBytes2  = pool.submit(() -> Files.readAllBytes(Paths.get("E:\\source\\java-multithreading-experiments\\golovach-course\\src\\test\\resources\\tmp0.txt")));
	
		System.out.println(futBytes0.isDone());
		byte[] bytes = futBytes0.get();
		System.out.println(futBytes0.isDone());
	
	}
	
	
	
}
