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
import br.com.atom.nsplanner.classes.Threshold;
import br.com.atom.nsplanner.dtos.HealingThresholdDto;
import br.com.atom.nsplanner.dtos.HorizontalScalingThresholdDto;
import br.com.atom.nsplanner.dtos.LowLevelPolicyDto;
import br.com.atom.nsplanner.repositories.ThresholdRepository;
import br.com.atom.nsplanner.responses.LowLevelPolicyResponse;
import br.com.atom.nsplanner.util.OntoPlannerUtil;

@Service
public class ThresholdManagementModule {

	protected OntologyManager ontomanager;
	protected ThresholdRepository thrDB;
	protected ConflictDetectionModule cdm;
	protected boolean status;
	
	public ThresholdManagementModule() {
		this.ontomanager = new OntologyManager();
		this.status = false;
	}
	
	public void start() {
		this.ontomanager.startOntologyProcessing(OntoPlannerUtil.ONTOFILE, OntoPlannerUtil.NFV_IRI);		
		this.cdm = new ConflictDetectionModule(ontomanager);		
		// instantiate all repositories
		this.thrDB = new ThresholdRepository(ontomanager);
		this.status = true;
	}
	
	public void reload() {
		this.cdm = new ConflictDetectionModule(ontomanager);		
		// instantiate all repositories
		this.thrDB = new ThresholdRepository(ontomanager);
		this.status = true;
	}
	
	public void saveUpdates() {
		this.ontomanager.saveOntologyFile();
	}
	
	public void stop() {
		this.ontomanager.close();		
		this.cdm = null;
		// apply null to all repositories
		this.thrDB = null;
		this.status = false;
	}
	
	public LowLevelPolicyResponse createPolicy(LowLevelPolicyDto policy) {
		
		LowLevelPolicyResponse response = new LowLevelPolicyResponse();
		
		if (status) {
			String prefix = policy.getName();
			if (policy.getHealPols() != null) {
				for (HealingThresholdDto healPol : policy.getHealPols()) {
					String thrName = prefix + " " + healPol.getName();
					Threshold thr = new Threshold();
					thr.setName(thrName);
					String thrIndName = this.thrDB.createHealingThresholdIndividual(thr);
					this.thrDB.configureHealingThreshold(thrIndName, healPol.getAction(), healPol.getMetric(), 
							healPol.getLowThresholdValue(), healPol.getMediumThresholdValue(), healPol.getHighThresholdValue());
				}
			}
			if (policy.getHorScalePols() != null) {
				for (HorizontalScalingThresholdDto scalePol : policy.getHorScalePols()) {
					String thrName = prefix + " " + scalePol.getName();
					Threshold thr = new Threshold();
					thr.setName(thrName);
					String thrIndName = this.thrDB.createScalingThresholdIndividual(thr);
					this.thrDB.configureScalingThreshold(thrIndName, scalePol.getMetric(), 
							scalePol.getLowScaleInThresholdValue(), scalePol.getLowScaleOutThresholdValue(), 
							scalePol.getMediumScaleInThresholdValue(), scalePol.getMediumScaleOutThresholdValue(), 
							scalePol.getHighScaleInThresholdValue(), scalePol.getHighScaleOutThresholdValue());
				}
			}
			this.cdm.start();
			ConsistencyResponse consResponse = this.cdm.testOntoConsistency();			
			if (consResponse.isConsistent()) {
				response.setCreated(true);
				response.setMessage("Policy created succesfully!");
				response.setConsistency(consResponse);
			} else {
				response.setMessage("ERROR: Policy was not created! Updates made Onto-Planner inconsistent!");
				response.setConsistency(consResponse);
			}				
		} else {
			response.setMessage("The ThresholdManagementModule was not started!");
		}
		
		return response;
	
	}
	
	public LowLevelPolicyResponse removePolicy(LowLevelPolicyDto policy) {
		
		LowLevelPolicyResponse response = new LowLevelPolicyResponse();
		
		if (status) {
			String prefix = policy.getName();
			for (HealingThresholdDto healPol : policy.getHealPols()) {
				String thrName = prefix + " " + healPol.getName();
				Threshold thr = new Threshold();
				thr.setName(thrName);
				this.thrDB.removeHealingThresholdIndividual(thr);
			}
			for (HorizontalScalingThresholdDto scalePol : policy.getHorScalePols()) {
				String thrName = prefix + " " + scalePol.getName();
				Threshold thr = new Threshold();
				thr.setName(thrName);
				this.thrDB.removeScalingThresholdIndividual(thr);
			}			
			this.cdm.start();
			ConsistencyResponse consResponse = this.cdm.testOntoConsistency();			
			if (consResponse.isConsistent()) {
				response.setCreated(true);
				response.setMessage("Policy removed succesfully!");
				response.setConsistency(consResponse);
			} else {
				response.setMessage("ERROR: Policy was not removed! Updates made Onto-Planner inconsistent!");
				response.setConsistency(consResponse);
			}				
		} else {
			response.setMessage("The ThresholdManagementModule was not started!");
		}
		
		return response;
	
	}
	
	public ListResponse listThresholdData(String className) {
		ListResponse response = new ListResponse();
		this.cdm.start();
		
		if (status) {
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
			response.setMessage("The ThresholdManagementModule was not started!");
		}
		
		return response;		
	}
	
}
