package br.com.atom.common.util;

public class ClassUtil {

	public static String processNameForClass(String name) {
		return name.toUpperCase().replace(" ", "-");
	}
	
}
