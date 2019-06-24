package com.mmall.common;

import com.mmall.pojo.User;

public class LoginUserContext {
	
	private static final ThreadLocal<User> threadLocal = new ThreadLocal<User>();
	
	public static void setUser(User user) {
		threadLocal.set(user);
	}
	
	public static User getUser() {
		return threadLocal.get();
	}
	
	public static void removeUser() {
		threadLocal.remove();
	}
}
