package br.com.atom.nschecker.repositories;

import java.util.UUID;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.SwitchingMatrix;
import br.com.atom.nschecker.util.NamedClasses;

public class SwitchingMatrixRepository {

	protected OntologyManager ontomanager;	

	public SwitchingMatrixRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;		
	}
	
	public String createSwitchingMatrixIndividual(SwitchingMatrix swt) {
		String swtIndName = IndividualUtil.processNameForIndividual(swt.getName() + "-" + UUID.randomUUID());
		ontomanager.createIndividual(swtIndName, NamedClasses.SWITCHINGMATRIX);
		return swtIndName;
	}
	
}
