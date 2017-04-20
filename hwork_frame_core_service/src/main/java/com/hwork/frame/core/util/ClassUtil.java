package com.hwork.frame.core.util;

public class ClassUtil {

	public static boolean contains(String className){
		try {
			Class.forName(className);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}
	
	public static Class<?> getClass(String className){
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
}
