package com.mmall.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionDemo {
	
	@Pointcut("execution(* com.mmall.service.*Service.*(..))")
	public void point() {
		
	}
	
	//ProceedingJoinPoint 
	
	@Before("point()")
	public void before() {
		System.out.println("transaction begin");
	}
	
	@After("point()")
	public void after() {
		System.out.println("transaction after");
	}
	
	@Around("point()")
	public Object aroun(ProceedingJoinPoint joinPoint) throws Throwable {
		System.out.println("开始事物");
		Object obj=joinPoint.proceed();
		Object[] ob  = joinPoint.getArgs();
		String method = joinPoint.getSignature().getName();
		System.out.println("方法名字"+method);
		String className = joinPoint.getTarget().getClass().getName();
		System.out.println("类名字"+className);
		System.err.println(ob.toString());
		System.out.println(ob[0]+"zsl");
		System.err.println(obj.toString()+"srn");
		System.out.println("结束事物");
		return obj;
	}

}
