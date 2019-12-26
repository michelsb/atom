package br.com.atom.nsplanner.modules;

import java.util.ArrayList;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.springframework.stereotype.Service;

import br.com.atom.common.dtos.IndividualDto;
import br.com.atom.common.owlmanager.ConflictDetectionModule;
import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.responses.ConsistencyResponse;
import br.com.atom.common.responses.ListResponse;
import br.com.atom.nsplanner.classes.Goal;
import br.com.atom.nsplanner.classes.NS;
import br.com.atom.nsplanner.classes.VNF;
import br.com.atom.nsplanner.dtos.GoalDto;
import br.com.atom.nsplanner.dtos.HighLevelPolicyDto;
import br.com.atom.nsplanner.repositories.GoalRepository;
import br.com.atom.nsplanner.repositories.NSRepository;
import br.com.atom.nsplanner.repositories.VNFRepository;
import br.com.atom.nsplanner.responses.GoalResponse;
import br.com.atom.nsplanner.responses.HighLevelPolicyResponse;
import br.com.atom.nsplanner.util.OntoPlannerUtil;

@Service
public class GoalPolicyManagementModule {

	protected OntologyManager ontomanager;
	protected GoalRepository goalDB;
	//protected ScalingGroupRepository sgDB;
	//protected HealingGroupRepository hgDB;
	protected NSRepository nsDB;
	protected VNFRepository vnfDB;
	protected ConflictDetectionModule cdm;
	protected boolean status;
	
	public GoalPolicyManagementModule() {
		this.ontomanager = new OntologyManager();
		this.status = false;
	}
	
	public void start() {
		this.ontomanager.startOntologyProcessing(OntoPlannerUtil.ONTOFILE, OntoPlannerUtil.NFV_IRI);		
		this.cdm = new ConflictDetectionModule(ontomanager);		
		// instantiate all repositories
		this.goalDB = new GoalRepository(ontomanager);
		//this.sgDB = new ScalingGroupRepository(ontomanager);
		//this.hgDB = new HealingGroupRepository(ontomanager);
		this.nsDB = new NSRepository(ontomanager);
		this.vnfDB = new VNFRepository(ontomanager);
		this.status = true;
	}
	
	public void reload() {
		this.cdm = new ConflictDetectionModule(ontomanager);		
		// instantiate all repositories
		this.goalDB = new GoalRepository(ontomanager);
		//this.sgDB = new ScalingGroupRepository(ontomanager);
		//this.hgDB = new HealingGroupRepository(ontomanager);
		this.nsDB = new NSRepository(ontomanager);
		this.vnfDB = new VNFRepository(ontomanager);
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
		//this.sgDB = null;
		//this.hgDB = null;
		this.nsDB = null;
		this.vnfDB = null;
		this.status = false;
	}
	
	public void instantiateGoal (GoalDto goalDto) {
		
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
	
	public GoalResponse createGoal(GoalDto goalDto) {
		
		GoalResponse response = new GoalResponse();
		
		if (status) {
			
			this.instantiateGoal(goalDto);
			
			this.cdm.start();
			ConsistencyResponse consResponse = this.cdm.testOntoConsistency();			
			if (consResponse.isConsistent()) {
				response.setCreated(true);
				response.setMessage("Goal created succesfully!");
				response.setConsistency(consResponse);
			} else {
				response.setMessage("ERROR: Goal was not created! Updates made Onto-Planner inconsistent!");
				response.setConsistency(consResponse);
			}				
		} else {
			response.setMessage("The GoalPolicyManagementModule was not started!");
		}
		
		return response;
	
	}
	
	public HighLevelPolicyResponse createGoals(HighLevelPolicyDto polDto) {
		
		HighLevelPolicyResponse response = new HighLevelPolicyResponse();
		
		if (status) {
			
			for (GoalDto goalDto : polDto.getGoals()) {
				this.instantiateGoal(goalDto);
			}			
			
			this.cdm.start();
			ConsistencyResponse consResponse = this.cdm.testOntoConsistency();			
			if (consResponse.isConsistent()) {
				response.setCreated(true);
				response.setMessage("Goals created succesfully!");
				response.setConsistency(consResponse);
			} else {
				response.setMessage("ERROR: Goals were not created! Updates made Onto-Planner inconsistent!");
				response.setConsistency(consResponse);
			}				
		} else {
			response.setMessage("The GoalPolicyManagementModule was not started!");
		}
		
		return response;
	
	}
	
	public GoalResponse removeGoal(GoalDto goalDto) {
		
		GoalResponse response = new GoalResponse();
		
		if (status) {
			
			Goal goal = new Goal();
			goal.setName(goalDto.getName());
			this.goalDB.removeGoalIndividual(goal);
			
			NS ns = new NS();
			ns.setName(goalDto.getName() + " NS " + goalDto.getNsrId());
			this.nsDB.removeNSIndividual(ns);
			
			ArrayList<String> vnfIndNames = new ArrayList<String>(); 
			for (String index : goalDto.getVnfMemberIndexes()) {
				VNF vnf = new VNF();
				vnf.setName(goalDto.getName() + " VNF " + index);
				String vnfIndName = this.vnfDB.removeVNFIndividual(vnf);
				vnfIndNames.add(vnfIndName);
			}
			
			this.cdm.start();
			ConsistencyResponse consResponse = this.cdm.testOntoConsistency();			
			if (consResponse.isConsistent()) {
				response.setCreated(true);
				response.setMessage("Goal removed succesfully!");
				response.setConsistency(consResponse);
			} else {
				response.setMessage("ERROR: Goal was not removed! Updates made Onto-Planner inconsistent!");
				response.setConsistency(consResponse);
			}				
		} else {
			response.setMessage("The GoalPolicyManagementModule was not started!");
		}
		
		return response;
	
	}
	
	public ListResponse listGoalData(String className) {
		ListResponse response = new ListResponse();
		
		if (status) {
			this.cdm.start();
			ArrayList<IndividualDto> listMetrics = new ArrayList<IndividualDto>();			
			Set<OWLNamedIndividual> individuals = this.ontomanager.getClassIndividualsWithoutReasoner(className);
			for (OWLNamedIndividual ind : individuals) {
				IndividualDto indDto = new IndividualDto();
				indDto.setName(ind.getIRI().getShortForm());
				listMetrics.add(indDto);
			}
			response.setRecovered(true);
			response.getJsondata().setData(listMetrics);
			response.setMessage("Data recoveredy succesfully!");
		} else {
			response.setMessage("The GoalPolicyManagementModule was not started!");
		}
		
		return response;		
	}
	
}
