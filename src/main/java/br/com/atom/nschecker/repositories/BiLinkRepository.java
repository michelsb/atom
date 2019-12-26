package br.com.atom.nschecker.repositories;

import org.semanticweb.owlapi.model.OWLDataFactory;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.BidirectionalLink;
import br.com.atom.nschecker.util.NamedClasses;

public class BiLinkRepository {

	protected OntologyManager ontomanager;
	protected OWLDataFactory factory;

	public BiLinkRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.factory = ontomanager.getFactory();
	}
	
	public String createBiLinkIndividual(BidirectionalLink biLink) {
		String biLinkIndName = IndividualUtil.processNameForIndividual(biLink.getName());
		ontomanager.createIndividual(biLinkIndName, NamedClasses.BIDIRECTIONALLINK);
		return biLinkIndName;
	}
	
}
