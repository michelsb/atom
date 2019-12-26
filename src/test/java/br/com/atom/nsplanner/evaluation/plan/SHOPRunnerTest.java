package br.com.atom.nsplanner.evaluation.plan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.semanticweb.owlapi.reasoner.OWLReasoner;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nsplanner.evaluation.util.OntoPlannerUtilTest;
import br.com.atom.nsplanner.util.NamedClasses;
import br.com.atom.nsplanner.util.NamedDataProp;
import br.com.atom.nsplanner.util.NamedObjectProp;

public class SHOPRunnerTest {

	protected OntologyManager ontomanager;
	protected OWLReasoner reasoner;

	public SHOPRunnerTest(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
	}
	
	public float generatePlan(ArrayList<String> facts) throws InterruptedException {

		Path pathDomain = Paths.get(OntoPlannerUtilTest.PLAN_DOMAIN_FILE);
		Path pathProblem = Paths.get(OntoPlannerUtilTest.PLAN_PROBLEM_FILE);
		ArrayList<String> orderedTasks = new ArrayList<String>();
		float timeSearch = 0;

		if (Files.exists(pathDomain) && Files.exists(pathProblem)) {

			try {

				String os = System.getProperty("os.name");
				Process p;

				if (os.contains("Windows")) {
					String[] cmdArray = { "cmd", "/c", "sbcl", "--load",
							OntoPlannerUtilTest.PLAN_DOMAIN_FILE, "--load",
							OntoPlannerUtilTest.PLAN_PROBLEM_FILE, "--quit" };
					p = Runtime.getRuntime().exec(cmdArray);
				} else {
					String[] cmdArray = { "/bin/bash", "-c", "sbcl", "--load",
							OntoPlannerUtilTest.PLAN_DOMAIN_FILE, "--load",
							OntoPlannerUtilTest.PLAN_PROBLEM_FILE, "--quit" };
					p = Runtime.getRuntime().exec(cmdArray);
				}

				boolean exitCode = true;//p.waitFor(10, TimeUnit.SECONDS);

				if (exitCode) {

					BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

					String plans = "";
					String line = "";
					String states = "";
					String stats = "";
					//ArrayList<String> states = new ArrayList<String>();
					while ((line = stdInput.readLine()) != null) {
						// System.out.println(s);
						if (line.contains("Problem #<SHOP")) {
							//states.add(stdInput.readLine().trim());
							states = stdInput.readLine().trim();
							while ((line = stdInput.readLine()) != null) {
								if (line.contains("Totals: Plans")) {
									stats = stdInput.readLine();
									break;
								}
								states = states + " " + line.trim();
								//states.add(line.trim());
							}
						}
						if (line.contains("Plans:")) {
							plans = stdInput.readLine();
							while ((line = stdInput.readLine()) != null) {
								plans = plans + " " + line.trim();
							}
							break;
						}
					}
					
					if (!states.equals("")) {
						
						Map<String,String> namedClasses = new HashMap<String,String>();
						Map<String,String> namedOps = new HashMap<String,String>();
						Map<String,String> namedDps = new HashMap<String,String>();
						
						NamedClasses cls = new NamedClasses();
						NamedObjectProp ops = new NamedObjectProp();
						NamedDataProp dps = new NamedDataProp();
						
						try {
							Field[] fields = NamedClasses.class.getDeclaredFields();
							for (Field field : fields) {								
									String value = (String) field.get(cls);
									namedClasses.put(value.toLowerCase(),value);								
							}
							fields = NamedObjectProp.class.getDeclaredFields();
							for (Field field : fields) {								
									String value = (String) field.get(ops);
									namedOps.put(value.toLowerCase(),value);								
							}
							fields = NamedDataProp.class.getDeclaredFields();
							for (Field field : fields) {								
									String value = (String) field.get(dps);
									namedDps.put(value.toLowerCase(),value);								
							}
						} catch (IllegalArgumentException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return -1;
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							return -1;
						}						
						
						Set<String> newFactsSet = new HashSet<String>();
						Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(states);
						while(m.find()) {
							newFactsSet.add(m.group(1).toLowerCase());       
						}
						Set<String> oldFactsSet = new HashSet<String>();
						for (String fact : facts) {
							oldFactsSet.add(fact.toLowerCase());
						}
						newFactsSet.removeAll(oldFactsSet);
						Set<String> newTypeSet = new HashSet<String>();
						Set<String> newPropSet = new HashSet<String>();
						
						for (String newFact : newFactsSet) {
							String[] factParts = newFact.split(" ");
							if (factParts.length == 2) {
								newTypeSet.add(newFact);
															
							} else {
								if (factParts.length == 3) {
									newPropSet.add(newFact);									
								}
							} 
						}
						Set<String> processingTypeSet = new HashSet<String>(newTypeSet);
						//Set<String> processingTypeSet = new HashSet<String>();
						//processingTypeSet.addAll(newTypeSet);
						int sequence = 1;
						for (String newType : processingTypeSet) {
							String className = newType.split(" ")[0];
							String indOldName = newType.split(" ")[1];
							//UUID indNewName = UUID.randomUUID();
							if (className.contentEquals(NamedClasses.POLICYRULE.toLowerCase())) {
								continue;
							} 
					
							String indNewName = "object"+sequence;
							
							sequence+=1;
							newTypeSet.remove(newType);
							newTypeSet.add(newType.replace(indOldName, indNewName));
							Set<String> processingPropSet = new HashSet<String>(newPropSet);
							for (String newProp : processingPropSet) {
								String[] values = newProp.split(" ");
								if (Arrays.stream(values).anyMatch(indOldName::equals)) {
									newPropSet.remove(newProp);
									newPropSet.add(newProp.replace(indOldName, indNewName));
								}
								/*if (newProp.contains(" "+indOldName+" ")) {
									newPropSet.remove(newProp);
									newPropSet.add(newProp.replace(indOldName, indNewName));
								}*/
							}							
						}
						
						
						for (String newType : newTypeSet) {
							//System.out.println(newType);
							String[] factParts = newType.split(" ");
							if (namedClasses.containsKey(factParts[0])) {
								this.ontomanager.createIndividual(factParts[1], namedClasses.get(factParts[0]));
							}
						}
						for (String newProp : newPropSet) {
							String[] factParts = newProp.split(" ");
							if (namedOps.containsKey(factParts[0])) {
								this.ontomanager.createObjectPropertyAssertionAxiom(factParts[1], factParts[2], namedOps.get(factParts[0]));
							} else {
								if (namedDps.containsKey(factParts[0])) {
									boolean isNumeric = factParts[2].chars().allMatch( Character::isDigit );
									if ((isNumeric) && (!factParts[0].equals("hasmembervnfindex")) && (!factParts[0].equals("hasmembervnfindexref") && (!factParts[0].equals("hasparametervalue")))){
										this.ontomanager.createDataPropertyAssertionAxiom(factParts[1], this.ontomanager.getFactory().getOWLLiteral(Integer.parseInt(factParts[2])), namedDps.get(factParts[0]));
									} else {
										this.ontomanager.createDataPropertyAssertionAxiom(factParts[1], this.ontomanager.getFactory().getOWLLiteral(factParts[2].toUpperCase()), namedDps.get(factParts[0]));
									}
								}
							}
						}				
						
					} else {
						return -1;
					}
									
					if (!plans.contains("NIL")) {
						Matcher m = Pattern.compile("\\(([^)]+)\\)").matcher(plans);
						while(m.find()) {
							String taskName = m.group(1).replace("!", "").replace("(", "").replace(")", "");
							orderedTasks.add(taskName);							
						}
					} else {
						return -1;
					}	
					//this.ontomanager.saveNewOntologyFile("new-planner.owl");
					timeSearch = Float.parseFloat(stats.trim().replaceAll(" +", " ").split(" ")[6]);
					return timeSearch;
				} else {
					return -1;
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		} 
		
		return -1;

	}

}
