package br.com.atom.nschecker.evaluation.repositories;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.VNFC;
import br.com.atom.nschecker.evaluation.classes.VNF;
import br.com.atom.nschecker.evaluation.classes.VNFFG;
import br.com.atom.nschecker.repositories.VirtualConnectionRepository;
import br.com.atom.nschecker.util.NamedClasses;
import br.com.atom.nschecker.util.NamedObjectProp;

public class VNFFGRepository {

	protected OntologyManager ontomanager;
	protected VNFRepository vnfDB;	
	protected VirtualConnectionRepository vcrDB;

	public VNFFGRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.vnfDB = new VNFRepository(ontomanager);
		this.vcrDB = new VirtualConnectionRepository(ontomanager);
	}
	
	public void createVNFFGIndividual(VNFFG vnffg) {
		String vnffgIndName = IndividualUtil.processNameForIndividual(vnffg.getName());
		ontomanager.createIndividual(vnffgIndName, NamedClasses.VNFFG);
		if (vnffg.getVnfs().size() > 1) {
			for (int i = 1; i < vnffg.getVnfs().size(); i++) {
				VNF vnfSource = vnffg.getVnfs().get(i - 1);
				VNF vnfSink = vnffg.getVnfs().get(i);
				VNFC vnfcSource = vnfSource.getVnfcs().get(vnfSource.getVnfcs().size() - 1);
				VNFC vnfcSink = vnfSink.getVnfcs().get(0);
				String vnfSourceIndName = this.vnfDB.createVNFIndividual(vnfSource);
				String vnfSinkIndName = this.vnfDB.createVNFIndividual(vnfSink);
				String vLinkIndName = this.vcrDB.createVirtualConnectionBetweenVNFCs(vnfcSource, 1, vnfcSink, 1, 100000);
				ontomanager.createObjectPropertyAssertionAxiom(vnffgIndName, vnfSourceIndName, NamedObjectProp.CONTAINS);
				ontomanager.createObjectPropertyAssertionAxiom(vnffgIndName, vnfSinkIndName, NamedObjectProp.CONTAINS);
				ontomanager.createObjectPropertyAssertionAxiom(vnffgIndName, vLinkIndName, NamedObjectProp.CONTAINS);
			}
		} else {
			VNF vnf = vnffg.getVnfs().get(0);
			String vnfIndName = this.vnfDB.createVNFIndividual(vnf);
			ontomanager.createObjectPropertyAssertionAxiom(vnffgIndName, vnfIndName, NamedObjectProp.CONTAINS);
		}
	}
	
}
