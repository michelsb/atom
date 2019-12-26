package br.com.atom.nsplanner.evaluation;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import br.com.atom.common.owlmanager.ConflictDetectionModule;
import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nsplanner.dtos.GoalDto;
import br.com.atom.nsplanner.dtos.HealingThresholdDto;
import br.com.atom.nsplanner.dtos.HighLevelPolicyDto;
import br.com.atom.nsplanner.dtos.HorizontalScalingThresholdDto;
import br.com.atom.nsplanner.dtos.LowLevelPolicyDto;
import br.com.atom.nsplanner.evaluation.classes.InitialStateStatus;
import br.com.atom.nsplanner.evaluation.modules.GoalPolicyManagementModuleTest;
import br.com.atom.nsplanner.evaluation.modules.PlannerManagementModuleTest;
import br.com.atom.nsplanner.evaluation.modules.ThresholdManagementModuleTest;
import br.com.atom.nsplanner.evaluation.util.NamedMetricTest;
import br.com.atom.nsplanner.evaluation.util.OntoPlannerUtilTest;
import br.com.atom.nsplanner.util.NamedAction;
import br.com.atom.nsplanner.util.NamedClasses;

public class Experiments {

	public OntologyManager ontomanager;
	public Map<String, String> levels;
	public InitialStateStatus status;
	public ArrayList<String> namedActions;
	public ArrayList<String> namedMetrics;

	public Experiments() {
		levels = new HashMap<String, String>();
		// levels.put("low", "LOW");
		// levels.put("medium", "MEDIUM");
		levels.put("high", "HIGH");
		this.namedActions = new ArrayList<String>();
		this.namedMetrics = new ArrayList<String>();
	}

	public String getFileName(int num_thresholds, int num_goals, String level_name) {
		String fileName = "onto-nfv-with-level-" + level_name.toLowerCase() + "-" + num_thresholds + "-" + num_goals
				+ ".owl";
		return fileName;
	}

	public void generateInitialState(int num_thresholds, int num_goals, String level_name) {
		String fileName = getFileName(num_thresholds, num_goals, level_name);

		LowLevelPolicyDto policy = new LowLevelPolicyDto();
		ArrayList<HealingThresholdDto> healPols = new ArrayList<HealingThresholdDto>();
		ArrayList<HorizontalScalingThresholdDto> horScalePols = new ArrayList<HorizontalScalingThresholdDto>();
		int count = 0;
		boolean test = false;
		
		for (String metric : namedMetrics) {
			this.ontomanager.createIndividual(metric, NamedClasses.METRIC);
			for (String action : namedActions) {
				if (action.equals(NamedAction.HORIZONTAL_SCALE)) {
					count += 1;
					HorizontalScalingThresholdDto horScalePol = new HorizontalScalingThresholdDto();
					horScalePol.setName("scale thr " + count);
					horScalePol.setMetric(metric);
					horScalePol.setHighScaleOutThresholdValue(50);
					horScalePol.setHighScaleInThresholdValue(10);
					horScalePol.setMediumScaleOutThresholdValue(70);
					horScalePol.setMediumScaleInThresholdValue(20);
					horScalePol.setLowScaleOutThresholdValue(90);
					horScalePol.setLowScaleInThresholdValue(30);
					horScalePols.add(horScalePol);
					if (count == num_thresholds) {
						test = true;
						break;
					}
				} else {
					count += 1;
					HealingThresholdDto healPol = new HealingThresholdDto();
					healPol.setName("heal thr " + count);
					healPol.setMetric(metric);
					healPol.setAction(action);
					healPol.setHighThresholdValue(10);
					healPol.setMediumThresholdValue(30);
					healPol.setLowThresholdValue(50);
					healPols.add(healPol);
					if (count == num_thresholds) {
						test = true;
						break;
					}
				}
			}
			if (test)
				break;
		}

		/*System.out.println(num_thresholds);
		System.out.println(count);
		System.out.println(healPols.size());
		System.out.println(horScalePols.size());*/
		
		policy.setName("test");
		policy.setHealPols(healPols);
		policy.setHorScalePols(horScalePols);

		ThresholdManagementModuleTest thrModule = new ThresholdManagementModuleTest(this.ontomanager);
		thrModule.createPolicy(policy);

		HighLevelPolicyDto polDto = new HighLevelPolicyDto();
		polDto.setName("test");
		ArrayList<GoalDto> goals = new ArrayList<GoalDto>();

		for (int i = 1; i <= num_goals; i++) {
			GoalDto goal = new GoalDto();
			goal.setName("goal " + i);
			goal.setNsrId("" + i);
			goal.setLevel(level_name.toLowerCase());
			ArrayList<String> vnfMemberIndexes = new ArrayList<String>();
			vnfMemberIndexes.add("" + i);
			goal.setVnfMemberIndexes(vnfMemberIndexes);
			ArrayList<String> att = new ArrayList<String>();
			att.add("resilience");
			goal.setAttributes(att);
			goals.add(goal);
		}
		polDto.setGoals(goals);

		GoalPolicyManagementModuleTest goalModule = new GoalPolicyManagementModuleTest(this.ontomanager);
		goalModule.createGoals(polDto);

		this.ontomanager.saveNewOntologyFile(OntoPlannerUtilTest.ONTOTEMPDIR + fileName);

		/*status = new InitialStateStatus();
		status.setTotalThresholds(num_thresholds);
		status.setTotalGoals(num_goals);
		status.setFile(new File(OntoPlannerUtilTest.ONTOTEMPDIR + fileName));*/
	}

