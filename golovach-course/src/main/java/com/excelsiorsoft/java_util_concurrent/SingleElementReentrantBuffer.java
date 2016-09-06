package com.excelsiorsoft.java_util_concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SingleElementReentrantBuffer {

	private final Lock lock = new ReentrantLock(true);
	private final Condition notEmpty = lock.newCondition();
	private final Condition notFull = lock.newCondition();
	private Integer elem = null;

	public void put(int newElem) throws InterruptedException {

		lock.lock();
		try {
			while (this.elem != null) {
				notFull.await(); // await instead of wait
			}
			this.elem = newElem;
			notEmpty.signal();// signal instead of signalAll
		} finally {
			lock.unlock();
		}
	}

	// @SuppressWarnings("finally")
	public boolean tryPut(int newElem) throws InterruptedException {

		if (lock.tryLock()) {
			try {
				while (this.elem != null) {
					notFull.await(); // await instead of wait
				}
				this.elem = newElem;
				notEmpty.signal();// signal instead of signalAll
				return true;
			} finally {
				lock.unlock();
			}
		} else {
			return false;
		}
	}

	public boolean tryPut(int newElem, long timeout, TimeUnit units) throws InterruptedException {

		if (lock.tryLock(timeout, units)) {
			try {
				while (this.elem != null) {
					notFull.await(); // await instead of wait
				}
				this.elem = newElem;
				notEmpty.signal();// signal instead of signalAll
				return true;
			} finally {
				lock.unlock();
			}
		} else {
			return false;
		}
	}
	
	public int get() throws InterruptedException {
		lock.lock();
		try {
			while (elem == null) {
				notEmpty.await(); // await instead of wait
			}
			Integer result = this.elem;
			notFull.signal(); // signal intead of signalAll
			return result;
		} finally {
			lock.unlock();
		}
	}

}
