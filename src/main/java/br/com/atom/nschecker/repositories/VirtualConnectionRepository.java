package br.com.atom.nschecker.repositories;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.Path;
import br.com.atom.nschecker.classes.VNFC;
import br.com.atom.nschecker.classes.VirtualLink;
import br.com.atom.nschecker.util.NamedObjectProp;

public class VirtualConnectionRepository {

	protected OntologyManager ontomanager;
	protected LinkRepository link;	

	public VirtualConnectionRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;		
		this.link = new LinkRepository(ontomanager);		
	}
	
	public String createVirtualConnectionBetweenVNFCs(VNFC vnfc1, int inteIndexVNFC1, VNFC vnfc2, int inteIndexVNFC2,
			float speed) {

		String outInteVNFC1IndName = IndividualUtil.processNameForIndividual(vnfc1.getName() + " Interface" + inteIndexVNFC1 + " Out");
		String inInteVNFC2IndName = IndividualUtil.processNameForIndividual(vnfc2.getName() + " Interface" + inteIndexVNFC2 + " In");

		String nodeSourceIndName = IndividualUtil.processNameForIndividual(vnfc1.getGuestNode().getName());
		String nodeSinkIndName = IndividualUtil.processNameForIndividual(vnfc2.getGuestNode().getName());

		String outInteNodeSourceIndName = IndividualUtil.processNameForIndividual(
				vnfc1.getGuestNode().getName() + " Interface" + inteIndexVNFC1 + " Out");
		String inInteNodeSinkIndName = IndividualUtil.processNameForIndividual(
				vnfc2.getGuestNode().getName() + " Interface" + inteIndexVNFC2 + " In");

		VirtualLink vLink = new VirtualLink();
		vLink.setName("Virtual Link " + vnfc1.getName() + "-" + vnfc2.getName());
		vLink.setCapacity(speed);
		vLink.setAvailableCapacity(speed);

		if (nodeSourceIndName != nodeSinkIndName) {
			Path path = new Path();
			path.setName(IndividualUtil.processNameForIndividual(
					"Path " + vnfc1.getGuestNode().getName() + "-" + vnfc2.getGuestNode().getName()));
			vLink.setPath(path);
		}

		String vLinkIndName = this.link.createVirtualLinkIndividual(vLink);
		ontomanager.createObjectPropertyAssertionAxiom(vLinkIndName, outInteVNFC1IndName, NamedObjectProp.HASSOURCE);
		ontomanager.createObjectPropertyAssertionAxiom(vLinkIndName, inInteVNFC2IndName, NamedObjectProp.HASSINK);

		ontomanager.createObjectPropertyAssertionAxiom(outInteVNFC1IndName, outInteNodeSourceIndName,
				NamedObjectProp.IMPLEMENTEDBY);
		ontomanager.createObjectPropertyAssertionAxiom(inInteVNFC2IndName, inInteNodeSinkIndName,
				NamedObjectProp.IMPLEMENTEDBY);
		return vLinkIndName;
	}	
	
}
