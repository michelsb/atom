package br.com.atom.nsplanner.evaluation.repositories;

import org.semanticweb.owlapi.model.OWLNamedIndividual;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nsplanner.util.NamedObjectProp;
import br.com.atom.nsplanner.classes.Threshold;
import br.com.atom.nsplanner.util.NamedAction;
import br.com.atom.nsplanner.util.NamedClasses;
import br.com.atom.nsplanner.util.NamedDataProp;

public class ThresholdRepositoryTest {

	protected OntologyManager ontomanager;	
	
	public ThresholdRepositoryTest(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;	
	}
	
	public String createHealingThresholdIndividual(Threshold thr) {
		String thrIndName = IndividualUtil.processNameForIndividual(thr.getName());
		OWLNamedIndividual ind = ontomanager.createIndividual(thrIndName, NamedClasses.HEALINGTHRESHOLD);
		//ontomanager.makeDifferentFromOtherIndividuals(ind, NamedClasses.HEALINGTHRESHOLD, ontomanager.createOWLReasoner());
		ontomanager.makeDifferentFromOtherIndividualsWithoutReasoner(ind, NamedClasses.HEALINGTHRESHOLD);
		return thrIndName;
	}
	
	public String createScalingThresholdIndividual(Threshold thr) {
		String thrIndName = IndividualUtil.processNameForIndividual(thr.getName());
		OWLNamedIndividual ind = ontomanager.createIndividual(thrIndName, NamedClasses.SCALINGTHRESHOLD);
		//ontomanager.makeDifferentFromOtherIndividuals(ind, NamedClasses.SCALINGTHRESHOLD, ontomanager.createOWLReasoner());
		ontomanager.makeDifferentFromOtherIndividualsWithoutReasoner(ind, NamedClasses.SCALINGTHRESHOLD);
		return thrIndName;
	}
	
	public String removeHealingThresholdIndividual(Threshold thr) {
		String thrIndName = IndividualUtil.processNameForIndividual(thr.getName());
		ontomanager.removeIndividual(thrIndName);
		return thrIndName;
	}
	
	public String removeScalingThresholdIndividual(Threshold thr) {
		String thrIndName = IndividualUtil.processNameForIndividual(thr.getName());
		ontomanager.removeIndividual(thrIndName);
		return thrIndName;
	}
	
	public void configureHealingThreshold(String thrIndName, String actionIndName, String metricIndName, int lowValue, int mediumValue, int highValue) {
		ontomanager.createObjectPropertyAssertionAxiom(actionIndName, thrIndName, NamedObjectProp.HASTHRESHOLD);
		ontomanager.createObjectPropertyAssertionAxiom(thrIndName, metricIndName, NamedObjectProp.HASMETRIC);
		ontomanager.createDataPropertyAssertionAxiom(thrIndName, this.ontomanager.getFactory().getOWLLiteral(lowValue), NamedDataProp.HASLOWTHRESHOLDVALUE);
		ontomanager.createDataPropertyAssertionAxiom(thrIndName, this.ontomanager.getFactory().getOWLLiteral(mediumValue), NamedDataProp.HASMEDIUMTHRESHOLDVALUE);
		ontomanager.createDataPropertyAssertionAxiom(thrIndName, this.ontomanager.getFactory().getOWLLiteral(highValue), NamedDataProp.HASHIGHTHRESHOLDVALUE);		
	}
	
	public void configureScalingThreshold(String thrIndName, String metricIndName, 
			int lowInValue, int lowOutValue, int mediumInValue, int mediumOutValue, int highInValue, int highOutValue) {
		ontomanager.createObjectPropertyAssertionAxiom(NamedAction.HORIZONTAL_SCALE, thrIndName, NamedObjectProp.HASTHRESHOLD);
		ontomanager.createObjectPropertyAssertionAxiom(thrIndName, metricIndName, NamedObjectProp.HASMETRIC);		
		ontomanager.createDataPropertyAssertionAxiom(thrIndName, this.ontomanager.getFactory().getOWLLiteral(lowInValue), NamedDataProp.HASLOWSCALEINTHRESHOLDVALUE);
		ontomanager.createDataPropertyAssertionAxiom(thrIndName, this.ontomanager.getFactory().getOWLLiteral(lowOutValue), NamedDataProp.HASLOWSCALEOUTTHRESHOLDVALUE);		
		ontomanager.createDataPropertyAssertionAxiom(thrIndName, this.ontomanager.getFactory().getOWLLiteral(mediumInValue), NamedDataProp.HASMEDIUMSCALEINTHRESHOLDVALUE);
		ontomanager.createDataPropertyAssertionAxiom(thrIndName, this.ontomanager.getFactory().getOWLLiteral(mediumOutValue), NamedDataProp.HASMEDIUMSCALEOUTTHRESHOLDVALUE);		
		ontomanager.createDataPropertyAssertionAxiom(thrIndName, this.ontomanager.getFactory().getOWLLiteral(highInValue), NamedDataProp.HASHIGHSCALEINTHRESHOLDVALUE);
		ontomanager.createDataPropertyAssertionAxiom(thrIndName, this.ontomanager.getFactory().getOWLLiteral(highOutValue), NamedDataProp.HASHIGHSCALEOUTTHRESHOLDVALUE);		
	}
	
}
