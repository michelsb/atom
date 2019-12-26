package br.com.atom.nsplanner.modules;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import br.com.atom.common.owlmanager.ConflictDetectionModule;
import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.responses.ConsistencyResponse;
import br.com.atom.nsplanner.classes.Goal;
import br.com.atom.nsplanner.dtos.GoalDto;
import br.com.atom.nsplanner.dtos.HealingGroupDto;
import br.com.atom.nsplanner.dtos.PlanDto;
import br.com.atom.nsplanner.dtos.RuleDto;
import br.com.atom.nsplanner.dtos.ScalingGroupDto;
import br.com.atom.nsplanner.plan.SHOPParser;
import br.com.atom.nsplanner.plan.SHOPRunner;
import br.com.atom.nsplanner.repositories.EnforceablePolicyRepository;
import br.com.atom.nsplanner.repositories.GoalRepository;
import br.com.atom.nsplanner.repositories.HealingGroupRepository;
import br.com.atom.nsplanner.repositories.ScalingGroupRepository;
import br.com.atom.nsplanner.responses.PlanResponse;
import br.com.atom.nsplanner.util.OntoPlannerUtil;

@Service
public class PlannerManagementModule {

	protected OntologyManager ontomanager;
	protected GoalRepository goalDB;
	protected EnforceablePolicyRepository polDB;
	protected ScalingGroupRepository sgDB;
	protected HealingGroupRepository hgDB;
	protected ConflictDetectionModule cdm;
	protected boolean status;

	public PlannerManagementModule() {
		this.ontomanager = new OntologyManager();
		this.status = false;
	}

	public void start() {
		this.ontomanager.startOntologyProcessing(OntoPlannerUtil.ONTOFILE, OntoPlannerUtil.NFV_IRI);
		this.cdm = new ConflictDetectionModule(ontomanager);
		// instantiate all repositories
		this.goalDB = new GoalRepository(ontomanager);
		this.polDB = new EnforceablePolicyRepository(ontomanager);
		this.sgDB = new ScalingGroupRepository(ontomanager);
		this.hgDB = new HealingGroupRepository(ontomanager);
		this.status = true;
	}

	public void reload() {
		this.cdm = new ConflictDetectionModule(ontomanager);
		// instantiate all repositories
		this.goalDB = new GoalRepository(ontomanager);
		this.polDB = new EnforceablePolicyRepository(ontomanager);
		this.sgDB = new ScalingGroupRepository(ontomanager);
		this.hgDB = new HealingGroupRepository(ontomanager);
		this.status = true;
	}

	public void saveUpdates() {
		this.ontomanager.saveOntologyFile();
	}

	public void stop() {
		this.ontomanager.close();
		this.cdm = null;
		// apply null to all repositories
		this.goalDB = null;
		this.polDB = null;
		this.sgDB = null;
		this.hgDB = null;
		this.status = false;
	}

