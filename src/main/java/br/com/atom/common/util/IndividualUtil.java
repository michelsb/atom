package br.com.atom.common.util;

public class IndividualUtil {

	public static String processNameForIndividual(String name) {
		return name.toLowerCase().replace(" ", "-");
	}
	
}
