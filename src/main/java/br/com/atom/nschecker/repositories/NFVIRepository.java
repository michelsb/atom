package br.com.atom.nschecker.repositories;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.NFVI;
import br.com.atom.nschecker.util.NamedClasses;

public class NFVIRepository {

	protected OntologyManager ontomanager;	

	public NFVIRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;	
	}
	
	public void createNFVIIndividual(NFVI nfvi) {
		String nfviIndName = IndividualUtil.processNameForIndividual(nfvi.getName());
		ontomanager.createIndividual(nfviIndName, NamedClasses.NFVI);		
	}
	
}
