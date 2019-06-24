package com.mmall.aqs;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CountDownLatchExample1 {
	private static final Logger logger=LoggerFactory.getLogger(CountDownLatchExample1.class);
	
	private static int threadCount=200;
	public static void main(String[] args) throws InterruptedException {
		ExecutorService exec=Executors.newCachedThreadPool();
		final CountDownLatch countDownLatch=new CountDownLatch(threadCount);
		for(int i=0;i<threadCount;i++) {
			final int threadNum=i;
			exec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						test(threadNum);
					}catch(InterruptedException e) {
						logger.error("exception", e);
					}finally {
						countDownLatch.countDown();
					}
				}
			});
		}
		//超过10ms则不再等待，会继续执行下面的代码
		countDownLatch.await(10,TimeUnit.MILLISECONDS);
		logger.info("finish");
		exec.shutdown();
	}
	
	private static void test(int threadNum) throws InterruptedException {
		Thread.sleep(100);
		logger.info("{}",threadNum);
	}

}
