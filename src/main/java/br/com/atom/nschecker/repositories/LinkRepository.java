package br.com.atom.nschecker.repositories;

import org.semanticweb.owlapi.model.OWLDataFactory;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.Link;
import br.com.atom.nschecker.classes.VirtualLink;
import br.com.atom.nschecker.util.NamedClasses;
import br.com.atom.nschecker.util.NamedDataProp;
import br.com.atom.nschecker.util.NamedObjectProp;

public class LinkRepository {

	protected OntologyManager ontomanager;
	protected OWLDataFactory factory;

	public LinkRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.factory = ontomanager.getFactory();
	}
	
	public String createLinkIndividual(Link link) {
		String linkIndName = IndividualUtil.processNameForIndividual(link.getName());
		ontomanager.createIndividual(linkIndName, NamedClasses.LINK);
		ontomanager.createDataPropertyAssertionAxiom(linkIndName, factory.getOWLLiteral(link.getCapacity()),
				NamedDataProp.HASCAPACITY);
		ontomanager.createDataPropertyAssertionAxiom(linkIndName, factory.getOWLLiteral(link.getAvailableCapacity()),
				NamedDataProp.HASAVAILABLECAPACITY);
		return linkIndName;
	}
	
	public String createVirtualLinkIndividual(VirtualLink vLink) {
		String vLinkIndName = IndividualUtil.processNameForIndividual(vLink.getName());
		ontomanager.createIndividual(vLinkIndName, NamedClasses.VIRTUALLINK);
		ontomanager.createDataPropertyAssertionAxiom(vLinkIndName, factory.getOWLLiteral(vLink.getCapacity()),
				NamedDataProp.HASCAPACITY);
		ontomanager.createDataPropertyAssertionAxiom(vLinkIndName, factory.getOWLLiteral(vLink.getAvailableCapacity()),
				NamedDataProp.HASAVAILABLECAPACITY);
		if (vLink.getPath() != null) {
			String pathIndName = IndividualUtil.processNameForIndividual(vLink.getPath().getName());
			ontomanager.createObjectPropertyAssertionAxiom(vLinkIndName, pathIndName, NamedObjectProp.PROVISIONEDBY);
		}
		return vLinkIndName;
	}
	
}
