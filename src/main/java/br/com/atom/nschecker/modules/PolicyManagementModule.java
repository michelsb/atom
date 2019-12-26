package br.com.atom.nschecker.modules;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import br.com.atom.common.owlmanager.ConflictDetectionModule;
import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.responses.ConsistencyResponse;
import br.com.atom.nschecker.classes.VNFC;
import br.com.atom.nschecker.dtos.AffinityRestDto;
import br.com.atom.nschecker.dtos.AntiAffinityRestDto;
import br.com.atom.nschecker.dtos.RelationshipDto;
import br.com.atom.nschecker.repositories.LocationRestRepository;
import br.com.atom.nschecker.repositories.NetFuncPrecRepository;
import br.com.atom.nschecker.responses.AffinityRestResponse;
import br.com.atom.nschecker.responses.AntiAffinityRestResponse;
import br.com.atom.nschecker.responses.NetFuncPrecRestResponse;
import br.com.atom.nschecker.responses.PlacementRestResponse;
import br.com.atom.nschecker.util.OntoNFVUtil;

@Service
public class PolicyManagementModule {

	protected OntologyManager ontomanager;		
	protected NetFuncPrecRepository nfr;
	protected LocationRestRepository lrr;
	protected ConflictDetectionModule cdm;
	protected boolean status;

	public PolicyManagementModule() {
		this.ontomanager = new OntologyManager();
		this.status = false;
	}

	public void start() {
		this.ontomanager.startOntologyProcessing(OntoNFVUtil.ONTOFILE, OntoNFVUtil.NFV_IRI);		
		this.cdm = new ConflictDetectionModule(ontomanager);		
		this.nfr = new NetFuncPrecRepository(ontomanager);
		this.lrr = new LocationRestRepository(ontomanager);
		this.status = true;
	}
	
	public void reload() {
		//this.cdm.close();
		this.cdm = new ConflictDetectionModule(ontomanager);		
		this.nfr = new NetFuncPrecRepository(ontomanager);
		this.lrr = new LocationRestRepository(ontomanager);
		this.status = true;
	}
	
	public void saveUpdates() {
		this.ontomanager.saveOntologyFile();
	}
	
	public void stop() {
		this.ontomanager.close();		
		//this.cdm.close(); 
		this.cdm = null;
		this.nfr = null;
		this.lrr = null;
		this.status = false;
	}

	public NetFuncPrecRestResponse createNetFuncPrecRest(ArrayList<RelationshipDto> netFuncPrec) {		
		NetFuncPrecRestResponse netFunPrecResponse = new NetFuncPrecRestResponse();		
		if (status) {
			boolean created = false;
			for (RelationshipDto rest : netFuncPrec) {				
				this.nfr.createNetFuncPrecRestriction(rest.getElement1(), rest.getElement2());
				created = true;
			}			
			if (created) {
				this.cdm.start();
				ConsistencyResponse consResponse = this.cdm.testOntoConsistency();
				
				if (consResponse.isConsistent()) {
					netFunPrecResponse.setCreated(true);
					netFunPrecResponse.setMessage("Network Function Precedence-type restrictions created succesfully!");
					netFunPrecResponse.setConsistency(consResponse);
				} else {
					netFunPrecResponse.setMessage("ERROR: Network Function Precedence-type restrictions was not created! Updates made Onto-NFV inconsistent!");
					netFunPrecResponse.setConsistency(consResponse);
				}
			} else {
				netFunPrecResponse.setCreated(true);
				netFunPrecResponse.setMessage("There is nothing to create!");
			}	
		} else {
			netFunPrecResponse.setMessage("The service was not started!");
		}
		return netFunPrecResponse;
	}
	
	public PlacementRestResponse createPlacementRest(ArrayList<VNFC> listVNFCs) {		
		PlacementRestResponse placementResponse = new PlacementRestResponse();		
		if (status) {
			boolean created = false;
			/*for (VNFC vnfc : listVNFCs) { 
				this.lrr.createPlacementRestriction(vnfc);
				created = true;
			}*/
			if (listVNFCs.size()>1) {
				this.lrr.createPlacementRestrictions(listVNFCs);
				created = true;
			} else {
				if (listVNFCs.size()==1) {
					this.lrr.createPlacementRestriction(listVNFCs.get(0));
					created = true;
				}
			}
			if (created) {
				this.cdm.start();
				ConsistencyResponse consResponse = this.cdm.testOntoConsistency();				
				if (consResponse.isConsistent()) {
					placementResponse.setCreated(true);
					placementResponse.setMessage("Placement-type restrictions created succesfully!");
					placementResponse.setConsistency(consResponse);
				} else {
					placementResponse.setMessage("ERROR: Placement-type restrictions was not created! Updates made Onto-NFV inconsistent!");
					placementResponse.setConsistency(consResponse);
				}
			} else {
				placementResponse.setCreated(true);
				placementResponse.setMessage("There is nothing to create!");
			}	
		} else {
			placementResponse.setMessage("The service was not started!");
		}
		return placementResponse;
	}
	
