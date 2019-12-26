package br.com.atom.nschecker.repositories;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.BidirectionalInterface;
import br.com.atom.nschecker.classes.Interface;
import br.com.atom.nschecker.util.NamedClasses;
import br.com.atom.nschecker.util.NamedObjectProp;

public class InterfaceRepository {

	protected OntologyManager ontomanager;

	public InterfaceRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;		
	}
	
	public String createBiInterfaceIndividual(BidirectionalInterface biInte) {
		String biInterfaceIndName = IndividualUtil.processNameForIndividual(biInte.getName());
		ontomanager.createIndividual(biInterfaceIndName, NamedClasses.BIDIRECTIONALINTERFACE);		
		return biInterfaceIndName;
	}
	
	public String createInterfaceIndividual(Interface inte) {
		String inteIndName = IndividualUtil.processNameForIndividual(inte.getName());
		ontomanager.createIndividual(inteIndName, NamedClasses.INTERFACE);		
		return inteIndName;
	}
	
	public void createInterfaces(String nodeIdName, int numInterfaces) {
		for (int i = 1; i <= numInterfaces; i++) {
			BidirectionalInterface biInterface = new BidirectionalInterface();
			biInterface.setName(nodeIdName + " Interface" + i);

			Interface inboundInterface = new Interface();
			inboundInterface.setName(biInterface.getName() + " In");
			Interface outboundInterface = new Interface();
			outboundInterface.setName(biInterface.getName() + " Out");

			String biInterfaceIndName = this.createBiInterfaceIndividual(biInterface);
			String inInteIndName = this.createInterfaceIndividual(inboundInterface);
			String outInteIndName = this.createInterfaceIndividual(outboundInterface);

			ontomanager.createObjectPropertyAssertionAxiom(nodeIdName, inInteIndName,
					NamedObjectProp.HASINBOUNDINTERFACE);
			ontomanager.createObjectPropertyAssertionAxiom(nodeIdName, outInteIndName,
					NamedObjectProp.HASOUTBOUNDINTERFACE);

			ontomanager.createObjectPropertyAssertionAxiom(biInterfaceIndName, inInteIndName, NamedObjectProp.CONTAINS);
			ontomanager.createObjectPropertyAssertionAxiom(biInterfaceIndName, outInteIndName,
					NamedObjectProp.CONTAINS);
		}
	}
	
}
