package com.mmall.aqs;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CyclicBarrierExample1 {
	private static final Logger logger=LoggerFactory.getLogger(CountDownLatchExample1.class);
	
	private static CyclicBarrier barrier=new CyclicBarrier(3);
	
	public static void main(String[] args) throws Exception {
		ExecutorService exec=Executors.newCachedThreadPool();
		
		for(int i=0;i<10;i++) {
			final int threadNum=i;
			Thread.sleep(1000);
			exec.execute(new Runnable() {
				@Override
				public void run() {
					try {
						race(threadNum);
					}catch(Exception e) {
						logger.error("exception", e);
					}finally {
						
					}
				}
			});
		}
		exec.shutdown();
	}
	
	private static void race(int threadNum) throws Exception {
		Thread.sleep(1000);
		logger.info("{} is ready",threadNum);
		barrier.await();
		logger.info("{} cintinue",threadNum);
	}

}
