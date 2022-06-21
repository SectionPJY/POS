package com.java.pos.account;

public class UserThreadLocal {
	private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
	
	public static void set(String id) {
		threadLocal.set(id);
	}
	
	public static String get() {
		return threadLocal.get();
	}
}
