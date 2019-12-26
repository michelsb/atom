package br.com.atom.nsplanner.evaluation.plan;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import br.com.atom.nsplanner.evaluation.util.OntoPlannerUtilTest;

public class SHOPParserTest {	
	
	public void writeProblemDescriptor(ArrayList<String> facts, ArrayList<String> attributes) {
		// creates path to .lisp file
	    Path p = Paths.get(OntoPlannerUtilTest.PLAN_PROBLEM_FILE);
	    // creates US-ASCII Charset
	    Charset c = Charset.forName("US-ASCII");
	    // Strings that will be read
	    
	    ArrayList<String> descriptor = new ArrayList<String>();
	    descriptor.add("(in-package :shop-user)");
	    descriptor.add("");
	    
	    descriptor.add("(defproblem "+ OntoPlannerUtilTest.PLAN_PROBLEM_NAME + " " + OntoPlannerUtilTest.PLAN_DOMAIN_NAME);
	    descriptor.add("\t; Initial State");
	    descriptor.add("\t(");
	    for (String fact : facts) {
	    	descriptor.add("\t\t" + "(" + fact + ")");
	    }
	    descriptor.add("\t)");
	    descriptor.add("\t; Task List");
	    descriptor.add("\t(:ordered");
	    for (String attribute : attributes) {
	    	descriptor.add("\t\t(:task " + attribute +"_goal_achieved)");
	    }
	    descriptor.add("\t\t(:task print-current-state)");
	    descriptor.add("\t))");
	    
	    descriptor.add("");
	    descriptor.add("(find-plans '"+ OntoPlannerUtilTest.PLAN_PROBLEM_NAME +" :verbose :plans)");	    
	    
	    try {
	      // writes lines from file
	      Files.write(p, descriptor, c);
	    } catch (IOException e) {
	      System.err.println(e);
	    }
	 
	}
	
}
