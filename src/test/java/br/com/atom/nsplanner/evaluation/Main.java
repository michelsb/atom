package br.com.atom.nsplanner.evaluation;

import java.io.IOException;

public class Main {	

	public static void main(String[] args) throws IOException {

		Experiments experiments = new Experiments();
		try {
			experiments.start();
		} catch (NumberFormatException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}