package com.mmall.aop;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mmall.service.IUserService;

public class TransactionDemoTest {
	
	@Test
	public void test1() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");
		IUserService iUserService=applicationContext.getBean(IUserService.class);
		System.out.println("###begin test###");
		iUserService.login("admin", "3456");
	}

}
