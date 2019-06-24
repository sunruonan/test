package com.mmall.threadLocal;

public class RequestHolder {
	private final static ThreadLocal<Long> requestHolder=new ThreadLocal<>();
	
	public static void add(Long id) {
		requestHolder.set(id);
	}
	
	public static Long getId() {
		return requestHolder.get();
	}
	
	//用完之后需要移除，否则会造成内存泄漏，只有当项目重新启动时才会释放
	public static void remove() {
		requestHolder.remove();
	}
}
