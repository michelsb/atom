package br.com.atom.nsplanner.repositories;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nsplanner.classes.NS;
import br.com.atom.nsplanner.util.NamedClasses;
import br.com.atom.nsplanner.util.NamedDataProp;

public class NSRepository {

	protected OntologyManager ontomanager;	
	
	public NSRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;	
	}
	
	public String createNSIndividual(NS ns) {
		String nsIndName = IndividualUtil.processNameForIndividual(ns.getName());
		ontomanager.createIndividual(nsIndName, NamedClasses.NS);
		ontomanager.createDataPropertyAssertionAxiom(nsIndName, this.ontomanager.getFactory().getOWLLiteral(ns.getId()), NamedDataProp.ID);
		return nsIndName;
	}
	
	public String removeNSIndividual(NS ns) {
		String nsIndName = IndividualUtil.processNameForIndividual(ns.getName());
		ontomanager.removeIndividual(nsIndName);
		return nsIndName;
	}
	
}
