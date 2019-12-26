package br.com.atom.nschecker.repositories;

import org.semanticweb.owlapi.model.OWLDataFactory;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.InterPoPSwitch;
import br.com.atom.nschecker.util.NamedClasses;
import br.com.atom.nschecker.util.NamedDataProp;

public class InterPoPSwitchRepository {

	protected OntologyManager ontomanager;
	protected OWLDataFactory factory;

	public InterPoPSwitchRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.factory = ontomanager.getFactory();
	}
	
	public String createInterPoPSwitchIndividual(InterPoPSwitch link) {
		String linkIndName = IndividualUtil.processNameForIndividual(link.getName());
		ontomanager.createIndividual(linkIndName, NamedClasses.INTERPOPSWITCH);
		ontomanager.createDataPropertyAssertionAxiom(linkIndName, factory.getOWLLiteral(link.getCapacity()),
				NamedDataProp.HASCAPACITY);
		ontomanager.createDataPropertyAssertionAxiom(linkIndName, factory.getOWLLiteral(link.getAvailableCapacity()),
				NamedDataProp.HASAVAILABLECAPACITY);
		ontomanager.createDataPropertyAssertionAxiom(linkIndName, factory.getOWLLiteral(link.getVxlanId() + ""),
				NamedDataProp.HASVXLANID);
		return linkIndName;
	}
	
}
