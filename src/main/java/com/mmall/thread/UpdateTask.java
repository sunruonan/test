package com.mmall.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class UpdateTask implements Callable<Integer> {
	
	
	private CountDownLatch latch;
	
	
	@Override
	public Integer call() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
