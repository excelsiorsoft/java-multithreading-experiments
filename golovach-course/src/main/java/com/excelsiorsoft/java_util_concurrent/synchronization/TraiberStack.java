package com.excelsiorsoft.java_util_concurrent.synchronization;

import java.util.concurrent.atomic.AtomicReference;

public class TraiberStack<T> {
	
	private AtomicReference<Node<T>> tail = null;
	private Object mutex= new Object();
	
	public void push(T newElem) {
		/*synchronized(mutex) {
			this.tail = new Node<T>(newElem, tail);
		}*/
		/*while (true) {
			Node<T> oldTail = this.tail.get();
			Node<T> newTail = new Node<>(newElem, oldTail); <- expensive
			if (tail.compareAndSet(oldTail, newTail)) {
				break;
			}
		}*/
		
		Node<T> newTail = new Node<>(newElem,null);	
		while(true) {
			Node<T> oldTail = this.tail.get();
			newTail.next = oldTail;
			if (tail.compareAndSet(oldTail, newTail)) {
				break;
			}
		}
	}
	
	public T pop() {
		/*synchronized(mutex) {
			T result = tail.value;	
			this.tail = tail.next;
			return result;
		}*/
		while (true) {
			Node<T> oldTail = tail.get();
			T result = oldTail.value;
			Node<T> newTail = oldTail.next;

			if (this.tail.compareAndSet(oldTail, newTail)) {
				return result;
			}
		}
	}
	
	private static class Node<T>{
		
		private final T value;
		private /*final*/Node<T> next;
		
		public Node(T value, Node<T> next) {
			super();
			this.value = value;
			this.next = next;
		}
		
	}

}