	public AffinityRestResponse createAffinityRestToVNFCs(AffinityRestDto affRest) {		
		AffinityRestResponse affResponse = new AffinityRestResponse();		
		if (status) {				
			String nameVNFC1 = "";
			String nameVNFC2 = "";
			boolean created = false;
			for (RelationshipDto hasSameNFVINode : affRest.getHasSameNFVINode()) {
				nameVNFC1 = hasSameNFVINode.getElement1();
				nameVNFC2 = hasSameNFVINode.getElement2();
				this.lrr.createSameNodeRestrictionToVNFCs(nameVNFC1, nameVNFC2);
				created = true;
			}			
			for (RelationshipDto hasSameNFVIPoP : affRest.getHasSameNFVIPoP()) {
				nameVNFC1 = hasSameNFVIPoP.getElement1();
				nameVNFC2 = hasSameNFVIPoP.getElement2();
				this.lrr.createSameNFVIPoPRestrictionToVNFCs(nameVNFC1, nameVNFC2);
				created = true;
			}
			for (RelationshipDto hasSameNFVI : affRest.getHasSameNFVI()) {
				nameVNFC1 = hasSameNFVI.getElement1();
				nameVNFC2 = hasSameNFVI.getElement2();
				this.lrr.createSameNFVIPoPRestrictionToVNFCs(nameVNFC1, nameVNFC2);
				created = true;
			}			
			if (created) {
				this.cdm.start();
				ConsistencyResponse consResponse = this.cdm.testOntoConsistency();				
				if (consResponse.isConsistent()) {
					affResponse.setCreated(true);
					affResponse.setMessage("Affinity-type restrictions created succesfully!");
					affResponse.setConsistency(consResponse);
				} else {
					affResponse.setMessage("ERROR: Affinity-type restrictions restrictions was not created! Updates made Onto-NFV inconsistent!");
					affResponse.setConsistency(consResponse);
				}
			} else {
				affResponse.setCreated(true);
				affResponse.setMessage("There is nothing to create!");
			}			
		} else {
			affResponse.setMessage("The service was not started!");
		}
		return affResponse;
	}
	
	public AntiAffinityRestResponse createAntiAffinityRestToVNFCs(AntiAffinityRestDto antiAffRest) {		
		AntiAffinityRestResponse antiAffResponse = new AntiAffinityRestResponse();		
		if (status) {				
			String nameVNFC1 = "";
			String nameVNFC2 = "";			
			boolean created = false;
			for (RelationshipDto hasNotSameNFVINode : antiAffRest.getHasNotSameNFVINode()) {
				nameVNFC1 = hasNotSameNFVINode.getElement1();
				nameVNFC2 = hasNotSameNFVINode.getElement2();
				this.lrr.createNotSameNodeRestrictionToVNFCs(nameVNFC1, nameVNFC2);
				created = true;
			}			
			for (RelationshipDto hasNotSameNFVIPoP : antiAffRest.getHasNotSameNFVIPoP()) {
				nameVNFC1 = hasNotSameNFVIPoP.getElement1();
				nameVNFC2 = hasNotSameNFVIPoP.getElement2();
				this.lrr.createNotSameNFVIPoPRestrictionToVNFCs(nameVNFC1, nameVNFC2);
				created = true;
			}
			for (RelationshipDto hasNotSameNFVI : antiAffRest.getHasNotSameNFVI()) {
				nameVNFC1 = hasNotSameNFVI.getElement1();
				nameVNFC2 = hasNotSameNFVI.getElement2();
				this.lrr.createNotSameNFVIRestrictionToVNFCs(nameVNFC1, nameVNFC2);
				created = true;
			}			
			if (created) {
				this.cdm.start();
				ConsistencyResponse consResponse = this.cdm.testOntoConsistency(); 				
				if (consResponse.isConsistent()) {
					antiAffResponse.setCreated(true);
					antiAffResponse.setMessage("Anti-Affinity-type restrictions created succesfully!");
					antiAffResponse.setConsistency(consResponse);
				} else {
					antiAffResponse.setMessage("ERROR: Anti-Affinity-type restrictions was not created! Updates made Onto-NFV inconsistent!");
					antiAffResponse.setConsistency(consResponse);
				}
			} else {
				antiAffResponse.setCreated(true);
				antiAffResponse.setMessage("There is nothing to create!");
			}	
		} else {
			antiAffResponse.setMessage("The service was not started!");
		}
		return antiAffResponse;
	}
	