	public PlanResponse refineGoal(GoalDto goalDto) {
		PlanResponse response = new PlanResponse();
		PlanDto planDto = new PlanDto();
		planDto.setVnfMemberIndexes(new ArrayList<String>());
		planDto.setRules(new ArrayList<RuleDto>());
		planDto.setHealingGroups(new ArrayList<HealingGroupDto>());
		planDto.setScalingGroups(new ArrayList<ScalingGroupDto>());
		response.getJsondata().setData(planDto);

		if (status) {
			//this.cdm.start();
			//this.cdm.getReasoner().precomputeInferences();
			Goal goal = new Goal();
			goal.setName(goalDto.getName());
			response.getJsondata().getData().setName("Plan for: " + goalDto.getName());

			ArrayList<String> facts = this.goalDB.getGoalPlannerInitialState(goal, response);
			ArrayList<String> attributes = this.goalDB.getGoalPlannerAttributes(goal);

			SHOPParser parser = new SHOPParser();
			parser.writeProblemDescriptor(facts, attributes);

			SHOPRunner runner = new SHOPRunner(this.ontomanager);
			ArrayList<String> tasks = new ArrayList<String>();
			try {
				tasks = runner.generatePlan(facts);
				if (tasks == null) {
					response.setMessage(
							"ERROR: Goal was not refined! Problems found when executing the Planning System!");
					return response;
				}
				//this.cdm.close();
				this.cdm.start();
				ConsistencyResponse consResponse = this.cdm.testOntoConsistency();
				response.setConsistency(consResponse);
				if (!consResponse.isConsistent()) {
					response.setMessage("ERROR: Goal was not refined! Updates made Onto-Planner inconsistent!");
					return response;
				}

				this.cdm.close();

				// int sequence = 1;
				for (String task : tasks) {
					String indPolicyRuleName = "pol_" + task.toLowerCase();
					response.getJsondata().getData().getRules().add(this.polDB.getPolicyRule(indPolicyRuleName));
					/*
					 * TaskDto taskDto = new TaskDto(); taskDto.setSequence(sequence); sequence+=1;
					 * taskDto.setName(task);
					 * response.getJsondata().getData().getTasks().add(taskDto);
					 */
				}

				// this.cdm.getReasoner().precomputeInferences();

				/*
				 * Set<OWLNamedIndividual> owlSgInds =
				 * ontomanager.getClassIndividuals(NamedClasses.SCALINGGROUP,
				 * this.cdm.getReasoner(), false);
				 * 
				 * for (OWLNamedIndividual owlSgInd : owlSgInds) { String owlSgIndName =
				 * owlSgInd.getIRI().getShortForm(); Set<OWLNamedIndividual> owlScInds =
				 * ontomanager.getOpValuesPerIndividual(owlSgIndName,
				 * NamedObjectProp.HASSCALINGCRITERIA, this.cdm.getReasoner(), false);
				 * ScalingCriteriaRepository scrDB = new
				 * ScalingCriteriaRepository(this.ontomanager); for (OWLNamedIndividual owlScInd
				 * : owlScInds) { String scIndName = owlScInd.getIRI().getShortForm();
				 * ScalingCriteriaDto scDto = scrDB.getScalingCriteria(scIndName,
				 * this.cdm.getReasoner()); System.out.println(scDto.getNsMonitoringParamRef());
				 * }
				 * 
				 * System.out.println(owlScInds.size()); }
				 */

				// response.getJsondata().getData().setScalingGroups(this.sgDB.getScalingGroups(this.cdm.getReasoner()));
				this.sgDB = new ScalingGroupRepository(ontomanager);
				response.getJsondata().getData().setScalingGroups(this.sgDB.getScalingGroups());
				this.hgDB = new HealingGroupRepository(ontomanager);
				response.getJsondata().getData().setHealingGroups(this.hgDB.getHealingGroups());

				response.setCreated(true);
				response.setMessage("Goal refined succesfully!");

			} catch (InterruptedException e) {
				response.setMessage("ERROR: Goal was not refined! Problems found when executing the Planning System!");
				e.printStackTrace();
				return response;
			}
		} else {
			response.setMessage("The GoalPolicyManagementModule was not started!");
		}

		return response;
	}

//	public ListResponse listPlannerData(String className) {
//		ListResponse response = new ListResponse();
//		this.cdm.start();
//		
//		if (status) {
//			ArrayList<IndividualDto> listMetrics = new ArrayList<IndividualDto>();			
//			Set<OWLNamedIndividual> individuals = this.ontomanager.getClassIndividuals(className, this.cdm.getReasoner(), true);
//			for (OWLNamedIndividual ind : individuals) {
//				IndividualDto indDto = new IndividualDto();
//				indDto.setName(ind.getIRI().getShortForm());
//				listMetrics.add(indDto);
//			}
//			response.setRecovered(true);
//			response.getJsondata().setData(listMetrics);
//			response.setMessage("Data recoveredy succesfully!");
//		} else {
//			response.setMessage("The PlannerManagementModule was not started!");
//		}
//		
//		return response;		
//	}

}
