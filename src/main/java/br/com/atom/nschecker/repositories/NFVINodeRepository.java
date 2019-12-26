package br.com.atom.nschecker.repositories;

import org.semanticweb.owlapi.model.OWLDataFactory;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.Node;
import br.com.atom.nschecker.util.NamedObjectProp;

public class NFVINodeRepository {

	protected OntologyManager ontomanager;
	protected OWLDataFactory factory;
	protected CPURepository cpu;
	protected MemoryRepository mem;
	protected StorageRepository sto;
	protected InterfaceRepository intf;
	protected ResourceUsageRestRepository rurest;

	public NFVINodeRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;	
		this.factory = ontomanager.getFactory();
		this.cpu = new CPURepository(ontomanager);
		this.mem = new MemoryRepository(ontomanager);
		this.sto = new StorageRepository(ontomanager);
		this.intf = new InterfaceRepository(ontomanager);
		this.rurest = new ResourceUsageRestRepository(ontomanager);
	}
	
	public void createNFVINodeIndividual(Node nfvinode) {
		String nfvinodeIndName = IndividualUtil.processNameForIndividual(nfvinode.getName());		
		
		String nameClsNUM = this.rurest.createNFVINodeNumRestriction(nfvinode.getMaxNumVNFs());
		String nameClsCPU = this.rurest.createNFVINodeCPURestriction(nfvinode.getMaxNumVCPUs());
		String nameClsMEM = this.rurest.createNFVINodeMemRestriction(nfvinode.getMaxNumVMems());				

		ontomanager.createIndividualSuperClasses(nfvinodeIndName, nameClsNUM, nameClsCPU, nameClsMEM);

		// Creating CPU
		String cpuIndName = this.cpu.createCPUIndividual(nfvinode.getCpu());
		ontomanager.createObjectPropertyAssertionAxiom(nfvinodeIndName, cpuIndName, NamedObjectProp.HASCOMPONENT);
		// Creating Memory
		String memIndName = this.mem.createMemoryIndividual(nfvinode.getMemory());
		ontomanager.createObjectPropertyAssertionAxiom(nfvinodeIndName, memIndName, NamedObjectProp.HASCOMPONENT);
		// Creating Storage
		String stoIndName = this.sto.createStorageIndividual(nfvinode.getStorage());
		ontomanager.createObjectPropertyAssertionAxiom(nfvinodeIndName, stoIndName, NamedObjectProp.HASCOMPONENT);

		// Creating Interface
		this.intf.createInterfaces(nfvinodeIndName, nfvinode.getNumInterfaces());

		// Contains
		String nfvipopIndName = IndividualUtil.processNameForIndividual(nfvinode.getNfvipop().getName());
		ontomanager.createObjectPropertyAssertionAxiom(nfvipopIndName, nfvinodeIndName, NamedObjectProp.CONTAINS);
	}
	
}
