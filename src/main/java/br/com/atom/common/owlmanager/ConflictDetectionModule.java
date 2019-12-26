package br.com.atom.common.owlmanager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.semanticweb.HermiT.Configuration;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owl.explanation.api.Explanation;
import org.semanticweb.owl.explanation.api.ExplanationGenerator;
import org.semanticweb.owl.explanation.api.ExplanationGeneratorFactory;
import org.semanticweb.owl.explanation.impl.blackbox.checker.InconsistentOntologyExplanationGeneratorFactory;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.ReasonerProgressMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.atom.common.responses.ConsistencyResponse;
import br.com.atom.nschecker.util.OntoNFVUtil;

public class ConflictDetectionModule {

	private OntologyManager ontomanager;	
	private ReasonerFactory reasonerFactory;
	private OWLReasoner reasoner;
	private IRI ontoIRI;

	public ConflictDetectionModule(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
		this.ontoIRI = ontomanager.getOntoIRI();
		// Now we can start and create the reasoner. Since explanation is not
		// natively supported by
		// HermiT and is realized in the OWL API, we need to instantiate HermiT
		// as an OWLReasoner. This is done via a ReasonerFactory object.
		this.reasonerFactory = new ReasonerFactory();
		this.reasoner = null;

	}

	public void start() {
		//this.close();
		// We don't want HermiT to thrown an exception for inconsistent
		// ontologies because then we
		// can't explain the inconsistency. This can be controlled via a
		// configuration setting.
		Configuration configuration = new Configuration();
		configuration.throwInconsistentOntologyException = false;

		// The factory can now be used to obtain an instance of HermiT as an
		// OWLReasoner.
		//System.out.println("INDEX 1");
		this.reasoner = reasonerFactory.createReasoner(ontomanager.getOntology(), configuration);
		
		
		//System.out.println("INDEX 2");
		//ontomanager.makeAllNFVINodesDifferent(reasoner);
	}

	public void close() {
		if (reasoner != null){
			//reasoner.flush();
            reasoner.dispose();
            reasoner = null;
        }		
	}
	
	// Testing Ontology Consistency
	public ConsistencyResponse testOntoConsistency() {
		
		ConsistencyResponse response = new ConsistencyResponse();		
		ArrayList<ArrayList<String>> explanationList = new ArrayList<ArrayList<String>>();
		response.setExplanations(explanationList);
		
		if (!reasoner.isConsistent()) {
			//System.out.println("Ontology is inconsistent!");
			//System.out.println("Computing explanations for the inconsistency...");

			response.setConsitent(false);
			
			Supplier<OWLOntologyManager> management = new Supplier<OWLOntologyManager>() {
				@Override
				public OWLOntologyManager get() {
					return ontomanager.getManager();
				}
			};
			OWLOntology ontology = ontomanager.getOntology();
			OWLDataFactory dataFactory = ontomanager.getManager().getOWLDataFactory();
			Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();
			
			axioms.addAll(ontology.getAxioms());

			// ***************************
			ExplanationGeneratorFactory<OWLAxiom> explFactory = new InconsistentOntologyExplanationGeneratorFactory(
					this.reasonerFactory, dataFactory, management, 1000L);
			ExplanationGenerator<OWLAxiom> delegate = explFactory.createExplanationGenerator(axioms);

			OWLAxiom entailment = dataFactory.getOWLSubClassOfAxiom(
					dataFactory.getOWLClass(this.ontoIRI + "#", "Thing"),
					dataFactory.getOWLClass(this.ontoIRI + "#", "Nothing"));

			Set<Explanation<OWLAxiom>> explanations = delegate.getExplanations(entailment, 2);
			
			for (Explanation<OWLAxiom> explanation : explanations) {
				//System.out.println("------------------");
				//System.out.println("Axioms causing the inconsistency: ");
				ArrayList<String> causingList = new ArrayList<String>();
				for (OWLAxiom causingAxiom : explanation.getAxioms()) {
					causingList.add(causingAxiom.toString());
					//System.out.println(causingAxiom);
				}
				explanationList.add(causingList);
				//System.out.println("------------------");
			}

		} else {
			//ystem.out.println("Ontology is consistent!");
			response.setConsitent(true);
		}

		return response;
		
	}

	public OWLReasoner getReasoner() {
		return reasoner;
	}

	public void setReasoner(OWLReasoner reasoner) {
		this.reasoner = reasoner;
	}

	public static class LoggingReasonerProgressMonitor implements ReasonerProgressMonitor {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static Logger logger;

		public LoggingReasonerProgressMonitor(Logger log) {
			logger = log;
		}

		public LoggingReasonerProgressMonitor(Logger log, String methodName) {
			String loggerName = log.getName() + '.' + methodName;
			logger = LoggerFactory.getLogger(loggerName);
		}

		@Override
		public void reasonerTaskStarted(String taskName) {
			logger.info("Reasoner Task Started: {}.", taskName);
		}

		@Override
		public void reasonerTaskStopped() {
			logger.info("Task stopped.");
		}

		@Override
		public void reasonerTaskProgressChanged(int value, int max) {
			logger.info("Reasoner Task made progress: {}/{}", Integer.valueOf(value), Integer.valueOf(max));
		}

		@Override
		public void reasonerTaskBusy() {
			logger.info("Reasoner Task is busy");
		}
	}

}
