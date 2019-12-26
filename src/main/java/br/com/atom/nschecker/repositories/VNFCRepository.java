package br.com.atom.nschecker.repositories;

import org.semanticweb.owlapi.model.OWLNamedIndividual;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.VNFC;
import br.com.atom.nschecker.util.NamedClasses;
import br.com.atom.nschecker.util.NamedNetFunction;
import br.com.atom.nschecker.util.NamedObjectProp;

public class VNFCRepository {

	protected OntologyManager ontomanager;
	protected CPURepository cpu;
	protected MemoryRepository mem;
	protected StorageRepository sto;
	protected InterfaceRepository intf;

	public VNFCRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.cpu = new CPURepository(ontomanager);
		this.mem = new MemoryRepository(ontomanager);
		this.sto = new StorageRepository(ontomanager);
		this.intf = new InterfaceRepository(ontomanager);
	}
	
	public String createVNFCIndividual(VNFC vnfc, boolean temporarily) {
		String vnfcIndName = IndividualUtil.processNameForIndividual(vnfc.getName());
		String nameCls = "";

		switch (vnfc.getNetFunction()) {
		case NamedNetFunction.FIREWALL:
			nameCls = NamedClasses.VNFCFIREWALL;
			break;
		case NamedNetFunction.NAT:
			nameCls = NamedClasses.VNFCNAT;
			break;
		case NamedNetFunction.INTRUSIONDETECTION:
			nameCls = NamedClasses.VNFCINTRUSIONDETECTION;
			break;
		case NamedNetFunction.L2SWITCH:
			nameCls = NamedClasses.VNFCL2SWITCH;
			break;
		case NamedNetFunction.L3SWITCH:
			nameCls = NamedClasses.VNFCL3SWITCH;
			break;
		case NamedNetFunction.LOADBALANCE:
			nameCls = NamedClasses.VNFCLOADBALANCE;
			break;
		case NamedNetFunction.WANOPTIMIZATION:
			nameCls = NamedClasses.VNFCWANOPTIMIZATION;
			break;
		case NamedNetFunction.DPI:
			nameCls = NamedClasses.VNFCDPI;
			break;
		}
		
		OWLNamedIndividual ind = null;
		
		if (temporarily) {
			nameCls = nameCls + "FromNSRequest";
			ind = ontomanager.createIndividual(vnfcIndName, nameCls);
		} else {
			ind = ontomanager.createIndividual(vnfcIndName, nameCls);
		}		

		// TODO: Comment
		ontomanager.makeDifferentFromOtherIndividualsWithoutReasoner(ind, nameCls);
		
		// Creating Interface
		this.intf.createInterfaces(vnfcIndName, vnfc.getNumInterfaces());

		String method_implements = "";
		if (temporarily) {
			method_implements = NamedObjectProp.REQUESTEDBYNSREQTOBEIMPLEMENTEDBY;			
		} else {
			method_implements = NamedObjectProp.IMPLEMENTEDBY;			
		}		
		
		boolean hasGuest = false;
		String nfviNodeIndName = "";
		if (vnfc.getGuestNode() != null) {
			hasGuest = true;
			nfviNodeIndName = IndividualUtil.processNameForIndividual(vnfc.getGuestNode().getName());
			ontomanager.createObjectPropertyAssertionAxiom(vnfcIndName, nfviNodeIndName, method_implements);
			/*if (temporarily) {
				nfvinodesFromNSRequest.add(nfviNodeIndName);
			}*/			
		}
		
		// Creating CPU
		int numVCPUs = vnfc.getCpu().getNumCores();
		for (int i=0;i<numVCPUs;i++) {
			String cpuIndName = this.cpu.createVCPUIndividual(vnfc.getName(),i);
			ontomanager.createObjectPropertyAssertionAxiom(vnfcIndName, cpuIndName, NamedObjectProp.HASCOMPONENT);
			if (hasGuest) {
				ontomanager.createObjectPropertyAssertionAxiom(nfviNodeIndName, cpuIndName, NamedObjectProp.ALLOCATES);
			}
		}		
		// Creating Memory
		int numVMems = (int) vnfc.getMemory().getSize();
		for (int i=0;i<numVMems;i++) {
			String memIndName = this.mem.createVMemoryIndividual(vnfc.getName(),i);
			ontomanager.createObjectPropertyAssertionAxiom(vnfcIndName, memIndName, NamedObjectProp.HASCOMPONENT);
			if (hasGuest) {
				ontomanager.createObjectPropertyAssertionAxiom(nfviNodeIndName, memIndName, NamedObjectProp.ALLOCATES);
			}
		}
		// Creating Storage
		/*int numVStos = (int) vnfc.getStorage().getSize()/10;
		for (int i=0;i<numVStos;i++) {
			String stoIndName = nfvimanager.createVStorageIndividual(vnfc.getName(),i);
			ontomanager.createObjectPropertyAssertionAxiom(vnfcIndName, stoIndName, NamedObjectProp.HASCOMPONENT);
			if (hasGuest && !temporarily) {
				ontomanager.createObjectPropertyAssertionAxiom(nfviNodeIndName, stoIndName, method_allocates);
			}
		}*/
		// Creating Storage
		String stoIndName = this.sto.createVStorageIndividual(vnfc.getName(),1);
		ontomanager.createObjectPropertyAssertionAxiom(vnfcIndName, stoIndName, NamedObjectProp.HASCOMPONENT);		
		if (hasGuest) {
			ontomanager.createObjectPropertyAssertionAxiom(nfviNodeIndName, stoIndName, NamedObjectProp.ALLOCATES);
		}

		return vnfcIndName;

	}
	
}
