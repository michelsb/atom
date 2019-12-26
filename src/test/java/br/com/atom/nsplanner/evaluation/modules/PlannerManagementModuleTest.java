package br.com.atom.nsplanner.evaluation.modules;

import java.util.ArrayList;

import br.com.atom.common.owlmanager.ConflictDetectionModule;
import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nsplanner.classes.Goal;
import br.com.atom.nsplanner.dtos.GoalDto;
import br.com.atom.nsplanner.dtos.HealingGroupDto;
import br.com.atom.nsplanner.dtos.PlanDto;
import br.com.atom.nsplanner.dtos.RuleDto;
import br.com.atom.nsplanner.dtos.ScalingGroupDto;
import br.com.atom.nsplanner.evaluation.plan.SHOPParserTest;
import br.com.atom.nsplanner.evaluation.plan.SHOPRunnerTest;
import br.com.atom.nsplanner.evaluation.repositories.GoalRepositoryTest;
import br.com.atom.nsplanner.repositories.EnforceablePolicyRepository;
import br.com.atom.nsplanner.repositories.HealingGroupRepository;
import br.com.atom.nsplanner.repositories.ScalingGroupRepository;
import br.com.atom.nsplanner.responses.PlanResponse;
import br.com.atom.nsplanner.util.NamedClasses;
import br.com.atom.nsplanner.util.OntoPlannerUtil;

public class PlannerManagementModuleTest {

	protected OntologyManager ontomanager;
	protected GoalRepositoryTest goalDB;
	protected EnforceablePolicyRepository polDB;
	protected ScalingGroupRepository sgDB;
	protected HealingGroupRepository hgDB;
	protected ConflictDetectionModule cdm;

	public PlannerManagementModuleTest(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.goalDB = new GoalRepositoryTest(ontomanager);
		this.polDB = new EnforceablePolicyRepository(ontomanager);
		this.sgDB = new ScalingGroupRepository(ontomanager);
		this.hgDB = new HealingGroupRepository(ontomanager);
		this.cdm = new ConflictDetectionModule(ontomanager);
	}
	
	public float refineGoal(GoalDto goalDto) {
		PlanResponse response = new PlanResponse();
		PlanDto planDto = new PlanDto();
		planDto.setVnfMemberIndexes(new ArrayList<String>());
		planDto.setRules(new ArrayList<RuleDto>());
		planDto.setHealingGroups(new ArrayList<HealingGroupDto>());
		planDto.setScalingGroups(new ArrayList<ScalingGroupDto>());
		response.getJsondata().setData(planDto);

		Goal goal = new Goal();
		goal.setName(goalDto.getName());
		//response.getJsondata().getData().setName("Plan for: " + goalDto.getName());

		ArrayList<String> facts = this.goalDB.getGoalPlannerInitialState(goal, response);
		ArrayList<String> attributes = this.goalDB.getGoalPlannerAttributes(goal);

		SHOPParserTest parser = new SHOPParserTest();
		parser.writeProblemDescriptor(facts, attributes);

		SHOPRunnerTest runner = new SHOPRunnerTest(this.ontomanager);
		ArrayList<String> tasks = new ArrayList<String>();
		
		float timeSearch = -1;
		
		try {
			timeSearch = runner.generatePlan(facts);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return timeSearch;
	}
}
