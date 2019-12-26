package br.com.atom.nschecker.repositories;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nschecker.util.NamedClasses;
import br.com.atom.nschecker.util.NamedObjectProp;
import br.com.atom.nschecker.util.OntoNFVUtil;

public class ResourceUsageRestRepository {

	protected OntologyManager ontomanager;
	protected OWLDataFactory factory;

	public ResourceUsageRestRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;	
		this.factory = ontomanager.getFactory();
	}
	
	/*
	 * Resource Usage Restriction: The maximum number of VNFCs supported per NFVI-Node
	 */
	public String createNFVINodeNumRestriction(int maxNumVNFs) {
		OWLOntologyManager manager = ontomanager.getManager();
		OWLOntology onto = ontomanager.getOntology();

		String nameClsNUM = "";
		
		if (maxNumVNFs == 0) {
			nameClsNUM = NamedClasses.RESTMAXNUM10;
		} else {
			nameClsNUM = "RestMaxNum" + maxNumVNFs;			
		}		
		
		IRI iri = IRI.create(OntoNFVUtil.NFV_IRI + "#", nameClsNUM);

		if (!onto.containsClassInSignature(iri)) {
			OWLClass subCls = factory.getOWLClass(OntoNFVUtil.NFV_IRI + "#", nameClsNUM);
			OWLClass superCls = factory.getOWLClass(OntoNFVUtil.NFV_IRI + "#", NamedClasses.NFVINODE);
			
			OWLClass fillerCls = factory.getOWLClass(OntoNFVUtil.NFV_IRI + "#", NamedClasses.VIRTUALNODE);						
			
			// Now create the Subclass axiom
			OWLSubClassOfAxiom axiom1 = factory.getOWLSubClassOfAxiom(subCls, superCls);
			// add the axiom to the ontology.
			AddAxiom addAxiom1 = new AddAxiom(onto, axiom1);
			// We now use the manager to apply the change
			manager.applyChange(addAxiom1);			
			
			// Now create the Restriction axiom
			OWLObjectProperty implement = factory.getOWLObjectProperty(OntoNFVUtil.NFV_IRI + "#", NamedObjectProp.IMPLEMENTS);
			OWLClassExpression implementMaxVNFCs = factory.getOWLObjectMaxCardinality(maxNumVNFs, implement, fillerCls);
			OWLSubClassOfAxiom axiom2 = factory.getOWLSubClassOfAxiom(subCls, implementMaxVNFCs);
			// add the axiom to the ontology.
			AddAxiom addAxiom2 = new AddAxiom(onto, axiom2);
			// We now use the manager to apply the change
			manager.applyChange(addAxiom2);			
		}
		
		return nameClsNUM;		
	}
	
	/*
	 * Resource Usage Restriction: The maximum number of allocated vCPUs supported per NFVI-Node
	 */
	public String createNFVINodeCPURestriction(int maxNumCPUs) {
		OWLOntologyManager manager = ontomanager.getManager();
		OWLOntology onto = ontomanager.getOntology();

		String nameClsCPU = "";
		
		if (maxNumCPUs == 0) {
			nameClsCPU = NamedClasses.RESTMAXVCPU10;
		} else {
			nameClsCPU = "RestMaxVCPU" + maxNumCPUs;
		}
		
		IRI iri = IRI.create(OntoNFVUtil.NFV_IRI + "#", nameClsCPU);

		if (!onto.containsClassInSignature(iri)) {
			OWLClass subCls = factory.getOWLClass(OntoNFVUtil.NFV_IRI + "#", nameClsCPU);
			OWLClass superCls = factory.getOWLClass(OntoNFVUtil.NFV_IRI + "#", NamedClasses.NFVINODE);
			
			OWLClass fillerCls = factory.getOWLClass(OntoNFVUtil.NFV_IRI + "#", NamedClasses.VCPU);						
			
			// Now create the Subclass axiom
			OWLSubClassOfAxiom axiom1 = factory.getOWLSubClassOfAxiom(subCls, superCls);
			// add the axiom to the ontology.
			AddAxiom addAxiom1 = new AddAxiom(onto, axiom1);
			// We now use the manager to apply the change
			manager.applyChange(addAxiom1);			
			
			// Now create the Restriction axiom
			OWLObjectProperty allocMaxVCPUs = factory.getOWLObjectProperty(OntoNFVUtil.NFV_IRI + "#", NamedObjectProp.ALLOCATES);
			OWLClassExpression allocatestMaxVCPUs = factory.getOWLObjectMaxCardinality(maxNumCPUs, allocMaxVCPUs, fillerCls);
			OWLSubClassOfAxiom axiom2 = factory.getOWLSubClassOfAxiom(subCls, allocatestMaxVCPUs);
			// add the axiom to the ontology.
			AddAxiom addAxiom2 = new AddAxiom(onto, axiom2);
			// We now use the manager to apply the change
			manager.applyChange(addAxiom2);			
		}
		
		return nameClsCPU;
	}
	
	/*
	 * Resource Usage Restriction: The maximum number of allocated 1GB vMem slots supported per NFVI-Node
	 */
	public String createNFVINodeMemRestriction(int maxNumMems) {
		OWLOntologyManager manager = ontomanager.getManager();
		OWLOntology onto = ontomanager.getOntology();

		String nameClsMEM = "";

		if (maxNumMems == 0) {
			nameClsMEM = NamedClasses.RESTMAXVMEM10;
		} else {
			nameClsMEM = "RestMaxVMem" + maxNumMems;			
		}
		
		IRI iri = IRI.create(OntoNFVUtil.NFV_IRI + "#", nameClsMEM);

		if (!onto.containsClassInSignature(iri)) {
			OWLClass subCls = factory.getOWLClass(OntoNFVUtil.NFV_IRI + "#", nameClsMEM);
			OWLClass superCls = factory.getOWLClass(OntoNFVUtil.NFV_IRI + "#", NamedClasses.NFVINODE);
			
			OWLClass fillerCls = factory.getOWLClass(OntoNFVUtil.NFV_IRI + "#", NamedClasses.VMEMORY);						
			
			// Now create the Subclass axiom
			OWLSubClassOfAxiom axiom1 = factory.getOWLSubClassOfAxiom(subCls, superCls);
			// add the axiom to the ontology.
			AddAxiom addAxiom1 = new AddAxiom(onto, axiom1);
			// We now use the manager to apply the change
			manager.applyChange(addAxiom1);			
			
			// Now create the Restriction axiom
			OWLObjectProperty allocMaxVMems = factory.getOWLObjectProperty(OntoNFVUtil.NFV_IRI + "#", NamedObjectProp.ALLOCATES);
			OWLClassExpression allocatestMaxVMems = factory.getOWLObjectMaxCardinality(maxNumMems, allocMaxVMems, fillerCls);
			OWLSubClassOfAxiom axiom2 = factory.getOWLSubClassOfAxiom(subCls, allocatestMaxVMems);
			// add the axiom to the ontology.
			AddAxiom addAxiom2 = new AddAxiom(onto, axiom2);
			// We now use the manager to apply the change
			manager.applyChange(addAxiom2);			
		}
		return nameClsMEM;
	}	
	
}
