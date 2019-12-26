package br.com.atom.nschecker.repositories;

import org.semanticweb.owlapi.model.OWLDataFactory;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.Storage;
import br.com.atom.nschecker.util.NamedClasses;
import br.com.atom.nschecker.util.NamedDataProp;

public class StorageRepository {

	protected OntologyManager ontomanager;
	protected OWLDataFactory factory;

	public StorageRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.factory = ontomanager.getFactory();
	}
	
	public String createStorageIndividual(Storage sto) {
		// String stoIndName =
		// IndividualUtil.processNameForIndividual(sto.getName() + "-" +
		// UUID.randomUUID());
		String stoIndName = IndividualUtil.processNameForIndividual(sto.getName());
		ontomanager.createIndividual(stoIndName, NamedClasses.STORAGE);
		// ontomanager.createDataPropertyAssertionAxiom(stoIndName,
		// factory.getOWLLiteral(OntoNFVUtil.NFV_IRI + "#" + stoIndName),
		// NamedDataProp.ID);
		// ontomanager.createDataPropertyAssertionAxiom(stoIndName,
		// factory.getOWLLiteral(sto.getName()), NamedDataProp.HASNAME);
		ontomanager.createDataPropertyAssertionAxiom(stoIndName, factory.getOWLLiteral(sto.getSize()),
				NamedDataProp.HASSTORAGESIZE);
		ontomanager.createDataPropertyAssertionAxiom(stoIndName, factory.getOWLLiteral(sto.getAvailableSize()),
				NamedDataProp.HASAVAILABLESTORAGESIZE);
		return stoIndName;
	}
	
	public String createVStorageIndividual(String vnfcName, int index) {
		//String name = vnfcName + "-disk10gb-" + index; 
		String name = vnfcName + "-disk-" + index;
		String vStoIndName = IndividualUtil.processNameForIndividual(name);
		ontomanager.createIndividual(vStoIndName, NamedClasses.VSTORAGE);
		return vStoIndName;
	}
	
}
