package br.com.atom.nsplanner.repositories;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.common.util.IndividualUtil;
import br.com.atom.nsplanner.classes.ScalingCriteria;
import br.com.atom.nsplanner.dtos.ScalingCriteriaDto;
import br.com.atom.nsplanner.util.NamedClasses;
import br.com.atom.nsplanner.util.NamedDataProp;
import br.com.atom.nsplanner.util.NamedObjectProp;

public class ScalingCriteriaRepository {

	protected OntologyManager ontomanager;

	public ScalingCriteriaRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
	}

	public ScalingCriteriaDto getScalingCriteria(String indName) {

		ScalingCriteriaDto scDto = new ScalingCriteriaDto();
		scDto.setName(indName);

		Set<OWLLiteral> scaleInThr = ontomanager.getDpValuesPerIndividualWithoutReasoner(indName,
				NamedDataProp.HASSCALEINTHRESHOLD);
		for (OWLLiteral value : scaleInThr) {
			scDto.setScaleInThreshold(value.parseInteger());
			break;
		}

		Set<OWLLiteral> scaleInRelOp = ontomanager.getDpValuesPerIndividualWithoutReasoner(indName,
				NamedDataProp.HASSCALEINRELATIONALOPERATION);
		for (OWLLiteral value : scaleInRelOp) {
			scDto.setScaleInRelationalOperation(value.getLiteral());
			break;
		}

		Set<OWLLiteral> scaleOutThr = ontomanager.getDpValuesPerIndividualWithoutReasoner(indName,
				NamedDataProp.HASSCALEOUTTHRESHOLD);
		for (OWLLiteral value : scaleOutThr) {
			scDto.setScaleOutThreshold(value.parseInteger());
			break;
		}

		Set<OWLLiteral> scaleOutRelOp = ontomanager.getDpValuesPerIndividualWithoutReasoner(indName,
				NamedDataProp.HASSCALEOUTRELATIONALOPERATION);
		for (OWLLiteral value : scaleOutRelOp) {
			scDto.setScaleOutRelationalOperation(value.getLiteral());
			break;
		}

		Set<OWLNamedIndividual> metrics = ontomanager.getOpValuesPerIndividualWithoutReasoner(indName,
				NamedObjectProp.HASNSMONITORINGPARAMREF);
		for (OWLNamedIndividual metric : metrics) {
			scDto.setNsMonitoringParamRef(metric.getIRI().getShortForm());
			break;
		}

		return scDto;

	}

	public String createScalingCriteriaIndividual(ScalingCriteria sc) {
		String scIndName = IndividualUtil.processNameForIndividual(sc.getName());
		ontomanager.createIndividual(scIndName, NamedClasses.SCALINGCRITERIA);
		ontomanager.createDataPropertyAssertionAxiom(scIndName,
				this.ontomanager.getFactory().getOWLLiteral(sc.getScaleInThreshold()),
				NamedDataProp.HASSCALEINTHRESHOLD);
		ontomanager.createDataPropertyAssertionAxiom(scIndName,
				this.ontomanager.getFactory().getOWLLiteral(sc.getScaleInRelationalOperation()),
				NamedDataProp.HASSCALEINRELATIONALOPERATION);
		ontomanager.createDataPropertyAssertionAxiom(scIndName,
				this.ontomanager.getFactory().getOWLLiteral(sc.getScaleOutThreshold()),
				NamedDataProp.HASSCALEOUTTHRESHOLD);
		ontomanager.createDataPropertyAssertionAxiom(scIndName,
				this.ontomanager.getFactory().getOWLLiteral(sc.getScaleOutRelationalOperation()),
				NamedDataProp.HASSCALEOUTRELATIONALOPERATION);
		ontomanager.createObjectPropertyAssertionAxiom(scIndName, sc.getNsMonitoringParamRef(),
				NamedObjectProp.HASNSMONITORINGPARAMREF);
		return scIndName;
	}

	public String removeScalingCriteriaIndividual(ScalingCriteria sc) {
		String scIndName = IndividualUtil.processNameForIndividual(sc.getName());
		ontomanager.removeIndividual(scIndName);
		return scIndName;
	}

}
