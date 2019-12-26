package br.com.atom.nschecker.repositories;

import org.semanticweb.owlapi.model.OWLDataFactory;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.CPU;
import br.com.atom.nschecker.util.NamedClasses;
import br.com.atom.nschecker.util.NamedDataProp;

public class CPURepository {

	protected OntologyManager ontomanager;
	protected OWLDataFactory factory;

	public CPURepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;	
		this.factory = ontomanager.getFactory();
	}
	
	public String createCPUIndividual(CPU cpu) {
		String cpuIndName = IndividualUtil.processNameForIndividual(cpu.getName());
		ontomanager.createIndividual(cpuIndName, NamedClasses.CPU);
		ontomanager.createDataPropertyAssertionAxiom(cpuIndName, factory.getOWLLiteral(cpu.getNumCores()),
				NamedDataProp.HASCORES);
		ontomanager.createDataPropertyAssertionAxiom(cpuIndName, factory.getOWLLiteral(cpu.getAvailableCores()),
				NamedDataProp.HASAVAILABLECORES);
		ontomanager.createDataPropertyAssertionAxiom(cpuIndName, factory.getOWLLiteral(cpu.getSpeed()),
				NamedDataProp.HASCPUSPEED);
		return cpuIndName;
	}
	
	public String createVCPUIndividual(String vnfcName, int index) {
		String name = vnfcName + "-core" + index; 
		String vCpuIndName = IndividualUtil.processNameForIndividual(name);
		ontomanager.createIndividual(vCpuIndName, NamedClasses.VCPU);
		//OWLNamedIndividual ind = ontomanager.createIndividual(vCpuIndName, NamedClasses.VCPU);
		//ontomanager.makeDifferentFromOtherIndividuals(ind, NamedClasses.VCPU, ontomanager.createOWLReasoner());
		return vCpuIndName;
	}
	
}
