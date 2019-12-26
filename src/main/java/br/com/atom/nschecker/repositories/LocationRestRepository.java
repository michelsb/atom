package br.com.atom.nschecker.repositories;

import java.util.ArrayList;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nschecker.classes.VNFC;
import br.com.atom.nschecker.util.NamedObjectProp;

public class LocationRestRepository {

	protected OntologyManager ontomanager;	
	protected VNFCRepository vnfc;

	public LocationRestRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;		
		this.vnfc = new VNFCRepository(ontomanager);
	}
	
	/*
	 * Single Placement Restriction: NFVI-Node
	 */
	public void createPlacementRestriction(VNFC vnfc) {
		this.vnfc.createVNFCIndividual(vnfc, true);
	}
	
	/*
	 * Multiple Placement Restrictions: NFVI-Node
	 */
	public void createPlacementRestrictions(ArrayList<VNFC> vnfcs) {
		for (VNFC vnfc : vnfcs) {
			this.createPlacementRestriction(vnfc);
		}
		VNFC vnfc1 = null;
		VNFC vnfc2 = null;
		for (int i=0;i<vnfcs.size()-1;i++) {
			vnfc1 = vnfcs.get(i);
			if (vnfc1.getGuestNode() != null) {
				for (int j=i+1;j<vnfcs.size();j++) {
					vnfc2 = vnfcs.get(j);
					if (vnfc2.getGuestNode() != null) {
						if (vnfc1.getGuestNode().getName().equals(vnfc2.getGuestNode().getName())) {
							this.createSameNodeRestrictionToVNFCs(vnfc1.getName(),vnfc2.getName());
						} else {
							this.createNotSameNodeRestrictionToVNFCs(vnfc1.getName(),vnfc2.getName());
						}
					}
				}
			}
		}
	}	
	
	/*
	 * Affinity Restriction: NFVI-Node
	 */	
	public void createSameNodeRestrictionToVNFCs(String nameVNFC1, String nameVNFC2) {
		String vnfc1IndName = IndividualUtil.processNameForIndividual(nameVNFC1);
		String vnfc2IndName = IndividualUtil.processNameForIndividual(nameVNFC2);
		ontomanager.createObjectPropertyAssertionAxiom(vnfc1IndName, vnfc2IndName, NamedObjectProp.HASSAMENFVINODE);
	}

	/*
	 * Affinity Restriction: NFVI-PoP
	 */	
	public void createSameNFVIPoPRestrictionToVNFCs(String nameVNFC1, String nameVNFC2) {
		String vnfc1IndName = IndividualUtil.processNameForIndividual(nameVNFC1);
		String vnfc2IndName = IndividualUtil.processNameForIndividual(nameVNFC2);;
		ontomanager.createObjectPropertyAssertionAxiom(vnfc1IndName, vnfc2IndName, NamedObjectProp.HASSAMENFVIPOP);
	}

	/*
	 * Affinity Restriction: NFVI
	 */	
	public void createSameNFVIRestrictionToVNFCs(String nameVNFC1, String nameVNFC2) {
		String vnfc1IndName = IndividualUtil.processNameForIndividual(nameVNFC1);
		String vnfc2IndName = IndividualUtil.processNameForIndividual(nameVNFC2);
		ontomanager.createObjectPropertyAssertionAxiom(vnfc1IndName, vnfc2IndName, NamedObjectProp.HASSAMENFVI);
	}

	/*
	 * Anti-Affinity Restriction: NFVI-Node
	 */	
	public void createNotSameNodeRestrictionToVNFCs(String nameVNFC1, String nameVNFC2) {
		String vnfc1IndName = IndividualUtil.processNameForIndividual(nameVNFC1);
		String vnfc2IndName = IndividualUtil.processNameForIndividual(nameVNFC2);;
		ontomanager.createObjectPropertyAssertionAxiom(vnfc1IndName, vnfc2IndName, NamedObjectProp.HASNOTSAMENFVINODE);
	}

	/*
	 * Anti-Affinity Restriction: NFVI-PoP
	 */	
	public void createNotSameNFVIPoPRestrictionToVNFCs(String nameVNFC1, String nameVNFC2) {
		String vnfc1IndName = IndividualUtil.processNameForIndividual(nameVNFC1);
		String vnfc2IndName = IndividualUtil.processNameForIndividual(nameVNFC2);
		ontomanager.createObjectPropertyAssertionAxiom(vnfc1IndName, vnfc2IndName, NamedObjectProp.HASNOTSAMENFVIPOP);
	}

	/*
	 * Anti-Affinity Restriction: NFVI
	 */	
	public void createNotSameNFVIRestrictionToVNFCs(String nameVNFC1, String nameVNFC2) {
		String vnfc1IndName = IndividualUtil.processNameForIndividual(nameVNFC1);
		String vnfc2IndName = IndividualUtil.processNameForIndividual(nameVNFC2);
		ontomanager.createObjectPropertyAssertionAxiom(vnfc1IndName, vnfc2IndName, NamedObjectProp.HASNOTSAMENFVI);
	}

	/*
	 * Affinity Restriction Between Types of Network Function: NFVI-Node
	 */
	public void createSameNodeRestrictionToNetFunctions(String netFunction1, String netFunction2) {
		ontomanager.createObjectPropertyAssertionAxiom(netFunction1, netFunction2, NamedObjectProp.HASSAMENFVINODE);
	}
	
	/*
	 * Affinity Restriction Between Types of Network Function: NFVI-PoP
	 */
	public void createSameNFVIPoPRestrictionToNetFunctions(String netFunction1, String netFunction2) {
		ontomanager.createObjectPropertyAssertionAxiom(netFunction1, netFunction2, NamedObjectProp.HASSAMENFVIPOP);
	}
	
	/*
	 * Affinity Restriction Between Types of Network Function: NFVI
	 */
	public void createSameNFVIRestrictionToNetFunctions(String netFunction1, String netFunction2) {
		ontomanager.createObjectPropertyAssertionAxiom(netFunction1, netFunction2, NamedObjectProp.HASSAMENFVI);
	}	
	
	/*
	 * Anti-Affinity Restriction Between Types of Network Function: NFVI-Node
	 */
	public void createNotSameNodeRestrictionToNetFunctions(String netFunction1, String netFunction2) {
		ontomanager.createObjectPropertyAssertionAxiom(netFunction1, netFunction2, NamedObjectProp.HASNOTSAMENFVINODE);
	}
	
	/*
	 * Anti-Affinity Restriction Between Types of Network Function: NFVI-Node
	 */
	public void createNotSameNFVIPoPRestrictionToNetFunctions(String netFunction1, String netFunction2) {
		ontomanager.createObjectPropertyAssertionAxiom(netFunction1, netFunction2, NamedObjectProp.HASNOTSAMENFVIPOP);
	}
	
	/*
	 * Anti-Affinity Restriction Between Types of Network Function: NFVI-Node
	 */
	public void createNotSameNFVIRestrictionToNetFunctions(String netFunction1, String netFunction2) {
		ontomanager.createObjectPropertyAssertionAxiom(netFunction1, netFunction2, NamedObjectProp.HASNOTSAMENFVI);
	}
	
}