	public void runExperiment(int num_thresholds, int num_goals, int rounds, PrintWriter writer)
			throws IOException, InterruptedException {

		ArrayList<Float> timesConsistencyTest = null;
		ArrayList<Float> timesPlanningTest = null;
		ArrayList<Float> timesSearchPlanTest = null;
		ArrayList<Float> timesConsistencyPlanTest = null;
		ConflictDetectionModule cdm = null;
		PlannerManagementModuleTest planModule = null;
		long startTime = 0;
		long endTime = 0;
		long totalTime = 0;
		float searchTime = 0;
		String level_name = "";
		String step2 = "";
		GoalDto goal = new GoalDto();
		goal.setName("goal 1");
		
		System.out.println("#### BEGIN - Run Experiment for: " + num_thresholds + " deployed Thresholds and "
				+ num_goals + " deployed Goals" + " ####");

		for (String key : levels.keySet()) {
			level_name = key;

			System.out.println("# Step 1 - Generating Initial State..."); // LOG
			this.ontomanager = new OntologyManager();
			this.ontomanager.startOntologyProcessing(OntoPlannerUtilTest.ONTOFILE, OntoPlannerUtilTest.NFV_IRI);
			generateInitialState(num_thresholds, num_goals, level_name);
			System.out.println("# Step 1 - Initial State Generated..."); // LOG
			//this.ontomanager.close();
			//this.ontomanager = null;

			Thread.sleep(5000);
			
			writer.println("------------ BEGIN LEVEL: " + level_name + " ------------");

			String ontoFilePath = OntoPlannerUtilTest.ONTOTEMPDIR + getFileName(num_thresholds, num_goals, level_name);
			File ontoFile = new File(ontoFilePath);
			writer.println("File Size (bytes): " + ontoFile.length());
			writer.println("Total Thresholds: " + num_thresholds);
			writer.println("Total Goals: " + num_goals);
			
			timesConsistencyTest = new ArrayList<Float>();
			timesPlanningTest = new ArrayList<Float>();
			timesSearchPlanTest = new ArrayList<Float>();
			timesConsistencyPlanTest = new ArrayList<Float>();
			
			//this.ontomanager = new OntologyManager();
			//this.ontomanager.startOntologyProcessing(ontoFilePath, OntoPlannerUtilTest.NFV_IRI);
			step2 = "# Step 2 - Performing Experiments - " + num_thresholds
					+ " deployed Thresholds and " + num_goals + " deployed Goals " + level_name + " Level";
			System.out.println(step2); // LOG
			//System.out.println(">>>>>> File loaded!");
			//Thread.sleep(10000);
			
			for (int i = 0; i < rounds; i++) {
				// Calculate the time for Consistency Test
				System.out.println(">> Testing Consistency");
				System.out.println("> Round: " + i);
				
				startTime = System.nanoTime();
				cdm = new ConflictDetectionModule(this.ontomanager);
				cdm.start();
				cdm.testOntoConsistency();
				endTime = System.nanoTime();
				totalTime = endTime - startTime;
				timesConsistencyTest.add((float) totalTime / 1000000);
				
				cdm.close();
				cdm = null;
				System.out.println(this.ontomanager.getAllIndividuals().size());
				//System.gc();
				//Thread.sleep(1000);
			}
			
			planModule = new PlannerManagementModuleTest(this.ontomanager);
			
			for (int i = 0; i < rounds; i++) {
				// Calculate the time for Planning Test
				//System.out.println(step2); // LOG
				System.out.println(">> Testing Planning");
				System.out.println("> Round: " + i);
				
				System.out.println(this.ontomanager.getAllIndividuals().size());
				
				startTime = System.nanoTime();
				searchTime = planModule.refineGoal(goal);
				endTime = System.nanoTime();
				totalTime = endTime - startTime;
				timesPlanningTest.add((float) totalTime / 1000000);
				if (searchTime == -1) {
					System.out.println("PROBLEMS WHILE PLANNING. EXITING...");
					System.exit(0);
				}
				timesSearchPlanTest.add(searchTime);
				System.out.println(this.ontomanager.getAllIndividuals().size());
				//System.gc();
				//Thread.sleep(1000);
			}

			for (int i = 0; i < rounds; i++) {
				//System.out.println(step2); // LOG
				System.out.println(">> Testing Consistency after Planning");
				System.out.println("> Round: " + i);
				
				startTime = System.nanoTime();
				cdm = new ConflictDetectionModule(this.ontomanager);
				cdm.start();
				cdm.testOntoConsistency();
				endTime = System.nanoTime();
				totalTime = endTime - startTime;
				timesConsistencyPlanTest.add((float) totalTime / 1000000);
				
				cdm.close();
				cdm = null;
				//System.gc();
				//Thread.sleep(1000);
			}
			
			this.ontomanager.close();
			this.ontomanager = null;
			
			writer.println("Time to check consistency (milliseconds): " + timesConsistencyTest.toString());
			writer.println("Time to planning (milliseconds): " + timesPlanningTest.toString());
			writer.println("Time to search (seconds): " + timesSearchPlanTest.toString());
			//writer.println("Number of generated policy rules: " + this.ontomanager.getClassIndividualsWithoutReasoner(NamedClasses.POLICYRULE).size());
			writer.println("Time to check plancon (milliseconds): " + timesConsistencyPlanTest.toString());
			writer.println("--------");

			System.out.println("# Step 2 - Finished Performing Experiments..."); // LOG

			writer.println("------------ END LEVEL: " + level_name + " ------------");

			Thread.sleep(5000);
		}

		System.out.println("#### END - Run Experiment for: " + num_thresholds + " deployed Thresholds and " + num_goals
				+ " deployed Goals" + " ####");
		
		
	}

