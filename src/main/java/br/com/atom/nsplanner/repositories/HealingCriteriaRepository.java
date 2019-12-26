package br.com.atom.nsplanner.repositories;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLNamedIndividual;

import br.com.atom.common.owlmanager.OntologyManager;
import br.com.atom.nsplanner.dtos.HealingCriteriaDto;
import br.com.atom.nsplanner.util.NamedDataProp;
import br.com.atom.nsplanner.util.NamedObjectProp;

public class HealingCriteriaRepository {

	protected OntologyManager ontomanager;

	public HealingCriteriaRepository(OntologyManager ontomanager) {
		this.ontomanager = ontomanager;
	}

	public HealingCriteriaDto getHealingCriteria(String indName) {

		HealingCriteriaDto hcDto = new HealingCriteriaDto();
		hcDto.setName(indName);

		Set<OWLNamedIndividual> actions = ontomanager.getOpValuesPerIndividualWithoutReasoner(indName, NamedObjectProp.HASACTIONTYPE);
		for (OWLNamedIndividual action : actions) {
			hcDto.setActionType(action.getIRI().getShortForm());
			break;
		}

		Set<OWLLiteral> healThr = ontomanager.getDpValuesPerIndividualWithoutReasoner(indName, NamedDataProp.HASHEALTHRESHOLD);
		for (OWLLiteral value : healThr) {
			hcDto.setHealThreshold(value.parseInteger());
			break;
		}

		Set<OWLLiteral> healRelOp = ontomanager.getDpValuesPerIndividualWithoutReasoner(indName,
				NamedDataProp.HASHEALRELATIONALOPERATION);
		for (OWLLiteral value : healRelOp) {
			hcDto.setHealRelationalOperation(value.getLiteral());
			break;
		}

		Set<OWLNamedIndividual> metrics = ontomanager.getOpValuesPerIndividualWithoutReasoner(indName,
				NamedObjectProp.HASNSMONITORINGPARAMREF);
		for (OWLNamedIndividual metric : metrics) {
			hcDto.setNsMonitoringParamRef(metric.getIRI().getShortForm());
			break;
		}

		return hcDto;

	}

}
