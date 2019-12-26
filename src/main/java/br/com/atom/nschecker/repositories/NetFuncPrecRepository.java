package br.com.atom.nschecker.repositories;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nschecker.util.NamedObjectProp;

public class NetFuncPrecRepository {

	protected OntologyManager ontomanager;	
	
	public NetFuncPrecRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;			
	}
	
	/*
	 * Network Function Precedence Restriction
	 */
	public void createNetFuncPrecRestriction(String netFunctionNameIndPre, String netFunctionNameIndPos) {
		ontomanager.createObjectPropertyAssertionAxiom(netFunctionNameIndPre, netFunctionNameIndPos,
				NamedObjectProp.HASNETWORKFUNCTIONTYPEPRECEDENCE);
	}
	
}
