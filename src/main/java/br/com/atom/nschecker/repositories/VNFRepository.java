package br.com.atom.nschecker.repositories;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.VNF;
import br.com.atom.nschecker.util.NamedClasses;
import br.com.atom.nschecker.util.NamedObjectProp;

public class VNFRepository {

	protected OntologyManager ontomanager;
	//protected VNFCRepository vnfc;	
	//protected VirtualConnectionRepository vcr;

	public VNFRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		//this.vnfc = new VNFCRepository(ontomanager);
		//this.vcr = new VirtualConnectionRepository(ontomanager);		
	}
	
	public String createVNFIndividual(VNF vnf) {
		String vnfIndName = IndividualUtil.processNameForIndividual(vnf.getName());
		ontomanager.createIndividual(vnfIndName, NamedClasses.VNF);
		String vnffgIndName = IndividualUtil.processNameForIndividual(vnf.getVnffg().getName());
		ontomanager.createObjectPropertyAssertionAxiom(vnffgIndName, vnfIndName, NamedObjectProp.CONTAINS);
		return vnfIndName;
	}
	
	public void createPortsVNF(String vnfIndName, String nameVNFC1, int inteIndexVNFC1, String nameVNFC2, int inteIndexVNFC2) {
		String ingressPortIndName = IndividualUtil.processNameForIndividual(nameVNFC1 + " Interface" + inteIndexVNFC1 + " In");
		String egressPortIndName = IndividualUtil.processNameForIndividual(nameVNFC2 + " Interface" + inteIndexVNFC2 + " Out");
		ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, ingressPortIndName, NamedObjectProp.HASINGRESSPORT);
		ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, egressPortIndName, NamedObjectProp.HASEGRESSPORT);		
	}
	
	/*public String createVNFIndividual(VNF vnf) {
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
				String vnfcSourceIndName = this.vnfc.createVNFCIndividual(vnfcSource, false);
				String vnfcSinkIndName = this.vnfc.createVNFCIndividual(vnfcSink, false);
				String vLinkIndName = this.vcr.createVirtualConnectionBetweenVNFCs(vnfcSource, 1, vnfcSink, 1, 100000);
				ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, vnfcSourceIndName, NamedObjectProp.CONTAINS);
				ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, vnfcSinkIndName, NamedObjectProp.CONTAINS);
				ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, vLinkIndName, NamedObjectProp.CONTAINS);
			}
		} else {
			VNFC vnfc = vnf.getVnfcs().get(0);
			String vnfcIndName = this.vnfc.createVNFCIndividual(vnfc, false);
			ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, vnfcIndName, NamedObjectProp.CONTAINS);
			ingressPortIndName = IndividualUtil.processNameForIndividual(vnfc.getName() + " Interface" + 1 + " In");
			egressPortIndName = IndividualUtil.processNameForIndividual(vnfc.getName() + " Interface" + 1 + " Out");
		}

		ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, ingressPortIndName, NamedObjectProp.HASINGRESSPORT);
		ontomanager.createObjectPropertyAssertionAxiom(vnfIndName, egressPortIndName, NamedObjectProp.HASEGRESSPORT);

		return vnfIndName;

	}*/
	
}
