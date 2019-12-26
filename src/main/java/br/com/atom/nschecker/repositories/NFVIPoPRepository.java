package br.com.atom.nschecker.repositories;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.NFVIPoP;
import br.com.atom.nschecker.util.NamedClasses;
import br.com.atom.nschecker.util.NamedObjectProp;

public class NFVIPoPRepository {

	protected OntologyManager ontomanager;	

	public NFVIPoPRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;	
	}
	
	public void createNFVIPoPIndividual(NFVIPoP nfvipop) {
		String nfvipopIndName = IndividualUtil.processNameForIndividual(nfvipop.getName());
		ontomanager.createIndividual(nfvipopIndName, NamedClasses.NFVIPOP);
		String nfviIndName = IndividualUtil.processNameForIndividual(nfvipop.getNfvi().getName());
		ontomanager.createObjectPropertyAssertionAxiom(nfviIndName, nfvipopIndName, NamedObjectProp.CONTAINS);
	}
	
}
