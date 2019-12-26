package br.com.atom.common.owlmanager;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.io.File;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.FileDocumentSource;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.MissingImportHandlingStrategy;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.OWLEntityRemover;

import br.com.atom.nschecker.util.NamedClasses;

public class OntologyManager {

	private OWLOntologyManager manager = null;
	private OWLOntology ontology = null;	
	protected OWLDataFactory factory;
	private IRI ontoIRI = null;

	public void loadOntology(String path, IRI iri) {
		if (manager == null) {
			System.out.println("Manager does not generated!");
		}
		try {
			OWLOntologyLoaderConfiguration loadingConfig = new OWLOntologyLoaderConfiguration();
			loadingConfig = loadingConfig.setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
			File file = new File(path);
			this.setOntology(manager.loadOntologyFromOntologyDocument(new FileDocumentSource(file), loadingConfig));
			this.setOntoIRI(iri);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void loadOntology(File file, IRI iri) {
		if (manager == null) {
			System.out.println("Manager does not generated!");
		}
		try {
			OWLOntologyLoaderConfiguration loadingConfig = new OWLOntologyLoaderConfiguration();
			loadingConfig = loadingConfig.setMissingImportHandlingStrategy(MissingImportHandlingStrategy.SILENT);
			this.setOntology(manager.loadOntologyFromOntologyDocument(new FileDocumentSource(file), loadingConfig));
			this.setOntoIRI(iri);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createOWLManager() {
		this.setManager(OWLManager.createOWLOntologyManager());
		this.factory = OWLManager.getOWLDataFactory();
	}

	public OWLReasoner createOWLReasoner() {
		ReasonerFactory reasonerFactory = new ReasonerFactory();		
		Configuration configuration = new Configuration();
		configuration.throwInconsistentOntologyException = false;
		OWLReasoner reasoner = reasonerFactory.createReasoner(this.getOntology(), configuration);
		return reasoner;
	}
	
	public void startOntologyProcessing(String path, IRI iri) {
		this.createOWLManager();		
		this.loadOntology(path, iri);
		//this.createOWLReasoner();
	}

	public void startOntologyProcessing(File file, IRI iri) {
		this.createOWLManager();		
		this.loadOntology(file, iri);
		//this.createOWLReasoner();
	}
	
	public void close() {
		manager.removeOntology(ontology);
		manager = null;
		ontology = null;
		factory = null;
	}

	public void listAllClasses() {
		// These are the named classes referenced by axioms in the ontology.
		ontology.classesInSignature().forEach(cls ->
		// use the class for whatever purpose
		System.out.println(cls.getIRI()));
	}

	public void listAllClassesFormatted() {
		// These are the named classes referenced by axioms in the ontology.
		ontology.classesInSignature().forEach(cls ->
		// use the class for whatever purpose
		
		System.out.println("public static final @Nonnull String " + new String(cls.getIRI().getShortForm()).toUpperCase() + " = \"" + cls.getIRI().getShortForm() + "\";"));
	}
	
	public void listAllObjectProperties() {
		// These are all the Object Properties in the ontology.
		ontology.objectPropertiesInSignature().forEach(op ->
		// use the class for whatever purpose
		System.out.println(op));
	}

	public void listAllObjectPropertiesFormatted() {
		// These are all the Object Properties in the ontology.
		ontology.objectPropertiesInSignature().forEach(op ->
		// use the class for whatever purpose
		System.out.println("public static final @Nonnull String " + new String(op.getIRI().getShortForm()).toUpperCase() + " = \"" + op.getIRI().getShortForm() + "\";"));
	}
	
	public void listAllDatatypeProperties() {
		// These are all the Datatype Properties in the ontology.
		ontology.dataPropertiesInSignature().forEach(data ->
		// use the class for whatever purpose
		System.out.println(data));
	}

	public void listAllDatatypePropertiesFormatted() {
		// These are all the Datatype Properties in the ontology.
		ontology.dataPropertiesInSignature().forEach(data ->
		// use the class for whatever purpose
		System.out.println("public static final @Nonnull String " + new String(data.getIRI().getShortForm()).toUpperCase() + " = \"" + data.getIRI().getShortForm() + "\";"));
	}
	
	public void listAllAxioms() {
		// These are all Axioms in the ontology.
		ontology.axioms().forEach(ax ->
		// use the class for whatever purpose
		System.out.println(ax));
	}

	public void listAllIndividuals() {
		// These are all Individuals in the ontology.
		ontology.individualsInSignature().forEach(in ->
		// use the class for whatever purpose
		System.out.println(in));
	}

	public void listAllIndividualsFormatted() {
		// These are all Individuals in the ontology.
		ontology.individualsInSignature().forEach(in ->
		// use the class for whatever purpose
		System.out.println("public static final @Nonnull String " + new String(in.getIRI().getShortForm()).toUpperCase() + " = \"" + in.getIRI().getShortForm() + "\";"));
	}
	
	public Set<OWLObjectProperty> getAllObjectProperties() {
		// These are all Data Properties in the ontology.
		Set<OWLObjectProperty> ops = asSet(ontology.objectPropertiesInSignature());
		return ops;
	}
	
	public Set<OWLDataProperty> getAllDataProperties() {
		// These are all Data Properties in the ontology.
		Set<OWLDataProperty> dps = asSet(ontology.dataPropertiesInSignature());
		return dps;
	}
	
	public Set<OWLNamedIndividual> getAllIndividuals() {
		// These are all Individuals in the ontology.
		Set<OWLNamedIndividual> individuals = asSet(ontology.individualsInSignature());
		return individuals;
	}
	
	
	
	/*
	 * public void saveOntologyMemoryBuffer() { try {
	 * manager.saveOntology(ontology, target); } catch
	 * (OWLOntologyStorageException e) { e.printStackTrace(); } }
	 */

	public void saveOntologyFile() {
		try {
			System.out.println("Saving Onto-NFV...");
			manager.saveOntology(ontology);
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
	}

	public void saveNewOntologyFile(String path) {
		try {
			System.out.println("Saving Onto-NFV in file: " + path + "...");
			File newOntoNFVFile = new File(path);
			manager.saveOntology(ontology, IRI.create(newOntoNFVFile.toURI()));
		} catch (OWLOntologyStorageException e) {
			e.printStackTrace();
		}
	}

	public OWLNamedIndividual createIndividual(String nameInd, String nameCls) {
		OWLNamedIndividual ind = null;
		IRI iri = IRI.create(this.ontoIRI + "#", nameCls);

		if (ontology.containsClassInSignature(iri)) {
			OWLClass cls = factory.getOWLClass(this.ontoIRI + "#", nameCls);
			ind = factory.getOWLNamedIndividual(this.ontoIRI + "#", nameInd);
			OWLClassAssertionAxiom classAssertion = factory.getOWLClassAssertionAxiom(cls, ind);
			manager.addAxiom(ontology, classAssertion);
		} else {			
			System.out.println("Class not found: "+iri);
		}

		return ind;
	}
	
	public OWLNamedIndividual createIndividualSuperClasses(String nameInd, String nameCls1, String nameCls2, String nameCls3) {
		OWLNamedIndividual ind = null;
		IRI iri1 = IRI.create(this.ontoIRI + "#", nameCls1);
		IRI iri2 = IRI.create(this.ontoIRI + "#", nameCls2);
		IRI iri3 = IRI.create(this.ontoIRI + "#", nameCls3);

		if (ontology.containsClassInSignature(iri1)) {
			OWLClass cls = factory.getOWLClass(this.ontoIRI + "#", nameCls1);
			ind = factory.getOWLNamedIndividual(this.ontoIRI + "#", nameInd);
			OWLClassAssertionAxiom classAssertion = factory.getOWLClassAssertionAxiom(cls, ind);
			manager.addAxiom(ontology, classAssertion);
		} else {			
			System.out.println("Class not found: "+iri1);
		}

		if (ontology.containsClassInSignature(iri2)) {
			OWLClass cls = factory.getOWLClass(this.ontoIRI + "#", nameCls2);
			ind = factory.getOWLNamedIndividual(this.ontoIRI + "#", nameInd);
			OWLClassAssertionAxiom classAssertion = factory.getOWLClassAssertionAxiom(cls, ind);
			manager.addAxiom(ontology, classAssertion);
		} else {			
			System.out.println("Class not found: "+iri2);
		}
		
		if (ontology.containsClassInSignature(iri3)) {
			OWLClass cls = factory.getOWLClass(this.ontoIRI + "#", nameCls3);
			ind = factory.getOWLNamedIndividual(this.ontoIRI + "#", nameInd);
			OWLClassAssertionAxiom classAssertion = factory.getOWLClassAssertionAxiom(cls, ind);
			manager.addAxiom(ontology, classAssertion);
		} else {			
			System.out.println("Class not found: "+iri3);
		}
		
		return ind;
	}
	
	public void removeIndividual(String nameInd) {
		OWLNamedIndividual ind = factory.getOWLNamedIndividual(this.ontoIRI + "#", nameInd);
		OWLEntityRemover remover = new OWLEntityRemover(Collections.singleton(ontology));
		ind.accept(remover);
		manager.applyChanges(remover.getChanges());
	}

	/*public Set<OWLNamedIndividual> getClassIndividuals(String nameCls, OWLReasoner reasoner, Boolean precompute) {
		OWLClass cls = factory.getOWLClass(this.ontoIRI + "#", nameCls);
		if (reasoner != null) {
			if (precompute) {
				reasoner.precomputeInferences();
			}
			NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(cls, true);
			Set<OWLNamedIndividual> individuals = asSet(individualsNodeSet.entities());
			return individuals;
		} else {
			return null;
		}
	}*/

	public Set<OWLNamedIndividual> getClassIndividualsWithoutReasoner(String nameCls) {
		OWLClass cls = factory.getOWLClass(this.ontoIRI + "#", nameCls);
		Stream<OWLClassAssertionAxiom> assertionAxioms = ontology.classAssertionAxioms(cls);
		Set<OWLNamedIndividual> individuals = new HashSet<OWLNamedIndividual>();
		assertionAxioms.forEach(axiom -> individuals.add((OWLNamedIndividual) axiom.getIndividual()));
		return individuals;
	}
	
	/*public Set<OWLNamedIndividual> getObjectPropertiesForIndividual(String nameInd, String nameOp, OWLReasoner reasoner,
			Boolean precompute) {
		OWLNamedIndividual ind = factory.getOWLNamedIndividual(this.ontoIRI + "#", nameInd);
		OWLObjectProperty contains = factory.getOWLObjectProperty(this.ontoIRI + "#", NamedObjectProp.CONTAINS);
		if (reasoner != null) {
			if (precompute) {
				// reasoner.flush();
				reasoner.precomputeInferences();
			}
			NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getObjectPropertyValues(ind, contains);
			Set<OWLNamedIndividual> individuals = asSet(individualsNodeSet.entities());
			return individuals;
		} else {
			return null;
		}
	}*/

	/* Methods to create initial state for nsplanner */
	
	public Set<OWLClass> getTypesPerIndividual(OWLNamedIndividual ind, OWLReasoner reasoner, Boolean precompute) {
		if (reasoner != null) {
			if (precompute) {
				reasoner.precomputeInferences();
			}
			Stream<OWLClass> typesStream = reasoner.types(ind);
			Set<OWLClass> types = asSet(typesStream);
			return types;
		} else {
			return null;
		}
	}
	
	/*public Set<OWLNamedIndividual> getOpValuesPerIndividual(String indName, String opIndName, OWLReasoner reasoner, Boolean precompute) {
		OWLNamedIndividual domainInd = factory.getOWLNamedIndividual(this.ontoIRI + "#", indName);
		OWLObjectProperty opInd = factory.getOWLObjectProperty(this.ontoIRI + "#", opIndName);
		if (reasoner != null) {
			if (precompute) {
				reasoner.precomputeInferences();
			}
			Stream<OWLNamedIndividual> partnersStream = reasoner.objectPropertyValues(domainInd, opInd); 
			Set<OWLNamedIndividual> partners = asSet(partnersStream);
			return partners;
		} else {
			return null;
		}
	}*/
	
	public Set<OWLNamedIndividual> getOpValuesPerIndividualWithoutReasoner(String indName, String opIndName) {
		OWLNamedIndividual domainInd = factory.getOWLNamedIndividual(this.ontoIRI + "#", indName);
		OWLObjectProperty opInd = factory.getOWLObjectProperty(this.ontoIRI + "#", opIndName);
		Stream<OWLObjectPropertyAssertionAxiom> assertionAxioms = ontology.objectPropertyAssertionAxioms(domainInd);
		Set<OWLObjectPropertyAssertionAxiom> assertionAxiomsSet = asSet(assertionAxioms);
		Set<OWLNamedIndividual> individuals = new HashSet<OWLNamedIndividual>();
		for (OWLObjectPropertyAssertionAxiom axiom : assertionAxiomsSet) {
			if (opInd == axiom.getProperty().getNamedProperty()) {
				individuals.add((OWLNamedIndividual) axiom.getObject());
			}
		}
		return individuals;
	}
	
	/*public Set<OWLLiteral> getDpValuesPerIndividual(String indName, String dpIndName, OWLReasoner reasoner, Boolean precompute) {
		OWLNamedIndividual domainInd = factory.getOWLNamedIndividual(this.ontoIRI + "#", indName);
		OWLDataProperty dpInd = factory.getOWLDataProperty(this.ontoIRI + "#", dpIndName);
		if (reasoner != null) {
			if (precompute) {
				reasoner.precomputeInferences();
			}
			Stream<OWLLiteral> valuesStream = reasoner.dataPropertyValues(domainInd, dpInd); 
			Set<OWLLiteral> values = asSet(valuesStream);
			return values;
		} else {
			return null;
		}
	}*/
	
	public Set<OWLLiteral> getDpValuesPerIndividualWithoutReasoner(String indName, String dpIndName) {
		OWLNamedIndividual domainInd = factory.getOWLNamedIndividual(this.ontoIRI + "#", indName);
		OWLDataProperty dpInd = factory.getOWLDataProperty(this.ontoIRI + "#", dpIndName);
		Stream<OWLDataPropertyAssertionAxiom> assertionAxioms = ontology.dataPropertyAssertionAxioms(domainInd);
		Set<OWLDataPropertyAssertionAxiom> assertionAxiomsSet = asSet(assertionAxioms);
		Set<OWLLiteral> values = new HashSet<OWLLiteral>();
		for (OWLDataPropertyAssertionAxiom axiom : assertionAxiomsSet) {
			if (dpInd == axiom.getProperty().asOWLDataProperty()) {
				values.add((OWLLiteral) axiom.getObject());
			}
		}
		return values;
	}
	
	/* *********** */
	
	public void listClassIndividuals(String nameCls, OWLReasoner reasoner) {
		OWLClass cls = factory.getOWLClass(this.ontoIRI + "#", nameCls);
		if (reasoner != null) {
			// reasoner.flush();
			reasoner.precomputeInferences();
			NodeSet<OWLNamedIndividual> individualsNodeSet = reasoner.getInstances(cls, false);
			Set<OWLNamedIndividual> individuals = asSet(individualsNodeSet.entities());
			for (OWLNamedIndividual i : individuals) {
				System.out.println(i);
			}
		} else {
			System.out.println("Reasoner is null!");
		}
	}

	// Make an individual different from the other of same type
	/*public void makeAllIndividualssDifferent(String clsName, OWLReasoner reasoner) {
		Set<OWLNamedIndividual> individuals = this.getClassIndividuals(clsName, reasoner,true);
		manager.addAxiom(ontology, factory.getOWLDifferentIndividualsAxiom(individuals));
	}*/
	
	public void makeAllIndividualssDifferentWithoutReasoner(String clsName) {
		Set<OWLNamedIndividual> individuals = this.getClassIndividualsWithoutReasoner(clsName);
		manager.addAxiom(ontology, factory.getOWLDifferentIndividualsAxiom(individuals));
	}
	
	// Make an individual different from the other of same type
	/*public void makeAllNFVINodesDifferent(OWLReasoner reasoner) {
		Set<OWLNamedIndividual> individuals = this.getClassIndividuals(NamedClasses.NFVINODE, reasoner, true);
		manager.addAxiom(ontology, factory.getOWLDifferentIndividualsAxiom(individuals));
	}*/

	public void makeAllNFVINodesDifferentWithoutReasoner() {
		Set<OWLNamedIndividual> individuals = this.getClassIndividualsWithoutReasoner(NamedClasses.NFVINODE);
		manager.addAxiom(ontology, factory.getOWLDifferentIndividualsAxiom(individuals));
	}
	
	// Make individuals different from each other
	public void makeIndividualsDifferent(Set<OWLNamedIndividual> individuals) {
		manager.addAxiom(ontology, factory.getOWLDifferentIndividualsAxiom(individuals));
	}

	// Make an individual different from the other of same type
	/*public void makeDifferentFromOtherIndividuals(OWLNamedIndividual individual, String cls, OWLReasoner reasoner) {
		Set<OWLNamedIndividual> individuals = this.getClassIndividuals(cls, reasoner, true);
		for (OWLNamedIndividual i : individuals) {
			if (!i.equals(individual)) {
				manager.addAxiom(ontology, factory.getOWLDifferentIndividualsAxiom(individual, i));
			}
		}
	}*/

	public void makeDifferentFromOtherIndividualsWithoutReasoner(OWLNamedIndividual individual, String cls) {
		Set<OWLNamedIndividual> individuals = this.getClassIndividualsWithoutReasoner(cls);
		for (OWLNamedIndividual i : individuals) {
			if (!i.equals(individual)) {
				manager.addAxiom(ontology, factory.getOWLDifferentIndividualsAxiom(individual, i));
			}
		}
	}
	
	public void createObjectPropertyAssertionAxiom(String domainIndName, String rangeIndName, String objectPropName) {
		OWLNamedIndividual domainInd = factory.getOWLNamedIndividual(this.ontoIRI + "#", domainIndName);
		OWLNamedIndividual rangeInd = factory.getOWLNamedIndividual(this.ontoIRI + "#", rangeIndName);
		OWLObjectProperty objectProp = factory.getOWLObjectProperty(this.ontoIRI + "#", objectPropName);
		OWLObjectPropertyAssertionAxiom propAssertion = factory.getOWLObjectPropertyAssertionAxiom(objectProp,
				domainInd, rangeInd);

		AddAxiom addAxiomChange = new AddAxiom(ontology, propAssertion);
		manager.applyChange(addAxiomChange);
	}

	public void createObjectPropertyAssertionAxiom(String domainIndName, OWLNamedIndividual rangeInd,
			String objectPropName) {
		OWLNamedIndividual domainInd = factory.getOWLNamedIndividual(this.ontoIRI + "#", domainIndName);
		OWLObjectProperty objectProp = factory.getOWLObjectProperty(this.ontoIRI + "#", objectPropName);
		OWLObjectPropertyAssertionAxiom propAssertion = factory.getOWLObjectPropertyAssertionAxiom(objectProp,
				domainInd, rangeInd);

		AddAxiom addAxiomChange = new AddAxiom(ontology, propAssertion);
		manager.applyChange(addAxiomChange);
	}

	public void createObjectPropertyAssertionAxiom(OWLNamedIndividual domainInd, String rangeIndName,
			String objectPropName) {
		OWLNamedIndividual rangeInd = factory.getOWLNamedIndividual(this.ontoIRI + "#", rangeIndName);
		OWLObjectProperty objectProp = factory.getOWLObjectProperty(this.ontoIRI + "#", objectPropName);
		OWLObjectPropertyAssertionAxiom propAssertion = factory.getOWLObjectPropertyAssertionAxiom(objectProp,
				domainInd, rangeInd);

		AddAxiom addAxiomChange = new AddAxiom(ontology, propAssertion);
		manager.applyChange(addAxiomChange);
	}

	public void createObjectPropertyAssertionAxiom(OWLNamedIndividual domainInd, OWLNamedIndividual rangeInd,
			String objectPropName) {
		OWLObjectProperty objectProp = factory.getOWLObjectProperty(this.ontoIRI + "#", objectPropName);
		OWLObjectPropertyAssertionAxiom propAssertion = factory.getOWLObjectPropertyAssertionAxiom(objectProp,
				domainInd, rangeInd);

		AddAxiom addAxiomChange = new AddAxiom(ontology, propAssertion);
		manager.applyChange(addAxiomChange);
	}

	public void createDataPropertyAssertionAxiom(String domainIndName, OWLLiteral value, String dataPropName) {
		OWLNamedIndividual domainInd = factory.getOWLNamedIndividual(this.ontoIRI + "#", domainIndName);
		OWLDataProperty dataProp = factory.getOWLDataProperty(this.ontoIRI + "#", dataPropName);
		OWLDataPropertyAssertionAxiom dataPropertyAssertion = factory.getOWLDataPropertyAssertionAxiom(dataProp,
				domainInd, value);

		manager.addAxiom(ontology, dataPropertyAssertion);
	}

	public OWLOntologyManager getManager() {
		return manager;
	}

	public void setManager(OWLOntologyManager manager) {
		this.manager = manager;
	}

	public OWLOntology getOntology() {
		return ontology;
	}

	public void setOntology(OWLOntology ontology) {
		this.ontology = ontology;
	}

	public OWLDataFactory getFactory() {
		return factory;
	}

	public void setFactory(OWLDataFactory factory) {
		this.factory = factory;
	}

	public IRI getOntoIRI() {
		return ontoIRI;
	}

	public void setOntoIRI(IRI ontoIRI) {
		this.ontoIRI = ontoIRI;
	}	
	
}
