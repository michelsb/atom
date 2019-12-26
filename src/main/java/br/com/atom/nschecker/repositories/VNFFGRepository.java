package br.com.atom.nschecker.repositories;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.VNFFG;
import br.com.atom.nschecker.util.NamedClasses;

public class VNFFGRepository {

	protected OntologyManager ontomanager;
	protected VNFRepository vnf;	
	protected VirtualConnectionRepository vcr;

	public VNFFGRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.vnf = new VNFRepository(ontomanager);
		this.vcr = new VirtualConnectionRepository(ontomanager);		
	}
	
	public void createVNFFGIndividual(VNFFG vnffg) {
		String vnffgIndName = IndividualUtil.processNameForIndividual(vnffg.getName());
		ontomanager.createIndividual(vnffgIndName, NamedClasses.VNFFG);		
	}
	
	/*public void createVNFFGIndividual(VNFFG vnffg) {
		String vnffgIndName = IndividualUtil.processNameForIndividual(vnffg.getName());
		ontomanager.createIndividual(vnffgIndName, NamedClasses.VNFFG);
		if (vnffg.getVnfs().size() > 1) {
			for (int i = 1; i < vnffg.getVnfs().size(); i++) {
				VNF vnfSource = vnffg.getVnfs().get(i - 1);
				VNF vnfSink = vnffg.getVnfs().get(i);
				VNFC vnfcSource = vnfSource.getVnfcs().get(vnfSource.getVnfcs().size() - 1);
				VNFC vnfcSink = vnfSink.getVnfcs().get(0);
				String vnfSourceIndName = this.vnf.createVNFIndividual(vnfSource);
				String vnfSinkIndName = this.vnf.createVNFIndividual(vnfSink);
				String vLinkIndName = this.vcr.createVirtualConnectionBetweenVNFCs(vnfcSource, 1, vnfcSink, 1, 100000);
				ontomanager.createObjectPropertyAssertionAxiom(vnffgIndName, vnfSourceIndName, NamedObjectProp.CONTAINS);
				ontomanager.createObjectPropertyAssertionAxiom(vnffgIndName, vnfSinkIndName, NamedObjectProp.CONTAINS);
				ontomanager.createObjectPropertyAssertionAxiom(vnffgIndName, vLinkIndName, NamedObjectProp.CONTAINS);
			}
		} else {
			VNF vnf = vnffg.getVnfs().get(0);
			String vnfIndName = this.vnf.createVNFIndividual(vnf);
			ontomanager.createObjectPropertyAssertionAxiom(vnffgIndName, vnfIndName, NamedObjectProp.CONTAINS);
		}
	}*/
	
}
