package br.com.atom.nschecker.repositories;

import org.semanticweb.owlapi.model.OWLDataFactory;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.Node;
import br.com.atom.nschecker.util.NamedClasses;
import br.com.atom.nschecker.util.NamedObjectProp;

public class NetworkNodeRepository {

	protected OntologyManager ontomanager;
	protected OWLDataFactory factory;
	protected CPURepository cpu;
	protected MemoryRepository mem;
	protected StorageRepository sto;
	protected InterfaceRepository intf;	

	public NetworkNodeRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;	
		this.factory = ontomanager.getFactory();
		this.cpu = new CPURepository(ontomanager);
		this.mem = new MemoryRepository(ontomanager);
		this.sto = new StorageRepository(ontomanager);
		this.intf = new InterfaceRepository(ontomanager);		
	}
	
	public void createNetworkNodeIndividual(Node netnode) {
		String netnodeIndName = IndividualUtil.processNameForIndividual(netnode.getName());

		ontomanager.createIndividual(netnodeIndName, NamedClasses.NODE);		

		// Creating CPU
		String cpuIndName = this.cpu.createCPUIndividual(netnode.getCpu());
		ontomanager.createObjectPropertyAssertionAxiom(netnodeIndName, cpuIndName, NamedObjectProp.HASCOMPONENT);
		// Creating Memory
		String memIndName = this.mem.createMemoryIndividual(netnode.getMemory());
		ontomanager.createObjectPropertyAssertionAxiom(netnodeIndName, memIndName, NamedObjectProp.HASCOMPONENT);
		// Creating Storage
		String stoIndName = this.sto.createStorageIndividual(netnode.getStorage());
		ontomanager.createObjectPropertyAssertionAxiom(netnodeIndName, stoIndName, NamedObjectProp.HASCOMPONENT);
		// Creating Switching Matrix
		// String swtIndName =
		// this.createSwitchingMatrixIndividual(netnode.getSwitchingMatrix());
		// ontomanager.createObjectPropertyAssertionAxiom(netnodeIndName,
		// swtIndName, NamedObjectProp.HASCOMPONENT);

		// Creating Interface
		this.intf.createInterfaces(netnodeIndName, netnode.getNumInterfaces());

		// Contains
		String nfvipopIndName = IndividualUtil.processNameForIndividual(netnode.getNfvipop().getName());
		ontomanager.createObjectPropertyAssertionAxiom(nfvipopIndName, netnodeIndName, NamedObjectProp.CONTAINS);
	}
	
}
