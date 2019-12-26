package br.com.atom.nschecker.evaluation.repositories;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.VNFC;
import br.com.atom.nschecker.evaluation.classes.VNF;
import br.com.atom.nschecker.repositories.VNFCRepository;
import br.com.atom.nschecker.repositories.VirtualConnectionRepository;
import br.com.atom.nschecker.util.NamedClasses;
import br.com.atom.nschecker.util.NamedObjectProp;

public class VNFRepository {

	protected OntologyManager ontomanager;
	protected VNFCRepository vnfcDB;	
	protected VirtualConnectionRepository vcrDB;

	public VNFRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.vnfcDB = new VNFCRepository(ontomanager);
		this.vcrDB = new VirtualConnectionRepository(ontomanager);		
	}	
	
	public String createVNFIndividual(VNF vnf) {
		String vnfIndName = IndividualUtil.processNameForIndividual(vnf.getName());
		ontomanager.createIndividual(vnfIndName, NamedClasses.VNF);
		
		String ingressPortIndName = "";
		String egressPortIndName = "";

		if (vnf.getVnfcs().size() > 1) {
			for (int i = 1; i < vnf.getVnfcs().size(); i++) {
				VNFC vnfcSource = vnf.getVnfcs().get(i - 1);
				VNFC vnfcSink = vnf.getVnfcs().get(i);
				if (i == 1) {
					ingressPortIndName = IndividualUtil.processNameForIndividual(vnfcSource.getName() + " Interface" + 1 + " In");
				}
				if (i == vnf.getVnfcs().size() - 1) {
					egressPortIndName = IndividualUtil.processNameForIndividual(vnfcSink.getName() + " Interface" + 1 + " Out");
				}
				String vnfcSourceIndName = this.vnfcDB.createVNFCIndividual(vnfcSource, false);
				String vnfcSinkIndName = this.vnfcDB.createVNFCIndividual(vnfcSink, false);
				String vLinkIndName = this.vcrDB.createVirtualConnectionBetweenVNFCs(vnfcSource, 1, vnfcSink, 1, 100000);
				ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, vnfcSourceIndName, NamedObjectProp.CONTAINS);
				ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, vnfcSinkIndName, NamedObjectProp.CONTAINS);
				ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, vLinkIndName, NamedObjectProp.CONTAINS);
			}
		} else {
			VNFC vnfc = vnf.getVnfcs().get(0);
			String vnfcIndName = this.vnfcDB.createVNFCIndividual(vnfc, false);
			ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, vnfcIndName, NamedObjectProp.CONTAINS);
			ingressPortIndName = IndividualUtil.processNameForIndividual(vnfc.getName() + " Interface" + 1 + " In");
			egressPortIndName = IndividualUtil.processNameForIndividual(vnfc.getName() + " Interface" + 1 + " Out");
		}

		ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, ingressPortIndName, NamedObjectProp.HASINGRESSPORT);
		ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, egressPortIndName, NamedObjectProp.HASEGRESSPORT);

		return vnfIndName;

	}
	
}
