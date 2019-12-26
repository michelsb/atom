package br.com.atom.nschecker.repositories;

import org.semanticweb.owlapi.model.OWLDataFactory;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.BidirectionalLink;
import br.com.atom.nschecker.classes.InterPoPSwitch;
import br.com.atom.nschecker.classes.Link;
import br.com.atom.nschecker.classes.Node;
import br.com.atom.nschecker.classes.Path;
import br.com.atom.nschecker.util.NamedObjectProp;

public class PhysicalConnectionRepository {

	protected OntologyManager ontomanager;
	protected OWLDataFactory factory;
	protected LinkRepository link;
	protected BiLinkRepository bilink;
	protected PathRepository path;
	protected InterPoPSwitchRepository interpop;

	public PhysicalConnectionRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.factory = ontomanager.getFactory();
		this.link = new LinkRepository(ontomanager);
		this.bilink = new BiLinkRepository(ontomanager);
		this.path = new PathRepository(ontomanager);
		this.interpop = new InterPoPSwitchRepository(ontomanager);
	}
	
	public void createPhysicalConnectionBetweenHosts(Node node1, int inteIndexNode1, Node node2, int inteIndexNode2,
			float speed) {

		String node1IndName = IndividualUtil.processNameForIndividual(node1.getName());
		String node2IndName = IndividualUtil.processNameForIndividual(node2.getName());

		String inInteNode1IndName = IndividualUtil
				.processNameForIndividual(node1.getName() + " Interface" + inteIndexNode1 + " In");
		String outInteNode1IndName = IndividualUtil
				.processNameForIndividual(node1.getName() + " Interface" + inteIndexNode1 + " Out");
		String inInteNode2IndName = IndividualUtil
				.processNameForIndividual(node2.getName() + " Interface" + inteIndexNode2 + " In");
		String outInteNode2IndName = IndividualUtil
				.processNameForIndividual(node2.getName() + " Interface" + inteIndexNode2 + " Out");

		BidirectionalLink biLink = new BidirectionalLink();
		biLink.setName("PhyLink " + node1.getName() + "-" + node2.getName());

		Link linkForward = new Link();
		linkForward.setName(biLink.getName() + " Forward");
		linkForward.setCapacity(speed);
		linkForward.setAvailableCapacity(speed);
		Link linkBackward = new Link();
		linkBackward.setName(biLink.getName() + " Backward");
		linkBackward.setCapacity(speed);
		linkBackward.setAvailableCapacity(speed);

		Path pathForward = new Path();
		pathForward.setName("Path " + node1.getName() + "-" + node2.getName());
		Path pathBackward = new Path();
		pathBackward.setName("Path " + node2.getName() + "-" + node1.getName());

		String biLinkIndName = this.bilink.createBiLinkIndividual(biLink);
		String linkForwardIndName = this.link.createLinkIndividual(linkForward);
		String linkBackwardIndName = this.link.createLinkIndividual(linkBackward);
		String pathForwardIndName = this.path.createPathIndividual(pathForward);
		String pathBackwardIndName = this.path.createPathIndividual(pathBackward);

		ontomanager.createObjectPropertyAssertionAxiom(biLinkIndName, linkForwardIndName, NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(biLinkIndName, linkBackwardIndName, NamedObjectProp.CONTAINS);

		ontomanager.createObjectPropertyAssertionAxiom(linkForwardIndName, outInteNode1IndName,
				NamedObjectProp.HASSOURCE);
		ontomanager.createObjectPropertyAssertionAxiom(linkForwardIndName, inInteNode2IndName, NamedObjectProp.HASSINK);

		ontomanager.createObjectPropertyAssertionAxiom(linkBackwardIndName, outInteNode2IndName,
				NamedObjectProp.HASSOURCE);
		ontomanager.createObjectPropertyAssertionAxiom(linkBackwardIndName, inInteNode1IndName,
				NamedObjectProp.HASSINK);

		ontomanager.createObjectPropertyAssertionAxiom(node1IndName, node2IndName, NamedObjectProp.CONNECTEDTO);

		ontomanager.createObjectPropertyAssertionAxiom(pathForwardIndName, outInteNode1IndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathForwardIndName, linkForwardIndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathForwardIndName, inInteNode2IndName,
				NamedObjectProp.CONTAINS);

		ontomanager.createObjectPropertyAssertionAxiom(pathBackwardIndName, outInteNode2IndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathBackwardIndName, linkBackwardIndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathBackwardIndName, inInteNode1IndName,
				NamedObjectProp.CONTAINS);

	}

	public void createPhysicalConnectionBetweenHosts(String nameNode1, int inteIndexNode1, String nameNode2, int inteIndexNode2,
			float speed) {

		String node1IndName = IndividualUtil.processNameForIndividual(nameNode1);
		String node2IndName = IndividualUtil.processNameForIndividual(nameNode2);

		String inInteNode1IndName = IndividualUtil
				.processNameForIndividual(nameNode1 + " Interface" + inteIndexNode1 + " In");
		String outInteNode1IndName = IndividualUtil
				.processNameForIndividual(nameNode1 + " Interface" + inteIndexNode1 + " Out");
		String inInteNode2IndName = IndividualUtil
				.processNameForIndividual(nameNode2 + " Interface" + inteIndexNode2 + " In");
		String outInteNode2IndName = IndividualUtil
				.processNameForIndividual(nameNode2 + " Interface" + inteIndexNode2 + " Out");

		BidirectionalLink biLink = new BidirectionalLink();
		biLink.setName("PhyLink " + nameNode1 + "-" + nameNode2);

		Link linkForward = new Link();
		linkForward.setName(biLink.getName() + " Forward");
		linkForward.setCapacity(speed);
		linkForward.setAvailableCapacity(speed);
		Link linkBackward = new Link();
		linkBackward.setName(biLink.getName() + " Backward");
		linkBackward.setCapacity(speed);
		linkBackward.setAvailableCapacity(speed);

		Path pathForward = new Path();
		pathForward.setName("Path " + nameNode1 + "-" + nameNode2);
		Path pathBackward = new Path();
		pathBackward.setName("Path " + nameNode2 + "-" + nameNode1);

		String biLinkIndName = this.bilink.createBiLinkIndividual(biLink);
		String linkForwardIndName = this.link.createLinkIndividual(linkForward);
		String linkBackwardIndName = this.link.createLinkIndividual(linkBackward);
		String pathForwardIndName = this.path.createPathIndividual(pathForward);
		String pathBackwardIndName = this.path.createPathIndividual(pathBackward);

		ontomanager.createObjectPropertyAssertionAxiom(biLinkIndName, linkForwardIndName, NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(biLinkIndName, linkBackwardIndName, NamedObjectProp.CONTAINS);

		ontomanager.createObjectPropertyAssertionAxiom(linkForwardIndName, outInteNode1IndName,
				NamedObjectProp.HASSOURCE);
		ontomanager.createObjectPropertyAssertionAxiom(linkForwardIndName, inInteNode2IndName, NamedObjectProp.HASSINK);

		ontomanager.createObjectPropertyAssertionAxiom(linkBackwardIndName, outInteNode2IndName,
				NamedObjectProp.HASSOURCE);
		ontomanager.createObjectPropertyAssertionAxiom(linkBackwardIndName, inInteNode1IndName,
				NamedObjectProp.HASSINK);

		ontomanager.createObjectPropertyAssertionAxiom(node1IndName, node2IndName, NamedObjectProp.CONNECTEDTO);

		ontomanager.createObjectPropertyAssertionAxiom(pathForwardIndName, outInteNode1IndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathForwardIndName, linkForwardIndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathForwardIndName, inInteNode2IndName,
				NamedObjectProp.CONTAINS);

		ontomanager.createObjectPropertyAssertionAxiom(pathBackwardIndName, outInteNode2IndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathBackwardIndName, linkBackwardIndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathBackwardIndName, inInteNode1IndName,
				NamedObjectProp.CONTAINS);

	}
	
	public void createPhysicalConnectionBetweenPoPs(Node node1, int inteIndexNode1, Node node2, int inteIndexNode2,
			float speed, int vxlanId) {

		String node1IndName = IndividualUtil.processNameForIndividual(node1.getName());
		String node2IndName = IndividualUtil.processNameForIndividual(node2.getName());

		String inInteNode1IndName = IndividualUtil
				.processNameForIndividual(node1.getName() + " Interface" + inteIndexNode1 + " In");
		String outInteNode1IndName = IndividualUtil
				.processNameForIndividual(node1.getName() + " Interface" + inteIndexNode1 + " Out");
		String inInteNode2IndName = IndividualUtil
				.processNameForIndividual(node2.getName() + " Interface" + inteIndexNode2 + " In");
		String outInteNode2IndName = IndividualUtil
				.processNameForIndividual(node2.getName() + " Interface" + inteIndexNode2 + " Out");

		BidirectionalLink biLink = new BidirectionalLink();
		biLink.setName("InterPoPLink " + node1.getName() + "-" + node2.getName());

		InterPoPSwitch linkForward = new InterPoPSwitch();
		linkForward.setName(biLink.getName() + " Forward");
		linkForward.setCapacity(speed);
		linkForward.setAvailableCapacity(speed);
		linkForward.setVxlanId(vxlanId);
		InterPoPSwitch linkBackward = new InterPoPSwitch();
		linkBackward.setName(biLink.getName() + " Backward");
		linkBackward.setCapacity(speed);
		linkBackward.setAvailableCapacity(speed);
		linkBackward.setVxlanId(vxlanId);

		Path pathForward = new Path();
		pathForward.setName("Path " + node1.getName() + "-" + node2.getName());
		Path pathBackward = new Path();
		pathBackward.setName("Path " + node2.getName() + "-" + node1.getName());

		String biLinkIndName = this.bilink.createBiLinkIndividual(biLink);
		String linkForwardIndName = this.interpop.createInterPoPSwitchIndividual(linkForward);
		String linkBackwardIndName = this.interpop.createInterPoPSwitchIndividual(linkBackward);
		String pathForwardIndName = this.path.createPathIndividual(pathForward);
		String pathBackwardIndName = this.path.createPathIndividual(pathBackward);

		ontomanager.createObjectPropertyAssertionAxiom(biLinkIndName, linkForwardIndName, NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(biLinkIndName, linkBackwardIndName, NamedObjectProp.CONTAINS);

		ontomanager.createObjectPropertyAssertionAxiom(linkForwardIndName, outInteNode1IndName,
				NamedObjectProp.HASSOURCE);
		ontomanager.createObjectPropertyAssertionAxiom(linkForwardIndName, inInteNode2IndName, NamedObjectProp.HASSINK);

		ontomanager.createObjectPropertyAssertionAxiom(linkBackwardIndName, outInteNode2IndName,
				NamedObjectProp.HASSOURCE);
		ontomanager.createObjectPropertyAssertionAxiom(linkBackwardIndName, inInteNode1IndName,
				NamedObjectProp.HASSINK);

		ontomanager.createObjectPropertyAssertionAxiom(node1IndName, node2IndName, NamedObjectProp.CONNECTEDTO);

		ontomanager.createObjectPropertyAssertionAxiom(pathForwardIndName, outInteNode1IndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathForwardIndName, linkForwardIndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathForwardIndName, inInteNode2IndName,
				NamedObjectProp.CONTAINS);

		ontomanager.createObjectPropertyAssertionAxiom(pathBackwardIndName, outInteNode2IndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathBackwardIndName, linkBackwardIndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathBackwardIndName, inInteNode1IndName,
				NamedObjectProp.CONTAINS);

	}
	
	public void createPhysicalConnectionBetweenPoPs(String nameNode1, int inteIndexNode1, String nameNode2, int inteIndexNode2,
			float speed, int vxlanId) {

		String node1IndName = IndividualUtil.processNameForIndividual(nameNode1);
		String node2IndName = IndividualUtil.processNameForIndividual(nameNode2);

		String inInteNode1IndName = IndividualUtil
				.processNameForIndividual(nameNode1 + " Interface" + inteIndexNode1 + " In");
		String outInteNode1IndName = IndividualUtil
				.processNameForIndividual(nameNode1 + " Interface" + inteIndexNode1 + " Out");
		String inInteNode2IndName = IndividualUtil
				.processNameForIndividual(nameNode2 + " Interface" + inteIndexNode2 + " In");
		String outInteNode2IndName = IndividualUtil
				.processNameForIndividual(nameNode2 + " Interface" + inteIndexNode2 + " Out");

		BidirectionalLink biLink = new BidirectionalLink();
		biLink.setName("InterPoPLink " + nameNode1 + "-" + nameNode2);

		InterPoPSwitch linkForward = new InterPoPSwitch();
		linkForward.setName(biLink.getName() + " Forward");
		linkForward.setCapacity(speed);
		linkForward.setAvailableCapacity(speed);
		linkForward.setVxlanId(vxlanId);
		InterPoPSwitch linkBackward = new InterPoPSwitch();
		linkBackward.setName(biLink.getName() + " Backward");
		linkBackward.setCapacity(speed);
		linkBackward.setAvailableCapacity(speed);
		linkBackward.setVxlanId(vxlanId);

		Path pathForward = new Path();
		pathForward.setName("Path " + nameNode1 + "-" + nameNode2);
		Path pathBackward = new Path();
		pathBackward.setName("Path " + nameNode2 + "-" + nameNode1);

		String biLinkIndName = this.bilink.createBiLinkIndividual(biLink);
		String linkForwardIndName = this.interpop.createInterPoPSwitchIndividual(linkForward);
		String linkBackwardIndName = this.interpop.createInterPoPSwitchIndividual(linkBackward);
		String pathForwardIndName = this.path.createPathIndividual(pathForward);
		String pathBackwardIndName = this.path.createPathIndividual(pathBackward);

		ontomanager.createObjectPropertyAssertionAxiom(biLinkIndName, linkForwardIndName, NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(biLinkIndName, linkBackwardIndName, NamedObjectProp.CONTAINS);

		ontomanager.createObjectPropertyAssertionAxiom(linkForwardIndName, outInteNode1IndName,
				NamedObjectProp.HASSOURCE);
		ontomanager.createObjectPropertyAssertionAxiom(linkForwardIndName, inInteNode2IndName, NamedObjectProp.HASSINK);

		ontomanager.createObjectPropertyAssertionAxiom(linkBackwardIndName, outInteNode2IndName,
				NamedObjectProp.HASSOURCE);
		ontomanager.createObjectPropertyAssertionAxiom(linkBackwardIndName, inInteNode1IndName,
				NamedObjectProp.HASSINK);

		ontomanager.createObjectPropertyAssertionAxiom(node1IndName, node2IndName, NamedObjectProp.CONNECTEDTO);

		ontomanager.createObjectPropertyAssertionAxiom(pathForwardIndName, outInteNode1IndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathForwardIndName, linkForwardIndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathForwardIndName, inInteNode2IndName,
				NamedObjectProp.CONTAINS);

		ontomanager.createObjectPropertyAssertionAxiom(pathBackwardIndName, outInteNode2IndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathBackwardIndName, linkBackwardIndName,
				NamedObjectProp.CONTAINS);
		ontomanager.createObjectPropertyAssertionAxiom(pathBackwardIndName, inInteNode1IndName,
				NamedObjectProp.CONTAINS);

	}
	
	/*public void createPathBetweenHosts(Node nodeSource, ArrayList<Node> intermediaryNodes, Node nodeSink) {
	Boolean precompute = true;
	String nodeSourceIndName = IndividualUtil.processNameForIndividual(nodeSource.getName());
	String nodeSinkIndName = IndividualUtil.processNameForIndividual(nodeSink.getName());

	Path path = new Path();
	path.setName("Path " + nodeSource.getName() + "-" + nodeSink.getName());
	String pathIndName = this.createPathIndividual(path);

	Node currentNode = nodeSource;
	intermediaryNodes.add(nodeSink);

	for (Node node : intermediaryNodes) {
		String secPathIndName = IndividualUtil
				.processNameForIndividual("Path " + currentNode.getName() + "-" + node.getName());
		Set<OWLNamedIndividual> individuals = ontomanager.getObjectPropertiesForIndividual(secPathIndName,
				NamedObjectProp.CONTAINS, precompute);
		for (OWLNamedIndividual i : individuals) {
			ontomanager.createObjectPropertyAssertionAxiom(pathIndName, i, NamedObjectProp.CONTAINS);
		}
		currentNode = node;
		if (precompute) {
			precompute = false;
		}
	}

	ontomanager.createObjectPropertyAssertionAxiom(nodeSourceIndName, nodeSinkIndName, NamedObjectProp.CONNECTEDTO);

	}*/
	
}