	public AffinityRestResponse createAffinityRestToNetFunctions(AffinityRestDto affRest) {		
		AffinityRestResponse affResponse = new AffinityRestResponse();		
		if (status) {				
			String netFunction1 = "";
			String netFunction2 = "";			
			boolean created = false;
			for (RelationshipDto hasSameNFVINode : affRest.getHasSameNFVINode()) {
				netFunction1 = hasSameNFVINode.getElement1();
				netFunction2 = hasSameNFVINode.getElement2();
				this.lrr.createSameNodeRestrictionToNetFunctions(netFunction1, netFunction2);
				created = true;
			}			
			for (RelationshipDto hasSameNFVIPoP : affRest.getHasSameNFVIPoP()) {
				netFunction1 = hasSameNFVIPoP.getElement1();
				netFunction2 = hasSameNFVIPoP.getElement2();
				this.lrr.createSameNFVIPoPRestrictionToNetFunctions(netFunction1, netFunction2);
				created = true;
			}
			for (RelationshipDto hasSameNFVI : affRest.getHasSameNFVI()) {
				netFunction1 = hasSameNFVI.getElement1();
				netFunction2 = hasSameNFVI.getElement2();
				this.lrr.createSameNFVIRestrictionToNetFunctions(netFunction1, netFunction2);
				created = true;
			}			
			if (created) {
				this.cdm.start();
				ConsistencyResponse consResponse = this.cdm.testOntoConsistency();				
				if (consResponse.isConsistent()) {
					affResponse.setCreated(true);
					affResponse.setMessage("Affinity-type restrictions created succesfully!");
					affResponse.setConsistency(consResponse);
				} else {
					affResponse.setMessage("ERROR: Affinity-type restrictions restrictions was not created! Updates made Onto-NFV inconsistent!");
					affResponse.setConsistency(consResponse);
				}
			} else {
				affResponse.setCreated(true);
				affResponse.setMessage("There is nothing to create!");
			}	
		} else {
			affResponse.setMessage("The service was not started!");
		}
		return affResponse;
	}
	
	public AntiAffinityRestResponse createAntiAffinityRestToNetFunctions(AntiAffinityRestDto antiAffRest) {		
		AntiAffinityRestResponse antiAffResponse = new AntiAffinityRestResponse();		
		if (status) {				
			String netFunction1 = "";
			String netFunction2 = "";
			boolean created = false;
			for (RelationshipDto hasNotSameNFVINode : antiAffRest.getHasNotSameNFVINode()) {
				netFunction1 = hasNotSameNFVINode.getElement1();
				netFunction2 = hasNotSameNFVINode.getElement2();
				this.lrr.createNotSameNodeRestrictionToNetFunctions(netFunction1, netFunction2);
				created = true;
			}			
			for (RelationshipDto hasNotSameNFVIPoP : antiAffRest.getHasNotSameNFVIPoP()) {
				netFunction1 = hasNotSameNFVIPoP.getElement1();
				netFunction2 = hasNotSameNFVIPoP.getElement2();
				this.lrr.createNotSameNFVIPoPRestrictionToNetFunctions(netFunction1, netFunction2);
				created = true;
			}
			for (RelationshipDto hasNotSameNFVI : antiAffRest.getHasNotSameNFVI()) {
				netFunction1 = hasNotSameNFVI.getElement1();
				netFunction2 = hasNotSameNFVI.getElement2();
				this.lrr.createNotSameNFVIRestrictionToNetFunctions(netFunction1, netFunction2);
				created = true;
			}
			if (created) {
				this.cdm.start();
				ConsistencyResponse consResponse = this.cdm.testOntoConsistency();				
				if (consResponse.isConsistent()) {
					antiAffResponse.setCreated(true);
					antiAffResponse.setMessage("Anti-Affinity-type restrictions created succesfully!");
					antiAffResponse.setConsistency(consResponse);
				} else {
					antiAffResponse.setMessage("ERROR: Anti-Affinity-type restrictions was not created! Updates made Onto-NFV inconsistent!");
					antiAffResponse.setConsistency(consResponse);
				}
			} else {
				antiAffResponse.setCreated(true);
				antiAffResponse.setMessage("There is nothing to create!");
			}	
		} else {
			antiAffResponse.setMessage("The service was not started!");
		}
		return antiAffResponse;
	}

	public OntologyManager getOntomanager() {
		return ontomanager;
	}

	public void setOntomanager(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
	}	
	
}