	public void start() throws IOException, NumberFormatException, InterruptedException {

		Properties prop = new Properties();
		InputStream input = null;
		PrintWriter writer = null;

		NamedAction actions = new NamedAction();
		NamedMetricTest metrics = new NamedMetricTest();

		try {
			Field[] fields = NamedAction.class.getDeclaredFields();
			for (Field field : fields) {
				String value = (String) field.get(actions);
				this.namedActions.add(value.toLowerCase());
			}
			fields = NamedMetricTest.class.getDeclaredFields();
			for (Field field : fields) {
				String value = (String) field.get(metrics);
				this.namedMetrics.add(value.toLowerCase());
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();

		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}

		if (levels.size() > 0) {
			try {
				input = new FileInputStream(OntoPlannerUtilTest.CONFIGFILE);
				// load a properties file
				prop.load(input);

				ArrayList<String> levels_num_thresholds = new ArrayList<String>(
						Arrays.asList(prop.getProperty("num_thresholds").split(",")));
				ArrayList<String> levels_num_goals = new ArrayList<String>(
						Arrays.asList(prop.getProperty("num_goals").split(",")));
				String rounds = prop.getProperty("rounds");

				input.close();

				for (String level_num_thresholds : levels_num_thresholds) {
					for (String level_num_goals : levels_num_goals) {
						writer = new PrintWriter(OntoPlannerUtilTest.RESULTDIR + "result-"
								+ new Integer(level_num_thresholds) + "-" + new Integer(level_num_goals) + ".txt",
								"UTF-8");
						runExperiment(new Integer(level_num_thresholds), new Integer(level_num_goals),
								new Integer(rounds), writer);
						writer.close();
					}
				}

			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			System.out.println("Please insert levels");
		}
	}

}

