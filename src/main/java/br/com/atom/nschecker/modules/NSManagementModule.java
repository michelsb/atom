package br.com.atom.nschecker.modules;

import org.springframework.stereotype.Service;

import br.com.atom.common.owlmanager.ConflictDetectionModule;
import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.responses.ConsistencyResponse;
import br.com.atom.nschecker.classes.VNF;
import br.com.atom.nschecker.classes.VNFC;
import br.com.atom.nschecker.classes.VNFFG;
import br.com.atom.nschecker.dtos.NetworkServiceDto;
import br.com.atom.nschecker.dtos.VNFDto;
import br.com.atom.nschecker.dtos.VirtConnBetweenVNFCsDto;
import br.com.atom.nschecker.dtos.VirtConnBetweenVNFsDto;
import br.com.atom.nschecker.repositories.VNFCRepository;
import br.com.atom.nschecker.repositories.VNFFGRepository;
import br.com.atom.nschecker.repositories.VNFRepository;
import br.com.atom.nschecker.repositories.VirtualConnectionRepository;
import br.com.atom.nschecker.responses.NSResponse;
import br.com.atom.nschecker.util.OntoNFVUtil;

@Service
public class NSManagementModule {

	protected OntologyManager ontomanager;		
	protected VNFFGRepository vnffgDB;
	protected VNFRepository vnfDB;
	protected VNFCRepository vnfcDB;
	protected VirtualConnectionRepository vcDB;
	protected ConflictDetectionModule cdm;
	protected boolean status;
	
	public NSManagementModule () {	
			this.ontomanager = new OntologyManager();
			this.status = false;		
	}		
	
	public void start() {
		this.ontomanager.startOntologyProcessing(OntoNFVUtil.ONTOFILE, OntoNFVUtil.NFV_IRI);		
		this.cdm = new ConflictDetectionModule(ontomanager);		
		this.vnffgDB = new VNFFGRepository(ontomanager);
		this.vnfDB = new VNFRepository(ontomanager);
		this.vnfcDB = new VNFCRepository(ontomanager);
		this.vcDB = new VirtualConnectionRepository(ontomanager);
		this.status = true;
	}
	
	public void saveUpdates() {
		this.ontomanager.saveOntologyFile();
	}
	
	public void stop() {
		this.ontomanager.close();		
		this.cdm = null;
		this.vnffgDB = null;
		this.vnfDB = null;
		this.vnfcDB = null;
		this.status = false;
	}
	
	public NSResponse createNetworkService(NetworkServiceDto networkService) {
		
		NSResponse nsResponse = new NSResponse();	
		
		if (status) {			
			VNFFG vnffg = new VNFFG();
			vnffg.setName(networkService.getName());
			this.vnffgDB.createVNFFGIndividual(vnffg);			
			
			for (VNFDto vnfDto : networkService.getVnfs()) {
				VNF vnf = new VNF();
				vnf.setName(vnfDto.getName());
				vnf.setVnffg(vnffg);
				String vnfIndName = this.vnfDB.createVNFIndividual(vnf);
				
				for (VNFC vnfc : vnfDto.getVnfcs()) {
					vnfc.setVnf(vnf);
					this.vnfcDB.createVNFCIndividual(vnfc, false);
					if (vnfc.getName().equals(vnfDto.getIngressPort().getNameVNFC()))
						vnfDto.setIngressVNFC(vnfc);
					if (vnfc.getName().equals(vnfDto.getEgressPort().getNameVNFC()))
						vnfDto.setEgressVNFC(vnfc);
				}
				
				for (VirtConnBetweenVNFCsDto connVNFC : vnfDto.getConnVNFCs()) {
					VNFC vnfc1 = null;
					int inteIndexVNFC1 = connVNFC.getInteIndexVNFC1();
					VNFC vnfc2 = null;
					int inteIndexVNFC2 = connVNFC.getInteIndexVNFC2();
					float speed = connVNFC.getSpeed();					
					for (VNFC vnfc : vnfDto.getVnfcs()) {
						if (vnfc.getName().equals(connVNFC.getNameVNFC1())) {
							vnfc1 = vnfc;
						} 
						if (vnfc.getName().equals(connVNFC.getNameVNFC2())) {
								vnfc2 = vnfc;
						}												
					}					
					if ((vnfc1 != null) && (vnfc2 != null)) {
						this.vcDB.createVirtualConnectionBetweenVNFCs(vnfc1, inteIndexVNFC1, vnfc2, inteIndexVNFC2, speed);
					} else {
						nsResponse.setMessage("ERROR: Network Service was not created! Misconfigurations in VNFC's connections!");
						return nsResponse;
					}
				}				
				String nameVNFC1 = vnfDto.getIngressPort().getNameVNFC();
				int inteIndexVNFC1 = vnfDto.getIngressPort().getInteIndexVNFC();
				String nameVNFC2 = vnfDto.getEgressPort().getNameVNFC();
				int inteIndexVNFC2 = vnfDto.getEgressPort().getInteIndexVNFC();				
				this.vnfDB.createPortsVNF(vnfIndName, nameVNFC1, inteIndexVNFC1, nameVNFC2, inteIndexVNFC2);				
			}			
			for (VirtConnBetweenVNFsDto connVNF : networkService.getConnVNFs()) {
				VNFC vnfc1 = null;
				int inteIndexVNFC1 = 0;
				VNFC vnfc2 = null;
				int inteIndexVNFC2 = 0;
				float speed = connVNF.getSpeed();				
				for (VNFDto vnfDto : networkService.getVnfs()) {
					if (vnfDto.getName().equals(connVNF.getNameVNF1())) {
						vnfc1 = vnfDto.getEgressVNFC();
						inteIndexVNFC1 = vnfDto.getEgressPort().getInteIndexVNFC();
					}
					if (vnfDto.getName().equals(connVNF.getNameVNF2())) {
						vnfc2 = vnfDto.getIngressVNFC();
						inteIndexVNFC2 = vnfDto.getIngressPort().getInteIndexVNFC();
					}	
				}	
				if ((vnfc1 != null) && (vnfc2 != null)) {
					this.vcDB.createVirtualConnectionBetweenVNFCs(vnfc1, inteIndexVNFC1, vnfc2, inteIndexVNFC2, speed);
				} else {
					nsResponse.setMessage("ERROR: Network Service was not created! Misconfigurations in VNF's connections!");
					return nsResponse;
				}
			}			
			this.cdm.start();
			ConsistencyResponse consResponse = this.cdm.testOntoConsistency();			
			if (consResponse.isConsistent()) {
				nsResponse.setCreated(true);
				nsResponse.setMessage("Network Service created succesfully!");
				nsResponse.setConsistency(consResponse);
			} else {
				nsResponse.setMessage("ERROR: Network Service was not created! Updates made Onto-NFV inconsistent!");
				nsResponse.setConsistency(consResponse);
			}			
		} else {			
			nsResponse.setMessage("The NSManagementModule was not started!");
		}
		
		return nsResponse;
	}
	
}
