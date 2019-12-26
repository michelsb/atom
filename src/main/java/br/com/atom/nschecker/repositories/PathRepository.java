package br.com.atom.nschecker.repositories;

import org.semanticweb.owlapi.model.OWLDataFactory;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.Path;
import br.com.atom.nschecker.util.NamedClasses;

public class PathRepository {

	protected OntologyManager ontomanager;
	protected OWLDataFactory factory;

	public PathRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.factory = ontomanager.getFactory();
	}
	
	public String createPathIndividual(Path path) {
		String pathIndName = IndividualUtil.processNameForIndividual(path.getName());
		ontomanager.createIndividual(pathIndName, NamedClasses.PATH);
		return pathIndName;
	}
	
}
