package br.com.atom.nsplanner.repositories;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nsplanner.classes.VNF;
import br.com.atom.nsplanner.util.NamedClasses;
import br.com.atom.nsplanner.util.NamedDataProp;

public class VNFRepository {

	protected OntologyManager ontomanager;

	public VNFRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
	}

	public String createVNFIndividual(VNF vnf) {
		String vnfIndName = IndividualUtil.processNameForIndividual(vnf.getName());
		ontomanager.createIndividual(vnfIndName, NamedClasses.VNF);
		ontomanager.createDataPropertyAssertionAxiom(vnfIndName,
				this.ontomanager.getFactory().getOWLLiteral(vnf.getVnfMemberIndex()), NamedDataProp.HASMEMBERVNFINDEX);
		return vnfIndName;
	}

	public String removeVNFIndividual(VNF vnf) {
		String vnfIndName = IndividualUtil.processNameForIndividual(vnf.getName());
		ontomanager.removeIndividual(vnfIndName);
		return vnfIndName;
	}

}
