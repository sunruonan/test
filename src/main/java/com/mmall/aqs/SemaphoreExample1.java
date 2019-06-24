package com.mmall.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SemaphoreExample1 {
	private static final Logger logger=LoggerFactory.getLogger(CountDownLatchExample1.class);
	
	private static int threadCount=20;
	
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec=Executors.newCachedThreadPool();
		final Semaphore semaphore=new Semaphore(3);
		for(int i=0;i<threadCount;i++) {
			final int threadNum=i;
			exec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						semaphore.acquire();//获取一个许可
						test(threadNum);
						semaphore.release();//释放一个许可
					}catch(InterruptedException e) {
						logger.error("exception", e);
					}
				}
			});
		}
		exec.shutdown();
	}
	
	private static void test(int threadNum) throws InterruptedException {
		logger.info("{}",threadNum);
		Thread.sleep(100);
	}

}
