package br.com.atom.nsplanner.evaluation.modules;

import java.util.ArrayList;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nsplanner.classes.Goal;
import br.com.atom.nsplanner.classes.NS;
import br.com.atom.nsplanner.classes.VNF;
import br.com.atom.nsplanner.dtos.GoalDto;
import br.com.atom.nsplanner.dtos.HighLevelPolicyDto;
import br.com.atom.nsplanner.evaluation.repositories.GoalRepositoryTest;
import br.com.atom.nsplanner.repositories.NSRepository;
import br.com.atom.nsplanner.repositories.VNFRepository;

public class GoalPolicyManagementModuleTest {

	protected OntologyManager ontomanager;
	protected GoalRepositoryTest goalDB;
	protected NSRepository nsDB;
	protected VNFRepository vnfDB;

	public GoalPolicyManagementModuleTest(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.goalDB = new GoalRepositoryTest(ontomanager);
		this.nsDB = new NSRepository(ontomanager);
		this.vnfDB = new VNFRepository(ontomanager);
	}

	public void instantiateGoal(GoalDto goalDto) {

		Goal goal = new Goal();
		goal.setName(goalDto.getName());
		String goalIndName = this.goalDB.createGoalIndividual(goal);

		NS ns = new NS();
		ns.setId(goalDto.getNsrId());
		ns.setName(goalDto.getName() + " NS " + goalDto.getNsrId());
		String nsIndName = this.nsDB.createNSIndividual(ns);

		ArrayList<String> vnfIndNames = new ArrayList<String>();
		for (String index : goalDto.getVnfMemberIndexes()) {
			VNF vnf = new VNF();
			vnf.setName(goalDto.getName() + " VNF " + index);
			vnf.setVnfMemberIndex(index);
			String vnfIndName = this.vnfDB.createVNFIndividual(vnf);
			vnfIndNames.add(vnfIndName);
		}

		this.goalDB.configureGoal(goalIndName, nsIndName, vnfIndNames, goalDto.getAttributes(), goalDto.getLevel());

	}

	public void createGoals(HighLevelPolicyDto polDto) {

		for (GoalDto goalDto : polDto.getGoals()) {
			this.instantiateGoal(goalDto);
		}

	}

}
